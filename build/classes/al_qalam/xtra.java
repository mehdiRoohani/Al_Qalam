/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package al_qalam;

/**
 *
 * @author Muhammad
 */
public class xtra extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="purchaseHistory">
    
//    public Vector<Vector<String>> data;
//    public  Vector<String> header;
//    public Vector gettemp() throws SQLException,ClassNotFoundException
//    {
//    Vector<Vector<String>> employeevector=new Vector<Vector<String>>();
//    
//    ps=cn.prepareStatement("select date, sellerName, itemName, quantity, rate, amount, paymentStatus, paymentLeft, delayedDuration from itemsList join purchaseTransaction on itemsList.itemNo=purchaseTransaction.itemNo");
//    rs=ps.executeQuery();
//
//    while(rs.next())
//    {
//    Vector<String> employee=new Vector<String>();
//    employee.add(rs.getString(1));
//    employee.add(rs.getString(2));
//    employee.add(rs.getString(3));
//    employee.add(rs.getString(4));
//    employee.add(rs.getString(5));
//    employee.add(rs.getString(6));
//    employee.add(rs.getString(7));
//    employee.add(rs.getString(8));
//    employee.add(rs.getString(9));
//    
//    employeevector.add(employee);
//    }
//
//
//
//    return employeevector;
//    }
// </editor-fold>
    
    /**
     * Creates new form xtra
     */
    public xtra() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PHistory_lpane4 = new javax.swing.JLayeredPane();
        PHistory_panel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        pHistory_table4 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        PurchaseHi_menu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PHistory_lpane4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true), " Purchase History ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Comic Sans MS", 0, 14), new java.awt.Color(9, 7, 89))); // NOI18N
        PHistory_lpane4.setOpaque(true);

        PHistory_panel4.setBackground(new java.awt.Color(244, 244, 255));
        PHistory_panel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));

        pHistory_table4.setForeground(new java.awt.Color(153, 153, 153));
        pHistory_table4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {" Write Your Item Name here", "Unit",  new Integer(0),  new Float(0.0),  new Integer(0)},
                {null, null, null, null, null},
                {null, "", null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item Name", "Unit", "Quanity", "Rate", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        pHistory_table4.setToolTipText("This Is Your Purchase History");
        pHistory_table4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        pHistory_table4.setCellSelectionEnabled(true);
        pHistory_table4.setGridColor(new java.awt.Color(0, 0, 0));
        pHistory_table4.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(pHistory_table4);

        javax.swing.GroupLayout PHistory_panel4Layout = new javax.swing.GroupLayout(PHistory_panel4);
        PHistory_panel4.setLayout(PHistory_panel4Layout);
        PHistory_panel4Layout.setHorizontalGroup(
            PHistory_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PHistory_panel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        PHistory_panel4Layout.setVerticalGroup(
            PHistory_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PHistory_panel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        PHistory_lpane4.add(PHistory_panel4);
        PHistory_panel4.setBounds(40, 50, 740, 320);

        jMenu1.setText("File");

        PurchaseHi_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        PurchaseHi_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/trans.png"))); // NOI18N
        PurchaseHi_menu.setText("Purchase History");
        PurchaseHi_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PurchaseHi_menuActionPerformed(evt);
            }
        });
        jMenu1.add(PurchaseHi_menu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(426, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(PHistory_lpane4, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(335, 335, 335)
                    .addComponent(PHistory_lpane4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PurchaseHi_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchaseHi_menuActionPerformed
//        Visiblefalse(PHistory_lpane, purchaseHistory);
//        getdata();
    }//GEN-LAST:event_PurchaseHi_menuActionPerformed

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
            java.util.logging.Logger.getLogger(xtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(xtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(xtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(xtra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new xtra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane PHistory_lpane4;
    private javax.swing.JPanel PHistory_panel4;
    private javax.swing.JMenuItem PurchaseHi_menu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable pHistory_table4;
    // End of variables declaration//GEN-END:variables
}
