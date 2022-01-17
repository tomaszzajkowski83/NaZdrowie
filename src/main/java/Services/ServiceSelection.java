package Services;

import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class ServiceSelection extends JFrame {
    private JPanel contentPane;
    private JList listFinishedServices;
    private JList listRedyServices;
    private JButton writeButton;
    private JButton cancelButton;
    private JButton correctButton;
    private JLabel dataLabel;
    private JLabel logoLabel;
    private JButton buttonRefresh;
    private DefaultListModel<Object> redyServiceModel;
    private DefaultListModel<Object> finishedServiceModel;
    private MedicalEmployee medicalEmployee;

    public ServiceSelection(MedicalEmployee medicalEmployee) {
        redyServiceModel = new DefaultListModel<>();
        finishedServiceModel = new DefaultListModel<>();
        setTitle("Wybór Usłóg do Opisania");
        ImageIcon img = new ImageIcon("E:\\Z PJATK GAKKO\\MAS\\Projekty\\Projekt końcowy\\Logo.jpg");
        logoLabel.setIcon(img);
        dataLabel.setText(LocalDate.now().toString());
        loadServices(medicalEmployee);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(writeButton);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        correctButton.setEnabled(false);
        writeButton.setEnabled(false);
        listRedyServices.setModel(redyServiceModel);
        listFinishedServices.setModel(finishedServiceModel);
        listFinishedServices.addListSelectionListener(e -> {
            correctButton.setEnabled(true);
            writeButton.setEnabled(false);
            listRedyServices.clearSelection();

        });
        listRedyServices.addListSelectionListener(e -> {
            writeButton.setEnabled(true);
            correctButton.setEnabled(false);
            listFinishedServices.clearSelection();

        });

        writeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onWrite();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        correctButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCorrect();
            }
        });

        buttonRefresh.addActionListener(e -> {
            refreshData();
        });
        setMinimumSize(new Dimension(900, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void onWrite() {
        new InputData(medicalEmployee, (Treatment) listRedyServices.getSelectedValue());
        dispose();
    }

    private void onCancel() {
        new WelcomeFrame();
        dispose();
    }

    private void onCorrect() {
        dispose();
    }

    private void loadServices(MedicalEmployee medicalEmployee) {
        this.medicalEmployee = medicalEmployee;
        for (Treatment treatment : medicalEmployee.getTreatments()) {
            if (treatment.getVisit().getStatus().equals(VisitStatus.ZAREZERVWANA) || treatment.getVisit().getStatus().equals(VisitStatus.W_TRAKCIE_REALIZACJI))
                redyServiceModel.addElement(treatment);
            else finishedServiceModel.addElement(treatment);
        }
        repaint();
    }

    public void refreshData() {
        repaint();
    }
}
