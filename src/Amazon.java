import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Amazon extends Thread {
	private String input;
	private String omit;
	private static WebDriver driver;

	public void run() {
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		driver = new ChromeDriver(options);

		driver.get("http://www.amazon.in/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// searching..
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(input + Keys.ENTER);

		// wait unil page loads..
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
				.equals("complete"));

		System.out.println("In Amazon: ");
		int count = 0;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		// fetching results..
		for (int i = 0; i < 40; i++) {

			// trying to find elements with two different sets of xpaths..
			String nameListpath1 = "//*[@id='search']/div[1]/div[2]/div/span[3]/div[2]/div[" + i
					+ "]/div/span/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/h2/a/span";
			String priceListpath1 = "//*[@id='search']/div[1]/div[2]/div/span[3]/div[2]/div[" + i
					+ "]/div/span/div/div/div[2]/div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/div/a";

			String nameListpath2 = "//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[2]/div[" + i
					+ "]/div/span/div/div/div[2]/h2/a/span ";
			String priceListpath2 = "//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[2]/div[" + i
					+ "]/div/span/div/div/div[4]/div[1]/div/div/a/span[1]/span[2]/span[2] ";

			try {
				try {
					if (driver.findElement(By.xpath(nameListpath1)).isDisplayed()
							&& driver.findElement(By.xpath(priceListpath1)).isDisplayed()) {

						// extracting texts..
						String nameList = driver.findElement(By.xpath(nameListpath1)).getText();
						String priceList = driver.findElement(By.xpath(priceListpath1)).getText();

						// filtering results
						if (filter(nameList, priceList, input, omit) != null) {
							System.out.println(filter(nameList, priceList, input, omit));
							count++;
						}
					}
				} catch (Exception e) {
					if (driver.findElement(By.xpath(nameListpath2)).isDisplayed()
							&& driver.findElement(By.xpath(priceListpath2)).isDisplayed()) {

						// extracting texts..
						String nameList = driver.findElement(By.xpath(nameListpath2)).getText();
						String priceList = driver.findElement(By.xpath(priceListpath2)).getText();

						// filtering..
						String result = filter(nameList, priceList, input, omit);

						if (result != null) {

							// printing results..
							System.out.println(result);
							count++;
						}
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

	public Amazon(String input, String omit) {
		this.input = input;
		this.omit = omit;
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
