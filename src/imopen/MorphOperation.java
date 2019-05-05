package imopen;

public class MorphOperation {
    int masksize;
    int[] mask;
    int[] maskIndex;
    double[] array;
    int[] pixels;
    int width;
    int height;
    int elements;
    int radius;
    int pointOffset;
    int newPixels[];
    int indent;

    void assignData(){
        elements = masksize*masksize;
        radius=(elements-1)/2;
        pointOffset=0;
        newPixels= new int[width*height];
        indent = (int) Math.floor(masksize/2);
    }
}
