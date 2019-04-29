package finalVMF;

import java.awt.*;
import java.awt.event.*;

public class InfoPopup extends Dialog {

    public Label x= new Label("X:     ");
    public Label y= new Label("Y:     ");
    public Label r= new Label("R:     ");
    public Label g= new Label("G:     ");
    public Label b= new Label("B:     ");
    public Label h= new Label("H:          ");
    public Label s= new Label("S:          ");
    public Label v= new Label("V:          ");
    private Label blank= new Label("");


    InfoPopup(Frame parent)
    {
        super(parent,"Image Info",false);
        setFont(new Font("Helvetica", Font.BOLD, 10));
        Panel p1 = new Panel();
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.white);
        p1.add("North",x);
        p1.add("South",y);

        Panel p2 = new Panel();
        p2.setLayout(new BorderLayout());
        r.setBackground(Color.red);
        g.setBackground(Color.green);
        b.setBackground(Color.blue);
        r.setForeground(Color.white);
        g.setForeground(Color.black);
        b.setForeground(Color.white);
        p2.add("North",r);
        p2.add("Center",g);
        p2.add("South",b);
        Color prp=new Color(160,160,255);

        Panel p3 = new Panel();
        p3.setLayout(new BorderLayout());
        p3.setBackground(prp);
        p3.setForeground(Color.white);
        p3.add("North",h);
        p3.add("Center",s);
        p3.add("South",v);

        setLayout(new FlowLayout());
        add(p1);
        add(p2);
        add(p3);

        setResizable(false);
        setSize(200,100);
        setTitle("Image Info");
        setLocation((int)parent.getLocation().x+50,(int)parent.getLocation().y+50);
    }
}

