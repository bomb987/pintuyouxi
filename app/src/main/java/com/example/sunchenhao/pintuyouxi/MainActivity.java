package com.example.sunchenhao.pintuyouxi;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 孙晨昊 2015/9/25
 */
public class MainActivity extends AppCompatActivity {
    private int[] imageId = new int[]{R.drawable.img01,R.drawable.img02,R.drawable.img03,R.drawable.img04,R.drawable.img05,R.drawable.img06,R.drawable.img07,R.drawable.img08,R.drawable.img09};
    private ImageView[] imageViews;
    private int[] img_src={0,1,2,3,4,5,6,7,8};
    private int k, index, step;
    private float dx=0, dy=0,ux=0,uy=0, xx=0, yy=0;
    private TextView tv;
    private boolean isOver = false, isBegin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Chronometer ch =(Chronometer)findViewById(R.id.chronometer);
        ch.setBase(SystemClock.elapsedRealtime());
        ch.setFormat("已用时间：%s");
        tv = (TextView)findViewById(R.id.tv1);
        imageViews= new ImageView[]{(ImageView) findViewById(R.id.iv1), (ImageView) findViewById(R.id.iv2), (ImageView) findViewById(R.id.iv3), (ImageView) findViewById(R.id.iv4), (ImageView) findViewById(R.id.iv5), (ImageView) findViewById(R.id.iv6), (ImageView) findViewById(R.id.iv7), (ImageView) findViewById(R.id.iv8), (ImageView) findViewById(R.id.iv9)};
        for (int i=0;i<9;i++){
            if (img_src[i]==6){
                index=i;
            }
            imageViews[i].setImageResource(imageId[img_src[i]]);
        }
        for (int i=0;i<1024;i++){
            xx= (float) Math.random()*2-1;
            yy = (float) Math.random()*2-1;
            Move();
        }
        step=0;
        tv.setText("  第" + step + "步");
        isBegin =true;
        isOver = false;
        if (isBegin)
        ch.start();
        ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (isOver){
                    ch.stop();
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX(), y=event.getY();
        boolean flag=false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                dx = x;
                dy = y;
                break;
            case MotionEvent.ACTION_UP:
                ux = x;
                uy = y;
                flag=true;
                break;
        }
        xx = ux-dx;
        yy = uy-dy;
        if (flag){
            Move();
        }
        return true;
    }
    public void Move(){

        if (Math.abs(xx)>Math.abs(yy)){
            if (xx>(-xx) && index%3!=2){
                k = img_src[index];
                img_src[index] = img_src[index+1];
                img_src[index+1] = k;
                imageViews[index].setImageResource(imageId[img_src[index]]);
                imageViews[index+1].setImageResource(imageId[img_src[index+1]]);
                index++;
                step++;
            }else
            if (xx<(-xx) && index%3!=0){
                k = img_src[index];
                img_src[index] = img_src[index-1];
                img_src[index-1] = k;
                imageViews[index].setImageResource(imageId[img_src[index]]);
                imageViews[index-1].setImageResource(imageId[img_src[index-1]]);
                index--;
                step++;
            }
        }else {
            if (yy>(-yy) && index<6){
                k = img_src[index];
                img_src[index] = img_src[index+3];
                img_src[index+3] = k;
                imageViews[index].setImageResource(imageId[img_src[index]]);
                imageViews[index+3].setImageResource(imageId[img_src[index+3]]);
                index=index+3;
                step++;
            }else
            if (yy<(-yy) && index>2){
                k = img_src[index];
                img_src[index] = img_src[index-3];
                img_src[index-3] = k;
                imageViews[index].setImageResource(imageId[img_src[index]]);
                imageViews[index-3].setImageResource(imageId[img_src[index-3]]);
                index=index-3;
                step++;
             }
        }
        tv.setText("  第"+step+"步");
        isGameOver();
    }
    public boolean isGameOver(){
        int i;
        for (i=0;i<9;i++)
        if (img_src[i]!=i) {
            break;
        }
        if (i==9) {
           isOver = true;
        }
        if (isBegin && isOver){
            Toast toast = Toast.makeText(this, "游戏结束，共用" + step + "步", Toast.LENGTH_LONG);
            toast.show();
            isBegin = false;
        }
        return true;
    }

}
