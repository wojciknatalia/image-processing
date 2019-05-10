package allTogether;

import imopen.LineStrel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class openDialog {

    private static JDialog dialog;
    static int[] mask;
    static int maskSize;
    static LineStrel strel;

    JLabel label1=new JLabel("Mask size");
    JLabel label2=new JLabel("Angle");

    static JTextField in1Field=new JTextField();
    static JTextField in2Field=new JTextField();

    public openDialog(){
        JFrame frame=new JFrame();
        dialog=new JDialog(frame, "Customize Opening", true);
        init();
    }

    void init(){
        dialog.setTitle("Customize Opening");
        dialog.setLayout(new GridLayout(3,2));
        dialog.add(label1);
        dialog.add(in1Field);
        dialog.add(label2);
        dialog.add(in2Field);
        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        dialog.add(new JLabel("Confirm"));
        dialog.add(button);
        dialog.setSize(300,300);
        dialog.setVisible(true);
        generateData();

    }

    public void generateData(){
        strel=new LineStrel(Integer.parseInt(in1Field.getText()), Integer.parseInt(in2Field.getText())); //len, deg
        int[][] mask2D=strel.getMask();
        mask=strel.strelTo1D(mask2D);
        maskSize=strel.getMaskSize(mask);
    }

    static int getMaskSize(){
        return maskSize;
    }

    static int[] getMask(){
        return mask;
    }
}
