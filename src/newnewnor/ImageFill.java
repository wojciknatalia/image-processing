package newnewnor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFill {

    private static BufferedImage myImage;
    private static BufferedImage imfillImage;

    public int getWidth(){
        return myImage.getWidth();
    }

    public int getHeight(){
        return myImage.getHeight();
    }

    public ImageFill(String imFill, int i1, int o1, int i2, int o2, int i3, int o3) throws IOException{
        int[][] imageColor ;
        try {
            myImage = ImageIO.read(new File(imFill));
            imfillImage = new BufferedImage(myImage.getWidth(),
                    myImage.getHeight(),
                    myImage.getType());
            imageColor = new int[myImage.getWidth()][myImage.getHeight()];

            for (int i = 0; i < myImage.getWidth(); i++) {
                for (int j = 0; j < myImage.getHeight(); j++) {
                    imageColor[i][j] = myImage.getRGB(i , j);
                }
            } //read image as array

            Color myBlack = new Color(0, 0, 0); // Color white
            int rgbBlack = myBlack.getRGB();

            Color myWhite = new Color(255, 255, 255); // Color white
            int rgbWhite = myWhite.getRGB();

            int slope;
            int intercept;
            int pixel;

            for (int i = 0; i < myImage.getWidth(); i++) {
                for (int j = 0; j < myImage.getHeight(); j++) {
                    if(imageColor[i][j]<imageColor[i1][o1])
                        imfillImage.setRGB(i, j, rgbBlack);
                    else if(imageColor[i][j]>imageColor[i1][o1] & imageColor[i][j]<imageColor[i2][o2])
                    {
                        slope=(o2-o1)/(i2-i1); //(maxvalout-minvalout)/(maxvalin-minvalin)
                        intercept=(o2)-(slope*i2); //(maxvalout) - (slope*maxvalin);
                        pixel=slope*imageColor[i][j]+intercept;
                        imfillImage.setRGB(i, j, rgbWhite);
                    }
                    else if(imageColor[i][j]>imageColor[i2][o2] & imageColor[i][j]<imageColor[i3][o3])
                    {
                        slope=(o3-o2)/(i3-i2); //(maxvalout-minvalout)/(maxvalin-minvalin)
                        intercept=(o3)-(slope*i3); //(maxvalout) - (slope*maxvalin);
                        pixel=slope*imageColor[i][j]+intercept;
                        imfillImage.setRGB(i, j, rgbBlack);
                        System.out.println("elsee if2");
                    }
                    else if(imageColor[i][j]>imageColor[i3][o3])
                        imfillImage.setRGB(i, j, rgbBlack);
                }
            }

            //imageColor=imadjust(imageColor, 50, 255, 255,30, myImage.getWidth(), myImage.getHeight());
            /*imageColor=imadjust(imageColor, i1, i2, o1, o2);
            imageColor=imadjust(imageColor, i2, 255, o2, 255);*/

            imfillImage=makeNewBufferedImage1D(convertTo1D(imageColor,myImage.getWidth(), myImage.getHeight()),myImage.getWidth(), myImage.getHeight());


            }
        catch (IOException e) {
            e.printStackTrace();
        }

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


    public int[][] imadjust(int[][] img, double minvalin, double maxvalin, double minvalout, double maxvalout, int h, int w) {

        if (minvalin < 0 || minvalin > 255 || maxvalin < 0 || maxvalout > 255 || minvalout < 0 || minvalout > 255 || maxvalout < 0 || maxvalin > 255) {
            JOptionPane.showMessageDialog(null,"ERR: imadjust INPUTS MUST BE BETWEEN 0 and 1");
            System.exit(0);
        }

        int height = h;
        int width = w;

        double slope = (maxvalout-minvalout)/(maxvalin-minvalin);
        double intercept = (maxvalout) - (slope*maxvalin);

        int pixel;
        double newpix;
        int[][] scldimg = new int[height][width];

        for (int ii = 0; ii < height; ii++) {
            for (int jj = 0; jj < width; jj++) {
                pixel = img[ii][jj];

                if (pixel < minvalin) {
                    scldimg[ii][jj] = (int) Math.round((minvalout));
                } else if (pixel > (maxvalin)) {
                    scldimg[ii][jj] = (int) Math.round((maxvalout));
                } else {
                    newpix = slope*(pixel) + intercept;
                    scldimg[ii][jj] = (int) Math.round(newpix);
                }
            }
        }

        return scldimg;
    }

    public static BufferedImage getFilledImage() {
        return imfillImage;
    }

}