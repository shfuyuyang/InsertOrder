package com.example.fuyuyang.insertorder;

import android.widget.Toast;

import java.util.Random;

/**
 * Created by fuyuyang on 16/6/19.
 */
public class Square {

    public long count = 0;

    public int[][] getSquart(int i, int j, int min, int max) {
        int[][] result = new int[i][j];


        Random random = new Random();

        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                result[k][l] = random.nextInt(max - min + 1) + min;
                this.count++;
            }
        }

        return result;
    }

    public long[][] mergeSquart(int[][] squart1, int[][] squart2) {
        long[][] result = new long[squart1[1].length][squart1[1].length];

        for (int i = 0; i < squart1.length; i++) {
            for (int j = 0; j < squart1.length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < squart1.length; k++) {
                    result[i][j] += squart1[i][k] * squart2[k][j];
                    this.count++;
                }
            }
        }
        return result;
    }

}
