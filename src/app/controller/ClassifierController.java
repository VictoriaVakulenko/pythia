package app.controller;

import app.controller.AdditionalUtils.FileUtil;
import app.model.Colour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ClassifierController {

    String sep = System.lineSeparator();


    public ClassifierController(){}

    public void createClass(Map<Integer, ArrayList<Colour>> classes, Integer classT, Colour colour){
        ArrayList<Colour> color = new ArrayList<>();
        color.add(colour);
        classes.put(classT, color);
    }

    public void showStatistics(Map<Integer, ArrayList<Colour>> classes){
        for(Map.Entry<Integer, ArrayList<Colour>> item : classes.entrySet()){
            System.out.printf("Class: %d Size: %d %s", item.getKey(), item.getValue().size(),sep);
            System.out.printf("Number of total pixels: %d  %s", getSumCount(item.getValue()), sep);
            System.out.printf("Number of total volume: %d %s", getSumVolume(item.getValue()),sep);
            System.out.printf("The average representative of the class: %s %s", getAverageRepresentative(item.getValue()),sep);
            System.out.printf("Values: %s %s", item.getValue().toString(), sep);
        }
    }

    public void statisticsToFile(Map<Integer, ArrayList<Colour>> classes, String path) throws IOException{
        FileUtil fileUtil = new FileUtil(path);
        for(Map.Entry<Integer, ArrayList<Colour>> item : classes.entrySet()){
            fileUtil.writeToFile("Class: %d ", item.getKey());
            fileUtil.writeToFile("Size: %d ", item.getValue().size());
            fileUtil.writeToFile("Number of total pixels: %d ", getSumCount(item.getValue()));
            fileUtil.writeToFile("Number of total volume: %d ", getSumVolume(item.getValue()));
            fileUtil.writeToFile("The average representative of the class: %s ", getAverageRepresentative(item.getValue()));
            fileUtil.writeToFile("Values: %s ", item.getValue().toString());
            fileUtil.writeToFile("%s", sep);
        }
        fileUtil.closeFile();
    }

    public int getSumCount(ArrayList<Colour> colours){
        int sumCount = 0;
            for(Colour c : colours){
                sumCount += c.getCount();
            }
        return sumCount;
    }

    public int getSumVolume(ArrayList<Colour> colours){
        int sumVolume = 0;
        for(Colour c : colours){
            sumVolume += c.getVolume();
        }
        return sumVolume;
    }

    public Colour getAverageRepresentative(ArrayList<Colour> colours){
        Colour colour;
        int size = colours.size();
        int r, g, b;
        int rBuf = 0 , gBuf = 0, bBuf = 0;
        for(Colour c : colours){
            rBuf += c.getR();
            gBuf += c.getG();
            bBuf += c.getB();
        }
        r = rBuf/size;
        g = gBuf/size;
        b = bBuf/size;
        colour = new Colour(r,g,b);
        return colour;
    }

}
