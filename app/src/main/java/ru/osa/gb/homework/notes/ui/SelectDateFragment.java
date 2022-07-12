package ru.osa.gb.homework.notes.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import ru.osa.gb.homework.notes.model.Note;
import ru.osa.gb.homework.notes.model.NotesRepoImpl;
import ru.osa.gb.homework.notes.model.NotesRepository;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int id;

    public SelectDateFragment(int noteId) {
        this.id = noteId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm + 1, dd);
    }

    public void populateSetDate(int year, int month, int day) {
        String selectedDate = month + "/" + day + "/" + year;
        NotesRepository nr = NotesRepoImpl.getInstance(getContext());
        Note noteToEdit = nr.getNote(id);
        noteToEdit.setCreateDate(selectedDate);
        nr.editNote(noteToEdit);
    }
}
