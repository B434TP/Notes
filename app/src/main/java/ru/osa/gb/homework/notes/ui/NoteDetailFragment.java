package ru.osa.gb.homework.notes.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
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

        if (savedInstanceState == null) setHasOptionsMenu(true);

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

        ViewGroup dateDiv = new LinearLayout(context);
        ((LinearLayout) dateDiv).setOrientation(LinearLayout.HORIZONTAL);
        dateDiv.addView(noteDate);


        container.addView(dateDiv);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("NoteDetailFragment", "onCreateOptionsMenu: menu inflater");
        inflater.inflate(R.menu.detail_fragment_menu, menu);

        MenuItem item = menu.findItem(R.id.add_item);
        if (item != null) {
            item.setVisible(false);
        }

        item = menu.findItem(R.id.about_item);
        if (item != null) {
            item.setVisible(false);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("MNU", "onOptionsItemSelected: " + item);

        switch (item.getItemId()) {
            case R.id.edit_date_item:
                DialogFragment newFragment = new SelectDateFragment(noteId);
                newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
                break;
            case R.id.delete_item:
                NotesRepository repo = NotesRepoImpl.getInstance(getContext());
                repo.removeNote(noteId);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(this);
                ft.commit();

                MainActivity ma = (MainActivity) getActivity();
                ma.selectFragment(AllFragments.LIST);

                Snackbar.make(getView(),"Заметка перемеща в корзину",3000)
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


        return super.onOptionsItemSelected(item);
    }
}