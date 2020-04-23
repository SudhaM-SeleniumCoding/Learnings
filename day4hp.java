package Learnings.Extra;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day4hp {
	
	public ChromeDriver driver;
	public WebDriverWait wait;
	
@Test
public void hp() throws InterruptedException

{
	
	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win32/chromedriver.exe");
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--disable-notifications");
	
	DesiredCapabilities cap = new DesiredCapabilities();
	cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
	options.merge(cap);
	ChromeDriver driver = new ChromeDriver(options);
	WebDriverWait wait=new WebDriverWait(driver,30);
	
	driver.get("https://store.hp.com/in-en/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	//Handler cookies alert
	wait.until(ExpectedConditions.visibilityOf(driver.findElementById("alert-box-title")));
	
	driver.findElementByXPath("//div[@class='optanon-alert-box-corner-close']/button").click();
	
	try
	{
		
			driver.findElement(By.xpath("//span[@class='optly-modal-close close-icon']"));
	}
		
		catch(Exception e)
		{
			System.out.println("The pop up didnt occur");
		}
	
	
     //Mouse over on Laptops menu and click on Pavilion
	Actions action=new Actions(driver);
	action.moveToElement(driver.findElementByXPath("(//span[text()='Laptops'])[1]")).perform();
	driver.findElementByXPath("(//span[text()='Pavilion'])[1]").click();
	
	//wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElementsByClassName("product-image-photo")));

	
	//Under SHOPPING OPTIONS -->Processor -->Select Intel Core i7
	driver.findElementByXPath("(//span[text()='Processor'])[1]/following::span[1]").click();
	
	driver.findElementByXPath("//span[text()='Intel Core i7']").click();
	//js.executeScript("arguments[0].scrollIntoView();",driver.findElementByXPath("//span[text()='More than 1 TB']") );	
	//Hard Drive Capacity -->More than 1TB
	
	Thread.sleep(5000);
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[contains(@class,'closeButton')]")));
	driver.findElementByXPath("//div[contains(@class,'closeButton')]").click();
	driver.findElementByXPath("//span[text()='More than 1 TB']").click();
	
    
	//verify filter
	if ((driver.findElementByClassName("filter-value").getText()).contains("Intel Core i7") & ((driver.findElementByXPath("(//span[@class='filter-value'])[2]").getText()).contains("More than 1 TB")))
	{System.out.println("The filter is successful");}
	
	//Select Sort By: Price: Low to High
	Select dropdown=new Select(driver.findElementById("sorter"));
	dropdown.selectByValue("price_asc");
    wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("(//label[text()='Sort By'])[1]")));
    
    Thread.sleep(3000);
	//Print the First resulting Product Name and Price
    String prodName=driver.findElementByXPath("(//a[@class='product-item-link'])[1]").getText();
    int prodPrice=Integer.parseInt((driver.findElementByXPath("(//span[@class='price-container price-final_price tax weee'])[1]//span/span").getText()).replaceAll("\\D", ""));
	System.out.println("The first product and price is " +prodName +prodPrice);

	//Click on Add to Cart
	Thread.sleep(3000);
	driver.findElementByXPath("(//span[text()='Add To Cart'])[1]").click();
	wait.until(ExpectedConditions.textToBePresentInElement(driver.findElementByXPath("//span[@class='counter qty']"),"1"));
	
	//Click on Shopping Cart icon --> Click on View and Edit Cart
	driver.findElementByXPath("//a[@class='action showcart']").click();
	driver.findElementByXPath("//a[@class='action primary viewcart']").click();
	wait.until(ExpectedConditions.urlContains("checkout/cart/"));
	
	//Check the Shipping Option --> Check availability at Pincode
	driver.findElementByName("pincode").sendKeys("600019");
	driver.findElementByXPath("//button[@class='primary standard_delivery-button']").click();
	wait.until(ExpectedConditions.textToBePresentInElement(driver.findElementByXPath("//span[@class='available']"), "In stock"));
	
	//Verify the order Total against the product price
	int totalPrice=Integer.parseInt((driver.findElementByXPath("//table[@class='data table totals']//tr[3]//td/strong").getText()).replaceAll("\\D", ""));
	System.out.println(totalPrice);
	if(totalPrice==prodPrice)
	{
		System.out.println("The product price matched total price");
		
		//Proceed to Checkout if Order Total and Product Price matches
		driver.findElementByXPath("(//span[text()='Proceed to Checkout'])[1]").click();
		
		//Click on Place Order
		driver.findElementByXPath("//div[@class='place-order-primary']//span").click();
		
		//Capture the Error message and Print
		driver.findElementByXPath("//div[@class='message notice']/span").getText();
		
		//Close Browser
		driver.close();
	}
	
}}
	