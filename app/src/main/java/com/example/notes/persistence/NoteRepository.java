package com.example.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.notes.Model.Note;

import java.util.List;

public class NoteRepository {
    private NoteDataBase mNodeDataBase;

    public NoteRepository(Context context) {
        mNodeDataBase = NoteDataBase.getInstance(context);
    }

    public void insertNoteTask(Note note){
        new InsertAsyncTask(mNodeDataBase.getNoteDao()).execute(note);
    }

    public void deleteNote(Note note){
        new DeleteAsyncTask(mNodeDataBase.getNoteDao()).execute(note);
    }

    public void updateNote(Note note){
        new UpdateAsyncTask(mNodeDataBase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> retrieveNotesTask(){
        return mNodeDataBase.getNoteDao().getNotes();
    }
}
