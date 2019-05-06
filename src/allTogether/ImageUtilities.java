package allTogether;

import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageUtilities {

    //create Image from a file, then turns into a BufferedImage

    public static final String imageName = "snow.jpg";

    public static BufferedImage getBufferedImage(String imageFile,
                                                 Component c) {
        Image image = c.getToolkit().getImage(imageFile);
        waitForImage(image, c);
        BufferedImage bufferedImage =
                new BufferedImage(image.getWidth(c), image.getHeight(c),
                        BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, c);
        return(bufferedImage);
    }

    public static boolean waitForImage(Image image, Component c) {
        MediaTracker tracker = new MediaTracker(c);
        tracker.addImage(image, 0);
        try {
            tracker.waitForAll();
        } catch(InterruptedException ie) {}
        return(!tracker.isErrorAny());
    }

    public String getImageName() {
        return imageName;
    }
}
