package com.example.notes.persistence;

import android.os.AsyncTask;

import com.example.notes.Model.Note;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

    NoteDao mNoteDao;
    public DeleteAsyncTask(NoteDao noteDao) {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.delete(notes);
        return null;
    }
}
