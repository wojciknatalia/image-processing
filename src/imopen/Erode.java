package imopen;

public class Erode extends MorphOperation {

    public Erode(int[] px, int size, int[] input, int w, int h)
    {
        this.pixels=px;
        this.width=w;
        this.height=h;
        masksize = size;
        mask = input;
    }

    //run transformation
    public int[] performEffect()
    {
        int minr, minb, ming;
        super.assignData();

        //move through input array pixel by pixel
        for (int y=indent;y<height-indent;y++)
        {
            for (int x=indent;x<width-indent;x++)
            {
                //calculate centre of mask
                pointOffset = y*width+x;

                minr = 255;
                ming = 255;
                minb = 255;

                //find min of RGB values covered by mask
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

                newPixels[pointOffset] = (255<<24) | minr<<16 | ming<<8 | minb; //calculate pixel value
            }
        }

        this.pixels = newPixels;
        return pixels;
    }
}
