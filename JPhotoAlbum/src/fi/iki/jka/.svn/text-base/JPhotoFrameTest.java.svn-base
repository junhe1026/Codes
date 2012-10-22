package fi.iki.jka;


import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: jh1212
 * Date: 08/10/12
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */
public class JPhotoFrameTest implements SlideShowInterface {

    @Test
    public void testSlideShow() throws Exception {
        JPhotoCollection jPhotoCollection = new JPhotoCollection();
        JPhotoFrame jPhotoFrame = new JPhotoFrame(null, jPhotoCollection);
        assertEquals(0,jPhotoFrame.photos.size());


    }

    @Override
    public void slideShow(JPhotoFrame jPhotoFrame, JPhotoCollection photos, int interval, JList list) {
        if (photos.getSize()>0) {
            JPhotoShow show = new JPhotoShow(photos, 5000, list);
            show.setVisible(true);
            System.out.println(">>>>>>>>>>>>Printing from test mode!! <<<<<<<<<<<");
        }
        else
            JOptionPane.showMessageDialog(jPhotoFrame, "No photos to show!",
                    "APP_NAME", JOptionPane.ERROR_MESSAGE);
            System.out.println(">>>>>>>>>>>>Printing from test mode!! <<<<<<<<<<<");
    }

    @Test
    public void testDummy(){
        int result = 1+2;
        assertEquals(3,result);
    }
}
