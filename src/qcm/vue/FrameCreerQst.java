package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameCreerQst extends JDialog
{
    private PanelCreerQst pnlQuestions;
    private Controleur    ctrl;

    public FrameCreerQst(JFrame parent, Controleur ctrl)
    {
        super(parent, "Créer question", true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.ctrl = ctrl;

        this.pnlQuestions = new PanelCreerQst(this, ctrl);

        this.add(this.pnlQuestions);

        this.setVisible(true);

    }

    // todo vérifier si lors de la création il y a une notion qui est présent eou non. exemple erreur si la ressource est sans notion donc aucune notion de rentré
    // todo quand on ferme la fenetre des questions ( qcm, assos, ...) cela ferme aussi la fenetre creer question
    public void fermerFenetre() {
        this.dispose();
    }

}