package com.example.task_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.wang.avi.*;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btn1,btn2,prgs;
    private DatePickerDialog.OnDateSetListener date;
    private Button displayDate;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
        avi.hide();
        btn1=(Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Dialog Box","This is a dialog box");
            }
        });
        btn2=(Button)findViewById(R.id.snack);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBar();
            }
        });
        displayDate=(Button) findViewById(R.id.datepickbtn);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog,date,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month+=1;
                String daate=day+"/"+month+"/"+year;
                displayDate.setText(daate);
            }
        };
        prgs=(Button)findViewById(R.id.progess);
        prgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avi.show();
                new CountDownTimer(5000,5000){
                    @Override
                    public void onTick(long mill){

                    }
                    public void onFinish() {
                        avi.hide();
                    }
                }.start();
            }
        });
    }
    private void cancelMthd(){
        Log.d(TAG, "cancelMthd: Called");
        toastMessage("Cancelled...");
    }
    private void okMthd(){
        Log.d(TAG, "okMthd: Called");
        toastMessage("Clicked OK");
    }
    public void customDialog(String title,String msg){
        AlertDialog.Builder bld=new AlertDialog.Builder(this,R.style.AlertDialogTheme);
        bld.setIcon(R.drawable.ic_stat_name);
        bld.setTitle(title);
        bld.setMessage(msg);
        bld.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: Cancel Called.");
                cancelMthd();
            }
        }
        );
        bld.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: OK Called.");
                        okMthd();
                    }
                }
        );
        AlertDialog alll=bld.create();
        alll.show();
    }
    public void toastMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    public void showSnackBar(){
        Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"This is a snackbar",Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Clicked OK");
            }
        });
        snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
        snackbar.show();
    }
}