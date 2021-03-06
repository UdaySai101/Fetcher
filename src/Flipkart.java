import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Flipkart extends Thread {
	private String input;
	private String omit;
	private static WebDriver driver;

	public void run() {

		ChromeOptions options=new ChromeOptions();
		options.setHeadless(true);
		driver=new ChromeDriver(options);

		driver.get("http://www.flipkart.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		try {
			driver.findElement(By.cssSelector("body > div._2Sn47c > div > div > button")).click();
		}
		catch(Exception e) {
		}
		 
		driver.findElement(By.xpath("//*[@id='container']/div/div[1]/div[1]/div[2]/div[2]/form/div/div/input"))
				.sendKeys(input);
		driver.findElement(By.xpath("//button[@class='L0Z3Pu']")).click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		int j = 0;
		System.out.println("In Flipkart: ");
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		for (int i = 0; i < 40; i++) {

			String s = "//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[   " + i + "    ]/div/div/div/a/div[2]/div["
					+ " 1 " + "]/div[1]";
			String p = "//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[   " + i + "  ]/div/div/div/a/div[2]/div["
					+ " 2 " + "]/div[1]/div[1]/div[1]";
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
		//webClient.close();
		if (j == 0) {
			System.out.println("Cannot find anything!");
		}
	}

	public Flipkart(String input, String omitter) {
		this.input = input;
		omit = omitter;
	}

	public static String filter(String s, String p, String input, String omit) {
		
		if (s.toLowerCase().contains(input.toLowerCase())) {
			return s + " - " + p;
		}

		return null;
	}

}
