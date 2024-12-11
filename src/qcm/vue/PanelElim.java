package qcm.vue;
import qcm.Controleur;
import qcm.metier.Notion;
import qcm.metier.Ressource;
import qcm.vue.donnees.GrilleDonneesEval;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class PanelElim extends JPanel implements ActionListener
{
    private FrameInfosQuestion frameParent;

    private JButton        btnSubmit, btnAdd, btnDelete;
    private JTextArea      txtQst;
    private JScrollPane    scrollPane;
    private JPanel         panelHaut, panelMilieu, panelBas;

    private Controleur ctrl;

    private final int nbReponsesMax  = 4;
    private int nbReponsesActuelles = 0;

    private List<JPanel> listReponses = new ArrayList<JPanel>();


    public PanelElim( FrameInfosQuestion parent, Controleur ctrl ){
        this.frameParent = parent;
        this.ctrl = ctrl;

        this.setLayout(new BorderLayout());

        this.panelHaut   = new JPanel();
        this.panelMilieu = new JPanel();
        this.panelBas    = new JPanel();

        this.txtQst     = new JTextArea  (7, 1);
        this.scrollPane = new JScrollPane(txtQst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.btnSubmit  = new JButton    ("Sauvegarder");
        this.btnAdd     = new JButton    (new ImageIcon("src/data/img/add.png"));
        this.btnDelete  = new JButton    (new ImageIcon("src/data/img/delete.png"));

        this.panelHaut.setLayout   ( new GridBagLayout() );
        this.panelMilieu.setLayout(new BoxLayout(panelMilieu, BoxLayout.Y_AXIS));
        this.panelMilieu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets             = new Insets(5, 5, 5, 5);
        gbc.fill               = GridBagConstraints.HORIZONTAL;

        // Panel haut
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.panelHaut.add(new JLabel("Question - " + txtQst.isEditable()), gbc); // isEditable temporaire, pour tester

        gbc.gridy     = 1;
        gbc.gridwidth = 10;
        gbc.ipadx     = 50;
        gbc.ipady     = 50;
        this.panelHaut.add(scrollPane, gbc);

        // Panel milieu
        this.createAnswerPanel();
        this.createAnswerPanel();

        // Panel bas
        this.panelBas.add(btnSubmit);
        this.panelBas.add(btnAdd);

        this.add(panelHaut  , BorderLayout.NORTH);
        this.add(panelMilieu, BorderLayout.CENTER);
        this.add(panelBas   , BorderLayout.SOUTH);

        this.btnSubmit.addActionListener(this);
        this.btnAdd   .addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            createAnswerPanel();
        }
    }

    public boolean createAnswerPanel()
    {
        if (this.nbReponsesActuelles < this.nbReponsesMax)
        {
            JPanel responsePanel = new JPanel();
            responsePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Alignement à gauche avec padding


            responsePanel.add(btnDelete);

            // Création du texte principal
            JTextArea txtRep = new JTextArea(3, 10);
            responsePanel.add(txtRep);

            // Ajout du bouton de suppression
            JButton btnDelete = new JButton(new ImageIcon("src/data/img/delete.png"));
            btnDelete.addActionListener(e -> {
                supprimerReponse(responsePanel);
            });

            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new GridLayout(2, 1, 5, 5));
            JTextField scoreField1 = new JTextField("1");
            scoreField1.setHorizontalAlignment(JTextField.CENTER);
            scorePanel.add(scoreField1);

            JTextField scoreField2 = new JTextField("-0.5");
            scoreField2.setHorizontalAlignment(JTextField.CENTER);
            scorePanel.add(scoreField2);

            JCheckBox cbValide = new JCheckBox();
            scorePanel.add(cbValide);

            responsePanel.add(scorePanel);

            this.panelMilieu.add(responsePanel);
            this.panelMilieu.revalidate(); // Actualise le panneau après ajout
            this.panelMilieu.repaint();

            listReponses.add(responsePanel);
            nbReponsesActuelles++;
            return true;
        }

        return false;
    }

    public boolean supprimerReponse(JPanel responsePanel) {
        if (nbReponsesActuelles > 2) {
            this.panelMilieu.remove(responsePanel);
            this.panelMilieu.revalidate();
            this.panelMilieu.repaint();
            this.listReponses.remove(responsePanel);
            nbReponsesActuelles--;
            return true;
        }
        return false;
    }

    public List<JPanel> getReponses() {
        return listReponses;
    }

    public String getQuestion(){
        return txtQst.getText();
    }
}