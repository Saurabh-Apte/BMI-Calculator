package com.example.saurabh.bmicalci_saurabh;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BmiActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

    TextView tvWelcome,tvInfo,tvInfo2;
    Spinner spnFeet,spnInch;
    EditText etWeight;
    Button btnCalc,btnView;
    SharedPreferences sp1;
    GoogleApiClient c;
    Location loc;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        GoogleApiClient.Builder builder =new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        c=builder.build();

        tvWelcome= (TextView) findViewById(R.id.tvWelcome);
        tvInfo= (TextView) findViewById(R.id.tvInfo);
        tvInfo2= (TextView) findViewById(R.id.tvInfo2);
        spnFeet= (Spinner) findViewById(R.id.spnFeet);
        spnInch= (Spinner) findViewById(R.id.spnInch);
        btnCalc= (Button) findViewById(R.id.btnCalc);
        btnView= (Button) findViewById(R.id.btnView);
        etWeight= (EditText) findViewById(R.id.etWeight);
        sp1=getSharedPreferences("MyP1",MODE_PRIVATE);
        String rn=sp1.getString("n","");
        tvWelcome.setText("Welcome "+rn);
        final MyDbHelper db =new MyDbHelper(this);

        tts= new TextToSpeech(getApplicationContext(),new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR)
                    tts.setLanguage(Locale.ENGLISH);
            }
        });

        int orientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);

        ArrayList<String> Feet=new ArrayList<String>();
        Feet.add("0"); Feet.add("1"); Feet.add("2");
        Feet.add("3"); Feet.add("4"); Feet.add("5");
        Feet.add("6"); Feet.add("7");

        ArrayList<String> Inch=new ArrayList<String>();
        Inch.add("0"); Inch.add("1"); Inch.add("2"); Inch.add("3");
        Inch.add("4"); Inch.add("5"); Inch.add("6"); Inch.add("7");
        Inch.add("8"); Inch.add("9"); Inch.add("10"); Inch.add("11");
        Inch.add("12");

        ArrayAdapter<String> FeetAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Feet);
        ArrayAdapter<String> InchAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Inch);

        spnFeet.setAdapter(FeetAdapter);
        spnFeet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String F=adapterView.getItemAtPosition(i).toString();
                int Fe=Integer.parseInt(F);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnInch.setAdapter(InchAdapter);
        spnInch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String I=adapterView.getItemAtPosition(i).toString();
                int Inches=Integer.parseInt(I);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String w1=etWeight.getText().toString();

                if(w1.length()==0)
                {
                    etWeight.setError("Enter proper weight");
                    etWeight.requestFocus();
                    return;
                }
                double weight=Double.parseDouble(w1);
                if(weight==0)
                {
                    etWeight.setError("Enter proper weight");
                    etWeight.requestFocus();
                    return;
                }

                int F=spnFeet.getSelectedItemPosition();
                int I=spnInch.getSelectedItemPosition();
                int H=(F*12)+I;
                double Hm= H*0.025;
                double Hmi=Hm*Hm;
                double bmi=weight/Hmi;
                String bmi1= String.valueOf(bmi);

                Toast.makeText(BmiActivity.this, ""+bmi, Toast.LENGTH_SHORT).show();
                String toSpeak="Your BMI is"+bmi1;
                tts.speak(toSpeak,TextToSpeech.QUEUE_ADD,null);

                Intent i=new Intent(BmiActivity.this,ResultActivity.class);
                i.putExtra("b",bmi1);
                startActivity(i);


            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d=db.viewB();
                tvInfo.setText(d);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(c!=null)
        {
            c.connect();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.About)
        {

            Snackbar.make(findViewById(android.R.id.content),"Developed by Saurabh Apte",Snackbar.LENGTH_LONG).show();
        }

        if(item.getItemId() == R.id.Website)
        {

            Intent i1= new Intent(Intent.ACTION_VIEW);
            i1.setData(Uri.parse("https://www.google.com"));
            startActivity(i1);
        }
        return super.onOptionsItemSelected(item);
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

        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.setTitle("Exit");
        alert.show();


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,tvInfo2.getText().toString());
        startActivity(intent);

    }

    @Override
    public void onConnected(Bundle bundle) {
        loc=LocationServices.FusedLocationApi.getLastLocation(c);
        if(loc!=null)
        {
            double lat=loc.getLatitude();
            double log=loc.getLongitude();

            Geocoder geocode = new Geocoder(this, Locale.ENGLISH);

            try
            {
                List<Address> address = geocode.getFromLocation(lat,log,1);
                if(address!=null)
                {
                    Address fetchedAddress=address.get(0);
                    tvInfo2.setText("Location:"+fetchedAddress.getSubLocality());
                }
                else
                    tvInfo2.setText("No Location Found..!");
            }
            catch (IOException e)
            {
                Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();

    }
}
