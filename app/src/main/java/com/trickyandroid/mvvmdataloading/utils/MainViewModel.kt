package com.trickyandroid.mvvmdataloading.utils

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trickyandroid.mvvmdataloading.app.AppController
import com.trickyandroid.mvvmdataloading.data.api.DataManager
import com.trickyandroid.mvvmdataloading.data.models.DataModel
import com.trickyandroid.mvvmdataloading.data.repository.DatabaseHelper
import com.trickyandroid.mvvmdataloading.ui.activities.MainActivity

class MainViewModel : ViewModel() {

    internal var dbDataList: MutableLiveData<ArrayList<DataModel>>? = null
    internal var databaseHelper: DatabaseHelper? = null

    fun getdataList(mainActivity: MainActivity): MutableLiveData<ArrayList<DataModel>> {

        if (dbDataList == null) {
            dbDataList = MutableLiveData<ArrayList<DataModel>>()
            databaseHelper = DatabaseHelper(mainActivity) //DB Creation
            /*Call DataLoading From Json Request*/
            DataManager.mInstance.getDataLoad()
        }
        return this.dbDataList!!
    }

    /*Insert & Retrieve data from DB*/
    fun loadDBDataList(dataList: ArrayList<DataModel>): Unit {

        for (i in 0 until dataList.size) {

            var model: DataModel = dataList[i]
            /*Store data into DB using Insert method */
            var row = databaseHelper?.dbInsert(
                model.id!!,
                model.title.toString(),
                model.desc.toString(),
                model.imgUrl.toString()

            )

            Log.i("index", row.toString())
        }

        /* Call Access Data from DB */
        databaseHelper?.getAllData()
    }


    fun getDBDataList(dbDataList: ArrayList<DataModel>): Unit {
        /*GET store db arrayList assign into Live dataList */
        this.dbDataList?.value = dbDataList
    }


    override fun onCleared() {
        super.onCleared()
    }
}