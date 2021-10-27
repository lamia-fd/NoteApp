package com.example.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteException



import android.provider.SyncStateContract.Helpers.insert

class Helper(context: Context):SQLiteOpenHelper(context,"NotesData.db",null,1) {
    var SOLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(p: SQLiteDatabase?) {
        if (p != null) {
            p.execSQL("create table notes (id INTEGER PRIMARY KEY,Note TEXT)")

        }

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun savedata(note: String): Long {
        val cv = ContentValues()
        cv.put("Note", note)
        var status = SOLiteDatabase.insert("notes", null, cv)
        //  SOLiteDatabase.close()
        return status

    }

    @SuppressLint("Range")
    fun retrieves(): ArrayList<noteInfo> {
        val listOfNotes: ArrayList<noteInfo> = ArrayList()
        var c = this.readableDatabase.rawQuery("select * from notes", null)

        if (c.moveToFirst()) {
            do {

                var noteText = c.getString(c.getColumnIndex("Note"))
                var noteID = c.getString(c.getColumnIndex("id"))


                listOfNotes.add(noteInfo(noteText, noteID.toInt()))
            } while (c.moveToNext())
        }

        return listOfNotes
    }

    fun edit(id: Int, newNote: String): Int {
        var cv = ContentValues()
        cv.put("Note", newNote)
        var updated = SOLiteDatabase.update("notes", cv, "id=?", arrayOf(id.toString()))
        // SOLiteDatabase.rawQuery("UPDATE notes SET Note = $newNote WHERE id=$cv",null)

        return updated
    }
    fun deleteNote(id: Int): Int {
        var cv = ContentValues()

        var deleted = SOLiteDatabase.delete("notes", "id=?", arrayOf(id.toString()))
        // SOLiteDatabase.rawQuery("UPDATE notes SET Note = $newNote WHERE id=$cv",null)

        return deleted
    }


}