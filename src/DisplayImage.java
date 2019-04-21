import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DisplayImage{

    public DisplayImage(String imgPath) throws IOException{
        BufferedImage img= ImageIO.read(new File(imgPath));
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame("Image processing");
        JLabel label=new JLabel();
        label.setIcon(icon);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
