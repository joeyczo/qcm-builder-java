package qcm.vue;

import qcm.Controleur;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class PanelElim extends JPanel implements ActionListener, DocumentListener
{

    private FrameInfosQuestion frameParent;
    private Controleur         ctrl;


    private ArrayList<JButton>      lstBtnSupp;
    private ArrayList<JTextArea>    lstTxtReponses;
    private ArrayList<JRadioButton> lstBtnValideReponse;
    private ArrayList<JScrollPane>  lstScrollTexte;
    private ArrayList<JTextField>   lstOrdrePrioQst;
    private ArrayList<JTextField>   lstPointEnMoins;


    private JButton           btnEnregistrer, btnAdd, btnExplain,btnInfoSupp;
    private JTextArea         txtQst, txtInfoSupp;
    private JScrollPane       scrollPane;
    private ButtonGroup       btg;

    public PanelElim( FrameInfosQuestion parent, Controleur ctrl )
    {

        this.frameParent = parent;
        this.ctrl        = ctrl;

        this.lstBtnSupp          = new ArrayList<JButton>();
        this.lstTxtReponses      = new ArrayList<JTextArea>();
        this.lstBtnValideReponse = new ArrayList<JRadioButton>();
        this.lstScrollTexte      = new ArrayList<JScrollPane>();
        this.lstOrdrePrioQst     = new ArrayList<JTextField>();
        this.lstPointEnMoins     = new ArrayList<JTextField>();


        this.txtQst         = new JTextArea  (5, 1);
        this.txtInfoSupp    = new JTextArea  (10, 10);
        this.scrollPane     = new JScrollPane(this.txtQst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.btnEnregistrer = new JButton    ("Enregistrer");
        this.btnInfoSupp    = new JButton    ();
        this.btnAdd         = new JButton    (new ImageIcon("src/data/img/add.png"));
        this.btnExplain     = new JButton    (new ImageIcon("src/data/img/edit.png"));
        this.btg            = new ButtonGroup();

        for( int cpt = 0; cpt < 2; cpt ++)
        {
            this.lstBtnSupp.add(new JButton());
            this.lstTxtReponses.add(new JTextArea(5, 1));
            this.lstScrollTexte.add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            this.lstOrdrePrioQst.add(new JTextField(3));
            this.lstPointEnMoins.add(new JTextField(3));
            JRadioButton radioBouton = new JRadioButton();
            this.lstBtnValideReponse.add(radioBouton);
            this.btg.add(radioBouton);

            this.lstOrdrePrioQst.get(cpt).getDocument().addDocumentListener(this);
            this.lstPointEnMoins.get(cpt).getDocument().addDocumentListener(this);
        }

        this.majIHM();

        this.btnEnregistrer.addActionListener(this);
        this.btnAdd        .addActionListener(this);
        this.btnInfoSupp   .addActionListener(this);
        this.btnExplain    .addActionListener(this);


        for ( int cpt = 0; cpt < this.lstBtnSupp.size(); cpt ++)
            this.lstBtnSupp.get(cpt).addActionListener(this);

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAdd)
        {
            this.lstBtnSupp         .add(new JButton());
            this.lstTxtReponses     .add(new JTextArea (5, 1));
            this.lstScrollTexte     .add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            this.lstOrdrePrioQst    .add(new JTextField(3));
            this.lstPointEnMoins    .add(new JTextField(3));
            JRadioButton radioBouton = new JRadioButton();
            this.lstBtnValideReponse.add(radioBouton);
            this.btg                .add(radioBouton);

            this.lstOrdrePrioQst.getLast().getDocument().addDocumentListener(this);
            this.lstPointEnMoins.getLast().getDocument().addDocumentListener(this);

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
                    this.lstOrdrePrioQst    .remove(cpt);
                    this.lstPointEnMoins    .remove(cpt);

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
            JScrollPane scrollPane = new JScrollPane(this.txtInfoSupp);

            int resultat = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Ajouter explication",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            return;
        }

        if ( e.getSource() == this.btnEnregistrer)
        {
            if ( this.txtQst.getText().isEmpty() || this.txtQst.getText().trim().isEmpty() )
            {
                this.afficherMessageErreur("Erreur : Aucun texte n'est entré pour la question");
                return;
            }

            for (JTextArea lstTxtRepons : this.lstTxtReponses)
                if (lstTxtRepons.getText().isEmpty() || lstTxtRepons.getText().trim().isEmpty()) {
                    this.afficherMessageErreur("Erreur : Aucun texte n'est entré pour l'une des réponse");
                    return;
                }

            int cptBoutonValide = 0;
            for (JRadioButton jRadioButton : this.lstBtnValideReponse)
                if (jRadioButton.isSelected())
                    cptBoutonValide++;

            if ( cptBoutonValide == 0 )
            {
                this.afficherMessageErreur("Erreur : Il faut au moins sélectionner un réponse valide");
                return;
            }
        }


    }

    /**
     * Afficher un message d'erreur à l'utilisateur
     * @param message Message à afficher
     */
    private void afficherMessageErreur ( String message ) {
        JOptionPane.showMessageDialog(this,  message, "Impossible de sauvegarder la réponse", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Afficher un message de validation à l'utilisateur
     * @param message Message à afficher
     */
    private void afficherMessageValide ( String message ) {
        JOptionPane.showMessageDialog(this,  message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    public void majIHM()
    {

        this.removeAll();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets             = new Insets(5, 5, 5, 5);
        gbc.fill               = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Question"), gbc);

        gbc.gridy     = 1;
        gbc.gridwidth = 6;
        gbc.ipadx     = 600;
        gbc.ipady     = 50;
        this.add(this.scrollPane, gbc);


        for( int cpt = 0; cpt < this.lstTxtReponses.size(); cpt ++)
        {

            gbc.gridwidth  = 1;
            gbc.gridheight = 2;
            gbc.ipadx      = 0;
            gbc.ipady      = 0;
            gbc.gridx      = 0;
            gbc.gridy      = 2 + ( cpt * 2 );
            this.lstBtnSupp.get(cpt).setOpaque(false);
            this.lstBtnSupp.get(cpt).setContentAreaFilled(false);
            this.lstBtnSupp.get(cpt).setBorderPainted(false);
            this.lstBtnSupp.get(cpt).setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.lstBtnSupp.get(cpt).setIcon(new ImageIcon("src/data/img/delete.png"));
            this.add(this.lstBtnSupp.get(cpt), gbc);

            gbc.gridx     = 1;
            gbc.gridwidth = 5;
            gbc.ipadx     = 400;
            gbc.ipady     = 30;
            this.add(this.lstScrollTexte.get(cpt), gbc);

            gbc.ipadx      = 0;
            gbc.ipady      = 0;
            gbc.gridheight = 1;
            gbc.gridx      = GridBagConstraints.RELATIVE;
            gbc.gridwidth  = GridBagConstraints.RELATIVE;
            this.add(this.lstOrdrePrioQst.get(cpt), gbc);

            gbc.gridy      = 2 + ( cpt * 2 ) + 1;
            this.add(this.lstPointEnMoins.get(cpt), gbc);

            gbc.gridheight = 2;
            gbc.gridy      = 2 + ( cpt * 2 );
            this.add(this.lstBtnValideReponse.get(cpt), gbc);


        }

        gbc.gridheight = 1;
        gbc.gridwidth  = 1;
        gbc.gridx      = 0;
        gbc.gridy      = 2 + ( this.lstTxtReponses.size() * 2 );
// TODO faire en sorte de voir quand la sourie passe sur le bouton
        this.btnAdd.setOpaque(false);
        this.btnAdd.setContentAreaFilled(false);
        this.btnAdd.setBorderPainted(false);
        this.btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(btnAdd, gbc);

        gbc.gridx      = 1;
        this.btnExplain.setOpaque(false);
        this.btnExplain.setContentAreaFilled(false);
        this.btnExplain.setBorderPainted(false);
        this.btnExplain.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(btnExplain, gbc);


        // TODO travaille Evan !! Fait pareil pour les boutons enregistrer et fichier
        gbc.gridx      = 2;
        this.add(btnEnregistrer, gbc);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

        for ( int cpt = 0; cpt < this.lstOrdrePrioQst.size(); cpt ++)
        {
            if (!this.lstOrdrePrioQst.get(cpt).getText().isEmpty() || !this.lstPointEnMoins.get(cpt).getText().isEmpty() )
            {
                this.lstBtnValideReponse.get(cpt).setEnabled(false);
                this.lstBtnValideReponse.get(cpt).setSelected(false);
            }

            if (this.lstOrdrePrioQst.get(cpt).getText().isEmpty() && this.lstPointEnMoins.get(cpt).getText().isEmpty())
            {
                this.lstBtnValideReponse.get(cpt).setEnabled(true);
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

        for ( int cpt = 0; cpt < this.lstOrdrePrioQst.size(); cpt ++)
        {

            if (!this.lstOrdrePrioQst.get(cpt).getText().isEmpty() || !this.lstPointEnMoins.get(cpt).getText().isEmpty() )
            {
                this.lstBtnValideReponse.get(cpt).setEnabled(false);
                this.lstBtnValideReponse.get(cpt).setSelected(false);
            }

            if (this.lstOrdrePrioQst.get(cpt).getText().isEmpty() && this.lstPointEnMoins.get(cpt).getText().isEmpty())
            {
                this.lstBtnValideReponse.get(cpt).setEnabled(true);
            }

        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}