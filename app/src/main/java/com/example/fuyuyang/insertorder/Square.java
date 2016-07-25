package com.example.fuyuyang.insertorder;

import android.widget.Toast;

import java.util.Random;

/**
 * Created by fuyuyang on 16/6/19.
 */
public class Square {

    public int multiplyingSquartCount = 0;
    public int mergeSquartSubCount = 0;

    public int[][] getSquart(int i, int j, int min, int max) {
        int[][] result = new int[i][j];


        Random random = new Random();

        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                result[k][l] = random.nextInt(max - min + 1) + min;
//                this.multiplyingSquartCount++;
            }
        }

        return result;
    }

    //矩阵乘法   i表示行数   j表示列数
    public int[][] multiplyingSquart(int[][] squart1, int[][] squart2) {
        if (squart1.length != squart2[0].length) {
            //第一个矩阵的行数与第二个矩阵的列数不相等,相乘无意义
            return null;
        }

        int[][] result = new int[squart1.length][squart2[0].length];

        for (int i = 0; i < squart1.length; i++) {
            for (int j = 0; j < squart2[0].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < squart1[0].length; k++) {
                    result[i][j] += squart1[i][k] * squart2[k][j];
                    this.multiplyingSquartCount++;
                }
            }
        }
        return result;
    }

    //填充数组
    public int[][] fillArrary(int[][] srcArrary, int[][] dirArrary, int startX, int startY, int lenX, int lenY) {

        int[][] result = dirArrary;

        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                result[i][j] = srcArrary[i + startY][j + startX];
            }
        }
        return result;
    }

    public int[][] addArrary(int[][] squartA, int[][] squartB) {
        int[][] result = new int[squartA.length][squartA.length];

        for (int i = 0; i < squartA.length; i++) {
            for (int j = 0; j < squartA.length; j++) {
                result[i][j] = squartA[i][j] + squartB[i][j];
            }
        }

        return result;
    }

    public int[][] mergeSquart(int[][] A11, int[][] A12, int[][] A21, int[][] A22) {
        int[][] result = new int[A11.length + A21.length][A11[0].length + A12[0].length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if ((i < (result.length / 2)) && (j < (result[0].length / 2))) {
                    result[i][j] = A11[i][j];
                } else if ((i < (result.length / 2)) && (j >= (result[0].length / 2))) {
                    result[i][j] = A12[i][j - A11[0].length];
                } else if ((i >= (result.length / 2)) && (j < (result[0].length / 2))) {
                    result[i][j] = A21[i - A11.length][j];
                } else if ((i >= (result.length / 2)) && (j >= (result[0].length / 2))) {
                    result[i][j] = A22[i - A11.length][j - A11[0].length];
                }
            }
        }

        return result;
    }


    public int[][] mergeSquartSub(int[][] squartA, int[][] squartB) {
        int[][] result = null;

        int subLen = squartA.length / 2;

        if (squartA.length == 2) {
            result = multiplyingSquart(squartA, squartB);
            return result;
        }

        int[][] squartA11 = new int[subLen][subLen];
        int[][] squartA12 = new int[subLen][subLen];
        int[][] squartA21 = new int[subLen][subLen];
        int[][] squartA22 = new int[subLen][subLen];

        int[][] squartB11 = new int[subLen][subLen];
        int[][] squartB12 = new int[subLen][subLen];
        int[][] squartB21 = new int[subLen][subLen];
        int[][] squartB22 = new int[subLen][subLen];

        squartA11 = fillArrary(squartA, squartA11, 0, 0, subLen, subLen);
        squartA12 = fillArrary(squartA, squartA12, subLen, 0, subLen, subLen);
        squartA21 = fillArrary(squartA, squartA21, 0, subLen, subLen, subLen);
        squartA22 = fillArrary(squartA, squartA22, subLen, subLen, subLen, subLen);

        squartB11 = fillArrary(squartB, squartB11, 0, 0, subLen, subLen);
        squartB12 = fillArrary(squartB, squartB12, subLen, 0, subLen, subLen);
        squartB21 = fillArrary(squartB, squartB21, 0, subLen, subLen, subLen);
        squartB22 = fillArrary(squartB, squartB22, subLen, subLen, subLen, subLen);

//        int[][] squartC11 = addArrary(mergeSquartSub(squartA11, squartB11), mergeSquartSub(squartA12, squartB21));
//        int[][] squartC12 = addArrary(mergeSquartSub(squartA11, squartB12), mergeSquartSub(squartA12, squartB22));
//        int[][] squartC21 = addArrary(mergeSquartSub(squartA21, squartB11), mergeSquartSub(squartA22, squartB21));
//        int[][] squartC22 = addArrary(mergeSquartSub(squartA21, squartB12), mergeSquartSub(squartA22, squartB22));

        int[][] squartC11 = addArrary(multiplyingSquart(squartA11, squartB11), multiplyingSquart(squartA12, squartB21));
        int[][] squartC12 = addArrary(multiplyingSquart(squartA11, squartB12), multiplyingSquart(squartA12, squartB22));
        int[][] squartC21 = addArrary(multiplyingSquart(squartA21, squartB11), multiplyingSquart(squartA22, squartB21));
        int[][] squartC22 = addArrary(multiplyingSquart(squartA21, squartB12), multiplyingSquart(squartA22, squartB22));

        result = mergeSquart(squartC11, squartC12, squartC21, squartC22);

        return result;
    }

}
