package ru.osa.gb.homework.notes.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import ru.osa.gb.homework.notes.R;
import ru.osa.gb.homework.notes.model.Note;
import ru.osa.gb.homework.notes.model.NotesRepoImpl;
import ru.osa.gb.homework.notes.model.NotesRepository;

public class NoteDetailFragment extends Fragment {

    private static int noteId;
    private static NoteDetailFragment fragment;

    public static NoteDetailFragment getInstance(int Id) {
        fragment = new NoteDetailFragment();
        noteId = Id;
        Log.d("FRMS", "getInstance noteId: " + noteId);
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

        ViewGroup noteDetailContainer = noteDetail.findViewById(R.id.noteDetailContainer);

        fillDetailNoteView(noteDetailContainer, noteDetail.getContext());

        Log.d("FRMS", "NoteDetailFragment onCreateView: done");

        return noteDetail;
    }

    private void fillDetailNoteView(ViewGroup container, Context context) {
        NotesRepository notesRepository = NotesRepoImpl.getInstance(context);
        Note note = notesRepository.getNote(noteId);

        TextInputEditText noteTitle = new TextInputEditText(context);
        noteTitle.setText(note.getTitle());
        noteTitle.setTextSize(getActivity().getResources().getDimension(R.dimen.note_detail_text_size_title));

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotesRepository notesRepository = NotesRepoImpl.getInstance(context);
                Note note = notesRepository.getNote(noteId);
                note.setTitle(charSequence.toString());
                notesRepository.editNote(note);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        container.addView(noteTitle);

        TextInputEditText noteText = new TextInputEditText(context);
        noteText.setText(note.getText());
        noteText.setTextSize(getActivity().getResources().getDimension(R.dimen.note_detail_text_size_main));
        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotesRepository notesRepository = NotesRepoImpl.getInstance(context);
                Note note = notesRepository.getNote(noteId);
                note.setText(charSequence.toString());
                notesRepository.editNote(note);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        container.addView(noteText);

        TextView noteAuthor = new TextView(context);
        noteAuthor.setText(getString(R.string.author_title) + " " + note.getAuthor());
        noteAuthor.setTextSize(getActivity().getResources().getDimension(R.dimen.note_detail_text_size_service));
        noteAuthor.setTextColor(getActivity().getResources().getColor(R.color.note_service_info));
        container.addView(noteAuthor);




        TextView noteDate = new TextView(context);
        noteDate.setText(getString(R.string.created_title) + " " + note.getChangeDate());
        noteDate.setTextSize(getActivity().getResources().getDimension(R.dimen.note_detail_text_size_service));
        noteDate.setTextColor(getActivity().getResources().getColor(R.color.note_service_info));
        // container.addView(noteDate);

        ImageView editBut = new ImageView(context);
        editBut.setImageDrawable(getActivity().getDrawable(R.drawable.ic_baseline_edit_24));



        editBut.setOnClickListener(view -> {
            Toast.makeText(context, "Редактор", Toast.LENGTH_SHORT).show();
            DialogFragment newFragment = new SelectDateFragment(noteId);
            newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");

        });

        ViewGroup dateDiv = new LinearLayout(context);
        ((LinearLayout) dateDiv).setOrientation(LinearLayout.HORIZONTAL);
        dateDiv.addView(noteDate);
        dateDiv.addView(editBut);

        container.addView(dateDiv);

//        Button backBtn = new Button(getActivity());
//        backBtn.setText("Назад");
//        backBtn.setOnClickListener(v -> {
//                    NotesListFragment noteListFragment = NotesListFragment.getInstance();
//                    FragmentManager fm = requireActivity().getSupportFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.mainFragmentContainer, noteListFragment);
//                    ft.commit();
//            Log.d("FRMS", "NoteDetailFragment: remove");
//                }
//        );
//
//
//        container.addView(backBtn);
    }
}