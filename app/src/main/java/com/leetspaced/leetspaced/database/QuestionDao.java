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

    @Query("SELECT COUNT(number) FROM questions WHERE reminder_date < :today AND bucket < 3")
    public Integer getTodaysQuestionsCount(long today);

    // Get solved questions
    @Query("SELECT * FROM questions WHERE bucket = 1")
    public LiveData<Question[]> getSolvedQuestions();

    // Get confident questions
    @Query("SELECT * FROM questions WHERE bucket = 2")
    public LiveData<Question[]> getConfidentQuestions();

    // Get mastered questions
    @Query("SELECT * FROM questions WHERE bucket = 3")
    public LiveData<Question[]> getMasteredQuestions();

    @Update
    public void updateQuestion(Question question);

    @Query("SELECT COUNT(number) FROM questions WHERE bucket = 0")
    public LiveData<Integer> countUnsolvedQuestions();

    @Query("SELECT COUNT(number) FROM questions WHERE bucket = 1")
    public LiveData<Integer> countSolvedQuestions();

    @Query("SELECT COUNT(number) FROM questions WHERE bucket = 2")
    public LiveData<Integer> countConfidentQuestions();

    @Query("SELECT COUNT(number) FROM questions WHERE bucket = 3")
    public LiveData<Integer> countMasteredQuestions();
}