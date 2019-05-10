package imopen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Opening extends JFrame {

    private static BufferedImage openImage;
    BufferedImage sourceImage = null;
    //static ImageUtilities util= new ImageUtilities();
    //public static final String imageName = util.getImageName();


    public Opening(String fileName, int mSize, int[] myMask){

        try {
            sourceImage = ImageIO.read(new File(fileName));
            new BufferedImage(sourceImage.getWidth(),
                    sourceImage.getHeight(),
                    sourceImage.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] tab2D = convertTo2D(sourceImage);
        int[] tab1D=convertTo1D(tab2D, sourceImage.getWidth(), sourceImage.getHeight());

        openImage = makeNewBufferedImage1D(doOpening(tab1D,sourceImage.getWidth(),sourceImage.getHeight(), mSize, myMask), sourceImage.getWidth(), sourceImage.getHeight());
    }


public int[][] convertTo2D(BufferedImage source){
        int greyScale[][] = new int[source.getWidth()][source.getHeight()];
        for(int x=0; x<source.getWidth(); x++){
            for(int y=0; y<source.getHeight(); y++){
                int c = source.getRGB(x, y);
                greyScale[x][y] = c; }
        }
        return greyScale;
    }

public int[] doOpening(int[] input, int width, int height, int mSize, int[] myMask){
        int[] output;
        Erode erode=new Erode(input,mSize, myMask, width, height);
        output=erode.performEffect();
        Dilate dilate=new Dilate(output,mSize, myMask, width, height);
        output=dilate.performEffect();
        return output;
    }

public int[] convertTo1D(int[][] input, int width, int height){
        int[] pixels=new int[width*height];
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[i++] = input[x][y]; }
        }
        return pixels;
    }

public BufferedImage makeNewBufferedImage1D(int[] pixels, int width, int height){

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }

public static BufferedImage getOpenImage() {
        return openImage;
        }

}