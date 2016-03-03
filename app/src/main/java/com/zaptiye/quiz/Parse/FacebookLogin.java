package com.zaptiye.quiz.Parse;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.zaptiye.quiz.R;
import com.zaptiye.quiz.SplashActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FacebookLogin extends Activity implements View.OnClickListener {

    Button FacebookLogin;


    public static final List<String> mPermissions = new ArrayList<String>() {{

        add("public_profile");
    }};


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);

        FacebookLogin = (Button) findViewById(R.id.login_button);
        FacebookLogin.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login_button:


                ParseFacebookUtils.logInWithReadPermissionsInBackground(this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user == null) {

                            Log.d("Myapp", "users logout");

                        } else if (user.isNew()) {

                            Log.d("Myapp", "new user logged with facebook");
                            /*
                           Intent a = new Intent(FacebookLogin.this, SplashActivity.class);
                            startActivity(a);
                            */


                        }else {
                            Log.d("Myapp", "User logged in through Facebook!");

                            Intent n=new Intent(FacebookLogin.this,SplashActivity.class);
                            startActivity(n);
                        }
                    }
                });


                break;


        }
    }


}
