package finalVMF;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;


public class AIPPCanvas extends Canvas
{
    AIPP myframe;

    AIPPCanvas(AIPP temp)
    {
        myframe = temp;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paint(Graphics g)
    {
        setSize(myframe.image.getWidth(this),myframe.image.getHeight(this));
        validate();
        g.drawImage(myframe.image,0,0,this);
    }
}