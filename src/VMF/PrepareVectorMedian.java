package VMF;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PrepareVectorMedian extends JFrame{

    private static BufferedImage medianImage; //grey image of input image
    BufferedImage sourceImage = null;

    public PrepareVectorMedian(String fileName, int maskSize) //throws Exception
    {
        try {
            sourceImage = ImageIO.read(new File(fileName));
            new BufferedImage(sourceImage.getWidth(),
                    sourceImage.getHeight(),
                    sourceImage.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] to2D = RGBTo2D(sourceImage);
        int[] tab1D=convertTo1D(to2D, sourceImage.getWidth(), sourceImage.getHeight());
        medianImage = makeNewBufferedImage1D(vectorMedian(tab1D,sourceImage.getWidth(),sourceImage.getHeight(), maskSize), sourceImage.getWidth(), sourceImage.getHeight());
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

    public int[] vectorMedian(int[] input, int width, int height, int masksize){
        int[] output;
        VMF vectorMed=new VMF(input,width,height, masksize);
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