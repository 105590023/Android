package com.datepickerandtimepickerdialog;

//import android.app.Activity;
import android.app.AlertDialog;
//import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private CalendarView cleanView;
    private Intent intent;
    private int counts;
    private Button btnInput, btnRecord;

    // Context Menu
    private LinearLayout mScreen;

    // 選單
    private static final int MENU_MUSIC = Menu.FIRST,
                            MENU_PLAY_MUSIC = Menu.FIRST + 1,
                            MENU_STOP_MUSIC = Menu.FIRST + 2,
                            MENU_ABOUT = Menu.FIRST + 3,
                            MENU_EXIT = Menu.FIRST + 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerItem = (Spinner) findViewById(R.id.spinnerItem);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextCost = (EditText) findViewById(R.id.editTextCost);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        cleanView = (CalendarView) findViewById(R.id.calendarView2);
        btnInput = (Button) findViewById(R.id.btnInput);
        btnRecord = (Button) findViewById(R.id.btnRecord);

        mScreen = (LinearLayout) findViewById(R.id.myScreen);
        registerForContextMenu(mScreen);

        btnInput.setOnClickListener(onBtnClick);
        btnRecord.setOnClickListener(onBtnClick);
        cleanView.setOnDateChangeListener(onDateChangeListener);

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

    // 建立長按螢幕選單
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v == mScreen){
            if (menu.size() == 0){
                SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "背景音樂");
                subMenu.add(0, MENU_PLAY_MUSIC, 0, "播放背景音樂");
                subMenu.add(0, MENU_STOP_MUSIC, 1, "停止播放背景音樂");
                menu.add(0, MENU_ABOUT, 1, "關於這個程式...");
                menu.add(0, MENU_EXIT, 2, "結束");
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        onOptionsItemSelected(item);
        return super.onContextItemSelected(item);
    }

    // 建立右上方選單
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "背景音樂").setIcon(android.R.drawable.ic_media_ff);
        subMenu.add(0, MENU_PLAY_MUSIC, 0, "播放背景音樂");
        subMenu.add(0, MENU_STOP_MUSIC, 1, "停止播放背景音樂");
        menu.add(0, MENU_ABOUT, 1, "關於這個程式...");
        menu.add(0, MENU_EXIT, 2, "結束").setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        return true;
    }

    // 選單點擊事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent it;
        switch (item.getItemId()){
            case MENU_PLAY_MUSIC:
                it = new Intent(MainActivity.this, MediaPlayService.class);
                startService(it);
                Toast.makeText(MainActivity.this, "開啟音樂", Toast.LENGTH_SHORT).show();
                return  true;

            case MENU_STOP_MUSIC:
                it = new Intent(MainActivity.this, MediaPlayService.class);
                stopService(it);
                return  true;

            case MENU_ABOUT:
                new AlertDialog.Builder(MainActivity.this).setTitle("關於這個程式")
                                                                    .setMessage("選單範例程式")
                                                                    .setCancelable(false)
                                                                    .setIcon(android.R.drawable.star_big_on)
                                                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which){
                                                                        }
                                                                    }).show();
                return true;

            case MENU_EXIT:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            cleanView.setDate(calendar.getTimeInMillis());
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
