package com.example.fuyuyang.insertorder;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageActivity extends AppCompatActivity {

    private Canvas canvas = null;
    private Bitmap bitmap = null;
    private Paint paint = null;
    private ImageView imageView = null;

    Button buttonLeft = null;
    Button buttonRight = null;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonRight = (Button) findViewById(R.id.buttonRight);
        imageView = (ImageView) findViewById(R.id.imageView);

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateImage(0, 0);
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateImage(1, 0);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void rotateImage(int dir, int angle) {
//        bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Resources res = getResources();

        bitmap = BitmapFactory.decodeResource(res, R.drawable.testimage);
        bitmap = bitmap.copy(bitmap.getConfig(), true);

        canvas = new Canvas(bitmap);


        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);


        //bitmap图片原始数组
        int[][] bitmapInt1 = turnArrary(bitmap, 300, 300, 15);

        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                paint.setColor(bitmapInt1[i][j]);
                canvas.drawPoint(i, j, paint);
            }
        }

        canvas.drawBitmap(bitmap, 0, 0, paint);


        imageView.setImageBitmap(bitmap);
//        imageView.invalidate();
//        imageView.setImageBitmap(bitmap);

    }

    public class point {
        public int x = 0;
        public int y = 0;
    }

    public int[][] turnArrary(Bitmap bitmap, int x, int y, int angle) {
        //bitmap转换后的数组
        int[][] bitmapIntresult = new int[bitmap.getWidth()][bitmap.getHeight()];
        int[][] bitmapInt = Bitmap2Ints(bitmap);

        //数组全部填充白色
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                bitmapIntresult[i][j] = Color.WHITE;
            }
        }

        //先求出四个定点相对于旋转点的坐标
        point upLeft = new point();
        point upRight = new point();
        point downLeft = new point();
        point downRight = new point();

        upLeft.x = -x;
        upLeft.y = y;
        upRight.x = bitmap.getWidth() - x;
        upRight.y = y;
        downLeft.x = -x;
        downLeft.y = y - bitmap.getHeight();
        downRight.x = bitmap.getWidth() - x;
        downRight.y = y - bitmap.getHeight();

        //计算旋转后的四个点坐标
        point upLeftT = new point();
        point upRightT = new point();
        point downLeftT = new point();
        point downRightT = new point();

        double pi = 3.1415926;

        //upLeftT.x

//        double res = Math.abs(upLeft.y) / Math.abs(upLeft.x);
//        res=Math.atan(res);
//        res=res+angle/180.0*pi;
//        res=Math.cos(res);
//        res=res*Math.sqrt(upLeft.x*upLeft.x+upLeft.y*upLeft.y);

        upLeftT.x=  (int)(0.5+Math.cos(Math.atan(Math.abs(upLeft.y) / Math.abs(upLeft.x)) + angle / 180.0 * pi)*Math.sqrt(upLeft.x*upLeft.x+upLeft.y*upLeft.y));
        upLeftT.y = y;
        upRightT.x = bitmap.getWidth() - x;
        upRightT.y = y;
        downLeftT.x = -x;
        downLeftT.y = y - bitmap.getHeight();
        downRightT.x = bitmap.getWidth() - x;
        downRightT.y = y - bitmap.getHeight();


        return bitmapIntresult;
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public int[][] Bitmap2Ints(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        int[][] result = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = bm.getPixel(i, j);
            }
        }
        return result;
    }

}
