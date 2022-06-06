package com.example.notekipper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_note_list.*

class NoteList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        listNotes.adapter=ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            DataManeger.notes)
        listNotes.setOnItemClickListener{parent,vies, position,id ->
            val intent=Intent(this,MainActivity::class.java)
            intent.putExtra(Note_position, position)
            startActivity(intent)
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onResume() {
        super.onResume()
        (listNotes.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()

    }
}