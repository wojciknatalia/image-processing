package finalVMF;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.applet.*;

public class AIPP extends Frame
{
    static int framew = 300;
    static int frameh = 300;
    static Cursor defaultCursor;
    static Cursor waitCursor;

    static AIPP AIPPframe;
    static TextField statusBar;

    public AIPPCanvas mainCanvas;
    public AIPPhandler action;

    public InfoPopup infoPopup;

    public MouseHandler action2;
    public Image image;
    public MenuBar mbar;
    public Menu fileM;
    public Menu editM;
    public Menu filtM;
    public Menu analM;
    public Menu noiseM;
    public MenuItem open1m;
    public MenuItem open2m;
    public MenuItem open3m;
    public MenuItem savem;
    public CheckboxMenuItem infom;
    public CheckboxMenuItem mask3m;
    public CheckboxMenuItem mask5m;
    public CheckboxMenuItem mask7m;
    public CheckboxMenuItem custm;
    public CheckboxMenuItem hsvm;
    public CheckboxMenuItem labm;
    public CheckboxMenuItem yiqm;
    public boolean infoBox;

    public Dimension imageSize;
    public int currentImageArray[];

    private	ImageObserver currentImageObserver;
    private	MediaTracker currentImageTracker = new MediaTracker(this);

    AIPP()
    {
        super("AIPP Applet");
        defaultCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        waitCursor = new Cursor(Cursor.WAIT_CURSOR);
        infoBox = false;
        setBackground(Color.lightGray);
        setSize(400,400);
        setTitle("AIPP Applet");
        image = AIPPapplet.image1;
        mainCanvas = new AIPPCanvas(this);
        mainCanvas.setCursor(defaultCursor);
        getArray(image);

        action = new AIPPhandler(this);
        action2 = new MouseHandler(this);
        mainCanvas.addMouseMotionListener(action2);

        mbar = new MenuBar();
        statusBar = new TextField();
        statusBar.setEditable(false);
        statusBar.setBackground(Color.lightGray);
        setLayout(new BorderLayout());

        fileM = new Menu("File");
        open1m = new MenuItem("Open Image 1");
        open1m.addActionListener(action);
        fileM.add(open1m);
        open2m = new MenuItem("Open Image 2");
        open2m.addActionListener(action);
        fileM.add(open2m);
        open3m = new MenuItem("Open Image 3");
        open3m.addActionListener(action);
        fileM.add(open3m);
        savem = new MenuItem("Save");
        savem.addActionListener(action);
        fileM.add(savem);
        MenuItem sep1m = new MenuItem("-");
        fileM.add(sep1m);
        MenuItem exitm = new MenuItem("Exit");
        exitm.addActionListener(action);
        fileM.add(exitm);
        editM = new Menu("Edit");
        MenuItem origm = new MenuItem("Revert to Original");
        origm.addActionListener(action);
        editM.add(origm);
        MenuItem undom = new MenuItem("Undo");
        undom.addActionListener(action);
        editM.add(undom);
        MenuItem sepm = new MenuItem("-");
        editM.add(sepm);
        Menu maskm = new Menu("Select Mask Size");
        mask3m = new CheckboxMenuItem("3x3",true);
        mask3m.addItemListener(action);
        maskm.add(mask3m);
        mask5m = new CheckboxMenuItem("5x5",false);
        mask5m.addItemListener(action);
        maskm.add(mask5m);
        mask7m = new CheckboxMenuItem("7x7",false);
        mask7m.addItemListener(action);
        maskm.add(mask7m);
        MenuItem sep2m = new MenuItem("-");
        maskm.add(sep2m);
        custm = new CheckboxMenuItem("Custom",false);
        custm.addItemListener(action);
        maskm.add(custm);
        editM.add(maskm);
        Menu typem = new Menu("Select Info Type",false);
        hsvm = new CheckboxMenuItem("HSV",true);
        hsvm.addItemListener(action2);
        typem.add(hsvm);
        yiqm = new CheckboxMenuItem("YIQ",false);
        yiqm.addItemListener(action2);
        typem.add(yiqm);
        labm = new CheckboxMenuItem("CIEL*a*b*",false);
        labm.addItemListener(action2);
        typem.add(labm);
        editM.add(typem);
        infom = new CheckboxMenuItem("Show Image Info",false);
        infom.addItemListener(action);
        editM.add(infom);
        filtM = new Menu("Filters");
        MenuItem embm = new MenuItem("Emboss");
        embm.addActionListener(action);
        filtM.add(embm);
        MenuItem greym = new MenuItem("Grey");
        greym.addActionListener(action);
        filtM.add(greym);
        MenuItem negm = new MenuItem("Negative");
        negm.addActionListener(action);
        filtM.add(negm);
        MenuItem blurm = new MenuItem("Blur");
        blurm.addActionListener(action);
        filtM.add(blurm);
        MenuItem sharpm = new MenuItem("Sharpen");
        sharpm.addActionListener(action);
        filtM.add(sharpm);
        Menu dilateM = new Menu("Dilate");
        MenuItem dilatem = new MenuItem("D_Plus");
        dilatem.addActionListener(action);
        dilateM.add(dilatem);
        MenuItem dilate2m = new MenuItem("D_Square");
        dilate2m.addActionListener(action);
        dilateM.add(dilate2m);
        MenuItem dilate3m = new MenuItem("D_X");
        dilate3m.addActionListener(action);
        dilateM.add(dilate3m);
        MenuItem dilate4m = new MenuItem("D_Circle");
        dilate4m.addActionListener(action);
        dilateM.add(dilate4m);
        filtM.add(dilateM);
        Menu erodeM = new Menu("Erode");
        MenuItem erodem = new MenuItem("E_Plus");
        erodem.addActionListener(action);
        erodeM.add(erodem);
        MenuItem erode2m = new MenuItem("E_Square");
        erode2m.addActionListener(action);
        erodeM.add(erode2m);
        MenuItem erode3m = new MenuItem("E_X");
        erode3m.addActionListener(action);
        erodeM.add(erode3m);
        MenuItem erode4m = new MenuItem("E_Circle");
        erode4m.addActionListener(action);
        erodeM.add(erode4m);
        filtM.add(erodeM);
        Menu vectorM = new Menu("Vector");
        MenuItem vector1m = new MenuItem("Arithmatic_Mean");
        vector1m.addActionListener(action);
        vectorM.add(vector1m);
        MenuItem vector2m = new MenuItem("Vector_Median");
        vector2m.addActionListener(action);
        vectorM.add(vector2m);
        MenuItem vector3m = new MenuItem("Basic_Vector_Directional");
        vector3m.addActionListener(action);
        vectorM.add(vector3m);
        MenuItem vector4m = new MenuItem("Generalized_Vector_Directional");
        vector4m.addActionListener(action);
        vectorM.add(vector4m);
        MenuItem vector5m = new MenuItem("Directional_Distance");
        vector5m.addActionListener(action);
        vectorM.add(vector5m);
        MenuItem vector6m = new MenuItem("Hybrid_Directional");
        vector6m.addActionListener(action);
        vectorM.add(vector6m);
        MenuItem vector7m = new MenuItem("Adaptive_Hybrid_Directional");
        vector7m.addActionListener(action);
        vectorM.add(vector7m);
        MenuItem vector8m = new MenuItem("Fuzzy_Vector_Directional");
        vector8m.addActionListener(action);
        vectorM.add(vector8m);
        MenuItem vector9m = new MenuItem("Adaptive_Nearest_Neighbor");
        vector9m.addActionListener(action);
        vectorM.add(vector9m);
        MenuItem vector10m = new MenuItem("Adaptive_Nearest_Neighbor(Canberra)");
        vector10m.addActionListener(action);
        vectorM.add(vector10m);
        MenuItem vector11m = new MenuItem("Adaptive_Nearest_Neighbor(Goude)");
        vector11m.addActionListener(action);
        vectorM.add(vector11m);
        MenuItem vector12m = new MenuItem("Adaptive_NonParametric(Exponential)");
        vector12m.addActionListener(action);
        vectorM.add(vector12m);
        MenuItem vector13m = new MenuItem("Adaptive_NonParametric(Gaussian)");
        vector13m.addActionListener(action);
        vectorM.add(vector13m);
        MenuItem vector14m = new MenuItem("Adaptive_NonParametric(Directional)");
        vector14m.addActionListener(action);
        vectorM.add(vector14m);
        MenuItem vector15m = new MenuItem("Bayesian Adaptive(Median/Mean)");
        vector15m.addActionListener(action);
        vectorM.add(vector15m);
        filtM.add(vectorM);
        analM = new Menu("Analysis");
        MenuItem hsim = new MenuItem("HSI Segmentation");
        hsim.addActionListener(action);
        analM.add(hsim);
        Menu edgem = new Menu("Edge Detection");
        MenuItem edge1m = new MenuItem("Mean");
        edge1m.addActionListener(action);
        edgem.add(edge1m);
        MenuItem edge2m = new MenuItem("Median");
        edge2m.addActionListener(action);
        edgem.add(edge2m);
        MenuItem edge3m = new MenuItem("VR (Euclidean Distance)");
        edge3m.addActionListener(action);
        edgem.add(edge3m);
        MenuItem edge4m = new MenuItem("VR (Angle Distance)");
        edge4m.addActionListener(action);
        edgem.add(edge4m);
        MenuItem edge5m = new MenuItem("Adaptive");
        edge5m.addActionListener(action);
        edgem.add(edge5m);
        analM.add(edgem);
        Menu channelM = new Menu("Channel");
        MenuItem bluem = new MenuItem("Blue");
        bluem.addActionListener(action);
        channelM.add(bluem);
        MenuItem greenm = new MenuItem("Green");
        greenm.addActionListener(action);
        channelM.add(greenm);
        MenuItem redm = new MenuItem("Red");
        redm.addActionListener(action);
        channelM.add(redm);
        analM.add(channelM);
        noiseM = new Menu("Noise");
        MenuItem noise1m = new MenuItem("Exponential");
        noise1m.addActionListener(action);
        noiseM.add(noise1m);
        MenuItem noise2m = new MenuItem("Gaussian");
        noise2m.addActionListener(action);
        noiseM.add(noise2m);
        MenuItem noise3m = new MenuItem("Impulsive");
        noise3m.addActionListener(action);
        noiseM.add(noise3m);
        MenuItem noise4m = new MenuItem("Poisson");
        noise4m.addActionListener(action);
        noiseM.add(noise4m);
        MenuItem noise5m = new MenuItem("Uniform");
        noise5m.addActionListener(action);
        noiseM.add(noise5m);
        MenuItem noise6m = new MenuItem("White Impulsive");
        noise6m.addActionListener(action);
        noiseM.add(noise6m);
        mbar.add(fileM);
        mbar.add(editM);
        mbar.add(filtM);
        mbar.add(analM);
        mbar.add(noiseM);
        setMenuBar(mbar);
        add(mainCanvas,"Center");
        add(statusBar,"South");
        statusBar.setText("AIPP Applet Started");
        mainCanvas.setVisible(true);
        statusBar.setVisible(true);
        repaint();
    }

    public void getArray(Image pic)
    {
        currentImageTracker.addImage(pic, 0);
        try
        {
            // Make sure entire image is loaded using the MediaTracker
            currentImageTracker.waitForAll();
        }
        catch(InterruptedException IE)
        {
        }

        // Determine image dimensions and store in 'currentImageSize'
        imageSize = new Dimension(pic.getWidth(currentImageObserver),
                pic.getHeight(currentImageObserver));

        currentImageArray = new int[imageSize.width*imageSize.height];

        try
        {
            PixelGrabber pg = new PixelGrabber(pic,0,0,imageSize.width,imageSize.height,currentImageArray,0,imageSize.width);
            if (pg.grabPixels() && ((pg.status() & ImageObserver.ALLBITS) != 0))
            {
            }
            else
            {
            }
        }
        catch(InterruptedException ie)
        {
        }
    }

    public void getArray(Image pic, int w, int h)
    {
        // Determine image dimensions and store in 'currentImageSize'
        imageSize = new Dimension(w,h);
        currentImageArray = new int[imageSize.width*imageSize.height];

        try
        {
            PixelGrabber pg = new PixelGrabber(pic,0,0,imageSize.width,imageSize.height,currentImageArray,0,imageSize.width);
            if (pg.grabPixels() && ((pg.status() & ImageObserver.ALLBITS) != 0))
            {
            }
            else
            {
            }
        }
        catch(InterruptedException ie)
        {
        }
    }

    public void paint(Graphics g)
    {
        int height = image.getHeight(this)+statusBar.getBounds().height+81;

        if (image.getWidth(this) > framew)
            setSize(image.getWidth(this),height);
        else
            setSize(framew,height);

        mainCanvas.repaint();
    }

    public void update(Graphics g)
    {
        paint(g);
    }
}