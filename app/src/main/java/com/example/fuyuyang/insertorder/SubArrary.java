package com.example.fuyuyang.insertorder;

/**
 * Created by fuyuyang on 16/6/7.
 */
public class SubArrary {

    class Arrary {
        int low = 0;
        int high = 0;
        int sum = 0;
        int[] data = null;

        public Arrary(int[] data, int low, int high) {
            this.low = low;
            this.high = high;
            this.data = data;
        }
    }

    //求取最大子数组,暴力求解
    public int[] getMaxSubarrary(int[] list) {
        boolean allOverZero = true;
        for (int i = 0; i < list.length; i++) {
            if (list[i] <= 0) {
                allOverZero = false;
            }
        }
        if (allOverZero == true) {
            //全部大于零,返回自身
//            return list;
        }

        int[] listTemp = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            listTemp[i] = list[i];
        }

        int start = 0, end = 0, max = 0;
        for (int i = 0; i < listTemp.length - 1; i++) {
            for (int j = i + 1; j < listTemp.length; j++) {
                if (listTemp[j] - listTemp[i] > max) {
                    max = listTemp[j] - listTemp[i];
                    start = i;
                    end = j;
                }
            }
        }
        int[] result = new int[end - start + 1];

        for (int i = start; i <= end; i++) {
            result[i - start] = listTemp[i];
        }
        return result;

    }

    //获取最大子数组,差值算法
    public int[] getMaxSubarraryAlgo(int[] list) {
        int[] listTemp = new int[list.length - 1];

        for (int i = 1; i < listTemp.length; i++) {
            listTemp[i - 1] = list[i] - list[i - 1];
        }


        int start = 0, end = 0, sum = 0, maxSum = 0;
        for (int i = 0; i < listTemp.length - 1; i++) {
            sum = 0;
            for (int j = i + 1; j < listTemp.length; j++) {
                sum += listTemp[j];
                if (sum > maxSum) {
                    maxSum = sum;
                    start = i + 1;
                    end = j + 1;
                }
            }
        }
        int[] result = new int[end - start + 1];

        for (int i = start; i <= end; i++) {
            result[i - start] = list[i];
        }
        return result;
    }

    //获取最大子数组,递归算法
    int[] recursionGetArrary(int[] list) {
        int[] listTemp1 = null;
        int[] listTemp2 = null;
        int[] listResult = new int[list.length];


        if (list.length % 2 == 0) {
            listTemp1 = new int[list.length / 2];
            listTemp2 = new int[list.length / 2];
        } else {
            listTemp1 = new int[list.length / 2];
            listTemp2 = new int[list.length / 2 + 1];
        }

        //填充两个子list
        for (int i = 0; i < listTemp1.length; i++) {
            listTemp1[i] = list[i];
        }

        for (int j = 0; j < listTemp2.length; j++) {
            listTemp2[j] = list[listTemp1.length + j];
        }

        //获取两个子list的最大子数组
        int[] listTemp1Result = null;
        int[] listTemp2Result = null;

        listTemp2Result = getMaxSubarrary(listTemp2);
        listTemp1Result = getMaxSubarrary(listTemp1);
        int start = 0, end = 0, max = 0, flag = 0;

        for (int k = 0; k < listTemp1.length; k++) {
            for (int l = 0; l < listTemp2.length; l++) {
                if (max < listTemp2[l] - listTemp1[k]) {
                    max = listTemp2[l] - listTemp1[k];
                    start = k;
                    end = l + listTemp2.length;
                }
            }
        }

        if (max <= (listTemp1Result[listTemp1Result.length - 1] - listTemp1Result[0])) {
            max = (listTemp1Result[listTemp1Result.length - 1] - listTemp1Result[0]);
            flag = 1;
        }
        if (max < (listTemp2Result[listTemp2Result.length - 1] - listTemp2Result[0])) {
            max = listTemp2Result[listTemp2Result.length - 1] - listTemp2Result[0];
            flag = 2;
        }
        if (flag == 0) {
            listResult = new int[end - start + 1];

            for (int i = start; i <= end; i++) {
                listResult[i - start] = list[i];
            }
        } else if (flag == 1) {
            return listTemp1Result;
        } else if (flag == 2) {
            return listTemp2Result;
        }
        return listResult;
    }

    public int[] getDvalue(int[] list) {
        int[] result = new int[list.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = list[i + 1] - list[i];
        }
        return result;
    }

    public int[] findMaxSubArrary(int[] data) {
        int[] temp = getDvalue(data);
        Arrary arraryTemp = new Arrary(temp, 0, temp.length - 1);
        Arrary arrary = findMaxSubArrary(arraryTemp);
        int[] result = new int[arrary.high - arrary.low + 1];
        for (int i = arrary.low; i <= arrary.high; i++) {
            result[i - arrary.low] = data[i];
        }
        return result;
    }

    //采用递归方式求最大子数组
    public Arrary findMaxSubArrary(Arrary arrary) {

        if (arrary.low == arrary.high) {
            arrary.sum = arrary.data[arrary.low];
            return arrary;
        } else {
            int mid = (arrary.low + arrary.high) / 2;

            Arrary arraryLeft = new Arrary(arrary.data, arrary.low, mid);
            Arrary arraryRight = new Arrary(arrary.data, mid + 1, arrary.high);

            Arrary arraryLeftResult = findMaxSubArrary(arraryLeft);
            Arrary arraryRightResult = findMaxSubArrary(arraryRight);
            Arrary arraryCrossResult = findMaxCrossSubArrary(arrary.data, arrary.low, mid, arrary.high);
            if (arraryLeftResult.sum > arraryRightResult.sum && arraryLeftResult.sum > arraryCrossResult.sum) {
                return arraryLeftResult;
            } else if (arraryRightResult.sum > arraryLeftResult.sum && arraryRightResult.sum > arraryCrossResult.sum) {
                return arraryRightResult;
            } else {
                return arraryCrossResult;
            }
        }
    }

    public Arrary findMaxCrossSubArrary(int[] data, int low, int mid, int high) {
        Arrary arrary = new Arrary(data, low, high);

        int leftSum = 0, leftMax = 0;
        int rightSum = 0, rightMax = 0;

        for (int i = mid; i >= low; i--) {
            leftSum += data[i];
            if (leftMax < leftSum) {
                leftMax = leftSum;
                arrary.low = i;
            }
        }

        for (int j = mid + 1; j < high; j++) {
            rightSum += data[j];
            if (rightMax < rightSum) {
                rightMax = rightSum;
                arrary.high = j;
            }
        }

        arrary.sum = leftSum + rightSum;

        return arrary;
    }
}
