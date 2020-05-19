package com.baskoroadi.datacovidpwr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lihat_data.*

class LihatDataActivity : AppCompatActivity() {

    private lateinit var mAdapter: FirestoreRecyclerAdapter<Covid, AdapterLihatData.ViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mCovidCollection = mFirestore.collection("datacovid")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_data)

        supportActionBar?.title = resources.getString(R.string.label_lihatdata)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab_add.setOnClickListener {
            startActivity(Intent(this,AddActivity::class.java))
        }

        rv_lihatdata.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@LihatDataActivity)
        }

        setupAdapter()
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupAdapter(){
        val options = FirestoreRecyclerOptions.Builder<Covid>()
            .setQuery(mCovidCollection, Covid::class.java)
            .build()

        mAdapter = AdapterLihatData(this,options)
        mAdapter.notifyDataSetChanged()
        rv_lihatdata.adapter = mAdapter
    }
}
