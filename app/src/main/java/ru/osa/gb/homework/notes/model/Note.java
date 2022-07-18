package ru.osa.gb.homework.notes.model;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Note {
    private int id;
    private String title;
    private String text;
    private LocalDateTime changeDate;
    private String deadline;
    private String author;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Note(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.changeDate = LocalDateTime.now();
        this.deadline = "tbd";
        this.author = "System";

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTitle(String title) {
        this.title = title;
        this.changeDate = LocalDateTime.now();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setText(String text) {
        this.text = text;
        this.changeDate = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
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
                ", createDate='" + changeDate + '\'' +
                ", deadline='" + deadline + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }
}

