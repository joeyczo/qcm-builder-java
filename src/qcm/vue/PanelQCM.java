package qcm.vue;

import qcm.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class PanelQCM extends JPanel implements ActionListener
{

    private FrameInfosQuestion frameParent;
    private Controleur         ctrl;

    private ArrayList<JButton>      lstBtnSupp;
    private ArrayList<JTextArea>    lstTxtReponses;
    private ArrayList<JCheckBox>    lstBtnValideReponse;
    private ArrayList<JScrollPane>  lstScrollTexte;

    private JTextArea   txtQst;
    private JButton     btnAjouter;
    private JButton     btnInfoSupp;
    private JButton     btnEnregistrer;
    private JScrollPane scrollTexte;



    public PanelQCM (FrameInfosQuestion parent, Controleur ctrl)
    {
        this.frameParent         = parent;
        this.ctrl                = ctrl;
        this.lstBtnSupp          = new ArrayList<JButton>();
        this.lstTxtReponses      = new ArrayList<JTextArea>();
        this.lstBtnValideReponse = new ArrayList<JCheckBox>();
        this.lstScrollTexte      = new ArrayList<JScrollPane>();

        this.txtQst           = new JTextArea (5, 1);
        this.btnAjouter       = new JButton   ();
        this.btnInfoSupp      = new JButton   ();
        this.btnEnregistrer   = new JButton   ("Enregistrer");
        this.scrollTexte      = new JScrollPane(this.txtQst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


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
        this.add(this.scrollTexte, gbc);


        for( int cpt = 0; cpt < 2; cpt ++)
        {
            this.lstBtnSupp         .add(new JButton());
            this.lstTxtReponses     .add(new JTextArea (5, 1));
            this.lstScrollTexte     .add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            this.lstBtnValideReponse.add(new JCheckBox());

            gbc.gridwidth = 1;
            gbc.ipadx     = 0;
            gbc.ipady     = 0;
            gbc.gridx     = 0;
            gbc.gridy     = 2 + cpt;
            this.lstBtnSupp.get(cpt).setOpaque(false);
            this.lstBtnSupp.get(cpt).setContentAreaFilled(false);
            this.lstBtnSupp.get(cpt).setBorderPainted(false);
            this.lstBtnSupp.get(cpt).setIcon(new ImageIcon("src/data/img/delete.png"));
            this.add(this.lstBtnSupp.get(cpt), gbc);

            gbc.gridx     = 1;
            gbc.gridwidth = 5;
            gbc.ipadx     = 400;
            gbc.ipady     = 30;
            this.add(this.lstScrollTexte.get(cpt), gbc);

            gbc.gridx     = GridBagConstraints.RELATIVE;
            gbc.gridwidth = GridBagConstraints.RELATIVE;
            gbc.ipadx     = 0;
            gbc.ipady     = 0;
            this.add(this.lstBtnValideReponse.get(cpt), gbc);

        }


        gbc.gridwidth = 1;
        gbc.gridx     = 0;
        gbc.gridy     = 2 + this.lstTxtReponses.size();
        this.btnAjouter.setOpaque(false);
        this.btnAjouter.setContentAreaFilled(false);
        this.btnAjouter.setBorderPainted(false);
        this.btnAjouter.setIcon(new ImageIcon("src/data/img/add.png"));
        this.add(this.btnAjouter, gbc);


        gbc.gridx = 1;
        this.btnInfoSupp.setOpaque(false);
        this.btnInfoSupp.setContentAreaFilled(false);
        this.btnInfoSupp.setBorderPainted(false);
        this.btnInfoSupp.setIcon(new ImageIcon("src/data/img/edit.png"));
        this.add(this.btnInfoSupp, gbc);

        gbc.gridx = 2;
        this.add(this.btnEnregistrer, gbc);


        this.btnAjouter    .addActionListener(this);
        this.btnInfoSupp   .addActionListener(this);
        this.btnEnregistrer.addActionListener(this);

    }


    public void actionPerformed(ActionEvent e)
    {

        if ( e.getSource() == this.btnAjouter )
        {
            this.lstBtnSupp         .add(new JButton());
            this.lstTxtReponses     .add(new JTextArea (5, 1));
            this.lstScrollTexte     .add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            this.lstBtnValideReponse.add(new JCheckBox());

            this.majIHM();
        }



    }

    public void majIHM()
    {
        this.removeAll();

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
        this.add(this.scrollTexte, gbc);


        for( int cpt = 0; cpt < this.lstTxtReponses.size(); cpt ++)
        {

            gbc.gridwidth = 1;
            gbc.ipadx     = 0;
            gbc.ipady     = 0;
            gbc.gridx     = 0;
            gbc.gridy     = 2 + cpt;
            this.lstBtnSupp.get(cpt).setOpaque(false);
            this.lstBtnSupp.get(cpt).setContentAreaFilled(false);
            this.lstBtnSupp.get(cpt).setBorderPainted(false);
            this.lstBtnSupp.get(cpt).setIcon(new ImageIcon("src/data/img/delete.png"));
            this.add(this.lstBtnSupp.get(cpt), gbc);

            gbc.gridx     = 1;
            gbc.gridwidth = 5;
            gbc.ipadx     = 400;
            gbc.ipady     = 30;
            this.add(this.lstScrollTexte.get(cpt), gbc);

            gbc.gridx     = GridBagConstraints.RELATIVE;
            gbc.gridwidth = GridBagConstraints.RELATIVE;
            gbc.ipadx     = 0;
            gbc.ipady     = 0;
            this.add(this.lstBtnValideReponse.get(cpt), gbc);

        }

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2 + this.lstTxtReponses.size();
        this.btnAjouter.setOpaque(false);
        this.btnAjouter.setContentAreaFilled(false);
        this.btnAjouter.setBorderPainted(false);
        this.btnAjouter.setIcon(new ImageIcon("src/data/img/add.png"));
        this.add(this.btnAjouter, gbc);


        gbc.gridx = 1;
        this.btnInfoSupp.setOpaque(false);
        this.btnInfoSupp.setContentAreaFilled(false);
        this.btnInfoSupp.setBorderPainted(false);
        this.btnInfoSupp.setIcon(new ImageIcon("src/data/img/edit.png"));
        this.add(this.btnInfoSupp, gbc);

        gbc.gridx = 2;
        this.add(this.btnEnregistrer, gbc);

        this.revalidate();
        this.repaint();
    }

}



