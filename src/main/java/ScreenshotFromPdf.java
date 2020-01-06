import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;

public class ScreenshotFromPdf {

    public static void Pdf2Image(String html, WebDriver driver) throws IOException, InterruptedException {

        Thread.sleep(5000);
        URL url=new URL(html);
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

    public static void deleteFileWithExtension(String directory, String extension) throws IOException {

        File dir = new File(directory);

        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(extension) && !file.delete()) {
                throw new IOException();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // do something here...
        System.setProperty("webdriver.chrome.driver","C:\\Users\\timok\\IdeaProjects\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.vandevenbv.nl/dynamics/modules/SFIL0200/view.php?fil_Id=5515");
        String url = driver.getCurrentUrl();
        ScreenshotFromPdf.Pdf2Image(url, driver);

        String directory = System.getProperty("user.dir") + "\\src\\main\\DataFiles";

        // Extension.
        String extension = ".jpg";

        try {
            deleteFileWithExtension(directory, extension);
        } catch (IOException e) {
            System.out.println("Problem occurs when deleting files");
            e.printStackTrace();
        }
    }
}

