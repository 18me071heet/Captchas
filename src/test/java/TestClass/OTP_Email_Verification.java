package TestClass;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Scanner;

public class OTP_Email_Verification {


	  @Test
	    public void testEmailOTP_manual() throws InterruptedException {

	        System.setProperty("webdriver.chrome.driver",
	                "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

	        WebDriver driver = new ChromeDriver();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

	        try {
	            // ---------------- OPEN APP ----------------
	            driver.get("C:\\Users\\INX\\OneDrive\\Documents\\index.html");
	            System.out.println("✅ OTP page opened");

	            // ---------------- MANUAL EMAIL ENTRY ----------------
	            System.out.println("👉 Enter EMAIL and click SEND OTP");

	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otpInput")));

	            // ---------------- MANUAL OTP ENTRY ----------------
	            System.out.println("👉 Paste OTP and press ENTER here");
	            new Scanner(System.in).nextLine();

	            // ---------------- CLICK SUBMIT ----------------
	            wait.until(ExpectedConditions.elementToBeClickable(By.id("submitBtn"))).click();

	            // ---------------- VERIFY RESULT ----------------
	            WebElement result = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(By.id("result"))
	            );

	            String resultText = result.getText();
	            System.out.println("🎯 RESULT: " + resultText);

	            // ✅ ASSERT (THIS IS THE KEY FIX)
	            Assert.assertTrue(
	                    resultText.toLowerCase().contains("verified"),
	                    "❌ OTP verification failed"
	            );

	            // Wait 3 seconds after success
	            Thread.sleep(3000);

	            System.out.println("✅ OTP verification PASSED");

	        } finally {
	            driver.quit();
	        }
	    }
}
