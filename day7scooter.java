package Learnings.Extra;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day7scooter {
	
	public float conversion(String data)
	{
		Float Displvalue=Float.parseFloat(data);
	    return Displvalue;
	}
	
	@Test
	public void scooter() throws InterruptedException
	{
		
		//launch browser
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);
		
		ChromeDriver driver=new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebDriverWait wait=new WebDriverWait(driver,30);
		
		//launch scooter url
		driver.get("https://www.honda2wheelersindia.com/");
		
		try
		{
			if(driver.findElementByXPath("//div[@class='modal-content']").isDisplayed())
			{driver.findElementByXPath("//button[@class='close']").click();}
		}
		
		catch(Exception e)
		{
			System.out.println("Pop up didnt appear");
		}
		
		//Click on scooters and click dio
		driver.findElementByLinkText("Scooter").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//img[@src='/assets/images/thumb/dioBS6-icon.png']")));
		driver.findElementByXPath("//img[@src='/assets/images/thumb/dioBS6-icon.png']").click();
		
		//Click on Specifications and mouseover on ENGINE
		driver.findElementByLinkText("Specifications").click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByLinkText("ELECTRICALS")));
		
	   Actions action=new Actions(driver);
	   action.moveToElement(driver.findElementByLinkText("ENGINE")).click().build().perform();
	   
	   //Get Displacement value
	   String strdioDisplvalue=(driver.findElementByXPath("//span[text()='Displacement']/following-sibling::span").getText()).replaceAll("[a-z]", "");
	   System.out.println("The displacement value of DIO is " +strdioDisplvalue);
	   Float dioDisplvalue=conversion(strdioDisplvalue);
	   
	   //Go to Scooters and click Activa 125
	   driver.findElementByLinkText("Scooter").click();
	   wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//img[@src='/assets/images/thumb/activa-125new-icon.png']")));
		driver.findElementByXPath("//img[@src='/assets/images/thumb/activa-125new-icon.png']").click();
		
		//Click on Specifications and mouseover on ENGINE
		driver.findElementByLinkText("Specifications").click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByLinkText("ELECTRICALS")));
		action.moveToElement(driver.findElementByLinkText("ENGINE")).click().build().perform();
		
		//Get Displacement value
		String strActivaDisplvalue=(driver.findElementByXPath("//span[text()='Displacement']/following-sibling::span").getText()).replaceAll("[a-z]", "");
		   System.out.println("The displacement value of Activa is " +strActivaDisplvalue);
		   Float ActivaDisplvalue=conversion(strActivaDisplvalue);
		   
		   //Compare Displacement of Dio and Activa 125 and print the Scooter name having better Displacement.
	   if(Float.compare(dioDisplvalue, ActivaDisplvalue)<0)
	   {
		   System.out.println("Activa displacement is better");
	   }
	   else 
	   {
		   System.out.println("Dio displacement is better");
	   }
	   
	  //Click FAQ from Menu 
	   driver.findElementByLinkText("FAQ").click();
	   
	   //Click Activa 125 BS-VI under Browse By Product
	   driver.findElementByLinkText("Activa 125 BS-VI").click();
	   wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//a[contains(text(),'Product Features')]")));
	   
	   //Click  Vehicle Price 
	   driver.findElementByXPath("//a[contains(text(),'Vehicle Price')]").click();
	   Thread.sleep(3000);
	   //Make sure Activa 125 BS-VI selected and click submit
	   Select select=new Select(driver.findElementById("ModelID6"));
	   String selectedOption = select.getFirstSelectedOption().getText();
	   
	   if (selectedOption.contains("Activa 125 BS-VI"))
	   {
		   System.out.println("Activa 125 BS-VI is selected");
		   driver.findElementByXPath("//select[@id='ModelID6']//following::button[1]").click();
	   }
	   else
	   {
		   System.out.println("The selection is wrong");
	   }
		
	   //click the price link
		driver.findElementByLinkText("Click here to know the price of Activa 125 BS-VI.").click();
		
		//Go to the new Window and select the state as Tamil Nadu and  city as Chennai
		
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> listwin=new ArrayList <String> (windowHandles);
		driver.switchTo().window(listwin.get(1));
		Select selectState=new Select(driver.findElementById("StateID"));
		selectState.selectByVisibleText("Tamil Nadu");
		
		Select selectCity =new Select(driver.findElementById("CityID"));
		selectCity.selectByVisibleText("Chennai");
		
		//Click Search
		driver.findElementByXPath("//button[text()='Search']").click();
		
		//Print all the 3 models and their prices
		Map<String,String> tableData=new LinkedHashMap<String,String>();
		List<WebElement> model = driver.findElementsByXPath("//table[@class='datashow']//tr//td[contains(text(),'ACT')]");
		List<WebElement> price = driver.findElementsByXPath("//table[@class='datashow']//tr//td[contains(text(),'Rs')]");
		
		
		
		for (int i=0;i<=model.size()-1;i++)
		{
			tableData.put(model.get(i).getText(), price.get(i).getText());		
	}
		
		for (Entry<String,String> mapData : tableData.entrySet()) {
			System.out.println("The model is " +mapData.getKey() +"The price is " +mapData.getValue());
		}
		
		driver.close();
    
}}
