package norm;

/*import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class ImageArray
{
    double M;
    double VAR;
    BufferedImage img;
    int w;
    int h;
    double[][] imagedata;
    double [][] outputdata;


    public ImageArray()
    {
        try {
            img = ImageIO.read(new File("meadow.jpg"));
            w = img.getWidth();
            h = img.getHeight();
            imagedata = new double[w][h];
            outputdata = new double[w][h];
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public double Mean()
    {

        for(int x=0;x<imagedata.length;x++)
        {
            for(int y=0;y<imagedata[x].length;y++)
            {
                Color color = new Color(img.getRGB(x, y));

                imagedata[x][y] = color.getRed();
            }
        }


        for(int x=0;x<imagedata.length;x++)
        {
            for(int y=0;y<imagedata[x].length;y++)
            {
                M+=(imagedata[x][y]);
            }
        }

        M =   M/(w*h);
        return M;

    }



    public double Variance()
    {

        for(int x=0;x<imagedata.length;x++)
        {
            for(int y=0;y<imagedata[x].length;y++)
            {
                VAR+=Math.pow(imagedata[x][y]-M,2);
            }
        }

        VAR = VAR/(w*h);
        return VAR;
    }



    public void normalization(double mean,double varience)
    {
        int M0 = 100;
        int VAR0 = 100;

        for(int x=0;x<imagedata.length;x++)
        {
            for(int y=0;y<imagedata[x].length;y++)
            {
                if(imagedata[x][y]>mean)
                {
                    outputdata[x][y]=M0+(Math.sqrt(VAR0*Math.pow(imagedata[x][y]-M, 2)))/VAR;
                }else
                {
                    outputdata[x][y]=M0-(Math.sqrt(VAR0*Math.pow(imagedata[x][y]-M, 2)))/VAR;
                }
            }
        }

    }




    public void DrawImage() throws IOException
    {
        BufferedImage outputImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);

        for(int x=0;x<outputdata.length;x++)
        {
            for(int y=0;y<outputdata[x].length;y++)
            {
                outputImage.setRGB(x, y, (int) outputdata[x][y]);
            }
        }



        Graphics2D g2 = outputImage.createGraphics();
        g2.drawImage(outputImage, null, null);

        File imageFile = new File("output.jpg");
        ImageIO.write(outputImage, "jpg", imageFile);

    }







    public static void main(String args[])
    {

        try {
            ImageArray abc = new ImageArray();
            abc.Mean();
            abc.Variance();
            abc.normalization(abc.M,abc.VAR);
            abc.DrawImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/