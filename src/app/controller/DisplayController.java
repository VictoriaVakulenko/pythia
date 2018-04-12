package app.controller;

import app.controller.ColourFormat.HSLUtil;
import app.controller.ColourFormat.HSVUtil;
import app.model.PAD;

import java.util.ArrayList;

import static app.controller.ColourFormat.RGBUtil.*;

public class DisplayController {

    public static String printColour (MMCQ.VBox vbox){
        return vbox.toString();
    }

    public static String getRGB (int [] rgb){
        return RGBToString(rgb);
    }

    public static String getHex(int [] rgb){
        return RGBToHex(rgb);
    }

    public static String getHSL(int [] rgb){
        int [] hsl;
        HSLUtil hslUtil = new HSLUtil();
        hsl = hslUtil.RGBToHSL(rgb[0], rgb[1], rgb[2]);
        return "H: " + hsl[0] + " S: " + hsl[1] + " L: " + hsl[2];
    }

    public static String getHSV(int [] rgb){
        int [] hsv;
        HSVUtil hsvUtil = new HSVUtil();
        hsv = hsvUtil.RGBToHSV(rgb[0], rgb[1], rgb[2]);
        return "H: " + hsv[0] + " S: " + hsv[1] + " V: " + hsv[2];
    }

    public static String getDirectAssociation(PAD pad){
        PADController controller = new PADController();
        return controller.getAssociations(pad);
    }

    public static String getPAD(int [] rgb){
        int [] hsv;
        HSVUtil hsvUtil = new HSVUtil();
        hsv = hsvUtil.RGBToHSV(rgb[0], rgb[1], rgb[2]);
        PAD pad = new PAD();
        pad.getPADFromHSV(hsv);
        return pad.toString();
    }

}
