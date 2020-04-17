package com.example.notes.persistence;

import android.os.AsyncTask;

import com.example.notes.Model.Note;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

    NoteDao mNoteDao;
    public InsertAsyncTask(NoteDao noteDao) {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.insertNotes(notes);
        return null;
    }
}
