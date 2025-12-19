package TestClass;

import org.testng.annotations.Test;

import BaseClass.BaseClass_Captcha;
import PageObject.LogIn_Captcha;



public class Strum_Captcha extends BaseClass_Captcha {

	
	@Test
	void loginDetails() throws InterruptedException {
		
		
		LogIn_Captcha login = new LogIn_Captcha(driver);
		
		logger.info("Tc-01 --> Verify user is able to enter email ");
		Thread.sleep(3000);
		login.emailField(p.getProperty("email"));
		
		
		logger.info("Tc-02 --> Verify user is able to enter password ");
		Thread.sleep(3000);
		login.passField(p.getProperty("password"));
		Thread.sleep(3000);
		
	/*	 logger.info("TC-03 --> Capture CAPTCHA text");
	        String captchaValue = login.getCaptchaValue();
	        logger.info("Captured CAPTCHA value: {}", captchaValue);

	        logger.info("TC-04 --> Enter CAPTCHA");
	    	Thread.sleep(5000);
	        login.enterCaptcha(captchaValue); */
	        
	        // For regenrate captchas
	        
	        logger.info("TC-03 --> Refresh CAPTCHA and capture new value");
	        String captchaValue = login.refreshAndGetNewCaptcha();
	        logger.info("New CAPTCHA value: {}", captchaValue);

	        logger.info("TC-04 --> Enter CAPTCHA");
	        Thread.sleep(5000);
	        login.enterCaptcha(captchaValue);

	        
	
		logger.info("Tc-03 --> Verify user is able to login by clicking login button");
		Thread.sleep(5000);
		login.loginClick();
		


		
	}
}

