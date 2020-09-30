package com.leetspaced.leetspaced.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Fts4
@Entity(tableName = "Questions")
public class Question {
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    private int rowId;

    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "difficulty")
    private String difficulty;

    @ColumnInfo(name = "link")
    private String link;

    @ColumnInfo(name = "personal_notes")
    private String personalNotes;

    @ColumnInfo(name = "bucket")
    private int bucket;

    @ColumnInfo(name = "reminder_date")
    private long reminderDate;

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPersonalNotes() {
        return personalNotes;
    }

    public void setPersonalNotes(String personalNotes) {
        this.personalNotes = personalNotes;
    }

    public int getBucket() {
        return bucket;
    }

    public void setBucket(int bucket) {
        this.bucket = bucket;
    }

    public long getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(long reminderDate) {
        this.reminderDate = reminderDate;
    }
}
