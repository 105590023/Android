package com.datepickerandtimepickerdialog;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> data;
    private ArrayAdapter<CharSequence> arrayAdapter;

    private Spinner spinnerItem;
    private EditText editTextDate, editTextCost;
    private DatePicker datePicker;
    private CalendarView calenView;
    private Intent intent;
    private int counts;
    private Button btnInput, btnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerItem = (Spinner) findViewById(R.id.spinnerItem);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextCost = (EditText) findViewById(R.id.editTextCost);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        calenView = (CalendarView) findViewById(R.id.calendarView2);
        btnInput = (Button) findViewById(R.id.btnInput);
        btnRecord = (Button) findViewById(R.id.btnRecord);

        btnInput.setOnClickListener(onBtnClick);
        btnRecord.setOnClickListener(onBtnClick);
        calenView.setOnDateChangeListener(onDateChangeListener);

        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this.onDpkchange);

        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.meal,android.R.layout.simple_spinner_dropdown_item);
        spinnerItem.setAdapter(arrayAdapter);
        String result = String.valueOf(datePicker.getYear()) + "/"  + String.valueOf(datePicker.getMonth() + 1) + "/" + String.valueOf(datePicker.getDayOfMonth());
        editTextDate.setText(result);
        data = new ArrayList();
        counts = 0;
        intent = new Intent();
        intent.setClass(MainActivity.this,RecordActivity.class);
    }

    public View.OnClickListener onBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnInput){
                String cost = editTextCost.getText().toString();
                String item = spinnerItem.getSelectedItem().toString();
                String date = editTextDate.getText().toString();

                String result = "項目" + String.valueOf(counts++) + "  " + date + "  " +item + "  " + cost;
                data.add(result);
                Toast.makeText(MainActivity.this, cost, Toast.LENGTH_SHORT).show();
            }
            else if (v.getId() == R.id.btnRecord){
                intent.putStringArrayListExtra("data",data);
                startActivity(intent);
            }
        }
    };

    public DatePicker.OnDateChangedListener onDpkchange = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String result = String.valueOf(year) + "/"  + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,monthOfYear,dayOfMonth);
            calenView.setDate(calendar.getTimeInMillis());
            editTextDate.setText(result);
        }
    };

    public CalendarView.OnDateChangeListener onDateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            String result = String.valueOf(year) + "/"  + String.valueOf(month + 1) + "/" + String.valueOf(dayOfMonth);
            editTextDate.setText(result);
            datePicker.updateDate(year,month,dayOfMonth);
        }
    };
}
