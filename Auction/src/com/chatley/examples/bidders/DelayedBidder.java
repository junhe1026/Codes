package com.chatley.examples.bidders;

import com.chatley.examples.auction.BidReceiver;
import com.chatley.examples.auction.Participant;

public class DelayedBidder implements Participant {
	private String name;
	private int numberOfBidsToWaitFor;
	private int bid;
	private int numberOfBidsSoFar;
	private int leadingBid;

    public DelayedBidder(){

    }

	public DelayedBidder(String name, int numberOfBidsToWaitFor, int bid) {
		this.name = name;
		this.numberOfBidsToWaitFor = numberOfBidsToWaitFor;
		this.bid = bid;
	}

    public DelayedBidder named(String name){
        this.name = name;
        return this;
    }

    public DelayedBidder willBid(int bid){
        this.bid = bid;
        return this;
    }

    public DelayedBidder afterWaitForBids(int numberOfBidsToWaitFor){
        this.numberOfBidsToWaitFor = numberOfBidsToWaitFor;
        return this;
    }



	public String getName() {
		return name;
	}

	public void onBidAccepted(int price) {
		leadingBid = price;
		numberOfBidsSoFar++;
	}

	public void onYourTurnToBid(BidReceiver receiver) {
		if (numberOfBidsSoFar >= numberOfBidsToWaitFor && bid > leadingBid) {
			receiver.bid(bid);
		}
	}

	public String toString() {
		return "DelayedBidder: " + name + ", " + numberOfBidsSoFar + ", " + bid;
	}

}
