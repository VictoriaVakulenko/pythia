package app.controller.AdditionalUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    public static double round(double value, int scale) {
        value = new BigDecimal(value).setScale(scale, RoundingMode.UP).doubleValue();
        return value;
    }

    public static int[][] multiMatrix(int [][] m1, int [][] m2){
        int m = m1.length;
        int n = m2[0].length;
        int o = m2.length;
        int [][] res = new int[m][n];
        for(int i =0; i < m; i++){
            for(int j =0; j <n; j++){
                for(int k =0; k < o; k++){
                    res[i][j] += m1[i][k]*m2[k][j];
                }
            }
        }
        return res;
    }

    public static int[][] transposeMatrix(int [][] matrix, int n, int m){
        int temp;
        for(int i = 0; i < n; i ++){
            for(int j =0 ; j < m; j++){
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        return matrix;
    }

    public static boolean intervalNum(int fNum, int sNum, int tNum, int ivl){
        int diff1, diff2, diff3;
        if(fNum >= sNum){
            diff1 = fNum - sNum;
        } else {
            diff1 = sNum - fNum;
        }
        if(fNum >= tNum){
            diff2 = fNum - tNum;
        } else {
            diff2 = tNum - fNum;
        }
        if(sNum >= tNum){
            diff3 = sNum - tNum;
        } else {
            diff3 = tNum - sNum;
        }
        int buf = 255, buf1 = 255, buf2 = 255;
        if(diff1 >= diff2){
            buf = diff1 - diff2;
            if(diff1 >= diff3){
                buf1 = diff1 - diff3;
            } else {
                buf1 = diff3 - diff1;
            }
            if(diff2 >= diff3){
                buf2 = diff2 - diff3;
            } else {
                buf2 = diff3 - diff2;
            }
        } else {
            buf = diff2 - diff1;
            if(diff2 >= diff3){
                buf1 = diff2 - diff3;
            } else {
                buf1 = diff3 - diff2;
            }
            if(diff1 >= diff3){
                buf2 = diff1 - diff3;
            } else {
                buf2 = diff3 - diff1;
            }
        }
        if(buf < ivl && buf1 < ivl && buf2 < ivl){
            return true;
        } else {
            return  false;
        }
    }
}
