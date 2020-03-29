package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.notes.Adapter.NoteRecyclerAdapter;
import com.example.notes.Model.Note;
import com.example.notes.Util.NoteItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {

    private static final String TAG = "NotesListActivity";

    //Ui components
    List<Note> mNoteList = new ArrayList<>();

    //vars
    RecyclerView mRecyclerView;
    NoteRecyclerAdapter mNoteRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recycler_view);
        populateNoteList();
        initRecyclerView();
        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Notes");
    }

    private void initRecyclerView() {
        mNoteRecyclerViewAdapter = new NoteRecyclerAdapter(mNoteList);
        mRecyclerView.setAdapter(mNoteRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NoteItemDecorator itemDecorator = new NoteItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
    }

    private void populateNoteList() {
        Note note = new Note("Reminder"," ", "March 29, 2020");
        mNoteList.add(note);
        note = new Note("Todo"," ", "March 30, 2020");
        mNoteList.add(note);
        note = new Note("Exersize"," ", "March 21, 2020");
        mNoteList.add(note);
        note = new Note("Todo"," ", "March 30, 2020");
        mNoteList.add(note);
        note = new Note("Exersize"," ", "March 21, 2020");
        mNoteList.add(note);

        note = new Note("Todo"," ", "March 30, 2020");
        mNoteList.add(note);
        note = new Note("Exersize"," ", "March 21, 2020");
        mNoteList.add(note);



    }
}
