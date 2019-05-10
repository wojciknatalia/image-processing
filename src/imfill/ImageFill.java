package imfill;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFill {

    private static BufferedImage inputImage;
    private static BufferedImage imfillImage;

    public int getWidth(){
        return inputImage.getWidth();
    }
    public int getHeight(){
        return inputImage.getHeight();
    }

    public ImageFill(String imFill) throws IOException{
        int[][] inputImg2D ;
        try {
            inputImage = ImageIO.read(new File(imFill));
            imfillImage = new BufferedImage(inputImage.getWidth(),
                    inputImage.getHeight(),
                    inputImage.getType());

            inputImg2D = new int[inputImage.getWidth()][inputImage.getHeight()];

            for (int i = 0; i < inputImage.getWidth(); i++) {
                for (int j = 0; j < inputImage.getHeight(); j++) {
                    inputImg2D[i][j] = inputImage.getRGB(i , j);
                }
            }

            inputImg2D = imfillImage(15,inputImg2D);
            for (int i = 0; i < inputImage.getWidth(); i++) {
                for (int j = 0; j < inputImage.getHeight(); j++) {
                    imfillImage.setRGB(i, j, inputImg2D[i][j]);
                }
            }

            }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int[][] imfillImage(int distance, int[][] imgIn) {
        int[][] newImg2D;
        newImg2D = imgIn;
        int counter;
        for (int i = distance; i < imgIn.length - distance; i++) {
            for (int j = distance; j < imgIn[1].length - distance; j++) {
                counter = 0;
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imgIn[i-k1][j] > 0xffaa0000)
                    {
                        counter++;
                        break;
                    }
                }

                for (int k1 = 0; k1 < distance; k1++) {
                    if (imgIn[i][j-k1] > 0xffaa0000)
                    {
                        counter++;
                        break;
                    }
                }
                for (int k1 = 0; k1 < distance; k1++) {
                    if (imgIn[i+k1][j] > 0xffaa0000)
                    {
                        counter++;
                        break;
                    }
                }

                for (int k1 = 0; k1 < distance; k1++) {
                    if (imgIn[i][j+k1] > 0xffaa0000)
                    {
                        counter++;
                        break;
                    }
                }
                if (counter == 4)  newImg2D[i][j] = 0xffffffff;
            }
        }
        return newImg2D;
    }

    public static BufferedImage getFilledImage() {
        return imfillImage;
    }

}