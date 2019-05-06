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


public class Gui extends JPanel {

    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    boolean ifImadjust=false;
    boolean ifImfill=false;
    boolean ifImopen=false;
    boolean ifVMF=false;

    BufferedImage sourceImage = null;
    String sWidth, sHeight;
    Imadjust imAdj;
    ImageFill imgFill;
    Opening open;
    PrepareVectorMedian vecMed;
    File savedFile;

    String[] imadjustData={"0","0","0","0","0","0"};
    String vmfData;

    public Gui() throws IOException {
        //imAdj=new Imadjust(imageName,Integer.parseInt(imadjustData[0]),Integer.parseInt(imadjustData[1]),Integer.parseInt(imadjustData[2]),Integer.parseInt(imadjustData[3]),Integer.parseInt(imadjustData[4]),Integer.parseInt(imadjustData[5]));
        imgFill=new ImageFill(imageName);
        //open=new Opening(imageName);
        //vecMed = new PrepareVectorMedian(imageName);

        sourceImage = ImageUtilities.getBufferedImage(imageName, this);

        sWidth = Integer.toString(sourceImage.getWidth());
        sHeight = Integer.toString(sourceImage.getHeight());

        /*savedFile = new File("performEffect.jpg");
        try {
            ImageIO.write(imAdj.getAdjustedImage(), "jpeg", savedFile);
        } catch (Exception e) {
        }*/

        //TO DO: write file

        /*this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("Img Processing Demo");
        this.setSize(sourceImage.getWidth()*2, sourceImage.getHeight()*3);
        this.setVisible(true);
        this.repaint();
        this.repaint();*/
    }

    public void paint(Graphics g) {
        g.drawImage(sourceImage, 0, 0, this);  // original image
        if(ifImadjust==true)
        {
            imadjustData=normalizationDialog.getData();
            imAdj=new Imadjust(imageName,Integer.parseInt(imadjustData[0]),Integer.parseInt(imadjustData[1]),Integer.parseInt(imadjustData[2]),Integer.parseInt(imadjustData[3]),Integer.parseInt(imadjustData[4]),Integer.parseInt(imadjustData[5]));
            g.drawImage(imAdj.getAdjustedImage(),sourceImage.getWidth(),0, this);
            ifImadjust=false;
        }
        else if(ifImfill==true)
        {
            g.drawImage(imgFill.getFilledImage(),sourceImage.getWidth(),0, this);
            ifImfill=false;
        }
        else if(ifImopen)
        {
            open=new Opening(imageName,openDialog.getMaskSize(),openDialog.getMask());
            g.drawImage(open.getOpenImage(),sourceImage.getWidth(),0, this);
            ifImopen=false;
        }
        else if(ifVMF)
        {
            vmfData=vmfDialog.getData();
            vecMed = new PrepareVectorMedian(imageName, Integer.parseInt(vmfData));
            g.drawImage(vecMed.getMedianImage(),sourceImage.getWidth(),0, this);
            ifVMF=false;
        }

    }

    public String getImagename() {
        return imageName;
    }


}