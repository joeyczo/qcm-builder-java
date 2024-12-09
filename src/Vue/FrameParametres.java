package Vue;

import javax.swing.*;

public class FrameParametres extends JDialog
{

    private PanelParametre panelParametre;

    public FrameParametres(JFrame parent)
    {
        super(parent, "Paramètres", true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.panelParametre = new PanelParametre(this);
        this.add(this.panelParametre);

        this.setVisible(true);
    }

}