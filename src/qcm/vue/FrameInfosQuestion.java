package qcm.vue;

import qcm.Controleur;
import qcm.metier.*;

import javax.swing.*;

public class FrameInfosQuestion extends JFrame {

    public FrameInfosQuestion(Controleur ctrl, DonneesCreationQuestion data) {

        this.setTitle("Éditer les informations");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        if (data.type() == TypeQuestion.QCMSOLO) {

            PanelQCMSolo panelQCMSolo = new PanelQCMSolo(data, ctrl, this);
            JScrollPane scrollPanel   = new JScrollPane(panelQCMSolo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scrollPanel);

        } else if (data.type() == TypeQuestion.QCMMULTI) {

            PanelQCMMulti panelQCMMulti = new PanelQCMMulti(data, ctrl, this);
            JScrollPane scrollPanel     = new JScrollPane(panelQCMMulti, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scrollPanel);

        }
        else if (data.type() == TypeQuestion.ASSOCIATION) {

            PanelAssociation panelAssociation = new PanelAssociation(data, ctrl, this);
            JScrollPane scrollPanel           = new JScrollPane(panelAssociation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scrollPanel);

        } else if(data.type() == TypeQuestion.ELIMINATION) {

            PanelElim panelElim     = new PanelElim(data, this, ctrl);
            JScrollPane scrollPanel = new JScrollPane(panelElim, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scrollPanel);

        }

        this.setVisible(true);

    }

    public void fermerFenetre() { this.dispose(); }

}
