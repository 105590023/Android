package com.codekong.wuzilianzhu;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.xiaomi.mistatistic.sdk.MiStatInterface;
import com.xiaomi.mistatistic.sdk.URLStatsRecorder;
import com.xiaomi.mistatistic.sdk.controller.HttpEventFilter;
import com.xiaomi.mistatistic.sdk.data.HttpEvent;

public class WuZiQiActivity extends MainActivity implements WuziqiPanel.OnGameOverListener {

    private WuziqiPanel wuziqiPanel;
    private TextView firstPieceTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuziqi);

        initUI();
        initData();
    }

    //初始化
    private void initData() {
        boolean isWhiteFirst = Math.random() > 0.5;
        wuziqiPanel.setFirstPiece(isWhiteFirst);

        if (isWhiteFirst){
            firstPieceTv.setText(getResources().getText(R.string.str_white_piece));
        }else{
            firstPieceTv.setText(getResources().getText(R.string.str_black_piece));
        }
    }

    private void initUI() {
        wuziqiPanel = (WuziqiPanel) findViewById(R.id.id_wuziqi_panel);
        wuziqiPanel.setOnGameOverListener(this);

        firstPieceTv = (TextView) findViewById(R.id.id_first_piece);
    }

    public static class App extends Application {
        @Override
        public void onCreate() {
            super.onCreate();

            Log.d("mitong", "onCreate: " + MiStatInterface.getDeviceID(this));
            MiStatInterface.initialize(this.getApplicationContext(), AppConfig.MI_APP_ID, AppConfig.MI_APP_KEY, AppConfig.APP_CHANNEL);

            MiStatInterface.setUploadPolicy(
                    MiStatInterface.UPLOAD_POLICY_WHILE_INITIALIZE, 0);
            MiStatInterface.enableLog();

            MiStatInterface.enableExceptionCatcher(true);

            URLStatsRecorder.enableAutoRecord();
            URLStatsRecorder.setEventFilter(new HttpEventFilter() {

                @Override
                public HttpEvent onEvent(HttpEvent event) {
                    return event;
                }
            });

            Log.d("MI_STAT", MiStatInterface.getDeviceID(this) + " is the device.");
        }
    }

    public static class AppConfig {
        public static final String MI_APP_ID = "105590023";
        public static final String MI_APP_KEY = "54543967";
        public static final String APP_CHANNEL = "JamesRui";
    }

    @Override
    public void gameOver(boolean isWhiterWinner) {
        if (isWhiterWinner){
            Utils.showGameOverDialog(this, wuziqiPanel, R.string.str_white_piece_victory);
        }else {
            Utils.showGameOverDialog(this, wuziqiPanel, R.string.str_black_piece_victory);
        }
    }

    public static class Utils {

        // 遊戲結束畫面
        public static void showGameOverDialog(Context context, final WuziqiPanel wuziqiPanel, int resId) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.str_game_over);
            builder.setMessage(resId);
            builder.setPositiveButton(R.string.str_restart_game, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    wuziqiPanel.restart();
                }
            });
            builder.create().show();
        }
    }
}
