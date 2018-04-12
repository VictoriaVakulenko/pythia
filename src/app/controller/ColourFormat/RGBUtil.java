package app.controller.ColourFormat;

import app.model.Colour;

public class RGBUtil {

    public static String RGBToString(int[] rgb) {
        return "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")";
    }

   public static String RGBToHex(int[] rgb) {
        String rgbHex = Integer.toHexString(rgb[0] << 16 | rgb[1] << 8 | rgb[2]);
        // Left-pad with 0s
        int length = rgbHex.length();
        if (length < 6) {
            rgbHex = "00000".substring(0, 6 - length) + rgbHex;
        }

        return "#" + rgbHex;
    }

    public static int[] setRGBArray(Colour colour, int [] rgb){
        rgb[0]= colour.getR();
        rgb[1] = colour.getG();
        rgb[2] = colour.getB();
        return rgb;
    }
}
