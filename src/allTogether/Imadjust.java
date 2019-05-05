package allTogether;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Imadjust {

    private static File file;
    private static BufferedImage in;

    Imadjust(String fileName){
        try {
            readFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //in is contrast, out is brightness

        //na sztywno
        increaseBrightness(in, 20);
        increaseContrast(in, 130);
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

    private static void increaseContrast(BufferedImage img, int contrastMod)
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

            rgbArr[i] = toRGB(truncate(r < 127 ? r - contrastMod : r + contrastMod, 0, 255),
                    truncate(g < 127 ? r - contrastMod : g + contrastMod, 0, 255),
                    truncate(b < 127 ? r - contrastMod : b + contrastMod, 0, 255));
        }

        img.setRGB(0, 0, width, height, rgbArr, 0, width);
    }

    public static BufferedImage getAdjustedImage() {
        return in;
    }
}