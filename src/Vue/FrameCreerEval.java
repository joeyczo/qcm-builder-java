package Vue;

import javax.swing.*;

public class FrameCreerEval extends JDialog{

    public FrameCreerEval(JFrame parent){
        super(parent, "Créer éval", true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}