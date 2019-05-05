package imfill;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class performEffect extends JPanel{

    public static void main(String[] args) throws IOException {

        JFrame mFrame = new JFrame();
        mFrame.setVisible(true);
        mFrame.setSize(800, 500);
        mFrame.add(new performEffect());
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    @Override

    public void paint(Graphics g) {
        super.paint(g);
        imfill myImfill = new imfill("C:\\Users\\asd\\Desktop\\untitled\\coins.jpg", g);
    }
}
