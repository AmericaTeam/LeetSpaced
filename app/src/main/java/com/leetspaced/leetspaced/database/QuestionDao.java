package com.leetspaced.leetspaced.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface QuestionDao {
//    @Query("SELECT *, rowid FROM questions")
    @Query("SELECT * FROM questions")
    public LiveData<Question[]> getAllQuestions();

//    @Query("SELECT *, rowid FROM questions WHERE reminder_date < date('now', '+1 day') AND bucket < 3")
    @Query("SELECT * FROM questions WHERE reminder_date < :today AND bucket < 3")
    public LiveData<Question[]> getTodaysQuestions(long today);

    @Update
    public void updateQuestion(Question question);
}