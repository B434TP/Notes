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
    public Note getNote(int id) {
        return notes.get(id);
    }

    @Override
    public List<Note> getRemovedNotes() {
        List<Note> removedList = new ArrayList<>();

        for (Note item : notes
        ) {
            if (item.getRemoveStatus()) {
                removedList.add(item);
            }
        }
        return removedList;
    }

    @Override
    public void restoreNote(int noteIdToRestore) {
        Note note = getNote(noteIdToRestore);
        note.setRemoveStatus(false);
        editNote(note);
    }

    @Override
    public List<Note> getNotes() {
        List<Note> clearList = new ArrayList<>();

        for (Note item : notes
        ) {
            if (!item.getRemoveStatus()) {
                clearList.add(item);
            }
        }
        return clearList;
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


    public void removeNote(int noteIdToRemove) {
        Note note = getNote(noteIdToRemove);
        note.setRemoveStatus(true);
        editNote(note);
    }


}
