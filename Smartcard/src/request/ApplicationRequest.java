package request;

import smartcard.SmartCard;
import application.Application;
import cardholder.Cardholder;

public class ApplicationRequest {
    private Cardholder cardholder;
    private Application application;
    private String cardNumber;

    public ApplicationRequest(Cardholder cardholder, Application application, String cardNumber) {
        this.cardholder = cardholder;
        this.application = application;
        this.cardNumber = cardNumber;
    }

    public void buildApplicationAndDeliverIt() {
        // find the appropriate card from the cardholder
        SmartCard card = cardholder.findCard(cardNumber);

        // ask the application to build a program
        application.buildAndDeliverApplication(card);
    }
}
