package vmf;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class Dilate {
    int masksize;
    int[] mask;
    int[] maskIndex;
    double[] array;
    int[] pixels;
    int width;
    int height;
    int indent;

    /*masksize =5;
    int mask[] =
        {	0,0,1,0,0,
            0,0,1,0,0,
            1,1,1,1,1,
            0,0,1,0,0,
            0,0,1,0,0};
    dilate_filt filter=new dilate_filt(masksize,mask);*/

    // Create object and initialize data
    public Dilate(int[] px, int size, int[] input)
    {
        this.pixels=px;
        masksize = size;
        mask = input;
    }

    // Runs algorithm
    public int[] performEffect()
    {
        int elements = masksize*masksize;
        int radius=(elements-1)/2;
        int pointOffset=0;
        int newPixels[] = new int[width*height];
        int indent = (int) Math.floor(masksize/2);
        int maxr, maxg, maxb;

        // Moves through the input array pixel by pixel
        for (int y=indent;y<height-indent;y++)
        {
            for (int x=indent;x<width-indent;x++)
            {
                // Location of centre of mask
                pointOffset = y*width+x;

                maxr = 0;
                maxg = 0;
                maxb = 0;

                // Finds and outputs max of RGB values covered by mask
                for (int m=-indent;m<=indent;m++)
                {
                    for (int n=-indent;n<=indent;n++)
                    {
                        maxr = Math.max(maxr,((pixels[pointOffset+n+m*width]
                                >>16)&0xff) * mask[radius+n+m*masksize]);
                        maxg = Math.max(maxg,((pixels[pointOffset+n+m*width]
                                >>8)&0xff) * mask[radius+n+m*masksize]);
                        maxb = Math.max(maxb,(pixels[pointOffset+n+m*width]
                                &0xff) * mask[radius+n+m*masksize]);
                    }
                }

                newPixels[pointOffset] = (255<<24) | maxr<<16 | maxg<<8 | maxb;
            }
        }

        this.pixels = newPixels;
        return pixels;
    }
}
