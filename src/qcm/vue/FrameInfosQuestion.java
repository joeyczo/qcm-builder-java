package qcm.vue;

import qcm.Controleur;
import qcm.metier.*;

import javax.swing.*;
import java.nio.channels.DatagramChannel;

public class FrameInfosQuestion extends JFrame {

    private Controleur ctrl;
    private JScrollPane scrollPanelQCM;


    public FrameInfosQuestion(Controleur ctrl, DonneesCreationQuestion data) {

        this.ctrl = ctrl;

        this.setTitle("Éditer les informations");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        if (data.type() == TypeQuestion.QCM) {
            PanelQCM panelQCM = new PanelQCM(this, ctrl);
            this.scrollPanelQCM = new JScrollPane(panelQCM, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scrollPanelQCM);

        } else if (data.type() == TypeQuestion.ASSOCIATION) {
            PanelAssociation panelAssociation = new PanelAssociation();
            this.add(panelAssociation);
        } else if(data.type() == TypeQuestion.ELIMINATION) {
            PanelElim panelElim = new PanelElim(this, ctrl);
            this.add(panelElim);
        }


        this.setVisible(true);

    }

    public static void main(String[] args) {

        Ressource r = new Ressource("MATHS");
        Notion    n = new Notion("cool");

        r.ajouterNotion(n);

        DonneesCreationQuestion data = new DonneesCreationQuestion(1, "15:50", r, n, DifficulteQuestion.FACILE, TypeQuestion.QCM);

        new FrameInfosQuestion(null, data);

    }


}
