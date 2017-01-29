package com.example.fuyuyang.insertorder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FingerActivity extends AppCompatActivity {

    Button buttonScan = null;
    Button buttonDisplay = null;
    TextView textViewPath = null;
    ImageView imageViewPro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);

        buttonDisplay = (Button) findViewById(R.id.buttonDisplay);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewPath = (TextView) findViewById(R.id.textViewPath);
        imageViewPro = (ImageView) findViewById(R.id.imageViewPro);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileRW fileRW = new FileRW();
                fileRW.showFileChooser(FingerActivity.this, getApplicationContext());
            }
        });

        buttonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == 0) {
                    imageViewPro.setImageURI(Uri.fromFile(new File(textViewPath.getText().toString())));
                } else {
                    FileRW fileRW = new FileRW();
                    byte[] imageDataTemp = fileRW.readFileToBytes(textViewPath.getText().toString());
                    byte[][] imageData = fileRW.bytesToBytesArrary(imageDataTemp, 256, 360);
                    Bitmap bitmapTemp = Bitmap.createBitmap(256, 360, Bitmap.Config.ALPHA_8);
                    Canvas canvas = new Canvas(bitmapTemp);
                    Paint paint = new Paint();
                    paint.setStrokeWidth(1);

                    for (int i = 0; i < 256; i++) {
                        for (int j = 0; j < 360; j++) {
                            paint.setARGB(255-imageData[j][i], 0, 0, 0);
                            canvas.drawPoint(i, j, paint);
                        }
                    }
                    canvas.drawBitmap(bitmapTemp, 0, 0, paint);
                    imageViewPro.setImageBitmap(bitmapTemp);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == -1) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    FileRW fileRW = new FileRW();
                    String path = fileRW.getPath(this, uri);
                    textViewPath.setText(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
} 
