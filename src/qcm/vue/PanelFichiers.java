package qcm.vue;

import qcm.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

public class PanelFichiers extends JPanel implements ActionListener, ItemListener {

    private Controleur      ctrl;
    private FrameFichiers   frameParent;

    private JButton         btnFermer;
    private JButton         btnAjouterFichier;
    private JButton         btnSupprimerFichier;
    private JButton         btnOuvrirInfos;

    private List            lstFichiers;

    public PanelFichiers(Controleur ctrl, FrameFichiers frameParent) {

        this.ctrl           = ctrl;
        this.frameParent    = frameParent;

        this.setLayout(new BorderLayout());

        JPanel  panelButtons     = new JPanel();

        this.btnAjouterFichier   = new JButton("Ajouter un fichier"  );
        this.btnSupprimerFichier = new JButton("Supprimer le fichier");
        this.btnOuvrirInfos      = new JButton("?");
        this.btnSupprimerFichier.setEnabled(false);

        this.btnFermer           = new JButton("Fermer");

        this.lstFichiers         = new List();

        panelButtons.add(btnAjouterFichier  );
        panelButtons.add(btnSupprimerFichier);
        panelButtons.add(this.btnOuvrirInfos);

        this.add(panelButtons       , BorderLayout.NORTH );
        this.add(this.lstFichiers   , BorderLayout.CENTER);
        this.add(this.btnFermer     , BorderLayout.SOUTH );

        this.btnFermer          .addActionListener(this);
        this.btnAjouterFichier  .addActionListener(this);
        this.btnSupprimerFichier.addActionListener(this);
        this.btnOuvrirInfos     .addActionListener(this);

        this.lstFichiers.addItemListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this.btnFermer)
            this.frameParent.fermerFenetre();

        if (e.getSource() == this.btnAjouterFichier) {

            JFileChooser desti = new JFileChooser();
            int returnValue = desti.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File selectedFile = desti.getSelectedFile();
                this.lstFichiers.add(selectedFile.getName());

                System.out.println(selectedFile.getPath());

            }

        }

        if (e.getSource() == this.btnSupprimerFichier) {

            if (this.lstFichiers.getSelectedIndex() != -1) {
                this.lstFichiers.remove(this.lstFichiers.getSelectedIndex());
                this.btnSupprimerFichier.setEnabled(false);
            }

        }

        if (e.getSource() == this.btnOuvrirInfos)
            JOptionPane.showMessageDialog(this, "Les fichiers seront proposés aux étudiants sous la question sous forme de pièce jointe", "Information", JOptionPane.INFORMATION_MESSAGE);

        
    }

    public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == this.lstFichiers) {

            if (this.lstFichiers.getSelectedIndex() != -1)
                this.btnSupprimerFichier.setEnabled(true);
            else
                this.btnSupprimerFichier.setEnabled(false);

        }

    }
}
