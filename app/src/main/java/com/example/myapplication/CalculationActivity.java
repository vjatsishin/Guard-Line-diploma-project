package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalculationActivity extends AppCompatActivity {

    private ImageButton calendarBtn;
    private Button calculateConsumptionBtn, calculateSavings;
    private EditText  consumptionResult, dateRange, quantityLedBulb, quantityIncandescentBulb, quantityHalogenBulb, quantityFluorescentBulb, savingResult;
    private CheckBox ledCheckBox, incandescentCheckBox, halogenCheckBox, fluorescentCheckBox;
    private Spinner ledBulbPower, incandescentBulbPower, halogenBulbPower, fluorescentBulbPower;
    private String rs, rsSavings;
    private SeekBar ledDimmer, incandescentDimmer, halogenDimmer, fluorescentDimmer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation_layout);
        initComponents();
    }

    private void initComponents() {
        // initializing all fields in layout
        calendarBtn = findViewById(R.id.calendarBtn);
        calculateConsumptionBtn = findViewById(R.id.calculateConsumptionBtn);
        consumptionResult = findViewById(R.id.consumptionResult);
        savingResult = findViewById(R.id.savingResult);
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
        ledDimmer = findViewById(R.id.ledDimmer);
        incandescentDimmer = findViewById(R.id.incandescentDimmer);
        halogenDimmer = findViewById(R.id.halogenDimmer);
        fluorescentDimmer = findViewById(R.id.fluorescentDimmer);
        calculateSavings = findViewById(R.id.calculateSavings);

        // adding spinner data for selection
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

        ledBulbPower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarAndSelectDate();
            }
        });

        calculateConsumptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject json = prepareDataBeforeSending();

                ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator(json);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rs = consumptionCalculator.calculate();
                        consumptionResult.setText(rs + " kW-hour");
                    }
                }).start();
            }
        });

        calculateSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = prepareDataBeforeSending();

                ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator(jsonObject);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rsSavings = consumptionCalculator.calculate();
                        rsSavings = String.valueOf(Double.parseDouble(rs)- Double.parseDouble(rsSavings));

                        double percentOfSave = (Double.parseDouble(rsSavings)/Double.parseDouble(rs));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                savingResult.setText("Your savings around " + rsSavings + ", which is " + percentOfSave * (double) 100 + "%");
                            }
                        });

                    }
                }).start();

            }
        });

        ledDimmer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(CalculationActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });

        incandescentDimmer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(CalculationActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });

        halogenDimmer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(CalculationActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });

        fluorescentDimmer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(CalculationActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private JSONObject prepareDataBeforeSending() {
        ArrayList<String> listOfBulbs = new ArrayList<>();
        if (!checkLedParams()) {
            System.out.println("there is no led lamp");
        } else {
            listOfBulbs.add("led");
        }
        if (!checkIncandescentParams()){
            System.out.println("there is no led lamp");
        } else {
            listOfBulbs.add("incandescent");
        }
        if (!checkFluorescentParams()) {
            System.out.println("there is no led lamp");
        } else {
            listOfBulbs.add("fluorescent");
        }
        if (!halogenLedParams()) {
            System.out.println("there is no led lamp");
        } else {
            listOfBulbs.add("halogen");
        }
        JSONObject json = new JSONObject();
        try {
            json.put("months", dateRange.getText().toString().replaceAll("/", "."));
            json.put("nameDev", "calculation");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (String type : listOfBulbs) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", type);
                switch (type) {
                    case "led": {
                        jsonObject.put("quantity", quantityLedBulb.getText());
                        if (ledDimmer.getProgress() == 100) {
                            jsonObject.put("power", String.valueOf(Double.parseDouble(ledBulbPower.getSelectedItem().toString().replace("W", ""))));
                        } else {
                            double power = Double.parseDouble(ledBulbPower.getSelectedItem().toString().replace("W", ""));
                            double savePower = power * (double) ledDimmer.getProgress() / 100.0;
                            System.out.println("savingPower: " + savePower);
                            jsonObject.put("power", String.valueOf(savePower));
                        }

                        json.put("led", jsonObject);
                        break;
                    }
                    case "incandescent": {
                        jsonObject.put("quantity", quantityIncandescentBulb.getText());
                        if (incandescentDimmer.getProgress() == 100) {
                            jsonObject.put("power", String.valueOf(Double.parseDouble(incandescentBulbPower.getSelectedItem().toString().replace("W", ""))));
                        } else {
                            double power = Double.parseDouble(incandescentBulbPower.getSelectedItem().toString().replace("W", ""));
                            double savePower = power * (double) incandescentDimmer.getProgress() / 100.0;
                            System.out.println("savingPower: " + savePower);
                            jsonObject.put("power", String.valueOf(savePower));
                        }
                        json.put("incandescent", jsonObject);
                        break;
                    }
                    case "fluorescent": {
                        jsonObject.put("quantity", quantityFluorescentBulb.getText());
                        if (fluorescentDimmer.getProgress() == 100) {
                            jsonObject.put("power", String.valueOf(Double.parseDouble(fluorescentBulbPower.getSelectedItem().toString().replace("W", ""))));
                        } else {
                            double power = Double.parseDouble(fluorescentBulbPower.getSelectedItem().toString().replace("W", ""));
                            double savePower = power * (double) fluorescentDimmer.getProgress() / 100.0;
                            System.out.println("savingPower: " + savePower);
                            jsonObject.put("power", String.valueOf(savePower));
                        }
                        json.put("fluorescent", jsonObject);
                        break;
                    }
                    case "halogen": {
                        jsonObject.put("quantity", quantityHalogenBulb.getText());
                        if (halogenDimmer.getProgress() == 100) {
                            jsonObject.put("power", String.valueOf(Double.parseDouble(halogenBulbPower.getSelectedItem().toString().replace("W", ""))));
                        } else {
                            double power = Double.parseDouble(halogenBulbPower.getSelectedItem().toString().replace("W", ""));
                            double savePower = power * (double) halogenDimmer.getProgress() / 100.0;
                            System.out.println("savingPower: " + savePower);
                            jsonObject.put("power", String.valueOf(savePower));
                        }
                        json.put("halogen", jsonObject);
                        break;
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return json;
    }

    private boolean halogenLedParams() {
        if (halogenCheckBox.isChecked() && !quantityHalogenBulb.getText().equals("") && !halogenBulbPower.getSelectedItem().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkFluorescentParams() {
        if (fluorescentCheckBox.isChecked() && !quantityFluorescentBulb.getText().equals("") && !fluorescentBulbPower.getSelectedItem().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIncandescentParams() {
        if (incandescentCheckBox.isChecked() && !quantityIncandescentBulb.getText().equals("") && !incandescentBulbPower.getSelectedItem().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkLedParams() {
        if (ledCheckBox.isChecked() && !quantityLedBulb.getText().equals("") && !ledBulbPower.getSelectedItem().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void openCalendarAndSelectDate() {
        // Creating a MaterialDatePicker builder for selecting a date range
        MaterialDatePicker.Builder<androidx.core.util.Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

        // Building the date picker dialog
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            // Retrieving the selected start and end dates
            Long startDate = selection.first;
            Long endDate = selection.second;

            // Formating the selected dates as strings
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            String startDateString = sdf.format(new Date(startDate));
            String endDateString = sdf.format(new Date(endDate));

            // Creating the date range string
            String selectedDateRange = startDateString + "-" + endDateString;

            // Displaying the selected date range in the TextView
            dateRange.setText(selectedDateRange);
        });

        // Showing the date picker dialog
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
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
