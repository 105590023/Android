package com.hw4_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTxtComPlay, mTxtResult;
    private Button mBtnScissors, mBtnStone, mBtnPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtComPlay = (TextView)findViewById(R.id.txtComPlay);
        mTxtResult = (TextView)findViewById(R.id.txtResult);
        mBtnScissors = (Button)findViewById(R.id.btnScissors);
        mBtnStone = (Button)findViewById(R.id.btnStone);
        mBtnPaper = (Button)findViewById(R.id.btnPaper);

        mBtnScissors.setOnClickListener(btnScissorsOnClick);
        mBtnStone.setOnClickListener(btnStoneOnClick);
        mBtnPaper.setOnClickListener(btnPaperOnClick);
    }

    private View.OnClickListener btnScissorsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            handleMora hm = new handleMora();
            switch (hm.handle(1,(int)(Math.random()*3 + 1))) {
                case "draw":
                    mTxtComPlay.setText(R.string.play_scissors);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_draw));
                    break;
                case "lose":
                    mTxtComPlay.setText(R.string.play_stone);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_lose));
                    break;
                case "win":
                    mTxtComPlay.setText(R.string.play_paper);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_win));
                    break;
            }
        }
    };

    private View.OnClickListener btnStoneOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            handleMora hm = new handleMora();
            switch (hm.handle(2,(int)(Math.random()*3 + 1))) {
                case "win":
                    mTxtComPlay.setText(R.string.play_scissors);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_win));
                    break;
                case "draw":
                    mTxtComPlay.setText(R.string.play_stone);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_draw));
                    break;
                case "lose":
                    mTxtComPlay.setText(R.string.play_paper);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_lose));
                    break;
            }
        }
    };

    private View.OnClickListener btnPaperOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            handleMora hm = new handleMora();
            switch (hm.handle(3,(int)(Math.random()*3 + 1))) {
                case "win":
                    mTxtComPlay.setText(R.string.play_scissors);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_lose));
                    break;
                case "draw":
                    mTxtComPlay.setText(R.string.play_stone);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_win));
                    break;
                case "lose":
                    mTxtComPlay.setText(R.string.play_paper);
                    mTxtResult.setText(getString(R.string.result)+getString(R.string.player_draw));
                    break;
            }
        }
    };
}
