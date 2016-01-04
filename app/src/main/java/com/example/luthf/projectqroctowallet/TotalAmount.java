package com.example.luthf.projectqroctowallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.luthf.projectqroctowallet.app.AppController;
import com.example.luthf.projectqroctowallet.app.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TotalAmount extends AppCompatActivity {

    //public String KEY_CID;
    SessionManager session;

    //public static String KEY_CID = "cid";

    private static final String TAG = TotalAmount.class.getSimpleName();

    private static final String url = "http://202.152.30.60/octowallet/index.php/api/transaction/sales";
    private ProgressDialog pDialog;
    //String COSTUMER_ID = "0812121212";
    //String merchant = "19000001;0001";
    //String amount = "1000";


    ArrayList<HashMap<String, String>> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_amount);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cid = user.get(SessionManager.KEY_CID);

        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalAmount.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //cid = (MobilModel) getIntent().getSerializableExtra(KEY_ITEM);

        Intent intent = getIntent();
        final String amount = intent.getStringExtra("amount");
        //final String cid = intent.getStringExtra("cid");
        final String merchant = intent.getStringExtra("merchantid");
        //Log.d("cidmain", "cidmain" + cid);
        Log.d("merchant","merchant"+merchant);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        historyList = new ArrayList<HashMap<String, String>>();

        final TextView mid = (TextView) findViewById(R.id.merchantname);
        final TextView sale1 = (TextView) findViewById(R.id.sale);
        final TextView disc1 = (TextView) findViewById(R.id.disc);
        final TextView newamount1 = (TextView) findViewById(R.id.newamount);
        //hiden from failed total amount
        final TextView succes = (TextView) findViewById(R.id.success);
        final TextView reason = (TextView) findViewById(R.id.reason);
        final TextView hide1 = (TextView) findViewById(R.id.hide1);
        final TextView hide2 = (TextView) findViewById(R.id.hide2);
        final TextView hide3 = (TextView) findViewById(R.id.hide3);
        final TextView hide4 = (TextView) findViewById(R.id.hide4);
        final View hide5 = (View) findViewById(R.id.hide5);

        //Log.d("salediamount", "salediamount" + amount);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Log.d("network", "network" + networkInfo);
        //String network = networkInfo.toString();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            StringRequest movieReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject job = new JSONObject(response);
                        String error = job.getString("response");
                        if (error.equals("00")) {
                            JSONObject data = job.getJSONObject("status");
                            String storename = data.getString("store_name");
                            String sale = data.getString("amount");
                            String disc = data.getString("discount");
                            String newamount = data.getString("new_amount");
                            mid.setText(storename);
                            sale1.setText(sale);
                            disc1.setText(disc);
                            newamount1.setText(newamount);
                            Log.d("store", "store" + storename);

                        } else if (error.equals("60")) {
                            Log.d("apa", "apa" + error);
                            succes.setText("Sale Failed");
                            reason.setText("Qr Code does not exist");
                            hide1.setVisibility(View.INVISIBLE);
                            hide2.setVisibility(View.INVISIBLE);
                            hide3.setVisibility(View.INVISIBLE);
                            hide4.setVisibility(View.INVISIBLE);
                            hide5.setVisibility(View.INVISIBLE);
                        }
                    } catch (JSONException e) {
                        String eror = e.getMessage();
                        e.printStackTrace();
                    }
                    hidePDialog();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hidePDialog();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("merchant", merchant);
                    params.put("amount", amount);
                    params.put("cid", cid);
                    Log.d("cidkey", "cidkey" + cid);
                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(movieReq);

        } else {
            succes.setText("Sale Failed");
            reason.setText("No internet access available");
            hide1.setVisibility(View.INVISIBLE);
            hide2.setVisibility(View.INVISIBLE);
            hide3.setVisibility(View.INVISIBLE);
            hide4.setVisibility(View.INVISIBLE);
            hide5.setVisibility(View.INVISIBLE);
            hidePDialog();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
