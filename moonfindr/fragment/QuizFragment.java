package com.tanion.aston.rovery.moonfindr.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tanion.aston.rovery.moonfindr.R;
import com.tanion.aston.rovery.moonfindr.model.Question;

/**
 * Created by Aston Tanion on 27/04/2016.
 */
public class QuizFragment extends Fragment {
    public static final String TAG = "QuizFragment";

    private EditText mQuestionEditText;
    private EditText mAnswerEditText;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_1, R.string.answer_1, false),
            new Question(R.string.question_2, R.string.answer_2, true),
            new Question(R.string.question_3, R.string.answer_3, false),
            new Question(R.string.question_4, R.string.answer_4, false),
            new Question(R.string.question_5, R.string.answer_5, true)
    };

    private int mCurrentQuestion = 0;
    private Resources mResources;
    private Button mPreviewButton;
    private Button mNextButton;

    public static QuizFragment newInstance() {
        Bundle args = new Bundle();

        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResources = getResources();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        mQuestionEditText = (EditText) view.findViewById(R.id.fragment_quiz_question);

        mAnswerEditText = (EditText) view.findViewById(R.id.fragment_quiz_answer);
        mAnswerEditText.setVisibility(View.GONE);

        Button falseButton = (Button) view.findViewById(R.id.fragment_quiz_false_button);
        falseButton.setOnClickListener(new ButtonOnClickListener());

        Button trueButton = (Button) view.findViewById(R.id.fragment_quiz_true_button);
        trueButton.setOnClickListener(new ButtonOnClickListener());

        mPreviewButton = (Button) view.findViewById(R.id.fragment_quiz_previous_button);
        mPreviewButton.setVisibility(View.GONE);
        mPreviewButton.setOnClickListener(new ButtonOnClickListener());

        mNextButton = (Button) view.findViewById(R.id.fragment_quiz_next_button);
        mNextButton.setVisibility(View.GONE);
        mNextButton.setOnClickListener(new ButtonOnClickListener());

        updateUI();

        return view;
    }

    private void updateUI() {
        String question = String.format(
                mResources.getString(R.string.question),
                mCurrentQuestion + 1,
                mResources.getString(mQuestionBank[mCurrentQuestion].getQuestionResId()));

        String answer = String.format(
                mResources.getString(R.string.answer),
                mCurrentQuestion + 1,
                mResources.getString(mQuestionBank[mCurrentQuestion].getAnswerResId()));

        mQuestionEditText.setText(question);
        mAnswerEditText.setText(answer);
        mAnswerEditText.setVisibility(View.GONE);
        mPreviewButton.setVisibility(View.GONE);
        mNextButton.setVisibility(View.GONE);
    }

    private class ButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_quiz_false_button:
                    checkAnswer(false);
                    mAnswerEditText.setVisibility(View.VISIBLE);
                    mPreviewButton.setVisibility(View.VISIBLE);
                    mNextButton.setVisibility(View.VISIBLE);
                    break;
                case R.id.fragment_quiz_true_button:
                    checkAnswer(true);
                    mAnswerEditText.setVisibility(View.VISIBLE);
                    mPreviewButton.setVisibility(View.VISIBLE);
                    mNextButton.setVisibility(View.VISIBLE);
                    break;
                case R.id.fragment_quiz_previous_button:
                    if (mCurrentQuestion == 0) mCurrentQuestion = mQuestionBank.length - 1;
                    else mCurrentQuestion = (mCurrentQuestion - 1) % mQuestionBank.length;
                    updateUI();
                    break;
                case R.id.fragment_quiz_next_button:
                    mCurrentQuestion = (mCurrentQuestion + 1) % mQuestionBank.length;
                    updateUI();
                    break;
                default:
                    Log.e(TAG, "This button doesn't have and onclick listener attach to it");
                    break;
            }
        }
    }

    private void checkAnswer(boolean isItTrue) {

        boolean isAnswerTrue = mQuestionBank[mCurrentQuestion].isAnswerTrue();

        String response;

        if (isAnswerTrue == isItTrue) {
            response = mResources.getString(R.string.correct_answer);
        } else {
            response = mResources.getString(R.string.incorrect_answer);
        }

        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
    }
}
