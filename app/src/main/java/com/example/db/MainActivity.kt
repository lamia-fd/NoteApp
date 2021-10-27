package com.example.db

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class MainActivity : AppCompatActivity() {
    //  val userNote = ArrayList<String>()
    lateinit var tv1: EditText
    lateinit var dbh :Helper
    lateinit var button: Button
    lateinit var myRv:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv1 = findViewById(R.id.tv1)
        val layout = findViewById<ConstraintLayout>(R.id.layout)
        dbh = Helper(applicationContext)
        myRv = findViewById(R.id.rvMain)
        layout.setBackgroundResource(R.drawable.background)
        button = findViewById(R.id.button)
        myRv.adapter = recycler(this,dbh.retrieves())
        myRv.layoutManager = LinearLayoutManager(this)

        button.setOnClickListener {

            if(tv1.text.isNotBlank()) {
                addNote()
                tv1.clearFocus()
            }else{
                Toast.makeText(applicationContext, "write something", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun addNote(){
        var note=tv1.text.toString()
        val status = dbh.savedata(note)
        if(status>0) {
            Toast.makeText(applicationContext, "data is served", Toast.LENGTH_LONG).show()
        }else if (status<0){
            Toast.makeText(applicationContext, "fail to serve", Toast.LENGTH_LONG).show()

        }
        /////update the notes to the recycler view
        myRv.adapter = recycler(this,dbh.retrieves())
        myRv.layoutManager = LinearLayoutManager(this)

        myRv.adapter?.notifyDataSetChanged()
    }

    fun openDialog(id:Int){

        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        dialogBuilder.setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> dbh.edit(id, updatedNote.text.toString())

                myRv.adapter = recycler(this,dbh.retrieves())
                myRv.layoutManager = LinearLayoutManager(this)

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
    fun deleteNote(id:Int){
        var deleted=dbh.deleteNote(id)
        if (deleted<1){
            Toast.makeText(applicationContext,"not working..",Toast.LENGTH_LONG).show()

        }
        myRv.adapter = recycler(this,dbh.retrieves())
        myRv.layoutManager = LinearLayoutManager(this)

    }




}
