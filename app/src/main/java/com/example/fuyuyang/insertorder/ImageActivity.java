package com.example.fuyuyang.insertorder;

import android.annotation.TargetApi;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageActivity extends AppCompatActivity {

    private Canvas canvas = null;
    private Bitmap bitmap = null;
    private Paint paint = null;
    private ImageView imageView = null;

    Button buttonLeft = null;
    Button buttonRight = null;
    EditText editText = null;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonRight = (Button) findViewById(R.id.buttonRight);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editTextAngle);


        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int angle = Integer.valueOf(editText.getText().toString());
                rotateImage(0, angle);
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int angle = Integer.valueOf(editText.getText().toString());
                rotateImage(1, angle);
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

        angle = angle % 360;

        //将所有方向的旋转都转变为顺时针旋转
        if (dir == 0) {
            //逆时针
            angle = 360 - angle;
        } else if (dir == 1) {
            //顺时针
        }

        //bitmap图片原始数组
        int[][] bitmapInt1 = turnArrary(bitmap, 300, 300, angle);

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

        public point() {
            this.x = 0;
            this.y = 0;
            this.col = Color.WHITE;
        }

        public int x = 0;
        public int y = 0;
        public int col = 0;
    }

    point[][] mapPoint = new point[600][600];

    public int[][] turnArrary(Bitmap bitmap, int x, int y, double angle) {
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

        //先求出常用的常量
        angle = angle / 180 * Math.PI;
        double hypotenuseLen = Math.sqrt(upLeft.x * upLeft.x + upLeft.y * upLeft.y);      //三角函数中弦的长度

        upLeftT.x = (int) (Math.cos(Math.atan2(upLeft.y, upLeft.x) - angle) * hypotenuseLen - 0.5);
        upLeftT.y = (int) (Math.sin(Math.atan2(upLeft.y, upLeft.x) - angle) * hypotenuseLen + 0.5);
        upRightT.x = (int) (Math.cos(Math.atan2(upRight.y, upRight.x) - angle) * hypotenuseLen + 0.5);
        upRightT.y = (int) (Math.sin(Math.atan2(upRight.y, upRight.x) - angle) * hypotenuseLen + 0.5);
        downLeftT.x = (int) (Math.cos(Math.atan2(downLeft.y, downLeft.x) - angle) * hypotenuseLen - 0.5);
        downLeftT.y = (int) (Math.sin(Math.atan2(downLeft.y, downLeft.x) - angle) * hypotenuseLen - 0.5);
        downRightT.x = (int) (Math.cos(Math.atan2(downRight.y, downRight.x) - angle) * hypotenuseLen + 0.5);
        downRightT.y = (int) (Math.sin(Math.atan2(downRight.y, downRight.x) - angle) * hypotenuseLen - 0.5);


        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                mapPoint[i][j] = new point();
            }
        }

        mapPoint[0][0].x = upLeftT.x;
        mapPoint[0][0].y = upLeftT.y;
        mapPoint[0][0].col = Color.WHITE;

        mapPoint[bitmap.getWidth() - 1][0].x = upRightT.x;
        mapPoint[bitmap.getWidth() - 1][0].y = upRightT.y;
        mapPoint[bitmap.getWidth() - 1][0].col = Color.WHITE;

        mapPoint[0][bitmap.getHeight() - 1].x = downLeftT.x;
        mapPoint[0][bitmap.getHeight() - 1].y = downLeftT.y;
        mapPoint[0][bitmap.getHeight() - 1].col = Color.WHITE;

        mapPoint[bitmap.getWidth() - 1][bitmap.getHeight() - 1].x = downRightT.x;
        mapPoint[bitmap.getWidth() - 1][bitmap.getHeight() - 1].y = downRightT.y;
        mapPoint[bitmap.getWidth() - 1][bitmap.getHeight() - 1].col = Color.WHITE;

        int[] temp = new int[600];
        //求上边坐标
        for (int i = 1; i < bitmap.getWidth() - 1; i++) {
            mapPoint[i][0].x = upLeftT.x + (int) (i * (upRightT.x - upLeftT.x) / ((double) bitmap.getWidth()) + 0.5);
            mapPoint[i][0].y = upLeftT.y + (int) (i * (upRightT.y - upLeftT.y) / ((double) bitmap.getHeight()) - 0.5);
            mapPoint[i][0].col = Color.BLACK;
        }
        //求下边坐标
        for (int i = 1; i < bitmap.getWidth() - 1; i++) {
            mapPoint[i][bitmap.getHeight() - 1].x = downLeftT.x + (int) (i * (downRightT.x - downLeftT.x) / ((double) bitmap.getWidth()) + 0.5);
            mapPoint[i][bitmap.getHeight() - 1].y = downLeftT.y + (int) (i * (downRightT.y - downLeftT.y) / ((double) bitmap.getHeight()) - 0.5);
            mapPoint[i][bitmap.getHeight() - 1].col = Color.BLACK;
        }
        //求左边坐标
        for (int i = 1; i < bitmap.getHeight(); i++) {
            mapPoint[0][i].x = upLeftT.x + (int) (i * (downLeftT.x - upLeftT.x) / ((double) bitmap.getWidth()) - 0.5);
            mapPoint[0][i].y = upLeftT.y + (int) (i * (downLeftT.y - upLeftT.y) / ((double) bitmap.getHeight()) - 0.5);
            mapPoint[0][i].col = Color.BLACK;
        }

        //求右边坐标
        for (int i = 1; i < bitmap.getHeight(); i++) {
            mapPoint[bitmap.getWidth() - 1][i].x = upRightT.x + (int) (i * (downRightT.x - upRightT.x) / ((double) bitmap.getWidth()) - 0.5);
            mapPoint[bitmap.getWidth() - 1][i].y = upRightT.y + (int) (i * (downRightT.y - upRightT.y) / ((double) bitmap.getHeight()) - 0.5);
            mapPoint[bitmap.getWidth() - 1][i].col = Color.BLACK;
        }

        for (int i = 1; i < bitmap.getWidth(); i++) {
            for (int j = 1; j < bitmap.getHeight(); j++) {
                mapPoint[i][j].x = mapPoint[0][j].x + (int) (i * (mapPoint[bitmap.getWidth() - 1][j].x - mapPoint[0][j].x) / ((double) bitmap.getWidth()) + 0.5);
                mapPoint[i][j].y = mapPoint[i][0].y + (int) (j * (mapPoint[i][bitmap.getHeight() - 1].y - mapPoint[i][0].y) / ((double) bitmap.getHeight()) - 0.5);

            }
        }

        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                if (((mapPoint[i][j].x + x >= 0) && (mapPoint[i][j].x + x < bitmap.getWidth())) &&
                        (((y - mapPoint[i][j].y) >= 0) && ((y - mapPoint[i][j].y) < bitmap.getHeight()))) {
                    bitmapIntresult[mapPoint[i][j].x + x][y - mapPoint[i][j].y] = bitmapInt[i][j];
                }
            }
        }

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
