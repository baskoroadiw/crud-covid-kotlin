package com.baskoroadi.datacovidpwr

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_info_covid.*
import org.json.JSONObject
import java.text.DecimalFormat

class InfoCovidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_covid)

        beginProgress()
        getDataCovidIndo()
    }

    private fun getDataCovidIndo(){
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val workGetDataCovid = OneTimeWorkRequestBuilder<DataApiWorker>()
            .setConstraints(constraint)
            .build()

        WorkManager.getInstance(this).enqueue(workGetDataCovid)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workGetDataCovid.id)
            .observe(this, Observer { info ->
                if (info != null && info.state.isFinished) {

                    val responseJson = info.outputData.getString("responseJson")

                    val rootApi = JSONObject(responseJson)

                    for (i in 0..0){
                        val jmlPositif = rootApi.getJSONObject("confirmed").getString("value")
                        val jmlSembuh = rootApi.getJSONObject("recovered").getString("value")
                        val jmlMeninggal = rootApi.getJSONObject("deaths").getString("value")

                        val formatter = DecimalFormat("#,###")

                        tvPositif.setText(formatter.format(jmlPositif.toDouble()))
                        tvSembuh.setText(formatter.format(jmlSembuh.toDouble()))
                        tvMeninggal.setText(formatter.format(jmlMeninggal.toDouble()))

                        endProgress()
                    }
                }
            })
    }

    private fun beginProgress(){
        pbPositif.visibility = View.VISIBLE
        pbSembuh.visibility = View.VISIBLE
        pbMeninggal.visibility = View.VISIBLE
        tvPositif.visibility = View.GONE
        tvSembuh.visibility = View.GONE
        tvMeninggal.visibility = View.GONE
        tvTitlePositif.visibility = View.GONE
        tvTitleSembuh.visibility = View.GONE
        tvTitleMeninggal.visibility = View.GONE
    }

    private fun endProgress(){
        pbPositif.visibility = View.GONE
        pbSembuh.visibility = View.GONE
        pbMeninggal.visibility = View.GONE
        tvPositif.visibility = View.VISIBLE
        tvSembuh.visibility = View.VISIBLE
        tvMeninggal.visibility = View.VISIBLE
        tvTitlePositif.visibility = View.VISIBLE
        tvTitleSembuh.visibility = View.VISIBLE
        tvTitleMeninggal.visibility = View.VISIBLE
    }
}
