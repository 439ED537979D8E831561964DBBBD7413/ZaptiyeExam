package com.zaptiye.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zaptiye.quiz.Kanunlar.Aile;
import com.zaptiye.quiz.Kanunlar.Anayasa;
import com.zaptiye.quiz.Kanunlar.Bayrak;
import com.zaptiye.quiz.Kanunlar.BilgiEdinme;
import com.zaptiye.quiz.Kanunlar.Cmk;
import com.zaptiye.quiz.Kanunlar.Cocuk;
import com.zaptiye.quiz.Kanunlar.Dernek;
import com.zaptiye.quiz.Kanunlar.Dilekce;
import com.zaptiye.quiz.Kanunlar.Eodt;
import com.zaptiye.quiz.Kanunlar.Etk;
import com.zaptiye.quiz.Kanunlar.Kabahatler;
import com.zaptiye.quiz.Kanunlar.Kacak;
import com.zaptiye.quiz.Kanunlar.Kimlik;
import com.zaptiye.quiz.Kanunlar.Ohal;
import com.zaptiye.quiz.Kanunlar.Pasaport;
import com.zaptiye.quiz.Kanunlar.Pvsk;
import com.zaptiye.quiz.Kanunlar.SikiYonetim;
import com.zaptiye.quiz.Kanunlar.Silah;
import com.zaptiye.quiz.Kanunlar.Siyasi;
import com.zaptiye.quiz.Kanunlar.Spor;
import com.zaptiye.quiz.Kanunlar.Tanik;
import com.zaptiye.quiz.Kanunlar.Tck;
import com.zaptiye.quiz.Kanunlar.Teblig;
import com.zaptiye.quiz.Kanunlar.Tmk;
import com.zaptiye.quiz.Kanunlar.Toplanti;
import com.zaptiye.quiz.Kanunlar.Trafik;
import com.zaptiye.quiz.Kanunlar.Vatan;
import com.zaptiye.quiz.Kanunlar.YardimToplama;


public class GuncelKanunlar extends Activity implements View.OnClickListener {


    Button anayasa,tck,cmk,pvsk,etk,btnDilekce,btnBilgiEdinme,
            btnKabahat,btnCocuk,kimlik,toplanti,dernekler,vatan,pasaport,tmk,kacak,tanik,spor,silah,teblig,siyasi,ohal,sikiyonetim,bayrak,yardim,trafik,aile,eodt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guncel_kanunlar);

        anayasa= (Button) findViewById(R.id.btnAnayasa);
        anayasa.setOnClickListener(this);

        tck= (Button) findViewById(R.id.btnTck);
        tck.setOnClickListener(this);

        cmk= (Button) findViewById(R.id.btnCmk);
        cmk.setOnClickListener(this);

        pvsk= (Button) findViewById(R.id.btnPvsk);
        pvsk.setOnClickListener(this);

        etk= (Button) findViewById(R.id.btnEtk);
        etk.setOnClickListener(this);

        btnDilekce= (Button) findViewById(R.id.btnDilekce);
        btnDilekce.setOnClickListener(this);

        btnBilgiEdinme= (Button) findViewById(R.id.btnBilgiEdinme);
        btnBilgiEdinme.setOnClickListener(this);

        btnKabahat= (Button) findViewById(R.id.btnKabahat);
        btnKabahat.setOnClickListener(this);

        btnCocuk= (Button) findViewById(R.id.btnCocuk);
        btnCocuk.setOnClickListener(this);

        kimlik= (Button) findViewById(R.id.kimlik);
        kimlik.setOnClickListener(this);

        toplanti= (Button) findViewById(R.id.toplanti);
        toplanti.setOnClickListener(this);

        dernekler= (Button) findViewById(R.id.dernekler);
        dernekler.setOnClickListener(this);

        vatan= (Button) findViewById(R.id.vatan);
        vatan.setOnClickListener(this);

        pasaport= (Button) findViewById(R.id.pasaport);
        pasaport.setOnClickListener(this);

        tmk= (Button) findViewById(R.id.tmk);
        tmk.setOnClickListener(this);

        kacak= (Button) findViewById(R.id.kacak);
        kacak.setOnClickListener(this);

        tanik= (Button) findViewById(R.id.tanik);
        tanik.setOnClickListener(this);

        spor= (Button) findViewById(R.id.spor);
        spor.setOnClickListener(this);

        silah= (Button) findViewById(R.id.silah);
        silah.setOnClickListener(this);

        teblig= (Button) findViewById(R.id.teblig);
        teblig.setOnClickListener(this);

        siyasi= (Button) findViewById(R.id.siyasi);
        siyasi.setOnClickListener(this);

        ohal= (Button) findViewById(R.id.ohal);
        ohal.setOnClickListener(this);

        sikiyonetim= (Button) findViewById(R.id.sikiyonetim);
        sikiyonetim.setOnClickListener(this);

        bayrak= (Button) findViewById(R.id.bayrak);
        bayrak.setOnClickListener(this);

        yardim= (Button) findViewById(R.id.yardim);
        yardim.setOnClickListener(this);

        trafik= (Button) findViewById(R.id.trafik);
        trafik.setOnClickListener(this);

        aile= (Button) findViewById(R.id.aile);
        aile.setOnClickListener(this);

        eodt= (Button) findViewById(R.id.eodt);
        eodt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnAnayasa:
                    Intent anayasa=new Intent(this,Anayasa.class);
                    startActivity(anayasa);
            break;

            case R.id.btnTck:
                Intent tck=new Intent(this,Tck.class);
                startActivity(tck);
                break;

            case R.id.btnCmk:
                Intent cmk=new Intent(this,Cmk.class);
                startActivity(cmk);
                break;

            case R.id.btnPvsk:
                Intent pvsk=new Intent(this,Pvsk.class);
                startActivity(pvsk);
                break;

            case R.id.btnEtk:
                Intent etk=new Intent(this,Etk.class);
                startActivity(etk);
                break;

            case R.id.btnDilekce:
                Intent dilekce=new Intent(this,Dilekce.class);
                startActivity(dilekce);
                break;

            case R.id.btnBilgiEdinme:
                Intent bilgi=new Intent(this,BilgiEdinme.class);
                startActivity(bilgi);
                break;

            case R.id.btnKabahat:
                Intent kabahat=new Intent(this,Kabahatler.class);
                startActivity(kabahat);
                break;

            case R.id.btnCocuk:
                Intent cocuk=new Intent(this,Cocuk.class);
                startActivity(cocuk);
                break;

            case R.id.kimlik:
                Intent kimlik=new Intent(this,Kimlik.class);
                startActivity(kimlik);
                break;

            case R.id.toplanti:
                Intent toplanti=new Intent(this,Toplanti.class);
                startActivity(toplanti);
                break;

            case R.id.dernekler:
                Intent dernek=new Intent(this,Dernek.class);
                startActivity(dernek);
                break;

            case R.id.vatan:
                Intent vatan=new Intent(this,Vatan.class);
                startActivity(vatan);
                break;

            case R.id.pasaport:
                Intent pass=new Intent(this,Pasaport.class);
                startActivity(pass);
                break;

            case R.id.tmk:
                Intent tmk=new Intent(this,Tmk.class);
                startActivity(tmk);
                break;

            case R.id.kacak:
                Intent kac=new Intent(this,Kacak.class);
                startActivity(kac);
                break;

            case R.id.tanik:
                Intent tan=new Intent(this,Tanik.class);
                startActivity(tan);
                break;

            case R.id.spor:
                Intent spor=new Intent(this,Spor.class);
                startActivity(spor);
                break;

            case R.id.silah:
                Intent sil=new Intent(this,Silah.class);
                startActivity(sil);
                break;

            case R.id.teblig:
                Intent teb=new Intent(this,Teblig.class);
                startActivity(teb);
                break;

            case R.id.siyasi:
                Intent siy=new Intent(this,Siyasi.class);
                startActivity(siy);
                break;

            case R.id.ohal:
                Intent oha=new Intent(this,Ohal.class);
                startActivity(oha);
                break;

            case R.id.sikiyonetim:
                Intent sik=new Intent(this,SikiYonetim.class);
                startActivity(sik);
                break;

            case R.id.bayrak:
                Intent bayrak=new Intent(this,Bayrak.class);
                startActivity(bayrak);
                break;

            case R.id.yardim:
                Intent yar=new Intent(this,YardimToplama.class);
                startActivity(yar);
                break;

            case R.id.trafik:
                Intent tra=new Intent(this,Trafik.class);
                startActivity(tra);
                break;

            case R.id.aile:
                Intent aile=new Intent(this,Aile.class);
                startActivity(aile);
                break;

            case R.id.eodt:
                Intent eo=new Intent(this,Eodt.class);
                startActivity(eo);
                break;
        }
    }
}
