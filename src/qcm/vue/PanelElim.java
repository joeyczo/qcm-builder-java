package qcm.vue;

import qcm.Controleur;
import qcm.metier.DonneesCreationQuestion;
import qcm.metier.EliminationReponse;
import qcm.metier.EliminationReponseItem;
import qcm.metier.Question;

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

    private FrameInfosQuestion      frameParent;
    private Controleur              ctrl;
    private DonneesCreationQuestion data;

    private ArrayList<JButton>      lstBtnSupp;
    private ArrayList<JTextArea>    lstTxtReponses;
    private ArrayList<JRadioButton> lstBtnValideReponse;
    private ArrayList<JScrollPane>  lstScrollTexte;
    private ArrayList<JTextField>   lstOrdrePrioQst;
    private ArrayList<JTextField>   lstPointEnMoins;


    private JButton           btnEnregistrer, btnAdd,btnInfoSupp;
    private JTextArea         txtQst, txtInfoSupp;
    private JScrollPane       scrollPane;
    private ButtonGroup       btg;

    public PanelElim( DonneesCreationQuestion data, FrameInfosQuestion parent, Controleur ctrl )
    {

        this.data        = data;
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
        this.btnAdd         = new JButton    (new ImageIcon("src/data/img/add.png"));
        this.btnInfoSupp    = new JButton    (new ImageIcon("src/data/img/edit.png"));
        this.btg            = new ButtonGroup();

        for( int cpt = 0; cpt < 2; cpt ++)
        {
            this.lstBtnSupp         .add(new JButton());
            this.lstTxtReponses     .add(new JTextArea(5, 1));
            this.lstScrollTexte     .add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            this.lstOrdrePrioQst    .add(new JTextField(3));
            this.lstPointEnMoins    .add(new JTextField(3));
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
            this.lstBtnSupp     .getLast().addActionListener(this);

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

            System.out.println("CLIQUE SUR LE BOUTON");

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

            for (JTextArea lstTxtReponse : this.lstTxtReponses)
                if (lstTxtReponse.getText().isEmpty() || lstTxtReponse.getText().trim().isEmpty())
                {
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

            List<Integer> ordreSuppressionMauvaiseRep = new ArrayList<Integer>();
            Double        pointSoustrait              = 0.0;
            Double        sommePointSoustrait         = 0.0;

            for (int cpt = 0; cpt < this.lstOrdrePrioQst.size(); cpt ++)
            {
                try
                {
                    if ( !(this.lstOrdrePrioQst.get(cpt).getText().trim().isEmpty() && this.lstPointEnMoins.get(cpt).getText().trim().isEmpty()) )
                    {

                        System.out.println(this.lstOrdrePrioQst.get(cpt).getText());
                        System.out.println(this.lstPointEnMoins.get(cpt).getText());

                        ordreSuppressionMauvaiseRep.add(Integer.parseInt  (this.lstOrdrePrioQst.get(cpt).getText().trim()));
                        pointSoustrait                = Double.parseDouble(this.lstPointEnMoins.get(cpt).getText().trim());

                        if ( pointSoustrait < 0 )
                            sommePointSoustrait += pointSoustrait;
                        else
                        {
                            this.afficherMessageErreur("Erreur : Le nombre de point à enlevé doit être négatif -> (-0.5)");
                            return;
                        }


                        if ( this.data.nbPoints() + sommePointSoustrait < 0 )
                        {
                            this.afficherMessageErreur("Erreur : Trop de points sont enlevé");
                            return;
                        }
                    }

                }
                catch (Exception exception)
                {
                    System.out.println(exception.getMessage());
                    this.afficherMessageErreur("Erreur : Soit les deux cases de priorité d'ordre et point en moins doivent contenir des données soit aucune");
                    return;
                }
            }

            // Trie dans l'ordre croissant l'ordre des suppressions
            ordreSuppressionMauvaiseRep.sort(Comparator.naturalOrder());

            for (int cpt = 1; cpt <= ordreSuppressionMauvaiseRep.size(); cpt++)
            {

                if ( ordreSuppressionMauvaiseRep.get(cpt-1) != cpt )
                {
                    this.afficherMessageErreur("Erreur : La saisie de l'ordre de suppression est incorrect");
                    return;
                }
            }
               

            // On crée l'objet réponse

            EliminationReponse eliminationReponse = new EliminationReponse();

            int i = 0;
            for (JTextArea txt : this.lstTxtReponses) {

                String  txtReponse      = txt.getText().trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t");
                String  ordreReponse    = this.lstOrdrePrioQst.get(i).getText().trim();
                String  ptsReponse      = this.lstPointEnMoins.get(i).getText().trim();

                int     ordreReponseInt = (!ordreReponse.isEmpty()) ? Integer.parseInt(ordreReponse) : 0;
                float   pointsReponse   = (!ptsReponse.isEmpty())   ? Float.parseFloat(ptsReponse)   : 0;
                boolean bonneReponse    = this.lstBtnValideReponse.get(i).isSelected();

                EliminationReponseItem item = new EliminationReponseItem(txtReponse, ordreReponseInt, pointsReponse, bonneReponse);

                eliminationReponse.ajouterReponseItem(item);

                i++;

            }

            if ( !this.txtInfoSupp.getText().isEmpty() && !this.txtInfoSupp.getText().trim().isEmpty())
                eliminationReponse.ajouterTexteExplication(this.txtInfoSupp.getText().trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t"));

            // On crée l'objet question et on l'ajoute à la base de données

            String txtQuestion = this.txtQst.getText().trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t");

            Question question = new Question(txtQuestion, this.data.tempsReponse(), this.data.nbPoints(), this.data.type(), eliminationReponse, this.data.diff(), this.data.notion());

            if (!this.ctrl.sauvegarderQuestion(question)) {
                this.afficherMessageErreur("Impossible de sauvegarder la réponse dans la base de données");
                return;
            }

            this.data.notion().ajouterQuestion(question);

            this.afficherMessageValide("La question a bien été sauvegardée dans la base de données !");

            this.frameParent.fermerFenetre();


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
        this.btnAdd.setOpaque(false);
        this.btnAdd.setContentAreaFilled(false);
        this.btnAdd.setBorderPainted(false);
        this.btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(btnAdd, gbc);

        gbc.gridx      = 1;
        this.btnInfoSupp.setOpaque(false);
        this.btnInfoSupp.setContentAreaFilled(false);
        this.btnInfoSupp.setBorderPainted(false);
        this.btnInfoSupp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(btnInfoSupp, gbc);


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
                this.lstBtnValideReponse.get(cpt).setSelected(false);
                this.lstBtnValideReponse.get(cpt).setEnabled(false);
                System.out.println(this.lstBtnValideReponse.get(cpt).isSelected());
            }
            else
                this.lstBtnValideReponse.get(cpt).setEnabled(true);

        }

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

        for ( int cpt = 0; cpt < this.lstOrdrePrioQst.size(); cpt ++)
        {

            if (!this.lstOrdrePrioQst.get(cpt).getText().isEmpty() || !this.lstPointEnMoins.get(cpt).getText().isEmpty() )
            {
                this.lstBtnValideReponse.get(cpt).setSelected(false);
                this.lstBtnValideReponse.get(cpt).setEnabled(false);
            }
            else
                this.lstBtnValideReponse.get(cpt).setEnabled(true);

        }

        this.majIHM();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    /*
    private void chargerDonnees()
    {
        Question q = this.data.qst();
        this.txtQuestion.setText(q.getTexteQuestion());
        AssociationReponse associationReponse = (AssociationReponse) q.getReponse();

        for(int i = 0 ; i < associationReponse.getNbReponses(); i++) {
            AssociationReponse a = (AssociationReponse) q.getReponse();
            AssociationReponseItem ai = a.getReponse(i);

            if(i >= 2){
                this.addDefinition(ai.getTexte());
                this.addReponse(ai.getReponse().getTexte());
            } else {
                this.lstTxtDefinitions.get(i).setText(ai.getTexte());
                this.lstTxtReponses.get(i).setText(ai.getReponse().getTexte());
            }

        }
        majIHM();
    }
    */
}