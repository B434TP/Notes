package ru.osa.gb.homework.notes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ru.osa.gb.homework.notes.R;
import ru.osa.gb.homework.notes.model.Note;
import ru.osa.gb.homework.notes.model.NotesRepoImpl;
import ru.osa.gb.homework.notes.model.NotesRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetailFragment extends Fragment {

    private static int noteId;

    public NoteDetailFragment() {
        // Required empty public constructor
    }


    public static NoteDetailFragment newInstance(int Id) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        noteId = Id;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View noteDetail = inflater.inflate(R.layout.fragment_note_detail, container, false);

        NotesRepository notesRepository = NotesRepoImpl.getInstance(getContext());
        Note note = notesRepository.getNote(noteId);

        TextView noteTitle = new TextView(getContext());
        noteTitle.setText(note.getTitle());
        noteTitle.setTextSize(getActivity().getResources().getDimension(R.dimen.note_detail_text_size_title));
        container.addView(noteTitle);

        TextView noteText = new TextView(getContext());
        noteText.setText(note.getText());
        noteText.setTextSize(getActivity().getResources().getDimension(R.dimen.note_detail_text_size_main));
        container.addView(noteText);

        TextView noteAuthor = new TextView(getContext());
        noteAuthor.setText(getString(R.string.author_title) + note.getAuthor());
        noteAuthor.setTextSize(getActivity().getResources().getDimension(R.dimen.note_detail_text_size_service));
        noteAuthor.setTextColor(R.color.note_service_info);
        container.addView(noteAuthor);

        TextView noteDate = new TextView(getContext());
        noteDate.setText(getString(R.string.created_title) + note.getCreateDate());
        noteDate.setTextSize(getActivity().getResources().getDimension(R.dimen.note_detail_text_size_service));
        noteDate.setTextColor(R.color.note_service_info);
        container.addView(noteDate);

        ImageView editBut = new ImageView(getContext());
        editBut.setImageDrawable(getActivity().getDrawable(R.drawable.ic_baseline_edit_24));

        editBut.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Редактор", Toast.LENGTH_SHORT).show();


            DialogFragment newFragment = new SelectDateFragment(noteId);
            newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");

        });

        container.addView(editBut);

        return noteDetail;
    }
}