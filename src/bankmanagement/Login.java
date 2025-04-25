package bankmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class Login extends JFrame implements ActionListener {

    JButton signin, exit, signup;
    JTextField namefield;
    JPasswordField passwordfield;
    JLabel namelabel, passwordLabel, titleLabel;

    // Customer info
    String lastName, pass;
    public String customerfirstname, customerlastname, customeraddress, customeremail, customernumber, accountNumber, hashedpassword, accountType, status;
    double balance, credit, debt;
    int customerID;

    public Login() {
        // Frame setup
        setTitle("Login Page");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title label
        titleLabel = new JLabel("Bank Management Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Center form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Last Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        namelabel = new JLabel("Last Name:");
        namelabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(namelabel, gbc);

        // Last Name Text Field
        gbc.gridx = 1;
        namefield = new JTextField(18);
        formPanel.add(namefield, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        // Password Field
        gbc.gridx = 1;
        passwordfield = new JPasswordField(18);
        formPanel.add(passwordfield, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        signin = new JButton("Sign In");
        signup = new JButton("Sign Up");
        exit = new JButton("Exit");

        // Style buttons (optional)
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        signin.setFont(buttonFont);
        signup.setFont(buttonFont);
        exit.setFont(buttonFont);

        // Add action listeners
        signin.addActionListener(this);
        signup.addActionListener(this);
        exit.addActionListener(this);

        // Add buttons to panel
        buttonPanel.add(signin);
        buttonPanel.add(signup);
        buttonPanel.add(exit);

        // Add panels to frame
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signin) {
            try {
                con c = new con();
                PreparedStatement preparedStatement = null;
                ResultSet result = null;

                lastName = namefield.getText();
                pass = new String(passwordfield.getPassword());

                String query = "SELECT password FROM Customers WHERE lastname = ?";
                preparedStatement = c.connection.prepareStatement(query);
                preparedStatement.setString(1, lastName);
                result = preparedStatement.executeQuery();

                if (result.next()) {
                    String password = result.getString("password");
                    if (BCrypt.checkpw(pass, password)) {
                        getCustomerDetails(lastName, password);
                        CustomerDetails customer = new CustomerDetails(
                            customerID, customerfirstname, customerlastname, password,
                            customeraddress, customeremail, customernumber,
                            accountNumber, accountType, balance, status, credit, debt
                        );
                        Dashboard dashboard = new Dashboard(customer);
                         this.dispose();
                    }else {
                    JOptionPane.showMessageDialog(this, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Lastname", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException exc) {
                System.out.println("Error: " + exc.getMessage());
            }
           
        } else if (e.getSource() == exit) {
            System.exit(0);
        } else if (e.getSource() == signup) {
            JOptionPane.showMessageDialog(this, "Redirect to Sign Up page");
        }
    }

    public void getCustomerDetails(String name, String hashedpassword) {
        con c = new con();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PreparedStatement accountStmt = null;
        ResultSet accountRS = null;

        try {
            String query = "SELECT * FROM Customers WHERE lastname = ? and password = ?";
            preparedStatement = c.connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, hashedpassword);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customerID = resultSet.getInt("customerID");
                customerfirstname = resultSet.getString("firstName");
                customerlastname = resultSet.getString("lastName");
                customeraddress = resultSet.getString("address");
                customeremail = resultSet.getString("email");
                customernumber = resultSet.getString("phone_number");
               

                String accountQuery = "SELECT account_number FROM Customer_Accounts WHERE customerID = ?";
                accountStmt = c.connection.prepareStatement(accountQuery);
                accountStmt.setInt(1, customerID);
                accountRS = accountStmt.executeQuery();

                if (accountRS.next()) {
                    accountNumber = accountRS.getString("account_number");
                    String accountDetailsQuery = "SELECT * FROM Accounts WHERE account_number = ?";
                    PreparedStatement accDetailsStmt = c.connection.prepareStatement(accountDetailsQuery);
                    accDetailsStmt.setString(1, accountNumber);
                    ResultSet accDetailsRS = accDetailsStmt.executeQuery();

                    if (accDetailsRS.next()) {
                        balance = accDetailsRS.getDouble("balance");
                        accountType = accDetailsRS.getString("account_type");
                        status = accDetailsRS.getString("status");
                        credit = accDetailsRS.getDouble("credit");
                        debt  = accDetailsRS.getDouble("debt_balance");
                    }

                    accDetailsRS.close();
                    accDetailsStmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (accountRS != null) accountRS.close();
                if (accountStmt != null) accountStmt.close();
                if (c.connection != null) c.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


class CustomerDetails {
   String firstname;
    String lastname;
    String address;
    String email;
    String number;
    String password;
     String accountNumber;
     String accountType;
     String status;
    double balance;
    double credit;
    double debt;
    
     int CustomerID;
    public CustomerDetails(Integer CustomerID, String firstname,String lastname,
            String pass, String address, String email, String number, String accountNumber,
            String accountType, Double balance, String status, Double credit, Double debt) {
        this.CustomerID = CustomerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address; 
        this.email = email;
        this.number = number; 
        this.password = pass; 
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.accountType  = accountType;
        this.credit= credit;
        this.status = status;
        this.debt = debt;
    }

    public String getName() {
        return firstname + " " +  lastname;
    }
    
     public String getAddress() {
        return address;
    }
     
    public String getAccountType() {
        return accountType;
    }
       public String getEmail() {
        return email;
    }
         public String getNumber() {
        return number;
    }
       public String getStatus() {
        return status;
    }
         public double getCredit() {
        return credit;
    }

    public String getPass() {
        return password;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
      public Double getBalance() {
        return balance;
    }
      
   public void setName(String name) throws SQLException {
    String[] parts = name.trim().split("\\s+", 2);
    this.firstname = parts[0];
    this.lastname = (parts.length > 1) ? parts[1] : "";

    con c = new con(); 
    String sql = "UPDATE Customers SET FirstName = ?, LastName = ? WHERE CustomerID = ?";

    try (PreparedStatement statement = c.connection.prepareStatement(sql)) {
        statement.setString(1, this.firstname);
        statement.setString(2, this.lastname);
        statement.setInt(3, this.CustomerID); 
        statement.executeUpdate();
    } finally {
        if (c.connection != null) {
            c.connection.close();
        }
    }
}
   

       
    
      public void setAddress(String address) throws SQLException {
        this.address = address;
        con c = new con(); 
     String sql = "UPDATE Customers SET address = ? WHERE CustomerID = ?";

    PreparedStatement statement = null;

    try {
        statement = c.connection.prepareStatement(sql);
        statement.setString(1, this.address);
        statement.setInt(2, this.CustomerID);
        statement.executeUpdate();
    } 
    finally {
        if (statement != null) {
            statement.close();
        }
        if (c.connection != null) {
            c.connection.close(); 
        }
    }
    }
      
       public void setEmail(String Email) throws SQLException {
        this.email = Email;
        con c = new con(); 
     String sql = "UPDATE Customers SET Email = ? WHERE CustomerID = ?";

    PreparedStatement statement = null;

    try {
        statement = c.connection.prepareStatement(sql);
        statement.setString(1, this.email);
        statement.setInt(2, this.CustomerID);
        statement.executeUpdate();
    } 
    finally {
        if (statement != null) {
            statement.close();
        }
        if (c.connection != null) {
            c.connection.close(); 
        }
    }
    
        
    }
      public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
       
      public void setNumber(String number) throws SQLException{
        this.number = number;
        con c = new con(); 
     String sql = "UPDATE Customers SET phone_number = ? WHERE CustomerID = ?";

    PreparedStatement statement = null;

    try {
        statement = c.connection.prepareStatement(sql);
        statement.setString(1, this.number);
        statement.setInt(2, this.CustomerID);
        statement.executeUpdate();
    } 
    finally {
        if (statement != null) {
            statement.close();
        }
        if (c.connection != null) {
            c.connection.close(); 
        }
    }
     }
      
     public void setWithdrawalBalance(double balance, double withdrawal) {
        double finalbalance = balance - withdrawal;
        this.balance = finalbalance;
        con c = new con();
    PreparedStatement updateBalanceStmt = null;
    PreparedStatement insertTransactionStmt = null;

    try {
        String updateQuery = "UPDATE Accounts SET balance = ? WHERE account_number = ?";
        updateBalanceStmt = c.connection.prepareStatement(updateQuery);
        updateBalanceStmt.setDouble(1, this.balance);
        updateBalanceStmt.setString(2, this.accountNumber);
        updateBalanceStmt.executeUpdate();

        String insertQuery = "INSERT INTO Transactions (account_number, transaction_date, transaction_type, amount, balance) VALUES (?, NOW(), ?, ?, ?)";
        insertTransactionStmt = c.connection.prepareStatement(insertQuery);
        insertTransactionStmt.setString(1, this.accountNumber); 
        insertTransactionStmt.setString(2, "Withdrawal");
        insertTransactionStmt.setDouble(3, withdrawal);
        insertTransactionStmt.setDouble(4, finalbalance); 
        insertTransactionStmt.executeUpdate();
        
     

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (updateBalanceStmt != null) updateBalanceStmt.close();
            if (insertTransactionStmt != null) insertTransactionStmt.close();
            if (c.connection != null) c.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
    
      public void setDepositBalance(double deposit) throws SQLException{
        this.balance += deposit;
        con c = new con();
    PreparedStatement updateBalanceStmt = null;
    PreparedStatement insertTransactionStmt = null;

    try {
        String updateQuery = "UPDATE Accounts SET balance = ? WHERE account_number = ?";
        updateBalanceStmt = c.connection.prepareStatement(updateQuery);
        updateBalanceStmt.setDouble(1, this.balance);
        updateBalanceStmt.setString(2, this.accountNumber);
        updateBalanceStmt.executeUpdate();

        String insertQuery = "INSERT INTO Transactions (account_number, transaction_date, transaction_type, amount, balance) VALUES (?, NOW(), ?, ?, ?)";
        insertTransactionStmt = c.connection.prepareStatement(insertQuery);
        insertTransactionStmt.setString(1, this.accountNumber); 
        insertTransactionStmt.setString(2, "Deposit");
        insertTransactionStmt.setDouble(3, deposit);
        insertTransactionStmt.setDouble(4, balance); 
        insertTransactionStmt.executeUpdate();
        
   

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,"Error", "Cannot Make Deposit", JOptionPane.ERROR_MESSAGE); 
        e.printStackTrace();
                
    } finally {
        try {
            if (updateBalanceStmt != null) updateBalanceStmt.close();
            if (insertTransactionStmt != null) insertTransactionStmt.close();
            if (c.connection != null) c.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      }
      
      public void setCredit(Double amount) throws SQLException{
        double finalcredit = amount * 0.0001;
        this.credit = finalcredit;
        con c = new con(); 
     String sql = "UPDATE Accounts SET credit = ? WHERE account_number = ?";

    PreparedStatement statement = null;

    try {
        statement = c.connection.prepareStatement(sql);
        statement.setDouble(1, this.credit);
        statement.setString(2, this.accountNumber);
        statement.executeUpdate();
    } 
    finally {
        if (statement != null) {
            statement.close();
        }
        if (c.connection != null) {
            c.connection.close(); 
        }
    }
    }
     public void setStatus(String status) throws SQLException{
        this.status = status;
         con c = new con(); 
     String sql = "UPDATE Accounts SET status = ? WHERE account_number = ?";

    PreparedStatement statement = null;

    try {
        statement = c.connection.prepareStatement(sql);
        statement.setString(1, this.status);
        statement.setString(2, this.accountNumber);
        statement.executeUpdate();
    } 
    finally {
        if (statement != null) {
            statement.close();
        }
        if (c.connection != null) {
            c.connection.close(); 
        }
    }
    }    
      public Double getDebt() {
        return debt;
    }
      
    public void setDebt(Double debt) throws SQLException{
    this.debt = debt;
     con c = new con(); 
     String sql = "UPDATE Accounts SET debt_balance = ? WHERE account_number = ?";

    PreparedStatement statement = null;

    try {
        statement = c.connection.prepareStatement(sql);
        statement.setDouble(1, this.debt);
        statement.setString(2, this.accountNumber);
        statement.executeUpdate();
    } 
    finally {
        if (statement != null) {
            statement.close();
        }
        if (c.connection != null) {
            c.connection.close(); 
        }
    }
    }
    
    
     public void setPass(String newPassword) throws SQLException {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        con c = new con(); 
        String sql ="UPDATE Customers SET password = ? WHERE customerID = ?";
        PreparedStatement stmt = null;
        try {
            stmt = c.connection.prepareStatement(sql);
            stmt.setString(1, hashedPassword);
            stmt.setInt(2, this.CustomerID);
            stmt.executeUpdate();

            this.password = hashedPassword;  // Also update it locally

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
     public String getPassword() {
    String result = null;
    con c = new con(); 
    String sql = "SELECT password FROM Customers WHERE customerID = ?";
    PreparedStatement stmt = null;

    try {
        stmt = c.connection.prepareStatement(sql);
        stmt.setInt(1, this.CustomerID); // Assuming CustomerID is an int

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            result = rs.getString("password");
            this.password = result;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return result;
}
}



