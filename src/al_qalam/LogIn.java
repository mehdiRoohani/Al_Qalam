/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package al_qalam;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author Windows XP
 */
public class LogIn extends javax.swing.JFrame{
     Connection con;
        PreparedStatement ps;
        ResultSet rs;
        Statement st;
        public static Connection conn() throws ClassNotFoundException,SQLException
    {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        String url="jdbc:odbc:Al_Qalam";
        return DriverManager.getConnection(url, "sa","sa9");
    }
        public void focusGain(JTextField txt,String s){
        if(txt.getText().equals(s)){
            txt.setText(null);
            txt.setForeground(Color.black);
        }else{
            txt.selectAll();
        }
    }
    public void focusLost(JTextField txt,String s){
        if(txt.getText().isEmpty()){
            txt.setText(s);
            txt.setForeground(Color.GRAY);
        }
    }
    SignUp obj;
    Software obj123;
    SplashScreenM a = new SplashScreenM();
    
    /**
     * Creates new form LogIn
     */
    public LogIn() {
        initComponents();
        setLocationRelativeTo(rootPane);
        setlookNfeel();
    }
    public void setlookNfeel(){
         try {
             LookAndFeel lnf = new NimbusLookAndFeel();
             UIManager.setLookAndFeel(lnf);
             revalidate();
         } catch (UnsupportedLookAndFeelException ex) {
             JOptionPane.showMessageDialog(rootPane, "Cannot set Nimbus Look And Feel");
         }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane2 = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        Pass = new javax.swing.JLabel();
        UserId1 = new javax.swing.JTextField();
        Password1 = new javax.swing.JPasswordField();
        LIn = new javax.swing.JLabel();
        LogIn = new javax.swing.JLabel();
        copyRight = new javax.swing.JLabel();
        signUp = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane2.setBackground(new java.awt.Color(204, 255, 255));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Log In", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Comic Sans MS", 0, 24), new java.awt.Color(0, 0, 102))))); // NOI18N
        jPanel2.setOpaque(false);

        Pass.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        Pass.setForeground(new java.awt.Color(0, 0, 102));
        Pass.setText("Password");

        UserId1.setForeground(new java.awt.Color(153, 153, 153));
        UserId1.setText("Write your Log_In Id here");
        UserId1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                UserId1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                UserId1FocusLost(evt);
            }
        });

        Password1.setForeground(new java.awt.Color(153, 153, 153));
        Password1.setText("password");
        Password1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Password1ActionPerformed(evt);
            }
        });
        Password1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Password1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                Password1FocusLost(evt);
            }
        });

        LIn.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        LIn.setForeground(new java.awt.Color(0, 0, 102));
        LIn.setText("LogIn Id");
        LIn.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pass, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Password1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(UserId1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LIn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Pass, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Password1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBounds(10, 30, 280, 120);
        jDesktopPane2.add(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        LogIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Welcome2.png"))); // NOI18N
        LogIn.setToolTipText("Click Here To Log In");
        LogIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogInMouseClicked(evt);
            }
        });
        LogIn.setBounds(10, 160, 275, 120);
        jDesktopPane2.add(LogIn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        copyRight.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        copyRight.setForeground(new java.awt.Color(0, 0, 102));
        copyRight.setText("© RHR_solutions");
        copyRight.setBounds(190, 270, 100, 14);
        jDesktopPane2.add(copyRight, javax.swing.JLayeredPane.DEFAULT_LAYER);

        signUp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        signUp.setText("Sign Up");
        signUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signUpMouseClicked(evt);
            }
        });
        signUp.setBounds(230, 10, 50, 20);
        jDesktopPane2.add(signUp, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ab-5.jpg"))); // NOI18N
        Background.setBounds(0, 0, 300, 300);
        jDesktopPane2.add(Background, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    static String user_id,pass,email;
    boolean flag;
    private void UserId1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UserId1FocusGained
        focusGain(UserId1, "Write your Log_In Id here");
    }//GEN-LAST:event_UserId1FocusGained

    private void Password1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Password1FocusGained
        focusGain(Password1, "password");
    }//GEN-LAST:event_Password1FocusGained

    private void UserId1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UserId1FocusLost
        focusLost(UserId1, "Write your Log_In Id here");
    }//GEN-LAST:event_UserId1FocusLost

    private void Password1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Password1FocusLost
        focusLost(Password1, "password");
    }//GEN-LAST:event_Password1FocusLost

    private void LogInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogInMouseClicked
        if(Password1.getText().isEmpty())
            Password1.setText("password");
        try {
            con=conn();
            ps=con.prepareStatement("select userId,password,emaiId from signUp where userId = '"+UserId1.getText()+"' and password = '"+Password1.getText()+"'");
            rs=ps.executeQuery();
            if(rs.next()){
                user_id=rs.getString(1);
                pass=rs.getString(2);
                email=rs.getString(3);
                flag = true;
            }
            if(flag){
                Software s=new Software();
                s.setVisible(true);
                UserId1.setText(null);
                Password1.setText(null);
                this.setVisible(false);
                flag = false;
            }
            else if(UserId1.getText().equals("Write your Log_In Id here") && Password1.getText().equals("password") ){
                JOptionPane.showMessageDialog(rootPane, "Log In Failed : Please enter valid values");
            } 
            else if(UserId1.getText().equals("Write your Log_In Id here") && !Password1.getText().equals("password")){
                JOptionPane.showMessageDialog(rootPane, "Log In Failed : Please enter an User Id");
            }
            else if(!UserId1.getText().equals("Write your Log_In Id here") && Password1.getText().equals("password")){
                JOptionPane.showMessageDialog(rootPane, "Log In Failed : Please enter a Password");
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Access Denied : Wrong User Id or Password");
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(rootPane, "The Class You Are Finding is not Found");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex+"The Database Connection Is Failed");
        }
    }//GEN-LAST:event_LogInMouseClicked

    private void Password1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Password1ActionPerformed
        if(Password1.getText().isEmpty())
            Password1.setText("password");
        try {
            con=conn();
            ps=con.prepareStatement("select userId,password,emaiId from signUp where userId = '"+UserId1.getText()+"' and password = '"+Password1.getText()+"'");
            rs=ps.executeQuery();
            if(rs.next()){
                user_id=rs.getString(1);
                pass=rs.getString(2);
                email=rs.getString(3);
                flag = true;
            }
            if(flag){
                
                Software sa=new Software();
                sa.setVisible(true);
                UserId1.setText(null);
                Password1.setText(null);
                this.setVisible(false);
                flag = false;
            }
            else if(UserId1.getText().equals("Write your Log_In Id here") && Password1.getText().equals("password") ){
                JOptionPane.showMessageDialog(rootPane, "Log In Failed : Please enter valid values");
            } 
            else if(UserId1.getText().equals("Write your Log_In Id here") && !Password1.getText().equals("password")){
                JOptionPane.showMessageDialog(rootPane, "Log In Failed : Please enter an User Id");
            }
            else if(!UserId1.getText().equals("Write your Log_In Id here") && Password1.getText().equals("password")){
                JOptionPane.showMessageDialog(rootPane, "Log In Failed : Please enter a Password");
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Access Denied : Wrong User Id or Password");
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(rootPane, "The Class You Are Finding is not Found");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex+"The Database Connection Is Failed");
        }
    }//GEN-LAST:event_Password1ActionPerformed

    private void signUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpMouseClicked
        this.setVisible(false);
        SignUp abc = new SignUp();
        abc.setVisible(true);
    }//GEN-LAST:event_signUpMouseClicked

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
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
//        </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel LIn;
    private javax.swing.JLabel LogIn;
    private javax.swing.JLabel Pass;
    private javax.swing.JPasswordField Password1;
    private javax.swing.JTextField UserId1;
    private javax.swing.JLabel copyRight;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel signUp;
    // End of variables declaration//GEN-END:variables
}
