package ru.osa.gb.homework.notes.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
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
    private ViewGroup listContainer;
    private View notesListView;

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

        notesListView = inflater.inflate(R.layout.fragment_notes_list, container, false);
        listContainer = notesListView.findViewById(R.id.NoteListContainer);

        RecyclerView recyclerView = notesListView.findViewById(R.id.NoteListContainer);


        fillNotesListView(recyclerView);

        getActivity().getSupportFragmentManager().setFragmentResultListener("NOTES_LIST_ACTION", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String signal = result.getString("ACTION");
                if (signal == "REFRESH") {
                    Log.d("FRMS", "NOTES_LIST_ACTION: REFRESH");
                    fillNotesListView(recyclerView);
                }
            }
        });

        Log.d("FRMS", "NotesListFragment onCreateView: done");
        return notesListView;
    }

    private void fillNotesListView(RecyclerView recyclerView) {
        notesRepository = NotesRepoImpl.getInstance(notesListView.getContext());
        List<Note> notesList =  notesRepository.getNotes();

        NotesListAdapter notesListAdapter = new NotesListAdapter((ArrayList<Note>) notesList);

        recyclerView.setAdapter(notesListAdapter);

        notesListAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int positionId) {
                showNoteDetail(notesRepository.getNote(positionId));
            }

        });


//        for (Note item : notesList
//        ) {
//            TextView note = new TextView(getContext());
//            note.setText(item.getTitle());
//            note.setTextSize(getActivity().getResources().getDimension(R.dimen.note_list_text_size));
//            listContainer.addView(note);
//            initPopupMenu(listContainer, note, item.getId());
//            note.setOnClickListener(v -> {
//                selectedNoteId = item.getId();
//                showNoteDetail(item);
//            });
//        }
    }

    private void initPopupMenu(ViewGroup listContainer, TextView noteListItem, int noteId) {
        noteListItem.setOnLongClickListener(view -> {
                    Activity act = getActivity();
                    PopupMenu popupMenu = new PopupMenu(act, noteListItem);
                    act.getMenuInflater().inflate(R.menu.list_fragment_popup, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.delete_popup_item:
                                    listContainer.removeView(noteListItem);
                                    NotesRepository repo = NotesRepoImpl.getInstance(getContext());
                                    repo.removeNote(noteId);
                                    Snackbar.make(getView(), "Заметка перемеща в корзину", 3000)
                                            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                                            .setBehavior(new BaseTransientBottomBar.Behavior())
                                            .setAction("ОТМЕНА", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    NotesRepository repo = NotesRepoImpl.getInstance(getContext());
                                                    repo.restoreNote(noteId);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("ACTION", "REFRESH");
                                                    getActivity().getSupportFragmentManager().setFragmentResult("NOTES_LIST_ACTION", bundle);
                                                }
                                            })
                                            .show();
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();

                    return true;
                }
        );
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