package com.chatley.examples.bidders;

import com.chatley.examples.auction.BidReceiver;
import com.chatley.examples.auction.Participant;

public class LimitBidder implements Participant {
	private String name;
	private int limit;
	private int latestBid;

    public  LimitBidder(){

    }

	public LimitBidder(String name, int limit) {
		this.name = name;
		this.limit = limit;
	}

    public LimitBidder named(String name){
        this.name = name;
        return this;
    }

    public LimitBidder upToALimitOf(int limit){
        this.limit = limit;
        return this;
    }

	public String getName() {
		return name;
	}

	public void onBidAccepted(int price) {
		this.latestBid = price;
	}

	public void onYourTurnToBid(BidReceiver receiver) {
		if (latestBid < limit) {
			int increment = Math.max((limit - latestBid)/ 2, 1);
			receiver.bid(latestBid + increment);
		}
	}

	public String toString() {
		return "LimitBidder: " + name + ", " + limit;
	}

}
