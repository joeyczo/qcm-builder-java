package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameListeQst extends JDialog
{

    private Controleur      ctrl;
    private PanelListeQst  panelListeQst;

    public FrameListeQst(JFrame parent, Controleur ctrl)
    {

        super(parent, "Liste des questions", true);

        this.ctrl = ctrl;
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.panelListeQst = new PanelListeQst(this, this.ctrl);
        this.add(this.panelListeQst);

        this.setVisible(true);
    }

    public void fermerFenetre() {
        this.dispose();
    }


}