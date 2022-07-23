package ru.osa.gb.homework.notes.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.osa.gb.homework.notes.R;
import ru.osa.gb.homework.notes.model.Note;
import ru.osa.gb.homework.notes.model.NotesRepoImpl;
import ru.osa.gb.homework.notes.model.NotesRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecycleBinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecycleBinFragment extends Fragment {

    private ViewGroup listContainer;
    private View notesListView;
    private NotesRepository notesRepository;

    public RecycleBinFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecycleBinFragment newInstance() {
        RecycleBinFragment fragment = new RecycleBinFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        notesListView = inflater.inflate(R.layout.fragment_recycle_bin, container, false);
        listContainer = notesListView.findViewById(R.id.NoteListContainer);
        fillNotesListView();

        return notesListView;
    }

    private void fillNotesListView() {
        notesRepository = NotesRepoImpl.getInstance(notesListView.getContext());
        List<Note> notesList = notesRepository.getRemovedNotes();
        listContainer.removeAllViews();
        for (Note item : notesList
        ) {
            TextView noteTitleView = new TextView(getContext());
            noteTitleView.setText(item.getTitle());
            noteTitleView.setTextSize(getActivity().getResources().getDimension(R.dimen.note_list_text_size));

            // TODO: Не нашел как установить weight для текстового поля, чтобы при длинном заголовке заментки кнопка посстановления не уезжала за экран
//            noteTitleView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(noteTitleView);

            ImageView restoreButton = new ImageView(getContext());
            restoreButton.setImageResource(R.drawable.ic_baseline_restore_from_trash_24);
            restoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listContainer.removeView(linearLayout);
                    NotesRepository repo = NotesRepoImpl.getInstance(getContext());
                    repo.restoreNote(item.getId());
                    Toast.makeText(getContext(), "Заметка восстановлена", Toast.LENGTH_SHORT).show();
                    Log.d("RecycleBin", "Restored: " + item);
                }
            });

            linearLayout.addView(restoreButton);

            listContainer.addView(linearLayout);

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("RecycleBin", "onCreateOptionsMenu: menu inflater");
        super.onCreateOptionsMenu(menu, inflater);

    }

    //    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        Log.d("RecycleBin", "onCreateOptionsMenu: menu inflater");
//        inflater.inflate(R.menu.detail_fragment_menu, menu);
//
//        MenuItem item = menu.findItem(R.id.add_item);
//        if (item != null) {
//            item.setVisible(false);
//        }
//
//        item = menu.findItem(R.id.about_item);
//        if (item != null) {
//            item.setVisible(false);
//        }
//    }

}