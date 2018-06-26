package com.codekong.wuzilianzhu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends MainActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initUI();
    }

    //初始化
    private void initUI() {
        Button startGameBtn = (Button) findViewById(R.id.id_start_game);
        startGameBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_start_game:
                startGame();
                break;
        }
    }

   //開始遊戲
    private void startGame() {
        Intent startGameIntent = new Intent(this, WuZiQiActivity.class);
        startActivity(startGameIntent);
    }
}
