package ru.osa.gb.homework.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;

import ru.osa.gb.homework.notes.R;

public class NoteDetailActivity extends AppCompatActivity {
    int noteId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return;
        }

        if (getIntent().hasExtra(NotesListFragment.BUNDLE_NOTE_ID)) {
            noteId = getIntent().getExtras().getInt(NotesListFragment.BUNDLE_NOTE_ID);
        }

        if (savedInstanceState == null) {
            NoteDetailFragment noteDetailFragment = NoteDetailFragment.newInstance(noteId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.noteDetailContainer, noteDetailFragment)
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outPersistentState.putInt("dd",7);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}