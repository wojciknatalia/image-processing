package contrastToBe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public class MyImage extends Frame {
    Image rawImg;
    int imgCols;
    int imgRows;
    Image modImg; //reference to modified image

    int inTop;
    int inLeft;

    static String theProcessingClass = "ProgramTest";
    static String myImgFile="mountains.jpg"; //default image

    MediaTracker tracker;
    Display display=new Display();
    Button replotButton=new Button("Replot");

    //references to arrays that store pixel data
    int[][][] dimPixels;
    /*int[row][column][depth].
    The third dimension always has a value of 4 and
    contains the following values by index value: 0 alpha, 1 red, 2 green, 3 blue*/

    int[][][] dimPixelsMod;
    int[] dPix;

    ImageInterface imageProcessingObject;

    public static void main(String[] args){
        MyImage obj=new MyImage();
    }

    public MyImage(){
        rawImg=Toolkit.getDefaultToolkit().getImage(myImgFile);
        tracker=new MediaTracker(this); //block until img is loaded
        tracker.addImage(rawImg, 1);

        try{
            if(!tracker.waitForID(1,10000)){
                System.out.println("Loding image error");
                System.exit(1);
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        }

        if((tracker.statusAll(false) & MediaTracker.ABORTED & MediaTracker.ERRORED) != 0){
            System.out.println("Load error or aborted");
            System.exit(1);
        }

        imgCols=rawImg.getWidth(this);
        imgRows=rawImg.getHeight(this);

        this.add(display);
        this.add(replotButton, BorderLayout.SOUTH);
        setVisible(true);
        inTop=this.getInsets().top;
        inLeft=this.getInsets().left;
        int buttonHeight=replotButton.getSize().height;
        this.setSize(2*inLeft+imgCols+1, inTop+buttonHeight+2*imgRows+7);

        //=== action listener
        replotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimPixelsMod=imageProcessingObject.processImg(dimPixels,imgRows, imgCols);
                dPix=convertToOneDim(dimPixelsMod, imgCols, imgRows);
                modImg=createImage(new MemoryImageSource(imgCols, imgRows, dPix, 0, imgCols));
                display.repaint();
            }
        });

        //===
        dPix=new int[imgCols*imgRows];
        try{
            PixelGrabber pgObj=new PixelGrabber(
                    rawImg, 0, 0, imgCols, imgRows,
                    dPix, 0, imgCols);
            //extract pixels
            if(pgObj.grabPixels() && ((pgObj.getStatus() & ImageObserver.ALLBITS) != 0)){
                dimPixels=convertToThreeDim(dPix, imgCols, imgRows);

                try{
                    imageProcessingObject=(ImageInterface)Class.forName(theProcessingClass).newInstance();
                    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                            new ActionEvent(
                                    replotButton,
                                    ActionEvent.ACTION_PERFORMED, "Replot"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        this.setVisible(true);

        this.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        System.exit(0);
                    }
                }
        );

    } //end constructor

    class Display extends Canvas{
        //display both rawImg and modImg
        public void paint(Graphics g){
            if(tracker.statusID(1, false) == MediaTracker.COMPLETE){
                if((rawImg != null) && (modImg != null)){
                    g.drawImage(rawImg, 0,0,this);
                    g.drawImage(modImg, 0, imgRows+1, this);
                }
            }
        }
    }

    int[][][] convertToThreeDim(int[] dPix, int imgCols, int imgRows){
        int[][][] data= new int[imgRows][imgCols][4];

        for(int row=0; row<imgRows; row++){
            //extract row to remporary array
            int[] aRow=new int[imgCols];
            for(int col=0; col<imgCols; col++){
                int element=row*imgCols+col;
                aRow[col]=dPix[element];
            }

            //move data into the 3d array

            for(int col=0; col<imgCols; col++){

                //alpha data
                data[row][col][0]=(aRow[col] >> 24) & 0xFF;

                //red data
                data[row][col][1]=(aRow[col] >> 16) & 0xFF;

                //green data
                data[row][col][2]=(aRow[col] >> 8) & 0xFF;

                //blue data
                data[row][col][3]=(aRow[col]) & 0xFF;
            }
        }
        return data;
    } //end convert3dim

    int[] convertToOneDim(int[][][] data, int imgCols, int imgRows){

        int[] oneDPix= new int[imgCols*imgRows*4];

        for(int row=0, cnt=0; row<imgRows; row++){
            for(int col=0; col<imgCols; col++){
                oneDPix[cnt]=((data[row][col][0] << 24)
                & 0xFF000000)
                        | ((data[row][col][1] << 16)
                & 0x00FF0000)
                        | ((data[row][col][2] << 8)
                & 0x0000FF00)
                        | ((data[row][col][3])
                & 0x000000FF);
                cnt++;
            }
        }
        return oneDPix;
    } //end convert1Dim
}
