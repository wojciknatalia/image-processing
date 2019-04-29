package vmf;

import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.*;

import javax.swing.*;

public class Normalization extends JFrame{
    private static final long serialVersionUID = -4834892318628515297L;

    private static BufferedImage normalizedImage; //grey image of input image
    BufferedImage sourceImage = null;
    File sourceFile, savedFile;
    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    public Normalization() //throws Exception
    {
        sourceImage = ImageUtilities.getBufferedImage(imageName, this);

        //getRGB();
        int[][] greyScale = RGBToGrey(sourceImage);
        int[] tab1D=convertTo1D(greyScale, sourceImage.getWidth(), sourceImage.getHeight());
        //greyScale = vectorMedian(greyScale,sourceImage.getWidth(),sourceImage.getHeight());
        //normalizedImage = makeNewBufferedImage(greyScale, sourceImage.getWidth(), sourceImage.getHeight());
        //vectorMedian(tab1D,sourceImage.getWidth(),sourceImage.getHeight());
        normalizedImage = makeNewBufferedImage1D(vectorMedian(tab1D,sourceImage.getWidth(),sourceImage.getHeight()), sourceImage.getWidth(), sourceImage.getHeight());

        savedFile = new File("test.jpg");
        try {
            ImageIO.write(normalizedImage, "jpeg", savedFile);
        } catch (Exception e) {
        }
    }

    public int[][] RGBToGrey(BufferedImage source){
        int greyScale[][] = new int[source.getWidth()][source.getHeight()];
        for(int x=0; x<source.getWidth(); x++){
            for(int y=0; y<source.getHeight(); y++){
                int c = source.getRGB(x, y);
                /*float r = (c&0x00ff0000)>>16;
                float g = (c&0x0000ff00)>>8;
                float b = c&0x000000ff;*/
                //greyScale[x][y] = (int)(0.3*r + 0.59*g + 0.11*b);
                greyScale[x][y] = c;
            }
        }
        return greyScale;
    }

    public int findMinimum(int[][] input, int width, int height){
        int min = input[0][0];
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                int n = input[x][y];
                if(n<min){
                    min = n;
                }
            }
        }
        return min;
    }
    public int findMaximum(int[][] input, int width, int height){
        int max = input[0][0];
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                int n = input[x][y];
                if(n>max){
                    max = n;
                }
            }
        }
        return max;
    }

    public int[] vectorMedian(int[] input, int width, int height){
        int[] output = new int[width*height];
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

    public BufferedImage makeNewBufferedImage(int[][] gs, int width, int height){

        BufferedImage image = new BufferedImage(width, height, 3);

        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                //int rgb = (int)gs[x][y]<<16 | (int)gs[x][y] << 8 | (int)gs[x][y];
                image.setRGB(x, y, gs[x][y]);
            }
        }
        //image.setData(gs);
        return image;
    }

    public BufferedImage makeNewBufferedImage1D(int[] pixels, int width, int height){

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        image.setRGB(0, 0, width, height, pixels, 0, width);

        return image;
    }

    public static BufferedImage getNormalizedImage() {
        return normalizedImage;
    }

}