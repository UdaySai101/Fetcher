import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class RelianceDigital extends Thread {
	private static String input;
	private static String omit;
	private static WebDriver driver;

	public void run() {

		ChromeOptions options=new ChromeOptions();
		options.setHeadless(true);

		driver = new ChromeDriver(options);

		driver.get("http://www.reliancedigital.in/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		driver.findElement(By.id("suggestionBoxEle")).sendKeys(input + Keys.RETURN);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("In RelianceDigital: ");
		int j = 0;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		for (int i = 0; i < 40; i++) {
			String s = "//*/ul/li[" + i + "]/div/a/div[1]/div[2]/p";
			String p = "//*/ul/li[" + i + "]/div/a/div[1]/div[2]/div[1]/div/div/span[1]";
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
		if (j == 0) {
			System.out.println("Cannot find anything!");
		}
	}

	public RelianceDigital(String input, String omit) {
		RelianceDigital.input = input;
		RelianceDigital.omit = omit;
	}

	public static String filter(String s, String p, String input, String omit) {
		
		if (s.toLowerCase().contains(input.toLowerCase())) {
			return s + " - " + p;
		}

		return null;
	}

}