package Learnings.Extra;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class day13shiksha {
	
public void dropdown(WebElement element,String text)
{
	Select dropdownvalue=new Select(element);
	dropdownvalue.selectByVisibleText(text);
}
	
@Test
public void shiksha() throws InterruptedException
{
	//Go to https://studyabroad.shiksha.com/
	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win32/chromedriver.exe");
	ChromeOptions option=new ChromeOptions();
	option.addArguments("--disable-notifications");
	ChromeDriver driver=new ChromeDriver(option);
	driver.get("https://studyabroad.shiksha.com/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	WebDriverWait wait=new WebDriverWait(driver,30);
	Actions action=new Actions(driver);
	JavascriptExecutor js=(JavascriptExecutor) driver; 
	
	//Mouse over on Colleges and click MS in Computer Science &Engg under MS Colleges
	action.moveToElement(driver.findElementByXPath("//label[@class='menuTab-div fnt-wt melabel' and text()='Colleges ']")).pause(2000).perform();
	driver.findElementByXPath("//a[@class='pnl_a gaTrack' and text()='MS in Computer Science &Engg']").click();
	
	//Select GRE under Exam Accepted and Score 300 & Below 
	driver.findElementByXPath("//form[@id='formCategoryPageFilter']//p[text()='GRE']").click();
	Thread.sleep(1000);
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("(//select[@name='examsScore[]'])[1]")));
	
	dropdown(driver.findElementByXPath("(//select[@name='examsScore[]'])[1]"),"300 & below");
	//Select dropdown=new Select(driver.findElementByXPath("(//select[@name='examsScore[]'])[1]"));
	//dropdown.selectByVisibleText("300 & below");
	
	//Max 10 Lakhs under 1st year Total fees, USA under countries
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@id='feesFilterScrollbar']//p[text()='Max 10 Lakhs']")));
	Thread.sleep(1000);
	driver.findElementByXPath("//div[@id='feesFilterScrollbar']//p[text()='Max 10 Lakhs']").click();
	Thread.sleep(1000);
	js.executeScript("arguments[0].click();",driver.findElementByXPath("//a[text()='USA']/preceding::span[@class='common-sprite'][1]"));
	
	//Select Sort By: Low to high 1st year total fees
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementById("categorySorter")));
	Thread.sleep(1000);
	dropdown((driver.findElementById("categorySorter")),"Low to high 1st year total fees");
	Thread.sleep(1000);
	//Click Add to compare of the College having least fees with Public University, Scholarship and Accommodation facilities
	List<WebElement> publicUniv = driver.findElementsByXPath("(//div[@class='tuple-title']//a)");
	
	List<WebElement> university = driver.findElementsByXPath("//div[@class='detail-col flLt']/p[text()='Public university']/span");
	List<WebElement> scholarship = driver.findElementsByXPath("//div[@class='detail-col flLt']/p[text()='Scholarship']/span");
	List<WebElement> accomodation = driver.findElementsByXPath("//div[@class='detail-col flLt']/p[text()='Accommodation']/span");
	
	String leastClg = null;

	for (int i=1;i<=publicUniv.size();i++)
	{
		String univ=university.get(i).getAttribute("class");
		String scholar=scholarship.get(i).getAttribute("class");
		String acc=accomodation.get(i).getAttribute("class");
		
		if(univ.equals("tick-mark") & scholar.equals("tick-mark") & acc.equals("tick-mark") )
		{
			leastClg=publicUniv.get(i).getText();
			System.out.println("The least college with all 3 facility is" +leastClg);
			break;
		}
	}
	driver.findElementByXPath("(//div[@class='tuple-title']//a[text()='"+leastClg+"']/following::p[text()='Add to compare'])[1]").click();
	
	//Select the first college under Compare with similar colleges
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//div[@id='recommendationDiv']//a)[1]")));
	Thread.sleep(1000);
	driver.findElementByXPath("(//div[@id='recommendationDiv']//a)[1]").click();
	
	//Click on Compare College>
	driver.findElementByXPath("//div[@class='compare-col flLt']//strong[text()=\"Compare Colleges >\"]").click();
	
	//Select When to Study as 2021
	driver.findElementByXPath("//input[@name='whenPlanToGo']/following::strong[text()='2021']").click();
	
	//Select Preferred Countries as USA
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@class='placeholder' and text()='Preferred Countries']")));
	Thread.sleep(1000);
	js.executeScript("arguments[0].click();",driver.findElementByXPath("//div[@class='placeholder' and text()='Preferred Countries']"));
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//label[@class='nolnht'])[1]")));
	driver.findElementByXPath("(//label[@class='nolnht'])[1]").click();
	driver.findElementByXPath("//a[@class='ok-btn']").click();
	
	//Select Level of Study as Masters
	driver.findElementByXPath("//strong[text()='Masters']").click();
	
	//Select Preferred Course as MS
	js.executeScript("arguments[0].click();", driver.findElementByXPath("//div[@class='placeholder' and text()='Preferred Course']"));
	Thread.sleep(2000);
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//li[@datalvl='Masters' and text()='MS']")));
	driver.findElementByXPath("//li[@datalvl='Masters' and text()='MS']").click();
	
	
	//Select Specialization as "Computer Science & Engineering"
	js.executeScript("arguments[0].click();",driver.findElementByXPath("//div[@class='placeholder' and text()='Preferred Specialisations']"));
	driver.findElementByXPath("//li[@class='lr-row' and text()='Computer Science & Engineering']").click();
	
	//Click on Sign Up
	driver.findElementByXPath("//a[contains(text(),'Sign Up')]").click();
	
	//Print all the warning messages displayed on the screen for missed mandatory fields
	 List<WebElement> warnMsgs = driver.findElementsByXPath("//div[@class='helper-text' and contains(text(),'Please')]");
	 for (WebElement eachWarn : warnMsgs) {
		 System.out.println(eachWarn.getText());
		
	}
	 
	 driver.close();
	
	
	
	
}}