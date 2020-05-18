package com.baskoroadi.datacovidpwr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LihatDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_data)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = resources.getString(R.string.label_lihatdata)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
