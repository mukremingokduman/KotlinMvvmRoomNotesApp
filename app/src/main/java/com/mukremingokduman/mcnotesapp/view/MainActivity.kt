package com.mukremingokduman.mcnotesapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mukremingokduman.mcnotesapp.*
import com.mukremingokduman.mcnotesapp.modal.Note
import com.mukremingokduman.mcnotesapp.viewmodal.NoteViewModal

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {
    lateinit var noteRv: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModal: NoteViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteRv = findViewById(R.id.idRVNotes)
        addFAB = findViewById(R.id.idFABAddNote)
        noteRv.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteRVAdapter(this, this, this)
        noteRv.adapter = noteRVAdapter
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            NoteViewModal::class.java)
        viewModal.allNotes.observe(this, Observer { list ->
            list?.let {
             noteRVAdapter.updateList(it)
            }
        })

        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }


    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity , AddEditNoteActivity::class.java)
        intent.putExtra("noteType" , "Edit")
        intent.putExtra("noteTitle" , note.noteTitle)
        intent.putExtra("noteDescription" , note.noteDescription )
        intent.putExtra("noteID" , note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        viewModal.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG ).show()
    }

}