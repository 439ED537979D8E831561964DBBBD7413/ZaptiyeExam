package com.zaptiye.quiz.trialexams;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zaptiye.quiz.R;
import com.zaptiye.quiz.bean.TrialQuestions;

/**
 * Created by Mitesh on 18/03/17.
 */

public class QuestionFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "QuestionFragment";
    private View rootView;

    private TrialQuestions questions;
    private int current_question_no;

    private Button btnOptionA, btnOptionB, btnOptionC, btnOptionD, btnOptionE;
    private TextView tvQuestionNo, tvQuestionTitle;


    public static int attemptedQuesCount = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questions = (TrialQuestions) getArguments().getSerializable("current_question");
        current_question_no = getArguments().getInt("question_no");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_trial_exams_row, container, false);
        bindComponents(rootView);
        init();
        addListeners();

        return rootView;
    }

    private void init() {

        tvQuestionNo.setText(getString(R.string.label_question_no, current_question_no));
        tvQuestionTitle.setText(questions.getQuestion());

        btnOptionA.setText(questions.getOptionA());
        btnOptionB.setText(questions.getOptionB());
        btnOptionC.setText(questions.getOptionC());
        btnOptionD.setText(questions.getOptionD());
        btnOptionE.setText(questions.getOptionE());

        if (questions.getAttemptedQuestionID() != null) {

            if (questions.getAttemptedQuestionID().equals(questions.getQuestionID())) {

                if (questions.getAttemptedAnswer() == 1) {
                    enableDisableOptions(false, true, true, true, true);
                } else if (questions.getAttemptedAnswer() == 2)
                    enableDisableOptions(true, false, true, true, true);
                else if (questions.getAttemptedAnswer() == 3)
                    enableDisableOptions(true, true, false, true, true);
                else if (questions.getAttemptedAnswer() == 4)
                    enableDisableOptions(true, true, true, false, true);
                else if (questions.getAttemptedAnswer() == 5)
                    enableDisableOptions(true, true, true, true, false);
                else if (questions.getAttemptedAnswer() == -1)
                    enableDisableOptions(true, true, true, true, true);
            }

        }
    }

    // newInstance constructor for creating fragment with arguments
    public static QuestionFragment newInstance(int position, TrialQuestions questions) {

        QuestionFragment questFragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putSerializable("current_question", questions);
        args.putInt("question_no", position + 1);
        questFragment.setArguments(args);

        return questFragment;
    }

    private void addListeners() {

        btnOptionA.setOnClickListener(this);
        btnOptionB.setOnClickListener(this);
        btnOptionC.setOnClickListener(this);
        btnOptionD.setOnClickListener(this);
        btnOptionE.setOnClickListener(this);

    }

    private void bindComponents(View rootView) {


        tvQuestionNo = (TextView) rootView.findViewById(R.id.tvQuestionNo);
        tvQuestionTitle = (TextView) rootView.findViewById(R.id.tvQuestionTitle);

        btnOptionA = (Button) rootView.findViewById(R.id.btnOptionA);
        btnOptionB = (Button) rootView.findViewById(R.id.btnOptionB);
        btnOptionC = (Button) rootView.findViewById(R.id.btnOptionC);
        btnOptionD = (Button) rootView.findViewById(R.id.btnOptionD);
        btnOptionE = (Button) rootView.findViewById(R.id.btnOptionE);
    }

    @Override
    public void onClick(View v) {

        if (questions.getAttemptedQuestionID() == null)
            attemptedQuesCount = attemptedQuesCount + 1;


        if (v == btnOptionA) {

            enableDisableOptions(false, true, true, true, true);
            questions.setAttemptedQuestionID(questions.getQuestionID());
            questions.setAttemptedAnswer(1);


        }

        if (v == btnOptionB) {

            enableDisableOptions(true, false, true, true, true);
            questions.setAttemptedQuestionID(questions.getQuestionID());
            questions.setAttemptedAnswer(2);

        }

        if (v == btnOptionC) {

            enableDisableOptions(true, true, false, true, true);
            questions.setAttemptedQuestionID(questions.getQuestionID());
            questions.setAttemptedAnswer(3);


        }

        if (v == btnOptionD) {

            enableDisableOptions(true, true, true, false, true);

            questions.setAttemptedQuestionID(questions.getQuestionID());
            questions.setAttemptedAnswer(4);


        }

        if (v == btnOptionE) {

            enableDisableOptions(true, true, true, true, false);
            questions.setAttemptedQuestionID(questions.getQuestionID());
            questions.setAttemptedAnswer(5);

        }

    }

    private void enableDisableOptions(boolean optionA, boolean optionB, boolean optionC, boolean optionD, boolean optionE) {

        btnOptionA.setEnabled(optionA);
        btnOptionB.setEnabled(optionB);
        btnOptionC.setEnabled(optionC);
        btnOptionD.setEnabled(optionD);
        btnOptionE.setEnabled(optionE);
    }
}
