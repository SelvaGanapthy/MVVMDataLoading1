package com.trickyandroid.mvvmdataloading.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.trickyandroid.mvvmdataloading.app.AppController
import com.trickyandroid.mvvmdataloading.data.models.DataModel
import java.lang.StringBuilder

class DatabaseHelper(var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /*Static Variable & Methods*/
    companion object {
        private val DATABASE_NAME: String = "TEST_DB"
        private val TABLE_NAME: String = "TEST_TABLE"
        private val UID: String = "id"
        private val TITLE: String = "Title"
        private val DESC: String = "Desc"
        private val IMGURL: String = "ImgUrl"
        private val DATABASE_VERSION: Int = 1
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            p0?.execSQL(
                "CREATE TABLE $TABLE_NAME( $UID INTEGER PRIMARY KEY  ,$TITLE VARCHAR(255)," +
                        "$DESC VARCHAR(25),$IMGURL VARCHAR(25) )"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        try {
            p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(p0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /* Insert Json data into db*/

    fun dbInsert(indexId: Int, title: String, desc: String, imgUrl: String): Boolean {

        var db: SQLiteDatabase = this.writableDatabase
        var cv: ContentValues = ContentValues()
        cv.put(UID, indexId)
        cv.put(TITLE, title)
        cv.put(DESC, desc)
        cv.put(IMGURL, imgUrl)
        /*Data inset into DB & if success return true else false */
        var id: Long = db.insert(TABLE_NAME, null, cv)
        return if (id > -1) true else false

    }

    /* Retrieve all data from db */
    fun getAllData(): Unit {

        var dbDataList: ArrayList<DataModel> = ArrayList()
        val db: SQLiteDatabase = this.writableDatabase
        var columns = arrayOf<String>(UID, TITLE, DESC, IMGURL)
        var cursor: Cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)

        /* Get & stored result set into arraylist using cursor */
        if (cursor.moveToFirst()) {

            do {
                try {

                    var model: DataModel = DataModel()
                    model.id = cursor.getInt(cursor.getColumnIndex(UID))
                    model.title = StringBuilder(cursor.getString(cursor.getColumnIndex(TITLE)))
                    model.desc = StringBuilder(cursor.getString(cursor.getColumnIndex(DESC)))
                    model.imgUrl = StringBuilder(cursor.getString(cursor.getColumnIndex(IMGURL)))
                    dbDataList.add(model)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext() && !cursor.isAfterLast)


            /* Check stored dbdataList is empty ! if not just pass to ViewModel */
            if (!dbDataList.isEmpty()) {
                AppController.mainActivity?.mainViewModel?.getDBDataList(dbDataList)
            }

        } else {
            Toast.makeText(context, "No data Found", Toast.LENGTH_SHORT).show()
        }

        /* Close the db all operation is finished */
        db.close()
    }


}