package TestClass;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
public class MatheMaticalCaptcha_Automation {

	@Test
	public void testMathCaptchaDirect() {
	    WebDriver driver = null;

	    try {
	        System.out.println("=== Starting Mathematical CAPTCHA automation ===");

	        // 1️⃣ Set ChromeDriver path
	        System.setProperty("webdriver.chrome.driver",
	                "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

	        driver = new ChromeDriver();

	        // 2️⃣ Load the demo page
	        driver.get("file:///C:/Users/INX/OneDrive/Documents/mathematical_captcha.html");
	        System.out.println("Page loaded successfully");

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // 3️⃣ Locate CAPTCHA element
	        WebElement captchaElement = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.id("captchaBox"))
	        );
	        System.out.println("CAPTCHA element located");

	        // 4️⃣ Read CAPTCHA text directly
	        String captchaText = captchaElement.getText().trim();
	        System.out.println("CAPTCHA text: " + captchaText);

	        // 5️⃣ Parse and compute mathematical expression
	        int captchaValue = 0;
	        if (captchaText.contains("+")) {
	            String[] parts = captchaText.split("\\+");
	            captchaValue = Integer.parseInt(parts[0].trim()) + Integer.parseInt(parts[1].trim());
	        } else if (captchaText.contains("-")) {
	            String[] parts = captchaText.split("-");
	            captchaValue = Integer.parseInt(parts[0].trim()) - Integer.parseInt(parts[1].trim());
	        } else if (captchaText.contains("*")) {
	            String[] parts = captchaText.split("\\*");
	            captchaValue = Integer.parseInt(parts[0].trim()) * Integer.parseInt(parts[1].trim());
	        } else {
	            System.out.println("No valid operator found. Exiting test.");
	            return;
	        }
	        System.out.println("Calculated CAPTCHA value: " + captchaValue);

	        // 6️⃣ Locate input box and submit button
	        WebElement inputBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("captchaInput")));
	        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button")));

	        // 7️⃣ Pause before entering CAPTCHA
	        Thread.sleep(3000);

	        // 8️⃣ Enter calculated CAPTCHA value
	        inputBox.sendKeys(String.valueOf(captchaValue));

	        // 9️⃣ Pause before clicking submit
	        Thread.sleep(3000);

	        // 10️⃣ Click submit
	        submitButton.click();
	        System.out.println("CAPTCHA submitted successfully.");

	        // 11️⃣ Wait for result text and display
	        WebElement resultText = new WebDriverWait(driver, Duration.ofSeconds(5))
	                .until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
	        System.out.println("CAPTCHA Result on Page: " + resultText.getText());

	        // 12️⃣ Optional visual confirmation before closing
	        Thread.sleep(3000);
	        System.out.println("=== Mathematical CAPTCHA automation completed ===");

	    } catch (InterruptedException e) {
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
