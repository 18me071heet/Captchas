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

public class Recaptcha_Automation {

	@Test
	public void testMathCaptchaMultiple() {
	    WebDriver driver = null;

	    try {
	        System.out.println("=== Starting Mathematical CAPTCHA automation ===");

	        System.setProperty("webdriver.chrome.driver",
	                "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

	        driver = new ChromeDriver();

	        driver.get("file:///C:/Users/INX/OneDrive/Documents/mathematical_captcha.html");
	        System.out.println("Page loaded successfully");

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Locate elements
	        WebElement captchaElement = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.id("captchaBox"))
	        );
	        WebElement inputBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("captchaInput")));
	        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button")));
	        WebElement resendButton = driver.findElement(By.xpath("//button[text()='Regenerate CAPTCHA']"));

	        // Number of times to solve CAPTCHA
	        int attempts = 2; // first CAPTCHA + 1 resend

	        for (int i = 1; i <= attempts; i++) {
	            System.out.println("=== Attempt #" + i + " ===");

	            // Optional: pause to visually see CAPTCHA
	            Thread.sleep(2000);

	            // Read CAPTCHA text directly
	            String captchaText = captchaElement.getText().trim();
	            System.out.println("CAPTCHA text: " + captchaText);

	            // Compute CAPTCHA
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
	                System.out.println("No valid operator found. Exiting attempt.");
	                continue;
	            }
	            System.out.println("Calculated CAPTCHA value: " + captchaValue);

	            // Clear input and enter answer
	            inputBox.clear();
	            Thread.sleep(1000);
	            inputBox.sendKeys(String.valueOf(captchaValue));
	            Thread.sleep(1000);

	            // Submit
	            submitButton.click();
	            System.out.println("CAPTCHA submitted.");

	            // Wait for result
	            WebElement resultText = new WebDriverWait(driver, Duration.ofSeconds(5))
	                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
	            System.out.println("Result: " + resultText.getText());

	            // If not last attempt, click resend
	            if (i < attempts) {
	                Thread.sleep(2000);
	                resendButton.click();
	                System.out.println("Clicked Resend CAPTCHA.");
	                Thread.sleep(2000); // wait for new CAPTCHA to appear
	            }
	        }

	        Thread.sleep(2000);
	        System.out.println("=== Mathematical CAPTCHA automation completed ===");

	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    } finally {
	        if (driver != null) {
	            driver.quit();
	            System.out.println("Browser closed");
	        }
	    }

	}
}
