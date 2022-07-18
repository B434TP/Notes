package ru.osa.gb.homework.notes.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import ru.osa.gb.homework.notes.R;


public class MainActivity extends AppCompatActivity {

    public AllFragments currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            currentFragment = AllFragments.START;
        } else {
            currentFragment = (AllFragments) savedInstanceState.getSerializable("currentFragment");
        }




        selectFragment();

    }

    public void selectFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (currentFragment) {
            case ABOUT:
                AboutFragment aboutFragment = AboutFragment.newInstance();
                ft.replace(R.id.mainFragmentContainer, aboutFragment);
                ft.addToBackStack("aboutFragment");
                ft.commit();
                break;
            case LIST:
                NotesListFragment notesListFragment = NotesListFragment.getInstance();
                ft.replace(R.id.mainFragmentContainer, notesListFragment);
                ft.addToBackStack("notesListFragment");
                ft.commit();
                break;
            case START:
                StartPageFragment startPageFragment = StartPageFragment.newInstance();
                ft.replace(R.id.mainFragmentContainer, startPageFragment);
                ft.commit();
                break;
            case DETAIL:
////                // TODO: придумать как передавать id и надо ли вообще
////                NoteDetailFragment noteDetailFragment = NoteDetailFragment.getInstance(0);
////                ft.replace(R.id.mainFragmentContainer, noteDetailFragment);
////                ft.addToBackStack("noteDetailFragment");
////                ft.commit();
                break;
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("currentFragment", currentFragment);
        super.onSaveInstanceState(outState);
    }
}