package qcm.vue;
import qcm.Controleur;
import qcm.metier.Notion;
import qcm.metier.Ressource;
import qcm.vue.donnees.GrilleDonneesEval;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;

public class PanelEval extends JPanel implements ActionListener
{
    private JButton             btnSubmit;
    private JComboBox<String>   ddlstRessource;
    private JRadioButton        rbOui, rbNon;

    private FrameCreerEval      frameParent;
    private JTable              tblGrilleDonnees;
    private JPanel              panelHaut;
    private String[]            tabLbl;

    private Controleur ctrl;

    public PanelEval( FrameCreerEval parent, Controleur ctrl ){
        this.frameParent = parent;
        this.setLayout ( new BorderLayout() );
        this.ctrl = ctrl;

        String[] tabRessource = new String[this.ctrl.getNbRessource()];

        for ( int i = 0; i < this.ctrl.getNbRessource(); i++)
            tabRessource[i] = this.ctrl.getRessource(i).getNom();

        Ressource premiereRessource = (this.ctrl.getNbRessource() == 0) ? null : this.ctrl.getRessource(0);

        this.panelHaut = new JPanel();
        this.ddlstRessource    = new JComboBox<>(tabRessource);
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesEval(this.ctrl, premiereRessource) );
        this.tblGrilleDonnees.setFillsViewportHeight(true);

        this.btnSubmit = new JButton("Créer une nouvelle évaluation");

        rbNon = new JRadioButton("non");
        rbOui = new JRadioButton("oui");
        ButtonGroup group = new ButtonGroup();

        group.add(rbOui);
        group.add(rbNon);

        JScrollPane spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );

        this.panelHaut.add(this.ddlstRessource);
        this.panelHaut.add(new JLabel(new ImageIcon("src/data/img/time.png")));
        this.panelHaut.add(rbOui);
        this.panelHaut.add(rbNon);

        this.add(this.panelHaut,        BorderLayout.NORTH );
        this.add(spGrilleDonnees, BorderLayout.CENTER);
        this.add(this.btnSubmit,  BorderLayout.SOUTH );

        this.rbOui.addActionListener(this);
        this.rbNon.addActionListener(this);
        this.ddlstRessource.addActionListener(this);
        this.btnSubmit.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == this.ddlstRessource) {
            Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());
            this.tblGrilleDonnees.setModel(new GrilleDonneesEval(this.ctrl, ressource));
            this.tblGrilleDonnees.revalidate();
            this.tblGrilleDonnees.repaint();
            this.ctrl.resetGenerationEvals();
        }

        // Action lors de la création d'une évaluation
        if (e.getSource() == this.btnSubmit) {

            /*  ----------------------  */
            /*	 Vérification données   */
            /*  ----------------------  */

            Ressource   rsc  = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());
            boolean     time = this.rbOui.isSelected();
            int         nbSe = this.ctrl.getNotionsSelected();
            int         nbQs = this.ctrl.getNbQuestions();

            if (rsc == null) {
                this.afficherMessageErreur("Impossible de récupérer la ressource !");
                return;
            }

            if (!time && !this.rbNon.isSelected()) {
                this.afficherMessageErreur("Veuillez sélectionner si l'évaluation est chronométré ou non");
                return;
            }

            if (nbSe <= 0) {
                this.afficherMessageErreur("Veullez sélectionner au moins une ressource dans le tableau !");
                return;
            }

            if (nbQs <= 0) {
                this.afficherMessageErreur("Le nombre de question généré doît être supérieur à 0 !");
                return;
            }
            
            System.out.println("QST : " + nbQs);
            
            JFileChooser desti = new JFileChooser();
            int returnValue = desti.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File selectedFile = desti.getSelectedFile();
                System.out.println(selectedFile.getPath());

            }


        }

    }


    /**
     * Afficher un message d'erreur au client
     * @param message Message à afficher
     */
    private void afficherMessageErreur(String message) {

        JOptionPane.showMessageDialog(this, message, "Erreur lors de la validation des données", JOptionPane.ERROR_MESSAGE);

    }
}
