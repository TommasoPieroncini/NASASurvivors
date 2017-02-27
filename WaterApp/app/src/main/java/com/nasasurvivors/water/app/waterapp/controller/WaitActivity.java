package com.nasasurvivors.water.app.waterapp.controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;
import com.nasasurvivors.water.app.waterapp.R;

/**
 * Created by zachschlesinger on 2/19/17.
 */

public class WaitActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        progressDialog = new ProgressDialog(this);

        //UI components
        webView = (WebView) findViewById(R.id.webview);

        String x = "<!DOCTYPE html><html><body><img src=\"https://media.giphy.com/media/vMRDAT7oqbMfm/giphy.gif\"" +
                " alt=\"clouds\" width=\"100%\" height=\"100%\"></body></html>";

        webView.loadData(x, "text/html", "utf-8");
        progressDialog.setMessage("Putting you in the clouds");
        progressDialog.show();
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                //TODO: Do something every second
            }

            public void onFinish() {
                Toast.makeText(getBaseContext(), "Timeout!", Toast.LENGTH_SHORT).show();
                finish();

            }
        }.start();
    }



    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }
}
