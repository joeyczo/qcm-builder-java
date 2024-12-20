package qcm.vue;

import qcm.Controleur;
import qcm.metier.Evalutation;

import javax.swing.*;

public class FrameVisuEval extends JFrame {

    public FrameVisuEval(Controleur ctrl, Evalutation eval) {

        this.setTitle("Visualisation avant exportation");
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        PanelVisuEval panelVisuEval = new PanelVisuEval(this, ctrl, eval);
        JScrollPane jspPanelVisu = new JScrollPane(panelVisuEval);
        this.add(jspPanelVisu);

        this.setVisible(true);

    }

    public void fermerFenetre() {
        this.dispose();
    }

}
