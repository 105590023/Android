package com.galleryusingviewanimation;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory {

    Animation[] myAnimationList;
    private GridView mGridView;
    private ImageSwitcher mImgSwitcher;

    private Integer[] miImgArr = {
            R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04,
            R.drawable.img05, R.drawable.img06, R.drawable.img07, R.drawable.img08,
            R.drawable.img09, R.drawable.img10, R.drawable.img11, R.drawable.img12};

    private Integer[] miThumbImgArr = {
            R.drawable.img01th, R.drawable.img02th, R.drawable.img03th, R.drawable.img04th,
            R.drawable.img05th, R.drawable.img06th, R.drawable.img07th, R.drawable.img08th,
            R.drawable.img09th, R.drawable.img10th, R.drawable.img11th, R.drawable.img12th};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgSwitcher = (ImageSwitcher) findViewById(R.id.imgSwitcher);

        mImgSwitcher.setFactory(this);	// 主程式類別必須implements ViewSwitcher.ViewFactory
        mImgSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        mImgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));

        ImageAdapter imgAdap = new ImageAdapter(this, miThumbImgArr);

        mGridView = (GridView)findViewById(R.id.gridView);
        mGridView.setAdapter(imgAdap);
        myAnimation();
        mGridView.setOnItemClickListener(gridViewOnItemClick);
    }

    @Override
    public View makeView() {
        ImageView v = new ImageView(this);
        v.setBackgroundColor(0xFF000000);
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);
        v.setLayoutParams(new ImageSwitcher.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        v.setBackgroundColor(Color.WHITE);
        return v;
    }


    private AdapterView.OnItemClickListener gridViewOnItemClick = new
            AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View v,
                                        int position,
                                        long id) {

                    switch ((int)(Math.random()*4)) {
                        case 1:
                            mImgSwitcher.setInAnimation(myAnimationList[0]);
                            mImgSwitcher.setOutAnimation(myAnimationList[1]);
                            break;
                        case 2:
                            mImgSwitcher.setInAnimation(myAnimationList[2]);
                            mImgSwitcher.setOutAnimation(myAnimationList[3]);
                            break;
                        case 3:
                            mImgSwitcher.setInAnimation(myAnimationList[4]);
                            mImgSwitcher.setOutAnimation(myAnimationList[5]);
                            break;
                        case 0:
                            mImgSwitcher.setInAnimation(myAnimationList[6]);
                            mImgSwitcher.setOutAnimation(myAnimationList[7]);
                            break;
                    }

                    mImgSwitcher.setImageResource(miImgArr[position]);
                }
            };
    private void myAnimation(){
        ScaleAnimation myScale;
        RotateAnimation myRota;
        TranslateAnimation myTran;

        //AlphaAnimation_in----------------------------------------------------------------------------------------------------------
        AlphaAnimation alpha_in = new AlphaAnimation(0, 1);
        alpha_in.setStartOffset(1000);
        alpha_in.setDuration(3000);
        alpha_in.setInterpolator(new LinearInterpolator());


        //AlphaAnimation_out---------------------------------------------------------------------------------------------------------
        AlphaAnimation alpha_out = new AlphaAnimation(0, 1);
        alpha_out.setStartOffset(1000);
        alpha_out.setDuration(3000);
        alpha_out.setInterpolator(new LinearInterpolator());

        //Scale_RotateAnimation_in-----------------------------------------------------------------------------------------------------
        AnimationSet scale_rotate_in = new AnimationSet(false);

        myScale = new ScaleAnimation(0, 1, 0, 1);
        myScale.setInterpolator(new LinearInterpolator());
        myScale.setStartOffset(1000);
        myScale.setDuration(3000);

        myRota = new RotateAnimation(0, 720, 10.0f, 10.0f);
        myRota.setInterpolator(new AccelerateDecelerateInterpolator());
        myRota.setStartOffset(1000);
        myRota.setDuration(3000);

        scale_rotate_in.addAnimation(myScale);
        scale_rotate_in.addAnimation(myRota);

        //Scale_RotateAnimation_out-----------------------------------------------------------------------------------------------------
        AnimationSet scale_rotate_out = new AnimationSet(false);

        myScale = new ScaleAnimation(1, 0, 1, 0);
        myScale.setInterpolator(new LinearInterpolator());
        myScale.setStartOffset(1000);
        myScale.setDuration(3000);

        myRota = new RotateAnimation(0, 720, 90.0f, 90.0f);
        myRota.setInterpolator(new AccelerateDecelerateInterpolator());
        myRota.setStartOffset(1000);
        myRota.setDuration(3000);

        scale_rotate_out.addAnimation(myScale);
        scale_rotate_out.addAnimation(myRota);

        //TransAnimation_in-------------------------------------------------------------------------------------------------------------
        AnimationSet tran_in = new AnimationSet(false);

        myTran = new TranslateAnimation(1000, 0, -500, 0);
        myTran.setInterpolator(new LinearInterpolator());
        myTran.setDuration(3000);

        tran_in.addAnimation(myTran);

        //TransAnimation_out---------------------------------------------------------------------------------------------------------------
        AnimationSet tran_out = new AnimationSet(false);

        myTran = new TranslateAnimation(0, 1000, 0, -500);
        myTran.setInterpolator(new LinearInterpolator());
        myTran.setDuration(3000);

        tran_out.addAnimation(myTran);
        //Scale_Rotate_TransAnimation_in-----------------------------------------------------------------------------------------------------
        AnimationSet scale_rota_tran_in = new AnimationSet(false);

        myScale = new ScaleAnimation(0, 1, 0, 1);
        myScale.setInterpolator(new LinearInterpolator());
        myScale.setStartOffset(1000);
        myScale.setDuration(3000);

        myRota = new RotateAnimation(0, 720, 10.0f, 10.0f);
        myRota.setInterpolator(new AccelerateDecelerateInterpolator());
        myRota.setStartOffset(1000);
        myRota.setDuration(3000);

        myTran = new TranslateAnimation(0, 1000, 0, -500);
        myTran.setInterpolator(new LinearInterpolator());
        myTran.setDuration(3000);

        scale_rota_tran_in.addAnimation(myTran);
        scale_rota_tran_in.addAnimation(myRota);
        scale_rota_tran_in.addAnimation(myScale);

        //Scale_Rotate_TransAnimation_out-----------------------------------------------------------------------------------------------------
        AnimationSet scale_rota_tran_out = new AnimationSet(false);

        myScale = new ScaleAnimation(1, 0, 1, 0);
        myScale.setInterpolator(new LinearInterpolator());
        myScale.setStartOffset(1000);
        myScale.setDuration(3000);

        myRota = new RotateAnimation(0, 720, 90.0f, 90.0f);
        myRota.setInterpolator(new AccelerateDecelerateInterpolator());
        myRota.setStartOffset(1000);
        myRota.setDuration(3000);

        myTran = new TranslateAnimation(1000, 0, -500, 0);
        myTran.setInterpolator(new LinearInterpolator());
        myTran.setDuration(3000);

        scale_rota_tran_out.addAnimation(myTran);
        scale_rota_tran_out.addAnimation(myRota);
        scale_rota_tran_out.addAnimation(myScale);

        myAnimationList = new Animation[]{
                    alpha_in,
                    alpha_out,
                    scale_rotate_in,
                    scale_rotate_out,
                    tran_in,
                    tran_out,
                    scale_rota_tran_in,
                    scale_rota_tran_out
        };
    }
}
