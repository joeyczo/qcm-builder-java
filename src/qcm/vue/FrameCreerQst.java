package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameCreerQst extends JDialog
{
    private PanelCreerQst pnlQuestions;
    private Controleur    ctrl;

    public FrameCreerQst(JFrame parent, Controleur ctrl)
    {
        super(parent, "Créer question", true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.ctrl = ctrl;

        this.pnlQuestions = new PanelCreerQst(this, ctrl);

        this.add(this.pnlQuestions);

        this.setVisible(true);

    }

    public void fermerFenetre() {
        this.dispose();
    }

}