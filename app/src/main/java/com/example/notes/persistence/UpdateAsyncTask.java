package com.example.notes.persistence;

import android.os.AsyncTask;

import com.example.notes.Model.Note;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    NoteDao mNoteDao;
    public UpdateAsyncTask(NoteDao noteDao) {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.update(notes);
        return null;
    }
}
