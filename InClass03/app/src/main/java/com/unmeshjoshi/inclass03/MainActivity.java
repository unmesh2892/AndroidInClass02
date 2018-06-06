package com.unmeshjoshi.inclass03;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  RadioGroup radioGroup;
    static String STUDENT_KEY="STUDENT";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);

         radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = findViewById(checkedId);
            }
        });

        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        Button submitButton = findViewById(R.id.submitButton);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter the Name", Toast.LENGTH_SHORT).show();

                } else if (editTextEmail.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter the Email", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra(STUDENT_KEY, new Student(editTextName.getText().toString(), editTextEmail.getText().toString(), ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString(), seekBar.getProgress(),radioGroup.getCheckedRadioButtonId()));
                    startActivity(intent);
                }
            }
        });
    }


}
