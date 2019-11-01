package com.trickyandroid.mvvmdataloading.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trickyandroid.mvvmdataloading.R
import com.trickyandroid.mvvmdataloading.app.AppController
import com.trickyandroid.mvvmdataloading.data.models.DataModel
import com.trickyandroid.mvvmdataloading.ui.adapters.DataAdapter
import com.trickyandroid.mvvmdataloading.utils.MainViewModel


class MainActivity : AppCompatActivity() {

    internal var rv: RecyclerView? = null
    internal var mainViewModel: MainViewModel? = null
    internal var adapter: DataAdapter? = null
    internal val requestcode_permisson: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* Check Self Permission */
        if (checkPermission()) ;
        else {
            requestPermission()
        }

        //Instance Creation
        AppController.mainActivity = this
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        rv = findViewById<View>(R.id.rv) as RecyclerView
        set_layoutManager()

        //Live Data to upload the recyclerview adapter
        mainViewModel?.getdataList(this@MainActivity)
            ?.observe(this, Observer<ArrayList<DataModel>> { dataList ->
                adapter = DataAdapter(this@MainActivity, dataList)
                rv?.adapter = this.adapter
            })

    }


    private fun set_layoutManager(): Unit {
        rv?.layoutManager = LinearLayoutManager(this@MainActivity)
        rv?.setHasFixedSize(true)
    }


    private fun checkPermission(): Boolean {
        var result: Int = ContextCompat.checkSelfPermission(
            getApplicationContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return (result == PackageManager.PERMISSION_GRANTED)
    }


    private fun requestPermission(): Unit {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            requestcode_permisson
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            requestcode_permisson ->
                if (grantResults.size > 0) {
                    val StoragePermisson = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (!StoragePermisson) {
                        Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_LONG)
                            .show()
                        checkPermission()
                    } else {
                        Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG)
                            .show()
                        checkPermission()
                    }

                }
        }
    }


}
