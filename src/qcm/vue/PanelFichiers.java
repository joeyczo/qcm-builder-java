package qcm.vue;

import qcm.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelFichiers extends JPanel implements ActionListener {

    private Controleur      ctrl;
    private FrameFichiers   frameParent;

    private JButton         btnFermer;

    public PanelFichiers(Controleur ctrl, FrameFichiers frameParent) {

        this.ctrl           = ctrl;
        this.frameParent    = frameParent;


        this.setLayout(new BorderLayout());

        this.btnFermer      = new JButton("Fermer");

        this.add(this.btnFermer, BorderLayout.SOUTH);

        this.btnFermer.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this.btnFermer)
            this.frameParent.fermerFenetre();
        
    }

}
