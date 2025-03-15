
import dao.ConnectionProvider;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author ahsan
 */
public class Profile extends javax.swing.JFrame {

    public String emailPattern = "^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";
    public String mobileNumberPattern = "^[0-9]*$";
    private String username = "";
    private String idpicture;
    private String filename;

    /**
     * Creates new form Profile
     */
    public Profile() {
        initComponents();
    }

    public Profile(String tempUsername) {
        initComponents();
        username = tempUsername;
        setLocationRelativeTo(null);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNumber = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblPassword = new javax.swing.JTextField();

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Email");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel1.setText("Profile");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(624, 36, 233, 69));

        jButton1.setIcon(new javax.swing.ImageIcon("D:\\Pharmacy management tools\\cancel001-icon-front-side.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1380, 0, 29, 27));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, -1, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 93, 1460, 10));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("User Name");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 140, 130, -1));

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        getContentPane().add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 180, 410, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setText("Mobile Number");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 230, 140, -1));

        txtNumber.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(txtNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 270, 410, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Address");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, -1, -1));

        txtAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        getContentPane().add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 380, 410, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon("D:\\Pharmacy management tools\\Pharmacy Management System Images & Pattern\\Pharmacy Management System Images & Pattern\\save.png")); // NOI18N
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 590, -1, -1));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblUsername.setText("Uasername");
        getContentPane().add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 40, 120, 70));

        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\Pharmacy management tools\\Pharmacy Management System Images & Pattern\\rafat vai.png")); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Password");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 440, 90, -1));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 480, 420, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);  // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        try {
            Connection con = ConnectionProvider.getCon();
            Statement stm = con.createStatement();
            ResultSet rt = stm.executeQuery("select *from appuser where username ='" + username + "'");
            while (rt.next()) {
                txtName.setText(rt.getString("username"));
                txtNumber.setText(rt.getString("mobileNumber"));
               // txtEmail.setText(rt.getString("email"));
                txtAddress.setText(rt.getString("Address"));
                lblUsername.setText(rt.getString("username"));
                lblPassword.setText(rt.getString("password"));
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }      // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
  String userName = txtName.getText();
    String mobileNumber = txtNumber.getText();
    String address = txtAddress.getText();
    String password = lblPassword.getText();

    if (userName.equals("")) {
        JOptionPane.showMessageDialog(null, "Name field is required!");
    } else if (mobileNumber.equals("")) {
        JOptionPane.showMessageDialog(null, "Mobile Number field is required!");
    } else if (!mobileNumber.matches(mobileNumberPattern) || mobileNumber.length() != 11) {
        JOptionPane.showMessageDialog(null, "Mobile Number is Invalid!");
    } else if (address.equals("")) {
        JOptionPane.showMessageDialog(null, "Address field is required!");
    } else {
        Connection con = null;
        try {
            con = ConnectionProvider.getCon();
            con.setAutoCommit(false); // Start transaction

            // Update profile information
            PreparedStatement ps = con.prepareStatement(
                "UPDATE appuser SET   address=?, password=? WHERE userName=?"
            );
           // ps.setString(1, userName);
          //  ps.setString(2, mobileNumber);
            ps.setString(1, address);
            ps.setString(2, password);
            ps.setString(3, username); // Original username (for WHERE clause)
            
            int rowsAffected = ps.executeUpdate();

            // Check if the update succeeded
            if (rowsAffected > 0) {
                // Update profile picture if a new picture is selected
                if (filename != null && !filename.isEmpty()) {
                    File file = new File(filename);
                    FileInputStream fis = new FileInputStream(file);
                    byte[] image = new byte[(int) file.length()];
                    fis.read(image);
                    fis.close();

                    PreparedStatement psImage = con.prepareStatement(
                        "UPDATE appuser SET idpicture=? WHERE userName=?"
                    );
                    psImage.setBytes(1, image);
                    psImage.setString(2, userName); // Use the new username
                    psImage.executeUpdate();
                }

                con.commit(); // Commit transaction
                JOptionPane.showMessageDialog(null, "Profile Updated Successfully!");
                setVisible(false);
                new Profile(userName).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Profile update failed. Username not found.");
            }
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback(); // Rollback transaction on error
                }
            } catch (SQLException rollbackException) {
                JOptionPane.showMessageDialog(null, rollbackException);
            }
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Reset to default commit mode
                    con.close(); // Close the connection
                }
            } catch (SQLException closeException) {
                JOptionPane.showMessageDialog(null, closeException);
            }
        }
    }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void lblPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNumber;
    // End of variables declaration//GEN-END:variables
}
