package com.baskoroadi.datacovidpwr

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class DataApiWorker (appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    val mContext = appContext

    override fun doWork(): Result {
        val future = RequestFuture.newFuture<JSONObject>()

        val url = "https://covid19.mathdro.id/api/countries/id"

        val request = JsonObjectRequest(Request.Method.GET, url, null, future, future)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(mContext)

        // Add the request to the RequestQueue.
        queue.add(request)

        val response = future[60, TimeUnit.SECONDS]

        val output: Data = workDataOf("responseJson" to response.toString())

        return Result.success(output)
    }
}