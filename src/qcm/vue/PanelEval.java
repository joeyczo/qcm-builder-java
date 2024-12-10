package qcm.vue;
import qcm.Controleur;
import qcm.metier.Notion;
import qcm.metier.Ressource;
import qcm.vue.donnees.GrilleDonneesEval;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class PanelEval extends JPanel implements ActionListener
{
    private List<Ressource>   lstRessources;
    private List<Notion>      lstNotions;

    private JButton           btnSubmit;
    private JComboBox<String> lstDeroulante;
    private JRadioButton      rbOui, rbNon;

    private FrameCreerEval frameParent;
    private JTable         tblGrilleDonnees;
    private JPanel         pnl;
    private String[]       tabLbl;

    private Controleur ctrl;

    public PanelEval( FrameCreerEval parent, Controleur ctrl ){
        this.frameParent = parent;
        this.setLayout ( new FlowLayout() );

        this.lstRessources = new ArrayList<Ressource>();
        this.lstNotions    = new ArrayList<Notion>();
        this.ctrl          = ctrl;

        this.pnl = new JPanel();

        String[] tabRessource = new String[this.ctrl.getNbRessource()];

        for ( int i = 0; i < this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i).getNom();

        JScrollPane spGrilleDonnees;

        Ressource premiereRessource = (this.ctrl.getNbRessource() == 0) ? null : this.ctrl.getRessource(0);

        this.lstDeroulante    = new JComboBox<>(tabRessource);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesEval(this.ctrl, premiereRessource) );
        this.tblGrilleDonnees.setFillsViewportHeight(true);

        this.btnSubmit = new JButton("Créer une nouvelle évaluation");

        rbNon = new JRadioButton("non");
        rbOui = new JRadioButton("oui");
        ButtonGroup group = new ButtonGroup();

        group.add(rbOui);
        group.add(rbNon);

        spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );

        this.add(this.lstDeroulante);
        //this.add(new JLabel(new ImageIcon("./Donnees/time.png")));
        this.add(rbOui);
        this.add(rbNon);
        this.add(this.tblGrilleDonnees);

        this.rbOui.addActionListener(this);
        this.rbNon.addActionListener(this);
        this.lstDeroulante.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == this.lstDeroulante) {

            Ressource ressource = this.ctrl.getRessource((String) this.lstDeroulante.getSelectedItem());
            this.tblGrilleDonnees.setModel(new GrilleDonneesEval(this.ctrl, ressource));
        }
    }
}
