package com.example.luthf.projectqroctowallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;


public class Sale extends AppCompatActivity {
    //private EditText amount;
    private static String amount;
    EditText sale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        Intent intent = getIntent();
        final String cid = intent.getStringExtra("cid");
        Log.d("cidmain2", "cidmain2" + cid);

        sale = (EditText)findViewById (R.id.amount);
        sale.addTextChangedListener(new NumberTextWatcher(sale));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //if

        findViewById(R.id.scanqr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=sale.getText().toString();


                if (s.contains(",")) {
                    amount = s.replaceAll(",", "");
                } else {
                    amount = s;
                }


                if (amount.trim().length() > 0 && amount.trim().length() < 10) {

                    String a = amount.charAt(0) + "";

                    if (a.equals("0")) {
                        Toast.makeText(getApplicationContext(), "Your amount must have value", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(Sale.this, BarcodeScanner.class);
                        intent.putExtra("sale", amount);
                        Log.d("sale", "sale" + amount);
                        intent.putExtra("cid", cid);
                        startActivity(intent);
                        finish();
                    }
                }
                else if(amount.trim().length() > 9) {
                    Toast.makeText(getApplicationContext(),
                            "Your amount reach the limit", Toast.LENGTH_LONG)
                            .show();


                } else{
                    Toast.makeText(getApplicationContext(),
                            "Please enter your amount", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
