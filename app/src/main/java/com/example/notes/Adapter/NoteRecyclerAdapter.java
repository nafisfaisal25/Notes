package com.example.notes.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notes.Model.Note;
import com.example.notes.Model.NoteClickListener;
import com.example.notes.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {

    private List<Note> mNoteList;
    private NoteClickListener mNoteClickListener;

    public NoteRecyclerAdapter(List<Note> notes, NoteClickListener noteClickListener) {
        this.mNoteList = notes;
        this.mNoteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item,parent,false );
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTextView.setText(mNoteList.get(position).getTitle());
        holder.timestampTeTextView.setText(mNoteList.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView timestampTeTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title);
            timestampTeTextView = itemView.findViewById(R.id.note_timestamp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mNoteClickListener.onNoteClick(getAdapterPosition());
        }
    }

}
