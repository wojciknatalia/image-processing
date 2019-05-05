package imfill;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class imfill {

    BufferedImage grayImage;
    BufferedImage imfillImage;

    public int getWidth(){
        return grayImage.getWidth();
    }

    public int getHeight(){
        return grayImage.getHeight();
    }

    public imfill(String imFill, Graphics graphics) {
        int[][] imageColor ;
        try {
            grayImage = ImageIO.read(new File(imFill));
            imfillImage = new BufferedImage(grayImage.getWidth(),
                    grayImage.getHeight(),
                    grayImage.getType());
            imageColor = new int[grayImage.getWidth()][grayImage.getHeight()];

            for (int i = 0; i < grayImage.getWidth(); i++) {
                for (int j = 0; j < grayImage.getHeight(); j++) {
                    imageColor[i][j] = grayImage.getRGB(i , j);
                }
            }

            imageColor = imfillImage(15,imageColor);
            for (int i = 0; i < grayImage.getWidth(); i++) {
                for (int j = 0; j < grayImage.getHeight(); j++) {
                    imfillImage.setRGB(i, j, imageColor[i][j]);
                }
            }

            graphics.drawImage(grayImage, 0, 0, 380, 400,null);
            graphics.drawImage(imfillImage,400,0, 380,400,null);

            File mFile = new File("C:\\Users\\asd\\Desktop\\untitled\\coins2.jpg");
            ImageIO.write(imfillImage, "jpg", mFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int[][] imfillImage(int distance, int[][] imageColor) {
        int[][] newImageColor;
        newImageColor = imageColor;
        int counter;
        for (int i = distance; i < imageColor.length - distance; i++) {
            for (int j = distance; j < imageColor[1].length - distance; j++) {
                counter = 0;
//					for (int k = 0; k < distance; k++) {
//						if (  imageColor[i-k][j] > 0xffee0000
//							&& imageColor[i][j-k] > 0xffee0000
//							&& imageColor[i][j+k] > 0xffee0000
//							&& imageColor[i+k][j] > 0xffee0000)
//						{
//							newImageColor[i][j] = 0xffffffff;
//							break;
//						}
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i-k1][j] > 0xffaa0000)
                    {
                        counter++;
                        break;
                    }
                }

                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i][j-k1] > 0xffaa0000)
                    {
                        counter++;
                        break;
                    }
                }
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i+k1][j] > 0xffaa0000)
                    {
                        counter++;
                        break;
                    }
                }

                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i][j+k1] > 0xffaa0000)
                    {
                        counter++;
                        break;
                    }
                }
                if (counter == 4)  newImageColor[i][j] = 0xffffffff;
//				}//if (imageColor[i][j] == 0xff000000)
            }//for (int j = 0; j < imageColor[1].length; j++)
        }//for (int i = 0; i < imageColor.length; i++)

        return newImageColor;


    }


}
