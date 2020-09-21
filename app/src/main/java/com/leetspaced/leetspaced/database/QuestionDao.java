package com.leetspaced.leetspaced.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface QuestionDao {
    @Query("SELECT *, rowid FROM questions")
    public LiveData<Question[]> getAllQuestions();
}