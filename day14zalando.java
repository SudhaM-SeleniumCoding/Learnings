package Learnings.Extra;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day14zalando {
	
@Test
public void zalando() throws InterruptedException
{
	//Go to https://www.zalando.com/
	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win32/chromedriver.exe");
	ChromeDriver driver=new ChromeDriver();
	driver.get("https://www.zalando.com/");
	
	//Get the Alert text and print it//Close the Alert box 
		String alertText = driver.switchTo().alert().getText();
		System.out.println("The text in the alert box is" +alertText);
		driver.switchTo().alert().accept();
	
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	WebDriverWait wait=new WebDriverWait(driver,30);
	Actions action=new Actions(driver);
	JavascriptExecutor js=(JavascriptExecutor) driver;
	
	
	//click on Zalando.uk
	driver.findElementByXPath("//a[text()='Zalando.uk']").click();
	Thread.sleep(3000);
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[@id='uc-banner-text']")));
	driver.findElementByXPath("//div[@id='uc-banner-text']/following::button[contains(text(),\"That’s OK\")]").click();
	
	//Click Women--> Clothing and click Coat 
	driver.findElementByXPath("//span[text()='Women' and @class='z-text z-navicat-header_genderText z-text-cta z-text-black']").click();
	action.moveToElement(driver.findElementByXPath("//span[text()='Clothing' and @class='z-text z-navicat-header_categoryListLinkText z-text-cta z-text-black']")).pause(2000).perform();
	driver.findElementByXPath("//span[text()='Coats' and @class='z-text z-text-body z-text-black']").click();
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Material']")));
	
	//Choose Material as cotton (100%) and Length as thigh-length
	Thread.sleep(3000);
	js.executeScript("arguments[0].click();",driver.findElementByXPath("//span[text()='Material']"));
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[text()='cotton (100%)']")));
	Thread.sleep(2000);

	driver.findElementByXPath("//span[text()='cotton (100%)']").click();
	driver.findElementByXPath("//button[text()='Save']").click();
	
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Length']")));
	Thread.sleep(1000);
	driver.findElementByXPath("//span[text()='Length']").click();
	driver.findElementByXPath("//span[text()='thigh-length']").click();
	driver.findElementByXPath("//button[text()='Save']").click();
	
	//Click on Q/S designed by MANTEL - Parka coat
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[text()='Q/S designed by']/following-sibling::div[text()='MANTEL - Parka - navy']")));
	Thread.sleep(1000);
	driver.findElementByXPath("//div[text()='Q/S designed by']/following-sibling::div[contains(text(),'MANTEL - Parka')]").click();
	
	//Check the availability for Color as Olive and Size as 'M'
	driver.findElementByXPath("(//img[@alt='olive'])[2]").click();
	Thread.sleep(2000);
	
     if (driver.findElementByXPath("//h2[text()='Out of stock']").isDisplayed())
	{
		driver.findElementByXPath("(//img[@alt='navy'])[2]").click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Choose your size']")));
		driver.findElementByXPath("//span[text()='Choose your size']").click();
		driver.findElementByXPath("//span[text()='M']").click();
	}
	
     else if(driver.findElementByXPath("//span[text()='Choose your size']").isDisplayed())
 	{
 	driver.findElementByXPath("//span[text()='Choose your size']").click();
 	driver.findElementByXPath("//span[text()='M']").click();
 	}
     
	else
	{
		System.out.println("Both the items are out of stock");
	}
	
	String standDelivery = driver.findElementByXPath("(//span[text()='Standard delivery']//following::span[@class='AtOZbZ'])[1]").getText();
	System.out.println("The standardDelivery is " +standDelivery);
	
	//Add to bag only if Standard Delivery is free
	if(standDelivery.equalsIgnoreCase("Free"))
	{
		driver.findElementByXPath("//span[text()='Add to bag']").click();
	}
	else
	{
		System.out.println("The standard delivery is not free");
	}
	
	//Mouse over on Your Bag and Click on "Go to Bag"
	action.moveToElement(driver.findElementByXPath("//a[@class='z-navicat-header_navToolItemLink']/div")).pause(1000).perform();
	driver.findElementByXPath("//div[@class='z-1-button__content' and text()='Go to bag']").click();
	
	//Capture the Estimated Deliver Date and print
	String estDelivery = driver.findElementByXPath("//div[@data-id='delivery-estimation']/span").getText();
	System.out.println("The estimated delivery date is " +estDelivery);
	
	//Mouse over on FREE DELIVERY & RETURNS*, get the tool tip text and print
	action.moveToElement(driver.findElementByXPath("(//a[@name='“headbanner.about.us\"']/parent::span)[1]")).perform();
	String toolTipText = driver.findElementByXPath("(//a[@name='“headbanner.about.us\"']/parent::span)[1]").getAttribute("title");
	System.out.println("The tooltip text is " +toolTipText);
	
	//Click on FREE DELIVERY & RETURNS
	driver.findElementByXPath("//a[text()='Free delivery & returns*']").click();
	
	//Click on Start chat
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Start chat']/parent::button")));
	js.executeScript("arguments[0].click();", driver.findElementByXPath("//span[text()='Start chat']/parent::button"));
	Thread.sleep(4000);
	
	//Navigate to new window
	Set<String> windowHandles = driver.getWindowHandles();
	List<String> windowId=new ArrayList<String> (windowHandles);
	driver.switchTo().window(windowId.get(1));
	
	//Enter you first name and a dummy email and click Start Chat
	
	driver.findElementById("prechat_customer_name_id").sendKeys("Testing purpose");
	driver.findElementById("prechat_customer_email_id").sendKeys("test@gmail.com");
	driver.findElementByXPath("//span[text()='Start Chat']").click();
	
	//Type Hi, click Send and print thr reply message and close the chat window.
	
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//button[text()='Save Chat']")));
	driver.findElementById("liveAgentChatTextArea").sendKeys("Hi");
	driver.findElementByXPath("//button[text()='Send']").click();
	Thread.sleep(3000);
	
	List<WebElement> chatMsg = driver.findElementsByXPath("//span[@class='operator']/span[@class='messageText']");
	int chatSize=chatMsg.size();
	System.out.println("The reply message from operator is "+chatMsg.get(chatSize-1).getText());
	
	driver.close();
	
	
	
	
	
	
	
	
}

}
