package Services;

import Models.Address;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.*;

public class AddAddress extends JDialog {
    private Session session;
    private JComboBox caller;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textCity;
    private JTextField textStreet;
    private JTextField textHouseNumber;

    public AddAddress() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationByPlatform(true);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
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
    }

    private void onOK() {
        Address adr = new Address(textCity.getText(), textStreet.getText(), Integer.parseInt(textHouseNumber.getText()));
        if (adr != null) {
            session.beginTransaction();
            session.save(adr);
            session.getTransaction().commit();
            if(caller!=null) {
                caller.addItem(adr);
                caller.setSelectedItem(adr);
            }
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static Session init(Session session, JComboBox cont) {
        AddAddress dialog = new AddAddress();
        dialog.caller=cont;
        dialog.session = session;
        dialog.pack();
        dialog.setVisible(true);
        return session;
    }
}
