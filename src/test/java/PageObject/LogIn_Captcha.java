package PageObject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogIn_Captcha {
	
	public WebDriver driver;
	
	WebDriverWait wait;

      public LogIn_Captcha(WebDriver driver) {
		
		this.driver=driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath="//input[@placeholder='Email']")
	WebElement txtEmail;
	
	@FindBy(xpath="//input[@placeholder='Password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//button[normalize-space()='Login']")
	WebElement btnLogin;
	
	 @FindBy(xpath = "//div[@id='image']//span")
	    WebElement captchaText;

	    @FindBy(xpath = "//input[@placeholder='Captcha code']")
	    WebElement captchaInput;

	    @FindBy(xpath = "//i[contains(@class,'fa-sync')]")
	    WebElement refreshCaptchaIcon;
	    
	public void emailField(String email) {
		
		txtEmail.sendKeys(email);
	}
	
	public void passField(String password) {
		
	   txtPassword.sendKeys(password);
	   
	}
	
	 public String getCaptchaValue() {
	        return wait.until(ExpectedConditions.visibilityOf(captchaText))
	                   .getText().trim();
	    }

	    /** Enter CAPTCHA value into input field */
	    public void enterCaptcha(String captcha) {
	        wait.until(ExpectedConditions.elementToBeClickable(captchaInput))
	            .clear();
	        captchaInput.sendKeys(captcha);
	    }

	    public void refreshCaptcha() {
	        wait.until(ExpectedConditions.elementToBeClickable(refreshCaptchaIcon))
	            .click();
	    }

	    public String refreshAndGetNewCaptcha() {
	        String oldCaptcha = getCaptchaValue();
	        refreshCaptcha();

	        wait.until(ExpectedConditions.not(
	                ExpectedConditions.textToBePresentInElement(captchaText, oldCaptcha)
	        ));

	        return getCaptchaValue();
	    }
	    
	public void loginClick() {
		
		btnLogin.click();
	}
	
}
