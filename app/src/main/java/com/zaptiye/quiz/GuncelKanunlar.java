package com.zaptiye.quiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.zaptiye.quiz.Kanunlar.Anayasa;


public class GuncelKanunlar extends Activity implements View.OnClickListener {


    Button anayasa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guncel_kanunlar);

        anayasa= (Button) findViewById(R.id.btnAnayasa);
        anayasa.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnAnayasa:
                    Intent anayasa=new Intent(this,Anayasa.class);
                    startActivity(anayasa);
            break;

        }
    }
}
