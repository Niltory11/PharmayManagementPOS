
import java.util.Random;

import com.itextpdf.text.Paragraph;
import static com.itextpdf.text.SpecialSymbol.index;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import common.OpenPdf;
import dao.ConnectionProvider;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.TableModel;
import dao.PharmacyUtils;
import java.awt.BorderLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.SwingUtilities;

public class SellMedicine extends javax.swing.JFrame {

    private javax.swing.JTextField txtDue;
    private JPopupMenu batchPopupMenu;
    //  private javax.swing.JTextField txtBatch;
    //  private javax.swing.JTextField txtExpDate;

    public String numberPattern = "^[0.0-9.0]*$";
    private int finalTotalPrice = 0;
    private String billId = "";
    private String username = "";
    //private String MobileNumber;
    //private String Due="";
    // private String CustomerName="";
    public String due = "";
    private String CustomerName;
    private String batchX;
    //private javax.swing.JTable batchTable;
    private javax.swing.JTextField textExpDate;

    // private JPopupMenu batchPopupMenu;
    public SellMedicine() {
        initComponents();
        createPopupMenu();
        addMouseListenerTotxtBatch();

        txtBatch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBatchMouseClicked(evt);
            }
        });

    }

    public SellMedicine(String tempUsername) {
        initComponents();
        createPopupMenu();
        addMouseListenerTotxtBatch();

        username = tempUsername;

        setLocationRelativeTo(null);
    }

    private void medicineName(String nameOrUniqueId) {
        DefaultTableModel model = (DefaultTableModel) MedicineTable.getModel();
        model.setRowCount(0);
        try {
            Connection con = ConnectionProvider.getCon();
            Statement stm = con.createStatement();
            ResultSet rt = stm.executeQuery("select *from medicine where name like '" + nameOrUniqueId + "%'or uniqueid like '" + nameOrUniqueId + "%' ");
            while (rt.next()) {
                model.addRow(new Object[]{rt.getString("name")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void clearMedicineFields() {
       // medID3.setText("Product ID");
        txtMedicineName.setText("Product Description");
        txtCompanyName.setText("Company Name");
        txtPricePerUnit.setText("Price per Unit");
        txtQuantity.setText("");

        // txtBatch.setText("Batch");
        txtLessAmount.setText("");
        txtPack.setText("Pack Size");
        txtExpDate.setText("Exp.Date");
        txtgenName.setText("Generic Name");

    }

    public String getUniqeId(String prefix) {
        return prefix + System.nanoTime();

    }

    
    
    
    public void createPopupMenu() {

    batchPopupMenu = new JPopupMenu();

       try {
            Connection con = ConnectionProvider.getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT batch FROM medicine");
            while (rs.next()) {
                String batch = rs.getString("batch");
                /* 88888*/        // String batch1 =rs.getString("batch");
                JMenuItem menuItem = new JMenuItem(batch);
                menuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        updateBatchAndExpDate(batch);
                        // updateBatch(batch1);

                    }
                });
                batchPopupMenu.add(menuItem);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    

}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    public void addMouseListenerTotxtBatch() {

        txtBatch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    batchPopupMenu.show(txtBatch, evt.getX(), evt.getY());
                }
            }
        });
    }

    private void updateBatchAndExpDate(String batch) {
        try {
            Connection con = ConnectionProvider.getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT batch, expDate FROM medicine WHERE batch = '" + batch + "'");
            if (rs.next()) {
                String expDate = rs.getString("expDate");
                String batchx = rs.getString("batch");
                txtBatch.setText(batchx);
                txtExpDate.setText(expDate);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public class date {

        public static void main(String[] args) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        }
    }

    /* public String billId1() {
    
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Generate a random number between 0 and 9999
        String billNumber = String.format("%04d", randomNumber); // Format the number to be 4 digits with leading zeros
       
    }
     */
    private void updatePayableAmount() {
        String lessAmountStr = txtLessAmount.getText().trim();
        if (!lessAmountStr.isEmpty()) {
            try {
                int lessAmount = Integer.parseInt(lessAmountStr);
                int finalTotal = Integer.parseInt(lblFinalTotalPrice.getText());
                int payableAmount = finalTotal - lessAmount;
                txtPayableAmount.setText(String.valueOf(payableAmount));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid less amount format. Please enter a valid number.");
            }
        } else {
            txtPayableAmount.setText(lblFinalTotalPrice.getText());
        }
    }

    public void updateDueAmount() {

        //String Due=txtDue1.getText();
        String lessAmountStr = txtLessAmount.getText().trim();
        String paidAmountStr = txtPaid.getText().trim();
        if (!paidAmountStr.isEmpty()) {

            try {

                int paidAmount = Integer.parseInt(paidAmountStr);
                int lessAmount = Integer.parseInt(lessAmountStr);
                int finalTotal = Integer.parseInt(lblFinalTotalPrice.getText());
                int payableAmount = finalTotal - lessAmount;

                int due = payableAmount - paidAmount;
                txtDue1.setText(String.valueOf(due));

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid less amount format. Please enter a valid number.");
            }

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMedicineId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        txtMedicineId1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        medID3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MedicineTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtMedicineName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCompanyName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPricePerUnit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPayableAmount = new javax.swing.JTextField();
        btnAddToCart = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblFinalTotalPrice = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        CartTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        lblAvialableQuantity = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtgenName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtExpDate = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtBatch = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtLessAmount = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPack = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtPaid = new javax.swing.JTextField();
        txtDue1 = new javax.swing.JTextField();

        txtMedicineId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtMedicineId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMedicineIdActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Medicine ID");

        jMenuItem1.setText("jMenuItem1");

        jMenu1.setText("jMenu1");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jLabel14.setText("id");

        jButton1.setIcon(new javax.swing.ImageIcon("D:\\Pharmacy management tools\\cancel001-icon-front-side.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        medID3.setText("          Product ID");
        medID3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medID3ActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(153, 153, 153));
        setMinimumSize(new java.awt.Dimension(1709, 781));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        jLabel1.setText("SELL PRODUCTS");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 324, 62));

        txtSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        getContentPane().add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 331, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Search");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 52, -1));

        MedicineTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MedicineTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Products Description"
            }
        ));
        MedicineTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MedicineTableMouseClicked(evt);
            }
        });
        MedicineTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                MedicineTableComponentShown(evt);
            }
        });
        jScrollPane1.setViewportView(MedicineTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 331, 500));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, 174, -1));

        txtMedicineName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtMedicineName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMedicineNameActionPerformed(evt);
            }
        });
        getContentPane().add(txtMedicineName, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, 300, 30));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 164, -1));

        txtCompanyName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        getContentPane().add(txtCompanyName, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, 300, 30));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 290, 110, -1));

        txtPricePerUnit.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtPricePerUnit.setForeground(new java.awt.Color(51, 153, 0));
        txtPricePerUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPricePerUnitActionPerformed(evt);
            }
        });
        getContentPane().add(txtPricePerUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 301, 30));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setText("Quantity");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, 98, -1));

        txtQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtQuantity.setForeground(new java.awt.Color(255, 0, 51));
        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQuantityKeyReleased(evt);
            }
        });
        getContentPane().add(txtQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 510, 301, 30));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 204));
        jLabel8.setText("Payable Amount");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 630, -1, -1));

        txtPayableAmount.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtPayableAmount.setForeground(new java.awt.Color(0, 0, 204));
        txtPayableAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPayableAmountActionPerformed(evt);
            }
        });
        txtPayableAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPayableAmountKeyReleased(evt);
            }
        });
        getContentPane().add(txtPayableAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 630, 80, 30));

        btnAddToCart.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnAddToCart.setIcon(new javax.swing.ImageIcon("D:\\Pharmacy management tools\\Pharmacy Management System Images & Pattern\\Pharmacy Management System Images & Pattern\\add to cart.png")); // NOI18N
        btnAddToCart.setText("Add to Cart");
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });
        getContentPane().add(btnAddToCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 650, 213, 37));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(102, 102, 102));
        jButton3.setIcon(new javax.swing.ImageIcon("D:\\Pharmacy management tools\\Pharmacy Management System Images & Pattern\\Pharmacy Management System Images & Pattern\\generate bill & print.png")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 670, 68, 36));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("Total");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 610, 120, 40));

        lblFinalTotalPrice.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblFinalTotalPrice.setForeground(new java.awt.Color(0, 0, 255));
        lblFinalTotalPrice.setText("0.00");
        getContentPane().add(lblFinalTotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 620, 110, 24));

        jScrollPane2.setBackground(new java.awt.Color(0, 0, 0));

        CartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Products Description", "Price Per Unit", "Quantity", "Total"
            }
        ));
        CartTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CartTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(CartTable);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 150, 630, 458));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1709, 90, -1, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1697, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 102));
        jLabel10.setText("Available Quantity :");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 80, -1, -1));

        lblAvialableQuantity.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblAvialableQuantity.setForeground(new java.awt.Color(255, 51, 102));
        lblAvialableQuantity.setText("0");
        getContentPane().add(lblAvialableQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 80, 80, 20));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 180, -1));

        txtgenName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtgenName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtgenNameActionPerformed(evt);
            }
        });
        txtgenName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtgenNameKeyReleased(evt);
            }
        });
        getContentPane().add(txtgenName, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, 300, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 430, -1, -1));
        getContentPane().add(txtExpDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 440, 301, 30));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 360, -1, -1));

        txtBatch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBatchMouseClicked(evt);
            }
        });
        txtBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBatchActionPerformed(evt);
            }
        });
        getContentPane().add(txtBatch, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, 300, 30));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jButton2.setText("Due");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 660, 89, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 255));
        jLabel12.setText("Less Amount");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 630, 114, -1));

        txtLessAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLessAmountActionPerformed(evt);
            }
        });
        txtLessAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLessAmountKeyReleased(evt);
            }
        });
        getContentPane().add(txtLessAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 630, 75, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 570, 86, -1));
        getContentPane().add(txtPack, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 560, 301, 31));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel17.setText("Price (TK)");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 70, -1));

        jLabel18.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel18.setText("Paid:");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 670, 60, 20));

        txtPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaidActionPerformed(evt);
            }
        });
        txtPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaidKeyTyped(evt);
            }
        });
        getContentPane().add(txtPaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 670, 80, 30));

        txtDue1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtDue1.setForeground(new java.awt.Color(255, 0, 51));
        txtDue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDue1ActionPerformed(evt);
            }
        });
        txtDue1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDue1PropertyChange(evt);
            }
        });
        txtDue1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDue1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDue1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDue1KeyTyped(evt);
            }
        });
        getContentPane().add(txtDue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 659, 110, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);  // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPayableAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPayableAmountActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPayableAmountActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        medicineName("");
     //   medID3.setEditable(false);
        txtMedicineName.setEditable(false);
        txtCompanyName.setEditable(false);
        txtPricePerUnit.setEditable(false);
        txtQuantity.setEditable(true);
        //  txtPayableAmount.setEditable(true);
        txtLessAmount.setEditable(true);
        txtPack.setEditable(false);
        //txtPricePerUnit.setEditable(false);
        txtBatch.setEditable(false);
        txtgenName.setEditable(false);
        txtExpDate.setEditable(false);

    }//GEN-LAST:event_formComponentShown

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased

        String search = txtSearch.getText();
        medicineName(search);

// TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyReleased

    private void MedicineTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MedicineTableMouseClicked
        int index = MedicineTable.getSelectedRow();
        TableModel model = MedicineTable.getModel();
        String name = model.getValueAt(index, 0).toString(); // Assuming the first column contains the medicine name

        try {
            Connection con = ConnectionProvider.getCon();
            Statement stm = con.createStatement();
            ResultSet rt = stm.executeQuery("select * from medicine where Name='" + name + "'");
            while (rt.next()) {
                // Assuming the column names in the database are exactly as used below
                txtMedicineName.setText(rt.getString("Name"));
                txtCompanyName.setText(rt.getString("CompanyName"));
                txtPricePerUnit.setText(rt.getString("Price"));
                txtQuantity.setText("");
                lblAvialableQuantity.setText(rt.getString("quantity"));
                txtgenName.setText(rt.getString("genName"));
                txtBatch.setText(rt.getString("batch"));
                txtExpDate.setText(rt.getString("expDate"));
                txtLessAmount.setText("");
                txtPack.setText(rt.getString("pack"));
                //medID3.setText(rt.getString("uniqueID"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_MedicineTableMouseClicked

    private void txtQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyReleased
        // TODO add your handling code here:
        String Less = txtLessAmount.getText();
        String Quantity = txtQuantity.getText();
        if (!Quantity.equals("")) {
            String price = txtPricePerUnit.getText();
            if (!Quantity.matches(numberPattern)) {
                JOptionPane.showMessageDialog(null, "Number of Quantity fields is invalid ! ");

            }
            int totalPrice = Integer.parseInt(Quantity) * Integer.parseInt(price);

            lblFinalTotalPrice.setText(String.valueOf(totalPrice));
        } else {
            lblFinalTotalPrice.setText("");
        }
    }//GEN-LAST:event_txtQuantityKeyReleased

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        // TODO add your handling code here:
        String Quantity = txtQuantity.getText();
        String uniqueId = medID3.getText();
        String name = txtMedicineName.getText();
        if (!Quantity.equals("") && !name.equals("")) {

            String PricePerUnit = txtPricePerUnit.getText();
            String MedicineName = txtMedicineName.getText();
            String TotalPrice = lblFinalTotalPrice.getText();

            int checkStock = 0;
            int checkMedicineAlreadyExistInCart = 0;

            try {
                Connection con = ConnectionProvider.getCon();
                String query = "SELECT * FROM medicine WHERE name = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, name);
                ResultSet rt = pstmt.executeQuery();

                if (rt.next()) {
                    int currentStock = rt.getInt("quantity");
                    if (currentStock >= Integer.parseInt(Quantity)) {
                        checkStock = 1;
                    } else {
                        JOptionPane.showMessageDialog(null, "Medicine is out of stock. Only " + currentStock + " left.");
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

            if (checkStock == 1) {
                DefaultTableModel dtm = (DefaultTableModel) CartTable.getModel();

                if (CartTable.getRowCount() != 0) {
                    for (int i = 0; i < CartTable.getRowCount(); i++) {
                        if (dtm.getValueAt(i, 0).toString().equals(name)) {
                            checkMedicineAlreadyExistInCart = 1;
                            JOptionPane.showMessageDialog(null, "This Medicine already exists in the cart");
                            break;
                        }
                    }
                }

                if (checkMedicineAlreadyExistInCart == 0) {
                    dtm.addRow(new Object[]{MedicineName, PricePerUnit, Quantity, TotalPrice});
                    finalTotalPrice = finalTotalPrice + Integer.parseInt(TotalPrice);
                    lblFinalTotalPrice.setText(String.valueOf(finalTotalPrice));

                    // Update the medicine quantity in the database
                    try {
                        Connection con = ConnectionProvider.getCon();
                        String updateQuery = "UPDATE medicine SET quantity = quantity - ? WHERE name = ?";
                        PreparedStatement updatePstmt = con.prepareStatement(updateQuery);
                        updatePstmt.setInt(1, Integer.parseInt(Quantity));
                        updatePstmt.setString(2, name);
                        updatePstmt.executeUpdate();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }

                //    JOptionPane.showMessageDialog(null, "Medicine Added Successfully ");
                }

                clearMedicineFields();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Medicine Quantity field is required!");
        }

        // updatePayableAmount();
    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void txtPricePerUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPricePerUnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPricePerUnitActionPerformed

    private void CartTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CartTableMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        int index = CartTable.getSelectedRow();
        if (index != -1) { // Check if a row is actually selected
            int a = JOptionPane.showConfirmDialog(null, "Do you want to remove this medicine?", "Select", JOptionPane.YES_NO_OPTION);
            if (a == 0) {
                TableModel model = CartTable.getModel();
                String Total = model.getValueAt(index, 3).toString(); // Adjusted the index to the correct column for TotalPrice
                finalTotalPrice = finalTotalPrice - Integer.parseInt(Total);
                lblFinalTotalPrice.setText(String.valueOf(finalTotalPrice));

                String medicineName = model.getValueAt(index, 0).toString(); // Get the medicine name from the table
                String quantityToRemove = model.getValueAt(index, 2).toString(); // Get the quantity to remove

                // Update the medicine quantity in the database
                try {
                    Connection con = ConnectionProvider.getCon();
                    String updateQuery = "UPDATE medicine SET quantity = quantity + ? WHERE name = ?";
                    PreparedStatement updatePstmt = con.prepareStatement(updateQuery);
                    updatePstmt.setInt(1, Integer.parseInt(quantityToRemove));
                    updatePstmt.setString(2, medicineName);
                    updatePstmt.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

                ((DefaultTableModel) CartTable.getModel()).removeRow(index);
                JOptionPane.showMessageDialog(null, "Medicine removed successfully.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a medicine to remove.");
        }

    }//GEN-LAST:event_CartTableMouseClicked

    private void updateDuePage() {
        int payableAmount = Integer.parseInt(txtPayableAmount.getText());
        int paidAmount = Integer.parseInt(txtPaid.getText());

        int dueAmount = payableAmount - paidAmount;

        if (dueAmount != 0) {
            new Due().setVisible(true);
        }

    }


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String batch = txtBatch.getText();

        if (finalTotalPrice != 0) {
            billId = getUniqueId("");

            DefaultTableModel dtm = (DefaultTableModel) CartTable.getModel();
            if (CartTable.getRowCount() != 0) {
                for (int i = 0; i < CartTable.getRowCount(); i++) {
                    try {
                        Connection con = ConnectionProvider.getCon();
                        Statement stm = con.createStatement();
                        stm.executeUpdate("update medicine set quantity = quantity - " + Integer.parseInt(dtm.getValueAt(i, 3).toString()) + " SELECT * FROM medicine WHERE name = ?" + Integer.parseInt(dtm.getValueAt(i, 0).toString()));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
            //   updateDuePage();

            try {
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                String currentDate = myFormat.format(new Date());
                Calendar cal = Calendar.getInstance();
                Connection con = ConnectionProvider.getCon();
                PreparedStatement ps = con.prepareStatement("insert into bill (billId, billDate, totalPaid, generatedBy, CustomerName,Due,batch) values (?,?,?, ?, ?, ?, ?)");
                ps.setString(1, billId);
                ps.setString(2, currentDate);
                ps.setInt(3, finalTotalPrice);
                ps.setString(4, username);
                ps.setString(5, CustomerName);

                // Calculate the due amount
                int paidAmount;
                int payableAmount;

                try {
                    payableAmount = Integer.parseInt(txtPayableAmount.getText());
                    paidAmount = Integer.parseInt(txtPaid.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid payment amount. Please enter a valid number.");
                    return; // Exit the method if the payment amount is invalid
                }

                int dueAmount = payableAmount - paidAmount;

                ps.setInt(6, dueAmount);
                ps.setString(7, batch);

                ps.executeUpdate();

                // Create the bill PDF
                com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
                PdfWriter.getInstance(doc, new FileOutputStream(PharmacyUtils.billPath + "" + billId + ".pdf"));
                doc.open();

                // Add pharmacy details
                Paragraph pharmacyName = new Paragraph("                                                                   LIFE PHARMACY\n");//html can be used for design
                doc.add(pharmacyName);

                Paragraph starLine = new Paragraph("****************************************************************************************************************");
                doc.add(starLine);

                // Add bill details
                SimpleDateFormat myTime = new SimpleDateFormat("hh:mm a");
                String currentTime = myTime.format(new Date());
                Paragraph details1 = new Paragraph("\tInvoice ID: " + billId + "\n\n Date: " + currentDate + "                                                               \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t\t \t \t \t \t \t \t \t \t \t \t \t \t Time: " + currentTime + "\n Total Amount: " + finalTotalPrice + " TK");
                doc.add(details1);

                // Add due amount to the bill
                Paragraph details = new Paragraph("Due Amount: " + dueAmount + " TK");
                doc.add(details);
                doc.add(starLine);

                // Add table for medicine details
                PdfPTable tbl = new PdfPTable(3);
                tbl.addCell("Product Description");
                tbl.addCell("Price Per Unit");
                tbl.addCell("Quantity");

                for (int i = 0; i < CartTable.getRowCount(); i++) {
                    String a = CartTable.getValueAt(i, 0).toString();
                    String b = CartTable.getValueAt(i, 1).toString();
                    String c = CartTable.getValueAt(i, 2).toString();
                    tbl.addCell(a);
                    tbl.addCell(b);
                    tbl.addCell(c);
                }
                doc.add(tbl);
                doc.add(starLine);

                // Add a thank you message
                Paragraph thanksMsg = new Paragraph("Thanks for your purchase.\n Systems Developed By: IT Division of LIFE PHARMACY");
                doc.add(thanksMsg);

                OpenPdf.openById(String.valueOf(billId));
                doc.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

            setVisible(false);
            new SellMedicine(username).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Please add some Medicine to Cart.");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtgenNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtgenNameKeyReleased
        // TODO add your handling code here:
        String search = txtSearch.getText();
        genName(search);
    }//GEN-LAST:event_txtgenNameKeyReleased

    private void txtgenNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtgenNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtgenNameActionPerformed

    private void txtMedicineNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMedicineNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedicineNameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        new Due().setVisible(true);
        //updateDueAmount();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtMedicineIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMedicineIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedicineIdActionPerformed

    private void MedicineTableComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_MedicineTableComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_MedicineTableComponentShown

    private void txtBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBatchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBatchActionPerformed

    private void txtBatchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBatchMouseClicked

    }//GEN-LAST:event_txtBatchMouseClicked

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:


    }//GEN-LAST:event_formMouseReleased

    private void medID3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medID3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_medID3ActionPerformed

    private void txtLessAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLessAmountActionPerformed
        // TODO add your handling code here:
        // updatePayableAmount();
    }//GEN-LAST:event_txtLessAmountActionPerformed

    private void txtPayableAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPayableAmountKeyReleased
        // TODO add your handling code here:
        // updatePayableAmount();
    }//GEN-LAST:event_txtPayableAmountKeyReleased

    private void txtLessAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLessAmountKeyReleased
        // TODO add your handling code here:
        updatePayableAmount();
    }//GEN-LAST:event_txtLessAmountKeyReleased

    private void txtPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaidActionPerformed
        // TODO add your handling code here:
        // new Due().setVisible(true);
    }//GEN-LAST:event_txtPaidActionPerformed

    private void txtPaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyReleased
        // TODO add your handling code here:
        updateDueAmount();
        // updateDuePage();
    }//GEN-LAST:event_txtPaidKeyReleased

    private void txtPaidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyPressed
        // TODO add your handling code here:
        // new Due().setVisible(true);
    }//GEN-LAST:event_txtPaidKeyPressed

    private void txtDue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDue1ActionPerformed
        // TODO add your handling code here:
        updateDuePage();
    }//GEN-LAST:event_txtDue1ActionPerformed

    private void txtDue1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDue1KeyReleased
        // TODO add your handling code here:
        //updateDuePage();

    }//GEN-LAST:event_txtDue1KeyReleased

    private void txtDue1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDue1KeyPressed
        // TODO add your handling code here:
        //updateDuePage();
    }//GEN-LAST:event_txtDue1KeyPressed

    private void txtPaidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyTyped
        // TODO add your handling code here:
        // updateDuePage();
    }//GEN-LAST:event_txtPaidKeyTyped

    private void txtDue1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDue1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDue1PropertyChange

    private void txtDue1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDue1KeyTyped
        // TODO add your handling code here:
        //updateDuePage();
    }//GEN-LAST:event_txtDue1KeyTyped

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SellMedicine.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SellMedicine sellMedicine = new SellMedicine();
                sellMedicine.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable CartTable;
    private javax.swing.JTable MedicineTable;
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAvialableQuantity;
    private javax.swing.JLabel lblFinalTotalPrice;
    private javax.swing.JTextField medID3;
    private javax.swing.JTextField txtBatch;
    private javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtDue1;
    private javax.swing.JTextField txtExpDate;
    private javax.swing.JTextField txtLessAmount;
    private javax.swing.JTextField txtMedicineId;
    private javax.swing.JTextField txtMedicineId1;
    private javax.swing.JTextField txtMedicineName;
    private javax.swing.JTextField txtPack;
    private javax.swing.JTextField txtPaid;
    private javax.swing.JTextField txtPayableAmount;
    private javax.swing.JTextField txtPricePerUnit;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtgenName;
    // End of variables declaration//GEN-END:variables

    /*private String getUniqueId(String prefix) {
        return prefix + System.nanoTime();
    }
*/
    
    
    
    private static int lastId = 100;

private String getUniqueId(String prefix) {
    lastId++;
    if (lastId > 99999) {
        lastId = 1; // Reset to 0001 after reaching 9999
    }
    String id = String.format("%05d", lastId); // Format the ID to be 4 digits
    return prefix + id;
}

    
    
    
    
    
    
    
    
    private void genName(String search) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
