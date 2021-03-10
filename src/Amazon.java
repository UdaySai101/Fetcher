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
		wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete"));

		System.out.println("In Amazon: ");
		int j = 0;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		// fetching results..
		for (int i = 0; i < 40; i++) {
			String nameList = "//*[@id='search']/div[1]/div[2]/div/span[3]/div[2]/div[" + i
					+ "]/div/span/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/h2/a/span";
			String priceList = "//*[@id='search']/div[1]/div[2]/div/span[3]/div[2]/div[" + i
					+ "]/div/span/div/div/div[2]/div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/div/a";
			try {
				if (driver.findElement(By.xpath(nameList)).isDisplayed()
						&& driver.findElement(By.xpath(priceList)).isDisplayed()) {

					// extracting texts..
					nameList = driver.findElement(By.xpath(nameList)).getText();
					priceList = driver.findElement(By.xpath(priceList)).getText();

					// filtering results
					if (filter(nameList, priceList, input, omit) != null) {
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

	public Amazon(String input, String omit) {
		this.input = input;
		this.omit = omit;
	}

	public String filter(String nameList, String priceList, String input, String omit) {
		if (nameList.toLowerCase().contains(input.toLowerCase()) && !nameList.toLowerCase().contains(omit.toLowerCase())) {
			return nameList + " - " + priceList;
		}

		return null;
	}

}
