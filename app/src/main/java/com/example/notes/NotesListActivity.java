package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.example.notes.persistence.NoteRepository;
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
    NoteRepository mNoteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFab = findViewById(R.id.fab);
        mNoteRepository = new NoteRepository(this);
        initRecyclerView();
        retrieveNotes();
        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Notes");
        setListeners();
    }

    private void retrieveNotes() {
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if(mNoteList.size() > 0) {
                    mNoteList.clear();
                }
                if(notes != null){
                    mNoteList.addAll(notes);
                }
                mNoteRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
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
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addItemDecoration(itemDecorator);
    }

    @Override
    public void onNoteClick(int position) {
        Log.e(TAG, "onNoteClick: "+ position);
        Note note = mNoteList.get(position);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", note);
        startActivity(intent);
    }

    private void removeNote(int position) {
        mNoteRepository.deleteNote(mNoteList.get(position));
    }

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            removeNote(viewHolder.getAdapterPosition());
        }
    });
}
