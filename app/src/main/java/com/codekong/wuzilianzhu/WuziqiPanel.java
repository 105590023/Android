package com.codekong.wuzilianzhu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class WuziqiPanel extends View{
    //棋盤寬度
    private int mPanelWidth;
    //棋盤每格行高
    private float mLineHeight;
    //最大行數
    private int MAX_LINE_NUM = 10;

    private Paint mPaint = new Paint();

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    private float pieceScaleRatio = 3 * 1.0f / 4;

    //棋子座標
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();

    private boolean isWhiteFirst = true;

    //遊戲是否結束
    private boolean isGameOver;
    //赢家
    private boolean isWhiteWinner = false;
    private OnGameOverListener onGameOverListener;

    public WuziqiPanel(Context context) {
        this(context, null);
    }

    public WuziqiPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WuziqiPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化方法
    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        //初始化棋子
        mWhitePiece = BitmapFactory.decodeResource(getResources(), com.codekong.wuzilianzhu.R.drawable.icon_white_piece);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), com.codekong.wuzilianzhu.R.drawable.icon_black_piece);
    }

    // 測量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED){
            width = heightSize;
        }else if (heightMode == MeasureSpec.UNSPECIFIED){
            width = widthSize;
        }
        Log.d("pyh", "onMeasure: width" + width + "height" + heightSize);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("pyh", "onSizeChanged: w" + w + "h" + h);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE_NUM;

        //棋子根據行高變化
        int pieceWidth = (int) (pieceScaleRatio * mLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
    }

    //繪圖
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBoard(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    // 棋盤繪製
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0; i < MAX_LINE_NUM; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            int y = (int) ((0.5 + i) * lineHeight);
            //橫線
            canvas.drawLine(startX, y, endX, y, mPaint);
            //直線
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    // 畫棋子
    private void drawPieces(Canvas canvas) {
        //白子
        for (int i = 0, n = mWhiteArray.size(); i < n; i++) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x + (1 - pieceScaleRatio) / 2) * mLineHeight,
                    (whitePoint.y + (1 - pieceScaleRatio) / 2) * mLineHeight, null);
        }
        //黑子
        for (int i = 0, n = mBlackArray.size(); i < n; i++) {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (blackPoint.x + (1 - pieceScaleRatio) / 2) * mLineHeight,
                    (blackPoint.y + (1 - pieceScaleRatio) / 2) * mLineHeight, null);
        }
    }

   // 手指觸發
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isGameOver) return false;

        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP){

            int x = (int) event.getX();
            int y = (int) event.getY();
            Log.d("pyh1", "onTouchEvent: fx" + event.getX() + "fy" + event.getY() + "ix" + x + "iy" + y);
            Point point = getValidPoint(x, y);
            if (mWhiteArray.contains(point) || mBlackArray.contains(point)){
                return false;
            }
            if (isWhiteFirst){
                mWhiteArray.add(point);
            }else{
                mBlackArray.add(point);
            }
            invalidate();
            isWhiteFirst = !isWhiteFirst;
        }
        return true;
    }

    // 轉換座標
    private Point getValidPoint(int x, int y) {
        Log.d("pyh1", "getValidPoint: lx" + (int) (x / mLineHeight) + "ly" + (int) (y / mLineHeight));
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    private void checkGameOver() {
        //是否連線
        boolean whiteWin = WuziqiUtil.checkFiveInLine(mWhiteArray);
        boolean blackWin = WuziqiUtil.checkFiveInLine(mBlackArray);
        if (whiteWin || blackWin){
            isGameOver = true;
            isWhiteWinner = whiteWin;
            onGameOverListener.gameOver(isWhiteWinner);
        }
    }

    //再來一次
    public void restart(){
        mBlackArray.clear();
        mWhiteArray.clear();
        isGameOver = false;
        isWhiteWinner = false;
        invalidate();
    }

    public void setOnGameOverListener(OnGameOverListener onGameOverListener){
        this.onGameOverListener = onGameOverListener;
    }

    public interface OnGameOverListener{
       void gameOver(boolean isWhiterWinner);
    }

    public void setFirstPiece(boolean isWhiteFirst){
        this.isWhiteFirst = isWhiteFirst;
    }
}
