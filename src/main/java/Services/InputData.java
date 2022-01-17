package Services;

import Models.*;

import javax.swing.*;
import java.awt.*;

public class InputData extends JFrame {
    private JTextField diagnosisField;
    private JTextArea textInterviev;
    private JButton utwórzDokumentDodatkowyButton;
    private JRadioButton pominńReceptęRadioButton;
    private JRadioButton wystawReceptęRadioButton;
    private JButton anulujButton;
    private JButton dalejButton;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JLabel logoLabel;

    public InputData(MedicalEmployee medicalEmployee, Treatment treatment) {
        setTitle("Wprowadzanie Danych");
        nameLabel.setText(treatment.getVisit().getPatient().getPerson().getFirstName() + " " + treatment.getVisit().getPatient().getPerson().getLastName());
        diagnosisField.setSize(200, 50);
        ImageIcon img = new ImageIcon("E:\\Z PJATK GAKKO\\MAS\\Projekty\\Projekt końcowy\\Logo.jpg");
        logoLabel.setIcon(img);
        textInterviev.setColumns(2000);
        anulujButton.addActionListener(e -> onCancel(medicalEmployee));
        dalejButton.addActionListener(e -> onForward(treatment.getVisit(), medicalEmployee));
        utwórzDokumentDodatkowyButton.addActionListener(e -> onCreateNewDocument(treatment.getVisit()));
        wystawReceptęRadioButton.setSelected(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(pominńReceptęRadioButton);
        buttonGroup.add(wystawReceptęRadioButton);
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 600));
        setVisible(true);
    }

    private void onCancel(MedicalEmployee medicalEmployee) {
        new ServiceSelection(medicalEmployee);
        dispose();
    }

    private void onForward(Visit visit, MedicalEmployee medicalEmployee) {
        visit.setMedicalInterviev(textInterviev.getText());
        visit.setDiagnosis(diagnosisField.getText());
        dispose();
        if (wystawReceptęRadioButton.isSelected()) {
            new MakePrescription(visit, medicalEmployee, this);
        } else
            new Finishing(visit, medicalEmployee, this);
    }

    private void onCreateNewDocument(Visit visit) {
        new AdditionalDocument(visit);
    }
}
