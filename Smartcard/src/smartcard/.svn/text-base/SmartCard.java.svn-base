package smartcard;

import cardholder.Cardholder;

public class SmartCard {
    private String cardNumber;
    private Cardholder owner;

    public SmartCard(Cardholder owner, String cardNumber) {
        this.owner = owner;
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void deliverApplication(String program) {
        owner.getDeliveryChannel().deliverApplication(this, program);
    }

    public void notifyOfNewApplication(String program) {
        // notify the owner
        owner.sendEmail("The following application has been delivered to your card: " + program);
    }
}
