package Learnings.Extra;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day17azure {
	
	
@Test	
public void azure()
{
	
	//https://github.com/TestLeafPages/Research/blob/master/PdfFileDownload.java
	//1) Go to https://azure.microsoft.com/en-in/
	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win32/chromedriver.exe");
	ChromeOptions option=new ChromeOptions(); 
	option.addArguments("--disable-notifications"); 
	ChromeDriver driver =new ChromeDriver(option);
	driver.get("https://azure.microsoft.com/en-in/");
	
	
   
	
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	WebDriverWait wait=new WebDriverWait(driver,30);
	JavascriptExecutor js=(JavascriptExecutor) driver;
	
		//2) Click on Pricing
	driver.findElementById("navigation-pricing").click();
	
		//3) Click on Pricing Calculator
	driver.findElementByXPath("//a[contains(text(),'Pricing calculator')]").click();
		
		//4) Click on Containers
	driver.findElementByXPath("//button[@data-event-property='containers']").click();

		//5) Select Container Instances
	driver.findElementByXPath("(//span[@class='service-heading' and text()='Container Instances'])[2]").click();
	

		//6) Click on Container Instance Added View
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//div[text()='Container Instances added.']//a[text()='View']")));
	driver.findElementByXPath("//div[text()='Container Instances added.']//a[text()='View']").click();
	
		//7) Select Region as "South India"
	Select region=new Select(driver.findElementByXPath("//select[@name='region']"));
	region.selectByVisibleText("South India");
	
		//8) Set the Duration as 180000 seconds
	driver.findElementByName("seconds").clear();
	driver.findElementByName("seconds").sendKeys("80000");
	
		//9) Select the Memory as 4GB
	Select memory=new Select(driver.findElementByName("memory"));
	memory.selectByVisibleText("4 GB");
	
		//10) Enable SHOW DEV/TEST PRICING
	driver.findElementByName("devTestSelected").click();
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByClassName("notification-copy")));
	String pricingContent = driver.findElementByClassName("notification-copy").getText();
	System.out.println("The notification content is "+pricingContent);
	wait.until(ExpectedConditions.invisibilityOf(driver.findElementByClassName("notification-copy")));
		//11) Select Indian Rupee  as currency
	Select currency=new Select(driver.findElementByXPath("//select[@class='select currency-dropdown']"));
	currency.selectByVisibleText("Indian Rupee (₹)");
	
		//12) Print the Estimated monthly price
	String estimatedMonthlyCost = driver.findElementByXPath("(//h3[text()='Estimated monthly cost']//following::span[@class='numeric']/span)[2]").getText();
	System.out.println("The estimated monthly cost is "+estimatedMonthlyCost);
	
		//13) Click on Export to download the estimate as excel
	driver.findElementByXPath("//button[@class='calculator-button button-transparent export-button']").click();
	
	//Verify the downloded file in the local folder
	File containerFile = new File("C:\\Users\\sudham\\Downloads\\ExportedEstimate.xlsx");
	if(containerFile.exists())
	{
		System.out.println("Estimate Sheet Downloaded successfully");
	}
		//14) Navigate to Example Scenarios and Select CI/CD for Containers
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByLinkText("Example Scenarios")));
	js.executeScript("arguments[0].click();", driver.findElementByXPath("//a[text()='Example Scenarios']"));
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='CI/CD for Containers']")));
	js.executeScript("arguments[0].click();", driver.findElementByXPath("//span[text()='CI/CD for Containers']"));
	
		//15) Click Add to Estimate
	js.executeScript("arguments[0].click();", driver.findElementByXPath("//button[text()='Add to estimate']"));
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByClassName("notification-copy")));
	
		//16) Change the Currency as Indian Rupee
	wait.until(ExpectedConditions.invisibilityOf(driver.findElementByClassName("notification-copy")));
	Select ciCurrency=new Select(driver.findElementByXPath("//select[@class='select currency-dropdown']"));
	ciCurrency.selectByVisibleText("Indian Rupee (₹)");
	
		//17) Enable SHOW DEV/TEST PRICING
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByName("devTestSelected")));
	driver.findElementByName("devTestSelected").click();
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByClassName("notification-copy")));
	String ciPricingContent = driver.findElementByClassName("notification-copy").getText();
	System.out.println("The notification content is "+ciPricingContent);
	
		//18) Export the Estimate
	wait.until(ExpectedConditions.invisibilityOf(driver.findElementByClassName("notification-copy")));
	driver.findElementByXPath("//button[@class='calculator-button button-transparent export-button']").click();
	File examplefile = new File("C:\\Users\\sudham\\Downloads\\ExportedEstimate.xlsx");
	if(examplefile.exists())
	{
		System.out.println("Estimate sheet Downloaded successfully");
	}
	
}

}
