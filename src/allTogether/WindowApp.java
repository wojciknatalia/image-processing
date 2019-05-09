package allTogether;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import allTogether.Gui;

public class WindowApp extends JFrame {
    Gui gui;
    JButton imadjust, imfill, imopen, VMF;
    Graphics g;

    public WindowApp() throws IOException{
        super();

        Container container=getContentPane();
        gui=new Gui();
        container.add(gui);

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(2,2));
        panel.setBorder(new TitledBorder("Click a Button to Perform Operation"));

        imadjust=new JButton("Imadjust");
        imadjust.addActionListener(new ButtonListener());

        imfill=new JButton("Imfill");
        imfill.addActionListener(new ButtonListener());

        imopen=new JButton("Imopen");
        imopen.addActionListener(new ButtonListener());

        VMF=new JButton("VMF");
        VMF.addActionListener(new ButtonListener());

        panel.add(imadjust);
        panel.add(imfill);
        panel.add(imopen);
        panel.add(VMF);

        container.add(BorderLayout.SOUTH, panel);
        addWindowListener(new WindowEventHandler());
        setSize(gui.sourceImage.getWidth()*2, gui.sourceImage.getHeight()+120);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //show();
    }

    class WindowEventHandler extends WindowAdapter{
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JButton temp=(JButton)e.getSource();

            if(temp.equals(imadjust)){
                normalizationDialog normDial=new normalizationDialog();
                gui.ifImadjust=true;
                gui.repaint();
            }
            else if(temp.equals(imfill)){
                gui.ifImfill=true;
                gui.repaint();
            }
            else if(temp.equals(imopen)){
                new openDialog();
                gui.ifImopen=true;
                gui.repaint();
            }
            else if(temp.equals(VMF)){
                new vmfDialog();
                gui.ifVMF=true;
                gui.repaint();
            }

        }
    }

    public static void main(String[] args) throws IOException{
        new WindowApp();
    }
}
