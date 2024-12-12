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

    public GrilleDonneesQuestion(Controleur controleur, Notion notion) {

        this.ctrl = controleur;
        if ( notion    == null ) notion    = new Notion   ( "TEST" , null);

        this.tabEntete  = new String[] { "Intitulé", "Difficulté", "Type"};
        this.tabDonnees = new Object[notion.getNbQuestions()][this.tabEntete.length];

        for ( int i = 0; i < notion.getNbQuestions(); i++) {

            Question question = notion.getQuestion(i);

            tabDonnees[i][0] = question.getTexteQuestion();
            tabDonnees[i][1] = question.getDifficulte();
            tabDonnees[i][2] = question.getTypeQuestion();

        }

    }

    /* ------------ */
    /*	 Getters 	*/
    /* ------------ */

    public int      getRowCount()    {   return this.tabDonnees.length; }
    public int      getColumnCount() {   return this.tabEntete.length;  }

    public String   getColumnName (int col)                       { return this.tabEntete[col];                      }
    public Object   getValueAt    (int rowIndex, int columnIndex) { return this.tabDonnees[rowIndex][columnIndex];   }
    public Class    getColumnClass(int c)                         { return this.getValueAt(0, c).getClass(); }

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
