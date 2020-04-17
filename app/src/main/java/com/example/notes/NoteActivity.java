package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.Model.Note;

public class NoteActivity extends AppCompatActivity {

    //ui components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;

    //var
    private boolean mIsNewNode;
    private Note mInitialNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mLinedEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        if(isNewNode()) {
            setNewNoteProperty();
        } else {
            setNoteProperty();
        }


    }

    boolean isNewNode() {
        if(getIntent().hasExtra("selected_note")){
            mInitialNote = getIntent().getParcelableExtra("selected_note");
            mIsNewNode = false;
            return false;
        }
        mIsNewNode = true;
        return true;
    }

    private void setNewNoteProperty() {
        mEditTitle.setText("Note Title");
        mViewTitle.setText("Note Title");
    }

    private void setNoteProperty() {
        mLinedEditText.setText(mInitialNote.getContent());
        mEditTitle.setText(mInitialNote.getTitle());
        mViewTitle.setText(mInitialNote.getTitle());
    }
}
