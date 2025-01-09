package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameGenererEval extends JDialog {

    public FrameGenererEval(JFrame parent, Controleur ctrl) {

        super(parent, "Générer évaluation", true);

        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        ImageIcon img = new ImageIcon("../data/img/icon.png");
        this.setIconImage(img.getImage());

        PanelGenererEval pnlEval = new PanelGenererEval(this,ctrl);

        this.add(pnlEval);

        this.setVisible(true);

    }

    public void fermerFenetre() { this.dispose(); }

}