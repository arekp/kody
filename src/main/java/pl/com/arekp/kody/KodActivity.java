package pl.com.arekp.kody;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

public class KodActivity extends AppCompatActivity {
    private Button mButton;
    private EditText editTextconte;
    private EditText editTexttype;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kod);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //setContentView(R.layout.barcode_capture);
        mButton = (Button) findViewById(R.id.scan_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(KodActivity.this);
                integrator.initiateScan();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String content;
            String formatName;
            String errorCorrect;
            content = scanResult.getContents();
            formatName = scanResult.getFormatName();
            errorCorrect = scanResult.getErrorCorrectionLevel();
            editTextconte = (EditText) findViewById(R.id.editTextcode);
            editTexttype = (EditText) findViewById(R.id.editTextType);
            editTextconte.setText(content);
            editTexttype.setText(formatName);


            Log.d("code czytanie danych ", content+" -- "+formatName+" - "+errorCorrect);

        }
        // else continue with any other code you need in the method

    }

}
