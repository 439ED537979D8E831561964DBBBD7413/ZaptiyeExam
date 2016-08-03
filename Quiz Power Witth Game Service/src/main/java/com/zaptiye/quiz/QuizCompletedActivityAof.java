package com.zaptiye.quiz;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.PlusShare;
import com.zaptiye.quiz.bean.GameDataAof;
import com.zaptiye.quiz.facebook.AsyncFacebookRunner;
import com.zaptiye.quiz.facebook.DialogError;
import com.zaptiye.quiz.facebook.Facebook;
import com.zaptiye.quiz.facebook.FacebookError;

/**
 * Created by ahmet on 31.10.2015.
 */
public class QuizCompletedActivityAof extends Fragment implements View.OnClickListener {

    public Dialog d;
    private Button btnPlayAgain, btnHome,  btnFacebook, btnGooglePlus, btnShare;
    private TextView txtLevelHeading, txtLevelScore,txtLevelTotalScore;
    boolean isLevelCompleted=false;
    //private SharedPreferences settings;
    private SharedPreferences settingsAof;

    int levelNo=1;
    int lastLevelScore = 0;
    boolean isCurrentAfair =false;


    int totalScore =0;
    private View v;

    public static String APP_ID = "305174149681312";

    private Facebook mFacebook = null;
    private AsyncFacebookRunner mAsyncRunner = null;
    private String appName="Quiz Power";
    private String appHashTag = "#quizranking";
    public interface Listener {
        public void onStartGameRequested(boolean hardMode);
        public void displyHomeScreen();
        public GameDataAof getGameDataAof();
        public void playAgainAof();

    }
    Listener mListener = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_game_over, container, false);
        final int[] CLICKABLES = new int[] { R.id.btnPlayAgain, R.id.btnHome
        };
        for (int i : CLICKABLES) {
            v.findViewById(i).setOnClickListener(this);
        }
        settingsAof = getActivity().getSharedPreferences(MenuHomeScreenActivity.PREFS_NAME_AOF, 0);

        appName = getActivity().getResources().getString(R.string.app_name);

        mFacebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(mFacebook);

        txtLevelHeading = (TextView)v.findViewById(R.id.txtLevelHeading);

        txtLevelScore = (TextView)v.findViewById(R.id.txtLevelScore);

        //totalScore = settings.getInt(MenuHomeScreenActivity.TOTAL_SCORE, 0);
        totalScore = mListener.getGameDataAof().getTotalScore();
        txtLevelScore.setText(""+totalScore);

        lastLevelScore = settingsAof.getInt(MenuHomeScreenActivity.LAST_LEVEL_SCORE, 0);
        txtLevelTotalScore = (TextView)v.findViewById(R.id.lblLevelTotalScore);
        txtLevelTotalScore.setText(""+lastLevelScore);


        btnPlayAgain = (Button) v.findViewById(R.id.btnPlayAgain);
        btnHome = (Button)v.findViewById(R.id.btnHome);

        btnPlayAgain.setOnClickListener(this);
        btnHome.setOnClickListener(this);

        btnFacebook = (Button) v.findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(this);

        btnGooglePlus = (Button) v.findViewById(R.id.btnGooglePlus);
        btnGooglePlus.setOnClickListener(this);

        btnShare = (Button) v.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);

        if(isCurrentAfair){
            btnPlayAgain.setText("Cancel");
        }

        boolean islevelcomplted = settingsAof.getBoolean(MenuHomeScreenActivity.IS_LAST_LEVEL_COMPLETED, false);

        levelNo = settingsAof.getInt(MenuHomeScreenActivity.LEVEL_COMPLETED, 1);

        if(islevelcomplted){
            levelNo--;
            txtLevelHeading.setText(getActivity().getString(R.string.level)+" "+ levelNo +" "+  getActivity().getResources().getString(R.string.finished));
            btnPlayAgain.setText("Sonra ki Level");
        }else{
            txtLevelHeading.setText(getActivity().getString(R.string.level)+" "+ levelNo +" "+  getActivity().getResources().getString(R.string.not_completed));
            btnPlayAgain.setText("Tekrar Dene");
        }


        return v;
    }

    public void setListener(Listener l) {
        mListener = l;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnPlayAgain:
                if (isCurrentAfair) {
                    getActivity().onBackPressed();
                } else {
                    mListener.playAgainAof();
                }
                break;
            case R.id.btnHome:
                getActivity().onBackPressed();
                break;
            case R.id.btnFacebook:
                facebookPost();
                break;
            case R.id.btnGooglePlus:
                Intent shareIntent = new PlusShare.Builder(getActivity())
                        .setType("text/plain")
                        .setText("\""+appName+"\"   I'm playing "+appHashTag+" Android App and just completed "+levelNo+" levels with "+totalScore+ " score Can you beat my high score?.")
                        .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?" + getActivity().getPackageName()))
                        .getIntent();
                getActivity().startActivityForResult(shareIntent, 0);

                break;
            case R.id.btnShare:
                Intent s = new Intent(Intent.ACTION_SEND);
                s.setType("text/plain");
                s.putExtra(Intent.EXTRA_SUBJECT, ""+appName);
                s.putExtra(Intent.EXTRA_TEXT, ""+appName+"\"   I'm playing "+appHashTag+" Android App and just completed "+levelNo+" levels with "+totalScore+ " score Can you beat my high score?.");
                s.putExtra("url", "http://goo.gl/CrGQTI");
                getActivity().startActivity(Intent.createChooser(s, ""+appName));
                break;
            default:
                break;
        }

    }

    public void facebookPost() {
        Bundle params = new Bundle();
        params.putString("name", ""+appName);
        params.putString("caption", "I'm playing "+appName+" Android App.");
        params.putString("description", "I'm just completed "+levelNo+" level on "+appHashTag+" with "+totalScore+" Can you beat my high score?.");
        params.putString("link", "https://play.google.com/store/apps/details?id="+getActivity().getPackageName());
        params.putString("picture", getActivity().getResources().getString(R.string.icon_url));
        mFacebook.dialog(getActivity(), "stream.publish", params, new Facebook.DialogListener() {

            @Override
            public void onFacebookError(FacebookError e) {
                // TODO handle error in publishing
            }

            @Override
            public void onError(DialogError e) {
                // TODO handle dialog errors
            }

            @Override
            public void onComplete(Bundle values) {
                Toast.makeText(getActivity(), "Post successful",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // TODO user don't want to share and presses cancel button
            }
        });
    }


}