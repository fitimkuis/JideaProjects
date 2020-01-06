import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.List;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.imageio.ImageIO;

public class ScreenshotFromPdf {

    public static void Pdf2Image(String html, WebDriver driver) throws IOException, InterruptedException {

        Thread.sleep(5000);
        URL url= new URL(html);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        InputStream is=connection.getInputStream();

        PDDocument document = PDDocument.load(is);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            File outputFile = new File(System.getProperty("user.dir") + "/src/main/DataFiles/" + page + "image.jpg");
            ImageIO.write(bim, "jpg", outputFile);
        }
        document.close();
        driver.close();
    }

    public static void Pdf2Image2(String html, WebDriver driver) throws IOException, InterruptedException {

        Thread.sleep(5000);
        //URL url= new URL(html);
        //HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        //InputStream is=connection.getInputStream();

        BufferedInputStream in = new BufferedInputStream(new URL(html).openStream());

        PDDocument document = PDDocument.load(in);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        for (int page = 0; page < document.getNumberOfPages(); ++page) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            File outputFile = new File(System.getProperty("user.dir") + "/src/main/DataFiles/" + page + "image.jpg");
            ImageIO.write(bim, "jpg", outputFile);
        }
        document.close();
        driver.close();
    }

    public static void Pdf2Image3(String html, WebDriver driver) throws IOException, InterruptedException {

        Thread.sleep(5000);
        URL url1 = new URL(html);

        byte[] ba1 = new byte[1024];
        int baLength;

        String downPath = "C:\\Users\\timok\\Downloads";

        try {
            // Contacting the URL
            System.out.print("Connecting to " + url1.toString() + " ... ");
            URLConnection urlConn = url1.openConnection();

            FileOutputStream fos1 = new FileOutputStream(downPath+"/samplePDF.pdf");

            BufferedInputStream in = new BufferedInputStream(new URL(html).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(downPath+"/samplePDF.pdf");
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            in.close();
            fileOutputStream.close();

            // Checking whether the URL contains a PDF
            //if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
            if (url1.equals("")) {
                System.out.println("FAILED.\n[Sorry. This is not a PDF.]");
            } else {
                try {

                    // Read the PDF from the URL and save to a local file
                    InputStream is1 = url1.openStream();
                    while ((baLength = is1.read(ba1)) != -1) {
                        fos1.write(ba1, 0, baLength);
                    }
                    fos1.flush();
                    fos1.close();
                    is1.close();

                    // Load the PDF document and display its page count
                    System.out.print("DONE.\nProcessing the PDF ... ");

                    PDDocument doc = new PDDocument();

                    if (doc.isEncrypted())
                    {
                        System.out.print("document in crypted, cannot be saved ... ");
                    }
                    try {
                        doc.load(new File(downPath+"/samplePDF.pdf"));
                        System.out.println("DONE.\nNumber of pages in the PDF is " + doc.getNumberOfPages());

                        PDDocument document = PDDocument.load(new File(downPath+"/samplePDF.pdf"));
                        PDFRenderer pdfRenderer = new PDFRenderer(document);

                        for (int page = 0; page < document.getNumberOfPages(); ++page) {
                            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                            File outputFile = new File(System.getProperty("user.dir") + "/src/main/DataFiles/" + page + "image.jpg");
                            ImageIO.write(bim, "jpg", outputFile);
                        }

                        doc.close();
                        document.close();
                    } catch (Exception e) {
                        System.out.println("FAILED.\n[" + e.getMessage() + "]");
                    }

                } catch (ConnectException ce) {
                    System.out.println("FAILED.\n[" + ce.getMessage() + "]\n");
                }
            }

        } catch (NullPointerException npe) {
            System.out.println("FAILED.\n[" + npe.getMessage() + "]\n");
        }


        //document.close();
        driver.close();
    }

    public static void downloadFileFromURL(String urlString, File destination) {
        try {
            URL website = new URL(urlString);
            ReadableByteChannel rbc;
            rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(destination);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFileWithExtension(String directory, String extension) throws IOException {

        File dir = new File(directory);

        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(extension) && !file.delete()) {
                throw new IOException();
            }
        }
    }

    public static WebDriver setChromeOptions(String downloadPath){

        ChromeOptions options = new ChromeOptions();
        //String downloadPath = folder;
        //String downloadsPath = System.getProperty("user.home") + "/Downloads";
        System.out.println ("downloadpath "+downloadPath);

        Map<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadPath);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("plugins.plugins_disabled", "Chrome PDF Viewer");
        //options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--test-type");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-software-rasterizer");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        System.setProperty("webdriver.chrome.driver","C:\\Users\\timok\\IdeaProjects\\chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\timok1\\Desktop\\chromedriver\\chromedriver.exe")
        WebDriver driver = new ChromeDriver(cap);
        return driver;
    }

    public static String getFileFromFolder(String downloadPath){

        File path = new File(downloadPath);
        File[] files = path.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                // Automatically weeds out directories
                return name.toLowerCase().endsWith(".pdf");
            }
        });

        String fileName = "";
        for (File file : files) {
            System.out.println(file.getName());
            fileName = file.getName();
        }

        return fileName;
    }

    public static List<String> openPfdFile(String pdfFile) throws IOException {

        List<String> lines = new ArrayList<>();

        //PDDocument pdfDocument = PDDocument.load(new File("C:/xxxx/fitim/Desktop/data/file2.pdf"))
        PDDocument pdfDocument = PDDocument.load(new File(pdfFile));
        pdfDocument.getClass();
        if (!pdfDocument.isEncrypted()) {

            PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
            pdfTextStripperByArea.setSortByPosition(Boolean.TRUE);

            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            String pdfFileInText = pdfTextStripper.getText(pdfDocument);

            lines = Arrays.asList(pdfFileInText.split("\\r?\\n"));
            for (String line : lines) {
                System.out.println(line);
            }

        }
        return lines;
    }

    //Cross platform solution to view a PDF file
        public static void openInBrowser(String pdfFilePath) {

            try {

                File pdfFile = new File(pdfFilePath);
                if (pdfFile.exists()) {

                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(pdfFile);
                    } else {
                        System.out.println("Awt Desktop is not supported!");
                    }

                } else {
                    System.out.println("File is not exists!");
                }

                System.out.println("Done");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    public static void main(String[] args) throws IOException, InterruptedException {
        // do something here...
        //System.setProperty("webdriver.chrome.driver","C:\\Users\\timok\\IdeaProjects\\chromedriver.exe");
        //WebDriver driver = new ChromeDriver();



        WebDriver driver = setChromeOptions(System.getProperty("user.dir") + "\\src\\main\\DataFiles");


        //driver.get("http://www.vandevenbv.nl/dynamics/modules/SFIL0200/view.php?fil_Id=5515");
        driver.get("http://aplaidshirt.epizy.com/samplePDF.pdf");
        Thread.sleep(3000);
        String url = driver.getCurrentUrl();
        //URLEncoder.encode(url, "UTF-8");
        //System.out.println(url);
        String rep1 = url.replaceAll("[^a-zA-Z2-9-.-:-/-//]", "");
        //System.out.println(rep1);
        //System.out.println(rep1.substring(0, rep1.length() - 2));
        String rep2 = rep1.substring(0, rep1.length() - 2);
        /*
        String destination = System.getProperty("user.dir") + "\\src\\main\\DataFiles\\samplePDF.pdf";
        File dest = new File(destination);
        downloadFileFromURL(url, dest);
        ScreenshotFromPdf.Pdf2Image3(url, driver);
        */


        //save and download pdf file from disk
        driver.get("https://gofile.io/?c=WYPqpZ");
        Thread.sleep(5000);
        driver.findElement(By.xpath("/html/body/div[2]/div/div[10]/button[2]")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("fileInfoDownload")).click();
        Thread.sleep(3000);

        String pdfDir = System.getProperty("user.dir") + "\\src\\main\\DataFiles";
        String pdfFile = getFileFromFolder(pdfDir);
        //openInBrowser(pdfDir+"/"+pdfFile); //open file in edge, don't know why, set chrome as default browser
        //this url should be get from chrome
        String pdfUrl = "file:///C:/Users/timok/IdeaProjects/JideaProjects/src/main/DataFiles/samplePDF.pdf";
        //open downloaded pdf file in browser
        driver.get("file:///C:/Users/timok/IdeaProjects/JideaProjects/src/main/DataFiles/samplePDF.pdf");
        String url2 = driver.getCurrentUrl();
        ScreenshotFromPdf.Pdf2Image2(url2, driver);

        //delete all .jpg files
        String directory = System.getProperty("user.dir") + "\\src\\main\\DataFiles";

        // Extension.
        String extension = ".jpg";
/*
        try {
            deleteFileWithExtension(directory, extension);
        } catch (IOException e) {
            System.out.println("Problem occurs when deleting files");
            e.printStackTrace();
        }*/
    }
}

