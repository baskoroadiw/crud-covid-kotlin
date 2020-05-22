package com.baskoroadi.datacovidpwr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_lihatdata.setOnClickListener {
            startActivity(Intent(this,LihatDataActivity::class.java))
        }

        button_infocovid.setOnClickListener {
            startActivity(Intent(this,InfoCovidActivity::class.java))
        }
    }
}
