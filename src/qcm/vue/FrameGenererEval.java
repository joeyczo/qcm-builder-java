package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameGenererEval extends JDialog{

    private PanelGenererEval pnlEval;
    private Controleur       ctrl;

    public FrameGenererEval(JFrame parent, Controleur ctrl) {

        super(parent, "Générer évaluation", true);

        this.ctrl = ctrl;

        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.pnlEval = new PanelGenererEval(this,ctrl);
        this.add(pnlEval);


        this.setVisible(true);

    }

}