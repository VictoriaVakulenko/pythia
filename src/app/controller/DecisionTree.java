package app.controller;

import app.controller.AdditionalUtils.MathUtil;
import app.model.Colour;

import static app.controller.ColourFormat.HSLUtil.RGBToHSL;

public class DecisionTree implements DecisionTreeInterface{

    public static Integer classify(Colour colour){
        MathUtil mathUtil = new MathUtil();
        int r = colour.getR();
        int g = colour.getG();
        int b = colour.getB();
        Integer classT = 0;
        int [] hsl = RGBToHSL(r,g,b);
        int h = hsl[0];
        int s = hsl[1];
        int l = hsl[2];
        if(l <= 9){
            classT = 18;
        } else if(l >= 95){
            classT = 19;
        } else if(h == 0 && s == 0 && l > 14 && l < 95 || h == s && s == l || mathUtil.intervalNum(r,g,b, 10)){
            classT = 17;
        } else if(r >= b && r >= g){
            if(h >= 355 || h <= 10){
                classT = 1;
            } else {
                if(g > b){
                    if(h >= 41){
                        if(h >= 51){
                            classT = 5;
                        } else{
                            classT =4;
                        }
                    } else{
                        if(h >= 21){
                            classT = 3;
                        } else{
                            classT =2;
                        }
                    }
                } else {
                    if(h >=10){
                        if(h <= 330){
                            classT = 14;
                        } else{
                            if(h <= 345){
                                classT = 15;
                            } else {
                                classT =16;
                            }
                        }
                    }
                }
            }
        } else if(g > b){
                if(h >=141){
                    classT = 8;
                } else {
                    if(h >= 81){
                        classT = 7;
                    } else {
                        classT = 6;
                    }
                }
            } else {
                if(h >=281){
                    classT = 13;
                } else {
                    if(h >= 221){
                        if(h <= 240){
                            classT = 11;
                        } else {
                            classT = 12;
                        }
                    } else {
                        if(h <= 200){
                            classT = 9;
                        } else {
                            classT = 10;
                        }
                    }
                }
            }

        return classT;
    }

}
