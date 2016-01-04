package com.example.luthf.projectqroctowallet;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;


import com.example.luthf.projectqroctowallet.app.AppController;
import com.example.luthf.projectqroctowallet.app.SessionManager;


import com.example.luthf.projectqroctowallet.history.Event;
import com.example.luthf.projectqroctowallet.history.EventAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class History extends AppCompatActivity {
    // Log tag
    private static final String TAG = History.class.getSimpleName();

    ColorGenerator generator = ColorGenerator.MATERIAL;

    ArrayList<HashMap<String, String>> arraylist;

    // Movies json url
    private static final String url = "http://202.152.30.60/octowallet/index.php/api/users/history";
    private ProgressDialog pDialog;
    private List<Event> eventList = new ArrayList<Event>();
    private ListView listView;
    private EventAdapter adapter;
    String d_version = "0.0.0.1";
    private Toolbar toolbar;
    //ArrayList<HashMap<String, String>> historyList;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cid = user.get(SessionManager.KEY_CID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.list);
        adapter = new EventAdapter(this, eventList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        //TextDrawable drawable = TextDrawable.builder().buildRoundRect("A", Color.RED,10);
        //ImageView image = (ImageView) findViewById(R.id.letter);
        //image.setImageDrawable(drawable);

        StringRequest movieReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arraylist = new ArrayList<HashMap<String, String>>();
                //hidePDialog();
                try {

                    JSONObject job = new JSONObject(response);
                    JSONArray data = job.getJSONArray("history");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        Event event = new Event();
                        event.setTitle(obj.getString("merchant_id"));
                        event.setDesc(obj.getString("transaction_date"));
                        event.setDate(obj.getString("total_amount"));
                        event.setThumbnailUrl(obj.getString("merchant_name"));

                        eventList.add(event);

                        HashMap<String, String> map = new HashMap<String, String>();
                        obj = data.getJSONObject(i);
                        // Retrive JSON Objects
                        map.put("name", obj.getString("merchant_name"));
                        map.put("rank", obj.getString("merchant_id"));
                        map.put("country", obj.getString("transaction_date"));
                        map.put("population", obj.getString("total_amount"));
                        arraylist.add(map);



                    }

                    listView.setAdapter(adapter);



                    //hidePDialog();
                }
 //String a= "apa";
   //             String p = a.charAt(0);

                catch (JSONException e) {
                    e.printStackTrace();
                }
                //hidePDialog();
                adapter.notifyDataSetChanged();
                hidePDialog();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("cid", cid);
                Log.d("cidkey", "cidkey" + cid);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(movieReq);
        EventAdapter adapter = new EventAdapter(History.this, eventList);
        listView.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
