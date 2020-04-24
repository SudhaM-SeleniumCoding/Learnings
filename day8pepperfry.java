package Learnings.Extra;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day8pepperfry {
	
	public ChromeDriver driver;
	public String wishlistCount;
	public Actions mouse;
	
	public void mousehover(String Product,String subProd,String catProd)
	{
		mouse=new Actions(driver);
		mouse.moveToElement(driver.findElementByXPath("//a[text()='"+Product+"' and @class='level-top']")).pause(2000).perform();
		driver.findElementByLinkText(catProd).click();
	}
	
	public void wishlist(String ModelName) throws InterruptedException
	{
        
		driver.findElementByXPath("//a[contains(@title,'"+ModelName+"')]/following::a[contains(@data-productname,'"+ModelName+"')]").click();
		Thread.sleep(3000);
		wishlistCount = driver.findElementByXPath("//div[@class='wishlist_bar']//span").getText();
		
	}
	
	
	@Test
	public void pepperfry() throws IOException, InterruptedException
	{
		
		//Go to https://www.pepperfry.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options= new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver=new ChromeDriver(options);
		WebDriverWait wait=new WebDriverWait(driver,30);
		JavascriptExecutor Js=(JavascriptExecutor)driver;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.pepperfry.com/");
		
		//Close the signup pop up
	   wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("(//div[@class='reg-modal-right-frm-wrap'])[1]")));
	   driver.findElementByXPath("(//a[@class='popup-close'])[5]").click();

		//Mouseover on Furniture and click Office Chairs under Chairs -Using method
		mousehover("Furniture","Chairs","Office Chairs");
		
		//click Executive Chairs
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//h5[text()='Executive Chairs']")));
		driver.findElementByXPath("//h5[text()='Executive Chairs']").click();
		
		//Change the minimum Height as 50 in under Dimensions
		driver.findElementByXPath("//h2[contains(text(),'DIMENSION')]/following::input[1]").clear();
		driver.findElementByXPath("//h2[contains(text(),'DIMENSION')]/following::input[1]").sendKeys("50",Keys.ENTER);
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[@id='clipFilterCardContainer']//li[contains(text(),'Height')]")));
		if(driver.findElementByXPath("//div[@id='clipFilterCardContainer']//li[contains(text(),'Height')]").isDisplayed())
		{
			System.out.println("The height filter is applied successfully");
		}
		
		//Add "Poise Executive Chair in Black Colour" chair to Wishlist
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()='Poise Executive Chair in Black Colour']")));
		String ChairName = driver.findElementByXPath("//a[text()='Poise Executive Chair in Black Colour']").getText();
		System.out.println(ChairName.substring(0, 15));
		wishlist(ChairName.substring(0, 15));
		
		
		//Mouseover on Homeware and Click Pressure Cookers under Cookware
		mousehover("Homeware","Cookware","Pressure Cookers");
		
		//Select Prestige as Brand
		driver.findElementByXPath("//label[text()='Prestige']").click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//li[text()='Prestige']")));
		
		//Select Capacity as 1-3 Ltr
		Thread.sleep(3000);
		driver.findElementByXPath("//label[@for='capacity_db1_Ltr_-_3_Ltr']").click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//li[text()='1 Ltr - 3 Ltr']")));
		
		if(driver.findElementByXPath("//li[text()='1 Ltr - 3 Ltr']").isDisplayed() & driver.findElementByXPath("//li[text()='Prestige']").isDisplayed())
		{
			System.out.println("The filter Prestige and capacity is applied successfully");
		}
		
		//Add "Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr" to Wishlist
		
		String CookerName = driver.findElementByXPath("//a[text()='Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr']").getText();
		System.out.println(CookerName.substring(0, 14));
		Thread.sleep(3000);
		wishlist(CookerName.substring(0, 14));
		if (wishlistCount.equals("2"))
		{
			System.out.println("The number of items in wishlist is " +wishlistCount);
		}
		
		
		//Navigate to Wishlist
		driver.findElementByXPath("//div[@class='wishlist_bar']/a").click();
		
		//Move Pressure Cooker only to Cart from Wishlist
		Thread.sleep(3000);
		Js.executeScript("arguments[0].scrollIntoView();",driver.findElementByXPath("//div[@id='cart_item_holder']//a[contains(text(),'Cooker')]/following::div[1]//a[contains(text(),'Add to Cart')]"));
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[@id='cart_item_holder']//a[contains(text(),'Cooker')]/following::div[1]//a[contains(text(),'Add to Cart')]")));
		driver.findElementByXPath("//div[@id='cart_item_holder']//a[contains(text(),'Cooker')]/following::div[1]//a[contains(text(),'Add to Cart')]").click();
		
		//Check for the availability for Pincode 600128
		Js.executeScript("arguments[0].scrollIntoView();",driver.findElementByClassName("srvc_pin_text"));
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByClassName("srvc_pin_text")));
		driver.findElementByClassName("srvc_pin_text").sendKeys("600128");
		driver.findElementByClassName("check_available").click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//div[@id='cart_item_holder']//a[contains(text(),'Cooker')])[1]")));
		
		if (driver.findElementByXPath("(//div[@class='minicart-error'])[1]").isDisplayed()) {
			System.out.println("The product is not delivered to your location");}
		
		else {
			//Js.executeScript("arguments[0].scrollIntoView();",driver.findElementByXPath("//a[text()='Proceed to pay securely ']"));
			//Click Proceed to Pay Securely
			wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Proceed to pay securely')]")));
			driver.findElementByXPath("//a[contains(text(),'Proceed to pay securely')]").click();
			
			//mouse.moveToElement(driver.findElementByXPath("//a[contains(text(),'Proceed to pay securely')]")).click().build().perform();
			
			//Click Proceed to Pay
			driver.findElementByLinkText("PLACE ORDER").click();
			
			//Capture the screenshot of the item under Order Item
			wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='ORDER SUMMARY']")));
			driver.findElementByXPath("//span[text()='ORDER SUMMARY']").click();
			File screenShot = driver.findElementByXPath("//li[@data-slick-index='0']").getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenShot, new File("./screenshot/image.png"));
			
		}
		driver.close();
		
	}

}
