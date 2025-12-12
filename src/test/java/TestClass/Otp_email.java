package TestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Otp_email {

    @Test
    public void testEmailOTP() throws Exception {

        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // ================= Demo Page =================
            driver.get("file:///C:/Users/INX/OneDrive/Documents/otp_demo.html");
            System.out.println("Demo page loaded");

            // ================= Yopmail Inbox =================
            String yopUser = "kennedy.parks"; // your Yopmail username
            String inboxUrl = "https://yopmail.com/en/?" + yopUser; // Updated URL structure
            driver.get(inboxUrl);

            // Wait for the email iframe to load
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("ifmail"));

            // Read email content
            WebElement emailBody = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
            String content = emailBody.getText();
            System.out.println("Email Content: " + content);

            // Extract 6-digit OTP
            Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
            Matcher matcher = pattern.matcher(content);
            String otp = matcher.find() ? matcher.group() : null;

            if (otp == null) {
                System.out.println("OTP not found in email. Exiting test.");
                return;
            }

            System.out.println("Fetched OTP: " + otp);

            // ================= Submit OTP =================
            driver.switchTo().defaultContent(); // back to demo page
            WebElement otpInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("otpInput")));
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitBtn")));

            otpInput.sendKeys(otp);
            Thread.sleep(1000);
            submitBtn.click();

            WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
            System.out.println("Result: " + result.getText());

            Thread.sleep(2000);

        } finally {
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}