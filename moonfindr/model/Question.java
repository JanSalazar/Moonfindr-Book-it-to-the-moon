package com.tanion.aston.rovery.moonfindr.model;

/**
 * Created by Aston on 04/11/2015.
 */
public class Question {

    private int mQuestionResId;
    private int mAnswerResId;
    private boolean mAnswerTrue;

    public Question(int questionResId, int answerResId, boolean answerTrue) {
        mQuestionResId = questionResId;
        mAnswerResId = answerResId;
        mAnswerTrue = answerTrue;
    }

    public int getQuestionResId() {
        return mQuestionResId;
    }

    public void setQuestionResId(int questionResId) {
        mQuestionResId = questionResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public int getAnswerResId() {
        return mAnswerResId;
    }
}
