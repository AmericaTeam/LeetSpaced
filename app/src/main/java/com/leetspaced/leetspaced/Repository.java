package com.leetspaced.leetspaced;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leetspaced.leetspaced.database.AppDatabase;
import com.leetspaced.leetspaced.database.Question;
import com.leetspaced.leetspaced.database.QuestionDao;

public class Repository {

    private QuestionDao mQuestionDao;
    private Thread mThread;

    Repository(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application.getApplicationContext());
        mQuestionDao = appDatabase.questionDao();
    }

    public LiveData<Question[]> getAllQuestions() {
        return mQuestionDao.getAllQuestions();
    }

    public void updateQuestion(final Question question){
        Runnable updateQuestionRunnable = new Runnable() {
            @Override
            public void run() {
                mQuestionDao.updateQuestion(question);
            }
        };

        // Stop the thread if its initialized
        // If the thread is not working interrupt will do nothing
        // If its working it stops the previous work and starts the
        // new runnable
        if (mThread != null){
            mThread.interrupt();
        }
        mThread = new Thread(updateQuestionRunnable);
        mThread.start();
    }

    public LiveData<Question[]> getTodaysQuestions(long today) {
        return mQuestionDao.getTodaysQuestions(today);
    }

    public LiveData<Integer> getUnsolvedQuestionsCount(){
        return mQuestionDao.countUnsolvedQuestions();
    }

    public LiveData<Integer> getSolvedQuestionsCount(){
        return mQuestionDao.countSolvedQuestions();
    }

    public LiveData<Integer> getConfidentQuestionsCount(){
        return mQuestionDao.countConfidentQuestions();
    }

    public LiveData<Integer> getMasteredQuestionsCount(){
        return mQuestionDao.countMasteredQuestions();
    }
}
