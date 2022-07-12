package ru.osa.gb.homework.notes.model;


import java.util.Calendar;

public class Note {
    private int id;
    private String title;
    private String text;
    // TODO: изменить тип данных на LocalDateTime
    private String createDate;
    private String deadline;
    private String author;

    public Note(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        Calendar calendar = Calendar.getInstance();
        this.createDate = String.valueOf(calendar.getTime());
        this.deadline = "tbd";
        this.author = "system";

    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", createDate='" + createDate + '\'' +
                ", deadline='" + deadline + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

