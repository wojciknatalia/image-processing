package contrastToBe;

import java.awt.*;

public class ProgramTest extends Frame implements ImageInterface {

    double slope;
    String inputData;
    TextField inputField;

    ProgramTest(){
        setLayout(new FlowLayout());

        Label instructions=new Label("Type a slope value");
        add(instructions);

        inputField=new TextField("1.0", 5);
        add(inputField);

        setBounds(400,0,200,100);
        setVisible(true);
    }

    public int[][][] processImg(int[][][] dimensionsPx,
                         int imgRows,
                         int imgCols){

        int[][][] temp3D=new int[imgRows][imgCols][4];
        for (int row=0; row<imgRows; row++){
            for(int col=0; col<imgCols; col++){
                temp3D[row][col][0]=dimensionsPx[row][col][0];
                temp3D[row][col][1]=dimensionsPx[row][col][1];
                temp3D[row][col][2]=dimensionsPx[row][col][2];
                temp3D[row][col][3]=dimensionsPx[row][col][3];
            }
        }

        slope=Double.parseDouble(inputField.getText());

        for(int col=0; col<imgCols; col++){
            int row=(int)(slope*col);
            if(row>imgRows-1) break;

            temp3D[row][col][0]=(byte)0xff;
            temp3D[row][col][1]=(byte)0xff;
            temp3D[row][col][2]=(byte)0xff;
            temp3D[row][col][3]=(byte)0xff;
        }

        return temp3D;
    }
}
