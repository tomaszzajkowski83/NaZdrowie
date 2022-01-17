package Services;

import Models.MedicalEmployee;

import javax.swing.*;
import java.awt.event.*;

public class End extends JDialog {
    private JPanel contentPane;
    private JButton lekiButton;
    private JButton okButton;

    public End(MedicalEmployee previous, JFrame inputData) {
        setContentPane(contentPane);
        setModal(true);

        lekiButton.addActionListener(e->new Leki());
        okButton.addActionListener(e->{
            inputData.dispose();
            dispose();
            onCancel(previous);});

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                inputData.dispose();
                onCancel(previous);
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputData.dispose();
                onCancel(previous);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setSize(400,400);
        setLocation(600,800);
        setVisible(true);
    }
    private void onCancel(MedicalEmployee previous){
        dispose();
        new ServiceSelection(previous);
    }
}
