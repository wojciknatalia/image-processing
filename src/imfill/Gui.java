package imfill;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gui extends JFrame {

    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    BufferedImage sourceImage = null;
    String sWidth, sHeight;
    imfill imgFill;
    File savedFile;

    public Gui() throws IOException {
        imgFill=new imfill(imageName);
        sourceImage = ImageUtilities.getBufferedImage(imageName, this);

        sWidth = Integer.toString(sourceImage.getWidth());
        sHeight = Integer.toString(sourceImage.getHeight());

        savedFile = new File("performEffect.jpg");
        try {
            ImageIO.write(imfill.getFilledImage(), "jpeg", savedFile);
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
        g.drawImage(imfill.getFilledImage(),sourceImage.getWidth(),0, this);
    }

    public String getImagename() {
        return imageName;
    }

    public static void main(String[] args) throws IOException{
        Gui gui=new Gui();

    }

}