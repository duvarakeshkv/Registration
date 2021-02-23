package MobileTesting.OnNativeWebHybridApplication;

import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class TestRegisterPage 
{
	public AndroidDriver driver;
	
	public String fn_Name="duvarakesh";
	public String ln_Name="venugopal";
	public String email_id="duva2441@gmail.com";
	public String pass_word="duva1234";
	public String conform_passowrd="duva1234";

	@Test
	public void login() throws InterruptedException 
	{
		

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@class='ico-register']")).click();

		WebElement selected = driver.findElement(By.xpath("//*[@id='gender-male']"));
		selected.click();
		
		Assert.assertEquals(true, selected.isSelected());
		System.out.println("Checkbox is selected – Assert passed");

		WebElement firstName=driver.findElement(By.xpath("//*[@id='FirstName']"));
		firstName.sendKeys(fn_Name);
		String fn = firstName.getAttribute("value");
		Assert.assertEquals(fn_Name, fn);
		
		WebElement lastName=driver.findElement(By.xpath("//*[@id='LastName']"));
		lastName.sendKeys(ln_Name);
		String ln = lastName.getAttribute("value");
		Assert.assertEquals(ln_Name, ln);
		
		WebElement emaidId=driver.findElement(By.xpath("//*[@id='Email']"));
		emaidId.sendKeys(email_id);
		String email = emaidId.getAttribute("value");
		Assert.assertEquals(email_id, email);
		
		WebElement password=driver.findElement(By.xpath("//*[@id='Password']"));
		password.sendKeys(pass_word);
		String pass = password.getAttribute("value");
		Assert.assertEquals(pass_word, pass);
		
		WebElement conformPassword=driver.findElement(By.xpath("//*[@id='ConfirmPassword']"));
		conformPassword.sendKeys(conform_passowrd);
		String conform = conformPassword.getAttribute("value");
		Assert.assertEquals(conform_passowrd, conform);
		
		System.out.println(fn+" "+ln+" "+email+" "+pass+" "+conform);
		
		
		driver.hideKeyboard();
		
		driver.findElement(By.xpath("//*[@id='register-button']")).click();
		
		try 
		{
			driver.findElement(By.xpath("//span[@class='field-validation-error']/span")).isDisplayed();
			String errorMessage = driver.findElement(By.xpath("//span[@class='field-validation-error']/span"))
					.getText();
			System.out.println("Registration IS FAILED & ERROR MESSAGE IS :" + errorMessage);
		}

		catch (NoSuchElementException e) {

			String actualMessage = driver.findElement(By.xpath("//div[@class='result']")).getText();
			System.out.println("registration IS SUCCESS & SUCCESS MESSAGE IS :" + actualMessage);

		}

	}

	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability(MobileCapabilityType.DEVICE_NAME, "duvarakesh");
		capability.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capability.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capability);
		driver.get("http://demowebshop.tricentis.com/login");
	}

	
	  @AfterClass 
	  public void afterClass() 
	  {
		  driver.close(); 
		  }
	 

}