package app.controller.AdditionalUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtil {

    FileWriter fileWriter;
    PrintWriter printWriter;
    String sep = System.lineSeparator();

    public FileUtil(String path) throws IOException{
        fileWriter = new FileWriter(path);
        printWriter = new PrintWriter(fileWriter);
    }

    public void writeToFile(String text, Object object) throws IOException{
        printWriter.printf(text, object, sep);
    }

    public void closeFile(){
        printWriter.close();
    }

}
