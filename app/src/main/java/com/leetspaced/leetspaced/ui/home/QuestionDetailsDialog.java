package com.leetspaced.leetspaced.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.chip.Chip;
import com.leetspaced.leetspaced.R;
import com.leetspaced.leetspaced.database.Question;
import com.leetspaced.leetspaced.utils.utils;

public class QuestionDetailsDialog extends DialogFragment {
    private int number;
    private String title;
    private String difficulty;
    private String link;
    private String personalNotes;
    private int bucket;

    public interface QuestionProvider {
        Question getClickedQuestion();
        void updateClickedQuestion(Question question);
    }

    QuestionProvider questionProvider;
    public static final String TAG = QuestionDetailsDialog.class.getSimpleName();

    public static QuestionDetailsDialog newInstance(){
        return new QuestionDetailsDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionProvider = (QuestionProvider) getParentFragment();
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog");
        Log.d(TAG, questionProvider.getClickedQuestion().getTitle());
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_question_details, null);

        TextView bucketTV = view.findViewById(R.id.question_bucket_text_view);
        Chip difficultyChip = view.findViewById(R.id.difficulty_chip);
        final EditText notesEt = view.findViewById(R.id.question_notes_edit_text);

        final Question question = questionProvider.getClickedQuestion();
        Log.d(TAG, String.valueOf(question.getReminderDate()));
        title = question.getTitle();
        link = question.getLink();
        difficulty = question.getDifficulty();
        personalNotes = question.getPersonalNotes();
        number = question.getNumber();
        bucket = question.getBucket();

        difficultyChip.setText(difficulty);
        difficultyChip.setChipBackgroundColorResource(utils.getDifficultyColor(difficulty));

        bucketTV.setText(utils.convertQuestionBucketNumberToText(bucket));
        notesEt.setText(personalNotes);

        builder.setView(view);
        builder.setTitle(getString(R.string.dialog_title, number, title));

        builder.setPositiveButton(R.string.question_details_dialog_positive_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Log.d(TAG, getString(R.string.bucket_one_duration_key));
                final long REMINDER_DURATION_ONE = Long.parseLong(preferences.getString(getString(R.string.bucket_one_duration_key), "4"));
                final long REMINDER_DURATION_TWO = Long.parseLong(preferences.getString(getString(R.string.bucket_two_duration_key), "7"));

                final long MILLISECONDS_IN_A_DAY = 24 * 60 * 60 * 1000;

                // Update reminder based on the bucket
                java.util.Date utilDate = new java.util.Date();
                switch (bucket){
                    case 0:
                        question.setReminderDate(new java.sql.Date(utilDate.getTime()
                                + MILLISECONDS_IN_A_DAY * REMINDER_DURATION_ONE));
                        break;
                    case 1:
                        question.setReminderDate(new java.sql.Date(utilDate.getTime()
                                + MILLISECONDS_IN_A_DAY * REMINDER_DURATION_TWO));
                        break;
                    case 3:
                        // Since bucket 3 means we have mastered the question
                        // we don't want it to move to 4
                        bucket--;
                    default:
                }

                // Update bucket
                question.setBucket(bucket + 1);
                question.setPersonalNotes(notesEt.getText().toString());

                // Save the updated question
                questionProvider.updateClickedQuestion(question);
            }
        });

        builder.setNegativeButton(R.string.question_details_dialog_negative_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                QuestionDetailsDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
