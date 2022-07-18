package ru.osa.gb.homework.notes.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.osa.gb.homework.notes.R;

public class NotesRepoImpl implements NotesRepository {

    private static Context repoContext;
    private List<Note> notes;
    private int lastIndex = 0;
    private static NotesRepoImpl INSTANCE;

    private NotesRepoImpl(Context repoContext) {
        NotesRepoImpl.repoContext = repoContext;
        notes = new ArrayList<>();
        initRepoData();
    }

    public static NotesRepoImpl getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NotesRepoImpl(context);

        }
        return INSTANCE;
    }

    private void initRepoData() {
        // Здесь загрузка списка пометок из ресурсов
        String[] titlesRes = repoContext.getResources().getStringArray(R.array.titlesArray);
        String[] textsRes = repoContext.getResources().getStringArray(R.array.textArray);

        for (int i = 0; i < titlesRes.length; i++) {
            Note newNote = new Note(lastIndex, titlesRes[i], textsRes[i]);
            Log.d("MyRepo", "addingNote: " + newNote);
            notes.add(newNote);
            lastIndex++;
        }
    }

    @Override
    public int getLastId() {
        return lastIndex;
    }

    @Override
    public Note getNote(int id) {
        return notes.get(id);
    }

    @Override
    public List<Note> getAllNotes() {
        return notes;
    }

    @Override
    public int addNote(String title, String text) {
        int id = lastIndex + 1;
        Note newNote = new Note(lastIndex, title, text);
        notes.add(newNote);
        lastIndex = id;
        return id;
    }

    @Override
    public void editNote(Note noteToEdit) {
        notes.set(noteToEdit.getId(), noteToEdit);
    }



}
