package fi.iki.jka;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: jh1212
 * Date: 08/10/12
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public class SlideShow implements SlideShowInterface {
    public static String APP_NAME = "JPhotoAlbum";

    @Override
    public void slideShow(JPhotoFrame jPhotoFrame,JPhotoCollection photos, int interval, JList list) {
        if (photos.getSize()>0) {
            JPhotoShow show = new JPhotoShow(photos, interval, list);
            show.setVisible(true);
        }
        else
            /*JOptionPane.showMessageDialog(jPhotoFrame, "No photos to show!",
                    APP_NAME, JOptionPane.ERROR_MESSAGE);  */
            System.out.println("No photos to show!");
    }
}
