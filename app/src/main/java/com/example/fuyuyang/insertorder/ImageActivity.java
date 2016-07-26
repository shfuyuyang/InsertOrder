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
        int[][] bitmapInt1 = turnArrary(bitmap, 300, 300, 90);

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
        int upLeftX = -x;
        int upLeftY = y;
        int upRightX = bitmap.getWidth() - x;
        int upRightY = y;
        int downLeftX = -x;
        int downLeftY = y - bitmap.getHeight();
        int downRightX = bitmap.getWidth() - x;
        int downRightY = y - bitmap.getHeight();



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
