/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package al_qalam;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import suggest.JSuggestField;

//
/**
 *
 * @author Windows XP
 */
public class Software extends javax.swing.JFrame {
 
    public static Connection conn() throws ClassNotFoundException,SQLException
    {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        String url="jdbc:odbc:Al_Qalam";
        return DriverManager.getConnection(url,"sa","sa9" );
    }
    
    // <editor-fold defaultstate="collapsed" desc="variablesDeclaration">
    
//  Layered Panes Variables For Visibility
    
    Owner   owner; Bank bank;Capital capital;
    boolean purchaseHistory=false,purchaseOrder=false,purchaseTransaction=false,deliveryChalan=false,salesReport_search1=false,salesReport,
            addClient=false,addSeller=false,addProduct=false,stockReport=false,agingReport_parameter=false,agingReport=false,expense=false,Calc=false,paymentRecieved=false,paymentPaid=false,ledger=false,
            profitR=false,profitRP=false,view=false,threshold=false,salesInvoice=false,unInvoicedChReport=false,addBankName=false,addExpenseName=false,
            cashBook=false,chequeBook=false;
    DefaultTableModel   editableModel=new DefaultTableModel();
    
//  Connection Variables  
    Connection cn;
    ResultSet rs;
    PreparedStatement ps;
    static Connection cn1;
    static ResultSet rs1;
    static PreparedStatement ps1;
    
//  PurchaseOrder
    DefaultTableModel   Order=new DefaultTableModel();
    final String[]      columnNamesO = {"Product Name", "Unit", "Quantity"};
    boolean             Oflag=false;
    int                 prevONo,OrderNo,lastONo,rowIndexPO,colIndexPO;
    int[]               OitemNo;
    
//  PurchaseTransactions
    boolean             Pflag=false,makeTransactionFlag=false;
    String              purchaseMode="Cash";
    int                 listHeight,itemN,TransactionNo,prevTNo,rowIndexP,sellerIdPT,lastTrNo,refrenceNo_PT;
    float               balanceLeftPT,totalPurchaseAmPT,totalPaidAmPT,bankBalancePT,bankIdPT;
    int[]               PitemNo;
    Integer             amount;
    DefaultTableModel   pTransaction=new DefaultTableModel();
    final String[]      columnNamesP = {"Product Name", "Unit", "Quantity", "Rate", "Amount"};
    final Object[]      newRowPT=new Object[]{"","", 0, (float) 0, (float) 0};
    JTextField          PamountPaid_field = new JTextField();
    TableColumn         itemNameColumn;
    JComboBox           itemNameBox;
    
//  DeliveryChalan
    String              RcvdOrCredit,tmp;
    int                 prevChalanNo,rowIndexDC,colIndexDC,ChalanNo,listHeight1,lastChNo,clientId1;
    float               fres=0;
    boolean             DCflag=false,stockCheckFlag=true,tAmount=true,repeatFlag=true;
    final String[]      columnNames = {"Product Name", "Unit", "Quantity"};
    DefaultTableModel   chalan=new DefaultTableModel(),chalanOrder=new DefaultTableModel();
    final Object[]      newRowDC=new Object[]{ "","", 0};
    int[]               DCitemNo;
    
//  Sales Invoice
    
    static boolean      SIFlag=false,makeInvoiceFlag=false;
    static String       merged,merged1;
    String              saleMode="Cash";
    int                 prevInvoiceNo,InvoiceNo,lastInvoiceNo,rowIndexSI,colIndexSI,prevInvoicesQuant,totalSoldQuan;
    float               balanceLeftSI,totalSaleAmSI,totalRcvAmSI,prevSaleAmount,totalSoldAmount,bankBalanceSI,bankIdSI;int[]               SIitemNo;
    selectChalanFrame   SCF;
    selectChalanDialog  SCD;
    static List         chalanNos;
    static              DefaultTableModel   salesInvoiceModel=new DefaultTableModel(),salesInvoiceModel1;
    final String[]      columnNamesSI = {"Product Name", "Unit", "Quantity","Rate", "Amount"};
    static int          mergedQuan;
    
//  Expense 
    String              expenseMode="Cash",expenseType="Personal";
    boolean             ExFlag;
    int                 rowIndexEx,rIndexEx,expenseId=0,expenseSheetId=0;
    float               fresEx=0,balanceLeftE,bankBalanceEx,bankIdEx,expenseBalance;
    DefaultTableModel   expense1=new DefaultTableModel();
    final String[]      expenseColumnNames = {"Description", "Quantity", "Rate", "Amount"};
    final Object[]      nRowE=new Object[]{ "", 0, (float) 0, (float) 0};
    TableColumn         expenseNameColumn;
    JComboBox           expenseNameBox;
    int[]               expenseID;
    
//  PaymentVouchers 
    boolean             rcvdPayFinished=false,paidPayFinished=false,ifPaymentLeft=false;
    int                 sellerId,clientId,firsCreditChNo,firstCreditPtId,prevRVNo,prevPVNo;
    float               supplierBalance,clientBalance,currentPaymentRcvd,paymentLeftRV,updatedPaymentRV,currentPaymentPaid
                        ,updatedPaymentPV,totalRecievedAm,totalSaleAm,balanceLeftRV,totalPaidAm,totalPurchaseAm
                        ,balanceLeftPV,bankBalancePV,bankIdPV,bankBalanceRV,bankIdRV,paymentLeftPV; 
    String              paidType="Cash",rcvdType="Cash";
    
//  FormComponentResizeVariables(LayeredPaneLocationsAndSizes) 
    int                 x,x1,y,y1,x2,y2,x3,y3,x4,y4,x5,y5,x6,y6,width;
    
//  ProfitReport
    boolean             quantityEnd = false;
    int                 prevQ,leftQ,currentQuantity,id,month;
    float               rateP,currentRate,purchaseAmount,saleAmount,leftAmount,itemProfit,profit,monthlyProfit,customProfit;
            
//  StockReport 
    int                 stock_previousQuantity,stock_recentQuantity,stock_previousQuantity1,stock_recentQuantity1,temp_itemNo;
    float               stock_previousAmount,final_stockAmount;
    double              totalStockAmount,mergedAmount;
    int[]               stock_finalQuantity;
    int[]               stock_finalQuantity1;
    String              temp;
    static              DefaultTableModel   stockReportModel=new DefaultTableModel();
    
//  SalesReport
    String              salesReportType;
    float               totalSaleCost;
    
//  PurchaseReport
    int                 prevTransactionsAmount;
    float               prevPurchaseAmount;
    
//  Sales Report Variables
    String              sReportType,sReportType1;
    int                 itemNo1;
    
//  Aiging Report Variables
    String              aigingType,ledgerType;
    int                 agingDuration=0;
    float               TAmount_aiging;
    
//  Threshold
    int []              thresholdItemNo;

//  Suggest Field
    JSuggestField ItemNField1;
    
//  Calculator
    String      Temp,Ch,Plus="+",Minus="-",Multiply="*",Divide="/";
    float       No1=0,No2,Result;
    boolean     flag12=false;
    
//  Date
    DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
    Date date;
    
//  Jtree Variables
    ImageIcon reportIcon1 = createImageIcon("/Images/1.png");
    ImageIcon transactionIcon = createImageIcon("/Images/transaction.png");
    ImageIcon rcvbleIcon = createImageIcon("/Images/rcvble.png");
    ImageIcon pybleIcon = createImageIcon("/Images/pyble.png");
    ImageIcon partyIcon = createImageIcon("/Images/partyW.png");
    ImageIcon productIcon = createImageIcon("/Images/product.png");
    
    
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    
    // </editor-fold>
    
    //  Methods
    
    // <editor-fold defaultstate="collapsed" desc="Calculator">
    public void txtfpro(JTextField txt){
        if("0.".equals(txt.getText()) || txt.getText().equals(Result)){
            txt.setText(null);
            txt.setForeground(Color.black);
            Temp=txt.getText();
            txt.requestFocus();
        }
        else
            Temp=txt.getText();
    }
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="visibleFalse">
    public void Visiblefalse (JLayeredPane JL,boolean abc){
       if (!abc){
//          layeredPanes
            pTransaction_lpane.setVisible(false);
            dChalan_lpane.setVisible(false);
            dChalan_lpane.setVisible(false);
            addClient_lpane.setVisible(false);
            stockReport_lpane.setVisible(false);
            salesReportParameter_lpane.setVisible(false);
            salesReport_lpane.setVisible(false);
            aigingReportParameter_lpane.setVisible(false);
            expense_lpane.setVisible(false);
            calc_lpane.setVisible(false);
            aigingReport_lpane.setVisible(false);
            paymentPaid_lpane.setVisible(false);
            paymentRecieved_lpane.setVisible(false);
            ledger_lpane.setVisible(false);
            profitReportPerimeter_lpane.setVisible(false);
            profitReport_lpane.setVisible(false);
            purchaseOrder_lpane.setVisible(false);
            salesInvoice_lpane.setVisible(false);
            thresholdPoint_lpane.setVisible(false);
            unInvoicedChReport_lpane.setVisible(false);
            addSupplier_lpane.setVisible(false);
            addProduct_lpane.setVisible(false);
            homeLable.setVisible(false);
            addBankNames_lpane.setVisible(false);
            addExpenseName_lpane.setVisible(false);
            cashBook_lpane.setVisible(false);
            bankBook_lpane.setVisible(false);
            JL.setVisible(true);
//          booleans
            purchaseHistory=false;
            purchaseOrder=false;
            purchaseTransaction=false;
            deliveryChalan=false;
            salesReport_search1=false;
            salesReport=false;
            stockReport=false;
            agingReport_parameter=false;
            expense=false;
            Calc=false;
            agingReport=false;
            paymentPaid=false;
            paymentRecieved=false;
            ledger=false;
            profitR=false;
            profitRP=false;
            view=false;
            threshold=false;
            salesInvoice=false;
            addClient=false;
            addSeller=false;
            addProduct=false;
            addBankName=false;
            addExpenseName=false;
            unInvoicedChReport=false;
            cashBook=false;
            chequeBook=false;
            abc=true;
    }
       abc=true;
        
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="setDate">
    public void setDate(datechooser.beans.DateChooserCombo d){
        date = new Date();
        d.setText(dateFormat.format(date));
            }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="ComboBoxInTable">;
    
    Vector<String> itemsList1;
    
    public void inserComboBox(JTable table){
        
    itemNameColumn = table.getColumnModel().getColumn(0);
    itemNameBox = new JComboBox();
    
    try{
        ps=cn.prepareStatement("SELECT itemName FROM itemsList");
        rs = ps.executeQuery();
        
        itemsList1 = new Vector<>();
        
        while(rs.next()){
            itemsList1.add(rs.getString(1));
        }
        itemNameBox=new JComboBox(itemsList1);
    }
    catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex+"The Database Connection Is Failed");
    }
    
        // <editor-fold defaultstate="collapsed" desc="SuggestFieldTable">;
    ItemNField1 = new JSuggestField(this , itemsList1);
    ItemNField1.setSize(151, 18);
    Font font1= new Font("Tahoma", Font.PLAIN, 11);
    ItemNField1.setFont(font1);
    listHeight1=itemsList1.size();
        if(listHeight1>5){
        ItemNField1.setSuggestSize(ItemNField1.getWidth(), ItemNField1.getHeight()*5); 
        }
        else {
            ItemNField1.setSuggestSize(ItemNField1.getWidth(), listHeight1*ItemNField1.getHeight());
        }
//        </editor-fold>
        
    itemNameColumn.setCellEditor(new DefaultCellEditor(itemNameBox));
    
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="ComboBoxInExpenseTable">;
    
    Vector<String> expenseList;
    
    public void inserComboBoxE(JTable table){
        
    expenseNameColumn = table.getColumnModel().getColumn(0);
    expenseNameBox = new JComboBox();
    
    try{
        ps=cn.prepareStatement("SELECT expenseName FROM expenseList");
        rs=ps.executeQuery();
        
        expenseList = new Vector<>();
        
        while(rs.next()){
            expenseList.add(rs.getString(1));
        }
        expenseNameBox=new JComboBox(expenseList);
    }
    catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex+"The Database Connection Is Failed");
    }
        
    expenseNameColumn.setCellEditor(new DefaultCellEditor(expenseNameBox));
    
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="focusGain">
        public void focusGain(JTextField txt,String s){
            if(txt.getText().equals(s)){
                txt.setText(null);
                txt.setForeground(Color.BLACK);
            }
            else{
                txt.setForeground(Color.BLACK);
                txt.selectAll();
            }
        }
    //    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="focusLost">
        public void focusLost(JTextField txt,String s){
            if(txt.getText().equals("")){
                txt.setText(s);
                txt.setForeground(Color.GRAY);
            }
        }
    //    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="GetDataInSwings">    
        // <editor-fold defaultstate="collapsed" desc="getSupplierNames">
        public void getSupplierNames(JComboBox box){
            try{
                box.removeAllItems();
                ps=cn.prepareStatement("SELECT sellerName FROM supplierList");
                rs=ps.executeQuery();

                while(rs.next())
                    box.addItem(rs.getString(1));
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
            }
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="getClientNames">
        public void getClientNames(JComboBox box){
            try{
                box.removeAllItems();
                ps=cn.prepareStatement("SELECT clientName FROM clientsList");
                rs=ps.executeQuery();

                while(rs.next())
                    box.addItem(rs.getString(1));
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
            }
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="getItemNames">
        public void getItemNames(JComboBox box) {
            try {
                box.removeAllItems();
                ps = cn.prepareStatement("SELECT itemName FROM itemsList");
                rs = ps.executeQuery();
                while(rs.next()){
                    box.addItem(rs.getString(1));
                }
            }
             catch(SQLException ex){
                 JOptionPane.showMessageDialog(pTransaction_panel, "Sql Exception");
             }
    }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="getBankNames">
        public void getBankNames(JComboBox box) {
            try {
                box.removeAllItems();
                ps = cn.prepareStatement("SELECT bankName FROM bankList");
                rs = ps.executeQuery();
                while(rs.next()){
                    box.addItem(rs.getString(1));
                }
            }
             catch(SQLException ex){
                 JOptionPane.showMessageDialog(pTransaction_panel, "Sql Exception");
             }
    }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="getExpenseNames">
        public void getExpenseNames(JComboBox box) {
            try {
                box.removeAllItems();
                ps = cn.prepareStatement("SELECT expenseName FROM expenseList");
                rs = ps.executeQuery();
                while(rs.next()){
                    box.addItem(rs.getString(1));
                }
            }
             catch(SQLException ex){
                 JOptionPane.showMessageDialog(pTransaction_panel, "Sql Exception");
             }
    }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="getOrderNos">
        public void getOrderNos(JComboBox box){
            try{
                box.removeAllItems();
                ps=cn.prepareStatement("SELECT DISTINCT orderNo FROM purchaseOrder WHERE orderCompleted='No'");
                rs=ps.executeQuery();

                while(rs.next()){
                    box.addItem(rs.getString(1));
                }
                box.addItem(0);
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
            }
        }// </editor-fold>
//        </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PreviousFormNos">
        // <editor-fold defaultstate="collapsed" desc="PreviousOrderNo">
        public void prevOrderNo(JTextField This){
            try{
                prevONo=0;
                ps=cn.prepareStatement("SELECT TOP 1 orderNo FROM purchaseOrder ORDER BY orderNo DESC");
                rs=ps.executeQuery();
                while(rs.next()){
                    prevONo=rs.getInt(1);
                }
                prevONo+=1;
                This.setText(String.valueOf(prevONo));
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(purchaseOrder_panel, ex.getLocalizedMessage());
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PreviousTransactionNo">
        public void prevTrNo(JTextField This){
            try{
                prevTNo=0;
                ps=cn.prepareStatement("Select TOP 1 transactionNo from purchaseTransaction ORDER BY transactionNo Desc");
                rs=ps.executeQuery();
                while(rs.next()){
                    prevTNo=rs.getInt(1);
                }
                prevTNo+=1;
                This.setText(String.valueOf(prevTNo));
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(dChalan_panel, ex.getLocalizedMessage());
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PreviousChalanNo">
        public void prevChNo(JTextField This){
            try{
                prevChalanNo=0;
                ps=cn.prepareStatement("SELECT TOP 1 chalanNo FROM deliveryChalan ORDER BY chalanNo DESC");
                rs=ps.executeQuery();
                while(rs.next()){
                    prevChalanNo=rs.getInt(1);
                }
                prevChalanNo+=1;
                This.setText(String.valueOf(prevChalanNo));
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(dChalan_panel, ex.getLocalizedMessage());
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PreviousInvoiceNo">
        public void prevInvNo(JTextField This){
            try{
                prevInvoiceNo=0;
                ps=cn.prepareStatement("SELECT TOP 1 invoiceNo FROM salesInvoice ORDER BY invoiceNo DESC");
                rs=ps.executeQuery();
                while(rs.next()){
                    prevInvoiceNo=rs.getInt(1);
                }
                prevInvoiceNo+=1;
                This.setText(String.valueOf(prevInvoiceNo));
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(salesInvoice_panel, ex.getLocalizedMessage());
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PreviousRcvdVoucherNo">
        public void prevRcvdVoucherNo(){
            try{
                prevRVNo=0;
                ps=cn.prepareStatement("Select TOP 1 voucherNo from rcvdVoucher ORDER BY voucherNo Desc");
                rs=ps.executeQuery();
                while(rs.next()){
                    prevRVNo=rs.getInt(1);
                }
                prevRVNo+=1;
                rcvdVNo.setText(String.valueOf(prevRVNo));
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(paymentRecieved_lpane, ex.getLocalizedMessage());
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PreviousPaidVoucherNo">
        public void prevPaidVoucherNo(){
            try{
                prevPVNo=0;
                ps=cn.prepareStatement("Select TOP 1 voucherNo from paidVoucher ORDER BY voucherNo Desc");
                rs=ps.executeQuery();
                while(rs.next()){
                    prevPVNo=rs.getInt(1);
                }
                prevPVNo+=1;
                paidVoucherNo.setText(String.valueOf(prevPVNo));
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(paymentPaid_lpane, ex.getLocalizedMessage());
            }
        }
    //    </editor-fold>
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PrintTable">
        
        MessageFormat headerF = null;
        MessageFormat footerF = null;
        boolean fitWidth = true;
        boolean showPrintDialog = true;
        boolean interactive = true;
        
        private void printTable(JTable table,String header,String footer) {
        
        headerF = new MessageFormat(header);
        footerF = new MessageFormat(footer);
        
        JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH
                                         : JTable.PrintMode.NORMAL;

        try {
            boolean complete = table.print(mode, headerF, footerF,
                                                 showPrintDialog, null,
                                                 interactive, null);

            if (complete) {
                JOptionPane.showMessageDialog(this,
                                              "Printing Complete",
                                              "Printing Result",
                                              JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                                              "Printing Cancelled",
                                              "Printing Result",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(this,
                                          "Printing Failed: " + pe.getMessage(),
                                          "Printing Result",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
//        </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="dChalanOrder(129)">

                public Vector<Vector<String>> data129;
                public  Vector<String> header129;
                public Vector gettemp129() throws SQLException,ClassNotFoundException
                {
                Vector<Vector<String>> employeevector129=new Vector<>();

                ps=cn.prepareStatement("SELECT itemName, unit, quantity FROM itemsList JOIN purchaseOrder ON itemsList.itemNo=purchaseOrder.itemNo where orderNo="+orderNo_box.getSelectedItem().toString()+"");
                rs=ps.executeQuery();

                while(rs.next())
                {
                Vector<String> employee129=new Vector<>();
                employee129.add(rs.getString(1));
                employee129.add(rs.getString(2));
                employee129.add(rs.getString(3));
                employee129.add("");
                employee129.add("");
                employee129.add("");
                employee129.add("");

                employeevector129.add(employee129);
                }

                return employeevector129;
        }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="dChalanOrderSetModel">
            public void chalanOrder() {
                try{
                        
                    data129=gettemp129();

                    header129=new Vector<>();
                    header129.add("ItemName");
                    header129.add("Unit");
                    header129.add("Quantity");
                    header129.add("Quantity Delivered");
                    header129.add("Rate");
                    header129.add("Amount");
                    header129.add("Profit");
                        
                    // <editor-fold defaultstate="collapsed" desc="columnEditable">
                        chalanOrder = new DefaultTableModel(data129,header129){
                            @Override
                            public boolean isCellEditable(int row, int col) {
                                switch (col) {
                                    case 3:
                                        return true;
                                    case 4:
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        };
                            dChalan_table.setModel(chalanOrder);
//                        </editor-fold>
                }
                catch(SQLException | ClassNotFoundException ex){
                  JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                }
            }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="MonthlyProfitReport">
    public void profitReport() throws SQLException{
        customProfit=0;
//        month=monthChooser.getMonth()+1;
        ps = cn.prepareStatement("SELECT DISTINCT profit FROM salesInvoice WHERE date BETWEEN '"+startDate.getText()+"' AND '"+endDate.getText()+"'");
        rs = ps.executeQuery();
        while(rs.next()){
            customProfit+=rs.getFloat(1);
        }
//        Visiblefalse(profitReport_lpane, profitR);
        if(customProfit<0)
            profitGain.setForeground(Color.red);
        profitGain.setText(String.valueOf(customProfit));
            }
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="delayedDurationUpdate">
        // <editor-fold defaultstate="collapsed" desc="delayedDurationUpdateSalesInvoice">
        private void delayedDurationUpdateSI(){
            try
            {
                ps=cn.prepareStatement("UPDATE salesInvoice SET delayedDuration=DATEDIFF(DAY,date,GETDATE())");
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(Software.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="delayedDurationUpdateDeliveryChalan">
        private void delayedDurationUpdateDC(){
            try
            {
                ps=cn.prepareStatement("UPDATE deliveryChalan SET delayedDuration=DATEDIFF(DAY,date,GETDATE())");
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(Software.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="delayedDurationUpdatePurchaseTransaction">
        private void delayedDurationUpdatePT(){
           try
            {
                ps=cn.prepareStatement("UPDATE purchaseTransaction SET delayedDuration=DATEDIFF(DAY,date,GETDATE())");
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(Software.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="delayedDurationUpdateClientLedger">
        private void delayedDurationUpdateCL(){
           try
            {
                ps=cn.prepareStatement("UPDATE ledger SET duration=DATEDIFF(DAY,date,GETDATE())");
                ps.execute();
                
            } catch (SQLException ex) {
                Logger.getLogger(Software.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    //    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="delayedDurationUpdateSupplierLedger">
        private void delayedDurationUpdateSL(){
           try
            {
                ps=cn.prepareStatement("UPDATE supplierLedger SET duration=DATEDIFF(DAY,date,GETDATE())");
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(Software.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    //    </editor-fold>
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="JtreeClass">
    private void Jtree(){
	DefaultMutableTreeNode rootNode;
        rootNode = new DefaultMutableTreeNode("Books");
        rootNode = createNodes(rootNode);
        TreeModel treeMod = new DefaultTreeModel(rootNode);
        myTree.setModel(treeMod);
}
//    </editor-fold>
    
//    Tables
    
    // <editor-fold defaultstate="collapsed" desc="StockReport(1)">
        // <editor-fold defaultstate="collapsed" desc="stockReportQuery">

        public Vector<Vector<String>> data1;
        public  Vector<String> header1;
        public Vector gettemp1() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector1=new Vector<>();

        ps=cn.prepareStatement("SELECT itemName,leftQ,cast([leftAmount] as decimal(10,2)) FROM itemsList JOIN profit on itemsList.itemNo=profit.itemNo WHERE totalSold='No'");
        rs=ps.executeQuery();

        while(rs.next())
        {
            Vector<String> employee1=new Vector<>();
            employee1.add(rs.getString(1));
            employee1.add(rs.getString(2));
            employee1.add(rs.getString(3));

            employeevector1.add(employee1);
        }
        
        return employeevector1;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="StockReportSetModel">
    public void getdata1() 
        {
       
        try{
            totalStockAmount=0.0;
            data1=gettemp1();

            header1=new Vector<>();
            header1.add("Product Name");
            header1.add("Quantity");
            header1.add("Amount");
            
            stockReportModel = new DefaultTableModel (data1,header1){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            
            for(int i=0; i<stockReportModel.getRowCount();i++){

                for(int j = i+1; j<stockReportModel.getRowCount(); j++){
                    merged = stockReportModel.getValueAt(i, 0).toString();
                    merged1 = stockReportModel.getValueAt(j, 0).toString();
                    if(merged1.equals(merged)){
                        mergedQuan = (Integer.parseInt(stockReportModel.getValueAt(i, 1).toString())) + (Integer.parseInt(stockReportModel.getValueAt(j, 1).toString()));
                        stockReportModel.setValueAt(mergedQuan, i, 1);
                        mergedAmount = (Double.valueOf(stockReportModel.getValueAt(i, 2).toString())) + (Double.valueOf(stockReportModel.getValueAt(j, 2).toString()));
                        stockReportModel.setValueAt(mergedAmount, i, 2);
                        stockReportModel.removeRow(j);
                        j-=1;
                    }
                }
            }
            stockReport_table.setModel(stockReportModel);
            
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            stockReport_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            stockReport_table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
            stockReport_table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            
            for(int i=0; i<stockReport_table.getRowCount(); i++){
                temp=stockReport_table.getValueAt(i, 2).toString();
                temp=temp.replace(",", "");
                totalStockAmount+=Double.valueOf(temp);
            }
            
            totalStockAmount_label.setText("Total Amount : "+totalStockAmount+" ");
        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
        }

    }
// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="stockReport12Query">

        public Vector<Vector<String>> data0;
        public  Vector<String> header0;
        public Vector gettemp0() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector0=new Vector<>();

        ps=cn.prepareStatement("SELECT itemName,leftQ,rate,cast([leftAmount] as decimal(10,2)) FROM itemsList JOIN profit on itemsList.itemNo=profit.itemNo WHERE itemName='"+stockReport_table.getValueAt(stockReport_table.getSelectedRow(), 0)+"' AND totalSold='No' ORDER BY rate ASC");
        rs=ps.executeQuery();

        while(rs.next())
        {
            Vector<String> employee0=new Vector<>();
            employee0.add(rs.getString(1));
            employee0.add(rs.getString(2));
            employee0.add(rs.getString(3));
            employee0.add(rs.getString(4));

            employeevector0.add(employee0);
        }
        
        return employeevector0;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="StockReport12SetModel">
    public void getdata0() 
        {
       
        try{
            totalStockAmount=0.0;
            data0=gettemp0();

            header0=new Vector<>();
            header0.add("Product Name");
            header0.add("Quantity");
            header0.add("Rate");
            header0.add("Amount");
            
            stockReportModel = new DefaultTableModel (data0,header0){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            
            stockReport_table.setModel(stockReportModel);
            
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            stockReport_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            stockReport_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            stockReport_table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            stockReport_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
            
            for(int i=0; i<stockReport_table.getRowCount(); i++){
                temp=stockReport_table.getValueAt(i, 3).toString();
                temp=temp.replace(",", "");
                totalStockAmount+=Double.valueOf(temp);
            }
            
            totalStockAmount_label.setText("Total Amount : "+totalStockAmount+" ");
        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
        }

    }
// </editor-fold>
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SalesReports(2-5)">
        // <editor-fold defaultstate="collapsed" desc="clientSummary">

        public Vector<Vector<String>> data2;
        public  Vector<String> header2;
        public Vector gettemp2() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector2=new Vector<>();

        ps=cn.prepareStatement("SELECT totalSaleAmount,billsMade FROM clientsList WHERE clientName='"+salesReport_comboBox.getSelectedItem().toString()+"'");
        rs=ps.executeQuery();

        while(rs.next())
        {
        Vector<String> employee2=new Vector<>();
        employee2.add(rs.getString(1));
        employee2.add(rs.getString(2));

        employeevector2.add(employee2);
        }
        return employeevector2;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="clientS.SetModel">
        public void getClientSummary() 
            {
            try{
                data2=gettemp2();

                header2=new Vector<>();
                header2.add("Total Amount Of Sale(Rs.)");
                header2.add("Total Bills");
                
                editableModel = new DefaultTableModel (data2,header2){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                salesReport_table.setModel(editableModel);
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="clientWiseDetail">

        public Vector<Vector<String>> data3;
        public  Vector<String> header3;
        public Vector gettemp3() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector3=new Vector<>();

        ps=cn.prepareStatement("SELECT DISTINCT invoiceNo,date,tAmount FROM salesInvoice WHERE clientName='"+salesReport_comboBox.getSelectedItem().toString()+"' ORDER BY invoiceNo DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
        Vector<String> employee3=new Vector<>();
        employee3.add(rs.getString(1));
        employee3.add(rs.getString(2));
        employee3.add(rs.getString(3));

        employeevector3.add(employee3);
        }

        return employeevector3;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="clientWiseDetail.SetModel">
        public void getClientDetail() 
            {

            try{
                data3=gettemp3();

                header3=new Vector<String>();
                header3.add("Invoice No");
                header3.add("Date");
                header3.add("Total Amount");
                
                editableModel = new DefaultTableModel (data3,header3){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                salesReport_table.setModel(editableModel);
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="productSummary">

        public Vector<Vector<String>> data4;
        public  Vector<String> header4;
        public Vector gettemp4() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector4=new Vector<>();

        ps=cn.prepareStatement("SELECT itemName,soldQuantity,soldAmount FROM itemsList WHERE itemName='"+salesReport_comboBox.getSelectedItem().toString()+"'");
        rs=ps.executeQuery();

        while(rs.next())
        {
        Vector<String> employee4=new Vector<>();
        employee4.add(rs.getString(1));
        employee4.add(rs.getString(2));
        employee4.add(rs.getString(3));

        employeevector4.add(employee4);
        }

        return employeevector4;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="productS.SetModel">
        public void getProductSummary() 
            {

            try{
                data4=gettemp4();

                header4=new Vector<>();
                header4.add("Product Name");
                header4.add("Quantity Of Items Sold");
                header4.add("Amount Of Items Sold");

                editableModel = new DefaultTableModel (data4,header4){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                salesReport_table.setModel(editableModel);
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="productWiseDetail">

        public Vector<Vector<String>> data5;
        public  Vector<String> header5;
        public Vector gettemp5() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector5=new Vector<>();

        ps=cn.prepareStatement("SELECT date,clientName,itemName,quantity,rate,amount FROM itemsList JOIN salesInvoice on itemsList.itemNo=salesInvoice.itemNo WHERE itemName='"+salesReport_comboBox.getSelectedItem().toString()+"' ORDER BY date DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
        Vector<String> employee5=new Vector<>();
        employee5.add(rs.getString(1));
        employee5.add(rs.getString(2));
        employee5.add(rs.getString(3));
        employee5.add(rs.getString(4));
        employee5.add(rs.getString(5));
        employee5.add(rs.getString(6));


        employeevector5.add(employee5);
        }



        return employeevector5;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="productWiseDetail.SetModel">
        public void getProductDetail() 
            {

            try{
                data5=gettemp5();

                header5=new Vector<>();
                header5.add("Date");
                header5.add("Client Name");
                header5.add("Product Name");
                header5.add("Quantity");
                header5.add("Rate");
                header5.add("Amount");
                
                editableModel = new DefaultTableModel (data5,header5){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                salesReport_table.setModel(editableModel);
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }

        }
    // </editor-fold>
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PurchaseReports(6-8)">
        // <editor-fold defaultstate="collapsed" desc="supplierSummary">

        public Vector<Vector<String>> data6;
        public  Vector<String> header6;
        public Vector gettemp6() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector6=new Vector<>();

        ps=cn.prepareStatement("SELECT totalPurchaseAm,totalTransaction FROM supplierList where sellerName='"+salesReport_comboBox.getSelectedItem().toString()+"'");
        rs=ps.executeQuery();

        while(rs.next())
        {
        Vector<String> employee6=new Vector<>();
        employee6.add(rs.getString(1));
        employee6.add(rs.getString(2));


        employeevector6.add(employee6);
        }
        return employeevector6;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="sellerSummary.SetModel">
        public void getSupplierSummary() 
            {

            try{

                data6=gettemp6();

                header6=new Vector<>();
                header6.add("Total Amount Of Purchase(Rs.)");
                header6.add("Total Transactions");


        salesReport_table.setModel(new javax.swing.table.DefaultTableModel(data6,header6));
        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="supplierWiseDetail">

        public Vector<Vector<String>> data7;
        public  Vector<String> header7;
        public Vector gettemp7() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector7=new Vector<>();
        Vector<String> employee7;

        ps=cn.prepareStatement("SELECT DISTINCT transactionNo,date,tAmount FROM purchaseTransaction WHERE sellerName='"+salesReport_comboBox.getSelectedItem().toString()+"' ORDER BY transactionNo DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
        employee7=new Vector<>();
        employee7.add(rs.getString(1));
        employee7.add(rs.getString(2));
        employee7.add(rs.getString(3));

        employeevector7.add(employee7);
        }
        return employeevector7;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="supplierWiseDetail.SetModel">
        public void getSupplierDetail() 
            {

            try{
            data7=gettemp7();

                header7=new Vector<>();
                header7.add("Transaction No");
                header7.add("Date");
                header7.add("Amount");

        salesReport_table.setModel(new javax.swing.table.DefaultTableModel(data7,header7));
        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="productsWiseDetail">

        public Vector<Vector<String>> data8;
        public  Vector<String> header8;
        public Vector gettemp8() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector8=new Vector<>();

        ps=cn.prepareStatement("SELECT date, sellerName, quantity, rate, amount FROM itemsList JOIN purchaseTransaction ON itemsList.itemNo=purchaseTransaction.itemNo WHERE itemName='"+salesReport_comboBox.getSelectedItem().toString()+"' ORDER BY date DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
        Vector<String> employee8=new Vector<>();
        employee8.add(rs.getString(1));
        employee8.add(rs.getString(2));
        employee8.add(rs.getString(3));
        employee8.add(rs.getString(4));
        employee8.add(rs.getString(5));


        employeevector8.add(employee8);
        }



        return employeevector8;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="productsWiseDetail.SetModel">
        public void getProductsDetail() 
            {

            try{
            data8=gettemp8();

                header8=new Vector<>();
                header8.add("Date");
                header8.add("Seller Name");
                header8.add("Quantity");
                header8.add("Rate");
                header8.add("Amount");


        salesReport_table.setModel(new javax.swing.table.DefaultTableModel(data8,header8));
        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
//        </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PrevNext(9-13)">
        // <editor-fold defaultstate="collapsed" desc="prevChalan">

        public Vector<Vector<String>> data9;
        public  Vector<String> header9;
        public Vector gettemp9() throws SQLException,ClassNotFoundException
        {
            Vector<Vector<String>> employeevector9=new Vector<>();

            ps=cn.prepareStatement("SELECT itemName,unit,quantity FROM itemsList JOIN deliveryChalan ON itemsList.itemNo=deliveryChalan.itemNo WHERE chalanNo="+ChalanNo+"");
            rs=ps.executeQuery();

            while(rs.next())
            {
                Vector<String> employee9=new Vector<>();
                employee9.add(rs.getString(1));
                employee9.add(rs.getString(2));
                employee9.add(rs.getString(3));

                employeevector9.add(employee9);
            }
            return employeevector9;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="prevChalan.SetModel">
        public void prevChalan() {

            try{
                if(ChalanNo>0){


                data9=gettemp9();

                header9=new Vector<>();
                header9.add("Product Name");
                header9.add("Unit");
                header9.add("Quantity");

        dChalan_table.setModel(new javax.swing.table.DefaultTableModel(data9,header9));
                }
                else{
                    // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("THIS IS YOUR FIRST CHALAN !");
                    statusBarLabel.setForeground(Color.BLACK);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
                }

        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="prevTransaction">

        public Vector<Vector<String>> data10;
        public  Vector<String> header10;
        public Vector gettemp10() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector10=new Vector<>();

        ps=cn.prepareStatement("SELECT itemName, unit, quantity, rate, amount FROM itemsList JOIN purchaseTransaction ON itemsList.itemNo=purchaseTransaction.itemNo WHERE transactionNo="+TransactionNo+"");
        rs=ps.executeQuery();

        while(rs.next())
        {
        Vector<String> employee10=new Vector<>();
        employee10.add(rs.getString(1));
        employee10.add(rs.getString(2));
        employee10.add(rs.getString(3));
        employee10.add(rs.getString(4));
        employee10.add(rs.getString(5));

        employeevector10.add(employee10);
        }



        return employeevector10;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="prevTransaction.SetModel">
        public void prevTransaction() {

            try{
                if(TransactionNo>0){


                data10=gettemp10();

                header10=new Vector<>();
                header10.add("Product Name");
                header10.add("Unit");
                header10.add("Quantity");
                header10.add("Rate");
                header10.add("Amount");


        pTransaction_table.setModel(new javax.swing.table.DefaultTableModel(data10,header10));
                }
                else{
                    // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("THIS IS YOUR FIRST TRANSACTION !");
                    statusBarLabel.setForeground(Color.BLACK);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
                }

        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="previousExpense">

        public Vector<Vector<String>> data11;
        public  Vector<String> header11;
        public Vector gettemp11() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector11=new Vector<>();

        ps=cn.prepareStatement("SELECT date,expenseName,quantity,rate,amount FROM expenseList JOIN expense ON expenseList.expenseId=expense.expenseId WHERE type='"+expenseType+"' ORDER BY id DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
            Vector<String> employee11=new Vector<>();
            employee11.add(rs.getString(1));
            employee11.add(rs.getString(2));
            employee11.add(rs.getString(3));
            employee11.add(rs.getString(4));
            employee11.add(rs.getString(5));

            employeevector11.add(employee11);
        }

        return employeevector11;
        }
// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="previousExpense.SetModel">
        public void getPrevExpense() 
            {

            try{
                data11=gettemp11();

                header11=new Vector<>();
                header11.add("Date");
                header11.add("Description");
                header11.add("Quantity");
                header11.add("Rate");
                header11.add("Amount");
                
                editableModel = new DefaultTableModel (data11,header11){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                expense_table.setModel(editableModel);
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="previousOrder">
    
    public Vector<Vector<String>> data12;
    public  Vector<String> header12;
    public Vector gettemp12() throws SQLException,ClassNotFoundException
    {
    Vector<Vector<String>> employeevector12=new Vector<>();
    
    ps=cn.prepareStatement("SELECT itemName,quantity FROM itemsList JOIN purchaseOrder ON itemsList.itemNo=purchaseOrder.itemNo WHERE orderNo="+OrderNo+"");
    rs=ps.executeQuery();

    while(rs.next())
    {
    Vector<String> employee12=new Vector<>();
    employee12.add(rs.getString(1));
    employee12.add(rs.getString(2));
    
    employeevector12.add(employee12);
    }



    return employeevector12;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="previousOrder.SetModel">
        public void getPrevOrder() 
            {

            try{
                if(OrderNo>0){
                    data12=gettemp12();

                    header12=new Vector<>();
                    header12.add("Product Name");
                    header12.add("Quantity");
                    
                    purchaseOrder_table.setModel(new javax.swing.table.DefaultTableModel(data12,header12));
                }
                else{
                    // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("THIS IS YOUR FIRST ORDER !");
                    statusBarLabel.setForeground(Color.BLACK);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
                }
            }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="prevInvoice">

        public Vector<Vector<String>> data13;
        public  Vector<String> header13;
        public Vector gettemp13() throws SQLException,ClassNotFoundException
        {
            Vector<Vector<String>> employeevector13=new Vector<>();
            Vector<String> employee13;

            ps=cn.prepareStatement("SELECT itemName,unit,quantity,rate,amount FROM itemsList JOIN salesInvoice ON itemsList.itemNo=salesInvoice.itemNo WHERE invoiceNo="+InvoiceNo+"");
            rs=ps.executeQuery();

            while(rs.next())
            {
                employee13=new Vector<>();
                employee13.add(rs.getString(1));
                employee13.add(rs.getString(2));
                employee13.add(rs.getString(3));
                employee13.add(rs.getString(4));
                employee13.add(rs.getString(5));

                employeevector13.add(employee13);
            }
            return employeevector13;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="prevInvoice.SetModel">
        public void prevInvoice() {

            try{
                if(InvoiceNo>0){


                data13=gettemp13();

                header13=new Vector<>();
                header13.add("Product Name");
                header13.add("Unit");
                header13.add("Quantity");
                header13.add("Rate");
                header13.add("Amount");


        salesInvoice_table.setModel(new javax.swing.table.DefaultTableModel(data13,header13));
                }
                else{
                    // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("THIS IS YOUR FIRST INVOICE !");
                    statusBarLabel.setForeground(Color.BLACK);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
                }

        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PaymentVouchers(14-15)">
        // <editor-fold defaultstate="collapsed" desc="PaymentPaidHistory">
    
    public Vector<Vector<String>> data14;
    public  Vector<String> header14;
    public Vector gettemp14() throws SQLException,ClassNotFoundException
    {
    Vector<Vector<String>> employeevector14=new Vector<>();
    
    ps=cn.prepareStatement("select date, sellerName, amount, type from supplierList join paidVoucher on supplierList.sellerId=paidVoucher.sellerId");
    rs=ps.executeQuery();
    
    while(rs.next())
    {
    Vector<String> employee14=new Vector<>();
    employee14.add(rs.getString(1));
    employee14.add(rs.getString(2));
    employee14.add(rs.getString(3));
    employee14.add(rs.getString(4));

    employeevector14.add(employee14);
    }


    return employeevector14;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PaymentPaidHistory.SetModel">
        public void getPaymentPaidHistory() 
            {

            try{
            data14=gettemp14();

                header14=new Vector<String>();
                header14.add("Date");
                header14.add("Paid To");
                header14.add("Amount");
                header14.add("Type");


        paymentPaid_table.setModel(new javax.swing.table.DefaultTableModel(data14,header14));
        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PaymentRcvdHistory">

        public Vector<Vector<String>> data15;
        public  Vector<String> header15;
        public Vector gettemp15() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector15=new Vector<>();

        ps=cn.prepareStatement("select date, clientName, amount, type from clientsList join rcvdVoucher on clientsList.clientId=rcvdVoucher.clientId ORDER BY date desc");
        rs=ps.executeQuery();

        while(rs.next())
        {
            Vector<String> employee15=new Vector<>();
            employee15.add(rs.getString(1));
            employee15.add(rs.getString(2));
            employee15.add(rs.getString(3));
            employee15.add(rs.getString(4));

            employeevector15.add(employee15);
        }
    //        if(!rs.next())
    //        JOptionPane.showMessageDialog(paymentVouchers_lpane, "No Payment Recieved Yet");




        return employeevector15;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PaymentRcvdHistory.SetModel">
        public void getPaymentRcvdHistory() 
            {

            try{
                data15=gettemp15();

                header15=new Vector<String>();
                header15.add("Date");
                header15.add("Recieved From");
                header15.add("Amount");
                header15.add("Type");

                paymentRcvd_table.setModel(new javax.swing.table.DefaultTableModel(data15,header15));
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    // </editor-fold>
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Individual(16-20)">
        // <editor-fold defaultstate="collapsed" desc="supplierDetail">

        public Vector<Vector<String>> data16;
        public  Vector<String> header16;
        public Vector gettemp16() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector16=new Vector<>();
        Vector<String> employee16;
        ps=cn.prepareStatement("SELECT [sellerId]\n" +
"      ,[sellerName]\n" +
"      ,[address]\n" +
"      ,[contact]\n" +
"  FROM [dbo].[supplierList]  ORDER BY sellerId DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
        employee16=new Vector<>();
        employee16.add(rs.getString(1));
        employee16.add(rs.getString(2));
        employee16.add(rs.getString(3));
        employee16.add(rs.getString(4));

        employeevector16.add(employee16);
        }
        return employeevector16;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="supplierDetail.SetModel">
        public void getSupplierNames() 
            {

            try{
                data16=gettemp16();

                header16=new Vector<>();
                header16.add("Id");
                header16.add("Supplier Name");
                header16.add("Address");
                header16.add("Contact No");
                
                editableModel = new DefaultTableModel (data16,header16){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                supplierDetail_table.setModel(editableModel);
        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ClientDetail">

        public Vector<Vector<String>> data17;
        public  Vector<String> header17;
        public Vector gettemp17() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector17=new Vector<>();
        Vector<String> employee17;
        ps=cn.prepareStatement("SELECT [clientId]\n" +
"      ,[clientName]\n" +
"      ,[address]\n" +
"      ,[contact]\n" +
"  FROM [Al_Qalam].[dbo].[clientsList]\n" +
"  ORDER BY clientId DESC, clientName ASC");
        rs=ps.executeQuery();

        while(rs.next())
        {
            employee17 = new Vector<>();
            employee17.add(rs.getString(1));
            employee17.add(rs.getString(2));
            employee17.add(rs.getString(3));
            employee17.add(rs.getString(4));

            employeevector17.add(employee17);
        }
        return employeevector17;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ClientDetail.SetModel">
        public void getClientNames() 
            {

            try{
                data17=gettemp17();

                header17=new Vector<>();
                header17.add("Id");
                header17.add("Client Name");
                header17.add("Address");
                header17.add("Contact No");
                
                editableModel = new DefaultTableModel (data17,header17){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                clientDetail_table.setModel(editableModel);
            }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ItemDetail">

        public Vector<Vector<String>> data18;
        public  Vector<String> header18;
        public Vector gettemp18() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector18=new Vector<>();

        ps=cn.prepareStatement("SELECT * FROM itemsList ORDER BY itemNo DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
        Vector<String> employee18=new Vector<>();
        employee18.add(rs.getString(1));
        employee18.add(rs.getString(2));
        employee18.add(rs.getString(3));
        employee18.add(rs.getString(4));
        employee18.add(rs.getString(5));

        employeevector18.add(employee18);
        }
        return employeevector18;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ItemDetail.SetModel">
        public void getItemNames() 
            {

            try{
            data18=gettemp18();

                header18=new Vector<>();
                header18.add("Id");
                header18.add("Name");
                header18.add("Unit");
                header18.add("Total Sold Quantity");
                header18.add("Total Sold Amount");
                
                editableModel = new DefaultTableModel (data18,header18){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                productNames_table.setModel(editableModel);
        }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="BankList">

    public Vector<Vector<String>> data19;
    public  Vector<String> header19;
    public Vector gettemp19() throws SQLException,ClassNotFoundException
    {
        Vector<Vector<String>> employeevector19=new Vector<>();
        Vector<String> employee19;
        ps=cn.prepareStatement("SELECT [bankId]\n" +
        "      ,[bankName]\n" +
        "  FROM [Al_Qalam].[dbo].[bankList]\n" +
        "  ORDER BY bankId DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
            employee19 = new Vector<>();
            employee19.add(rs.getString(1));
            employee19.add(rs.getString(2));

            employeevector19.add(employee19);
        }
        return employeevector19;
    }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="BankList.SetModel">
        public void getBankList() 
            {

            try{
                data19=gettemp19();

                header19=new Vector<>();
                header19.add("Id");
                header19.add("Bank Name");
                
                editableModel = new DefaultTableModel (data19,header19){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                bankNames_table.setModel(editableModel);
            }
        catch(SQLException | ClassNotFoundException ex){
          JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
        }

        }
    // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ExpenseList">

        public Vector<Vector<String>> data20;
        public  Vector<String> header20;
        public Vector gettemp20() throws SQLException,ClassNotFoundException
        {
        Vector<Vector<String>> employeevector20=new Vector<>();
        Vector<String> employee20;
        ps=cn.prepareStatement("SELECT [expenseId]\n" +
        "      ,[expenseName]\n" +
        "  FROM [Al_Qalam].[dbo].[expenseList]\n" +
        "  ORDER BY expenseId DESC");
        rs=ps.executeQuery();

        while(rs.next())
        {
            employee20=new Vector<>();
            employee20.add(rs.getString(1));
            employee20.add(rs.getString(2));

            employeevector20.add(employee20);
        }
        return employeevector20;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ExpenseList.SetModel">
        public void getExpenseList() 
            {
                try{
                    data20=gettemp20();

                    header20=new Vector<>();
                    header20.add("Id");
                    header20.add("Name");
                
                editableModel = new DefaultTableModel (data20,header20){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };
                
                expenseNames_table.setModel(editableModel);
                }
                catch(SQLException | ClassNotFoundException ex){
                  JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
                }
        }
    // </editor-fold>
//        </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Threshold,Uninvoiced,SalesInvoice(21-23)">
        // <editor-fold defaultstate="collapsed" desc="ThresholdPoint(21)">

            public Vector<Vector<String>> data21;
            public  Vector<String> header21;
            public Vector gettemp21() throws SQLException,ClassNotFoundException
            {
            Vector<Vector<String>> employeevector21=new Vector<>();

            ps=cn.prepareStatement("SELECT itemName,quantity,minStockAmount FROM itemsList JOIN stock ON itemsList.itemNo=stock.itemNo WHERE quantity<=minStockAmount");
            rs=ps.executeQuery();

            while(rs.next())
            {
            Vector<String> employee21=new Vector<>();
            employee21.add(rs.getString(1));
            employee21.add(rs.getString(2));
            employee21.add(rs.getString(3));

            employeevector21.add(employee21);
            }
            return employeevector21;
            }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ThresholdPoint.SetModel">
            public void getThreshold() 
                {

                try{
                data21=gettemp21();

                    header21=new Vector<>();
                    header21.add("Product Name");
                    header21.add("Current Quantity");
                    header21.add("Minimum Stock Amount");

            thresholdPoint_table.setModel(new javax.swing.table.DefaultTableModel(data21,header21));
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
            }

            }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="UnInvoicedCh(22)">
            public Vector<Vector<String>> data22;
            public  Vector<String> header22;
            public Vector gettemp22() throws SQLException,ClassNotFoundException
            {
            Vector<Vector<String>> employeevector22=new Vector<>();
            Vector<String> employee22;

            ps=cn.prepareStatement("SELECT DISTINCT chalanNo,date,clientName,delayedDuration FROM deliveryChalan WHERE invoiced='No'");
            rs=ps.executeQuery();

            while(rs.next())
            {
                employee22=new Vector<>();
                employee22.add(rs.getString(1));
                employee22.add(rs.getString(2));
                employee22.add(rs.getString(3));
                employee22.add(rs.getString(4));

                employeevector22.add(employee22);
            }
            return employeevector22;
            }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="UnInvoicedCh.SetModel">
            public void getUnInvoicedCh() 
                {

                try{
                data22=gettemp22();

                    header22=new Vector<>();
                    header22.add("Chalan No");
                    header22.add("Date");
                    header22.add("Client Name");
                    header22.add("Days Before");

            unInvoicedChReport_table.setModel(new javax.swing.table.DefaultTableModel(data22,header22));
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
            }

            }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="salesInvoice(23)">
            public static Vector<Vector<String>> data23;
            public static Vector<String> header23;
            public static Vector gettemp23() throws SQLException,ClassNotFoundException,NullPointerException
            {
                Vector<Vector<String>> employeevector23=new Vector<>();
                Vector<String> employee23=new Vector<>();
                for (Object chalanNo1 : chalanNos) {
                ps1 = cn1.prepareStatement("SELECT itemName,unit,quantity FROM itemsList JOIN deliveryChalan ON itemsList.itemNo=deliveryChalan.itemNo WHERE chalanNo=" + chalanNo1 + "");
                rs1=ps1.executeQuery();
                while(rs1.next())
                {
                    employee23=new Vector<>();
                    employee23.add(rs1.getString(1));
                    employee23.add(rs1.getString(2));
                    employee23.add(rs1.getString(3));
                    employee23.add("0");
                    employee23.add("0.0");

                    employeevector23.add(employee23);
                }
            }

                return employeevector23;

            }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="salesInvoice.SetModel">
            public static void setSalesInvoice() {
                try{

                    data23=gettemp23();

                    header23=new Vector<>();
                    header23.add("Product Name");
                    header23.add("Unit");
                    header23.add("Quantity");
                    header23.add("Rate");
                    header23.add("Amount");

                    // <editor-fold defaultstate="collapsed" desc="columnEditable">
                            salesInvoiceModel = new DefaultTableModel(data23,header23){
                                @Override
                                public boolean isCellEditable(int row, int col) {
                                    switch (col) {
                                        case 3:
                                            return true;
                                        default:
                                            return false;
                                    }
                                }
                            };
    //                        </editor-fold>
                    for(int i=0; i<salesInvoiceModel.getRowCount();i++){

                        for(int j = i+1; j<salesInvoiceModel.getRowCount(); j++){
                            merged = salesInvoiceModel.getValueAt(i, 0).toString();
                            merged1 = salesInvoiceModel.getValueAt(j, 0).toString();
                            if(merged1.equals(merged)){
                                mergedQuan = (Integer.parseInt(salesInvoiceModel.getValueAt(i, 2).toString())) + (Integer.parseInt(salesInvoiceModel.getValueAt(j, 2).toString()));
                                salesInvoiceModel.setValueAt(mergedQuan, i, 2);
                                salesInvoiceModel.removeRow(j);
                                j-=1;
                            }
                        }
                    }
                        salesInvoice_table.setModel(salesInvoiceModel);

                    SIFlag=true;
                    salesInvoice_table.setColumnSelectionInterval(3, 3);



                }
            catch(SQLException | ClassNotFoundException | NullPointerException ex){

            }
            }
        // </editor-fold>
//        </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="AigingReports(19-20)">
        // <editor-fold defaultstate="collapsed" desc="PaybleAging">

    public Vector<Vector<String>> data24;
    public  Vector<String> header24;
    Vector<Vector<String>> employeevector24;
    Vector<String> employee24;
    public Vector gettemp24() throws SQLException,ClassNotFoundException
    {
        employeevector24=new Vector<>();

        ps=cn.prepareStatement("SELECT DISTINCT transactionNo,date,cast([tAmount] as decimal(10,2)),cast([paymentLeft] as decimal(10,2)),delayedDuration FROM purchaseTransaction WHERE sellerName='"+aigingReport_comboBox.getSelectedItem().toString()+"' and delayedDuration>="+agingDuration+" AND paymentStatus='Not Paid'");
        rs=ps.executeQuery();

        while(rs.next())
        {
            employee24=new Vector<>();
            employee24.add(rs.getString(1));
            employee24.add(rs.getString(2));
            employee24.add(rs.getString(3));
            employee24.add(rs.getString(4));
            employee24.add(rs.getString(5));

            employeevector24.add(employee24);
        }

        return employeevector24;
    }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="PaybleAging.SetModel">
    public void getPaybleAging() 
        {
        
        try{
            data24=gettemp24();
            
            header24=new Vector<>();
            header24.add("Transaction No");
            header24.add("Date");
            header24.add("Amount");
            header24.add("Payment Left");
            header24.add("Delayed Duration");
                
            editableModel = new DefaultTableModel (data24,header24){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
            };

            aigingReport_table.setModel(editableModel);
            
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            aigingReport_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            aigingReport_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            aigingReport_table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            aigingReport_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
            aigingReport_table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

            for(int i=0; i<aigingReport_table.getRowCount(); i++){
                if(i==0){
                    TAmount_aiging=0;
                }
                TAmount_aiging+=Float.parseFloat(aigingReport_table.getValueAt(i, 3).toString());
                tAmount_aiging.setText(String.valueOf(TAmount_aiging));
            }
        }
    catch(SQLException | ClassNotFoundException ex){
      JOptionPane.showMessageDialog(rootPane, ex.getMessage());
    }

    }
// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="RcvbleAging">

        public Vector<Vector<String>> data25;
        public  Vector<String> header25;
        Vector<String> employee25;
        Vector<Vector<String>> employeevector25;
        public Vector gettemp25() throws SQLException,ClassNotFoundException
        {
        
        ps=cn.prepareStatement("SELECT DISTINCT invoiceNo,date,cast([tAmount] as decimal(10,2)),cast([paymentLeft] as decimal(10,2)),delayedDuration FROM salesInvoice WHERE clientName='"+aigingReport_comboBox.getSelectedItem().toString()+"' AND delayedDuration>="+agingDuration+" AND paymentStatus='Not Recieved'");
        rs=ps.executeQuery();

        while(rs.next())
        {
            employee25=new Vector<>();
            
            employee25.add(rs.getString(1));
            employee25.add(rs.getString(2));
            employee25.add(rs.getString(3));
            employee25.add(rs.getString(4));
            employee25.add(rs.getString(5));

            employeevector25.add(employee25);
        }
        return employeevector25;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="RcvbleAging.SetModel">
        public void getRcvbleAging(){
            try{
                data25=gettemp25();

                header25=new Vector<>();
                header25.add("Invoice No");
                header25.add("Date");
                header25.add("Total Amount");
                header25.add("Payment Left");
                header25.add("Delayed Duration");
                
                editableModel = new DefaultTableModel (data25,header25){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
                };

                aigingReport_table.setModel(editableModel);
            
                rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                aigingReport_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                aigingReport_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                aigingReport_table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
                aigingReport_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
                aigingReport_table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

                for(int i=0; i<aigingReport_table.getRowCount(); i++){
                    if(i==0){
                        TAmount_aiging=0;
                    }
                    TAmount_aiging+=Float.parseFloat(aigingReport_table.getValueAt(i, 3).toString());
                    tAmount_aiging.setText(String.valueOf(TAmount_aiging));
                }
            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    // </editor-fold>
//    </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Books(26,27)">
        // <editor-fold defaultstate="collapsed" desc="CashBook(26)">

            public Vector<Vector<String>> data26;
            public  Vector<String> header26;
            public Vector gettemp26() throws SQLException,ClassNotFoundException
            {
            Vector<Vector<String>> employeevector26=new Vector<>();
            Vector<String> employee26;
            ps=cn.prepareStatement("SELECT [id]\n" +
                "            ,FORMAT(date,'dd/MM/yy')\n" +
                "            ,[refrenceNo]\n" +
                "            ,[headOfAccount]\n" +
                "            ,[description]\n" +
                "            ,cast([debit] as decimal(10,2))\n" +
                "            ,cast([credit] as decimal(10,2))\n" +
                "            ,cast([balance] as decimal(10,2))\n" +
                "            FROM [Al_Qalam].[dbo].[cashBook]");
            rs=ps.executeQuery();

            while(rs.next())
            {
            employee26=new Vector<>();
            employee26.add(rs.getString(1));
            employee26.add(rs.getString(2));
            employee26.add(rs.getString(3));
            employee26.add(rs.getString(4));
            employee26.add(rs.getString(5));
            employee26.add(rs.getString(6));
            employee26.add(rs.getString(7));
            employee26.add(rs.getString(8));

            employeevector26.add(employee26);
            }
            return employeevector26;
            }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="CashBook.SetModel">
            public void getCashBook() 
                {

                try{
                    data26=gettemp26();

                    header26=new Vector<>();
                    header26.add("S.No");
                    header26.add("Date");
                    header26.add("Refrence No");
                    header26.add("Head of Account");
                    header26.add("Description");
                    header26.add("Debit");
                    header26.add("Credit");
                    header26.add("Balance");

                    stockReportModel = new DefaultTableModel (data26,header26){
                        @Override
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    
                    cashBook_table.setModel(stockReportModel);
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    cashBook_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                    cashBook_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                    cashBook_table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                    cashBook_table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
                    cashBook_table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
                    cashBook_table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
                    cashBook_table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
                    cashBook_table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
                    TableColumnModel tcm = cashBook_table.getColumnModel();
                    tcm.getColumn(0).setMinWidth(40);
                    tcm.getColumn(1).setMinWidth(70);
                    tcm.getColumn(2).setMinWidth(35);
                    tcm.getColumn(3).setMinWidth(100);
                    tcm.getColumn(4).setMinWidth(150);
                    tcm.getColumn(5).setMinWidth(90);
                    tcm.getColumn(6).setMinWidth(90);
                    tcm.getColumn(7).setMinWidth(100);

            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
            }

            }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ChequeBook(27)">

            public Vector<Vector<String>> data27;
            public  Vector<String> header27;
            public Vector gettemp27() throws SQLException,ClassNotFoundException
            {
            Vector<Vector<String>> employeevector27=new Vector<>();
            Vector<String> employee27;
            ps=cn.prepareStatement("SELECT [id]\n" +
                "            ,FORMAT(date,'dd/MM/yy') AS Date\n" +
                "		 ,[refrenceNo]\n" +
                "		 ,[bank]\n" +
                "		 ,[headOfAccount]\n" +
                "		 ,[description]\n" +
                "            ,cast([debit] as decimal(10,2))\n" +
                "            ,cast([credit] as decimal(10,2))\n" +
                "            ,cast([balance] as decimal(10,2))\n" +
                "  FROM [Al_Qalam].[dbo].[chequeBook]");
            rs=ps.executeQuery();

            while(rs.next())
            {
            employee27=new Vector<>();
            employee27.add(rs.getString(1));
            employee27.add(rs.getString(2));
            employee27.add(rs.getString(3));
            employee27.add(rs.getString(4));
            employee27.add(rs.getString(5));
            employee27.add(rs.getString(6));
            employee27.add(rs.getString(7));
            employee27.add(rs.getString(8));
            employee27.add(rs.getString(9));

            employeevector27.add(employee27);
            }
            return employeevector27;
            }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ChequeBook.SetModel">
            public void getChequeBook() 
                {

                try{
                data27=gettemp27();

                    header27=new Vector<>();
                    header27.add("S.No");
                    header27.add("Date");
                    header27.add("Refrence No");
                    header27.add("Bank");
                    header27.add("Head of Account");
                    header27.add("Description");
                    header27.add("Debit");
                    header27.add("Credit");
                    header27.add("Balance");

                    chequeBook_table.setModel(new javax.swing.table.DefaultTableModel(data27,header27));
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    chequeBook_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                    chequeBook_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                    chequeBook_table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                    chequeBook_table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
                    chequeBook_table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
                    chequeBook_table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
                    chequeBook_table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
                    chequeBook_table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
                    chequeBook_table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
                    TableColumnModel tcm = chequeBook_table.getColumnModel();
                    tcm.getColumn(0).setMinWidth(40);
                    tcm.getColumn(1).setMinWidth(70);
                    tcm.getColumn(2).setMinWidth(40);
                    tcm.getColumn(3).setMinWidth(50);
                    tcm.getColumn(4).setMinWidth(90);
                    tcm.getColumn(5).setMinWidth(150);
                    tcm.getColumn(6).setMinWidth(80);
                    tcm.getColumn(7).setMinWidth(80);
                    tcm.getColumn(8).setMinWidth(80);

            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
            }

            }
        // </editor-fold>
//        </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="GetLedgers(28-31)">
        // <editor-fold defaultstate="collapsed" desc="SupplierLedger">

        public Vector<Vector<String>> data28;
        public  Vector<String> header28;
        public Vector gettemp28() throws SQLException,ClassNotFoundException
        {
            Vector<Vector<String>> employeevector28=new Vector<>();
            Vector<String> employee28;
            ps=cn.prepareStatement("SELECT FORMAT(date,'dd/MM/yy') AS Date,refrenceNo,description,cast([debit] as decimal(10,2)),cast([credit] as decimal(10,2)),cast([balance] as decimal(10,2)) FROM supplierList join supplierLedger ON supplierList.sellerId=supplierLedger.sellerId WHERE sellerName='"+ledgerBox.getSelectedItem().toString()+"' AND date BETWEEN '"+fromDate.getText()+"' AND '"+toDate.getText()+"' ORDER BY recordNo ASC");
            rs=ps.executeQuery();

            while(rs.next())
            {
                employee28=new Vector<>();
                employee28.add(rs.getString(1));
                employee28.add(rs.getString(2));
                employee28.add(rs.getString(3));
                employee28.add(rs.getString(4));
                employee28.add(rs.getString(5));
                employee28.add(rs.getString(6));

                employeevector28.add(employee28);
            }
                return employeevector28;
            }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="SupplierLedger.SetModel">
            public void getSupplierLedger() 
                {

                try{
                data28=gettemp28();

                    header28=new Vector<>();
                    header28.add("Date");
                    header28.add("Refrence No");
                    header28.add("Description");
                    header28.add("Debit");
                    header28.add("Credit");
                    header28.add("Balance");
                    
                    editableModel = new DefaultTableModel (data28,header28){
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                    };
                    ledger_table.setModel(editableModel);
                    
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    ((DefaultTableCellRenderer)ledger_table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                    ledger_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
                    ledger_table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
                    ledger_table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
                    TableColumnModel tcm = ledger_table.getColumnModel();
                    tcm.getColumn(0).setMinWidth(80);
                    tcm.getColumn(1).setMinWidth(100);
                    tcm.getColumn(2).setMinWidth(200);
                    tcm.getColumn(3).setMinWidth(100);
                    tcm.getColumn(4).setMinWidth(100);
                    tcm.getColumn(5).setMinWidth(100);

            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
            }

            }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ClientLedger">

        public Vector<Vector<String>> data29;
        public  Vector<String> header29;
        public Vector gettemp29() throws SQLException,ClassNotFoundException
        {
            Vector<Vector<String>> employeevector29=new Vector<>();
            Vector<String> employee29;
            ps=cn.prepareStatement("SELECT FORMAT(date,'dd/MM/yy') AS Date,refrenceNo,description,cast([debit] as decimal(10,2)),cast([credit] as decimal(10,2)),cast([balance] as decimal(10,2)) FROM clientsList join ledger ON clientsList.clientId=ledger.clientId WHERE clientName='"+ledgerBox.getSelectedItem().toString()+"' AND date BETWEEN '"+fromDate.getText()+"' AND '"+toDate.getText()+"' ORDER BY recordNo ASC");
            rs=ps.executeQuery();

            while(rs.next())
            {
                employee29=new Vector<>();
                employee29.add(rs.getString(1));
                employee29.add(rs.getString(2));
                employee29.add(rs.getString(3));
                employee29.add(rs.getString(4));
                employee29.add(rs.getString(5));
                employee29.add(rs.getString(6));

                employeevector29.add(employee29);
            }
                return employeevector29;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ClientLedger.SetModel">
            public void getClientLedger() 
                {

                try{
                data29=gettemp29();

                    header29=new Vector<>();
                    header29.add("Date");
                    header29.add("Refrence No");
                    header29.add("Description");
                    header29.add("Debit");
                    header29.add("Credit");
                    header29.add("Balance");
                    
                    editableModel = new DefaultTableModel (data29,header29){
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                    };
                    ledger_table.setModel(editableModel);
                    
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    ((DefaultTableCellRenderer)ledger_table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                    ledger_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
                    ledger_table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
                    ledger_table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
                    TableColumnModel tcm = ledger_table.getColumnModel();
                    tcm.getColumn(0).setMinWidth(80);
                    tcm.getColumn(1).setMinWidth(100);
                    tcm.getColumn(2).setMinWidth(200);
                    tcm.getColumn(3).setMinWidth(100);
                    tcm.getColumn(4).setMinWidth(100);
                    tcm.getColumn(5).setMinWidth(100);

            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
            }

            }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="BankLedger">

        public Vector<Vector<String>> data30;
        public  Vector<String> header30;
        public Vector gettemp30() throws SQLException,ClassNotFoundException
        {
            Vector<Vector<String>> employeevector30=new Vector<>();
            Vector<String> employee30;
            ps=cn.prepareStatement("SELECT FORMAT(date,'dd/MM/yy') AS Date,refrenceNo,description,cast([debit] as decimal(10,2)),cast([credit] as decimal(10,2)),cast([balance] as decimal(10,2)) FROM bankList join bankLedger ON bankList.bankId=bankLedger.bankId WHERE bankName='"+ledgerBox.getSelectedItem().toString()+"' AND date BETWEEN '"+fromDate.getText()+"' AND '"+toDate.getText()+"' ORDER BY id ASC");
            rs=ps.executeQuery();

            while(rs.next())
            {
                employee30=new Vector<>();
                employee30.add(rs.getString(1));
                employee30.add(rs.getString(2));
                employee30.add(rs.getString(3));
                employee30.add(rs.getString(4));
                employee30.add(rs.getString(5));
                employee30.add(rs.getString(6));

                employeevector30.add(employee30);
            }
                return employeevector30;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="BankLedger.SetModel">
            public void getBankLedger() 
                {

                try{
                    data30=gettemp30();

                    header30=new Vector<>();
                    header30.add("Date");
                    header30.add("Refrence No");
                    header30.add("Description");
                    header30.add("Debit");
                    header30.add("Credit");
                    header30.add("Balance");
                    
                    editableModel = new DefaultTableModel (data30,header30){
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                    };
                    ledger_table.setModel(editableModel);
                    
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    ((DefaultTableCellRenderer)ledger_table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                    ledger_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
                    ledger_table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
                    ledger_table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
                    TableColumnModel tcm = ledger_table.getColumnModel();
                    tcm.getColumn(0).setMinWidth(80);
                    tcm.getColumn(1).setMinWidth(100);
                    tcm.getColumn(2).setMinWidth(200);
                    tcm.getColumn(3).setMinWidth(100);
                    tcm.getColumn(4).setMinWidth(100);
                    tcm.getColumn(5).setMinWidth(100);

            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
            }

            }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ExpenseLedger">

        public Vector<Vector<String>> data31;
        public  Vector<String> header31;
        public Vector gettemp31() throws SQLException,ClassNotFoundException
        {
            Vector<Vector<String>> employeevector31=new Vector<>();
            Vector<String> employee31;
            ps=cn.prepareStatement("SELECT FORMAT(date,'dd/MM/yy') AS Date,refrenceNo,description,cast([debit] as decimal(10,2)),cast([credit] as decimal(10,2)),cast([balance] as decimal(10,2)) FROM expenseList JOIN expenseLedger ON expenseList.expenseId=expenseLedger.expenseId WHERE expenseName='"+ledgerBox.getSelectedItem().toString()+"' AND date BETWEEN '"+fromDate.getText()+"' AND '"+toDate.getText()+"' ORDER BY id ASC");
            rs=ps.executeQuery();

            while(rs.next())
            {
                employee31=new Vector<>();
                employee31.add(rs.getString(1));
                employee31.add(rs.getString(2));
                employee31.add(rs.getString(3));
                employee31.add(rs.getString(4));
                employee31.add(rs.getString(5));
                employee31.add(rs.getString(6));

                employeevector31.add(employee31);
            }
                return employeevector31;
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="ExpenseLedger.SetModel">
            public void getExpenseLedger() 
                {

                try{
                    data31=gettemp31();

                    header31=new Vector<>();
                    header31.add("Date");
                    header31.add("Refrence No");
                    header31.add("Description");
                    header31.add("Debit");
                    header31.add("Credit");
                    header31.add("Balance");
                    
                    editableModel = new DefaultTableModel (data31,header31){
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                    };
                    ledger_table.setModel(editableModel);
                    
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    ((DefaultTableCellRenderer)ledger_table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                    ledger_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                    ledger_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
                    ledger_table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
                    ledger_table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
                    TableColumnModel tcm = ledger_table.getColumnModel();
                    tcm.getColumn(0).setMinWidth(80);
                    tcm.getColumn(1).setMinWidth(100);
                    tcm.getColumn(2).setMinWidth(200);
                    tcm.getColumn(3).setMinWidth(100);
                    tcm.getColumn(4).setMinWidth(100);
                    tcm.getColumn(5).setMinWidth(100);

            }
            catch(SQLException | ClassNotFoundException ex){
              JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
            }

        }
        // </editor-fold>
//        </editor-fold>
    
    public ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Software.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
}
    private DefaultMutableTreeNode createNodes(DefaultMutableTreeNode rootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // <editor-fold defaultstate="collapsed" desc="RendererClass">
    class MyRenderer extends DefaultTreeCellRenderer{
    Icon ReportIcon;
    Icon ReportIcon1;
    Icon TransactionIcon;
    Icon CoinIcon;
    Icon RcvbleIcon;
    Icon PybleIcon;
    public MyRenderer(Icon transactionIconG, Icon reportIconG){
        ReportIcon1 = reportIconG;
        TransactionIcon = transactionIconG;
    }
    @Override
    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus ){
        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
        setBackgroundNonSelectionColor(new Color(244,244,255));
        ImageIcon openIconT = createImageIcon("/Images/silver_open.png");
        ImageIcon closeIcon = createImageIcon("/Images/silver_closed.png");
        setClosedIcon(closeIcon);
        setOpenIcon(openIconT);
        if(leaf){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            DefaultMutableTreeNode parentnode = (DefaultMutableTreeNode)node.getParent();
            String parentInfo = parentnode.getUserObject().toString();
            String childInfo = node.getUserObject().toString();
            switch(parentInfo){
                default:
                            setIcon(ReportIcon1);
                        break;
                    case "Aging Report":
                        if(childInfo.equals("Recievable"))
                            setIcon(rcvbleIcon);
                        else
                            setIcon(pybleIcon);
                        break;    
                    case "Transactions":
                        setIcon(TransactionIcon);
                        break;
                }
        }
        return this;
    }
}
//    </editor-fold>
    String ab111c;
    /**
     * Creates new form Software
     */
    public Software() {
        initComponents();
        setLocation(0,0);
        setExtendedState(MAXIMIZED_BOTH);
        
        DateFormat dateFormat1 = new SimpleDateFormat("M/d/yyyy");
        DateFormat dateFormat2 = new SimpleDateFormat("MMMM");
        Date date1;
        date1 = new Date();
        dateLabel.setText(dateFormat1.format(date1)+" | ");
        monthLabel.setText(dateFormat2.format(date1));
        try{
            cn=conn();
            cn1=conn();
        }
        catch(SQLException | ClassNotFoundException ex){
            JOptionPane.showMessageDialog(Main, ex.getLocalizedMessage());
        }
        delayedDurationUpdateSI();
        delayedDurationUpdateDC();
        delayedDurationUpdatePT();
        delayedDurationUpdateCL();
        delayedDurationUpdateSL();
        myTree.setCellRenderer(new MyRenderer(transactionIcon, reportIcon1));
        // <editor-fold defaultstate="collapsed" desc="chalanTable">
        JTable table = new JTable(chalan);
        chalan.addRow(newRowDC);        
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

//  Create the scroll pane and add the table to it. 
    JScrollPane scrollPane = new JScrollPane(table);
//  Add the scroll pane to this window.
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
        System.exit(0);
        }
        });
//    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="pTransactionTable">
        JTable tableP = new JTable(pTransaction);
        pTransaction.addRow(newRowPT);        
        tableP.setPreferredScrollableViewportSize(new Dimension(500, 70));

//  Create the scroll pane and add the table to it. 
    JScrollPane scrollPaneP = new JScrollPane(tableP);
//  Add the scroll pane to this window.
    addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
    System.exit(0);
    }
    });
//    </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="expenseTable">
        JTable tablee = new JTable(expense1);
        expense1.addRow(nRowE);        
        tablee.setPreferredScrollableViewportSize(new Dimension(500, 70));

//  Create the scroll pane and add the table to it. 
    JScrollPane scrollPane1 = new JScrollPane(tablee);
//  Add the scroll pane to this window.
    addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
    System.exit(0);
    }
    });
//    </editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Visibling False All Layered Panes">
        pTransaction_lpane.setVisible(false);
        dChalan_lpane.setVisible(false);
        addClient_lpane.setVisible(false);
        stockReport_lpane.setVisible(false);
        salesReportParameter_lpane.setVisible(false);
        salesReport_lpane.setVisible(false);
        aigingReportParameter_lpane.setVisible(false);
        expense_lpane.setVisible(false);
        calc_lpane.setVisible(false);
        aigingReport_lpane.setVisible(false);
        paymentPaid_lpane.setVisible(false);
        paymentRecieved_lpane.setVisible(false);
        ledger_lpane.setVisible(false);
        profitReportPerimeter_lpane.setVisible(false);
        profitReport_lpane.setVisible(false);
        purchaseOrder_lpane.setVisible(false);
        salesInvoice_lpane.setVisible(false);
        addSupplier_lpane.setVisible(false);
        addProduct_lpane.setVisible(false);
        unInvoicedChReport_lpane.setVisible(false);
        thresholdPoint_lpane.setVisible(false);
        addBankNames_lpane.setVisible(false);
        addExpenseName_lpane.setVisible(false);
        cashBook_lpane.setVisible(false);
        bankBook_lpane.setVisible(false);
//        </editor-fold>
//        try{
//            
//            ps=cn.prepareStatement("SELECT bankName FROM bankList");
//            rs=ps.executeQuery();
//            if(rs.next()){
//                ab111c = rs.getString(1);
//            }else{
//                bank = new Bank(this, Calc);
//                bank.setVisible(true);
//                bank.setLocation(100, 100);
//            }
//            ps=cn.prepareStatement("SELECT name FROM owners");
//            rs=ps.executeQuery();
//            if(rs.next()){
//                ab111c = rs.getString(1);
//            }
//            else{
//                owner = new Owner(this, Calc);
//                owner.setVisible(true);
//                owner.setLocation(100, 100);
//                owner.getBankNames(owner.bankNameBox_owner);
//            }
//        }
//        catch(SQLException e){
//            System.out.println(e.getLocalizedMessage());
//        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mode_PT = new javax.swing.ButtonGroup();
        salesReport_modes = new javax.swing.ButtonGroup();
        saleR_modes = new javax.swing.ButtonGroup();
        aging_modes = new javax.swing.ButtonGroup();
        expenseModes = new javax.swing.ButtonGroup();
        paidGroup = new javax.swing.ButtonGroup();
        daysGroup = new javax.swing.ButtonGroup();
        mode_PV = new javax.swing.ButtonGroup();
        mode_RV = new javax.swing.ButtonGroup();
        mode_E = new javax.swing.ButtonGroup();
        mode_SI = new javax.swing.ButtonGroup();
        Backgorund = new javax.swing.JPanel();
        Banner = new javax.swing.JPanel();
        Buttons = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Tree = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        myTree = new javax.swing.JTree();
        Main = new javax.swing.JPanel();
        addProduct_lpane = new javax.swing.JLayeredPane();
        addProduct_panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        productNames_table = new javax.swing.JTable();
        iName_field = new javax.swing.JTextField();
        unit_field = new javax.swing.JTextField();
        minStockAmount_field = new javax.swing.JTextField();
        addProduct_btn = new javax.swing.JButton();
        openingStockQauntiy_field = new javax.swing.JTextField();
        openingStockAmount_field = new javax.swing.JTextField();
        addClient_lpane = new javax.swing.JLayeredPane();
        addClient_panel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        clientDetail_table = new javax.swing.JTable();
        clientName_Field = new javax.swing.JTextField();
        clientAddress_field = new javax.swing.JTextField();
        clientContactNo_field = new javax.swing.JTextField();
        AddClient_Btn = new javax.swing.JButton();
        clientOpeningBalance_field = new javax.swing.JTextField();
        addSupplier_lpane = new javax.swing.JLayeredPane();
        addSupplier_panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        supplierDetail_table = new javax.swing.JTable();
        AddSupplier_Btn = new javax.swing.JButton();
        supplierName_field = new javax.swing.JTextField();
        supplierAddress_field = new javax.swing.JTextField();
        supplierContactNo_field = new javax.swing.JTextField();
        supplierOpeningBalance_field = new javax.swing.JTextField();
        addBankNames_lpane = new javax.swing.JLayeredPane();
        addBankNames_panel = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        bankNames_table = new javax.swing.JTable();
        bankName_field = new javax.swing.JTextField();
        addBank_btn = new javax.swing.JButton();
        banKOpeningBalance_field = new javax.swing.JTextField();
        addExpenseName_lpane = new javax.swing.JLayeredPane();
        addExpenseName_panel = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        expenseNames_table = new javax.swing.JTable();
        expenseName_field = new javax.swing.JTextField();
        addExpenseName_btn = new javax.swing.JButton();
        purchaseOrder_lpane = new javax.swing.JLayeredPane();
        btns_purchaseOrder = new javax.swing.JPanel();
        previousOrder = new javax.swing.JButton();
        nextOrder = new javax.swing.JButton();
        newOrder = new javax.swing.JButton();
        makeOrder = new javax.swing.JButton();
        purchaseOrder_panel = new javax.swing.JPanel();
        Date5 = new javax.swing.JLabel();
        ENo3 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        purchaseOrder_table = new javax.swing.JTable();
        Client1 = new javax.swing.JLabel();
        orderNo = new javax.swing.JTextField();
        clientBox_PO = new javax.swing.JComboBox();
        dateField_O = new datechooser.beans.DateChooserCombo();
        orderStatus_label = new javax.swing.JLabel();
        pTransaction_lpane = new javax.swing.JLayeredPane();
        pTransaction_panel = new javax.swing.JPanel();
        supplierBox_pT = new javax.swing.JComboBox();
        sellerNameLabel_PT = new javax.swing.JLabel();
        Date3 = new javax.swing.JLabel();
        pT_scrollPane = new javax.swing.JScrollPane();
        pTransaction_table = new javax.swing.JTable();
        tAmount_field_PT = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        transactionNo = new javax.swing.JTextField();
        tAmount_label = new javax.swing.JLabel();
        paymentStatus_label = new javax.swing.JLabel();
        dateField_PT = new datechooser.beans.DateChooserCombo();
        Cash2 = new javax.swing.JRadioButton();
        Cheque2 = new javax.swing.JRadioButton();
        Credit2 = new javax.swing.JRadioButton();
        Modes2 = new javax.swing.JLabel();
        bankNameBox_PT = new javax.swing.JComboBox();
        btns_pTransaction = new javax.swing.JPanel();
        makeTransaction = new javax.swing.JButton();
        newTransaction = new javax.swing.JButton();
        nextTransaction = new javax.swing.JButton();
        prevTransaction = new javax.swing.JButton();
        dChalan_lpane = new javax.swing.JLayeredPane();
        dChalan_panel = new javax.swing.JPanel();
        Date2 = new javax.swing.JLabel();
        ENo2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        dChalan_table = new javax.swing.JTable();
        Client = new javax.swing.JLabel();
        chalanNo = new javax.swing.JTextField();
        clientsBox_Dc = new javax.swing.JComboBox();
        dateField_Dc = new datechooser.beans.DateChooserCombo();
        orderNo_box = new javax.swing.JComboBox();
        orderNoLable = new javax.swing.JLabel();
        btns_dChalan = new javax.swing.JPanel();
        previousChalan = new javax.swing.JButton();
        nextChalan = new javax.swing.JButton();
        newChalan = new javax.swing.JButton();
        makeChalan = new javax.swing.JButton();
        printChalan = new javax.swing.JButton();
        editChalan = new javax.swing.JButton();
        salesInvoice_lpane = new javax.swing.JLayeredPane();
        salesInvoice_panel = new javax.swing.JPanel();
        paymentLeft = new javax.swing.JLabel();
        tAmountLabel2 = new javax.swing.JLabel();
        Date6 = new javax.swing.JLabel();
        ENo4 = new javax.swing.JLabel();
        Client2 = new javax.swing.JLabel();
        invoiceNo = new javax.swing.JTextField();
        clientsBox_SI = new javax.swing.JComboBox();
        tAmount_field_SI = new javax.swing.JTextField();
        dateField_SI = new datechooser.beans.DateChooserCombo();
        addChalan = new javax.swing.JButton();
        modeLabel = new javax.swing.JLabel();
        Credit3 = new javax.swing.JRadioButton();
        Cheque3 = new javax.swing.JRadioButton();
        Cash3 = new javax.swing.JRadioButton();
        bankNameBox_SI = new javax.swing.JComboBox();
        jScrollPane12 = new javax.swing.JScrollPane();
        salesInvoice_table = new javax.swing.JTable();
        btns_salesInvoice = new javax.swing.JPanel();
        previousInvoice = new javax.swing.JButton();
        nextInvoice = new javax.swing.JButton();
        newInvoice = new javax.swing.JButton();
        makeInvoice = new javax.swing.JButton();
        printInvoice = new javax.swing.JButton();
        expense_lpane = new javax.swing.JLayeredPane();
        expense_panel = new javax.swing.JPanel();
        tAmountLabel1 = new javax.swing.JLabel();
        Date4 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        expense_table = new javax.swing.JTable();
        tAmount_fieldE = new javax.swing.JTextField();
        hExpense = new javax.swing.JRadioButton();
        oExpense = new javax.swing.JRadioButton();
        typeLabel = new javax.swing.JLabel();
        dateField_Ex = new datechooser.beans.DateChooserCombo();
        Cash4 = new javax.swing.JRadioButton();
        Cheque4 = new javax.swing.JRadioButton();
        Credit4 = new javax.swing.JRadioButton();
        bankNameBox_E = new javax.swing.JComboBox();
        modeLabel1 = new javax.swing.JLabel();
        Btns_expense = new javax.swing.JPanel();
        addExpense = new javax.swing.JButton();
        companyExpenseBtn = new javax.swing.JButton();
        personalExpenseBtn = new javax.swing.JButton();
        newExpense = new javax.swing.JButton();
        paymentPaid_lpane = new javax.swing.JLayeredPane();
        paymentPaid_panel = new javax.swing.JPanel();
        paidVoucher = new javax.swing.JPanel();
        amountPaid_field = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        supplierBox_pV = new javax.swing.JComboBox();
        cash3 = new javax.swing.JRadioButton();
        cheque3 = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        bankNameBox_pV = new javax.swing.JComboBox();
        paidVoucherNo = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        makePaidVoucher = new javax.swing.JButton();
        paidVoucher_history = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        paymentPaid_table = new javax.swing.JTable();
        paymentRecieved_lpane = new javax.swing.JLayeredPane();
        paymentReceived_panel = new javax.swing.JPanel();
        rcvdHistory_panel = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        paymentRcvd_table = new javax.swing.JTable();
        rcvdVoucher = new javax.swing.JPanel();
        makeRecivedVoucher = new javax.swing.JButton();
        amountRecieved_field = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        clientsBox_rV = new javax.swing.JComboBox();
        cash4 = new javax.swing.JRadioButton();
        cheque4 = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        bankNameBox_rV = new javax.swing.JComboBox();
        rcvdVNo = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        cashBook_lpane = new javax.swing.JLayeredPane();
        cashBook_panel = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        cashBook_table = new javax.swing.JTable();
        noteLabelCashBook = new javax.swing.JLabel();
        bankBook_lpane = new javax.swing.JLayeredPane();
        chequeBook_panel = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        chequeBook_table = new javax.swing.JTable();
        noteLabelchequeBook = new javax.swing.JLabel();
        stockReport_lpane = new javax.swing.JLayeredPane();
        stockReport_panel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        stockReport_table = new javax.swing.JTable();
        totalStockAmount_label = new javax.swing.JLabel();
        noteLabelStock = new javax.swing.JLabel();
        salesReportParameter_lpane = new javax.swing.JLayeredPane();
        salesReportParameter_panel = new javax.swing.JPanel();
        ViewReport = new javax.swing.JButton();
        salesReport_comboBox = new javax.swing.JComboBox();
        cOrP = new javax.swing.JLabel();
        summary = new javax.swing.JRadioButton();
        detail = new javax.swing.JRadioButton();
        salesReport_lpane = new javax.swing.JLayeredPane();
        salesReport_panel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        salesReport_table = new javax.swing.JTable();
        printSalesReport = new javax.swing.JButton();
        noteLabel = new javax.swing.JLabel();
        aigingReportParameter_lpane = new javax.swing.JLayeredPane();
        aigingReportParameter_panel = new javax.swing.JPanel();
        viewReport = new javax.swing.JButton();
        totalAiging = new javax.swing.JRadioButton();
        nintyP = new javax.swing.JRadioButton();
        sixtyP = new javax.swing.JRadioButton();
        thirtyP = new javax.swing.JRadioButton();
        aigingReport_comboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        startingDate_aiging = new datechooser.beans.DateChooserCombo();
        endDate_aiging = new datechooser.beans.DateChooserCombo();
        viewAigingBW_dates = new javax.swing.JButton();
        aigingReport_lpane = new javax.swing.JLayeredPane();
        aigingReport_panel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        aigingReport_table = new javax.swing.JTable();
        tAmount_aiging = new javax.swing.JLabel();
        paymentLeftLble = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        ledger_lpane = new javax.swing.JLayeredPane();
        ledger_panel = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        ledger_table = new javax.swing.JTable();
        viewLedger_Button = new javax.swing.JButton();
        ledgerBox = new javax.swing.JComboBox();
        printLedger = new javax.swing.JButton();
        fromDate = new datechooser.beans.DateChooserCombo();
        toDate = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();
        profitReport_lpane = new javax.swing.JLayeredPane();
        profitReport_panel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        profitReport_panel1 = new javax.swing.JPanel();
        chalanQuan = new javax.swing.JLabel();
        chalanAmount = new javax.swing.JLabel();
        stockAmount = new javax.swing.JLabel();
        profitAmount = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        companyStatus = new javax.swing.JLabel();
        transactionQuan = new javax.swing.JLabel();
        transactionAmount = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        calc_lpane = new javax.swing.JLayeredPane();
        calculator = new javax.swing.JPanel();
        TextFeild = new javax.swing.JTextField();
        btns = new javax.swing.JPanel();
        B1 = new javax.swing.JButton();
        B2 = new javax.swing.JButton();
        B3 = new javax.swing.JButton();
        B4 = new javax.swing.JButton();
        B5 = new javax.swing.JButton();
        B6 = new javax.swing.JButton();
        B7 = new javax.swing.JButton();
        B8 = new javax.swing.JButton();
        B9 = new javax.swing.JButton();
        B0 = new javax.swing.JButton();
        BPoint = new javax.swing.JButton();
        BEquals2 = new javax.swing.JButton();
        BPlus = new javax.swing.JButton();
        BMinus = new javax.swing.JButton();
        BMultiply = new javax.swing.JButton();
        BDivide = new javax.swing.JButton();
        C = new javax.swing.JButton();
        Ce = new javax.swing.JButton();
        BackSpace = new javax.swing.JButton();
        profitReportPerimeter_lpane = new javax.swing.JLayeredPane();
        jPanel3 = new javax.swing.JPanel();
        profitGain = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        viewProfit = new javax.swing.JButton();
        startDate = new datechooser.beans.DateChooserCombo();
        endDate = new datechooser.beans.DateChooserCombo();
        jLabel25 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        thresholdPoint_lpane = new javax.swing.JLayeredPane();
        thresholdPoint_panel = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        thresholdPoint_table = new javax.swing.JTable();
        unInvoicedChReport_lpane = new javax.swing.JLayeredPane();
        unInvoicedChReport_panel = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        unInvoicedChReport_table = new javax.swing.JTable();
        homeLable = new javax.swing.JLabel();
        Status = new javax.swing.JPanel();
        statusBarLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        main_menubar = new javax.swing.JMenuBar();
        File = new javax.swing.JMenu();
        PurchaseO_menu = new javax.swing.JMenuItem();
        PurchaseTr_menu = new javax.swing.JMenuItem();
        DeliveryCh_menu = new javax.swing.JMenuItem();
        salesInvoice_menu = new javax.swing.JMenuItem();
        expense_menu = new javax.swing.JMenuItem();
        rcvdVoucher_menu = new javax.swing.JMenuItem();
        paidVoucher_menu = new javax.swing.JMenuItem();
        reports_menu = new javax.swing.JMenu();
        aigingReport_menu = new javax.swing.JMenu();
        recievableAiging_menu = new javax.swing.JMenuItem();
        payableAiging_menu = new javax.swing.JMenuItem();
        salesRe = new javax.swing.JMenu();
        clientsSReport_menu = new javax.swing.JMenuItem();
        salersSReport_menu = new javax.swing.JMenuItem();
        purchaseReport_menu = new javax.swing.JMenu();
        sellersPReport_menu = new javax.swing.JMenuItem();
        productsPReport_menu = new javax.swing.JMenuItem();
        StockRe_menu = new javax.swing.JMenuItem();
        Edit = new javax.swing.JMenu();
        addItem_menu = new javax.swing.JMenuItem();
        addSeller_menu = new javax.swing.JMenuItem();
        addProducts_menu = new javax.swing.JMenuItem();
        addBank_menu = new javax.swing.JMenuItem();
        addExpense_menu = new javax.swing.JMenuItem();
        addOwner_menu = new javax.swing.JMenuItem();
        addInvestment_menu = new javax.swing.JMenuItem();
        ViewLedger = new javax.swing.JMenu();
        clientLedger_menu = new javax.swing.JMenuItem();
        sellerLedger_menu = new javax.swing.JMenuItem();
        bankLedger_menu = new javax.swing.JMenuItem();
        expenseLedger_menu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inventory Managment Solution (By: Xclusive Soft)");
        setExtendedState(1000);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        Backgorund.setBackground(new java.awt.Color(0, 72, 100));

        Banner.setBackground(new java.awt.Color(244, 244, 255));

        Buttons.setBackground(new java.awt.Color(244, 244, 255));
        Buttons.setOpaque(false);
        Buttons.setLayout(new java.awt.GridBagLayout());

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logoSoft.png"))); // NOI18N

        javax.swing.GroupLayout BannerLayout = new javax.swing.GroupLayout(Banner);
        Banner.setLayout(BannerLayout);
        BannerLayout.setHorizontalGroup(
            BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BannerLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BannerLayout.setVerticalGroup(
            BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Buttons, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Tree.setBackground(new java.awt.Color(244, 244, 255));

        scroll.setBorder(null);

        myTree.setBackground(new java.awt.Color(244, 244, 255));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Mange Data");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Head Of Accounts");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Assets");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Current Assets");
        javax.swing.tree.DefaultMutableTreeNode treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Investment");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Client Name");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Bank Name");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Product Name");
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Fixed Assets");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Libalities");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Supplier Name");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Owners Equity");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Expense Categories");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("View Books");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Cash Book");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Bank Book");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Transactions");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Purchase Order");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Purchase Transaction");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Delivery Chalan");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sales Invoice");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Expense");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Payment Recieved Voucher");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Payment Paid Voucher");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Report");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Aging Report");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Recievable");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Payable");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sales Report");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Client Wise");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Product Wise");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Purchase Report");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Supplier Wise");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Products Wise");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ledgers");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Supplier Ledger");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Client Ledger");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Bank Ledger");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Expense Ledger");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Stock Valuation");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Threshold Point");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("UnInvoiced Chalan Report");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Balance Sheet");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        myTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        myTree.setToolTipText("");
        myTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                myTreeMouseClicked(evt);
            }
        });
        scroll.setViewportView(myTree);

        javax.swing.GroupLayout TreeLayout = new javax.swing.GroupLayout(Tree);
        Tree.setLayout(TreeLayout);
        TreeLayout.setHorizontalGroup(
            TreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
        );
        TreeLayout.setVerticalGroup(
            TreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TreeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))
        );

        Main.setBackground(new java.awt.Color(244, 244, 255));

        addProduct_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Add Products ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
        addProduct_lpane.setOpaque(true);

        addProduct_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true));
        addProduct_panel.setMinimumSize(new java.awt.Dimension(620, 400));
        addProduct_panel.setOpaque(false);
        addProduct_panel.setPreferredSize(new java.awt.Dimension(620, 400));

        productNames_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        productNames_table.setForeground(new java.awt.Color(51, 51, 51));
        productNames_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        productNames_table.setRowHeight(22);
        jScrollPane3.setViewportView(productNames_table);

        iName_field.setForeground(java.awt.Color.gray);
        iName_field.setText("Name");
        iName_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                iName_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                iName_fieldFocusLost(evt);
            }
        });
        iName_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                iName_fieldKeyTyped(evt);
            }
        });

        unit_field.setForeground(java.awt.Color.gray);
        unit_field.setText("Unit");
        unit_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                unit_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                unit_fieldFocusLost(evt);
            }
        });
        unit_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                unit_fieldKeyTyped(evt);
            }
        });

        minStockAmount_field.setForeground(java.awt.Color.gray);
        minStockAmount_field.setText("Minimum Stock Amount");
        minStockAmount_field.setToolTipText("Write Min Stock Amount");
        minStockAmount_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minStockAmount_fieldActionPerformed(evt);
            }
        });
        minStockAmount_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                minStockAmount_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                minStockAmount_fieldFocusLost(evt);
            }
        });
        minStockAmount_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                minStockAmount_fieldKeyTyped(evt);
            }
        });

        addProduct_btn.setForeground(new java.awt.Color(0, 51, 102));
        addProduct_btn.setText("Add Product Name");
        addProduct_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct_btnActionPerformed(evt);
            }
        });

        openingStockQauntiy_field.setForeground(java.awt.Color.gray);
        openingStockQauntiy_field.setText("Opening Stock Quantity");
        openingStockQauntiy_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                openingStockQauntiy_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                openingStockQauntiy_fieldFocusLost(evt);
            }
        });
        openingStockQauntiy_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                openingStockQauntiy_fieldKeyTyped(evt);
            }
        });

        openingStockAmount_field.setForeground(java.awt.Color.gray);
        openingStockAmount_field.setText("Opening Stock Amount");
        openingStockAmount_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                openingStockAmount_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                openingStockAmount_fieldFocusLost(evt);
            }
        });
        openingStockAmount_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                openingStockAmount_fieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout addProduct_panelLayout = new javax.swing.GroupLayout(addProduct_panel);
        addProduct_panel.setLayout(addProduct_panelLayout);
        addProduct_panelLayout.setHorizontalGroup(
            addProduct_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addProduct_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addProduct_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addComponent(iName_field)
                    .addComponent(unit_field)
                    .addComponent(minStockAmount_field)
                    .addComponent(addProduct_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(openingStockQauntiy_field, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(openingStockAmount_field, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        addProduct_panelLayout.setVerticalGroup(
            addProduct_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addProduct_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iName_field)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unit_field)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openingStockQauntiy_field)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openingStockAmount_field)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(minStockAmount_field)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProduct_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addProduct_lpane.add(addProduct_panel);
        addProduct_panel.setBounds(110, 130, 620, 400);

        addClient_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), "Add Clients ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
        addClient_lpane.setOpaque(true);

        addClient_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true));
        addClient_panel.setOpaque(false);
        addClient_panel.setPreferredSize(new java.awt.Dimension(620, 400));

        clientDetail_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        clientDetail_table.setForeground(new java.awt.Color(51, 51, 51));
        clientDetail_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        clientDetail_table.setRowHeight(22);
        jScrollPane2.setViewportView(clientDetail_table);

        clientName_Field.setForeground(java.awt.Color.gray);
        clientName_Field.setText("Client Name");
        clientName_Field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                clientName_FieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                clientName_FieldFocusLost(evt);
            }
        });
        clientName_Field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                clientName_FieldKeyTyped(evt);
            }
        });

        clientAddress_field.setForeground(java.awt.Color.gray);
        clientAddress_field.setText("Address");
        clientAddress_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                clientAddress_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                clientAddress_fieldFocusLost(evt);
            }
        });

        clientContactNo_field.setForeground(java.awt.Color.gray);
        clientContactNo_field.setText("Contact No");
        clientContactNo_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                clientContactNo_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                clientContactNo_fieldFocusLost(evt);
            }
        });
        clientContactNo_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                clientContactNo_fieldKeyTyped(evt);
            }
        });

        AddClient_Btn.setForeground(new java.awt.Color(0, 51, 102));
        AddClient_Btn.setText("Add Client Name");
        AddClient_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddClient_BtnActionPerformed(evt);
            }
        });

        clientOpeningBalance_field.setForeground(java.awt.Color.gray);
        clientOpeningBalance_field.setText("Opening Balance");
        clientOpeningBalance_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientOpeningBalance_fieldActionPerformed(evt);
            }
        });
        clientOpeningBalance_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                clientOpeningBalance_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                clientOpeningBalance_fieldFocusLost(evt);
            }
        });
        clientOpeningBalance_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                clientOpeningBalance_fieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout addClient_panelLayout = new javax.swing.GroupLayout(addClient_panel);
        addClient_panel.setLayout(addClient_panelLayout);
        addClient_panelLayout.setHorizontalGroup(
            addClient_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addClient_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addClient_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addComponent(AddClient_Btn, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addComponent(clientName_Field)
                    .addComponent(clientAddress_field)
                    .addComponent(clientContactNo_field)
                    .addComponent(clientOpeningBalance_field))
                .addContainerGap())
        );
        addClient_panelLayout.setVerticalGroup(
            addClient_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addClient_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientName_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientAddress_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientContactNo_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientOpeningBalance_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AddClient_Btn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );

        addClient_lpane.add(addClient_panel);
        addClient_panel.setBounds(110, 70, 620, 400);

        addSupplier_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Add Supplier Names ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
        addSupplier_lpane.setOpaque(true);

        addSupplier_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true));
        addSupplier_panel.setMinimumSize(new java.awt.Dimension(620, 400));
        addSupplier_panel.setOpaque(false);
        addSupplier_panel.setPreferredSize(new java.awt.Dimension(620, 400));

        supplierDetail_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        supplierDetail_table.setForeground(new java.awt.Color(51, 51, 51));
        supplierDetail_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        supplierDetail_table.setRowHeight(22);
        jScrollPane1.setViewportView(supplierDetail_table);

        AddSupplier_Btn.setForeground(new java.awt.Color(0, 51, 102));
        AddSupplier_Btn.setText("Add Supplier");
        AddSupplier_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddSupplier_BtnActionPerformed(evt);
            }
        });

        supplierName_field.setForeground(java.awt.Color.gray);
        supplierName_field.setText("Supplier Name");
        supplierName_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                supplierName_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                supplierName_fieldFocusLost(evt);
            }
        });
        supplierName_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                supplierName_fieldKeyTyped(evt);
            }
        });

        supplierAddress_field.setForeground(java.awt.Color.gray);
        supplierAddress_field.setText("Address");
        supplierAddress_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                supplierAddress_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                supplierAddress_fieldFocusLost(evt);
            }
        });

        supplierContactNo_field.setForeground(java.awt.Color.gray);
        supplierContactNo_field.setText("Contact No");
        supplierContactNo_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                supplierContactNo_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                supplierContactNo_fieldFocusLost(evt);
            }
        });
        supplierContactNo_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                supplierContactNo_fieldKeyTyped(evt);
            }
        });

        supplierOpeningBalance_field.setForeground(java.awt.Color.gray);
        supplierOpeningBalance_field.setText("Opening Balance");
        supplierOpeningBalance_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierOpeningBalance_fieldActionPerformed(evt);
            }
        });
        supplierOpeningBalance_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                supplierOpeningBalance_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                supplierOpeningBalance_fieldFocusLost(evt);
            }
        });
        supplierOpeningBalance_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                supplierOpeningBalance_fieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout addSupplier_panelLayout = new javax.swing.GroupLayout(addSupplier_panel);
        addSupplier_panel.setLayout(addSupplier_panelLayout);
        addSupplier_panelLayout.setHorizontalGroup(
            addSupplier_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addSupplier_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addSupplier_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addComponent(supplierName_field, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AddSupplier_Btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(supplierAddress_field, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(supplierContactNo_field, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(supplierOpeningBalance_field, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        addSupplier_panelLayout.setVerticalGroup(
            addSupplier_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addSupplier_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(supplierName_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(supplierAddress_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(supplierContactNo_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(supplierOpeningBalance_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AddSupplier_Btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );

        addSupplier_lpane.add(addSupplier_panel);
        addSupplier_panel.setBounds(111, 100, 620, 400);

        addBankNames_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Add Bank Names ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
        addBankNames_lpane.setOpaque(true);

        addBankNames_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true));
        addBankNames_panel.setMinimumSize(new java.awt.Dimension(620, 400));
        addBankNames_panel.setOpaque(false);

        bankNames_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bankNames_table.setForeground(new java.awt.Color(51, 51, 51));
        bankNames_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        bankNames_table.setRowHeight(22);
        jScrollPane15.setViewportView(bankNames_table);

        bankName_field.setForeground(java.awt.Color.gray);
        bankName_field.setText("Name");
        bankName_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bankName_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                bankName_fieldFocusLost(evt);
            }
        });
        bankName_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                bankName_fieldKeyTyped(evt);
            }
        });

        addBank_btn.setForeground(new java.awt.Color(0, 51, 102));
        addBank_btn.setText("Add Bank Account");
        addBank_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBank_btnActionPerformed(evt);
            }
        });

        banKOpeningBalance_field.setForeground(java.awt.Color.gray);
        banKOpeningBalance_field.setText("Opening Balance");
        banKOpeningBalance_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                banKOpeningBalance_fieldActionPerformed(evt);
            }
        });
        banKOpeningBalance_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                banKOpeningBalance_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                banKOpeningBalance_fieldFocusLost(evt);
            }
        });
        banKOpeningBalance_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                banKOpeningBalance_fieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout addBankNames_panelLayout = new javax.swing.GroupLayout(addBankNames_panel);
        addBankNames_panel.setLayout(addBankNames_panelLayout);
        addBankNames_panelLayout.setHorizontalGroup(
            addBankNames_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBankNames_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addBankNames_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addComponent(bankName_field)
                    .addComponent(addBank_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(banKOpeningBalance_field))
                .addContainerGap())
        );
        addBankNames_panelLayout.setVerticalGroup(
            addBankNames_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addBankNames_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bankName_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(banKOpeningBalance_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addBank_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        addBankNames_lpane.add(addBankNames_panel);
        addBankNames_panel.setBounds(110, 130, 620, 426);

        addExpenseName_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), "Add Expense Names ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
        addExpenseName_lpane.setOpaque(true);

        addExpenseName_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true));
        addExpenseName_panel.setMinimumSize(new java.awt.Dimension(620, 400));
        addExpenseName_panel.setOpaque(false);

        expenseNames_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        expenseNames_table.setForeground(new java.awt.Color(51, 51, 51));
        expenseNames_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        expenseNames_table.setRowHeight(22);
        jScrollPane16.setViewportView(expenseNames_table);

        expenseName_field.setForeground(java.awt.Color.gray);
        expenseName_field.setText("Name");
        expenseName_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseName_fieldActionPerformed(evt);
            }
        });
        expenseName_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                expenseName_fieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                expenseName_fieldFocusLost(evt);
            }
        });
        expenseName_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                expenseName_fieldKeyTyped(evt);
            }
        });

        addExpenseName_btn.setForeground(new java.awt.Color(0, 51, 102));
        addExpenseName_btn.setText("Add Expense Name");
        addExpenseName_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExpenseName_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addExpenseName_panelLayout = new javax.swing.GroupLayout(addExpenseName_panel);
        addExpenseName_panel.setLayout(addExpenseName_panelLayout);
        addExpenseName_panelLayout.setHorizontalGroup(
            addExpenseName_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addExpenseName_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addExpenseName_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                    .addComponent(expenseName_field)
                    .addComponent(addExpenseName_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        addExpenseName_panelLayout.setVerticalGroup(
            addExpenseName_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addExpenseName_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(expenseName_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addExpenseName_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addContainerGap())
        );

        addExpenseName_lpane.add(addExpenseName_panel);
        addExpenseName_panel.setBounds(110, 130, 620, 400);

        purchaseOrder_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Purchase Order ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
        purchaseOrder_lpane.setOpaque(true);

        btns_purchaseOrder.setOpaque(false);
        btns_purchaseOrder.setLayout(new java.awt.GridBagLayout());

        previousOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/prev-icon.png"))); // NOI18N
        previousOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousOrderActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        btns_purchaseOrder.add(previousOrder, gridBagConstraints);

        nextOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Next-icon.png"))); // NOI18N
        nextOrder.setIconTextGap(-55);
        nextOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextOrderActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        btns_purchaseOrder.add(nextOrder, gridBagConstraints);

        newOrder.setText("New");
        newOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOrderActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        btns_purchaseOrder.add(newOrder, gridBagConstraints);

        makeOrder.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        makeOrder.setForeground(new java.awt.Color(0, 51, 102));
        makeOrder.setText("Make Purchase Order");
        makeOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeOrderActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        btns_purchaseOrder.add(makeOrder, gridBagConstraints);

        purchaseOrder_lpane.add(btns_purchaseOrder);
        btns_purchaseOrder.setBounds(162, 474, 346, 40);

        purchaseOrder_panel.setBackground(new java.awt.Color(244, 244, 255));
        purchaseOrder_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
        purchaseOrder_panel.setForeground(new java.awt.Color(0, 51, 102));
        purchaseOrder_panel.setOpaque(false);
        purchaseOrder_panel.setPreferredSize(new java.awt.Dimension(700, 340));

        Date5.setText("Date :");

        ENo3.setText("Order No.");

        purchaseOrder_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        purchaseOrder_table.setForeground(new java.awt.Color(102, 102, 102));
        purchaseOrder_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        purchaseOrder_table.setToolTipText("Purchase Order Table Insert Data To Save It");
        purchaseOrder_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        purchaseOrder_table.setCellSelectionEnabled(true);
        purchaseOrder_table.setGridColor(new java.awt.Color(153, 153, 153));
        purchaseOrder_table.setRowHeight(20);
        purchaseOrder_table.getTableHeader().setReorderingAllowed(false);
        purchaseOrder_table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                purchaseOrder_tablePropertyChange(evt);
            }
        });
        jScrollPane11.setViewportView(purchaseOrder_table);
        purchaseOrder_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Client1.setText("Client: ");

        orderNo.setEditable(false);

        dateField_O.setCalendarPreferredSize(new java.awt.Dimension(400, 300));

        javax.swing.GroupLayout purchaseOrder_panelLayout = new javax.swing.GroupLayout(purchaseOrder_panel);
        purchaseOrder_panel.setLayout(purchaseOrder_panelLayout);
        purchaseOrder_panelLayout.setHorizontalGroup(
            purchaseOrder_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, purchaseOrder_panelLayout.createSequentialGroup()
                .addGroup(purchaseOrder_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(purchaseOrder_panelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(orderStatus_label, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(purchaseOrder_panelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(purchaseOrder_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(purchaseOrder_panelLayout.createSequentialGroup()
                                .addComponent(ENo3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(orderNo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Date5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateField_O, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Client1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clientBox_PO, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE))))
                .addGap(15, 15, 15))
        );
        purchaseOrder_panelLayout.setVerticalGroup(
            purchaseOrder_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(purchaseOrder_panelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(purchaseOrder_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(purchaseOrder_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ENo3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(orderNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Date5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(purchaseOrder_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Client1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clientBox_PO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateField_O, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(orderStatus_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        purchaseOrder_lpane.add(purchaseOrder_panel);
        purchaseOrder_panel.setBounds(162, 122, 700, 340);

        pTransaction_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Purchase Transaction ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
        pTransaction_lpane.setOpaque(true);

        pTransaction_panel.setBackground(new java.awt.Color(244, 244, 255));
        pTransaction_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
        pTransaction_panel.setOpaque(false);
        pTransaction_panel.setPreferredSize(new java.awt.Dimension(700, 340));

        supplierBox_pT.setMaximumRowCount(50);

        sellerNameLabel_PT.setText("Supplier : ");

        Date3.setText("Date :");

        pTransaction_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pTransaction_table.setForeground(new java.awt.Color(102, 102, 102));
        pTransaction_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pTransaction_table.setToolTipText("Purchase Table ");
        pTransaction_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        pTransaction_table.setCellSelectionEnabled(true);
        pTransaction_table.setGridColor(new java.awt.Color(153, 153, 153));
        pTransaction_table.setRowHeight(20);
        pTransaction_table.getTableHeader().setReorderingAllowed(false);
        pTransaction_table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                pTransaction_tablePropertyChange(evt);
            }
        });
        pT_scrollPane.setViewportView(pTransaction_table);
        pTransaction_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        tAmount_field_PT.setEditable(false);
        tAmount_field_PT.setText("0.0");

        jLabel16.setText("No.");

        transactionNo.setEditable(false);

        tAmount_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tAmount_label.setText("Total Amount : ");

        paymentStatus_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        dateField_PT.setCalendarPreferredSize(new java.awt.Dimension(400, 300));
        dateField_PT.setNothingAllowed(false);

        Cash2.setBackground(new java.awt.Color(255, 255, 255));
        mode_PT.add(Cash2);
        Cash2.setSelected(true);
        Cash2.setText("Cash");
        Cash2.setOpaque(false);
        Cash2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cash2ActionPerformed(evt);
            }
        });

        Cheque2.setBackground(new java.awt.Color(255, 255, 255));
        mode_PT.add(Cheque2);
        Cheque2.setText("Cheque");
        Cheque2.setOpaque(false);
        Cheque2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cheque2ActionPerformed(evt);
            }
        });

        Credit2.setBackground(new java.awt.Color(255, 255, 255));
        mode_PT.add(Credit2);
        Credit2.setText("Credit");
        Credit2.setOpaque(false);
        Credit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Credit2ActionPerformed(evt);
            }
        });

        Modes2.setText("Payment Mode :");

        bankNameBox_PT.setEnabled(false);

        javax.swing.GroupLayout pTransaction_panelLayout = new javax.swing.GroupLayout(pTransaction_panel);
        pTransaction_panel.setLayout(pTransaction_panelLayout);
        pTransaction_panelLayout.setHorizontalGroup(
            pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTransaction_panelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pTransaction_panelLayout.createSequentialGroup()
                        .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pTransaction_panelLayout.createSequentialGroup()
                                .addComponent(Modes2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(Cash2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Cheque2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(Credit2))
                            .addComponent(bankNameBox_PT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pTransaction_panelLayout.createSequentialGroup()
                                .addComponent(tAmount_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10)
                                .addComponent(tAmount_field_PT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(paymentStatus_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(pT_scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pTransaction_panelLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transactionNo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Date3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateField_PT, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sellerNameLabel_PT, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supplierBox_pT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        pTransaction_panelLayout.setVerticalGroup(
            pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTransaction_panelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Date3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(transactionNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(supplierBox_pT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sellerNameLabel_PT, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateField_PT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTransaction_panelLayout.createSequentialGroup()
                        .addComponent(pT_scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tAmount_field_PT)
                            .addComponent(tAmount_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Modes2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Cash2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Cheque2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Credit2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pTransaction_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paymentStatus_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bankNameBox_PT, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pTransaction_lpane.add(pTransaction_panel);
        pTransaction_panel.setBounds(190, 80, 700, 340);

        btns_pTransaction.setOpaque(false);
        btns_pTransaction.setLayout(new java.awt.GridBagLayout());

        makeTransaction.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        makeTransaction.setForeground(new java.awt.Color(0, 51, 102));
        makeTransaction.setText("Make Transaction");
        makeTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeTransactionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        btns_pTransaction.add(makeTransaction, gridBagConstraints);

        newTransaction.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        newTransaction.setText("New");
        newTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTransactionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        btns_pTransaction.add(newTransaction, gridBagConstraints);

        nextTransaction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Next-icon.png"))); // NOI18N
        nextTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextTransactionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        btns_pTransaction.add(nextTransaction, gridBagConstraints);

        prevTransaction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/prev-icon.png"))); // NOI18N
        prevTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevTransactionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        btns_pTransaction.add(prevTransaction, gridBagConstraints);

        pTransaction_lpane.add(btns_pTransaction);
        btns_pTransaction.setBounds(190, 443, 320, 33);

        dChalan_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true), " Delivery Chalan ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N

        dChalan_panel.setBackground(new java.awt.Color(244, 244, 255));
        dChalan_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
        dChalan_panel.setForeground(new java.awt.Color(0, 51, 102));
        dChalan_panel.setPreferredSize(new java.awt.Dimension(700, 340));

        Date2.setText("Date :");

        ENo2.setText("No.");

        dChalan_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dChalan_table.setForeground(new java.awt.Color(102, 102, 102));
        dChalan_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        dChalan_table.setToolTipText("Delivery Chalan Table Insert Data To Save It");
        dChalan_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        dChalan_table.setCellSelectionEnabled(true);
        dChalan_table.setGridColor(new java.awt.Color(153, 153, 153));
        dChalan_table.setRowHeight(20);
        dChalan_table.getTableHeader().setReorderingAllowed(false);
        dChalan_table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dChalan_tablePropertyChange(evt);
            }
        });
        dChalan_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dChalan_tableKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(dChalan_table);
        dChalan_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        Client.setText("Client: ");

        chalanNo.setEditable(false);

        dateField_Dc.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dateField_Dc.setCalendarPreferredSize(new java.awt.Dimension(400, 300));

    orderNo_box.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0" }));
    orderNo_box.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            orderNo_boxActionPerformed(evt);
        }
    });

    orderNoLable.setText("Order No.");

    javax.swing.GroupLayout dChalan_panelLayout = new javax.swing.GroupLayout(dChalan_panel);
    dChalan_panel.setLayout(dChalan_panelLayout);
    dChalan_panelLayout.setHorizontalGroup(
        dChalan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dChalan_panelLayout.createSequentialGroup()
            .addGroup(dChalan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(dChalan_panelLayout.createSequentialGroup()
                    .addContainerGap(15, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dChalan_panelLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(orderNoLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(orderNo_box, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(ENo2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(chalanNo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(Date2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateField_Dc, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(Client, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(clientsBox_Dc, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(15, 15, 15))
    );
    dChalan_panelLayout.setVerticalGroup(
        dChalan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(dChalan_panelLayout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addGroup(dChalan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dChalan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dChalan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(orderNoLable, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(orderNo_box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ENo2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chalanNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Date2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dChalan_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Client, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clientsBox_Dc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(dateField_Dc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
            .addGap(15, 15, 15))
    );

    dChalan_lpane.add(dChalan_panel);
    dChalan_panel.setBounds(100, 100, 700, 340);

    btns_dChalan.setOpaque(false);
    btns_dChalan.setLayout(new java.awt.GridBagLayout());

    previousChalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/prev-icon.png"))); // NOI18N
    previousChalan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            previousChalanActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.ipadx = 8;
    gridBagConstraints.ipady = 1;
    btns_dChalan.add(previousChalan, gridBagConstraints);

    nextChalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Next-icon.png"))); // NOI18N
    nextChalan.setIconTextGap(-55);
    nextChalan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            nextChalanActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.ipadx = 8;
    gridBagConstraints.ipady = 1;
    btns_dChalan.add(nextChalan, gridBagConstraints);

    newChalan.setText("New");
    newChalan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            newChalanActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
    btns_dChalan.add(newChalan, gridBagConstraints);

    makeChalan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    makeChalan.setForeground(new java.awt.Color(0, 51, 102));
    makeChalan.setText("Make Chalan");
    makeChalan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            makeChalanActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    btns_dChalan.add(makeChalan, gridBagConstraints);

    printChalan.setText("Print");
    printChalan.setEnabled(false);
    printChalan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            printChalanActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 5;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    btns_dChalan.add(printChalan, gridBagConstraints);

    editChalan.setText("Delete");
    editChalan.setEnabled(false);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    btns_dChalan.add(editChalan, gridBagConstraints);

    dChalan_lpane.add(btns_dChalan);
    btns_dChalan.setBounds(100, 460, 400, 40);

    salesInvoice_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Sales Invoice ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    salesInvoice_lpane.setOpaque(true);

    salesInvoice_panel.setBackground(new java.awt.Color(244, 244, 255));
    salesInvoice_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
    salesInvoice_panel.setForeground(new java.awt.Color(0, 51, 102));
    salesInvoice_panel.setOpaque(false);
    salesInvoice_panel.setPreferredSize(new java.awt.Dimension(700, 340));

    paymentLeft.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    paymentLeft.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    paymentLeft.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    paymentLeft.setInheritsPopupMenu(false);

    tAmountLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    tAmountLabel2.setText("Total Amount :");

    Date6.setText("Date :");

    ENo4.setText("Invoice No");

    Client2.setText("Client: ");

    invoiceNo.setEditable(false);

    clientsBox_SI.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            clientsBox_SIItemStateChanged(evt);
        }
    });

    tAmount_field_SI.setEditable(false);
    tAmount_field_SI.setText("0.0");

    dateField_SI.setCalendarPreferredSize(new java.awt.Dimension(400, 300));

    addChalan.setText("Add Chalans");
    addChalan.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addChalanActionPerformed(evt);
        }
    });

    modeLabel.setText("Payment Mode :");

    Credit3.setBackground(new java.awt.Color(255, 255, 255));
    mode_SI.add(Credit3);
    Credit3.setText("Credit");
    Credit3.setOpaque(false);
    Credit3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            Credit3ActionPerformed(evt);
        }
    });

    Cheque3.setBackground(new java.awt.Color(255, 255, 255));
    mode_SI.add(Cheque3);
    Cheque3.setText("Cheque");
    Cheque3.setOpaque(false);
    Cheque3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            Cheque3ActionPerformed(evt);
        }
    });

    Cash3.setBackground(new java.awt.Color(255, 255, 255));
    mode_SI.add(Cash3);
    Cash3.setSelected(true);
    Cash3.setText("Cash");
    Cash3.setOpaque(false);
    Cash3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            Cash3ActionPerformed(evt);
        }
    });

    bankNameBox_SI.setEnabled(false);

    salesInvoice_table.setForeground(new java.awt.Color(51, 51, 51));
    salesInvoice_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Product Name", "Unit", "Quantity", "Rate", "Amount"
        }
    ));
    salesInvoice_table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            salesInvoice_tablePropertyChange(evt);
        }
    });
    jScrollPane12.setViewportView(salesInvoice_table);

    javax.swing.GroupLayout salesInvoice_panelLayout = new javax.swing.GroupLayout(salesInvoice_panel);
    salesInvoice_panel.setLayout(salesInvoice_panelLayout);
    salesInvoice_panelLayout.setHorizontalGroup(
        salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salesInvoice_panelLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addGroup(salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jScrollPane12)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, salesInvoice_panelLayout.createSequentialGroup()
                    .addGroup(salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(salesInvoice_panelLayout.createSequentialGroup()
                            .addComponent(modeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(6, 6, 6)
                            .addComponent(Cash3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Cheque3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Credit3))
                        .addComponent(bankNameBox_SI, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salesInvoice_panelLayout.createSequentialGroup()
                            .addGap(63, 63, 63)
                            .addComponent(tAmountLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tAmount_field_SI, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(salesInvoice_panelLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                            .addComponent(paymentLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(salesInvoice_panelLayout.createSequentialGroup()
                    .addComponent(ENo4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(invoiceNo)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(Date6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateField_SI, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(Client2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(clientsBox_SI, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(addChalan)))
            .addGap(15, 15, 15))
    );
    salesInvoice_panelLayout.setVerticalGroup(
        salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(salesInvoice_panelLayout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addGroup(salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ENo4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invoiceNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(dateField_SI, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientsBox_SI)
                    .addComponent(addChalan, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(Client2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addGroup(salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(modeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cash3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cheque3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Credit3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(tAmount_field_SI)
                .addComponent(tAmountLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(salesInvoice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(salesInvoice_panelLayout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(bankNameBox_SI, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(salesInvoice_panelLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(paymentLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(47, Short.MAX_VALUE))
    );

    salesInvoice_lpane.add(salesInvoice_panel);
    salesInvoice_panel.setBounds(150, 100, 700, 340);

    btns_salesInvoice.setOpaque(false);
    btns_salesInvoice.setLayout(new java.awt.GridBagLayout());

    previousInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/prev-icon.png"))); // NOI18N
    previousInvoice.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            previousInvoiceActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 8;
    gridBagConstraints.ipady = 1;
    btns_salesInvoice.add(previousInvoice, gridBagConstraints);

    nextInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Next-icon.png"))); // NOI18N
    nextInvoice.setEnabled(false);
    nextInvoice.setIconTextGap(-55);
    nextInvoice.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            nextInvoiceActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 8;
    gridBagConstraints.ipady = 1;
    btns_salesInvoice.add(nextInvoice, gridBagConstraints);

    newInvoice.setText("New");
    newInvoice.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            newInvoiceActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    btns_salesInvoice.add(newInvoice, gridBagConstraints);

    makeInvoice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    makeInvoice.setForeground(new java.awt.Color(0, 51, 102));
    makeInvoice.setText("Make Invoice");
    makeInvoice.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            makeInvoiceActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    btns_salesInvoice.add(makeInvoice, gridBagConstraints);

    printInvoice.setText("Print");
    printInvoice.setEnabled(false);
    printInvoice.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            printInvoiceActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    btns_salesInvoice.add(printInvoice, gridBagConstraints);

    salesInvoice_lpane.add(btns_salesInvoice);
    btns_salesInvoice.setBounds(150, 446, 340, 34);

    expense_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Expense ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    expense_lpane.setOpaque(true);

    expense_panel.setBackground(new java.awt.Color(244, 244, 255));
    expense_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
    expense_panel.setOpaque(false);
    expense_panel.setPreferredSize(new java.awt.Dimension(700, 340));

    tAmountLabel1.setText("Total Amount :");

    Date4.setText("Date :");

    expense_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    expense_table.setForeground(new java.awt.Color(51, 51, 51));
    expense_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
        },
        new String [] {
            "Description", "Quantity", "Rate", "Amount"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
    });
    expense_table.setToolTipText("Enter Your Expense Details");
    expense_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    expense_table.setCellSelectionEnabled(true);
    expense_table.setGridColor(new java.awt.Color(0, 0, 0));
    expense_table.setRowHeight(22);
    expense_table.getTableHeader().setReorderingAllowed(false);
    expense_table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            expense_tablePropertyChange(evt);
        }
    });
    expense_table.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            expense_tableKeyPressed(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            expense_tableKeyTyped(evt);
        }
    });
    jScrollPane7.setViewportView(expense_table);
    expense_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    expenseModes.add(hExpense);
    hExpense.setSelected(true);
    hExpense.setText("Personal Expnses");
    hExpense.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            hExpenseActionPerformed(evt);
        }
    });

    expenseModes.add(oExpense);
    oExpense.setText("Company Expenses");
    oExpense.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            oExpenseActionPerformed(evt);
        }
    });

    typeLabel.setText("Expense :");

    dateField_Ex.setCalendarPreferredSize(new java.awt.Dimension(400, 300));

    Cash4.setBackground(new java.awt.Color(255, 255, 255));
    mode_E.add(Cash4);
    Cash4.setSelected(true);
    Cash4.setText("Cash");
    Cash4.setOpaque(false);
    Cash4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            Cash4ActionPerformed(evt);
        }
    });

    Cheque4.setBackground(new java.awt.Color(255, 255, 255));
    mode_E.add(Cheque4);
    Cheque4.setText("Cheque");
    Cheque4.setOpaque(false);
    Cheque4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            Cheque4ActionPerformed(evt);
        }
    });

    Credit4.setBackground(new java.awt.Color(255, 255, 255));
    mode_E.add(Credit4);
    Credit4.setText("Credit");
    Credit4.setOpaque(false);
    Credit4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            Credit4ActionPerformed(evt);
        }
    });

    bankNameBox_E.setEnabled(false);

    modeLabel1.setText("Payment Mode :");

    javax.swing.GroupLayout expense_panelLayout = new javax.swing.GroupLayout(expense_panel);
    expense_panel.setLayout(expense_panelLayout);
    expense_panelLayout.setHorizontalGroup(
        expense_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(expense_panelLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addGroup(expense_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expense_panelLayout.createSequentialGroup()
                    .addComponent(Date4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateField_Ex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(hExpense)
                    .addGap(10, 10, 10)
                    .addComponent(oExpense))
                .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expense_panelLayout.createSequentialGroup()
                    .addComponent(modeLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(Cash4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(Credit4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(Cheque4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tAmountLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tAmount_fieldE, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(expense_panelLayout.createSequentialGroup()
                    .addComponent(bankNameBox_E, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGap(15, 15, 15))
    );
    expense_panelLayout.setVerticalGroup(
        expense_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(expense_panelLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addGroup(expense_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Date4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(dateField_Ex, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(expense_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hExpense)
                    .addComponent(oExpense)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addGroup(expense_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(expense_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tAmountLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tAmount_fieldE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(expense_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modeLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cash4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Credit4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cheque4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(bankNameBox_E, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    expense_lpane.add(expense_panel);
    expense_panel.setBounds(160, 90, 700, 340);

    Btns_expense.setOpaque(false);
    Btns_expense.setLayout(new java.awt.GridBagLayout());

    addExpense.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    addExpense.setForeground(new java.awt.Color(0, 51, 102));
    addExpense.setText("Add Expense");
    addExpense.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addExpenseActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    Btns_expense.add(addExpense, gridBagConstraints);

    companyExpenseBtn.setText("View Company Expense");
    companyExpenseBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            companyExpenseBtnActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    Btns_expense.add(companyExpenseBtn, gridBagConstraints);

    personalExpenseBtn.setText("View Personal Expense");
    personalExpenseBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            personalExpenseBtnActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    Btns_expense.add(personalExpenseBtn, gridBagConstraints);

    newExpense.setText("New Expense");
    newExpense.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            newExpenseActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    Btns_expense.add(newExpense, gridBagConstraints);

    expense_lpane.add(Btns_expense);
    Btns_expense.setBounds(150, 470, 492, 30);

    paymentPaid_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Amount Paid ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    paymentPaid_lpane.setOpaque(true);

    paymentPaid_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
    paymentPaid_panel.setOpaque(false);
    paymentPaid_panel.setPreferredSize(new java.awt.Dimension(770, 300));

    paidVoucher.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Amount Paid Recipt ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 51, 102))); // NOI18N
    paidVoucher.setOpaque(false);

    amountPaid_field.setText("0.0");
    amountPaid_field.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            amountPaid_fieldFocusGained(evt);
        }
    });
    amountPaid_field.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            amountPaid_fieldKeyTyped(evt);
        }
    });

    jLabel19.setText("Paid To: ");

    jLabel20.setText("Amount: ");

    mode_PV.add(cash3);
    cash3.setSelected(true);
    cash3.setText("Cash");
    cash3.setOpaque(false);
    cash3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cash3ActionPerformed(evt);
        }
    });

    mode_PV.add(cheque3);
    cheque3.setText("Cheque");
    cheque3.setOpaque(false);
    cheque3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cheque3ActionPerformed(evt);
        }
    });

    jLabel21.setText("Type: ");

    jLabel22.setText("Voucher No.");

    jLabel23.setText("Bank Name");

    bankNameBox_pV.setEnabled(false);

    paidVoucherNo.setEditable(false);
    paidVoucherNo.setText("1");

    makePaidVoucher.setText("Make Voucher");
    makePaidVoucher.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            makePaidVoucherActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout paidVoucherLayout = new javax.swing.GroupLayout(paidVoucher);
    paidVoucher.setLayout(paidVoucherLayout);
    paidVoucherLayout.setHorizontalGroup(
        paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(paidVoucherLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(makePaidVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paidVoucherLayout.createSequentialGroup()
                    .addGroup(paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(supplierBox_pV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(amountPaid_field)
                        .addGroup(paidVoucherLayout.createSequentialGroup()
                            .addComponent(cash3)
                            .addGap(18, 18, 18)
                            .addComponent(cheque3)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(paidVoucherNo, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGroup(paidVoucherLayout.createSequentialGroup()
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bankNameBox_pV, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    paidVoucherLayout.setVerticalGroup(
        paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(paidVoucherLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(paidVoucherNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(supplierBox_pV, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(amountPaid_field)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cash3)
                .addComponent(cheque3)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(paidVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(bankNameBox_pV, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(makePaidVoucher)
            .addContainerGap())
    );

    paidVoucher_history.setBackground(new java.awt.Color(244, 244, 255));
    paidVoucher_history.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Amount Paid Vouchers ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 51, 102))); // NOI18N
    paidVoucher_history.setOpaque(false);

    paymentPaid_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    paymentPaid_table.setForeground(new java.awt.Color(51, 51, 51));
    paymentPaid_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {}
        },
        new String [] {

        }
    ));
    paymentPaid_table.setToolTipText("Payment Paid Vouchers");
    paymentPaid_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    paymentPaid_table.setCellSelectionEnabled(true);
    paymentPaid_table.setGridColor(new java.awt.Color(0, 0, 0));
    paymentPaid_table.setRowHeight(22);
    paymentPaid_table.getTableHeader().setReorderingAllowed(false);
    jScrollPane9.setViewportView(paymentPaid_table);
    paymentPaid_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    javax.swing.GroupLayout paidVoucher_historyLayout = new javax.swing.GroupLayout(paidVoucher_history);
    paidVoucher_history.setLayout(paidVoucher_historyLayout);
    paidVoucher_historyLayout.setHorizontalGroup(
        paidVoucher_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(paidVoucher_historyLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane9)
            .addContainerGap())
    );
    paidVoucher_historyLayout.setVerticalGroup(
        paidVoucher_historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(paidVoucher_historyLayout.createSequentialGroup()
            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addContainerGap())
    );

    javax.swing.GroupLayout paymentPaid_panelLayout = new javax.swing.GroupLayout(paymentPaid_panel);
    paymentPaid_panel.setLayout(paymentPaid_panelLayout);
    paymentPaid_panelLayout.setHorizontalGroup(
        paymentPaid_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(paymentPaid_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(paidVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(paidVoucher_history, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    paymentPaid_panelLayout.setVerticalGroup(
        paymentPaid_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(paymentPaid_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(paymentPaid_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(paidVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(paidVoucher_history, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );

    paymentPaid_lpane.add(paymentPaid_panel);
    paymentPaid_panel.setBounds(50, 140, 770, 300);

    paymentRecieved_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Amount Recieved ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    paymentRecieved_lpane.setOpaque(true);

    paymentReceived_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
    paymentReceived_panel.setOpaque(false);
    paymentReceived_panel.setPreferredSize(new java.awt.Dimension(770, 300));

    rcvdHistory_panel.setBackground(new java.awt.Color(244, 244, 255));
    rcvdHistory_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Amount Recieved Vouchers ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 51, 102))); // NOI18N
    rcvdHistory_panel.setOpaque(false);

    paymentRcvd_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    paymentRcvd_table.setForeground(new java.awt.Color(51, 51, 51));
    paymentRcvd_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {}
        },
        new String [] {

        }
    ));
    paymentRcvd_table.setToolTipText("You Are Watching Payment Recieved Vouchers");
    paymentRcvd_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    paymentRcvd_table.setCellSelectionEnabled(true);
    paymentRcvd_table.setGridColor(new java.awt.Color(0, 0, 0));
    paymentRcvd_table.setRowHeight(22);
    paymentRcvd_table.getTableHeader().setReorderingAllowed(false);
    jScrollPane10.setViewportView(paymentRcvd_table);
    paymentRcvd_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    if (paymentRcvd_table.getColumnModel().getColumnCount() > 0) {
        paymentRcvd_table.getColumnModel().getColumn(0).setMinWidth(50);
        paymentRcvd_table.getColumnModel().getColumn(0).setPreferredWidth(210);
        paymentRcvd_table.getColumnModel().getColumn(0).setMaxWidth(50);
        paymentRcvd_table.getColumnModel().getColumn(1).setResizable(false);
        paymentRcvd_table.getColumnModel().getColumn(2).setResizable(false);
        paymentRcvd_table.getColumnModel().getColumn(3).setResizable(false);
        paymentRcvd_table.getColumnModel().getColumn(4).setResizable(false);
        paymentRcvd_table.getColumnModel().getColumn(4).setPreferredWidth(110);
    }

    javax.swing.GroupLayout rcvdHistory_panelLayout = new javax.swing.GroupLayout(rcvdHistory_panel);
    rcvdHistory_panel.setLayout(rcvdHistory_panelLayout);
    rcvdHistory_panelLayout.setHorizontalGroup(
        rcvdHistory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rcvdHistory_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane10)
            .addContainerGap())
    );
    rcvdHistory_panelLayout.setVerticalGroup(
        rcvdHistory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rcvdHistory_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addContainerGap())
    );

    rcvdVoucher.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " New Voucher ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 51, 102))); // NOI18N
    rcvdVoucher.setOpaque(false);

    makeRecivedVoucher.setText("Make Voucher");
    makeRecivedVoucher.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            makeRecivedVoucherActionPerformed(evt);
        }
    });

    amountRecieved_field.setText("0.0");
    amountRecieved_field.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            amountRecieved_fieldFocusGained(evt);
        }
    });
    amountRecieved_field.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            amountRecieved_fieldKeyTyped(evt);
        }
    });

    jLabel5.setText("Recieved From: ");

    jLabel10.setText("Amount: ");

    mode_RV.add(cash4);
    cash4.setSelected(true);
    cash4.setText("Cash");
    cash4.setOpaque(false);
    cash4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cash4ActionPerformed(evt);
        }
    });

    mode_RV.add(cheque4);
    cheque4.setText("Cheque");
    cheque4.setOpaque(false);
    cheque4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cheque4ActionPerformed(evt);
        }
    });

    jLabel11.setText("Type: ");

    jLabel12.setText("Voucher No. ");

    jLabel13.setText("Bank Name: ");

    bankNameBox_rV.setEnabled(false);

    rcvdVNo.setEditable(false);
    rcvdVNo.setText("1");

    javax.swing.GroupLayout rcvdVoucherLayout = new javax.swing.GroupLayout(rcvdVoucher);
    rcvdVoucher.setLayout(rcvdVoucherLayout);
    rcvdVoucherLayout.setHorizontalGroup(
        rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(rcvdVoucherLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(rcvdVoucherLayout.createSequentialGroup()
                    .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(rcvdVoucherLayout.createSequentialGroup()
                            .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(bankNameBox_rV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rcvdVoucherLayout.createSequentialGroup()
                                    .addComponent(cash4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cheque4))
                                .addComponent(amountRecieved_field, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(clientsBox_rV, javax.swing.GroupLayout.Alignment.LEADING, 0, 141, Short.MAX_VALUE)
                                .addComponent(rcvdVNo, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jSeparator3))
                    .addGap(7, 7, 7))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rcvdVoucherLayout.createSequentialGroup()
                    .addComponent(makeRecivedVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addContainerGap())))
    );
    rcvdVoucherLayout.setVerticalGroup(
        rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rcvdVoucherLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rcvdVNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(clientsBox_rV, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(amountRecieved_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cash4)
                .addComponent(cheque4))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(rcvdVoucherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(bankNameBox_rV, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, Short.MAX_VALUE)
            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(makeRecivedVoucher)
            .addContainerGap())
    );

    javax.swing.GroupLayout paymentReceived_panelLayout = new javax.swing.GroupLayout(paymentReceived_panel);
    paymentReceived_panel.setLayout(paymentReceived_panelLayout);
    paymentReceived_panelLayout.setHorizontalGroup(
        paymentReceived_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(paymentReceived_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(rcvdVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(rcvdHistory_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    paymentReceived_panelLayout.setVerticalGroup(
        paymentReceived_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paymentReceived_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(paymentReceived_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(rcvdHistory_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rcvdVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );

    paymentRecieved_lpane.add(paymentReceived_panel);
    paymentReceived_panel.setBounds(57, 175, 770, 300);

    cashBook_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Cash Book ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    cashBook_lpane.setOpaque(true);

    cashBook_panel.setBackground(new java.awt.Color(244, 244, 255));
    cashBook_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
    cashBook_panel.setOpaque(false);
    cashBook_panel.setPreferredSize(new java.awt.Dimension(700, 340));

    cashBook_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    cashBook_table.setForeground(new java.awt.Color(51, 51, 51));
    cashBook_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        },
        new String [] {

        }
    ));
    cashBook_table.setToolTipText("Cash Book");
    cashBook_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    cashBook_table.setCellSelectionEnabled(true);
    cashBook_table.setGridColor(new java.awt.Color(0, 0, 0));
    cashBook_table.setRowHeight(22);
    cashBook_table.getTableHeader().setReorderingAllowed(false);
    cashBook_table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            cashBook_tableMouseClicked(evt);
        }
    });
    jScrollPane17.setViewportView(cashBook_table);
    cashBook_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    javax.swing.GroupLayout cashBook_panelLayout = new javax.swing.GroupLayout(cashBook_panel);
    cashBook_panel.setLayout(cashBook_panelLayout);
    cashBook_panelLayout.setHorizontalGroup(
        cashBook_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(cashBook_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
            .addContainerGap())
    );
    cashBook_panelLayout.setVerticalGroup(
        cashBook_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cashBook_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
            .addContainerGap())
    );

    cashBook_lpane.add(cashBook_panel);
    cashBook_panel.setBounds(200, 150, 700, 340);

    noteLabelCashBook.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    cashBook_lpane.add(noteLabelCashBook);
    noteLabelCashBook.setBounds(200, 500, 700, 20);

    bankBook_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Cheque Book ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    bankBook_lpane.setOpaque(true);

    chequeBook_panel.setBackground(new java.awt.Color(244, 244, 255));
    chequeBook_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
    chequeBook_panel.setOpaque(false);
    chequeBook_panel.setPreferredSize(new java.awt.Dimension(700, 340));

    chequeBook_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    chequeBook_table.setForeground(new java.awt.Color(51, 51, 51));
    chequeBook_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        },
        new String [] {

        }
    ));
    chequeBook_table.setToolTipText("Cheque Book");
    chequeBook_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    chequeBook_table.setCellSelectionEnabled(true);
    chequeBook_table.setGridColor(new java.awt.Color(0, 0, 0));
    chequeBook_table.setRowHeight(22);
    chequeBook_table.getTableHeader().setReorderingAllowed(false);
    chequeBook_table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            chequeBook_tableMouseClicked(evt);
        }
    });
    jScrollPane18.setViewportView(chequeBook_table);
    chequeBook_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    javax.swing.GroupLayout chequeBook_panelLayout = new javax.swing.GroupLayout(chequeBook_panel);
    chequeBook_panel.setLayout(chequeBook_panelLayout);
    chequeBook_panelLayout.setHorizontalGroup(
        chequeBook_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(chequeBook_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
            .addContainerGap())
    );
    chequeBook_panelLayout.setVerticalGroup(
        chequeBook_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chequeBook_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
            .addContainerGap())
    );

    bankBook_lpane.add(chequeBook_panel);
    chequeBook_panel.setBounds(200, 150, 700, 340);

    noteLabelchequeBook.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    bankBook_lpane.add(noteLabelchequeBook);
    noteLabelchequeBook.setBounds(200, 500, 700, 20);

    stockReport_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Stock Report ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    stockReport_lpane.setOpaque(true);

    stockReport_panel.setBackground(new java.awt.Color(244, 244, 255));
    stockReport_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true));
    stockReport_panel.setOpaque(false);
    stockReport_panel.setPreferredSize(new java.awt.Dimension(700, 340));

    stockReport_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    stockReport_table.setForeground(new java.awt.Color(51, 51, 51));
    stockReport_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {}
        },
        new String [] {

        }
    ));
    stockReport_table.setToolTipText("This is Your Stock Report");
    stockReport_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    stockReport_table.setCellSelectionEnabled(true);
    stockReport_table.setGridColor(new java.awt.Color(0, 0, 0));
    stockReport_table.setRowHeight(22);
    stockReport_table.getTableHeader().setReorderingAllowed(false);
    stockReport_table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            stockReport_tableMouseClicked(evt);
        }
    });
    jScrollPane5.setViewportView(stockReport_table);
    stockReport_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    totalStockAmount_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    totalStockAmount_label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    javax.swing.GroupLayout stockReport_panelLayout = new javax.swing.GroupLayout(stockReport_panel);
    stockReport_panel.setLayout(stockReport_panelLayout);
    stockReport_panelLayout.setHorizontalGroup(
        stockReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(stockReport_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(stockReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addComponent(totalStockAmount_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    stockReport_panelLayout.setVerticalGroup(
        stockReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockReport_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(totalStockAmount_label, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    stockReport_lpane.add(stockReport_panel);
    stockReport_panel.setBounds(190, 110, 700, 340);

    noteLabelStock.setText("TO VIEW INDIVIDUAL ITEM STOCK, CLICK ON ITEM NAME");
    stockReport_lpane.add(noteLabelStock);
    noteLabelStock.setBounds(190, 460, 700, 20);

    salesReportParameter_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Sales Report Parameters ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    salesReportParameter_lpane.setOpaque(true);

    salesReportParameter_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(java.awt.Color.gray, 1, true), " Select Report Type ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 12), java.awt.Color.black)); // NOI18N
    salesReportParameter_panel.setOpaque(false);

    ViewReport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    ViewReport.setForeground(new java.awt.Color(0, 0, 102));
    ViewReport.setText("View Report");
    ViewReport.setEnabled(false);
    ViewReport.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ViewReportActionPerformed(evt);
        }
    });

    cOrP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    cOrP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    cOrP.setText("Select Client Name :");
    cOrP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    saleR_modes.add(summary);
    summary.setText("Summary");
    summary.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            summaryActionPerformed(evt);
        }
    });

    saleR_modes.add(detail);
    detail.setText("Detail");
    detail.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            detailActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout salesReportParameter_panelLayout = new javax.swing.GroupLayout(salesReportParameter_panel);
    salesReportParameter_panel.setLayout(salesReportParameter_panelLayout);
    salesReportParameter_panelLayout.setHorizontalGroup(
        salesReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(salesReportParameter_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(salesReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cOrP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(salesReportParameter_panelLayout.createSequentialGroup()
                    .addComponent(summary, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(detail, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                .addComponent(salesReport_comboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ViewReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    salesReportParameter_panelLayout.setVerticalGroup(
        salesReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(salesReportParameter_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(salesReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(summary)
                .addComponent(detail))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(cOrP, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(salesReport_comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(ViewReport)
            .addContainerGap())
    );

    salesReportParameter_lpane.add(salesReportParameter_panel);
    salesReportParameter_panel.setBounds(260, 80, 340, 160);

    salesReport_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Sales Report ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    salesReport_lpane.setOpaque(true);

    salesReport_panel.setBackground(new java.awt.Color(244, 244, 255));
    salesReport_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true));
    salesReport_panel.setPreferredSize(new java.awt.Dimension(700, 340));

    salesReport_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    salesReport_table.setForeground(new java.awt.Color(51, 51, 51));
    salesReport_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        },
        new String [] {

        }
    ));
    salesReport_table.setToolTipText("Sales Report");
    salesReport_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    salesReport_table.setCellSelectionEnabled(true);
    salesReport_table.setGridColor(new java.awt.Color(0, 0, 0));
    salesReport_table.setRowHeight(22);
    salesReport_table.getTableHeader().setReorderingAllowed(false);
    salesReport_table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            salesReport_tableMouseClicked(evt);
        }
    });
    jScrollPane6.setViewportView(salesReport_table);
    salesReport_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    printSalesReport.setText("Print");
    printSalesReport.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            printSalesReportActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout salesReport_panelLayout = new javax.swing.GroupLayout(salesReport_panel);
    salesReport_panel.setLayout(salesReport_panelLayout);
    salesReport_panelLayout.setHorizontalGroup(
        salesReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(salesReport_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(salesReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addComponent(printSalesReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    salesReport_panelLayout.setVerticalGroup(
        salesReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salesReport_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(printSalesReport)
            .addContainerGap())
    );

    salesReport_lpane.add(salesReport_panel);
    salesReport_panel.setBounds(200, 150, 700, 340);

    noteLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    salesReport_lpane.add(noteLabel);
    noteLabel.setBounds(50, 380, 730, 20);

    aigingReportParameter_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Aiging Report Searching Parameters ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    aigingReportParameter_lpane.setOpaque(true);

    aigingReportParameter_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
    aigingReportParameter_panel.setOpaque(false);

    viewReport.setText("View Report");
    viewReport.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            viewReportActionPerformed(evt);
        }
    });

    daysGroup.add(totalAiging);
    totalAiging.setSelected(true);
    totalAiging.setText("Total");
    totalAiging.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            totalAigingActionPerformed(evt);
        }
    });

    daysGroup.add(nintyP);
    nintyP.setText("90+");
    nintyP.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            nintyPActionPerformed(evt);
        }
    });

    daysGroup.add(sixtyP);
    sixtyP.setText("60+");
    sixtyP.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            sixtyPActionPerformed(evt);
        }
    });

    daysGroup.add(thirtyP);
    thirtyP.setText("30+");
    thirtyP.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            thirtyPActionPerformed(evt);
        }
    });

    aigingReport_comboBox.setMaximumRowCount(1000);

    jLabel9.setFont(new java.awt.Font("GeosansLight", 0, 18)); // NOI18N
    jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel9.setText("Payee Name :");
    jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    viewAigingBW_dates.setText("View Report B/W Dates");

    javax.swing.GroupLayout aigingReportParameter_panelLayout = new javax.swing.GroupLayout(aigingReportParameter_panel);
    aigingReportParameter_panel.setLayout(aigingReportParameter_panelLayout);
    aigingReportParameter_panelLayout.setHorizontalGroup(
        aigingReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(aigingReportParameter_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(aigingReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(viewReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(aigingReportParameter_panelLayout.createSequentialGroup()
                    .addComponent(thirtyP)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(sixtyP)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(nintyP)
                    .addGap(18, 18, 18)
                    .addComponent(totalAiging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(aigingReport_comboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, aigingReportParameter_panelLayout.createSequentialGroup()
                    .addComponent(startingDate_aiging, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(endDate_aiging, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(viewAigingBW_dates, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(13, Short.MAX_VALUE))
    );
    aigingReportParameter_panelLayout.setVerticalGroup(
        aigingReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, aigingReportParameter_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel9)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(aigingReport_comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(aigingReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(startingDate_aiging, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(endDate_aiging, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
            .addGroup(aigingReportParameter_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(nintyP)
                .addComponent(sixtyP)
                .addComponent(thirtyP)
                .addComponent(totalAiging))
            .addGap(16, 16, 16)
            .addComponent(viewAigingBW_dates)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(viewReport)
            .addContainerGap())
    );

    aigingReportParameter_lpane.add(aigingReportParameter_panel);
    aigingReportParameter_panel.setBounds(70, 70, 250, 220);

    aigingReport_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Aiging Report ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    aigingReport_lpane.setOpaque(true);

    aigingReport_panel.setBackground(new java.awt.Color(244, 244, 255));
    aigingReport_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
    aigingReport_panel.setOpaque(false);
    aigingReport_panel.setPreferredSize(new java.awt.Dimension(700, 340));

    aigingReport_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    aigingReport_table.setForeground(new java.awt.Color(51, 51, 51));
    aigingReport_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

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
    aigingReport_table.setToolTipText("Aiging Report");
    aigingReport_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    aigingReport_table.setCellSelectionEnabled(true);
    aigingReport_table.setGridColor(new java.awt.Color(0, 0, 0));
    aigingReport_table.setRowHeight(22);
    aigingReport_table.getTableHeader().setReorderingAllowed(false);
    aigingReport_table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            aigingReport_tableMouseClicked(evt);
        }
    });
    jScrollPane8.setViewportView(aigingReport_table);
    aigingReport_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    if (aigingReport_table.getColumnModel().getColumnCount() > 0) {
        aigingReport_table.getColumnModel().getColumn(0).setMinWidth(210);
        aigingReport_table.getColumnModel().getColumn(0).setPreferredWidth(210);
        aigingReport_table.getColumnModel().getColumn(0).setMaxWidth(250);
        aigingReport_table.getColumnModel().getColumn(1).setResizable(false);
        aigingReport_table.getColumnModel().getColumn(2).setResizable(false);
        aigingReport_table.getColumnModel().getColumn(3).setResizable(false);
        aigingReport_table.getColumnModel().getColumn(4).setResizable(false);
        aigingReport_table.getColumnModel().getColumn(4).setPreferredWidth(110);
    }

    tAmount_aiging.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    tAmount_aiging.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    tAmount_aiging.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    paymentLeftLble.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    paymentLeftLble.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    paymentLeftLble.setText("Total Payment Left :");

    jButton4.setText("Print");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout aigingReport_panelLayout = new javax.swing.GroupLayout(aigingReport_panel);
    aigingReport_panel.setLayout(aigingReport_panelLayout);
    aigingReport_panelLayout.setHorizontalGroup(
        aigingReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(aigingReport_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(aigingReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addGroup(aigingReport_panelLayout.createSequentialGroup()
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(paymentLeftLble, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tAmount_aiging, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    aigingReport_panelLayout.setVerticalGroup(
        aigingReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(aigingReport_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(aigingReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(tAmount_aiging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(paymentLeftLble, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    aigingReport_lpane.add(aigingReport_panel);
    aigingReport_panel.setBounds(170, 150, 700, 340);

    ledger_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Ledger ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    ledger_lpane.setOpaque(true);

    ledger_panel.setBackground(new java.awt.Color(244, 244, 255));
    ledger_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true));
    ledger_panel.setPreferredSize(new java.awt.Dimension(700, 340));

    ledger_table.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    ledger_table.setForeground(new java.awt.Color(51, 51, 51));
    ledger_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        },
        new String [] {

        }
    ));
    ledger_table.setToolTipText("Ledger");
    ledger_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    ledger_table.setCellSelectionEnabled(true);
    ledger_table.setGridColor(new java.awt.Color(0, 0, 0));
    ledger_table.setRowHeight(22);
    ledger_table.getTableHeader().setReorderingAllowed(false);
    ledger_table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            ledger_tableMouseClicked(evt);
        }
    });
    jScrollPane19.setViewportView(ledger_table);
    ledger_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    viewLedger_Button.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    viewLedger_Button.setForeground(new java.awt.Color(0, 0, 102));
    viewLedger_Button.setText("View Ledger");
    viewLedger_Button.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            viewLedger_ButtonActionPerformed(evt);
        }
    });

    printLedger.setText("Print");
    printLedger.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            printLedgerActionPerformed(evt);
        }
    });

    fromDate.setCalendarPreferredSize(new java.awt.Dimension(300, 150));

    toDate.setCalendarPreferredSize(new java.awt.Dimension(300, 150));

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("To");

    javax.swing.GroupLayout ledger_panelLayout = new javax.swing.GroupLayout(ledger_panel);
    ledger_panel.setLayout(ledger_panelLayout);
    ledger_panelLayout.setHorizontalGroup(
        ledger_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ledger_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(ledger_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(ledgerBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(viewLedger_Button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ledger_panelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(printLedger, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ledger_panelLayout.createSequentialGroup()
                    .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    ledger_panelLayout.setVerticalGroup(
        ledger_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ledger_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(ledgerBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ledger_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(viewLedger_Button)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(printLedger)
            .addContainerGap())
    );

    ledger_lpane.add(ledger_panel);
    ledger_panel.setBounds(200, 80, 700, 340);

    profitReport_lpane.setOpaque(true);

    profitReport_panel.setBackground(new java.awt.Color(244, 244, 255));
    profitReport_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " This Month Report", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N

    jLabel17.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel17.setText("Purchase Trans :");

    jLabel26.setText("jLabel26");

    jLabel27.setText("jLabel26");

    jLabel28.setText("jLabel26");

    jLabel29.setText("jLabel26");

    jLabel31.setText("jLabel26");

    jLabel32.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel32.setText("Total Chalans :");

    jLabel33.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel33.setText("Stock Amount :");

    jLabel34.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel34.setText("Total Profit");

    jLabel35.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel35.setText("Company Status");

    javax.swing.GroupLayout profitReport_panelLayout = new javax.swing.GroupLayout(profitReport_panel);
    profitReport_panel.setLayout(profitReport_panelLayout);
    profitReport_panelLayout.setHorizontalGroup(
        profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(profitReport_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
            .addGroup(profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(profitReport_panelLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(profitReport_panelLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap(11, Short.MAX_VALUE))
    );
    profitReport_panelLayout.setVerticalGroup(
        profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(profitReport_panelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(26, 26, 26)
            .addGroup(profitReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    profitReport_lpane.add(profitReport_panel);
    profitReport_panel.setBounds(40, 290, 630, 140);

    profitReport_panel1.setBackground(new java.awt.Color(244, 244, 255));
    profitReport_panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102)), " Comany Status From Begining", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    java.awt.GridBagLayout profitReport_panel1Layout = new java.awt.GridBagLayout();
    profitReport_panel1Layout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
    profitReport_panel1Layout.rowHeights = new int[] {0, 5, 0, 5, 0};
    profitReport_panel1.setLayout(profitReport_panel1Layout);

    chalanQuan.setText("quantity");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(chalanQuan, gridBagConstraints);

    chalanAmount.setText("label");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 17;
    profitReport_panel1.add(chalanAmount, gridBagConstraints);

    stockAmount.setText("label");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(stockAmount, gridBagConstraints);

    profitAmount.setText("label");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(profitAmount, gridBagConstraints);

    jLabel40.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel40.setText("Total Chalans : ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(jLabel40, gridBagConstraints);

    jLabel41.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel41.setText("Purchase Transactions : ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(jLabel41, gridBagConstraints);

    jLabel42.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel42.setText("Total Profit : ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(jLabel42, gridBagConstraints);

    jLabel43.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel43.setText("Company Status : ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 8;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(jLabel43, gridBagConstraints);

    companyStatus.setText("label");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 8;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(companyStatus, gridBagConstraints);

    transactionQuan.setText("label");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(transactionQuan, gridBagConstraints);

    transactionAmount.setText("label");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 17;
    profitReport_panel1.add(transactionAmount, gridBagConstraints);

    jButton1.setText("View Stock Details");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 14;
    profitReport_panel1.add(jButton1, gridBagConstraints);

    jLabel44.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
    jLabel44.setText("Stock Amount : ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 10;
    gridBagConstraints.ipady = 29;
    profitReport_panel1.add(jLabel44, gridBagConstraints);

    jButton2.setText("View All Chalans");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 6;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
    profitReport_panel1.add(jButton2, gridBagConstraints);

    profitReport_lpane.add(profitReport_panel1);
    profitReport_panel1.setBounds(40, 50, 710, 200);

    calc_lpane.setOpaque(true);

    calculator.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
    calculator.setOpaque(false);

    TextFeild.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    TextFeild.setForeground(new java.awt.Color(153, 153, 153));
    TextFeild.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    TextFeild.setText("0.");
    TextFeild.setToolTipText("Enter First No");
    TextFeild.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            TextFeildKeyPressed(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            TextFeildKeyTyped(evt);
        }
    });

    btns.setBackground(new java.awt.Color(204, 255, 204));
    btns.setForeground(new java.awt.Color(255, 255, 255));
    btns.setOpaque(false);
    btns.setLayout(new java.awt.GridBagLayout());

    B1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B1.setText("1");
    B1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B1ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 7;
    gridBagConstraints.ipady = 31;
    btns.add(B1, gridBagConstraints);

    B2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B2.setText("2");
    B2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B2ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 7;
    gridBagConstraints.ipady = 31;
    btns.add(B2, gridBagConstraints);

    B3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B3.setText("3");
    B3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B3ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 31;
    btns.add(B3, gridBagConstraints);

    B4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B4.setText("4");
    B4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B4ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 7;
    gridBagConstraints.ipady = 31;
    btns.add(B4, gridBagConstraints);

    B5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B5.setText("5");
    B5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B5ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 7;
    gridBagConstraints.ipady = 31;
    btns.add(B5, gridBagConstraints);

    B6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B6.setText("6");
    B6.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B6ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 31;
    btns.add(B6, gridBagConstraints);

    B7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B7.setText("7");
    B7.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B7ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 7;
    gridBagConstraints.ipady = 31;
    btns.add(B7, gridBagConstraints);

    B8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B8.setText("8");
    B8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B8ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 7;
    gridBagConstraints.ipady = 31;
    btns.add(B8, gridBagConstraints);

    B9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B9.setText("9");
    B9.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B9ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 31;
    btns.add(B9, gridBagConstraints);

    B0.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    B0.setText("0");
    B0.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            B0ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 7;
    gridBagConstraints.ipady = 30;
    btns.add(B0, gridBagConstraints);

    BPoint.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    BPoint.setText(".");
    BPoint.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BPointActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 7;
    gridBagConstraints.ipady = 30;
    btns.add(BPoint, gridBagConstraints);

    BEquals2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    BEquals2.setForeground(new java.awt.Color(255, 0, 0));
    BEquals2.setText("=");
    BEquals2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BEquals2ActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 30;
    btns.add(BEquals2, gridBagConstraints);

    BPlus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    BPlus.setForeground(new java.awt.Color(255, 0, 0));
    BPlus.setText("+");
    BPlus.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BPlusActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 24;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    btns.add(BPlus, gridBagConstraints);

    BMinus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    BMinus.setForeground(new java.awt.Color(255, 0, 0));
    BMinus.setText("-");
    BMinus.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BMinusActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 24;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    btns.add(BMinus, gridBagConstraints);

    BMultiply.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    BMultiply.setForeground(new java.awt.Color(255, 0, 51));
    BMultiply.setText("*");
    BMultiply.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BMultiplyActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 24;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    btns.add(BMultiply, gridBagConstraints);

    BDivide.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    BDivide.setForeground(new java.awt.Color(255, 0, 0));
    BDivide.setText("/");
    BDivide.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BDivideActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 23;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    btns.add(BDivide, gridBagConstraints);

    C.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    C.setText("C");
    C.setToolTipText("Clears The Current Screen");
    C.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            CActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 9;
    gridBagConstraints.ipady = 20;
    btns.add(C, gridBagConstraints);

    Ce.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    Ce.setText("Ce");
    Ce.setToolTipText("<html><body bgcolor=\"#E6E6FA\">Clears all Data From Memory</body></html>");
    Ce.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            CeActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 4;
    btns.add(Ce, gridBagConstraints);

    BackSpace.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    BackSpace.setText("BackSpace");
    BackSpace.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            BackSpaceActionPerformed(evt);
        }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 7;
    btns.add(BackSpace, gridBagConstraints);

    javax.swing.GroupLayout calculatorLayout = new javax.swing.GroupLayout(calculator);
    calculator.setLayout(calculatorLayout);
    calculatorLayout.setHorizontalGroup(
        calculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(calculatorLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(calculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(TextFeild)
                .addComponent(btns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    calculatorLayout.setVerticalGroup(
        calculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(calculatorLayout.createSequentialGroup()
            .addGap(27, 27, 27)
            .addComponent(TextFeild, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btns, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(25, Short.MAX_VALUE))
    );

    calc_lpane.add(calculator);
    calculator.setBounds(70, 70, 210, 370);

    profitReportPerimeter_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Profit Report ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    profitReportPerimeter_lpane.setOpaque(true);

    jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Custom Profit Report ", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 18), new java.awt.Color(0, 51, 102))); // NOI18N
    jPanel3.setOpaque(false);

    profitGain.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    profitGain.setForeground(new java.awt.Color(0, 153, 51));
    profitGain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    profitGain.setText("0.0");

    jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel30.setText("Profit (Rs.) :");

    viewProfit.setText("View Report");
    viewProfit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            viewProfitActionPerformed(evt);
        }
    });

    startDate.setCalendarPreferredSize(new java.awt.Dimension(400, 400));

    endDate.setCalendarPreferredSize(new java.awt.Dimension(400, 400));

    jLabel25.setText("To : ");

    jLabel24.setText("From : ");

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(viewProfit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addGap(66, 66, 66)))
                    .addGap(21, 21, 21)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel25)
                        .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(profitGain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel25)
                    .addGap(12, 12, 12)
                    .addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel24)
                    .addGap(12, 12, 12)
                    .addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(viewProfit)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(profitGain)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    profitReportPerimeter_lpane.add(jPanel3);
    jPanel3.setBounds(40, 70, 251, 180);

    thresholdPoint_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " Threshold Point ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    thresholdPoint_lpane.setOpaque(true);

    thresholdPoint_panel.setBackground(new java.awt.Color(244, 244, 255));
    thresholdPoint_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
    thresholdPoint_panel.setOpaque(false);

    thresholdPoint_table.setForeground(new java.awt.Color(51, 51, 51));
    thresholdPoint_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {}
        },
        new String [] {

        }
    ));
    thresholdPoint_table.setToolTipText("This is Your Stock Report");
    thresholdPoint_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    thresholdPoint_table.setCellSelectionEnabled(true);
    thresholdPoint_table.setGridColor(new java.awt.Color(0, 0, 0));
    thresholdPoint_table.getTableHeader().setReorderingAllowed(false);
    jScrollPane13.setViewportView(thresholdPoint_table);
    thresholdPoint_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    javax.swing.GroupLayout thresholdPoint_panelLayout = new javax.swing.GroupLayout(thresholdPoint_panel);
    thresholdPoint_panel.setLayout(thresholdPoint_panelLayout);
    thresholdPoint_panelLayout.setHorizontalGroup(
        thresholdPoint_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(thresholdPoint_panelLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
            .addGap(15, 15, 15))
    );
    thresholdPoint_panelLayout.setVerticalGroup(
        thresholdPoint_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thresholdPoint_panelLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
            .addGap(15, 15, 15))
    );

    javax.swing.GroupLayout thresholdPoint_lpaneLayout = new javax.swing.GroupLayout(thresholdPoint_lpane);
    thresholdPoint_lpane.setLayout(thresholdPoint_lpaneLayout);
    thresholdPoint_lpaneLayout.setHorizontalGroup(
        thresholdPoint_lpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 1023, Short.MAX_VALUE)
        .addGroup(thresholdPoint_lpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thresholdPoint_lpaneLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(thresholdPoint_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)))
    );
    thresholdPoint_lpaneLayout.setVerticalGroup(
        thresholdPoint_lpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 557, Short.MAX_VALUE)
        .addGroup(thresholdPoint_lpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thresholdPoint_lpaneLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(thresholdPoint_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)))
    );
    thresholdPoint_lpane.setLayer(thresholdPoint_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

    unInvoicedChReport_lpane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 102), 1, true), " UnInvoiced Chalan Report ", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("GeosansLight", 0, 22), new java.awt.Color(0, 51, 102))); // NOI18N
    unInvoicedChReport_lpane.setOpaque(true);

    unInvoicedChReport_panel.setBackground(new java.awt.Color(244, 244, 255));
    unInvoicedChReport_panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
    unInvoicedChReport_panel.setOpaque(false);

    unInvoicedChReport_table.setForeground(new java.awt.Color(51, 51, 51));
    unInvoicedChReport_table.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {}
        },
        new String [] {

        }
    ));
    unInvoicedChReport_table.setToolTipText("This is Your Stock Report");
    unInvoicedChReport_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    unInvoicedChReport_table.setCellSelectionEnabled(true);
    unInvoicedChReport_table.setGridColor(new java.awt.Color(0, 0, 0));
    unInvoicedChReport_table.getTableHeader().setReorderingAllowed(false);
    unInvoicedChReport_table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            unInvoicedChReport_tableMouseClicked(evt);
        }
    });
    jScrollPane14.setViewportView(unInvoicedChReport_table);
    unInvoicedChReport_table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    javax.swing.GroupLayout unInvoicedChReport_panelLayout = new javax.swing.GroupLayout(unInvoicedChReport_panel);
    unInvoicedChReport_panel.setLayout(unInvoicedChReport_panelLayout);
    unInvoicedChReport_panelLayout.setHorizontalGroup(
        unInvoicedChReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(unInvoicedChReport_panelLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
            .addGap(15, 15, 15))
    );
    unInvoicedChReport_panelLayout.setVerticalGroup(
        unInvoicedChReport_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, unInvoicedChReport_panelLayout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
            .addGap(15, 15, 15))
    );

    javax.swing.GroupLayout unInvoicedChReport_lpaneLayout = new javax.swing.GroupLayout(unInvoicedChReport_lpane);
    unInvoicedChReport_lpane.setLayout(unInvoicedChReport_lpaneLayout);
    unInvoicedChReport_lpaneLayout.setHorizontalGroup(
        unInvoicedChReport_lpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 1043, Short.MAX_VALUE)
        .addGroup(unInvoicedChReport_lpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unInvoicedChReport_lpaneLayout.createSequentialGroup()
                .addGap(0, 146, Short.MAX_VALUE)
                .addComponent(unInvoicedChReport_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 147, Short.MAX_VALUE)))
    );
    unInvoicedChReport_lpaneLayout.setVerticalGroup(
        unInvoicedChReport_lpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 579, Short.MAX_VALUE)
        .addGroup(unInvoicedChReport_lpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unInvoicedChReport_lpaneLayout.createSequentialGroup()
                .addGap(0, 125, Short.MAX_VALUE)
                .addComponent(unInvoicedChReport_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 126, Short.MAX_VALUE)))
    );
    unInvoicedChReport_lpane.setLayer(unInvoicedChReport_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

    homeLable.setBackground(new java.awt.Color(244, 244, 255));
    homeLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    homeLable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/home.png"))); // NOI18N
    homeLable.setOpaque(true);

    javax.swing.GroupLayout MainLayout = new javax.swing.GroupLayout(Main);
    Main.setLayout(MainLayout);
    MainLayout.setHorizontalGroup(
        MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(pTransaction_lpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stockReport_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dChalan_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addClient_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesReportParameter_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesReport_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aigingReportParameter_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(expense_lpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(calc_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aigingReport_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ledger_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profitReportPerimeter_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profitReport_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(purchaseOrder_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesInvoice_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thresholdPoint_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addSupplier_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addExpenseName_lpane, javax.swing.GroupLayout.Alignment.TRAILING))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(unInvoicedChReport_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeLable, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paymentPaid_lpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1052, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paymentRecieved_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addBankNames_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addProduct_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cashBook_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bankBook_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE))
    );
    MainLayout.setVerticalGroup(
        MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(pTransaction_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stockReport_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dChalan_lpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addClient_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesReportParameter_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesReport_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aigingReportParameter_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(expense_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(calc_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aigingReport_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ledger_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profitReportPerimeter_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profitReport_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(purchaseOrder_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(salesInvoice_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(thresholdPoint_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addSupplier_lpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addExpenseName_lpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(unInvoicedChReport_lpane, javax.swing.GroupLayout.Alignment.TRAILING))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeLable, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paymentPaid_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paymentRecieved_lpane))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addBankNames_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addProduct_lpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cashBook_lpane, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE))
        .addGroup(MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bankBook_lpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
    );

    Status.setBackground(new java.awt.Color(229, 229, 229));
    Status.setPreferredSize(new java.awt.Dimension(1210, 26));

    statusBarLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    statusBarLabel.setPreferredSize(new java.awt.Dimension(34, 20));

    dateLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    dateLabel.setForeground(new java.awt.Color(0, 51, 102));
    dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

    monthLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    monthLabel.setForeground(new java.awt.Color(0, 51, 102));
    monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

    javax.swing.GroupLayout StatusLayout = new javax.swing.GroupLayout(Status);
    Status.setLayout(StatusLayout);
    StatusLayout.setHorizontalGroup(
        StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StatusLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(statusBarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE)
            .addGap(15, 15, 15))
    );
    StatusLayout.setVerticalGroup(
        StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(StatusLayout.createSequentialGroup()
            .addGap(3, 3, 3)
            .addGroup(StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(statusBarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(monthLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(21, 21, 21))
    );

    javax.swing.GroupLayout BackgorundLayout = new javax.swing.GroupLayout(Backgorund);
    Backgorund.setLayout(BackgorundLayout);
    BackgorundLayout.setHorizontalGroup(
        BackgorundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackgorundLayout.createSequentialGroup()
            .addGap(6, 6, 6)
            .addGroup(BackgorundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(Status, javax.swing.GroupLayout.DEFAULT_SIZE, 1393, Short.MAX_VALUE)
                .addComponent(Banner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BackgorundLayout.createSequentialGroup()
                    .addComponent(Tree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGap(6, 6, 6))
    );
    BackgorundLayout.setVerticalGroup(
        BackgorundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(BackgorundLayout.createSequentialGroup()
            .addGap(6, 6, 6)
            .addComponent(Banner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(BackgorundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Tree, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(6, 6, 6))
    );

    main_menubar.setBackground(new java.awt.Color(0, 51, 102));
    main_menubar.setForeground(new java.awt.Color(255, 255, 255));

    File.setBackground(new java.awt.Color(0, 153, 153));
    File.setText("File");

    PurchaseO_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
    PurchaseO_menu.setBackground(new java.awt.Color(0, 102, 204));
    PurchaseO_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction1.png"))); // NOI18N
    PurchaseO_menu.setText("Purchase Order");
    PurchaseO_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PurchaseO_menuActionPerformed(evt);
        }
    });
    File.add(PurchaseO_menu);

    PurchaseTr_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
    PurchaseTr_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction1.png"))); // NOI18N
    PurchaseTr_menu.setText("Purchase Transaction");
    PurchaseTr_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            PurchaseTr_menuActionPerformed(evt);
        }
    });
    File.add(PurchaseTr_menu);

    DeliveryCh_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
    DeliveryCh_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction1.png"))); // NOI18N
    DeliveryCh_menu.setText("Delivery Chalan");
    DeliveryCh_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            DeliveryCh_menuActionPerformed(evt);
        }
    });
    File.add(DeliveryCh_menu);

    salesInvoice_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
    salesInvoice_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction1.png"))); // NOI18N
    salesInvoice_menu.setText("Sales Invoice");
    salesInvoice_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            salesInvoice_menuActionPerformed(evt);
        }
    });
    File.add(salesInvoice_menu);

    expense_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
    expense_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction1.png"))); // NOI18N
    expense_menu.setText("Expense");
    expense_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            expense_menuActionPerformed(evt);
        }
    });
    File.add(expense_menu);

    rcvdVoucher_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
    rcvdVoucher_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction1.png"))); // NOI18N
    rcvdVoucher_menu.setText("Amount Recieved Voucher");
    rcvdVoucher_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            rcvdVoucher_menuActionPerformed(evt);
        }
    });
    File.add(rcvdVoucher_menu);

    paidVoucher_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    paidVoucher_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction1.png"))); // NOI18N
    paidVoucher_menu.setText("Amount Paid Voucher");
    paidVoucher_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            paidVoucher_menuActionPerformed(evt);
        }
    });
    File.add(paidVoucher_menu);

    main_menubar.add(File);

    reports_menu.setText("Reports");

    aigingReport_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction.png"))); // NOI18N
    aigingReport_menu.setText("Aiging Report");

    recievableAiging_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    recievableAiging_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/rcvble.png"))); // NOI18N
    recievableAiging_menu.setText("Recievable");
    recievableAiging_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            recievableAiging_menuActionPerformed(evt);
        }
    });
    aigingReport_menu.add(recievableAiging_menu);

    payableAiging_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    payableAiging_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/pyble.png"))); // NOI18N
    payableAiging_menu.setText("Payable");
    payableAiging_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            payableAiging_menuActionPerformed(evt);
        }
    });
    aigingReport_menu.add(payableAiging_menu);

    reports_menu.add(aigingReport_menu);

    salesRe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction.png"))); // NOI18N
    salesRe.setText("Sales Report");

    clientsSReport_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    clientsSReport_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/partyW.png"))); // NOI18N
    clientsSReport_menu.setText("Clients Wise");
    clientsSReport_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            clientsSReport_menuActionPerformed(evt);
        }
    });
    salesRe.add(clientsSReport_menu);

    salersSReport_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    salersSReport_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/product.png"))); // NOI18N
    salersSReport_menu.setText("Product Wise");
    salersSReport_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            salersSReport_menuActionPerformed(evt);
        }
    });
    salesRe.add(salersSReport_menu);

    reports_menu.add(salesRe);

    purchaseReport_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction.png"))); // NOI18N
    purchaseReport_menu.setText("Purchase Report");

    sellersPReport_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    sellersPReport_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/partyW.png"))); // NOI18N
    sellersPReport_menu.setText("Supplier Wise");
    sellersPReport_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            sellersPReport_menuActionPerformed(evt);
        }
    });
    purchaseReport_menu.add(sellersPReport_menu);

    productsPReport_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    productsPReport_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/product.png"))); // NOI18N
    productsPReport_menu.setText("Product Wise");
    productsPReport_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            productsPReport_menuActionPerformed(evt);
        }
    });
    purchaseReport_menu.add(productsPReport_menu);

    reports_menu.add(purchaseReport_menu);

    StockRe_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
    StockRe_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction.png"))); // NOI18N
    StockRe_menu.setText("Stock Report");
    StockRe_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            StockRe_menuActionPerformed(evt);
        }
    });
    reports_menu.add(StockRe_menu);

    main_menubar.add(reports_menu);

    Edit.setText("Head Of Accounts");

    addItem_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
    addItem_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add1.png"))); // NOI18N
    addItem_menu.setText("Add Clients");
    addItem_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addItem_menuActionPerformed(evt);
        }
    });
    Edit.add(addItem_menu);

    addSeller_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
    addSeller_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add1.png"))); // NOI18N
    addSeller_menu.setText("Add Suppliers");
    addSeller_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addSeller_menuActionPerformed(evt);
        }
    });
    Edit.add(addSeller_menu);

    addProducts_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
    addProducts_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add1.png"))); // NOI18N
    addProducts_menu.setText("Add Products");
    addProducts_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addProducts_menuActionPerformed(evt);
        }
    });
    Edit.add(addProducts_menu);

    addBank_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK));
    addBank_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add1.png"))); // NOI18N
    addBank_menu.setText("Add Bank Names");
    addBank_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addBank_menuActionPerformed(evt);
        }
    });
    Edit.add(addBank_menu);

    addExpense_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
    addExpense_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add1.png"))); // NOI18N
    addExpense_menu.setText("Add Expense Categories");
    addExpense_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addExpense_menuActionPerformed(evt);
        }
    });
    Edit.add(addExpense_menu);

    addOwner_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
    addOwner_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add1.png"))); // NOI18N
    addOwner_menu.setText("Add Owner");
    addOwner_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addOwner_menuActionPerformed(evt);
        }
    });
    Edit.add(addOwner_menu);

    addInvestment_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK));
    addInvestment_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add1.png"))); // NOI18N
    addInvestment_menu.setText("Add Investment");
    addInvestment_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addInvestment_menuActionPerformed(evt);
        }
    });
    Edit.add(addInvestment_menu);

    main_menubar.add(Edit);

    ViewLedger.setText("View Ledger");
    ViewLedger.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ViewLedgerActionPerformed(evt);
        }
    });

    clientLedger_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
    clientLedger_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction.png"))); // NOI18N
    clientLedger_menu.setText("Clients Ledger");
    clientLedger_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            clientLedger_menuActionPerformed(evt);
        }
    });
    ViewLedger.add(clientLedger_menu);

    sellerLedger_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
    sellerLedger_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction.png"))); // NOI18N
    sellerLedger_menu.setText("Supplier Ledger");
    sellerLedger_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            sellerLedger_menuActionPerformed(evt);
        }
    });
    ViewLedger.add(sellerLedger_menu);

    bankLedger_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
    bankLedger_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction.png"))); // NOI18N
    bankLedger_menu.setText("Bank Ledger");
    bankLedger_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            bankLedger_menuActionPerformed(evt);
        }
    });
    ViewLedger.add(bankLedger_menu);

    expenseLedger_menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
    expenseLedger_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/transaction.png"))); // NOI18N
    expenseLedger_menu.setText("Expense Ledger");
    expenseLedger_menu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            expenseLedger_menuActionPerformed(evt);
        }
    });
    ViewLedger.add(expenseLedger_menu);

    main_menubar.add(ViewLedger);

    setJMenuBar(main_menubar);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Backgorund, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Backgorund, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void PurchaseTr_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchaseTr_menuActionPerformed
        Visiblefalse(pTransaction_lpane, purchaseTransaction);
        if(!Pflag){
            pTransaction = new DefaultTableModel(columnNamesP,10);
            pTransaction_table.setModel(pTransaction);
            TableColumnModel tcm = pTransaction_table.getColumnModel();
            tcm.getColumn(0).setMinWidth(170);
            tcm.getColumn(0).setMaxWidth(200);
            supplierBox_pT.requestFocus();
            setDate(dateField_PT);
            prevTrNo(transactionNo);
            pTransaction_table.setEnabled(true);
            Pflag=true;
        }
        getSupplierNames(supplierBox_pT);
        inserComboBox(pTransaction_table);
    }//GEN-LAST:event_PurchaseTr_menuActionPerformed

    @SuppressWarnings("ConvertToStringSwitch")
    private void makeTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeTransactionActionPerformed
    int i;
    PitemNo = new int[pTransaction_table.getRowCount()];
    stock_finalQuantity = new int[pTransaction_table.getRowCount()];
    balanceLeftPT=0;
    bankBalancePT=0;

    if(supplierBox_pT.getSelectedItem()!=null && Float.parseFloat(tAmount_field_PT.getText())>0){
        //<editor-fold defaultstate="collapsed" desc="TryBlock">
        try{
            //<editor-fold defaultstate="collapsed" desc="RecievingPreviousData">
            for(i=0; i<pTransaction_table.getRowCount(); i++){
                if(pTransaction_table.getValueAt(i, 0)!=null && Float.parseFloat(pTransaction_table.getValueAt(i, 4).toString())>0){

                //<editor-fold defaultstate="collapsed" desc="SelectingItemNos">
                ps=cn.prepareStatement("SELECT itemNo FROM itemsList WHERE itemName='"+pTransaction_table.getValueAt(i, 0)+"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    PitemNo[i]=rs.getInt(1);
                }
    //                </editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Selecting Previous Quantity And Amount">
                ps=cn.prepareStatement("SELECT quantity FROM stock WHERE itemNo="+PitemNo[i]+"");
                rs=ps.executeQuery();
                while(rs.next()){
                    stock_previousQuantity=rs.getInt(1);
                }
                stock_recentQuantity=Integer.valueOf(pTransaction_table.getValueAt(i, 2).toString());
                stock_finalQuantity[i]=stock_previousQuantity+stock_recentQuantity;
    //</editor-fold>

                }
            }
    //            </editor-fold>
            //<editor-fold defaultstate="collapsed" desc="UpdatingNewData">

            for(i=0; i<pTransaction_table.getRowCount(); i++){
                if(pTransaction_table.getValueAt(i, 0)!=null && Float.parseFloat(pTransaction_table.getValueAt(i, 4).toString())>0){

                    // <editor-fold defaultstate="collapsed" desc="Updating Stock">
                            ps=cn.prepareStatement("UPDATE stock SET quantity='"+stock_finalQuantity[i]+"' WHERE itemNo="+PitemNo[i]+"");
                            ps.execute();
            //        </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="InsertingDataInProfitTable">
                            ps=cn.prepareStatement("INSERT INTO profit VALUES('"+dateField_PT.getText()+"',"+PitemNo[i]+","+pTransaction_table.getValueAt(i, 2).toString()+","+pTransaction_table.getValueAt(i, 3).toString()+","+pTransaction_table.getValueAt(i, 2).toString()+","+pTransaction_table.getValueAt(i, 4).toString()+",'0','No')");
                            ps.execute();
            //        </editor-fold>
                    switch(purchaseMode){
                        case "Cash":
                            // <editor-fold defaultstate="collapsed" desc="InsertingDataOfPurchaseTransaction">
                                ps=cn.prepareStatement("INSERT INTO purchaseTransaction VALUES("+transactionNo.getText()+",'"+dateField_PT.getText()+"','"+supplierBox_pT.getSelectedItem().toString()+"','"+PitemNo[i]+"',"+pTransaction_table.getValueAt(i, 2).toString()+","+pTransaction_table.getValueAt(i, 3).toString()+","+pTransaction_table.getValueAt(i, 4).toString()+",'"+tAmount_field_PT.getText()+"','Paid','"+tAmount_field_PT.getText()+"','0')");
                                ps.execute();
                //        </editor-fold>
                            break;
                        case "Cheque":
                            if(bankNameBox_PT.getSelectedItem()!=null){
                                // <editor-fold defaultstate="collapsed" desc="InsertingDataOfPurchaseTransaction">
                                    ps=cn.prepareStatement("INSERT INTO purchaseTransaction VALUES("+transactionNo.getText()+",'"+dateField_PT.getText()+"','"+supplierBox_pT.getSelectedItem().toString()+"','"+PitemNo[i]+"',"+pTransaction_table.getValueAt(i, 2).toString()+","+pTransaction_table.getValueAt(i, 3).toString()+","+pTransaction_table.getValueAt(i, 4).toString()+",'"+tAmount_field_PT.getText()+"','Paid','"+tAmount_field_PT.getText()+"','0')");
                                    ps.execute();
                //        </editor-fold>
                            }
                            break;
                        case "Credit":
                            ps=cn.prepareStatement("SELECT TOP 1 balance FROM supplierLedger WHERE sellerId="+sellerId+" ORDER BY recordNo DESC");
                            rs=ps.executeQuery();
                            if(rs.next()){
                                supplierBalance=rs.getFloat(1);
                            }
                            // <editor-fold defaultstate="collapsed" desc="MinusPaymentLeft">
                            if(supplierBalance<0){
                                currentPaymentPaid= -supplierBalance;
                                JOptionPane.showMessageDialog(paymentLeft, currentPaymentPaid);
                                if(currentPaymentPaid<Float.parseFloat(tAmount_field_PT.getText())){
                                    updatedPaymentPV=Float.parseFloat(tAmount_field_PT.getText())-currentPaymentPaid;
                                    ps=cn.prepareStatement("INSERT INTO purchaseTransaction VALUES("+transactionNo.getText()+",'"+dateField_PT.getText()+"','"+supplierBox_pT.getSelectedItem().toString()+"','"+PitemNo[i]+"',"+pTransaction_table.getValueAt(i, 2).toString()+","+pTransaction_table.getValueAt(i, 3).toString()+","+pTransaction_table.getValueAt(i, 4).toString()+",'"+tAmount_field_PT.getText()+"','Not Paid',"+updatedPaymentPV+",'0')");
                                    ps.execute();
                                }
                                else if(currentPaymentPaid>=Float.parseFloat(tAmount_field_PT.getText())){
                                    ps=cn.prepareStatement("INSERT INTO purchaseTransaction VALUES("+transactionNo.getText()+",'"+dateField_PT.getText()+"','"+supplierBox_pT.getSelectedItem().toString()+"','"+PitemNo[i]+"',"+pTransaction_table.getValueAt(i, 2).toString()+","+pTransaction_table.getValueAt(i, 3).toString()+","+pTransaction_table.getValueAt(i, 4).toString()+",'"+tAmount_field_PT.getText()+"','Paid','0','0')");
                                    ps.execute();
                                }
                            }
                            else{
                                ps=cn.prepareStatement("INSERT INTO purchaseTransaction VALUES("+transactionNo.getText()+",'"+dateField_PT.getText()+"','"+supplierBox_pT.getSelectedItem().toString()+"','"+PitemNo[i]+"',"+pTransaction_table.getValueAt(i, 2).toString()+","+pTransaction_table.getValueAt(i, 3).toString()+","+pTransaction_table.getValueAt(i, 4).toString()+",'"+tAmount_field_PT.getText()+"','Not Paid',"+tAmount_field_PT.getText()+",'0')");
                                ps.execute();
                            }
                //        </editor-fold>
                            break;
                    }
                }
            }

            // <editor-fold defaultstate="collapsed" desc="Book,Leger,SupplierDetails">
                switch(purchaseMode){
                    case "Cash":
                        // <editor-fold defaultstate="collapsed" desc="Selecting TotalTransactions And TotalPurchaseAmount">
                                    ps=cn.prepareStatement("SELECT totalTransaction,totalPurchaseAm FROM supplierList WHERE sellerName='"+supplierBox_pT.getSelectedItem().toString()+"'");
                                    rs=ps.executeQuery();
                                    while (rs.next()){
                                        prevTransactionsAmount=rs.getInt(1);
                                        prevPurchaseAmount=rs.getFloat(2);
                                    }
                                    prevTransactionsAmount+=1;
                                    prevPurchaseAmount+=Float.parseFloat(tAmount_field_PT.getText());
            //                  </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Updating TotalTransactions And TotalPurchaseAmount">
                        ps=cn.prepareStatement("UPDATE supplierList SET totalTransaction="+prevTransactionsAmount+",totalPurchaseAm="+prevPurchaseAmount+",totalPaidAm="+prevPurchaseAmount+" WHERE sellerName='"+supplierBox_pT.getSelectedItem().toString()+"'");
                        ps.execute();
    //                    </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="CashBook">
                        ps=cn.prepareStatement("SELECT TOP 1 balance FROM cashBook ORDER BY Id DESC");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            balanceLeftPT=rs.getFloat(1);
                        }
                        balanceLeftPT=balanceLeftPT-Float.parseFloat(tAmount_field_PT.getText());
                        ps=cn.prepareStatement("INSERT INTO cashBook VALUES('"+dateField_PT.getText()+"',"+Integer.parseInt(transactionNo.getText())+",'"+supplierBox_pT.getSelectedItem()+"','Purchased Goods','',"+tAmount_field_PT.getText()+","+balanceLeftPT+")");
                        ps.execute();
    //                        </editor-fold>
                        break;
                    case "Cheque":
                        if(bankNameBox_PT.getSelectedItem()!=null){
                            // <editor-fold defaultstate="collapsed" desc="bankLedgerUpdate">
                                    ps=cn.prepareStatement("SELECT bankId FROM bankList WHERE bankName='"+bankNameBox_PT.getSelectedItem().toString()+"'");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        bankIdPT=rs.getInt(1);
                                    }
                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM bankLedger WHERE bankId="+bankIdPT+" ORDER BY id DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        bankBalancePT=rs.getFloat(1);
                                    }
                                    bankBalancePT=bankBalancePT-Float.parseFloat(tAmount_field_PT.getText());
                                    ps=cn.prepareStatement("INSERT INTO bankLedger VALUES("+bankIdPT+",'"+dateField_SI.getText()+"',"+transactionNo.getText()+",'Purchased Goods From "+supplierBox_pT.getSelectedItem().toString()+"','',"+tAmount_field_PT.getText()+","+bankBalancePT+")");
                                    ps.execute();
    //            </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="Selecting TotalTransactions And TotalPurchaseAmount">
                                        ps=cn.prepareStatement("SELECT totalTransaction,totalPurchaseAm FROM supplierList WHERE sellerName='"+supplierBox_pT.getSelectedItem().toString()+"'");
                                        rs=ps.executeQuery();
                                        while (rs.next()){
                                            prevTransactionsAmount=rs.getInt(1);
                                            prevPurchaseAmount=rs.getFloat(2);
                                        }
                                        prevTransactionsAmount+=1;
                                        prevPurchaseAmount+=Float.parseFloat(tAmount_field_PT.getText());
                //                  </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="Updating TotalTransactions And TotalPurchaseAmount">
                            ps=cn.prepareStatement("UPDATE supplierList SET totalTransaction="+prevTransactionsAmount+",totalPurchaseAm="+prevPurchaseAmount+",totalPaidAm="+prevPurchaseAmount+" WHERE sellerName='"+supplierBox_pT.getSelectedItem().toString()+"'");
                            ps.execute();
    //                    </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="ChequeBook">
                            ps=cn.prepareStatement("SELECT TOP 1 balance FROM chequeBook ORDER BY Id DESC");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                balanceLeftPT=rs.getFloat(1);
                            }
                            balanceLeftPT=balanceLeftPT-Float.parseFloat(tAmount_field_PT.getText());
                            ps=cn.prepareStatement("INSERT INTO chequeBook VALUES('"+dateField_PT.getText()+"',"+Integer.parseInt(transactionNo.getText())+",'"+bankNameBox_PT.getSelectedItem().toString()+"','"+supplierBox_pT.getSelectedItem()+"','Purchased Goods','',"+tAmount_field_PT.getText()+","+balanceLeftPT+")");
                            ps.execute();
    //                        </editor-fold>
                        }
                        break;
                    case "Credit":
                        // <editor-fold defaultstate="collapsed" desc="supplierLedgerUpdate">
                                ps=cn.prepareStatement("SELECT sellerId FROM supplierList WHERE sellerName='"+supplierBox_pT.getSelectedItem().toString()+"'");
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    sellerIdPT=rs.getInt(1);
                                }
                                ps=cn.prepareStatement("SELECT TOP 1 balance,recordNo FROM supplierLedger WHERE sellerId="+sellerIdPT+" ORDER BY recordNo DESC");
                                rs=ps.executeQuery();
                                while(rs.next()){
                                    balanceLeftPT=rs.getFloat(1);
                                }
                                balanceLeftPT=balanceLeftPT+Float.parseFloat(tAmount_field_PT.getText());
                                ps=cn.prepareStatement("INSERT INTO supplierLedger VALUES("+sellerIdPT+",'"+dateField_PT.getText()+"',"+transactionNo.getText()+",'Purchased Goods',"+tAmount_field_PT.getText()+",'',"+balanceLeftPT+",0)");
                                ps.execute();
    //            </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Selecting TotalTransactions And TotalPurchaseAmount">
                                    ps=cn.prepareStatement("SELECT totalTransaction,totalPurchaseAm FROM supplierList WHERE sellerName='"+supplierBox_pT.getSelectedItem().toString()+"'");
                                    rs=ps.executeQuery();
                                    while (rs.next()){
                                        prevTransactionsAmount=rs.getInt(1);
                                        prevPurchaseAmount=rs.getFloat(2);
                                    }
                                    prevTransactionsAmount+=1;
                                    prevPurchaseAmount+=Float.parseFloat(tAmount_field_PT.getText());
            //                  </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Updating TotalTransactions And TotalPurchaseAmount">
                            ps=cn.prepareStatement("UPDATE supplierList SET totalTransaction="+prevTransactionsAmount+",totalPurchaseAm="+prevPurchaseAmount+" WHERE sellerName='"+supplierBox_pT.getSelectedItem().toString()+"'");
                            ps.execute();
    //                    </editor-fold>
                        break;
               }

    //                    </editor-fold>
            ps=cn.prepareStatement("SELECT paymentStatus,paymentLeft,delayedDuration FROM purchaseTransaction WHERE transactionNo="+TransactionNo+"");
            rs=ps.executeQuery();

            while(rs.next()){
                tmp=rs.getString(1);
                switch (tmp) {
                    case "Not Paid":
                        paymentStatus_label.setForeground(Color.red);
                        paymentStatus_label.setText("Payment Left : "+rs.getString(2)+" | Transaction Duration : "+rs.getString(3));
                        break;
                    case "Paid":
                        paymentStatus_label.setForeground(Color.GREEN);
                        paymentStatus_label.setText("Payment Paid");
                        break;
                }
            }
    //        </editor-fold>
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
        }

//        </editor-fold>
        pTransaction_table.setEnabled(false);
        pTransaction_table.setForeground(Color.GRAY);
        makeTransaction.setEnabled(false);
    }
    }//GEN-LAST:event_makeTransactionActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
//      Layered Panes Sizing and location  
        width = Main.getWidth();
        
        addSupplier_lpane.setSize(width, addSupplier_lpane.getHeight());
        x=Main.getWidth()/2-(addSupplier_panel.getWidth()/2);
        y=Main.getHeight()/2-(addSupplier_panel.getHeight()/2);
        addSupplier_panel.setLocation(x,y);
        
        addClient_lpane.setSize(width, addClient_lpane.getHeight());
        x1=Main.getWidth()/2-(addClient_panel.getWidth()/2);
        y1=Main.getHeight()/2-(addClient_panel.getHeight()/2);
        addClient_panel.setLocation(x1,y1);
        
        addProduct_lpane.setSize(width, addProduct_lpane.getHeight());
        x2=Main.getWidth()/2-(addProduct_panel.getWidth()/2);
        y2=Main.getHeight()/2-(addProduct_panel.getHeight()/2);
        addProduct_panel.setLocation(x2, y2);
        
        pTransaction_lpane.setSize(width, pTransaction_lpane.getHeight());
        x3=Main.getWidth()/2-(pTransaction_panel.getWidth()/2);
        y3=Main.getHeight()/2-(pTransaction_panel.getHeight()/2);
        pTransaction_panel.setLocation(x3, y3);
        btns_pTransaction.setLocation(x3, y3+pTransaction_panel.getHeight()+10);
        
        dChalan_lpane.setSize(width, dChalan_lpane.getHeight());
        x4=Main.getWidth()/2-(dChalan_panel.getWidth()/2);
        y4=Main.getHeight()/2-(dChalan_panel.getHeight()/2);
        dChalan_panel.setLocation(x4, y4);
        btns_dChalan.setLocation(x4, y4+dChalan_panel.getHeight()+10);
        
        salesInvoice_lpane.setSize(width, salesInvoice_lpane.getHeight());
        x4=Main.getWidth()/2-(salesInvoice_panel.getWidth()/2);
        y4=Main.getHeight()/2-(salesInvoice_panel.getHeight()/2);
        salesInvoice_panel.setLocation(x4, y4);
        btns_salesInvoice.setLocation(x4, y4+salesInvoice_panel.getHeight()+10);
        
        expense_lpane.setSize(width, expense_lpane.getHeight());
        x5=Main.getWidth()/2-(expense_panel.getWidth()/2);
        y5=Main.getHeight()/2-(expense_panel.getHeight()/2);
        expense_panel.setLocation(x5, y5);
        Btns_expense.setLocation(x5, y5+expense_panel.getHeight()+10);
        
        purchaseOrder_lpane.setSize(width, purchaseOrder_lpane.getHeight());
        x6=Main.getWidth()/2-(purchaseOrder_panel.getWidth()/2);
        y6=Main.getHeight()/2-(purchaseOrder_panel.getHeight()/2);
        purchaseOrder_panel.setLocation(x6,y6);
        btns_purchaseOrder.setLocation(x6, y6+purchaseOrder_panel.getHeight()+10);
        
        salesReport_lpane.setSize(width, salesReport_lpane.getHeight());
        x=Main.getWidth()/2-(salesReport_panel.getWidth()/2);
        y=Main.getHeight()/2-(salesReport_panel.getHeight()/2);
        salesReport_panel.setLocation(x,y);
        noteLabel.setLocation(x, y+salesReport_panel.getHeight()+10);
        
        stockReport_lpane.setSize(width, stockReport_lpane.getHeight());
        x=Main.getWidth()/2-(stockReport_panel.getWidth()/2);
        y=Main.getHeight()/2-(stockReport_panel.getHeight()/2);
        stockReport_panel.setLocation(x,y);
        noteLabelStock.setLocation(x, y+stockReport_panel.getHeight()+10);
        
        aigingReport_lpane.setSize(width, aigingReport_lpane.getHeight());
        x=Main.getWidth()/2-(aigingReport_panel.getWidth()/2);
        y=Main.getHeight()/2-(aigingReport_panel.getHeight()/2);
        aigingReport_panel.setLocation(x,y);
        
        thresholdPoint_lpane.setSize(width, thresholdPoint_lpane.getHeight());
        x=Main.getWidth()/2-(thresholdPoint_panel.getWidth()/2);
        y=Main.getHeight()/2-(thresholdPoint_panel.getHeight()/2);
        thresholdPoint_panel.setLocation(x,y);
        
        paymentPaid_lpane.setSize(width, paymentPaid_lpane.getHeight());
        x=Main.getWidth()/2-(paymentPaid_panel.getWidth()/2);
        y=Main.getHeight()/2-(paymentPaid_panel.getHeight()/2);
        paymentPaid_panel.setLocation(x,y);
        
        paymentRecieved_lpane.setSize(width, paymentRecieved_lpane.getHeight());
        x=Main.getWidth()/2-(paymentReceived_panel.getWidth()/2);
        y=Main.getHeight()/2-(paymentReceived_panel.getHeight()/2);
        paymentReceived_panel.setLocation(x,y);
        
        addBankNames_lpane.setSize(width, addBankNames_lpane.getHeight());
        x=Main.getWidth()/2-(addBankNames_panel.getWidth()/2);
        y=Main.getHeight()/2-(addBankNames_panel.getHeight()/2);
        addBankNames_panel.setLocation(x,y);
        
        addExpenseName_lpane.setSize(width, addExpenseName_lpane.getHeight());
        x=Main.getWidth()/2-(addExpenseName_panel.getWidth()/2);
        y=Main.getHeight()/2-(addExpenseName_panel.getHeight()/2);
        addExpenseName_panel.setLocation(x,y);
        
        cashBook_lpane.setSize(width, cashBook_lpane.getHeight());
        x=Main.getWidth()/2-(cashBook_panel.getWidth()/2);
        y=Main.getHeight()/2-(cashBook_panel.getHeight()/2);
        cashBook_panel.setLocation(x,y);
        
        bankBook_lpane.setSize(width, bankBook_lpane.getHeight());
        x=Main.getWidth()/2-(chequeBook_panel.getWidth()/2);
        y=Main.getHeight()/2-(chequeBook_panel.getHeight()/2);
        chequeBook_panel.setLocation(x,y);
        
        salesReportParameter_lpane.setSize(width, salesReportParameter_lpane.getHeight());
        x=Main.getWidth()/2-(salesReportParameter_panel.getWidth()/2);
        y=Main.getHeight()/2-(salesReportParameter_panel.getHeight()/2);
        salesReportParameter_panel.setLocation(x,y);
        
        aigingReportParameter_lpane.setSize(width, aigingReportParameter_lpane.getHeight());
        x=Main.getWidth()/2-(aigingReportParameter_panel.getWidth()/2);
        y=Main.getHeight()/2-(aigingReportParameter_panel.getHeight()/2);
        aigingReportParameter_panel.setLocation(x,y);
        
        ledger_lpane.setSize(width, ledger_lpane.getHeight());
        x=Main.getWidth()/2-(ledger_panel.getWidth()/2);
        y=Main.getHeight()/2-(ledger_panel.getHeight()/2);
        ledger_panel.setLocation(x,y);
        
    }//GEN-LAST:event_formComponentResized

    private void addProduct_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct_btnActionPerformed
        
        // <editor-fold defaultstate="collapsed" desc="Inserting ItemName">
        try{
            if(iName_field.getText().equals("") || unit_field.getText().equals("") || minStockAmount_field.getText().equals("") || iName_field.getText().equals("Name") || unit_field.getText().equals("Unit") || minStockAmount_field.getText().equals("Minimum Stock Amount")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID VALUES TO ALL FIELDS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                ps=cn.prepareStatement("INSERT INTO itemslist (itemName,unit) VALUES('"+iName_field.getText()+"','"+unit_field.getText()+"')");
                ps.execute();
            }

        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(rootPane, "INCORRECT DATA");
        }
//        </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Inserting ItemNoInStock">
        try{
            if(iName_field.getText().equals("") || unit_field.getText().equals("") || minStockAmount_field.getText().equals("") || iName_field.getText().equals("Name") || unit_field.getText().equals("Unit") || minStockAmount_field.getText().equals("Minimum Stock Amount")){}
            else{
                ps=cn.prepareStatement("SELECT itemNo FROM itemsList WHERE itemName='"+iName_field.getText()+"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    temp_itemNo=rs.getInt(1);
                }
                ps=cn.prepareStatement("INSERT INTO stock (itemNo,minStockAmount) VALUES("+temp_itemNo+","+minStockAmount_field.getText()+")");
                ps.execute();
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
        }
//        </editor-fold>   
        iName_field.setText(null);
        unit_field.setText("Unit");
        minStockAmount_field.setText("Minimum Stock Amount");
        unit_field.setForeground(Color.GRAY);
        minStockAmount_field.setForeground(Color.GRAY);
        iName_field.requestFocus();
        getItemNames();

    }//GEN-LAST:event_addProduct_btnActionPerformed

    private void AddClient_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddClient_BtnActionPerformed
        try{
            if(clientName_Field.getText().equals("") || clientName_Field.getText().equals("Client Name") || clientAddress_field.getText().equals("") || clientAddress_field.getText().equals("Address") || clientContactNo_field.getText().equals("") || clientContactNo_field.getText().equals("Contact No") || clientOpeningBalance_field.getText().equals("") || clientOpeningBalance_field.getText().equals("Opening Balance")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID CLIENT NAME !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
            ps=cn.prepareStatement("INSERT INTO clientsList (clientName,address,contact,totalSaleAmount) VALUES('"+clientName_Field.getText()+"','"+clientAddress_field.getText()+"',"+clientContactNo_field.getText()+","+clientOpeningBalance_field.getText()+")");
            ps.execute();
            // <editor-fold defaultstate="collapsed" desc="selectingClientId">
            ps=cn.prepareStatement("SELECT clientId FROM clientsList WHERE clientName='"+clientName_Field.getText()+"'");
            rs=ps.executeQuery();
            while(rs.next()){
                clientId=rs.getInt(1);
            }
//            </editor-fold>
            ps=cn.prepareStatement("INSERT INTO ledger VALUES("+clientId+",GETDATE(),0,'Opening Balance','',"+clientOpeningBalance_field.getText()+","+clientOpeningBalance_field.getText()+",0)");
            ps.execute();
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
        getClientNames();
        clientName_Field.setText(null);
        clientAddress_field.setText("Address");
        clientContactNo_field.setText("Contact No");
        clientOpeningBalance_field.setText("Opening Balance");
        clientAddress_field.setForeground(Color.GRAY);
        clientContactNo_field.setForeground(Color.GRAY);
        clientOpeningBalance_field.setForeground(Color.GRAY);
        clientName_Field.requestFocus();
    }//GEN-LAST:event_AddClient_BtnActionPerformed

    private void addItem_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItem_menuActionPerformed
        Visiblefalse(addClient_lpane, addClient);
        clientName_Field.requestFocus();
        getClientNames();
    }//GEN-LAST:event_addItem_menuActionPerformed

    private void AddSupplier_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddSupplier_BtnActionPerformed
        try{
            if(supplierName_field.getText().equals("") || supplierName_field.getText().equals("Supplier Name") || supplierAddress_field.getText().equals("") || supplierAddress_field.getText().equals("Address") || supplierContactNo_field.getText().equals("") || supplierContactNo_field.getText().equals("Contact No") || supplierOpeningBalance_field.getText().equals("") || supplierOpeningBalance_field.getText().equals("Opening Balance")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID VALUES TO ALL FIELDS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                ps=cn.prepareStatement("INSERT INTO supplierList (sellerName,address,contact,totalPurchaseAm) VALUES('"+supplierName_field.getText()+"','"+supplierAddress_field.getText()+"',"+supplierContactNo_field.getText()+","+supplierOpeningBalance_field.getText()+")");
                ps.execute();
                // <editor-fold defaultstate="collapsed" desc="supplierLedgerUpdate">
                    ps=cn.prepareStatement("SELECT sellerId FROM supplierList WHERE sellerName='"+supplierName_field.getText()+"'");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        sellerId=rs.getInt(1);
                    }
                    ps=cn.prepareStatement("INSERT INTO supplierLedger VALUES("+sellerId+",GETDATE(),0,'Opening Balance',"+supplierOpeningBalance_field.getText()+",'',"+supplierOpeningBalance_field.getText()+",0)");
                    ps.execute();
//            </editor-fold>
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
        supplierName_field.setText(null);
        supplierAddress_field.setText("Address");
        supplierContactNo_field.setText("Contact No");
        supplierOpeningBalance_field.setText("Opening Balance");
        supplierAddress_field.setForeground(Color.GRAY);
        supplierContactNo_field.setForeground(Color.GRAY);
        supplierOpeningBalance_field.setForeground(Color.GRAY);
        supplierName_field.requestFocus();
        getSupplierNames();
    }//GEN-LAST:event_AddSupplier_BtnActionPerformed
Object obj;
TreeSet set;
    private void makeChalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeChalanActionPerformed
        set = new TreeSet();
        repeatFlag=true;
        TableModel tableModel = dChalan_table.getModel() ;
        for(int i=0; i<tableModel.getRowCount();i++){
            if(dChalan_table.getValueAt(i, 0)!=null){
                 obj = tableModel.getValueAt(i, 0);
                if(!set.add(obj)){
                    statusBarLabel.setText("PLEASE DONT REPEAT VALUES !");
                    statusBarLabel.setForeground(Color.red);
                    repeatFlag=false;
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        }
        if(repeatFlag){
            if(!(clientsBox_Dc.getSelectedItem().toString().equals(""))){
                DCitemNo=new int[dChalan_table.getRowCount()];
                int i;
                stockCheckFlag=true;
                stock_finalQuantity1=new int [dChalan_table.getRowCount()];
                // <editor-fold defaultstate="collapsed" desc="Try Block">
                try{
                    for(i=0; i<dChalan_table.getRowCount(); i++){
                        if(dChalan_table.getValueAt(i, 0)!=null && dChalan_table.getValueAt(i, 2)!=null){

                            //<editor-fold defaultstate="collapsed" desc="SelectingItemNos">
                            ps=cn.prepareStatement("SELECT itemNo FROM itemsList WHERE itemName='"+dChalan_table.getValueAt(i, 0)+"'");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                DCitemNo[i]=rs.getInt(1);
                            }
            //                </editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="Selecting Previous Quantity And Amount">
                            ps=cn.prepareStatement("SELECT quantity FROM stock WHERE itemNo="+DCitemNo[i]+"");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                stock_previousQuantity1=rs.getInt(1);
                            }
                            stock_recentQuantity1=Integer.parseInt(dChalan_table.getValueAt(i, 2).toString());
                            stock_finalQuantity1[i]=stock_previousQuantity1-stock_recentQuantity1;
                            if(stock_finalQuantity1[i]<0){
                                JOptionPane.showMessageDialog(dChalan_panel, "You Do Not Have "+dChalan_table.getValueAt(i, 2)+" "+dChalan_table.getValueAt(i, 1)+" of \""+dChalan_table.getValueAt(i, 0).toString()+"\" Kindly Check The Stock");
                                stockCheckFlag=false;
                            }

        //</editor-fold>

                        }
                    }
                        for(i=0; i<dChalan_table.getRowCount(); i++){
                            if(dChalan_table.getValueAt(i, 0)!=null && dChalan_table.getValueAt(i, 2)!=null){
                                // <editor-fold defaultstate="collapsed" desc="Updating Stock">
                                    ps=cn.prepareStatement("UPDATE stock SET quantity="+stock_finalQuantity1[i]+" WHERE itemNo="+DCitemNo[i]+"");
                                    ps.execute();
                                //  </editor-fold>
                                // <editor-fold defaultstate="collapsed" desc="InsertingDataOfDeliveryChalan">
                                    
                JOptionPane.showMessageDialog(profitAmount, dateField_Dc.getText());
                                    ps=cn.prepareStatement("INSERT INTO deliveryChalan VALUES("+chalanNo.getText()+","+orderNo_box.getSelectedItem().toString()+",'"+dateField_Dc.getText()+"','"+clientsBox_Dc.getSelectedItem().toString()+"','"+DCitemNo[i]+"',"+dChalan_table.getValueAt(i, 2).toString()+",0,'No')");
                                    ps.execute();
                    //        </editor-fold>
                            }
                        }

                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
                }
                catch(NullPointerException ex){
                    System.out.println("Null Pointer");
                }
        //        </editor-fold>
                    dChalan_table.setEnabled(false);
                    dChalan_table.setForeground(Color.gray);
                    makeChalan.setEnabled(false);
                    printChalan.setEnabled(true);
            }
        }
        
    }//GEN-LAST:event_makeChalanActionPerformed

    private void clientName_FieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clientName_FieldFocusGained
        focusGain(clientName_Field,"Client Name");
    }//GEN-LAST:event_clientName_FieldFocusGained

    private void supplierName_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierName_fieldFocusGained
        focusGain(supplierName_field,"Supplier Name");
    }//GEN-LAST:event_supplierName_fieldFocusGained

    private void clientName_FieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clientName_FieldFocusLost
        focusLost(clientName_Field, "Client Name");
    }//GEN-LAST:event_clientName_FieldFocusLost

    private void supplierName_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierName_fieldFocusLost
        focusLost(supplierName_field, "Supplier Name");
    }//GEN-LAST:event_supplierName_fieldFocusLost

    private void DeliveryCh_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeliveryCh_menuActionPerformed
        Visiblefalse(dChalan_lpane, deliveryChalan);
        if(!DCflag){
            chalan = new DefaultTableModel(columnNames,10);
            dChalan_table.setModel(chalan);
            TableColumnModel tcma = dChalan_table.getColumnModel();
            tcma.getColumn(0).setMinWidth(170);
            tcma.getColumn(0).setMaxWidth(200);
            clientsBox_Dc.requestFocus();
            chalanNo.setEditable(false);
            DCflag=true;
            dChalan_table.setModel(chalan); 
            prevChNo(chalanNo);
            dChalan_table.setEnabled(true);
            dChalan_table.setForeground(Color.DARK_GRAY);
        }
        inserComboBox(dChalan_table);
        getClientNames(clientsBox_Dc);
        getOrderNos(orderNo_box);
    }//GEN-LAST:event_DeliveryCh_menuActionPerformed

    private void dChalan_tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dChalan_tablePropertyChange
        if (DCflag) {
            
            int itemNumber=0;
            
            dChalan_table.setEditingRow(0);
            dChalan_table.changeSelection(dChalan_table.getSelectedRow(), 0, true, true);
            
            rowIndexDC = dChalan_table.getSelectedRow();
        
            try{    
                if (rowIndexDC>=0){
                
                    // <editor-fold defaultstate="collapsed" desc="UpdatingUnit">
            if(dChalan_table.getValueAt(rowIndexDC,0)!=null){
                ps=cn.prepareStatement("SELECT unit FROM itemsList WHERE itemName='"+dChalan_table.getValueAt(rowIndexDC, 0) +"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    dChalan_table.setValueAt(rs.getString(1), rowIndexDC, 1);
                }       
            }
//            </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="NewRow">
                if(rowIndexDC == dChalan_table.getRowCount()-1 && dChalan_table.getValueAt(rowIndexDC, 1)!=null){
                    chalan.addRow(newRowDC);
                    dChalan_table.setModel(chalan);
                } else {
                }
//                </editor-fold>
            }
            
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(dChalan_panel, ex.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_dChalan_tablePropertyChange

    private void dChalan_tableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dChalan_tableKeyTyped
        colIndexDC=dChalan_table.getSelectedColumn();
        if(dChalan_table.getSelectedColumn()==2){
            if (!Character.isDigit(evt.getKeyChar())) {
                Toolkit.getDefaultToolkit().beep();
                evt.consume();
        }
        }
    }//GEN-LAST:event_dChalan_tableKeyTyped

    private void StockRe_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StockRe_menuActionPerformed
        Visiblefalse(stockReport_lpane, stockReport);
        getdata1();
    }//GEN-LAST:event_StockRe_menuActionPerformed

    private void minStockAmount_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_minStockAmount_fieldKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
        if(minStockAmount_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_minStockAmount_fieldKeyTyped

    private void minStockAmount_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minStockAmount_fieldActionPerformed
        
        // <editor-fold defaultstate="collapsed" desc="Inserting ItemName">
        try{
            if(iName_field.getText().equals("") || unit_field.getText().equals("") || minStockAmount_field.getText().equals("") || iName_field.getText().equals("Name") || unit_field.getText().equals("Unit") || minStockAmount_field.getText().equals("Min Stock Amount")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID VALUES TO ALL FIELDS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                ps=cn.prepareStatement("INSERT INTO itemslist (itemName,unit) VALUES('"+iName_field.getText()+"','"+unit_field.getText()+"')");
                ps.execute();
            }

        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(rootPane, "INCORRECT DATA");
        }
//        </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Inserting ItemNoInStock">
        try{
            if(iName_field.getText().equals("") || unit_field.getText().equals("") || minStockAmount_field.getText().equals("") || iName_field.getText().equals("Name") || unit_field.getText().equals("Unit") || minStockAmount_field.getText().equals("Min Stock Amount") || openingStockQauntiy_field.getText().equals("") || openingStockAmount_field.getText().equals("") || openingStockQauntiy_field.getText().equals("Opening Stock Quantity") || openingStockAmount_field.getText().equals("Opening Stock Amount")){}
            else{
                ps=cn.prepareStatement("SELECT itemNo FROM itemsList WHERE itemName='"+iName_field.getText()+"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    temp_itemNo=rs.getInt(1);
                }
                ps=cn.prepareStatement("INSERT INTO stock VALUES("+temp_itemNo+","+openingStockQauntiy_field.getText()+","+minStockAmount_field.getText()+")");
                ps.execute();
                // <editor-fold defaultstate="collapsed" desc="InsertingDataInProfitTable">
                ps=cn.prepareStatement("INSERT INTO profit VALUES(GETDATE(),"+temp_itemNo+","+openingStockQauntiy_field.getText()+","+Float.parseFloat(openingStockAmount_field.getText())/Float.parseFloat(openingStockQauntiy_field.getText())+","+openingStockQauntiy_field.getText()+","+openingStockAmount_field.getText()+",'0','No')");
                ps.execute();
//        </editor-fold>
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage());
        }
//        </editor-fold>
        iName_field.setText(null);
        unit_field.setText("Unit");
        unit_field.setForeground(Color.GRAY);
        minStockAmount_field.setText("Minimum Stock Amount");
        minStockAmount_field.setForeground(Color.GRAY);
        openingStockQauntiy_field.setText("Opening Stock Quantity");
        openingStockQauntiy_field.setForeground(Color.GRAY);
        openingStockAmount_field.setText("Opening Stock Amount");
        openingStockAmount_field.setForeground(Color.GRAY);
        iName_field.requestFocus();
        getItemNames();

    }//GEN-LAST:event_minStockAmount_fieldActionPerformed

    private void ViewReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewReportActionPerformed

        Visiblefalse(salesReport_lpane, salesReport);
        switch (sReportType) {
            case "clientWise":
                switch(sReportType1){
                    case "summary":
                        getClientSummary();
                        noteLabel.setText("");
                    break;
                    case "detail":
                        getClientDetail();
                        noteLabel.setText("NOTE : CLICK ON THE INVOICE/TRANSACTION NO TO VIEW THAT INVOICE/TRANSACTION");
                    break;
                }
            break;
            case "productsWise":
                switch(sReportType1){
                    case "summary":
                        getProductsDetail();
                        noteLabel.setText("");
                    break;
                    case "detail":
                        getProductsDetail();
                        noteLabel.setText("");
                    break;
                }
            break;
            case "productWise":
                switch(sReportType1){
                    case "summary":
                        getProductSummary();
                        noteLabel.setText("");
                    break;
                    case "detail":
                        getProductDetail();
                        noteLabel.setText("");
                    break;
                }
            break;
            case "supplierWise":
                switch(sReportType1){
                    case "summary":
                        getSupplierSummary();
                        noteLabel.setText("");
                    break;
                    case "detail":
                        getSupplierDetail();
                        noteLabel.setText("NOTE : CLICK ON THE INVOICE/TRANSACTION NO TO VIEW THAT INVOICE/TRANSACTION");
                    break;
                }
            break;
        }
    }//GEN-LAST:event_ViewReportActionPerformed

    private void summaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_summaryActionPerformed
        sReportType1="summary";
        ViewReport.setEnabled(true);
    }//GEN-LAST:event_summaryActionPerformed

    private void detailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailActionPerformed
        sReportType1="detail";
        ViewReport.setEnabled(true);
    }//GEN-LAST:event_detailActionPerformed

    private void viewReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewReportActionPerformed
        Visiblefalse(aigingReport_lpane, agingReport);
        switch(aigingType){
            case "Recievable":
                try{
                    if(agingDuration==0){
                        
                        employeevector25=new Vector<>();
                        
                        ps=cn.prepareStatement("SELECT TOP 1 date,balance,duration FROM clientsList JOIN ledger ON clientsList.clientId=ledger.clientId WHERE clientName='"+aigingReport_comboBox.getSelectedItem().toString()+"' ORDER BY recordNo ASC");
                        rs=ps.executeQuery();
                        if(rs.next()){
                            employee25=new Vector<>();
                            
                            employee25.add("0");
                            employee25.add(rs.getString(1));
                            employee25.add("Opening Balance");
                            employee25.add(rs.getString(2));
                            employee25.add(rs.getString(3));
                        }
                        employeevector25.add(employee25);
                    }
                }
                catch(SQLException e){ 
                }
                getRcvbleAging();
                break;
            case "Payable":
                try{
                    if(agingDuration==0){
                        employeevector24=new Vector<>();
                        ps=cn.prepareStatement("SELECT TOP 1 date,balance,duration FROM supplierList JOIN supplierLedger ON supplierList.sellerId=supplierLedger.sellerId WHERE sellerName='"+aigingReport_comboBox.getSelectedItem().toString()+"' ORDER BY recordNo ASC");
                        rs=ps.executeQuery();
                        if(rs.next()){
                            employee24=new Vector<>();

                            employee24.add("0");
                            employee24.add(rs.getString(1));
                            employee24.add("Opening Balance");
                            employee24.add(rs.getString(2));
                            employee24.add(rs.getString(3));
                        }
                        employeevector24.add(employee24);
                    }
                }
                catch(SQLException e){ 
                }
                getPaybleAging();
                break;
        }
    }//GEN-LAST:event_viewReportActionPerformed

    private void expense_tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expense_tablePropertyChange
        if (ExFlag) {
            expense_table.setEditingRow(0);
            expense_table.changeSelection(expense_table.getSelectedRow(), 0, true, true);
            float res;
            try{    
            rowIndexEx = expense_table.getSelectedRow();
            if (rowIndexEx>=0){
                // <editor-fold defaultstate="collapsed" desc="Amount,TAmount">
                Object a,b;
                for(int i=0; i<expense_table.getRowCount(); i++){
                    a=expense_table.getValueAt(i,1);
                    b=expense_table.getValueAt(i,2);
                    if ((a!= null && b != null)) {
                        if(i==0){
                            fres=0;
                        }
                        res=Float.valueOf(expense_table.getValueAt(i,1).toString())*(Float.valueOf((expense_table.getValueAt(i,2)).toString()));
                        expense_table.setValueAt(res, i, 3);
                        fres+=Float.valueOf(expense_table.getValueAt(i, 3).toString());
                        tAmount_fieldE.setText((String.valueOf(fres)));
                    }
                }
                    expense_table.rowAtPoint(null);
//                </editor-fold>
            }
            }catch(NullPointerException ex){
              System.out.println(ex.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_expense_tablePropertyChange

    private void expense_tableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_expense_tableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_expense_tableKeyPressed

    private void expense_tableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_expense_tableKeyTyped
        rowIndexEx=expense_table.getSelectedColumn();
        if(rowIndexEx == 1 || rowIndexEx == 2){
            if (!Character.isDigit(evt.getKeyChar())) {
                Toolkit.getDefaultToolkit().beep();
                evt.consume();
            }
        }
    }//GEN-LAST:event_expense_tableKeyTyped

    private void addExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpenseActionPerformed
        try{
            int i;
            expenseID = new int[expense_table.getRowCount()];
            for(i=0; i<expense_table.getRowCount(); i++){
                if(expense_table.getValueAt(i, 0)!=null && expense_table.getValueAt(i, 3)!=null){

                    //<editor-fold defaultstate="collapsed" desc="SelectingItemNos">
                    ps=cn.prepareStatement("SELECT expenseID FROM expenseList WHERE expenseName='"+expense_table.getValueAt(i, 0).toString()+"'");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        expenseID[i]=rs.getInt(1);
                    }
    //                </editor-fold>

                }
            }
            for(i=0; i<expense_table.getRowCount(); i++){
                if(expense_table.getValueAt(i, 0)!=null && expense_table.getValueAt(i, 3)!=null){
                    ps=cn.prepareStatement("INSERT INTO expense VALUES('"+dateField_Ex.getText()+"',"+expenseID[i]+",'"+expenseType+"','"+expense_table.getValueAt(i, 0).toString()+"',"+expense_table.getValueAt(i, 1).toString()+","+expense_table.getValueAt(i, 2).toString()+","+expense_table.getValueAt(i, 3).toString()+","+tAmount_fieldE.getText()+")");
                    ps.execute();
                    ps=cn.prepareStatement("SELECT TOP 1 id FROM expense ORDER BY id DESC");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        expenseSheetId=rs.getInt(1);
                    }
                    // <editor-fold defaultstate="collapsed" desc="Books">
                                switch(expenseMode){
                                    case "Cash":
                                        // <editor-fold defaultstate="collapsed" desc="CashBook">
                                        ps=cn.prepareStatement("SELECT TOP 1 balance FROM cashBook ORDER BY Id DESC");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            balanceLeftE=rs.getFloat(1);
                                        }
                                        balanceLeftE=balanceLeftE-Float.parseFloat(tAmount_fieldE.getText());
                                        ps=cn.prepareStatement("INSERT INTO cashBook VALUES('"+dateField_Ex.getText()+"',"+expenseSheetId+",'"+expense_table.getValueAt(i, 0).toString()+"','Expense','',"+tAmount_fieldE.getText()+","+balanceLeftE+")");
                                        ps.execute();
//                                        </editor-fold>
                                        break;
                                    case "Cheque":
                                        // <editor-fold defaultstate="collapsed" desc="bankLedgerUpdate">
                                        ps=cn.prepareStatement("SELECT bankId FROM bankList WHERE bankName='"+bankNameBox_E.getSelectedItem().toString()+"'");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            bankIdEx=rs.getInt(1);
                                        }
                                        ps=cn.prepareStatement("SELECT TOP 1 balance,id FROM bankLedger WHERE bankId="+bankIdEx+" ORDER BY id DESC");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            bankBalanceEx=rs.getFloat(1);
                                        }
                                        bankBalanceEx=bankBalanceEx-Float.parseFloat(tAmount_fieldE.getText());
                                        ps=cn.prepareStatement("INSERT INTO bankLedger VALUES("+bankIdEx+",'"+dateField_Ex.getText()+"',"+expenseSheetId+",'Purchased "+expense_table.getValueAt(i, 0).toString()+"','',"+Float.parseFloat(tAmount_fieldE.getText())+","+bankBalanceEx+")");
                                        ps.execute();
        //            </editor-fold>
                                        // <editor-fold defaultstate="collapsed" desc="expenseLedgerUpdate">
                                        ps=cn.prepareStatement("SELECT expenseId FROM expenseList WHERE expenseName='"+expense_table.getValueAt(i, 0).toString()+"'");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            expenseId=rs.getInt(1);
                                        }
                                        ps=cn.prepareStatement("SELECT TOP 1 balance,id FROM expenseLedger WHERE expenseId="+expenseId+" ORDER BY id DESC");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            expenseBalance=rs.getFloat(1);
                                        }
                                        expenseBalance=expenseBalance+Float.parseFloat(tAmount_fieldE.getText());
                                        ps=cn.prepareStatement("INSERT INTO expenseLedger VALUES("+expenseId+",'"+dateField_Ex.getText()+"',"+expenseSheetId+",'Purchased "+expense_table.getValueAt(i, 0).toString()+"','',"+Float.parseFloat(tAmount_fieldE.getText())+","+expenseBalance+")");
                                        ps.execute();
//                                        </editor-fold>
                                        // <editor-fold defaultstate="collapsed" desc="ChequeBook">
                                        ps=cn.prepareStatement("SELECT TOP 1 balance FROM chequeBook ORDER BY Id DESC");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            balanceLeftE=rs.getFloat(1);
                                        }
                                        balanceLeftE=balanceLeftE-Float.parseFloat(tAmount_fieldE.getText());
                                        ps=cn.prepareStatement("INSERT INTO chequeBook VALUES('"+dateField_Ex.getText()+"',"+expenseSheetId+",'"+bankNameBox_E.getSelectedItem().toString()+"','"+expense_table.getValueAt(i, 0).toString()+"','Expense','','"+tAmount_fieldE.getText()+"',"+balanceLeftE+")");
                                        ps.execute();
//                                        </editor-fold>
                                        break;
                                }
        //                    </editor-fold>
                }
            }
            expense_table.setEnabled(false);
            expense_table.setForeground(Color.GRAY);
            addExpense.setEnabled(false);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(expense_lpane, ex.getLocalizedMessage());
        }
    }//GEN-LAST:event_addExpenseActionPerformed

    private void expense_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expense_menuActionPerformed
        Visiblefalse(expense_lpane, expense);
                        if(!ExFlag){
                            expense1 = new DefaultTableModel(expenseColumnNames,10);
                            expense_table.setModel(expense1);
                            tAmount_fieldE.setText("0.0");
                            ExFlag=true;
                        }
                        getBankNames(bankNameBox_E);
                        inserComboBoxE(expense_table);
    }//GEN-LAST:event_expense_menuActionPerformed

    private void previousChalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousChalanActionPerformed
        ChalanNo=Integer.parseInt(chalanNo.getText())-1;
        prevChalan();
        // <editor-fold defaultstate="collapsed" desc="PrevChalan(ExceptTableDetails)"> 
        try{
            ps=cn.prepareStatement("SELECT chalanNo,orderNo,date,clientName FROM deliveryChalan WHERE chalanNo="+ChalanNo+"");
            rs=ps.executeQuery();
            
            while(rs.next()){
                chalanNo.setText(rs.getString(1));
                orderNo_box.setSelectedItem(rs.getInt(2));
                dateField_Dc.setText(rs.getString(3));
                clientsBox_Dc.setSelectedItem(rs.getObject(4)); 
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
//        </editor-fold>
        dChalan_table.setEnabled(false);
        dChalan_table.setForeground(Color.GRAY);
        makeChalan.setEnabled(false);
    }//GEN-LAST:event_previousChalanActionPerformed

    private void nextChalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextChalanActionPerformed
        // <editor-fold defaultstate="collapsed" desc="FindingLastChNo">   
        try{
            ps=cn.prepareStatement("SELECT TOP 1 chalanNo FROM deliveryChalan ORDER BY chalanNo DESC");
            rs=ps.executeQuery();
            while(rs.next()){
                lastChNo=rs.getInt(1);
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(dChalan_panel, ex.getLocalizedMessage());
        }
//        </editor-fold>
        ChalanNo=Integer.parseInt(chalanNo.getText())+1;
        // <editor-fold defaultstate="collapsed" desc="NextChalan(ExceptTableDetails)"> 
        if(ChalanNo>lastChNo){
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("THIS IS YOUR LAST CHALAN");
                    statusBarLabel.setForeground(Color.BLACK);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
        else {
            prevChalan();
            try{
                ps=cn.prepareStatement("SELECT chalanNo,date,clientName FROM deliveryChalan WHERE chalanNo="+ChalanNo+"");
                rs=ps.executeQuery();

                while(rs.next()){
                    chalanNo.setText(rs.getString(1));
                    dateField_Dc.setText(rs.getString(2));
                    clientsBox_Dc.setSelectedItem(rs.getObject(3));
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
            }
            makeChalan.setEnabled(false);
            dChalan_table.setEnabled(false);
            dChalan_table.setForeground(Color.gray);
        }
//        </editor-fold>
    }//GEN-LAST:event_nextChalanActionPerformed

    private void newChalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newChalanActionPerformed
        chalan = new DefaultTableModel(columnNames,10);
        clientsBox_Dc.requestFocus();
        dChalan_table.setModel(chalan); 
        inserComboBox(dChalan_table);
        prevChNo(chalanNo);
        paymentLeft.setText("");
        dChalan_table.setEnabled(true);
        dChalan_table.setForeground(Color.DARK_GRAY);
        makeChalan.setEnabled(true);
        printChalan.setEnabled(false);
    }//GEN-LAST:event_newChalanActionPerformed

    private void myTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_myTreeMouseClicked
        if(evt.getClickCount() == 2){
            DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode)myTree.getLastSelectedPathComponent();
            if (selectednode == null){
                return;
            }
            String nodeInfo = selectednode.getUserObject().toString();
                switch(nodeInfo){
                    case "Purchase Transaction":
                        Visiblefalse(pTransaction_lpane, purchaseTransaction);
                        if(!Pflag){
                            pTransaction = new DefaultTableModel(columnNamesP,10);
                            pTransaction_table.setModel(pTransaction);
                            TableColumnModel tcm = pTransaction_table.getColumnModel();
                            tcm.getColumn(0).setMinWidth(170);
                            tcm.getColumn(0).setMaxWidth(200);
                            supplierBox_pT.requestFocus();
                            setDate(dateField_PT);
                            prevTrNo(transactionNo);
                            pTransaction_table.setEnabled(true);
                            Pflag=true;
                        }
                        getSupplierNames(supplierBox_pT);
                        inserComboBox(pTransaction_table);
                        getBankNames(bankNameBox_PT);
                        break;
                    case "Purchase Order":
                            Visiblefalse(purchaseOrder_lpane, purchaseOrder);
                            if(!Oflag){
                                prevOrderNo(orderNo);
                                setDate(dateField_O);
                                Order = new DefaultTableModel(columnNamesO,10);
                                purchaseOrder_table.setModel(Order);
                                purchaseOrder_table.setEnabled(true);
                                Oflag=true;
                                clientBox_PO.requestFocus();
                            }
                            getClientNames(clientBox_PO);
                            inserComboBox(purchaseOrder_table);
                        break;
                    case "Delivery Chalan":
                        Visiblefalse(dChalan_lpane, deliveryChalan);
                        if(!DCflag){
                            chalan = new DefaultTableModel(columnNames,10);
                            dChalan_table.setModel(chalan);
                            clientsBox_Dc.requestFocus();
                            prevChNo(chalanNo);
                            paymentLeft.setText("");
                            dChalan_table.setForeground(Color.darkGray);
                            DCflag=true;
                        }
                            inserComboBox(dChalan_table);
                            getClientNames(clientsBox_Dc);
                            getOrderNos(orderNo_box);
                        break;
                    case "Sales Invoice":
                        Visiblefalse(salesInvoice_lpane, salesInvoice);
                        if(!SIFlag){
                            prevInvNo(invoiceNo);
                            clientsBox_SI.requestFocus();
                        }
                            getClientNames(clientsBox_SI);
                            getBankNames(bankNameBox_SI);
                        break;
                    case "Expense":
                        Visiblefalse(expense_lpane, expense);
                        if(!ExFlag){
                            expense1 = new DefaultTableModel(expenseColumnNames,10);
                            expense_table.setModel(expense1);
                            tAmount_fieldE.setText("0.0");
                            ExFlag=true;
                        }
                        getBankNames(bankNameBox_E);
                        inserComboBoxE(expense_table);
                        break;
                    case "Stock Valuation":
                        Visiblefalse(stockReport_lpane, stockReport);
                        getdata1();
                        break;
                    case "Client Wise":
                        Visiblefalse(salesReportParameter_lpane, salesReport_search1);
                        sReportType="clientWise";
                        cOrP.setVisible(true);
                        salesReport_comboBox.setVisible(true);
                        cOrP.setText("Select Client Name");
                        getClientNames(salesReport_comboBox);
                        salesReportType="Sale";
                        break;
                    case "Product Wise":
                        Visiblefalse(salesReportParameter_lpane, salesReport_search1);
                        sReportType="productWise";
                        cOrP.setVisible(true);
                        salesReport_comboBox.setVisible(true);
                        cOrP.setText("Select Product");
                        getItemNames(salesReport_comboBox);
                        salesReportType="";
                        break;
                    case "Supplier Wise":
                        Visiblefalse(salesReportParameter_lpane, salesReport_search1);
                        salesReport_comboBox.removeAllItems();
                        sReportType="supplierWise";
                        cOrP.setVisible(true);
                        salesReport_comboBox.setVisible(true);
                        cOrP.setText("Select Supplier");
                        getSupplierNames(salesReport_comboBox);
                        salesReportType="Purchase";
                        break;
                    case "Products Wise":
                        Visiblefalse(salesReportParameter_lpane, salesReport_search1);
                        salesReport_comboBox.removeAllItems();
                        sReportType="productsWise";
                        cOrP.setVisible(true);
                        salesReport_comboBox.setVisible(true);
                        cOrP.setText("Select Product");
                        getItemNames(salesReport_comboBox);
                        salesReportType="";
                        break;
                    case "Profit Report":
                        Visiblefalse(profitReportPerimeter_lpane, profitRP);
                        break;
                    case "Threshold Point":
                        Visiblefalse(thresholdPoint_lpane, threshold);
                        getThreshold();
                        break;
                    case "UnInvoiced Chalan Report":
                        Visiblefalse(unInvoicedChReport_lpane, unInvoicedChReport);
                        getUnInvoicedCh();
                        break;
                    case "Recievable":
                        Visiblefalse(aigingReportParameter_lpane, agingReport_parameter);
                        getClientNames(aigingReport_comboBox);
                        aigingType="Recievable";
                        break;
                    case "Payable":
                        Visiblefalse(aigingReportParameter_lpane, agingReport_parameter);
                        getSupplierNames(aigingReport_comboBox);
                        aigingType="Payable";
                        break;
                    case "Payment Recieved Voucher":
                        Visiblefalse(paymentRecieved_lpane, paymentRecieved);
                        getPaymentRcvdHistory();
                        getClientNames(clientsBox_rV);
                        prevRcvdVoucherNo();
                        clientsBox_rV.requestFocus();
                        getBankNames(bankNameBox_rV);
                        break;
                    case "Payment Paid Voucher":
                        Visiblefalse(paymentPaid_lpane, paymentPaid);
                        getPaymentPaidHistory();
                        getSupplierNames(supplierBox_pV);
                        prevPaidVoucherNo();
                        supplierBox_pV.requestFocus();
                        getBankNames(bankNameBox_pV);
                        break;
                    case "Bank Name":
                        Visiblefalse(addBankNames_lpane, addBankName);
                        getBankList();
                        bankName_field.requestFocus();
                        break;
                    case "Expense Categories":
                        Visiblefalse(addExpenseName_lpane, addExpenseName);
                        getExpenseList();
                        expenseName_field.requestFocus();
                        break;
                    case "Supplier Name":
                        Visiblefalse(addSupplier_lpane, addSeller);
                        supplierName_field.requestFocus();
                        getSupplierNames();
                        break;
                    case "Client Name":
                        Visiblefalse(addClient_lpane, addClient);
                        clientName_Field.requestFocus();
                        getClientNames();
                        break;
                    case "Product Name":
                        Visiblefalse(addProduct_lpane, addProduct);
                        iName_field.requestFocus();
                        getItemNames();
                        break;
                    case "Cash Book":
                        Visiblefalse(cashBook_lpane, cashBook);
                        getCashBook();
                        break;
                    case "Bank Book":
                        Visiblefalse(bankBook_lpane, chequeBook);
                        getChequeBook();
                        break;
                    case "Supplier Ledger":
                        ledgerType="Supplier";
                        ledger_table.removeAll();
                        Visiblefalse(ledger_lpane, ledger);
                        getSupplierNames(ledgerBox);
                        break;
                    case "Client Ledger":
                        ledgerType="Client";
                        ledger_table.removeAll();
                        Visiblefalse(ledger_lpane, ledger);
                        getClientNames(ledgerBox);
                        break;
                    case "Bank Ledger":
                        ledgerType="Bank";
                        ledger_table.removeAll();
                        Visiblefalse(ledger_lpane, ledger);
                        getBankNames(ledgerBox);
                        break;
                    case "Expense Ledger":
                        ledgerType="Expense";
                        ledger_table.removeAll();
                        Visiblefalse(ledger_lpane, ledger);
                        getExpenseNames(ledgerBox);
                        break;
                }
            }
    }//GEN-LAST:event_myTreeMouseClicked

    private void TextFeildKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFeildKeyTyped
        if(!(Character.isDigit(evt.getKeyChar()))){
            evt.consume();
        }
        else{
            txtfpro(TextFeild);
        }
    }//GEN-LAST:event_TextFeildKeyTyped

    private void B1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B1ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"1");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"1");}
    }//GEN-LAST:event_B1ActionPerformed

    private void B2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B2ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"2");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"2");}
    }//GEN-LAST:event_B2ActionPerformed

    private void B3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B3ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"3");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"3");}
    }//GEN-LAST:event_B3ActionPerformed

    private void B4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B4ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"4");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"4");}
    }//GEN-LAST:event_B4ActionPerformed

    private void B5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B5ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"5");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"5");}
    }//GEN-LAST:event_B5ActionPerformed

    private void B6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B6ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"6");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"6");}
    }//GEN-LAST:event_B6ActionPerformed

    private void B7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B7ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"7");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"7");}
    }//GEN-LAST:event_B7ActionPerformed

    private void B8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B8ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"8");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"8");}
    }//GEN-LAST:event_B8ActionPerformed

    private void B9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B9ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"9");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"9");}
    }//GEN-LAST:event_B9ActionPerformed

    private void B0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B0ActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".") && Temp.length()<=8){
            TextFeild.setText(Temp+"0");}
        else if(Temp.length()<8){
            TextFeild.setText(Temp+"0");}
    }//GEN-LAST:event_B0ActionPerformed

    private void BPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BPointActionPerformed
        txtfpro(TextFeild);
        if(Temp.contains(".")||Temp.length()>=8){
        }
        else{
            TextFeild.setText(Temp+".");}
    }//GEN-LAST:event_BPointActionPerformed

    private void BEquals2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEquals2ActionPerformed
        Temp=TextFeild.getText();
        No2=Float.parseFloat(Temp);
        switch (Ch) {
            case "+":
                {
                    TextFeild.setText(null);
                    Result=No1+No2;
                    String a=Float.toString(Result);
                    TextFeild.setText(a);
                    No1=0;
                    break;
                }
            case "-":
                {
                    TextFeild.setText(null);
                    Result=No1-No2;
                    String a=Float.toString(Result);
                    TextFeild.setText(a);
                    No1=0;
                    break;
                }
            case "*":
                {
                    TextFeild.setText(null);
                    Result=No1*No2;
                    String a=Float.toString(Result);
                    TextFeild.setText(a);
                    break;
                }
            case "/":
                {
                    TextFeild.setText(null);
                    Result=No1/No2;
                    String a=Float.toString(Result);
                    TextFeild.setText(a);
                    break;
                }
            default:
                JOptionPane.showMessageDialog(rootPane, "You have not done any Operation");
                break;
        }

    }//GEN-LAST:event_BEquals2ActionPerformed

    private void BPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BPlusActionPerformed
        Temp=TextFeild.getText();
        No1+=Float.parseFloat(Temp);
        TextFeild.setText(null);
        Ch="+";
    }//GEN-LAST:event_BPlusActionPerformed

    private void BMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BMinusActionPerformed
        Temp=TextFeild.getText();
        if(No1>0)
        No1-= Float.parseFloat(Temp);
        else
        No1= Float.parseFloat(Temp);
        TextFeild.setText(null);
        Ch="-";
    }//GEN-LAST:event_BMinusActionPerformed

    private void BMultiplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BMultiplyActionPerformed
        Temp=TextFeild.getText();
        No1= Float.parseFloat(Temp);
        TextFeild.setText(null);
        Ch="*";
    }//GEN-LAST:event_BMultiplyActionPerformed

    private void BDivideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDivideActionPerformed
        Temp=TextFeild.getText();
        No1= Float.parseFloat(Temp);
        TextFeild.setText(null);
        Ch="/";
    }//GEN-LAST:event_BDivideActionPerformed

    private void CActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CActionPerformed
        TextFeild.setText("0.");
        TextFeild.setForeground(Color.GRAY);
    }//GEN-LAST:event_CActionPerformed

    private void CeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CeActionPerformed
        TextFeild.setText(null);
        Temp=null;
        No1=0;
        No2=0;
        TextFeild.setForeground(Color.GRAY);
        TextFeild.setText("0.");
    }//GEN-LAST:event_CeActionPerformed

    private void BackSpaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackSpaceActionPerformed
        Temp=TextFeild.getText();
        StringBuilder A;
        A=new StringBuilder(Temp);
        if(!TextFeild.getText().equals("0.")){
            if(Temp.length()>1){
                TextFeild.setText(A.deleteCharAt(A.length()-1).toString());
            }
            else{
                TextFeild.setForeground(Color.GRAY);
                TextFeild.setText("0.");
            }
        }
        else if(TextFeild.getText().equals("")){
            TextFeild.setText("0.");
        }
    }//GEN-LAST:event_BackSpaceActionPerformed

    private void TextFeildKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFeildKeyPressed
        switch(evt.getKeyCode()){
            case KeyEvent.VK_ADD:
                evt.consume();
                Temp=TextFeild.getText();
                No1+= Float.parseFloat(Temp);
                TextFeild.setText(null);
                Ch="+";
            break;
                
            case KeyEvent.VK_PLUS:
                evt.consume();
                Temp=TextFeild.getText();
                No1+= Float.parseFloat(Temp);
                TextFeild.setText(null);
                Ch="+";
           break;
                
           case KeyEvent.VK_MINUS:
                evt.consume();
                Temp=TextFeild.getText();
                if(No1>0)
                    No1-= Float.parseFloat(Temp);
                else
                    No1= Float.parseFloat(Temp);
                TextFeild.setText(null);
                Ch="-";
           break;
               
           case KeyEvent.VK_SUBTRACT:
                evt.consume();
                Temp=TextFeild.getText();
                if(No1>0)
                    No1-= Float.parseFloat(Temp);
                else
                    No1= Float.parseFloat(Temp);
                TextFeild.setText(null);
                Ch="-";
           break;
               
           case KeyEvent.VK_MULTIPLY:
                evt.consume();
                Temp=TextFeild.getText();
                if(No1>0)
                    No1*= Float.parseFloat(Temp);
                else
                    No1= Float.parseFloat(Temp);
                TextFeild.setText(null);
                Ch="*";
           break;
           case KeyEvent.VK_DIVIDE:
                evt.consume();
                Temp=TextFeild.getText();
                if(No1>0)
                    No1/= Float.parseFloat(Temp);
                else
                    No1= Float.parseFloat(Temp);
                TextFeild.setText(null);
                Ch="/";
           break;
           case KeyEvent.VK_EQUALS:
                Temp=TextFeild.getText();
                No2=Float.parseFloat(Temp);
                switch (Ch) {
                    case "+":
                        {
                            TextFeild.setText(null);
                            Result=No1+No2;
                            String a=Float.toString(Result);
                            TextFeild.setText(a);
                            No1=0;
                            break;
                        }
                    case "-":
                        {
                            TextFeild.setText(null);
                            Result=No1-No2;
                            String a=Float.toString(Result);
                            TextFeild.setText(a);
                            No1=0;
                            break;
                        }
                    case "*":
                        {
                            TextFeild.setText(null);
                            Result=No1*No2;
                            String a=Float.toString(Result);
                            TextFeild.setText(a);
                            break;
                        }
                    case "/":
                        {
                            TextFeild.setText(null);
                            Result=No1/No2;
                            String a=Float.toString(Result);
                            TextFeild.setText(a);
                            break;
                        }
                    default:
                        JOptionPane.showMessageDialog(rootPane, "You have have not done any Operation");
                        break;           
                }
           break;
        }
    }//GEN-LAST:event_TextFeildKeyPressed

    private void clientsSReport_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientsSReport_menuActionPerformed
        Visiblefalse(salesReportParameter_lpane, salesReport_search1);
        sReportType="clientWise";
        cOrP.setVisible(true);
        salesReport_comboBox.setVisible(true);
        cOrP.setText("Select Client Name");
        getClientNames(salesReport_comboBox);
        salesReportType="Sale";
    }//GEN-LAST:event_clientsSReport_menuActionPerformed

    private void salersSReport_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salersSReport_menuActionPerformed
        Visiblefalse(salesReportParameter_lpane, salesReport_search1);
        sReportType="productWise";
        cOrP.setVisible(true);
        salesReport_comboBox.setVisible(true);
        cOrP.setText("Select Product");
        getItemNames(salesReport_comboBox);
        salesReportType="";
    }//GEN-LAST:event_salersSReport_menuActionPerformed

    private void thirtyPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirtyPActionPerformed
        agingDuration = 30;
    }//GEN-LAST:event_thirtyPActionPerformed

    private void sixtyPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixtyPActionPerformed
        agingDuration = 60;
    }//GEN-LAST:event_sixtyPActionPerformed

    private void nintyPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nintyPActionPerformed
        agingDuration = 90;
    }//GEN-LAST:event_nintyPActionPerformed

    private void hExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hExpenseActionPerformed
        expenseType="Personal Expense";
    }//GEN-LAST:event_hExpenseActionPerformed

    private void oExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oExpenseActionPerformed
        expenseType="Company Expense";
    }//GEN-LAST:event_oExpenseActionPerformed

    private void personalExpenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personalExpenseBtnActionPerformed
        expenseType="Personal";
        getPrevExpense();
    }//GEN-LAST:event_personalExpenseBtnActionPerformed

    private void companyExpenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_companyExpenseBtnActionPerformed
        expenseType="Company";
        getPrevExpense();
    }//GEN-LAST:event_companyExpenseBtnActionPerformed

    private void newExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newExpenseActionPerformed
        expense1 = new DefaultTableModel(expenseColumnNames,10);
        expense_table.setModel(expense1);
        tAmount_fieldE.setText("0.0");
        expense_table.setEnabled(true);
        expense_table.setForeground(Color.DARK_GRAY);
    }//GEN-LAST:event_newExpenseActionPerformed

    private void amountRecieved_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_amountRecieved_fieldFocusGained
        amountRecieved_field.setText(null);
    }//GEN-LAST:event_amountRecieved_fieldFocusGained

    private void paidVoucher_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidVoucher_menuActionPerformed
        Visiblefalse(paymentPaid_lpane, paymentPaid);
        getPaymentPaidHistory();
        getSupplierNames(supplierBox_pV);
        prevPaidVoucherNo();
        supplierBox_pV.requestFocus();
    }//GEN-LAST:event_paidVoucher_menuActionPerformed

    private void rcvdVoucher_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rcvdVoucher_menuActionPerformed
        Visiblefalse(paymentRecieved_lpane, paymentRecieved);
        getPaymentRcvdHistory();
        getClientNames(clientsBox_rV);
        prevRcvdVoucherNo();
        clientsBox_rV.requestFocus();
    }//GEN-LAST:event_rcvdVoucher_menuActionPerformed

    private void cash4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cash4ActionPerformed
        rcvdType="Cash";
        bankNameBox_rV.setEnabled(false);
    }//GEN-LAST:event_cash4ActionPerformed

    private void cheque4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cheque4ActionPerformed
        rcvdType="Cheque";
        bankNameBox_rV.setEnabled(true);
    }//GEN-LAST:event_cheque4ActionPerformed

    private void makeRecivedVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeRecivedVoucherActionPerformed
        try{
            if((Float.parseFloat(amountRecieved_field.getText()) <= 0) || amountRecieved_field.getText().equals("")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID VALUES TO ALL FIELDS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                rcvdPayFinished=false;
                totalRecievedAm=0;
                currentPaymentRcvd=0;
                paymentLeftRV=0;
                updatedPaymentPV=0;
                // <editor-fold defaultstate="collapsed" desc="selectingClientId">
                ps=cn.prepareStatement("SELECT clientId FROM clientsList WHERE clientName='"+clientsBox_rV.getSelectedItem().toString()+"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    clientId=rs.getInt(1);
                }
    //            </editor-fold>
                ps=cn.prepareStatement("SELECT TOP 1 balance FROM ledger WHERE clientId="+clientId+" ORDER BY recordNo DESC");
                rs=ps.executeQuery();
                    if(rs.next()){
                        clientBalance=rs.getFloat(1);
                    }
                currentPaymentRcvd= Float.parseFloat(amountRecieved_field.getText());
                    // <editor-fold defaultstate="collapsed" desc="MinusPaymentLeft">
                while(!rcvdPayFinished){
                    if(clientBalance>0){
                        ps=cn.prepareStatement("SELECT TOP 1 invoiceNo,paymentLeft FROM salesInvoice WHERE clientName='"+clientsBox_rV.getSelectedItem().toString()+"' AND paymentStatus='Not Recieved' ORDER BY invoiceNo ASC");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            firsCreditChNo=rs.getInt(1);
                            paymentLeftRV=rs.getFloat(2);
                        }
                        if(currentPaymentRcvd<paymentLeftRV){
                            updatedPaymentRV=paymentLeftRV-currentPaymentRcvd;
                            ps=cn.prepareStatement("UPDATE salesInvoice SET paymentLeft="+updatedPaymentRV+" WHERE invoiceNo="+firsCreditChNo+"");
                            ps.execute();
                            rcvdPayFinished=true;
                        }
                        else if(currentPaymentRcvd==paymentLeftRV){
                            ps=cn.prepareStatement("UPDATE salesInvoice SET paymentLeft=0,paymentStatus='Recieved' WHERE invoiceNo="+firsCreditChNo+"");
                            ps.execute();
                            rcvdPayFinished=true;
                        }
                        else{
                            clientBalance-=paymentLeftRV;
                            currentPaymentRcvd=currentPaymentRcvd-paymentLeftRV;
                            ps=cn.prepareStatement("UPDATE salesInvoice SET paymentLeft=0,paymentStatus='Recieved' WHERE invoiceNo="+firsCreditChNo+"");
                            ps.execute();
                        }
                    }
                    else{
                        rcvdPayFinished=true;}
                }
        //        </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="RecordOfPayRcvd">
                    switch(rcvdType){
                        case "Cheque":
                            ps=cn.prepareStatement("INSERT INTO rcvdVoucher VALUES(GETDATE(),"+clientId+","+amountRecieved_field.getText()+",'"+rcvdType+"','"+bankNameBox_rV.getSelectedItem().toString()+"')");
                            ps.execute();
                            break;
                        case "Cash":
                            ps=cn.prepareStatement("INSERT INTO rcvdVoucher VALUES(GETDATE(),"+clientId+","+amountRecieved_field.getText()+",'"+rcvdType+"','Cash Recieved')");
                            ps.execute();
                            break;
                    }


        //        </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="totalPayRcvd update of particular client">
                    ps=cn.prepareStatement("SELECT totalRcvAm,totalSaleAmount FROM clientsList WHERE clientId="+clientId+"");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        totalRecievedAm=rs.getFloat(1);
                        totalSaleAm=rs.getFloat(2);
                    }
                    totalRecievedAm+=Float.parseFloat(amountRecieved_field.getText());
                    ps=cn.prepareStatement("UPDATE clientsList SET totalRcvAm ="+totalRecievedAm+" WHERE clientId ="+clientId+"");
                    ps.execute();
        //        </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="ledgerUpdate">
                    clientBalance=clientBalance-currentPaymentRcvd;
                    ps=cn.prepareStatement("INSERT INTO ledger VALUES("+clientId+",GETDATE(),"+rcvdVNo.getText()+",'Amount Recieved',"+amountRecieved_field.getText()+",'',"+clientBalance+",0)");
                    ps.execute();
        //            </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="Books">
                            switch(rcvdType){
                                case "Cash":
                                    // <editor-fold defaultstate="collapsed" desc="CashBook">
                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM cashBook ORDER BY Id DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        balanceLeftRV=rs.getFloat(1);
                                    }
                                    balanceLeftRV=balanceLeftRV+Float.parseFloat(amountRecieved_field.getText());
                                    ps=cn.prepareStatement("INSERT INTO cashBook VALUES(GETDATE(),"+Integer.parseInt(rcvdVNo.getText())+",'"+clientsBox_rV.getSelectedItem().toString()+"','Amount Recieved',"+amountRecieved_field.getText()+",'',"+balanceLeftRV+")");
                                    ps.execute();
//                                    </editor-fold>
                                    break;
                                case "Cheque":
                                    // <editor-fold defaultstate="collapsed" desc="bankLedgerUpdate">
                                    ps=cn.prepareStatement("SELECT bankId FROM bankList WHERE bankName='"+bankNameBox_rV.getSelectedItem().toString()+"'");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        bankIdRV=rs.getInt(1);
                                    }
                                    ps=cn.prepareStatement("SELECT TOP 1 balance,id FROM bankLedger WHERE bankId="+bankIdRV+" ORDER BY id DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        bankBalanceRV=rs.getFloat(1);
                                    }
                                    bankBalanceRV=bankBalanceRV+Float.parseFloat(amountRecieved_field.getText());
                                    ps=cn.prepareStatement("INSERT INTO bankLedger VALUES("+bankIdRV+",GETDATE(),"+rcvdVNo.getText()+",'Amount Recived',"+amountRecieved_field.getText()+",'',"+bankBalanceRV+")");
                                    ps.execute();
                        //            </editor-fold>
                                    // <editor-fold defaultstate="collapsed" desc="ChequeBook">
                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM chequeBook ORDER BY Id DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        balanceLeftRV=rs.getFloat(1);
                                    }
                                    balanceLeftRV=balanceLeftRV+Float.parseFloat(amountRecieved_field.getText());
                                    ps=cn.prepareStatement("INSERT INTO chequeBook VALUES(GETDATE(),"+Integer.parseInt(rcvdVNo.getText())+",'"+bankNameBox_rV.getSelectedItem().toString()+"','"+clientsBox_rV.getSelectedItem().toString()+"','Amount Recieved',"+amountRecieved_field.getText()+",'',"+balanceLeftRV+")");
                                    ps.execute();
//                                    </editor-fold>
                                    break;
                            }
    //                    </editor-fold>
//                else{
//                    // <editor-fold defaultstate="collapsed" desc="RecordOfPayRcvd">
//                    switch(rcvdType){
//                        case "Cheque":
//                            ps=cn.prepareStatement("INSERT INTO rcvdVoucher VALUES(GETDATE(),"+clientId+","+amountRecieved_field.getText()+",'"+rcvdType+"','"+bankNameBox_rV.getSelectedItem().toString()+"')");
//                            ps.execute();
//                            break;
//                        case "Cash":
//                            ps=cn.prepareStatement("INSERT INTO rcvdVoucher VALUES(GETDATE(),"+clientId+","+amountRecieved_field.getText()+",'"+rcvdType+"','Cash Recieved')");
//                            ps.execute();
//                            break;
//                    }
//
//
//        //        </editor-fold>
//                    // <editor-fold defaultstate="collapsed" desc="totalPayRcvd update of particular client">
//                    ps=cn.prepareStatement("SELECT totalRcvAm,totalSaleAmount FROM clientsList WHERE clientId="+clientId+"");
//                    rs=ps.executeQuery();
//                    while(rs.next()){
//                        totalRecievedAm=rs.getFloat(1);
//                        totalSaleAm=rs.getFloat(2);
//                    }
//                    totalRecievedAm+=Float.parseFloat(amountRecieved_field.getText());
//                    ps=cn.prepareStatement("UPDATE clientsList SET totalRcvAm ="+totalRecievedAm+" WHERE clientId ="+clientId+"");
//                    ps.execute();
//        //        </editor-fold>
//                    // <editor-fold defaultstate="collapsed" desc="ledgerUpdate">
//                    clientBalance=clientBalance-currentPaymentRcvd;
//                    ps=cn.prepareStatement("INSERT INTO ledger VALUES("+clientId+",GETDATE(),"+rcvdVNo.getText()+",'Amount Recieved',"+amountRecieved_field.getText()+",'',"+clientBalance+",0)");
//                    ps.execute();
//        //            </editor-fold>
//                    // <editor-fold defaultstate="collapsed" desc="Books">
//                            switch(rcvdType){
//                                case "Cash":
//                                    // <editor-fold defaultstate="collapsed" desc="CashBook">
//                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM cashBook ORDER BY Id DESC");
//                                    rs=ps.executeQuery();
//                                    while(rs.next()){
//                                        balanceLeftRV=rs.getFloat(1);
//                                    }
//                                    balanceLeftRV=balanceLeftRV+Float.parseFloat(amountRecieved_field.getText());
//                                    ps=cn.prepareStatement("INSERT INTO cashBook VALUES(GETDATE(),"+Integer.parseInt(rcvdVNo.getText())+",'"+clientsBox_rV.getSelectedItem().toString()+"','Amount Recieved',"+amountRecieved_field.getText()+",'',"+balanceLeftRV+")");
//                                    ps.execute();
////                                    </editor-fold>
//                                    break;
//                                case "Cheque":
//                                    // <editor-fold defaultstate="collapsed" desc="bankLedgerUpdate">
//                                    ps=cn.prepareStatement("SELECT bankId FROM bankList WHERE bankName='"+bankNameBox_rV.getSelectedItem().toString()+"'");
//                                    rs=ps.executeQuery();
//                                    while(rs.next()){
//                                        bankIdRV=rs.getInt(1);
//                                    }
//                                    ps=cn.prepareStatement("SELECT TOP 1 balance,id FROM bankLedger WHERE bankId="+bankIdRV+" ORDER BY id DESC");
//                                    rs=ps.executeQuery();
//                                    while(rs.next()){
//                                        bankBalanceRV=rs.getFloat(1);
//                                    }
//                                    bankBalanceRV=bankBalanceRV+Float.parseFloat(amountRecieved_field.getText());
//                                    ps=cn.prepareStatement("INSERT INTO bankLedger VALUES("+bankIdRV+",GETDATE(),"+rcvdVNo.getText()+",'Amount Recived From "+clientsBox_rV.getSelectedItem().toString()+"',"+amountRecieved_field.getText()+",'',"+bankBalanceRV+")");
//                                    ps.execute();
//                        //            </editor-fold>
//                                    // <editor-fold defaultstate="collapsed" desc="ChequeBook">
//                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM chequeBook ORDER BY Id DESC");
//                                    rs=ps.executeQuery();
//                                    while(rs.next()){
//                                        balanceLeftRV=rs.getFloat(1);
//                                    }
//                                    balanceLeftRV=balanceLeftRV+Float.parseFloat(amountRecieved_field.getText());
//                                    ps=cn.prepareStatement("INSERT INTO chequeBook VALUES(GETDATE(),"+Integer.parseInt(rcvdVNo.getText())+",'"+bankNameBox_rV.getSelectedItem().toString()+"','"+clientsBox_rV.getSelectedItem().toString()+"','Amount Recieved',"+amountRecieved_field.getText()+",'',"+balanceLeftRV+")");
//                                    ps.execute();
////                                    </editor-fold>
//                                    break;
//                            }
//    //                    </editor-fold>
//                }
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(paymentRecieved_lpane, ex.getLocalizedMessage());
        }
        amountRecieved_field.setText("0.0");
        prevRcvdVoucherNo();
        getPaymentPaidHistory();
        getPaymentRcvdHistory();
        clientsBox_rV.requestFocus();
    }//GEN-LAST:event_makeRecivedVoucherActionPerformed

    private void pTransaction_tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_pTransaction_tablePropertyChange
        if (Pflag) {
            pTransaction_table.setEditingRow(0);
            pTransaction_table.changeSelection(pTransaction_table.getSelectedRow(), 0, true, true);

            try{
            rowIndexP = pTransaction_table.getSelectedRow();
            if (rowIndexP>=0){
                
                // <editor-fold defaultstate="collapsed" desc="UpdatingUnit">
            if(!(pTransaction_table.getValueAt(rowIndexP,0) == null)){
                ps=cn.prepareStatement("Select unit from itemsList where itemName='"+pTransaction_table.getValueAt(rowIndexP, 0) +"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    pTransaction_table.setValueAt(rs.getString(1), rowIndexP, 1);
                }       
            }
//            </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="Amount,TAmount,NewRow">    
            if (pTransaction_table.getValueAt(rowIndexP,2) != null && pTransaction_table.getValueAt(rowIndexP,3) != null) {
                
                float res=Float.valueOf(pTransaction_table.getValueAt(rowIndexP,2).toString())*(Float.valueOf((pTransaction_table.getValueAt(rowIndexP,3)).toString()));
                pTransaction_table.setValueAt(res, rowIndexP, 4);
                for(int i=0; i<pTransaction_table.getRowCount(); i++){
                    if (pTransaction_table.getValueAt(i,2) != null && pTransaction_table.getValueAt(i,3) != null) {
                        if(i==0){
                            fres=0;
                        }
                        if (!(pTransaction_table.getValueAt(i,4) == null)) {
                            fres+=Float.valueOf(pTransaction_table.getValueAt(i, 4).toString());
                            tAmount_field_PT.setText((String.valueOf(fres)));
                        }
                    }
                }
                if(!(pTransaction_table.getValueAt(pTransaction_table.getRowCount()-1,3) == null)){
                    pTransaction.addRow(newRowPT);
                    pTransaction_table.setModel(pTransaction);
                }
                
                
            }
//            </editor-fold>
                
            }
            
            }catch(SQLException | NumberFormatException ex){
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_pTransaction_tablePropertyChange

    private void printChalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printChalanActionPerformed
        Map parameter = new HashMap();
        parameter.put("chalanNo", Integer.parseInt(chalanNo.getText()));
        Dimension d = new Dimension(1024, 768);
        ReportView r = new ReportView();
        r.showReport(cn, parameter, "deliveryChalan.jrxml", "Delivery Chalan", d);
    }//GEN-LAST:event_printChalanActionPerformed

    private void viewLedger_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewLedger_ButtonActionPerformed
        switch(ledgerType){
            case "Client":
                getClientLedger();
                break;
            case "Supplier":
                getSupplierLedger();
                break;
            case "Bank":
                getBankLedger();
                break;
            case "Expense":
                getExpenseLedger();
                break;
        }
    }//GEN-LAST:event_viewLedger_ButtonActionPerformed

    private void ViewLedgerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewLedgerActionPerformed
        Visiblefalse(ledger_lpane, ledger);
        getClientNames(ledgerBox);
    }//GEN-LAST:event_ViewLedgerActionPerformed

    private void clientLedger_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientLedger_menuActionPerformed
        ledgerType="Client";
        Visiblefalse(ledger_lpane, ledger);
        getClientNames(ledgerBox);
    }//GEN-LAST:event_clientLedger_menuActionPerformed

    private void aigingReport_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aigingReport_tableMouseClicked
        tmp="";
        int a = aigingReport_table.getSelectedRow();
        int c = aigingReport_table.getSelectedColumn();
        int ab;
        String b;
        if(c==0){
            switch(aigingType){
            case "Recievable":
                ab =JOptionPane.showConfirmDialog(aigingReport_panel, "WANT TO VIEW THIS INVOICE | CLICK YES TO VIEW");
                if(ab == JOptionPane.YES_OPTION){
                    b = String.valueOf(aigingReport_table.getValueAt(a, 0));
                    Visiblefalse(salesInvoice_lpane, salesInvoice);
                    InvoiceNo=Integer.parseInt(b);
                    getClientNames(clientsBox_SI);
                    // <editor-fold defaultstate="collapsed" desc="PrevInvoice(ExceptTableDetails)"> 
                    try{
                        ps=cn.prepareStatement("SELECT invoiceNo,date,clientName,tAmount,paymentStatus,paymentLeft,delayedDuration FROM salesInvoice WHERE invoiceNo="+InvoiceNo+"");
                        rs=ps.executeQuery();

                        while(rs.next()){
                            invoiceNo.setText(rs.getString(1));
                            dateField_SI.setText(rs.getString(2));
                            clientsBox_SI.addItem(rs.getObject(3));
                            tAmount_field_SI.setText(rs.getString(4));
                            tmp=rs.getString(5);
                            switch (tmp){
                                case "Recieved":
                                    paymentLeft.setForeground(Color.GREEN);
                                    paymentLeft.setText("Payment Recieved");
                                break;
                                case "Not Recieved":
                                    paymentLeft.setForeground(Color.red);
                                    paymentLeft.setText("Payment Left : "+rs.getString(6)+" | Invoice Duration : "+rs.getString(7)+" Days");
                                break;
                                default:
                            }
                        }
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
                    }
    //        </editor-fold>
                    salesInvoice_table.setEnabled(false);
                    makeInvoice.setEnabled(false);
                    nextInvoice.setEnabled(true);
                }
                break;
            case "Payable":
                ab =JOptionPane.showConfirmDialog(aigingReport_panel, "WANT TO VIEW THIS TRANSACTION\nCLICK YES TO VIEW");
                if(ab == JOptionPane.YES_OPTION){
                    b = String.valueOf(aigingReport_table.getValueAt(a, 0));
                    Visiblefalse(pTransaction_lpane, purchaseTransaction);
                    TransactionNo=Integer.parseInt(b);
                    prevTransaction();
                    getSupplierNames(supplierBox_pT);
                    // <editor-fold defaultstate="collapsed" desc="PrevTransaction(ExceptTableDetails)">
        try{

            ps=cn.prepareStatement("SELECT transactionNo,date,sellerName,tAmount,paymentStatus,paymentLeft,delayedDuration FROM purchaseTransaction WHERE transactionNo="+TransactionNo+"");
            rs=ps.executeQuery();

            while(rs.next()){
                transactionNo.setText(rs.getString(1));
                dateField_PT.setText(rs.getString(2));
                supplierBox_pT.setSelectedItem(rs.getObject(3));
                tAmount_field_PT.setText(rs.getString(4));
                tmp=rs.getString(5);
                switch(tmp){
                    case "Paid":
                        paymentStatus_label.setForeground(Color.GREEN);
                        paymentStatus_label.setText("Payment Paid");
                        break;
                    case "Not Paid":
                        paymentStatus_label.setForeground(Color.red);
                        paymentStatus_label.setText("Payment Left : "+rs.getString(6)+" | Transaction Duration : "+rs.getString(7));
                        break;    
                }
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
        pTransaction_table.setEnabled(false);
        //        </editor-fold>
                    pTransaction_table.setEnabled(false);
                }
        }
            
        }
    }//GEN-LAST:event_aigingReport_tableMouseClicked

    private void newTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTransactionActionPerformed
        pTransaction = new DefaultTableModel(columnNamesP,10);
        pTransaction_table.setModel(pTransaction);
        inserComboBox(pTransaction_table);
        supplierBox_pT.requestFocus();
        getSupplierNames(supplierBox_pT);
        prevTrNo(transactionNo);
        pTransaction_table.setEnabled(true);
        Pflag=true;
        tAmount_field_PT.setText("0.0");
        paymentStatus_label.setText("");
        makeTransaction.setEnabled(true);
        pTransaction_table.setForeground(Color.DARK_GRAY);
    }//GEN-LAST:event_newTransactionActionPerformed

    private void salesReport_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesReport_tableMouseClicked
        int a = salesReport_table.getSelectedRow();
        int c = salesReport_table.getSelectedColumn();
        int ab;
        String b;
        tmp="";
        if(c==0){
            switch(salesReportType){
            case "Sale":
                ab =JOptionPane.showConfirmDialog(salesReport_panel, "WANT TO VIEW THIS INVOICE?\nCLICK YES TO VIEW");
                if(ab == JOptionPane.YES_OPTION){
                    b = String.valueOf(salesReport_table.getValueAt(a, 0));
                    Visiblefalse(salesInvoice_lpane, salesInvoice);
                    InvoiceNo=Integer.parseInt(b);
                    getClientNames(clientsBox_SI);
                    prevInvoice();
                    // <editor-fold defaultstate="collapsed" desc="PrevInvoice(ExceptTableDetails)"> 
                    try{
                        ps=cn.prepareStatement("SELECT invoiceNo,date,clientName,tAmount,paymentStatus,paymentLeft,delayedDuration FROM salesInvoice WHERE invoiceNo="+InvoiceNo+"");
                        rs=ps.executeQuery();

                        while(rs.next()){
                            invoiceNo.setText(rs.getString(1));
                            dateField_SI.setText(rs.getString(2));
                            clientsBox_SI.addItem(rs.getObject(3));
                            tAmount_field_SI.setText(rs.getString(4));
                            tmp=rs.getString(5);
                            switch (tmp){
                                case "Recieved":
                                    paymentLeft.setForeground(Color.GREEN);
                                    paymentLeft.setText("Payment Recieved");
                                break;
                                case "Not Recieved":
                                    paymentLeft.setForeground(Color.red);
                                    paymentLeft.setText("Payment Left : "+rs.getString(6)+" | Invoice Duration : "+rs.getString(7)+" Days");
                                break;
                            }
                        }
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
                    }
    //        </editor-fold>
                    salesInvoice_table.setEnabled(false);
                    makeInvoice.setEnabled(false);
                    salesInvoice_table.setForeground(Color.GRAY);
                }
                break;
            case "Purchase":
                ab =JOptionPane.showConfirmDialog(aigingReport_panel, "WANT TO VIEW THIS TRANSACTION\nCLICK YES TO VIEW");
                if(ab == JOptionPane.YES_OPTION){
                    b = String.valueOf(salesReport_table.getValueAt(a, 0));
                    Visiblefalse(pTransaction_lpane, purchaseTransaction);
                    TransactionNo=Integer.parseInt(b);
                    prevTransaction();
                    getSupplierNames(supplierBox_pT);
                    // <editor-fold defaultstate="collapsed" desc="PrevTransaction(ExceptTableDetails)">
                    try{
                        ps=cn.prepareStatement("SELECT transactionNo,date,sellerName,tAmount,paymentStatus,paymentLeft,delayedDuration FROM purchaseTransaction WHERE transactionNo="+TransactionNo+"");
                        rs=ps.executeQuery();

                        while(rs.next()){
                            transactionNo.setText(rs.getString(1));
                            dateField_PT.setText(rs.getString(2));
                            supplierBox_pT.setSelectedItem(rs.getObject(3));
                            tAmount_field_PT.setText(rs.getString(4));
                            tmp=rs.getString(5);
                            switch (tmp) {
                                case "Not Paid":
                                    paymentStatus_label.setForeground(Color.red);
                                    paymentStatus_label.setText("Payment Left : "+rs.getString(6)+" | Transaction Duration : "+rs.getString(7)+" Days");
                                    break;
                                case "Paid":
                                    paymentStatus_label.setForeground(Color.GREEN);
                                    paymentStatus_label.setText("Payment Paid");
                                    break;
                            }
                        }

                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
                    }
        //        </editor-fold>
                    makeTransaction.setEnabled(false);
                    pTransaction_table.setEnabled(false);
                    pTransaction_table.setForeground(Color.GRAY);
                }
                break;
        }
            
        }
    }//GEN-LAST:event_salesReport_tableMouseClicked

    private void makePaidVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makePaidVoucherActionPerformed
        try{
            if((Float.parseFloat(amountPaid_field.getText()) <= 0) || amountPaid_field.getText().equals("")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID VALUES TO ALL FIELDS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                
                paidPayFinished=false;
                totalPurchaseAm=0;
                totalPaidAm=0;
                currentPaymentPaid=0;
                paymentLeftPV=0;
                updatedPaymentPV=0;
                // <editor-fold defaultstate="collapsed" desc="selectingSupplierId">
                    ps=cn.prepareStatement("SELECT sellerId FROM supplierList WHERE sellerName='"+supplierBox_pV.getSelectedItem().toString()+"'");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        sellerId=rs.getInt(1);
                    }
                //            </editor-fold>
                ps=cn.prepareStatement("SELECT TOP 1 balance FROM supplierLedger WHERE sellerId="+sellerId+" ORDER BY recordNo DESC");
                rs=ps.executeQuery();
                        if(rs.next()){
                            supplierBalance=rs.getFloat(1);
                        }
                        currentPaymentPaid= Float.parseFloat(amountPaid_field.getText());
                    // <editor-fold defaultstate="collapsed" desc="MinusPaymentLeft">
                while(!paidPayFinished){
                    
                    if(supplierBalance>0){
                        
                        ps=cn.prepareStatement("SELECT TOP 1 transactionNo,paymentLeft FROM purchaseTransaction WHERE sellerName='"+supplierBox_pV.getSelectedItem().toString()+"' AND paymentStatus='Not Paid' ORDER BY transactionNo ASC");
                        rs=ps.executeQuery();
                        if(rs.next()){
                            firstCreditPtId=rs.getInt(1);
                            paymentLeftPV=rs.getFloat(2);
                        }
                        if(currentPaymentPaid<paymentLeftPV){
                            updatedPaymentPV=paymentLeftPV-currentPaymentPaid;
                            ps=cn.prepareStatement("UPDATE purchaseTransaction SET paymentLeft="+updatedPaymentPV+" WHERE transactionNo="+firstCreditPtId+"");
                            ps.execute();
                            paidPayFinished=true;
                        }
                        else if(currentPaymentPaid==paymentLeftPV){
                            ps=cn.prepareStatement("UPDATE purchaseTransaction SET paymentLeft=0,paymentStatus='Paid' WHERE transactionNo="+firstCreditPtId+"");
                            ps.execute();
                            paidPayFinished=true;
                        }
                        else{
                            supplierBalance-=paymentLeftPV;
                            currentPaymentPaid=currentPaymentPaid-paymentLeftPV;
                            ps=cn.prepareStatement("UPDATE purchaseTransaction SET paymentLeft=0,paymentStatus='Paid' WHERE transactionNo="+firstCreditPtId+"");
                            ps.execute();
                        }
                    }
                    else{
                        paidPayFinished=true;
                    }
                }
                //        </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="RcdOfPayPaid">

                    switch(paidType){
                        case "Cheque":
                            ps=cn.prepareStatement("INSERT INTO paidVoucher VALUES(GETDATE(),"+sellerId+","+Float.parseFloat(amountPaid_field.getText())+",'"+paidType+"','"+bankNameBox_pV.getSelectedItem().toString()+"')");
                            ps.execute();
                            break;
                        case "Cash":
                            ps=cn.prepareStatement("INSERT INTO paidVoucher VALUES(GETDATE(),"+sellerId+","+Float.parseFloat(amountPaid_field.getText())+",'"+paidType+"','Cash Paid')");
                            ps.execute();
                            break;
                    }

                    //        </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="totalPayPaidUpdateOfParticularSUPPLIERr">
                        ps=cn.prepareStatement("SELECT totalPaidAm,totalPurchaseAm FROM supplierList WHERE sellerId="+sellerId+"");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            totalPaidAm=rs.getFloat(1);
                            totalPurchaseAm=rs.getFloat(2);
                        }
                        totalPaidAm+=Float.parseFloat(amountPaid_field.getText());
                        ps=cn.prepareStatement("UPDATE supplierList SET totalPaidAm ="+totalPaidAm+" WHERE sellerId ="+sellerId+"");
                        ps.execute();
                    //        </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="ledgerUpdate">
                        supplierBalance=supplierBalance-currentPaymentPaid;
                        ps=cn.prepareStatement("INSERT INTO supplierLedger VALUES("+sellerId+",GETDATE(),"+paidVoucherNo.getText()+",'Amount Paid','',"+amountPaid_field.getText()+","+supplierBalance+",0)");
                        ps.execute();
                    //            </editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="Books">
                            switch(paidType){
                                case "Cash":
                                    // <editor-fold defaultstate="collapsed" desc="Cash">
                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM cashBook ORDER BY Id DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        balanceLeftPV=rs.getFloat(1);
                                    }
                                    balanceLeftPV=balanceLeftPV-Float.parseFloat(amountPaid_field.getText());
                                    ps=cn.prepareStatement("INSERT INTO cashBook VALUES(GETDATE(),"+Integer.parseInt(paidVoucherNo.getText())+",'"+supplierBox_pV.getSelectedItem().toString()+"','Amount Paid','',"+amountPaid_field.getText()+","+balanceLeftPV+")");
                                    ps.execute();
//                                    </editor-fold>
                                    break;
                                case "Cheque":
                                    // <editor-fold defaultstate="collapsed" desc="bankLedgerUpdate">
                                    ps=cn.prepareStatement("SELECT bankId FROM bankList WHERE bankName='"+bankNameBox_pV.getSelectedItem().toString()+"'");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        bankIdPV=rs.getInt(1);
                                    }
                                    ps=cn.prepareStatement("SELECT TOP 1 balance,id FROM bankLedger WHERE bankId="+bankIdPV+" ORDER BY id DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        bankBalancePV=rs.getFloat(1);
                                    }
                                    bankBalancePV=bankBalancePV-Float.parseFloat(amountPaid_field.getText());
                                    ps=cn.prepareStatement("INSERT INTO bankLedger VALUES("+bankIdPV+",GETDATE(),"+paidVoucherNo.getText()+",'Paid Amount To "+supplierBox_pV.getSelectedItem().toString()+"','',"+amountPaid_field.getText()+","+bankBalancePV+")");
                                    ps.execute();
//            </editor-fold>
                                    // <editor-fold defaultstate="collapsed" desc="Cheque">
                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM chequeBook ORDER BY Id DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        balanceLeftPV=rs.getFloat(1);
                                    }
                                    balanceLeftPV=balanceLeftPV-Float.parseFloat(amountPaid_field.getText());
                                    ps=cn.prepareStatement("INSERT INTO chequeBook VALUES(GETDATE(),"+Integer.parseInt(paidVoucherNo.getText())+",'"+bankNameBox_pV.getSelectedItem().toString()+"','"+supplierBox_pV.getSelectedItem().toString()+"','Amount Paid','',"+amountPaid_field.getText()+","+balanceLeftPV+")");
                                    ps.execute();
//                                    </editor-fold>
                                    break;
                            }
//                        </editor-fold>
//               else{
//                    // <editor-fold defaultstate="collapsed" desc="RcdOfPayPaid">
//
//                    switch(paidType){
//                        case "Cheque":
//                            ps=cn.prepareStatement("INSERT INTO paidVoucher VALUES(GETDATE(),"+sellerId+","+Float.parseFloat(amountPaid_field.getText())+",'"+paidType+"','"+bankNameBox_pV.getSelectedItem().toString()+"')");
//                            ps.execute();
//                            break;
//                        case "Cash":
//                            ps=cn.prepareStatement("INSERT INTO paidVoucher VALUES(GETDATE(),"+sellerId+","+Float.parseFloat(amountPaid_field.getText())+",'"+paidType+"','Cash Paid')");
//                            ps.execute();
//                            break;
//                    }
//
//                    //        </editor-fold>
//                    // <editor-fold defaultstate="collapsed" desc="totalPayPaidUpdateOfParticularSUPPLIERr">
//                        ps=cn.prepareStatement("SELECT totalPaidAm,totalPurchaseAm FROM supplierList WHERE sellerId="+sellerId+"");
//                        rs=ps.executeQuery();
//                        while(rs.next()){
//                            totalPaidAm=rs.getFloat(1);
//                            totalPurchaseAm=rs.getFloat(2);
//                        }
//                        totalPaidAm+=Float.parseFloat(amountPaid_field.getText());
//                        ps=cn.prepareStatement("UPDATE supplierList SET totalPaidAm ="+totalPaidAm+" WHERE sellerId ="+sellerId+"");
//                        ps.execute();
//                    //        </editor-fold>
//                    // <editor-fold defaultstate="collapsed" desc="ledgerUpdate">
//                        supplierBalance=supplierBalance-currentPaymentPaid;
//                        ps=cn.prepareStatement("INSERT INTO supplierLedger VALUES("+sellerId+",GETDATE(),"+paidVoucherNo.getText()+",'Amount Paid','',"+amountPaid_field.getText()+","+supplierBalance+",0)");
//                        ps.execute();
//                    //            </editor-fold>
//                    // <editor-fold defaultstate="collapsed" desc="Books">
//                            switch(paidType){
//                                case "Cash":
//                                    // <editor-fold defaultstate="collapsed" desc="Cash">
//                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM cashBook ORDER BY Id DESC");
//                                    rs=ps.executeQuery();
//                                    while(rs.next()){
//                                        balanceLeftPV=rs.getFloat(1);
//                                    }
//                                    balanceLeftPV=balanceLeftPV-Float.parseFloat(amountPaid_field.getText());
//                                    ps=cn.prepareStatement("INSERT INTO cashBook VALUES(GETDATE(),"+Integer.parseInt(paidVoucherNo.getText())+",'"+supplierBox_pV.getSelectedItem().toString()+"','Amount Paid','',"+amountPaid_field.getText()+","+balanceLeftPV+")");
//                                    ps.execute();
////                                    </editor-fold>
//                                    break;
//                                case "Cheque":
//                                    // <editor-fold defaultstate="collapsed" desc="bankLedgerUpdate">
//                                    ps=cn.prepareStatement("SELECT bankId FROM bankList WHERE bankName='"+bankNameBox_pV.getSelectedItem().toString()+"'");
//                                    rs=ps.executeQuery();
//                                    while(rs.next()){
//                                        bankIdPV=rs.getInt(1);
//                                    }
//                                    ps=cn.prepareStatement("SELECT TOP 1 balance,id FROM bankLedger WHERE bankId="+bankIdPV+" ORDER BY id DESC");
//                                    rs=ps.executeQuery();
//                                    while(rs.next()){
//                                        bankBalancePV=rs.getFloat(1);
//                                    }
//                                    bankBalancePV=bankBalancePV-Float.parseFloat(amountPaid_field.getText());
//                                    ps=cn.prepareStatement("INSERT INTO bankLedger VALUES("+bankIdPV+",GETDATE(),"+paidVoucherNo.getText()+",'Amount To "+supplierBox_pV.getSelectedItem().toString()+"',"+amountPaid_field.getText()+",'',"+bankBalancePV+")");
//                                    ps.execute();
////            </editor-fold>
//                                    // <editor-fold defaultstate="collapsed" desc="Cheque">
//                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM chequeBook ORDER BY Id DESC");
//                                    rs=ps.executeQuery();
//                                    while(rs.next()){
//                                        balanceLeftPV=rs.getFloat(1);
//                                    }
//                                    balanceLeftPV=balanceLeftPV-Float.parseFloat(amountPaid_field.getText());
//                                    ps=cn.prepareStatement("INSERT INTO chequeBook VALUES(GETDATE(),"+Integer.parseInt(paidVoucherNo.getText())+",'"+bankNameBox_pV.getSelectedItem().toString()+"','"+supplierBox_pV.getSelectedItem().toString()+"','Amount Paid','',"+amountPaid_field.getText()+","+balanceLeftPV+")");
//                                    ps.execute();
////                                    </editor-fold>
//                                    break;
//                            }
////                        </editor-fold>
//               }
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(paymentPaid_lpane, ex.getLocalizedMessage());
        }
        amountPaid_field.setText("0.0");
        prevPaidVoucherNo();
        getPaymentPaidHistory();
        getPaymentRcvdHistory();
        supplierBox_pV.requestFocus();
    }//GEN-LAST:event_makePaidVoucherActionPerformed

    private void cheque3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cheque3ActionPerformed
        paidType="Cheque";
        bankNameBox_pV.setEnabled(true);
    }//GEN-LAST:event_cheque3ActionPerformed

    private void cash3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cash3ActionPerformed
        paidType="Cash";
        bankNameBox_pV.setEnabled(false);
    }//GEN-LAST:event_cash3ActionPerformed

    private void amountPaid_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_amountPaid_fieldFocusGained
        amountPaid_field.setText(null);
    }//GEN-LAST:event_amountPaid_fieldFocusGained

    private void prevTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevTransactionActionPerformed
        tmp="";
        TransactionNo = Integer.valueOf(transactionNo.getText())-1;
            pTransaction_table.setEnabled(false);
            pTransaction_table.setForeground(Color.GRAY);
        prevTransaction();
        // <editor-fold defaultstate="collapsed" desc="PrevTransaction(ExceptTableDetails)">
        try{

            ps=cn.prepareStatement("SELECT transactionNo,date,sellerName,tAmount,paymentStatus,paymentLeft,delayedDuration FROM purchaseTransaction WHERE transactionNo="+TransactionNo+"");
            rs=ps.executeQuery();

            while(rs.next()){
//                PeriodSet abc = new PeriodSet(periods);
                transactionNo.setText(rs.getString(1));
//                dateField_PT.setDefaultPeriods(rs.getString(2));
                supplierBox_pT.setSelectedItem(rs.getObject(3));
                tAmount_field_PT.setText(rs.getString(4));
                tmp=rs.getString(5);
                switch (tmp) {
                    case "Not Paid":
                        paymentStatus_label.setForeground(Color.red);
                        paymentStatus_label.setText("Payment Left : "+rs.getString(6)+" | Transaction Duration : "+rs.getString(7));
                        break;
                    case "Paid":
                        paymentStatus_label.setForeground(Color.GREEN);
                        paymentStatus_label.setText("Payment Paid");
                        break;
                }
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
        makeTransaction.setEnabled(false);
        //        </editor-fold>
    }//GEN-LAST:event_prevTransactionActionPerformed

    private void previousOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousOrderActionPerformed
        OrderNo = Integer.valueOf(orderNo.getText())-1;
        getPrevOrder();
        // <editor-fold defaultstate="collapsed" desc="PrevOder(ExceptTableDetails)">
        try{

            ps=cn.prepareStatement("SELECT orderNo,date,clientName,orderCompleted FROM purchaseOrder WHERE orderNo="+OrderNo+"");
            rs=ps.executeQuery();

            while(rs.next()){
                orderNo.setText(rs.getString(1));
                dateField_O.setText(rs.getString(2));
                clientBox_PO.setSelectedItem(rs.getString(3));
                orderStatus_label.setText("Order Completed : "+rs.getString(4));
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
        purchaseOrder_table.setEnabled(false);
        makeOrder.setEnabled(false);
        //        </editor-fold>
        
            purchaseOrder_table.setEnabled(false);
            purchaseOrder_table.setForeground(Color.GRAY);
    }//GEN-LAST:event_previousOrderActionPerformed

    private void nextOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextOrderActionPerformed
        OrderNo = Integer.valueOf(orderNo.getText())+1;
        // <editor-fold defaultstate="collapsed" desc="FindingLastONo">   
        try{
            ps=cn.prepareStatement("SELECT TOP 1 orderNo FROM purchaseOrder ORDER BY orderNo DESC");
            rs=ps.executeQuery();
            while(rs.next()){
                lastONo=rs.getInt(1);
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(purchaseOrder_panel, ex.getLocalizedMessage());
        }
//        </editor-fold>
        if(lastONo>=OrderNo){
            getPrevOrder();
            // <editor-fold defaultstate="collapsed" desc="PrevOder(ExceptTableDetails)">
            try{

                ps=cn.prepareStatement("SELECT orderNo,date,clientName,orderCompleted FROM purchaseOrder WHERE orderNo="+OrderNo+"");
                rs=ps.executeQuery();

                while(rs.next()){
                    orderNo.setText(rs.getString(1));
                    dateField_O.setText(rs.getString(2));
                    clientBox_PO.setSelectedItem(rs.getString(3));
                    orderStatus_label.setText("Order Completed : "+rs.getString(4));
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
            }
            //        </editor-fold>
        }
        else{
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("THIS IS YOUR LAST ORDER");
                    statusBarLabel.setForeground(Color.BLACK);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_nextOrderActionPerformed

    private void newOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newOrderActionPerformed
        //<editor-fold defaultstate="collapsed" desc="NewTable">
            Order = new DefaultTableModel(columnNamesO,10);
            purchaseOrder_table.setModel(Order);
            prevOrderNo(orderNo);
            getClientNames(clientBox_PO);
            inserComboBox(purchaseOrder_table);
            purchaseOrder_table.setEnabled(true);
            Oflag=true;
            clientBox_PO.requestFocus();
//            </editor-fold>
    }//GEN-LAST:event_newOrderActionPerformed

    private void makeOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeOrderActionPerformed
        int i;
        OitemNo=new int[purchaseOrder_table.getRowCount()];
        try{
            for(i=0; i<purchaseOrder_table.getRowCount(); i++){
                if(purchaseOrder_table.getValueAt(i, 0)!=null){
                    //<editor-fold defaultstate="collapsed" desc="SelectingItemNos">
                    ps=cn.prepareStatement("SELECT itemNo FROM itemsList WHERE itemName='"+purchaseOrder_table.getValueAt(i, 0).toString()+"'");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        OitemNo[i]=rs.getInt(1);
                    }
//                    </editor-fold>
                }
            }
            
            for(i=0; i<purchaseOrder_table.getRowCount(); i++){
                if(purchaseOrder_table.getValueAt(i, 0)!=null){
                    ps=cn.prepareStatement("INSERT INTO purchaseOrder values("+orderNo.getText()+",'"+dateField_O.getText()+"','"+clientBox_PO.getSelectedItem().toString()+"',"+OitemNo[i]+","+purchaseOrder_table.getValueAt(i, 2).toString()+","+purchaseOrder_table.getValueAt(i, 2).toString()+",'No')");
                    ps.execute();
                }
            }
            
            //<editor-fold defaultstate="collapsed" desc="NewTable">
            Order = new DefaultTableModel(columnNamesO,10);
            purchaseOrder_table.setModel(Order);
            prevOrderNo(orderNo);
            getClientNames(clientBox_PO);
            inserComboBox(purchaseOrder_table);
            purchaseOrder_table.setEnabled(true);
            Oflag=true;
            clientBox_PO.requestFocus();
//            </editor-fold>
        }
        catch(SQLException | NullPointerException ex){
            JOptionPane.showMessageDialog(purchaseOrder_panel, ex.getLocalizedMessage());
        }
    }//GEN-LAST:event_makeOrderActionPerformed

    private void Cash2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cash2ActionPerformed
        bankNameBox_PT.setEnabled(false);
        purchaseMode="Cash";
    }//GEN-LAST:event_Cash2ActionPerformed

    private void Cheque2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cheque2ActionPerformed
        bankNameBox_PT.setEnabled(true);
        purchaseMode="Cheque";
    }//GEN-LAST:event_Cheque2ActionPerformed

    private void Credit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Credit2ActionPerformed
        bankNameBox_PT.setEnabled(false);
        purchaseMode="Credit";
    }//GEN-LAST:event_Credit2ActionPerformed

    private void PurchaseO_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchaseO_menuActionPerformed
        Visiblefalse(purchaseOrder_lpane, purchaseOrder);
        if(!Oflag){
            Order = new DefaultTableModel(columnNamesO,10);
            purchaseOrder_table.setModel(Order);
            prevOrderNo(orderNo);
            setDate(dateField_O);
            purchaseOrder_table.setEnabled(true);
            Oflag=true;
            clientBox_PO.requestFocus();
        }
            getClientNames(clientBox_PO);
            inserComboBox(purchaseOrder_table);
    }//GEN-LAST:event_PurchaseO_menuActionPerformed

    private void purchaseOrder_tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_purchaseOrder_tablePropertyChange
        if (Oflag) {
            
            int itemNumber=0;
            
            purchaseOrder_table.setEditingRow(0);
            purchaseOrder_table.changeSelection(purchaseOrder_table.getSelectedRow(), 0, true, true);
            
            rowIndexPO = purchaseOrder_table.getSelectedRow();
        
            try{    
                if (rowIndexPO>=0){
                // <editor-fold defaultstate="collapsed" desc="UpdatingUnit">
            if(purchaseOrder_table.getValueAt(rowIndexPO,0)!=null){
                ps=cn.prepareStatement("SELECT unit FROM itemsList WHERE itemName='"+purchaseOrder_table.getValueAt(rowIndexPO, 0).toString()+"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    purchaseOrder_table.setValueAt(rs.getString(1), rowIndexPO, 1);
                }       
            }
//            </editor-fold>
                
            }
            
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(purchaseOrder_panel, ex.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_purchaseOrder_tablePropertyChange

    private void viewProfitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProfitActionPerformed
//        try {
//            profitReport();
//        } catch (SQLException ex) {
//            Logger.getLogger(Software.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_viewProfitActionPerformed

    private void totalAigingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalAigingActionPerformed
        agingDuration = 0;
    }//GEN-LAST:event_totalAigingActionPerformed

    private void recievableAiging_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recievableAiging_menuActionPerformed
        Visiblefalse(aigingReportParameter_lpane, agingReport_parameter);
                        getClientNames(aigingReport_comboBox);
                        aigingType="Recievable";
    }//GEN-LAST:event_recievableAiging_menuActionPerformed

    private void salesInvoice_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesInvoice_menuActionPerformed
        Visiblefalse(salesInvoice_lpane, salesInvoice);
        if(!SIFlag){
            prevInvNo(invoiceNo);
            clientsBox_SI.requestFocus();
        }
        getClientNames(clientsBox_SI);
    }//GEN-LAST:event_salesInvoice_menuActionPerformed

    private void addChalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addChalanActionPerformed
        SCD = new selectChalanDialog(this, Calc);
        SCD.setVisible(true);
        SCD.setLocation(100, 100);
        SCD.clientName=clientsBox_SI.getSelectedItem().toString();
        SCD.getChalanList();
    }//GEN-LAST:event_addChalanActionPerformed

    private void sellersPReport_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellersPReport_menuActionPerformed
        Visiblefalse(salesReportParameter_lpane, salesReport_search1);
                        salesReport_comboBox.removeAllItems();
                        sReportType="supplierWise";
                        cOrP.setVisible(true);
                        salesReport_comboBox.setVisible(true);
                        cOrP.setText("Select Supplier");
                        getSupplierNames(salesReport_comboBox);
                        salesReportType="Purchase";
    }//GEN-LAST:event_sellersPReport_menuActionPerformed

    private void productsPReport_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productsPReport_menuActionPerformed
        Visiblefalse(salesReportParameter_lpane, salesReport_search1);
                        salesReport_comboBox.removeAllItems();
                        sReportType="productsWise";
                        cOrP.setVisible(true);
                        salesReport_comboBox.setVisible(true);
                        cOrP.setText("Select Product");
                        getItemNames(salesReport_comboBox);
                        salesReportType="";
    }//GEN-LAST:event_productsPReport_menuActionPerformed

    private void sellerLedger_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellerLedger_menuActionPerformed
        ledgerType="Supplier";
        Visiblefalse(ledger_lpane, ledger);
        getSupplierNames(ledgerBox);
    }//GEN-LAST:event_sellerLedger_menuActionPerformed

    private void orderNo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderNo_boxActionPerformed
//        if(DCflag) {
//            chalanOrder();
//        } else {
//        }
    }//GEN-LAST:event_orderNo_boxActionPerformed

    private void iName_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_iName_fieldFocusGained
        focusGain(iName_field,"Name");
    }//GEN-LAST:event_iName_fieldFocusGained

    private void unit_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_unit_fieldFocusGained
        focusGain(unit_field,"Unit");
    }//GEN-LAST:event_unit_fieldFocusGained

    private void minStockAmount_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_minStockAmount_fieldFocusGained
        focusGain(minStockAmount_field,"Minimum Stock Amount");
    }//GEN-LAST:event_minStockAmount_fieldFocusGained

    private void iName_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_iName_fieldFocusLost
        focusLost(iName_field, "Name");
    }//GEN-LAST:event_iName_fieldFocusLost

    private void unit_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_unit_fieldFocusLost
        focusLost(unit_field, "Unit");
    }//GEN-LAST:event_unit_fieldFocusLost

    private void minStockAmount_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_minStockAmount_fieldFocusLost
        focusLost(minStockAmount_field, "Minimum Stock Amount");
    }//GEN-LAST:event_minStockAmount_fieldFocusLost

    private void payableAiging_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payableAiging_menuActionPerformed
        Visiblefalse(aigingReportParameter_lpane, agingReport_parameter);
                        getSupplierNames(aigingReport_comboBox);
                        aigingType="Payable";
    }//GEN-LAST:event_payableAiging_menuActionPerformed

    private void nextTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextTransactionActionPerformed
        tmp="";
        TransactionNo = Integer.valueOf(transactionNo.getText())+1;
        // <editor-fold defaultstate="collapsed" desc="FindingLastTransactionNo">   
        try{
            ps=cn.prepareStatement("SELECT TOP 1 transactionNo FROM purchaseTransaction ORDER BY transactionNo DESC");
            rs=ps.executeQuery();
            while(rs.next()){
                lastTrNo=rs.getInt(1);
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(dChalan_panel, ex.getLocalizedMessage());
        }
//        </editor-fold>
        if(!(TransactionNo>lastTrNo)){
            prevTransaction();
            // <editor-fold defaultstate="collapsed" desc="NextTransaction(ExceptTableDetails)">
        try{

            ps=cn.prepareStatement("SELECT transactionNo,date,sellerName,tAmount,paymentStatus,paymentLeft,delayedDuration FROM purchaseTransaction WHERE transactionNo="+TransactionNo+"");
            rs=ps.executeQuery();

            while(rs.next()){
                transactionNo.setText(rs.getString(1));
                dateField_PT.setText(rs.getString(2));
                supplierBox_pT.setSelectedItem(rs.getObject(3));
                tAmount_field_PT.setText(rs.getString(4));
                tmp=rs.getString(5);
                switch (tmp) {
                    case "Not Paid":
                        paymentStatus_label.setForeground(Color.red);
                        paymentStatus_label.setText("Payment Left : "+rs.getString(6)+" | Transaction Duration : "+rs.getString(7));
                        break;
                    case "Paid":
                        paymentStatus_label.setForeground(Color.GREEN);
                        paymentStatus_label.setText("Payment Paid");
                        break;
                }
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
        //        </editor-fold>
        }
        else{
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("THIS IS YOUR LAST TRANSACTION !");
                    statusBarLabel.setForeground(Color.BLACK);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_nextTransactionActionPerformed

    private void unInvoicedChReport_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unInvoicedChReport_tableMouseClicked
        int a = unInvoicedChReport_table.getSelectedRow();
        int c = unInvoicedChReport_table.getSelectedColumn();
        int ab;
        String b;
        tmp="";
        if(c==0){
                ab =JOptionPane.showConfirmDialog(unInvoicedChReport_panel, "Want To View This Chalan?\nClick Yes To View");
                if(ab == JOptionPane.YES_OPTION){
                    b = String.valueOf(unInvoicedChReport_table.getValueAt(a, 0));
                    Visiblefalse(dChalan_lpane, deliveryChalan);
                    ChalanNo=Integer.parseInt(b);
                    prevChalan();
                    // <editor-fold defaultstate="collapsed" desc="PrevChalan(ExceptTableDetails)"> 
                    try{
                        ps=cn.prepareStatement("SELECT chalanNo,date,clientName,tAmount FROM deliveryChalan WHERE chalanNo="+ChalanNo+"");
                        rs=ps.executeQuery();

                        while(rs.next()){
                            chalanNo.setText(rs.getString(1));
                            dateField_Dc.setText(rs.getString(2));
                            clientsBox_Dc.addItem(rs.getObject(3));
                        }
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
                    }
    //        </editor-fold>
                    dChalan_table.setEnabled(false);
                    makeChalan.setEnabled(false);
                }
        }
    }//GEN-LAST:event_unInvoicedChReport_tableMouseClicked

    private void previousInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousInvoiceActionPerformed
        InvoiceNo = Integer.parseInt(invoiceNo.getText())-1;
        prevInvoice();
        // <editor-fold defaultstate="collapsed" desc="PrevInvoice(ExceptTableDetails)"> 
                    try{
                        ps=cn.prepareStatement("SELECT invoiceNo,date,clientName,tAmount,paymentStatus,paymentLeft,delayedDuration,type FROM salesInvoice WHERE invoiceNo="+InvoiceNo+"");
                        rs=ps.executeQuery();

                        while(rs.next()){
                            invoiceNo.setText(rs.getString(1));
                            dateField_SI.setText(rs.getString(2));
                            clientsBox_SI.addItem(rs.getObject(3));
                            tAmount_field_SI.setText(rs.getString(4));
                            tmp=rs.getString(5);
                            switch (tmp){
                                case "Recieved":
                                    paymentLeft.setForeground(Color.GREEN);
                                    paymentLeft.setText("Payment Recieved");
                                break;
                                case "Not Recieved":
                                    paymentLeft.setForeground(Color.red);
                                    paymentLeft.setText("Payment Left : "+rs.getString(6)+" | Invoice Duration : "+rs.getString(7)+" Days");
                                break;
                                default:
                            }
                            tmp=rs.getString(8);
                                    switch (tmp){
                                case "Cash":
                                    Cash3.isSelected();
                                break;
                                case "Cheque":
                                    Cheque3.isSelected();
                                break;
                                default:
                                    Credit3.isSelected();
                            }
                        }
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
                    }
    //        </editor-fold>
        salesInvoice_table.setEnabled(false);
        salesInvoice_table.setForeground(Color.GRAY);
        makeInvoice.setEnabled(false);
        printInvoice.setEnabled(true);
        nextInvoice.setEnabled(true);
        addChalan.setEnabled(false);
    }//GEN-LAST:event_previousInvoiceActionPerformed

    private void nextInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextInvoiceActionPerformed
        // <editor-fold defaultstate="collapsed" desc="FindingLastInvNo">   
        try{
            ps=cn.prepareStatement("SELECT TOP 1 invoiceNo FROM salesInvoice ORDER BY invoiceNo DESC");
            rs=ps.executeQuery();
            while(rs.next()){
                lastInvoiceNo=rs.getInt(1);
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(salesInvoice_panel, ex.getLocalizedMessage());
        }
//        </editor-fold>
        InvoiceNo = Integer.parseInt(invoiceNo.getText())+1;
        // <editor-fold defaultstate="collapsed" desc="PrevInvoice(ExceptTableDetails)"> 
        if(InvoiceNo>lastInvoiceNo){
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("THIS IS YOUR LAST INVOICE");
                    statusBarLabel.setForeground(Color.BLACK);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
        else{
            prevInvoice();
            try{
                ps=cn.prepareStatement("SELECT invoiceNo,date,clientName,tAmount,paymentStatus,paymentLeft,delayedDuration FROM salesInvoice WHERE invoiceNo="+InvoiceNo+"");
                rs=ps.executeQuery();

                while(rs.next()){
                    invoiceNo.setText(rs.getString(1));
                    dateField_SI.setText(rs.getString(2));
                    clientsBox_SI.addItem(rs.getObject(3));
                    tAmount_field_SI.setText(rs.getString(4));
                    tmp=rs.getString(5);
                    switch (tmp){
                        case "Recieved":
                            paymentLeft.setForeground(Color.GREEN);
                            paymentLeft.setText("Payment Recieved");
                            break;
                        case "Not Recieved":
                            paymentLeft.setForeground(Color.red);
                            paymentLeft.setText("Payment Left : "+rs.getString(6)+" | Invoice Duration : "+rs.getString(7)+" Days");
                            break;
                            
                    }
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
            }
        }
    //        </editor-fold>
    }//GEN-LAST:event_nextInvoiceActionPerformed

    private void newInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newInvoiceActionPerformed
        salesInvoiceModel1 = new DefaultTableModel(columnNamesSI, 0);
        salesInvoice_table.setModel(salesInvoiceModel1);
        paymentLeft.setText("");
        tAmount_field_SI.setText("0.0");
        paymentLeft.setForeground(Color.BLACK);
        prevInvNo(invoiceNo);
        salesInvoice_table.setEnabled(true);
        makeInvoice.setEnabled(true);
        salesInvoice_table.setForeground(Color.DARK_GRAY);
        printInvoice.setEnabled(false);
        addChalan.setEnabled(true);
    }//GEN-LAST:event_newInvoiceActionPerformed

    private void makeInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeInvoiceActionPerformed
    if(clientsBox_SI.getSelectedItem()!=null && Float.parseFloat(tAmount_field_SI.getText())>=1){
        SIitemNo=new int[salesInvoice_table.getRowCount()];
        int i;
        quantityEnd=false;
        makeInvoiceFlag=true;
        // <editor-fold defaultstate="collapsed" desc="Try Block">
            try{
                for(i=0; i<salesInvoice_table.getRowCount(); i++){
                    if(salesInvoice_table.getValueAt(i, 1)!=null && Float.parseFloat(salesInvoice_table.getValueAt(i, 4).toString())>=1){
                        //<editor-fold defaultstate="collapsed" desc="SelectingItemNos">
                        ps=cn.prepareStatement("SELECT itemNo FROM itemsList WHERE itemName='"+salesInvoice_table.getValueAt(i, 0)+"'");
                        rs=ps.executeQuery();
                        while(rs.next()){
                            SIitemNo[i]=rs.getInt(1);
                        }
        //                </editor-fold>
                    }
                    else{
                        makeInvoiceFlag=false;
                        JOptionPane.showMessageDialog(salesInvoice_panel, "KINDLY SET AMOUNT GREATER THAN OR EQUAL TO 1 FOR"+salesInvoice_table.getValueAt(i, 0).toString());
                    }
                }
                for(i=0; i<salesInvoice_table.getRowCount(); i++){
                    if((salesInvoice_table.getValueAt(i, 1)!=null) && (Float.parseFloat(salesInvoice_table.getValueAt(i, 4).toString())>=1) && makeInvoiceFlag){
                        switch(saleMode){
                            case "Cash":
                                // <editor-fold defaultstate="collapsed" desc="InsertingDataOfSalesInvoice">
                                ps=cn.prepareStatement("INSERT INTO salesInvoice VALUES("+invoiceNo.getText()+",'"+dateField_SI.getText()+"','"+clientsBox_SI.getSelectedItem().toString()+"','"+chalanNos+"','"+SIitemNo[i]+"',"+salesInvoice_table.getValueAt(i, 2).toString()+","+salesInvoice_table.getValueAt(i, 3).toString()+","+salesInvoice_table.getValueAt(i, 4).toString()+",'"+tAmount_field_SI.getText()+"','Recieved',0,0,'Cash')");
                                ps.execute();
                //        </editor-fold>
                                break;
                            case "Cheque":
                                // <editor-fold defaultstate="collapsed" desc="InsertingDataOfSalesInvoice">
                                if(bankNameBox_SI.getSelectedItem()!=null){
                                    ps=cn.prepareStatement("INSERT INTO salesInvoice VALUES("+invoiceNo.getText()+",'"+dateField_SI.getText()+"','"+clientsBox_SI.getSelectedItem().toString()+"','"+chalanNos+"','"+SIitemNo[i]+"',"+salesInvoice_table.getValueAt(i, 2).toString()+","+salesInvoice_table.getValueAt(i, 3).toString()+","+salesInvoice_table.getValueAt(i, 4).toString()+",'"+tAmount_field_SI.getText()+"','Recieved',0,0,'Cheque')");
                                    ps.execute();
                                }
                            //        </editor-fold>
                                break;
                            case "Credit":
                                // <editor-fold defaultstate="collapsed" desc="InsertingDataOfSalesInvoice">
                                ps=cn.prepareStatement("SELECT TOP 1 balance FROM ledger WHERE clientId="+clientId+" ORDER BY recordNo DESC");
                                rs=ps.executeQuery();
                                    if(rs.next()){
                                        clientBalance=rs.getFloat(1);
                                    }
                                    if(clientBalance<0){
                                        clientBalance = -clientBalance;
                                        JOptionPane.showMessageDialog(paymentLeft, clientBalance);
                                        if(clientBalance<Float.parseFloat(tAmount_field_SI.getText())){
                                            updatedPaymentRV=Float.parseFloat(tAmount_field_SI.getText())-clientBalance;
                                            ps=cn.prepareStatement("INSERT INTO salesInvoice VALUES("+invoiceNo.getText()+",'"+dateField_SI.getText()+"','"+clientsBox_SI.getSelectedItem().toString()+"','"+chalanNos+"','"+SIitemNo[i]+"',"+salesInvoice_table.getValueAt(i, 2).toString()+","+salesInvoice_table.getValueAt(i,3).toString()+","+salesInvoice_table.getValueAt(i, 4).toString()+",'"+tAmount_field_SI.getText()+"','Not Recieved',"+updatedPaymentRV+",0,'Credit')");
                                            ps.execute();
                                        }
                                        else if(clientBalance>=Float.parseFloat(tAmount_field_SI.getText())){
                                            ps=cn.prepareStatement("INSERT INTO salesInvoice VALUES("+invoiceNo.getText()+",'"+dateField_SI.getText()+"','"+clientsBox_SI.getSelectedItem().toString()+"','"+chalanNos+"','"+SIitemNo[i]+"',"+salesInvoice_table.getValueAt(i, 2).toString()+","+salesInvoice_table.getValueAt(i,3).toString()+","+salesInvoice_table.getValueAt(i, 4).toString()+",'"+tAmount_field_SI.getText()+"','Recieved',0.0,0,'Credit')");
                                            ps.execute();
                                        }
                                    }
                                    else{
                                        ps=cn.prepareStatement("INSERT INTO salesInvoice VALUES("+invoiceNo.getText()+",'"+dateField_SI.getText()+"','"+clientsBox_SI.getSelectedItem().toString()+"','"+chalanNos+"','"+SIitemNo[i]+"',"+salesInvoice_table.getValueAt(i, 2).toString()+","+salesInvoice_table.getValueAt(i,3).toString()+","+salesInvoice_table.getValueAt(i, 4).toString()+",'"+tAmount_field_SI.getText()+"','Not Recieved',"+tAmount_field_SI.getText()+",0,'Credit')");
                                        ps.execute();
                                    }
                //        </editor-fold>
                                break;
                            default:
                        }

                        // <editor-fold defaultstate="collapsed" desc="pROFIT uPDATE">    
                            currentQuantity=Integer.parseInt(salesInvoice_table.getValueAt(i, 2).toString());
                            currentRate=Float.parseFloat(salesInvoice_table.getValueAt(i, 3).toString());
                            while (!quantityEnd){
                            ps=cn.prepareStatement("SELECT TOP 1 id,leftQ,rate FROM profit WHERE itemNo="+SIitemNo[i]+" AND totalSold='No' ORDER BY id ASC");
                            rs=ps.executeQuery();
                            while (rs.next()) {
                                id      = rs.getInt(1);
                                prevQ   = rs.getInt(2);
                                rateP   = rs.getFloat(3);
                            }
                            itemProfit=0;
                            if(currentQuantity<prevQ){
                                purchaseAmount=currentQuantity*rateP;
                                saleAmount=currentQuantity*currentRate;
                                itemProfit=saleAmount-purchaseAmount;
                                profit+=itemProfit;
                                leftQ=prevQ-currentQuantity;
                                leftAmount=leftQ*rateP;
                                ps=cn.prepareStatement("UPDATE profit SET leftQ="+leftQ+",leftAmount="+leftAmount+",profit+="+itemProfit+" WHERE id="+id+"");
                                ps.execute();
                                quantityEnd=true;
                                break;
                            }
                            else if(currentQuantity == prevQ){
                                purchaseAmount=currentQuantity*rateP;
                                saleAmount=currentQuantity*currentRate;
                                itemProfit=saleAmount-purchaseAmount;
                                profit+=itemProfit;
                                ps=cn.prepareStatement("UPDATE profit SET leftQ=0,leftAmount=0,totalSold='Yes',profit+="+itemProfit+" WHERE id="+id+"");
                                ps.execute();
                                quantityEnd=true;
                                break;
                            }
                            else if(currentQuantity > prevQ){
                                purchaseAmount=prevQ*rateP;
                                saleAmount=prevQ*currentRate;
                                itemProfit=saleAmount-purchaseAmount;
                                profit+=itemProfit;
                                ps=cn.prepareStatement("UPDATE profit SET leftQ=0,leftAmount=0,totalSold='Yes',profit+="+itemProfit+" WHERE id="+id+"");
                                ps.execute();
                                currentQuantity=currentQuantity-prevQ;
                            }

                            }
    //                        </editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="Selecting PreviousSaleAmount And PreviousSaleCost Of Items">
                            ps=cn.prepareStatement("SELECT soldQuantity,soldAmount FROM itemsList WHERE itemNo="+SIitemNo[i]+"");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                totalSoldQuan=rs.getInt(1);
                                totalSoldAmount=rs.getFloat(2);
                            }

                            totalSoldQuan+=Integer.valueOf(salesInvoice_table.getValueAt(i, 2).toString());
                            totalSoldAmount+=Float.valueOf(salesInvoice_table.getValueAt(i, 4).toString());
    //                  </editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="Updating PreviousSaleAmount And PreviousSaleCost Of Items">
                            ps=cn.prepareStatement("UPDATE itemsList SET soldQuantity="+totalSoldQuan+", soldAmount="+totalSoldAmount+" WHERE itemNo="+SIitemNo[i]+"");
                            ps.execute();
    //                  </editor-fold>
                    }
                }
                for (Object chalanNo1 : chalanNos) {
                    if(makeInvoiceFlag){
                        // <editor-fold defaultstate="collapsed" desc="UpdateInvoiced">
                    ps = cn.prepareStatement("UPDATE deliveryChalan SET invoiced='Yes' WHERE chalanNo=" + chalanNo1 + "");
                    ps.execute();
                    //        </editor-fold>
                    }
                }
                if(makeInvoiceFlag){
                    // <editor-fold defaultstate="collapsed" desc="Books,ClientDetail,Ledger">
                            switch(saleMode){
                                case "Cash":
                                    // <editor-fold defaultstate="collapsed" desc="Selecting TotalSaleInvoices And TotalSaleAmount">
                                        ps=cn.prepareStatement("SELECT billsMade,totalSaleAmount FROM clientsList WHERE clientName='"+clientsBox_SI.getSelectedItem().toString()+"'");
                                        rs=ps.executeQuery();
                                        while (rs.next()){
                                            prevInvoicesQuant=rs.getInt(1);
                                            prevSaleAmount=rs.getFloat(2);
                                        }
                                        prevInvoicesQuant+=1;
                                        prevSaleAmount+=Float.parseFloat(tAmount_field_SI.getText());
                    //                  </editor-fold>
                                    // <editor-fold defaultstate="collapsed" desc="Updating TotalSaleInvoices And TotalSaleAmount">
                                    ps=cn.prepareStatement("UPDATE clientsList SET billsMade="+prevInvoicesQuant+",totalSaleAmount="+prevSaleAmount+",totalRcvAm="+prevSaleAmount+" WHERE clientName='"+clientsBox_SI.getSelectedItem().toString()+"'");
                                    ps.execute();
        //                    </editor-fold>
                                    // <editor-fold defaultstate="collapsed" desc="cashBook">
                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM cashBook ORDER BY Id DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        balanceLeftSI=rs.getFloat(1);
                                    }
                                    balanceLeftSI=balanceLeftSI+Float.parseFloat(tAmount_field_SI.getText());
                                    ps=cn.prepareStatement("INSERT INTO cashBook VALUES('"+dateField_SI.getText()+"',"+Integer.parseInt(invoiceNo.getText())+",'"+clientsBox_SI.getSelectedItem().toString()+"','Amount Recived Against Sale',"+tAmount_field_SI.getText()+",'',"+balanceLeftSI+")");
                                    ps.execute();
    //                                </editor-fold>
                                    break;
                                case "Cheque":
                                    if(bankNameBox_SI.getSelectedItem()!=null){
                                        // <editor-fold defaultstate="collapsed" desc="bankLedgerUpdate">
                                        ps=cn.prepareStatement("SELECT bankId FROM bankList WHERE bankName='"+bankNameBox_SI.getSelectedItem().toString()+"'");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            bankIdSI=rs.getInt(1);
                                        }
                                        ps=cn.prepareStatement("SELECT TOP 1 balance,id FROM bankLedger WHERE bankId="+bankIdSI+" ORDER BY id DESC");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            bankBalanceSI=rs.getFloat(1);
                                        }
                                        bankBalanceSI=bankBalanceSI+Float.parseFloat(tAmount_field_SI.getText());
                                        ps=cn.prepareStatement("INSERT INTO bankLedger VALUES("+bankIdSI+",'"+dateField_SI.getText()+"',"+invoiceNo.getText()+",'Amount Recived',"+tAmount_field_SI.getText()+",'',"+bankBalanceSI+")");
                                        ps.execute();
        //            </editor-fold>
                                        // <editor-fold defaultstate="collapsed" desc="Selecting TotalSaleInvoices And TotalSaleAmount">
                                            ps=cn.prepareStatement("SELECT billsMade,totalSaleAmount FROM clientsList WHERE clientName='"+clientsBox_SI.getSelectedItem().toString()+"'");
                                            rs=ps.executeQuery();
                                            while (rs.next()){
                                                prevInvoicesQuant=rs.getInt(1);
                                                prevSaleAmount=rs.getFloat(2);
                                            }
                                            prevInvoicesQuant+=1;
                                            prevSaleAmount+=Float.parseFloat(tAmount_field_SI.getText());
                        //                  </editor-fold>
                                        // <editor-fold defaultstate="collapsed" desc="Updating TotalSaleInvoices And TotalSaleAmount">
                                ps=cn.prepareStatement("UPDATE clientsList SET billsMade="+prevInvoicesQuant+",totalSaleAmount="+prevSaleAmount+",totalRcvAm="+prevSaleAmount+" WHERE clientName='"+clientsBox_SI.getSelectedItem().toString()+"'");
                                ps.execute();
            //                    </editor-fold>
                                        // <editor-fold defaultstate="collapsed" desc="chequeBook">
                                        ps=cn.prepareStatement("SELECT TOP 1 balance FROM chequeBook ORDER BY Id DESC");
                                        rs=ps.executeQuery();
                                        while(rs.next()){
                                            balanceLeftSI=rs.getFloat(1);
                                        }
                                        balanceLeftSI=balanceLeftSI+Float.parseFloat(tAmount_field_SI.getText());
                                        ps=cn.prepareStatement("INSERT INTO chequeBook VALUES('"+dateField_SI.getText()+"',"+Integer.parseInt(invoiceNo.getText())+",'"+bankNameBox_SI.getSelectedItem().toString()+"','"+clientsBox_SI.getSelectedItem().toString()+"','Sold Goods','"+tAmount_field_SI.getText()+"','',"+balanceLeftSI+")");
                                        ps.execute();
        //                                </editor-fold>
                                    }
                                    break;
                                case "Credit":
                                    // <editor-fold defaultstate="collapsed" desc="Selecting TotalSaleInvoices And TotalSaleAmount">
                                        ps=cn.prepareStatement("SELECT billsMade,totalSaleAmount FROM clientsList WHERE clientName='"+clientsBox_SI.getSelectedItem().toString()+"'");
                                        rs=ps.executeQuery();
                                        while (rs.next()){
                                            prevInvoicesQuant=rs.getInt(1);
                                            prevSaleAmount=rs.getFloat(2);
                                        }
                                        prevInvoicesQuant+=1;
                                        prevSaleAmount+=Float.parseFloat(tAmount_field_SI.getText());
                    //                  </editor-fold>
                                    // <editor-fold defaultstate="collapsed" desc="Updating TotalSaleInvoices And TotalSaleAmount">
                            ps=cn.prepareStatement("UPDATE clientsList SET billsMade="+prevInvoicesQuant+",totalSaleAmount="+prevSaleAmount+" WHERE clientName='"+clientsBox_SI.getSelectedItem().toString()+"'");
                            ps.execute();
        //                    </editor-fold>
                                    // <editor-fold defaultstate="collapsed" desc="ledgerUpdate">
                                    ps=cn.prepareStatement("SELECT clientId FROM clientsList WHERE clientName='"+clientsBox_SI.getSelectedItem().toString()+"'");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        clientId1=rs.getInt(1);
                                    }
                                    ps=cn.prepareStatement("SELECT TOP 1 balance FROM ledger WHERE clientId="+clientId1+" ORDER BY recordNo DESC");
                                    rs=ps.executeQuery();
                                    while(rs.next()){
                                        balanceLeftSI=rs.getFloat(1);
                                    }
                                    balanceLeftSI=balanceLeftSI+Float.parseFloat(tAmount_field_SI.getText());
                                    ps=cn.prepareStatement("INSERT INTO ledger VALUES("+clientId1+",'"+dateField_SI.getText()+"',"+invoiceNo.getText()+",'Sold Goods','',"+tAmount_field_SI.getText()+","+balanceLeftSI+",0)");
                                    ps.execute();
                //            </editor-fold>
                            }
    //                    </editor-fold>
                    salesInvoice_table.setEnabled(false);
                    salesInvoice_table.setForeground(Color.GRAY);
                    makeInvoice.setEnabled(false);
                    printInvoice.setEnabled(true);
                    addChalan.setEnabled(false);
                    // <editor-fold defaultstate="collapsed" desc="PrevInvoice(ExceptTableDetails)"> 
                    try{
                        ps=cn.prepareStatement("SELECT paymentStatus,paymentLeft,delayedDuration,type FROM salesInvoice WHERE invoiceNo="+invoiceNo.getText()+"");
                        rs=ps.executeQuery();

                        while(rs.next()){
                            tmp=rs.getString(1);
                            switch (tmp){
                                case "Recieved":
                                    paymentLeft.setForeground(Color.GREEN);
                                    paymentLeft.setText("Payment Recieved");
                                break;
                                case "Not Recieved":
                                    paymentLeft.setForeground(Color.red);
                                    paymentLeft.setText("Payment Left : "+rs.getString(2)+" | Invoice Duration : "+rs.getString(3)+" Days");
                                break;
                                default:
                            }
                            tmp=rs.getString(4);
                                    switch (tmp){
                                case "Cash":
                                    Cash3.isSelected();
                                break;
                                case "Cheque":
                                    Cheque3.isSelected();
                                break;
                                default:
                                    Credit3.isSelected();
                            }
                        }
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
                    }
    //        </editor-fold>
                }
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
            }
            catch(NullPointerException ex){
                System.out.println("Null Pointer");
            }
    //        </editor-fold>
    }
    else{
        // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDL SUBMIT VALID DATA");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
    }
    }//GEN-LAST:event_makeInvoiceActionPerformed

    private void printInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printInvoiceActionPerformed
        Map parameter = new HashMap();
        parameter.put("invoiceNo", Integer.parseInt(invoiceNo.getText()));
        Dimension d = new Dimension(1000, 768);
        ReportView r = new ReportView();
        r.showReport(cn, parameter, "salesInvoice.jrxml", "Sales Invoice", d);
    }//GEN-LAST:event_printInvoiceActionPerformed

    private void supplierName_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_supplierName_fieldKeyTyped
       if(supplierName_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_supplierName_fieldKeyTyped

    private void clientName_FieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientName_FieldKeyTyped
        if(clientName_Field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_clientName_FieldKeyTyped

    private void iName_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iName_fieldKeyTyped
        if(iName_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_iName_fieldKeyTyped

    private void unit_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_unit_fieldKeyTyped
        if(unit_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_unit_fieldKeyTyped

    private void addProducts_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProducts_menuActionPerformed
        Visiblefalse(addProduct_lpane, addProduct);
        iName_field.requestFocus();
        getItemNames();
    }//GEN-LAST:event_addProducts_menuActionPerformed

    private void addSeller_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSeller_menuActionPerformed
        Visiblefalse(addSupplier_lpane, addSeller);
        supplierName_field.requestFocus();
        getSupplierNames();
    }//GEN-LAST:event_addSeller_menuActionPerformed

    private void supplierAddress_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierAddress_fieldFocusGained
        focusGain(supplierAddress_field, "Address");
    }//GEN-LAST:event_supplierAddress_fieldFocusGained

    private void supplierAddress_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierAddress_fieldFocusLost
        focusLost(supplierAddress_field, "Address");
    }//GEN-LAST:event_supplierAddress_fieldFocusLost

    private void supplierContactNo_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierContactNo_fieldFocusGained
        focusGain(supplierContactNo_field, "Contact No");
    }//GEN-LAST:event_supplierContactNo_fieldFocusGained

    private void supplierContactNo_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierContactNo_fieldFocusLost
        focusLost(supplierContactNo_field, "Contact No");
    }//GEN-LAST:event_supplierContactNo_fieldFocusLost

    private void supplierContactNo_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_supplierContactNo_fieldKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID DATA");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_supplierContactNo_fieldKeyTyped

    private void clientAddress_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clientAddress_fieldFocusGained
        focusGain(clientAddress_field, "Address");
    }//GEN-LAST:event_clientAddress_fieldFocusGained

    private void clientAddress_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clientAddress_fieldFocusLost
        focusLost(clientAddress_field, "Address");
    }//GEN-LAST:event_clientAddress_fieldFocusLost

    private void clientContactNo_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clientContactNo_fieldFocusGained
        focusGain(clientContactNo_field, "Contact No");
    }//GEN-LAST:event_clientContactNo_fieldFocusGained

    private void clientContactNo_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clientContactNo_fieldFocusLost
        focusLost(clientContactNo_field, "Contact No");
    }//GEN-LAST:event_clientContactNo_fieldFocusLost

    private void clientContactNo_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientContactNo_fieldKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID DATA");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_clientContactNo_fieldKeyTyped

    private void bankName_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bankName_fieldFocusGained
        focusGain(bankName_field, "Name");
    }//GEN-LAST:event_bankName_fieldFocusGained

    private void bankName_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bankName_fieldFocusLost
        focusLost(bankName_field, "Name");
    }//GEN-LAST:event_bankName_fieldFocusLost

    private void bankName_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bankName_fieldKeyTyped
        if(bankName_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_bankName_fieldKeyTyped
float bankOpeningBalance,bankBalance_opening;
int bankId_new;
    private void addBank_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBank_btnActionPerformed
        try{
            if(bankName_field.getText().equals("") || bankName_field.getText().equals("Name")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY SUBMIT VALID DATA !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                ps=cn.prepareStatement("INSERT INTO bankList VALUES('"+bankName_field.getText()+"')");
                ps.execute();
                // <editor-fold defaultstate="collapsed" desc="bankLedgerUpdate">
                ps=cn.prepareStatement("SELECT bankId FROM bankList WHERE bankName='"+bankName_field.getText()+"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    bankId_new=rs.getInt(1);
                }
                ps=cn.prepareStatement("INSERT INTO bankLedger VALUES("+bankId_new+",GETDATE(),0,'Opening Balance',"+banKOpeningBalance_field.getText()+",'',"+banKOpeningBalance_field.getText()+")");
                ps.execute();
//            </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="Cheque">
                ps=cn.prepareStatement("SELECT TOP 1 balance FROM chequeBook ORDER BY Id DESC");
                rs=ps.executeQuery();
                while(rs.next()){
                    bankOpeningBalance=rs.getFloat(1);
                }
                bankOpeningBalance=bankOpeningBalance+Float.parseFloat(banKOpeningBalance_field.getText());
                ps=cn.prepareStatement("INSERT INTO chequeBook VALUES(GETDATE(),0,'"+bankName_field.getText()+"','','Opening Balance',"+banKOpeningBalance_field.getText()+",'',"+bankOpeningBalance+")");
                ps.execute();
//                                    </editor-fold>
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(paymentLeft, ex.getLocalizedMessage());
        }
        bankName_field.setText(null);
        bankName_field.requestFocus();
        getBankList();
    }//GEN-LAST:event_addBank_btnActionPerformed

    private void expenseName_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_expenseName_fieldFocusGained
        focusGain(expenseName_field, "Name");
    }//GEN-LAST:event_expenseName_fieldFocusGained

    private void expenseName_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_expenseName_fieldFocusLost
        focusLost(expenseName_field, "Name");
    }//GEN-LAST:event_expenseName_fieldFocusLost

    private void expenseName_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_expenseName_fieldKeyTyped
        if(expenseName_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_expenseName_fieldKeyTyped

    private void addExpenseName_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpenseName_btnActionPerformed
        try{
            if(expenseName_field.getText().equals("") || expenseName_field.getText().equals("Name")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY SUBMIT VALID DATA !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                ps=cn.prepareStatement("INSERT INTO expenseList VALUES('"+expenseName_field.getText()+"')");
                ps.execute();
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(paymentLeft, ex.getLocalizedMessage());
        }
        expenseName_field.setText(null);
        expenseName_field.requestFocus();
        getExpenseList();
    }//GEN-LAST:event_addExpenseName_btnActionPerformed

    private void Cash3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cash3ActionPerformed
        bankNameBox_SI.setEnabled(false);
        saleMode="Cash";
    }//GEN-LAST:event_Cash3ActionPerformed

    private void Cheque3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cheque3ActionPerformed
        bankNameBox_SI.setEnabled(true);
        saleMode="Cheque";
    }//GEN-LAST:event_Cheque3ActionPerformed

    private void Credit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Credit3ActionPerformed
        bankNameBox_SI.setEnabled(false);
        saleMode="Credit";
    }//GEN-LAST:event_Credit3ActionPerformed

    private void expenseName_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseName_fieldActionPerformed
        try{
            if(expenseName_field.getText().equals("") || expenseName_field.getText().equals("Name")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY SUBMIT VALID DATA !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                ps=cn.prepareStatement("INSERT INTO expenseList VALUES('"+expenseName_field.getText()+"')");
                ps.execute();
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(paymentLeft, ex.getLocalizedMessage());
        }
        expenseName_field.setText(null);
        expenseName_field.requestFocus();
        getExpenseList();
    }//GEN-LAST:event_expenseName_fieldActionPerformed

    private void clientsBox_SIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clientsBox_SIItemStateChanged
        salesInvoiceModel1 = new DefaultTableModel(columnNamesSI, 0);
        salesInvoice_table.setModel(salesInvoiceModel1);
    }//GEN-LAST:event_clientsBox_SIItemStateChanged

    private void cashBook_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cashBook_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cashBook_tableMouseClicked

    private void chequeBook_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chequeBook_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_chequeBook_tableMouseClicked

    private void amountPaid_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountPaid_fieldKeyTyped
        if(Character.isLetter(evt.getKeyChar())){
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID VALUE");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold> 
        }
    }//GEN-LAST:event_amountPaid_fieldKeyTyped

    private void amountRecieved_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountRecieved_fieldKeyTyped
        if(Character.isLetter(evt.getKeyChar())){
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID VALUE");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold> 
        }
    }//GEN-LAST:event_amountRecieved_fieldKeyTyped

    private void Cash4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cash4ActionPerformed
        bankNameBox_E.setEnabled(false);
        expenseMode="Cash";
    }//GEN-LAST:event_Cash4ActionPerformed

    private void Cheque4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cheque4ActionPerformed
        bankNameBox_E.setEnabled(true);
        expenseMode="Cheque";
    }//GEN-LAST:event_Cheque4ActionPerformed

    private void Credit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Credit4ActionPerformed
        bankNameBox_E.setEnabled(false);
        expenseMode="Credit";
    }//GEN-LAST:event_Credit4ActionPerformed

    private void salesInvoice_tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_salesInvoice_tablePropertyChange
         if (SIFlag) {
            salesInvoice_table.setEditingRow(0);
            salesInvoice_table.changeSelection(salesInvoice_table.getSelectedRow(), 0, true, true);
            float res;
            try{    
            rowIndexSI = salesInvoice_table.getSelectedRow();
            if (rowIndexSI>=0){
                // <editor-fold defaultstate="collapsed" desc="Amount,TAmount">
                String a,b;
                for(int i=0; i<salesInvoice_table.getRowCount(); i++){
                    a=salesInvoice_table.getValueAt(i,2).toString();
                    b=salesInvoice_table.getValueAt(i,3).toString();
                    if ((!"".equals(a) && !"".equals(b))) {
                        if(i==0){
                            fres=0;
                        }
                        res=Float.valueOf(salesInvoice_table.getValueAt(i,2).toString())*(Float.valueOf((salesInvoice_table.getValueAt(i,3)).toString()));
                        salesInvoice_table.setValueAt(res, i, 4);
                        fres+=Float.valueOf(salesInvoice_table.getValueAt(i, 4).toString());
                        tAmount_field_SI.setText((String.valueOf(fres)));
                    }
                }
                    salesInvoice_table.rowAtPoint(null);
//                </editor-fold>
            }
            }catch(NullPointerException ex){
              System.out.println(ex.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_salesInvoice_tablePropertyChange

    private void ledger_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ledger_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ledger_tableMouseClicked

    private void printLedgerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printLedgerActionPerformed
        Map parameter = new HashMap();
        Dimension d = new Dimension(1024, 768);
        ReportView r = new ReportView();
        switch(ledgerType){
            case "Client":
                parameter.put("fromDateP", fromDate.getText());
                parameter.put("toDateP", toDate.getText());
                parameter.put("clientName", ledgerBox.getSelectedItem().toString());
                r.showReport(cn, parameter, "ledgerReport.jrxml", "Client Ledger", d);
                break;
            case "Supplier":
                parameter.put("fromDateP", fromDate.getText());
                parameter.put("toDateP", toDate.getText());
                parameter.put("sellerName", ledgerBox.getSelectedItem().toString());
                r.showReport(cn, parameter, "ledgerReport_supplier.jrxml", "Supplier Ledger", d);
                break;
            case "Bank":
                printTable(ledger_table, "Ledger ( Head Of Account : "+ledgerBox.getSelectedItem().toString()+" )", "Page{0}");
                break;
            case "Expense":
                printTable(ledger_table, "Ledger ( Head Of Account : "+ledgerBox.getSelectedItem().toString()+" )", "Page{0}");
                break;
        }
    }//GEN-LAST:event_printLedgerActionPerformed

    private void printSalesReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printSalesReportActionPerformed
        printTable(salesReport_table, "Sales/Purchase Report ( Head Of Account : "+salesReport_comboBox.getSelectedItem().toString()+" )", "Page{0}");
    }//GEN-LAST:event_printSalesReportActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        printTable(aigingReport_table, "Aiging Report ( Head Of Account : "+aigingReport_comboBox.getSelectedItem().toString()+" )", "Total Payment Left : "+tAmount_aiging.getText()+"\nPage{0}");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void stockReport_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockReport_tableMouseClicked
        getdata0();
    }//GEN-LAST:event_stockReport_tableMouseClicked

    private void openingStockQauntiy_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_openingStockQauntiy_fieldFocusGained
        focusGain(openingStockQauntiy_field, "Opening Stock Quantity");
    }//GEN-LAST:event_openingStockQauntiy_fieldFocusGained

    private void openingStockQauntiy_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_openingStockQauntiy_fieldFocusLost
        focusLost(openingStockQauntiy_field, "Opening Stock Quantity");
    }//GEN-LAST:event_openingStockQauntiy_fieldFocusLost

    private void openingStockQauntiy_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_openingStockQauntiy_fieldKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
        if(openingStockAmount_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_openingStockQauntiy_fieldKeyTyped

    private void openingStockAmount_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_openingStockAmount_fieldFocusGained
        focusGain(openingStockAmount_field, "Opening Stock Amount");
    }//GEN-LAST:event_openingStockAmount_fieldFocusGained

    private void openingStockAmount_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_openingStockAmount_fieldFocusLost
        focusLost(openingStockAmount_field, "Opening Stock Amount");
    }//GEN-LAST:event_openingStockAmount_fieldFocusLost

    private void openingStockAmount_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_openingStockAmount_fieldKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
        if(openingStockAmount_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_openingStockAmount_fieldKeyTyped

    private void bankLedger_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bankLedger_menuActionPerformed
        ledgerType="Bank";
        ledger_table.removeAll();
        Visiblefalse(ledger_lpane, ledger);
        getBankNames(ledgerBox);
    }//GEN-LAST:event_bankLedger_menuActionPerformed

    private void expenseLedger_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseLedger_menuActionPerformed
        ledgerType="Expense";
        ledger_table.removeAll();
        Visiblefalse(ledger_lpane, ledger);
        getExpenseNames(ledgerBox);
    }//GEN-LAST:event_expenseLedger_menuActionPerformed

    private void clientOpeningBalance_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientOpeningBalance_fieldActionPerformed
        try{
            if(clientName_Field.getText().equals("") || clientName_Field.getText().equals("Client Name") || clientAddress_field.getText().equals("") || clientAddress_field.getText().equals("Address") || clientContactNo_field.getText().equals("") || clientContactNo_field.getText().equals("Contact No") || clientOpeningBalance_field.getText().equals("") || clientOpeningBalance_field.getText().equals("Opening Balance")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID CLIENT NAME !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                ps=cn.prepareStatement("INSERT INTO clientsList (clientName,address,contact,totalSaleAmount) VALUES('"+clientName_Field.getText()+"','"+clientAddress_field.getText()+"',"+clientContactNo_field.getText()+","+clientOpeningBalance_field.getText()+")");
                ps.execute();
                // <editor-fold defaultstate="collapsed" desc="selectingClientId">
                ps=cn.prepareStatement("SELECT clientId FROM clientsList WHERE clientName='"+clientName_Field.getText()+"'");
                rs=ps.executeQuery();
                while(rs.next()){
                    clientId=rs.getInt(1);
                }
    //            </editor-fold>
                ps=cn.prepareStatement("INSERT INTO ledger VALUES("+clientId+",GETDATE(),0,'Opening Balance','',"+clientOpeningBalance_field.getText()+","+clientOpeningBalance_field.getText()+",0)");
                ps.execute();
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
        getClientNames();
        clientName_Field.setText(null);
        clientAddress_field.setText("Address");
        clientContactNo_field.setText("Contact No");
        clientOpeningBalance_field.setText("Opening Balance");
        clientAddress_field.setForeground(Color.GRAY);
        clientContactNo_field.setForeground(Color.GRAY);
        clientOpeningBalance_field.setForeground(Color.GRAY);
        clientName_Field.requestFocus();
    }//GEN-LAST:event_clientOpeningBalance_fieldActionPerformed

    private void clientOpeningBalance_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clientOpeningBalance_fieldFocusGained
        focusGain(clientOpeningBalance_field, "Opening Balance");
    }//GEN-LAST:event_clientOpeningBalance_fieldFocusGained

    private void clientOpeningBalance_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clientOpeningBalance_fieldFocusLost
        focusLost(clientOpeningBalance_field, "Opening Balance");
    }//GEN-LAST:event_clientOpeningBalance_fieldFocusLost

    private void clientOpeningBalance_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientOpeningBalance_fieldKeyTyped
        if(clientOpeningBalance_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_clientOpeningBalance_fieldKeyTyped

    private void supplierOpeningBalance_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierOpeningBalance_fieldActionPerformed
        try{
            if(supplierName_field.getText().equals("") || supplierName_field.getText().equals("Supplier Name") || supplierAddress_field.getText().equals("") || supplierAddress_field.getText().equals("Address") || supplierContactNo_field.getText().equals("") || supplierContactNo_field.getText().equals("Contact No") || supplierOpeningBalance_field.getText().equals("") || supplierOpeningBalance_field.getText().equals("Opening Balance")){
                // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("KINDLY ENTER VALID VALUES TO ALL FIELDS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
            }
            else{
                ps=cn.prepareStatement("INSERT INTO supplierList (sellerName,address,contact,totalPurchaseAm) VALUES('"+supplierName_field.getText()+"','"+supplierAddress_field.getText()+"',"+supplierContactNo_field.getText()+","+supplierOpeningBalance_field.getText()+")");
                ps.execute();
                // <editor-fold defaultstate="collapsed" desc="supplierLedgerUpdate">
                    ps=cn.prepareStatement("SELECT sellerId FROM supplierList WHERE sellerName='"+supplierName_field.getText()+"'");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        sellerId=rs.getInt(1);
                    }
                    ps=cn.prepareStatement("INSERT INTO supplierLedger VALUES("+sellerId+",GETDATE(),0,'Opening Balance',"+supplierOpeningBalance_field.getText()+",'',"+supplierOpeningBalance_field.getText()+",0)");
                    ps.execute();
//            </editor-fold>
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(pTransaction_panel, ex.getLocalizedMessage());
        }
        supplierName_field.setText(null);
        supplierAddress_field.setText("Address");
        supplierContactNo_field.setText("Contact No");
        supplierOpeningBalance_field.setText("Opening Balance");
        supplierAddress_field.setForeground(Color.GRAY);
        supplierContactNo_field.setForeground(Color.GRAY);
        supplierOpeningBalance_field.setForeground(Color.GRAY);
        supplierName_field.requestFocus();
        getSupplierNames();
    }//GEN-LAST:event_supplierOpeningBalance_fieldActionPerformed

    private void supplierOpeningBalance_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierOpeningBalance_fieldFocusGained
        focusGain(supplierOpeningBalance_field, "Opening Balance");
    }//GEN-LAST:event_supplierOpeningBalance_fieldFocusGained

    private void supplierOpeningBalance_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierOpeningBalance_fieldFocusLost
        focusLost(supplierOpeningBalance_field, "Opening Balance");
    }//GEN-LAST:event_supplierOpeningBalance_fieldFocusLost

    private void supplierOpeningBalance_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_supplierOpeningBalance_fieldKeyTyped
        if(supplierOpeningBalance_field.getText().length()==50){
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            // <editor-fold defaultstate="collapsed" desc="statusLable"> 
                    statusBarLabel.setText("TEXT CANNOT BE MORE THAN 50 CHARACHTERS !");
                    statusBarLabel.setForeground(Color.RED);
                    Timer timer = new Timer(2500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            statusBarLabel.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
//                    </editor-fold>
        }
    }//GEN-LAST:event_supplierOpeningBalance_fieldKeyTyped

    private void addBank_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBank_menuActionPerformed
        Visiblefalse(addBankNames_lpane, addBankName);
        getBankList();
        bankName_field.requestFocus();
    }//GEN-LAST:event_addBank_menuActionPerformed

    private void addExpense_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpense_menuActionPerformed
        Visiblefalse(addExpenseName_lpane, addExpenseName);
        getExpenseList();
        expenseName_field.requestFocus();
    }//GEN-LAST:event_addExpense_menuActionPerformed

    private void addInvestment_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addInvestment_menuActionPerformed
    capital = new Capital(this, Calc);
    capital.setVisible(true);
    capital.setLocation(100, 100);
    capital.getOwnerNames(capital.ownerName_comboBox);
    capital.getBankNames(capital.bankNameBox_owner);
    }//GEN-LAST:event_addInvestment_menuActionPerformed

    private void addOwner_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOwner_menuActionPerformed
    owner = new Owner(this, Calc);
    owner.setVisible(true);
    owner.setLocation(100, 100);
    owner.getBankNames(owner.bankNameBox_owner);
    }//GEN-LAST:event_addOwner_menuActionPerformed

    private void banKOpeningBalance_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banKOpeningBalance_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_banKOpeningBalance_fieldActionPerformed

    private void banKOpeningBalance_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_banKOpeningBalance_fieldFocusGained
        focusGain(banKOpeningBalance_field, "Opening Balance");
    }//GEN-LAST:event_banKOpeningBalance_fieldFocusGained

    private void banKOpeningBalance_fieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_banKOpeningBalance_fieldFocusLost
        focusLost(banKOpeningBalance_field, "Opening Balance");
    }//GEN-LAST:event_banKOpeningBalance_fieldFocusLost

    private void banKOpeningBalance_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_banKOpeningBalance_fieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_banKOpeningBalance_fieldKeyTyped

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Software.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Software().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddClient_Btn;
    private javax.swing.JButton AddSupplier_Btn;
    private javax.swing.JButton B0;
    protected javax.swing.JButton B1;
    private javax.swing.JButton B2;
    private javax.swing.JButton B3;
    private javax.swing.JButton B4;
    private javax.swing.JButton B5;
    private javax.swing.JButton B6;
    private javax.swing.JButton B7;
    private javax.swing.JButton B8;
    private javax.swing.JButton B9;
    private javax.swing.JButton BDivide;
    private javax.swing.JButton BEquals2;
    private javax.swing.JButton BMinus;
    private javax.swing.JButton BMultiply;
    private javax.swing.JButton BPlus;
    private javax.swing.JButton BPoint;
    private javax.swing.JButton BackSpace;
    private javax.swing.JPanel Backgorund;
    private javax.swing.JPanel Banner;
    private javax.swing.JPanel Btns_expense;
    private javax.swing.JPanel Buttons;
    private javax.swing.JButton C;
    private javax.swing.JRadioButton Cash2;
    private javax.swing.JRadioButton Cash3;
    private javax.swing.JRadioButton Cash4;
    private javax.swing.JButton Ce;
    private javax.swing.JRadioButton Cheque2;
    private javax.swing.JRadioButton Cheque3;
    private javax.swing.JRadioButton Cheque4;
    private javax.swing.JLabel Client;
    private javax.swing.JLabel Client1;
    private javax.swing.JLabel Client2;
    private javax.swing.JRadioButton Credit2;
    private javax.swing.JRadioButton Credit3;
    private javax.swing.JRadioButton Credit4;
    private javax.swing.JLabel Date2;
    private javax.swing.JLabel Date3;
    private javax.swing.JLabel Date4;
    private javax.swing.JLabel Date5;
    private javax.swing.JLabel Date6;
    private javax.swing.JMenuItem DeliveryCh_menu;
    private javax.swing.JLabel ENo2;
    private javax.swing.JLabel ENo3;
    private javax.swing.JLabel ENo4;
    private javax.swing.JMenu Edit;
    private javax.swing.JMenu File;
    private javax.swing.JPanel Main;
    private javax.swing.JLabel Modes2;
    private javax.swing.JMenuItem PurchaseO_menu;
    private javax.swing.JMenuItem PurchaseTr_menu;
    private javax.swing.JPanel Status;
    private javax.swing.JMenuItem StockRe_menu;
    private javax.swing.JTextField TextFeild;
    private javax.swing.JPanel Tree;
    private javax.swing.JMenu ViewLedger;
    private javax.swing.JButton ViewReport;
    private javax.swing.JLayeredPane addBankNames_lpane;
    private javax.swing.JPanel addBankNames_panel;
    private javax.swing.JButton addBank_btn;
    private javax.swing.JMenuItem addBank_menu;
    private javax.swing.JButton addChalan;
    private javax.swing.JLayeredPane addClient_lpane;
    private javax.swing.JPanel addClient_panel;
    private javax.swing.JButton addExpense;
    private javax.swing.JButton addExpenseName_btn;
    private javax.swing.JLayeredPane addExpenseName_lpane;
    private javax.swing.JPanel addExpenseName_panel;
    private javax.swing.JMenuItem addExpense_menu;
    private javax.swing.JMenuItem addInvestment_menu;
    private javax.swing.JMenuItem addItem_menu;
    private javax.swing.JMenuItem addOwner_menu;
    private javax.swing.JButton addProduct_btn;
    private javax.swing.JLayeredPane addProduct_lpane;
    private javax.swing.JPanel addProduct_panel;
    private javax.swing.JMenuItem addProducts_menu;
    private javax.swing.JMenuItem addSeller_menu;
    private javax.swing.JLayeredPane addSupplier_lpane;
    private javax.swing.JPanel addSupplier_panel;
    private javax.swing.ButtonGroup aging_modes;
    private javax.swing.JLayeredPane aigingReportParameter_lpane;
    private javax.swing.JPanel aigingReportParameter_panel;
    private javax.swing.JComboBox aigingReport_comboBox;
    private javax.swing.JLayeredPane aigingReport_lpane;
    private javax.swing.JMenu aigingReport_menu;
    private javax.swing.JPanel aigingReport_panel;
    private javax.swing.JTable aigingReport_table;
    private javax.swing.JTextField amountPaid_field;
    private javax.swing.JTextField amountRecieved_field;
    private javax.swing.JTextField banKOpeningBalance_field;
    private javax.swing.JLayeredPane bankBook_lpane;
    private javax.swing.JMenuItem bankLedger_menu;
    private javax.swing.JComboBox bankNameBox_E;
    private javax.swing.JComboBox bankNameBox_PT;
    private javax.swing.JComboBox bankNameBox_SI;
    private javax.swing.JComboBox bankNameBox_pV;
    private javax.swing.JComboBox bankNameBox_rV;
    private javax.swing.JTextField bankName_field;
    private javax.swing.JTable bankNames_table;
    private javax.swing.JPanel btns;
    private javax.swing.JPanel btns_dChalan;
    private javax.swing.JPanel btns_pTransaction;
    private javax.swing.JPanel btns_purchaseOrder;
    private javax.swing.JPanel btns_salesInvoice;
    private javax.swing.JLabel cOrP;
    private javax.swing.JLayeredPane calc_lpane;
    private javax.swing.JPanel calculator;
    private javax.swing.JRadioButton cash3;
    private javax.swing.JRadioButton cash4;
    private javax.swing.JLayeredPane cashBook_lpane;
    private javax.swing.JPanel cashBook_panel;
    private javax.swing.JTable cashBook_table;
    private javax.swing.JLabel chalanAmount;
    private javax.swing.JTextField chalanNo;
    private javax.swing.JLabel chalanQuan;
    private javax.swing.JRadioButton cheque3;
    private javax.swing.JRadioButton cheque4;
    private javax.swing.JPanel chequeBook_panel;
    private javax.swing.JTable chequeBook_table;
    private javax.swing.JTextField clientAddress_field;
    private javax.swing.JComboBox clientBox_PO;
    private javax.swing.JTextField clientContactNo_field;
    private javax.swing.JTable clientDetail_table;
    private javax.swing.JMenuItem clientLedger_menu;
    private javax.swing.JTextField clientName_Field;
    private javax.swing.JTextField clientOpeningBalance_field;
    private javax.swing.JComboBox clientsBox_Dc;
    private javax.swing.JComboBox clientsBox_SI;
    private javax.swing.JComboBox clientsBox_rV;
    private javax.swing.JMenuItem clientsSReport_menu;
    private javax.swing.JButton companyExpenseBtn;
    private javax.swing.JLabel companyStatus;
    private javax.swing.JLayeredPane dChalan_lpane;
    private javax.swing.JPanel dChalan_panel;
    private javax.swing.JTable dChalan_table;
    private datechooser.beans.DateChooserCombo dateField_Dc;
    private datechooser.beans.DateChooserCombo dateField_Ex;
    private datechooser.beans.DateChooserCombo dateField_O;
    private datechooser.beans.DateChooserCombo dateField_PT;
    private datechooser.beans.DateChooserCombo dateField_SI;
    private javax.swing.JLabel dateLabel;
    private javax.swing.ButtonGroup daysGroup;
    private javax.swing.JRadioButton detail;
    private javax.swing.JButton editChalan;
    private datechooser.beans.DateChooserCombo endDate;
    private datechooser.beans.DateChooserCombo endDate_aiging;
    private javax.swing.JMenuItem expenseLedger_menu;
    private javax.swing.ButtonGroup expenseModes;
    private javax.swing.JTextField expenseName_field;
    private javax.swing.JTable expenseNames_table;
    private javax.swing.JLayeredPane expense_lpane;
    private javax.swing.JMenuItem expense_menu;
    private javax.swing.JPanel expense_panel;
    private javax.swing.JTable expense_table;
    private datechooser.beans.DateChooserCombo fromDate;
    private javax.swing.JRadioButton hExpense;
    private javax.swing.JLabel homeLable;
    private javax.swing.JTextField iName_field;
    private javax.swing.JTextField invoiceNo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JComboBox ledgerBox;
    private javax.swing.JLayeredPane ledger_lpane;
    private javax.swing.JPanel ledger_panel;
    private javax.swing.JTable ledger_table;
    private javax.swing.JMenuBar main_menubar;
    private javax.swing.JButton makeChalan;
    private javax.swing.JButton makeInvoice;
    private javax.swing.JButton makeOrder;
    private javax.swing.JButton makePaidVoucher;
    private javax.swing.JButton makeRecivedVoucher;
    private javax.swing.JButton makeTransaction;
    private javax.swing.JTextField minStockAmount_field;
    private javax.swing.JLabel modeLabel;
    private javax.swing.JLabel modeLabel1;
    private javax.swing.ButtonGroup mode_E;
    private javax.swing.ButtonGroup mode_PT;
    private javax.swing.ButtonGroup mode_PV;
    private javax.swing.ButtonGroup mode_RV;
    private javax.swing.ButtonGroup mode_SI;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JTree myTree;
    private javax.swing.JButton newChalan;
    private javax.swing.JButton newExpense;
    private javax.swing.JButton newInvoice;
    private javax.swing.JButton newOrder;
    private javax.swing.JButton newTransaction;
    private javax.swing.JButton nextChalan;
    private javax.swing.JButton nextInvoice;
    private javax.swing.JButton nextOrder;
    private javax.swing.JButton nextTransaction;
    private javax.swing.JRadioButton nintyP;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JLabel noteLabelCashBook;
    private javax.swing.JLabel noteLabelStock;
    private javax.swing.JLabel noteLabelchequeBook;
    private javax.swing.JRadioButton oExpense;
    private javax.swing.JTextField openingStockAmount_field;
    private javax.swing.JTextField openingStockQauntiy_field;
    private javax.swing.JTextField orderNo;
    private javax.swing.JLabel orderNoLable;
    private javax.swing.JComboBox orderNo_box;
    private javax.swing.JLabel orderStatus_label;
    private javax.swing.JScrollPane pT_scrollPane;
    private javax.swing.JLayeredPane pTransaction_lpane;
    private javax.swing.JPanel pTransaction_panel;
    private javax.swing.JTable pTransaction_table;
    private javax.swing.ButtonGroup paidGroup;
    private javax.swing.JPanel paidVoucher;
    private javax.swing.JTextField paidVoucherNo;
    private javax.swing.JPanel paidVoucher_history;
    private javax.swing.JMenuItem paidVoucher_menu;
    private javax.swing.JMenuItem payableAiging_menu;
    private javax.swing.JLabel paymentLeft;
    private javax.swing.JLabel paymentLeftLble;
    private javax.swing.JLayeredPane paymentPaid_lpane;
    private javax.swing.JPanel paymentPaid_panel;
    private javax.swing.JTable paymentPaid_table;
    private javax.swing.JTable paymentRcvd_table;
    private javax.swing.JPanel paymentReceived_panel;
    private javax.swing.JLayeredPane paymentRecieved_lpane;
    private javax.swing.JLabel paymentStatus_label;
    private javax.swing.JButton personalExpenseBtn;
    private javax.swing.JButton prevTransaction;
    private javax.swing.JButton previousChalan;
    private javax.swing.JButton previousInvoice;
    private javax.swing.JButton previousOrder;
    private javax.swing.JButton printChalan;
    private javax.swing.JButton printInvoice;
    private javax.swing.JButton printLedger;
    private javax.swing.JButton printSalesReport;
    private javax.swing.JTable productNames_table;
    private javax.swing.JMenuItem productsPReport_menu;
    private javax.swing.JLabel profitAmount;
    private javax.swing.JLabel profitGain;
    private javax.swing.JLayeredPane profitReportPerimeter_lpane;
    private javax.swing.JLayeredPane profitReport_lpane;
    private javax.swing.JPanel profitReport_panel;
    private javax.swing.JPanel profitReport_panel1;
    private javax.swing.JLayeredPane purchaseOrder_lpane;
    private javax.swing.JPanel purchaseOrder_panel;
    private javax.swing.JTable purchaseOrder_table;
    private javax.swing.JMenu purchaseReport_menu;
    private javax.swing.JPanel rcvdHistory_panel;
    private javax.swing.JTextField rcvdVNo;
    private javax.swing.JPanel rcvdVoucher;
    private javax.swing.JMenuItem rcvdVoucher_menu;
    private javax.swing.JMenuItem recievableAiging_menu;
    private javax.swing.JMenu reports_menu;
    private javax.swing.ButtonGroup saleR_modes;
    private javax.swing.JMenuItem salersSReport_menu;
    private javax.swing.JLayeredPane salesInvoice_lpane;
    private javax.swing.JMenuItem salesInvoice_menu;
    private javax.swing.JPanel salesInvoice_panel;
    private static javax.swing.JTable salesInvoice_table;
    private javax.swing.JMenu salesRe;
    private javax.swing.JLayeredPane salesReportParameter_lpane;
    private javax.swing.JPanel salesReportParameter_panel;
    private javax.swing.JComboBox salesReport_comboBox;
    private javax.swing.JLayeredPane salesReport_lpane;
    private javax.swing.ButtonGroup salesReport_modes;
    private javax.swing.JPanel salesReport_panel;
    private javax.swing.JTable salesReport_table;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JMenuItem sellerLedger_menu;
    private javax.swing.JLabel sellerNameLabel_PT;
    private javax.swing.JMenuItem sellersPReport_menu;
    private javax.swing.JRadioButton sixtyP;
    private datechooser.beans.DateChooserCombo startDate;
    private datechooser.beans.DateChooserCombo startingDate_aiging;
    private javax.swing.JLabel statusBarLabel;
    private javax.swing.JLabel stockAmount;
    private javax.swing.JLayeredPane stockReport_lpane;
    private javax.swing.JPanel stockReport_panel;
    private javax.swing.JTable stockReport_table;
    private javax.swing.JRadioButton summary;
    private javax.swing.JTextField supplierAddress_field;
    private javax.swing.JComboBox supplierBox_pT;
    private javax.swing.JComboBox supplierBox_pV;
    private javax.swing.JTextField supplierContactNo_field;
    private javax.swing.JTable supplierDetail_table;
    private javax.swing.JTextField supplierName_field;
    private javax.swing.JTextField supplierOpeningBalance_field;
    private javax.swing.JLabel tAmountLabel1;
    private javax.swing.JLabel tAmountLabel2;
    private javax.swing.JLabel tAmount_aiging;
    private javax.swing.JTextField tAmount_fieldE;
    private javax.swing.JTextField tAmount_field_PT;
    private javax.swing.JTextField tAmount_field_SI;
    private javax.swing.JLabel tAmount_label;
    private javax.swing.JRadioButton thirtyP;
    private javax.swing.JLayeredPane thresholdPoint_lpane;
    private javax.swing.JPanel thresholdPoint_panel;
    private javax.swing.JTable thresholdPoint_table;
    private datechooser.beans.DateChooserCombo toDate;
    private javax.swing.JRadioButton totalAiging;
    private javax.swing.JLabel totalStockAmount_label;
    private javax.swing.JLabel transactionAmount;
    private javax.swing.JTextField transactionNo;
    private javax.swing.JLabel transactionQuan;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JLayeredPane unInvoicedChReport_lpane;
    private javax.swing.JPanel unInvoicedChReport_panel;
    private javax.swing.JTable unInvoicedChReport_table;
    private javax.swing.JTextField unit_field;
    private javax.swing.JButton viewAigingBW_dates;
    private javax.swing.JButton viewLedger_Button;
    private javax.swing.JButton viewProfit;
    private javax.swing.JButton viewReport;
    // End of variables declaration//GEN-END:variables
}
