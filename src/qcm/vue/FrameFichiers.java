package qcm.vue;

import qcm.Controleur;
import qcm.metier.DonneesCreationQuestion;

import javax.swing.*;

public class FrameFichiers extends JDialog {

    private Controleur              ctrl;
    private JFrame                  frameParent;
    private DonneesCreationQuestion data;

    public FrameFichiers(Controleur ctrl, JFrame frameParent, DonneesCreationQuestion data) {

        super(frameParent, "Sélection des fichiers", true);

        this.ctrl = ctrl;
        this.data = data;
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        PanelFichiers panelFichiers = new PanelFichiers(this.ctrl, this, data);
        this.add(panelFichiers);

        this.setVisible(true);

    }

    public void fermerFenetre() {
        this.dispose();
    }

}
