package qcm.vue;

import qcm.Controleur;
import qcm.metier.*;

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

    private Font              fontGenerale;
    private Font              fontGeneraleGras;


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

        this.fontGenerale        = new Font("Arial", Font.PLAIN, 16);
        this.fontGeneraleGras    = new Font("Arial", Font.BOLD , 16);


        JTextArea jTextAreaQst = new JTextArea (4, 1);
        jTextAreaQst.setFont(this.fontGenerale);

        JTextArea jTextAreaInfo = new JTextArea (10, 10);
        jTextAreaInfo.setFont(this.fontGenerale);


        this.txtQst         = jTextAreaQst;
        this.txtInfoSupp    = jTextAreaInfo;
        this.scrollPane     = new JScrollPane(this.txtQst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.btnEnregistrer = new JButton    ("Enregistrer");
        this.btnAdd         = new JButton    (new ImageIcon("src/data/img/add.png"));
        this.btnInfoSupp    = new JButton    (new ImageIcon("src/data/img/edit.png"));
        this.btg            = new ButtonGroup();

        if(data.qst() == null){
            for( int cpt = 0; cpt < 2; cpt ++) {
                JTextArea jTextAreaRep = new JTextArea (3, 1);
                jTextAreaRep.setFont(this.fontGenerale);

                this.lstBtnSupp         .add(new JButton());
                this.lstTxtReponses     .add(jTextAreaRep);
                this.lstScrollTexte     .add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
                this.lstOrdrePrioQst    .add(new JTextField(3));
                this.lstPointEnMoins    .add(new JTextField(3));
                JRadioButton radioBouton = new JRadioButton();
                this.lstBtnValideReponse.add(radioBouton);
                this.btg.add(radioBouton);

                this.lstOrdrePrioQst.get(cpt).getDocument().addDocumentListener(this);
                this.lstPointEnMoins.get(cpt).getDocument().addDocumentListener(this);
            }
        }else{
            this.ajoutTextePourModif();
            this.ajoutElementModifier();
        }

        this.btnEnregistrer.setFont(this.fontGeneraleGras);

        this.majIHM();

        this.btnEnregistrer.addActionListener(this);
        this.btnAdd        .addActionListener(this);
        this.btnInfoSupp   .addActionListener(this);

        for ( int cpt = 0; cpt < this.lstBtnSupp.size(); cpt ++)
            this.lstBtnSupp.get(cpt).addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAdd){
            JTextArea jTextAreaRep = new JTextArea (3, 1);
            jTextAreaRep.setFont(this.fontGenerale);

            this.lstBtnSupp         .add(new JButton());
            this.lstTxtReponses     .add(jTextAreaRep);
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
            if (e.getSource() == this.lstBtnSupp.get(cpt)){
                // Bloque la suppression s'il y a moins de 2 cases après la suppression
                if ( this.lstBtnSupp.size() > 2 ) {
                    this.lstBtnSupp         .remove(cpt);
                    this.lstTxtReponses     .remove(cpt);
                    this.lstScrollTexte     .remove(cpt);
                    this.lstBtnValideReponse.remove(cpt);
                    this.lstOrdrePrioQst    .remove(cpt);
                    this.lstPointEnMoins    .remove(cpt);

                    this.majIHM();
                } else{
                    JOptionPane.showMessageDialog(this,  "Erreur : Il doit y avoir au minimum 2 réponses possible", "Erreur Suppression ", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }

        if(e.getSource() == this.btnInfoSupp){
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

        for (int i = 0; i < this.lstBtnValideReponse.size(); i++) {
            if(e.getSource() == this.lstBtnValideReponse.get(i)){
                this.lstOrdrePrioQst.get(i).setEnabled(false);
                this.lstPointEnMoins.get(i).setEnabled(false);
            }
            if(!this.lstBtnValideReponse.get(i).isSelected()){
                this.lstOrdrePrioQst.get(i).setEnabled(true);
                this.lstPointEnMoins.get(i).setEnabled(true);
            }
        }

        if ( e.getSource() == this.btnEnregistrer)
        {
            if ( this.txtQst.getText().isEmpty() || this.txtQst.getText().trim().isEmpty() ) {
                this.afficherMessageErreur("Erreur : Aucun texte n'est entré pour la question");
                return;
            }

            for (JTextArea lstTxtReponse : this.lstTxtReponses)
                if (lstTxtReponse.getText().isEmpty() || lstTxtReponse.getText().trim().isEmpty()){
                    this.afficherMessageErreur("Erreur : Aucun texte n'est entré pour l'une des réponse");
                    return;
                }

            int cptBoutonValide = 0;
            for (JRadioButton jRadioButton : this.lstBtnValideReponse)
                if (jRadioButton.isSelected()){
                    cptBoutonValide++;
                    for (int i = 0; i < this.lstBtnValideReponse.toArray().length; i++) {
                        this.lstOrdrePrioQst.get(i).setEnabled(false);
                        this.lstPointEnMoins.get(i).setEnabled(false);
                    }
                }

            if ( cptBoutonValide == 0 ){
                this.afficherMessageErreur("Erreur : Il faut au moins sélectionner un réponse valide");
                return;
            }

            List<Integer> ordreSuppressionMauvaiseRep = new ArrayList<Integer>();
            Double        pointSoustrait;
            Double        sommePointSoustrait         = 0.0;

            for (int cpt = 0; cpt < this.lstOrdrePrioQst.size(); cpt ++){
                try{
                    if ( !(this.lstOrdrePrioQst.get(cpt).getText().trim().isEmpty() && this.lstPointEnMoins.get(cpt).getText().trim().isEmpty()) ){

                        System.out.println(this.lstOrdrePrioQst.get(cpt).getText());
                        System.out.println(this.lstPointEnMoins.get(cpt).getText());

                        ordreSuppressionMauvaiseRep.add(Integer.parseInt  (this.lstOrdrePrioQst.get(cpt).getText().trim()));
                        pointSoustrait                = Double.parseDouble(this.lstPointEnMoins.get(cpt).getText().trim());

                        if ( pointSoustrait < 0 )
                            sommePointSoustrait += pointSoustrait;
                        else{
                            this.afficherMessageErreur("Erreur : Le nombre de point à enlevé doit être négatif -> (-0.5)");
                            return;
                        }


                        if ( this.data.nbPoints() + sommePointSoustrait < 0 ){
                            this.afficherMessageErreur("Erreur : Trop de points sont enlevé");
                            return;
                        }
                    }
                } catch (Exception exception){
                    System.out.println(exception.getMessage());
                    this.afficherMessageErreur("Erreur : Soit les deux cases de priorité d'ordre et point en moins doivent contenir des données soit aucune");
                    return;
                }
            }

            // Trie dans l'ordre croissant l'ordre des suppressions
            ordreSuppressionMauvaiseRep.sort(Comparator.naturalOrder());

            for (int cpt = 1; cpt <= ordreSuppressionMauvaiseRep.size(); cpt++) {
                if ( ordreSuppressionMauvaiseRep.get(cpt-1) != cpt ) {
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

            String txtQuestion = this.txtQst.getText().trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t");

            // On ajoute la nouvelle question dans la base de données
            if (this.data.qst() == null){

                Question question = new Question(txtQuestion, this.data.tempsReponse(), this.data.nbPoints(), this.data.type(), eliminationReponse, this.data.diff(), this.data.notion());

                if (!this.ctrl.sauvegarderQuestion(question)) {
                    this.afficherMessageErreur("Impossible de sauvegarder la réponse dans la base de données");
                    return;
                }

                this.data.notion().ajouterQuestion(question);

                this.afficherMessageValide("La question a bien été sauvegardée dans la base de données !");

            } else { // On modifie la question dans la base de données

                Question question = new Question(this.data.qst().getUID(), txtQuestion, this.data.tempsReponse(), this.data.nbPoints(), this.data.type(), eliminationReponse, this.data.diff(), this.data.notion());

                if (!this.ctrl.modifierQuestion(question)) {
                    this.afficherMessageErreur("Impossible de modifier la réponse dans la base de données");
                    return;
                }

                this.data.notion().modifierQuestion(question);

                this.afficherMessageValide("La question a bien été modifiée dans la base de données !");
            }
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
        JLabel labelQst = new JLabel("Question");
        labelQst.setFont(this.fontGeneraleGras);
        this.add(labelQst, gbc);

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

        gbc.gridx = 1;
        gbc.gridy = 2 + ( this.lstTxtReponses.size() * 2 );;
        gbc.gridwidth = 3; // Étendre sur 3 colonnes
        gbc.weightx = 1.0; // Remplit l'espace restant
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout());
        pnl.add(this.btnInfoSupp, BorderLayout.WEST);
        pnl.add(this.btnEnregistrer, BorderLayout.EAST);
        this.add(pnl, gbc);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        for ( int cpt = 0; cpt < this.lstOrdrePrioQst.size(); cpt ++)
        {
            if (!this.lstOrdrePrioQst.get(cpt).getText().trim().isEmpty() || !this.lstPointEnMoins.get(cpt).getText().trim().isEmpty() )
            {
                if ( this.lstBtnValideReponse.get(cpt).isSelected())
                    this.btg.clearSelection();
                this.lstBtnValideReponse.get(cpt).setEnabled(false);
            }
            else
                this.lstBtnValideReponse.get(cpt).setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        for ( int cpt = 0; cpt < this.lstOrdrePrioQst.size(); cpt ++)
        {
            if (!this.lstOrdrePrioQst.get(cpt).getText().trim().isEmpty() || !this.lstPointEnMoins.get(cpt).getText().trim().isEmpty() )
            {
                if ( this.lstBtnValideReponse.get(cpt).isSelected())
                    this.btg.clearSelection();
                this.lstBtnValideReponse.get(cpt).setEnabled(false);
            }
            else
                this.lstBtnValideReponse.get(cpt).setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    public void ajoutTextePourModif()
    {
        EliminationReponse elimReponse = (EliminationReponse) this.data.qst().getReponse();

        for ( int cpt = 0; cpt < elimReponse.getNbReponse(); cpt ++)
        {
            JTextArea jTextAreaRep = new JTextArea (3, 1);
            jTextAreaRep.setFont(this.fontGenerale);

            this.lstBtnSupp         .add(new JButton());
            this.lstTxtReponses     .add(jTextAreaRep);
            this.lstScrollTexte     .add(new JScrollPane(this.lstTxtReponses.getLast(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            this.lstOrdrePrioQst    .add(new JTextField(3));
            this.lstPointEnMoins    .add(new JTextField(3));
            JRadioButton radioBouton = new JRadioButton();
            this.lstBtnValideReponse.add(radioBouton);
            this.btg                .add(radioBouton);

            this.lstOrdrePrioQst.getLast().getDocument().addDocumentListener(this);
            this.lstPointEnMoins.getLast().getDocument().addDocumentListener(this);
            this.lstBtnSupp     .getLast().addActionListener(this);
        }
    }

    public void ajoutElementModifier()
    {

        EliminationReponse eliminationReponse = (EliminationReponse) data.qst().getReponse();

        this.txtQst.setText(this.data.qst().getTexteQuestion());
        this.txtInfoSupp.setText(eliminationReponse.getTexteExplication());

        for ( int cpt = 0; cpt < eliminationReponse.getNbReponse(); cpt ++)
        {
            EliminationReponseItem item = eliminationReponse.getReponseItem(cpt);

            this.lstTxtReponses .get(cpt).setText( item.getTexte() );

            if ( item.getPtsSuppression() != 0 )
            {
                this.lstBtnValideReponse.get(cpt).setEnabled(false);
                this.lstPointEnMoins.get(cpt).setText( String.valueOf(item.getPtsSuppression()));
            }

            if ( item.getPtsSuppression() != 0.0 )
            {
                this.lstBtnValideReponse.get(cpt).setEnabled(false);
                this.lstOrdrePrioQst.get(cpt).setText(String.valueOf(item.getOrdreSuppression()));
            }

            if ( item.isBonneReponse() )
                this.lstBtnValideReponse.get(cpt).setSelected(true);

        }
    }
}