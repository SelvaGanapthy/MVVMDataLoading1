package com.trickyandroid.mvvmdataloading.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.trickyandroid.mvvmdataloading.ui.activities.MainActivity

class AppController : Application() {
    //Static  methods & variable
    companion object {
        val TAG = AppController::class.java.simpleName
        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        var mainActivity: MainActivity? = null
        @SuppressLint("StaticFieldLeak")
        var instance: AppController? = null
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
    }


    val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }


    override fun onCreate() {
        super.onCreate()
        instance = this
        context = this.applicationContext
    }

    //Instance creation for all Activity
    @Synchronized
    public fun getInstance(): AppController = instance!!


    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue?.add(request)

    }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = TAG
        requestQueue?.add(request)
    }

    fun cancelPendingRequests(tag: Any) {
        if (requestQueue != null) {
            requestQueue?.cancelAll(tag)
        }
    }


}