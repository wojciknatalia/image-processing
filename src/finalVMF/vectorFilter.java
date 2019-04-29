package finalVMF;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;


// Super-class for vector functions
// Contains Euclidean algebra modules and sorting algorithms
public abstract class vectorFilter extends EffectFilter
{
    // Global data
    int masksize;
    int maskIndex[];
    double array[];

    // Calculates the sum of the angles between each pixel in the mask
    // and all other pixels in the mask
    public void calc_angle()
    {
        int curr,curg,curb,r,g,b;
        double mag_sq,cur_mag_sq;
        double theta,costheta_sq,product,inner;

        // Runs through mask
        for (int i=0;i<masksize*masksize;i++)
        {
            curr = (pixels[maskIndex[i]] >> 16)&0xff;
            curg = (pixels[maskIndex[i]] >> 8)&0xff;
            curb = (pixels[maskIndex[i]])&0xff;

            // Magnitude squared
            cur_mag_sq = (curr*curr)+(curg*curg)+(curb*curb);
            costheta_sq=0;
            product = 0;

            // Runs through mask again
            for (int j=0;j<masksize*masksize;j++)
            {
                if (i != j)
                {
                    r = (pixels[maskIndex[j]] >> 16)&0xff;
                    g = (pixels[maskIndex[j]] >> 8)&0xff;
                    b = (pixels[maskIndex[j]])&0xff;

                    // Dot product of two vectors
                    inner = (curr*r)+(curg*g)+(curb*b);
                    mag_sq = (r*r)+(g*g)+(b*b);

                    // Calc the angle between vectors
                    if ((cur_mag_sq) == 0 || mag_sq == 0)
                    {
                        // special case
                        theta = 10;
                    }
                    else
                    {
                        costheta_sq = (inner*inner)/(cur_mag_sq*mag_sq);
                        theta = Math.acos(costheta_sq);
                    }

                    // Sum of all angles
                    product += theta;
                }
            }

            array[i] = product;
        }
    }

    // Calculates the sum of the euclidean distances between each pixel in
    // the mask and all other pixels in the mask
    public void calc_dist()
    {
        int curr,curg,curb,r,g,b;
        double dis;

        // Runs through mask
        for (int i=0;i<masksize*masksize;i++)
        {
            curr = (pixels[maskIndex[i]] >> 16)&0xff;
            curg = (pixels[maskIndex[i]] >> 8)&0xff;
            curb = (pixels[maskIndex[i]])&0xff;
            dis = 0;

            // Runs through mask again
            for (int j=0;j<masksize*masksize;j++)
            {
                if (i != j)
                {
                    r = (pixels[maskIndex[j]] >> 16)&0xff;
                    g = (pixels[maskIndex[j]] >> 8)&0xff;
                    b = (pixels[maskIndex[j]])&0xff;

                    // Sum of distances between pixels
                    dis += Math.sqrt((curr-r)*(curr-r)+(curg-g)*(curg-g)+(curb-b)*(curb-b));
                }
            }
            array[i] = dis;
        }
    }

    // Calculates the sum of the absolute distances between each pixel in
    // the mask and all other pixels in the mask
    public void calc_dist2()
    {
        int curr,curg,curb,r,g,b;
        double dis;

        // Runs through mask
        for (int i=0;i<masksize*masksize;i++)
        {
            curr = (pixels[maskIndex[i]] >> 16)&0xff;
            curg = (pixels[maskIndex[i]] >> 8)&0xff;
            curb = (pixels[maskIndex[i]])&0xff;
            dis = 0;

            // Runs through mask again
            for (int j=0;j<masksize*masksize;j++)
            {
                if (i != j)
                {
                    r = (pixels[maskIndex[j]] >> 16)&0xff;
                    g = (pixels[maskIndex[j]] >> 8)&0xff;
                    b = (pixels[maskIndex[j]])&0xff;

                    // Sum of distances between pixels
                    dis += Math.abs(curr-r)+Math.abs(curg-g)+Math.abs(curb-b);
                }
            }
            array[i] = dis;
        }
    }

    // Calculates Canberra distance between each pixel in the mask
    // and all other pixels in the mask
    public void calc_canb()
    {
        int curr,curg,curb,r,g,b;
        double dis, x, y;

        // Runs through mask
        for (int i=0;i<masksize*masksize;i++)
        {
            curr = (pixels[maskIndex[i]] >> 16)&0xff;
            curg = (pixels[maskIndex[i]] >> 8)&0xff;
            curb = (pixels[maskIndex[i]])&0xff;
            dis = 0;

            // Runs through mask again
            for (int j=0;j<masksize*masksize;j++)
            {
                if (i != j)
                {
                    r = (pixels[maskIndex[j]] >> 16)&0xff;
                    g = (pixels[maskIndex[j]] >> 8)&0xff;
                    b = (pixels[maskIndex[j]])&0xff;

                    // Distance between red vector values
                    if (Math.abs(r+curr) != 0)
                    {
                        x = Math.abs(r-curr);
                        y = Math.abs(r+curr);

                        if (y!=0)
                            dis += x/y;
                    }

                    // Distance between green vector values
                    if (Math.abs(g+curg) !=0)
                    {
                        x = Math.abs(g-curg);
                        y = Math.abs(g+curg);

                        if (y!=0)
                            dis += x/y;
                    }

                    // Distance between blue vector values
                    if (Math.abs(b+curb) !=0)
                    {
                        x = Math.abs(b-curb);
                        y = Math.abs(b+curb);

                        if (y!=0)
                            dis += x/y;
                    }
                }
            }
            array[i] = dis;
        }
    }

    // Calculates Goude distance between each pixel in the mask
    // and all other pixels in the mask
    public void calc_goude()
    {
        int curr,curg,curb,r,g,b;
        double mag_sq,cur_mag_sq;
        double theta,costhetha_sq,product,inner,num,den,mag1,mag2;

        // Runs through mask
        for (int i=0;i<masksize*masksize;i++)
        {
            curr = (pixels[maskIndex[i]] >> 16)&0xff;
            curg = (pixels[maskIndex[i]] >> 8)&0xff;
            curb = (pixels[maskIndex[i]])&0xff;

            // Magnitude squared
            cur_mag_sq = (curr*curr)+(curg*curg)+(curb*curb);
            costhetha_sq=0;
            product = 0;

            // Runs through mask again
            for (int j=0;j<masksize*masksize;j++)
            {
                if (i != j)
                {
                    r = (pixels[maskIndex[j]] >> 16)&0xff;
                    g = (pixels[maskIndex[j]] >> 8)&0xff;
                    b = (pixels[maskIndex[j]])&0xff;

                    // Dot product of two vectors
                    inner = (curr*r)+(curg*g)+(curb*b);
                    mag_sq = (r*r)+(g*g)+(b*b);

                    // Calc the angle between vectors
                    if ((cur_mag_sq) == 0 || mag_sq == 0)
                    {
                        // special case
                        theta = 10;
                    }
                    else
                    {
                        costhetha_sq = (inner*inner)/(cur_mag_sq*mag_sq);
                        theta = Math.acos(costhetha_sq);
                    }

                    // Calc the magnitude of the two vectors
                    mag1 = calc_mag(pixels[maskIndex[i]]);
                    mag2 = calc_mag(pixels[maskIndex[j]]);

                    // Calc the numerator and denominator for S equation
                    num = Math.sqrt(Math.pow(mag1,2) + Math.pow(mag2,2) -
                            (2*mag1*mag2*theta));
                    den = Math.sqrt(Math.pow(mag1,2) + Math.pow(mag2,2) +
                            (2*mag1*mag2*theta));

                    if (den == 0)
                        product = 0;
                    else
                        product = num/den;
                }
                else
                {
                    product = 0;
                }
            }

            array[i] = 1-product;
        }
    }

    // Calculates the euclidean distance between each pixel in the mask
    // and the centre pixel in the mask
    public void calc_centre()
    {
        int curr,curg,curb,r,g,b;
        double dis;

        // RGB components of centre pixel
        curr = (pixels[maskIndex[(int)Math.floor((masksize*masksize)/2)]] >> 16)&0xff;
        curg = (pixels[maskIndex[(int)Math.floor((masksize*masksize)/2)]] >> 8)&0xff;
        curb = (pixels[maskIndex[(int)Math.floor((masksize*masksize)/2)]])&0xff;

        // Runs through the mask
        for (int i=0;i<masksize*masksize;i++)
        {
            dis = 0;
            // Make sure it is not centre pixel
            if (i != (int)Math.floor((masksize*masksize)/2))
            {
                r = (pixels[maskIndex[i]] >> 16)&0xff;
                g = (pixels[maskIndex[i]] >> 8)&0xff;
                b = (pixels[maskIndex[i]])&0xff;

                // Distance calculation
                dis = Math.sqrt((curr-r)*(curr-r)+(curg-g)*(curg-g)+(curb-b)*(curb-b));
            }
            else
            {
                dis = 0;
            }
            array[i] = dis;
        }
    }

    // Calculates the absolute distance between each pixel in the mask
    // and the centre pixel in the mask
    public void calc_centre2()
    {
        int curr,curg,curb,r,g,b;
        double dis;

        // RGB components of centre pixel
        curr = (pixels[maskIndex[(int)Math.floor((masksize*masksize)/2)]] >> 16)&0xff;
        curg = (pixels[maskIndex[(int)Math.floor((masksize*masksize)/2)]] >> 8)&0xff;
        curb = (pixels[maskIndex[(int)Math.floor((masksize*masksize)/2)]])&0xff;

        // Runs through the mask
        for (int i=0;i<masksize*masksize;i++)
        {
            dis = 0;
            // Make sure it is not centre pixel
            if (i != (int)Math.floor((masksize*masksize)/2))
            {
                r = (pixels[maskIndex[i]] >> 16)&0xff;
                g = (pixels[maskIndex[i]] >> 8)&0xff;
                b = (pixels[maskIndex[i]])&0xff;

                // Distance calculation
                dis = Math.abs(curr-r)+Math.abs(curg-g)+Math.abs(curb-b);
            }
            else
            {
                dis = 0;
            }
            array[i] = dis;
        }
    }

    // Returns euclidean distance between two vectors
    public double vect_dist(int v1, int v2)
    {
        int r1, r2, g1, g2, b1, b2;
        double result;

        // RGB components of first vector
        r1 = (v1>>16)&0xff;
        g1 = (v1>>8)&0xff;
        b1 = (v1)&0xff;

        // RGB components of second vector
        r2 = (v2>>16)&0xff;
        g2 = (v2>>8)&0xff;
        b2 = (v2)&0xff;

        // Distance calculation
        result = Math.sqrt((r2-r1)*(r2-r1)+(g2-g1)*(g2-g1)+(b2-b1)*(b2-b1));

        return result;
    }

    // Returns absolute distance between two vectors
    public double vect_dist2(int v1, int v2)
    {
        int r1, r2, g1, g2, b1, b2;
        double result;

        // RGB components of first vector
        r1 = (v1>>16)&0xff;
        g1 = (v1>>8)&0xff;
        b1 = (v1)&0xff;

        // RGB components of second vector
        r2 = (v2>>16)&0xff;
        g2 = (v2>>8)&0xff;
        b2 = (v2)&0xff;

        // Distance calculation
        result = Math.abs(r2-r1)+Math.abs(g2-g1)+Math.abs(b2-b1);

        return result;
    }

    // Returns magnitude of vector
    public double calc_mag(int v)
    {
        int r, g, b;
        double result;

        // RGB components of vector
        r = (v>>16)&0xff;
        g = (v>>8)&0xff;
        b = (v)&0xff;

        // Magnitude calculation
        result = Math.sqrt((r*r)+(g*g)+(b*b));

        return result;
    }

    // Bubble sort of the mask array in ascending order
    public void b_sort()
    {
        int temp;
        double temp2;

        // Standard bubble sort
        for (int x=0; x < (masksize*masksize)-1; x++)
        {
            for (int y=0; y < (masksize*masksize)-x-1; y++)
            {
                if (array[y] > array[y+1])
                {
                    temp2 = array[y];
                    temp = maskIndex[y];
                    array[y] = array[y+1];
                    maskIndex[y] = maskIndex[y+1];
                    array[y+1] = temp2;
                    maskIndex[y+1] = temp;
                }
            }
        }
    }

}