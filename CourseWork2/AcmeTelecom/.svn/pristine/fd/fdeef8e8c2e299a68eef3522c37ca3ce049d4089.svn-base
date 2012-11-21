package com.acmetelecom.test;

import static com.acmetelecom.builders.CallBuilder.aCall;
import static com.acmetelecom.builders.CustomerBuilder.aCustomer;
import static com.acmetelecom.builders.Minute.Minutes;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.Test;

import com.acmetelecom.Call;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.platform.HtmlPrinter;
import com.acmetelecom.platform.Printer;

/**
 * Basic tests for HtmlPrinter class. We loosely check that the format
 * is accurate and that all the information required is present in the
 * output stream. Tests regarding format could be made more stringent.
 */
public class HtmlPrinterTest {
	private static final String HTML_BEGIN = "<html>";
	private static final String HTML_END = "</html>";
	private static final String HEAD_BEGIN = "<head>";
	private static final String HEAD_END = "</head>";
	private static final String BODY_BEGIN = "<body>";
	private static final String BODY_END = "</body>";

	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	private final Customer c1 = aCustomer().
									named("Yu Liu").
									withPhoneNumber("447427476882").
									andPricePlan(Tariff.Standard.toString()).build();

	private final Customer c2 = aCustomer().
									named("Yu Liu").
									withPhoneNumber("447427476882").
									andPricePlan(Tariff.Standard.toString()).build();

	private String withNoSpacesOrNewlines(String s) {
		return s.replace("\r", "").replace("\n","").trim();
	}
	
	private boolean isValidHtml(String s) {
		return 	s.startsWith(HTML_BEGIN) && 
				s.endsWith(HTML_END) &&
				s.contains(HEAD_BEGIN.subSequence(0, HEAD_BEGIN.length() - 1)) && 
				s.contains(HEAD_END.subSequence(0, HEAD_END.length() - 1)) &&
				s.contains(HEAD_BEGIN.subSequence(0, HEAD_BEGIN.length() - 1)) && 
				s.contains(BODY_BEGIN.subSequence(0, BODY_END.length() - 1)); 
	}
	
	private boolean containsValidHeader(Customer c, String s) {
		return 	s.contains(c.getFullName().subSequence(0, c.getFullName().length() - 1)) && 
				s.contains(c.getPhoneNumber().subSequence(0, c.getPhoneNumber().length() - 1)) &&
				s.contains(c.getPricePlan().subSequence(0, c.getPricePlan().length() - 1));
	}
	
	private boolean containsValidItem(Call c, String s) {
		return 	s.contains(c.startTime().toString().subSequence(0, c.startTime().toString().length() - 1)) && 
				s.contains(c.callee().subSequence(0, c.callee().length() - 1)) &&
				s.contains(Integer.toString(c.durationSeconds()).subSequence(0, Integer.toString(c.durationSeconds()).length() - 1)) &&
				s.contains("0.0");
	}
	
	private boolean containsValidTotal(String total, String s) {
		return s.contains(total);
	}
	
	@Test
	public void testValidHtml() throws FileNotFoundException {
		Printer printer = new HtmlPrinter(new PrintStream(this.out));
		
		Call call = aCall().
						from(c1).
						to(c2).
						startingNow().
						lastingFor(Minutes(5)).build();
		
		printer.printHeading(c1.getFullName(), c1.getPhoneNumber(), c1.getPricePlan());
		printer.printItem(call.startTime().toString(), c2.getPhoneNumber(), Integer.toString(call.durationSeconds()), "0.0");
		printer.printTotal("0.0");
		
		assertTrue(isValidHtml(withNoSpacesOrNewlines(this.out.toString())));
	}
	
	@Test
	public void testPrintHeading() throws FileNotFoundException {
		Printer printer = new HtmlPrinter(new PrintStream(this.out));

		Call call = aCall().
						from(c1).
						to(c2).
						startingNow().
						lastingFor(Minutes(5)).build();

		printer.printHeading(c1.getFullName(), c1.getPhoneNumber(), c1.getPricePlan());
		printer.printItem(call.startTime().toString(), c2.getPhoneNumber(), Integer.toString(call.durationSeconds()), "0.0");
		printer.printTotal("0.0");

		assertTrue(containsValidHeader(c1, withNoSpacesOrNewlines(this.out.toString())));
	}

	@Test
	public void testPrintItem() {
		Printer printer = new HtmlPrinter(new PrintStream(this.out));

		Call call1 = aCall().
						from(c1).
						to(c2).
						startingNow().
						lastingFor(Minutes(5)).build();

		Call call2 = aCall().
						from(c1).
						to(c2).
						startingNow().
						lastingFor(Minutes(5)).build();

		printer.printHeading(c1.getFullName(), c1.getPhoneNumber(), c1.getPricePlan());
		printer.printItem(call1.startTime().toString(), call1.callee(), Integer.toString(call1.durationSeconds()), "0.0");
		printer.printItem(call2.startTime().toString(), call2.callee(), Integer.toString(call2.durationSeconds()), "0.0");		
		printer.printTotal("0.0");
		
		assertTrue(containsValidItem(call1, withNoSpacesOrNewlines(this.out.toString())));
		assertTrue(containsValidItem(call2, withNoSpacesOrNewlines(this.out.toString())));
	}

	@Test
	public void testPrintTotal() {
		Printer printer = new HtmlPrinter(new PrintStream(this.out));

		Call call1 = aCall().
						from(c1).
						to(c2).
						startingNow().
						lastingFor(Minutes(5)).build();

		Call call2 = aCall().
						from(c1).
						to(c2).
						startingNow().
						lastingFor(Minutes(5)).build();

		String total = "100.0";
		
		printer.printHeading(c1.getFullName(), c1.getPhoneNumber(), c1.getPricePlan());
		printer.printItem(call1.startTime().toString(), call1.callee(), Integer.toString(call1.durationSeconds()), "50.0");
		printer.printItem(call2.startTime().toString(), call2.callee(), Integer.toString(call2.durationSeconds()), "50.0");		
		printer.printTotal(total);
		
		assertTrue(containsValidTotal(total, withNoSpacesOrNewlines(this.out.toString())));
	}
}
