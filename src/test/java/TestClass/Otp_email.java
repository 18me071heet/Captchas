package TestClass;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Otp_email {

    @Test
    public void testEmailOTP() throws Exception {

        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // ------------------- OPEN DEMO PAGE -------------------
            driver.get("file:///C:/Users/INX/OneDrive/Documents/index.html");
            System.out.println("Demo page loaded");
            Thread.sleep(2000);

            String demoTab = driver.getWindowHandle();

            // ------------------- ENTER EMAIL AND SEND OTP -------------------
            String yopEmail = "kennedy.parks@yopmail.com";

            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("emailInput")));
            emailInput.clear();
            emailInput.sendKeys(yopEmail);
            Thread.sleep(1000);

            WebElement sendOtpBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("sendOtpBtn")));
            sendOtpBtn.click();
            System.out.println("Send OTP button clicked");
            Thread.sleep(2000);

            // ------------------- HANDLE ALERT -------------------
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                System.out.println("Alert displayed: " + alert.getText());
                alert.accept();
            } catch (Exception e) {
                System.out.println("No alert displayed.");
            }

            // ------------------- FETCH OTP FROM YOPMAIL -------------------
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://yopmail.com/en/?" + yopEmail.split("@")[0]);
            System.out.println("Yopmail inbox opened");
            Thread.sleep(5000);

            // Refresh once
            driver.navigate().refresh();
            Thread.sleep(7000);

            // Open inbox iframe
            driver.switchTo().frame("ifinbox");
            WebElement firstMail = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.m")));
            firstMail.click();
            Thread.sleep(7000);

            // Switch to email body
            driver.switchTo().defaultContent();
            driver.switchTo().frame("ifmail");

            String mailText = driver.findElement(By.tagName("body")).getText();
            Matcher m = Pattern.compile("\\b\\d{6}\\b").matcher(mailText);
            String fetchedOtp = m.find() ? m.group() : null;
            System.out.println("Fetched OTP from email: " + fetchedOtp);

            // ------------------- RETURN TO DEMO PAGE -------------------
            driver.switchTo().window(demoTab);
            Thread.sleep(2000);

            // Enter OTP into input
            WebElement otpInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("otpInput")));
            otpInput.click();
            Thread.sleep(500);
            otpInput.sendKeys(Keys.CONTROL + "a");
            otpInput.sendKeys(Keys.DELETE);
            otpInput.sendKeys(fetchedOtp);
            Thread.sleep(1000);

            // Submit OTP
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitBtn")));
            submitBtn.click();
            Thread.sleep(2000);

            WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
            System.out.println("RESULT: " + result.getText());

            System.out.println("Test finished. Browser will stay open for observation.");
            new java.util.Scanner(System.in).nextLine(); // Keep browser open

        } finally {
            driver.quit();
            System.out.println("Browser closed");
        }
    }
}