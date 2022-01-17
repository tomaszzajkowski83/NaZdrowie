package Services;

import Models.Address;
import Models.Person;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class AddPerson extends JDialog implements ItemListener {
    private Address adr;
    private Session session;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textName;
    private JTextField textSurname;
    private JTextField textPesel;
    private JComboBox comboBox1;
    private JLabel adrLabel;

    private AddPerson() {
        session = DbContext.getSession();
        setTitle("Create New Person");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationByPlatform(true);

        comboBox1.addItemListener(this);

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
        try {
            if (comboBox1.getSelectedItem().getClass().isInstance("")) {
                session = AddAddress.init(session, comboBox1);
                return;
            }
            Person person = new Person(textName.getText(), textSurname.getText(), textPesel.getText(), (Address) comboBox1.getSelectedItem(), LocalDate.now());
            if (person != null) {
                session.beginTransaction();
                session.save(person);
                //session.getTransaction().commit();
            }
            closeSesion();
            dispose();
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"WARNING",1);
        }
    }

    private void onCancel() {
        closeSesion();
        dispose();
    }

    private void closeSesion() {
        if (session != null) {
            session.getTransaction().commit();
            session.close();
        }
    }

    public static void init() {
        AddPerson dialog = new AddPerson();
        DefaultComboBoxModel cmb = new DefaultComboBoxModel<>();
        dialog.session.beginTransaction();
        cmb.addElement("Add New Address");
        for (Address adr : (List<Address>) dialog.session.createQuery("from Address ").list())
            cmb.addElement(adr);
        dialog.comboBox1.setModel(cmb);
        dialog.pack();
        dialog.setVisible(true);
    }

    public Address getAdr() {
        return adr;
    }

    public void setAdr(Address adr) {
        this.adr = adr;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object obj = e.getItem();
        if (obj != null) {
            if (obj.getClass().isInstance("")) {
                adrLabel.setText("Tworze nowy adress");
                return;
            }
            setAdr((Address) obj);
        }
        adrLabel.setText(adr.toString());
    }
}
