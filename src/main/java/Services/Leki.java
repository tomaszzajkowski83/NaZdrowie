package Services;

import Models.Medicine;
import Models.Prescription;
import org.hibernate.Session;

import javax.swing.*;

public class Leki extends JDialog {
    private JPanel panel1;
    private JList listLeki;
    private JList listRecepty;
    private long prescriptionId = 0;
    private DefaultListModel<Medicine> dlm;
    private DefaultListModel<Prescription> dlp;

    Leki() {
        dlm = new DefaultListModel<>();
        dlp = new DefaultListModel<>();
        setModal(true);
        Session session = DbContext.getSession();
        session.beginTransaction();
        for (Object med : session.createQuery("from Medicine").list())
            dlm.addElement((Medicine) med);
        for (Object pre : session.createQuery("from Prescription ").list())
            dlp.addElement((Prescription) pre);
        listLeki.setModel(dlm);
        listRecepty.setModel(dlp);
        session.getTransaction().commit();

        listLeki.addListSelectionListener(e -> {
            listRecepty.clearSelection();
            dlp.clear();
            Session session1 = DbContext.getSession();
            session1.beginTransaction();
            for (Object pre : session1.createQuery("select prescriptions from Medicine as med where med.id = ?1").setParameter(1,((Medicine)listLeki.getSelectedValue()).getId()).list())
                dlp.addElement((Prescription) pre);
            session1.getTransaction().commit();
            session1.close();
            repaint();
        });
        listRecepty.addListSelectionListener(e->{
            listLeki.clearSelection();
            dlm.clear();
            Session session2 = DbContext.getSession();
            session2.beginTransaction();
            for (Object med : session2.createQuery("select medicines from Prescription as pres where pres.id = ?2").setParameter(2,((Prescription)listRecepty.getSelectedValue()).getId()).list())
                dlm.addElement((Medicine) med);
            session2.getTransaction().commit();
            session2.close();
            repaint();
        });

        add(panel1);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocation(1000, 400);
        repaint();
        setVisible(true);
    }
}
