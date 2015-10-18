package com.zaptiye.quiz.Kanunlar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zaptiye.quiz.R;

public class Bayrak extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kanunlar_bayrak);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://www.ttk.gov.tr/index.php?Page=Sayfa&No=81");
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


        final ProgressDialog progress = ProgressDialog.show(this, "Bayrak Kanunu", "Yükleniyor....", true);
        progress.show();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(getApplicationContext(), "Sayfa yüklendi", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Lütfen Ýnternete baðlanýnýz", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

}
