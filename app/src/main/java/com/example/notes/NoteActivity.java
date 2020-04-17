package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.print.PrinterId;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.notes.Model.Note;
import com.example.notes.persistence.NoteRepository;

public class NoteActivity extends AppCompatActivity {
    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //ui components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private ImageButton mCheckButton;
    private ImageButton mBackButton;
    private RelativeLayout mCheckBoxContainer;
    private RelativeLayout mBackArrowContainer;

    //var
    private Note mOldNote;
    private Note mNewNote;
    private GestureDetector mGestureDetector;
    private int mMode;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mLinedEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        mCheckButton = findViewById(R.id.toolbar_check);
        mBackButton = findViewById(R.id.toolbar_back_arrow);
        mCheckBoxContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mNoteRepository = new NoteRepository(this);
        if(isNewNode()) {
            setNewNoteProperty();
            enableEditMode();
        } else {
            setNoteProperty();
            disableInteraction();
        }
        setListeners();
    }

    private void setListeners() {
        mCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEditMode();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
            }
        });

        mLinedEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.e(TAG, "onDoubleTap: double tapped!");
                enableEditMode();
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
    }

    boolean isNewNode() {
        if(getIntent().hasExtra("selected_note")){
            mOldNote = getIntent().getParcelableExtra("selected_note");
            mMode = EDIT_MODE_DISABLED;
            return false;
        }
        mNewNote = new Note();
        return true;
    }

    private void setNewNoteProperty() {
        mEditTitle.setText("Note Title");
        mViewTitle.setText("Note Title");
    }

    private void setNoteProperty() {
        mLinedEditText.setText(mOldNote.getContent());
        mEditTitle.setText(mOldNote.getTitle());
        mViewTitle.setText(mOldNote.getTitle());
    }

    private void enableEditMode() {
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckBoxContainer.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.VISIBLE);
        mViewTitle.setVisibility(View.GONE);
        mMode = EDIT_MODE_ENABLED;
        enableInterAction();
    }

    private void disableEditMode() {
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckBoxContainer.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.GONE);
        mViewTitle.setVisibility(View.VISIBLE);
        mMode = EDIT_MODE_DISABLED;
        mViewTitle.setText(mEditTitle.getText().toString());
        disableInteraction();
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mLinedEditText.getWindowToken(),0);
    }

    private void disableInteraction() {
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.setKeyListener(null);
    }

    private void enableInterAction() {
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
    }

    @Override
    public void onBackPressed() {
        if(mMode == EDIT_MODE_ENABLED) {
            disableEditMode();
        } else {
            saveChanges();
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if (mMode == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }

    private  void updateNoteProperty(Note note) {
        note.setTitle(mEditTitle.getText().toString());
        note.setContent(mLinedEditText.getText().toString());
        note.setTimestamp("1 may, 2020");
    }

    private void saveChanges(){
        if(mNewNote != null) {
            updateNoteProperty(mNewNote);
            insertNewNote();
        } else {
            updateNoteProperty(mOldNote);
            updateOldNode();
        }
    }

    private void updateOldNode() {
        if(!mOldNote.getContent().equals("") || !mOldNote.getTitle().equals("Note Title")) {
            mNoteRepository.updateNote(mOldNote);
        } else {
            deleteNode();
        }
    }

    private void deleteNode() {
        mNoteRepository.deleteNote(mOldNote);
    }

    private void insertNewNote() {
        if(!mNewNote.getContent().equals("") || !mNewNote.getTitle().equals("Note Title")) {
            mNoteRepository.insertNoteTask(mNewNote);
        }
    }
}
