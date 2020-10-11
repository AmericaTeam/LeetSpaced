package com.leetspaced.leetspaced.utils;

import com.leetspaced.leetspaced.R;

public class utils {
    public static String convertQuestionBucketNumberToText(int bucketNumber) {
        switch (bucketNumber){
            case 1:
                return "Solved";
            case 2:
                return "Confident";
            case 3:
                return "Mastered";
            default:
                return "Unsolved";
        }
    }

    public static int getDifficultyColor (String difficulty) {
        switch (difficulty){
            case "Easy":
                return R.color.colorLeetcodeEasy;
            case "Medium":
                return R.color.colorLeetcodeMedium;
            default:
                return R.color.colorLeetcodeHard;
        }
    }
}
