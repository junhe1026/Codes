package smartcard;

import deliverychannel.OnlineDeliveryChannel;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-10-24
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public interface CardholderInterface {
    public void send(String message);
    public OnlineDeliveryChannel getDeliveryChannel();
    public SmartCard findCard(String cardNumber);
}
