package qcm.vue;

import qcm.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PanelQCM extends JPanel implements ActionListener
{

    private FrameInfosQuestion frameParent;
    private Controleur         ctrl;

    private JTextField txtQst;
    private JButton    btnAjouter;
    private JButton    btnModifier;
    private JButton    btnEnregistrer;




    public PanelQCM (FrameInfosQuestion parent, Controleur ctrl)
    {
        this.frameParent = parent;
        this.ctrl        = ctrl;

        this.txtQst         = new JTextField("", 5);
        this.btnAjouter     = new JButton("");
        this.btnModifier    = new JButton("");
        this.btnEnregistrer = new JButton("Enregistrer");

        this.txtQst.setSize(700,300);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Question"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(txtQst, gbc);

        this.btnAjouter    .addActionListener(this);
        this.btnModifier   .addActionListener(this);
        this.btnEnregistrer.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e)
    {
    }
}



