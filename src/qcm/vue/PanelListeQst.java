package qcm.vue;

import qcm.Controleur;
import qcm.metier.Ressource;
import qcm.metier.Notion;
import qcm.vue.donnees.GrilleDonneesNotion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Affiche la sélection des ressources, la sélection de la notion et enfin la liste des questions

public class PanelListeQst extends JPanel implements ActionListener
{
    private FrameListeQst frameListQst;

    private JTable            tblGrilleDonnees;
    private JComboBox<String> lstRessource;
    private JComboBox<String> lstNotions;

    private JButton edit;
    private JButton delete;

    private Controleur      ctrl;

    public PanelListeQst(FrameListeQst parent, Controleur ctrl)
    {
        this.frameListQst = parent;
        this.setLayout ( new FlowLayout() );

        this.ctrl          = ctrl;

        String[] tabRessource = new String[this.ctrl.getNbRessource()];

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i).getNom();

        //TODO : tabNotions trouver les notions puis les questions par notions
        /*String[] tabNotions = new String[50];

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i).getNom();*/

        JScrollPane spGrilleDonnees;

        Ressource premiereRessource = (this.ctrl.getNbRessource() == 0) ? null : this.ctrl.getRessource(0);

        this.lstRessource     = new JComboBox<>(tabRessource);
        this.lstNotions       = new JComboBox<>(tabRessource);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesNotion(this.ctrl, premiereRessource) );
        this.tblGrilleDonnees.setFillsViewportHeight(true);

        this.edit   = new JButton(new ImageIcon("src/data/img/edit.png"  ));
        this.delete = new JButton(new ImageIcon("src/data/img/delete.png"));

        spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );

        this.add(this.edit);
        this.add(this.delete);
        this.add(this.lstRessource);
        this.add(this.lstNotions);
        this.add(spGrilleDonnees);

        this.delete      .addActionListener(this);
        this.edit        .addActionListener(this);
        this.lstRessource.addActionListener(this);
        this.lstNotions.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e)
    {

    }
}
