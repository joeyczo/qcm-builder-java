package qcm.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelAssociation extends JPanel implements ActionListener
{
    private JTextArea txtQuestion;
    private JScrollPane scQuestion;

    private ArrayList<JTextArea> lstTxtReponses;
    private ArrayList<JTextArea> lstTxtDefinitions;
    private ArrayList<JButton> lstBtnSupprimerGauche; // Boutons à gauche
    private ArrayList<JButton> lstBtnSupprimerDroite; // Boutons à droite

    private ArrayList<Point[]> lstAssociations;

    private JButton btnAjouter;
    private JButton btnRelier;
    private JButton btnExplication;

    private JTextArea selectedDefinition;
    private JTextArea selectedReponse;
    private boolean relierMode;

    public PanelAssociation()
    {
        this.txtQuestion = new JTextArea("Ajouter une question", 10, 20);
        this.scQuestion = new JScrollPane(this.txtQuestion, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.lstTxtReponses = new ArrayList<>();
        this.lstTxtDefinitions = new ArrayList<>();
        this.lstBtnSupprimerGauche = new ArrayList<>();
        this.lstBtnSupprimerDroite = new ArrayList<>();
        this.lstAssociations = new ArrayList<>();

        this.btnAjouter = new JButton("Ajouter");
        this.btnRelier = new JButton("Relier");
        this.btnExplication = new JButton("Explication");

        Dimension buttonSize = new Dimension(80, 30); // Par exemple, 80x30
        this.btnAjouter.setPreferredSize(buttonSize);
        this.btnRelier.setPreferredSize(buttonSize);
        this.btnExplication.setPreferredSize(buttonSize);

        // Ajouter deux réponses et définitions par défaut
        addDefinition("Ajouter une définition");
        addReponse("Ajouter une réponse");

        // Layout
        this.setLayout(new GridBagLayout());

        // Activation des événements
        this.btnAjouter.addActionListener(this);
        this.btnRelier.addActionListener(this);
        this.btnExplication.addActionListener(this);

        // Afficher l'IHM
        majIHM();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAjouter) {
            // Ajouter une nouvelle définition et une nouvelle réponse
            addDefinition("Ajouter une définition");
            addReponse("Ajouter une réponse");
            majIHM();
        } else if (e.getSource() == this.btnRelier) {
            // Activer le mode relier
            this.relierMode = !this.relierMode;
            if (relierMode) {
                this.btnRelier.setText("Relier (actif)");
            } else {
                this.btnRelier.setText("Relier");
                deselectionner();
            }
        } else {
            // Gestion de la sélection des définitions/réponses en mode relier
            for (int i = 0; i < this.lstTxtDefinitions.size(); i++) {
                if (e.getSource() == this.lstTxtDefinitions.get(i)) {
                    if (relierMode) {
                        selectDefinition(this.lstTxtDefinitions.get(i));
                    }
                }
            }

            for (int i = 0; i < this.lstTxtReponses.size(); i++) {
                if (e.getSource() == this.lstTxtReponses.get(i)) {
                    if (relierMode) {
                        selectReponse(this.lstTxtReponses.get(i));
                    }
                }
            }
        }

        if(e.getSource() == this.btnExplication){
            JTextArea textArea = new JTextArea(10, 30);
            JScrollPane scrollPane = new JScrollPane(textArea);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Ajouter explication",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String explication = textArea.getText();
                System.out.println("Explication entrée : " + explication);
            }
        }
    }

    private void selectDefinition(JTextArea definition) {
        if (this.selectedDefinition != null) {
            this.selectedDefinition.setBackground(Color.WHITE); // Désélectionner la précédente
        }
        this.selectedDefinition = definition;
        this.selectedDefinition.setBackground(Color.YELLOW); // Mettre en surbrillance
        checkIfLinked();
    }

    private void selectReponse(JTextArea reponse) {
        if (this.selectedReponse != null) {
            this.selectedReponse.setBackground(Color.WHITE); // Désélectionner la précédente
        }
        this.selectedReponse = reponse;
        this.selectedReponse.setBackground(Color.YELLOW); // Mettre en surbrillance
        checkIfLinked();
    }

    private void checkIfLinked() {
        if (this.selectedDefinition != null && this.selectedReponse != null) {
            // Créer la liaison entre la définition et la réponse
            System.out.println("Définition reliée à la réponse : " +
                    this.selectedDefinition.getText() + " -> " + this.selectedReponse.getText());

            // Obtenir les positions des définitions et réponses
            Point defLocation = this.selectedDefinition.getLocationOnScreen();
            Point repLocation = this.selectedReponse.getLocationOnScreen();

            // Convertir les points de l'écran en points relatifs au panel
            SwingUtilities.convertPointFromScreen(defLocation, this);
            SwingUtilities.convertPointFromScreen(repLocation, this);

            // Ajuster les points pour partir du bord droit de la définition et arriver au bord gauche de la réponse
            int defWidth = this.selectedDefinition.getWidth();
            int repWidth = this.selectedReponse.getWidth();

            // Ajuster les points (bord droit de la définition, bord gauche de la réponse)
            Point start = new Point(defLocation.x + defWidth + 10, defLocation.y + this.selectedDefinition.getHeight() / 2);  // Ajouter un peu d'espace (10px) à droite de la définition
            Point end = new Point(repLocation.x - 10, repLocation.y + this.selectedReponse.getHeight() / 2);  // Ajouter un peu d'espace (10px) à gauche de la réponse

            // Ajouter cette association à la liste
            this.lstAssociations.add(new Point[]{start, end});

            // Réinitialiser la sélection
            deselectionner();
            // Mettre à jour l'IHM
            repaint(); // Pour redessiner les lignes
        }
    }

    private void deselectionner() {
        if (this.selectedDefinition != null) {
            this.selectedDefinition.setBackground(Color.WHITE);
        }
        if (this.selectedReponse != null) {
            this.selectedReponse.setBackground(Color.WHITE);
        }
        this.selectedDefinition = null;
        this.selectedReponse = null;
    }

    private void addReponse(String text)
    {
        // Ajouter une nouvelle réponse avec un bouton de suppression
        JTextArea newReponse = new JTextArea(text, 2, 20);
        JScrollPane scReponse = new JScrollPane(newReponse, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //newReponse.setEditable(false);

        JButton btnSupprimerDroite = new JButton("X");

        // Rendre les boutons plus petits (par exemple 20x20)
        btnSupprimerDroite.setPreferredSize(new Dimension(20, 20));

        // Ajouter un action listener pour sélectionner en mode relier
        newReponse.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (relierMode) {
                    selectReponse(newReponse);
                }
            }
        });

        this.lstTxtReponses.add(newReponse);
        this.lstBtnSupprimerDroite.add(btnSupprimerDroite);
    }

    private void addDefinition(String text) {
        // Ajouter une nouvelle définition avec un bouton de suppression
        JTextArea newDefinition = new JTextArea(text, 2, 20);
        newDefinition.setPreferredSize(new Dimension(200, 50)); // Définir une taille uniforme pour tous les JTextArea
        JScrollPane scDefinition = new JScrollPane(newDefinition, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton btnSupprimerGauche = new JButton("X");
        btnSupprimerGauche.setPreferredSize(new Dimension(20, 20)); // Taille du bouton

        // Ajouter un action listener pour sélectionner en mode relier
        newDefinition.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (relierMode) {
                    selectDefinition(newDefinition);
                }
            }
        });

        this.lstTxtDefinitions.add(newDefinition);
        this.lstBtnSupprimerGauche.add(btnSupprimerGauche);
    }

    private void majIHM() {
        // Effacer toutes les anciennes réponses
        this.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Question :"), gbc);

        // Réafficher la question
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4; // La question prend toute la ligne
        this.add(this.scQuestion, gbc);

        // Réafficher les définitions et réponses avec leurs boutons
        gbc.gridwidth = 1; // Réinitialiser gridwidth
        int yIndex = 2;

        for (int i = 0; i < this.lstTxtDefinitions.size(); i++) {
            // Bouton de suppression à gauche de la définition
            gbc.gridx = 0;
            gbc.gridy = yIndex;
            this.add(this.lstBtnSupprimerGauche.get(i), gbc);

            // Première définition (colonne 1) avec taille uniforme
            gbc.gridx = 1;
            JScrollPane scDefinition = new JScrollPane(this.lstTxtDefinitions.get(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            gbc.weightx = 1.0;
            this.add(scDefinition, gbc);

            // Bouton de suppression à droite de la réponse
            gbc.gridx = 2;
            this.add(this.lstBtnSupprimerDroite.get(i), gbc);

            // Première réponse (colonne 3) avec taille uniforme
            gbc.gridx = 3;
            JScrollPane scReponse = new JScrollPane(this.lstTxtReponses.get(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            gbc.weightx = 1.0;
            this.add(scReponse, gbc);

            yIndex++;
        }

        // Ajouter le bouton "Ajouter" et "Relier"
        gbc.gridx = 0;
        gbc.gridy = yIndex;
        gbc.gridwidth = 4;
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(this.btnAjouter);
        pnlButtons.add(this.btnRelier);
        pnlButtons.add(this.btnExplication);
        this.add(pnlButtons, gbc);

        // Rafraîchir l'interface
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Configurer le Graphics pour dessiner les traits
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK); // Couleur des lignes
        g2d.setStroke(new BasicStroke(2)); // Épaisseur des lignes

        // Parcourir toutes les associations et dessiner les lignes
        for (Point[] association : lstAssociations) {
            Point start = association[0]; // Bord droit de la définition
            Point end = association[1];   // Bord gauche de la réponse
            g2d.drawLine(start.x, start.y, end.x, end.y); // Tracer un trait entre la définition et la réponse
        }
    }
}