package Vue;

import javax.swing.*;

public class FrameCreerQts extends JDialog
{

    public FrameCreerQts(JFrame parent)
    {

        super(parent, "Créer question", true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.setVisible(true);

    }

}