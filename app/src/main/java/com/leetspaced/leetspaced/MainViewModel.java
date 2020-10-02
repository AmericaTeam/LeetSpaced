package com.leetspaced.leetspaced;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.leetspaced.leetspaced.database.AppDatabase;
import com.leetspaced.leetspaced.database.Question;

public class MainViewModel extends AndroidViewModel {
    Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<Question[]> getAllQuestions() {
        return repository.getAllQuestions();
    }

    public LiveData<Question[]> getTodaysQuestions(long today) {
        return repository.getTodaysQuestions(today);
    }

    public void updateQuestion(Question question) {
        repository.updateQuestion(question);
    }

    public LiveData<Integer> getUnsolvedQuestionsCount(){
        return repository.getUnsolvedQuestionsCount();
    }

    public LiveData<Integer> getSolvedQuestionsCount(){
        return repository.getSolvedQuestionsCount();
    }

    public LiveData<Integer> getConfidentQuestionsCount(){
        return repository.getConfidentQuestionsCount();
    }

    public LiveData<Integer> getMasteredQuestionsCount(){
        return repository.getMasteredQuestionsCount();
    }
}
