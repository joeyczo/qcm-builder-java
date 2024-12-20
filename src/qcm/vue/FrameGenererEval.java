package qcm.vue;

import qcm.Controleur;
import qcm.metier.DonneesCreationQuestion;

import javax.swing.*;

public class FrameGenererEval extends JDialog{

    private PanelGenererEval        pnlEval;
    private Controleur              ctrl;

    public FrameGenererEval(JFrame parent, Controleur ctrl) {

        super(parent, "Générer évaluation", true);

        this.ctrl = ctrl;

        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);

        this.pnlEval = new PanelGenererEval(this,ctrl);
        this.add(pnlEval);


        this.setVisible(true);

    }

    public void fermerFenetre() {
        this.dispose();
    }

}