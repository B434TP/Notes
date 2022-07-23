package ru.osa.gb.homework.notes.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.osa.gb.homework.notes.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartPageFragment extends Fragment {

    public StartPageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StartPageFragment newInstance() {
        StartPageFragment fragment = new StartPageFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup frView = (ViewGroup) inflater.inflate(R.layout.fragment_start_page, container, false);

        Button startBnt = frView.findViewById(R.id.startListBtn);
        startBnt.setOnClickListener(v -> {
            MainActivity ma = (MainActivity) getActivity();
            ma.selectFragment(AllFragments.LIST);
        });

        Button aboutBnt = frView.findViewById(R.id.startAboutBtn);
        aboutBnt.setOnClickListener(v -> {
            MainActivity ma = (MainActivity) getActivity();
            ma.selectFragment(AllFragments.ABOUT);
        });

        return frView;
    }
}