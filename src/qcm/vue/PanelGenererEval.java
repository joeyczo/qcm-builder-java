package qcm.vue;
import qcm.Controleur;
import qcm.metier.Ressource;
import qcm.vue.donnees.GrilleDonneesEval;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class PanelGenererEval extends JPanel implements ActionListener
{
    private JButton             btnSubmit;
    private JComboBox<String>   ddlstRessource;
    private JRadioButton        rbOui, rbNon;

    private FrameGenererEval    frameParent;
    private JTable              tblGrilleDonnees;
    private JPanel              panelHaut;

    private Controleur ctrl;

    public PanelGenererEval(FrameGenererEval parent, Controleur ctrl ){
        this.frameParent = parent;
        this.setLayout ( new BorderLayout() );
        this.ctrl = ctrl;

        this.panelHaut        = new JPanel();
        this.ddlstRessource   = new JComboBox<>();
        this.tblGrilleDonnees = new JTable ( new GrilleDonneesEval(this.ctrl, null) );
        this.tblGrilleDonnees.setFillsViewportHeight(true);
        this.tblGrilleDonnees.setRowHeight(30);
        this.tblGrilleDonnees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        this.ddlstRessource.addItem("-- Sélectionnez une ressource --");

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
            this.ddlstRessource.addItem(this.ctrl.getRessource(i).getNom());

        this.btnSubmit = new JButton("Généré une nouvelle évaluation");

        rbNon             = new JRadioButton("non");
        rbOui             = new JRadioButton("oui");
        ButtonGroup group = new ButtonGroup();

        group.add(rbOui);
        group.add(rbNon);

        JScrollPane spGrilleDonnees = new JScrollPane( this.tblGrilleDonnees );

        this.panelHaut.add(this.ddlstRessource);
        this.panelHaut.add(new JLabel(new ImageIcon("src/data/img/time.png")));
        this.panelHaut.add(rbOui);
        this.panelHaut.add(rbNon);

        this.add(this.panelHaut , BorderLayout.NORTH );
        this.add(spGrilleDonnees, BorderLayout.CENTER);
        this.add(this.btnSubmit , BorderLayout.SOUTH );

        this.rbOui         .addActionListener(this);
        this.rbNon         .addActionListener(this);
        this.ddlstRessource.addActionListener(this);
        this.btnSubmit     .addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == this.ddlstRessource) {

            if (this.ddlstRessource.getSelectedIndex() == 0) {
                this.tblGrilleDonnees.setModel(new GrilleDonneesEval(this.ctrl, null));
                return;
            }

            Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());
            // TODO : Réparer les ressources qui ne fonctionnent pas
            this.ctrl.changerRessourceEval(ressource);
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
                this.afficherMessageErreur("Veullez sélectionner au moins une notion dans le tableau !");
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

                boolean estEvalue = this.rbOui.isSelected();

                this.ctrl.generationEvaluation(estEvalue, selectedFile.getPath());
                this.frameParent.fermerFenetre();

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
