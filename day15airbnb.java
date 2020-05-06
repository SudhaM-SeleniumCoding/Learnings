package Learnings.Extra;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day15airbnb {
	
	public ChromeDriver driver;
	
//method to click multiple times
	public void multipleClick(String xpath,int count) throws InterruptedException
	{
		for (int i=1;i<=count;i++)
		{
			driver.findElementByXPath(xpath).click();
			Thread.sleep(500);
		}
	}
	
@Test	
public void airbnb() throws InterruptedException, ParseException
{
	//Go to https://www.airbnb.co.in/
		
	System.setProperty("webdriver.chrome.driver","./drivers/chromedriver_win32/chromedriver.exe"); 
	ChromeOptions option=new ChromeOptions(); 
	option.addArguments("--disable-notifications"); 
	driver=new ChromeDriver(option); 
	WebDriverWait wait =new WebDriverWait(driver,30);
	JavascriptExecutor js=(JavascriptExecutor) driver;
	driver.get("https://www.airbnb.co.in/");
	  
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	  
		 
	wait.until(ExpectedConditions.visibilityOf(driver.findElementById("alert-box-message")));
    driver.findElementByXPath("//button[@title='OK']").click();
    
		//2) Type Coorg in location and Select Coorg, Karnataka
	driver.findElementByName("query").sendKeys("Coorg");
	Thread.sleep(2000);
	driver.findElementByName("query").sendKeys(Keys.ARROW_DOWN,Keys.PAUSE,Keys.ENTER);
	
		//3) Select the Start Date as June 1st and End Date as June 5th

	Calendar currentMonth = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
    // Increment month
    currentMonth.add(Calendar.MONTH, 1);
    String nextMonth = dateFormat.format(currentMonth.getTime());
    System.out.println("Next month : " + dateFormat.format(currentMonth.getTime()));

    
	driver.findElementByXPath("//div[text()='"+nextMonth+" 2020']//following::table[1]//td//div[text()='1']").click();
	driver.findElementByXPath("//div[text()='"+nextMonth+" 2020']//following::table[1]//td//div[text()='5']").click();
	String userDate = driver.findElementByXPath("//div[text()='Check in / Check out']//following-sibling::div").getText();
	System.out.println(userDate);
	
	
		//4) Select guests as 6 adults, 3 child and Click Search
	driver.findElementByXPath("//div[text()='Add guests']/parent::button").click();
	multipleClick("//div[@id='searchFlow-title-label-stepper-adults']//following::button[@aria-label='increase value'][1]",6);
	multipleClick("//div[@id='searchFlow-title-label-stepper-children']//following::button[@aria-label='increase value'][1]",3);
	int userGuests=Integer.parseInt(driver.findElementByXPath("//div[text()='Guests']/following-sibling::div").getText().replaceAll("\\D", ""));
	System.out.println("The number of guests searched by user is "+userGuests);
	driver.findElementByXPath("//button[@type='submit']").click();
	
		//5) Click Cancellation flexibility and enable the filter and Save
	wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementsByXPath("//div[@itemprop='itemListElement']")));
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Cancellation flexibility']//ancestor::button")));
	driver.findElementByXPath("//span[text()='Cancellation flexibility']//ancestor::button").click();
	driver.findElementById("filterItem-switch-flexible_cancellation-true").click();
	
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//button[@id='filterItem-switch-flexible_cancellation-true' and @aria-checked='true']")));
	driver.findElementById("filter-panel-save-button").click();
	Thread.sleep(1000);
	
		//6) Select Type of Place as Entire Place and Save
	Thread.sleep(1000);
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Type of place']/ancestor::button")));
	driver.findElementByXPath("//span[text()='Type of place']/ancestor::button").click();
	driver.findElementByXPath("//input[@name='Entire place']/following-sibling::span[@data-checkbox='true']").click();
	driver.findElementById("filter-panel-save-button").click();
	
		//7) Set Min price as 3000 and  max price as 5000
	Thread.sleep(2000);
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Price']/ancestor::button")));
	driver.findElementByXPath("//span[text()='Price']/ancestor::button").click();
	wait.until(ExpectedConditions.visibilityOf(driver.findElementById("price_filter_min")));
	Thread.sleep(2000);
	driver.findElementByXPath("//input[@id='price_filter_min']").sendKeys(Keys.CONTROL,Keys.chord("a"));
	driver.findElementByXPath("//input[@id='price_filter_min']").sendKeys(Keys.BACK_SPACE);
	Thread.sleep(1000);
	driver.findElementByXPath("//input[@id='price_filter_min']").sendKeys("3000");
	driver.findElementById("price_filter_max").sendKeys(Keys.CONTROL,Keys.chord("a"));
	driver.findElementById("price_filter_max").sendKeys(Keys.BACK_SPACE);
	driver.findElementById("price_filter_max").sendKeys("5000");
	
		//8) Click More Filters and set 3 Bedrooms and 3 Bathrooms
	driver.findElementByXPath("//span[text()='More filters']/parent::button").click();
	Thread.sleep(1000);
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[@id='title-label-filterItem-stepper-min_bedrooms-0']//following::button[@aria-label='increase value']")));
	multipleClick("//div[@id='title-label-filterItem-stepper-min_bedrooms-0']//following::button[@aria-label='increase value']",3);
	Thread.sleep(500);
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@id='title-label-filterItem-stepper-min_bathrooms-0']//following::button[@aria-label='increase value']")));
	multipleClick("//div[@id='title-label-filterItem-stepper-min_bathrooms-0']//following::button[@aria-label='increase value']",3);
	
		//9) Check the Amenities with Kitchen, Facilities with Free parking on premisses, Property as House and Host Language as English
	driver.findElementByXPath("//input[@name='Kitchen']/following-sibling::span[@data-checkbox='true']").click();
	driver.findElementByXPath("//input[@name='Free parking on premises']/following-sibling::span[@data-checkbox='true']").click();
	driver.findElementByXPath("//input[@name='House']/following-sibling::span[@data-checkbox='true']").click();
	driver.findElementByXPath("//input[@name='English']/following-sibling::span[@data-checkbox='true']").click();
	
		   // and click on Stays only when stays available
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//input[@name='English']/following-sibling::span[@data-checkbox='true']")));
	Thread.sleep(3000);
	String availableStays = driver.findElementByXPath("//button[starts-with(text(),'Show')]").getText().replaceAll("\\D", "");
	System.out.println(availableStays);
	int availableStaysint=Integer.parseInt(availableStays);
	System.out.println("The number of available stay is "+availableStaysint); 
	
	if(availableStaysint>=1)
	{
		driver.findElementByXPath("//button[contains(text(),'Show "+availableStaysint+" stay')]").click();
	}
	else
	{
		System.out.println("There is no stay available");
	}
	
		//10) Click Prahari Nivas, the complete house
	
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[@itemprop='itemListElement']")));
	Thread.sleep(1000);
	driver.findElementByXPath("(//div[@itemprop='itemListElement']//a[contains(@aria-label,'Prahari Nivas')])[1]").click();
	
		//to move to new window
	Set<String> windowHandles = driver.getWindowHandles();
	List<String> listWindow=new ArrayList<String> (windowHandles);
	driver.switchTo().window(listWindow.get(listWindow.size()-1));
	
	
		//11) Click on "Show all * amenities"
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Location']")));
	Thread.sleep(3000);
	
	js.executeScript("arguments[0].click();",driver.findElementByXPath("(//*[contains(text(),'amenities')])[1]"));
	
		//12) Print all the Not included amenities
	js.executeScript("arguments[0].scrollIntoView();", driver.findElementByXPath("//*[text()='Not included']"));
	List<WebElement> unIncludedAmenities = driver.findElementsByXPath("//*[text()='Not included']//following::span[contains(text(),'Unavailable')]/following-sibling::del");
	for (WebElement eachAmenities : unIncludedAmenities) {
		
		System.out.println("The unincluded Amenities are" +eachAmenities.getText());
	}
	Thread.sleep(2000);
	js.executeScript("arguments[0].click();", driver.findElementByXPath("//button[@aria-label='Close' and not(contains(@title,'Close'))]"));
    Thread.sleep(3000);
	
		//13) Verify the Check-in date, Check-out date and Guests
	String checkInDate = driver.findElementByXPath("//span[contains(text(),'night')]//preceding::span[contains(text(),'3,500')]//following::div[contains(text(),'1/2020')]").getText();
	Thread.sleep(1000);
	String checkOutDate = driver.findElementByXPath("//span[contains(text(),'night')]//preceding::span[contains(text(),'3,500')]//following::div[contains(text(),'5/2020')]").getText();
	int guests =Integer.parseInt(driver.findElementByXPath("(//label[contains(@for,'')]//following::span[contains(text(),'guests')])[1]").getText().replaceAll("\\D", ""));
	
	if(((checkInDate.equals("6/1/2020") | checkInDate.equals("06/01/2020")) & (checkOutDate.equals("6/5/2020") | checkOutDate.equals("06/05/2020")) & guests==userGuests))
	{ System.out.println("The checkindate,checkoutdate and number of guests are verified"); }
		 
	
		//14) Read all the Sleeping arrangements and Print
		  
	List<WebElement> bedRoomCount = driver.findElementsByXPath("//*[text()='Sleeping arrangements']//following::div[contains(text(),'Bedroom')]");
	List<WebElement> bedCount = driver.findElementsByXPath("//*[text()='Sleeping arrangements']//following::div[contains(text(),'Bedroom')]/following-sibling::div");
	System.out.println("The size is" +bedRoomCount.size());
	int j=bedRoomCount.size()-1;
	
	Map<String,String> map=new LinkedHashMap<String,String>();
    for (int i=0;i<=j;i++) {
    	if(i>=3)
    	{
    		js.executeScript("arguments[0].click();",driver.findElementByXPath("//div[contains(@style,'right') and @class='_1mlprnc']//button"));
    		Thread.sleep(1000);
    		bedRoomCount.add(driver.findElementByXPath("//*[text()='Sleeping arrangements']//following::div[contains(text(),'Bedroom')]["+(i+1)+"]"));
    		bedCount.add(driver.findElementByXPath("(//*[text()='Sleeping arrangements']//following::div[contains(text(),'Bedroom')]/following-sibling::div)["+(i+1)+"]"));
    	}
    	
    	 map.put(bedRoomCount.get(i).getText(),bedCount.get(i).getText());
    }
    
    
    for (Entry<String,String> eachMapData: map.entrySet()) {
    	
    	System.out.println("The sleeping arrangements "+eachMapData.getKey()+"-----"+eachMapData.getValue());	
	}
	
		//15) Close all the browsers
    driver.quit();
	
}

}


