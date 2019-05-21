package imopen;

import static java.lang.Math.*;

public class LineStrel
{
    double length;
    double theta; //angle
    int[][] shifts; //store shape of SE as array of shifts with respect to central pixel

    public LineStrel(double length, double angleInDegrees)
    {
        this.length = length;
        this.theta = angleInDegrees;
        this.computeShifts();
    }

    private void computeShifts()
    {
        //direction vector
        double thetaRads = Math.toRadians(this.theta);
        double dx = Math.cos(thetaRads);
        double dy = Math.sin(thetaRads);

        //line length
        double dMax = max(abs(dx), abs(dy));
        double projLength = this.length * dMax;

        //half mask size and size of the mask
        int n2 = (int) round((projLength - 1) / 2);
        int n = 2 * n2 + 1;

        this.shifts = new int[n][2]; //allocate memory for shifts array

        //compute position of line pixels
        if (abs(dx) >= abs(dy))
        {
            //process horizontal lines
            for (int i = -n2; i <= n2; i++)
            {
                shifts[i + n2][0] = i;
                shifts[i + n2][1] = (int) round((double) i * dy / dx);
            }
        }
        else
        {
            //process vertical lines
            for (int i = -n2; i <= n2; i++)
            {
                shifts[i + n2][1] = i;
                shifts[i + n2][0] = (int) round((double) i * dx / dy);
            }
        }
    }

    public int[][] getMask()
    {
        int n = this.shifts.length;
        int[][] mask = new int[n][n];

        //calculate offsets
        int[] offsets = this.getOffset();
        int ox = offsets[0];
        int oy = offsets[1];

        //fill mask
        for (int i = 0; i < n; i++)
        {
            mask[this.shifts[i][1] + oy][this.shifts[i][0] + ox] = 1;
        }

        return mask;
    }

    public int[] getOffset()
    {
        int offset = (this.shifts.length - 1) / 2;
        return new int[]{offset, offset};
    }

    //convert to 1D array
    public int[] strelTo1D(int[][] arr){
        int[] oneDArray=new int[arr.length*arr.length];
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr.length; j++){
                oneDArray[(i*arr.length)+j]=arr[i][j];
            }
        }
        return oneDArray;
    }

    public int getMaskSize(int[] maskArray){
        return (int)sqrt(maskArray.length);
    }

}