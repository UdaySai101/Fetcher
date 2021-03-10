import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RelianceDigital extends Thread {
	private static String input;
	private static String omit;
	private static WebDriver driver;

	public void run() {

		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);

		driver = new ChromeDriver(options);

		driver.get("http://www.reliancedigital.in/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// searching..
		driver.findElement(By.id("suggestionBoxEle")).sendKeys(input + Keys.RETURN);
		
		// wait unil page loads.. 
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete"));
		
		System.out.println("In RelianceDigital: ");
		int j = 0;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		// fetching..
		for (int i = 0; i < 40; i++) {
			String nameList = "//*/ul/li[" + i + "]/div/a/div[1]/div[2]/p";
			String priceList = "//*/ul/li[" + i + "]/div/a/div[1]/div[2]/div[1]/div/div/span[1]";
			try {
				if (driver.findElement(By.xpath(nameList)).isDisplayed()
						&& driver.findElement(By.xpath(priceList)).isDisplayed()) {

					// extracting texts..
					nameList = driver.findElement(By.xpath(nameList)).getText();
					priceList = driver.findElement(By.xpath(priceList)).getText();

					if (filter(nameList, priceList, input, omit) != null) {

						// filtering..
						System.out.println(filter(nameList, priceList, input, omit));
						j++;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}

		driver.quit();
		if (j == 0) {
			System.out.println("Cannot find anything!");
		}
	}

	public RelianceDigital(String input, String omit) {
		RelianceDigital.input = input;
		RelianceDigital.omit = omit;
	}

	public  String filter(String nameList, String priceList, String input, String omit) {
		if (nameList.toLowerCase().contains(input.toLowerCase())) {
			return nameList + " - " + priceList;
		}

		return null;
	}

}