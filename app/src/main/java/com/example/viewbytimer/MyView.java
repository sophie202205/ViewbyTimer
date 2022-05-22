package com.example.viewbytimer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {
    //影像
    private Bitmap ball;
    private Resources resources; //所在的context才會知道resource -->指的是activity
    private Boolean isinit = false;
    private int ViewW, ViewH;
    private float ballW, ballH, moveW, moveH, ballx, bally;
    private Timer timer;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        resources = context.getResources();
        //做出bitmap ->解讀影像
        ball = BitmapFactory.decodeResource(resources, R.drawable.ball);
        timer = new Timer();
    }
    //移動的精確度 視覺的精確度
    private void  init(){
        ViewW = getWidth(); ViewH = getHeight();
        ballW = ViewW/4f; //8f = 8.0
        ballH = ballW;
        //圖片大小調整(Matrix -> graphics)
        Matrix matrix = new Matrix();
        matrix.postScale(ballW/ball.getWidth(), ballH/getHeight());//調整大小
        //拿到調整之後的球(等比率縮放的範圍 - >0,0,ball.getWidth(),ball.getHeight() )
        ball = Bitmap.createBitmap(ball, 0,0,ball.getWidth(),ball.getHeight(),matrix, false);

        //移動的精確度
        moveH = 14; moveW = 14;

        //視覺的精確度
        timer.schedule(new ballTask(), 1000, 80);
        isinit = true;

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!isinit){
            init();
        }
        //畫出這顆球，處理透明度才需要paint
        canvas.drawBitmap(ball, ballx, bally, null);
    }

    private class ballTask extends TimerTask{
        @Override
        public void run() {
            if(ballx < 0 || ballx + ballW > ViewW) {
                moveW *= -1;
            }else if(bally < 0 || bally + ballH > ViewH) {
                moveH *= -1;
            }
            ballx += moveW;
            bally += moveH;

            // timer的repaint
            postInvalidate();
        }
    }
}
