package com.example.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteCreator extends AppCompatActivity {
    private FloatingActionButton doneBtn;
    private EditText titleTxt;
    private EditText contentTxt;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creator);
        doneBtn = findViewById(R.id.fab_done);
        titleTxt = findViewById(R.id.TitleEditText);
        contentTxt = findViewById(R.id.ContentEdit);
        sqLiteHelper = new SQLiteHelper(this);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes new_note = new Notes(titleTxt.getText().toString(), contentTxt.getText().toString());
                sqLiteHelper.addNotes(new_note);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }


}