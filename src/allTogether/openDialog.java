package allTogether;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class openDialog {

    static int[] mask;
    static int maskSize;

    int mask0size3[]={
            0,0,0,
            1,1,1,
            0,0,0,
    };

    int mask0size5[]={
            0,0,0,0,0,
            0,0,0,0,0,
            1,1,1,1,1,
            0,0,0,0,0,
            0,0,0,0,0,
    };

    int mask0size9[]={
            0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,
            1,1,1,1,1,1,1,1,1,
            0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,
    };

    int mask45size3[]={
            0,0,1,
            0,1,0,
            1,0,0,
    };

    int mask45size5[]={
            0,0,0,0,1,
            0,0,0,1,0,
            0,0,1,0,0,
            0,1,0,0,0,
            1,0,0,0,0,
    };

    int mask45size9[]={
            0,0,0,0,0,0,0,0,1,
            0,0,0,0,0,0,0,1,0,
            0,0,0,0,0,0,1,0,0,
            0,0,0,0,0,1,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,1,0,0,0,0,0,
            0,0,1,0,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,
            1,0,0,0,0,0,0,0,0,
    };

    int mask90size3[]={
            0,1,0,
            0,1,0,
            0,1,0,
    };

    int mask90size5[]={
            0,0,1,0,0,
            0,0,1,0,0,
            0,0,1,0,0,
            0,0,1,0,0,
            0,0,1,0,0,
    };

    int mask90size9[]={
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
    };

    int mask135size3[]={
            1,0,0,
            0,1,0,
            0,0,1,
    };

    int mask135size5[]={
            1,0,0,0,0,
            0,1,0,0,0,
            0,0,1,0,0,
            0,0,0,1,0,
            0,0,0,0,1,
    };

    int mask135size9[]={
            1,0,0,0,0,0,0,0,0,
            0,1,0,0,0,0,0,0,0,
            0,0,1,0,0,0,0,0,0,
            0,0,0,1,0,0,0,0,0,
            0,0,0,0,1,0,0,0,0,
            0,0,0,0,0,1,0,0,0,
            0,0,0,0,0,0,1,0,0,
            0,0,0,0,0,0,0,1,0,
            0,0,0,0,0,0,0,0,1,
    };

    openDialog() {
        String[] choices = { "0°, size 3", "0°, size 5", "0°, size 9", "45°, size 3", "45°, size 5", "45°, size 9", "90°, size 3", "90°, size 5", "90°, size 9", "135°, size 3", "135°, size 5", "135°, size 9" };
        String input = (String) JOptionPane.showInputDialog(null, "Choose angle and masksize",
                "Customize Opening", JOptionPane.QUESTION_MESSAGE, null, // Use default icon
                choices, // Array of choices
                choices[0]); // Initial choice
        switch (input){
            case "0°, size 3":
                maskSize=3;
                mask=mask0size3;
                break;
            case "0°, size 5":
                maskSize=5;
                mask=mask0size5;
                break;
            case "0°, size 9":
                maskSize=9;
                mask=mask0size9;
                break;
            case "45°, size 3":
                maskSize=3;
                mask=mask45size3;
                break;
            case "45°, size 5":
                maskSize=5;
                mask=mask45size5;
                break;
            case "45°, size 9":
                maskSize=9;
                mask=mask45size9;
                break;
            case "90°, size 3":
                maskSize=3;
                mask=mask90size3;
                break;
            case "90°, size 5":
                maskSize=5;
                mask=mask90size5;
                break;
            case "90°, size 9":
                maskSize=9;
                mask=mask90size9;
                break;
            case "135°, size 3":
                maskSize=3;
                mask=mask135size3;
                break;
            case "135°, size 5":
                maskSize=5;
                mask=mask135size5;
                break;
            case "135°, size 9":
                maskSize=9;
                mask=mask135size9;
                break;
        }
    }

    static int getMaskSize(){
        return maskSize;
    }

    static int[] getMask(){
        return mask;
    }
}
