package qcm.vue;

import qcm.Controleur;
import qcm.metier.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class PanelQCM extends JPanel implements ActionListener
{

    private ArrayList<JButton>     lstBtnSupp;
    private ArrayList<JTextArea>   lstTxtReponses;
    private ArrayList<JCheckBox>   lstBtnValideReponse;
    private ArrayList<JScrollPane> lstScrollTexte;

    private JTextArea               txtQst;
    private JButton                 btnAjouter;
    private JButton                 btnInfoSupp;
    private JButton                 btnEnregistrer;
    private JScrollPane             scrollTexte;
    private Controleur              ctrl;
    private DonneesCreationQuestion data;



    public PanelQCM ( DonneesCreationQuestion data, Controleur ctrl )
    {

        this.lstBtnSupp          = new ArrayList<JButton>();
        this.lstTxtReponses      = new ArrayList<JTextArea>();
        this.lstBtnValideReponse = new ArrayList<JCheckBox>();
        this.lstScrollTexte      = new ArrayList<JScrollPane>();

        this.txtQst           = new JTextArea (5, 1);
        this.btnAjouter       = new JButton   ();
        this.btnInfoSupp      = new JButton   ();
        this.btnEnregistrer   = new JButton   ("Enregistrer");
        this.scrollTexte      = new JScrollPane(this.txtQst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.data             = data;
        this.ctrl             = ctrl;



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

        for ( int cpt = 0; cpt < this.lstBtnSupp.size(); cpt ++)
            this.lstBtnSupp.get(cpt).addActionListener(this);

    }


    public void actionPerformed(ActionEvent e)
    {

        if ( e.getSource() == this.btnAjouter )
        {
            this.lstBtnSupp         .add(new JButton());
            this.lstTxtReponses     .add(new JTextArea (5, 1));
            this.lstScrollTexte     .add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            this.lstBtnValideReponse.add(new JCheckBox());

            this.lstBtnSupp.getLast().addActionListener(this);

            this.majIHM();
            return;
        }

        for ( int cpt = 0; cpt < this.lstBtnSupp.size(); cpt ++)
            if (e.getSource() == this.lstBtnSupp.get(cpt))
            {
                // Bloque la suppression s'il y a moins de 2 cases après la suppression
                if ( this.lstBtnSupp.size() > 2 )
                {
                    this.lstBtnSupp         .remove(cpt);
                    this.lstTxtReponses     .remove(cpt);
                    this.lstScrollTexte     .remove(cpt);
                    this.lstBtnValideReponse.remove(cpt);

                    this.majIHM();
                }
                else
                {
                    JOptionPane.showMessageDialog(this,  "Erreur : Il doit y avoir au minimum 2 réponses possible", "Erreur Suppression ", JOptionPane.ERROR_MESSAGE);
                }
                return;


            }


        if(e.getSource() == this.btnInfoSupp)
        {
            JTextArea   zoneTexte  = new JTextArea(10, 30);
            JScrollPane scrollPane = new JScrollPane(zoneTexte);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Ajouter explication",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION)
            {
                String explication = zoneTexte.getText();
                System.out.println("Explication entrée : " + explication);
            }

            return;
        }

        if ( e.getSource() == this.btnEnregistrer )
        {

            if ( this.txtQst.getText().isEmpty() || this.txtQst.getText().trim().isEmpty() )
            {
                JOptionPane.showMessageDialog(this,  "Erreur : Aucun texte n'est entré pour la question", "Impossible de sauvegarder la réponse", JOptionPane.ERROR_MESSAGE);
                return;
            }


            for ( int cpt = 0; cpt < this.lstTxtReponses.size(); cpt ++)
                if ( this.lstTxtReponses.get(cpt).getText().isEmpty() || this.lstTxtReponses.get(cpt).getText().trim().isEmpty() )
                {
                    JOptionPane.showMessageDialog(this,  "Erreur : Aucun texte n'est entré pour l'une des réponse", "Impossible de sauvegarder la réponse", JOptionPane.ERROR_MESSAGE);
                    return;
                }


            // todo réparer
            int cptBoutonValide = 0;
            for ( int cpt = 0; cpt < this.lstBtnValideReponse.size(); cpt ++)
                if ( this.lstBtnValideReponse.get(cpt).isValid() )
                    cptBoutonValide ++;

            if ( cptBoutonValide == 0 )
            {
                JOptionPane.showMessageDialog(this,  "Erreur : Il faut au moins sélectionner un réponse valide", "Impossible de sauvegarder la réponse", JOptionPane.ERROR_MESSAGE);
                return;
            }


            QCMReponse qcmReponse = new QCMReponse();

            for ( int cpt = 0; cpt < this.lstTxtReponses.size(); cpt ++)
            {
                qcmReponse.ajouterItem(new QCMReponseItem(this.lstTxtReponses.get(cpt).getText(), this.lstBtnValideReponse.get(cpt).isValid()));
            }


            this.data.notion().ajouterQuestion(new Question(this.txtQst.getText(), this.data.tempsReponse(), this.data.type(), qcmReponse, this.data.diff()));

            System.out.println("Enregistrement ...");
            System.out.println("Les questions dans la ressource " + this.data.ressource().getNom() + " pour la notion " + this.data.notion().getNom() + " sont : ");


            for ( int cpt = 0; cpt < this.data.notion().getNbQuestions(); cpt ++)
            {
                System.out.println(this.data.notion().getQuestion(cpt));
            }




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

// todo Au moins un case cocher pour les réponses et du texte partout


