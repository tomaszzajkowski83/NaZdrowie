package Services;

import Models.MedicalEmployee;
import Models.Visit;
import Models.VisitStatus;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.*;

public class Finishing extends JDialog {
    private JPanel contentPane;
    private JButton buttonFinish;
    private JButton buttonCancel;
    private JButton buttonBack;

    public Finishing(Visit visit, MedicalEmployee previous, JFrame inputData) {
        setTitle("Kończenie");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonFinish);

        buttonFinish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(visit, previous, inputData);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(previous);
            }
        });

        buttonBack.addActionListener(e -> onBack(inputData));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(previous);
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(previous);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setSize(400, 400);
        setLocation(500, 700);
        setVisible(true);
    }

    private void onOK(Visit visit, MedicalEmployee previous, JFrame inputData) {
        visit.setStatus(VisitStatus.ZREALIZOWANA);
        Session session = DbContext.getSession();
        session.beginTransaction();
        session.update(visit);
        session.getTransaction().commit();
        session.close();
        new End(previous, inputData);
        dispose();
    }

    private void onCancel(MedicalEmployee previous) {
        dispose();
        new ServiceSelection(previous);
    }

    private void onBack(JFrame inputData) {
        inputData.setVisible(true);
        dispose();
    }
}
