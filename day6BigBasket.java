package Learnings.Extra;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day6BigBasket {
	
	public ChromeDriver driver;
	public Actions action;
	
//method to add items in cart
public void addToCart(String product,Float price)
{
	driver.findElementByXPath("(//a[contains(text(),'"+product+"')]/following::div[@class='delivery-opt']//button[contains(text(),'Add')])[1]").click();
	if (driver.findElementByXPath("//div[@class='toast-title']").isDisplayed()) {
		System.out.println(product+ " added to cart succefully");
		driver.findElementByClassName("toast-close-button").click();}}

//method to validate the cart items with quantity
public Float cartValidation(String product,Float price) {
	action.moveToElement(driver.findElementByXPath("//span[@title='Your Basket']")).pause(2000).perform();
	String[] eachqty=driver.findElementByXPath("//div[@class='product-name']/a[contains(text(),'"+product+"')]/following::div[1]").getText().split(" ");
	int intqty=Integer.parseInt(eachqty[0]);
	Float Cartprice=intqty*price;
	System.out.println("The cart price of "+product+" is " +Cartprice);
	return Cartprice;}

//method to validate total
public Float carttotal(Float dal,Float rice,Float total) {
if (dal+rice==total)
	{System.out.println("The cart total is validated");}
	else
	{System.out.println("There is mismatch in cart total");}
	return total;}

@Test
public void bigbasket() throws InterruptedException
{
		//Go to https://www.bigbasket.com/
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win32/chromedriver.exe");
		 driver=new ChromeDriver();
		 driver.get("https://www.bigbasket.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		 action=new Actions(driver);
		WebDriverWait wait=new WebDriverWait(driver,30);
		
		//handle location popup
		if(driver.findElementByXPath("//label[contains(text(),'You are seeing our catalogue in')]").isDisplayed())
		{
			driver.findElementByXPath("//span[@class='close-btn']").click();
		}
		
		//mouse over on  Shop by Category.Go to FOODGRAINS, OIL & MASALA --> RICE & RICE PRODUCTS
		action.moveToElement(driver.findElementByXPath("//a[@class='dropdown-toggle meganav-shop']")).click().build().perform();
		action.moveToElement(driver.findElementByXPath("(//a[text()='Foodgrains, Oil & Masala'])[2]")).pause(2000).perform();
		action.moveToElement(driver.findElementByXPath("(//a[text()='Rice & Rice Products'])[2]")).perform();
		action.moveToElement(driver.findElementByXPath("(//a[text()='Boiled & Steam Rice'])[2]")).click().build().perform();
		
		//Choose the Brand as bb Royal
		driver.findElementByXPath("(//span[text()='bb Royal'])[1]").click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[text()='bb Royal']")));
		
		//Go to Ponni Boiled Rice - Super Premium and select 5kg bag from Dropdown
		Thread.sleep(3000);
		driver.findElementByXPath("//a[text()='Ponni Boiled Rice - Super Premium']/following::button[1]").click();
		driver.findElementByXPath("//a[text()='Ponni Boiled Rice - Super Premium']/following::button[1]/following::span[text()='5 kg'][1]").click();
		Float ricePrice=Float.parseFloat(driver.findElementByXPath("//a[text()='Ponni Boiled Rice - Super Premium']/following::span[@class='discnt-price'][1]/span").getText());
		System.out.println("The price of rice is" +ricePrice);
		
		//Click Add button
		addToCart("Ponni Boiled Rice - Super Premium",ricePrice);
		Float cartricePrice=cartValidation("Ponni Boiled Rice - Super Premium",ricePrice);
		
		//Type Dal in Search field and enter
		driver.findElementByXPath("//input[@id='input']").sendKeys("Dal",Keys.ENTER);
		
		//Go to Toor/Arhar Dal and select 2kg & set Qty 2
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/following::button[1]")));
		driver.findElementByXPath("//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/following::button[1]").click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/following::button[1]/following::span[text()='2 kg'][1]")));
		driver.findElementByXPath("//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/following::button[1]/following::span[text()='2 kg'][1]").click();
		driver.findElementByXPath("//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/following::input[@ng-model='vm.startQuantity'][1]").clear();
		driver.findElementByXPath("//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/following::input[@ng-model='vm.startQuantity'][1]").sendKeys("2");
	
		//Print the price of Dal
		Float dalPrice=Float.parseFloat(driver.findElementByXPath("//a[text()='Toor/Arhar Dal/Thuvaram Paruppu']/following::span[@class='discnt-price'][1]/span").getText());
		System.out.println("The price of Dal is "+dalPrice);
		
		
		//Click Add button
		addToCart("Toor/Arhar", dalPrice);
		Float cartdalPrice=cartValidation("Toor/Arhar", dalPrice);
	
		//Mouse hover on My Basket 
		action.moveToElement(driver.findElementByXPath("//span[@title='Your Basket']")).pause(2000).perform();
		
		
		//Validate the Sub Total displayed for the selected items
		Float totalPrice=Float.parseFloat(driver.findElementByXPath("//span[@qa='subTotalMB']").getText());
		System.out.println("The subtotal in cart is " +totalPrice);
		//cart total validation
		System.out.println("The total amount of 1ricce and 2dal is " +carttotal(cartricePrice,cartdalPrice,totalPrice));
		
		//Reduce the Quantity of Dal as 1 
		action.moveToElement(driver.findElementByXPath("//span[@title='Your Basket']")).pause(2000).perform();
		driver.findElementByXPath("//div[@class='product-name']/a[contains(text(),'Toor/Arhar')]/following::button[@qa='decQtyMB']").click();
		Thread.sleep(3000);
		Float newtotalPrice=Float.parseFloat(driver.findElementByXPath("//span[@qa='subTotalMB']").getText());
		System.out.println("The total in cart is " +newtotalPrice);
		
		//cart total validation
		Float newdalPrice=cartValidation("Toor/Arhar",dalPrice);
		System.out.println("The total amount of 1 rice and 1 dal is " +carttotal(cartricePrice,newdalPrice,newtotalPrice));
	
		driver.close();
}
}
