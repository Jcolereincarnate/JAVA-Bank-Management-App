package bankmanagement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.ResultSet;
public class Employee extends JFrame implements ActionListener {
     JTextField textField1;
    JPasswordField passwordField1;
    JButton b1,b2;
    Employee(){
        JLabel label1 = new JLabel("Username");
        label1.setBounds(40,20,100,30);
        label1.setFont(new Font("Tahoma",Font.BOLD,16));
        label1.setForeground(Color.WHITE);
        add(label1);

        JLabel label2 = new JLabel("Password");
        label2.setBounds(40,70,100,30);
        label2.setFont(new Font("Tahoma",Font.BOLD,16));
        label2.setForeground(Color.WHITE);
        add(label2);

        textField1 = new JTextField();
        textField1.setBounds(150,20,150,30);
        textField1.setForeground(Color.WHITE);
        textField1.setFont(new Font("Tahoma", Font.PLAIN,15));
        textField1.setBackground(new Color(26,104,110));
        add(textField1);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(150,70,150,30);
        passwordField1.setForeground(Color.WHITE);
        passwordField1.setBackground(new Color(26,104,110));
        add(passwordField1);

        

        b1 = new JButton("Login");
        b1.setBounds(40,140,120,30);
        b1.setFont(new Font("serif", Font.BOLD, 15));
        b1.setBackground(Color.WHITE);
        b1.setForeground(Color.BLACK);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(180,140,120,30);
        b2.setFont(new Font("serif", Font.BOLD, 15));
        b2.setBackground(Color.WHITE);
        b2.setForeground(Color.BLACK);
        b2.addActionListener(this);
        add(b2);


        getContentPane().setBackground(new Color(3,45,48));
        setLayout(null); 
          setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false );
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Bank Management Software");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1){
            try {
                con c = new con();
                String user = textField1.getText();
                String pass = passwordField1.getText();

                String q = "select * from Employees where username = '"+user+"' and password = '"+pass+"'";
                ResultSet resultSet = c.statement.executeQuery(q);
                if (resultSet.next()){
                    new Login();
                    setVisible(false);
                }else {
                    JOptionPane.showMessageDialog(null,"Invalid");
                }

            }catch (SQLException E){
                 JOptionPane.showMessageDialog(null," Error:" + E.getMessage(), "Error", JOptionPane.DEFAULT_OPTION);
            }

        }else {
            System.exit(102);
        }
    }
}
