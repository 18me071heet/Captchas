package TestClass;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.nio.file.Paths;
import com.google.common.io.Files;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class CaptchaAutomationTC {

	 @Test
	    public void testCaptcha() {
	        WebDriver driver = null;

	        try {
	            System.out.println("=== Starting CAPTCHA automation ===");

	            // 1️⃣ Set ChromeDriver path
	            System.setProperty("webdriver.chrome.driver",
	                    "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

	            driver = new ChromeDriver();

	            // 2️⃣ Load the demo page
	            driver.get("file:///C:/Users/INX/OneDrive/Documents/captcha_demo.html");
	            System.out.println("Page loaded successfully");

	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	            // 3️⃣ Locate CAPTCHA element
	            WebElement captchaElement = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(By.id("captchaBox"))
	            );
	            System.out.println("CAPTCHA element located");

	            // 4️⃣ Capture screenshot of CAPTCHA
	            File src = captchaElement.getScreenshotAs(OutputType.FILE);
	            BufferedImage img = ImageIO.read(src);
	            File captchaFile = new File("captcha.png");
	            ImageIO.write(img, "png", captchaFile);
	            System.out.println("CAPTCHA screenshot saved: " + captchaFile.getAbsolutePath());

	            // 5️⃣ Run Tesseract OCR via command line
	            String outputFile = "captcha_output";
	            ProcessBuilder pb = new ProcessBuilder(
	                    "C:\\Program Files\\Tesseract-OCR\\tesseract.exe",
	                    captchaFile.getAbsolutePath(),
	                    outputFile,
	                    "-l", "eng"
	            );
	            pb.redirectErrorStream(true);
	            Process process = pb.start();
	            process.waitFor();

	            // 6️⃣ Read OCR output (Java-version compatible)
	            File outputTxt = new File(outputFile + ".txt");
	            StringBuilder sb = new StringBuilder();
	            try (BufferedReader br = new BufferedReader(new FileReader(outputTxt))) {
	                String line;
	                while ((line = br.readLine()) != null) {
	                    sb.append(line);
	                }
	            }
	            String ocrResult = sb.toString().trim();
	            System.out.println("Raw OCR result: " + ocrResult);

	            if (ocrResult.isEmpty()) {
	                System.out.println("OCR returned no text. Exiting test.");
	                return;
	            }

	            // 7️⃣ Extract letters and numbers only
	            String captchaText = ocrResult.replaceAll("[^a-zA-Z0-9]", "").trim();
	            if (captchaText.isEmpty()) {
	                System.out.println("OCR did not detect valid text. Exiting test.");
	                return;
	            }
	            System.out.println("Extracted CAPTCHA: " + captchaText);

	            // 8️⃣ Locate input box and submit button
	            WebElement inputBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("captchaInput")));
	            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button")));

	            // 9️⃣ Fill CAPTCHA and submit
	            inputBox.sendKeys(captchaText);
	            submitButton.click();
	            System.out.println("CAPTCHA submitted successfully.");

	            Thread.sleep(2000); // small delay for demonstration
	            System.out.println("=== CAPTCHA automation completed ===");

	        } catch (IOException | InterruptedException e) {
	            System.err.println("Error during CAPTCHA automation: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            if (driver != null) {
	                driver.quit();
	                System.out.println("Browser closed");
	            }
	        }
	    }
}

