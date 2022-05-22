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

public class MyView extends View {
    //影像
    private Bitmap ball;
    private Resources resources; //所在的context才會知道resource -->指的是activity
    private Boolean isinit = false;
    private int ViewW, ViewH;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        resources = context.getResources();
        //做出bitmap ->解讀影像
        ball = BitmapFactory.decodeResource(resources, R.drawable.ball);
    }

    private void  init(){
        ViewW = getWidth(); ViewH = getHeight();
        float ballW = ViewW/4f; //8f = 8.0
        float ballH = ballW;
        //圖片大小調整(Matrix -> graphics)
        Matrix matrix = new Matrix();
        matrix.postScale(ballW/ball.getWidth(), ballH/getHeight());//調整大小
        //拿到調整之後的球(等比率縮放的範圍 - >0,0,ball.getWidth(),ball.getHeight() )
        ball = Bitmap.createBitmap(ball, 0,0,ball.getWidth(),ball.getHeight(),matrix, false);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!isinit){
            init();
        }
        //畫出這顆球，處理透明度才需要paint
        canvas.drawBitmap(ball, 100, 100, null);
    }
}
