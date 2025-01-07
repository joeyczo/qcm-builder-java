package qcm.vue;

import qcm.Controleur;
import qcm.metier.*;
import qcm.vue.donnees.GrilleDonneesQuestion;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
//TODO : faire les trois tables et gérer avec le double clic quelles questions afficher
//Affiche la sélection des ressources, la sélection de la notion et enfin la liste des questions

public class PanelListeQst extends JPanel implements ActionListener
{
    private FrameListeQst       frameListQst;

    private JPanel              panelHaut;
    private JTable              tblGrilleDonnees;
    private JComboBox<String>   ddlstRessource;
    private JComboBox<String>   ddlstNotion;

    private JButton             btnModifier;
    private JButton             btnSupprimer;

    private Font                fontGeneraleGras;
    private Font                fontGenerale;

    private Controleur          ctrl;

    public PanelListeQst(FrameListeQst parent, Controleur ctrl)
    {
        this.frameListQst     = parent;
        this.ctrl             = ctrl;
        this.fontGeneraleGras = new Font("Arial", Font.BOLD , 16);
        this.fontGenerale     = new Font("Arial", Font.PLAIN, 16);


        this.setLayout ( new BorderLayout() );

        String[] tabRessource = new String[this.ctrl.getNbRessource()+1];

        tabRessource[0] = "-- Choisir une ressource --";
        for (int i = 1; i <= this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i-1).getNomCourt();

        JScrollPane spGrilleDonnees;

        this.panelHaut        = new JPanel();
        this.ddlstRessource   = new JComboBox<>(tabRessource);
        this.ddlstNotion      = new JComboBox<>();

        this.ddlstNotion.addItem("-- Choisir une notion --");

        this.ddlstNotion.setEnabled(false);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesQuestion(null) );
        this.tblGrilleDonnees.setFillsViewportHeight(true);
        this.tblGrilleDonnees.setRowHeight(30);
        this.tblGrilleDonnees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.btnModifier   = new JButton("Modifier la question");
        this.btnSupprimer  = new JButton("Supprimer la question");

        spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );

        this.panelHaut.add(this.ddlstRessource);
        this.panelHaut.add(this.ddlstNotion);
        this.panelHaut.add(this.btnModifier);
        this.panelHaut.add(this.btnSupprimer);

        this.add(this.panelHaut , BorderLayout.NORTH );
        this.add(spGrilleDonnees, BorderLayout.CENTER);

        this.tblGrilleDonnees.getTableHeader().setFont(this.fontGeneraleGras);
        this.tblGrilleDonnees.setFont(this.fontGenerale);
        this.ddlstRessource  .setFont(this.fontGeneraleGras);
        this.ddlstNotion     .setFont(this.fontGeneraleGras);
        this.btnModifier     .setFont(this.fontGeneraleGras);
        this.btnSupprimer    .setFont(this.fontGeneraleGras);


        this.btnSupprimer  .addActionListener(this);
        this.btnModifier   .addActionListener(this);
        this.ddlstRessource.addActionListener(this);
        this.ddlstNotion   .addActionListener(this);
    }


    public void actionPerformed(ActionEvent e)
    {
        this.revalidate();
        this.repaint();

        if (e.getSource() == this.ddlstRessource){

            this.ddlstNotion.removeAllItems();

            if (this.ddlstRessource.getSelectedIndex() != 0){

                Ressource rsc = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

                if (rsc == null)
                    return;

                this.ddlstNotion.addItem("-- Choisir une notion --");

                for (Notion n : rsc.getAlNotion())
                    this.ddlstNotion.addItem(n.getNomCourt());

                this.ddlstNotion.setEnabled(true);
            }
            else {
                this.ddlstNotion.addItem("-- Choisir une notion --");
                this.ddlstNotion.setEnabled(false);
                this.tblGrilleDonnees.setModel(new GrilleDonneesQuestion(null));
            }
        }

        if(e.getSource() == this.ddlstNotion)
        {

            if ( this.ddlstNotion.getSelectedIndex() != 0 )
            {
                Ressource rsc = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

                if (rsc == null)
                    return;

                Notion not = this.ctrl.getNotion(rsc, (String) this.ddlstNotion.getSelectedItem());

                if (not == null)
                    return;

                this.tblGrilleDonnees.setModel(new GrilleDonneesQuestion(not));
            }
            else
                this.tblGrilleDonnees.setModel(new GrilleDonneesQuestion(null));


        }

        if ( e.getSource() == this.btnModifier ) {

            Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

            if ( ressource == null )
            {
                JOptionPane.showMessageDialog(this, "Il n'y aucune ressource de sélectionnée", "Erreur lors de la modification des données", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Notion notion = this.ctrl.getNotion(ressource, (String) this.ddlstNotion.getSelectedItem());

            if ( notion == null )
            {
                JOptionPane.showMessageDialog(this, "Il n'y aucune notion de sélectionnée", "Erreur lors de la modification des données", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ( this.tblGrilleDonnees.getSelectedRowCount() != 1 )
            {
                JOptionPane.showMessageDialog(this, "Il doit y avoir une case de sélectionnée", "Erreur lors de la modification des données", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Question qst = this.ctrl.getQuestionUID(notion,(String) this.tblGrilleDonnees.getValueAt(this.tblGrilleDonnees.getSelectedRow(), 0));

            if (qst == null) {
                JOptionPane.showMessageDialog(this, "Impossible de récupérer la question dans la base de données", "Erreur lors de la modification des données", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DonneesCreationQuestion data = new DonneesCreationQuestion(qst.getNbPoints(), qst.getTempsReponse(), ressource, notion, qst.getDifficulte(), qst.getTypeQuestion(), qst);

            this.ctrl.ouvrirCreerQuestion(data);
            this.frameListQst.fermerFenetre();

        }

        if (e.getSource() == this.btnSupprimer) {

            int resultat = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cette question ?", "Supprimer la question de la base de données ?", JOptionPane.YES_NO_OPTION);

            if (resultat == 0)
                this.confirmeeSupprimerQuestion();

        }
    }

    /**
     * Afficher un message d'erreur au client
     * @param message Message à afficher
     */
    private void afficherMessageErreur(String message) {

        JOptionPane.showMessageDialog(this, message, "Erreur lors de la validation des données", JOptionPane.ERROR_MESSAGE);

    }

    private void confirmeeSupprimerQuestion() {

        Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

        if ( ressource == null )
        {
            JOptionPane.showMessageDialog(this, "Il n'y aucune ressource de sélectionnée", "Erreur lors de la suppression des données", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Notion notion = this.ctrl.getNotion(ressource, (String) this.ddlstNotion.getSelectedItem());

        if ( notion == null )
        {
            JOptionPane.showMessageDialog(this, "Il n'y aucune notion de sélectionnée", "Erreur lors de la suppression des données", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ( this.tblGrilleDonnees.getSelectedRowCount() != 1 )
        {
            JOptionPane.showMessageDialog(this, "Il doit y avoir une case de sélectionnée", "Erreur lors de la suppression des données", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Question qst = this.ctrl.getQuestionUID(notion,(String) this.tblGrilleDonnees.getValueAt(this.tblGrilleDonnees.getSelectedRow(), 0));

        if (qst == null) {
            JOptionPane.showMessageDialog(this, "Impossible de récupérer la question dans la base de données", "Erreur lors de la suppression des données", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.ctrl.supprimerQuestion(notion, qst);
        this.tblGrilleDonnees.setModel(new GrilleDonneesQuestion(notion));

    }


}
