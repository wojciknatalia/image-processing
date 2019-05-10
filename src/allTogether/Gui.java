package allTogether;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import imadjust.Imadjust;
import imfill.ImageFill;
import imopen.Opening;
import VMF.PrepareVectorMedian;


public class Gui extends JPanel {

    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    boolean ifImadjust=false;
    boolean ifImfill=false;
    boolean ifImopen=false;
    boolean ifVMF=false;

    BufferedImage sourceImage = null;
    BufferedImage savedImage;
    String sWidth, sHeight;
    Imadjust imAdj;
    ImageFill imgFill;
    Opening open;
    PrepareVectorMedian vecMed;
    File savedFile;

    String[] imadjustData={"0","0","0","0","0","0"};
    String vmfData;

    public Gui() throws IOException {
        imgFill=new ImageFill(imageName);

        sourceImage = ImageUtilities.getBufferedImage(imageName, this);

        sWidth = Integer.toString(sourceImage.getWidth());
        sHeight = Integer.toString(sourceImage.getHeight());
    }

    void saveFile(BufferedImage img){
        savedFile = new File("processedImage.jpg");
        try {
            ImageIO.write(img, "jpeg", savedFile);
        } catch (Exception e) {
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sourceImage, 0, 0, this);  // original image
        if(ifImadjust)
        {
            imadjustData=normalizationDialog.getData();
            imAdj=new Imadjust(imageName,Integer.parseInt(imadjustData[0]),Integer.parseInt(imadjustData[1]),Integer.parseInt(imadjustData[2]),Integer.parseInt(imadjustData[3]),Integer.parseInt(imadjustData[4]),Integer.parseInt(imadjustData[5]));
            g.drawImage(imAdj.getAdjustedImage(),sourceImage.getWidth(),0, this);
            saveFile(imAdj.getAdjustedImage());
            ifImadjust=false;
        }
        else if(ifImfill)
        {
            g.drawImage(imgFill.getFilledImage(),sourceImage.getWidth(),0, this);
            saveFile(imgFill.getFilledImage());
            ifImfill=false;
        }
        else if(ifImopen)
        {
            open=new Opening(imageName,openDialog.getMaskSize(),openDialog.getMask());
            g.drawImage(open.getOpenImage(),sourceImage.getWidth(),0, this);
            saveFile(open.getOpenImage());
            ifImopen=false;
        }
        else if(ifVMF)
        {
            vmfData=vmfDialog.getData();
            vecMed = new PrepareVectorMedian(imageName, Integer.parseInt(vmfData));
            g.drawImage(vecMed.getMedianImage(),sourceImage.getWidth(),0, this);
            saveFile(vecMed.getMedianImage());
            ifVMF=false;
        }

    }

    public String getImagename() {
        return imageName;
    }


}