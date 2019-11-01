package com.trickyandroid.mvvmdataloading.data.api

import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.trickyandroid.mvvmdataloading.app.AppController
import com.trickyandroid.mvvmdataloading.data.models.DataModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

class DataManager(mContext: AppController? = AppController.instance) {

    //Static Methods & Variable
    companion object {
        var mInstance: DataManager = DataManager()

        /*Global API */
        private val GETDATA_URL: String =
            "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=info|description|pageimages&generator=prefixsearch&redirects=1&formatversion=2&inprop=url&piprop=thumbnail&pithumbsize=50&gpssearch=android"
    }

    /*Get data from Json request*/
    internal fun getDataLoad(): Unit {

        val jsonObjReq: JsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, GETDATA_URL, null, Response.Listener { response ->

                if (response != null) {
                    try {

                        var dataList: ArrayList<DataModel> = ArrayList()

                        var jodataList: JSONObject = response.getJSONObject("query")

                        if (jodataList.length() != 0) {

                            var jadataList: JSONArray = jodataList.getJSONArray("pages")

                            for (i in 0 until jadataList.length()) {

                                var jodata: JSONObject = jadataList.getJSONObject(i) as JSONObject
                                var model: DataModel = DataModel()

                                model.id = jodata.getInt("index")
                                model.title = StringBuilder(jodata.getString("title"))
                                model.desc = StringBuilder(jodata.getString("description"))

                                /*Check if thumbnail json object exists or not*/
                                if (jodata.has("thumbnail")) {
                                    var joImg: JSONObject = jodata.getJSONObject("thumbnail")
                                    model.imgUrl = StringBuilder(joImg.getString("source"))
                                } else {
                                    model.imgUrl = StringBuilder("null")
                                }

                                /*Datamodel added into arraylist*/
                                dataList.add(model)
                            }

                            if (!dataList.isEmpty()) {
                                AppController.mainActivity?.mainViewModel?.loadDBDataList(dataList)
                            } else {
                                Toast.makeText(AppController.context, "No Data Found! ", Toast.LENGTH_SHORT).show()
                            }


                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }


            }, Response.ErrorListener {
                Toast.makeText(AppController.context, "Failed to Load!", Toast.LENGTH_SHORT).show()
            })

        /*Retry policy for to set timeout , no of time retry jsonrequest & backoffmutlipiler for calc both timeout & retryno */
        val retryPolicy: RetryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        jsonObjReq.retryPolicy = retryPolicy
        // Adding request to volley request queue
        AppController.instance?.addToRequestQueue(jsonObjReq, "")

    }


}