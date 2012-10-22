package cardholder;

import java.util.ArrayList;
import java.util.List;

import deliverychannel.OnlineDeliveryChannel;

import smartcard.SmartCard;


public class Cardholder {
    private String name;
    private OnlineDeliveryChannel deliveryChannel;
    private List<SmartCard> cards = new ArrayList<SmartCard>();

    public Cardholder(String name, OnlineDeliveryChannel deliveryChannel) {
        this.name = name;
        this.deliveryChannel = deliveryChannel;
    }

    public void addCard(SmartCard card) {
        cards.add(card);
    }

    public void sendEmail(String message) {
        System.out.println("Cardholder: Hi " + name + ", you have new e-mail");
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
