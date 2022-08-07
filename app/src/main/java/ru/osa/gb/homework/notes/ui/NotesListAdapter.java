package ru.osa.gb.homework.notes.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.osa.gb.homework.notes.R;
import ru.osa.gb.homework.notes.model.Note;


public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {


    private List<Note> dataSource;
    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notes_list_notes_item, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNoteListItemView().setText(dataSource.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    public NotesListAdapter(List<Note> dataSource) {
        this.dataSource = dataSource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView noteListItemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteListItemView = itemView.findViewById(R.id.textView);

            noteListItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int noteId = dataSource.get(getAdapterPosition()).getId();
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(v, getAdapterPosition(),noteId);
                }
            });

        }

        public TextView getNoteListItemView() {
            return noteListItemView;
        }
    }

}
