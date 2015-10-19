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

        Parse.initialize(this, "sNte7AF2eViB5oo4t3B3rHArFJS780C8J4BfFv3J", "FGKZ2urmeLJRMgJ8DKxR3bQgNkCSx06sn3Vxrqdv");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
