package qcm.vue;

import qcm.Controleur;
import qcm.metier.DonneesCreationQuestion;
import qcm.metier.Fichier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import java.util.ArrayList;

public class PanelFichiers extends JPanel implements ActionListener, ItemListener {

    private Controleur              ctrl;
    private FrameFichiers           frameParent;
    private DonneesCreationQuestion data;

    private JButton                 btnFermer;
    private JButton                 btnAjouterFichier;
    private JButton                 btnSupprimerFichier;
    private JButton                 btnOuvrirInfos;

    private List                    lstFichiers;

    public PanelFichiers(Controleur ctrl, FrameFichiers frameParent, DonneesCreationQuestion data) {

        this.ctrl        = ctrl;
        this.data        = data;
        this.frameParent = frameParent;

        this.setLayout(new BorderLayout());

        JPanel  panelButtons     = new JPanel();

        this.btnAjouterFichier   = new JButton("Ajouter un fichier"  );
        this.btnSupprimerFichier = new JButton("Supprimer le fichier");
        this.btnOuvrirInfos      = new JButton("?");
        this.btnFermer           = new JButton("Fermer");

        this.lstFichiers         = new List();

        this.btnSupprimerFichier.setEnabled(false);

        // Chargement des fichiers déjà présent
        ArrayList<Fichier> ensFichiers = this.ctrl.getFichiersQuestion();

        for (Fichier s : ensFichiers)
            this.lstFichiers.add(s.nomFichier());


        panelButtons.add(btnAjouterFichier  );
        panelButtons.add(btnSupprimerFichier);
        panelButtons.add(this.btnOuvrirInfos);

        this.add(panelButtons     , BorderLayout.NORTH );
        this.add(this.lstFichiers , BorderLayout.CENTER);
        this.add(this.btnFermer   , BorderLayout.SOUTH );

        this.btnFermer          .addActionListener(this);
        this.btnAjouterFichier  .addActionListener(this);
        this.btnSupprimerFichier.addActionListener(this);
        this.btnOuvrirInfos     .addActionListener(this);
        this.lstFichiers        .addActionListener(this);

        this.lstFichiers        .addItemListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this.btnFermer)
            this.frameParent.fermerFenetre();

        if (e.getSource() == this.btnAjouterFichier) {

            JFileChooser desti       = new JFileChooser();
            int          returnValue = desti.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File selectedFile = desti.getSelectedFile();

                this.lstFichiers.add(selectedFile.getName());

                // Ajout du fichier dans l'application
                if (!this.ctrl.ajouterFichierQuestion(selectedFile.getPath(), this.data))
                    JOptionPane.showMessageDialog(this, "Une erreur vient de se produire lors de l'ajout de la pièce jointe !", "Impossible d'ajouter le fichier", JOptionPane.ERROR_MESSAGE);

            }

        }

        if (e.getSource() == this.btnSupprimerFichier) {

            if (this.lstFichiers.getSelectedIndex() != -1) {

                if (!this.ctrl.supprimerFichierQuestion(this.lstFichiers.getSelectedIndex())) {

                    JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de la suppression du fichier", "Impossible de supprimer le fichier", JOptionPane.ERROR_MESSAGE);
                    return;

                }

                this.lstFichiers.remove(this.lstFichiers.getSelectedIndex());

                this.btnSupprimerFichier.setEnabled(false);

            }

        }

        if (e.getSource() == this.btnOuvrirInfos)
            JOptionPane.showMessageDialog(this, "Les fichiers seront proposés aux étudiants sous la question sous forme de pièce jointe", "Information", JOptionPane.INFORMATION_MESSAGE);

        if (e.getSource() == this.lstFichiers) {

            try {

                Desktop.getDesktop().open(this.ctrl.getFichierQuestion(this.lstFichiers.getSelectedIndex()));

            } catch (Exception ex) {

                System.out.println("Erreur ouverture fichier : " + ex.getMessage());

            }

        }
        
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
