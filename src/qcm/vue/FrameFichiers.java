package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameFichiers extends JDialog {

    private Controleur  ctrl;
    private JFrame      frameParent;

    public FrameFichiers(Controleur ctrl, JFrame frameParent) {

        super(frameParent, "Sélection des fichiers", true);

        this.ctrl = ctrl;
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        PanelFichiers panelFichiers = new PanelFichiers(this.ctrl, this);
        this.add(panelFichiers);

        this.setVisible(true);

    }

    public void fermerFenetre() {
        this.dispose();
    }

}
