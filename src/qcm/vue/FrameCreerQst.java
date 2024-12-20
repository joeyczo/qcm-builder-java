package qcm.vue;

import qcm.Controleur;
import qcm.metier.DonneesCreationQuestion;

import javax.swing.*;

public class FrameCreerQst extends JDialog
{
    private PanelCreerQst           pnlQuestions;
    private Controleur              ctrl;
    private DonneesCreationQuestion data;

    public FrameCreerQst(JFrame parent, Controleur ctrl, DonneesCreationQuestion data)
    {
        super(parent, ((data == null) ? "Créer une nouvelle question" : "Modifier une question"), true);
        this.setSize(1000, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.ctrl   = ctrl;
        this.data   = data;

        this.pnlQuestions = new PanelCreerQst(this, ctrl, data);

        this.add(this.pnlQuestions);

        this.setVisible(true);

    }

    public void fermerFenetre() {
        this.dispose();
    }

}