package deliverychannel;


public class OnlineDeliveryChannel {
    public void deliverApplication(final AbstractSmartCard card, final String program) {
        // we are delivering the application asynchronously
        // -- i.e. when the user logs in to load the application
        new Thread(new Runnable() {
            public void run() {
                // wait a while before delivering
                System.out.println("OnlineDeliveryChannel: waiting for the user...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }

                System.out.println("OnlineDeliveryChannel: writing program to card: " + card.getCardNumber());
                card.notifyOfNewApplication(program);
            }
        }).start();
    }
}
