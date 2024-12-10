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

        //this.btnAjouter.setIcon(new Icon());


        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets             = new Insets(5, 5, 5, 5);
        gbc.fill               = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Question"), gbc);

        gbc.gridy     = 1;
        gbc.gridwidth = 3;
        gbc.ipadx     = 600;
        gbc.ipady     = 50;
        this.add(scrollPane, gbc);

        gbc.gridwidth = 1;
        gbc.gridy     = 2;
        gbc.ipadx     = 0;
        gbc.ipady     = 0;

        this.add(panelAjouter, gbc);


        gbc.gridy = 3;
        this.add(btnAjouter, gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(btnInfoSupp, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        this.add(btnEnregistrer, gbc);


        this.btnAjouter    .addActionListener(this);
        this.btnInfoSupp   .addActionListener(this);
        this.btnEnregistrer.addActionListener(this);
    }


    /*
        gbc.gridx = 0; // Représente l'indice de la colonne dans laquelle le composant sera placé.
        gbc.gridy = 0; // Représente l'indice de la ligne dans laquelle le composant sera placé.
        gbc.gridwidth = 1; // Détermine le nombre de colonnes que le composant occupera.
        gbc.gridheight = 1; // Détermine le nombre de lignes que le composant occupera.
        gbc.weightx = 1.0; // Détermine l'importance relative de l'espace horizontal que le composant doit occuper lorsqu'il y a de l'espace disponible.
        gbc.weighty = 1.0; // Semblable à weightx, mais pour l'espace vertical.
        gbc.fill = GridBagConstraints.BOTH; // remplissage
     */

    public void actionPerformed(ActionEvent e)
    {
    }

}



