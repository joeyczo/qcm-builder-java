package qcm.vue;
import qcm.Controleur;
import qcm.metier.Notion;
import qcm.metier.Ressource;
import qcm.vue.donnees.GrilleDonneesEval;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class PanelElim extends JPanel implements ActionListener
{
    private FrameInfosQuestion frameParent;
    private JPanel         panel;
    private JTextArea      txtQst;

    private Controleur ctrl;

    public PanelElim( FrameInfosQuestion parent, Controleur ctrl ){
        this.frameParent = parent;
        this.setLayout ( new GridBagLayout() );
        this.ctrl = ctrl;

        this.txtQst = new JTextArea();

        this.add(new JLabel("Question"));
        this.add(txtQst);
        //this.add(txt);
    }

    public void actionPerformed(ActionEvent e) {

    }
}