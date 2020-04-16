package Learnings.Extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day2Nykaa {
	
	public static ChromeDriver driver;
	
	public String windowmethod()
	{
	Set<String> windowsid = driver.getWindowHandles();
	List<String> windowsidlist=new ArrayList<String> (windowsid);
	int size= windowsidlist.size();
	return windowsidlist.get(size-1);
	}

	@Test
	public void nykaa() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		
		//disable notifications
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		//launch url
		driver=new ChromeDriver(options);
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebDriverWait wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("(//div[@class='nw_grid_content'])[1]")));
		
		//Mouseover on Brands and Mouseover on Popular
		Actions action=new Actions(driver);
		action.moveToElement(driver.findElementByXPath("(//li[@class='menu-dropdown-icon'])//a[1]")).perform();
		action.moveToElement(driver.findElementByXPath("//a[text()='Popular']")).perform();
		
		//Click L'Oreal Paris
		driver.findElementByXPath("(//li[@class='brand-logo menu-links']//img)[5]").click();
		
		//switch to new window
		driver.switchTo().window(windowmethod());
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[text()='SPF']")));
		
		if ((driver.getTitle()).contains("L'Oreal Paris"))
				{
			System.out.println("The title contains L'Oreal Paris");
				}
		
		//Click sort By and select customer top rated
		Thread.sleep(5000);
		driver.findElementByXPath("//span[contains(text(),'Sort')]").click();
		Thread.sleep(2000);
		driver.findElementByXPath("//span[contains(text(),'customer top rated')]").click();
		
		//Click Category and click Shampoo
		Thread.sleep(3000);
		driver.findElementByXPath("//div[text()='Category']").click();
		driver.findElementByXPath("//label[@for='chk_Shampoo_undefined']//span[1]").click();
		
		//check whether the Filter is applied with Shampoo
		if( driver.findElementByXPath("//li[text()='Shampoo']").isDisplayed())
		{
			System.out.println("The filter is applied with Shampoo");
		}
		
		//Click on L'Oreal Paris Colour Protect Shampoo
		driver.findElementByXPath("//span[contains(text(),'Paris Colour Protect Shampoo')]").click();
		Thread.sleep(5000);
		//switch to new window
		driver.switchTo().window(windowmethod());
		
		//GO to the new window and select size as 175ml
		driver.findElementByXPath("(//span[@class='size-pallets'])[2]").click();
		
		//Print the MRP of the product
		String price=driver.findElementByXPath("//span[@class='mrp-tag']/following::span[2]").getText();
		System.out.println("The price of the shampoo is " +price);
		
		//Click on ADD to BAG
		driver.findElementByXPath("//button[text()='ADD TO BAG'][1]").click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[text()='Product has been added to bag.']")));
		
		//Go to Shopping Bag 
		
		driver.findElementByClassName("AddBagIcon").click();
		
		//Print the Grand Total amount
		String totalAmount = driver.findElementByXPath("//div[text()='Grand Total']/following::div[1]").getText();
		System.out.println("The total Amount is" +totalAmount);
		
		driver.findElementByXPath("//button[@class='btn full fill no-radius proceed ']//span").click();
		
		//Click on Continue as Guest
		driver.findElementByXPath("//button[@class='btn full big']").click();
		
		//Print the warning message (delay in shipment)
		String warningmsg = driver.findElementByClassName("message").getText();
		System.out.println("The warning msg is "+warningmsg);
		
		//to close all windows
		driver.quit();
	}
}
