package Learnings.Extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day16ajio {
	

@Test	
public void test() throws InterruptedException
{
	//www.ajio.com/shop/sale
	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win32/chromedriver.exe");
	ChromeOptions option=new ChromeOptions(); 
	option.addArguments("--disable-notifications"); 
	ChromeDriver driver =new ChromeDriver(option);
	driver.get("https://www.ajio.com/shop/sale");
	
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	WebDriverWait wait=new WebDriverWait(driver,30);
	
	
	
	//2) Enter Bags in the Search field and Select Bags in Women Handbags
	driver.findElementByXPath("//input[@name='searchVal']").sendKeys("Bags");
	Thread.sleep(1000);
	driver.findElementByXPath("//span[text()='Bags in ']/following-sibling::span[text()='Women Handbags']").click();
	
	//3) Click on five grid and Select SORT BY as "What's New"
	driver.findElementByClassName("five-grid").click();
	Select sort=new Select(driver.findElementByTagName("Select"));
	sort.selectByVisibleText("What's New");
	
	//4) Enter Price Range Min as 2000 and Max as 5000
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[@class='facet-left-pane-label' and text()='price']")));
	Thread.sleep(1000);
	driver.findElementByXPath("//span[@class='facet-left-pane-label' and text()='price']").click();
	driver.findElementById("minPrice").sendKeys("2000");
	driver.findElementById("maxPrice").sendKeys("5000");
	driver.findElementByXPath("//div[@class='facet-min-price-filter']//button[@type='submit']").click();
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[@class='pull-left' and text()='Rs.2000 - Rs.5000']")));
	
	if (driver.findElementByXPath("//span[@class='pull-left' and text()='Rs.2000 - Rs.5000']").isDisplayed())
	{
		System.out.println("The price filter is applied successfully");
	}
	
	//5) Click on the product "Puma Ferrari LS Shoulder Bag"
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[text()='Puma']/following-sibling::div[text()='Ferrari LS Shoulder Bag']")));
	Thread.sleep(1000);
	driver.findElementByXPath("//div[text()='Puma']/following-sibling::div[text()='Ferrari LS Shoulder Bag']").click();
	
	Set<String> windowHandles = driver.getWindowHandles();
	List<String> winList=new ArrayList<String>(windowHandles);
	driver.switchTo().window(winList.get(winList.size()-1));
	
	//6) Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount price for the coupon
	int sp = Integer.parseInt(driver.findElementByClassName("prod-sp").getText().replaceAll("\\D", ""));
	String couponCode = null;
	int actualDiscount = 0;
	if(sp>2690)
	{
	    String[] split = driver.findElementByClassName("promo-title").getText().split("\\s+"); //to split based on multiple space
	    couponCode=split[split.length-1];
		System.out.println("The applicable coupon code is "+couponCode);
		int discountPrice=Integer.parseInt(driver.findElementByXPath("//div[@class='promo-discounted-price']//span").getText().replaceAll("\\D", ""));
		
	    actualDiscount=sp-discountPrice;
		System.out.println("The actual discounted amount is "+ actualDiscount);
		
	}
	//7) Check the availability of the product for pincode 560043, print the expected delivery date if it is available
	driver.findElementByXPath("//div[@id='edd']//span[contains(text(),'Enter pin-code')]").click();
	driver.findElementByName("pincode").sendKeys("635001");
	driver.findElementByClassName("edd-pincode-modal-submit-btn").click();
	
	if(driver.findElementByClassName("edd-message-success-details").isDisplayed())
	{
		System.out.println("Ajio delivers to given pincode");
	}
	else
	{
		System.out.println("Ajio doesn't deliver to this pincode");
	}
	//8) Click on Other Informations under Product Details and Print the Customer Care address, phone and email
	driver.findElementByClassName("other-info-toggle").click();
	String text = driver.findElementByXPath("(//span[text()='Customer Care Address']//following::span[@class='other-info'])[1]").getText();
    System.out.println("The Customer Care Address,phone and email is " +text);
    
	
	//9) Click on ADD TO BAG and then GO TO BAG
    driver.findElementByXPath("//span[text()='ADD TO BAG']").click();
    Thread.sleep(2000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@class='ic-cart ']")));
    driver.findElementByXPath("//div[@class='ic-cart ']").click();
    
	//10) Check the Order Total before apply coupon
    String OrderTotal = driver.findElementByXPath("//section[@id='orderTotal']//span[@class='price-value bold-font']").getText();
    int OrderTotalint =Integer.parseInt(OrderTotal.substring(0,OrderTotal.length()-3).replaceAll("\\D", ""));  
    
	//11) Enter Coupon Code and Click Apply
    driver.findElementById("couponCodeInput").sendKeys(couponCode);
    driver.findElementByXPath("//button[text()='Apply']").click();
    
	//12) Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details
    Float savingsAmount =Float.parseFloat(driver.findElementByXPath("//span[text()='Coupon savings']/following-sibling::span").getText().replaceAll("[^0-9.]", "").substring(1));
    int roundedOffSavingAmt = Math.round(savingsAmount);
    System.out.println("The roundedOff savings amount is "+roundedOffSavingAmt);
    
    if(actualDiscount==roundedOffSavingAmt)
    {
    	System.out.println("The savings Amount is verified");
    }
    else
    {
    	System.out.println("The savings amount doesn't match");
    }
	//13) Click on Delete and Delete the item from Bag
    driver.findElementByClassName("delete-btn").click();
    driver.findElementByXPath("//div[text()='DELETE']").click();
    if(driver.findElementByClassName("empty-msg").isDisplayed())
    {
    	System.out.println("The product is deleted successfully");
    }
    
	//14) Close all the browsers
    driver.quit();
	
	
	
	
	
	
	
	
	
	

}
}