package test;

import application.Application;
import company.CompanyCardholder;
import deliverychannel.OnlineDeliveryChannel;
import request.ApplicationRequest;
import smartcard.SmartCard;
import cardholder.Cardholder;

public class TestScenario {
    public static void main(String args[]) {
        // make the online delivery channel
        OnlineDeliveryChannel deliveryChannel = new OnlineDeliveryChannel();

        // make a cardholder and a card
        Cardholder sarah = new Cardholder("Sarah Thomas", deliveryChannel);
        SmartCard card = new SmartCard(sarah, "4332312354");
        sarah.addCard(card);

        // create an application
        Application loyaltyApp = new Application("Loyalty Application");

        // build the application and deliver it
        ApplicationRequest request = new ApplicationRequest(sarah, loyaltyApp, card.getCardNumber());
        request.buildApplicationAndDeliverIt();

		// for the last part of the exercise, we would also like to run
		// make a cardholder and a card
		CompanyCardholder consolidated = new CompanyCardholder("Consolidated Holdings plc", deliveryChannel);
		SmartCard companyCard = new SmartCard(consolidated, "4666312354");
		consolidated.addCard(companyCard);

		// build the application and deliver it
		ApplicationRequest companyRequest = new ApplicationRequest(consolidated, loyaltyApp, companyCard.getCardNumber());
		companyRequest.buildApplicationAndDeliverIt();
    }

}
