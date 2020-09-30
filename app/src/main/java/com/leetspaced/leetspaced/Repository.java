package com.leetspaced.leetspaced;

import android.app.Application;

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
}
