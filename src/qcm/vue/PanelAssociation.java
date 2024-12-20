package qcm.vue;

import qcm.Controleur;
import qcm.metier.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelAssociation extends JPanel implements ActionListener
{
    private JTextArea   txtQuestion;
    private JScrollPane scQuestion;

    private DonneesCreationQuestion data;
    private Controleur              ctrl;
    private FrameInfosQuestion      frameParent;

    private ArrayList<JTextArea> lstTxtReponses;
    private ArrayList<JTextArea> lstTxtDefinitions;
    private ArrayList<JButton>   lstBtnSupprimerGauche;

    private JButton   btnAjouter;
    private JButton   btnExplication;
    private JButton   btnEnregistrer;
    private JTextArea txtInfoSupp;

    private Font      fontGenerale;
    private Font      fontGeneraleGras;


    public PanelAssociation(DonneesCreationQuestion data, Controleur ctrl, FrameInfosQuestion frameInfosQuestion)
    {
        this.data             = data;
        this.ctrl             = ctrl;
        this.frameParent      = frameInfosQuestion;
        this.fontGenerale     = new Font("Arial", Font.PLAIN, 16);
        this.fontGeneraleGras = new Font("Arial", Font.BOLD , 16);


        JTextArea jTextAreaQst = new JTextArea (4, 1);
        jTextAreaQst.setFont(this.fontGenerale);

        JTextArea jTextAreaInfo = new JTextArea (10, 10);
        jTextAreaInfo.setFont(this.fontGenerale);

        this.txtQuestion = jTextAreaQst;
        this.txtInfoSupp = jTextAreaInfo;
        this.scQuestion  = new JScrollPane(this.txtQuestion, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.lstTxtReponses        = new ArrayList<>();
        this.lstTxtDefinitions     = new ArrayList<>();
        this.lstBtnSupprimerGauche = new ArrayList<>();

        this.btnAjouter = new JButton();
        this.btnAjouter.setOpaque(false);
        this.btnAjouter.setContentAreaFilled(false);
        this.btnAjouter.setBorderPainted(false);
        this.btnAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnAjouter.setIcon(new ImageIcon("src/data/img/add.png"));

        this.btnExplication = new JButton();
        this.btnExplication.setOpaque(false);
        this.btnExplication.setContentAreaFilled(false);
        this.btnExplication.setBorderPainted(false);
        this.btnExplication.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnExplication.setIcon(new ImageIcon("src/data/img/edit.png"));

        this.btnEnregistrer = new JButton("Enregistrer");

        this.addDefinition("");
        this.addReponse   ("");
        this.addDefinition("");
        this.addReponse   ("");

        this.setLayout(new GridBagLayout());

        this.btnAjouter    .addActionListener(this);
        this.btnExplication.addActionListener(this);
        this.btnEnregistrer.addActionListener(this);

        this.btnEnregistrer.setFont(this.fontGeneraleGras);

        this.majIHM();

        if (this.data.qst() != null)
            this.chargerDonnees();

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAjouter) {
            this.addDefinition("");
            this.addReponse("");
            this.majIHM();
        }

        if (e.getSource() == this.btnExplication) {
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

        if (e.getSource() == this.btnEnregistrer) {

            // Vérification des données

            if (this.txtQuestion.getText().isEmpty() || this.txtQuestion.getText().trim().isEmpty()) {
                this.afficherMessageErreur("Veuillez indiquer le texte de la question");
                return;
            }

            for (JTextArea reponse : this.lstTxtReponses)
                if (reponse.getText().isEmpty() || reponse.getText().trim().isEmpty()) {
                    this.afficherMessageErreur("Veuillez remplir toutes les réponses !");
                    return;
                }

            for (JTextArea def : this.lstTxtDefinitions)
                if (def.getText().isEmpty() || def.getText().trim().isEmpty()) {
                    this.afficherMessageErreur("Veuillez remplir toutes les définitions !");
                    return;
                }

            AssociationReponse associationReponse = new AssociationReponse();

            if (!this.txtInfoSupp.getText().isEmpty() || !this.txtInfoSupp.getText().trim().isEmpty()) {
                associationReponse.ajouterTexteExplication(this.txtInfoSupp.getText().trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t"));
            }

            // Association des définitions <> réponses
            for (int i = 0; i < this.lstTxtDefinitions.size(); i++) {

                AssociationReponseItem reponse = new AssociationReponseItem(this.lstTxtReponses.get(i).getText().trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t"));
                AssociationReponseItem definition = new AssociationReponseItem(reponse, this.lstTxtDefinitions.get(i).getText().trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t"));

                associationReponse.ajouterDefinition(definition);
            }

            String txtQuestion = this.txtQuestion.getText().trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t");

            // On ajoute la nouvelle question dans la base de données
            if (this.data.qst() == null) {

                Question question = new Question(txtQuestion, this.data.tempsReponse(), this.data.nbPoints(), this.data.type(), associationReponse, this.data.diff(), this.data.notion());

                if (!this.ctrl.sauvegarderQuestion(question)) {
                    this.afficherMessageErreur("Impossible de sauvegarder la question dans la base de données !");
                    return;
                }

                this.data.notion().ajouterQuestion(question);

                this.afficherMessageValide("La question a bien été sauvegardée dans la base de données");

            } else { // On modifie la question dans la base de données

                Question question = new Question(this.data.qst().getUID(), txtQuestion, this.data.tempsReponse(), this.data.nbPoints(), this.data.type(), associationReponse, this.data.diff(), this.data.notion());

                if (!this.ctrl.modifierQuestion(question)) {
                    this.afficherMessageErreur("Impossible de modifier la question dans la base de données !");
                    return;
                }

                this.data.notion().modifierQuestion(question);

                this.afficherMessageValide("La question a bien été modifiée dans la base de données");

            }


            this.frameParent.fermerFenetre();

        }
    }

    /**
     * Afficher un message d'erreur à l'utilisateur
     *
     * @param message Message à afficher
     */
    private void afficherMessageErreur(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Impossible de sauvegarder la réponse", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Afficher un message de validation à l'utilisateur
     *
     * @param message Message à afficher
     */
    private void afficherMessageValide(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }




    private void addReponse(String text)
    {
        JTextArea   newReponse = new JTextArea(text, 5, 20);
        newReponse.setFont(this.fontGenerale);
        JScrollPane scReponse  = new JScrollPane(newReponse, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.lstTxtReponses.add(newReponse);
    }

    private void addDefinition(String text)
    {
        JTextArea newDefinition = new JTextArea(text, 5, 20);
        newDefinition.setFont(this.fontGenerale);
        JScrollPane scDefinition = new JScrollPane(newDefinition, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton btnSupprimerGauche = new JButton();
        btnSupprimerGauche.setOpaque(false);
        btnSupprimerGauche.setContentAreaFilled(false);
        btnSupprimerGauche.setBorderPainted(false);
        btnSupprimerGauche.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSupprimerGauche.setIcon(new ImageIcon("src/data/img/delete.png"));

        btnSupprimerGauche.addActionListener(e -> {
            if (this.lstTxtDefinitions.size() > 2) {
                int index = this.lstBtnSupprimerGauche.indexOf(btnSupprimerGauche);
                if (index >= 0) {
                    this.lstTxtDefinitions.remove(index);
                    this.lstTxtReponses.remove(index);
                    this.lstBtnSupprimerGauche.remove(index);
                    majIHM();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Il doit rester au moins deux lignes.");
            }
        });

        this.lstTxtDefinitions.add(newDefinition);
        this.lstBtnSupprimerGauche.add(btnSupprimerGauche);
    }

    private void majIHM()
    {
        this.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal par défaut

        // Label "Question"
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel labelQst = new JLabel("Question");
        labelQst.setFont(this.fontGeneraleGras);
        this.add(labelQst, gbc);

        // Zone de texte pour la question
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4; // Étendre sur 4 colonnes
        gbc.weightx = 1.0; // Donne la priorité pour occuper l'espace
        gbc.fill = GridBagConstraints.BOTH; // Remplissage horizontal et vertical pour la zone de question
        this.add(this.scQuestion, gbc);

        int yIndex = 2;

        for (int i = 0; i < this.lstTxtDefinitions.size(); i++) {
            // Bouton supprimer gauche
            gbc.gridx = 0;
            gbc.gridy = yIndex;
            gbc.gridwidth = 1; // Remet à 1 pour ce composant
            gbc.weightx = 0; // Pas d'extension pour le bouton
            gbc.fill = GridBagConstraints.NONE; // Pas de remplissage
            this.add(this.lstBtnSupprimerGauche.get(i), gbc);

            // Zone de texte pour la définition
            gbc.gridx = 1;
            gbc.gridy = yIndex;
            gbc.gridwidth = 1;
            gbc.weightx = 0.5; // Équilibre l'espace avec la réponse
            gbc.fill = GridBagConstraints.BOTH; // Remplissage dans les deux directions
            JScrollPane scDefinition = new JScrollPane(this.lstTxtDefinitions.get(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scDefinition, gbc);

            // Zone de texte pour la réponse
            gbc.gridx = 3;
            gbc.gridy = yIndex;
            gbc.gridwidth = 1;
            gbc.weightx = 0.5;
            gbc.fill = GridBagConstraints.BOTH;
            JScrollPane scReponse = new JScrollPane(this.lstTxtReponses.get(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scReponse, gbc);

            yIndex++;
        }

        // Bouton ajouter
        gbc.gridx = 0;
        gbc.gridy = yIndex;
        gbc.gridwidth = 1; // Remet à 1 pour les boutons
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        this.add(this.btnAjouter, gbc);

        // Panneau pour les boutons d'explication et d'enregistrement
        gbc.gridx = 1;
        gbc.gridy = yIndex;
        gbc.gridwidth = 3; // Étendre sur 3 colonnes
        gbc.weightx = 1.0; // Remplit l'espace restant
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout());
        pnl.add(this.btnExplication, BorderLayout.WEST);
        pnl.add(this.btnEnregistrer, BorderLayout.EAST);
        this.add(pnl, gbc);

        this.revalidate();
        this.repaint();
    }

    private void chargerDonnees()
    {
        Question q = this.data.qst();
        this.txtQuestion.setText(q.getTexteQuestion());
        AssociationReponse associationReponse = (AssociationReponse) q.getReponse();

        for(int i = 0 ; i < associationReponse.getNbReponses(); i++) {
            AssociationReponse a = (AssociationReponse) q.getReponse();
            AssociationReponseItem ai = a.getReponseItem(i);

            if(i >= 2){
                this.addDefinition(ai.getTexte());
                this.addReponse(ai.getReponse().getTexte());
            } else {
                this.lstTxtDefinitions.get(i).setText(ai.getTexte());
                this.lstTxtReponses.get(i).setText(ai.getReponse().getTexte());
            }
        }

        this.txtInfoSupp.setText(q.getReponse().getTexteExplication());
        majIHM();
    }
}
