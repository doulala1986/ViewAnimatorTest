package com.ctsi.viewanimator.test;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

public class MainActivity extends AppCompatActivity {


    ImageView img;
    Button btn1, btn2, btn3;
    View tiny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        img.setVisibility(View.GONE);
        tiny =  findViewById(R.id.tiny);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(onViewClickListener);
        btn2.setOnClickListener(onViewClickListener);
        btn3.setOnClickListener(onViewClickListener);
    }

    private View.OnClickListener onViewClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v == btn1) {
                ViewAnimator.animate(img).translationX(0, 100).andAnimate(btn3).alpha(0, 1).scale(0, 1)
                        .duration(2000).start();
            } else if (v == btn2) {

                ViewAnimator.animate(img).translationX(0, 100).andAnimate(btn1).alpha(0, 1).onStart(startListener).thenAnimate(btn3).alpha(0, 1).onStop(stopListener).start();
            } else if (v == btn3) {

//                ViewAnimator.animate(img).rotation(360).start();

//                custom();
                path();
            }
        }
    };


    private AnimationListener.Start startListener = new AnimationListener.Start() {
        @Override
        public void onStart() {
            Toast.makeText(MainActivity.this, "动画1部分启动", Toast.LENGTH_SHORT).show();

        }
    };

    private AnimationListener.Stop stopListener = new AnimationListener.Stop() {
        @Override
        public void onStop() {
            Toast.makeText(MainActivity.this, "动画2部分结束", Toast.LENGTH_SHORT).show();

        }
    };


    //需要注意dp作用域是在一个animate组
    private void dp() {
        //x轴方向位移200dp,y轴方向上位移200px
        ViewAnimator.animate(img).dp().translationX(0, 200).andAnimate(img).translationY(0, 200).start();
    }


    private void interpolator() {

        ViewAnimator.animate(img).dp().translationX(0, 200).accelerate();

    }

    private void custom() {
        ViewAnimator
                .animate(img)
                .custom(new AnimationListener.Update<ImageView>() {
                    @Override
                    public void update(ImageView view, float value) {
                        view.setAlpha(value);
                    }
                }, 0, 1).duration(5000)
                .start();

    }
    private void path(){
//        final int[] START_POINT = new int[]{ 300, 270 };
//        final int[] BOTTOM_POINT = new int[]{ 300, 400 };
//        final int[] LEFT_CONTROL_POINT = new int[]{ 450, 200 };
//        final int[] RIGHT_CONTROL_POINT = new int[]{ 150, 200 };
//        Path path = new Path();
//        path.moveTo(START_POINT[0], START_POINT[1]);
//        path.quadTo(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], BOTTOM_POINT[0], BOTTOM_POINT[1]);
//        path.quadTo(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], START_POINT[0], START_POINT[1]);
//        path.close();

        Path path0 = new Path();
        path0.rMoveTo(0,0);
        path0.rLineTo(500,500);
        path0.rLineTo(200,0);

        Path path1 = new Path();
        path1.addCircle(700,700,300, Path.Direction.CW);
        path1.moveTo(700,700);
        path1.addRect(new RectF(700, 700, 250, 200), Path.Direction.CW);
        Path path2 = new Path();
        path2.addRect(new RectF(700, 700, 250, 200), Path.Direction.CW);

        Path path3 = new Path();
        path3.addCircle(300, 300, 150, Path.Direction.CW);
        path3.addCircle(380, 380, 150, Path.Direction.CW);
        path3.setFillType(Path.FillType.INVERSE_EVEN_ODD);


        Path path4=new Path();
        path4.moveTo(700,700);
        path4.quadTo(100,400,700,1500);
        path4.quadTo(1300,400,700,700);
        ViewAnimator
                .animate(tiny).path(path4).start();

    }

}
