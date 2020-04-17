package Learnings.Extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day3makemytrip {
	
	@Test
	public void makemytrip()
	{
		//launch Browser
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--diable-notifications");
		ChromeDriver driver=new ChromeDriver(options);
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebDriverWait wait=new WebDriverWait(driver,30);
		
		//Click Hotels
		driver.findElementByXPath("//span[text()='Hotels']").click();
		
		//Enter city as Goa, and choose Goa, India
		String city = driver.findElementByXPath("//input[@type='text']").getAttribute("value");
		if (city.contains("Goa"))
		{
			System.out.println("The selected city is" +city);
		}
		
		else
		{
			driver.findElementByXPath("(//input[@type='text'])[2]").sendKeys("Goa",Keys.TAB);
		}
	   
		//Enter Check in date as Next month 15th (May 15) and Check out as start date+5
		driver.findElementById("checkin").click();
		driver.findElementByXPath("//div[@aria-label='Fri May 15 2020']").click();
		int checkIndate = Integer.parseInt(driver.findElementByXPath("//div[@aria-label='Fri May 15 2020']").getText());
		int checkOutdate=checkIndate+5;
		driver.findElementByXPath("//div[contains(@aria-label,'May "+checkOutdate+" 2020')]").click();
		
		//Click on ROOMS & GUESTS and click 2 Adults and one Children(age 12). Click Apply Button.
		driver.findElementById("guest").click();
		driver.findElementByXPath("(//div[@class='addRooomDetails']//li)[2]").click();
		driver.findElementByXPath("//li[@data-cy='children-1']").click();
		
		//childage
		Select age=new Select(driver.findElementByXPath("//select[@class='ageSelectBox']"));
		age.selectByVisibleText("12");
		
		//click on Apply
		driver.findElementByXPath("//button[text()='APPLY']").click();
		
		//click on search button
		driver.findElementById("hsw_search_button").click();
		
		//to ignore the overlay
		driver.findElementByXPath("//div[@class='mmBackdrop wholeBlack']").click();
		
		
		//Select locality as Baga
		driver.findElementByXPath("//label[text()='Baga']").click();
	    wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//label[text()='5 Star']")));
	    
		//Select 5 start in Star Category under Select Filters
		driver.findElementByXPath("//label[text()='5 Star']").click();
		
		//verification
		if((driver.findElementByXPath("//span[text()='Baga']").isDisplayed()) & (driver.findElementByXPath("//span[text()='5 Star hotels']").isDisplayed()))
		{
			System.out.println("Baga and 5star is selected");
		}
		
		//Click on the first resulting hotel and go to the new window
		List<WebElement> hotelList = driver.findElementsById("hlistpg_hotel_name");
		String firstHotel=hotelList.get(0).getText();
		driver.findElementByXPath("//span[contains(text(),'"+firstHotel+"')]").click();
		
		Set<String> window = driver.getWindowHandles();
		List<String> windowList=new ArrayList<String> (window);
	    driver.switchTo().window(windowList.get(1));
	    
	    //Print the Hotel Name 
	    wait.until(ExpectedConditions.visibilityOf(driver.findElementByClassName("hotelHeaderMiddle")));
	    String hotelName = driver.findElementById("detpg_hotel_name").getText();
	    System.out.println("The selected hotel name is" +hotelName);
	    
	    //Click MORE OPTIONS link and Select 3Months plan and close
	    driver.findElementByXPath("//span[text()='MORE OPTIONS']").click();
	    driver.findElementByXPath("//table[@class='tblEmiOption']//tbody//tr[2]//td[6]/span").click();
	    String verifySelected = driver.findElementByXPath("//table[@class='tblEmiOption']//tbody//tr[2]//td[6]/span").getText();
	    if (verifySelected.contains("SELECTED"))
	    {
	    	System.out.println("The 3months EMI option is selected");
	    }
	    driver.findElementByClassName("close").click();
	    
	    //Click on BOOK THIS NOW
	    driver.findElementByLinkText("BOOK THIS NOW").click();
		
	    //close the pop up
	    
	    if (driver.findElementByXPath("//div[@class='_Modal modalCont']").isDisplayed())
	    {
	    driver.findElementByClassName("close").click();
	    System.out.println("The modal is closed");
	    }

	    //Print the Total Payable amount
	    String totalAmt=driver.findElementById("revpg_total_payable_amt").getText();
	    System.out.println("The booking amount is" +totalAmt);
	    
	    //close all the windows
	    driver.quit();
	}

}
