package deliverychannel;

/**
 * Created with IntelliJ IDEA.
 * User: hejun
 * Date: 12-10-24
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public interface AbstractSmartCard {
    public String getCardNumber();
    public void notifyOfNewApplication(String program);

}
