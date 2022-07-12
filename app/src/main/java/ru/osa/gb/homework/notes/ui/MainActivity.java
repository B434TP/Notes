package ru.osa.gb.homework.notes.ui;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import ru.osa.gb.homework.notes.R;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            NotesListFragment notesListFragment = NotesListFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notesListContainer, notesListFragment)
                    .commit();
        }


    }
}