package Vue;

import Metier.*;
import Vue.donnees.GrilleDonneesNotion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelParametre extends JPanel implements ActionListener
{
    private FrameParametres frameParent;

    private JTable            tblGrilleDonnees;
    private JComboBox<String> lstDeroulante;

    private JButton btnAjouterRessource;
    private JButton btnAjouterNotion;

    public PanelParametre(FrameParametres parent)
    {
        this.frameParent = parent;
        this.setLayout ( new FlowLayout() );

        // A SUPPRIMER AVEC LES VRAIES VALEURS DEPUIS LE CONTROLEUR
        String[] ressources = {"R1.01 DEV", "R2.02 JSP PARCE JE SAIS PAS", "R3.03 JE SAIS TOUJOURS PAS T'AS CRU QUOI"};

        JScrollPane spGrilleDonnees;

        this.lstDeroulante    = new JComboBox<>(ressources);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesNotion() );
        this.tblGrilleDonnees.setFillsViewportHeight(true);

        this.btnAjouterRessource = new JButton("Ajouter une ressource");
        this.btnAjouterNotion    = new JButton("Ajouter une notion");

        spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );

        this.add(this.btnAjouterRessource);
        this.add(this.btnAjouterNotion);
        this.add(this.lstDeroulante);
        this.add(spGrilleDonnees);

        this.btnAjouterNotion   .addActionListener(this);
        this.btnAjouterRessource.addActionListener(this);
        this.lstDeroulante      .addActionListener(this);
    }

    /*  -----------------  */
    /*	 Autres méthodes   */
    /*  -----------------  */

    public void actionPerformed(ActionEvent e)
    {

        if(e.getSource() == this.btnAjouterRessource)
        {
            String    nomRessource = JOptionPane.showInputDialog(this, "Entrez le nom de la ressource :", "Ajouter Ressource", JOptionPane.PLAIN_MESSAGE);
            Ressource ressource    = new Ressource(nomRessource);

            if(nomRessource != null && !nomRessource.trim().isEmpty())
            {
                System.out.println("Ressource ajoutée : " + nomRessource);
            }
            else
            {
                System.out.println("Aucune ressource saisie");
            }
        }

        if (e.getSource() == this.btnAjouterNotion)
        {
            String nomNotion = JOptionPane.showInputDialog(this, "Entrez le nom de la notion :", "Ajouter Notion", JOptionPane.PLAIN_MESSAGE);
            Notion notion    = new Notion(nomNotion);

            if (nomNotion != null && !nomNotion.trim().isEmpty())
            {
                System.out.println("Notion ajoutée : " + nomNotion);
            }
            else
            {
                System.out.println("Aucune notion saisie");
            }
        }
    }
}