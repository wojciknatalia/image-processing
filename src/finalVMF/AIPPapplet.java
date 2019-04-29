package finalVMF;

import java.applet.*;
import java.awt.*;

public class AIPPapplet extends Applet
{
    public static Image image1;
    public static Image image2;
    public static Image image3;
    AIPP AIPPframe;

    public void init()
    {
        image1 = getImage(getCodeBase(),"meadow.jpg");
        image2 = getImage(getCodeBase(),"meadow.jpg");
        image3 = getImage(getCodeBase(),"meadow.jpg");
        AIPPframe = new AIPP();
        AIPPframe.setLocation(100,100);
        AIPPframe.setVisible(true);
    }

    public void paint()
    {
        AIPPframe.repaint();
    }

}