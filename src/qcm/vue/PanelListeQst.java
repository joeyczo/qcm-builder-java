package qcm.vue;

import qcm.Controleur;
import qcm.metier.DonneesCreationQuestion;
import qcm.metier.Question;
import qcm.metier.Ressource;
import qcm.metier.Notion;
import qcm.vue.donnees.GrilleDonneesNotion;
import qcm.vue.donnees.GrilleDonneesQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//TODO : faire les trois tables et gérer avec le double clic quelles questions afficher
//Affiche la sélection des ressources, la sélection de la notion et enfin la liste des questions

public class PanelListeQst extends JPanel implements ActionListener, ItemListener
{
    private FrameListeQst frameListQst;

    private JTable            tblGrilleDonnees;
    private JComboBox<String> ddlstRessource;
    private JComboBox<String> ddlstNotion;

    private JButton edit;
    private JButton delete;

    private Controleur ctrl;

    public PanelListeQst(FrameListeQst parent, Controleur ctrl)
    {
        this.frameListQst = parent;
        this.setLayout ( new FlowLayout() );

        this.ctrl = ctrl;

        String[] tabRessource = new String[this.ctrl.getNbRessource()];

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i).getNom();

        JScrollPane spGrilleDonnees;

        Ressource premiereRessource = (this.ctrl.getNbRessource() == 0) ? null : this.ctrl.getRessource(0);
        Notion    premiereNotion    = (premiereRessource == null || premiereRessource.getAlNotion().isEmpty()) ? null : premiereRessource.getAlNotion().getFirst();

        this.ddlstRessource = new JComboBox<>(tabRessource);
        this.ddlstNotion = new JComboBox<>();
        this.ddlstNotion.setEnabled(false);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesQuestion(this.ctrl, premiereNotion) );
        this.tblGrilleDonnees.setFillsViewportHeight(true);

        if (premiereRessource != null) {
            for (Notion n : premiereRessource.getAlNotion())
                this.ddlstNotion.addItem(n.getNomCourt());
            this.ddlstNotion.setEnabled(true);
        }


        //this.edit   = new JButton(new ImageIcon("src/data/img/edit.png"  ));
        //this.delete = new JButton(new ImageIcon("src/data/img/delete.png"));
        this.edit   = new JButton("Modifier la question");
        this.delete = new JButton("Supprimer la question");

        spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );

        this.add(this.ddlstRessource);
        this.add(this.ddlstNotion);
        this.add(this.edit);
        this.add(this.delete);
        this.add(spGrilleDonnees);

        this.delete        .addActionListener(this);
        this.edit          .addActionListener(this);
        this.ddlstRessource.addItemListener(this);
        this.ddlstNotion   .addItemListener(this);
    }


    public void actionPerformed(ActionEvent e)
    {
        /*if(e.getSource() == this.ddlstRessource) {
            String[] tabNotions = new String[this.ctrl.getNbNotion(this.ddlstRessource.getSelectedItem())];

            for (int i = 0; i < this.ctrl.getNbRessource(); i++)
                tabNotions[i] = this.ctrl.getNotion(this.ctrl.getRessource(i), this.ctrl.getRessource(i).getNom()).getNom();
        }*/

        if ( e.getSource() == this.edit ){
            Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());

            if ( ressource == null ) return;

            Notion notion = this.ctrl.getNotion(ressource, (String) this.ddlstNotion.getSelectedItem());

            if ( notion == null ) return;

            Question qst = this.ctrl.getQuestionUID(notion,(String) this.tblGrilleDonnees.getValueAt(this.tblGrilleDonnees.getSelectedRow(), 0));

            DonneesCreationQuestion data = new DonneesCreationQuestion(qst.getNbPoints(), qst.getTempsReponse(), ressource, notion, qst.getDifficulte(), qst.getTypeQuestion(), qst);

            System.out.println("Modification ...");
            new FrameInfosQuestion(this.ctrl, data);
        }
    }

    /**
     * Afficher un message d'erreur au client
     * @param message Message à afficher
     */
    private void afficherMessageErreur(String message) {

        JOptionPane.showMessageDialog(this, message, "Erreur lors de la validation des données", JOptionPane.ERROR_MESSAGE);

    }

    public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == this.ddlstRessource && e.getStateChange() == ItemEvent.SELECTED) {

            String nomRessource = (String) this.ddlstRessource.getSelectedItem();

            Ressource rsc = this.ctrl.getRessource(nomRessource);

            if (rsc == null) {
                this.afficherMessageErreur("Impossible de récupérer la ressource !");
                return;
            }

            this.ddlstNotion.removeAllItems();

            for (Notion n : rsc.getAlNotion())
                this.ddlstNotion.addItem(n.getNomCourt());

            this.ddlstNotion.setEnabled(true);

        }

        if (e.getSource() == this.ddlstNotion && e.getStateChange() == ItemEvent.SELECTED) {

            String nomRessource = (String) this.ddlstRessource.getSelectedItem();

            Ressource rsc = this.ctrl.getRessource(nomRessource);

            if (rsc == null) {
                this.afficherMessageErreur("Impossible de récupérer la ressource !");
                return;
            }

            Notion not = this.ctrl.getNotion(rsc, (String) this.ddlstNotion.getSelectedItem());

            if (not == null) {
                this.afficherMessageErreur("Impossible de récupérer la notion !");
                return;
            }

            this.tblGrilleDonnees.setModel(new GrilleDonneesQuestion(this.ctrl, not));

        }

    }
}
