package com.read.image;

import java.io.File;
import net.sourceforge.tess4j.*;

public class ReadImageText {

    public static void main(String[] args) throws TesseractException {

        ReadText();

    }

    public static void ReadText() throws TesseractException {

        File image = new File("C:\\Users\\fitim\\IdeaProjects\\JideaProjects\\src\\main\\java\\com\\read.image\\eurotext.png");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Users/fitim/IdeaProjects/JideaProjects/src/main/tessdata");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }
}
