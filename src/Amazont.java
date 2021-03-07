import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Amazont {
	private static String input;
	private static String omit;
	private static WebDriver driver;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ChromeOptions options=new ChromeOptions();
		options.setHeadless(true);
		driver = new ChromeDriver(options);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
