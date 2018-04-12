package app.model;

import app.controller.AdditionalUtils.MathUtil;
import app.controller.ColourFormat.HSVUtil;

public class PAD {

    double pleasure;
    double arousal;
    double dominance;

    public PAD(){}

    public PAD(double pleasure, double arousal, double dominance){
        this.pleasure = pleasure;
        this.arousal = arousal;
        this.dominance = dominance;
    }

    public double getPleasure() {
        return pleasure;
    }

    public void setPleasure(double pleasure) {
        this.pleasure = pleasure;
    }

    public double getArousal() {
        return arousal;
    }

    public void setArousal(double arousal) {
        this.arousal = arousal;
    }

    public double getDominance() {
        return dominance;
    }

    public void setDominance(double dominance) {
        this.dominance = dominance;
    }

    public PAD getPADFromHSV(int [] hsv){
        pleasure = 0.69*(hsv[2]/100.0) + 0.22*(hsv[1]/100.0);
        arousal = (-0.31)*(hsv[2]/100.0) + 0.60*(hsv[1]/100.0);
        dominance =(-0.76)*(hsv[2]/100.0) + 0.32*(hsv[1]/100.0);
        pleasure = MathUtil.round(pleasure, 6);
        arousal = MathUtil.round(arousal, 6);
        dominance = MathUtil.round(dominance, 6);
        PAD pad = new PAD(pleasure,arousal,dominance);
        return pad;
    }

    public PAD getPAD(int [] rgb){
        int [] hsv;
        HSVUtil hsvUtil = new HSVUtil();
        hsv = hsvUtil.RGBToHSV(rgb[0], rgb[1], rgb[2]);
        PAD pad = new PAD();
        pad.getPADFromHSV(hsv);
        return pad;
    }

    @Override
    public String toString() {
        return " Pleasure: " + pleasure + " Arousal: " + arousal + " Dominance: " + dominance;
    }
}
