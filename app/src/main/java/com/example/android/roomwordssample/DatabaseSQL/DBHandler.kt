package com.example.android.roomwordssample.DatabaseSQL

import android.content.ContentValues
import android.content.Context
import android.content.SearchRecentSuggestionsProvider
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class DBHandler(context : Context, name : String? , factory : SQLiteDatabase.CursorFactory?,version : Int) :
        SQLiteOpenHelper(context,DATABASE_NAME, factory,DATABASE_VERSION){

    //สร้างงและกำหนดค่าตัวแปร
    companion object{
        private val DATABASE_NAME = " MyData.db"
        private val DATABASE_VERSION = 1

        val WORD_TABLE_NAME     = "WORD"
        val COLUMN_WORDID       = "WORDID"
        val COLUMN_WORDNAME     = "WORDNAME"
        val COLUMN_CHECKIN      = "CHECKIN"
        val COLUMN_CHECKOUT     = "CHECKOUT"

    }

    //สร้าง ตารางของ DATABASE
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_WORD_TABLE  = ("CREATE TABLE $WORD_TABLE_NAME ("+
                "$COLUMN_WORDID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COLUMN_WORDNAME TEXT,"+
                "$COLUMN_CHECKIN BOOLEAN,"+
                "$COLUMN_CHECKOUT TEXT)")
        db?.execSQL(CREATE_WORD_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    fun getWords(mCtx : Context): ArrayList<Word> {
        val qry = "Select * From $WORD_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)
        val words = ArrayList<Word>()

        if (cursor.count == 0)

            Toast.makeText(mCtx, "No records Found ", Toast.LENGTH_SHORT).show()

        else {
            cursor.moveToFirst()
            while (!cursor.isAfterLast()) {
                val word = Word()
                word.wordID = cursor.getInt(cursor.getColumnIndex(COLUMN_WORDID))
                word.wordName = cursor.getString(cursor.getColumnIndex(COLUMN_WORDNAME))
                word.checkinName = cursor.getString(cursor.getColumnIndex(COLUMN_CHECKIN))
                words.add(word)
                cursor.moveToNext()
            }

            Toast.makeText(mCtx, "${cursor.count.toString()} Record Found", Toast.LENGTH_LONG).show()

        }
        cursor.close()
        db.close()
        return words
    }

    fun addWord (mCtx: Context,word: Word){

        val values = ContentValues()
        values.put(COLUMN_WORDNAME,word.wordName)
 //       values.put(COLUMN_CHECKIN,word.checkinName)
        val db = this.writableDatabase

        try {
            db.insert(WORD_TABLE_NAME,null,values)
   //         db.rawQuery("Insert Into $WORD_TABLE_NAME($COLUMN_WORDNAME) Values(?)")
            Toast.makeText(mCtx,"Word Add",Toast.LENGTH_SHORT).show()
        }
        catch (e : Exception){
        Toast.makeText(mCtx,e.message,Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun deleteWord( deletewordID : Int ) : Boolean{
        val qry = "Delete From $WORD_TABLE_NAME where $COLUMN_WORDID = $deletewordID"
        val db = this.writableDatabase
        var result = false
        try {
 //           val cursor = db.delete(WORD_TABLE_NAME,"$COLUMN_WORDID = ?", arrayOf(deletewordID.toString()))
            val cursor = db.execSQL(qry)
            result = true
        }
        catch (e : Exception){
            Log.e(ContentValues.TAG,"Error Deleting")
        }
        db.close()
        return result
    }


    fun checkout( id : String , wordName : String ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        var reslut : Boolean = false
        contentValues.put(COLUMN_WORDNAME,wordName)
        try {
            db.update(WORD_TABLE_NAME,contentValues,"$COLUMN_WORDID = ?", arrayOf(id))
            reslut = true
        }
        catch (e:Exception){
            reslut = false
        }
        Log.e(ContentValues.TAG,"Error Updating")
     return reslut
    }

}