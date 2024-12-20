package qcm.vue;

import qcm.Controleur;
import qcm.metier.Evalutation;
import qcm.metier.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelVisuEval extends JPanel implements ActionListener {

    private FrameVisuEval   frameParent;
    private Evalutation     evalutation;
    private Controleur      ctrl;

    private JButton         btnAnnuler;
    private JButton         btnConfirmer;

    private Font            fontGeneralGras;

    public PanelVisuEval(FrameVisuEval frame, Controleur ctrl, Evalutation eval) {

        this.setLayout(new BorderLayout());

        this.frameParent    = frame;
        this.ctrl           = ctrl;
        this.evalutation    = eval;

        this.fontGeneralGras = new Font("Arial", Font.BOLD, 16);

        JPanel  panelButtons    = new JPanel();

        this.btnAnnuler         = new JButton("Annuler");
        this.btnConfirmer       = new JButton("Confirmer et exporter");
        JLabel  labelInfos      = new JLabel("<html><h1>Nouveau questionnaire</h1></html>");
        JLabel  labelNbQst      = new JLabel();
        
        String estChrono = eval.evaluation() ? "Oui" : "Non";

        String txtQuestions = this.getTexteQuestions(eval.ensQuestion());

        double nbPoints = 0.0;

        for (Question q : eval.ensQuestion())
            nbPoints += q.getNbPoints();
        
        String txtLabel         =    "<html><ul style=\"font-size: 12px;\">"                        +
                                    "<li>Nombre de questions : " + eval.nbQuestions() + "</li>"     +
                                    "<li>Questionnaire chronométré : " + estChrono + "   </li>"     +
                                    "<li>Nombre de points : " + nbPoints + "</li>"                  +
                                    "<li>Ressource : " + eval.ressource().getNom() + "</li>"        +
                                    "</ul><br><hr style=\"width: 100vw;\"><ul style=\"font-size: 12px;\">" + txtQuestions + "</ul></html>";

        labelNbQst.setText(txtLabel);

        panelButtons.add(this.btnAnnuler);
        panelButtons.add(this.btnConfirmer);
        this.add(labelInfos  , BorderLayout.NORTH);
        this.add(labelNbQst);
        this.add(panelButtons, BorderLayout.SOUTH);

        this.btnAnnuler  .setFont(this.fontGeneralGras);
        this.btnConfirmer.setFont(this.fontGeneralGras);

        this.btnAnnuler  .addActionListener(this);
        this.btnConfirmer.addActionListener(this);

    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.btnAnnuler) {
            this.frameParent.fermerFenetre();
        }

        if (e.getSource() == this.btnConfirmer) {
            if (this.ctrl.exporterEval(this.evalutation)) {
                JOptionPane.showMessageDialog(this, "L'évaluation a bien été exportée !", "Évaluation générée", JOptionPane.INFORMATION_MESSAGE);
                this.frameParent.fermerFenetre();
            }
        }

    }

    /**
     * Récuéprer le texte au format HTML de toutes les questions
     * @return Le string avec toutes les questions
     */
    private String getTexteQuestions (ArrayList<Question> ensQuestion) {

        String sRet = "";

        for (Question q : ensQuestion) {
            
            String txtQuestion = q.getTexteQuestion().replaceAll("\n", ".").replaceAll("\t", "");

            if (txtQuestion.length() > 85) txtQuestion = txtQuestion.substring(0, 85) + "...";

            sRet += "<li style=\"margin: 10px 0;width: 100%;\">Notion : <b>" + q.getNotion().getNom()
                    + "</b> - Nb Points : <i>" + q.getNbPoints()
                    +"</i> - Difficulté : <i style=\"color: " + q.getDifficulte().getCouleurHtml() + "\"> " + q.getDifficulte() +"</i> "
                    + " - Type de question : " + q.getTypeQuestion().getNomType() +
                    "<br> " + txtQuestion + "</li>";

        }

        return sRet;

    }

}
