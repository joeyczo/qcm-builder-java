package qcm.vue;

import qcm.Controleur;
import qcm.metier.Ressource;
import qcm.metier.Notion;
import qcm.vue.donnees.GrilleDonneesNotion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelParametre extends JPanel implements ActionListener {

    private JPanel              panelHaut;

    private JTable              tblGrilleDonnees;
    private JComboBox<String>   ddlstRessource;
    private JButton             btnAjouterRessource;
    private JButton             btnAjouterNotion;

    private Font                fontGenerale;
    private Font                fontGeneraleGras;

    private Controleur          ctrl;

    public PanelParametre(Controleur ctrl) {

        this.ctrl             = ctrl;

        this.fontGenerale     = new Font("Arial",Font.PLAIN,16);
        this.fontGeneraleGras = new Font("Arial",Font.BOLD ,16);

        this.setLayout ( new BorderLayout() );

        String[] tabRessource = new String[this.ctrl.getNbRessource()+1];

        tabRessource[0] = "-- Choisir une ressource --";

        for (int i = 1; i <= this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i-1).getNomCourt();

        JScrollPane spGrilleDonnees;

        this.panelHaut        = new JPanel();

        this.ddlstRessource   = new JComboBox<>(tabRessource);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesNotion(this.ctrl, new Ressource("", "")) );

        this.tblGrilleDonnees.setFillsViewportHeight(true);
        this.tblGrilleDonnees.setRowHeight(30);
        this.tblGrilleDonnees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        this.btnAjouterRessource = new JButton("Ajouter une ressource");
        this.btnAjouterNotion    = new JButton("Ajouter une notion");
        spGrilleDonnees          = new JScrollPane( this.tblGrilleDonnees );

        this.btnAjouterNotion.setEnabled(false);

        this.panelHaut.add(this.ddlstRessource);
        this.panelHaut.add(this.btnAjouterRessource);
        this.panelHaut.add(this.btnAjouterNotion);

        this.add(this.panelHaut , BorderLayout.NORTH);
        this.add(spGrilleDonnees, BorderLayout.CENTER);

        this.btnAjouterNotion   .addActionListener(this);
        this.btnAjouterRessource.addActionListener(this);
        this.ddlstRessource     .addActionListener(this);

        this.tblGrilleDonnees.getTableHeader().setFont(this.fontGeneraleGras);
        this.tblGrilleDonnees                 .setFont(this.fontGenerale);
        this.ddlstRessource                   .setFont(this.fontGeneraleGras);
        this.btnAjouterNotion                 .setFont(this.fontGeneraleGras);
        this.btnAjouterRessource              .setFont(this.fontGeneraleGras);

    }

    /*  ------------------  */
    /*   Méthode d'action   */
    /*  ------------------  */

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.btnAjouterRessource) {

            JTextField txtCode      = new JTextField(5);
            JTextField txtRessource = new JTextField(15);
            JLabel     lblCode      = new JLabel("Code:");
            JLabel     lblRessource = new JLabel("Nom ressource:");
            JPanel     panelTemp    = new JPanel();

            String nomRessource;
            String code;

            txtCode     .setFont(this.fontGenerale);
            txtRessource.setFont(this.fontGenerale);
            lblCode     .setFont(this.fontGeneraleGras);
            lblRessource.setFont(this.fontGeneraleGras);

            panelTemp.add(lblCode);
            panelTemp.add(txtCode);
            panelTemp.add(Box.createHorizontalStrut(15));
            panelTemp.add(lblRessource);
            panelTemp.add(txtRessource);

            int resultat = JOptionPane.showConfirmDialog (null, panelTemp,
                    "Ajouter Ressource", JOptionPane.OK_CANCEL_OPTION);

            if (resultat == JOptionPane.CANCEL_OPTION || resultat == JOptionPane.CLOSED_OPTION)
                return;


            if ( txtCode.getText().isEmpty() || txtCode.getText().trim().isEmpty() ) {

                JOptionPane.showMessageDialog(this,  "Erreur : Nom de code vide", "Erreur lors de l'ajout de la ressource", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if ( txtRessource.getText().isEmpty() || txtRessource.getText().trim().isEmpty() ) {

                JOptionPane.showMessageDialog(this,  "Erreur : Nom de ressource vide", "Erreur lors de l'ajout de la ressource", JOptionPane.ERROR_MESSAGE);
                return;

            }

            code         = txtCode     .getText().trim();
            nomRessource = txtRessource.getText().trim();

            Ressource ressource = new Ressource(nomRessource, code);


            // On vérifie si le nom n'existe pas dans la base de données des ressources
            if (this.ctrl.getRessource(ressource.getNom()) != null) {

                JOptionPane.showMessageDialog(this,  "Erreur : nom de ressource existant", "Erreur lors de l'ajout de la ressource", JOptionPane.ERROR_MESSAGE);
                return;

            }

            this.ctrl          .ajouterRessource(ressource);
            this.ddlstRessource.addItem(ressource.getNomCourt());

        }

        if (e.getSource() == this.btnAjouterNotion) {

            if (this.ctrl.getNbRessource() != 0 && this.ddlstRessource.getSelectedIndex() != 0) {

                JLabel     lblNotion = new JLabel("Nom notion:");
                JTextField txtNotion = new JTextField(15);
                JPanel     panelTemp = new JPanel();

                String nomNotion;

                lblNotion.setFont(this.fontGeneraleGras);
                txtNotion.setFont(this.fontGenerale);

                panelTemp.add(lblNotion);
                panelTemp.add(txtNotion);
                panelTemp.add(Box.createHorizontalStrut(15));


                int resultat = JOptionPane.showConfirmDialog (null, panelTemp,
                        "Ajouter Ressource", JOptionPane.OK_CANCEL_OPTION);

                if (resultat == JOptionPane.CANCEL_OPTION || resultat == JOptionPane.CLOSED_OPTION)
                    return;

                          nomNotion = txtNotion.getText().trim();
                Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

                if (ressource == null) {

                    JOptionPane.showMessageDialog(this,  "Erreur : Impossible de récupérer la ressource !", "Erreur lors de l'ajout de la notion", JOptionPane.ERROR_MESSAGE);
                    return;

                }

                Notion notion = new Notion(nomNotion, ressource);

                // On vérifie si le nom n'existe pas dans la base de données des notions pour la ressource sélectionnée
                if (this.ctrl.getNotion(ressource, notion.getNom()) != null) {

                    JOptionPane.showMessageDialog(this,  "Erreur : nom de notion existant", "Erreur lors de l'ajout de la notion", JOptionPane.ERROR_MESSAGE);
                    return;

                }

                if ( nomNotion.isEmpty() || nomNotion.trim().isEmpty() ) {

                    JOptionPane.showMessageDialog(this,  "Erreur : Nom de notion vide", "Erreur lors de l'ajout de la notion", JOptionPane.ERROR_MESSAGE);
                    return;

                }

                this.ctrl            .ajouterNotion(ressource, notion);
                this.tblGrilleDonnees.setModel(new GrilleDonneesNotion(this.ctrl, ressource));

            }

        }

        if (e.getSource() == this.ddlstRessource) {

            Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

            this.tblGrilleDonnees.setModel(new GrilleDonneesNotion(this.ctrl, ressource));

            if ( this.ddlstRessource.getSelectedIndex() == 0 )
                this.btnAjouterNotion.setEnabled(false);
            else
                this.btnAjouterNotion.setEnabled(true);

        }

    }
}

// TODO mettre que les stats sont à titre indicatif et juste des stats
