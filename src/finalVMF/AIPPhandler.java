package finalVMF;

import java.awt.*;
import java.awt.image.*;
import java.awt.MediaTracker;
import java.io.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;


public class AIPPhandler implements ItemListener,ActionListener
{
    int width;
    int height;
    int pix[];
    byte info[];
    int image_num;

    String temp;
    AIPP myframe;
    Image pic;
    Image picold;

    static int masksize;
    static float variance;
    static float edge_thresh;
    static double hsi1,hsi2,hsi3,hsi4,hsi5;
    static boolean ok;

    boolean custom;


    AIPPhandler(AIPP win)
    {
        myframe=win;
        masksize=3;
        image_num = 1;
        picold = myframe.image;
        pic = myframe.image;
        ok = false;
    }

    public void eventStart()
    {
        myframe.mainCanvas.setCursor(AIPP.waitCursor);
        myframe.open1m.setEnabled(false);
        myframe.open2m.setEnabled(false);
        myframe.open3m.setEnabled(false);
        myframe.savem.setEnabled(false);
        myframe.editM.setEnabled(false);
        myframe.filtM.setEnabled(false);
        myframe.analM.setEnabled(false);
        myframe.noiseM.setEnabled(false);
        AIPP.statusBar.setText("AIPP Working ...");
    }

    public void eventEnd(int type)
    {
        MediaTracker mt=new MediaTracker(myframe);
        mt.addImage(pic,0);
        try
        {
            mt.waitForAll();
        }
        catch (Exception ex)
        {
        }

        myframe.mainCanvas.setCursor(AIPP.defaultCursor);
        myframe.open1m.setEnabled(true);
        myframe.open2m.setEnabled(true);
        myframe.open3m.setEnabled(true);
        myframe.savem.setEnabled(true);
        myframe.editM.setEnabled(true);
        myframe.filtM.setEnabled(true);
        myframe.analM.setEnabled(true);
        myframe.noiseM.setEnabled(true);
        if (type == 1)
        {
            // event was successful
            // set old image and current image
            // rebuild current image array ro reflect changes
            picold=myframe.image;
            myframe.image=pic;
            myframe.getArray(pic);
        }
        System.gc();
    }

    public void actionPerformed(ActionEvent e)
    {
        pic=myframe.image;

        if (e.getSource() instanceof MenuItem)
        {
            String label=((MenuItem)e.getSource()).getLabel();
            if (label.equals("Open Image 1")
                    || label.equals("Open Image 2")
                    || label.equals("Open Image 3")
                    || label.equals("Revert to Original"))
            {
                eventStart();
                if (myframe.infom.getState() == true)
                {
                    myframe.infoBox = false;
                    myframe.infoPopup.dispose();
                    myframe.infom.setState(false);
                }

                if (label.equals("Open Image 1"))
                {
                    image_num = 1;
                }
                else if (label.equals("Open Image 2"))
                {
                    image_num = 2;
                }
                if (label.equals("Open Image 3"))
                {
                    image_num = 3;
                }

                if (image_num == 1)
                    pic=AIPPapplet.image1;
                else if (image_num == 2)
                    pic=AIPPapplet.image2;
                else if (image_num == 3)
                    pic=AIPPapplet.image3;

                eventEnd(1);
                AIPP.statusBar.setText("Opened image "+image_num);
            }
            else if (label.equals("Save"))
            {
                AIPP.statusBar.setText("Save function not supported in AIPP Applet");
            }
            else if (label.equals("Undo"))
            {
                pic=myframe.image;
                myframe.image=picold;
                picold=pic;
                AIPP.statusBar.setText("Undo Performed");
            }
            else if (label.equals("Exit"))
            {
                myframe.dispose();
            }

            else if (label.equals("Vector_Median"))
            {
                eventStart();
                VMF filter = new VMF(masksize);
                pic = myframe.createImage(new FilteredImageSource
                        (myframe.image.getSource(),filter));
                eventEnd(1);
                AIPP.statusBar.setText("Vector_Median Applied");
            }
            /*else if (label.equals("VR (Euclidean Distance)"))
            {
                eventStart();
                genDialog gd = new genDialog(myframe,2);
                gd.setVisible(true);
                if (ok)
                {
                    vr0_edge filter = new vr0_edge(edge_thresh,masksize);
                    pic = myframe.createImage(new FilteredImageSource
                            (myframe.image.getSource(),filter));
                    eventEnd(1);
                    AIPP.statusBar.setText("Edge Detection (VR-Euclidean) Completed");
                }
                else
                {
                    eventEnd(0);
                    AIPP.statusBar.setText("Operation Canceled");
                }
            }*/
            myframe.repaint();
        }
    }

    public void itemStateChanged(ItemEvent e)
    {
        if(e.getSource() instanceof CheckboxMenuItem)
        {
            String label=((CheckboxMenuItem)e.getSource()).getLabel();
            if (label.equals("3x3"))
            {
                myframe.mask5m.setState(false);
                myframe.mask7m.setState(false);
                myframe.mask3m.setState(true);
                masksize=3;
                AIPP.statusBar.setText("Mask Size set to 3x3");
            }
            else if (label.equals("5x5"))
            {
                myframe.mask3m.setState(false);
                myframe.mask7m.setState(false);
                myframe.mask5m.setState(true);
                masksize=5;
                AIPP.statusBar.setText("Mask Size set to 5x5");
            }
            else if (label.equals("7x7"))
            {
                myframe.mask3m.setState(false);
                myframe.mask5m.setState(false);
                myframe.mask7m.setState(true);
                masksize=7;
                AIPP.statusBar.setText("Mask Size set to 7x7");
            }
            else if (label.equals("Custom"))
            {
                custom = myframe.custm.getState();
                AIPP.statusBar.setText("Custom Mask Selected");
            }
            else if(label.equals("Show Image Info"))
            {
                if (((CheckboxMenuItem)e.getSource()).getState() == true)
                {
                    myframe.infoPopup = new InfoPopup(myframe);
                    myframe.infoPopup.setVisible(true);
                    myframe.infoBox = true;
                }
                else
                {
                    myframe.infoBox = false;
                    myframe.infoPopup.dispose();
                }
            }
        }

    }

}
