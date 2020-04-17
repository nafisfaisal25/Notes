package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.notes.Adapter.NoteRecyclerAdapter;
import com.example.notes.Model.Note;
import com.example.notes.Model.NoteClickListener;
import com.example.notes.Util.NoteItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity implements NoteClickListener {

    private static final String TAG = "NotesListActivity";

    //Ui components
    RecyclerView mRecyclerView;
    NoteRecyclerAdapter mNoteRecyclerViewAdapter;
    FloatingActionButton mFab;

    //vars
    List<Note> mNoteList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFab = findViewById(R.id.fab);
        initRecyclerView();
        populateNoteList();
        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Notes");
        setListeners();
    }

    private void setListeners() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        mNoteRecyclerViewAdapter = new NoteRecyclerAdapter(mNoteList, this);
        mRecyclerView.setAdapter(mNoteRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NoteItemDecorator itemDecorator = new NoteItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
    }

    private void populateNoteList() {
        for(int i=0;i<100;i++){
            Note note = new Note();
            note.setTitle("Title #: " + i);
            note.setTimestamp("Mar, 2020");
            note.setContent("Content #: " + i);
            mNoteList.add(note);
        }
        mNoteRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteClick(int position) {
        Log.e(TAG, "onNoteClick: "+ position);
        Note note = mNoteList.get(position);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", note);
        startActivity(intent);
    }
}
