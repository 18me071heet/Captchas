package TestClass;
//Selenium imports
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//TestNG
import org.testng.annotations.Test;

//Java utilities
import java.time.Duration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.search.SubjectTerm;
public class OtpVerification_Automation {

 @Test
 public void testEmailOTP() throws Exception {

     System.setProperty("webdriver.chrome.driver",
             "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

     WebDriver driver = new ChromeDriver();
     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

     try {
         driver.get("file:///C:/Users/INX/OneDrive/Documents/otp_demo.html");
         System.out.println("Page loaded successfully");

         Thread.sleep(2000); // simulate OTP sending delay

         // === Fetch OTP from email ===
         String otp = fetchOTPFromEmail(
                 "your_email@gmail.com",
                 "your_email_password",
                 "Your OTP Subject"
         );

         if (otp == null) {
             System.out.println("OTP not received. Exiting test.");
             return;
         }

         System.out.println("Fetched OTP: " + otp);

         WebElement otpInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("otpInput")));
         WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitBtn")));

         otpInput.sendKeys(otp);
         Thread.sleep(2000);
         submitBtn.click();
         System.out.println("OTP submitted");

         WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
         System.out.println("Result: " + result.getText());

         Thread.sleep(2000);

     } finally {
         driver.quit();
         System.out.println("Browser closed");
     }
 }

 // ===================== Email OTP Reader ======================
 private String fetchOTPFromEmail(String username, String password, String subject) {
     String otp = null;

     try {
         Properties props = new Properties();
         props.put("mail.store.protocol", "imaps");

         Session session = Session.getInstance(props);
         Store store = session.getStore("imaps");

         store.connect("imap.gmail.com", 993, username, password);

         Folder inbox = store.getFolder("INBOX");
         inbox.open(Folder.READ_ONLY);

         // Filter emails by subject
         Message[] messages = inbox.search(new SubjectTerm(subject));

         if (messages.length > 0) {

             Message latest = messages[messages.length - 1];
             Object contentObj = latest.getContent();
             String content = contentObj.toString();

             // Extract a 6-digit OTP
             Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
             Matcher matcher = pattern.matcher(content);

             if (matcher.find()) {
                 otp = matcher.group();
                 System.out.println("Extracted OTP: " + otp);
             } else {
                 System.out.println("OTP pattern not found in email.");
             }
         } else {
             System.out.println("No email found with subject: " + subject);
         }

         inbox.close(false);
         store.close();

     } catch (Exception e) {
         e.printStackTrace();
     }

     return otp;
 }
}