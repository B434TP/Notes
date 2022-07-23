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
    private Boolean isRemoved;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Note(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.changeDate = LocalDateTime.now();
        this.deadline = "tbd";
        this.author = "System";
        this.isRemoved = false;

    }


    public void setTitle(String title) {
        this.title = title;
        this.changeDate = LocalDateTime.now();
    }


    public void setText(String text) {
        this.text = text;
        this.changeDate = LocalDateTime.now();
    }

    protected Boolean getRemoveStatus() {
        return isRemoved;
    }

    protected void setRemoveStatus(Boolean removed) {
        isRemoved = removed;
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
                ", changeDate=" + changeDate +
                ", deadline='" + deadline + '\'' +
                ", author='" + author + '\'' +
                ", isRemoved=" + isRemoved +
                '}';
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }
}

