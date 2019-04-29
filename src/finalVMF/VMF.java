package finalVMF;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

// Vector Median Filter
public class VMF extends vectorFilter
{
    int indent;

    // Create object and initialize data
    public VMF(int size)
    {
        masksize = size;
        maskIndex = new int[masksize*masksize];
        array = new double[masksize*masksize];
        indent = (int) Math.floor(masksize/2);
    }

    // Runs the algorithm
    public void performEffect()
    {
        int counter;
        int newPixels[] = new int [width*height];

        // Moves through the input array pixel by pixel
        for (int y=indent;y<height-indent;y++)
        {
            for (int x=indent;x<width-indent;x++)
            {
                // Location of centre of mask
                int pointOffset = y*width+x;

                // Calculate Index for mask
                counter = 0;
                for (int i=-indent;i<=indent;i++)
                {
                    for (int j=-indent;j<=indent;j++)
                    {
                        maskIndex[counter] = (y+i)*width+(x+j);
                        counter++;
                    }
                }

                // Sorts pixels in mask by sum of distances
                calc_dist();
                b_sort();

                // Outputs pixel with lowest sum of distances
                newPixels[pointOffset] = pixels[maskIndex[0]];
            }
        }
        this.pixels = newPixels;
    }
}
