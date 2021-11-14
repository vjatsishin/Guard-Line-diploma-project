package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import static android.view.View.*;


public class SecondActivity extends AppCompatActivity {

    Button btnTemp1, btnTemp2, btnHum1, btnHum2, btnSignout;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference T1 = db.getReference("Users");


    public void onClickTemp(View view) {
        Intent intent = new Intent(SecondActivity.this, readTemp.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnTemp1 = findViewById(R.id.btnTemp1);
        btnTemp2 = findViewById(R.id.btnTemp2);
        btnHum1 = findViewById(R.id.btnHum1);
        btnHum2 = findViewById(R.id.btnHum2);
        btnSignout = findViewById(R.id.btnSignout);
        db= FirebaseDatabase.getInstance();
        T1 = db.getReference("Users");

    }

   private void showTemperature() {
       AlertDialog.Builder temp1 = new AlertDialog.Builder(this);
       temp1.setTitle("Temperature in 1st room");

       LayoutInflater Temp1 = LayoutInflater.from(this);
       View temperature = Temp1.inflate(R.layout.temperature, null);
       temp1.setView(temperature);



       temp1.setNegativeButton("Close", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int which) {
               dialogInterface.dismiss();
           }
       });
temp1.show();

   }

}