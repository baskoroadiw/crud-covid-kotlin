package com.baskoroadi.datacovidpwr

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.RestrictionEntry.TYPE_NULL
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity() {

    lateinit var cal : Calendar
    lateinit var db : FirebaseFirestore
    lateinit var covid : Covid
    lateinit var id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //init calendar
        cal = Calendar.getInstance()

        //init Firestore
        db = FirebaseFirestore.getInstance()

        if (intent.extras == null){
            initViewAdd()
        }
        else{
            initViewEdit()
        }

        initDatePicker()

        buttonAdd.setOnClickListener {
            if (intent.extras == null){
                addData()
            }
            else{
                updateData()
            }
        }
    }

    private fun initViewAdd(){
        supportActionBar?.title = resources.getString(R.string.label_tambahdata)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewEdit(){
        supportActionBar?.title = resources.getString(R.string.label_updatedata)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        txt_add.setText(resources.getString(R.string.label_updatedata))

        covid = intent.getParcelableExtra("parcelCovid")
        id = intent.getStringExtra("id")

        editText_tanggal.setText(covid.date)
        editText_odp.setText(covid.odp)
        editText_pdp.setText(covid.pdp)
        editText_positif.setText(covid.positif)
    }

    private fun initDatePicker(){
        //disable editText tanggal
        editText_tanggal.inputType = TYPE_NULL

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        editText_tanggal.setOnClickListener {
            DatePickerDialog(this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateDateInView() {
        val myFormatTgl = "dd"
        val sdfTgl = SimpleDateFormat(myFormatTgl, Locale.getDefault())
        val tanggal = sdfTgl.format(cal.time)

        val myFormatBulan = "MMMM"
        val sdfBulan = SimpleDateFormat(myFormatBulan, Locale.getDefault())
        val bulan = sdfBulan.format(cal.time)
        var fixBulan:String?=""

        when(bulan){
            "January" -> {
                fixBulan = "Januari"
            }
            "February" -> {
                fixBulan = "Februari"
            }
            "March" -> {
                fixBulan = "Maret"
            }
            "April" -> {
                fixBulan = "April"
            }
            "May" -> {
                fixBulan = "Mei"
            }
            "June" -> {
                fixBulan = "Juni"
            }
            "July" -> {
                fixBulan = "Juli"
            }
            "August" -> {
                fixBulan = "Agustus"
            }
            "September" -> {
                fixBulan = "September"
            }
            "October" -> {
                fixBulan = "Oktober"
            }
            "November" -> {
                fixBulan = "November"
            }
            "December" -> {
                fixBulan = "Desember"
            }

        }

        val myFormatThn = "yyyy"
        val sdfThn = SimpleDateFormat(myFormatThn, Locale.getDefault())
        val tahun = sdfThn.format(cal.time)

        val fixDate = tanggal+" "+fixBulan+" "+tahun
        editText_tanggal.setText(fixDate)
    }

    private fun addData(){
        val date = editText_tanggal.text.toString()
        val odp = editText_odp.text.toString()
        val pdp = editText_pdp.text.toString()
        val positif = editText_positif.text.toString()

        val dataCovid = hashMapOf(
            "date" to date,
            "odp" to odp,
            "pdp" to pdp,
            "positif" to positif
        )

        db.collection("datacovid")
            .document()
            .set(dataCovid)
            .addOnSuccessListener {
                Toast.makeText(this,"Data Tersimpan",Toast.LENGTH_SHORT).show()

                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Gagal Tersimpan",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateData() {
        val date = editText_tanggal.text.toString()
        val odp = editText_odp.text.toString()
        val pdp = editText_pdp.text.toString()
        val positif = editText_positif.text.toString()

        val dataCovid = hashMapOf(
            "date" to date,
            "odp" to odp,
            "pdp" to pdp,
            "positif" to positif
        )

        db.collection("datacovid")
            .document(id)
            .set(dataCovid)
            .addOnSuccessListener {
                Toast.makeText(this,"Data Terupdate",Toast.LENGTH_SHORT).show()

                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Gagal Terupdate",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)

        if (intent.extras == null){
            val menuItem = menu?.findItem(R.id.menu_delete)
            menuItem?.setVisible(false)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete -> showDialogDel(id)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showDialogDel(strId: String) {
        //dialog pop delete
        val builder = AlertDialog.Builder(this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setTitle("Hapus Data")
            .setMessage("Yakin mau hapus data ini?")
            .setPositiveButton("Ya") { dialog, which ->
                deleteData(strId)
            }
            .setNegativeButton("Tidak", null)
        builder.create().show()
    }

    private fun deleteData(id : String){
        db.collection("datacovid")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this,"Data Terhapus",Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Gagal Terhapus",Toast.LENGTH_SHORT).show()
            }
    }
}