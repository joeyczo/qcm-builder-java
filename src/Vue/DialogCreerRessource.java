package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogCreerRessource extends JDialog implements ActionListener {

    private JTextField textField;
    private JButton okButton;
    private String inputText;

    public DialogCreerRessource(FrameParametres parent) {
        super(parent, "Input Dialog", true);
        this.setLayout(new FlowLayout());

        this.textField = new JTextField(20);
        this.okButton = new JButton("OK");

        this.add(new JLabel("Enter something:"));
        this.add(this.textField);
        this.add(this.okButton);

        this.okButton.addActionListener(this);

        this.setSize(300, 150);
        this.setLocationRelativeTo(parent);
    }


    /* ------------ */
    /*	 Getters 	*/
    /* ------------ */

    public String getInputText() {
        return this.inputText;
    }


    /*  -----------------  */
    /*	 Autres méthodes   */
    /*  -----------------  */


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.okButton) {
            this.inputText = this.textField.getText();
            this.setVisible(false);
        }
    }
}
