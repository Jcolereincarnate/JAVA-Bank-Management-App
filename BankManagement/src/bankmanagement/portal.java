package bankmanagement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class portal extends JFrame implements ActionListener{
    
    JButton admin, customer;
    JLabel adminlabel, customerlabel;
   portal(){
     ImageIcon icon = resizeIcon(new ImageIcon("boss.png"), 100, 100);
//adminlabel = new JLabel("Admin", icon, JLabel.CENTER);
//adminlabel.setHorizontalTextPosition(JLabel.CENTER);
//adminlabel.setVerticalTextPosition(JLabel.BOTTOM);

//customerlabel.setHorizontalTextPosition(JLabel.CENTER);
//customerlabel.setVerticalTextPosition(JLabel.BOTTOM);
     adminlabel = new JLabel();
     adminlabel.setIcon(icon);
     admin = new JButton("Employee");
     admin.setSize(30,30);
     admin.addActionListener(this);
    ImageIcon icon1 = resizeIcon(new ImageIcon("customer.png"), 100, 100);
     customerlabel = new JLabel();
     customerlabel.setIcon(icon1);
     customer = new JButton("Customer");
     customer.setSize(30,30);
     customer.addActionListener(this);
     
     JPanel panel = new JPanel();
     panel.setLayout(new GridBagLayout());
     GridBagConstraints gbc  = new GridBagConstraints();
     gbc.insets = new Insets(5,5,5,5);
     gbc.gridx = 0;
     gbc.gridy = 0;
     gbc.gridheight=2;
     gbc.gridwidth=2;
     panel.add(adminlabel, gbc);
     
     gbc.gridx = 0;
     gbc.gridy = 3;
     panel.add(admin, gbc);
     
     gbc.gridx = 2;
     gbc.gridy = 0;
     gbc.gridheight=2;
     gbc.gridwidth=2;
     panel.add(customerlabel, gbc);
     
     gbc.gridx = 2;
     gbc.gridy = 3;
     panel.add(customer, gbc);
     
 
     
     
   this.add(panel);
   this.setSize(720, 720);
   this.setDefaultCloseOperation(EXIT_ON_CLOSE);
   this.setLocationRelativeTo(null);
   this.setVisible(true);
   this.setTitle("Login Page");
   }
   
   public void actionPerformed(ActionEvent e) {
        if (e.getSource() == admin) {
            JOptionPane.showMessageDialog(this, "Proceeding to Employee Page!");
            Employee admin = new Employee();
            this.dispose();
           
        }
        if (e.getSource() == customer) {
            JOptionPane.showMessageDialog(this, "Proceeding to Login Page!");
            Login login = new Login();
            this.dispose();
            
        }
        
    }
   public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
    Image img = icon.getImage();
    Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(resized);
}

}