package com.chatley.examples.auction;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.junit.Assert.*;

public class AuctionTestContext {

	Map<Participant, String> bidders = new LinkedHashMap<Participant, String>();
	private TestLedger result = new TestLedger(); 
	
	public AuctionTestContext withBidder(Participant bidder) {
		bidders.put(bidder, bidder.getName());
		return this;
	}

	public AuctionTestContext whenAuctionRuns() {
		
	Auction auction = new EnglishAuction();
		
		for (Participant participant : bidders.keySet()) {
			auction.accept(participant);
		}
		
		auction.run();
		
		auction.close(result);
		
		return this;
	}
	
	private class TestLedger implements Ledger {
		private String winner;
		private int winningPrice;
		private boolean noWinner;
		
		public void noWinner() {
			this.noWinner = true;
		}

		public void win(Participant bidder, int price) {
			this.winner = bidders.get(bidder);
			this.winningPrice = price;
		}

		public boolean wasNoWinner() {
			return noWinner;
		}
		
		public String nameOfWinner() {
			return winner;
		}
		
		public String toString() {
			if (noWinner) {
				return "No Winner";
			} else {
				return "Winner: " + nameOfWinner() + " won at price " + winningPrice;
			}
		}
		
	}

	public void expectWinFor(String name, int i) {
	    assertThat(result, wasWonBy(name, i));
		assertEquals(name, result.nameOfWinner());
		assertEquals(i, result.winningPrice);
	}

	private Matcher<TestLedger> wasWonBy(final String name, final int i) {
		return new TypeSafeMatcher<TestLedger>() {

			@Override
			public boolean matchesSafely(TestLedger item) {
				return !item.wasNoWinner() && 
					   item.nameOfWinner().equals(name)
					&& item.winningPrice == i;
			}

			public void describeTo(Description description) {
				description.appendText("bidder: ").appendValue(name)
				.appendText(" won at price: ").appendValue(i);
			}
			
		};
	}

	public void expectNoWinner() {
		assertTrue(result.wasNoWinner());
	}

}
