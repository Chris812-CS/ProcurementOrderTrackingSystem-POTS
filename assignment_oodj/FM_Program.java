package assignment_oodj;

//import FM_Classes.Finance_Manager;
//import FM_Classes.FileOperations;
//import FM_Classes.Item;
//import FM_Classes.ItemUnitPrice;
//import FM_Classes.PaymentHistory;
//import FM_Classes.PurchaseRequisite;
//import FM_Classes.PurchaseOrder;
//import FM_Classes.Sales;
//import FM_Classes.Supplier;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.RowFilter;

//import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class FM_Program extends javax.swing.JFrame {
//    private static FM_Program() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

//    DecimalFormat df = new DecimalFormat("0.00");
    private boolean Admin;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    FileOperations<PurchaseOrder> po = new FileOperations<>();
    FileOperations<PurchaseRequisite> pr = new FileOperations<>();
    FileOperations<Item> itm = new FileOperations<>();
    FileOperations<Sales> sales = new FileOperations<>();
    FileOperations<ItemUnitPrice> unitprice = new FileOperations<>();
    FileOperations<Supplier> supplier = new FileOperations();
    FileOperations<PaymentHistory> history = new FileOperations();
    
    private ArrayList<PurchaseOrder> POList = po.ReadFile("src/data/PurchaseOrder.txt", new PurchaseOrder());
    private ArrayList<PurchaseRequisite> PRList = pr.ReadFile("src/data/PurchaseRequisite.txt", new PurchaseRequisite());
    private ArrayList<Item> ItemList = itm.ReadFile("src/data/Item.txt", new Item());
    private ArrayList<Sales> SalesList = sales.ReadFile("src/data/Sales.txt", new Sales());
    private ArrayList<ItemUnitPrice> IUPList = unitprice.ReadFile("src/data/ItemUnitPrice.txt", new ItemUnitPrice());
    private ArrayList<Supplier> SupplierList = supplier.ReadFile("src/data/Supplier.txt", new Supplier());
    private ArrayList<PaymentHistory> HistoryList = history.ReadFile("src/data/PaymentHistory.txt", new PaymentHistory());
    
    Item item = new Item();
    Sales sale = new Sales();
    PurchaseRequisite PR = new PurchaseRequisite();
    PurchaseOrder PO = new PurchaseOrder();
    Supplier sup = new Supplier();
    
    private HashMap<String, String> ids = new HashMap<String, String>();
    private HashSet<String> InventoryList = new HashSet<>();
    private HashSet<String> Approvelist = new HashSet<>();
    private HashSet<String> SupplierRecordList = new HashSet<>();
    
    private int totalDeliveryDays = 0;
    private int numberOfDeliveredOrders = 0;
    private int averageDays = 0;    
    
    DecimalFormat rm = new DecimalFormat("RM    0.00");
    
    DefaultTableModel InventoryTable;
    DefaultTableModel IUPTable;
    
    Finance_Manager FM = new Finance_Manager();
    
    
    public FM_Program(Finance_Manager fm){
        initComponents();
        InventoryTable = (DefaultTableModel)tblinventory.getModel();
        IUPTable = (DefaultTableModel)tbliup.getModel();


            checkPoStatus();
            DisplayPaymentHistory();
            
            PaymentSummary.addTab("Make Payment", MakePayment);

            // Remove a tab
            int index = PaymentSummary.indexOfTab("Make Payment");
            if (index != -1) {
            PaymentSummary.removeTabAt(index);
            
            LoadInventory();
            InitPOPage();
            
            FM = fm;
            SetNameID();
            FM.readLoginCredentials();
            FM.setPOwList(POList, PRList);
            FM.DisplayProfile(ProfileUserType, ProfileID, ProfileFN, ProfileLN, ProfileGender, ProfileEmail, ProfileContact);
            this.setVisible(true);
           if(FM.getUserType().equals("A")){
            Admin = true;
        }
    }
    }

//    FM_Program() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    
        public void checkPoStatus() {
        creationTimelbl.setText("");
        itemIDlbl.setText("");
        itemNamelbl.setText("");
        itemDescriptionlbl.setText("");
        amountRepurchase.setText("");
        QuantityInStock.setText("");
        ReorderLevel.setText("");
        totalcostlbl.setText("");
        unitcostlbl.setText("");
        totalsaleslbl.setText("");
        salesPercentage.setText("");
        SupplierID.setText("");
        SupplierName.setText("");
        SupplierContact.setText("");
        SupplierRating.setText("");
        SupplierAddress.setText("");
        deliverylbl.setText("");
        recordTable.clearSelection();
        
        PoNumber.removeAllItems();
        
        //get purchase history
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
        model.getDataVector().removeAllElements();
        
        for (String poInList : FM.List()) {
            PoNumber.addItem(poInList);
        } 
        InventoryTable = (DefaultTableModel)tblinventory.getModel();
        IUPTable = (DefaultTableModel)tbliup.getModel();
        LoadItmIDCMB();
    }
    
    public void show_PODetails(String IDSelected){       
        this.ids = FM.getDatas(IDSelected);

        int quantityinstock = Integer.parseInt(ids.get("Quantity_In_Stock"));
        int reorderlevel = Integer.parseInt(ids.get("Reorder_Level"));

        //calculate reorder level
        double reordercalculate = ((reorderlevel - quantityinstock) / (double) reorderlevel) * 100;
        
        double[] percentageResult = FM.calculation(ids.get("Item_ID"));        
        
        if(percentageResult[0] > 0){
            salesPercentage.setForeground(new Color(75, 176, 80));
        }
        else{
            salesPercentage.setForeground(Color.RED);
        }
        
        Record(ids.get("Supplier_ID"));
        
        
        //show in dashboard
        creationTimelbl.setText(ids.get("PO_Creation_TimeStamp"));
        itemIDlbl.setText(ids.get("Item_ID"));
        itemNamelbl.setText(ids.get("Item_Name"));
        itemDescriptionlbl.setText(ids.get("Item_Description"));
        amountRepurchase.setText(ids.get("Quantity"));
        QuantityInStock.setText(ids.get("Quantity_In_Stock"));
        ReorderLevel.setText(String.format("%.1f%%", reordercalculate));
        totalcostlbl.setText("RM " + ids.get("Total") + "0");
        unitcostlbl.setText("RM" + ids.get("Unit_Cost") + "0");
        totalsaleslbl.setText(String.format("%.1f%%", percentageResult[1]));
        salesPercentage.setText(String.format("%.1f%%", percentageResult[0]));
        SupplierID.setText(ids.get("Supplier_ID"));
        SupplierName.setText(ids.get("Supplier_Name"));
        SupplierContact.setText(ids.get("Contact_Number"));
        SupplierRating.setText(ids.get("Supplier_Rating"));
        SupplierAddress.setText(ids.get("Address"));
        deliverylbl.setText(Integer.toString(FM.getTotalDeliveryDays()));
    }
    
    public void Record(String supplierid){        
        HashSet<Object> deliveredOrders = FM.List(supplierid);
        
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
                    
        for (Object rowData : deliveredOrders) {
            int i = model.getRowCount() + 1;
    // Check if rowData is an array (assuming it's an Object[] like in your example)
            if (rowData instanceof Object[]) {
                // Add the row to the table (prepend the row number, i)
                Object[] row = (Object[]) rowData;
                Object[] fullRow = new Object[row.length + 1];  
                fullRow[0] = i;  // Add the row index=
                System.arraycopy(row, 0, fullRow, 1, row.length);  
        
                model.addRow(fullRow);}}   
    }    
  
    public void DisplayPaymentHistory() {
        HashSet<Object> histories = FM.PaymentHistoryList(); // Assume this returns a HashSet of Object[].

        DefaultTableModel model = (DefaultTableModel) paymentHistoryTable.getModel();

        for (Object rowData : histories) {
                // Directly cast and add the row to the table model.
                Object[] row = (Object[]) rowData;
                model.addRow(row);  
        }
    }
        
    public void filterSearch(String search){
        DefaultTableModel model = (DefaultTableModel) paymentHistoryTable.getModel();
        
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(model);
        paymentHistoryTable.setRowSorter(rowSorter);
        
        if (search.trim().isEmpty()) {
            rowSorter.setRowFilter(null); // No filter if search is empty
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + search)); // Case-insensitive search
        }
        
    }
    
    public void MakePaymentTab(String POID){
        ids = FM.getDatas(POID);
        
        double tax = Double.parseDouble(ids.get("Total")) * 0.06;
        double total = tax + Double.parseDouble(ids.get("Total"));
        
        String tax1 = String.format("%.2f", tax);
        String total1 = String.format("%.2f", total);
        
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(dateFormatter);
        
        datelbl.setText(formattedDate);
        po_summary.setText(POID);
        SupplierID1.setText(ids.get("Supplier_ID"));
        SupplierName1.setText(ids.get("Supplier_Name"));
        SupplierContact1.setText(ids.get("Contact_Number"));
        SupplierAddress1.setText(ids.get("Address"));
        BankName.setText(ids.get("Bank_Name"));
        BankAccNo.setText(ids.get("Bank_Account"));
        itemID_summary.setText(ids.get("Item_ID"));
        itemName_summary.setText(ids.get("Item_Name"));
        Quantity_summary.setText(ids.get("Quantity"));
        Amount_summary.setText(ids.get("Total"));
        SubTotal.setText(ids.get("Total"));
        Tax.setText(tax1);
        total_summary.setText(total1);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ItemComboBox = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        pnlTopPanel = new javax.swing.JPanel();
        lblNexusLogo = new javax.swing.JLabel();
        lblFM_ID = new javax.swing.JLabel();
        PnlSMDashboard = new javax.swing.JPanel();
        lblFM_Name = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dashboard = new javax.swing.JTabbedPane();
        PaymentHIstory = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        paymentHistoryTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        pnlVitm = new javax.swing.JPanel();
        pnlVI = new javax.swing.JPanel();
        tblscrollpanel = new javax.swing.JScrollPane();
        tblinventory = new javax.swing.JTable();
        pnladddetails = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbliup = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtfielditmid = new javax.swing.JTextField();
        txtfieldname = new javax.swing.JTextField();
        txtfielddesc = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtfieldQiS = new javax.swing.JTextField();
        txtfieldRL = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        lblitmstatus = new javax.swing.JLabel();
        lblstatusstar = new javax.swing.JLabel();
        cmbitmid = new javax.swing.JComboBox<>();
        btnclear = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        PurchaseOrder = new javax.swing.JPanel();
        PaymentSummary = new javax.swing.JTabbedPane();
        ApproveReject = new javax.swing.JPanel();
        PoNumber = new javax.swing.JComboBox<>();
        approvebtn = new javax.swing.JButton();
        rejectbtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        creationTimelbl = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        itemIDlbl = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        itemNamelbl = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        itemDescriptionlbl = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        amountRepurchase = new javax.swing.JLabel();
        creationTimelbl2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        ReorderLevel = new javax.swing.JLabel();
        ReorderLevel1 = new javax.swing.JLabel();
        QuantityInStock = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        totalcostlbl = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        unitcostlbl = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        totalsaleslbl = new javax.swing.JLabel();
        ReorderLevel2 = new javax.swing.JLabel();
        salesPercentage = new javax.swing.JLabel();
        ReorderLevel4 = new javax.swing.JLabel();
        creationTimelbl1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jlabel = new javax.swing.JLabel();
        SupplierID = new javax.swing.JLabel();
        jlabel1 = new javax.swing.JLabel();
        SupplierName = new javax.swing.JLabel();
        jlabel2 = new javax.swing.JLabel();
        SupplierContact = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        SupplierRating = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        deliverylbl = new javax.swing.JLabel();
        jlabel3 = new javax.swing.JLabel();
        SupplierAddress = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        recordTable = new javax.swing.JTable();
        itemDescriptionlbl6 = new javax.swing.JLabel();
        ViewPO = new javax.swing.JPanel();
        pnlViewPO = new javax.swing.JPanel();
        ScrollPaneViewPO = new javax.swing.JScrollPane();
        pnlViewPurchaseOrder = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lblViewPODate = new javax.swing.JLabel();
        lblViewPOID = new javax.swing.JLabel();
        lblViewPOName = new javax.swing.JLabel();
        lblViewPOStaffID = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        POViewTable = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        lblViewPOApprovedStaffID = new javax.swing.JLabel();
        lblViewPOApprovedName = new javax.swing.JLabel();
        lblViewPOApprovedDate = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        lblViewPOSupplier = new javax.swing.JLabel();
        lblViewPOSuppID = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel68 = new javax.swing.JLabel();
        lblViewDelStatus = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        lblViewDelDate = new javax.swing.JLabel();
        cmbPOID = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        MakePayment = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jlabel4 = new javax.swing.JLabel();
        SupplierID1 = new javax.swing.JLabel();
        jlabel5 = new javax.swing.JLabel();
        SupplierName1 = new javax.swing.JLabel();
        jlabel6 = new javax.swing.JLabel();
        SupplierContact1 = new javax.swing.JLabel();
        jlabel7 = new javax.swing.JLabel();
        SupplierAddress1 = new javax.swing.JLabel();
        jlabel8 = new javax.swing.JLabel();
        BankName = new javax.swing.JLabel();
        jlabel9 = new javax.swing.JLabel();
        BankAccNo = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        itemID_summary = new javax.swing.JLabel();
        itemName_summary = new javax.swing.JLabel();
        Quantity_summary = new javax.swing.JLabel();
        Amount_summary = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        SubTotal = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        Jlabel21 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        Tax = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        total_summary = new javax.swing.JLabel();
        confirmbtn = new javax.swing.JButton();
        rejectPaymentbtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        po_summary = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        datelbl = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        ProfileID = new javax.swing.JLabel();
        ProfileUserType = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        ProfileFN = new javax.swing.JLabel();
        ProfileLN = new javax.swing.JLabel();
        ProfileGender = new javax.swing.JLabel();
        ProfileEmail = new javax.swing.JLabel();
        ProfileContact = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        ProfileCurrentPass = new javax.swing.JPasswordField();
        ProfileNewPass = new javax.swing.JPasswordField();
        ProfileChgPass = new javax.swing.JButton();
        ChgPassError = new javax.swing.JLabel();
        ShowHidePass = new javax.swing.JToggleButton();
        btnLogout = new javax.swing.JButton();

        ItemComboBox.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        ItemComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ITM001", "ITM002", "ITM003", "ITM004", "ITM005","ITM006" }));
        ItemComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemComboBoxActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(228, 224, 225));
        setLocationByPlatform(true);
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(228, 224, 225));
        jPanel3.setPreferredSize(new java.awt.Dimension(1280, 720));

        pnlTopPanel.setBackground(new java.awt.Color(171, 136, 109));
        pnlTopPanel.setPreferredSize(new java.awt.Dimension(1280, 120));

        lblNexusLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/NexusLogoThemeSmall.png"))); // NOI18N

        lblFM_ID.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        lblFM_ID.setForeground(new java.awt.Color(228, 224, 225));
        lblFM_ID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblFM_ID.setText("Financial Manager ID");

        javax.swing.GroupLayout pnlTopPanelLayout = new javax.swing.GroupLayout(pnlTopPanel);
        pnlTopPanel.setLayout(pnlTopPanelLayout);
        pnlTopPanelLayout.setHorizontalGroup(
            pnlTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblNexusLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 469, Short.MAX_VALUE)
                .addComponent(lblFM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTopPanelLayout.setVerticalGroup(
            pnlTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNexusLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
            .addGroup(pnlTopPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblFM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PnlSMDashboard.setBackground(new java.awt.Color(228, 224, 225));

        lblFM_Name.setBackground(new java.awt.Color(228, 224, 225));
        lblFM_Name.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        lblFM_Name.setForeground(new java.awt.Color(73, 54, 40));
        lblFM_Name.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblFM_Name.setText("Welcome! SM_Name");

        jLabel2.setBackground(new java.awt.Color(228, 224, 225));
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(73, 54, 40));
        jLabel2.setText("Financial Manager Dashboard");

        javax.swing.GroupLayout PnlSMDashboardLayout = new javax.swing.GroupLayout(PnlSMDashboard);
        PnlSMDashboard.setLayout(PnlSMDashboardLayout);
        PnlSMDashboardLayout.setHorizontalGroup(
            PnlSMDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlSMDashboardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(lblFM_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PnlSMDashboardLayout.setVerticalGroup(
            PnlSMDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlSMDashboardLayout.createSequentialGroup()
                .addGroup(PnlSMDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFM_Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        dashboard.setBackground(new java.awt.Color(228, 224, 225));
        dashboard.setForeground(new java.awt.Color(73, 54, 40));
        dashboard.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        dashboard.setPreferredSize(new java.awt.Dimension(1280, 552));

        paymentHistoryTable.setAutoCreateRowSorter(true);
        paymentHistoryTable.setBackground(new java.awt.Color(228, 224, 225));
        paymentHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payment ID", "Supplier Name", "Payment TimeStamp", "Payment Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(paymentHistoryTable);

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        searchField.setText("Search Here...");
        searchField.setToolTipText("Search Here...");

        javax.swing.GroupLayout PaymentHIstoryLayout = new javax.swing.GroupLayout(PaymentHIstory);
        PaymentHIstory.setLayout(PaymentHIstoryLayout);
        PaymentHIstoryLayout.setHorizontalGroup(
            PaymentHIstoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaymentHIstoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PaymentHIstoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PaymentHIstoryLayout.createSequentialGroup()
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        PaymentHIstoryLayout.setVerticalGroup(
            PaymentHIstoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaymentHIstoryLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(PaymentHIstoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        dashboard.addTab("Payment History", PaymentHIstory);

        pnlVitm.setBackground(new java.awt.Color(228, 224, 225));

        pnlVI.setBackground(new java.awt.Color(228, 224, 225));

        tblinventory.setBackground(new java.awt.Color(214,192,179));
        tblinventory.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblinventory.setForeground(new java.awt.Color(73, 54, 40));
        tblinventory.getTableHeader().setOpaque(false);
        tblinventory.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblinventory.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblinventory.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblinventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Description", "Quantity in Stock", "Reorder Level"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblinventory.setGridColor(new java.awt.Color(214, 192, 179));
        tblinventory.getTableHeader().setReorderingAllowed(false);
        tblinventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblinventoryMouseClicked(evt);
            }
        });
        tblscrollpanel.setViewportView(tblinventory);

        pnladddetails.setBackground(new java.awt.Color(228, 224, 225));
        pnladddetails.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        tbliup.setAutoCreateRowSorter(true);
        tbliup.setBackground(new java.awt.Color(214,192,179));
        tbliup.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tbliup.setForeground(new java.awt.Color(73, 54, 40));
        tbliup.getTableHeader().setOpaque(false);
        tbliup.getTableHeader().setBackground(Color.decode("#AB886D"));
        tbliup.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tbliup.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tbliup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Unit Price (RM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbliup.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tbliup);

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel31.setText("Item ID");

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel32.setText("Item Name");

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel33.setText("Description");

        txtfielditmid.setEditable(false);
        txtfielditmid.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        txtfieldname.setEditable(false);
        txtfieldname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        txtfielddesc.setEditable(false);
        txtfielddesc.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel34.setText("Quantity in Stock");

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel35.setText("Reorder Level");

        txtfieldQiS.setEditable(false);
        txtfieldQiS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        txtfieldRL.setEditable(false);
        txtfieldRL.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel36.setText("Supplied by :");

        lblitmstatus.setFont(new java.awt.Font("Yu Gothic UI", 2, 14)); // NOI18N
        lblitmstatus.setForeground(new java.awt.Color(228, 224, 225));
        lblitmstatus.setText("*Item needs to be restocked as soon as possible!");

        lblstatusstar.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        lblstatusstar.setForeground(new java.awt.Color(228, 224, 225));
        lblstatusstar.setText("*");

        cmbitmid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbitmid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbitmid.setFocusable(false);
        cmbitmid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbitmidActionPerformed(evt);
            }
        });

        btnclear.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnclear.setText("Clear");
        btnclear.setFocusPainted(false);
        btnclear.setFocusable(false);
        btnclear.setRequestFocusEnabled(false);
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel43.setText("Item ID :");

        javax.swing.GroupLayout pnladddetailsLayout = new javax.swing.GroupLayout(pnladddetails);
        pnladddetails.setLayout(pnladddetailsLayout);
        pnladddetailsLayout.setHorizontalGroup(
            pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnladddetailsLayout.createSequentialGroup()
                .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnladddetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel36))
                    .addGroup(pnladddetailsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnladddetailsLayout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnclear))
                            .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel33)
                                .addComponent(jLabel32)
                                .addComponent(jLabel31)
                                .addComponent(txtfielditmid)
                                .addComponent(txtfieldname)
                                .addComponent(txtfielddesc)
                                .addGroup(pnladddetailsLayout.createSequentialGroup()
                                    .addComponent(jLabel34)
                                    .addGap(3, 3, 3)
                                    .addComponent(lblstatusstar, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40)
                                    .addComponent(jLabel35))
                                .addGroup(pnladddetailsLayout.createSequentialGroup()
                                    .addComponent(txtfieldQiS, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtfieldRL))
                                .addComponent(lblitmstatus, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pnladddetailsLayout.setVerticalGroup(
            pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladddetailsLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(btnclear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addGap(6, 6, 6)
                .addComponent(txtfielditmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfielddesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35)
                    .addComponent(lblstatusstar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfieldQiS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfieldRL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblitmstatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlVILayout = new javax.swing.GroupLayout(pnlVI);
        pnlVI.setLayout(pnlVILayout);
        pnlVILayout.setHorizontalGroup(
            pnlVILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVILayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblscrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnladddetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlVILayout.setVerticalGroup(
            pnlVILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVILayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlVILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tblscrollpanel)
                    .addComponent(pnladddetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlVitmLayout = new javax.swing.GroupLayout(pnlVitm);
        pnlVitm.setLayout(pnlVitmLayout);
        pnlVitmLayout.setHorizontalGroup(
            pnlVitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVitmLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(pnlVI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        pnlVitmLayout.setVerticalGroup(
            pnlVitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVitmLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlVI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        dashboard.addTab("View Stock Level", pnlVitm);

        PurchaseOrder.setBackground(new java.awt.Color(228, 224, 225));

        PaymentSummary.setBackground(new java.awt.Color(228, 224, 225));
        PaymentSummary.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        ApproveReject.setBackground(new java.awt.Color(228, 224, 225));
        ApproveReject.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        PoNumber.setBackground(new java.awt.Color(214, 192, 179));
        PoNumber.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        PoNumber.setToolTipText("PoNumber");
        PoNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PoNumberActionPerformed(evt);
            }
        });

        approvebtn.setBackground(new java.awt.Color(170, 252, 170));
        approvebtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        approvebtn.setText("Approve");
        approvebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approvebtnActionPerformed(evt);
            }
        });

        rejectbtn.setBackground(new java.awt.Color(255, 153, 153));
        rejectbtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        rejectbtn.setText("Reject");
        rejectbtn.setPreferredSize(new java.awt.Dimension(76, 23));
        rejectbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectbtnActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(214, 192, 179));
        jPanel7.setPreferredSize(new java.awt.Dimension(275, 275));

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel13.setText("PO Creation Time Stamp: ");

        creationTimelbl.setBackground(new java.awt.Color(228, 224, 225));
        creationTimelbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        creationTimelbl.setForeground(new java.awt.Color(73, 54, 41));
        creationTimelbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        creationTimelbl.setText("Time Stamp");

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Item ID: ");

        itemIDlbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        itemIDlbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        itemIDlbl.setText("Item ID");

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel15.setText("Item Name: ");

        itemNamelbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        itemNamelbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        itemNamelbl.setText("Item Name");

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel16.setText("Item Description: ");

        itemDescriptionlbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        itemDescriptionlbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        itemDescriptionlbl.setText("Item Description");

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel19.setText("Repurchase Amount: ");

        amountRepurchase.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        amountRepurchase.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        amountRepurchase.setText("Repurchase Amount");

        creationTimelbl2.setBackground(new java.awt.Color(228, 224, 225));
        creationTimelbl2.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        creationTimelbl2.setForeground(new java.awt.Color(73, 54, 41));
        creationTimelbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        creationTimelbl2.setText("Item Details");
        creationTimelbl2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(itemIDlbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(itemNamelbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(itemDescriptionlbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(amountRepurchase, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(creationTimelbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(creationTimelbl2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(creationTimelbl2)
                .addGap(3, 3, 3)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(creationTimelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemIDlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemNamelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemDescriptionlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amountRepurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(214, 192, 179));
        jPanel2.setPreferredSize(new java.awt.Dimension(275, 275));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel5.setText("Current Quantity In Stock");

        ReorderLevel.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        ReorderLevel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReorderLevel.setPreferredSize(new java.awt.Dimension(275, 275));

        ReorderLevel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        ReorderLevel1.setText("Percentage Below Reorder Level");

        QuantityInStock.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        QuantityInStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        QuantityInStock.setPreferredSize(new java.awt.Dimension(275, 275));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel6.setText("Total Cost");

        totalcostlbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        totalcostlbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel18.setText("Unit Cost per Item: ");

        unitcostlbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        unitcostlbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel20.setText("Sales Performance");

        totalsaleslbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        totalsaleslbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalsaleslbl.setPreferredSize(new java.awt.Dimension(275, 275));

        ReorderLevel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        ReorderLevel2.setText("Sales Percentage");

        salesPercentage.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        salesPercentage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salesPercentage.setPreferredSize(new java.awt.Dimension(275, 275));

        ReorderLevel4.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        ReorderLevel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReorderLevel4.setText("vs previous 30 days ");

        creationTimelbl1.setBackground(new java.awt.Color(228, 224, 225));
        creationTimelbl1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        creationTimelbl1.setForeground(new java.awt.Color(73, 54, 41));
        creationTimelbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        creationTimelbl1.setText("Performance Overview");
        creationTimelbl1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ReorderLevel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ReorderLevel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(QuantityInStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                            .addComponent(ReorderLevel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                            .addComponent(totalcostlbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalsaleslbl, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(salesPercentage, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                            .addComponent(ReorderLevel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(creationTimelbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(unitcostlbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(creationTimelbl1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QuantityInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReorderLevel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalcostlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unitcostlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalsaleslbl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReorderLevel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salesPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReorderLevel4)
                .addGap(32, 32, 32))
        );

        jPanel5.setBackground(new java.awt.Color(214, 192, 179));
        jPanel5.setPreferredSize(new java.awt.Dimension(275, 275));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Supplier Details");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jlabel.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlabel.setText("Supplier ID: ");

        SupplierID.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierID.setText("Supplier ID");

        jlabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel1.setText("Supplier Name：");

        SupplierName.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierName.setText("Supplier Name");

        jlabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel2.setText("Supplier Contact: ");

        SupplierContact.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierContact.setText("Supplier Contact");

        jLabel17.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel17.setText("Supplier Rating: ");

        SupplierRating.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierRating.setText("SupplierRating");

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel25.setText("Average Delivery Time");

        deliverylbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        deliverylbl.setText("Delivery Time");

        jlabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel3.setText("Supplier Address: ");

        SupplierAddress.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierAddress.setText("Supplier Address");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SupplierRating, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SupplierName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deliverylbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(SupplierAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(333, 333, 333))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SupplierContact, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupplierRating, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel1)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deliverylbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel2)
                    .addComponent(jlabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierContact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupplierAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jPanel11.setBackground(new java.awt.Color(214, 192, 179));
        jPanel11.setPreferredSize(new java.awt.Dimension(275, 275));

        recordTable.setBackground(new java.awt.Color(228, 224, 225));
        recordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. ", "Item ID", "Item Name", "Amount", "Total", "Delivery Time (Days)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(recordTable);
        if (recordTable.getColumnModel().getColumnCount() > 0) {
            recordTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            recordTable.getColumnModel().getColumn(1).setPreferredWidth(30);
            recordTable.getColumnModel().getColumn(2).setPreferredWidth(30);
            recordTable.getColumnModel().getColumn(3).setPreferredWidth(30);
            recordTable.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        itemDescriptionlbl6.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        itemDescriptionlbl6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemDescriptionlbl6.setText("Supplier Purchase Record History");
        itemDescriptionlbl6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(itemDescriptionlbl6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(itemDescriptionlbl6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );

        javax.swing.GroupLayout ApproveRejectLayout = new javax.swing.GroupLayout(ApproveReject);
        ApproveReject.setLayout(ApproveRejectLayout);
        ApproveRejectLayout.setHorizontalGroup(
            ApproveRejectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ApproveRejectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ApproveRejectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ApproveRejectLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ApproveRejectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)))
                    .addGroup(ApproveRejectLayout.createSequentialGroup()
                        .addComponent(PoNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(approvebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rejectbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        ApproveRejectLayout.setVerticalGroup(
            ApproveRejectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ApproveRejectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ApproveRejectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(approvebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rejectbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PoNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ApproveRejectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                    .addGroup(ApproveRejectLayout.createSequentialGroup()
                        .addGroup(ApproveRejectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ApproveRejectLayout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        PaymentSummary.addTab("Approve/Reject", ApproveReject);

        ViewPO.setBackground(new java.awt.Color(228, 224, 225));
        ViewPO.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        pnlViewPO.setBackground(new java.awt.Color(228, 224, 225));

        ScrollPaneViewPO.setBackground(new java.awt.Color(255, 255, 255));

        pnlViewPurchaseOrder.setBackground(new java.awt.Color(255, 255, 255));
        pnlViewPurchaseOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlViewPurchaseOrder.setPreferredSize(new java.awt.Dimension(738, 616));

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/NexusLogoBlackSmall.png"))); // NOI18N

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("PURCHASE ORDER");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel46.setText("Purchase Order ID :");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel47.setText("Date :");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel48.setText("Prepared by :");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel49.setText("Staff ID :");

        lblViewPODate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPODate.setText("Date");

        lblViewPOID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPOID.setText("Purchase Order ID");

        lblViewPOName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPOName.setText("Prepared Name");

        lblViewPOStaffID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPOStaffID.setText("Prepared Staff ID");

        POViewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Quantity", "Price", "Total"
            }
        ));
        POViewTable.setFocusable(false);
        POViewTable.setRequestFocusEnabled(false);
        POViewTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(POViewTable);

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        lblViewPOApprovedStaffID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPOApprovedStaffID.setText("Pending");

        lblViewPOApprovedName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPOApprovedName.setText("Pending");

        lblViewPOApprovedDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPOApprovedDate.setText("Pending");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel51.setText("Approved by :");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel52.setText("Staff ID :");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel53.setText("Approval Date :");

        jLabel66.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel66.setText("Supplier :");

        lblViewPOSupplier.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPOSupplier.setText("Prepared Name");

        lblViewPOSuppID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPOSuppID.setText("Prepared Staff ID");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel67.setText("Supplier ID :");

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel68.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Delivery");

        lblViewDelStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewDelStatus.setText("Pending");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel69.setText("Status :");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel72.setText("Delivered Date :");

        lblViewDelDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewDelDate.setText("Pending");

        javax.swing.GroupLayout pnlViewPurchaseOrderLayout = new javax.swing.GroupLayout(pnlViewPurchaseOrder);
        pnlViewPurchaseOrder.setLayout(pnlViewPurchaseOrderLayout);
        pnlViewPurchaseOrderLayout.setHorizontalGroup(
            pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel68)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                        .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblViewPOApprovedName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblViewPOApprovedStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblViewPOApprovedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblViewPODate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlViewPurchaseOrderLayout.createSequentialGroup()
                            .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblViewPOID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                                    .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblViewPOName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblViewPOStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblViewPOSuppID, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                        .addComponent(lblViewPOSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(21, 21, 21))))
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                        .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblViewDelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblViewDelStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlViewPurchaseOrderLayout.setVerticalGroup(
            pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(lblViewPOID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblViewPODate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                        .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(lblViewPOName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(lblViewPOStaffID)))
                    .addGroup(pnlViewPurchaseOrderLayout.createSequentialGroup()
                        .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66)
                            .addComponent(lblViewPOSupplier))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67)
                            .addComponent(lblViewPOSuppID))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(lblViewPOApprovedName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(lblViewPOApprovedStaffID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblViewPOApprovedDate)
                    .addComponent(jLabel53))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel68)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(lblViewDelStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblViewDelDate)
                    .addComponent(jLabel72))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ScrollPaneViewPO.setViewportView(pnlViewPurchaseOrder);

        cmbPOID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        cmbPOID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbPOID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPOIDActionPerformed(evt);
            }
        });
        cmbPOID.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbPOIDPropertyChange(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel65.setText("Purchase Order ID");

        jButton2.setText("View");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlViewPOLayout = new javax.swing.GroupLayout(pnlViewPO);
        pnlViewPO.setLayout(pnlViewPOLayout);
        pnlViewPOLayout.setHorizontalGroup(
            pnlViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewPOLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbPOID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                .addComponent(ScrollPaneViewPO, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnlViewPOLayout.setVerticalGroup(
            pnlViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewPOLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(pnlViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewPOLayout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addGap(0, 0, 0)
                        .addGroup(pnlViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbPOID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)))
                    .addComponent(ScrollPaneViewPO, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout ViewPOLayout = new javax.swing.GroupLayout(ViewPO);
        ViewPO.setLayout(ViewPOLayout);
        ViewPOLayout.setHorizontalGroup(
            ViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
            .addGroup(ViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ViewPOLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlViewPO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        ViewPOLayout.setVerticalGroup(
            ViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
            .addGroup(ViewPOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ViewPOLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlViewPO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        PaymentSummary.addTab("View", ViewPO);

        MakePayment.setBackground(new java.awt.Color(228, 224, 225));
        MakePayment.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(214, 192, 179));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel4.setText("Supplier Details: ");

        jPanel8.setBackground(new java.awt.Color(228, 224, 225));

        jlabel4.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jlabel4.setText("Supplier ID: ");

        SupplierID1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierID1.setText("Supplier ID");

        jlabel5.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel5.setText("Supplier Name：");

        SupplierName1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierName1.setText("Supplier Name");

        jlabel6.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel6.setText("Supplier Contact: ");

        SupplierContact1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierContact1.setText("Supplier Contact");

        jlabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel7.setText("Supplier Address: ");

        SupplierAddress1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        SupplierAddress1.setText("Supplier Address");

        jlabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel8.setText("Bank Name: ");

        BankName.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        BankName.setText("Bank Name");

        jlabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jlabel9.setText("Bank Account Number: ");

        BankAccNo.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        BankAccNo.setText("Bank Account Number");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SupplierID1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(SupplierName1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(jlabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SupplierContact1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jlabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SupplierAddress1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
                            .addComponent(BankName, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BankAccNo, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 57, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SupplierID1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel5)
                    .addComponent(jlabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SupplierName1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SupplierContact1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SupplierAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BankName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BankAccNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(228, 224, 225));

        jPanel10.setBackground(new java.awt.Color(153, 153, 153));
        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel9.setText("Item Name");

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel10.setText("Item ID");

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel11.setText("Amount");

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel12.setText("Quantity");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));

        itemID_summary.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        itemID_summary.setText("ItemID");

        itemName_summary.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        itemName_summary.setText("Item Name");

        Quantity_summary.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        Quantity_summary.setText("Quantity");

        Amount_summary.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        Amount_summary.setText("Amount");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(itemID_summary, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(itemName_summary, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(Quantity_summary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Amount_summary)
                .addGap(60, 60, 60))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(itemID_summary, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(itemName_summary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Quantity_summary))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Amount_summary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));

        SubTotal.setText("Sub Total");

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel24.setText("Sub Total: ");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Jlabel21.setBackground(new java.awt.Color(204, 204, 204));

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel22.setText("Tax (6%)");

        Tax.setText("Tax");

        javax.swing.GroupLayout Jlabel21Layout = new javax.swing.GroupLayout(Jlabel21);
        Jlabel21.setLayout(Jlabel21Layout);
        Jlabel21Layout.setHorizontalGroup(
            Jlabel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jlabel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tax, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Jlabel21Layout.setVerticalGroup(
            Jlabel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Jlabel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Jlabel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(Tax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panel.setBackground(new java.awt.Color(204, 204, 204));

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel23.setText("Total");

        total_summary.setText("Total");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total_summary, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(total_summary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        confirmbtn.setBackground(new java.awt.Color(170, 252, 170));
        confirmbtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        confirmbtn.setText("Confirm");
        confirmbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmbtnActionPerformed(evt);
            }
        });

        rejectPaymentbtn.setBackground(new java.awt.Color(255, 153, 153));
        rejectPaymentbtn.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        rejectPaymentbtn.setText("Reject");
        rejectPaymentbtn.setPreferredSize(new java.awt.Dimension(76, 23));
        rejectPaymentbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectPaymentbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Jlabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(confirmbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(rejectPaymentbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Jlabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rejectPaymentbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel8.setText("Order Summary: ");

        po_summary.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        po_summary.setText("PONUMBER");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po_summary, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(po_summary))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel3.setText("Payment Summary");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel7.setText("Date: ");

        datelbl.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        datelbl.setText("jLabel8");

        javax.swing.GroupLayout MakePaymentLayout = new javax.swing.GroupLayout(MakePayment);
        MakePayment.setLayout(MakePaymentLayout);
        MakePaymentLayout.setHorizontalGroup(
            MakePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MakePaymentLayout.createSequentialGroup()
                .addGroup(MakePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(MakePaymentLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(datelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MakePaymentLayout.createSequentialGroup()
                        .addContainerGap(53, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        MakePaymentLayout.setVerticalGroup(
            MakePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MakePaymentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MakePaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datelbl))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PaymentSummary.addTab("Payment Summary", MakePayment);

        javax.swing.GroupLayout PurchaseOrderLayout = new javax.swing.GroupLayout(PurchaseOrder);
        PurchaseOrder.setLayout(PurchaseOrderLayout);
        PurchaseOrderLayout.setHorizontalGroup(
            PurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PurchaseOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PaymentSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PurchaseOrderLayout.setVerticalGroup(
            PurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PurchaseOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PaymentSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 502, Short.MAX_VALUE)
                .addContainerGap())
        );

        dashboard.addTab("Purchase Order", PurchaseOrder);

        jPanel14.setBackground(new java.awt.Color(228, 224, 225));
        jPanel14.setLayout(null);

        jPanel15.setBackground(new java.awt.Color(214, 192, 179));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel54.setFont(new java.awt.Font("Yu Gothic UI", 0, 36)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(73, 54, 40));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Profile");

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile-modified (1).png"))); // NOI18N

        ProfileID.setFont(new java.awt.Font("Yu Gothic UI", 1, 20)); // NOI18N
        ProfileID.setForeground(new java.awt.Color(73, 54, 40));
        ProfileID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProfileID.setText("Profile ID");

        ProfileUserType.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        ProfileUserType.setForeground(new java.awt.Color(73, 54, 40));
        ProfileUserType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProfileUserType.setText("Profile Type");

        jSeparator4.setBackground(new java.awt.Color(171, 136, 109));
        jSeparator4.setForeground(new java.awt.Color(171, 136, 109));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel56.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(73, 54, 40));
        jLabel56.setText("First Name");

        jLabel57.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(73, 54, 40));
        jLabel57.setText("Last Name");

        jLabel58.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(73, 54, 40));
        jLabel58.setText("Gender");

        jLabel59.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(73, 54, 40));
        jLabel59.setText("Contact No.");

        jLabel60.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(73, 54, 40));
        jLabel60.setText("Email");

        jSeparator5.setBackground(new java.awt.Color(171, 136, 109));
        jSeparator5.setForeground(new java.awt.Color(73, 54, 40));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        ProfileFN.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ProfileFN.setForeground(new java.awt.Color(102, 102, 102));
        ProfileFN.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProfileFN.setText("First Name");

        ProfileLN.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ProfileLN.setForeground(new java.awt.Color(102, 102, 102));
        ProfileLN.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProfileLN.setText("Last Name");

        ProfileGender.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ProfileGender.setForeground(new java.awt.Color(102, 102, 102));
        ProfileGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProfileGender.setText("Gender");

        ProfileEmail.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ProfileEmail.setForeground(new java.awt.Color(102, 102, 102));
        ProfileEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProfileEmail.setText("Email");

        ProfileContact.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ProfileContact.setForeground(new java.awt.Color(102, 102, 102));
        ProfileContact.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProfileContact.setText("Contact No.");

        jLabel61.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(171, 136, 109));
        jLabel61.setText("*Contact the administrator to change personal details");

        jLabel62.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(73, 54, 40));
        jLabel62.setText("Change Password");

        jLabel63.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(73, 54, 40));
        jLabel63.setText("Current Password");

        jLabel64.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(73, 54, 40));
        jLabel64.setText("New Password");

        ProfileCurrentPass.setBackground(new java.awt.Color(228, 224, 225));
        ProfileCurrentPass.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ProfileCurrentPass.setForeground(new java.awt.Color(83, 60, 43));
        ProfileCurrentPass.setToolTipText("Password");
        ProfileCurrentPass.setBorder(null);
        ProfileCurrentPass.setMinimumSize(new java.awt.Dimension(64, 26));
        ProfileCurrentPass.setPreferredSize(new java.awt.Dimension(64, 26));
        ProfileCurrentPass.setSelectionColor(new java.awt.Color(178, 152, 132));

        ProfileNewPass.setBackground(new java.awt.Color(228, 224, 225));
        ProfileNewPass.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ProfileNewPass.setForeground(new java.awt.Color(83, 60, 43));
        ProfileNewPass.setToolTipText("Password");
        ProfileNewPass.setBorder(null);
        ProfileNewPass.setMinimumSize(new java.awt.Dimension(64, 26));
        ProfileNewPass.setPreferredSize(new java.awt.Dimension(64, 26));
        ProfileNewPass.setSelectionColor(new java.awt.Color(178, 152, 132));

        ProfileChgPass.setBackground(new java.awt.Color(228, 224, 225));
        ProfileChgPass.setFont(new java.awt.Font("Yu Gothic UI", 1, 20)); // NOI18N
        ProfileChgPass.setForeground(new java.awt.Color(46, 36, 28));
        ProfileChgPass.setText("Reset Password");
        ProfileChgPass.setBorder(null);
        ProfileChgPass.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ProfileChgPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfileChgPassActionPerformed(evt);
            }
        });

        ChgPassError.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        ChgPassError.setForeground(new java.awt.Color(255, 0, 51));

        ShowHidePass.setBackground(new java.awt.Color(228, 224, 225));
        ShowHidePass.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        ShowHidePass.setText("Show");
        ShowHidePass.setBorder(null);
        ShowHidePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowHidePassActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(228, 224, 225));
        btnLogout.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(46, 36, 28));
        btnLogout.setText("Logout");
        btnLogout.setBorder(null);
        btnLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(111, 111, 111)
                                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ProfileID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel55))))
                                .addGap(57, 57, 57))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97)))
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(ProfileUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56)
                            .addComponent(jLabel57)
                            .addComponent(jLabel58)
                            .addComponent(jLabel60)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ProfileLN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileFN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileContact, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProfileGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ShowHidePass, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(ProfileNewPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ProfileCurrentPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ProfileChgPass, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                    .addComponent(ChgPassError, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel56)
                                    .addComponent(ProfileFN, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel57)
                                    .addComponent(ProfileLN, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ProfileGender, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel58))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel60)
                                    .addComponent(ProfileEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ProfileContact, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel59))
                                .addGap(34, 34, 34)
                                .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(108, 108, 108)
                                        .addComponent(ChgPassError, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ProfileChgPass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(20, 20, 20)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(ProfileUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProfileID, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel63)
                        .addGap(18, 18, 18)
                        .addComponent(ProfileCurrentPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(ShowHidePass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProfileNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel15);
        jPanel15.setBounds(20, 28, 1230, 420);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1292, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        dashboard.addTab("Profile", jPanel4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(pnlTopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(PnlSMDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(82, 82, 82))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(pnlTopPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(PnlSMDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 541, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ItemComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ItemComboBoxActionPerformed

    private void rejectbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectbtnActionPerformed
        String IDSelected = (String) PoNumber.getSelectedItem();

        if(IDSelected != null){
            FM.updateDecision(IDSelected, "FM01");
        }

        JOptionPane.showMessageDialog(this, "Purchase Order Rejected. ");
        checkPoStatus();
    }//GEN-LAST:event_rejectbtnActionPerformed

    private void approvebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approvebtnActionPerformed
        String idSelected = (String) PoNumber.getSelectedItem();
        if(idSelected != null){
            PaymentSummary.addTab("Make Payment", MakePayment);
            PaymentSummary.setSelectedComponent(MakePayment);
            MakePaymentTab(idSelected);
        }
    }//GEN-LAST:event_approvebtnActionPerformed

    private void PoNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PoNumberActionPerformed
        String IDSelected = (String) PoNumber.getSelectedItem();

        if(IDSelected != null){
            show_PODetails(IDSelected);
        }
    }//GEN-LAST:event_PoNumberActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String search = searchField.getText();
        filterSearch(search);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void confirmbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmbtnActionPerformed
        String poNo = po_summary.getText();
        Double totalAmount = Double.parseDouble(total_summary.getText());
        
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(dateTimeFormatter);
        
        int answer = JOptionPane.showConfirmDialog(
            null, // Parent component, use null to center the dialog on the screen
            "Please double confirm that the payment is made.", // Message
            "Make Payment", // Title
            JOptionPane.OK_CANCEL_OPTION // This is the correct option type
        );

        if (answer == JOptionPane.OK_OPTION) {
            FM.updateDecision(poNo, FM.getUserID(), date, totalAmount);
            JOptionPane.showMessageDialog(MakePayment, "Purchase Order Approved. ");
            int index = PaymentSummary.indexOfTab("Make Payment");
            if (index != -1) {
                PaymentSummary.removeTabAt(index);
            
            PaymentSummary.setSelectedComponent(ApproveReject);
            
            }
        }  
    }//GEN-LAST:event_confirmbtnActionPerformed

    private void rejectPaymentbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectPaymentbtnActionPerformed
        String IDSelected = (String) PoNumber.getSelectedItem();

        if(IDSelected != null){
            FM.updateDecision(IDSelected, FM.getUserID());
        }

        JOptionPane.showMessageDialog(this, "Purchase Order Rejected. ");
        // Remove a tab
        int index = PaymentSummary.indexOfTab("Make Payment");
            if (index != -1) {
            PaymentSummary.removeTabAt(index);
        }
        PaymentSummary.setSelectedComponent(ApproveReject);
        checkPoStatus();
    }//GEN-LAST:event_rejectPaymentbtnActionPerformed

    private void tblinventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblinventoryMouseClicked
        int i = tblinventory.getSelectedRow();
        System.out.println(tblinventory.getValueAt(i, 0));
        cmbitmid.setSelectedIndex(i+1);
        DisplayInventoryDetails(i);
        checkQiS(i);
    }//GEN-LAST:event_tblinventoryMouseClicked

    private void cmbitmidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbitmidActionPerformed
        int i = cmbitmid.getSelectedIndex();
        if(i == -1){}
        else if(i != 0)
        {
            i = i-1;
            DisplayInventoryDetails(i);
            checkQiS(i);
            LoadIUP(IUPList, (String)tblinventory.getValueAt(i,0), IUPTable);
            tblinventory.setRowSelectionInterval(i, i);
            tblinventory.scrollRectToVisible(new Rectangle(tblinventory.getCellRect(i, 0, true)));
        }
        else{clearInventoryView();}
    }//GEN-LAST:event_cmbitmidActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        clearInventoryView();
    }//GEN-LAST:event_btnclearActionPerformed

    private void cmbPOIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPOIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPOIDActionPerformed

    private void cmbPOIDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbPOIDPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPOIDPropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        checkPoStatus();
        POList = po.ReadFile("src/data/PurchaseOrder.txt", new PurchaseOrder());
        PRList = pr.ReadFile("src/data/PurchaseRequisite.txt", new PurchaseRequisite());
        FM.setPOwList(POList, PRList);
        ScrollPaneViewPO.getVerticalScrollBar().setValue(0);
        if (!cmbPOID.getSelectedItem().equals("Select...")){
            pnlViewPurchaseOrder.setVisible(true);
            
            PurchaseRequisite thisPR = FM.ReturnPRwPO(PRList, cmbPOID.getSelectedItem().toString());
            int PMIndex = FM.ReturnUserIndex(thisPR.getPo().getPM_ID());
            
            int FMIndex = FM.ReturnUserIndex(thisPR.getPo().getFM_ID());
            int SupIndex = FM.ReturnSupIndex(thisPR.getPo().getSupplier_ID(), SupplierList);
            int ItemIndex = FM.ReturnItemIndex(ItemList, thisPR.getITM_ID());
            
            
            
            lblViewPOID.setText(thisPR.getPo().getPO_ID());
            lblViewPODate.setText(thisPR.getPo().getPO_Creation_TimeStamp());
            lblViewPOName.setText(FM.getAllUsers().get(PMIndex).getFirstName()+" "+
                                  FM.getAllUsers().get(PMIndex).getLastName());
            lblViewPOStaffID.setText(FM.getAllUsers().get(PMIndex).getUserID());
            
            lblViewPOSupplier.setText(SupplierList.get(SupIndex).getSupplierName());
            lblViewPOSuppID.setText(SupplierList.get(SupIndex).getSupplierID());
            
            if (FMIndex != -1){
                lblViewPOApprovedName.setText(FM.getAllUsers().get(FMIndex).getFirstName()+" "+
                                          FM.getAllUsers().get(FMIndex).getLastName());
                lblViewPOApprovedStaffID.setText(FM.getAllUsers().get(FMIndex).getUserID());
                lblViewPOApprovedDate.setText(thisPR.getPo().getFM_Approval_TimeStamp());
            } else {
                lblViewPOApprovedName.setText("Pending");
                lblViewPOApprovedStaffID.setText("Pending");
                lblViewPOApprovedDate.setText("Pending");
            }
            
            
            lblViewDelStatus.setText(thisPR.getPo().getDeliveryStatus());
            lblViewDelDate.setText(thisPR.getPo().getDelivered_TimeStamp());
            Object[] RowData = new Object[5];
            RowData[0] = thisPR.getITM_ID();
            RowData[1] = ItemList.get(ItemIndex).getItemName();
            RowData[2] = String.valueOf(thisPR.getPo().getQuantity());
            RowData[3] = rm.format(FM.returnIUP(IUPList,ItemList.get(ItemIndex).getItemID(),SupplierList.get(SupIndex).getSupplierID()).getUnitCost());
            RowData[4] = rm.format(thisPR.getPo().getTotal());
            
            DefaultTableModel dtm = (DefaultTableModel) POViewTable.getModel();
            try {dtm.removeRow(0);} catch(ArrayIndexOutOfBoundsException e){}
            dtm.addRow(RowData);

        } else {
            JOptionPane.showMessageDialog(null, "Please select a Purchase Order!", "Purchase Order not selected!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void ProfileChgPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfileChgPassActionPerformed
        FM.readLoginCredentials();
        FM.ResetPassword(ProfileCurrentPass.getText(),ProfileNewPass.getText(),ChgPassError, ProfileCurrentPass,ProfileNewPass,ShowHidePass);
    }//GEN-LAST:event_ProfileChgPassActionPerformed

    private void ShowHidePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowHidePassActionPerformed
        if(ShowHidePass.isSelected()){
            ProfileNewPass.setEchoChar((char)0);
        }else{
            ProfileNewPass.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_ShowHidePassActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here
        this.dispose();
        if(Admin == true){
            new Admin_Program(new Admin(FM));
        }else{
            new LoginUI();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    public FM_Program() {
    }
    
    

    public static void main(String args[]) {
    try {
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        FlatLaf.setGlobalExtraDefaults( Collections.singletonMap( "@accentColor", "#AB886D" ));
        FlatIntelliJLaf.setup();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FM_Program().setVisible(true);
            }
        });
    } catch (UnsupportedLookAndFeelException ex) {
//        Logger.getLogger(SM_Program.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("Please install flatlaf for a better experience!");
        }
    
//    new SM_Program().setVisible(true);
    }
    
    private void clearInventoryView()
    {
        txtfielditmid.setText("");
        txtfieldname.setText("");
        txtfielddesc.setText("");
        txtfieldQiS.setText("");
        txtfieldRL.setText("");
        tblinventory.clearSelection();
        tblscrollpanel.getVerticalScrollBar().setValue(0);
        lblitmstatus.setForeground(new java.awt.Color(228,224,225));
        lblstatusstar.setForeground(new java.awt.Color(228,224,225));
        if(cmbitmid.getSelectedIndex() != -1){cmbitmid.setSelectedIndex(0);}
        if(IUPTable != null){IUPTable.setRowCount(0);}
    }
    
    private void LoadItmIDCMB()
    {
        cmbitmid.removeAllItems();
        cmbitmid.addItem("Select...");
        for(String i: FM.getInventoryList())
        {
            cmbitmid.addItem(i);
        }
    }
    
    private void DisplayInventoryDetails(Integer i)
    {
        txtfielditmid.setText((String)tblinventory.getValueAt(i, 0));
        txtfieldname.setText((String)tblinventory.getValueAt(i, 1));
        txtfielddesc.setText((String)tblinventory.getValueAt(i, 2));
        txtfieldQiS.setText(String.valueOf(tblinventory.getValueAt(i, 3)));
        txtfieldRL.setText(String.valueOf(tblinventory.getValueAt(i, 4)));
    }
    
        private void checkQiS(Integer i)
    {
        int QiS = (Integer)tblinventory.getValueAt(i,3);
        int RL = (Integer)tblinventory.getValueAt(i,4);
        if(QiS<RL)
        {
            lblitmstatus.setForeground(new java.awt.Color(239,33,33));
            lblstatusstar.setForeground(new java.awt.Color(239,33,33));
            txtfieldQiS.setForeground(new java.awt.Color(239,33,33));
        }
        else
        {
            lblitmstatus.setForeground(new java.awt.Color(228,224,225));
            lblstatusstar.setForeground(new java.awt.Color(228,224,225));
            txtfieldQiS.setForeground(Color.BLACK);
        }
    }
        
    private void LoadInventory(){
        LoadItmIDCMB();
        InventoryTable.setRowCount(0);
        
        HashSet<Object> Inventories = FM.LoadInventory();
        
        for (Object rowData : Inventories) {
                // Directly cast and add the row to the table model.
                Object[] row = (Object[]) rowData;
                InventoryTable.addRow(row);  
        }
    }
    
    private void LoadIUP(ArrayList<ItemUnitPrice> IUPList,String ItemID, DefaultTableModel IUPTable)
    {
        IUPTable.setRowCount(0);

        for(ItemUnitPrice i: IUPList)
        {
            if(i.getItemID().equals(ItemID))
            {
                Object row[] = {i.getSupplierID(),i.getUnitCost()};
                IUPTable.addRow(row);
            }
        }
        if(IUPTable.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(null, "Please find supplier for this item ASAP!", "No Suppliers Found!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void InitPOPage(){
       cmbPOID.removeAllItems();
       cmbPOID.addItem("Select...");
       POViewTable.removeAll();
       pnlViewPurchaseOrder.setVisible(false);
       
       for (PurchaseOrder p: POList){
           if (!p.getStatus().equals("Rejected")){
            cmbPOID.addItem(p.getPO_ID());
           }
       }

    }
    
    private void SetNameID(){
        System.out.println(FM.getUserID());
        lblFM_ID.setText(FM.getUserID());
        lblFM_Name.setText("Welcome, "+FM.getFirstName()+" "+FM.getLastName()+"!");      
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Amount_summary;
    private javax.swing.JPanel ApproveReject;
    private javax.swing.JLabel BankAccNo;
    private javax.swing.JLabel BankName;
    private javax.swing.JLabel ChgPassError;
    private javax.swing.JComboBox<String> ItemComboBox;
    private javax.swing.JPanel Jlabel21;
    private javax.swing.JPanel MakePayment;
    private javax.swing.JTable POViewTable;
    private javax.swing.JPanel PaymentHIstory;
    private javax.swing.JTabbedPane PaymentSummary;
    private javax.swing.JPanel PnlSMDashboard;
    private javax.swing.JComboBox<String> PoNumber;
    private javax.swing.JButton ProfileChgPass;
    private javax.swing.JLabel ProfileContact;
    private javax.swing.JPasswordField ProfileCurrentPass;
    private javax.swing.JLabel ProfileEmail;
    private javax.swing.JLabel ProfileFN;
    private javax.swing.JLabel ProfileGender;
    private javax.swing.JLabel ProfileID;
    private javax.swing.JLabel ProfileLN;
    private javax.swing.JPasswordField ProfileNewPass;
    private javax.swing.JLabel ProfileUserType;
    private javax.swing.JPanel PurchaseOrder;
    private javax.swing.JLabel QuantityInStock;
    private javax.swing.JLabel Quantity_summary;
    private javax.swing.JLabel ReorderLevel;
    private javax.swing.JLabel ReorderLevel1;
    private javax.swing.JLabel ReorderLevel2;
    private javax.swing.JLabel ReorderLevel4;
    private javax.swing.JScrollPane ScrollPaneViewPO;
    private javax.swing.JToggleButton ShowHidePass;
    private javax.swing.JLabel SubTotal;
    private javax.swing.JLabel SupplierAddress;
    private javax.swing.JLabel SupplierAddress1;
    private javax.swing.JLabel SupplierContact;
    private javax.swing.JLabel SupplierContact1;
    private javax.swing.JLabel SupplierID;
    private javax.swing.JLabel SupplierID1;
    private javax.swing.JLabel SupplierName;
    private javax.swing.JLabel SupplierName1;
    private javax.swing.JLabel SupplierRating;
    private javax.swing.JLabel Tax;
    private javax.swing.JPanel ViewPO;
    private javax.swing.JLabel amountRepurchase;
    private javax.swing.JButton approvebtn;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnclear;
    private javax.swing.JComboBox<String> cmbPOID;
    private javax.swing.JComboBox<String> cmbitmid;
    private javax.swing.JButton confirmbtn;
    private javax.swing.JLabel creationTimelbl;
    private javax.swing.JLabel creationTimelbl1;
    private javax.swing.JLabel creationTimelbl2;
    private javax.swing.JTabbedPane dashboard;
    private javax.swing.JLabel datelbl;
    private javax.swing.JLabel deliverylbl;
    private javax.swing.JLabel itemDescriptionlbl;
    private javax.swing.JLabel itemDescriptionlbl6;
    private javax.swing.JLabel itemID_summary;
    private javax.swing.JLabel itemIDlbl;
    private javax.swing.JLabel itemName_summary;
    private javax.swing.JLabel itemNamelbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel jlabel;
    private javax.swing.JLabel jlabel1;
    private javax.swing.JLabel jlabel2;
    private javax.swing.JLabel jlabel3;
    private javax.swing.JLabel jlabel4;
    private javax.swing.JLabel jlabel5;
    private javax.swing.JLabel jlabel6;
    private javax.swing.JLabel jlabel7;
    private javax.swing.JLabel jlabel8;
    private javax.swing.JLabel jlabel9;
    private javax.swing.JLabel lblFM_ID;
    private javax.swing.JLabel lblFM_Name;
    private javax.swing.JLabel lblNexusLogo;
    private javax.swing.JLabel lblViewDelDate;
    private javax.swing.JLabel lblViewDelStatus;
    private javax.swing.JLabel lblViewPOApprovedDate;
    private javax.swing.JLabel lblViewPOApprovedName;
    private javax.swing.JLabel lblViewPOApprovedStaffID;
    private javax.swing.JLabel lblViewPODate;
    private javax.swing.JLabel lblViewPOID;
    private javax.swing.JLabel lblViewPOName;
    private javax.swing.JLabel lblViewPOStaffID;
    private javax.swing.JLabel lblViewPOSuppID;
    private javax.swing.JLabel lblViewPOSupplier;
    private javax.swing.JLabel lblitmstatus;
    private javax.swing.JLabel lblstatusstar;
    private javax.swing.JPanel panel;
    private javax.swing.JTable paymentHistoryTable;
    private javax.swing.JPanel pnlTopPanel;
    private javax.swing.JPanel pnlVI;
    private javax.swing.JPanel pnlViewPO;
    private javax.swing.JPanel pnlViewPurchaseOrder;
    private javax.swing.JPanel pnlVitm;
    private javax.swing.JPanel pnladddetails;
    private javax.swing.JLabel po_summary;
    private javax.swing.JTable recordTable;
    private javax.swing.JButton rejectPaymentbtn;
    private javax.swing.JButton rejectbtn;
    private javax.swing.JLabel salesPercentage;
    private javax.swing.JTextField searchField;
    private javax.swing.JTable tblinventory;
    private javax.swing.JTable tbliup;
    private javax.swing.JScrollPane tblscrollpanel;
    private javax.swing.JLabel total_summary;
    private javax.swing.JLabel totalcostlbl;
    private javax.swing.JLabel totalsaleslbl;
    private javax.swing.JTextField txtfieldQiS;
    private javax.swing.JTextField txtfieldRL;
    private javax.swing.JTextField txtfielddesc;
    private javax.swing.JTextField txtfielditmid;
    private javax.swing.JTextField txtfieldname;
    private javax.swing.JLabel unitcostlbl;
    // End of variables declaration//GEN-END:variables

   
}



