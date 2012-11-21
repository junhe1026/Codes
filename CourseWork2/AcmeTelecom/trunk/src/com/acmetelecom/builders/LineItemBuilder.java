package com.acmetelecom.builders;

import java.math.BigDecimal;

import com.acmetelecom.Call;
import com.acmetelecom.LineItem;

/*
 * This is a builder for the LineItem class. It is designed to provide
 * a fluent interface that facilitates creating particular instances of 
 * LineItem. These instances will be used mostly for unit and end-to-end tests.
 */
public class LineItemBuilder implements Builder {
	private Call call;
	private BigDecimal callCost;
	
	public static LineItemBuilder aLineItem() {
		return new LineItemBuilder();
	}
	
	private LineItemBuilder() {
		this.call = null;
		this.callCost = null;		
	}
	
	public LineItemBuilder forCall(Call call) {
		this.call = call;
		return this;
	}

	public LineItemBuilder withACostOf(BigDecimal callCost) {
		this.callCost = callCost;
		return this;
	}
	
	@Override
	public LineItem build() {
		// TODO Auto-generated method stub
		return new LineItem(this.call, this.callCost);
	}
}
