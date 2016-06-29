package com.example.fuyuyang.insertorder;

import java.util.Random;

/**
 * Created by fuyuyang on 16/3/13.
 */
public class getRanList {

    public int count = 0;

    //获取随机数
    public int[] getList(int maxValue, int count) {
        int[] result = new int[count];

        Random random = new Random();

        for (int i = 0; i < count; i++) {
            result[i] = random.nextInt(maxValue + 1);
        }

        return result;
    }

    public int[] getList(int minValue, int maxValue, int count) {
        int[] result = new int[count];
        if (minValue >= maxValue) {
            return null;
        }
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            result[i] = random.nextInt(maxValue - minValue + 1) + minValue;
        }

        return result;
    }


    //插入排序
    public int[] insertOrderList(int[] list) {
        int[] listTemp = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            listTemp[i] = list[i];
        }

        int key = 0;
        for (int i = 1; i < listTemp.length; i++) {
            key = listTemp[i];
            for (int j = i - 1; j >= 0 && key < listTemp[j]; j--) {
                listTemp[j + 1] = listTemp[j];
                listTemp[j] = key;
            }
        }

        return listTemp;
    }

    public int[] mergeSort(int[] list) {
        //首先拆分离成两个数组
        int[] tempAll = null;
        int[] temp1 = new int[list.length / 2];
        int[] temp2 = new int[list.length / 2];
        if (list.length % 2 == 1) {
            temp2 = new int[list.length / 2 + 1];
        }

        for (int i = 0; i < list.length / 2; i++) {
            temp1[i] = list[i];
        }
        for (int i = 0; i < temp2.length; i++) {
            temp2[i] = list[list.length / 2 + i];
        }

        temp1 = insertOrderList(temp1);
        temp2 = insertOrderList(temp2);

        tempAll = mergeArrary(temp1, temp2);

        return tempAll;
    }

    public int[] recursionSort(int[] list) {
        count++;

        //首先拆分离成两个数组
        int[] tempAll = null;
        if (list.length <= 1) {
            return list;
        }
        int[] temp1 = new int[list.length / 2];
        int[] temp2 = new int[list.length / 2];
        if (list.length % 2 == 1) {
            temp2 = new int[list.length / 2 + 1];
        }

        for (int i = 0; i < list.length / 2; i++) {
            temp1[i] = list[i];
        }
        for (int i = 0; i < temp2.length; i++) {
            temp2[i] = list[list.length / 2 + i];
        }

        temp1 = recursionSort(temp1);
        temp2 = recursionSort(temp2);

        tempAll = mergeArrary(temp1, temp2);

        return tempAll;
    }

    public int[] mergeArrary(int[] a1, int[] a2) {
        int[] temp = new int[a1.length + a2.length];
        int i = 0, j = 0, n = 0;
        for (i = 0, j = 0, n = 0; i < a1.length && j < a2.length; ) {
            if (a1[i] <= a2[j]) {
                temp[n++] = a1[i];
                i++;
            } else if (a2[j] < a1[i]) {
                temp[n++] = a2[j];
                j++;
            }
        }
        if (i < a1.length) {
            for (; i < a1.length; i++) {
                temp[n++] = a1[i];
            }
        } else {
            for (; j < a2.length; j++) {
                temp[n++] = a2[j];
            }
        }

        return temp;
    }

    //冒泡排序
    public int[] bubbleSort(int[] list) {
        int[] listTemp = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            listTemp[i] = list[i];
        }

        int key = 0;
        int length = listTemp.length;
        for (int j = 0; j < length; length--) {
            for (int i = 0; i < length - 1; i++) {
                if (listTemp[i] > listTemp[i + 1]) {
                    key = listTemp[i];
                    listTemp[i] = listTemp[i + 1];
                    listTemp[i + 1] = key;
                }
            }
        }
        return listTemp;
    }

}
