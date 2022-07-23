package ru.osa.gb.homework.notes.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ru.osa.gb.homework.notes.R;


public class MainActivity extends AppCompatActivity {

    public AllFragments currentFragment;

    private final String EXIT_CHOICE = "EXIT_CHOICE";
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        initDrawer();
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            currentFragment = AllFragments.LIST;
        } else {
            currentFragment = (AllFragments) savedInstanceState.getSerializable("currentFragment");
        }

        selectFragment(currentFragment);


    }

    private void initDrawer() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // TODO: как-то заставить появитсья и заработать бургер
//        getSupportActionBar().setIcon(R.drawable.ic_baseline_menu_24);


        // Находим DrawerLayout

        drawer = findViewById(R.id.drawerLayout);
        // Создаем ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new
                                                                 NavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                                         int id = item.getItemId();
                                                                         switch (id) {
                                                                             case R.id.about_drawer_item:
                                                                                 drawer.close();
                                                                                 selectFragment(AllFragments.ABOUT);
                                                                                 return true;
                                                                             case R.id.notes_list_item:
                                                                                 drawer.close();
                                                                                 selectFragment(AllFragments.LIST);
                                                                                 return true;
                                                                             case R.id.recycle_bin_drawer_item:
                                                                                 drawer.close();
                                                                                 selectFragment(AllFragments.BIN);
                                                                                 return true;
                                                                             case R.id.exit_drawer_item:
                                                                                 askForAppFinish();
                                                                                 return true;
                                                                         }
                                                                         return false;
                                                                     }
                                                                 });
    }

    private void askForAppFinish() {
        new AlertDialog.Builder(this)
                .setTitle("Внимание!")
                .setMessage("Вы действительно хотите выйти?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishApp();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Спасибо, что передумал!)", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

    private void finishApp() {
        Toast.makeText(this, "Хорошего дня! :)", Toast.LENGTH_SHORT).show();
        finish();
    }


    public void selectFragment(AllFragments newCurrentFragment) {

        currentFragment = newCurrentFragment;

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
//                ft.addToBackStack("notesListFragment");
                ft.commit();
                break;
            case START:
                StartPageFragment startPageFragment = StartPageFragment.newInstance();
                ft.replace(R.id.mainFragmentContainer, startPageFragment);
                ft.commit();
                break;
            case BIN:
                RecycleBinFragment recycleBinFragment = RecycleBinFragment.newInstance();
                ft.replace(R.id.mainFragmentContainer, recycleBinFragment);
                ft.addToBackStack("recycleBinFragment");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("MNU", "onOptionsItemSelected: " + item);

        switch (item.getItemId()) {
            case R.id.add_item:
//                TODO: добавить добавление
                Toast.makeText(this, "Добавить", Toast.LENGTH_SHORT).show();
                break;

            case R.id.about_item:
                selectFragment(AllFragments.ABOUT);
                break;

            case R.id.exit_item:
                askForAppFinish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}