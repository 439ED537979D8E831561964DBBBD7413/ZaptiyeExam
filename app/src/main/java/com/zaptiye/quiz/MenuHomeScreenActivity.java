package com.zaptiye.quiz;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zaptiye.quiz.bean.TrialExam;
import com.zaptiye.quiz.bean.TrialQuestions;
import com.zaptiye.quiz.http.HttpCallback;
import com.zaptiye.quiz.http.HttpUtil;
import com.zaptiye.quiz.trialexams.TrialExamActivity;
import com.zaptiye.quiz.util.IabHelper;
import com.zaptiye.quiz.util.IabResult;
import com.zaptiye.quiz.util.Purchase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Home Screen of this apps. Display Button to play quiz, Leaderboard, achievement, setting etc.
 *
 * @author Arkay Apps
 */
public class MenuHomeScreenActivity extends Activity implements
        View.OnClickListener, IabHelper.OnIabSetupFinishedListener, IabHelper.OnIabPurchaseFinishedListener {

    private Button btnSetting, btnAbout, btnHelp, btnGuncelKanunlar, btnMisyon;
    private Button ibTrialExam;    //private ImageButton ibTrialExam;

    /**
     * The interstitial ad.
     */
    public static final String PREFS_NAME = "preferences";
    public static final String PREFS_NAME_AOF = "preferencesAof";
    private static final String DATABASE_NAME = "database.db";
    public static final String myshareprefkey = "quizpower";
    public static final String myshareprefkeyAof = "quizpowerAof";

    public static final String SOUND_EFFECT = "sound_effect";
    public static final String VIBRATION = "vibration";

    public static final String TOTAL_SCORE = "total_score";
    public static final String LEVEL = "level";

    //Acheivement
    public static final String LEVEL_COMPLETED = "level_completed";
    public static final String IS_LAST_LEVEL_COMPLETED = "is_last_level_completed";
    public static final String LAST_LEVEL_SCORE = "last_level_score";
    public static final String HOW_MANY_TIMES_PLAY_QUIZ = "how_many_time_play_quiz";
    public static final String COUNT_QUESTION_COMPLETED = "count_question_completed";
    public static final String COUNT_RIGHT_ANSWARE_QUESTIONS = "count_right_answare_questions";

    public static final String VERY_CURIOUS_UNLOCK = "is_very_curious_unlocked";

    final int RC_RESOLVE = 5000, RC_UNUSED = 5001;

    SharedPreferences settings;
    private static final int OUR_STATE_KEY = 2;

    Context context;
    public static final String REG_ID = "regId";
    static final String TAG = "MenuHomeScreenActivity";
    AsyncTask<Void, Void, String> shareRegidTask;

    ProgressDialog progress;
    private final Handler mHandler = new Handler();


    // whether we already loaded the state the first time (so we don't reload
    // every time the activity goes to the background and comes back to the
    // foreground)
    boolean mAlreadyLoadedState = false;


    // Set to true to automatically start the sign in flow when the Activity
    // starts.  Set to false to require the user to click the button in order to sign in.

    public RelativeLayout main_home_layout;
    private static final int IN_APP_RQ_CODE = 104;

    private String selectedExamName,selectedExamID;
    private Dialog dialog;


    @Override
    public void onIabSetupFinished(IabResult result) {

        Log.d(TAG, result.toString());

        if (result.isSuccess())
            Log.d(TAG, "InApp Billing Setup Successful");
        else
            Log.d(TAG, "InApp Billing Setup Failed");
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {

        if (result.isFailure())
            return;


        Set<String> exams = settings.getStringSet("exam_list", null);

        if (exams != null)
            exams.add(info.getSku());
        else {

            exams = new HashSet<>();
            exams.add(info.getSku());

        }
        settings.edit().putStringSet("exam_list", exams).apply();
        Log.d(TAG, info.toString());
        Log.d(TAG, settings.getStringSet("exam_list", null).toString());


        if (internetErisimi())
            getQuestions(selectedExamID);
        else
            Toast.makeText(this, "No Internet Connection Found", Toast.LENGTH_LONG).show();

//        Intent intent = new Intent(this, TrialExamActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("exam_id", selectedExamID);
//        startActivity(intent);

    }

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    private IInAppBillingService mService;
    private IabHelper billingHelper;
    private String base64PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh2u+Q6TYr5rVTxHYOR4DeelGSBKgl8Bn8/1FqTVNOh6cRsJW4F22o0zYxg5fBAYrYDK1A3wVcwoh9B41mMm3QJ2x2x7++5+IMC+hEUZAahc+/vJC5eZRfqS4rTJbOKVNzkaxx0QLLb27TBd/JwPbiysatV0u3tLS8uJtp/nL0NbO7V4ByV57crA4+qCZCnDavtivSYCg3U19q1IKT7Lff45K5EcAF86qCy6tbC3I8J6hkbh5vW0ewxXncbehxcrOJ2H4IQdXp+XRfPpJOCmUlqGTvQdFZL+L6yix0xnoJ/U2JXHSaXkMCQDQJPacFDc9zdm775WiOo+QrZqQltvssQIDAQAB";


    public void purchaseExam(String SKU_ITEM) {

        try {

            billingHelper.launchPurchaseFlow(this, SKU_ITEM, IN_APP_RQ_CODE,
                    this, "");


        } catch (IabHelper.IabAsyncInProgressException e) {

            Log.d(TAG, e.getLocalizedMessage());
            e.printStackTrace();

        }
    }

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;

            Log.d(TAG, "Service Disconnected");

        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);

            getExamPurchaseList();
            Log.d(TAG, "Service Connected");
        }
    };


    private void getExamPurchaseList() {

        try {

            Bundle purchaseBundle = mService.getPurchases(3, getPackageName(), "inapp", null);

            if (purchaseBundle != null) {

                int response = purchaseBundle.getInt("RESPONSE_CODE");

                if (response == 0) {

                    List<String> ownedSKU =
                            purchaseBundle.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

                    Set<String> ownedSet = new HashSet<>();
                    ownedSet.addAll(ownedSKU);
                    settings.edit().putStringSet("exam_list", ownedSet).apply();


                    Log.d(TAG, ownedSet.toString());
                    Log.d(TAG, ownedSKU.toString());


                    String continuationToken =
                            purchaseBundle.getString("INAPP_CONTINUATION_TOKEN");

                    Log.d(TAG, continuationToken + "");

                }
            }


        } catch (RemoteException e) {

            e.printStackTrace();
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context = getApplicationContext();


        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);


        billingHelper = new IabHelper(this, base64PublicKey);

        // enable debug logging (for a production application, you should set this to false).
        billingHelper.enableDebugLogging(true);

        // Starting setup
        billingHelper.startSetup(this);


        settings = getSharedPreferences(MenuHomeScreenActivity.PREFS_NAME, 0);


        ibTrialExam= (Button) findViewById(R.id.ibTrialExams);
        ibTrialExam.setOnClickListener(this);


        btnMisyon= (Button) findViewById(R.id.btnMisyon);
        btnMisyon.setOnClickListener(this);



        btnSetting = (Button) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(this);

        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);

        btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(this);

        btnGuncelKanunlar = (Button) findViewById(R.id.btnGuncelKanunlar);
        btnGuncelKanunlar.setOnClickListener(this);

        checkDB();

        progress = new ProgressDialog(this);
        progress.setTitle(getResources().getString(R.string.pleasewait));
        progress.setMessage(getResources().getString(R.string.dataloading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        Handler delayhandler = new Handler();

        delayhandler.postDelayed(stopLoadDataDialogSomeTime, 5000);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    Runnable stopLoadDataDialogSomeTime = new Runnable() {
        public void run() {
            progress.dismiss();
        }
    };


    //?NTERNET ER???M? VARMI YOKMU KONTROL ET
    public boolean internetErisimi() {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null

                && conMgr.getActiveNetworkInfo().isAvailable()

                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;

        } else {

            return false;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {

            if (billingHelper != null)
                billingHelper.disposeWhenFinished();

        } catch (IllegalArgumentException e) {
            Log.d(TAG, e.getLocalizedMessage());

        } finally {
            billingHelper = null;
        }


        if (mService != null)
            unbindService(mServiceConn);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {



            case R.id.btnMisyon:

                Intent misyon=new Intent(this,Misyon.class);
                startActivity(misyon);

                break;



            case R.id.btnSetting:
                Intent playQuiz = new Intent(this, SettingActivity.class);
                startActivity(playQuiz);
                break;
            case R.id.btnAbout:
                Intent intAbout = new Intent(this, AboutUsActivity.class);
                startActivity(intAbout);
                break;
            case R.id.btnHelp:
                Intent howtoplay = new Intent(this, HowToPlayActivity.class);
                startActivity(howtoplay);
                break;

            case R.id.btnGuncelKanunlar:
                Intent kanunlar = new Intent(this, GuncelKanunlar.class);
                startActivity(kanunlar);
                break;

        }


        if (v.getId() == R.id.ibTrialExams) {

            getExamList();
        }

    }

    public void checkDB() {
        try {
            String packageName = this.getPackageName();
            String destPath = "/data/data/" + packageName + "/databases";
            String fullPath = "/data/data/" + packageName + "/databases/" + DATABASE_NAME;
            // this database folder location
            File f = new File(destPath);
            // this database file location
            File obj = new File(fullPath);
            // check if databases folder exists or not. if not create it
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
            } else {
                boolean isDelete = f.delete();
                Log.i("Delete", "Delete" + isDelete);

            }
            // check database file exists or not, if not copy database from
            // assets
            if (!obj.exists()) {
                this.CopyDB(fullPath);
            } else {
                this.CopyDB(fullPath);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void CopyDB(String path) throws IOException {

        InputStream databaseInput = null;
        String outFileName = path;
        OutputStream databaseOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        // open database file from asset folder
        databaseInput = this.getAssets().open(DATABASE_NAME);
        while ((length = databaseInput.read(buffer)) > 0) {
            databaseOutput.write(buffer, 0, length);
            databaseOutput.flush();
        }
        databaseInput.close();

        databaseOutput.flush();
        databaseOutput.close();
    }


    boolean addList = false;

    @Override
    public void onBackPressed() {


    }





    public static byte[] intToByteArray(int a) {
        return BigInteger.valueOf(a).toByteArray();
    }

    public static int byteArrayToInt(byte[] b) {
        return new BigInteger(b).intValue();
    }



    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    private void getExamList() {

        final List<TrialExam> examList = new ArrayList<>();

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.trialLoading));
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        HttpUtil httpUtil = new HttpUtil();
        httpUtil.post("getExams", null, new HttpCallback() {
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

                            JSONObject examObj = new JSONObject(result);

                            if (examObj.getBoolean("Result")) {
                                JSONArray examArray = examObj.getJSONArray("ExamList");

                                for (int i = 0; i < examArray.length(); i++) {

                                    TrialExam trialExam = new TrialExam();
                                    trialExam.setExamName(examArray.getJSONObject(i).getString("exam_name"));
                                    trialExam.setExamPurchaseID(examArray.getJSONObject(i).getString("exam_product_id"));
                                    trialExam.setExamID(examArray.getJSONObject(i).getString("exam_id"));
                                    trialExam.setExamCost(Float.parseFloat(examArray.getJSONObject(i).getString("exam_cost")));
                                    examList.add(trialExam);

                                }


                                showExamPopup(examList);

                            } else
                                Toast.makeText(MenuHomeScreenActivity.this, examObj.getString("Message"), Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        });


    }


    private void getQuestions(String examID) {

        final List<TrialQuestions> questionList = new ArrayList<>();

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.examLoading));
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        HttpUtil httpUtil = new HttpUtil();
        httpUtil.post("getQuestionsByExam", new String[]{examID}, new HttpCallback() {
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

                            JSONObject questionObject = new JSONObject(result);

                            if (questionObject.getBoolean("Result")) {

                                JSONArray questionArray = questionObject.getJSONArray("Questions");

                                for (int i = 0; i < questionArray.length(); i++) {

                                    JSONObject questionResult = questionArray.getJSONObject(i);
                                    String[] optionList = questionResult.getString("obj_description").split(Pattern.quote("#$#"));

                                    TrialQuestions trialQuestions = new TrialQuestions();

                                    trialQuestions.setQuestionID(questionResult.getString("question_id"));
                                    trialQuestions.setQuestion(questionResult.getString("question_name"));
                                    trialQuestions.setCorrectAnswer(Integer.parseInt(questionResult.getString("correct_answer")));

                                    trialQuestions.setAttemptedQuestionID(null);
                                    trialQuestions.setAttemptedAnswer(-1);

                                    trialQuestions.setOptionA(optionList[0]);
                                    trialQuestions.setOptionB(optionList[1]);
                                    trialQuestions.setOptionC(optionList[2]);
                                    trialQuestions.setOptionD(optionList[3]);
                                    trialQuestions.setOptionE(optionList[4]);

                                    questionList.add(trialQuestions);

                                }


                            } else
                                Toast.makeText(MenuHomeScreenActivity.this, questionObject.getString("Message"), Toast.LENGTH_LONG).show();


                            if (questionList.size() > 0) {

                                if (dialog != null && dialog.isShowing())
                                    dialog.dismiss();

                                Intent intent = new Intent(MenuHomeScreenActivity.this, TrialExamActivity.class);
                                intent.putExtra("question_list", (Serializable) questionList);
                                intent.putExtra("exam_name", selectedExamName);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }


    /********* Trial Exam Popup *********/

    private void showExamPopup(List<TrialExam> trialExamList) {


        View v = getLayoutInflater().inflate(R.layout.trial_exam_popup, null);

        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setTitle(null);

        GridView gVTrialExams = (GridView) v.findViewById(R.id.gvTrialExamList);
        TrialExamAdapter mAdapter = new TrialExamAdapter(trialExamList);
        gVTrialExams.setAdapter(mAdapter);

        dialog.setContentView(v);
        dialog.show();

        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    }

    private class TrialExamAdapter extends BaseAdapter {

        private List<TrialExam> trialExams;

        TrialExamAdapter(List<TrialExam> examList) {
            trialExams = examList;
        }


        @Override
        public int getCount() {
            return trialExams.size();
        }

        @Override
        public Object getItem(int position) {
            return trialExams.get(position).getExamID();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final TrialExam trialExam = trialExams.get(position);

            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.custom_trial_exam_row_popup, parent, false);

            TextView tvExam = (TextView) convertView.findViewById(R.id.tvTrialExamName);
            tvExam.setText(trialExam.getExamName());

            Button btnBuy = (Button) convertView.findViewById(R.id.btnBuyExam);


            if (trialExam.getExamCost() == 0) {

                btnBuy.setBackgroundResource(R.drawable.button_green);
                btnBuy.setText(getResources().getString(R.string.free));

            } else if (trialExam.getExamCost() > 0) {

                Set<String> examSet = settings.getStringSet("exam_list", null);

                if (examSet != null) {

                    if (examSet.contains(trialExam.getExamPurchaseID())) {

                        btnBuy.setBackgroundResource(R.drawable.button_green);
                        btnBuy.setText(getResources().getString(R.string.bought));

                    } else {

                        btnBuy.setBackgroundResource(R.drawable.button_red);
                        btnBuy.setText(getResources().getString(R.string.buy) /*+ trialExam.getExamCost()*/);

                    }

                } else {

                    btnBuy.setBackgroundResource(R.drawable.button_red);
                    btnBuy.setText(getResources().getString(R.string.buy) /*+ trialExam.getExamCost()*/);

                }

            }

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedExamName = trialExam.getExamName();
                    selectedExamID = trialExam.getExamID();

                    Set<String> purchaseExamList = settings.getStringSet("exam_list", null);

                    if (trialExam.getExamCost() == 0)
                        getQuestions(trialExam.getExamID());
                    else {

                        if (purchaseExamList != null && purchaseExamList.contains(trialExam.getExamPurchaseID()))
                            getQuestions(trialExam.getExamID());
                        else
                            purchaseExam(trialExam.getExamPurchaseID());

                    }

                }
            });


            return convertView;
        }
    }

}


