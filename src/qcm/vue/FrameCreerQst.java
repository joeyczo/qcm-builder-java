package qcm.vue;

import qcm.Controleur;
import qcm.metier.DonneesCreationQuestion;

import javax.swing.*;

public class FrameCreerQst extends JDialog {

    public FrameCreerQst(JFrame parent, Controleur ctrl, DonneesCreationQuestion data) {

        super(parent, ((data == null) ? "Créer une nouvelle question" : "Modifier une question"), true);

        this.setSize(1000, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        PanelCreerQst pnlQuestions = new PanelCreerQst(this, ctrl, data);

        this.add(pnlQuestions);

        this.setVisible(true);

    }

    public void fermerFenetre() { this.dispose(); }

}