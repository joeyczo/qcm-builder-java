package qcm.vue;
import qcm.Controleur;
import qcm.metier.Notion;
import qcm.metier.Ressource;
import qcm.vue.Donnees.GrilleDonneesNotion;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.io.FileInputStream;

public class PanelEval extends JPanel implements ActionListener
{
    private JButton btnSubmit;
    private JComboBox<String> lstDeroulante;
    private JRadioButton rbOui, rbNon;
    private List<Ressource> lstRessources;
    private List<Notion>    lstNotions;

    private FrameCreerEval frameParent;
    private JTable            tblGrilleDonnees;

    private Controleur ctrl;

    public PanelEval( FrameCreerEval parent, Controleur ctrl ){
        this.frameParent = parent;
        this.setLayout ( new FlowLayout() );

        this.lstRessources = new ArrayList<Ressource>();
        this.lstNotions    = new ArrayList<Notion>();
        this.ctrl          = ctrl;

        String[] tabRessource = new String[this.ctrl.getNbRessource()];

        for ( int i = 0; i < this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i).getNom();

        JScrollPane spGrilleDonnees;

        this.lstDeroulante    = new JComboBox<>(tabRessource);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesNotion(this.ctrl, null) );
        this.tblGrilleDonnees.setFillsViewportHeight(true);

        this.btnSubmit = new JButton("Créer une nouvelle évaluation");

        rbNon = new JRadioButton("non");
        rbOui = new JRadioButton("oui");
        ButtonGroup group = new ButtonGroup();

        group.add(rbOui);
        group.add(rbNon);

        spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );

        this.add(this.lstDeroulante);
        this.add(new JLabel(new ImageIcon("./Donnees/time.png")));
        this.add(rbOui);
        this.add(rbNon);

        this.rbOui.addActionListener(this);
        this.rbNon.addActionListener(this);
        this.lstDeroulante.addActionListener(this);
    }

    public List<Ressource> lire()
    {
        Scanner sc,scLig;
        String nom;
        List<Ressource> lstTemp = new ArrayList<Ressource>();
        try {
            sc = new Scanner(new FileInputStream("./Donnees/ressources.data"));

            while(sc.hasNextLine())
            {
                scLig = new Scanner(sc.nextLine());
                scLig.useDelimiter("\\t");

                nom  = scLig.next();

                Ressource r = new Ressource(nom);
                lstTemp.add(r);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        return lstTemp;
    }

    public void actionPerformed(ActionEvent e){
        if(this.lstDeroulante.getItemCount()>0 && (e.getSource() == this.rbOui || e.getSource() == this.rbNon))
        {
            System.out.println(this.lstDeroulante.getSelectedItem());
            Ressource r = new Ressource(this.lstDeroulante.getSelectedItem()+"");
            this.tblGrilleDonnees = new JTable ( new GrilleDonneesNotion(this.ctrl, r));
            this.add(this.tblGrilleDonnees);
            System.out.println("gg wp");
        }
    }
}
