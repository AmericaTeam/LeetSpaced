package com.leetspaced.leetspaced;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.leetspaced.leetspaced.database.AppDatabase;
import com.leetspaced.leetspaced.database.Question;

public class MainViewModel extends AndroidViewModel {
    AppDatabase appDatabase;
    LiveData<Question[]> allQuestions;
    Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        appDatabase = AppDatabase.getInstance(application.getApplicationContext());
        allQuestions = appDatabase.questionDao().getAllQuestions();
    }

    public LiveData<Question[]> getAllQuestions() {
        return allQuestions;
    }

    public LiveData<Question[]> getTodaysQuestions(long today) {
        return appDatabase.questionDao().getTodaysQuestions(today);
    }

    public void updateQuestion(Question question) {
        repository.updateQuestion(question);
    }

    public int getUnsolvedQuestionsCount(){
        return appDatabase.questionDao().countUnsolvedQuestions();
    }

    public int getSolvedQuestionsCount(){
        return appDatabase.questionDao().countSolvedQuestions();
    }

    public int getConfidentQuestionsCount(){
        return appDatabase.questionDao().countConfidentQuestions();
    }

    public int getMasteredQuestionsCount(){
        return appDatabase.questionDao().countMasteredQuestions();
    }
}
