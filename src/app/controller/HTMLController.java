package app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTMLController {

   public static StringBuilder builder;

   private static String sep = System.lineSeparator();

   public HTMLController(){
       builder = new StringBuilder();
   }

   public static void startHTML(){
       builder.append("<!DOCTYPE html>" + sep +"<html>"+sep);
   }

   public static void endHTML(){
       builder.append("</html>"+sep);
   }

   public static void setBody(){
       builder.append("<body>"+sep);
   }

    public static void endBody(){
        builder.append("</body>"+sep);
    }

    public static String setStyle(String path){
        return "<link rel=\"stylesheet\" href=\""+path+"\">";
    }

    public static void setHead(String title, String path){
        builder.append("<head><title>").append(title).append("</title>"+sep).append("<meta charset=\"utf-8\">"+sep).
                append(setStyle(path)).append(sep+"</head>"+sep);
    }

    public static void loadImage(String path){
        builder.append("<img src=\"").append(path).append("\">"+sep);
    }

    public static void setHTitle(int hNum, String title){
        builder.append("<h"+hNum).append(">"+title).append("</h"+hNum+">"+sep);
    }

    public static void setText(String text){
        builder.append("<p>"+text+"</p>"+sep);
    }

    public static void setColourBox(String hex, String text){
        builder.append("<div style=\""+ "background:"+ hex + "\" id=\"colour-box\">").append("</div>")
                .append("<p>"+text+"</p>"+sep);
    }

    public static void saveToHTMLFile(String fileName) {
        System.out.println("Saving HTML file...");
        try {
            Files.write(Paths.get(fileName), builder.toString().getBytes());
        } catch (IOException ex) {
            System.out.println("Error saving HTML file: " + ex.getMessage());
        }
    }
}
