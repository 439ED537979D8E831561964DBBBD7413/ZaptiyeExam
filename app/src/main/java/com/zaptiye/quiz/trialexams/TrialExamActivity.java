package com.zaptiye.quiz.trialexams;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zaptiye.quiz.MenuHomeScreenActivity;
import com.zaptiye.quiz.R;
import com.zaptiye.quiz.bean.TrialQuestions;
import com.zaptiye.quiz.http.HttpCallback;
import com.zaptiye.quiz.http.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mitesh on 17/03/17.
 */

public class TrialExamActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "TrialExamActivity";
    private List<TrialQuestions> questionList;

    private ViewPager questionPager;
    private QuestionPagerAdapter mAdapter;
    private String questionID, examName;

    private int total_seconds = 6000;
    private TextView tvTimer;
    private Button btnEndExam, btnReport;
    // private ImageButton ibPrevious,ibNext;
    private ImageView ivPause;
    private Handler handler = new Handler();

    private SharedPreferences sp;
    private boolean examEnded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_trial_exams);

        handler.postDelayed(timerRunnable, 1000);

        bindComponents();
        init();
        addListeners();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            if (bundle.containsKey("total_seconds"))
                total_seconds = bundle.getInt("total_seconds");

            if (bundle.containsKey("total_attempted_questions"))
                QuestionFragment.attemptedQuesCount = bundle.getInt("total_attempted_questions");

            examName = bundle.getString("exam_name");
            getSupportActionBar().setTitle(examName);
            getSupportActionBar().setSubtitle(getString(R.string.label_question_no, 1));

            questionList.addAll((Collection<? extends TrialQuestions>) bundle.getSerializable("question_list"));
            mAdapter.notifyDataSetChanged();
        }


    }

    private void init() {


        //ibPrevious.setVisibility(View.GONE);

        sp = getSharedPreferences(MenuHomeScreenActivity.PREFS_NAME, 0);

        questionList = new ArrayList<>();
        mAdapter = new QuestionPagerAdapter(getSupportFragmentManager(), questionList);
        questionPager.setAdapter(mAdapter);

    }

    private void addListeners() {

        btnEndExam.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        questionPager.addOnPageChangeListener(this);
        ivPause.setOnClickListener(this);

//        ibNext.setOnClickListener(this);
//        ibPrevious.setOnClickListener(this);
    }

    private void bindComponents() {

        questionPager = (ViewPager) findViewById(R.id.vPager);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        btnEndExam = (Button) findViewById(R.id.btnEndExam);
        btnReport = (Button) findViewById(R.id.btnReport);
        ivPause = (ImageView) findViewById(R.id.ivPause);

//        ibPrevious = (ImageButton)findViewById(R.id.ibPrevious);
//        ibNext = (ImageButton)findViewById(R.id.ibNext);

    }

    @Override
    protected void onPause() {
        super.onPause();


//        if(handler!=null)
//            handler.removeCallbacksAndMessages(null);
//


        if (!examEnded)
            saveExam();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sp.getBoolean("pause_exam", false)) {


            Intent intent = new Intent(this, ResumeExamActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else
            Toast.makeText(this, getResources().getString(R.string.swipe_right), Toast.LENGTH_LONG).show();

    }

    private void saveExam() {

        Gson gson = new Gson();
        sp.edit().putInt("total_seconds", total_seconds)
                .putInt("total_attempted_questions", QuestionFragment.attemptedQuesCount)
                .putString("exam_name", examName)
                .putString("questions", gson.toJson(questionList))
                .putBoolean("pause_exam", true)
                .apply();

    }

    @Override
    public void onClick(View v) {

        if (v == btnEndExam) {

            if (QuestionFragment.attemptedQuesCount > 0) {

                examEnded = true;
                finish();

                Intent intent = new Intent(this, ExamResultActivity.class);
                intent.putExtra("attempted_questions", (Serializable) questionList);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } else
                Toast.makeText(this, getResources().getString(R.string.solve_more), Toast.LENGTH_LONG).show();

        }

        if (v == btnReport) {
            showReportPopup(questionPager.getCurrentItem());
        }

        if (v == ivPause) {

            saveExam();

            Intent intent = new Intent(this, ResumeExamActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

//        if(v == ibNext){
//
//            questionPager.setCurrentItem(questionPager.getCurrentItem()+1);
//        }
//
//        if(v == ibPrevious){
//
//            questionPager.setCurrentItem(questionPager.getCurrentItem()-1);
//        }
//
    }


    private void showReportPopup(final int position) {

        View v = getLayoutInflater().inflate(R.layout.activity_report_question, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Soruyu Bildir");
        alertBuilder.setView(v);

        alertBuilder.setPositiveButton(getResources().getString(R.string.mail_gonder), null);
        alertBuilder.setNegativeButton("Kapat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

        final EditText etMessage = (EditText) v.findViewById(R.id.etUserMessage);
        final EditText etEmail = (EditText) v.findViewById(R.id.etUserEmail);

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkUserMessage(etMessage)) {

                    if (checkUserEmail(etEmail)) {

                        if (Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {

                            alertDialog.dismiss();
                            reportQuestion(questionList.get(position).getQuestionID(), etMessage.getText().toString(), etEmail.getText().toString());

                        } else {

                            etEmail.setError(getResources().getString(R.string.valid_mail));
                            etEmail.requestFocus();
                        }

                    } else {

                        etEmail.setError(getResources().getString(R.string.write_mail));
                        etEmail.requestFocus();

                    }

                } else {

                    etMessage.setError(getResources().getString(R.string.write_message));
                    etMessage.requestFocus();
                }
            }
        });
    }

    private boolean checkUserMessage(EditText etMsg) {

        if (!TextUtils.isEmpty(etMsg.getText().toString()))
            return true;
        else
            return false;
    }

    private boolean checkUserEmail(EditText etEmail) {

        if (!TextUtils.isEmpty(etEmail.getText().toString()))
            return true;
        else
            return false;
    }

    private void reportQuestion(String questionID, String message, String email) {

        String params[] = {questionID, message, email};

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.report_message));
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        HttpUtil httpUtil = new HttpUtil();
        httpUtil.post("reportQuestion", params, new HttpCallback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d(TAG, String.valueOf(e));

                        if (pd != null && pd.isShowing())
                            pd.dismiss();
                    }
                });
            }

            @Override
            public void onSuccess(Call call, final Response response) throws IOException {

                final String result = response.body().string();
                Log.d(TAG, result);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (pd != null && pd.isShowing())
                            pd.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            Toast.makeText(TrialExamActivity.this, jsonObject.getString("Message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//
//        if(position==0)
//            ibPrevious.setVisibility(View.GONE);
//        else
//            ibPrevious.setVisibility(View.VISIBLE);

        questionID = questionList.get(position).getQuestionID();
        getSupportActionBar().setSubtitle(getString(R.string.label_question_no, position + 1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class QuestionPagerAdapter extends FragmentStatePagerAdapter {

        List<TrialQuestions> quesList;

        public QuestionPagerAdapter(FragmentManager fm, List<TrialQuestions> questions) {
            super(fm);

            quesList = questions;
        }

        @Override
        public Fragment getItem(int position) {
            return QuestionFragment.newInstance(position, quesList.get(position));
        }

        @Override
        public int getCount() {
            return questionList.size();
        }
    }


    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            total_seconds = total_seconds - 1;
            handler.postDelayed(this, 1000);

            long current_milliseconds = total_seconds * 1000;
            tvTimer.setText(getString(R.string.timer_value, getHours(current_milliseconds), getMinutes(current_milliseconds), getSeconds(current_milliseconds)));

            if (total_seconds == 0 && QuestionFragment.attemptedQuesCount > 0) {

                finish();

                Intent intent = new Intent(TrialExamActivity.this, ExamResultActivity.class);
                intent.putExtra("attempted_questions", (Serializable) questionList);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        }
    };


    private int getHours(long timeInMillis) {

        return (int) ((timeInMillis / (1000 * 60 * 60)));

    }

    private int getMinutes(long timeInMillis) {

        return (int) ((timeInMillis / (1000 * 60)) % 60);
    }


    private int getSeconds(long timeInMillis) {

        return (int) ((timeInMillis / 1000) % 60);

    }

    @Override
    public void onBackPressed() {
        // You're not allowed to go back during exam
    }
}
