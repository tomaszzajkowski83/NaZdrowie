package Services;

import Models.MedicalEmployee;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WelcomeFrame extends JFrame {

    private JPanel panelLogo;
    private JLabel logo;
    private JPanel welcomePane;
    private JButton forwardButton;
    private JButton exitButton;
    private JPanel footerPane;
    private JPanel buttonPanel;
    private JTextArea welcomeText;
    private JComboBox comboEMployee;

    public WelcomeFrame(){
        setTitle("Na Zdrowie");
        ImageIcon img = new ImageIcon("E:\\Z PJATK GAKKO\\MAS\\Projekty\\Projekt końcowy\\Logo.jpg");
        logo.setIcon(img);
        panelLogo.add(logo);

        DefaultComboBoxModel dcb = new DefaultComboBoxModel();
        Session session = DbContext.getSession();
        session.beginTransaction();
        for(MedicalEmployee emp : (List<MedicalEmployee>)session.createQuery("from MedicalEmployee ").list())
            dcb.addElement(emp);
        session.getTransaction().commit();
        session.close();
        comboEMployee.setModel(dcb);
        welcomePane.setLayout(new BorderLayout());
        welcomeText.setEnabled(false);
        welcomeText.setLineWrap(true);
        welcomeText.setWrapStyleWord(true);
        welcomeText.setMargin(new Insets(50,50,0,50));
        welcomeText.setPreferredSize(new Dimension(500,100));
        welcomeText.setText("Witaj w naszym Centrum. Aby rozpocząć opisywanie usług medycznych wcisnij Dalej. Aby wyjść z programu wciśnij Zamknij.");
        welcomePane.add(welcomeText,BorderLayout.CENTER);
        welcomePane.add(buttonPanel,BorderLayout.SOUTH);
        add(panelLogo, BorderLayout.NORTH);
        add(welcomePane,BorderLayout.CENTER);
        add(footerPane,BorderLayout.SOUTH);
        setResizable(false);
        setLocation(800,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800,600));
        setVisible(true);

        forwardButton.addActionListener(e-> {
            new ServiceSelection((MedicalEmployee) comboEMployee.getSelectedItem());
            dispose();
        });
        exitButton.addActionListener(e->System.exit(0));
    }
}
