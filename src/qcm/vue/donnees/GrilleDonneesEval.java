package qcm.vue.donnees;
import qcm.Controleur;
import qcm.metier.Notion;
import qcm.metier.Ressource;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class GrilleDonneesEval extends AbstractTableModel
{
    private Controleur ctrl;

    private String[]   tabEntetes;
    private Object[][] tabDonnees;

    public GrilleDonneesEval (Controleur ctrl, Ressource r)
    {
        this.ctrl = ctrl;
        Notion notion;

        List<Notion> lstNotions = new ArrayList<Notion>();
        for(int i=0; i<this.ctrl.getNbNotion(r); i++)
            lstNotions.add(this.ctrl.getNotion(r, i));

        this.tabEntetes = new String[]{"Nom notion", "Select", "TF", "F", "M", "D", ""};
        this.tabDonnees = new Object[this.ctrl.getNbNotion(r)+1][tabEntetes.length];

        int taille = lstNotions.size();
        for ( int lig=0; lig<taille; lig++) {
            notion = lstNotions.get(lig);

            tabDonnees[lig][0] = notion.getNom();
            tabDonnees[lig][1] = false;
            tabDonnees[lig][2] = "";
            tabDonnees[lig][3] = "";
            tabDonnees[lig][4] = "";
            tabDonnees[lig][5] = "";
            tabDonnees[lig][6] = "";
        }
        //tabDonnees[taille][0] = "questions par catégorie";
        /*for (int i = 1; i < this.getColumnCount()-1; i++) {
            tabDonnees[taille][i] = sumCol(i);
        }*/
    }

    public int    getColumnCount()                 { return this.tabEntetes.length;          }
    public int    getRowCount   ()                 { return this.tabDonnees.length;          }
    public String getColumnName (int col)          { return this.tabEntetes[col];            }
    public Object getValueAt    (int row, int col) { return this.tabDonnees[row][col];       }
    public Class  getColumnClass(int c)            { return getValueAt(0, c).getClass(); }

    public boolean isCellEditable(int row, int col)
    {
        return col != 0 && col != 6;
    }

    public void setValueAt(Object value, int row, int col)
    {

        if (col != 0 && col != 6) {

            if (this.getValueAt(row, col) instanceof Boolean) {
                this.tabDonnees[row][col] = value;
                this.fireTableCellUpdated(row, col);
            }

        }
    }

    public int sumCol(int col){
        int sum=0;
        for (int i = 0; i < this.getRowCount() - 1; i++) {
            Object val = this.getValueAt(i, col);
            if (val instanceof Integer)
                sum += (int) val;
        }
        return sum;
    }
}