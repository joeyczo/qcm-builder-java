package qcm.vue;

import qcm.Controleur;
import qcm.metier.DifficulteQuestion;
import qcm.metier.Ressource;
import qcm.vue.donnees.GrilleDonneesEval;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class PanelGenererEval extends JPanel implements ActionListener {
    private JButton btnSubmit;
    private JComboBox<String> ddlstRessource;
    private JRadioButton rbOui, rbNon;

    private FrameGenererEval frameParent;
    private JTable tblGrilleDonnees;
    private JPanel panelHaut;

    private Font fontGeneraleGras;

    private Controleur ctrl;

    public PanelGenererEval(FrameGenererEval parent, Controleur ctrl) {
        this.frameParent = parent;
        this.ctrl = ctrl;
        this.fontGeneraleGras = new Font("Arial", Font.BOLD, 16);

        this.panelHaut = new JPanel();
        this.ddlstRessource = new JComboBox<>();
        this.tblGrilleDonnees = new JTable(new GrilleDonneesEval(this.ctrl, null));

        this.setLayout(new BorderLayout());

        this.tblGrilleDonnees.setFillsViewportHeight(true);
        this.tblGrilleDonnees.setRowHeight(30);
        this.tblGrilleDonnees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int i = 0; i < this.tblGrilleDonnees.getColumnModel().getColumnCount(); i++)
            this.tblGrilleDonnees.getColumnModel().getColumn(i).setCellRenderer(new RenduCellule());



        this.ddlstRessource.addItem("-- Sélectionnez une ressource --");

        for (int i = 0; i < this.ctrl.getNbRessource(); i++)
            this.ddlstRessource.addItem(this.ctrl.getRessource(i).getNomCourt());

        this.btnSubmit = new JButton("Générer une nouvelle évaluation");

        rbNon = new JRadioButton("non");
        rbOui = new JRadioButton("oui");
        ButtonGroup group = new ButtonGroup();

        group.add(rbOui);
        group.add(rbNon);

        JScrollPane spGrilleDonnees = new JScrollPane(this.tblGrilleDonnees);

        this.panelHaut.add(this.ddlstRessource);
        this.panelHaut.add(new JLabel(new ImageIcon("src/data/img/time.png")));
        this.panelHaut.add(rbOui);
        this.panelHaut.add(rbNon);

        this.add(this.panelHaut, BorderLayout.NORTH);
        this.add(spGrilleDonnees, BorderLayout.CENTER);
        this.add(this.btnSubmit, BorderLayout.SOUTH);

        this.tblGrilleDonnees.getTableHeader().setFont(this.fontGeneraleGras);
        this.tblGrilleDonnees.setFont(this.fontGeneraleGras);
        this.ddlstRessource.setFont(this.fontGeneraleGras);
        this.rbNon.setFont(this.fontGeneraleGras);
        this.rbOui.setFont(this.fontGeneraleGras);
        this.btnSubmit.setFont(this.fontGeneraleGras);

        this.rbOui.addActionListener(this);
        this.rbNon.addActionListener(this);
        this.ddlstRessource.addActionListener(this);
        this.btnSubmit.addActionListener(this);


    }

    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == this.ddlstRessource) {

            if (this.ddlstRessource.getSelectedIndex() == 0) {
                this.tblGrilleDonnees.setModel(new GrilleDonneesEval(this.ctrl, null));
                return;
            }

            Ressource ressource = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());
            this.ctrl.changerRessourceEval(ressource);
            this.tblGrilleDonnees.setModel(new GrilleDonneesEval(this.ctrl, ressource));
            for (int i = 0; i < this.tblGrilleDonnees.getColumnModel().getColumnCount(); i++) {

                Class<?> classe = this.tblGrilleDonnees.getColumnClass(i);

                if (classe == Boolean.class)
                    this.tblGrilleDonnees.getColumnModel().getColumn(i).setCellRenderer(this.tblGrilleDonnees.getDefaultRenderer(Boolean.class));
                else
                    this.tblGrilleDonnees.getColumnModel().getColumn(i).setCellRenderer(new RenduCellule());


            }
            this.tblGrilleDonnees.revalidate();
            this.tblGrilleDonnees.repaint();
            this.ctrl.resetGenerationEvals();
        }

        // Action lors de la création d'une évaluation
        if (e.getSource() == this.btnSubmit) {

            /*  ----------------------  */
            /*	 Vérification données   */
            /*  ----------------------  */

            Ressource rsc = this.ctrl.getRessource((String) this.ddlstRessource.getSelectedItem());
            boolean time = this.rbOui.isSelected();
            int nbSe = this.ctrl.getNotionsSelected();
            int nbQs = this.ctrl.getNbQuestions();

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

                this.ctrl.changerRessourceEval(rsc);
                this.ctrl.generationEvaluation(estEvalue, selectedFile.getPath());
                this.frameParent.fermerFenetre();

            }
        }

    }

    /**
     * Afficher un message d'erreur au client
     *
     * @param message Message à afficher
     */
    private void afficherMessageErreur(String message) {

        JOptionPane.showMessageDialog(this, message, "Erreur lors de la validation des données", JOptionPane.ERROR_MESSAGE);

    }

    private static class RenduCellule extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int lig, int col) {

            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, lig, col);

            cell.setBackground(Color.WHITE);

            if (isSelected) {
                cell.setBackground(table.getSelectionBackground());
            }

            if (lig == (table.getRowCount() -1 ) ) cell.setBackground(Color.LIGHT_GRAY);
            else {

                if ((boolean) table.getValueAt(lig, 1)) {

                    if (col == 2)
                        cell.setBackground(DifficulteQuestion.TRESFACILE.getCouleur());
                    else if (col == 3)
                        cell.setBackground(DifficulteQuestion.FACILE.getCouleur());
                    else if (col == 4)
                        cell.setBackground(DifficulteQuestion.MOYEN.getCouleur());
                    else if (col == 5)
                        cell.setBackground(DifficulteQuestion.DIFFICILE.getCouleur());

                }

                if (col >= 2 && col <= 6)
                    ((DefaultTableCellRenderer) cell).setHorizontalAlignment(SwingConstants.RIGHT);

            }


            return cell;
        }

    }


}
