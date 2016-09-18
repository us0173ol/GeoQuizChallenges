package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
//buttons created
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;
//call for questions to be displayed
    private Question [] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
//variable for the current index of question
    private int mCurrentIndex = 0;
    //boolean variable holds the value that CheatActivity is passing back
    private boolean mIsCheater;
//method for moving from question to question
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
//method for checking if the question is answered correctly
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId;
        //if the user cheats, display the judgement toast
        if (mIsCheater){
            messageResId = R.string.judgment_toast;
        }else {
        //if they did not cheat, go on as normal
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }//Toast for displaying if answers are answered correctly, to user
        }//or to call them a cheater
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
//called when activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);


//what to do when the text view is clicked and to show it
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
//        int question = mQuestionBank[mCurrentIndex].getTextResId();
//        mQuestionTextView.setText(question);
//display true button and what to do when clicked
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(QuizActivity.this,
//                                R.string.incorrect_toast,
//                                Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });
        //display false button and what to do when clicked
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                checkAnswer(false);
            }
        });
        //display next button and what to do when clicked
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
//                int question = mQuestionBank[mCurrentIndex].getTextResId();
//                mQuestionTextView.setText(question);
                updateQuestion();
            }
        });

        //display previous button and what to do when clicked
        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
//                if(mCurrentIndex == 0){
//                    mCurrentIndex = mQuestionBank.length;
//                }
                updateQuestion();
            }

        });
//cheat button and what to do when clicked
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inform CheatActivity of the answer to the current question
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                //Tell activity manager to Start CheatActivity with an extra
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
//                startActivity(i);
                //tell on the cheater
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();


    if (savedInstanceState != null){
        mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }
        updateQuestion();
    }
    @Override
    //override onActivityResult to retrieve value held by boolean mIsCheater
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }//tags to be logged to see when each is called
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}