package com.example.saurabh.bmicalci_saurabh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    TextView tvResult,tvResult1,tvRange1,tvRange2,tvRange3,tvRange4;
    Button btnBack,btnShare,btnSave;
    SharedPreferences sp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvResult= (TextView) findViewById(R.id.tvResult);
        tvRange3= (TextView) findViewById(R.id.tvRange3);
        tvResult1= (TextView) findViewById(R.id.tvResult1);
        tvRange1= (TextView) findViewById(R.id.tvRange1);
        tvRange2= (TextView) findViewById(R.id.tvRange2);
        tvRange3= (TextView) findViewById(R.id.tvRange3);
        tvRange4= (TextView) findViewById(R.id.tvRange4);
        btnBack= (Button) findViewById(R.id.btnBack);
        btnSave= (Button) findViewById(R.id.btnSave);
        btnShare= (Button) findViewById(R.id.btnShare);
        final MyDbHelper db =new MyDbHelper(this);



        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);

        Intent i=getIntent();
        final String msg=i.getStringExtra("b");
        tvResult.setText("Your Bmi is: "+msg);



        Double bmi2=Double.parseDouble(msg);
        if(bmi2<18.5)
        {
            tvResult1.setText("You are UnderWeight");
            tvRange1.setTextColor(Color.parseColor("#ff0000"));

        }
        else if(bmi2>18.5 && bmi2<25)
        {
            tvResult1.setText("Your weight is Normal");
            tvRange2.setTextColor(Color.parseColor("#ff0000"));
        }
        else if(bmi2>25 && bmi2<30)
        {
            tvResult1.setText("You are OverWeight");
            tvRange3.setTextColor(Color.parseColor("#ff0000"));
        }
        else
        {
            tvResult1.setText("You are Obese");
            tvRange4.setTextColor(Color.parseColor("#ff0000"));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp1=getSharedPreferences("MyP1",MODE_PRIVATE);
                String rn=sp1.getString("n","");
                String ra=sp1.getString("a","");
                Intent i= new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,"Name: "+rn+"\n"+"Age: "+ra+"\n"+"My BMI is"+msg);
                startActivity(i);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.addB(msg);

            }
        });


    }
}
