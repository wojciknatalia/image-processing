package imadjust;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Imadjust {

    private static BufferedImage in;

    public Imadjust(String fileName, int i1, int o1, int i2, int o2, int i3, int o3){
        try {
            in = ImageIO.read(new File(fileName));
            new BufferedImage(in.getWidth(),
                    in.getHeight(),
                    in.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        normalize(in, i1, i2, o1, o2);
        normalize(in, i2, i3, o2, o3);
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

    private static int crop(int n, int left, int right) {
        if (n < left) {
            n = left;
        } else if (n > right) {
            n = right;
        }
        return n;
    }

    private void normalize(BufferedImage img, int minvalin, int maxvalin, int minvalout, int maxvalout)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        int r,g,b;
        boolean flag=false;

        int[] rgbArr = new int[width * height];
        img.getRGB(0, 0, width, height, rgbArr, 0, width);
        int pixel, red, green, blue;

        for (int i = 0; i < width * height; i++) {
            r = getR(rgbArr[i]);
            g = getG(rgbArr[i]);
            b = getB(rgbArr[i]);

            if(r==g & g==b) //monochrome
            {
                flag=true;
            }
            if (r < minvalin | r > maxvalin)
                r = crop(r, minvalout, maxvalout);

            else if (g < minvalin | g > maxvalin)
                g = crop(g, minvalout, maxvalout);

            else if (b < minvalin | b > maxvalin)
                b = crop(b, minvalout, maxvalout);

            if(flag){
                int val = (int)(0.21 * r) + (int)(0.72 * g) + (int)(0.07 * b);
                rgbArr[i] = toRGB(val, val, val);
                flag=false;
            }
            else
                rgbArr[i] = toRGB(r, g, b);

        }

        img.setRGB(0, 0, width, height, rgbArr, 0, width);
    }

    public static BufferedImage getAdjustedImage() {
        return in;
    }
}