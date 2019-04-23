package demo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculatorTest {

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
	public void testAdd() {
		Calculator calculator = new Calculator();
	    int a = 1234;
	    int b = 5678;
	    int actual = calculator.addition(a, b);
	    int expected = 6912;
	 
	    assertEquals(expected, actual);
		
	}
	
	
	@Test
	public void testSubtract() {
	    Calculator calculator = new Calculator();
	    int a = 1234;
	    int b = 5678;
	    int actual = calculator.substraction(b, a);
	 
	    int expected = 4443;
	 
	    assertEquals(expected, actual);
	}


}
