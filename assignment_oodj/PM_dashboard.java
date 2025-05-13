/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package assignment_oodj;

//import library
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;

import java.util.Collections;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import javax.swing.JTable;

public class PM_dashboard extends javax.swing.JFrame {

    private boolean Admin;
    Supplier supplier = new Supplier();
    FileOperations<Supplier> sup = new FileOperations<Supplier>();
    private ArrayList <Supplier> SupList = new ArrayList<>();

    Item items = new Item();
    FileOperations<Item> itm = new FileOperations<Item>();
    private ArrayList<Item> ItemList = new ArrayList<>();

    PurchaseOrder purchaseOrder = new PurchaseOrder();
    FileOperations<PurchaseOrder> po = new FileOperations<PurchaseOrder>();
    private ArrayList<PurchaseOrder> POList = new ArrayList<>();
    
    PurchaseRequisite purchaseRequisite = new PurchaseRequisite();
    FileOperations<PurchaseRequisite> pr = new FileOperations<PurchaseRequisite>();
    private ArrayList<PurchaseRequisite> PRList = new ArrayList<>();

    ItemUnitPrice itemUnitPrice = new ItemUnitPrice();
    FileOperations<ItemUnitPrice> iup = new FileOperations<ItemUnitPrice>();
    private ArrayList<ItemUnitPrice> IUPList = new ArrayList<>();
    
    //PM.PurchaseManager pm = new PM.PurchaseManager();
    Purchase_Manager pm = new Purchase_Manager();
    
    private static final SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private static final SimpleDateFormat tmstmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    
    int selectedRow = -1;
    String userID;
    
    
    
    public PM_dashboard(Purchase_Manager pm) {
        initComponents();
        
        
        POList = po.ReadFile("src/data/PurchaseOrder.txt", purchaseOrder);
        PRList = pr.ReadFile("src/data/PurchaseRequisite.txt", purchaseRequisite);
        SupList = sup.ReadFile("src/data/Supplier.txt",supplier);
        ItemList = itm.ReadFile("src/data/Item.txt",items);
        IUPList = iup.ReadFile("src/data/ItemUnitPrice.txt",itemUnitPrice);
        
        
        userID = pm.getUserID();
        pm.DisplayProfile(ProfileUserType, ProfileID, ProfileFN, ProfileLN, ProfileGender, ProfileEmail, ProfileContact);
        this.pm.readLoginCredentials();
        
        this.pm = pm;
        fill_SupTable(SupList);
        fill_ItemTable(ItemList);
        fill_POTable(POList,PRList,userID,IUPList);
        fill_Add_PRTable(PRList);
        if(this.pm.getUserType().equals("A")){
            Admin = true;
        }
        
        
        InitPRPage();
        InitPRViewPage();
        btn_cancelEdit.setVisible(false);
        btn_confirmEdit.setVisible(false);
        btn_confirmApprove.setVisible(false);
        btn_cancelApprove.setVisible(false);
        
        lbl_userID.setText(userID);
        lblPM_Name.setText("Welcome, "+pm.getFirstName()+" "+pm.getLastName()+"!");
        
        setVisible(true);
        
        
        
        
        
        
//        try 
//        {
//            UIManager.setLookAndFeel(new FlatIntelliJLaf());
//            FlatLaf.setGlobalExtraDefaults( Collections.singletonMap( "@accentColor", "#AB886D" ));
//            FlatIntelliJLaf.setup();
//        
//        } 
//        catch (UnsupportedLookAndFeelException ex) 
//        {
//            System.out.println("Please install flatlaf for a better experience!");
//        }
    }

    private void fill_SupTable(ArrayList<Supplier> SupList){
        DefaultTableModel Sup_Model = (DefaultTableModel) Sup_table.getModel();
        Sup_Model.setRowCount(0);
        
        for(Supplier sup : SupList)
        {
            if(sup.getSupplierRating()>0)
            {
                Sup_Model.addRow(new Object[]
                {
                    sup.getSupplierID(),
                    sup.getSupplierName(),
                    sup.getAddress(),
                    sup.getContactNumber(),
                    sup.getSupplierRating(),
                    sup.getBankName(),
                    sup.getBankAccountNo()
                });
            }
        }
    }
    
    private void fill_ItemTable(ArrayList<Item> ItemList){
        DefaultTableModel Item_Model = (DefaultTableModel) Item_table.getModel();
        Item_Model.setRowCount(0);

        for (Item item : ItemList)
        {
            if(item.getQuantityInStock()>0)
            {
                Item_Model.addRow(new Object[]
                {
                    item.getItemID(),
                    item.getItemName(),
                    item.getDescription(),
                    item.getQuantityInStock(),
                    item.getReorderLevel(),
                    item.getRetailPrice()
                });
            }
        }
    }
    
    private void fill_POTable(ArrayList<PurchaseOrder> POList, ArrayList<PurchaseRequisite> PRList, String userID, ArrayList<ItemUnitPrice> IUPList) {
        DefaultTableModel PO_Model = (DefaultTableModel) PO_table.getModel();
        PO_Model.setRowCount(0);

        for (PurchaseOrder po : POList) 
        {
            PO_Model.addRow(new Object[]
            {
                po.getPO_ID(),
                po.getPR_ID(),
                po.getPM_ID(),
                po.getFM_ID(),
                po.getSupplier_ID(),
                po.getPO_Creation_TimeStamp(),
                po.getFM_Approval_TimeStamp(),
                po.getStatus(),
                po.getQuantity(),
                po.getTotal(),
                po.getPaymentStatus(),
                po.getDeliveryStatus(),
                po.getDelivered_TimeStamp()
            });
        }
        
        DefaultTableModel PO_Model2 = (DefaultTableModel) Edit_PO_table.getModel();
        PO_Model2.setRowCount(0);
        
        for (PurchaseOrder po : POList) 
        {
            if (userID.equals(po.getPM_ID()) && po.getStatus().equals("Pending")) 
            {
                for (PurchaseRequisite pr : PRList) 
                {
                    if (po.getPR_ID().equals(pr.getPR_ID())) 
                    {
                        for (ItemUnitPrice iup : IUPList) 
                        {
                            if (pr.getITM_ID().equals(iup.getItemID())&& po.getSupplier_ID().equals(iup.getSupplierID())) 
                            {
                                PO_Model2.addRow(new Object[]
                                {
                                    po.getPO_ID(),
                                    po.getPM_ID(),
                                    pr.getITM_ID(),
                                    po.getSupplier_ID(),
                                    po.getQuantity(),
                                    iup.getUnitCost(),
                                    po.getTotal()
                                });
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void fill_Add_PRTable(ArrayList<PurchaseRequisite> PRList){
        DefaultTableModel PR_Model = (DefaultTableModel) AddPO_PR_table.getModel();
        PR_Model.setRowCount(0);


        for (PurchaseRequisite pr : PRList) 
        {
            if (pr.getStatus().equals("Pending"))
            {
                PR_Model.addRow(new Object[]
                {
                    pr.getPR_ID(),
                    pr.getRequest_TimeStamp(),
                    pr.getITM_ID(),
                    pr.getQuantity(),
                });
            }
            
            
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

        jPanel_background = new javax.swing.JPanel();
        jPanel_upperBackground = new javax.swing.JPanel();
        jLabel_NSB_logo = new javax.swing.JLabel();
        lbl_userID = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel_PO = new javax.swing.JPanel();
        subTab = new javax.swing.JTabbedPane();
        subTab_Add = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        AddPO_PR_table = new javax.swing.JTable();
        jLabel_PR_ID = new javax.swing.JLabel();
        txtF_PR_ID = new javax.swing.JTextField();
        lbl_RTS = new javax.swing.JLabel();
        txtF_RTS = new javax.swing.JTextField();
        lbl_ITM_ID = new javax.swing.JLabel();
        txtF_Add_ITM_ID = new javax.swing.JTextField();
        lbl_Quantity = new javax.swing.JLabel();
        txtF_Quantity = new javax.swing.JTextField();
        lbl_Supplier_ID = new javax.swing.JLabel();
        cbb_Add_Supplier_ID = new javax.swing.JComboBox<>();
        lbl_total = new javax.swing.JLabel();
        txtF_Add_Total = new javax.swing.JTextField();
        lbl_UnitCost = new javax.swing.JLabel();
        txtF_Add_UnitCost = new javax.swing.JTextField();
        btn_Reject = new javax.swing.JButton();
        btn_Approve = new javax.swing.JButton();
        btn_confirmApprove = new javax.swing.JButton();
        btn_cancelApprove = new javax.swing.JButton();
        subTab_Edit = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Edit_PO_table = new javax.swing.JTable();
        btn_confirmEdit = new javax.swing.JButton();
        jLabel_PO_ID = new javax.swing.JLabel();
        txtF_PO_ID = new javax.swing.JTextField();
        jLabel_PM_ID = new javax.swing.JLabel();
        txtF_PM_ID = new javax.swing.JTextField();
        jLabel_ITM_ID = new javax.swing.JLabel();
        txtF_ITM_ID = new javax.swing.JTextField();
        jLabel_Supplier_ID = new javax.swing.JLabel();
        jLabel_Supplier_ID1 = new javax.swing.JLabel();
        cbb_Supplier_ID = new javax.swing.JComboBox<>();
        spn_Quantity = new javax.swing.JSpinner();
        btn_cancelEdit = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        jLabel_total = new javax.swing.JLabel();
        txtF_Total = new javax.swing.JTextField();
        btn_delete = new javax.swing.JButton();
        txtF_UnitCost = new javax.swing.JTextField();
        jLabel_UnitCost = new javax.swing.JLabel();
        subTab_View = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        PO_table = new javax.swing.JTable();
        jPanel_PR = new javax.swing.JPanel();
        pnlCreatePR = new javax.swing.JPanel();
        subTab2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtPRID = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtReqTimestamp = new javax.swing.JTextField();
        txtReqbyName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtReqbyID = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnSearch1 = new javax.swing.JButton();
        PRScrollPane = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblRequestDate = new javax.swing.JLabel();
        lblPRID = new javax.swing.JLabel();
        lblRequestName = new javax.swing.JLabel();
        lblRequestStaffID = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        PRItemTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        lblRequestQuantity = new javax.swing.JLabel();
        lblApprovedStaffID = new javax.swing.JLabel();
        lblApprovedName = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblApprovedDate = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cmbPRItemID = new javax.swing.JComboBox<>();
        spiQuantityPR = new javax.swing.JSpinner();
        lblQuantityNeeded = new javax.swing.JLabel();
        lblCurrentStock = new javax.swing.JLabel();
        lblReorderLevel = new javax.swing.JLabel();
        lblStockStatus = new javax.swing.JLabel();
        txtCurrentStock = new javax.swing.JTextField();
        txtReorderLevel = new javax.swing.JTextField();
        txtStockStatus = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        ScrollPaneViewPR = new javax.swing.JScrollPane();
        pnlViewPR = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblViewRequestDate = new javax.swing.JLabel();
        lblViewPRID = new javax.swing.JLabel();
        lblViewRequestName = new javax.swing.JLabel();
        lblViewRequestStaffID = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        PRViewItemTable = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel39 = new javax.swing.JLabel();
        lblViewRequestQuantity = new javax.swing.JLabel();
        lblViewApprovedStaffID = new javax.swing.JLabel();
        lblViewApprovedName = new javax.swing.JLabel();
        lblViewApprovedDate = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        cmbPRID = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel_viewItems = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Item_table = new javax.swing.JTable();
        jPanel_viewSuppliers = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Sup_table = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
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
        lblPM_Name = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel_background.setBackground(new java.awt.Color(228, 224, 225));
        jPanel_background.setToolTipText("");
        jPanel_background.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel_upperBackground.setBackground(new java.awt.Color(171, 136, 109));

        jLabel_NSB_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NexusLogoThemeSmall.png"))); // NOI18N

        lbl_userID.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        lbl_userID.setForeground(new java.awt.Color(255, 237, 226));
        lbl_userID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        javax.swing.GroupLayout jPanel_upperBackgroundLayout = new javax.swing.GroupLayout(jPanel_upperBackground);
        jPanel_upperBackground.setLayout(jPanel_upperBackgroundLayout);
        jPanel_upperBackgroundLayout.setHorizontalGroup(
            jPanel_upperBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_upperBackgroundLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel_NSB_logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_userID, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel_upperBackgroundLayout.setVerticalGroup(
            jPanel_upperBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_NSB_logo, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
            .addComponent(lbl_userID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setBackground(new java.awt.Color(228, 224, 225));
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(73, 54, 40));
        jLabel2.setText("Purchase Manager Dashboard");

        jTabbedPane1.setBackground(new java.awt.Color(228, 224, 225));
        jTabbedPane1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel_PO.setBackground(new java.awt.Color(228, 224, 225));
        jPanel_PO.setPreferredSize(new java.awt.Dimension(1280, 572));

        subTab.setBackground(new java.awt.Color(228, 224, 225));
        subTab.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        subTab.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        subTab.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N

        subTab_Add.setBackground(new java.awt.Color(228, 224, 225));

        AddPO_PR_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PR ID", "Request TimeStamp", "Item ID", "Quantity"
            }
        ));
        jScrollPane5.setViewportView(AddPO_PR_table);
        if (AddPO_PR_table.getColumnModel().getColumnCount() > 0) {
            AddPO_PR_table.getColumnModel().getColumn(0).setResizable(false);
            AddPO_PR_table.getColumnModel().getColumn(1).setResizable(false);
            AddPO_PR_table.getColumnModel().getColumn(1).setPreferredWidth(140);
            AddPO_PR_table.getColumnModel().getColumn(2).setResizable(false);
            AddPO_PR_table.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel_PR_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel_PR_ID.setText("PR ID");

        txtF_PR_ID.setEditable(false);
        txtF_PR_ID.setBackground(new java.awt.Color(224, 224, 224));
        txtF_PR_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_PR_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_PR_IDActionPerformed(evt);
            }
        });

        lbl_RTS.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lbl_RTS.setText("Request TimeStamp");

        txtF_RTS.setEditable(false);
        txtF_RTS.setBackground(new java.awt.Color(224, 224, 224));
        txtF_RTS.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_RTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_RTSActionPerformed(evt);
            }
        });

        lbl_ITM_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lbl_ITM_ID.setText("ITM ID");

        txtF_Add_ITM_ID.setEditable(false);
        txtF_Add_ITM_ID.setBackground(new java.awt.Color(224, 224, 224));
        txtF_Add_ITM_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_Add_ITM_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_Add_ITM_IDActionPerformed(evt);
            }
        });

        lbl_Quantity.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lbl_Quantity.setText("Quantity");

        txtF_Quantity.setEditable(false);
        txtF_Quantity.setBackground(new java.awt.Color(224, 224, 224));
        txtF_Quantity.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_Quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_QuantityActionPerformed(evt);
            }
        });

        lbl_Supplier_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lbl_Supplier_ID.setText("Supplier ID");

        cbb_Add_Supplier_ID.setEditable(true);
        cbb_Add_Supplier_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        cbb_Add_Supplier_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_Add_Supplier_IDActionPerformed(evt);
            }
        });

        lbl_total.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lbl_total.setText("Total");

        txtF_Add_Total.setEditable(false);
        txtF_Add_Total.setBackground(new java.awt.Color(224, 224, 224));
        txtF_Add_Total.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_Add_Total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_Add_TotalActionPerformed(evt);
            }
        });

        lbl_UnitCost.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lbl_UnitCost.setText("Unit Cost");

        txtF_Add_UnitCost.setEditable(false);
        txtF_Add_UnitCost.setBackground(new java.awt.Color(224, 224, 224));
        txtF_Add_UnitCost.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_Add_UnitCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_Add_UnitCostActionPerformed(evt);
            }
        });

        btn_Reject.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btn_Reject.setText("Reject");
        btn_Reject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RejectActionPerformed(evt);
            }
        });

        btn_Approve.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btn_Approve.setText("Approve");
        btn_Approve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ApproveActionPerformed(evt);
            }
        });

        btn_confirmApprove.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btn_confirmApprove.setText("Confirm");
        btn_confirmApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confirmApproveActionPerformed(evt);
            }
        });

        btn_cancelApprove.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btn_cancelApprove.setText("Cancel");
        btn_cancelApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelApproveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subTab_AddLayout = new javax.swing.GroupLayout(subTab_Add);
        subTab_Add.setLayout(subTab_AddLayout);
        subTab_AddLayout.setHorizontalGroup(
            subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subTab_AddLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_PR_ID)
                    .addComponent(txtF_PR_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_RTS)
                    .addComponent(txtF_RTS, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ITM_ID)
                    .addComponent(txtF_Add_ITM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Quantity)
                    .addComponent(txtF_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Supplier_ID)
                    .addComponent(cbb_Add_Supplier_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(subTab_AddLayout.createSequentialGroup()
                        .addGroup(subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_total)
                            .addComponent(txtF_Add_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(subTab_AddLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_Reject, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_confirmApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_cancelApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Approve, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_UnitCost)
                            .addComponent(txtF_Add_UnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        subTab_AddLayout.setVerticalGroup(
            subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subTab_AddLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(subTab_AddLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel_PR_ID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtF_PR_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_RTS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtF_RTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_ITM_ID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtF_Add_ITM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_Quantity)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtF_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_Supplier_ID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbb_Add_Supplier_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(subTab_AddLayout.createSequentialGroup()
                        .addComponent(lbl_total)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtF_Add_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(subTab_AddLayout.createSequentialGroup()
                        .addComponent(lbl_UnitCost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtF_Add_UnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58)
                .addGroup(subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Reject)
                    .addComponent(btn_Approve))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(subTab_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_confirmApprove)
                    .addComponent(btn_cancelApprove))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        subTab.addTab("ADD", subTab_Add);

        subTab_Edit.setBackground(new java.awt.Color(228, 224, 225));
        subTab_Edit.setPreferredSize(new java.awt.Dimension(1160, 522));

        Edit_PO_table.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        Edit_PO_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO ID", "PM ID", "ITM_ID", "Supplier ID", "Quantity", "Unit Cost", "Total"
            }
        ));
        jScrollPane4.setViewportView(Edit_PO_table);
        if (Edit_PO_table.getColumnModel().getColumnCount() > 0) {
            Edit_PO_table.getColumnModel().getColumn(0).setResizable(false);
            Edit_PO_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            Edit_PO_table.getColumnModel().getColumn(1).setResizable(false);
            Edit_PO_table.getColumnModel().getColumn(1).setPreferredWidth(70);
            Edit_PO_table.getColumnModel().getColumn(2).setResizable(false);
            Edit_PO_table.getColumnModel().getColumn(3).setResizable(false);
            Edit_PO_table.getColumnModel().getColumn(3).setPreferredWidth(80);
            Edit_PO_table.getColumnModel().getColumn(4).setResizable(false);
            Edit_PO_table.getColumnModel().getColumn(5).setResizable(false);
            Edit_PO_table.getColumnModel().getColumn(6).setResizable(false);
            Edit_PO_table.getColumnModel().getColumn(6).setPreferredWidth(60);
        }

        btn_confirmEdit.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btn_confirmEdit.setText("Confirm Edit");
        btn_confirmEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confirmEditActionPerformed(evt);
            }
        });

        jLabel_PO_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel_PO_ID.setText("PO ID");

        txtF_PO_ID.setEditable(false);
        txtF_PO_ID.setBackground(new java.awt.Color(224, 224, 224));
        txtF_PO_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_PO_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_PO_IDActionPerformed(evt);
            }
        });

        jLabel_PM_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel_PM_ID.setText("PM ID");

        txtF_PM_ID.setEditable(false);
        txtF_PM_ID.setBackground(new java.awt.Color(224, 224, 224));
        txtF_PM_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_PM_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_PM_IDActionPerformed(evt);
            }
        });

        jLabel_ITM_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel_ITM_ID.setText("ITM ID");

        txtF_ITM_ID.setEditable(false);
        txtF_ITM_ID.setBackground(new java.awt.Color(224, 224, 224));
        txtF_ITM_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_ITM_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_ITM_IDActionPerformed(evt);
            }
        });

        jLabel_Supplier_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel_Supplier_ID.setText("Supplier ID");

        jLabel_Supplier_ID1.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel_Supplier_ID1.setText("Quantity");

        cbb_Supplier_ID.setEditable(true);
        cbb_Supplier_ID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        cbb_Supplier_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_Supplier_IDActionPerformed(evt);
            }
        });

        spn_Quantity.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        spn_Quantity.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        btn_cancelEdit.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btn_cancelEdit.setText("Cancel Edit");
        btn_cancelEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelEditActionPerformed(evt);
            }
        });

        btn_edit.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        jLabel_total.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel_total.setText("Total");

        txtF_Total.setEditable(false);
        txtF_Total.setBackground(new java.awt.Color(224, 224, 224));
        txtF_Total.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_Total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_TotalActionPerformed(evt);
            }
        });

        btn_delete.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        txtF_UnitCost.setEditable(false);
        txtF_UnitCost.setBackground(new java.awt.Color(224, 224, 224));
        txtF_UnitCost.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        txtF_UnitCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtF_UnitCostActionPerformed(evt);
            }
        });

        jLabel_UnitCost.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel_UnitCost.setText("Unit Cost");

        javax.swing.GroupLayout subTab_EditLayout = new javax.swing.GroupLayout(subTab_Edit);
        subTab_Edit.setLayout(subTab_EditLayout);
        subTab_EditLayout.setHorizontalGroup(
            subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subTab_EditLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(subTab_EditLayout.createSequentialGroup()
                        .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_Supplier_ID)
                            .addComponent(cbb_Supplier_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spn_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(subTab_EditLayout.createSequentialGroup()
                                .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_total)
                                    .addComponent(txtF_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_UnitCost)
                                    .addComponent(txtF_UnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(subTab_EditLayout.createSequentialGroup()
                        .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_confirmEdit)
                            .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(subTab_EditLayout.createSequentialGroup()
                                    .addComponent(btn_delete)
                                    .addGap(18, 18, 18)
                                    .addComponent(btn_edit))
                                .addComponent(jLabel_PO_ID)
                                .addComponent(txtF_PO_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel_PM_ID)
                                .addComponent(txtF_PM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel_ITM_ID)
                                .addComponent(txtF_ITM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel_Supplier_ID1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(btn_cancelEdit)
                        .addGap(62, 62, 62)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        subTab_EditLayout.setVerticalGroup(
            subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subTab_EditLayout.createSequentialGroup()
                .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subTab_EditLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, subTab_EditLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel_PO_ID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtF_PO_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_PM_ID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtF_PM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_ITM_ID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtF_ITM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_Supplier_ID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbb_Supplier_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_Supplier_ID1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(subTab_EditLayout.createSequentialGroup()
                                .addComponent(spn_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel_total)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtF_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(subTab_EditLayout.createSequentialGroup()
                                .addComponent(jLabel_UnitCost)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtF_UnitCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_edit)
                            .addComponent(btn_delete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(subTab_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_confirmEdit)
                            .addComponent(btn_cancelEdit))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(38, 38, 38))
        );

        subTab.addTab("EDIT/DELETE", subTab_Edit);

        subTab_View.setBackground(new java.awt.Color(228, 224, 225));

        PO_table.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        PO_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO ID", "PR ID", "PM ID", "FM ID", "Supplier ID", "PO Creation TimeStamp", "FM Approval TimeStamp", "Status", "Quantity", "Total", "Paymnet Status", "Delivery Status", "Delivered TimeStamp"
            }
        ));
        jScrollPane3.setViewportView(PO_table);
        if (PO_table.getColumnModel().getColumnCount() > 0) {
            PO_table.getColumnModel().getColumn(0).setResizable(false);
            PO_table.getColumnModel().getColumn(0).setPreferredWidth(70);
            PO_table.getColumnModel().getColumn(1).setResizable(false);
            PO_table.getColumnModel().getColumn(1).setPreferredWidth(70);
            PO_table.getColumnModel().getColumn(2).setResizable(false);
            PO_table.getColumnModel().getColumn(2).setPreferredWidth(70);
            PO_table.getColumnModel().getColumn(3).setResizable(false);
            PO_table.getColumnModel().getColumn(3).setPreferredWidth(70);
            PO_table.getColumnModel().getColumn(4).setResizable(false);
            PO_table.getColumnModel().getColumn(4).setPreferredWidth(80);
            PO_table.getColumnModel().getColumn(5).setResizable(false);
            PO_table.getColumnModel().getColumn(5).setPreferredWidth(160);
            PO_table.getColumnModel().getColumn(6).setResizable(false);
            PO_table.getColumnModel().getColumn(6).setPreferredWidth(160);
            PO_table.getColumnModel().getColumn(7).setResizable(false);
            PO_table.getColumnModel().getColumn(8).setResizable(false);
            PO_table.getColumnModel().getColumn(8).setPreferredWidth(120);
            PO_table.getColumnModel().getColumn(9).setResizable(false);
            PO_table.getColumnModel().getColumn(9).setPreferredWidth(60);
            PO_table.getColumnModel().getColumn(10).setResizable(false);
            PO_table.getColumnModel().getColumn(10).setPreferredWidth(120);
            PO_table.getColumnModel().getColumn(11).setResizable(false);
            PO_table.getColumnModel().getColumn(11).setPreferredWidth(120);
            PO_table.getColumnModel().getColumn(12).setResizable(false);
            PO_table.getColumnModel().getColumn(12).setPreferredWidth(155);
        }

        javax.swing.GroupLayout subTab_ViewLayout = new javax.swing.GroupLayout(subTab_View);
        subTab_View.setLayout(subTab_ViewLayout);
        subTab_ViewLayout.setHorizontalGroup(
            subTab_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subTab_ViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
                .addContainerGap())
        );
        subTab_ViewLayout.setVerticalGroup(
            subTab_ViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subTab_ViewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        subTab.addTab("VIEW", subTab_View);

        javax.swing.GroupLayout jPanel_POLayout = new javax.swing.GroupLayout(jPanel_PO);
        jPanel_PO.setLayout(jPanel_POLayout);
        jPanel_POLayout.setHorizontalGroup(
            jPanel_POLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_POLayout.createSequentialGroup()
                .addComponent(subTab, javax.swing.GroupLayout.PREFERRED_SIZE, 1265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel_POLayout.setVerticalGroup(
            jPanel_POLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_POLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(subTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Purchase Order", jPanel_PO);

        jPanel_PR.setBackground(new java.awt.Color(228, 224, 225));

        pnlCreatePR.setBackground(new java.awt.Color(228, 224, 225));

        subTab2.setBackground(new java.awt.Color(228, 224, 225));
        subTab2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        subTab2.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        subTab2.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        subTab2.setPreferredSize(new java.awt.Dimension(1265, 526));
        subTab2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                subTab2StateChanged(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(228, 224, 225));

        txtPRID.setEditable(false);
        txtPRID.setForeground(java.awt.SystemColor.textInactiveText);
        txtPRID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPRIDActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(73, 54, 40));
        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel13.setText("New Purchase Requisite ID ");

        jLabel14.setBackground(new java.awt.Color(73, 54, 40));
        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel14.setText("Request Date");

        txtReqTimestamp.setEditable(false);
        txtReqTimestamp.setForeground(java.awt.SystemColor.textInactiveText);
        txtReqTimestamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReqTimestampActionPerformed(evt);
            }
        });

        txtReqbyName.setEditable(false);
        txtReqbyName.setForeground(java.awt.SystemColor.textInactiveText);
        txtReqbyName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReqbyNameActionPerformed(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(73, 54, 40));
        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel15.setText("Requested by");

        txtReqbyID.setEditable(false);
        txtReqbyID.setForeground(java.awt.SystemColor.textInactiveText);
        txtReqbyID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReqbyIDActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(73, 54, 40));
        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel16.setText("ID");

        jLabel21.setBackground(new java.awt.Color(73, 54, 40));
        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel21.setText("Item ID");

        btnSearch1.setText("Search");
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        PRScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setPreferredSize(new java.awt.Dimension(730, 480));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NexusLogoBlackSmall.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel12.setText("PURCHASE REQUISITION");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel22.setText("Purchase Requisite ID :");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel23.setText("Request Date :");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel24.setText("Requested by :");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel25.setText("Staff ID :");

        lblRequestDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRequestDate.setText("Request Date");

        lblPRID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPRID.setText("Purchase Requisite ID");

        lblRequestName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRequestName.setText("Requested Name");

        lblRequestStaffID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRequestStaffID.setText("Requested Staff ID");

        PRItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Current Stock", "Reorder Stock", "Quantity Needed"
            }
        ));
        PRItemTable.setFocusable(false);
        PRItemTable.setRequestFocusEnabled(false);
        PRItemTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(PRItemTable);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel26.setText("Quantity Requested :");

        lblRequestQuantity.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRequestQuantity.setText("Quantity");

        lblApprovedStaffID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblApprovedStaffID.setText("Pending");

        lblApprovedName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblApprovedName.setText("Pending");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel27.setText("Approved by :");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel28.setText("Staff ID :");

        lblApprovedDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblApprovedDate.setText("Pending");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel29.setText("Approval Date :");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(226, 226, 226))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(jLabel20))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblRequestDate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPRID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRequestName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRequestStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel29))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addComponent(lblApprovedName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblRequestQuantity)
                                            .addGap(2, 2, 2))
                                        .addComponent(lblApprovedStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblApprovedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(lblPRID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRequestDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lblRequestName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lblRequestStaffID))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(lblApprovedName)
                    .addComponent(jLabel26)
                    .addComponent(lblRequestQuantity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(lblApprovedStaffID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApprovedDate)
                    .addComponent(jLabel29))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        PRScrollPane.setViewportView(jPanel7);

        cmbPRItemID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        cmbPRItemID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select...", " " }));
        cmbPRItemID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPRItemIDActionPerformed(evt);
            }
        });

        spiQuantityPR.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spiQuantityPRStateChanged(evt);
            }
        });

        lblQuantityNeeded.setBackground(new java.awt.Color(73, 54, 40));
        lblQuantityNeeded.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblQuantityNeeded.setText("Quantity Needed");

        lblCurrentStock.setBackground(new java.awt.Color(73, 54, 40));
        lblCurrentStock.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblCurrentStock.setText("Current Stock");

        lblReorderLevel.setBackground(new java.awt.Color(73, 54, 40));
        lblReorderLevel.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblReorderLevel.setText("Reorder Level");
        lblReorderLevel.setPreferredSize(new java.awt.Dimension(80, 16));

        lblStockStatus.setBackground(new java.awt.Color(73, 54, 40));
        lblStockStatus.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lblStockStatus.setText("Stock Status");

        txtCurrentStock.setEditable(false);
        txtCurrentStock.setForeground(java.awt.SystemColor.textInactiveText);
        txtCurrentStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentStockActionPerformed(evt);
            }
        });

        txtReorderLevel.setEditable(false);
        txtReorderLevel.setForeground(java.awt.SystemColor.textInactiveText);
        txtReorderLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReorderLevelActionPerformed(evt);
            }
        });

        txtStockStatus.setEditable(false);
        txtStockStatus.setForeground(java.awt.SystemColor.textInactiveText);
        txtStockStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockStatusActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(cmbPRItemID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtReqTimestamp))
                            .addGap(21, 21, 21)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addComponent(txtReqbyName, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtReqbyID)))
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPRID)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblQuantityNeeded, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spiQuantityPR, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCurrentStock)
                                .addComponent(lblCurrentStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblReorderLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtReorderLevel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtStockStatus)
                                .addComponent(lblStockStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addComponent(PRScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PRScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, 0)
                        .addComponent(txtPRID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, 0)
                                .addComponent(txtReqTimestamp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, 0)
                                .addComponent(txtReqbyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(0, 0, 0)
                                .addComponent(txtReqbyID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel21)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPRItemID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCurrentStock)
                            .addComponent(lblReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStockStatus))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCurrentStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStockStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblQuantityNeeded)
                        .addGap(0, 0, 0)
                        .addComponent(spiQuantityPR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(170, 170, 170))))
        );

        subTab2.addTab("ADD", jPanel1);

        jPanel2.setBackground(new java.awt.Color(228, 224, 225));

        ScrollPaneViewPR.setBackground(new java.awt.Color(255, 255, 255));

        pnlViewPR.setBackground(new java.awt.Color(255, 255, 255));
        pnlViewPR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlViewPR.setPreferredSize(new java.awt.Dimension(730, 480));

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel34.setText("PURCHASE REQUISITION");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel35.setText("Purchase Requisite ID :");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel36.setText("Request Date :");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel37.setText("Requested by :");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel38.setText("Staff ID :");

        lblViewRequestDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewRequestDate.setText("Request Date");

        lblViewPRID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewPRID.setText("Purchase Requisite ID");

        lblViewRequestName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewRequestName.setText("Requested Name");

        lblViewRequestStaffID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewRequestStaffID.setText("Requested Staff ID");

        PRViewItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Current Stock", "Reorder Level", "Quantity Needed"
            }
        ));
        PRViewItemTable.setFocusable(false);
        PRViewItemTable.setRequestFocusEnabled(false);
        PRViewItemTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(PRViewItemTable);

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel39.setText("Quantity Requested :");

        lblViewRequestQuantity.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblViewRequestQuantity.setText("Quantity");

        lblViewApprovedStaffID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewApprovedStaffID.setText("Pending");

        lblViewApprovedName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewApprovedName.setText("Pending");

        lblViewApprovedDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblViewApprovedDate.setText("Pending");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel40.setText("Approved by :");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel41.setText("Staff ID :");

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel42.setText("Approval Date :");

        javax.swing.GroupLayout pnlViewPRLayout = new javax.swing.GroupLayout(pnlViewPR);
        pnlViewPR.setLayout(pnlViewPRLayout);
        pnlViewPRLayout.setHorizontalGroup(
            pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlViewPRLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addGap(226, 226, 226))
            .addGroup(pnlViewPRLayout.createSequentialGroup()
                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlViewPRLayout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(jLabel33))
                    .addGroup(pnlViewPRLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlViewPRLayout.createSequentialGroup()
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblViewRequestDate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlViewPRLayout.createSequentialGroup()
                                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblViewPRID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblViewRequestName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblViewRequestStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlViewPRLayout.createSequentialGroup()
                                    .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel42))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlViewPRLayout.createSequentialGroup()
                                            .addComponent(lblViewApprovedName, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblViewRequestQuantity)
                                            .addGap(2, 2, 2))
                                        .addComponent(lblViewApprovedStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblViewApprovedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        pnlViewPRLayout.setVerticalGroup(
            pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlViewPRLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(lblViewPRID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblViewRequestDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(lblViewRequestName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(lblViewRequestStaffID))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(lblViewApprovedName)
                    .addComponent(jLabel39)
                    .addComponent(lblViewRequestQuantity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(lblViewApprovedStaffID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlViewPRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblViewApprovedDate)
                    .addComponent(jLabel42))
                .addContainerGap(128, Short.MAX_VALUE))
        );

        ScrollPaneViewPR.setViewportView(pnlViewPR);

        jLabel43.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel43.setText("Purchase Requisite ID");

        cmbPRID.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        cmbPRID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbPRID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPRIDActionPerformed(evt);
            }
        });
        cmbPRID.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbPRIDPropertyChange(evt);
            }
        });

        jButton1.setText("View");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbPRID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                .addComponent(ScrollPaneViewPR, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbPRID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))
                    .addComponent(ScrollPaneViewPR, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        subTab2.addTab("VIEW", jPanel2);

        javax.swing.GroupLayout pnlCreatePRLayout = new javax.swing.GroupLayout(pnlCreatePR);
        pnlCreatePR.setLayout(pnlCreatePRLayout);
        pnlCreatePRLayout.setHorizontalGroup(
            pnlCreatePRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
            .addGroup(pnlCreatePRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlCreatePRLayout.createSequentialGroup()
                    .addComponent(subTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 1272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );
        pnlCreatePRLayout.setVerticalGroup(
            pnlCreatePRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 526, Short.MAX_VALUE)
            .addGroup(pnlCreatePRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlCreatePRLayout.createSequentialGroup()
                    .addComponent(subTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel_PRLayout = new javax.swing.GroupLayout(jPanel_PR);
        jPanel_PR.setLayout(jPanel_PRLayout);
        jPanel_PRLayout.setHorizontalGroup(
            jPanel_PRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
            .addGroup(jPanel_PRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_PRLayout.createSequentialGroup()
                    .addComponent(pnlCreatePR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel_PRLayout.setVerticalGroup(
            jPanel_PRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
            .addGroup(jPanel_PRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_PRLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlCreatePR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Purchase Requisition", jPanel_PR);

        jPanel_viewItems.setBackground(new java.awt.Color(228, 224, 225));

        Item_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITM ID", "Item Name", "Description", "Quantity in Stock", "Reorder Level", "Retail Price"
            }
        ));
        jScrollPane2.setViewportView(Item_table);

        javax.swing.GroupLayout jPanel_viewItemsLayout = new javax.swing.GroupLayout(jPanel_viewItems);
        jPanel_viewItems.setLayout(jPanel_viewItemsLayout);
        jPanel_viewItemsLayout.setHorizontalGroup(
            jPanel_viewItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_viewItemsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_viewItemsLayout.setVerticalGroup(
            jPanel_viewItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_viewItemsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("View Items", jPanel_viewItems);

        jPanel_viewSuppliers.setBackground(new java.awt.Color(228, 224, 225));

        Sup_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Supplier Name", "Address", "Contact", "Rating", "Bank Name", "Bank Account"
            }
        ));
        jScrollPane1.setViewportView(Sup_table);
        if (Sup_table.getColumnModel().getColumnCount() > 0) {
            Sup_table.getColumnModel().getColumn(0).setResizable(false);
            Sup_table.getColumnModel().getColumn(1).setResizable(false);
            Sup_table.getColumnModel().getColumn(2).setResizable(false);
            Sup_table.getColumnModel().getColumn(3).setResizable(false);
            Sup_table.getColumnModel().getColumn(4).setResizable(false);
            Sup_table.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel_viewSuppliersLayout = new javax.swing.GroupLayout(jPanel_viewSuppliers);
        jPanel_viewSuppliers.setLayout(jPanel_viewSuppliersLayout);
        jPanel_viewSuppliersLayout.setHorizontalGroup(
            jPanel_viewSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_viewSuppliersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_viewSuppliersLayout.setVerticalGroup(
            jPanel_viewSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_viewSuppliersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("View Suppliers", jPanel_viewSuppliers);

        jPanel12.setBackground(new java.awt.Color(228, 224, 225));
        jPanel12.setLayout(null);

        jPanel13.setBackground(new java.awt.Color(214, 192, 179));
        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ProfileID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(92, 92, 92))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(105, 105, 105)))))
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(ProfileUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56)
                            .addComponent(jLabel57)
                            .addComponent(jLabel58)
                            .addComponent(jLabel60)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ProfileLN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileFN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileContact, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161)))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ShowHidePass, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(ProfileNewPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ProfileCurrentPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ProfileChgPass, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                    .addComponent(ChgPassError, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel56)
                                    .addComponent(ProfileFN, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel57)
                                    .addComponent(ProfileLN, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ProfileGender, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel58))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel60)
                                    .addComponent(ProfileEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ProfileContact, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel59))
                                .addGap(34, 34, 34)
                                .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(108, 108, 108)
                                        .addComponent(ChgPassError, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ProfileChgPass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(20, 20, 20)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(ProfileUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProfileID, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel63)
                        .addGap(18, 18, 18)
                        .addComponent(ProfileCurrentPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(ShowHidePass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProfileNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.add(jPanel13);
        jPanel13.setBounds(20, 28, 1230, 420);

        jTabbedPane1.addTab("Profile", jPanel12);

        lblPM_Name.setBackground(new java.awt.Color(228, 224, 225));
        lblPM_Name.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        lblPM_Name.setForeground(new java.awt.Color(73, 54, 40));
        lblPM_Name.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblPM_Name.setText("Welcome!PM_Name");

        javax.swing.GroupLayout jPanel_backgroundLayout = new javax.swing.GroupLayout(jPanel_background);
        jPanel_background.setLayout(jPanel_backgroundLayout);
        jPanel_backgroundLayout.setHorizontalGroup(
            jPanel_backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_upperBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPM_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel_backgroundLayout.setVerticalGroup(
            jPanel_backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                .addComponent(jPanel_upperBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPM_Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_background, 726, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ShowHidePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowHidePassActionPerformed
        if(ShowHidePass.isSelected()){
            ProfileNewPass.setEchoChar((char)0);
        }else{
            ProfileNewPass.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_ShowHidePassActionPerformed

    private void ProfileChgPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfileChgPassActionPerformed
        pm.readLoginCredentials();

        pm.ResetPassword(ProfileCurrentPass.getText(),ProfileNewPass.getText(),ChgPassError, ProfileCurrentPass,ProfileNewPass,ShowHidePass);
    }//GEN-LAST:event_ProfileChgPassActionPerformed

    private void txtF_UnitCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_UnitCostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_UnitCostActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        selectedRow = Edit_PO_table.getSelectedRow();

        if(selectedRow != -1)
        {
            String POID = Edit_PO_table.getValueAt(selectedRow, 0).toString();
            int confirmMsg = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?","Delete Purchase Order",JOptionPane.YES_NO_OPTION);

            if (confirmMsg == JOptionPane.YES_OPTION)
            {
                DefaultTableModel model = (DefaultTableModel) Edit_PO_table.getModel();
                model.removeRow(selectedRow);

                for (int i = POList.size() - 1; i >= 0; i--)
                {
                    if (POList.get(i).getPO_ID().equals(POID))
                    {
                        POList.remove(i);
                        break;
                    }
                }
                po.WriteFile("src/data/PurchaseOrder.txt", POList);
                fill_POTable(POList, PRList, userID, IUPList);

                //purchaseOrder.WritePO(POList);
                JOptionPane.showMessageDialog(this,"Purchase Order Deleted Successfully!","Purchase Order Deleted",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please Select a row to Delete!", "Purchase Order Not Selected!",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void txtF_TotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_TotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_TotalActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        selectedRow = Edit_PO_table.getSelectedRow();

        if(selectedRow != -1)
        {
            //comboBox_Edit_Sup_ID(cbb_Supplier_ID, IUPList);
            comboBox_Edit_Sup_ID(cbb_Supplier_ID, IUPList, selectedRow);
            SupID_calculate_total(cbb_Supplier_ID, IUPList);

            txtF_PO_ID.setText(Edit_PO_table.getValueAt(selectedRow, 0).toString());
            txtF_PM_ID.setText(Edit_PO_table.getValueAt(selectedRow, 1).toString());
            txtF_ITM_ID.setText(Edit_PO_table.getValueAt(selectedRow, 2).toString());
            spn_Quantity.setValue(Edit_PO_table.getValueAt(selectedRow, 4));
            txtF_UnitCost.setText(Edit_PO_table.getValueAt(selectedRow, 5).toString());
            txtF_Total.setText(Edit_PO_table.getValueAt(selectedRow, 6).toString());
            btn_edit.setVisible(false);
            btn_cancelEdit.setVisible(true);
            btn_delete.setVisible(false);
            btn_confirmEdit.setVisible(true);

            Quantity_calculate_total();
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Please Select a row to Edit!","Row Not Selected!",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_cancelEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelEditActionPerformed
        spn_Quantity.setValue(0);
        cbb_Supplier_ID.removeAllItems();
        btn_cancelEdit.setVisible(false);
        btn_confirmEdit.setVisible(false);
        btn_delete.setVisible(true);
        btn_edit.setVisible(true);
        txtF_PO_ID.setText("");
        txtF_PM_ID.setText("");
        txtF_ITM_ID.setText("");
        txtF_Total.setText("");
        txtF_UnitCost.setText("");
    }//GEN-LAST:event_btn_cancelEditActionPerformed

    private void cbb_Supplier_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_Supplier_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbb_Supplier_IDActionPerformed

    private void txtF_ITM_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_ITM_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_ITM_IDActionPerformed

    private void txtF_PM_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_PM_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_PM_IDActionPerformed

    private void txtF_PO_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_PO_IDActionPerformed

    }//GEN-LAST:event_txtF_PO_IDActionPerformed

    private void btn_confirmEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confirmEditActionPerformed

        int editQuantity = (Integer) spn_Quantity.getValue();
        if (editQuantity == 0)
        {
            JOptionPane.showMessageDialog(this,"Quantity cannot be 0!","Quantity 0 Cannot edit!",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            for (PurchaseOrder po : POList)
            {
                if (po.getPO_ID().equals(txtF_PO_ID.getText()))
                {
                    po.setSupplier_ID(cbb_Supplier_ID.getSelectedItem().toString());
                    po.setQuantity(editQuantity);
                    po.setTotal(Double.parseDouble(txtF_Total.getText()));
                    break;
                }
            }
            po.WriteFile("src/data/PurchaseOrder.txt", POList);
            JOptionPane.showMessageDialog(this,"Purchase Order Edited Successfully!","Purchase Order Edited",JOptionPane.INFORMATION_MESSAGE);
            fill_POTable(POList, PRList, userID, IUPList);

            btn_confirmEdit.setVisible(false);
            btn_cancelEdit.setVisible(false);
            btn_delete.setVisible(true);
            btn_edit.setVisible(true);
            spn_Quantity.setValue(0);
            cbb_Supplier_ID.removeAllItems();
            txtF_PO_ID.setText("");
            txtF_PM_ID.setText("");
            txtF_ITM_ID.setText("");
            txtF_Total.setText("");
            txtF_UnitCost.setText("");
        }

    }//GEN-LAST:event_btn_confirmEditActionPerformed

    private void btn_cancelApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelApproveActionPerformed
        txtF_Quantity.setText("");
        txtF_PR_ID.setText("");
        txtF_RTS.setText("");
        txtF_Add_ITM_ID.setText("");
        txtF_Add_Total.setText("");
        txtF_Add_UnitCost.setText("");
        cbb_Add_Supplier_ID.removeAllItems();
        btn_confirmApprove.setVisible(false);
        btn_cancelApprove.setVisible(false);
        btn_Reject.setVisible(true);
        btn_Approve.setVisible(true);
    }//GEN-LAST:event_btn_cancelApproveActionPerformed

    private void btn_confirmApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confirmApproveActionPerformed
        // TODO add your handling code here:
    if (cbb_Add_Supplier_ID.getSelectedItem() == null || cbb_Add_Supplier_ID.getSelectedItem().toString().equals("Select...")) {
        JOptionPane.showMessageDialog(this, "Please select a Supplier!", "No Supplier Selected!", JOptionPane.ERROR_MESSAGE);
        return;
       }
        for (PurchaseRequisite pr : PRList)
        {
            
            if (pr.getPR_ID().equals(txtF_PR_ID.getText()))
            {
                pr.setStatus("Approved");
                pr.setApp_Rej_TimeStamp(timeStamp.format(timestamp));
                pr.setPM_ID(userID);
                break;
            }
        }

        String newPOID  = generatePO_ID(POList);

        POList.add(new PurchaseOrder(
            newPOID,
            txtF_PR_ID.getText(),
            userID,
            "Pending",
            cbb_Add_Supplier_ID.getSelectedItem().toString(),
            timeStamp.format(timestamp),
            "Pending",
            "Pending",
            Integer.parseInt(txtF_Quantity.getText()),
            Double.parseDouble(txtF_Add_Total.getText()),
            "Pending",
            "Pending",
            "Pending"));

    po.WriteFile("src/data/PurchaseOrder.txt", POList);
    pr.WriteFile("src/data/PurchaseRequisite.txt", PRList);
    //purchaseRequisite.WritePR(PRList);
    //purchaseOrder.WritePO(POList);
    JOptionPane.showMessageDialog(this,"Purchase Requisite Approved and Purchase Order Created Successfully!","Purchase Requisite Approved and Purchase Order Created",JOptionPane.INFORMATION_MESSAGE);
    fill_Add_PRTable(PRList);
    fill_POTable(POList, PRList, userID, IUPList);

    txtF_Quantity.setText("");
    cbb_Add_Supplier_ID.removeAllItems();
    btn_confirmApprove.setVisible(false);
    btn_cancelApprove.setVisible(false);
    btn_Reject.setVisible(true);
    btn_Approve.setVisible(true);
    txtF_PR_ID.setText("");
    txtF_RTS.setText("");
    txtF_Add_ITM_ID.setText("");
    txtF_Add_Total.setText("");
    txtF_Add_UnitCost.setText("");
    }//GEN-LAST:event_btn_confirmApproveActionPerformed

    private void btn_ApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ApproveActionPerformed
        selectedRow = AddPO_PR_table.getSelectedRow();

        if(selectedRow != -1)
        {
            //comboBox_Add_Sup_ID(cbb_Add_Supplier_ID, IUPList);
            comboBox_Add_Sup_ID(cbb_Add_Supplier_ID, IUPList, selectedRow);
            Add_calculate_total(cbb_Add_Supplier_ID, IUPList);

            txtF_PR_ID.setText(AddPO_PR_table.getValueAt(selectedRow, 0).toString());
            txtF_RTS.setText(AddPO_PR_table.getValueAt(selectedRow, 1).toString());
            txtF_Add_ITM_ID.setText(AddPO_PR_table.getValueAt(selectedRow, 2).toString());
            txtF_Quantity.setText(AddPO_PR_table.getValueAt(selectedRow, 3).toString());
            btn_Reject.setVisible(false);
            btn_confirmApprove.setVisible(true);
            btn_Approve.setVisible(false);
            btn_cancelApprove.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Please Select a Row!","Row Not Selected!",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_ApproveActionPerformed

    private void btn_RejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RejectActionPerformed
        // TODO add your handling code here:
        selectedRow = AddPO_PR_table.getSelectedRow();

        if(selectedRow != -1)
        {
            String PRID = AddPO_PR_table.getValueAt(selectedRow, 0).toString();
            int confirmMsg = JOptionPane.showConfirmDialog(null, "Are you sure you want to reject this Purchase Requisite?","Reject Purchase Requisite",JOptionPane.YES_NO_OPTION);

            if (confirmMsg == JOptionPane.YES_OPTION)
            {
                DefaultTableModel model = (DefaultTableModel) AddPO_PR_table.getModel();
                model.removeRow(selectedRow);

                for (PurchaseRequisite pr : PRList)
                {
                    if (pr.getPR_ID().equals(PRID))
                    {
                        pr.setStatus("Rejected");
                        pr.setApp_Rej_TimeStamp(timeStamp.format(timestamp));
                        pr.setPM_ID(userID);
                        break;
                    }
                }

//                purchaseRequisite.WritePR(PRList);
                pr.WriteFile("src/data/PurchaseRequisite.txt", PRList);
                fill_Add_PRTable(PRList);
                JOptionPane.showMessageDialog(this,"Purcahse Requisite Rejected Successfully!","Purchase Requisite Rejected",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please Select a row to Reject!", "Purchase Requisite Not Selected!",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_RejectActionPerformed

    private void txtF_Add_UnitCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_Add_UnitCostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_Add_UnitCostActionPerformed

    private void txtF_Add_TotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_Add_TotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_Add_TotalActionPerformed

    private void cbb_Add_Supplier_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_Add_Supplier_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbb_Add_Supplier_IDActionPerformed

    private void txtF_QuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_QuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_QuantityActionPerformed

    private void txtF_Add_ITM_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_Add_ITM_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_Add_ITM_IDActionPerformed

    private void txtF_RTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_RTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_RTSActionPerformed

    private void txtF_PR_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtF_PR_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtF_PR_IDActionPerformed

    private void txtPRIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPRIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPRIDActionPerformed

    private void txtReqTimestampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReqTimestampActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReqTimestampActionPerformed

    private void txtReqbyNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReqbyNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReqbyNameActionPerformed

    private void txtReqbyIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReqbyIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReqbyIDActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
        // TODO add your handling code here:
        String ItemID = cmbPRItemID.getSelectedItem().toString();
        if (pm.CheckItemValid(ItemList,ItemID) == true){
            PRVisibility(true);

            int ItemIndex = pm.ReturnItemIndex(ItemList, ItemID);
            txtCurrentStock.setText(String.valueOf(ItemList.get(ItemIndex).getQuantityInStock()));
            txtReorderLevel.setText(String.valueOf(ItemList.get(ItemIndex).getReorderLevel()));
            if (Integer.parseInt(txtCurrentStock.getText()) < Integer.parseInt(txtReorderLevel.getText())){
                txtStockStatus.setText("Low");
            } else if (Integer.parseInt(txtCurrentStock.getText()) < Integer.parseInt(txtReorderLevel.getText())+10){
                txtStockStatus.setText("Below Normal");
            } else
            {
                txtStockStatus.setText("Normal");
            }

            AddPRTable(ItemList, pm.ReturnItemIndex(ItemList, ItemID), Integer.parseInt(spiQuantityPR.getValue().toString()));
        } else{
            JOptionPane.showMessageDialog(this,"Please select an item!","No Item Selected!",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void cmbPRItemIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPRItemIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPRItemIDActionPerformed

    private void spiQuantityPRStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spiQuantityPRStateChanged
        // TODO add your handling code here:
        if ( (Integer) spiQuantityPR.getValue() < 0){
            spiQuantityPR.setValue(0);
            JOptionPane.showMessageDialog(this,"No negative value accepted!","Negative value found!",JOptionPane.ERROR_MESSAGE);
        }
        String ItemID = cmbPRItemID.getSelectedItem().toString();
        AddPRTable(ItemList, pm.ReturnItemIndex(ItemList, ItemID), Integer.parseInt(spiQuantityPR.getValue().toString()));
        lblRequestQuantity.setText(spiQuantityPR.getValue().toString());
    }//GEN-LAST:event_spiQuantityPRStateChanged

    private void txtCurrentStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurrentStockActionPerformed

    private void txtReorderLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReorderLevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReorderLevelActionPerformed

    private void txtStockStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockStatusActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        int confirmed = JOptionPane.showConfirmDialog(null, "Confirm Submission?","Submission",JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION){
            if ((Integer) spiQuantityPR.getValue() != 0){
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                PRList.add(new PurchaseRequisite(txtPRID.getText(),tmstmp.format(timestamp),
                txtReqbyID.getText(),cmbPRItemID.getSelectedItem().toString(),
                (Integer) spiQuantityPR.getValue(),
                "Pending","Pending","Pending"));
                pr.WriteFile("src/data/PurchaseRequisite.txt", PRList);
                JOptionPane.showMessageDialog(null, "Submission Completed!","Submitted",JOptionPane.INFORMATION_MESSAGE);
                InitPRPage();
                fill_Add_PRTable(PRList);
        }
    }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void cmbPRIDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbPRIDPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPRIDPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel) PRViewItemTable.getModel();
        try{dtm.removeRow(0);}catch(ArrayIndexOutOfBoundsException e){}
        ScrollPaneViewPR.getVerticalScrollBar().setValue(0);
        String PRID = cmbPRID.getSelectedItem().toString();
        for (PurchaseRequisite PR: PRList){
            if (PRID.equals(PR.getPR_ID())){
                pnlViewPR.setVisible(true);
                lblViewRequestDate.setText(PR.getRequest_TimeStamp());
                lblViewPRID.setText(PR.getPR_ID());
                
                pm.readLoginCredentials();
                for (User user: pm.getAllUsers()){
                    
                    if (user.getUserID().equals(PR.getSM_ID())){
                                                System.out.println(user.getUserID());
                        //                        System.out.println(PR.getSM_ID());
                        lblViewRequestName.setText(user.getFirstName()+" "+user.getLastName());
                        break;
                    }
                }

                lblViewRequestStaffID.setText(PR.getSM_ID());
                lblViewRequestQuantity.setText(String.valueOf(PR.getQuantity()));

                lblViewApprovedStaffID.setText(PR.getPM_ID());
                for (User user: pm.getAllUsers()){
                    if (user.getUserID().equals(PR.getPM_ID())){
                        lblViewApprovedName.setText(user.getFirstName()+" "+user.getLastName());
                        break;
                    }
                }

                lblViewApprovedDate.setText(PR.getApp_Rej_TimeStamp());

                Object[] rowData = new Object[5];
                for (Item it:ItemList){
                    if (PR.getITM_ID().equals(it.getItemID())){
                        rowData[0] = it.getItemID();
                        rowData[1] = it.getItemName();
                        rowData[2] = it.getQuantityInStock();
                        rowData[3] = it.getReorderLevel();
                        rowData[4] = lblViewRequestQuantity.getText();
                        break;
                    }
                }
                dtm.addRow(rowData);
                break;
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void subTab2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_subTab2StateChanged
        // TODO add your handling code here:
        InitPRPage();
        InitPRViewPage();

    }//GEN-LAST:event_subTab2StateChanged

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        InitPRPage();
        InitPRViewPage();
        fill_Add_PRTable(PRList);
        fill_POTable(POList, PRList, userID, IUPList);
        fill_SupTable(SupList);
        fill_ItemTable(ItemList);

    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void cmbPRIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPRIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPRIDActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here
        this.dispose();
        if(Admin == true){
            new Admin_Program(new Admin(pm));
        }else{
            new LoginUI();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void populateComboBox(JComboBox<String> supComboBox, ArrayList<ItemUnitPrice> iupList, JTable table, int selectedRow, boolean includeCurrentSupplier) {
        supComboBox.removeAllItems();

        String itemID = table.getValueAt(selectedRow, 2).toString();
        String currentSupplierID = includeCurrentSupplier ? table.getValueAt(selectedRow, 3).toString() : null; 

        if (includeCurrentSupplier && currentSupplierID != null) {
            supComboBox.addItem(currentSupplierID); 
        }

        supComboBox.addItem("Select...");
        for (ItemUnitPrice iup : iupList) {
            if (iup.getItemID().equals(itemID) && (currentSupplierID == null || !iup.getSupplierID().equals(currentSupplierID))) {
                supComboBox.addItem(iup.getSupplierID());
            }
        }
    }

    private void comboBox_Edit_Sup_ID(JComboBox<String> supComboBox, ArrayList<ItemUnitPrice> iupList, int selectedRow) {
        populateComboBox(supComboBox, iupList, Edit_PO_table, selectedRow, true);
    }

    private void comboBox_Add_Sup_ID(JComboBox<String> supComboBox, ArrayList<ItemUnitPrice> iupList, int selectedRow) {
        populateComboBox(supComboBox, iupList, AddPO_PR_table, selectedRow, false);
    }

    
    
//    private void comboBox_Edit_Sup_ID(JComboBox<String> Sup_cbb, ArrayList<ItemUnitPrice> IUPList)
//    {
//        
//        Sup_cbb.addItem(Edit_PO_table.getValueAt(selectedRow, 3).toString());
//        
//        for (ItemUnitPrice iup : IUPList)
//        {
//            if(Edit_PO_table.getValueAt(selectedRow, 2).toString().equals(iup.getItemID()) && !iup.getSupplierID().equals(Edit_PO_table.getValueAt(selectedRow, 3).toString()))
//            {
//                Sup_cbb.addItem(iup.getSupplierID());
//            }
//        }
//    }
//    
//    private void comboBox_Add_Sup_ID(JComboBox<String> Sup_cbb, ArrayList<ItemUnitPrice> IUPList)
//    {        
//        for (ItemUnitPrice iup : IUPList)
//        {
//            if(AddPO_PR_table.getValueAt(selectedRow, 2).toString().equals(iup.getItemID()))
//            {
//                Sup_cbb.addItem(iup.getSupplierID());
//            }
//        }
//    }
    
    private void SupID_calculate_total(JComboBox<String> Sup_cbb, ArrayList<ItemUnitPrice> IUPList)
    {
        Sup_cbb.addActionListener(evt -> 
        {
            String selectedSupplierID = (String) Sup_cbb.getSelectedItem();
            double unitCost = 0.0;
            
            for (ItemUnitPrice iup : IUPList) 
            {
                if (iup.getItemID().equals(Edit_PO_table.getValueAt(selectedRow, 2).toString()) && iup.getSupplierID().equals(selectedSupplierID)) 
                {
                    unitCost = iup.getUnitCost();
                }
            } 
        txtF_UnitCost.setText(String.valueOf(unitCost));
        int quantity = (int) spn_Quantity.getValue();
        double total = unitCost * quantity;
        txtF_Total.setText(String.format("%.2f", total));
        });
    }
    
    private void Add_calculate_total(JComboBox<String> Sup_cbb, ArrayList<ItemUnitPrice> IUPList)
    {
        Sup_cbb.addActionListener(evt -> 
        {
            String selectedSupplierID = (String) Sup_cbb.getSelectedItem();
            double unitCost = 0.0;
            
            for (ItemUnitPrice iup : IUPList) 
            {
                if (iup.getItemID().equals(AddPO_PR_table.getValueAt(selectedRow, 2).toString()) && iup.getSupplierID().equals(selectedSupplierID)) 
                {
                    unitCost = iup.getUnitCost();
                }
            } 
            
            txtF_Add_UnitCost.setText(String.valueOf(unitCost));
            String quantityText = txtF_Quantity.getText();
       
        
            try {
                int quantity = Integer.parseInt(quantityText); 
                double total = unitCost * quantity;
                txtF_Add_Total.setText(String.format("%.2f", total));
            } 
            catch (NumberFormatException e) {
                txtF_Add_Total.setText(""); 
            }

                //txtF_Add_UnitCost.setText(String.valueOf(unitCost));
                //int quantity = Integer.parseInt(txtF_Quantity.getText());
                //double total = unitCost * quantity;
                //txtF_Add_Total.setText(String.format("%.2f", total));
        });
    }
    
    private void Quantity_calculate_total()
    {
        spn_Quantity.addChangeListener(evt -> 
        {
            int quantity = (int) spn_Quantity.getValue();
            double unitCost = Double.parseDouble(txtF_UnitCost.getText());
            double total = unitCost * quantity;
            txtF_Total.setText(String.format("%.2f", total));
        });
    }
    
    public String generatePO_ID(ArrayList<PurchaseOrder> POList) 
    {
        int highestNo = 0;

        for (PurchaseOrder po : POList) 
        {
            String poID = po.getPO_ID();
            int PO_No = Integer.parseInt(poID.substring(2)); 
            
            if (PO_No > highestNo) 
            {
                highestNo = PO_No;
            }
        }

        highestNo++;
        return String.format("PO%03d", highestNo); 
    }
    
    public void PRVisibility (boolean torf){
        lblCurrentStock.setVisible(torf);
        lblReorderLevel.setVisible(torf);
        lblStockStatus.setVisible(torf);
        txtCurrentStock.setVisible(torf);
        txtReorderLevel.setVisible(torf);
        txtStockStatus.setVisible(torf);
        lblQuantityNeeded.setVisible(torf);
        spiQuantityPR.setVisible(torf);
        btnSubmit.setVisible(torf);
    }
    
        
    private void InitPRPage(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PRScrollPane.getVerticalScrollBar().setValue(0);
        txtReqTimestamp.setText(date.format(timestamp));
        txtPRID.setText(pm.AssignPR_ID(PRList));
        txtReqbyName.setText(pm.getFirstName()+" "+pm.getLastName());
        txtReqbyID.setText(pm.getUserID());
        lblPRID.setText(pm.AssignPR_ID(PRList));
        lblRequestDate.setText(date.format(timestamp));
        lblRequestName.setText(pm.getFirstName()+" "+pm.getLastName());
        lblRequestStaffID.setText(pm.getUserID());
        
        
        lblApprovedName.setText("Pending...");
        lblApprovedStaffID.setText("Pending...");
        lblApprovedDate.setText("Pending...");
        
        lblRequestQuantity.setText("0");
        
        cmbPRItemID.removeAllItems();
        cmbPRItemID.addItem("Select...");
        WriteItemComboBoxPR(cmbPRItemID);
        
        DefaultTableModel dtm = (DefaultTableModel) PRItemTable.getModel();
        try{
            dtm.removeRow(0);
        } catch (ArrayIndexOutOfBoundsException e){}
        
        PRVisibility(false);
        
        
    }
    
    public void AddPRTable (ArrayList <Item> ItemList , int ItemIndex,int SelectQuantity){
        Object[] rowData = {ItemList.get(ItemIndex).getItemID(),ItemList.get(ItemIndex).getItemName(),
                    ItemList.get(ItemIndex).getQuantityInStock(),ItemList.get(ItemIndex).getReorderLevel(),
                    SelectQuantity};
        DefaultTableModel dtm = (DefaultTableModel) PRItemTable.getModel();
        try{
        dtm.removeRow(0);} 
        catch(ArrayIndexOutOfBoundsException e){
            
        }
        dtm.addRow(rowData);
    }
    
    private void WriteItemComboBoxPR(JComboBox<String> cBox){
        cBox.removeAllItems();
        cBox.addItem("Select...");
        HashSet<String> PRItem = new HashSet<>();
        for (PurchaseRequisite prr: PRList){
            if (prr.getStatus().equals("Pending")){
                PRItem.add(prr.getITM_ID());}
        }
        for (Item itmm: ItemList){
            if (!PRItem.contains(itmm.getItemID()) 
                    && itmm.getQuantityInStock() <= itmm.getReorderLevel()){
                cBox.addItem(itmm.getItemID());
            }
        }
    }

    
        
    public void InitPRViewPage(){
        pnlViewPR.setVisible(false);
        ScrollPaneViewPR.getVerticalScrollBar().setValue(0);
        
        cmbPRID.removeAllItems();
        cmbPRID.addItem("Select...");
        for (PurchaseRequisite PR: PRList){
            cmbPRID.addItem(PR.getPR_ID());
            
        }
    }
    /**
     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        try 
//        {
//            UIManager.setLookAndFeel(new FlatIntelliJLaf());
//            FlatLaf.setGlobalExtraDefaults( Collections.singletonMap( "@accentColor", "#AB886D" ));
//            FlatIntelliJLaf.setup();
//        
//            java.awt.EventQueue.invokeLater(new Runnable() 
//            {
//                public void run() 
//                {
//                    new PM_dashboard().setVisible(true);
//                }
//            });
//        } 
//        catch (UnsupportedLookAndFeelException ex) 
//        {
//            System.out.println("Please install flatlaf for a better experience!");
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AddPO_PR_table;
    private javax.swing.JLabel ChgPassError;
    private javax.swing.JTable Edit_PO_table;
    private javax.swing.JTable Item_table;
    private javax.swing.JTable PO_table;
    private javax.swing.JTable PRItemTable;
    private javax.swing.JScrollPane PRScrollPane;
    private javax.swing.JTable PRViewItemTable;
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
    private javax.swing.JScrollPane ScrollPaneViewPR;
    private javax.swing.JToggleButton ShowHidePass;
    private javax.swing.JTable Sup_table;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btn_Approve;
    private javax.swing.JButton btn_Reject;
    private javax.swing.JButton btn_cancelApprove;
    private javax.swing.JButton btn_cancelEdit;
    private javax.swing.JButton btn_confirmApprove;
    private javax.swing.JButton btn_confirmEdit;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JComboBox<String> cbb_Add_Supplier_ID;
    private javax.swing.JComboBox<String> cbb_Supplier_ID;
    private javax.swing.JComboBox<String> cmbPRID;
    private javax.swing.JComboBox<String> cmbPRItemID;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel_ITM_ID;
    private javax.swing.JLabel jLabel_NSB_logo;
    private javax.swing.JLabel jLabel_PM_ID;
    private javax.swing.JLabel jLabel_PO_ID;
    private javax.swing.JLabel jLabel_PR_ID;
    private javax.swing.JLabel jLabel_Supplier_ID;
    private javax.swing.JLabel jLabel_Supplier_ID1;
    private javax.swing.JLabel jLabel_UnitCost;
    private javax.swing.JLabel jLabel_total;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel_PO;
    private javax.swing.JPanel jPanel_PR;
    private javax.swing.JPanel jPanel_background;
    private javax.swing.JPanel jPanel_upperBackground;
    private javax.swing.JPanel jPanel_viewItems;
    private javax.swing.JPanel jPanel_viewSuppliers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblApprovedDate;
    private javax.swing.JLabel lblApprovedName;
    private javax.swing.JLabel lblApprovedStaffID;
    private javax.swing.JLabel lblCurrentStock;
    private javax.swing.JLabel lblPM_Name;
    private javax.swing.JLabel lblPRID;
    private javax.swing.JLabel lblQuantityNeeded;
    private javax.swing.JLabel lblReorderLevel;
    private javax.swing.JLabel lblRequestDate;
    private javax.swing.JLabel lblRequestName;
    private javax.swing.JLabel lblRequestQuantity;
    private javax.swing.JLabel lblRequestStaffID;
    private javax.swing.JLabel lblStockStatus;
    private javax.swing.JLabel lblViewApprovedDate;
    private javax.swing.JLabel lblViewApprovedName;
    private javax.swing.JLabel lblViewApprovedStaffID;
    private javax.swing.JLabel lblViewPRID;
    private javax.swing.JLabel lblViewRequestDate;
    private javax.swing.JLabel lblViewRequestName;
    private javax.swing.JLabel lblViewRequestQuantity;
    private javax.swing.JLabel lblViewRequestStaffID;
    private javax.swing.JLabel lbl_ITM_ID;
    private javax.swing.JLabel lbl_Quantity;
    private javax.swing.JLabel lbl_RTS;
    private javax.swing.JLabel lbl_Supplier_ID;
    private javax.swing.JLabel lbl_UnitCost;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_userID;
    private javax.swing.JPanel pnlCreatePR;
    private javax.swing.JPanel pnlViewPR;
    private javax.swing.JSpinner spiQuantityPR;
    private javax.swing.JSpinner spn_Quantity;
    private javax.swing.JTabbedPane subTab;
    private javax.swing.JTabbedPane subTab2;
    private javax.swing.JPanel subTab_Add;
    private javax.swing.JPanel subTab_Edit;
    private javax.swing.JPanel subTab_View;
    private javax.swing.JTextField txtCurrentStock;
    private javax.swing.JTextField txtF_Add_ITM_ID;
    private javax.swing.JTextField txtF_Add_Total;
    private javax.swing.JTextField txtF_Add_UnitCost;
    private javax.swing.JTextField txtF_ITM_ID;
    private javax.swing.JTextField txtF_PM_ID;
    private javax.swing.JTextField txtF_PO_ID;
    private javax.swing.JTextField txtF_PR_ID;
    private javax.swing.JTextField txtF_Quantity;
    private javax.swing.JTextField txtF_RTS;
    private javax.swing.JTextField txtF_Total;
    private javax.swing.JTextField txtF_UnitCost;
    private javax.swing.JTextField txtPRID;
    private javax.swing.JTextField txtReorderLevel;
    private javax.swing.JTextField txtReqTimestamp;
    private javax.swing.JTextField txtReqbyID;
    private javax.swing.JTextField txtReqbyName;
    private javax.swing.JTextField txtStockStatus;
    // End of variables declaration//GEN-END:variables
}
