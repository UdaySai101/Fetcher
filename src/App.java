import java.util.Scanner;
import java.util.logging.Level;

public class App {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter your input to fetch: ");
		String input = scanner.nextLine();
		scanner.close();
		String omitter = "yareyaredaze";

		if (input.contains(" -o ")) {

			String[] parts = input.split(" -o ");
			input = parts[0];
			omitter = parts[1];

		}

		Amazon amazon = new Amazon(input, omitter);
		Flipkart flipkart = new Flipkart(input, omitter);
		RelianceDigital rdigital = new RelianceDigital(input, omitter);
 
		amazon.run();

		flipkart.run();

		rdigital.run();

	}

}
