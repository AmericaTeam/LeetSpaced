package com.leetspaced.leetspaced;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leetspaced.leetspaced.database.AppDatabase;
import com.leetspaced.leetspaced.database.Question;

public class MainViewModel extends AndroidViewModel {
    Repository repository;

    LiveData<Question[]> allQuestions;
    LiveData<Question[]> solvedQuestions;
    LiveData<Question[]> confidentQuestions;
    LiveData<Question[]> masteredQuestions;

    LiveData<Integer> unsolvedQuestionsCount;
    LiveData<Integer> solvedQuestionsCount;
    LiveData<Integer> confidentQuestionsCount;
    LiveData<Integer> masteredQuestionsCount;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);

        allQuestions = repository.getAllQuestions();
        solvedQuestions = repository.getSolvedQuestions();
        confidentQuestions = repository.getConfidentQuestions();
        masteredQuestions = repository.getMasteredQuestions();

        unsolvedQuestionsCount = repository.getUnsolvedQuestionsCount();
        solvedQuestionsCount = repository.getSolvedQuestionsCount();
        confidentQuestionsCount = repository.getConfidentQuestionsCount();
        masteredQuestionsCount = repository.getMasteredQuestionsCount();
    }

    public LiveData<Question[]> getAllQuestions() {
        return allQuestions;
    }

    public LiveData<Question[]> getTodaysQuestions() {
        return repository.getTodaysQuestions();
    }

    public void updateQuestion(Question question) {
        repository.updateQuestion(question);
    }

    public LiveData<Integer> getUnsolvedQuestionsCount(){
        return unsolvedQuestionsCount;
    }

    public LiveData<Integer> getSolvedQuestionsCount(){
        return solvedQuestionsCount;
    }

    public LiveData<Integer> getConfidentQuestionsCount(){
        return confidentQuestionsCount;
    }

    public LiveData<Integer> getMasteredQuestionsCount(){
        return masteredQuestionsCount;
    }

    public LiveData<Question[]> getSolvedQuestions() {
        return solvedQuestions;
    }

    public LiveData<Question[]> getConfidentQuestions() {
        return confidentQuestions;
    }

    public LiveData<Question[]> getMasteredQuestions() {
        return masteredQuestions;
    }
}
