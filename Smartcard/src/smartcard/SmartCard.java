package smartcard;

import deliverychannel.AbstractSmartCard;

public class SmartCard implements AbstractSmartCard{
    private String cardNumber;
    private CardholderInterface owner;

    public SmartCard(CardholderInterface owner, String cardNumber) {
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
        owner.send("The following application has been delivered to your card: " + program);
    }
}
