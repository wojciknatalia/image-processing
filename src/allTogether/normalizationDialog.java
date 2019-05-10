package allTogether;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class normalizationDialog{

    private static JDialog dialog;

    JLabel label1=new JLabel("In1");
    JLabel label2=new JLabel("Out1");
    JLabel label3=new JLabel("In2");
    JLabel label4=new JLabel("Out2");
    JLabel label5=new JLabel("In3");
    JLabel label6=new JLabel("Out3");

    static JTextField in1Field=new JTextField();
    static JTextField out1Field=new JTextField();
    static JTextField in2Field=new JTextField();
    static JTextField out2Field=new JTextField();
    static JTextField in3Field=new JTextField();
    static JTextField out3Field=new JTextField();

    static String[] data=new String[6];

    public normalizationDialog(){
        JFrame frame=new JFrame();
        dialog=new JDialog(frame, "Customize Normalization", true);
        init();
    }

    void init(){
        dialog.setTitle("Customize Normaliation");
        dialog.setLayout(new GridLayout(7,2));
        dialog.add(label1);
        dialog.add(in1Field);
        dialog.add(label2);
        dialog.add(out1Field);
        dialog.add(label3);
        dialog.add(in2Field);
        dialog.add(label4);
        dialog.add(out2Field);
        dialog.add(label5);
        dialog.add(in3Field);
        dialog.add(label6);
        dialog.add(out3Field);
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
        getData();

    }

    public static String[] getData(){
        data[0]=in1Field.getText();
        data[1]=out1Field.getText();
        data[2]=in2Field.getText();
        data[3]=out2Field.getText();
        data[4]=in3Field.getText();
        data[5]=out3Field.getText();
        return data;
    }

}
