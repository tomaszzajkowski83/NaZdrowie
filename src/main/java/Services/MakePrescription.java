package Services;

import Models.MedicalEmployee;
import Models.Medicine;
import Models.Prescription;
import Models.Visit;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class MakePrescription extends JDialog {
    private JPanel contentPane;
    private JButton anotherButton;
    private JButton forwardButton;
    private JButton cancelButton;
    private JLabel doctorLabel;
    private JLabel jobLicence;
    private JLabel creattionDateLabel;
    private JTextField codeText;
    private JComboBox comboMedicine;
    private JTextArea textRecomendations;
    private DefaultComboBoxModel<Medicine> dfc;

    public MakePrescription(Visit visit, MedicalEmployee previous, JFrame inputData) {
        this(visit, previous, inputData, null);
    }

    private MakePrescription(Visit visit, MedicalEmployee previous, JFrame inputData, DefaultComboBoxModel defComb) {
        setTitle("Tworzenie Recepty");
        dfc = defComb;
        setContentPane(contentPane);
        setModal(true);
        setLocation(400, 400);
        setResizable(false);
        getRootPane().setDefaultButton(anotherButton);
        jobLicence.setText("" + visit.getMedicalEmployee().getJobLicenceNumber());
        doctorLabel.setText(visit.getMedicalEmployee().getPerson().getFirstName() + " " + visit.getMedicalEmployee().getPerson().getLastName());
        creattionDateLabel.setText("Wystawiono: " + LocalDate.now());
        if (dfc == null)
            dfc = new DefaultComboBoxModel<>();
        if (dfc.getSize() == 0)
            for (Medicine med : getMedicins())
                dfc.addElement(med);

        comboMedicine.setModel(dfc);

        anotherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(visit);
                dfc.removeElement(comboMedicine.getSelectedItem());
                repaint();
                new MakePrescription(visit, previous, inputData, dfc);
            }
        });

        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(visit);
                forward(visit, previous, inputData);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack(inputData);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onBack(inputData);
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack(inputData);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }

    private void onOK(Visit visit) {
        Prescription prescription = new Prescription();
        prescription.setVisit(visit);
        prescription.setE_code(codeText.getText());
        prescription.addMedicine((Medicine) comboMedicine.getSelectedItem());
        prescription.setRecommendations(textRecomendations.getText());
        dispose();
    }

    private void forward(Visit visit, MedicalEmployee previous, JFrame inputData) {
        dispose();
        new Finishing(visit, previous, inputData);
    }

    private void onBack(JFrame previous) {
        previous.setVisible(true);
        dispose();
    }

    private List<Medicine> getMedicins() {
        Session session = DbContext.getSession();
        session.beginTransaction();
        List<Medicine> medicins = session.createQuery("from Medicine ").list();
        session.getTransaction().commit();
        session.close();
        return medicins;
    }
}
