package com.read.image;

import java.io.File;
import net.sourceforge.tess4j.*;

public class ReadImageText {

    static Tesseract tesseract = new Tesseract();

    public static void main(String[] args) throws TesseractException {

        try {

            File image = new File("src\\main\\images\\image.png");
            // the path of your tess data folder
            // inside the extracted file
            tesseract.setDatapath("src\\main\\tessdata");
            // path of your image file
            String text = tesseract.doOCR(image);
            System.out.print(text);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }

        ReadText();

    }

    public static void ReadText() throws TesseractException {

        File image = new File("src\\main\\images\\eurotext.png");

        tesseract.setDatapath("src\\main\\tessdata");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }
}
