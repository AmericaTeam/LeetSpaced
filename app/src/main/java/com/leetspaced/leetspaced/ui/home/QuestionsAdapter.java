package com.leetspaced.leetspaced.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.leetspaced.leetspaced.R;
import com.leetspaced.leetspaced.database.Question;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsAdapterViewHolder> {

    interface ListItemClickListener{
        void onListItemClick(int position);
    }
    final private ListItemClickListener mOnClickListener;
    private Question[] mQuestions;

    public QuestionsAdapter(ListItemClickListener onClickListener){
        this.mOnClickListener = onClickListener;
    }

    public class QuestionsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final Chip difficultyChip;
        public final TextView number;
        public final  TextView title;
//        public final Chip difficulty;
        public final TextView bucket;


        public QuestionsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.difficultyChip = itemView.findViewById(R.id.difficulty_chip);
            this.number = itemView.findViewById(R.id.question_number_text_view);
            this.title = itemView.findViewById(R.id.question_title_text_view);
            this.bucket = itemView.findViewById(R.id.question_bucket_text_view);
            itemView.setOnClickListener(this);
        }

        public void bind(Question question){
            // Difficulty color
            int color;
            switch (question.getDifficulty()){
                case "Easy":
                    color = R.color.colorLeetcodeEasy;
                    break;
                case "Medium":
                    color = R.color.colorLeetcodeMedium;
                    break;
                default:
                    color = R.color.colorLeetcodeHard;
                    break;
            }

            difficultyChip.setText(question.getDifficulty());
            difficultyChip.setChipBackgroundColorResource(color);
            difficultyChip.setClickable(false);
            title.setText(question.getTitle());
            number.setText(String.valueOf(question.getNumber()));
            bucket.setText(String.valueOf(question.getBucket()));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }
    @NonNull
    @Override
    public QuestionsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_list_item, parent, false);
        return new QuestionsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapterViewHolder holder, int position) {
        holder.bind(mQuestions[position]);
    }

    @Override
    public int getItemCount() {
        if (mQuestions == null){
            return 0;
        }
        else {
            return mQuestions.length;
        }
    }

    public void setmQuestions(Question[] mQuestions) {
        this.mQuestions = mQuestions;
        notifyDataSetChanged();
    }
}
