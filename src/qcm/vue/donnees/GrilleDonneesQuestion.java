package qcm.vue.donnees;

import qcm.Controleur;
import qcm.metier.Notion;
import qcm.metier.Ressource;
import qcm.metier.Question;

import javax.swing.table.AbstractTableModel;

public class GrilleDonneesQuestion extends AbstractTableModel {

    private String[]    tabEntete;
    private Object[][]  tabDonnees;

    private Controleur  ctrl;
    private Ressource   ressource;

    public GrilleDonneesQuestion(Controleur controleur, Ressource ressource, Notion notion) {

        this.ctrl = controleur;

        if (ressource == null) ressource = new Ressource("TEST");
        if (notion == null) notion = new Notion("TEST");

        this.tabEntete  = new String[] { "Intitulé", "Difficulté", "Type"};
        this.tabDonnees = new Object[this.ctrl.getNbQuestions()][this.tabEntete.length];

        for (int i = 0; i < this.ctrl.getNbNotion(ressource); i++) {

            //Question question = this.ctrl.getNbQuestions(ressource, i);

            tabDonnees[i][0] = notion.getNom();
            tabDonnees[i][1] = 0;
            tabDonnees[i][2] = 0;
            tabDonnees[i][3] = 0;
            tabDonnees[i][4] = 0;

        }

    }

    /* ------------ */
    /*	 Getters 	*/
    /* ------------ */

    public int getRowCount()    {   return this.tabDonnees.length; }
    public int getColumnCount() {   return this.tabEntete.length;  }

    public String getColumnName (int col)                       { return this.tabEntete[col];                      }
    public Object getValueAt    (int rowIndex, int columnIndex) { return this.tabDonnees[rowIndex][columnIndex];   }
    public Class  getColumnClass(int c)                         { return this.getValueAt(0, c).getClass(); }

    /* ------------ */
    /*	 Setters 	*/
    /* ------------ */

    public void setValueAt (Object value, int row, int col) {

        if (col == 1) {

            this.tabDonnees[row][col] = value;
            this.fireTableCellUpdated(row, col);

        }

    }
}
