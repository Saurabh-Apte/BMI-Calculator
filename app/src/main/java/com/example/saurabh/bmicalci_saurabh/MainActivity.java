package com.example.saurabh.bmicalci_saurabh;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etName,etAge,etNumber;
    Button btnRegister;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);

        etName= (EditText) findViewById(R.id.etName);
        etAge= (EditText) findViewById(R.id.etAge);
        etNumber= (EditText) findViewById(R.id.etNumber);
        btnRegister= (Button) findViewById(R.id.btnRegister);
        sp1=getSharedPreferences("MyP1",MODE_PRIVATE);
        String rn=sp1.getString("n","");

     if(rn.length()==0) {
         btnRegister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String name = etName.getText().toString();
                 String age = etAge.getText().toString();
                 String phoneNo = etNumber.getText().toString();

                 if (name.length() == 0) {
                     etName.setError("Enter Name");
                     etName.requestFocus();
                     return;
                 }

                 if (age.length() == 0 || age.equals("0")) {
                     etAge.setError("Enter Age");
                     etAge.requestFocus();
                     return;
                 }
                 if (phoneNo.length() < 10) {
                     etNumber.setError("Enter a Valid Number");
                     etNumber.requestFocus();
                     return;
                 }

                 SharedPreferences.Editor editor = sp1.edit();
                 editor.putString("n", name);
                 editor.putString("a", age);
                 editor.putString("p", phoneNo);
                 editor.commit();
                 Intent intent=new Intent(MainActivity.this,BmiActivity.class);
                 startActivity(intent);


             }

         });
     }
         else
         {
             Intent intent=new Intent(MainActivity.this,BmiActivity.class);
             startActivity(intent);
              finish();
         }
     }

   @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close the Application ?");
        builder.setCancelable(false);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.setTitle("Exit");
        alert.show();
    }
}

