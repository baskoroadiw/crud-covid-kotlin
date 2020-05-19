package com.baskoroadi.datacovidpwr

import android.app.DatePickerDialog
import android.content.RestrictionEntry.TYPE_NULL
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity() {

    lateinit var cal : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //init calendar
        cal = Calendar.getInstance()

        supportActionBar?.title = resources.getString(R.string.label_tambahdata)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initDatePicker()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}