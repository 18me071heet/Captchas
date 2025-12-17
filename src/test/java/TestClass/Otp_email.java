package TestClass;

import org.openqa.selenium.*;
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        try {
            // ------------------- OPEN DEMO PAGE -------------------
            driver.get("C:\\Users\\INX\\OneDrive\\Documents\\index.html");
            System.out.println("Demo page loaded");

            String demoTab = driver.getWindowHandle();

            // ------------------- ENTER EMAIL MANUALLY -------------------
            System.out.println("👉 Enter EMAIL manually (yopmail.com only) and CLICK 'Send OTP' now.");

            // Wait for Send OTP button and click
            WebElement sendOtpBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("sendOtpBtn")));
            sendOtpBtn.click();

            // ------------------- HANDLE ALERT IMMEDIATELY -------------------
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                System.out.println("Alert displayed: " + alert.getText());
                alert.accept();
                Thread.sleep(1000);
            } catch (TimeoutException e) {
                System.out.println("No alert displayed.");
            }

            // ------------------- READ ENTERED EMAIL -------------------
            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("emailInput")));
            String enteredEmail = emailInput.getAttribute("value").trim();

            if (enteredEmail.isEmpty()) {
                throw new RuntimeException("❌ Email field is empty!");
            }
            if (!enteredEmail.endsWith("@yopmail.com")) {
                throw new RuntimeException("❌ Please use yopmail.com email only!");
            }

            String yopUser = enteredEmail.split("@")[0];
            System.out.println("📧 Opening Yopmail inbox for: " + yopUser);

            // ------------------- OPEN YOPMAIL TAB -------------------
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://yopmail.com/en/?" + yopUser);

            Thread.sleep(6000);
            driver.navigate().refresh();
            Thread.sleep(8000);

            // ------------------- OPEN LATEST EMAIL -------------------
            driver.switchTo().frame("ifinbox");
            WebElement firstMail = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.m")));
            firstMail.click();

            Thread.sleep(5000);

            driver.switchTo().defaultContent();
            driver.switchTo().frame("ifmail");

            String mailText = driver.findElement(By.tagName("body")).getText();
            Matcher matcher = Pattern.compile("\\b\\d{6}\\b").matcher(mailText);

            String fetchedOtp = matcher.find() ? matcher.group() : null;

            if (fetchedOtp == null) {
                throw new RuntimeException("❌ OTP not found in email!");
            }

            System.out.println("✅ OTP fetched: " + fetchedOtp);

            // ------------------- BACK TO DEMO PAGE -------------------
            driver.switchTo().window(demoTab);

            WebElement otpInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("otpInput")));
            otpInput.click();
            otpInput.sendKeys(Keys.CONTROL + "a");
            otpInput.sendKeys(Keys.DELETE);
            otpInput.sendKeys(fetchedOtp);

            System.out.println("👉 OTP entered automatically. Click SUBMIT manually.");

            // ------------------- WAIT FOR RESULT -------------------
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
            WebElement result = driver.findElement(By.id("result"));
            System.out.println("🎯 RESULT: " + result.getText());

            System.out.println("✅ Test completed. Browser remains open for observation.");

        } finally {
            // Do not close browser automatically if you want to observe
            // driver.quit();
        }
    }
}