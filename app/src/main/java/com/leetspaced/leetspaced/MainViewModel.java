package com.leetspaced.leetspaced;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.leetspaced.leetspaced.database.AppDatabase;
import com.leetspaced.leetspaced.database.Question;

public class MainViewModel extends AndroidViewModel {
    LiveData<Question[]> allQuestions;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(application.getApplicationContext());
        allQuestions = appDatabase.questionDao().getAllQuestions();
    }

    public LiveData<Question[]> getAllQuestions() {
        return allQuestions;
    }
}
