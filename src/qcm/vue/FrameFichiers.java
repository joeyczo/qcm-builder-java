package qcm.vue;

import qcm.Controleur;
import qcm.metier.DonneesCreationQuestion;

import javax.swing.*;

public class FrameFichiers extends JDialog {

    public FrameFichiers(Controleur ctrl, JFrame frameParent, DonneesCreationQuestion data) {

        super(frameParent, "Sélection des fichiers", true);

        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        PanelFichiers panelFichiers = new PanelFichiers(ctrl, this, data);

        this.add(panelFichiers);

        this.setVisible(true);

    }

    public void fermerFenetre() { this.dispose(); }

}
