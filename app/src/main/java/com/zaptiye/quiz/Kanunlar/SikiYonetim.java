package com.zaptiye.quiz.Kanunlar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zaptiye.quiz.R;

public class SikiYonetim extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kanunlar_siki_yonetim);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("https://docs.google.com/document/d/1ZxUqtRoNy18yGBDE3KMzEqnw3OXkXxzoxprqNHfbQH0/edit");
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


        final ProgressDialog progress = ProgressDialog.show(this, "S�k� Y�netim Kanunu", "Y�kleniyor....", true);
        progress.show();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(getApplicationContext(), "Sayfa y�klendi", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "L�tfen �nternete ba�lan�n�z", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

}
