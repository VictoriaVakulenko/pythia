package app.controller.ColourFormat;

import app.controller.AdditionalUtils.MathUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HSVUtil {

    int[] hsv;

    public HSVUtil(){}

    public static int[] RGBToHSV(int r, int g, int b) {
        double min = 0.0, max = 0.0;
        double s = 0.0, h = 0.0, v = 0.0;
        int[] hsv = new int[3];
        double rk = r / 255.0;
        double gk = g / 255.0;
        double bk = b / 255.0;
        rk = new BigDecimal(rk).setScale(4, RoundingMode.DOWN).doubleValue();
        gk = new BigDecimal(gk).setScale(4, RoundingMode.DOWN).doubleValue();
        bk = new BigDecimal(bk).setScale(4, RoundingMode.DOWN).doubleValue();
        if (rk >= gk && rk >= bk) {
            max = rk;
            if (gk <= bk) {
                min = gk;
            } else {
                min = bk;
            }
        }
        if (gk >= rk && gk >= bk) {
            max = gk;
            if (rk <= bk) {
                min = rk;
            } else {
                min = bk;
            }
        }
        if (bk >= rk && bk >= gk) {
            max = bk;
            if (rk <= gk) {
                min = rk;
            } else {
                min = gk;
            }
        }
        double diff = max - min;
        diff = MathUtil.round(diff, 4);
        if(diff == 0.0){
            h = 0.0;
        } else if(max == rk){
            h = 60.0*(((gk-bk)/diff)%6);
        } else if(max == gk){
            h = (60.0*((bk-rk)/diff)) + 2.0;
        } else if(max == bk){
            h = (60.0*((rk-gk)/diff)) + 4.0;
        }
        if(h < 0){
            h += 360.0;
        }
        if(max == 0.0){
            s = 0.0;
        } else {
            s = (diff/max)*100.0;
        }
        v = max*100.0;
        hsv[0] = (int) h;
        hsv[1] = (int) s;
        hsv[2] = (int) v;
        return hsv;
    }

    @Override
    public String toString() {
        return " H : " + hsv[0] + " S: " + hsv[1] + " V: " + hsv[2];
    }
}
