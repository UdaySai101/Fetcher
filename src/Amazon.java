
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Amazon extends Thread {
	private String input;
	private String omit;
	private static WebDriver driver;

	public void run() {
		ChromeOptions options=new ChromeOptions();
		options.setHeadless(true);
		driver=new ChromeDriver(options);

		driver.get("http://www.amazon.in/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(input + Keys.ENTER);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("In Amazon: ");
		int j=0;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		for (int i = 0; i < 40; i++) {
			String s = "//*[@id='search']/div[1]/div[2]/div/span[3]/div[2]/div[" + i
					+ "]/div/span/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/h2/a/span";
			String p = "//*[@id='search']/div[1]/div[2]/div/span[3]/div[2]/div[" + i
					+ "]/div/span/div/div/div[2]/div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/div/a";
			try {
				if (driver.findElement(By.xpath(s)).isDisplayed() && driver.findElement(By.xpath(p)).isDisplayed()) {
					s = driver.findElement(By.xpath(s)).getText();
					p = driver.findElement(By.xpath(p)).getText();
					if (filter(s, p, input, omit) != null) {
						System.out.println(filter(s, p, input, omit));
						j++;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		driver.quit();
		if(j==0) {
			System.out.println("Cannot find anything!");
		}
	}

	public Amazon(String input, String omit) {
		this.input = input;
		this.omit = omit;
	}

	public static String filter(String s, String p, String input, String omit) {
		if (s.toLowerCase().contains(input.toLowerCase())) {
			return s + " - " + p;
		}

		return null;
	}

}
