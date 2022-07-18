package ru.osa.gb.homework.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.osa.gb.homework.notes.R;
import ru.osa.gb.homework.notes.model.Note;
import ru.osa.gb.homework.notes.model.NotesRepoImpl;
import ru.osa.gb.homework.notes.model.NotesRepository;

public class NotesListFragment extends Fragment {

    public static final String BUNDLE_NOTE_ID = "noteID";
    private static NotesListFragment fragment;
    private int selectedNoteId;
    NoteDetailFragment noteDetailFragment;

    private NotesRepository notesRepository;

    public static NotesListFragment getInstance() {

        fragment = new NotesListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FRMS", "NotesListFragment onCreate: done");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View notesListView = inflater.inflate(R.layout.fragment_notes_list, container, false);

        ViewGroup listContainer = notesListView.findViewById(R.id.NoteListContainer);

        notesRepository = NotesRepoImpl.getInstance(notesListView.getContext());
        List<Note> notesList = notesRepository.getAllNotes();

        for (Note item : notesList
        ) {
            TextView note = new TextView(getContext());
            note.setText(item.getTitle());
            note.setTextSize(getActivity().getResources().getDimension(R.dimen.note_list_text_size));
            listContainer.addView(note);
            note.setOnClickListener(v -> {
                selectedNoteId = item.getId();
                showNoteDetail(item);
            });
        }

        Log.d("FRMS", "NotesListFragment onCreateView: done");
        return notesListView;
    }

    private void showNoteDetail(Note note) {
        if (isPortrait()) {
            showPortraitNoteDetail(note);
        } else {
            showLandScapeNoteDetail(note);
        }
    }

    private void showLandScapeNoteDetail(Note note) {


        noteDetailFragment = NoteDetailFragment.getInstance(note.getId());

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.notesDetailOnMain, noteDetailFragment, "NoteDetail");
        // ft.addToBackStack(null);
        Log.d("FRMS", "NoteDetailFragment: replace");
        ft.commit();


        Log.d("LANDSCAPE", "NEW " + noteDetailFragment);

    }


    private void showPortraitNoteDetail(Note note) {

        noteDetailFragment = NoteDetailFragment.getInstance(note.getId());

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!noteDetailFragment.isAdded()) {
            ft.replace(R.id.mainFragmentContainer, noteDetailFragment, "NoteDetail");
            ft.addToBackStack(null);
            Log.d("FRMS", "NoteDetailFragment: replace");
            ft.commit();
        }

    }


    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(BUNDLE_NOTE_ID, selectedNoteId);
        super.onSaveInstanceState(outState);
    }
}