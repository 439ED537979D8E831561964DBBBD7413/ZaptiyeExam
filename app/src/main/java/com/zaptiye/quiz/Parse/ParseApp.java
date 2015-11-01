package com.zaptiye.quiz.Parse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by asys on 19.10.2015.
 */
public class ParseApp extends Application {

    @Override
    public void onCreate() {

        Parse.initialize(this, "pPLeYrsw65LM6hQvbT3AjcWEhCcKtifVZNZN9Krb", "AX5uRpOgOMfpgH0BQjpZp9rCWIMxAPrQCtBG2TC6");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}