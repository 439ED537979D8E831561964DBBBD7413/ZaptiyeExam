package com.zaptiye.quiz.trialexams;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.zaptiye.quiz.R;

/**
 * Created by Mitesh on 22/03/17.
 */

public class ScoreCardActivity extends AppCompatActivity {

    private static final String TAG="ScoreCardActivity";

    private DonutProgress wrongProgress,correctProgress,attemptedProgress,unAttemptedProgress;
    private int total_correct=0,total_wrong=0,total_attempted_ques=0,total_un_attempted_ques=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_scorecard);


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){


            total_wrong = bundle.getInt("total_wrong");
            total_correct = bundle.getInt("total_correct");

            total_attempted_ques = bundle.getInt("total_attempted");
            total_un_attempted_ques = bundle.getInt("total_un-attempted");

        }

        bindComponents();
        init();

    }

    private void bindComponents() {

        wrongProgress = (DonutProgress)findViewById(R.id.wrongProgress);
        correctProgress =(DonutProgress)findViewById(R.id.correctProgress);

        attemptedProgress = (DonutProgress)findViewById(R.id.attemptedProgress);
        unAttemptedProgress = (DonutProgress)findViewById(R.id.unAttemptedProgress);

    }

    private void init() {


        wrongProgress.setMax(total_attempted_ques+total_un_attempted_ques);
        wrongProgress.setDonut_progress(String.valueOf(total_wrong));
        wrongProgress.setText(String.valueOf(total_wrong));

        correctProgress.setMax(total_attempted_ques+total_un_attempted_ques);
        correctProgress.setText(String.valueOf(total_correct));
        correctProgress.setText(String.valueOf(total_correct));


        unAttemptedProgress.setMax(total_attempted_ques+total_un_attempted_ques);
        unAttemptedProgress.setDonut_progress(String.valueOf(total_un_attempted_ques));
        unAttemptedProgress.setText(String.valueOf(total_un_attempted_ques));

        attemptedProgress.setMax(total_attempted_ques+total_un_attempted_ques);
        attemptedProgress.setDonut_progress(String.valueOf(total_attempted_ques));
        attemptedProgress.setText(String.valueOf(total_attempted_ques));


    }



}
