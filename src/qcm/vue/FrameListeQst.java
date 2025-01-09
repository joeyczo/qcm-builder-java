package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameListeQst extends JDialog {

    public FrameListeQst(JFrame parent, Controleur ctrl) {

        super(parent, "Liste des questions", true);

        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        ImageIcon img = new ImageIcon("../data/img/icon.png");
        this.setIconImage(img.getImage());

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        PanelListeQst panelListeQst = new PanelListeQst(this, ctrl);

        this.add(panelListeQst);

        this.setVisible(true);

    }

    public void fermerFenetre() { this.dispose(); }

}