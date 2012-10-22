package company;

import java.util.ArrayList;
import java.util.List;

import deliverychannel.OnlineDeliveryChannel;

import smartcard.SmartCard;


public class CompanyCardholder {
    private String companyName;
    private OnlineDeliveryChannel deliveryChannel;
    private List<SmartCard> cards = new ArrayList<SmartCard>();

    public CompanyCardholder(String companyName, OnlineDeliveryChannel deliveryChannel) {
        this.companyName = companyName;
        this.deliveryChannel = deliveryChannel;
    }

    public void addCard(SmartCard card) {
        cards.add(card);
    }

    public void sendFax(String message) {
        System.out.println("CompanyCardHolder: Attention " + companyName + ", you have a new fax");
        System.out.println("    Message is: " + message);
    }

    public SmartCard findCard(String cardNumber) {
        for (SmartCard card : cards)
            if (card.getCardNumber().equals(cardNumber))
                return card;
        return null;
    }

    public OnlineDeliveryChannel getDeliveryChannel() {
        return deliveryChannel;
    }
}
