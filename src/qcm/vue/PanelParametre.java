package qcm.vue;

import qcm.Controleur;
import qcm.metier.Ressource;
import qcm.metier.Notion;
import qcm.vue.donnees.GrilleDonneesNotion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelParametre extends JPanel implements ActionListener
{

    private FrameParametres     frameParent;

    private JTable              tblGrilleDonnees;
    private JComboBox<String>   ddlstRessource;

    private JButton             btnAjouterRessource;
    private JButton             btnAjouterNotion;

    private Controleur          ctrl;

    public PanelParametre(FrameParametres parent, Controleur ctrl)
    {
        this.frameParent = parent;
        this.ctrl        =   ctrl;

        this.setLayout ( new FlowLayout() );

        String[] tabRessource = new String[this.ctrl.getNbRessource()];

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i).getNomCourt();

        JScrollPane spGrilleDonnees;

        Ressource premiereRessource = (this.ctrl.getNbRessource() == 0) ? null : this.ctrl.getRessource(0);

        this.ddlstRessource   = new JComboBox<>(tabRessource);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesNotion(this.ctrl, premiereRessource) );
        this.tblGrilleDonnees.setFillsViewportHeight(true);
        
        this.btnAjouterRessource = new JButton("Ajouter une ressource");
        this.btnAjouterNotion    = new JButton("Ajouter une notion");

        spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );


        this.add(this.btnAjouterRessource);
        this.add(this.btnAjouterNotion);
        this.add(this.ddlstRessource);
        this.add(spGrilleDonnees);

        this.btnAjouterNotion   .addActionListener(this);
        this.btnAjouterRessource.addActionListener(this);
        this.ddlstRessource     .addActionListener(this);
    }

    /*  ------------------  */
    /*	 Méthode d'action   */
    /*  ------------------  */

    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == this.btnAjouterRessource)
        {
            String    nomRessource = JOptionPane.showInputDialog(this, "Entrez le nom de la ressource :", "Ajouter Ressource", JOptionPane.PLAIN_MESSAGE);

            if (nomRessource == null) return;
            nomRessource = nomRessource.trim();

            Ressource ressource    = new Ressource(nomRessource);

            // On vérifie si le nom n'existe pas dans la base de données des ressources
            if (this.ctrl.getRessource(ressource.getNom()) != null) {
                JOptionPane.showMessageDialog(this,  "Erreur : nom de ressource existant", "Erreur lors de l'ajout de la ressource", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Permets de vérifier que les données sont ok
            if (nomRessource.isEmpty()) {
                JOptionPane.showMessageDialog(this,  "Erreur : Nom de ressource vide", "Erreur lors de l'ajout de la ressource", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.ddlstRessource.addItem(ressource.getNomCourt());

            this.ctrl.ajouterRessource(ressource);

            System.out.println("Ressource ajoutée : " + nomRessource);
        }

        if (e.getSource() == this.btnAjouterNotion)
        {
            if (this.ctrl.getNbRessource() != 0)
            {
                String nomNotion = JOptionPane.showInputDialog(this, "Entrez le nom de la notion :", "Ajouter Notion", JOptionPane.PLAIN_MESSAGE);

                if (nomNotion == null)
                    return;

                nomNotion = nomNotion.trim();

                Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

                if (ressource == null) {
                    JOptionPane.showMessageDialog(this,  "Erreur : Impossible de récupérer la ressource !", "Erreur lors de l'ajout de la notion", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Notion    notion    = new Notion(nomNotion, ressource);

                // On vérifie si le nom n'existe pas dans la base de données des notions pour la ressource sélectionnée
                if (this.ctrl.getNotion(ressource, notion.getNom()) != null) {
                    JOptionPane.showMessageDialog(this,  "Erreur : nom de notion existant", "Erreur lors de l'ajout de la notion", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if ( nomNotion.isEmpty() || nomNotion.trim().isEmpty() ) {
                    JOptionPane.showMessageDialog(this,  "Erreur : Nom de notion vide", "Erreur lors de l'ajout de la notion", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                this.ctrl.ajouterNotion(ressource, notion);
                System.out.println("Notion ajoutée : " + nomNotion);
                this.tblGrilleDonnees.setModel(new GrilleDonneesNotion(this.ctrl, ressource));

            }
        }

        if (e.getSource() == this.ddlstRessource) {

            Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

            this.tblGrilleDonnees.setModel(new GrilleDonneesNotion(this.ctrl, ressource));

        }

    }
}

// TODO sauvegarder les qst
// TODO relier les données (tf, f, m, d) avec les paramètres
// TODO mettre que les stats sont a titre indicatif et juste des stats
// TODO si , dans creation de ressource ( param ) remlpacer par "|"
// TODO prbl si pas de notion
// TODO gérer le temps
// TODO vérifier si les commentaires erreur n'ont pas de faute
