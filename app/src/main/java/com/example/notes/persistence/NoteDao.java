package com.example.notes.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.Model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    long[] insertNotes(Note...notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Update
    void update(Note ...notes);

    @Delete
    void delete(Note ...notes);
}
