package com.acmetelecom.test;

import com.acmetelecom.fakes.FakePrinter;
import com.acmetelecom.platform.Printer;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Simple tests for the fake printer. We will use this class in acceptance tests.
 */
public class FakePrinterTest {
	@Test
    public void testSimplePrinterFunc()	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		PrintStream out = new PrintStream(os);
		Printer printer = new FakePrinter(new PrintStream(out));

		printer.printHeading("Test","123","Business");
		printer.printItem("30/11/2011 00:00 jj","321" , "00:30" , "5,00");
		printer.printTotal("5,00");

		String expected = 	"Test/123 - Business" + "\n" + 
							"00:00" + "\t" + 
							"321" + "\t"+ 
							"00:30" + "\t" +
							"5,00" + "\n" + 
							"Total:" + "\t" +
							"5,00" + "\n";
		String result = "";
  
		try {
			// We simply print to an output stream which can be used for comparison
			result = os.toString("UTF8");
			expected = new String(expected.getBytes() , "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(result.equals(expected));
	}
}
