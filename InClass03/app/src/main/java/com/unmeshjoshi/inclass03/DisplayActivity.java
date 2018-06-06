package com.unmeshjoshi.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    public static final int requestCodeName = 100;
    public static final int requestCodeEmail = 101;
    public static final int requestCodeDept = 102;
    public static final int requestCodeMood = 103;

    public static String REQCODENAME = "REQCODE";
    public static String STUDENTNAME = "STUDENTNAME";
    public static String STUDENTEMAIL = "STUDENTEMAIL";
    public static String STUDENTDEPT = "STUDENTDEPT";
    public static String STUDENTMOOD = "STUDENTMOOD";
    static String VALUE_KEY = "value";
   // static int VALUE_SEEK = 0;

     Student student;
    TextView textViewName,textViewEmail,textViewDepartment,textViewSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        student = getIntent().getExtras().getParcelable(MainActivity.STUDENT_KEY);

         textViewName = (TextView) findViewById(R.id.textViewName);
         textViewEmail = (TextView) findViewById(R.id.textViewEmail);
         textViewDepartment = (TextView) findViewById(R.id.textViewDepartment);
         textViewSeekBar = (TextView) findViewById(R.id.textViewMoode);

        textViewName.setText(student.name);
        textViewEmail.setText( student.email);
        textViewDepartment.setText(student.department);
        textViewSeekBar.setText(student.seekPercentage + "  Positive");

        ImageView imageViewName = findViewById(R.id.imageViewName);
        imageViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra(REQCODENAME,requestCodeName);
                intent.putExtra(STUDENTNAME,student.name);

                startActivityForResult(intent,requestCodeName);

            }
        });

        ImageView imageViewEmail = findViewById(R.id.imageViewEmail);

        imageViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra(REQCODENAME,requestCodeEmail);
                intent.putExtra(STUDENTEMAIL,student.email);
                startActivityForResult(intent,requestCodeEmail);

            }
        });


        ImageView imageViewDept = findViewById(R.id.imageViewDept);

        imageViewDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra(REQCODENAME,requestCodeDept);
                intent.putExtra(STUDENTDEPT,student.deptId);
                startActivityForResult(intent,requestCodeDept);

            }
        });


        ImageView imageViewMood = findViewById(R.id.imageViewMood);

        imageViewMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra(REQCODENAME,requestCodeMood);
                intent.putExtra(STUDENTMOOD,student.seekPercentage);
                startActivityForResult(intent,requestCodeMood);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == requestCodeName){
            if(resultCode == RESULT_OK){
                textViewName.setText(data.getExtras().getString(VALUE_KEY));
                student.name = data.getExtras().getString(VALUE_KEY);
            }else if(resultCode == RESULT_CANCELED){
                Log.d("InClass03","Not correct value");
            }
        } else  if(requestCode == requestCodeEmail){
            if(resultCode == RESULT_OK){
                textViewEmail.setText(data.getExtras().getString(VALUE_KEY));
                student.email = data.getExtras().getString(VALUE_KEY);
            }else if(resultCode == RESULT_CANCELED){
                Log.d("InClass03","Not correct value");
            }
        }else  if(requestCode == requestCodeDept){
            if(resultCode == RESULT_OK){
                String dept = data.getExtras().getString(VALUE_KEY);
                Log.d("Department",dept);
                textViewDepartment.setText(data.getExtras().getString(VALUE_KEY));
                student.department = data.getExtras().getString(VALUE_KEY);
            }else if(resultCode == RESULT_CANCELED){
                Log.d("InClass03","Not correct value");
            }
        }else  if(requestCode == requestCodeMood){
            if(resultCode == RESULT_OK){
                //Log.d("Displayactivity",data.getExtras().getString(VALUE_KEY));
                textViewSeekBar.setText(data.getExtras().getInt(VALUE_KEY)+"  Positive");
                student.seekPercentage = data.getExtras().getInt(VALUE_KEY);
            }else if(resultCode == RESULT_CANCELED){
                Log.d("InClass03","Not correct value");
            }
        }

    }
}
