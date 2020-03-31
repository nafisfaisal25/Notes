package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.notes.Model.Note;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        if (getIntent().hasExtra("selected_note")) {
            Note note = getIntent().getParcelableExtra("selected_note");
        }


    }
}
