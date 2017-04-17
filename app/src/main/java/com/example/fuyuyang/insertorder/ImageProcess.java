package com.example.fuyuyang.insertorder;

/**
 * Created by fuyuyang on 2017/3/18.
 */
public class ImageProcess {

    //将数组变成两个方向极值
    public byte[] doubleDirect(byte[] data) {

        byte max = 0;
        byte min = 0;
        for (int i = 0; i < data.length; i++) {
            if (max < data[i]) {
                max = data[i];
            }
            if (min > data[i]) {
                min = data[i];
            }
        }

        byte[] returnData = new byte[data.length];

        for (int i = 0; i < data.length; i++) {
            if ((int) data[i] > -100 && (int) data[i] < 0) {
                returnData[i] = (byte) 0xff;
            } else {
                returnData[i] = (byte) 0;
            }
        }

        return returnData;
    }

}
