package newnew;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Imadjust {

    private static File file;
    private static BufferedImage in;

    public Imadjust(String fileName, int i1, int o1, int i2, int o2, int i3, int o3){
        try {
            //readFile(fileName);
            in = ImageIO.read(new File(fileName));
            new BufferedImage(in.getWidth(),
                    in.getHeight(),
                    in.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //in is contrast, out is brightness

        //na sztywno
       /* increaseBrightness(in, o1);
        increaseContrast(in, i1);
        increaseBrightness(in, o2);
        increaseContrast(in, i2);
        increaseBrightness(in, o3);
        increaseContrast(in, i3);*/

        int[][] imageColor = new int[in.getWidth()][in.getHeight()];

        for (int i = 0; i < in.getWidth(); i++) {
            for (int j = 0; j < in.getHeight(); j++) {
                imageColor[i][j] = in.getRGB(i , j);
            }
        } //read image as array

        for (int i = 0; i < in.getWidth(); i++) {
            for (int j = 0; j < in.getHeight(); j++) {
                if(i<i1)
                    increaseContrast(in, 0,i1);
                else if(i>i1 & i< i2)
                {
                    increaseContrast(in, i1,i2);
                }
                else if(i>i2 & i<i3)
                {
                    increaseContrast(in, i2, i3);
                }
                else if(i>i3)
                    increaseContrast(in, i2, 255);
            }
        }

       /* increaseBrightness(in, o1);
        increaseContrast(in, 0,i1);
        increaseBrightness(in, o2);
        increaseContrast(in, i1,i2);
        increaseBrightness(in, o3);
        increaseContrast(in, i2, i3);*/
        //increaseContrast(in, i2, 255)
    }

    private static void readFile(String fileName) throws IOException {
        file = new File(fileName);
        in = ImageIO.read(file);
    }

    private static int getR(int in) {
        return (int)((in << 8) >> 24) & 0xff;
    }
    private static int getG(int in) {
        return (int)((in << 16) >> 24) & 0xff;
    }
    private static int getB(int in) {
        return (int)((in << 24) >> 24) & 0xff;
    }
    private static int toRGB(int r,int g,int b) {
        return (int)((((r << 8)|g) << 8)|b);
    }

    private static int truncate(int n, int leftMargin, int rightMargin) {
        if (n < leftMargin) {
            n = leftMargin;
        } else if (n > rightMargin) {
            n = rightMargin;
        }
        return n;
    }

    private static void grayscale(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int r,g,b;

        int[] rgbArr = new int[width * height];
        img.getRGB(0, 0, width, height, rgbArr, 0, width);

        for (int i = 0; i < width * height; i++) {
            r = getR(rgbArr[i]);
            g = getG(rgbArr[i]);
            b = getB(rgbArr[i]);

            int val = (int)(0.21 * r) + (int)(0.72 * g) + (int)(0.07 * b);
            rgbArr[i] = toRGB(val, val, val);
        }

        img.setRGB(0, 0, width, height, rgbArr, 0, width);
    }

    private static void increaseBrightness(BufferedImage img, int brigtnessMod)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        int r,g,b;

        int[] rgbArr = new int[width * height];
        img.getRGB(0, 0, width, height, rgbArr, 0, width);

        for (int i = 0; i < width * height; i++) {
            r = getR(rgbArr[i]);
            g = getG(rgbArr[i]);
            b = getB(rgbArr[i]);

            rgbArr[i] = toRGB(r + brigtnessMod > 255 ? 255 : r + brigtnessMod,
                    g + brigtnessMod > 255 ? 255 : g + brigtnessMod,
                    b + brigtnessMod > 255 ? 255 : b + brigtnessMod);
        }

        img.setRGB(0, 0, width, height, rgbArr, 0, width);
    }

    private static void increaseContrast(BufferedImage img, int l, int rM)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        int r,g,b;

        int[] rgbArr = new int[width * height];
        img.getRGB(0, 0, width, height, rgbArr, 0, width);

        for (int i = 0; i < width * height; i++) {
            r = getR(rgbArr[i]);
            g = getG(rgbArr[i]);
            b = getB(rgbArr[i]);

            rgbArr[i] = toRGB(truncate(r, l, rM),
                    truncate(g, l, rM),
                    truncate(b, l, rM));
        }

        img.setRGB(0, 0, width, height, rgbArr, 0, width);
    }

    public static BufferedImage getAdjustedImage() {
        return in;
    }
}