package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.NotesViewHolder> {
    Context mContext;
    private List<Notes> list;
    private SQLiteHelper sqLiteHelper;
    public RecyclerViewAdapter(Context context){
        list = new ArrayList<>();
        mContext = context;
    }
    public void setList(List<Notes> list){
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.NotesViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.notes_item, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.NotesViewHolder holder,
                                 int position) {
        Notes notes = list.get(position);
        int id = notes.getId();
        holder.TitleTxt.setText(notes.getTitle());
        holder.contentTxt.setText(notes.getContent());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoteEditor.class);
                Notes picked = new Notes(id, holder.TitleTxt.getText().toString(),
                        holder.contentTxt.getText().toString());
                intent.putExtra("note", picked);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!=null) return list.size();
        else return 0;
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView TitleTxt;
        private TextView contentTxt;
        private CardView cardView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            TitleTxt = itemView.findViewById(R.id.note_title);
            contentTxt = itemView.findViewById(R.id.note_content);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
