package com.zaptiye.quiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Misyon extends Activity implements View.OnClickListener {

    private Button btnSilah,btnProtokol,btnEnglish,btnMisyon2015,protokolsoru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misyon);



        btnSilah= (Button) findViewById(R.id.btnSilah);
        btnSilah.setOnClickListener(this);

        btnProtokol= (Button) findViewById(R.id.btnProtokol);
        btnProtokol.setOnClickListener(this);

        btnMisyon2015= (Button) findViewById(R.id.btnMisyon2015);
        btnMisyon2015.setOnClickListener(this);

        btnEnglish= (Button) findViewById(R.id.btnEnglish);
        btnEnglish.setOnClickListener(this);

        protokolsoru= (Button) findViewById(R.id.protokolsoru);
        protokolsoru.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSilah:

                Intent silah=new Intent(this,Silah.class);
                startActivity(silah);

                break;


            case R.id.btnProtokol:

                Intent protokol=new Intent(this,Protokol.class);
                startActivity(protokol);

                break;

            case R.id.btnMisyon2015:

                Intent misyon2015=new Intent(this,Misyon2015.class);
                startActivity(misyon2015);

                break;

            case R.id.btnEnglish:

                Intent ingilizce=new Intent(this,English.class);
                startActivity(ingilizce);

                break;

            case R.id.protokolsoru:

                Intent ps=new Intent(this,ProtokolSoru.class);
                startActivity(ps);

                break;


        }

    }
}
