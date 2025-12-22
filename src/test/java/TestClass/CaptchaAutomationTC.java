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
import java.util.Scanner;

public class CaptchaAutomationTC {

	 @Test
	    public void testCaptcha() {

	        System.setProperty("webdriver.chrome.driver",
	                "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

	        WebDriver driver = new ChromeDriver();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        try {
	            System.out.println("=== Starting CAPTCHA automation ===");

	            driver.get("file:///C:/Users/INX/OneDrive/Documents/captcha_demo.html");
	            System.out.println("Page loaded successfully");

	            // 1️⃣ Locate CAPTCHA
	            WebElement captchaElement = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(By.id("captchaBox"))
	            );

	            // 2️⃣ Screenshot CAPTCHA
	            File src = captchaElement.getScreenshotAs(OutputType.FILE);
	            BufferedImage img = ImageIO.read(src);
	            File captchaFile = new File("captcha.png");
	            ImageIO.write(img, "png", captchaFile);
	            System.out.println("CAPTCHA screenshot saved");

	            // 3️⃣ OCR using Tesseract CLI
	            ProcessBuilder pb = new ProcessBuilder(
	                    "C:\\Program Files\\Tesseract-OCR\\tesseract.exe",
	                    captchaFile.getAbsolutePath(),
	                    "captcha_output",
	                    "-l", "eng"
	            );
	            pb.redirectErrorStream(true);
	            Process process = pb.start();
	            process.waitFor();

	            // 4️⃣ Read OCR output
	            BufferedReader br = new BufferedReader(new FileReader("captcha_output.txt"));
	            String ocrResult = br.readLine();
	            br.close();

	            if (ocrResult == null || ocrResult.isEmpty()) {
	                System.out.println("❌ OCR failed");
	                return;
	            }

	            String captchaText = ocrResult.replaceAll("[^0-9]", "");
	            System.out.println("Extracted CAPTCHA: " + captchaText);

	            // 5️⃣ Enter CAPTCHA
	            WebElement inputBox = wait.until(
	                    ExpectedConditions.elementToBeClickable(By.id("captchaInput"))
	            );
	            inputBox.sendKeys(captchaText);

	            driver.findElement(By.tagName("button")).click();
	            System.out.println("CAPTCHA submitted");

	            // 6️⃣ Wait for result
	            WebElement result = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(By.id("result"))
	            );
	            System.out.println("🎯 RESULT ON UI: " + result.getText());

	            // 7️⃣ Observe UI
	            Thread.sleep(5000);

	            System.out.println("=== CAPTCHA automation completed ===");

	        } catch (Exception e) {
	            System.err.println("❌ Error during CAPTCHA automation");
	            e.printStackTrace();
	        }

	        // 🔴 DO NOT AUTO CLOSE
	        System.out.println("⏸ Press ENTER to close browser...");
	        new Scanner(System.in).nextLine();
	        driver.quit();
	    }
}

