package VMF;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PrepareVectorMedian extends JFrame{

    private static BufferedImage medianImage; //grey image of input image
    BufferedImage sourceImage = null;
    File savedFile;
    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    public PrepareVectorMedian(String fileName) //throws Exception
    {
        sourceImage = ImageUtilities.getBufferedImage(fileName, this);

        int[][] to2D = RGBTo2D(sourceImage);
        int[] tab1D=convertTo1D(to2D, sourceImage.getWidth(), sourceImage.getHeight());
        medianImage = makeNewBufferedImage1D(vectorMedian(tab1D,sourceImage.getWidth(),sourceImage.getHeight()), sourceImage.getWidth(), sourceImage.getHeight());

        savedFile = new File("performEffect.jpg"); //save jpg to file
        try {
            ImageIO.write(medianImage, "jpeg", savedFile);
        } catch (Exception e) {
        }
    }

    public int[][] RGBTo2D(BufferedImage source){
        int twoDimensions[][] = new int[source.getWidth()][source.getHeight()];
        for(int x=0; x<source.getWidth(); x++){
            for(int y=0; y<source.getHeight(); y++){
                int c = source.getRGB(x, y);
                twoDimensions[x][y] = c;
            }
        }
        return twoDimensions;
    }

    public int[] vectorMedian(int[] input, int width, int height){
        int[] output;
        VMF vectorMed=new VMF(input,width,height);
        output=vectorMed.performEffect();
        return output;
    }

    public int[] convertTo1D(int[][] input, int width, int height){
        int[] pixels=new int[width*height];
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[i++] = input[x][y];
            }
        }
        return pixels;
    }

    public BufferedImage makeNewBufferedImage1D(int[] pixels, int width, int height){

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        image.setRGB(0, 0, width, height, pixels, 0, width);

        return image;
    }

    public static BufferedImage getMedianImage() {
        return medianImage;
    }

}