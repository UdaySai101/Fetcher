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
		wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
				.equals("complete"));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}

		System.out.println("In RelianceDigital: ");
		int count = 0;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		// fetching..
		for (int i = 0; i < 40; i++) {
			String nameListpath = "//*/ul/li[" + i + "]/div/a/div[1]/div[2]/p";
			String priceListpath = "//*/ul/li[" + i + "]/div/a/div[1]/div[2]/div[1]/div/div/span[1]";
			try {
				if (driver.findElement(By.xpath(nameListpath)).isDisplayed()
						&& driver.findElement(By.xpath(priceListpath)).isDisplayed()) {

					// extracting texts..
					String nameList = driver.findElement(By.xpath(nameListpath)).getText();
					String priceList = driver.findElement(By.xpath(priceListpath)).getText();

					// filtering..
					String result = filter(nameList, priceList, input, omit);

					if (result != null) {

						// printing results..
						System.out.println(result);
						count++;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}

		driver.quit();
		
		// if nothing was found..
		if (count == 0) {
			System.out.println("Cannot find anything!");
		}
	}

	public RelianceDigital(String input, String omit) {
		RelianceDigital.input = input;
		RelianceDigital.omit = omit;
	}

	public String filter(String itemName, String itemPrice, String input, String omit) {

		String[] splits = input.split(" ");

		for (int i = 0; i < splits.length; i++) {

			if (itemName.toLowerCase().contains(splits[i].toLowerCase())) {
				continue;
			}

			else {
				return null;
			}
		}

		if (!itemName.toLowerCase().contains(omit.toLowerCase())) {
			return itemName + " - " + itemPrice;
		}

		return null;
	}

}