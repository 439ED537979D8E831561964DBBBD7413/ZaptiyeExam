package com.zaptiye.quiz.trialexams;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zaptiye.quiz.MenuHomeScreenActivity;
import com.zaptiye.quiz.R;
import com.zaptiye.quiz.bean.TrialQuestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitesh on 18/03/17.
 */

public class ExamResultActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ExamResultActivity";

    private List<TrialQuestions> questionsList, attemptedQuestionList;

    private ListView lvExamResult;
    private ExamResultAdapter mAdapter;

    private int total_un_attempted_ques = 0, total_attempted_ques = 0, total_wrong = 0, total_correct = 0;

    private Button btnExamResult, btnHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exam_result);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            questionsList = (List<TrialQuestions>) bundle.getSerializable("attempted_questions");
        }


        bindComponents();
        init();
        addListeners();
    }

    private void addListeners() {
        btnExamResult.setOnClickListener(this);
        btnHome.setOnClickListener(this);
    }


    private void init() {

        attemptedQuestionList = new ArrayList<>();

        for (int i = 0; i < questionsList.size(); i++) {

            if (questionsList.get(i).getAttemptedQuestionID() != null) {

                attemptedQuestionList.add(questionsList.get(i));

                if (questionsList.get(i).getAttemptedAnswer() == questionsList.get(i).getCorrectAnswer())
                    total_correct = total_correct + 1;
                else
                    total_wrong = total_wrong + 1;


            }


        }


        total_attempted_ques = attemptedQuestionList.size();
        total_un_attempted_ques = questionsList.size() - total_attempted_ques;

        mAdapter = new ExamResultAdapter();
        lvExamResult.setAdapter(mAdapter);
    }

    private void bindComponents() {

        lvExamResult = (ListView) findViewById(R.id.lvExamResult);
        btnExamResult = (Button) findViewById(R.id.btnStatistics);

        btnHome = (Button) findViewById(R.id.btnMenu);

    }

    @Override
    public void onClick(View v) {

        if (v == btnExamResult) {

            Intent intent = new Intent(this, ScoreCardActivity.class);

            intent.putExtra("total_wrong", total_wrong);
            intent.putExtra("total_correct", total_correct);
            intent.putExtra("total_attempted", total_attempted_ques);
            intent.putExtra("total_un-attempted", total_un_attempted_ques);


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if (v == btnHome) {


            Intent intent = new Intent(this, MenuHomeScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private class ExamResultAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return attemptedQuestionList.size();
        }

        @Override
        public Object getItem(int position) {
            return attemptedQuestionList.get(position).getQuestionID();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        class ViewHolder {

            private Button btnOptionA, btnOptionB, btnOptionC, btnOptionD, btnOptionE;
            private TextView tvResultInfo, tvQuestionNo, tvQuestion;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder holder;

            TrialQuestions questions = attemptedQuestionList.get(position);

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.activity_exam_result_row, parent, false);


                holder.tvQuestion = (TextView) convertView.findViewById(R.id.tvQuestionTitle);
                holder.tvQuestionNo = (TextView) convertView.findViewById(R.id.tvQuestionNo);

                holder.btnOptionA = (Button) convertView.findViewById(R.id.btnOptionA);
                holder.btnOptionB = (Button) convertView.findViewById(R.id.btnOptionB);
                holder.btnOptionC = (Button) convertView.findViewById(R.id.btnOptionC);
                holder.btnOptionD = (Button) convertView.findViewById(R.id.btnOptionD);
                holder.btnOptionE = (Button) convertView.findViewById(R.id.btnOptionE);

                holder.tvResultInfo = (TextView) convertView.findViewById(R.id.tvResultInfo);

                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            holder.tvQuestionNo.setText(getString(R.string.label_question_no, position + 1));
            holder.tvQuestion.setText(questions.getQuestion());

            holder.btnOptionA.setText(questions.getOptionA());
            holder.btnOptionB.setText(questions.getOptionB());
            holder.btnOptionC.setText(questions.getOptionC());
            holder.btnOptionD.setText(questions.getOptionD());
            holder.btnOptionE.setText(questions.getOptionE());

            if (questions.getQuestionID().equals(questions.getAttemptedQuestionID())) {

                // Set Correct Answer to Green Background
                if (questions.getAttemptedAnswer() == questions.getCorrectAnswer()) {

                    if (questions.getCorrectAnswer() == 1) {

                        holder.btnOptionA.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_blue);
                    }
                    else if (questions.getCorrectAnswer() == 2) {
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionA.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_blue);
                    }
                    else if (questions.getCorrectAnswer() == 3) {
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionA.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_blue);
                    }
                    else if (questions.getCorrectAnswer() == 4) {
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionA.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_blue);
                    }
                    else if (questions.getCorrectAnswer() == 5)
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionA.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_blue);

                    holder.tvResultInfo.setText(getString(R.string.correct_answer_txt));

                } else {

                    String correct_answer = null;

                    // Set Correct Answer to Green Background
                    if (questions.getCorrectAnswer() == 1) {

                        holder.btnOptionA.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_blue);
                        correct_answer = questions.getOptionA();

                    } else if (questions.getCorrectAnswer() == 2) {

                        holder.btnOptionB.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionA.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_blue);
                        correct_answer = questions.getOptionB();

                    } else if (questions.getCorrectAnswer() == 3) {

                        holder.btnOptionC.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionA.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_blue);
                        correct_answer = questions.getOptionC();


                    } else if (questions.getCorrectAnswer() == 4) {

                        holder.btnOptionA.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_green);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_blue);

                        correct_answer = questions.getOptionD();

                    } else if (questions.getCorrectAnswer() == 5) {

                        holder.btnOptionA.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionB.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionC.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionD.setBackgroundResource(R.drawable.button_blue);
                        holder.btnOptionE.setBackgroundResource(R.drawable.button_green);

                        correct_answer = questions.getOptionE();

                    }

                    // Set Attempted Answer to disabled
                    if (questions.getAttemptedAnswer() == 1)
                        enableDisableOptions(holder.btnOptionA, holder.btnOptionB, holder.btnOptionC, holder.btnOptionD, holder.btnOptionE, false, true, true, true, true);
                    else if (questions.getAttemptedAnswer() == 2)
                        enableDisableOptions(holder.btnOptionA, holder.btnOptionB, holder.btnOptionC, holder.btnOptionD, holder.btnOptionE, true, false, true, true, true);
                    else if (questions.getAttemptedAnswer() == 3)
                        enableDisableOptions(holder.btnOptionA, holder.btnOptionB, holder.btnOptionC, holder.btnOptionD, holder.btnOptionE, true, true, false, true, true);
                    else if (questions.getAttemptedAnswer() == 4)
                        enableDisableOptions(holder.btnOptionA, holder.btnOptionB, holder.btnOptionC, holder.btnOptionD, holder.btnOptionE, true, true, true, false, true);
                    else if (questions.getAttemptedAnswer() == 5)
                        enableDisableOptions(holder.btnOptionA, holder.btnOptionB, holder.btnOptionC, holder.btnOptionD, holder.btnOptionE, true, true, true, true, false);

                    holder.tvResultInfo.setText(getString(R.string.wrong_answer_txt, correct_answer));
                }


            }

            return convertView;
        }
    }


    private void enableDisableOptions(Button btnOptionA, Button btnOptionB, Button btnOptionC, Button btnOptionD, Button btnOptionE, boolean optionA, boolean optionB, boolean optionC, boolean optionD, boolean optionE) {

        btnOptionA.setEnabled(optionA);
        btnOptionB.setEnabled(optionB);
        btnOptionC.setEnabled(optionC);
        btnOptionD.setEnabled(optionD);
        btnOptionE.setEnabled(optionE);
    }

}
