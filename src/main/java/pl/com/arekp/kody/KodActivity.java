package pl.com.arekp.kody;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class KodActivity extends AppCompatActivity {
    private Button mButton;
    private Button mButton1;
    private TextView textViewKod;
    private TextView textViewTyp;
    private TextView textViewWynik;
    private EditText cena;
    private String cena1;
    private String content;
    private String formatName;
    private String errorCorrect;
    private String status;
    private String message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kod);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewWynik = (TextView) findViewById(R.id.textViewWynik);

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

        mButton1 = (Button) findViewById(R.id.buttonWyslij);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cena = (EditText)findViewById(R.id.editTextCena);
                cena1=cena.getText().toString();
                NetAsync(v);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {

            content = scanResult.getContents();
            formatName = scanResult.getFormatName();
            errorCorrect = scanResult.getErrorCorrectionLevel();
            textViewKod = (TextView) findViewById(R.id.textViewKod);
            textViewTyp = (TextView) findViewById(R.id.textViewTyp);
            textViewKod.setText(content);
            textViewTyp.setText(formatName);


            Log.d("code czytanie danych ", content+" -- "+formatName+" - "+errorCorrect);

        }
        // else continue with any other code you need in the method

    }

    private class CreateNewProduct extends AsyncTask implements pl.com.arekp.kody.CreateNewProduct {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   textViewWynik = (TextView) findViewById(R.id.textViewWynik);
            textViewWynik.setText("Wysyłamyyyyyyy .......");
           // pDialog = new ProgressDialog(NewProductActivity.this);
           // pDialog.setMessage("Creating Product..");
          //  pDialog.setIndeterminate(false);
          //  pDialog.setCancelable(true);
          //  pDialog.show();
        }

        /**
         * Creating product
         * */
       // protected String doInBackground(String... args) {
        //    @Override
            protected Boolean doInBackground(Object[] params) {
            String kod = content;
            String typ = formatName;
           // String cena1 = cena.getText().toString();
           // String cena = cena.toString();
String url = "http://arekp.xlx.pl/pawel/create_product.php?name="+kod+"&price="+cena1+"&description="+typ;
            // Building Parameters
          //  List<NameValuePair> params = new ArrayList<NameValuePair>();
         //   params.add(new BasicNameValuePair("name", name));
         //   params.add(new BasicNameValuePair("price", price));
         //   params.add(new BasicNameValuePair("description", description));

            // getting JSON Object
            // Note that create product url accepts POST method
          //  JSONObject json = jsonParser.makeHttpRequest(url_create_product,"POST", params);
            ServiceHandler sh = new ServiceHandler();
            String json = sh.makeServiceCall(url,1);
            try {
                JSONObject jObj = new JSONObject(json);
                status = jObj.getString("success");
                message = jObj.getString("message");
                Log.d("CreateNewProduct", status + " - " + message);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("CreateNewProduct","blad parsowania json");
            }



            return true;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        public void onPostExecute(Boolean unused) {
            // dismiss the dialog once done
           // textViewWynik.clearComposingText();
           textViewWynik.setText("Wynik dodawaniw " + status + " Komunikat " + message);
Log.d("onPostExecute","powinno dzialac "+unused);
                Toast.makeText(KodActivity.this, "Wynik dodawaniw "+status+" Komunikat "+message, Toast.LENGTH_LONG).show();
        }


    }
    public void NetAsync(View view){
        new CreateNewProduct().execute();
    }
}
