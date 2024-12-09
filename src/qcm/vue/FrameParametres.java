package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameParametres extends JDialog
{

    private Controleur      ctrl;
    private PanelParametre  panelParametre;

    public FrameParametres(JFrame parent, Controleur ctrl)
    {

        super(parent, "Paramètres", true);

        this.ctrl = ctrl;
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.panelParametre = new PanelParametre(this, this.ctrl);
        this.add(this.panelParametre);

        this.setVisible(true);
    }

}