package imfill;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFill {

    private static BufferedImage inputImage;
    private static BufferedImage imfillImage;

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

    private int[][] imfillImage(int dist, int[][] imgIn) {
        int[][] newImg2D;
        newImg2D = imgIn;
        int counter;
        for (int i = dist; i < imgIn.length - dist; i++) {
            for (int j = dist; j < imgIn[1].length - dist; j++) {
                counter = 0;
                for (int k = 0; k < dist; k++) { //pixel sides
                    if (imgIn[i-k][j] > 0xff808080)
                    {
                        counter++;
                        break;
                    }
                }
                for (int k = 0; k < dist; k++) {
                    if (imgIn[i][j-k] > 0xff808080) {
                        counter++;
                        break;
                    }
                }
                for (int k = 0; k < dist; k++) {
                    if (imgIn[i+k][j] > 0xff808080)
                    {
                        counter++;
                        break;
                    }
                }
                for (int k = 0; k < dist; k++) {
                    if (imgIn[i][j + k] > 0xff808080) {
                        counter++;
                        break;
                    }
                } //end of pixel sides
                for (int k = 0; k < dist; k++) { //pixel vertex area
                    if (imgIn[i-k][j-k] > 0xff808080)
                    {
                        counter++;
                        break;
                    }
                }
                for (int k = 0; k < dist; k++) {
                    if (imgIn[i+k][j+k] > 0xff808080)
                    {
                        counter++;
                        break;
                    }
                }
                for (int k = 0; k < dist; k++) {
                    if (imgIn[i - k][j + k] > 0xff808080) {
                        counter++;
                        break;
                    }
                }
                for (int k = 0; k < dist; k++) {
                    if (imgIn[i + k][j - k] > 0xff808080) {
                        counter++;
                        break;
                    }
                }
                if (counter == 8)  newImg2D[i][j] = 0xffffffff;
            }
        }
        return newImg2D;
    }

    public static BufferedImage getFilledImage() {
        return imfillImage;
    }

}