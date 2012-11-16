package com.chatley.examples.bidders;

import com.chatley.examples.auction.BidReceiver;
import com.chatley.examples.auction.Participant;

public class IncrementingBidder implements Participant {
	private  String name;
	private  int incr;
	private  int max;
	private  int start;
	private int leadingBid = 0;

    public IncrementingBidder(){

    }

	public IncrementingBidder(String name, int start, int incr, int max) {
		this.name = name;
		this.start = start;
		this.incr = incr;
		this.max = max;
	}

    public IncrementingBidder named(String name){
        this.name = name;
        return this;
    }

    public IncrementingBidder startBidFrom(int start){
        this.start = start;
        return this;
    }

    public IncrementingBidder eachRoundIncreasedBy(int incr){
        this.incr = incr;
        return this;
    }

    public IncrementingBidder upToALimitOf(int max){
        this.max = max;
        return this;
    }

    public IncrementingBidder bidsAFixPriceOf(int price) {
        this.start = price;
        this.incr = 0;
        this.max = price;
        return this;
    }

    //public IncrementingBidder
	
	public void onBidAccepted(int price) {
		leadingBid = price;
	}

	public void onYourTurnToBid(BidReceiver receiver) {
		if (wantToBid()) {
			receiver.bid(nextBid());
		}
	}

	private boolean wantToBid() {
		return (nextBid() <= max);
	}

	private int nextBid() {
		if (leadingBid == 0 || incr == 0) {
			return start;
		} else {
			return leadingBid + incr;
		}
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "IncrementingBidder: " + name + ", " + start + ", " + incr + ", " + max;
	}


}