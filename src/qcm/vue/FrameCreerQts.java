package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameCreerQts extends JDialog
{
    private PanelCreerQts pnlQuestions;
    private Controleur    ctrl;

    public FrameCreerQts(JFrame parent, Controleur ctrl)
    {
        super(parent, "Créer question", true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.ctrl = ctrl;

        this.pnlQuestions = new PanelCreerQts(this, ctrl);

        this.add(this.pnlQuestions);

        this.setVisible(true);

    }

}