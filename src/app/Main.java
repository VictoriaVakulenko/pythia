package app;

import app.controller.*;
import app.controller.ColourFormat.RGBUtil;
import app.model.Colour;
import app.model.PAD;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static app.controller.DecisionTree.classify;
import static app.controller.DisplayController.*;


public class Main {
    public static void main(String[] args) throws IOException {
        Map<Integer, ArrayList<Colour>> classes = new HashMap<>();
        ArrayList<PAD> pads = new ArrayList<>();
        Integer classT;
        int volume, count;
        int [] rgb;
        String path = "D:/Projects/workspace_oxygen/FinalProject/sources/street_advertising/test_image_2.jpg";
        ClassifierController classifier = new ClassifierController();
        HTMLController html = new HTMLController();
        RGBUtil rgbUtil = new RGBUtil();
        PAD padController = new PAD();
        PADController controller = new PADController();

        BufferedImage img = ImageIO.read(new File(path));

        MMCQ.CMap result = PaletteController.getColorMap(img, 3);
        MMCQ.VBox dominantColor = result.vboxes.get(0);
        rgb = dominantColor.avg(false);
        volume = dominantColor.volume(false);
        count = dominantColor.count(false);
        classT = classify(new Colour(rgb[0], rgb[1], rgb[2], volume, count));
        classifier.createClass(classes, classT, new Colour(rgb[0], rgb[1], rgb[2], volume, count));
        html.startHTML();
        html.setHead("Image Processing", "style.css");
        html.setBody();
        html.loadImage(path);
        html.setText("Image Folder: "+path);
        html.setHTitle(3, "Emotional state based only on Saturnation and Brightness");
     /*   html.setColourBox(getHex(dominantColor.avg(false)), getHex(dominantColor.avg(false)));
        html.setText("Class number: " + classT);*/
        result = PaletteController.getColorMap(img, 70);
        int sum_volume = 0, sum_count = 0;
        for(MMCQ.VBox vbox : result.vboxes){
            if(vbox.count(false) > 100) {
                rgb = vbox.avg(false);
                int r = rgb[0];
                int g = rgb[1];
                int b = rgb[2];
                volume = vbox.volume(false);
                count = vbox.count(false);
                sum_count += count;
                sum_volume += volume;

                Colour colour = new Colour(r, g, b, volume, count);
                System.out.println(colour);
                classT = classify(colour);
                if(!classes.containsKey(classT)){
                    classifier.createClass(classes,classT,colour);
                } else {
                  classes.get(classT).add(colour);
                }
                html.setColourBox(getHex(vbox.avg(false)),getHex(vbox.avg(false)));
                html.setText("Class number: " + classT);
            }
        }
        System.out.println(sum_count + " " + sum_volume);
        for(Map.Entry<Integer, ArrayList<Colour>> item : classes.entrySet()){
            ArrayList<Colour> colours = item.getValue();
            html.setText("Class #" + item.getKey().toString());
            Colour tmp = classifier.getAverageRepresentative(colours);
            for(Colour c : colours){
               rgbUtil.setRGBArray(c, rgb);
              html.setColourBox(getHex(rgb), getHSL(rgb)+ " " + getRGB(rgb) + " " + getHSV(rgb));
            }
            rgbUtil.setRGBArray(tmp,rgb);
            html.setText("Average representative: ");
            html.setColourBox(getHex(rgb), getHSL(rgb)+ " "+ getRGB(rgb) + " " + getHSV(rgb)+ " "+ getPAD(rgb));
            PAD pad = padController.getPAD(rgb);
            pads.add(pad);
        }
        List<String> actn = new ArrayList<>();
        for(PAD p : pads){

            actn.add(getDirectAssociation(p));
            html.setText(getDirectAssociation(p));
        }
        Set<String> set = new LinkedHashSet<>(actn);
       /* for(String s : set){
            html.setText(s);
        }*/
        html.endBody();
        html.endHTML();
        html.saveToHTMLFile("src/app/view/test.html");

        //classifier.showStatistics(classes);
        classifier.statisticsToFile(classes, "src/app/view/statistics.txt");
        //controller.showPAD(pads);
        //controller.showPredicates(pads);
        //controller.showDirectAssociation(pads);
    }

}
