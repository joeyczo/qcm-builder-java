package qcm.vue;

import qcm.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Accueil extends JFrame implements ActionListener
{
    private JPanel  pnlBtns;

    private JButton btnCreerQuestion;
    private JButton btnCreerEval;
    private JButton btnParametres;

    private JLabel lblTitre;

    private FrameParametres frameParametres;
    private FrameCreerQst frameCreerQuestion;
    private FrameCreerEval  frameCreerEval;

    private Controleur      ctrl;


    public Accueil(Controleur ctrl)
    {

        this.ctrl = ctrl;

        this.setTitle("Accueil");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.pnlBtns           = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets             = new Insets(10, 10, 10, 10);
        gbc.fill               = GridBagConstraints.HORIZONTAL;
        gbc.gridx              = 0;
        gbc.gridy              = GridBagConstraints.RELATIVE;

        this.lblTitre = new JLabel("QCM Builder");
        this.lblTitre.setFont(new Font("Arial", Font.BOLD, 30));

        this.btnCreerEval     = new JButton("Créer une évaluation");
        this.btnCreerQuestion = new JButton("Créer une question");
        this.btnParametres    = new JButton("Paramètres");

        Dimension btnSize = new Dimension(200, 50);
        this.btnCreerQuestion.setPreferredSize(btnSize);
        this.btnCreerEval    .setPreferredSize(btnSize);
        this.btnParametres   .setPreferredSize(btnSize);

        this.btnCreerQuestion.addActionListener(this);
        this.btnCreerEval    .addActionListener(this);
        this.btnParametres   .addActionListener(this);

        this.pnlBtns.add(lblTitre);
        this.pnlBtns.add(btnCreerQuestion, gbc);
        this.pnlBtns.add(btnCreerEval, gbc);
        this.pnlBtns.add(btnParametres, gbc);

        this.add(pnlBtns);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /*  -----------------  */
    /*	 Autres méthodes   */
    /*  -----------------  */

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnParametres) {
            this.frameParametres = new FrameParametres(this, this.ctrl);
        }

        if(e.getSource() == this.btnCreerQuestion) {
            this.frameCreerQuestion = new FrameCreerQst(this, this.ctrl);
        }

        if(e.getSource() == this.btnCreerEval) {
            this.frameCreerEval = new FrameCreerEval(this, this.ctrl);
        }
    }
}