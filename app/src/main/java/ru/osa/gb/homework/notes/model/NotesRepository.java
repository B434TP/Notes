package ru.osa.gb.homework.notes.model;

import java.util.List;

public interface NotesRepository {

    int getLastId();

    Note getNote(int id);

    List<Note> getAllNotes();

    int addNote(String title, String text);

    void editNote(Note data);

}
