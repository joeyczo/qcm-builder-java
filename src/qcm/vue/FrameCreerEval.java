package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameCreerEval extends JDialog{
    private PanelEval pnlEval;
    private Controleur ctrl;
    public FrameCreerEval(JFrame parent, Controleur ctrl){
        super(parent, "Créer éval", true);
        this.setSize(800, 600);
        this.ctrl = ctrl;
        this.pnlEval = new PanelEval(this,ctrl);
        this.setLocationRelativeTo(null);
        this.add(pnlEval);
        this.setVisible(true);
    }
}