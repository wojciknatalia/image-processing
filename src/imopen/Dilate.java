package imopen;

public class Dilate extends MorphOperation{

    //Create object and initialize data
    public Dilate(int[] px, int size, int[] input, int w, int h)
    {
        this.pixels=px;
        this.width=w;
        this.height=h;
        masksize = size;
        mask = input;
    }

    //Runs algorithm
    public int[] performEffect()
    {
        int maxr, maxg, maxb;
        super.assignData();

        //Moves through the input array pixel by pixel
        for (int y=indent;y<height-indent;y++)
        {
            for (int x=indent;x<width-indent;x++)
            {
                //Location of centre of mask
                pointOffset = y*width+x;

                maxr = 0;
                maxg = 0;
                maxb = 0;

                //Finds and outputs max of RGB values covered by mask
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
