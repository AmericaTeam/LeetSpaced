package com.leetspaced.leetspaced.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface QuestionDao {
    @Query("SELECT *, rowid FROM questions")
    public LiveData<Question[]> getAllQuestions();

    @Update
    public void updateQuestion(Question question);
}