package com.intentcarryingdata;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgRollingDice;
    private Button mBtnRollDice;
    static int diceResult = 0;

    // 新增統計遊戲局數和輸贏的變數
    private int miCountSet = 0,
            miCountPlayerWin = 0,
            miCountComWin = 0,
            miCountDraw = 0;

    private Button mBtnShowResult;

    // Use static Handler to avoid memory leaks.
    private static class StaticHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public StaticHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity == null) return;

            int iRand = (int)(Math.random()*6 + 1);

            switch (iRand) {
                case 1:
                    activity.mImgRollingDice.setImageResource(R.drawable.dice01);
                    diceResult = 1;
                    break;
                case 2:
                    activity.mImgRollingDice.setImageResource(R.drawable.dice02);
                    diceResult = 2;
                    break;
                case 3:
                    activity.mImgRollingDice.setImageResource(R.drawable.dice03);
                    diceResult = 3;
                    break;
                case 4:
                    activity.mImgRollingDice.setImageResource(R.drawable.dice04);
                    diceResult = 4;
                    break;
                case 5:
                    activity.mImgRollingDice.setImageResource(R.drawable.dice05);
                    diceResult = 5;
                    break;
                case 6:
                    activity.mImgRollingDice.setImageResource(R.drawable.dice06);
                    diceResult = 6;
                    break;
            }
        }
    }

    public final StaticHandler mHandler = new StaticHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgRollingDice = (ImageView)findViewById(R.id.imgRollingDice);
        mBtnRollDice = (Button)findViewById(R.id.btnRollDice);

        mBtnShowResult = (Button)findViewById(R.id.btnShowResult);
        mBtnShowResult.setOnClickListener(btnShowResultOnClick);

        mBtnRollDice.setOnClickListener(btnRollDiceOnClick);
    }

    public void diceResult(){
        int iRand = (int)(Math.random()*6 + 1);
        miCountSet++;
        if (iRand == 1){
            mImgRollingDice.setImageResource(R.drawable.dice01);
            miCountPlayerWin ++;
            Toast.makeText(MainActivity.this, R.string.player_win1, Toast.LENGTH_LONG).show();
        }
        else if(iRand == 2){
            mImgRollingDice.setImageResource(R.drawable.dice02);
            miCountPlayerWin ++;
            Toast.makeText(MainActivity.this, R.string.player_win2, Toast.LENGTH_LONG).show();
        }
        else if (iRand == 3){
            mImgRollingDice.setImageResource(R.drawable.dice03);
            miCountDraw ++;
            Toast.makeText(MainActivity.this, R.string.player_draw1, Toast.LENGTH_LONG).show();
        }
        else if (iRand == 4){
            mImgRollingDice.setImageResource(R.drawable.dice04);
            miCountDraw ++;
            Toast.makeText(MainActivity.this, R.string.player_draw2, Toast.LENGTH_LONG).show();
        }
        else if (iRand == 5){
            mImgRollingDice.setImageResource(R.drawable.dice05);
            miCountComWin ++;
            Toast.makeText(MainActivity.this, R.string.player_lose1, Toast.LENGTH_LONG).show();
        }
        else if (iRand == 6){
            mImgRollingDice.setImageResource(R.drawable.dice06);
            miCountComWin ++;
            Toast.makeText(MainActivity.this, R.string.player_lose2, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
        }
    }
    private View.OnClickListener btnRollDiceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 從程式資源中取得動畫檔，設定給ImageView物件，然後開始播放。
            Resources res = getResources();
            final AnimationDrawable animDraw =
                    (AnimationDrawable) res.getDrawable(R.drawable.anim_roll_dice);
            mImgRollingDice.setImageDrawable(animDraw);
            animDraw.start();

            // 計時的另一種作法。
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    animDraw.stop();
                    diceResult();
                    //mHandler.sendMessage(mHandler.obtainMessage());
                }
            }, 2000);
        }
    };

    private View.OnClickListener btnShowResultOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent it = new Intent();
            it.setClass(MainActivity.this, GameResultActivity.class);

            Bundle bundle = new Bundle();
            bundle.putInt("KEY_COUNT_SET", miCountSet);
            bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
            bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
            bundle.putInt("KEY_COUNT_DRAW", miCountDraw);
            it.putExtras(bundle);

            startActivity(it);
        }
    };
}
