package app.controller.ColourFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HSLUtil {
    int[] hsl;

    public HSLUtil() {

    }

    public HSLUtil(int[] hsl) {
        this.hsl = hsl;
    }

    public static int[] RGBToHSL(int r, int g, int b) {
        double min = 0.0, max = 0.0;
        double s = 0.0, h = 0.0, l = 0.0;
        double temp = 0.0;
        int[] hsl = new int[3];
        double rk = r / 255.0;
        double gk = g / 255.0;
        double bk = b / 255.0;
        rk = new BigDecimal(rk).setScale(2, RoundingMode.DOWN).doubleValue();
        gk = new BigDecimal(gk).setScale(2, RoundingMode.DOWN).doubleValue();
        bk = new BigDecimal(bk).setScale(2, RoundingMode.DOWN).doubleValue();

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
        if (min == max) {
            l = (min + max) / 2;
            l = l * 100.0;
            l = new BigDecimal(l).setScale(3, RoundingMode.UP).doubleValue();
            s = 0.0;
            h = 0.0;
        }
        if (min != max) {
            l = (min + max) / 2;
            l = new BigDecimal(l).setScale(3, RoundingMode.UP).doubleValue();
            if (l < 0.5) {
                s = (max - min) / (max + min);
            } else {
                s = (max - min) / (2.0 - max - min);

            }
            s = s * 100.0;
            l = l * 100.0;
            s = new BigDecimal(s).setScale(3, RoundingMode.UP).doubleValue();
            if (max == rk) {
                temp = gk - bk;
                temp = new BigDecimal(temp).setScale(3, RoundingMode.UP).doubleValue();
                h = temp / (max - min);
            } else if (max == gk) {
                temp = bk - rk;
                temp = new BigDecimal(temp).setScale(3, RoundingMode.UP).doubleValue();
                h = (temp) / (max - min) + 2.0;
            } else if (max == bk) {
                temp = rk - gk;
                temp = new BigDecimal(temp).setScale(3, RoundingMode.UP).doubleValue();
                h = (temp) / (max - min) + 4.0;
            }
        }
        h = new BigDecimal(h).setScale(3, RoundingMode.UP).doubleValue();
        h = h * 60.0;
        if (h < 0) {
            h += 360.0;
        }

        if(h != 0.0 || s != 0.0) {
            h = Math.round(h);
            s = Math.round(s);
            l = Math.round(l);
        }
        hsl[0] = (int) h;
        hsl[1] = (int) s;
        hsl[2] = (int) l;
        return hsl;
    }

    public static int[] HSLToRGB(int h, int s, int l) {
        double c = 0.0, x = 0.0, m = 0.0;
        double rt = 0.0, gt = 0.0, bt = 0.0;
        double r = 0.0, g = 0.0, b = 0.0;
        int [] rgb = new int[3];
        double ht = (double) h;
        double st = (double) s;
        double lt = (double) l;
        st = st/100.0;
        lt = lt/100.0;
        c = (1.0 - Math.abs(2.0*lt - 1.0))*st;
        x = c*(1.0-Math.abs((ht/60.0)%2.0-1.0));
        m = lt - c/2.0;
        c = new BigDecimal(c).setScale(3, RoundingMode.UP).doubleValue();
        x = new BigDecimal(x).setScale(3, RoundingMode.UP).doubleValue();
        m = new BigDecimal(m).setScale(3, RoundingMode.UP).doubleValue();
        if(ht >= 0.0 && ht < 60.0) {
            rt = c;
            gt = x;
            bt = 0.0;
        }
        else if(ht >= 60.0 && ht < 120.0 ) {
            rt = x;
            gt = c;
            bt = 0.0;
        }
        else if(ht >= 120.0 && ht < 180.0) {
            rt = 0.0;
            gt = c;
            bt = x;
        }
        else if(ht >= 180.0 && ht < 240.0) {
            rt = 0.0;
            gt = x;
            bt = c;
        }
        else if(ht >= 240.0 && ht < 300.0) {
            rt = x;
            gt = 0.0;
            bt = c;
        }
        else if(ht >= 300.0 && ht < 360.0) {
            rt = c;
            gt = 0.0;
            bt = x;
        }
        r = Math.round((rt+m)*255.0);
        g = Math.round((gt+m)*255.0);
        b = Math.round((bt+m)*255.0);
        rgb[0] = (int)r;
        rgb[1] = (int)g;
        rgb[2] = (int)b;
        return rgb;
    }

    public static void showHSL(int[] hsl) {
        System.out.println("Hue: " + hsl[0] + System.lineSeparator() + "Saturnation: " + hsl[1] + System.lineSeparator()
                + "Luminace: " + hsl[2]);
    }

    public static void showRGB(int[] rgb) {
        System.out.println("Red: " + rgb[0] + System.lineSeparator() + "Green: " + rgb[1] + System.lineSeparator()
                + "Blue: " + rgb[2]);
    }

    @Override
    public String toString() {
        return "Hue: " + hsl[0] + System.lineSeparator() + "Saturnation: " + hsl[1] + System.lineSeparator()
                + "Luminace: " + hsl[2];
    }
}
