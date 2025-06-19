package bankmanagement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;
public class Signup extends JFrame implements ActionListener {
    JLabel firstNamelabel,lastName, addresslabel, emaillabel,numberlabel, passlabel,verifylabel, accountlabel, accountnumberlabel;
    JTextField firstNamefield, emailfield, lastNamefield,accountnumber;
    JPasswordField password, verifypass;
    JTextField numberfield;
    JTextArea addressarea;
    JButton create, exit, signin;
    String firstname,lastname,address,userpass,hashedPassword,verify,email,number, selected;
    NumberFormat format;
    int customerID;
    String[] accountTypes = { "savings", "current", "corporate", "kiddies" };
    JComboBox<String> comboBox;
    Login login;
    
public Signup(){
    firstNamefield = new JTextField();
     firstNamefield.setSize(150, 50);
     firstNamefield.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
     firstNamefield.setPreferredSize(new Dimension(150, 30));
    
     
     lastNamefield = new JTextField();
     lastNamefield.setSize(150, 50);
     lastNamefield.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
     lastNamefield.setPreferredSize(new Dimension(150, 30));
    
    
    numberfield = new JTextField();
    numberfield.setSize(150, 50);
    numberfield.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    numberfield.setPreferredSize(new Dimension(150, 30));
    
    
    emailfield = new JTextField();
    emailfield.setSize(150, 50);
    emailfield.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    emailfield.setPreferredSize(new Dimension(150, 30));
    
    
    addressarea = new JTextArea();
    addressarea.setSize(150, 100);
    addressarea.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    addressarea.setPreferredSize(new Dimension(150, 150));
    
    password = new JPasswordField();
    password.setSize(150, 50);
    password.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    password.setPreferredSize(new Dimension(150, 30));
    
    verifypass = new JPasswordField();
    verifypass.setSize(150, 50);
    verifypass.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    verifypass.setPreferredSize(new Dimension(150, 30));
 
    firstNamelabel  = new JLabel("Enter your First Name");
    firstNamelabel.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    firstNamelabel.setSize(30, 30);
    
    lastName  = new JLabel("Enter your First Name");
    lastName.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    lastName.setSize(30, 30);
    
    passlabel  = new JLabel("Enter your Password");
    passlabel.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    passlabel.setSize(30, 30);
    
    
    verifylabel  = new JLabel("Re-enter your password");
    verifylabel.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    verifylabel.setSize(30, 30);
    
    addresslabel  = new JLabel("Enter your House address");
    addresslabel.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    addresslabel.setSize(30, 30);
    
    emaillabel  = new JLabel("Enter your Email Address");
    emaillabel.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    emaillabel.setSize(30, 30);
    
    numberlabel  = new JLabel("Enter your Phone Number");
    numberlabel.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    numberlabel.setSize(30, 30);
    
    
    
    accountnumberlabel = new JLabel("Account Number: ");
     accountnumberlabel.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
     accountnumberlabel.setSize(30, 30);
     
    accountnumber = new JTextField();
    accountnumber.setSize(150, 50);
    accountnumber.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    accountnumber.setPreferredSize(new Dimension(150, 30));
    accountnumber.setEditable(false);
    
    create = new JButton("Create Account");
    create.setSize(30,30 );
    create.addActionListener(this);
    
    exit = new JButton ("Exit Application");
    exit.setSize(30,30);
    exit.addActionListener(this);
    
    signin = new JButton("Back to Signin page");
    signin.setSize(30,30);
    signin.addActionListener(this);
    
    
    accountlabel  = new JLabel("Account");
    accountlabel.setFont(new Font("verdana", Font.CENTER_BASELINE, 12));
    accountlabel.setSize(30, 30);
    
    comboBox = new JComboBox<>(accountTypes);
    comboBox.setSelectedIndex(0); 
    comboBox.addActionListener(this);

    
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5,5,5,5);
    
    gbc.gridx = 0;
    gbc.gridy  =0;
    add(firstNamelabel, gbc);
    
    gbc.gridx = 1;
    gbc.gridy  =0;
    add(firstNamefield, gbc);
    
    gbc.gridx = 2;
    gbc.gridy  =0;
    add(accountlabel, gbc);
    gbc.gridx = 3;
    gbc.gridy  =0;
    add(comboBox, gbc);
    
    gbc.gridx = 0;
    gbc.gridy  =1;
    add(lastName, gbc);
    
    gbc.gridx = 1;
    gbc.gridy  =1;
    add(lastNamefield, gbc);
    
    gbc.gridx = 0;
    gbc.gridy  =2;
    add(emaillabel, gbc);
    gbc.gridx = 1;
    gbc.gridy  =2;
    add(emailfield, gbc);
    
    gbc.gridx = 0;
    gbc.gridy  =3;
    add(addresslabel, gbc);
        
    gbc.gridx = 1;
    gbc.gridy  =3;
    add(addressarea, gbc);
    
        
    gbc.gridx = 0;
    gbc.gridy  =4;
    add(passlabel, gbc);
    
    gbc.gridx = 1;
    gbc.gridy  =4;
    add(password, gbc);
    
    gbc.gridx = 0;
    gbc.gridy  =5;
    add(numberlabel, gbc);
    
    gbc.gridx = 1;
    gbc.gridy  =5;
    add(numberfield, gbc);
    
    
    
    gbc.gridx = 0;
    gbc.gridy  =6;
    add(verifylabel, gbc);
    gbc.gridx = 1;
    gbc.gridy  =6;
    add(verifypass, gbc);
    
    
    gbc.gridx = 2;
    gbc.gridy  =6;
    add(accountnumberlabel, gbc);
    
    gbc.gridx = 3;
    gbc.gridy  =6;
    add(accountnumber, gbc);
    
    
    gbc.gridx = 0;
    gbc.gridy  =7;
    add(create, gbc);
    
    gbc.gridx = 1;
    gbc.gridy  =7;
    add(signin, gbc);
    
    gbc.gridx = 2;
    gbc.gridy  =7;
    add(exit, gbc);
    
    setVisible(true);
    setSize(720,720);
    setResizable(true);
    setTitle("Sign up");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
   
    
}
    

public void actionPerformed(ActionEvent e){
    
    if (e.getSource()== create){
        firstname= firstNamefield.getText();
        lastname =lastNamefield.getText();
        address = addressarea.getText();
        userpass = password.getText();
        hashedPassword = BCrypt.hashpw(userpass, BCrypt.gensalt());
        verify =verifypass.getText();
        email = emailfield.getText();
        number = numberfield.getText();
       
       
       
       if(userpass.equals(verify) && userpass.length() >=8 && !firstname.isEmpty() && !lastname.isEmpty()&&  !email.isEmpty() && !number.isEmpty() && !address.isEmpty() ){
        customerCreation();
        
        
        
        JOptionPane.showMessageDialog(this, "Proceeding to Login Page");
        this.dispose();
        login = new Login();
        
       }
       
       else{
       JOptionPane.showMessageDialog(this, "Fill in the necessary information");
       }
        
        
    }
    
    else if (e.getSource() == exit){
    System.exit(0);   
   }

    else if(e.getSource()== signin){
      login = new Login();
    }
}

public void customerCreation(){           
           try{
               con c = new con();
               String q = "INSERT INTO CUSTOMERS (firstname,lastname, address, password, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
               PreparedStatement preparedStatement = c.connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
               preparedStatement.setString(1, firstname);
               preparedStatement.setString(2, lastname);
               preparedStatement.setString(3, address);
               preparedStatement.setString(4, hashedPassword);
               preparedStatement.setString(5, email);
               preparedStatement.setString(6, number);
               
                int rowsAffected = preparedStatement.executeUpdate();
                customerID = -1;
                if (rowsAffected > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                customerID = rs.getInt(1); 
                }
                
                accountCreation(c.connection);
}
            if (rowsAffected > 0) {
                
                 Thread.sleep(5000);
                JOptionPane.showMessageDialog(this, "Account Successfully Created");
                
                firstNamefield.setText("");
                lastNamefield.setText("");
                addressarea.setText("");
                password.setText("");
                verifypass.setText("");
                emailfield.setText("");
                numberfield.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error Creating Account!");
            }
              
           }
           catch (InterruptedException e) {
            e.printStackTrace();
        }
           catch(SQLException ex){
               JOptionPane.showMessageDialog(this, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
           }
}

public void accountCreation(Connection con){
    try{
        selected = (String)comboBox.getSelectedItem();
  
        String accountNumber = generateUniqueAccountNumber(con);
        int balance = 0;
        double interest = 0.00;
        
        String p = "INSERT INTO Accounts (account_number, account_type, balance, interest_rate) VALUES ( ?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(p);
               preparedStatement.setString(1, accountNumber);
               preparedStatement.setString(2, selected);
               preparedStatement.setInt(3, balance);
               preparedStatement.setDouble(4, interest);
               preparedStatement.executeUpdate();
               
               System.out.println(customerID);
    String l = "INSERT INTO Customer_Accounts (customerID, account_number) VALUES (?,?)";
     PreparedStatement ps = con.prepareStatement(l);
               ps.setInt(1, customerID);
               ps.setString(2, accountNumber);
               ps.executeUpdate(); 
               
               
           
    
    }
    
    
    catch(SQLException a){
      a.printStackTrace();
    }

}




public String generateUniqueAccountNumber(Connection con) throws SQLException {
    Random random = new Random();
    String accountNumber;

    while (true) {
        accountNumber = String.valueOf(1_000_000_000L + (long)(random.nextDouble() * 9_000_000_000L));

        String query = "SELECT COUNT(*) FROM Accounts WHERE account_number = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                break; 
            }
        }
    }
    
    accountnumber.setText(accountNumber);

    return accountNumber;
}


}



