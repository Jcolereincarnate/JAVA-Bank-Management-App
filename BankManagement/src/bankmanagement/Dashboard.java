package bankmanagement;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;


public class Dashboard extends JFrame implements ActionListener {
    private JButton profileButton, settingButton, transactionButton, depositButton, withdrawalButton, loanButton;
    private CardLayout cardLayout;
    private JPanel cardPanel, sidebarPanel;
    private JTextField nameField, emailField, numberField, balanceField, accountNumberField, accountTypeField;
    private JTextArea addressArea;
    private JLabel nameLabel, addressLabel, emailLabel, numberLabel, balanceLabel, accountNumberLabel, accountTypeLabel, debtLabel, debttext;
    private JButton exitButton, editButton, saveButton;
    private String newName, newAddress, newEmail, newNumber;
    private JTable transactionTable;
    JButton withdrawButton;
    
 
    private final Color PRIMARY_COLOR = new Color(25, 118, 210);     
    private final Color SECONDARY_COLOR = new Color(66, 165, 245);  
    private final Color ACCENT_COLOR = new Color(255, 152, 0);       
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245); 
    private final Color CARD_COLOR = new Color(255, 255, 255);       
    private final Color TEXT_COLOR = new Color(33, 33, 33);          
    private final Color SIDEBAR_COLOR = new Color(38, 50, 56);       
    private final Color SIDEBAR_TEXT_COLOR = new Color(255, 255, 255); 
    
 
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font SIDEBAR_FONT = new Font("Segoe UI", Font.BOLD, 16);
    
    public Dashboard(CustomerDetails customer) {
        // Set up the frame
        setTitle("Bank Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(true);
        
        // Set the look and feel to system default for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Main layout
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Create components
        createSidebar();
        createCardPanel(customer);
        
        // Add components to frame
        add(sidebarPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        
        // Display the frame
        setVisible(true);
    }
    
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(SIDEBAR_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(300, getHeight()));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        JLabel bankLogo = new JLabel("BANK MANAGEMENT");
        bankLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bankLogo.setForeground(SIDEBAR_TEXT_COLOR);
        bankLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        bankLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        profileButton = createSidebarButton("Profile");
        settingButton = createSidebarButton("Settings");
        transactionButton = createSidebarButton("Transactions");
        depositButton = createSidebarButton("Deposit");
        withdrawalButton = createSidebarButton("Withdrawal");
        loanButton = createSidebarButton("Take a loan");
        
        profileButton.addActionListener(e -> {
            highlightButton(profileButton);
            cardLayout.show(cardPanel, "Profile");
        });
        settingButton.addActionListener(e -> {
            highlightButton(settingButton);
            cardLayout.show(cardPanel, "Settings");
        });
        transactionButton.addActionListener(e -> {
            highlightButton(transactionButton);
            cardLayout.show(cardPanel, "Transactions");
        });
        depositButton.addActionListener(e -> {
            highlightButton(depositButton);
            cardLayout.show(cardPanel, "Deposit");
        });
        withdrawalButton.addActionListener(e -> {
            highlightButton(withdrawalButton);
            cardLayout.show(cardPanel, "Withdrawal");
        });
        loanButton.addActionListener(e -> {
            highlightButton(loanButton);
            cardLayout.show(cardPanel, "Loan");
        });
        
        // Add components to sidebar
        sidebarPanel.add(bankLogo);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(profileButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(settingButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(transactionButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(depositButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(withdrawalButton);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(loanButton);
        
        // Highlight the profile button by default
        highlightButton(profileButton);
    }
    
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(SIDEBAR_FONT);
        button.setForeground(SIDEBAR_TEXT_COLOR);
        button.setBackground(SIDEBAR_COLOR);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(190, 50));
        button.setPreferredSize(new Dimension(190, 50));
        
        // Add padding
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!button.getBackground().equals(SECONDARY_COLOR)) {
                    button.setBackground(new Color(55, 71, 79)); // Slightly lighter than sidebar
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!button.getBackground().equals(SECONDARY_COLOR)) {
                    button.setBackground(SIDEBAR_COLOR);
                }
            }
        });
        
        return button;
    }
    
    private void highlightButton(JButton selectedButton) {
        // Reset all buttons
        profileButton.setBackground(SIDEBAR_COLOR);
        settingButton.setBackground(SIDEBAR_COLOR);
        transactionButton.setBackground(SIDEBAR_COLOR);
        depositButton.setBackground(SIDEBAR_COLOR);
        withdrawalButton.setBackground(SIDEBAR_COLOR);
        
        // Highlight selected button
        selectedButton.setBackground(SECONDARY_COLOR);
    }
    
    private void createCardPanel(CustomerDetails customer) {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BACKGROUND_COLOR);
        
        // Create panels for each section
        JPanel profilePanel = createProfilePanel(customer);
        JPanel settingsPanel = createSettingsPanel(customer);
        JPanel transactionPanel = createTransactionPanel(customer);
        JPanel depositPanel = createDepositPanel(customer);
        JPanel withdrawalPanel = createWithdrawalPanel(customer);
        JPanel loanPanel = createloanPanel(customer);
        
        // Add panels to card layout
        cardPanel.add(profilePanel, "Profile");
        cardPanel.add(settingsPanel, "Settings");
        cardPanel.add(transactionPanel, "Transactions");
        cardPanel.add(depositPanel, "Deposit");
        cardPanel.add(withdrawalPanel, "Withdrawal");
        cardPanel.add(loanPanel, "Loan");
    }
     private JPanel createloanPanel(CustomerDetails customer){
      
     JPanel panel = new JPanel(new BorderLayout());
      panel.setBackground(BACKGROUND_COLOR);
      panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
      
      JLabel headerLabel = new JLabel("Take a loan");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(PRIMARY_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
     JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(230, 230, 230), 1),
                    new EmptyBorder(20, 20, 20, 20)
                )
            )
        ));
         JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel eligible = new JLabel("Eligibity Status: ");
        JLabel eligiblevalue = new JLabel(" ");
        JLabel eligibleamount = new JLabel("Enter amount: ");
        JTextField field = new JTextField();
        field.setEditable(true);
        field.setPreferredSize(new Dimension(250, 35));
        styleTextField(field);
        
        
        JButton eligibityButton = createStyledButton("Check Eligibility", new Color(76, 175, 80)); 
        eligibityButton.setPreferredSize(new Dimension(250, 35));
        
        eligibityButton.addActionListener(e ->{
        if(customer.getCredit()< 5){
        showCustomDialog("Error", "Ineligible, Perform transactions to increase tier level and qualify for loan", JOptionPane.INFORMATION_MESSAGE);
        eligiblevalue.setText("Ineligible" + customer.getStatus());
        }
        else if(customer.getCredit()> 5 && customer.getCredit()< 10){
      
        showCustomDialog("Hooray", "You are eligible for 100,000", JOptionPane.INFORMATION_MESSAGE);
        eligiblevalue.setText("Eligble for up to 100k");
          try {
                customer.setStatus("Tier 3");
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
           showCustomDialog("Hooray", "You are eligible for 100,000 - 200,000", JOptionPane.INFORMATION_MESSAGE);
        eligiblevalue.setText("Eligble for between 100k to 200k");
            try {
                customer.setStatus("Tier 3");
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
        
        JButton loanButton = createStyledButton("Take Loan", new Color(76, 175, 80)); 
        loanButton.setPreferredSize(new Dimension(250, 35));
       loanButton.addActionListener(e -> {
    double debtvalue = customer.getDebt();
    String fieldvalue = field.getText();

    try {
        double fielddouble = Double.parseDouble(fieldvalue);
        String status = customer.getStatus();

        if (status.equals("Tier 1")) {
            showCustomDialog("Womp", "You are not eligible", JOptionPane.INFORMATION_MESSAGE);
        }

        else if (status.equals("Tier 2")) {
            double maxLimit = 100000.0;
            double remaining = maxLimit - debtvalue;

            if (debtvalue >= maxLimit) {
                showCustomDialog("Loan Denied", "You have reached your borrowing limit.", JOptionPane.ERROR_MESSAGE);
            } else if (fielddouble > remaining) {
                showCustomDialog("Loan Denied", "You can only borrow up to: " + remaining, JOptionPane.WARNING_MESSAGE);
            } else {
                customer.setDebt(debtvalue + fielddouble);
                showCustomDialog("Loan Approved", "You have borrowed: " + fielddouble, JOptionPane.INFORMATION_MESSAGE);
                customer.setDepositBalance(fielddouble);
            }
        String debt = Double.toString(customer.getDebt());
        debttext.setText(debt);
        }

        else if (status.equals("Tier 3")) {
            double maxLimit = 200000.0;
            double remaining = maxLimit - debtvalue;

            if (debtvalue >= maxLimit) {
                showCustomDialog("Loan Denied", "You have reached your borrowing limit.", JOptionPane.ERROR_MESSAGE);
            } else if (fielddouble > remaining) {
                showCustomDialog("Loan Denied", "You can only borrow up to: " + remaining, JOptionPane.WARNING_MESSAGE);
            } else {
                customer.setDebt(debtvalue + fielddouble);
                showCustomDialog("Loan Approved", "You have borrowed: " + fielddouble, JOptionPane.INFORMATION_MESSAGE);
                customer.setDepositBalance(fielddouble);
            }
            String debt = Double.toString(customer.getDebt());
        debttext.setText(debt);
        }

        else {
            showCustomDialog("Error", "Unknown customer tier.", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException ex) {
        showCustomDialog("Invalid Input", "Please enter a valid amount.", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        showCustomDialog("Database Error", "Could not update debt. Please try again.", JOptionPane.ERROR_MESSAGE);
    }
    field.setText("");
});
        
        
        gbc.gridx = 0;
        gbc.gridy= 0;
        formPanel.add(eligible, gbc);
        
        gbc.gridx = 1;
        gbc.gridy= 0;
        formPanel.add(eligiblevalue, gbc);
        gbc.gridx = 0;
        gbc.gridy= 1;
        
        formPanel.add(eligibleamount, gbc);
        
        gbc.gridx = 1;
        gbc.gridy= 1;
        formPanel.add(field, gbc);
      
        gbc.gridx = 0;
        gbc.gridy= 3;
        formPanel.add(eligibityButton, gbc);
        
        gbc.gridx =1;
        gbc.gridy=3;
        formPanel.add(loanButton, gbc);
       
        cardPanel.add(formPanel, BorderLayout.CENTER);
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(cardPanel, BorderLayout.CENTER); 
        
     return panel;
     }
    private JPanel createProfilePanel(CustomerDetails customer) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
       
        JLabel headerLabel = new JLabel("Account Profile");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(PRIMARY_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridBagLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)
            )
        ));
        
       
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            cardPanel.getBorder()
        ));
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
    
        nameLabel = new JLabel("Name:");
        nameLabel.setFont(LABEL_FONT);
        nameLabel.setForeground(TEXT_COLOR);
        
        debtLabel = new JLabel("Debt Owed");
        debtLabel.setFont(LABEL_FONT);
        debtLabel.setForeground(TEXT_COLOR);
        
        
        debttext = new JLabel();
        debttext.setFont(LABEL_FONT);
        String debt = Double.toString(customer.getDebt());
        debttext.setText(debt);
        debttext.setForeground(TEXT_COLOR);
        
        
        nameField = new JTextField(customer.getName());
        nameField.setFont(TEXT_FONT);
        nameField.setEditable(false);
        nameField.setPreferredSize(new Dimension(250, 35));
        styleTextField(nameField);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        cardPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cardPanel.add(nameField, gbc);
        
       
        addressLabel = new JLabel("Address:");
        addressLabel.setFont(LABEL_FONT);
        addressLabel.setForeground(TEXT_COLOR);
        
        addressArea = new JTextArea(customer.getAddress());
        addressArea.setFont(TEXT_FONT);
        addressArea.setEditable(false);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        addressScrollPane.setPreferredSize(new Dimension(250, 80));
        styleTextArea(addressArea, addressScrollPane);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cardPanel.add(addressLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        cardPanel.add(addressScrollPane, gbc);
        
        // Email field
        emailLabel = new JLabel("Email:");
        emailLabel.setFont(LABEL_FONT);
        emailLabel.setForeground(TEXT_COLOR);
        
        emailField = new JTextField(customer.getEmail());
        emailField.setFont(TEXT_FONT);
        emailField.setEditable(false);
        emailField.setPreferredSize(new Dimension(250, 35));
        styleTextField(emailField);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        cardPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cardPanel.add(emailField, gbc);
        
        // Number field
        numberLabel = new JLabel("Phone Number:");
        numberLabel.setFont(LABEL_FONT);
        numberLabel.setForeground(TEXT_COLOR);
        
        numberField = new JTextField(customer.getNumber());
        numberField.setFont(TEXT_FONT);
        numberField.setEditable(false);
        numberField.setPreferredSize(new Dimension(250, 35));
        styleTextField(numberField);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cardPanel.add(numberLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cardPanel.add(numberField, gbc);
        
        // Balance field
        balanceLabel = new JLabel("Balance:");
        balanceLabel.setFont(LABEL_FONT);
        balanceLabel.setForeground(TEXT_COLOR);
        
        balanceField = new JTextField("₦" + String.format("%.2f", customer.getBalance()));
        balanceField.setFont(TEXT_FONT);
        balanceField.setEditable(false);
        balanceField.setPreferredSize(new Dimension(250, 35));
        styleTextField(balanceField);
        balanceField.setBackground(new Color(232, 245, 233)); // Light green background
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cardPanel.add(balanceLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cardPanel.add(balanceField, gbc);
        
        // Account Number field
        accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setFont(LABEL_FONT);
        accountNumberLabel.setForeground(TEXT_COLOR);
        
        accountNumberField = new JTextField(customer.getAccountNumber());
        accountNumberField.setFont(TEXT_FONT);
        accountNumberField.setEditable(false);
        accountNumberField.setPreferredSize(new Dimension(250, 35));
        styleTextField(accountNumberField);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cardPanel.add(accountNumberLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cardPanel.add(accountNumberField, gbc);
        
        // Account Type field
        accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(LABEL_FONT);
        accountTypeLabel.setForeground(TEXT_COLOR);
        
        accountTypeField = new JTextField(customer.getAccountType());
        accountTypeField.setFont(TEXT_FONT);
        accountTypeField.setEditable(false);
        accountTypeField.setPreferredSize(new Dimension(250, 35));
        styleTextField(accountTypeField);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cardPanel.add(accountTypeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cardPanel.add(accountTypeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cardPanel.add(debtLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        cardPanel.add(debttext, gbc);
        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.setBackground(CARD_COLOR);
        
        editButton = createStyledButton("Edit Details", PRIMARY_COLOR);
        editButton.addActionListener(this);
        
        saveButton = createStyledButton("Save Details", new Color(76, 175, 80)); // Green
        saveButton.addActionListener(e -> {
            newName = nameField.getText();
            newAddress = addressArea.getText();
            newNumber = numberField.getText();
            newEmail = emailField.getText();
            
            try {
                customer.setName(newName);
                customer.setAddress(newAddress);
                customer.setEmail(newEmail);
                customer.setNumber(newNumber);
                
                nameField.setEditable(false);
                addressArea.setEditable(false);
                numberField.setEditable(false);
                emailField.setEditable(false);
                
                // Reset styling
                styleTextField(nameField);
                styleTextArea(addressArea, addressScrollPane);
                styleTextField(emailField);
                styleTextField(numberField);
                
                showCustomDialog("Success", "Details successfully updated", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                showCustomDialog("Error", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        });
        
        exitButton = createStyledButton("Exit", new Color(244, 67, 54)); // Red
        exitButton.addActionListener(this);
        
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(20, 8, 8, 8);
        cardPanel.add(buttonPanel, gbc);
        
        // Add components to main panel
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createSettingsPanel(CustomerDetails customer) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel headerLabel = new JLabel("Settings");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(PRIMARY_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        JLabel nameLabel = new JLabel("Change Password");
        nameLabel.setFont(HEADER_FONT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel currentpassword = new JLabel("Enter Current password:");
currentpassword.setFont(TEXT_FONT);
currentpassword.setHorizontalAlignment(SwingConstants.CENTER);

JPasswordField currpass = new JPasswordField();
styleTextField(currpass);

JLabel password = new JLabel("Enter new password:");
password.setFont(TEXT_FONT);
password.setHorizontalAlignment(SwingConstants.CENTER);

JLabel password2 = new JLabel("Re-enter new password:");
password2.setFont(TEXT_FONT);
password2.setHorizontalAlignment(SwingConstants.CENTER);

JPasswordField passwordtext = new JPasswordField();
styleTextField(passwordtext);

JPasswordField passwordtext2 = new JPasswordField();
styleTextField(passwordtext2);

currpass.setPreferredSize(new Dimension(200, 30));
passwordtext.setPreferredSize(new Dimension(200, 30));
passwordtext2.setPreferredSize(new Dimension(200, 30));

JButton save = createStyledButton("Save changes", PRIMARY_COLOR);
save.setFont(BUTTON_FONT);
save.setEnabled(false); // 🔒 Disable initially

// 👉 Reusable method to check if all fields are filled
Runnable checkFields = () -> {
    boolean filled = currpass.getPassword().length > 0 &&
                     passwordtext.getPassword().length > 0 &&
                     passwordtext2.getPassword().length > 0;
    save.setEnabled(filled);
};

// 👉 Add listeners to each password field
DocumentListener fieldWatcher = new DocumentListener() {
    public void insertUpdate(DocumentEvent e) { checkFields.run(); }
    public void removeUpdate(DocumentEvent e) { checkFields.run(); }
    public void changedUpdate(DocumentEvent e) { checkFields.run(); }
};

currpass.getDocument().addDocumentListener(fieldWatcher);
passwordtext.getDocument().addDocumentListener(fieldWatcher);
passwordtext2.getDocument().addDocumentListener(fieldWatcher);

// 💾 Button action logic
save.addActionListener(e -> {
    String userpass = new String(currpass.getPassword());
    String newPassword = new String(passwordtext.getPassword());
    String confirmPassword = new String(passwordtext2.getPassword());

    if (!BCrypt.checkpw(userpass, customer.getPassword())) {
        showCustomDialog("OOPS", "Your current password is incorrect", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!newPassword.equals(confirmPassword)) {
        showCustomDialog("OOPS", "Password mismatch", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        customer.setPass(newPassword);
        showCustomDialog("Success", "Your password has been updated", JOptionPane.INFORMATION_MESSAGE);

        // Optional: Clear fields and disable button again
        currpass.setText("");
        passwordtext.setText("");
        passwordtext2.setText("");
        save.setEnabled(false);

    } catch (SQLException ex) {
        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        showCustomDialog("Error", "Something went wrong while updating password", JOptionPane.ERROR_MESSAGE);
    }
});
        
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(230, 230, 230), 1),
                    new EmptyBorder(20, 20, 20, 20)
                )
            )
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        cardPanel.add(nameLabel, gbc);
        
         gbc.gridx = 0;
        gbc.gridy = 1;
        cardPanel.add(currentpassword, gbc);
        gbc.gridx = 1;
        cardPanel.add(currpass, gbc);
         gbc.gridx = 0;
        gbc.gridy = 2;
        cardPanel.add(password, gbc);
        gbc.gridx = 1;
        cardPanel.add(passwordtext, gbc);
        
         gbc.gridx = 0;
        gbc.gridy = 3;
        cardPanel.add(password2, gbc);
        gbc.gridx = 1;
        cardPanel.add(passwordtext2, gbc);
        
         gbc.gridx = 0;
        gbc.gridy = 4;
        cardPanel.add(save, gbc);
        
        // Add components to main panel
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(cardPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTransactionPanel(CustomerDetails customer) {
        JPanel panel = new JPanel(new BorderLayout());
        String accountNumber = customer.getAccountNumber();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel headerpanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Transaction History");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(PRIMARY_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
       
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(230, 230, 230), 1),
                    new EmptyBorder(20, 20, 20, 20)
                )
            )
        ));
        
        String[] columnNames = {"Date", "Transaction Type", "Amount", "Balance"};
        Object[][] data = null;
        
       con c = new con();
       PreparedStatement stmt = null;
       ResultSet rs = null;

try {
   stmt = c.connection.prepareStatement(
    "SELECT transaction_date, transaction_type, amount, balance FROM Transactions WHERE account_number = ? ORDER BY transaction_date DESC",
    ResultSet.TYPE_SCROLL_INSENSITIVE,
    ResultSet.CONCUR_READ_ONLY
);
    stmt.setString(1, customer.getAccountNumber() );

    rs = stmt.executeQuery();

   
    rs.last();
    int rowCount = rs.getRow();
    rs.beforeFirst();

    data = new Object[rowCount][4];
    int row = 0;

    while (rs.next()) {
        data[row][0] = rs.getTimestamp("transaction_date").toString();
        data[row][1] = rs.getString("transaction_type");

        double amount = rs.getDouble("amount");
        if ("Deposit".equalsIgnoreCase(rs.getString("transaction_type"))) {
            data[row][2] = "+" + amount;
        } else {
            data[row][2] = "-" + amount;
        }

        data[row][3] = rs.getDouble("balance");
        row++;
    }

} catch (SQLException e) {
    e.printStackTrace();
} finally {
    try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (c.connection != null) c.connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

transactionTable = new JTable(data, columnNames);
transactionTable.setFont(TEXT_FONT);
transactionTable.setRowHeight(30);
transactionTable.setShowGrid(false);
transactionTable.setIntercellSpacing(new Dimension(0, 0));

transactionTable.getTableHeader().setFont(LABEL_FONT);
transactionTable.getTableHeader().setBackground(new Color(240, 240, 240));
transactionTable.getTableHeader().setForeground(TEXT_COLOR);


transactionTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (!isSelected) {
            c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
        }

        if (column == 2) {
            String text = value.toString();
            if (text.startsWith("+")) {
                c.setForeground(new Color(76, 175, 80)); 
            } else if (text.startsWith("-")) {
                c.setForeground(new Color(244, 67, 54)); 
            }
        } else {
            c.setForeground(TEXT_COLOR);
        }

        // Align
        ((JLabel) c).setHorizontalAlignment(column == 0 ? JLabel.LEFT : JLabel.CENTER);
        return c;
    }
});
        
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        styleScrollPane(scrollPane);
        
        JButton refreshButton = createStyledButton("Refresh", new Color(66, 133, 244)); 
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         refreshButton.addActionListener(e ->{
        loadTransactionData(accountNumber);
         });
         
        
        cardPanel.add(scrollPane, BorderLayout.CENTER);
       
       headerpanel.add(headerLabel, BorderLayout.CENTER);
       headerpanel.add(refreshButton, BorderLayout.EAST);

 
        panel.add(headerpanel, BorderLayout.NORTH);
        panel.add(cardPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createDepositPanel(CustomerDetails customer) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
    
        JLabel headerLabel = new JLabel("Make a Deposit");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(PRIMARY_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(230, 230, 230), 1),
                    new EmptyBorder(20, 20, 20, 20)
                )
            )
        ));
       
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        
        JLabel statusLabel = new JLabel("Current Tier:");
        statusLabel.setFont(LABEL_FONT);
        JLabel statusvalue = new JLabel("Refresh to view current Tier");
        statusvalue.setFont(LABEL_FONT);
        JLabel balanceLabel = new JLabel("Current Balance:");
        balanceLabel.setFont(LABEL_FONT);
        
        JLabel balanceValue = new JLabel("₦" + String.format("%.2f", customer.getBalance()));
        balanceValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        balanceValue.setForeground(new Color(76, 175, 80)); // Green
        
         gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(statusLabel, gbc);
        
         gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(statusvalue, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(balanceLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(balanceValue, gbc);
        
      
        JLabel amountLabel = new JLabel("Deposit Amount:");
        amountLabel.setFont(LABEL_FONT);
        
        JTextField amountField = new JTextField(10);
        amountField.setFont(TEXT_FONT);
        amountField.setPreferredSize(new Dimension(250, 35));
        styleTextField(amountField);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(amountLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2; 
        formPanel.add(amountField, gbc);
  
        JButton depositButton = createStyledButton("Make Deposit", new Color(76, 175, 80)); 
        depositButton.setPreferredSize(new Dimension(200, 40));
        
depositButton.addActionListener(e -> {
    String stringDeposit = amountField.getText();

    // Check if input is empty
    if (stringDeposit.isEmpty()) {
        showCustomDialog("Error", "Enter an amount you would like to deposit", JOptionPane.ERROR_MESSAGE);
        return;
    }

    double deposit;
    try {
        deposit = Double.parseDouble(stringDeposit);
    } catch (NumberFormatException ex) {
        showCustomDialog("Error", "Please enter a valid number", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Check for minimum deposit
    if (deposit < 100) {
        showCustomDialog("Error", "Deposit must be more than ₦100", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        double debt = customer.getDebt(); 

        if (debt > 0) {
            if (deposit < debt) {
                double remainingDebt = debt - deposit;
                customer.setDebt(remainingDebt); // update the debt
                showCustomDialog("Info", "₦" + deposit + " has been used to reduce your debt.\nRemaining debt: ₦" + remainingDebt, JOptionPane.INFORMATION_MESSAGE);
                debttext.setText(Double.toString(customer.getDebt()));
            } else {
                // Deposit covers the debt and leaves extra for credit
                double remaining = deposit - debt;
                customer.setDebt(0.00); // clear debt
                customer.setDepositBalance(remaining);
                customer.setCredit(remaining);
                showCustomDialog("Message", "₦" + debt + " has been used to clear your debt.\n₦" + remaining + " has been deposited into your account.", JOptionPane.INFORMATION_MESSAGE);
                debttext.setText(Double.toString(customer.getDebt()));
                balanceField.setText("₦" + String.format("%.2f", customer.getBalance()));
            }
        } else {
            // No debt, proceed with normal deposit
            customer.setDepositBalance(deposit);
            customer.setCredit(deposit);
            showCustomDialog("Message", "You have successfully deposited ₦" + deposit, JOptionPane.INFORMATION_MESSAGE);
            balanceField.setText("₦" + String.format("%.2f", customer.getBalance()));
        }

        amountField.setText("");

    } catch (SQLException ex) {
        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        showCustomDialog("Error", "An error occurred while processing your deposit.", JOptionPane.ERROR_MESSAGE);
    }
});
         
         JButton refreshButton = createStyledButton("Refresh Balance", new Color(76, 175, 80)); 
         refreshButton.addActionListener(e ->{
         String value = customer.getStatus();
         statusvalue.setText(value);
         String newbalance = Double.toString(customer.getBalance());
         balanceValue.setText("₦" + String.format("%.2f", customer.getBalance()));
         
         });
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(depositButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        formPanel.add(refreshButton, gbc);
        
        cardPanel.add(formPanel, BorderLayout.CENTER);

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(cardPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createWithdrawalPanel(CustomerDetails customer) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
       
        JLabel headerLabel = new JLabel("Make a Withdrawal");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(PRIMARY_COLOR);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(230, 230, 230), 1),
                    new EmptyBorder(20, 20, 20, 20)
                )
            )
        ));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_COLOR);
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel balanceLabel = new JLabel("Current Balance:");
        balanceLabel.setFont(LABEL_FONT);
        
       JLabel balanceValue = new JLabel("₦" + String.format("%.2f", customer.getBalance()));
        balanceValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        balanceValue.setForeground(new Color(76, 175, 80)); 
         JLabel statusLabel = new JLabel("Current Tier:");
        statusLabel.setFont(LABEL_FONT);
        JLabel statusvalue = new JLabel("Refresh to view current Tier");
        
        
         
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(statusLabel, gbc);
        
         gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(statusvalue, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(balanceLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(balanceValue, gbc);
 
        JLabel amountLabel = new JLabel("Withdrawal Amount:");
        amountLabel.setFont(LABEL_FONT);
        
        JTextField amountField = new JTextField(10);
        amountField.setFont(TEXT_FONT);
        amountField.setPreferredSize(new Dimension(200, 35));
        styleTextField(amountField);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(amountLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(amountField, gbc);
        
        withdrawButton = createStyledButton("Make Withdrawal", PRIMARY_COLOR);
        withdrawButton.addActionListener(e ->{
           double balance = customer.getBalance();
           String  stringwithdrawal= amountField.getText();
           double withdrawal = Double.parseDouble(stringwithdrawal);
           if (stringwithdrawal.isEmpty()){
           showCustomDialog("Error","Enter an amount you would like to withdraw ", JOptionPane.ERROR_MESSAGE);
           }
           if(balance< withdrawal){
           showCustomDialog("Error", "₦ "+ stringwithdrawal +" is more than your balance, please deposit ", JOptionPane.ERROR_MESSAGE);
           }
           
           if (balance >= withdrawal){
           double customerbalance = customer.getBalance();
           customerbalance -= withdrawal;
           
           customer.setWithdrawalBalance(balance, withdrawal);
           showCustomDialog("Success", "₦ "+ stringwithdrawal +" You have successfully made a withdrawal, refresh to see balance ", JOptionPane.INFORMATION_MESSAGE);
           amountField.setText("");
           
           }
        });
        withdrawButton.setPreferredSize(new Dimension(200, 40));
        
         JButton refreshButton = createStyledButton("Refresh Balance", PRIMARY_COLOR); 
         refreshButton.addActionListener(e ->{
         String value = customer.getStatus();
         statusvalue.setText(value);
         String newbalance = Double.toString(customer.getBalance());
         //String newbalance = Double.toString(customer.getBalance());
         balanceValue.setText("₦" + String.format("%.2f", customer.getBalance()));
         
         });
         
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 30, 10, 10);
        formPanel.add(withdrawButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        formPanel.add(refreshButton, gbc);
        cardPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add components to main panel
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(cardPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(darken(bgColor, 0.1f));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void styleTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        if (textField.isEditable()) {
            textField.setBackground(new Color(255, 250, 230)); // Light yellow for editable
            textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        } else {
            textField.setBackground(Color.WHITE);
        }
    }
    
    private void styleTextArea(JTextArea textArea, JScrollPane scrollPane) {
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        if (textArea.isEditable()) {
            textArea.setBackground(new Color(255, 250, 230)); // Light yellow for editable
            scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR));
        } else {
            textArea.setBackground(Color.WHITE);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }
        
        styleScrollPane(scrollPane);
    }
    
    private void styleScrollPane(JScrollPane scrollPane) {
        // Style the scrollbars
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
    }
    
    private void showCustomDialog(String title, String message, int messageType) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        
        // Create panel with padding
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        // Create message label
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(TEXT_FONT);
        
        // Set icon based on message type
        Icon icon = null;
        if (messageType == JOptionPane.INFORMATION_MESSAGE) {
            messageLabel.setForeground(new Color(76, 175, 80)); // Green
            icon = UIManager.getIcon("OptionPane.informationIcon");
        } else if (messageType == JOptionPane.ERROR_MESSAGE) {
            messageLabel.setForeground(new Color(244, 67, 54)); // Red
            icon = UIManager.getIcon("OptionPane.errorIcon");
        }
        
        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
            panel.add(iconLabel, BorderLayout.WEST);
        }
        
        panel.add(messageLabel, BorderLayout.CENTER);
        
        // Create OK button
        JButton okButton = createStyledButton("OK", PRIMARY_COLOR);
        okButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(okButton);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setSize(350, dialog.getHeight());
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private Color darken(Color color, float fraction) {
        int red = Math.max(0, Math.round(color.getRed() * (1 - fraction)));
        int green = Math.max(0, Math.round(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, Math.round(color.getBlue() * (1 - fraction)));
        return new Color(red, green, blue);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            System.exit(0);
        } 
        if (e.getSource() == editButton) {
            nameField.setEditable(true);
            addressArea.setEditable(true);
            emailField.setEditable(true);
            numberField.setEditable(true);
            
            nameField.setBackground(new Color(255, 250, 230));
            nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            
            addressArea.setBackground(new Color(255, 250, 230));
            ((JScrollPane) addressArea.getParent().getParent()).setBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR)
            );
            
            emailField.setBackground(new Color(255, 250, 230));
            emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            
            numberField.setBackground(new Color(255, 250, 230));
            numberField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            
            showCustomDialog("Edit Mode", "You can now edit Name, Address, Email and Number", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    private class ShadowBorder extends AbstractBorder {
        private final int SHADOW_SIZE = 5;
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color shadowColor = new Color(0, 0, 0, 30);
            g2.setColor(shadowColor);
            g2.fillRect(x + SHADOW_SIZE, y + height - SHADOW_SIZE, width - SHADOW_SIZE * 2, SHADOW_SIZE);
            g2.fillRect(x + width - SHADOW_SIZE, y + SHADOW_SIZE, SHADOW_SIZE, height - SHADOW_SIZE * 2);
            g2.fillRect(x + width - SHADOW_SIZE, y + height - SHADOW_SIZE, SHADOW_SIZE, SHADOW_SIZE);
            
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, SHADOW_SIZE, SHADOW_SIZE);
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = 0;
            insets.top = 0;
            insets.right = SHADOW_SIZE;
            insets.bottom = SHADOW_SIZE;
            return insets;
        }
    }
    
    private class ModernScrollBarUI extends BasicScrollBarUI {
    private final int THUMB_SIZE = 8;

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setVisible(false);
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color thumbColor = new Color(120, 120, 120, 180); // semi-transparent gray
        g2.setPaint(thumbColor);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, THUMB_SIZE, THUMB_SIZE);

        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // Optionally paint the track or leave it transparent
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(THUMB_SIZE, THUMB_SIZE);
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }
    }
    private void loadTransactionData(String accountNumber) {
    String[] columnNames = {"Date", "Type", "Amount", "Balance"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 0 rows initially

    con c = new con();
    String query = "SELECT transaction_date, transaction_type, amount, balance FROM Transactions WHERE account_number = ? ORDER BY transaction_date DESC";

    try (PreparedStatement stmt = c.connection.prepareStatement(query)) {
        stmt.setString(1, accountNumber);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String date = rs.getString("transaction_date");
            String type = rs.getString("transaction_type");
            double amount = rs.getDouble("amount");
            double balance = rs.getDouble("balance");

            // Format amount
            String formattedAmount = (type.equalsIgnoreCase("Deposit") ? "+" : "-") + String.format("%.2f", amount);

            Object[] row = {date, type, formattedAmount, balance};
            model.addRow(row);
        }

        transactionTable.setModel(model); // Replace table data with new model
        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (c.connection != null) c.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}






