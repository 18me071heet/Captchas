package BaseClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass_Captcha {

	public WebDriver driver;
	public Properties p;
	public Logger logger;
	
public String randomString() {
		
		String generatedString = RandomStringUtils.randomAlphabetic(7);
		return generatedString;
	}
	
	public String randomNumber() {
		
		String generatedNumeric = RandomStringUtils.randomNumeric(10);
		return generatedNumeric;
		
	}
	
	public String randomAlphaNumeric() {
		
		String generatedString = RandomStringUtils.randomAlphabetic(4);
		String generatedNumeric = RandomStringUtils.randomNumeric(4);
       return(generatedString+"@"+generatedNumeric);
	}

	
    @BeforeClass()
    @Parameters({"browser"})
	public void setUp(String br) throws IOException {
    	
 
    	ChromeOptions co = new ChromeOptions();
		co.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		
    	logger= LogManager.getLogger(this.getClass());
    	
    	FileReader file = new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		  if (br.equalsIgnoreCase("chrome")) {
		        driver = new ChromeDriver();
		    } else if (br.equalsIgnoreCase("firefox")) {
		        driver = new FirefoxDriver();
		    } else {
		        throw new IllegalArgumentException("Browser not supported: " + br);
		    }

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appUrl"));
		
  }
    
    
  //  @BeforeClass
  //  @Parameters({"browser"})
    void SignUp(String br) throws IOException {
    	
        logger= LogManager.getLogger(this.getClass());
    	
    	FileReader file = new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		

		  if (br.equalsIgnoreCase("chrome")) {
		        driver = new ChromeDriver();
		    } else if (br.equalsIgnoreCase("firefox")) {
		        driver = new FirefoxDriver();
		    } else {
		        throw new IllegalArgumentException("Browser not supported: " + br);
		    }

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("appUrlSignUp");
    }
    
    
  //  @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

	
	  public String captureScreen(String testName) {
		  
		  
	    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

	    String screenshotDir = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "screenshots";
	    new File(screenshotDir).mkdirs(); 

	    String filePath = screenshotDir + File.separator + testName + "_" + timeStamp + ".png";

	    try {
	    	
	        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        File target = new File(filePath);
	        Files.copy(src.toPath(), target.toPath());

	        if (target.exists()) {
	            System.out.println("Screenshot saved] " + filePath);
	        } else {
	            System.out.println("[ Screenshot not saved] " + filePath);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println(" Error ");
	    }

	    return filePath;
	}

}
