package com.baskoroadi.datacovidpwr

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.list_item.view.*

class AdapterLihatData (private val context : Context, options: FirestoreRecyclerOptions<Covid>) : FirestoreRecyclerAdapter<Covid, AdapterLihatData.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item:Covid) {
        holder.bindItem(item)
        holder.view.setOnClickListener {
            val id = getSnapshots().getSnapshot(position).getId()
            val intent = Intent(context,AddActivity::class.java).apply {
                putExtra("parcelCovid",item)
                putExtra("id",id)
            }
            context.startActivity(intent)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItem(covid: Covid) {
            view.apply {
                val jumlahOdp = "Jumlah ODP\t\t\t\t: ${covid.odp}"
                val jumlahPdp = "Jumlah PDP\t\t\t\t: ${covid.pdp}"
                val jumlahPositif = "Jumlah Positif\t\t: ${covid.positif}"

                txt_jml_odp.text = jumlahOdp
                txt_jml_pdp.text = jumlahPdp
                txt_jml_positif.text = jumlahPositif
                txt_date.text = covid.date
            }
        }
    }
}