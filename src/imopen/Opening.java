package imopen;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Opening extends JFrame {

    private static BufferedImage openImage; //grey image of input image
    BufferedImage sourceImage = null;
    static ImageUtilities util= new ImageUtilities();
    public static final String imageName = util.getImageName();

    int masksize=5; //TO DO: customize masksize and mask
    int mask[]={
            0,0,1,0,0,
            0,0,1,0,0,
            1,1,1,1,1,
            0,0,1,0,0,
            0,0,1,0,0,
    };

    Opening(){
        sourceImage = ImageUtilities.getBufferedImage(imageName, this);
        int[][] greyScale = RGBToGrey(sourceImage);
        int[] tab1D=convertTo1D(greyScale, sourceImage.getWidth(), sourceImage.getHeight());
        openImage = makeNewBufferedImage1D(doOpening(tab1D,sourceImage.getWidth(),sourceImage.getHeight()), sourceImage.getWidth(), sourceImage.getHeight());
        //below need to be checked
    }


public int[][] RGBToGrey(BufferedImage source){
        int greyScale[][] = new int[source.getWidth()][source.getHeight()];
        for(int x=0; x<source.getWidth(); x++){
        for(int y=0; y<source.getHeight(); y++){
        int c = source.getRGB(x, y);
        float r = (c&0x00ff0000)>>16;
        float g = (c&0x0000ff00)>>8;
        float b = c&0x000000ff;
        //greyScale[x][y] = (int)(0.3*r + 0.59*g + 0.11*b);
        greyScale[x][y] = c; }
        }
        return greyScale;
    }

public int[] doOpening(int[] input, int width, int height){
        int[] output;
        Erode erode=new Erode(input,masksize, mask, width, height);
        output=erode.performEffect();
        Dilate dilate=new Dilate(output,masksize, mask, width, height);
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