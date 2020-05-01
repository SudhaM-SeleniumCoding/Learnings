package Learnings.Extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day12carwale {
	
	
	@Test
	public void carwale() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win32/chromedriver.exe");
		ChromeOptions option=new ChromeOptions();
		option.addArguments("--disable-notifications");
		
		ChromeDriver driver=new ChromeDriver(option);
		WebDriverWait wait=new WebDriverWait(driver,30);
		Actions action=new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get("https://www.carwale.com/");
		
		//Click on Used
		driver.findElementByXPath("//li[@data-tabs='usedCars']").click();
		
		//Select the City as Chennai
	    wait.until(ExpectedConditions.visibilityOf(driver.findElementById("budgetBtn")));
		driver.findElementById("usedCarsList").sendKeys("Chennai");
		Thread.sleep(3000);
		driver.findElementById("usedCarsList").sendKeys(Keys.ARROW_DOWN,Keys.TAB);
		
		//Select budget min (8L) and max(12L) and Click Search
		wait.until(ExpectedConditions.visibilityOf(driver.findElementById("minInput")));
		driver.findElementById("minInput").sendKeys("8",Keys.TAB);
		driver.findElementById("maxInput").sendKeys("12",Keys.TAB);
		
		driver.findElementById("btnFindCar").click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//p[text()='Filters']")));
		if(driver.findElementByXPath("//p[text()='Filters']").isDisplayed())
		{
			driver.findElementByXPath("(//a[text()=\"Don't show anymore tips\"])[1]").click();
		}
		
		//Select Cars with Photos under Only Show Cars With
		driver.findElementByXPath("//span[text()='Cars with Photos']").click();
		
		//Select Manufacturer as "Hyundai" --> Creta
		
		js.executeScript("arguments[0].click();", driver.findElementByXPath("//ul[@id='makesList']//span[text()=' Hyundai ']"));
		Thread.sleep(3000);
		js.executeScript("arguments[0].click();", driver.findElementByXPath("//span[@class='model-txt' and text()='Creta']"));
		
		if((driver.findElementByXPath("//span[@class='makeModel filter-parameters' and contains(text(),'Creta')]").isDisplayed() & driver.findElementByXPath("//span[@class='close-icon filter-parameters' and contains(text(),'Cars with Photos')]").isDisplayed()));
		{
			System.out.println("The Creta and Cars with photos filter is applied successfully");
		}
			
		//Select Fuel Type as Petrol
		js.executeScript("arguments[0].click();",driver.findElementByXPath("//li[@class='us-sprite']/span[text()='Petrol']"));
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[@parentfiltername='fuel']")));
		
		//Select Best Match as "KM: Low to High"
		Select dropdown=new Select(driver.findElementById("sort"));
		dropdown.selectByVisibleText("KM: Low to High");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[@data-carname-id='carname']/following::span[@class='slkms vehicle-data__item']")));
		
		//Validate the Cars are listed with KMs Low to High
		List<WebElement> kmlist = driver.findElementsByXPath("//span[@data-carname-id='carname']/following::span[@class='slkms vehicle-data__item']");
		
		//Map to put string and int km
		Map<Integer,String> map=new TreeMap<Integer,String>();
        
	  // int km after sorting
		List<Integer> intKmList=new ArrayList<Integer>();
		
		//int km without sorting
		List<Integer> originalKmListint=new ArrayList<Integer>();
		
		for (WebElement eachcarkm : kmlist) {
			
			String orginalKm=eachcarkm.getText();
			int eachCarkm=Integer.parseInt(eachcarkm.getText().replaceAll("\\D", ""));
			intKmList.add(eachCarkm);
			originalKmListint.add(eachCarkm);
			map.put(eachCarkm, orginalKm);
			
		}
		
		Collections.sort(intKmList);
      //comparing sorted list and original list
		if (intKmList.equals(originalKmListint))
		{
			System.out.println("All the cars are sorted based on km");
		}
		
		else
		{
			System.out.println("Sorting has issue");
		}
		
		// To get least KM from MAP// Since using TreeMap data will be inserted in ASCII order
		String leastStringData = (String) map.values().toArray()[0];
		System.out.println("The least Km is" +leastStringData);
		
		
		//Add the least KM ran car to Wishlist
		
		String LeastCarName=driver.findElementByXPath("//span[@class='slkms vehicle-data__item' and contains(text(),'"+leastStringData+"')]/preceding::span[@data-carname-id='carname']").getText();
		System.out.println("The Car with least KM is" +LeastCarName);
		driver.findElementByXPath("//span[@class='slkms vehicle-data__item' and contains(text(),'30,000')]/preceding::span[@class='shortlist-icon--inactive shortlist']").click();
		
		//Go to Wishlist and Click on More Details
		driver.findElementByXPath("//li[@class='action-box shortlistBtn']/span").click();
		
		//Go to Wishlist and Click on More Details
		Thread.sleep(2000);
		driver.findElementByXPath("//a[contains(text(),'More details')]").click();
		
		//Print all the details under Overview 
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> listwin=new ArrayList<String> (windowHandles);
		driver.switchTo().window(listwin.get(1));
		
		Map<String,String> mapdetails=new LinkedHashMap<String,String>();
		List<WebElement> overView = driver.findElementsByXPath("//div[@id='overview']//div[@class='equal-width text-light-grey']");
		List<WebElement> features = driver.findElementsByXPath("//div[@id='overview']//div[@class='equal-width dark-text']");
		
		for (int i=0;i<=overView.size()-1;i++) {
			
			mapdetails.put(overView.get(i).getText(),features.get(i).getText());
		}
		for (Entry<String,String> eachmapdata : mapdetails.entrySet()) {
			System.out.println(eachmapdata.getKey()+"---"+eachmapdata.getValue());
		}
		
		
	}

}
