import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Ebay {
	
public void run(String input, String omit) {
	
	ChromeOptions options=new ChromeOptions();
	options.setHeadless(true);
	WebDriver driver=new ChromeDriver(options);

	driver.get("http://www.ebay.com");
	
	driver.findElement(By.id("gh-ac")).sendKeys(input+Keys.ENTER);
	
	List<WebElement> list=driver.findElements(By.cssSelector("h3.s-item__title"));
	List<String> rlist=new ArrayList<String>();
	//List<WebElement> price=driver.findElement(By.className("s-item__details clearfix"));
	
	
	
	String s=driver.findElement(By.className("\"s-item__details clearfix")).getText();
	System.out.println(s);
	
	
	
	
	
	
	
}










}
