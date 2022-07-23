package ru.osa.gb.homework.notes.model;

import java.util.List;

public interface NotesRepository {

    // Получить заметку по id
    Note getNote(int id);

    // Получить список всех заметок, без удаленных
    List<Note> getNotes();


    int addNote(String title, String text);

    // Редактировать заметку
    void editNote(Note data);

    // Поместить заметку в корзину
    void removeNote(int noteIdToRemove);


    // TODO: для реализации корзины в будущем
    // Получить список всех удаленных заметок
    List<Note> getRemovedNotes();

    // Восстановить удаленную заметку
    void restoreNote(int noteIdToRestore);

}
