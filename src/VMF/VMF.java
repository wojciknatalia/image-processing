package VMF;

public class VMF {
    private int masksize;
    int[] maskIndex;
    double[] array;
    int[] pixels;
    int width;
    int height;
    int indent;

    VMF(int[] px, int w, int h, int mSize){
        this.pixels=px;
        this.width=w;
        this.height=h;
        this.masksize=mSize;
        this.maskIndex=new int[masksize*masksize];
        this.array=new double[masksize*masksize];
        this.indent=(int)Math.floor(masksize/2);
    }

    public int[] performEffect(){
        int counter;
        int newPixels[]=new int[width*height];

        //move through input array pixel by pixel
        for(int y=indent; y<height-indent; y++){
            for(int x=indent; x<width-indent; x++){
                int pointOffset=y*width+x; //centre of mask
                counter=0; //index for maskIndex
                outloop:
                for(int i=-indent;i<=indent;i++){
                    for(int j=-indent;j<=indent;j++){
                        if(counter>=masksize*masksize)
                            break outloop;
                        maskIndex[counter]=(y+i)*width+(x+j);
                        counter++;
                    }
                }
                calcDist();
                bSort();

                //outputs pixel with the lowest sum of distances
                newPixels[pointOffset]=pixels[maskIndex[0]];
            }
        }
        this.pixels=newPixels;
        return pixels;
    }

    public void calcDist()
    {
        int curr,curg,curb,r,g,b;
        double dis;

        //run through mask and get values
        for (int i=0;i<masksize*masksize;i++)
        {
            curr = (pixels[maskIndex[i]] >> 16)&0xff;
            curg = (pixels[maskIndex[i]] >> 8)&0xff;
            curb = (pixels[maskIndex[i]])&0xff;
            dis = 0;

            //run through mask again and compare current pixel to others
            for (int j=0;j<masksize*masksize;j++)
            {
                if (i != j)
                {
                    r = (pixels[maskIndex[j]] >> 16)&0xff;
                    g = (pixels[maskIndex[j]] >> 8)&0xff;
                    b = (pixels[maskIndex[j]])&0xff;

                    //sum of distance between pixels
                    dis += Math.sqrt((curr-r)*(curr-r)+(curg-g)*(curg-g)+(curb-b)*(curb-b));
                }
            }
            array[i] = dis;
        }
    }

    public void bSort() //standard bubble sort
    {
        int temp;
        double temp2;

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
