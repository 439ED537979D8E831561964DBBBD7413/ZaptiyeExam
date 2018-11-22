package com.zaptiye.quiz.trialexams;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zaptiye.quiz.MenuHomeScreenActivity;
import com.zaptiye.quiz.R;
import com.zaptiye.quiz.bean.TrialQuestions;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Mitesh on 21/03/17.
 */

public class ResumeExamActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ResumeExamActivity";
    private Button btnContinue;

    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_exam);

        bindComponents();
        init();
        addListeners();
    }

    private void init() {

        sp = getSharedPreferences(MenuHomeScreenActivity.PREFS_NAME, MODE_PRIVATE);
    }

    private void addListeners() {
        btnContinue.setOnClickListener(this);
    }

    private void bindComponents() {
        btnContinue = (Button) findViewById(R.id.btnContinueExam);
    }

    @Override
    public void onClick(View v) {

        if (v == btnContinue) {

                Gson gson = new Gson();

                Type type = new TypeToken<List<TrialQuestions>>(){}.getType();
                List<TrialQuestions> questionList = gson.fromJson(sp.getString("questions",null),type);

                Intent intent = new Intent(this, TrialExamActivity.class);
                intent.putExtra("question_list", (Serializable) questionList);
                intent.putExtra("exam_name",sp.getString("exam_name",null));
                intent.putExtra("total_seconds",sp.getInt("total_seconds",0));
                intent.putExtra("total_attempted_questions",sp.getInt("total_attempted_questions",0));

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                // Reset data to null
                sp.edit().putBoolean("pause_exam",false).apply();

        }
    }

    @Override
    public void onBackPressed() {
    }
}
