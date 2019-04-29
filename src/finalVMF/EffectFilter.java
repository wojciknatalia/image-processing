package finalVMF;

import java.awt.image.*;
import java.awt.*;

public abstract class EffectFilter extends ImageFilter
{
    int width;
    int height;
    int pixels[];

    public EffectFilter()
    {
    }

    public void setHints(int hints)
    {
        consumer.setHints(hints & ~ImageConsumer.COMPLETESCANLINES);
    }

    public void setDimensions(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.pixels = new int[width*height];

        consumer.setDimensions(width, height);
    }

    public void setPixels(int x,int y,int width,int height,ColorModel model,byte[] pixels,int offset,int scansize)
    {
        for (int i=0;i<height;i++)
        {
            int destLineOffset = (y+i)*width;
            int srcLineOffset = i*scansize+offset;

            for (int j=0;j<width;j++)
            {
                int pixel = pixels[srcLineOffset+j]&0xff;
                this.pixels[destLineOffset+x+j]=model.getRGB(pixel);
            }
        }
    }

    public void setPixels(int x,int y,int width,int height,ColorModel model,int[] pixels,int offset,int scansize)
    {
        for (int i=0;i<height;i++)
        {
            int destLineOffset = (y+i)*width;
            int srcLineOffset = i*scansize+offset;

            for (int j=0;j<width;j++)
            {
                int pixel = pixels[srcLineOffset+j];
                this.pixels[destLineOffset+x+j]=model.getRGB(pixel);
            }
        }
    }

    public void imageComplete(int status)
    {
        performEffect();
        deliverPixels();
        super.imageComplete(status);
    }

    public abstract void performEffect();

    protected void deliverPixels()
    {
        consumer.setPixels(0,0,this.width,this.height,ColorModel.getRGBdefault(),this.pixels,0,this.width);
    }
}
