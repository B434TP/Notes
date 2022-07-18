package ru.osa.gb.homework.notes.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String strMonth = String.valueOf(month);
        String strDay = String.valueOf(day);
        if (month < 10) {strMonth = "0" + strMonth;}
        if (day < 10) {strDay = "0" + strDay;}

        String str = year  + "-" + strMonth  + "-" + strDay + " 00:00";
        LocalDateTime selectedDate = LocalDateTime.parse(str, formatter);

        NotesRepository nr = NotesRepoImpl.getInstance(getContext());
        Note noteToEdit = nr.getNote(id);
        noteToEdit.setChangeDate(selectedDate);
        nr.editNote(noteToEdit);
    }
}
