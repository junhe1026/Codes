package com.chatley.examples.auction;

import org.junit.Test;

import com.chatley.examples.bidders.DelayedBidder;
import com.chatley.examples.bidders.IncrementingBidder;
import com.chatley.examples.bidders.LimitBidder;
import com.chatley.examples.bidders.PrimeBidder;

public class BidderScenariosTest {

	AuctionTestContext auctionContext = new AuctionTestContext();
	
	/*
	 * fred bids 10 -> winner
	 */
	@Test
	public void soleFixedBidderWins() {
		auctionContext
          //.withBidder(bidder("fred",10,0,100);
			.withBidder(bidder().named("fred").bidsAFixPriceOf(10))
			.whenAuctionRuns()
			.expectWinFor("fred", 10);
	}
    
	/*
	 * george bids 25 -> winner
	 */
	@Test
	public void soleReactionaryBidderWins() {
		auctionContext
          //.withBidder(bidder("george", 10, 25, 10000))
            .withBidder(bidder().named("george").startBidFrom(10).eachRoundIncreasedBy(25).upToALimitOf(10000))
			.whenAuctionRuns()
			.expectWinFor("george", 10);
	}

	/*
	 * fred bids 10
	 * george bids 20 -> winner
	 */
	@Test
	public void highBidBeatsLowBid() throws Exception {
		auctionContext
			//.withBidder(bidder("fred", 10, 0, 10000))
			//.withBidder(bidder("george", 20, 0, 10000))
            .withBidder(bidder().named("fred").bidsAFixPriceOf(10))
            .withBidder(bidder().named("george").bidsAFixPriceOf(20))
			.whenAuctionRuns()
			.expectWinFor("george", 20);
	}
	
	/*
	 * fred bids    10
	 * george bids  25
	 * fred bids    25+ 10 = 35 -> winner
	 */
	@Test
	public void reactionaryBidderBeatsFixedBidder() throws Exception {
		auctionContext
			//.withBidder(bidder("fred", 10, 10, 10000))
			//.withBidder(bidder("george", 25, 0, 10000))
            .withBidder(bidder().named("fred").startBidFrom(10).eachRoundIncreasedBy(10).upToALimitOf(10000))
            .withBidder(bidder().named("geogre").bidsAFixPriceOf(25))
			.whenAuctionRuns()
			.expectWinFor("fred", 35);
	}
	
	/*
	 * fred bids   10
	 * george bids 10 + 25 = 35
	 * fred bids   35 + 10 = 45
	 * george bids 45 + 25 = 70
	 * fred bids   70 + 10 = 80 -> winner
	 */
	@Test
	public void biddersKeepBiddingUntilTheirFinancialConstraintsStopThem() throws Exception {
		auctionContext
			//.withBidder(bidder("fred", 10, 10, 100))
			//.withBidder(bidder("george", 30, 25, 100))
            .withBidder(bidder().named("fred").startBidFrom(10).eachRoundIncreasedBy(10).upToALimitOf(100))
            .withBidder(bidder().named("geogry").startBidFrom(30).eachRoundIncreasedBy(25).upToALimitOf(100))
			.whenAuctionRuns()
			.expectWinFor("fred", 80);
	}
	
	/*
	 * george bids 30
	 * fred bids   30 + 10 = 40
	 * george bids 40 + 25 = 65
	 * fred bids   65 + 10 = 75
	 * george bids 75 + 25 = 100 -> winner
	 */
	@Test
	public void orderOfEntryAffectsTheResult() {
		auctionContext
			//.withBidder(bidder("george", 30, 25, 100))
			//.withBidder(bidder("fred", 10, 10, 100))
            .withBidder(bidder().named("george").startBidFrom(30).eachRoundIncreasedBy(25).upToALimitOf(100))
            .withBidder(bidder().named("fred").startBidFrom(10).eachRoundIncreasedBy(10).upToALimitOf(100))
			.whenAuctionRuns()
			.expectWinFor("george", 100);
	}
	
	/*
	 * prime     bids 5 -> winner
	 * slowcoach waits
	 */
	@Test
	public void aDelayedBidderMightMissOutWhenOnlyOneOtherBidder() {
		auctionContext
			//.withBidder(primeBidder("prime", 5, 20))
			//.withBidder(delayedBidder("slowcoach", 2, 100))
            .withBidder(primeBidder().named("prime").startBidFrom(5).upToALimitOf(20))
            .withBidder(delayedBidder().named("slowcoach").afterWaitForBids(2).willBid(100))
			.whenAuctionRuns()
			.expectWinFor("prime", 5);
	}
	
	/*
	 * prime1    bids 5
	 * prime2    bids 7
	 * slowcoach waits
	 * prime1    bids 11
	 * prime2    bids 13
	 * slowcoach bids 100 -> winner
	 */
	@Test
	public void aDelayedBidderJoinsInAndGazumpsOtherSmallFryWhenThereHaveBeenEnoughBids() {
		auctionContext
			//.withBidder(primeBidder("prime1", 5, 13))
			//.withBidder(primeBidder("prime2", 5, 13))
			//.withBidder(delayedBidder("slowcoach", 4, 100))
            .withBidder(primeBidder().named("prime1").startBidFrom(5).upToALimitOf(13))
            .withBidder(primeBidder().named("prime2").startBidFrom(5).upToALimitOf(13))
            .withBidder(delayedBidder().named("slowcoach").afterWaitForBids(4).willBid(100))
            .whenAuctionRuns()
			.expectWinFor("slowcoach", 100);
	}
	
	/*
	 * prime1   bids 180
	 * prime2   bids 181
	 * prime1   bids 191
	 * prime2   bids 193
	 * prime1   bids 197 -> winner
	 */
	@Test
	public void aPairOfPrimeBiddersBidEachOtherUpTheSequenceOfPrimesUntilOneGoesBust() {
		auctionContext
			//.withBidder(primeBidder("prime1", 180, 200))
			//.withBidder(primeBidder("prime2", 180, 196))
            .withBidder(primeBidder().named("prime1").startBidFrom(180).upToALimitOf(200))
            .withBidder(primeBidder().named("prime2").startBidFrom(180).upToALimitOf(196))
			.whenAuctionRuns()
			.expectWinFor("prime1", 197);
	}
	
	/*
	 * adam bids 10
	 * ben bids 55
	 * adam bids 85
	 * ben bids 92 -> winner
	 */
	@Test
	public void aLimitBidderOutbidsAnIncrementalBidderByBeingMoreFlexibleWithTheBidIncrement() {
		auctionContext
			//.withBidder(bidder("adam", 10, 30, 100))
			//.withBidder(limitBidder("ben", 100))
            .withBidder(bidder().named("adam").startBidFrom(10).eachRoundIncreasedBy(30).upToALimitOf(100))
            .withBidder(limitBidder().named("ben").upToALimitOf(100))
			.whenAuctionRuns()
			.expectWinFor("ben", 92);
	}
	
	/*
	 * charles bids 50
	 * david bids 100 -> winner
	 */
	@Test
	public void whenTwoLimitBiddersFightThenTheOneWithTheHigherLimitWins() {
		auctionContext
			//.withBidder(limitBidder("charles", 100))
			//.withBidder(limitBidder("david", 150))
            .withBidder(limitBidder().named("charles").upToALimitOf(100))
            .withBidder(limitBidder().named("david").upToALimitOf(150))
			.whenAuctionRuns()
			.expectWinFor("david", 100);
	}

	private IncrementingBidder bidder() {
    	return new IncrementingBidder();
    }
	
	private LimitBidder limitBidder() {
		return new LimitBidder();
	}

	private PrimeBidder primeBidder() {
		return new PrimeBidder();
	}

	private DelayedBidder delayedBidder() {
		return new DelayedBidder();
	}
}


