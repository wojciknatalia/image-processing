package vmf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

public class Gui extends JFrame {

    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    BufferedImage sourceImage = null;
    String sWidth, sHeight;
    Normalization norm;
    HistogramEqualizerDemo hist;
    Opening open;
    File sourceFile, savedFile, savedFile2;

    public Gui(){
        norm= new Normalization();
        hist= new HistogramEqualizerDemo();
//        open=new Opening();
        sourceImage = ImageUtilities.getBufferedImage(imageName, this);

        sWidth = Integer.toString(sourceImage.getWidth());
        sHeight = Integer.toString(sourceImage.getHeight());

        savedFile = new File("test.jpg");
        try {
            ImageIO.write(norm.getNormalizedImage(), "jpeg", savedFile);
        } catch (Exception e) {
        }

        /*savedFile2 = new File("test2.jpg");
        try {
            ImageIO.write(hist.getHeColorImage(), "jpeg", savedFile2);
        } catch (Exception e) {
        }*/

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("Img Processing Demo");
        this.setSize(sourceImage.getWidth()*2, sourceImage.getHeight());
        this.setVisible(true);
        this.repaint();
        this.repaint();
    }

    public void paint(Graphics g) {
        //g.fillRect(0, 0, sourceImage.getWidth(), sourceImage.getHeight());
        // Draw the image on the Graphics context.
        // This is non-blocking.
        g.drawImage(sourceImage, 0, 0, this);  // original image
        //g.drawImage(hist.getHeColorImage(), 70 + sourceImage.getWidth(), 30, this); // histogram image
        g.drawImage(norm.getNormalizedImage(),sourceImage.getWidth(),0, this); //normalized image
        //g.drawImage(open.getNormalizedImage(),sourceImage.getWidth(),0, this);
    }

    /*public BufferedImage makeNewBufferedImage(int[][] gs, int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[] iArray = {0,0,0,255};
        WritableRaster r = image.getRaster();
        for(int x=0; x<width; x++){

            for(int y=0; y<height; y++){
                int v = gs[x][y];
                iArray[0] = v;
                iArray[1] = v;
                iArray[2] = v;
                r.setPixel(x, y, iArray);
            }
        }
        image.setData(r);
        return image;
    }*/

    public String getImagename() {
        return imageName;
    }

    public static void main(String[] args) {
        Gui gui=new Gui();

    }

}