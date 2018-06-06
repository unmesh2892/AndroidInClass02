package com.unmeshjoshi.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    public static int reqCode = 0;
    public int cas=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView textViewDept = (TextView) findViewById(R.id.textView4);
        final TextView textViewMood = (TextView) findViewById(R.id.textView5);


       reqCode = getIntent().getExtras().getInt(DisplayActivity.REQCODENAME);
        if (reqCode == 100) {
            Log.d("demo", "in name");
            editTextName.setVisibility(View.VISIBLE);
            editTextName.setText(getIntent().getExtras().getString(DisplayActivity.STUDENTNAME));
            cas=1;


        } else if (reqCode == 101) {
            editTextEmail.setVisibility(View.VISIBLE);
            editTextEmail.setText(getIntent().getExtras().getString(DisplayActivity.STUDENTEMAIL));
            cas=2;


        } else if (reqCode == 102) {
            radioGroup.setVisibility(View.VISIBLE);
            textViewDept.setVisibility(View.VISIBLE);
            RadioButton radioButton = findViewById(getIntent().getExtras().getInt(DisplayActivity.STUDENTDEPT));
            radioButton.setChecked(true);
            cas=3;

        } else {
            seekBar.setVisibility(View.VISIBLE);
            textViewMood.setVisibility(View.VISIBLE);
            seekBar.setProgress(getIntent().getExtras().getInt(DisplayActivity.STUDENTMOOD));
            cas=4;

        }

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cas==1){
                    String value = editTextName.getText().toString();
                    if(value == null || value.length()==0){
                        setResult(RESULT_CANCELED);
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra(DisplayActivity.VALUE_KEY,value);
                        setResult(RESULT_OK,intent);
                    }
                    finish();

                }else if(cas == 2){
                    String value = editTextEmail.getText().toString();
                    if(value == null || value.length()==0){
                        setResult(RESULT_CANCELED);
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra(DisplayActivity.VALUE_KEY,value);
                        setResult(RESULT_OK,intent);
                    }
                    finish();
                }else if(cas == 3){

                    RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    int id = radioGroup.getCheckedRadioButtonId();
                    // RadioButton radioButton = findViewById(getIntent().getExtras().getInt(DisplayActivity.STUDENTDEPT));
                    String value = radioButton.getText().toString();
                    Log.d("Editactivity",value);
                    if(value == null || value.length()==0){
                        setResult(RESULT_CANCELED);
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra(DisplayActivity.ID_KEY,id);
                        intent.putExtra(DisplayActivity.VALUE_KEY,value);
                        setResult(RESULT_OK,intent);
                    }
                    finish();
                }else if(cas == 4){
                    int value = seekBar.getProgress();
                    if(value < 0){
                        setResult(RESULT_CANCELED);
                    }else{
                        Intent intent = new Intent();

                        intent.putExtra(DisplayActivity.VALUE_KEY,value);
                        setResult(RESULT_OK,intent);
                    }
                    finish();
                }
            }
        });

    }
}
