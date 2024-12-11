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

    private JTextArea   txtQst;
    private JButton     btnAjouter;
    private JButton     btnInfoSupp;
    private JButton     btnEnregistrer;
    private JPanel      panelAjouter;
    private JScrollPane scrollPane;


    public PanelQCM (FrameInfosQuestion parent, Controleur ctrl)
    {
        this.frameParent = parent;
        this.ctrl        = ctrl;

        this.txtQst         = new JTextArea (5, 1);
        this.scrollPane     = new JScrollPane(txtQst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.btnAjouter     = new JButton   ("");
        this.btnInfoSupp    = new JButton   ("");
        this.btnEnregistrer = new JButton   ("Enregistrer");
        this.panelAjouter   = new JPanel    ();



        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets             = new Insets(5, 5, 5, 5);
        gbc.fill               = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Question"), gbc);

        gbc.gridy     = 1;
        gbc.gridwidth = 5;
        gbc.ipadx     = 600;
        gbc.ipady     = 50;
        this.add(this.scrollPane, gbc);

        gbc.gridwidth = 1;
        gbc.gridy     = 2;
        gbc.ipadx     = 0;
        gbc.ipady     = 0;
        this.add(this.panelAjouter, gbc);


        gbc.gridy = 3;
        this.btnAjouter.setOpaque(false);
        this.btnAjouter.setContentAreaFilled(false);
        this.btnAjouter.setBorderPainted(false);
        this.btnAjouter.setIcon(new ImageIcon("src/data/img/add.png"));
        this.add(this.btnAjouter, gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        this.btnInfoSupp.setOpaque(false);
        this.btnInfoSupp.setContentAreaFilled(false);
        this.btnInfoSupp.setBorderPainted(false);
        this.btnInfoSupp.setIcon(new ImageIcon("src/data/img/edit.png"));
        this.add(this.btnInfoSupp, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        this.add(this.btnEnregistrer, gbc);


        this.btnAjouter    .addActionListener(this);
        this.btnInfoSupp   .addActionListener(this);
        this.btnEnregistrer.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e)
    {

        if ( e.getSource() == this.btnAjouter )
        {

        }

    }

}



