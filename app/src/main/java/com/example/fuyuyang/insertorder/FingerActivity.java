package com.example.fuyuyang.insertorder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
    Button buttonCaptureImage = null;
    TextView textViewPath = null;
    ImageView imageViewPro = null;
    ImageView imageViewPro2 = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);

        textViewPath = (TextView) findViewById(R.id.textViewPath);
        imageViewPro = (ImageView) findViewById(R.id.imageViewPro);
        imageViewPro2 = (ImageView) findViewById(R.id.imageViewPro2);
        buttonDisplay = (Button) findViewById(R.id.buttonDisplay);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonCaptureImage = (Button) findViewById(R.id.buttonCaptureImage);

        buttonInit();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    private void buttonInit() {
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
                    showInImageView(imageDataTemp, 256, 360, imageViewPro);

                    ImageProcess imageProcess = new ImageProcess();
                    imageDataTemp = imageProcess.doubleDirect(imageDataTemp);
                    showInImageView(imageDataTemp, 256, 360, imageViewPro2);

                }
            }
        });

    }

    public void showInImageView(byte[] imageDataTemp, int w, int h, ImageView imageView) {
        FileRW fileRW = new FileRW();
        byte[][] imageData = fileRW.bytesToBytesArrary(imageDataTemp, w, h);
        Bitmap bitmapTemp = Bitmap.createBitmap(w, h, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(bitmapTemp);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                paint.setARGB((w - 1) - imageData[j][i], 0, 0, 0);
                canvas.drawPoint(i, j, paint);
            }
        }
        canvas.drawBitmap(bitmapTemp, 0, 0, paint);
        imageView.setImageBitmap(bitmapTemp);
    }

}
