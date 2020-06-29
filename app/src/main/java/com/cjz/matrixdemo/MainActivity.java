package com.cjz.matrixdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv1;
    private ImageView mIv1;
    private TextView mTv2;
    private ImageView mIv2;
    private Button mBn1;
    private Button mBn2;
    private Button mBn3;
    private Button mBn4;
    private Button mBn5;

    private Bitmap bitmap;
    private static float smallScale = 1.0f;
    private static int turnRotate = 0;
    private Matrix matrix = new Matrix();

    private static float[] oldArr = {
            0.33F,0.59F,0.11F,0F,0F,
            0.33F,0.59F,0.11F,0F,0F,
            0.33F,0.59F,0.11F,0F,0F,
            0F,0F,0F,1F,0F
    };//怀旧

    private static float[] satArr = {
            1.438F,-0.122F,-0.016F,0F,-0.03F,
            -0.062F,1.378F,-0.016F,0F,0.05F,
            -0.062F,-0.12F,1.483F,0F,-0.02F,
            0F,0F,0F,1F,0F
    };//高饱和度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        bitmap = getResizedBitmap(bitmap,592,850);
        mIv2.setImageBitmap(bitmap);
        mIv2.setScaleType(ImageView.ScaleType.MATRIX);//不改变原图
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bn_1:
                smallImag();
                break;
            case R.id.bn_2:
                largeImg();
                break;
            case R.id.bn_3:
                rotateImg();
                break;
            case R.id.bn_4:
                saturateImg();
                break;
            case R.id.bn_5:
                oldImg();
                break;
        }
        mIv2.setVisibility(View.VISIBLE);
        mTv2.setVisibility(View.VISIBLE);
    }

    private void smallImag(){
        if (smallScale>0.5f){
            smallScale -= 0.1f;
        } else {
            smallScale=0.5f;
        }

        int width = mIv2.getWidth();
        int height = mIv2.getHeight();
        matrix.setScale(smallScale,smallScale,width/2,height/2);
        mIv2.setImageMatrix(matrix);
    }

    private void largeImg(){
        int width = mIv2.getWidth();
        int height = mIv2.getHeight();
        if (smallScale<1.5f){
            smallScale = smallScale + 0.1f;
        } else {
            smallScale = 1.5f;
        }

        matrix.setScale(smallScale,smallScale,width/2,height/2);
        mIv2.setImageMatrix(matrix);
    }

    private void rotateImg(){
        turnRotate+=15;
        int width = mIv2.getWidth();
        int height = mIv2.getHeight();
        matrix.postRotate(turnRotate,width/2,height/2);
        mIv2.setImageMatrix(matrix);
    }

    private void saturateImg(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(satArr);
        mIv2.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    private void oldImg(){
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(oldArr);
        mIv2.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    private void initView() {
        mTv1 = findViewById(R.id.tv_1);
        mIv1 = findViewById(R.id.iv_1);
        mTv2 = findViewById(R.id.tv_2);
        mIv2 = findViewById(R.id.iv_2);
        mBn1 = findViewById(R.id.bn_1);
        mBn2 = findViewById(R.id.bn_2);
        mBn3 = findViewById(R.id.bn_3);
        mBn4 = findViewById(R.id.bn_4);
        mBn5 = findViewById(R.id.bn_5);
        mBn1 = (Button) findViewById(R.id.bn_1);
        mBn1.setOnClickListener(this);
        mBn2 = (Button) findViewById(R.id.bn_2);
        mBn2.setOnClickListener(this);
        mBn3 = (Button) findViewById(R.id.bn_3);
        mBn3.setOnClickListener(this);
        mBn4 = (Button) findViewById(R.id.bn_4);
        mBn4.setOnClickListener(this);
        mBn5 = (Button) findViewById(R.id.bn_5);
        mBn5.setOnClickListener(this);
    }
}
