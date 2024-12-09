package Vue.donnees;

import javax.swing.table.AbstractTableModel;

public class GrilleDonneesNotion extends AbstractTableModel {

    private String[]    tabEntete;
    private Object[][]  tabDonnees;

    public GrilleDonneesNotion() {

        this.tabEntete = new String[] { "Notion", "Nb. Très facile", "Nb. Facile", "Nb. Moyen", "Nb. difficile"};
        this.tabDonnees = new Object[4][this.tabEntete.length];

    }

    /* ------------ */
    /*	 Getters 	*/
    /* ------------ */

    public int getRowCount()    {   return this.tabDonnees.length; }
    public int getColumnCount() {   return this.tabEntete.length;  }

    public String getColumnName (int col)                   {   return this.tabEntete[col];                     }
    public Object getValueAt(int rowIndex, int columnIndex) {   return this.tabDonnees[rowIndex][columnIndex];  }

    public boolean isCellEditable(int row, int col) {   return col == 1; }

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
