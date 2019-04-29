package finalVMF;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.Color;

public class MouseHandler implements MouseListener,MouseMotionListener,ItemListener
{
    int infoType;
    float result[] = new float[3];
    AIPP myframe;
    Point curPos = new Point(0,0);

    MouseHandler(AIPP temp)
    {
        myframe = temp;
        infoType = 1;
    }

    public void itemStateChanged(ItemEvent e)
    {
        if(e.getSource() instanceof CheckboxMenuItem)
        {
            String label=((CheckboxMenuItem)e.getSource()).getLabel();
            if (label.equals("HSV"))
            {
                infoType = 1;
                myframe.labm.setState(false);
                myframe.yiqm.setState(false);
                myframe.hsvm.setState(true);
                myframe.statusBar.setText("HSV Info Selected");
            }
            else if (label.equals("CIEL*a*b*"))
            {
                infoType = 2;
                myframe.hsvm.setState(false);
                myframe.yiqm.setState(false);
                myframe.labm.setState(true);
                myframe.statusBar.setText("CIEL*a*b* Info Selected");
            }
            else if (label.equals("YIQ"))
            {
                infoType = 3;
                myframe.labm.setState(false);
                myframe.hsvm.setState(false);
                myframe.yiqm.setState(true);
                myframe.statusBar.setText("YIQ Info Selected");
            }
        }
    }

    private void RGBtoHSV(int r,int g,int b)
    {
        // From www.cs.rit.edu web site
        float rf,gf,bf;
        float min,max,delta;

        // scale to [0,1] range
        rf = 0.0039215f * r;	// 0..1
        gf = 0.0039215f * g;	// 0..1
        bf = 0.0039215f * b;	// 0..1
        min = Math.min(Math.min(rf,gf),bf);
        max = Math.max(Math.max(rf,gf),bf);
        delta = max-min;

        result[2] = max;

        if (max != 0.0)
        {
            result[1] = delta/max;
        }
        else
        {
            result[1] = 0;
            result[0] = -1;
        }

        if (rf == max)
            result[0] = (gf-bf)/delta;
        else if (g == max)
            result[0] = (bf-rf)/delta;
        else
            result[0] = (rf-gf)/delta;

        result[0] = (float)(result[0]*60.0f);
        if (result[0] < 0.0)
            result[0] = (float)(result[0]+360.0f);

        result[0] = (float)(result[0]/360.0f);
        format();
    }

    private void RGBtoLab(int r,int g,int b)
    {
        // From www.cs.rit.edu web site
        //[ R ]   [  3.240479 -1.537150 -0.498535 ]   [ X ]
        //[ G ] = [ -0.969256  1.875992  0.041556 ] * [ Y ]
        //[ B ]   [  0.055648 -0.204043  1.057311 ]   [ Z ]

        float rf,gf,bf,X,Y,Z;

        // scale to [0,1] range
        rf = 0.0039215f * r;	// 0..1
        gf = 0.0039215f * g;	// 0..1
        bf = 0.0039215f * b;	// 0..1

        X = 0.431f*rf + 0.342f*gf + 0.178f*bf;
        Y = 0.222f*rf + 0.707f*gf + 0.071f*bf;
        Z = 0.020f*rf + 0.130f*gf + 0.939f*bf;

        if( Y > 0.008856 )
            result[0] = (float)( 116f * Math.pow( Y, 0.3333333 ) - 16 );
        else
            result[0] = 903.3f * Y;

        result[1] = 500f * (float)( Math.pow( X, 0.3333333) - Math.pow( Y, 0.3333333) );
        result[2] = 200f * (float)( Math.pow( Y, 0.3333333) - Math.pow( Z, 0.3333333) );
        format();
    }

    private void RGBtoYIQ(int r,int g,int b)
    {
        // From www.cs.rit.edu web site
        //[ Y ]     [ 0.299   0.587   0.114 ] [ R ]
        //[ I ]  =  [ 0.596  -0.275  -0.321 ] [ G ]
        //[ Q ]     [ 0.212  -0.523   0.311 ] [ B ]

        result[0] = ((0.299f * r) + (0.587f * g) + (0.114f * b)) / 256;
        result[1] = ((0.596f * r) - (0.274f * g) - (0.322f * b)) / 256;
        result[2] = ((0.212f * r) - (0.523f * g) + (0.311f * b)) / 256;
        format();
    }

    private void format()
    {
        // round to two decimal places
        result[0] = Math.round(result[0]*100.0f)/100.0f;
        result[1] = Math.round(result[1]*100.0f)/100.0f;
        result[2] = Math.round(result[2]*100.0f)/100.0f;
    }

    public void mouseMoved (MouseEvent e)
    {
        int x1,y1,r,g,b;

        if (e.getSource() instanceof Canvas)
        {
            curPos.x = e.getX();
            curPos.y = e.getY();

            if (curPos.x < myframe.imageSize.width && curPos.y < myframe.imageSize.height && myframe.infoBox == true)
            {
                r=(int)(myframe.currentImageArray[curPos.x+curPos.y*myframe.imageSize.width]>>16)&0xff;
                g=(int)(myframe.currentImageArray[curPos.x+curPos.y*myframe.imageSize.width]>>8)&0xff;
                b=(int)(myframe.currentImageArray[curPos.x+curPos.y*myframe.imageSize.width])&0xff;

                myframe.infoPopup.x.setText("X: " + curPos.x);
                myframe.infoPopup.y.setText("Y: " + curPos.y);
                myframe.infoPopup.r.setText("R: " + r);
                myframe.infoPopup.g.setText("G: " + g);
                myframe.infoPopup.b.setText("B: " + b);

                if (infoType == 1)
                {
                    RGBtoHSV(r,g,b);
                    myframe.infoPopup.h.setText("H: " + result[0]);
                    myframe.infoPopup.s.setText("S: " + result[1]);
                    myframe.infoPopup.v.setText("V: " + result[2]);
                }
                else if (infoType == 2)
                {
                    RGBtoLab(r,g,b);
                    myframe.infoPopup.h.setText("L: " + result[0]);
                    myframe.infoPopup.s.setText("a: " + result[1]);
                    myframe.infoPopup.v.setText("b: " + result[2]);
                }
                else if (infoType == 3)
                {
                    RGBtoYIQ(r,g,b);
                    myframe.infoPopup.h.setText("Y: " + result[0]);
                    myframe.infoPopup.s.setText(" I: " + result[1]);
                    myframe.infoPopup.v.setText("Q: " + result[2]);
                }
            }
        }
    }

    public void mouseDragged(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void performEffect()
    {
    }
}