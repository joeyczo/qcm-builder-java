package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameInfosQuestion extends JFrame {

    private Controleur ctrl;
    private PanelQCM   panelQCM;

    public FrameInfosQuestion(Controleur ctrl) {

        this.ctrl = ctrl;

        this.setTitle("Éditer les informations");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.panelQCM = new PanelQCM(this, ctrl);

        this.add(this.panelQCM);

        this.setVisible(true);



    }


}
