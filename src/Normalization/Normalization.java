package Normalization;

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
        greyScale = normalizeMagic(greyScale,sourceImage.getWidth(),sourceImage.getHeight());
        normalizedImage = makeNewBufferedImage(greyScale, sourceImage.getWidth(), sourceImage.getHeight());

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
                float b = c&0x000000ff;
                float sum=255; //
                greyScale[x][y] = (int)(0.3*r + 0.59*g + 0.11*b);*/

                int red = c >> 16 & 0xFF;
                int green = c >> 8 & 0xFF;
                int blue = c & 0xFF;

                if(red>255)
                    red=255;
                else if (red<0)
                    red=0;

                if(green>255)
                    green=255;
                else if(green<0)
                    green=0;

                if(blue>255)
                    blue=255;
                else if (blue<0)
                    blue=blue;

                greyScale[x][y] = c;//(int) (0.299*((double)red) + 0.587*((double)green) +0.114*((double)blue) );
            }
        }
        return greyScale;
    }

    public static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(row, col);
            }
        }

        return result;
        /*final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;*/
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
    public int findMaximum(int[][] input, int width, int height) {
        int max = input[0][0];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int n = input[x][y];
                if (n > max) {
                    max = n;
                }
            }
        }
        return max;
    }

    public int[][] normalizeMagic(int[][] input, int width, int height){
        int[][] output = new int[width][height];
        int a=0, b=255;
        int c = findMaximum(input,width,height)-findMinimum(input,width,height);
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                a = (input[x][y])-findMinimum(input,width,height);
                int e =b/c;
                output[x][y] = e*a;
            }
        }
        return output;
    }

    /*
     *now, draw a new image with the magically normalized GreyScale!
     */
/*
   public BufferedImage makeNewBufferedImage(float[]newGrayScale) {
       int[] newBufferedImageData = new int[rows * cols];
       int index;
       for (int row = 0; row < rows; row++) {
           for (int col = 0; col < cols; col++) {
               index = (row * cols) + col;
               
           }
       }
       BufferedImage newImage = new BufferedImage(cols, rows, BufferedImage.TYPE_BYTE_GRAY);
       for (int row = 0; row < rows; row++) {
           for (int col = 0; col < cols; col++) {
               index = (row * cols) + col;
               newImage.setRGB(col, row, newBufferedImageData[index]);
           }
       }
       return newImage;
   }*/
    public BufferedImage makeNewBufferedImage(int[][] gs, int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //TYPE_BYTE_GRAY
        int[] iArray = {0,0,0,255};
        WritableRaster r = image.getRaster();
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                int v = gs[x][y];
                iArray[0] = v >> 16;
                iArray[1] = v >> 8;
                iArray[2] = v;
                r.setPixel(x, y, iArray);
            }
        }
        image.setData(r);
        return image;
    }

    public static BufferedImage getNormalizedImage() {
        return normalizedImage;
    }

}