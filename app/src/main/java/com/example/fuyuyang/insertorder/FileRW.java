package com.example.fuyuyang.insertorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by fuyuyang on 16/10/25.
 */
public class FileRW {

    public String getPath(Context context, Uri uri) {

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public void showFileChooser(AppCompatActivity appCompatActivity, Context context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            appCompatActivity.startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 0);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] readFileToBytes(String path)
    {
        try{
            FileInputStream fin = new FileInputStream(path);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            fin.close();
            return buffer;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public byte[][] bytesToBytesArrary(byte[] data,int w,int h)
    {
        if(data.length!=w*h)
        {
            return null;
        }
        byte[][] temp=new byte[h][w];

        for(int i=0;i<h;i++)
        {
            for(int j=0;j<w;j++)
            {
                temp[i][j]=data[i*w+j];
            }
        }
        return temp;
    }
}
