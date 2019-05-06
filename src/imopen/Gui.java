package imopen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Gui extends JFrame {

    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    BufferedImage sourceImage = null;
    String sWidth, sHeight;
    Opening open;
    File savedFile;

    int mask0size3[]={
            0,0,0,
            1,1,1,
            0,0,0,
    };

    public Gui(){
        open=new Opening(imageName, 5, mask0size3);
        sourceImage = ImageUtilities.getBufferedImage(imageName, this);
        sWidth = Integer.toString(sourceImage.getWidth());
        sHeight = Integer.toString(sourceImage.getHeight());

        savedFile = new File("test2.jpg");
        try {
            ImageIO.write(open.getOpenImage(), "jpeg", savedFile);
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
        g.drawImage(open.getOpenImage(),sourceImage.getWidth(),0, this);
    }


    public String getImagename() {
        return imageName;
    }

    public static void main(String[] args) {
        Gui gui=new Gui();

    }

}