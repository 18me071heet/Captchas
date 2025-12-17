package TestClass;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Scanner;

public class TwilioOTP {

	 @Test
	    public void testPhoneOtpManual() throws Exception {
	        System.setProperty("webdriver.chrome.driver",
	                "C:\\path\\to\\chromedriver.exe");

	        WebDriver driver = new ChromeDriver();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	        try {
	            driver.get("file:///C:/path/to/phone_otp.html");

	            // Wait for user to manually enter mobile number and click Send OTP
	            System.out.println("Please enter your phone number in UI and click Send OTP...");
	            new Scanner(System.in).nextLine(); // pause until user presses Enter

	            // Wait a few seconds to allow OTP to be generated
	            Thread.sleep(5000);

	            // Ask user to manually enter OTP from server console
	            System.out.print("Enter OTP displayed in console: ");
	            String otp = new Scanner(System.in).nextLine();

	            WebElement otpInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("otpInput")));
	            otpInput.sendKeys(otp);

	            driver.findElement(By.xpath("//button[text()='Submit']")).click();

	            String result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result"))).getText();
	            System.out.println("RESULT: " + result);

	            // Keep browser open for observation
	            new Scanner(System.in).nextLine();

	        } finally {
	            driver.quit();
	        }
	    }
}
