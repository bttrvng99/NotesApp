package com.example.notesapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteEditor extends AppCompatActivity {
    private FloatingActionButton doneBtn;
    private Button DelBtn;
    private EditText titleTxt;
    private EditText contentTxt;
    private SQLiteHelper sqLiteHelper;
    private int id;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        doneBtn = findViewById(R.id.fab_done);
        titleTxt = findViewById(R.id.TitleEditText);
        contentTxt = findViewById(R.id.ContentEdit);
        DelBtn = findViewById(R.id.delbtn);
        actionBar = getSupportActionBar();

        Intent receiveIntent = getIntent();
        if (receiveIntent.getSerializableExtra("note")!=null){
            Notes notes = (Notes)receiveIntent.getSerializableExtra("note");
            id = notes.getId();
            actionBar.setTitle(notes.getTitle().toString());
            titleTxt.setText(notes.getTitle().toString());
            contentTxt.setText(notes.getContent().toString());
        } else Toast.makeText(this,"Something wrong with retrieving note!",
                Toast.LENGTH_SHORT).show();

        sqLiteHelper = new SQLiteHelper(this);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes edited_note = new Notes(id, titleTxt.getText().toString(),
                        contentTxt.getText().toString());
                sqLiteHelper.update(edited_note);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.deleteNote(id);
                Intent deletedIntent = new Intent();
                setResult(RESULT_CANCELED, deletedIntent);
                finish();
            }
        });

    }
}