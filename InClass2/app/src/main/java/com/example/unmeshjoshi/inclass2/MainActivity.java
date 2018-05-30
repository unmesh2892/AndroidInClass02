package com.example.unmeshjoshi.inclass2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("inCreate","start create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final EditText et = (EditText) findViewById(R.id.editText);
        Button btnEuro = (Button) findViewById(R.id.buttonEuro);
        Button btnCan = (Button) findViewById(R.id.buttonCanadian);
        Button btnClear = (Button) findViewById(R.id.buttonClear);
        final TextView tvDisplay = (TextView) findViewById(R.id.textViewDisplay);


        btnEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("in Euro",et.getText().toString());
                if(!et.getText().toString().matches("") && !et.getText().toString().matches(".")) {
                    double numericValue = Double.parseDouble(et.getText().toString());
                    numericValue = numericValue *0.87;
                    String eurString =  String.format("%.2f",numericValue);
                    tvDisplay.setText(eurString + "EUR");
                }else{
                    Toast.makeText(getApplicationContext(), "Enter correct amount in USD",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("in CAD",et.getText().toString());
                if(!et.getText().toString().matches("") && !et.getText().toString().matches(".")) {
                    double numericValue = Double.parseDouble(et.getText().toString());
                    numericValue = numericValue *1.30;
                  String cadString =  String.format("%.2f",numericValue);

                    tvDisplay.setText(cadString + "CAD");
                }else{
                    Toast.makeText(getApplicationContext(), "Enter correct amount in USD",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDisplay.setText("");
                et.setText("");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }
}


