package com.psm.horrorg.Db
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.psm.horrorg.Model.User

class DbHelper (context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER){
    companion object{
        private val DATABASE_VER= 1
        private val DATABASE_NAME = "Horrorg.db"
        //Table
        private val TABLE_NAME = "User"
        private val COL_ID = "Id"
        private val COL_NAME = "UserName"
        private val COL_PASSWORD = "Password"
        private val COL_DATE = "DateBirth"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY:String = ("CREATE TABLE  $TABLE_NAME ( $COL_ID INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_PASSWORD TEXT, $COL_DATE TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allUser:List<User>
        get(){
            val lstUsers = ArrayList<User>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null )
            if(cursor.moveToFirst()){
                do{
                    val user = User()
                    user.id= cursor.getColumnIndex(COL_ID)
                    user.username= cursor.getColumnIndex(COL_NAME).toString()
                    user.password= cursor.getColumnIndex(COL_PASSWORD).toString()
                    user.dateBirth= cursor.getColumnIndex(COL_DATE).toString()
                    lstUsers.add(user)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstUsers
        }

    fun addUser(user:User){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,user.id)
        values.put(COL_NAME,user.username)
        values.put(COL_PASSWORD,user.password)
        values.put(COL_DATE,user.dateBirth)
        db.insert(TABLE_NAME,null, values)
        db.close()
    }

    fun updateUser(user:User):Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,user.id)
        values.put(COL_NAME,user.username)
        values.put(COL_PASSWORD,user.password)
        values.put(COL_DATE,user.dateBirth)

        return db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(user.id.toString()))

    }

    fun deleteUser(user:User){
        val db = this.writableDatabase


        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(user.id.toString()))
        db.close()

    }

}