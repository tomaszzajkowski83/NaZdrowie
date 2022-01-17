package Services;

import Models.DokumentType;
import Models.Visit;
import com.github.lgooddatepicker.components.CalendarPanel;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.*;

public class AdditionalDocument extends JDialog {
    private JPanel contentPane;
    private JButton cancelButton;
    private JButton forwardButton;
    private JComboBox comboDocumentType;
    private JLabel patientLabel;
    private JLabel creationDateLabel;
    private JTextArea textDescription;
    private CalendarPanel expDateCalendar;

    public AdditionalDocument(Visit visit) {
        setTitle("Tworzenie Dodatkowego Dokument");
        setContentPane(contentPane);
        setModal(true);
        setLocation(400,400);
        setResizable(false);
        getRootPane().setDefaultButton(cancelButton);
        creationDateLabel.setText("Data wystawienia: "+visit.getDate());
        patientLabel.setText("Pacjent nr: "+visit.getPatient().getPatientNumber());
        DefaultComboBoxModel<DokumentType> cmb = new DefaultComboBoxModel<>();
        for (DokumentType dt : DokumentType.values())
            cmb.addElement(dt);
        comboDocumentType.setModel(cmb);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(visit);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setVisible(true);
    }

    private void onOK(Visit visit) {
        Models.AdditionalDocument doc = Models.AdditionalDocument.createDocument(expDateCalendar.getSelectedDate(),(DokumentType) comboDocumentType.getSelectedItem());
        doc.setVisit(visit);
        doc.setDescription(textDescription.getText());
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
