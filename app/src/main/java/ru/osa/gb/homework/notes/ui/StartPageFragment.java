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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
            ma.currentFragment = AllFragments.LIST;
            ma.selectFragment();
        });

        Button aboutBnt = frView.findViewById(R.id.startAboutBtn);
        aboutBnt.setOnClickListener(v -> {
            MainActivity ma = (MainActivity) getActivity();
            ma.currentFragment = AllFragments.ABOUT;
            ma.selectFragment();
        });

        return frView;
    }
}