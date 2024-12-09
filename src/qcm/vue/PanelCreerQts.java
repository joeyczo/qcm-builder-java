package qcm.vue;

import qcm.metier.*;
import qcm.vue.*;
import qcm.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCreerQts extends JPanel implements ActionListener
{
    private FrameCreerQts frameParent;
    private Controleur ctrl;

    private JTextField txtNbPoints;
    private JTextField txtTempsRep;

    private JComboBox<String> cmbRessources;
    private JComboBox<String> cmbNotions;

    private JButton btnTFacile;
    private JButton btnFacile;
    private JButton btnMoyen;
    private JButton btnDifficile;

    private JButton btnValider;

    public PanelCreerQts(FrameCreerQts parent, Controleur ctrl)
    {
        this.frameParent = parent;
        this.ctrl = ctrl;

        // Initialisation des composants
        this.txtNbPoints = new JTextField("1.0", 5); // par défaut "1.0" pour le nombre de points
        this.txtTempsRep = new JTextField("00:30", 5); // par défaut "00:30" pour le temps

        this.cmbRessources = new JComboBox<>();
        this.cmbRessources.addItem("-- Choisir une ressource --");
        this.cmbNotions    = new JComboBox<>();
        this.cmbNotions.addItem("-- Choisir une notion --");
        this.cmbNotions.setEnabled(false);

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
            this.cmbRessources.addItem(this.ctrl.getRessource(i).getNom());


        this.btnTFacile   = createRoundButton(Color.GREEN, "TF");
        this.btnFacile    = createRoundButton(Color.BLUE , "T");
        this.btnMoyen     = createRoundButton(Color.RED  , "M");
        this.btnDifficile = createRoundButton(Color.GRAY , "D");

        this.btnValider = new JButton("Valider");

        // Configurer le layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // Pour que les composants prennent toute la largeur de leur cellule

        // Ligne 1 : Label et champs texte pour nombre de points et temps de réponse
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("nombre de points"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(txtNbPoints, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(new JLabel("temps de réponse (min:sec)"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        this.add(txtTempsRep, gbc);

        // Ligne 2 : Label et JComboBox pour Ressource
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("ressource"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(cmbRessources, gbc);

        // Ligne 3 : Label et JComboBox pour Notion
        gbc.gridx = 2;
        gbc.gridy = 1;
        this.add(new JLabel("notion"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Pour étendre la largeur de la combobox
        this.add(cmbNotions, gbc);
        gbc.weightx = 0.0; // Réinitialiser après utilisation

        // Ligne 4 : Label et boutons de niveaux
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(new JLabel("niveau"), gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        this.add(btnTFacile, gbc);

        gbc.gridx = 6;
        gbc.gridy = 1;
        this.add(btnFacile, gbc);

        gbc.gridx = 7;
        gbc.gridy = 1;
        this.add(btnMoyen, gbc);

        gbc.gridx = 8;
        gbc.gridy = 1;
        this.add(btnDifficile, gbc);

        //Ligne 5 : Bouton Valider
        gbc.gridx = 5;
        gbc.gridy = 5;
        this.add(this.btnValider);

        this.cmbRessources  .addActionListener(this);
        this.cmbNotions     .addActionListener(this);

    }

    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == this.cmbRessources) {

            if (this.cmbRessources.getSelectedIndex() != 0) {

                this.cmbNotions.removeAllItems();

                Ressource rsc = this.ctrl.getRessource((String) this.cmbRessources.getSelectedItem());

                if (rsc == null) return;

                for (Notion n : rsc.getAlNotion())
                    this.cmbNotions.addItem(n.getNom());

                this.cmbNotions.setEnabled(true);

            }

        }

    }

    // Méthode pour créer un bouton rond
    private JButton createRoundButton(Color color, String text)
    {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(30, 30)); // Taille fixe de 30x30
        button.setBackground(color);
        button.setText(text);
        button.setBorderPainted(false); // Supprimer les bordures
        button.setFocusPainted(false); // Supprimer la bordure du focus
        button.setContentAreaFilled(false); // Supprimer la zone remplie
        button.setOpaque(true); // Pour que la couleur de fond soit visible
        return button;
    }
}