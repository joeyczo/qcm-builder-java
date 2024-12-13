package qcm.vue;

import qcm.Controleur;
import qcm.metier.AssociationReponse;
import qcm.metier.AssociationReponseItem;
import qcm.metier.DonneesCreationQuestion;
import qcm.metier.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class PanelAssociation extends JPanel implements ActionListener {
    private JTextArea               txtQuestion;
    private JScrollPane             scQuestion;

    private DonneesCreationQuestion data;
    private Controleur              ctrl;
    private FrameInfosQuestion      frameParent;

    private ArrayList<JTextArea>    lstTxtReponses;
    private ArrayList<JTextArea>    lstTxtDefinitions;
    private ArrayList<JButton>      lstBtnSupprimerGauche;

    private JButton                 btnAjouter;
    private JButton                 btnExplication;
    private JButton                 btnEnregistrer;
    private JTextArea               txtInfoSupp;

    public PanelAssociation(DonneesCreationQuestion data, Controleur ctrl, FrameInfosQuestion frameInfosQuestion) {

        this.data           = data;
        this.ctrl           = ctrl;
        this.frameParent    = frameInfosQuestion;

        this.txtQuestion  = new JTextArea("Ajouter une question", 10, 20);
        this.txtInfoSupp  = new JTextArea (2, 1);
        this.scQuestion   = new JScrollPane(this.txtQuestion, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.lstTxtReponses = new ArrayList<>();
        this.lstTxtDefinitions = new ArrayList<>();
        this.lstBtnSupprimerGauche = new ArrayList<>();

        this.btnAjouter = new JButton("Ajouter");
        this.btnExplication = new JButton("Explication");
        this.btnEnregistrer = new JButton("Enregistrer");

        Dimension buttonSize = new Dimension(80, 30);
        this.btnAjouter.setPreferredSize(buttonSize);
        this.btnExplication.setPreferredSize(buttonSize);
        this.btnEnregistrer.setPreferredSize(buttonSize);

        // Ajouter deux réponses et définitions par défaut
        addDefinition("Ajouter une définition");
        addReponse("Ajouter une réponse");
        addDefinition("Ajouter une autre définition");
        addReponse("Ajouter une autre réponse");

        // Layout
        this.setLayout(new GridBagLayout());

        // Activation des événements
        this.btnAjouter.addActionListener(this);
        this.btnExplication.addActionListener(this);
        this.btnEnregistrer.addActionListener(this);

        // Afficher l'IHM
        majIHM();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnAjouter) {
            // Ajouter une nouvelle définition et une nouvelle réponse
            addDefinition("Ajouter une définition");
            addReponse("Ajouter une réponse");
            majIHM();
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
                associationReponse.ajouterTexteExplication(this.txtInfoSupp.getText().trim());
            }

            // Association des définitions <> réponses
            for ( int i = 0; i < this.lstTxtDefinitions.size(); i++) {

                AssociationReponseItem reponse      = new AssociationReponseItem(this.lstTxtReponses.get(i).getText().trim());
                AssociationReponseItem definition   = new AssociationReponseItem(reponse, this.lstTxtDefinitions.get(i).getText().trim());

                associationReponse.ajouterDefinition(definition);
            }

            Question question = new Question(this.txtQuestion.getText().trim(), this.data.tempsReponse(), this.data.nbPoints(), this.data.type(), associationReponse, this.data.diff(), this.data.notion());

            this.data.notion().ajouterQuestion(question);

            if (!this.ctrl.sauvegarderQuestion(question)) {
                this.afficherMessageErreur("Impossible de sauvegarder la question dans la base de données !");
                return;
            }

            this.afficherMessageValide("La question a bien été sauvegardé");
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

    private void addReponse(String text) {
        // Augmenter la taille de la JTextArea à 5 lignes
        JTextArea newReponse = new JTextArea(text, 5, 20);
        JScrollPane scReponse = new JScrollPane(newReponse, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.lstTxtReponses.add(newReponse);
    }

    private void addDefinition(String text) {
        // Augmenter la taille de la JTextArea à 5 lignes
        JTextArea newDefinition = new JTextArea(text, 5, 20);
        JScrollPane scDefinition = new JScrollPane(newDefinition, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton btnSupprimerGauche = new JButton("X");
        btnSupprimerGauche.setPreferredSize(new Dimension(20, 20));

        btnSupprimerGauche.addActionListener(e -> {
            if (this.lstTxtDefinitions.size() > 2) {  // Ne permettre la suppression que si plus de 2 lignes
                int index = this.lstBtnSupprimerGauche.indexOf(btnSupprimerGauche);
                if (index >= 0) {
                    this.lstTxtDefinitions.remove(index);
                    this.lstTxtReponses.remove(index);
                    this.lstBtnSupprimerGauche.remove(index);
                    majIHM(); // Rafraîchir l'IHM après suppression
                }
            } else {
                JOptionPane.showMessageDialog(this, "Il doit rester au moins deux lignes.");
            }
        });

        this.lstTxtDefinitions.add(newDefinition);
        this.lstBtnSupprimerGauche.add(btnSupprimerGauche);
    }

    private void majIHM() {
        this.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Question :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        this.add(this.scQuestion, gbc);

        gbc.gridwidth = 1;
        int yIndex = 2;

        for (int i = 0; i < this.lstTxtDefinitions.size(); i++) {
            gbc.gridx = 0;
            gbc.gridy = yIndex;
            this.add(this.lstBtnSupprimerGauche.get(i), gbc);

            gbc.gridx = 1;
            JScrollPane scDefinition = new JScrollPane(this.lstTxtDefinitions.get(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            gbc.weightx = 1.0;
            this.add(scDefinition, gbc);

            gbc.gridx = 3;
            JScrollPane scReponse = new JScrollPane(this.lstTxtReponses.get(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            gbc.weightx = 1.0;
            this.add(scReponse, gbc);

            yIndex++;
        }

        gbc.gridx = 0;
        gbc.gridy = yIndex;
        gbc.gridwidth = 4;
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(this.btnAjouter);
        pnlButtons.add(this.btnExplication);
        pnlButtons.add(this.btnEnregistrer);
        this.add(pnlButtons, gbc);

        this.revalidate();
        this.repaint();
    }
}