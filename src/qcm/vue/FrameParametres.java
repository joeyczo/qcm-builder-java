package qcm.vue;

import qcm.Controleur;

import javax.swing.*;

public class FrameParametres extends JDialog {


    public FrameParametres(JFrame parent, Controleur ctrl) {

        super(parent, "Paramètres", true);

        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        ImageIcon img = new ImageIcon("../data/img/icon.png");
        this.setIconImage(img.getImage());

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        PanelParametre panelParametre = new PanelParametre(ctrl);

        this.add(panelParametre);

        this.setVisible(true);

    }

}