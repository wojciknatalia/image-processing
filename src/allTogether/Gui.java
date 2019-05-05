package allTogether;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import imadjust.Imadjust;
import imfill.ImageFill;
import imopen.Dilate;
import imopen.Erode;
import imopen.MorphOperation;
import imopen.Opening;
import VMF.VMF;
import VMF.PrepareVectorMedian;


public class Gui extends JFrame {

    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    BufferedImage sourceImage = null;
    String sWidth, sHeight;
    Imadjust imAdj;
    ImageFill imgFill;
    Opening open;
    PrepareVectorMedian vecMed;
    File savedFile;

    public Gui() throws IOException {
        imAdj=new Imadjust(imageName);
        imgFill=new ImageFill(imageName);
        open=new Opening(imageName);
        vecMed = new PrepareVectorMedian(imageName);

        sourceImage = ImageUtilities.getBufferedImage(imageName, this);

        sWidth = Integer.toString(sourceImage.getWidth());
        sHeight = Integer.toString(sourceImage.getHeight());

        savedFile = new File("performEffect.jpg");
        try {
            ImageIO.write(imAdj.getAdjustedImage(), "jpeg", savedFile);
        } catch (Exception e) {
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("Img Processing Demo");
        this.setSize(sourceImage.getWidth()*2, sourceImage.getHeight()*3);
        this.setVisible(true);
        this.repaint();
        this.repaint();
    }

    public void paint(Graphics g) {
        g.drawImage(sourceImage, 0, 0, this);  // original image
        g.drawImage(imAdj.getAdjustedImage(),sourceImage.getWidth(),0, this);
        g.drawImage(imgFill.getFilledImage(),0,sourceImage.getHeight(), this);
        g.drawImage(open.getOpenImage(),sourceImage.getWidth(),sourceImage.getHeight(), this);
        g.drawImage(vecMed.getMedianImage(),0,sourceImage.getHeight()*2, this);
    }

    public String getImagename() {
        return imageName;
    }

    public static void main(String[] args) throws IOException{
        Gui gui=new Gui();

    }

}