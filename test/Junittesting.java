import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Junittesting {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAmazon() {
	
		String name= "Oneplus 8 pro back cover";
		String price= "?499";
		String input = "Oneplus";
		String omit = "back cover";
		
		Amazon amazon=new Amazon(input,omit);

	    assertEquals(amazon.filter(name,price,input,omit), null);
	}
	
	@Test
	public void testFlipkart() {
		
		String name= "Redmi Note 7 pro back cover";
		String price= "?499";
		String input = "Redmi Note 9";
		String omit = "back cover";
		
		Flipkart flipkart=new Flipkart(input,omit);
		
		assertEquals(flipkart.filter(name,price,input,omit), null);
	}
	
	@Test
	public void testRelianceDigital() {
		
		String name= "Samsung Galaxy S21 back cover";
		String price= "?499";
		String input = "Oneplus";
		String omit = "back cover";
		
		RelianceDigital rDigital=new RelianceDigital(input,omit);
		
		assertEquals(rDigital.filter(name,price,input,omit), null);
		
	}
	
	

}
