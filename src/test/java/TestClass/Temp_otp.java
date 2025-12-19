package TestClass;

import org.openqa.selenium.*;
import static io.restassured.RestAssured.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Scanner;
public class Temp_otp {

	  @Test
	    public void verifyOtpFlow_WithManualMobileEntry() {

	        System.setProperty("webdriver.chrome.driver",
	                "C:\\Users\\INX\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

	        WebDriver driver = new ChromeDriver();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        Scanner scanner = new Scanner(System.in);

	        try {
	            // 1️⃣ Open OTP page from server
	            driver.get("http://localhost:3000/temp_otp.html");

	            // 2️⃣ USER enters mobile number manually in UI
	            System.out.println("➡️ Enter mobile number in UI and click 'Send OTP'");
	            System.out.println("➡️ After clicking Send OTP, press ENTER here...");
	            scanner.nextLine();

	            // 3️⃣ Ask user which number was entered (for backend fetch)
	            System.out.print("Enter the SAME mobile number you entered in UI: ");
	            String phoneNumber = scanner.nextLine();

	            // 4️⃣ Fetch OTP from backend (TEST API)
	            String otp =
	                    given()
	                    .when()
	                        .get("http://localhost:3000/get-otp/" + phoneNumber)
	                    .then()
	                        .statusCode(200)
	                        .extract()
	                        .jsonPath()
	                        .getString("otp");

	            System.out.println("Fetched OTP from server: " + otp);

	            // 5️⃣ Enter OTP automatically
	            wait.until(ExpectedConditions.elementToBeClickable(By.id("otpInput")))
	                .sendKeys(otp);

	            // 6️⃣ Submit OTP
	            driver.findElement(By.xpath("//button[text()='Submit']")).click();

	            // 7️⃣ Validate result
	            String result =
	                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")))
	                        .getText();

	            Assert.assertEquals(result, "OTP VERIFIED ✅");
	            System.out.println("✅ OTP verification PASSED");

	        } finally {
	            driver.quit();
	            scanner.close();
	        }
	    }
	
}
