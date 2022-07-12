package ru.osa.gb.homework.notes.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
    private int selectedNoteId;
    NoteDetailFragment noteDetailFragment;
    NoteDetailFragment fragmentToRemove;

    private NotesRepository notesRepository;

    public NotesListFragment() {
        // Required empty public constructor
    }

    public static NotesListFragment newInstance() {
        NotesListFragment fragment = new NotesListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesRepository = NotesRepoImpl.getInstance(getContext());
        Log.d("NotesList", "onCreate: done");

        if (savedInstanceState != null && isPortrait()) {
            int noteId = savedInstanceState.getInt(BUNDLE_NOTE_ID);
            showNoteDetail(notesRepository.getNote(noteId));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("NotesList", "onCreateView: done");

        View notesListView = inflater.inflate(R.layout.fragment_notes_list, container, false);


        List<Note> notesList = notesRepository.getAllNotes();

        for (Note item : notesList
        ) {
            TextView note = new TextView(getContext());
            note.setText(item.getTitle());
            note.setTextSize(getActivity().getResources().getDimension(R.dimen.note_list_text_size));
            container.addView(note);
            note.setOnClickListener(v -> {
                selectedNoteId = item.getId();
                showNoteDetail(item);
            });
        }
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

        if (fragmentToRemove != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .hide(fragmentToRemove)
                    .remove(fragmentToRemove)
                    .commitNow();
            Log.d("LANDSCAPE", "Remove!!! " + fragmentToRemove);


            final List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                Log.d("LANDSCAPE", "LIST ____ " + fragment);
            }


        }
        noteDetailFragment = NoteDetailFragment.newInstance(note.getId());
        fragmentToRemove = noteDetailFragment;

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notesDetailOnMain, noteDetailFragment)
                .commit();

        Log.d("LANDSCAPE", "NEW " + noteDetailFragment);

    }

    private void showPortraitNoteDetail(Note note) {
        Activity activity = requireActivity();
        Intent intent = new Intent(activity, NoteDetailActivity.class);
        intent.putExtra(BUNDLE_NOTE_ID, note.getId());
        startActivity(intent);
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