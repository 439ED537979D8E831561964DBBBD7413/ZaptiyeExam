package com.zaptiye.quiz.Parse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.zaptiye.quiz.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ahmet on 17.05.2015.
 */
public class PushOpen extends Activity {

    TextView notification_title;
    TextView notification_message;

    //Button devamEt;
    //Button cikis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pushopen);

        /*
        devamEt= (Button) findViewById(R.id.btnDevam);
        cikis= (Button) findViewById(R.id.btnExit);

        devamEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PushOpen.this,MainActivity.class);
                startActivity(i);
            }
        });

        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        */

        notification_title= (TextView) findViewById(R.id.alert);
        notification_message= (TextView) findViewById(R.id.message);

        ParseAnalytics.trackAppOpened(getIntent());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String jsonData = extras.getString("com.parse.Data");

        try{
            JSONObject notification = new JSONObject(jsonData);
            String title = notification.getString("title");
            String message = notification.getString("alert");

            notification_title.setText(title);
            notification_message.setText(message);
        }
        catch(JSONException e){
            Toast.makeText(getApplicationContext(), "Birþeyler Ters Gitti", Toast.LENGTH_SHORT).show();
        }
    }
}
