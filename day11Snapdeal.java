package Learnings.Extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day11Snapdeal {
	
	public ChromeDriver driver;
	public WebDriverWait wait;
	public int prodPrice;
	public int delCharge;
	
public int validatecart(int cartPriceEachProduct,String productName)
{
	driver.findElementByXPath("(//div[@id='add-cart-button-id']/span)[1]").click();
	//wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[@class='you-pay']/span")));
	int youPay=Integer.parseInt(driver.findElementByXPath("//div[@class='you-pay']/span").getText().replaceAll("\\D", ""));
	if(youPay==cartPriceEachProduct)
	{System.out.println("The cart price and product price is validated for" +productName);}
	
	else{System.out.println("The cart price didnt match the total product price");}
	driver.findElementByXPath("//span[@class='sd-icon sd-icon-delete-sign close-mess']");
	return youPay;
}
	
public int price() {
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[@class='payBlkBig']")));
	int prodPrice=Integer.parseInt(driver.findElementByXPath("//span[@class='payBlkBig']").getText());
	System.out.println("The prodPrice is" +prodPrice);
	int delCharge=Integer.parseInt(driver.findElementByXPath("(//span[@class='head' and contains(text(),'Delivery in')]/following::span[@class='availCharges'])[2]").getText().replaceAll("\\D", ""));
	System.out.println("The delPrice is" +delCharge);
	return prodPrice+delCharge;	
}

@Test
public void snapdeal() throws InterruptedException
{
	//Go to https://www.snapdeal.com/
	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
	
	ChromeOptions option =new ChromeOptions();
	option.addArguments("--disable-notifications");
	 driver=new ChromeDriver(option);
	Actions action=new Actions(driver);
	wait=new WebDriverWait(driver,30);
	
	driver.get("https://www.snapdeal.com/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	//Mouse over on Toys, Kids' Fashion & more and click on Toys
	action.moveToElement(driver.findElementByXPath("//span[contains(text(),\"Toys, Kids' Fashion & more\")]")).perform();
	driver.findElementByXPath("//span[@class='headingText' and contains(text(),'Toys')]").click();
	
	//Click Educational Toys in Toys & Games
	driver.findElementByXPath("//div[@class='sub-cat-name ' and contains(text(),'Educational Toys')]").click();
	
	//Click the Customer Rating 4 star and Up
	Thread.sleep(3000);
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//label[@for='avgRating-4.0']")));
	driver.findElementByXPath("//label[@for='avgRating-4.0']").click();
	
	//Click the offer as 40-50
	Thread.sleep(3000);
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//label[@for='discount-40%20-%2050']")));
	driver.findElementByXPath("//label[@for='discount-40%20-%2050']").click();
	
	//filter validation
	if(driver.findElementByXPath("(//div[text()='Discount %: ']/a[text()='40 - 50'])[1]").isDisplayed() & driver.findElementByXPath("(//div[text()='Customer Rating: ']/a[text()='4.0'])[1]").isDisplayed())
	{
	System.out.println("The rating and discount filter is applied successfully");
	}
	else
	{
		System.out.println("The filter is not applied");
	}
	
	//Check the availability for the pincode
	driver.findElementByXPath("//input[@placeholder='Enter your pincode']").sendKeys("600012");
	Thread.sleep(2000);
	action.moveToElement(driver.findElementByXPath("//button[text()='Check']")).click().build().perform();
	wait.until(ExpectedConditions.visibilityOf((driver.findElementByXPath("//input[@checked='checked']/following-sibling::label[1]"))));
	if (driver.findElementByXPath("//input[@checked='checked']/following-sibling::label[1]").isDisplayed())
	{
		System.out.println("The deliver option is available to the given pincode");
	}
	
	else
	{
		System.out.println("The delivery option is not available for given pincode");
	}
	
	//Click the Quick View of the first product 
	String productName=driver.findElementByXPath("(//p[@class='product-title'])[1]").getText();
	Thread.sleep(3000);
	action.moveToElement(driver.findElementByXPath("(//picture[@class='picture-elem'])[1]")).pause(3000).perform();
	driver.findElementByXPath("(//picture[@class='picture-elem'])[1]/following::div[contains(text(),'Quick View')][1]").click();
	
	//Click on View Details
	Thread.sleep(3000);
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("(//a[contains(@class,'btn btn-theme-secondary')])[1]")));
	action.moveToElement(driver.findElementByXPath("(//a[contains(@class,'btn btn-theme-secondary')])[1]")).click().build().perform();
	Thread.sleep(3000);
	
	//Capture the Price of the Product and Delivery Charge
	int cartPriceFirstProduct = price();
	
	//Add to cart
	int youPayFirstProduct = validatecart(cartPriceFirstProduct,productName);
	
	//Search for Sanitizer
	driver.findElementById("inputValEnter").sendKeys("Sanitizer",Keys.ENTER);
	
	//Click on Product "BioAyurveda Neem Power Hand Sanitizer"
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//p[text()='BioAyurveda Neem Power  Hand Sanitizer 500 mL Pack of 1'])[1]")));
	action.moveToElement(driver.findElementByXPath("(//p[text()='BioAyurveda Neem Power  Hand Sanitizer 500 mL Pack of 1'])[1]")).click().build().perform();
	
	//Capture the Price and Delivery Charge
	Set<String> windowHandles = driver.getWindowHandles(); List<String>
	windows=new ArrayList<String> (windowHandles);
	System.out.println(windows.size());
	driver.switchTo().window(windows.get(windows.size()-1));
	int cartPriceSecondProduct=price();
	
	//Click on Add to Cart
	driver.findElementByXPath("(//div[@id='add-cart-button-id']/span)[1]").click();
	
	//Click on Cart
	Thread.sleep(3000);
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByClassName("cartTextSpan")));
	driver.findElementByClassName("cartTextSpan").click();
	
	//Validate the Proceed to Pay matches the total amount of both the products
	
	int carttotalAmt = Integer.parseInt(driver.findElementByXPath("//input[contains(@value,'PROCEED TO PAY')]").getAttribute("value").replaceAll("\\D", ""));
	
	if(carttotalAmt==youPayFirstProduct+cartPriceSecondProduct)
	
	{
		System.out.println("The TotalCart Amount is " +carttotalAmt);
	}
	
	else
	{
		System.out.println("There is mismatch in cart Amount");
	}
	
	driver.quit();
	
	
}

}
