package Services;

import Models.Prescription;
import org.hibernate.Session;

import javax.swing.*;

public class Recepty extends JDialog{

    private JPanel panel1;
    private JList listRecepty;
    private  long medicinId = 0;
    private DefaultListModel<Prescription> dlm;

    Recepty(){
        dlm = new DefaultListModel<>();
        setModal(true);
        showRecepty(medicinId);
        setVisible(true);
    }

    public void showRecepty(long id){
        dlm.clear();
        medicinId = id;
        Session session = DbContext.getSession();
        session.beginTransaction();
        if(medicinId == 0) {
            for (Object pre : session.createQuery("from Prescription ").list())
                dlm.addElement((Prescription) pre);
        }else
            for (Object pre : session.createQuery("select prescriptions from Medicine where Medicine.id = ?1").setParameter(1,medicinId) .list())
                dlm.addElement((Prescription) pre);
        listRecepty.setModel(dlm);
        session.getTransaction().commit();
        session.close();

        add(panel1);
        setSize(500,500);
        setLocation(400,400);
        repaint();
    }
}
