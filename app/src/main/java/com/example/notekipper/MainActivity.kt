package com.example.notekipper

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var noteposition= POSITION_NOT_SET
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
//        var  value=0;
//        textview.text="0"
//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
//            value++
//            textview.text=value.toString()
////            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                .setAction("Action", null).show()
//        }


        val adapterCourse=ArrayAdapter<CourseInfo>(
            this,android.R.layout.simple_spinner_dropdown_item,DataManeger.courses.values.toList()
        )
        spinner.adapter=adapterCourse

        noteposition=savedInstanceState?.getInt(Note_position, POSITION_NOT_SET)?:
            intent.getIntExtra(Note_position, POSITION_NOT_SET)

        if (noteposition!= POSITION_NOT_SET){
            displayNote()

        }
        else{
            DataManeger.notes.add(NoteInfo())
            noteposition=DataManeger.notes.lastIndex
        }



    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putInt(Note_position, noteposition)
    }

    private fun displayNote() {
        val note= DataManeger.notes[noteposition]
        notetitle.setText(note.title)
        noteText.setText(note.text)
        val coursePosition=DataManeger.courses.values.indexOf(note.course)
        spinner.setSelection(coursePosition)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_Next ->{
                moveNext()
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++noteposition
        displayNote()
        invalidateOptionsMenu()

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(noteposition>=DataManeger.notes.lastIndex){
            val  menuItem=menu?.findItem(R.id.action_Next)
            if (menuItem!=null){
                menuItem.icon=getDrawable(R.drawable.ic_baseline_block_24)
                menuItem.isEnabled=false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note=DataManeger.notes[noteposition]
        note.title=notetitle.text.toString()
        note.text=noteText.text.toString()
        note.course=spinner.selectedItem as CourseInfo

    }
}
