package MobileTesting.OnNativeWebHybridApplication;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class RegistrationDataDriven 
{
	public AndroidDriver driver;
	public static String EXCEL_PATH="C:\\Users\\DuvarakeshKV\\Mobile_testing\\OnNativeWebHybridApplication\\src\\test\\java\\com\\config\\mobiletestinglogins.xlsx";
	
	@Test
	public void login() throws InterruptedException, IOException 
	{
		File file= new File(EXCEL_PATH);
        FileInputStream fis= new FileInputStream(file);
        XSSFWorkbook wb= new XSSFWorkbook(fis);
        XSSFSheet sheet=wb.getSheetAt(0);
        int rc= sheet.getLastRowNum();
        System.out.println(rc);
        for (int i=1;i<=rc;i++) {
            String fn_Name=sheet.getRow(i).getCell(0).getStringCellValue();
            String ln_Name=sheet.getRow(i).getCell(1).getStringCellValue();
            String email_id=sheet.getRow(i).getCell(2).getStringCellValue();
            String pass_word=sheet.getRow(i).getCell(3).getStringCellValue();
            String conform_passowrd=sheet.getRow(i).getCell(4).getStringCellValue();
            String expected_result=sheet.getRow(i).getCell(5).getStringCellValue();
            
            System.out.println(fn_Name+" \n"+ln_Name+"\n"+email_id+"\n"+pass_word+"\n"+conform_passowrd+"\n"+expected_result);
            
		

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement reg=driver.findElement(By.xpath("//a[@class='ico-register']"));
		Assert.assertEquals(true, reg.isDisplayed());
		reg.click();

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
			//Assert.assertEquals(errorMessage, expected_result);
			write_Output(errorMessage,expected_result);
			
		}

		catch (NoSuchElementException e) {
			
			String actualMessage = driver.findElement(By.xpath("//div[@class='result']")).getText();
			System.out.println("registration IS SUCCESS & SUCCESS MESSAGE IS :" + actualMessage);
			//Assert.assertEquals(actualMessage, expected_result);
			write_Output(actualMessage,expected_result);
			
		}
        }

	}

	private void write_Output(String message, String expected_result) 
	{
		 try
  	   {
  	       FileInputStream myxls = new FileInputStream(EXCEL_PATH);
  	       @SuppressWarnings("resource")
  	       XSSFWorkbook studentsSheet = new XSSFWorkbook(myxls);
  	       XSSFSheet worksheet = studentsSheet.getSheetAt(1);
  	       int lastRow=worksheet.getLastRowNum();
  	       System.out.println(lastRow);
  	       Row row = worksheet.createRow(++lastRow);
  	       row.createCell(0).setCellValue(expected_result);
  	       row.createCell(1).setCellValue(message);
  	       if(message.equals(expected_result))
  	       {
  	    	 row.createCell(2).setCellValue("PASSED");  
  	       }
  	       else
  	       {
  	    	 row.createCell(2).setCellValue("FAILED");   
  	       }
  	       
  	       
  	      // row.createCell(2).setCellValue(value);
  	       myxls.close();
  	       FileOutputStream output_file =new FileOutputStream(new File(EXCEL_PATH));  
  	       //write changes
  	       studentsSheet.write(output_file);
  	       output_file.close();
  	       System.out.println("successfully written");
  	    }
  	   
  	    catch(Exception e)
  	    {
  	    	System.out.println(e);
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