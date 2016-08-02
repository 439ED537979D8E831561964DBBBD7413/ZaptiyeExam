package com.zaptiye.quiz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zaptiye.quiz.bean.GameData;


public class LevelBitti extends Activity {

    private SharedPreferences.Editor editor;
    private SharedPreferences settings;
    private static int levelNo = 1;

    Button btnSifirla;

    public interface Listener {
        public void onStartGameRequested(boolean hardMode);

        public void displyHomeScreen();

        public GameData getGameData();

        public QuizCompletedActivity getQuizCompletedFragment();

    }

    QuizPlayActivity.Listener mListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_bitti);

        settings = getApplicationContext().getSharedPreferences(MenuHomeScreenActivity.PREFS_NAME, 0);

        btnSifirla = (Button) findViewById(R.id.btnSifirla);

        editor = settings.edit();
        editor.putBoolean(MenuHomeScreenActivity.IS_LAST_LEVEL_COMPLETED, true);
        mListener.getGameData().setLevelCompleted(levelNo);

        mListener.getGameData().save(settings, MenuHomeScreenActivity.myshareprefkey);
        editor.commit();

        btnSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                clear();
            }
        });




    }





    public void clear() {
        SharedPreferences prefs; // here you get your prefrences by either of two methods
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }



}
