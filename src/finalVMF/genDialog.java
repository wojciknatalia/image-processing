package finalVMF;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;

public class genDialog extends Dialog implements ActionListener
{
    static int NOISE = 1;
    static int EDGE = 2;
    static int HSI = 3;
    static int MASK = 4;
    int type;
    int mask[];
    TextField input[];

    // my parseDouble routine, since Netscape does not
    // recognize Double.parseDouble(String) method form Java1.2
    private double parse(String temp)
    {
        try
        {
            Double temp3 = new Double(temp);
            double temp2 = temp3.doubleValue();
            return temp2;
        }
        catch(NumberFormatException ne)
        {
            AIPP.statusBar.setText("Incorrect Input");
            return 0.0;
        }
    }

    genDialog(Frame parent, int val)
    {
        super(parent,"Set Parameters",true);
        Panel p1 = new Panel();
        Panel p2 = new Panel();
        Button done = new Button("Ok");
        Button can = new Button("Cancel");
        type = val;

        if (type == NOISE)
        {
            TextField title = new TextField();
            title.setBackground(Color.lightGray);
            title.setEditable(false);
            input = new TextField[1];
            input[0] = new TextField();
            input[0].setBackground(Color.white);
            input[0].setEditable(true);
            title.setText("Noise Variance: (0-100%)");
            input[0].setText("5.0");
            p1.setLayout(new GridLayout(2,1));
            p1.add(title);
            p1.add(input[0]);
        }
        else if (type == EDGE)
        {
            TextField title = new TextField();
            title.setBackground(Color.lightGray);
            title.setEditable(false);
            input = new TextField[1];
            input[0] = new TextField();
            input[0].setBackground(Color.white);
            input[0].setEditable(true);
            title.setText("Threshold Level: (0-0.25)");
            input[0].setText("0.01");
            p1.setLayout(new GridLayout(2,1));
            p1.add(title);
            p1.add(input[0]);
        }
        else if (type == HSI)
        {
            TextField title[] = new TextField[5];
            input = new TextField[5];

            for (int i=0;i<5;i++)
            {
                input[i] = new TextField();
                title[i] = new TextField();
                input[i].setEditable(true);
                input[i].setBackground(Color.white);
                title[i].setBackground(Color.lightGray);
                title[i].setEditable(false);
            }

            title[0].setText("Chromatic Difference");
            input[0].setText("15.0");
            title[1].setText("Achromatic Difference");
            input[1].setText("15.0");
            title[2].setText("Saturation Level");
            input[2].setText("10.0");
            title[3].setText("Low Intensity Level");
            input[3].setText("10.0");
            title[4].setText("High Intensity Level");
            input[4].setText("90.0");
            p1.setLayout(new GridLayout(5,2));

            for (int j=0;j<5;j++)
            {
                p1.add(title[j]);
                p1.add(input[j]);
            }
        }

        p2.setLayout(new GridLayout(1,2));
        p2.add(done);
        p2.add(can);

        this.setLayout(new BorderLayout());
        add("North",p1);
        add("South",p2);

        done.addActionListener(this);
        can.addActionListener(this);
        if (type == HSI)
        {
            setSize(300,200);
        }
        else
        {
            setSize(200,120);
        }

        setResizable(false);
        setLocation((int)parent.getLocation().x+50,(int)parent.getLocation().y+50);
    }

    genDialog(Frame parent, int mask_input[])
    {
        super(parent,"Set Parameters",true);
        mask = mask_input;
        type = MASK;
        Panel p1 = new Panel();
        Panel p2 = new Panel();
        Button done = new Button("Ok");
        Button can = new Button("Cancel");

        p1.setLayout(new GridLayout(AIPPhandler.masksize,AIPPhandler.masksize));
        input = new TextField[AIPPhandler.masksize*AIPPhandler.masksize];

        for (int i=0;i<AIPPhandler.masksize*AIPPhandler.masksize;i++)
        {
            input[i] = new TextField();
            input[i].setBackground(Color.white);
            input[i].setEditable(true);
            input[i].setText("1");
            p1.add(input[i]);
        }

        p2.setLayout(new GridLayout(1,2));
        p2.add(done);
        p2.add(can);

        this.setLayout(new BorderLayout());
        add("North",p1);
        add("South",p2);

        done.addActionListener(this);
        can.addActionListener(this);
        setSize(AIPPhandler.masksize*37,AIPPhandler.masksize*35+40);

        setResizable(false);
        setLocation((int)parent.getLocation().x+50,(int)parent.getLocation().y+50);
        AIPP.statusBar.setText("Enter Custom Mask Values (int)");
    }

    public void actionPerformed(ActionEvent e)
    {
        if (((Button)e.getSource()).getLabel().equals("Ok"))
        {
            float temp = -1.0f;

            temp = (float)parse(input[0].getText());

            if (type == NOISE && (temp >= 0.0f && temp <= 100.0f))
            {
                AIPPhandler.variance = temp;
                AIPPhandler.ok = true;
                dispose();
            }
            if (type == EDGE && (temp >= 0.0f && temp <= 0.25f))
            {
                AIPPhandler.edge_thresh = temp;
                AIPPhandler.ok = true;
                dispose();
            }
            else if (type == HSI)
            {
                double temp2[] = new double[5];
                for(int j=0;j<5;j++)
                {
                    temp2[j] = -1.0;
                    temp2[j] = parse(input[j].getText());
                }

                if (temp2[0] > 0.0 && temp2[1] > 0.0 && temp2[2] > 0.0
                        && temp2[3] > 0.0 && temp2[4] > temp2[3])
                {
                    AIPPhandler.hsi1 = temp2[0];
                    AIPPhandler.hsi2 = temp2[1];
                    AIPPhandler.hsi3 = temp2[2];
                    AIPPhandler.hsi4 = temp2[3];
                    AIPPhandler.hsi5 = temp2[4];
                    AIPPhandler.ok = true;
                    dispose();
                }
                else
                {
                    AIPP.statusBar.setText("Incorrect Input");
                }
            }
            else if (type == MASK)
            {
                for(int j=0;j<AIPPhandler.masksize*AIPPhandler.masksize;j++)
                {
                    try
                    {
                        mask[j] = Integer.parseInt(input[j].getText());
                    }
                    catch(NumberFormatException ne)
                    {
                        AIPP.statusBar.setText("Incorrect Input");
                    }
                }

                AIPPhandler.ok = true;
                dispose();
            }
        }
        else if (((Button)e.getSource()).getLabel().equals("Cancel"))
        {
            AIPPhandler.ok = false;
            dispose();
        }
    }
}
