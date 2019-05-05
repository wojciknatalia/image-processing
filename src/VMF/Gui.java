package VMF;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Gui extends JFrame {

    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    BufferedImage sourceImage = null;
    String sWidth, sHeight;
    PrepareVectorMedian vecMed;
    File sourceFile, savedFile, savedFile2;

    public Gui(){
        vecMed = new PrepareVectorMedian();
        sourceImage = ImageUtilities.getBufferedImage(imageName, this);

        sWidth = Integer.toString(sourceImage.getWidth());
        sHeight = Integer.toString(sourceImage.getHeight());

        savedFile = new File("performEffect.jpg");
        try {
            ImageIO.write(vecMed.getMedianImage(), "jpeg", savedFile);
        } catch (Exception e) {
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("Img Processing Demo");
        this.setSize(sourceImage.getWidth()*2, sourceImage.getHeight());
        this.setVisible(true);
        this.repaint();
        this.repaint();
    }

    public void paint(Graphics g) {
        g.drawImage(sourceImage, 0, 0, this);  // original image
        g.drawImage(vecMed.getMedianImage(),sourceImage.getWidth(),0, this); //vector median img
    }

    public String getImagename() {
        return imageName;
    }

    public static void main(String[] args) {
        Gui gui=new Gui();

    }

}