package imopen;

import java.util.Arrays;

import static java.lang.Math.*;

public class LineStrel
{
    /** The length of the line, in pixels */
    double length;

    /** orientation of the line, in degrees, counted CCW from horizontal. */
    double theta;

    /**
     * Stores the shape of this structuring element as an array of shifts with
     * respect to the central pixel.
     * The array has N-by-2 elements, where N is the number of pixels composing the strel.
     */
    int[][] shifts;

    /**
     * Creates an new instance of linear structuring element. The number of
     * pixels composing the line may differ from the specified length due to
     * rounding effects.
     *
     * @param length
     *            the (approximate) length of the structuring element.
     * @param angleInDegrees
     *            the angle with the horizontal of the structuring element
     */
    public LineStrel(double length, double angleInDegrees)
    {
        this.length = length;
        this.theta = angleInDegrees;

        this.computeShifts();
    }

    /**
     * Computes the position of the pixels that constitutes this structuring
     * element.
     */
    private void computeShifts()
    {
        // Components of direction vector
        double thetaRads = Math.toRadians(this.theta);
        double dx = Math.cos(thetaRads);
        double dy = Math.sin(thetaRads);

        // length of projected line
        double dMax = max(abs(dx), abs(dy));
        double projLength = this.length * dMax;

        // half-size and size of the mask
        int n2 = (int) round((projLength - 1) / 2);
        int n = 2 * n2 + 1;

        // allocate memory for shifts array
        this.shifts = new int[n][2];

        // compute position of line pixels
        if (abs(dx) >= abs(dy))
        {
            // process horizontal lines
            for (int i = -n2; i <= n2; i++)
            {
                shifts[i + n2][0] = i;
                shifts[i + n2][1] = (int) round((double) i * dy / dx);
            }
        }
        else
        {
            // process vertical lines
            for (int i = -n2; i <= n2; i++)
            {
                shifts[i + n2][1] = i;
                shifts[i + n2][0] = (int) round((double) i * dx / dy);
            }
        }
    }

    /**
     * Returns the size of the structuring element, as an array of size in
     * each direction.
     * @return the size of the structuring element
     */
    public int[] getSize()
    {
        int n = this.shifts.length;
        return new int[]{n, n};
    }

    /**
     * Returns the structuring element as a mask. Each value is either 0 or 255.
     * @return the mask of the structuring element
     */
    public int[][] getMask()
    {
        int n = this.shifts.length;
        int[][] mask = new int[n][n];

        // precompute offsets
        int[] offsets = this.getOffset();
        int ox = offsets[0];
        int oy = offsets[1];

        // fill up the mask
        for (int i = 0; i < n; i++)
        {
            mask[this.shifts[i][1] + oy][this.shifts[i][0] + ox] = 1;
        }

        return mask;
    }

    /**
     * Returns the offset in the mask.
     * @return the offset in the mask
     */
    public int[] getOffset()
    {
        int offset = (this.shifts.length - 1) / 2;
        return new int[]{offset, offset};
    }

    /**
     * Returns the structuring element as a set of shifts.
     * @return a set of shifts
     */
    public int[][] getShifts()
    {
        return this.shifts;
    }

    /**
     * Returns this structuring element, as oriented line structuring elements
     * are symmetric by definition.
     */

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

   /* public static void main(String[] args){
        LineStrel strel=new LineStrel(3,45);
        int[][] mask=strel.getMask();
        int[] mask1D=strelTo1D(mask); //convert 2D to 1D

        System.out.println(Arrays.toString(mask1D));
        System.out.println((int)sqrt(mask1D.length));
    }*/
}