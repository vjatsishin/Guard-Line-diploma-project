package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CalculationActivity extends AppCompatActivity {

    private ImageButton calendarBtn;
    private Button calculateConsumptionBtn;
    private EditText  consumptionResult, dateRange, quantityLedBulb, quantityIncandescentBulb, quantityHalogenBulb, quantityFluorescentBulb;
    private CheckBox ledCheckBox, incandescentCheckBox, halogenCheckBox, fluorescentCheckBox;
    private Spinner ledBulbPower, incandescentBulbPower, halogenBulbPower, fluorescentBulbPower;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation_layout);
        initComponents();
    }

    private void initComponents() {
        calendarBtn = findViewById(R.id.calendarBtn);
        calculateConsumptionBtn = findViewById(R.id.calculateConsumptionBtn);
        consumptionResult = findViewById(R.id.consumptionResult);
        dateRange = findViewById(R.id.dateRange);
        quantityLedBulb = findViewById(R.id.quantityLedBulb);
        quantityIncandescentBulb = findViewById(R.id.quantityIncandescentBulb);
        quantityHalogenBulb = findViewById(R.id.quantityHalogenBulb);
        quantityFluorescentBulb = findViewById(R.id.quantityFluorescentBulb);
        ledCheckBox = findViewById(R.id.ledBulb);
        incandescentCheckBox = findViewById(R.id.incandescentBulb);
        halogenCheckBox = findViewById(R.id.halogenBulb);
        fluorescentCheckBox = findViewById(R.id.fluorescentBulb);
        ledBulbPower = findViewById(R.id.ledBulbPower);
        incandescentBulbPower = findViewById(R.id.incandescentBulbPower);
        halogenBulbPower = findViewById(R.id.halogenBulbPower);
        fluorescentBulbPower = findViewById(R.id.fluorescentBulbPower);

        String[] ledBulbAdapterStrings = {"5W", "7W", "9W", "12W", "15W"};
        String[] incandescentBulbAdapterStrings = {"40W", "60W", "75W", "100W"};
        String[] fluorescentBulbAdapterStrings = {"9W", "14W", "19W", "29W"};
        String[] halogenBulbAdapterStrings = {"29W", "43W", "53W", "72W"};

        ArrayAdapter<String> ledAdapter = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item,ledBulbAdapterStrings);
        ArrayAdapter<String> incandescentAdapter = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item,incandescentBulbAdapterStrings);
        ArrayAdapter<String> fluorescentAdapter = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item,fluorescentBulbAdapterStrings);
        ArrayAdapter<String> halogenAdapter = new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item,halogenBulbAdapterStrings);


        ledBulbPower.setAdapter(ledAdapter);
        ledBulbPower.setDropDownHorizontalOffset(400);
        incandescentBulbPower.setAdapter(incandescentAdapter);
        incandescentBulbPower.setDropDownHorizontalOffset(400);
        fluorescentBulbPower.setAdapter(fluorescentAdapter);
        fluorescentBulbPower.setDropDownHorizontalOffset(400);
        halogenBulbPower.setAdapter(halogenAdapter);
        halogenBulbPower.setDropDownHorizontalOffset(400);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
