package com.example.saurabh.bmicalci_saurabh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;



public class MyDbHelper extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase db;

    MyDbHelper(Context context)
    {
        super(context,"BMI",null,1);
        Log.d("DB456","DB CREATED");
        this.context=context;
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table BMI (bmi)");
         Log.d("DB456","TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void addB(String bmi)
    {

        ContentValues cv =new ContentValues();
        //cv.put("id",id);
        //cv.put("name",name);
        cv.put("bmi",bmi);
        long rid=db.insert("BMI",null,cv);

        if(rid<0)
        {
            Toast.makeText(context, "Insert issue", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();
        }
    }


    public String viewB()
    {
        StringBuffer sb =new StringBuffer();
        Cursor c = db.query("BMI",null,null,null,null,null,null);
        c.moveToFirst();

        if(c.getCount()==0)
        {
            sb.append("no records found");
        }
        else
        {
            do{
                //String id=c.getString(0);
                //String name=c.getString(1);
                String bmi=c.getString(0);
                sb.append( "BMI :"+bmi+"\n");

            }while(c.moveToNext());
        }
        return sb.toString();

    }

    public void updateB(String bmi)
    {
        ContentValues cv =new ContentValues();
        //cv.put("id",id);
        //cv.put("name",name);
        cv.put("bmi",bmi);
        long rid=db.update("BMI",cv,null,null);

        if(rid<0)
        {
            Toast.makeText(context, "Update issue", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, rid+ "Records Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteB(String name)
    {
        long rid = db.delete("BMI",null,null);
        if(rid<0)
        {
            Toast.makeText(context, "Delete issue", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, rid+ "Records deleted", Toast.LENGTH_SHORT).show();
        }

    }




}





