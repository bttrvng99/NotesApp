package com.example.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SQLiteHelper sqLiteHelper;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton newBtn;
    private Button findBtn, clearBtn;
    private EditText searchBox;
    private final static int REQUEST_CODE = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.notes_view);
        newBtn = findViewById(R.id.fab_new);
        findBtn = findViewById(R.id.searchbtn);
        clearBtn = findViewById(R.id.clearbtn);
        searchBox = findViewById(R.id.seachbox);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        sqLiteHelper = new SQLiteHelper(this);

        getAll();

        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newnote = new Intent(getApplicationContext(), NoteCreator.class);
                startActivity(newnote);
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Notes> list = new ArrayList<>();
                list.clear();
                list = sqLiteHelper.getNotesByKeyword(searchBox.getText().toString());
                adapter.setList(list);
                recyclerView.setAdapter(adapter);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBox.setText("");
                getAll();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode==RESULT_OK){
            getAll();
        }
    }

    private void getAll(){
        List<Notes> list = new ArrayList<>();
        list.clear();
        list = sqLiteHelper.getAll();
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getAll();
    }
}