import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Flipkart extends Thread {
	private String input;
	private String omit;
	private static WebDriver driver;

	public void run() {

		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		driver = new ChromeDriver(options);

		driver.get("http://www.flipkart.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		try {
			// killing login popup..
			driver.findElement(By.cssSelector("body > div._2Sn47c > div > div > button")).click();
		} catch (Exception e) {
		}

		// searching..
		driver.findElement(By.xpath("//*[@id='container']/div/div[1]/div[1]/div[2]/div[2]/form/div/div/input"))
				.sendKeys(input);
		driver.findElement(By.xpath("//button[@class='L0Z3Pu']")).click();

		// wait unil page loads..
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
				.equals("complete"));

		int resultCount = 0;
		System.out.println("In Flipkart: ");
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		// fetching..
		for (int i = 0; i < 40; i++) {

			String nameListpath = "//*[@id='container']/div/div[3]/div[1]/div[2]/div[   " + i
					+ "    ]/div/div/div/a/div[2]/div[" + " 1 " + "]/div[1]";
			String priceListpath = "//*[@id='container']/div/div[3]/div[1]/div[2]/div[   " + i
					+ "  ]/div/div/div/a/div[2]/div[" + " 2 " + "]/div[1]/div[1]/div[1]";

			try {

				try {
					if (driver.findElement(By.xpath(nameListpath)).isDisplayed()
							&& driver.findElement(By.xpath(priceListpath)).isDisplayed()) {

						// extracting texts..
						String nameList = driver.findElement(By.xpath(nameListpath)).getText();
						String priceList = driver.findElement(By.xpath(priceListpath)).getText();
						if (filter(nameList, priceList, input, omit) != null) {

							// filtering..
							System.out.println(filter(nameList, priceList, input, omit));
							resultCount++;
						}
					}
				}

				catch (Exception e) {

					// finding elements with a different sets of xpaths..
					for (int k = 1; k < 5; k++) {

						String nameListpath2 = "//*[@id='container']/div/div[3]/div[1]/div[2]/div[" + i + "]/div/div["
								+ k + "]/div/a[2]";
						String priceListpath2 = "//*[@id='container']/div/div[3]/div[1]/div[2]/div[" + i + "]/div/div["
								+ k + "]/div/a[3]/div/div[1]";

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
								resultCount++;
							}
						}
					}
				}
			} catch (Exception e) {
				continue;
			}
		}

		driver.quit();

		// if nothing was found..
		if (resultCount == 0) {
			System.out.println("Cannot find anything!");
		}
	}

	public Flipkart(String input, String omitter) {
		this.input = input;
		omit = omitter;
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
