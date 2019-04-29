package vmf;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class Erode {

    int masksize;
    int[] mask;
    int[] maskIndex;
    double[] array;
    int[] pixels;
    int width;
    int height;
    int indent;

    /*masksize=5;
    int mask[] = {	0,0,1,0,0,
            0,0,1,0,0,
            1,1,1,1,1,
            0,0,1,0,0,
            0,0,1,0,0};
    erode_filt filter=new Erode(masksize,mask);*/

    // Create object and initialize data
    public Erode(int[] px, int size, int[] input)
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
        int minr, minb, ming;

        // Moves through the input array pixel by pixel
        for (int y=indent;y<height-indent;y++)
        {
            for (int x=indent;x<width-indent;x++)
            {
                // Location of centre of mask
                pointOffset = y*width+x;

                minr = 255;
                ming = 255;
                minb = 255;

                // Finds and outputs max of RGB values covered by mask
                for (int m=-indent;m<=indent;m++)
                {
                    for (int n=-indent;n<=indent;n++)
                    {
                        if (mask[radius+n+m*masksize] !=0)
                        {
                            minr = Math.min(minr,((pixels[pointOffset+n+m*width]
                                    >>16)&0xff)*mask[radius+n+m*masksize]);
                            ming = Math.min(ming,((pixels[pointOffset+n+m*width]
                                    >>8)&0xff)*mask[radius+n+m*masksize]);
                            minb = Math.min(minb,(pixels[pointOffset+n+m*width]
                                    &0xff)*mask[radius+n+m*masksize]);
                        }
                    }
                }

                newPixels[pointOffset] = (255<<24) | minr<<16 | ming<<8 | minb;
            }
        }

        this.pixels = newPixels;
        return pixels;
    }
}
