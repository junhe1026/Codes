package application;

import smartcard.SmartCard;

public class Application {
    private String name;

    public Application(String name) {
        this.name = name;
    }

    public void buildAndDeliverApplication(SmartCard card) {
        String program = name + " for card " + card.getCardNumber();
        card.deliverApplication(program);
    }
}
