/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package assignment_oodj;

//import IM_Assignment.FileOperations;
//import IM_Assignment.Inventory_Manager;
//import IM_Assignment.Item;
//import IM_Assignment.ItemUnitPrice;
//import IM_Assignment.PurchaseOrder;
//import IM_Assignment.PurchaseRequisite;
//import IM_Assignment.Supplier;
//import IM_Assignment.User;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class IM_Program extends javax.swing.JFrame {
    //Inventory Manager object
    Inventory_Manager im = new Inventory_Manager();
    private boolean Admin;
    //object delcaration for class
    Item items = new Item();
    ItemUnitPrice iups = new ItemUnitPrice();
    Supplier sps = new Supplier();
    PurchaseOrder pos = new PurchaseOrder();
    PurchaseRequisite prs = new PurchaseRequisite();
    
    //object declaration for reading
    FileOperations<Item> itm = new FileOperations<>();
    FileOperations<ItemUnitPrice> iup = new FileOperations<>();
    FileOperations<Supplier> sp = new FileOperations<>();
    FileOperations<PurchaseOrder> po = new FileOperations<>();
    FileOperations<PurchaseRequisite> pr = new FileOperations<>();
    
    //array declaration
    ArrayList<Item> ItemList = new ArrayList<Item>();
    ArrayList<ItemUnitPrice> IUPList = new ArrayList<ItemUnitPrice>();
    ArrayList<Supplier> SupplierList = new ArrayList<Supplier>();
    ArrayList<PurchaseOrder> POList = new ArrayList<PurchaseOrder>();
    ArrayList<PurchaseRequisite> PRList = new ArrayList<PurchaseRequisite>();
    //table declaration
    DefaultTableModel InventoryTable;
    DefaultTableModel IUPTable;
    DefaultTableModel InventoryTableEdit;
    DefaultTableModel IUPTableEdit;
    DefaultTableModel IUPTableAdd;
    DefaultTableModel ItemTable;
    DefaultTableModel ItemTableEdit;
    DefaultTableModel IUPTableItem;
    DefaultTableModel IUPTableItemAdd;
    DefaultTableModel SupplierTableView;
    DefaultTableModel IUPTableSupplierAdd;
    DefaultTableModel SupplierTableEdit;
    DefaultTableModel UpdateStockTable;
    
    private static final SimpleDateFormat tmstmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public IM_Program(Inventory_Manager im) {
        //readfile
        ItemList = itm.ReadFile("src/data/Item.txt", items);
        IUPList = iup.ReadFile("src/data/ItemUnitPrice.txt", iups);
        SupplierList = sp.ReadFile("src/data/Supplier.txt", sps);
        POList = po.ReadFile("src/data/PurchaseOrder.txt", pos);
        PRList = pr.ReadFile("src/data/PurchaseRequisite.txt", prs);
        
        initComponents();
        //tables
        InventoryTable = (DefaultTableModel)tblinventory.getModel();
        IUPTable = (DefaultTableModel)tbliup.getModel();
        InventoryTableEdit = (DefaultTableModel)tblinv.getModel();
        IUPTableEdit = (DefaultTableModel)tblsid.getModel();
        IUPTableAdd = (DefaultTableModel) tblAiup.getModel();
        ItemTable = (DefaultTableModel)tblVitm.getModel();
        ItemTableEdit = (DefaultTableModel)tblEditItem.getModel();
        IUPTableItem = (DefaultTableModel)tblitmiup.getModel();
        IUPTableItemAdd = (DefaultTableModel)tblAIiup.getModel();
        SupplierTableView = (DefaultTableModel)tblVsup.getModel();
        SupplierTableEdit = (DefaultTableModel)tblEsup.getModel();
        IUPTableSupplierAdd = (DefaultTableModel)tblASiup.getModel();
        UpdateStockTable = (DefaultTableModel)tblStock.getModel();
        
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(tmstmp.format(timestamp));
        //Item & Inventory---------------------------------------------------------------------------------
        //for edit inventory
        ShowHide("hideall");
        ShowHide2("hideall");
        
        //load different types of item tables
        LoadInventory();
        LoadInventoryEdit();
        LoadItemView();
        LoadItemEdit();
        //load same itmids into different combo boxes
        LoadItmIDCMB(cmbitmid);
        LoadItmIDCMB(cmbEitmid);
        LoadItmIDCMB(cmbAitmid);
        LoadItmIDCMB(cmbVitmid);
        LoadItmIDCMB(cmbEdititmid);
        
        // make the add item's itm id and supplier's sid last one+1
        txtfieldAIitmid.setText(GetNextItemID());
        txtfieldAddSIDS.setText(GetNextSupplierID());
        //-------------------------------------------------------------------------------------------------
        
        //Supplier-----------------------------------------------------------------------------------------
        // load data into table
        LoadSupplier(SupplierTableView);
        LoadSupplier(SupplierTableEdit);
        // load sid into combo box
        LoadSIDCMB(cmbVsid);
        LoadSIDCMB(cmbEsid);
        
        //--------------------------------------------------------------------------------------------------
        
        //Update Stock--------------------------------------------------------------------------------------
        //Load Data into table 
        prs.setPOwList(POList, PRList);
        LoadUpdateStock();
        
        //Load poid into combo box
        LoadcmbStock();
        // check if item id or supplier id in itemunitprice does not exist
        iups.CheckItemExist(IUPList, ItemList, SupplierList);
        rereadItemUnitPriceFile();
        
        //inventory manager details------------------------------------------------------------------------
        this.im = im;
        SetNameID();
        im.readLoginCredentials();
        if(this.im.getUserType().equals("A")){
            Admin = true;
        }
        
        im.DisplayProfile(ProfileUserType, ProfileID, ProfileFN, ProfileLN, ProfileGender, ProfileEmail, ProfileContact);
        ChgPassError.setText("");
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        pnlTopPanel3 = new javax.swing.JPanel();
        lblNexusLogo3 = new javax.swing.JLabel();
        lblIM_ID = new javax.swing.JLabel();
        PnlSMDashboard = new javax.swing.JPanel();
        lblIM_Name = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tabpnlIM = new javax.swing.JTabbedPane();
        pnlItems = new javax.swing.JPanel();
        pnlitm = new javax.swing.JTabbedPane();
        pnlVitm = new javax.swing.JPanel();
        tblscrollpanelitem = new javax.swing.JScrollPane();
        tblVitm = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        cmbVitmid = new javax.swing.JComboBox<>();
        btnVclear = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        txtfieldVitmid = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtfieldVitmname = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtfieldVdesc = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtfieldVRP = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblitmiup = new javax.swing.JTable();
        pnlAitm = new javax.swing.JPanel();
        pnlitmd1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtfieldAIitmid = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtfieldAIname = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtfieldAIdesc = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        txtfieldAIQiS = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtfieldAIRL = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        txtfieldAIRP = new javax.swing.JTextField();
        btnclearAI = new javax.swing.JButton();
        btnAddAddItem = new javax.swing.JButton();
        btncancelAI = new javax.swing.JButton();
        pnladdsup1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cmbAddsid = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtfieldAddunitprice = new javax.swing.JTextField();
        btnAddAddSup = new javax.swing.JButton();
        btnclearsup = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblAIiup = new javax.swing.JTable();
        jLabel57 = new javax.swing.JLabel();
        txtfieldASitmid = new javax.swing.JTextField();
        btnsaveall = new javax.swing.JButton();
        pnlsupd1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txtfieldAddSID = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txtfieldAddSname = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        txtfieldAddaddress = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        txtfieldAddCN = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txtfieldAddSR = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        txtfieldAddBN = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        txtfieldAddBAN = new javax.swing.JTextField();
        pnlEitm = new javax.swing.JPanel();
        tblEditItemscrollpanel = new javax.swing.JScrollPane();
        tblEditItem = new javax.swing.JTable();
        pnleditQIS1 = new javax.swing.JPanel();
        btnEditsave = new javax.swing.JButton();
        btnEditcancel = new javax.swing.JButton();
        lblEitmstatus1 = new javax.swing.JLabel();
        btnEditedit = new javax.swing.JButton();
        txtfieldEditRP = new javax.swing.JTextField();
        lblEstatusstar1 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        txtfieldEditdesc = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        txtfieldEditname = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        txtfieldEdititmid = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        cmbEdititmid = new javax.swing.JComboBox<>();
        btnEditclear = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        btnEditdelete = new javax.swing.JButton();
        pnlSuppliers = new javax.swing.JPanel();
        pnlsup = new javax.swing.JTabbedPane();
        pnlVsup = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        cmbVsid = new javax.swing.JComboBox<>();
        btnVSclear = new javax.swing.JButton();
        jLabel73 = new javax.swing.JLabel();
        txtfieldVsid = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        txtfieldVsupname = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        txtfieldVsupadd = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        txtfieldVsupCN = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        txtfieldVsupSR = new javax.swing.JTextField();
        txtfieldVsupBAN = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        txtfieldVsupBN = new javax.swing.JTextField();
        tblVsupscrollpanel = new javax.swing.JScrollPane();
        tblVsup = new javax.swing.JTable();
        pnlAsup = new javax.swing.JPanel();
        pnlsupd2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        txtfieldAddSIDS = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        txtfieldAddSnameS = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        txtfieldAddaddressS = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        txtfieldAddCNS = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        txtfieldAddSRS = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        txtfieldAddBNS = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        txtfieldAddBANS = new javax.swing.JTextField();
        btnAddAS = new javax.swing.JButton();
        btnclearAS = new javax.swing.JButton();
        btncancelAS = new javax.swing.JButton();
        pnladdsup2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        cmbAdditmid = new javax.swing.JComboBox<>();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        txtfieldAddunitpriceS = new javax.swing.JTextField();
        btnAddAdditmidS = new javax.swing.JButton();
        btnclearitmS = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblASiup = new javax.swing.JTable();
        jLabel92 = new javax.swing.JLabel();
        txtfieldASsid = new javax.swing.JTextField();
        btnsaveallS = new javax.swing.JButton();
        pnlsupd3 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        txtfieldAitmidS = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        txtfieldAnameS = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        txtfieldAdescS = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        txtfieldAQiSS = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        txtfieldARLS = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        txtfieldARPS = new javax.swing.JTextField();
        pnlEsup = new javax.swing.JPanel();
        pnlsupd4 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        txtfieldEditSname = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        txtfieldEditaddressS = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        txtfieldEditCNS = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        txtfieldEditSRS = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
        txtfieldEditBNS = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        txtfieldEditBANS = new javax.swing.JTextField();
        btneditsup = new javax.swing.JButton();
        btndeletesup = new javax.swing.JButton();
        btncancelsup = new javax.swing.JButton();
        cmbEsid = new javax.swing.JComboBox<>();
        btncleareditsup = new javax.swing.JButton();
        btnsavesup = new javax.swing.JButton();
        tblEsupscrollpanel = new javax.swing.JScrollPane();
        tblEsup = new javax.swing.JTable();
        pnlInventory = new javax.swing.JPanel();
        pnlUI = new javax.swing.JTabbedPane();
        pnlVI = new javax.swing.JPanel();
        tblscrollpanel = new javax.swing.JScrollPane();
        tblinventory = new javax.swing.JTable();
        pnladddetails = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbliup = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtfielditmid = new javax.swing.JTextField();
        txtfieldname = new javax.swing.JTextField();
        txtfielddesc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtfieldQiS = new javax.swing.JTextField();
        txtfieldRL = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblitmstatus = new javax.swing.JLabel();
        lblstatusstar = new javax.swing.JLabel();
        cmbitmid = new javax.swing.JComboBox<>();
        btnclear = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        pnlAI = new javax.swing.JPanel();
        pnlitmd = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtfieldAitmid = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtfieldAname = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtfieldAdesc = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtfieldAQiS = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtfieldARL = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtfieldARP = new javax.swing.JTextField();
        pnladdsup = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        cmbAitmid = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cmbAsid = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtfieldAunitprice = new javax.swing.JTextField();
        btnAadd = new javax.swing.JButton();
        btnAclear = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAiup = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        pnlsupd = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtfieldASID = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtfieldASname = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtfieldAaddress = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtfieldACN = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtfieldASR = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtfieldABN = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtfieldABAN = new javax.swing.JTextField();
        pnlEI = new javax.swing.JPanel();
        pnleditQIS = new javax.swing.JPanel();
        btnsave = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();
        txtfieldERL = new javax.swing.JTextField();
        lblEitmstatus = new javax.swing.JLabel();
        btnedit = new javax.swing.JButton();
        txtfieldEQiS = new javax.swing.JTextField();
        lblEstatusstar = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtfieldEdesc = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtfieldEname = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtfieldEitmid = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbEitmid = new javax.swing.JComboBox<>();
        btnEclear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        pnleditSID = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblsid = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        cmbSID = new javax.swing.JComboBox<>();
        btnclearSID = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtfieldEsupplierid = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtfieldEunitprice = new javax.swing.JTextField();
        btneditsip = new javax.swing.JButton();
        btnsavesip = new javax.swing.JButton();
        btncancelsip = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        btndltsip = new javax.swing.JButton();
        tblinvscrollpanel = new javax.swing.JScrollPane();
        tblinv = new javax.swing.JTable();
        pnlStock = new javax.swing.JPanel();
        tblStockscrollpanel = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        pnleditQIS2 = new javax.swing.JPanel();
        txtfieldPSstock = new javax.swing.JTextField();
        txtfieldstatusstock = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        txtfieldquantitystock = new javax.swing.JTextField();
        jLabel110 = new javax.swing.JLabel();
        txtfielditmnamestock = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        txtfieldpoidstock = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        cmbstock = new javax.swing.JComboBox<>();
        btnclearstock = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        txtfieldpridstock = new javax.swing.JTextField();
        jLabel115 = new javax.swing.JLabel();
        txtfielditmidstock = new javax.swing.JTextField();
        jLabel116 = new javax.swing.JLabel();
        btnupdatestock = new javax.swing.JButton();
        pnlProfile = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        ProfileID = new javax.swing.JLabel();
        ProfileUserType = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        ProfileFN = new javax.swing.JLabel();
        ProfileLN = new javax.swing.JLabel();
        ProfileEmail = new javax.swing.JLabel();
        ProfileGender = new javax.swing.JLabel();
        ProfileContact = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        ProfileCurrentPass = new javax.swing.JPasswordField();
        ProfileNewPass = new javax.swing.JPasswordField();
        ProfileChgPass = new javax.swing.JButton();
        ChgPassError = new javax.swing.JLabel();
        ShowHidePass = new javax.swing.JToggleButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inventory Manager");
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(228, 224, 225));
        jPanel3.setPreferredSize(new java.awt.Dimension(1280, 720));

        pnlTopPanel3.setBackground(new java.awt.Color(171, 136, 109));
        pnlTopPanel3.setPreferredSize(new java.awt.Dimension(1280, 120));

        lblNexusLogo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/NexusLogoThemeSmall.png"))); // NOI18N

        lblIM_ID.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        lblIM_ID.setForeground(new java.awt.Color(228, 224, 225));
        lblIM_ID.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIM_ID.setText("Inventory Manager ID");

        javax.swing.GroupLayout pnlTopPanel3Layout = new javax.swing.GroupLayout(pnlTopPanel3);
        pnlTopPanel3.setLayout(pnlTopPanel3Layout);
        pnlTopPanel3Layout.setHorizontalGroup(
            pnlTopPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblNexusLogo3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 455, Short.MAX_VALUE)
                .addComponent(lblIM_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnlTopPanel3Layout.setVerticalGroup(
            pnlTopPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNexusLogo3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
            .addComponent(lblIM_ID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        PnlSMDashboard.setBackground(new java.awt.Color(228, 224, 225));

        lblIM_Name.setBackground(new java.awt.Color(228, 224, 225));
        lblIM_Name.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        lblIM_Name.setForeground(new java.awt.Color(73, 54, 40));
        lblIM_Name.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblIM_Name.setText("Welcome! IM_Name");

        jLabel2.setBackground(new java.awt.Color(228, 224, 225));
        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(73, 54, 40));
        jLabel2.setText("Inventory Manager Dashboard");

        javax.swing.GroupLayout PnlSMDashboardLayout = new javax.swing.GroupLayout(PnlSMDashboard);
        PnlSMDashboard.setLayout(PnlSMDashboardLayout);
        PnlSMDashboardLayout.setHorizontalGroup(
            PnlSMDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlSMDashboardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIM_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        PnlSMDashboardLayout.setVerticalGroup(
            PnlSMDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlSMDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblIM_Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabpnlIM.setBackground(new java.awt.Color(228, 224, 225));
        tabpnlIM.setForeground(new java.awt.Color(73, 54, 40));
        tabpnlIM.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        tabpnlIM.setPreferredSize(new java.awt.Dimension(1280, 552));
        tabpnlIM.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabpnlIMStateChanged(evt);
            }
        });

        pnlItems.setBackground(new java.awt.Color(228, 224, 225));

        pnlitm.setBackground(new java.awt.Color(228, 224, 225));
        pnlitm.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        pnlitm.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        pnlitm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pnlitmStateChanged(evt);
            }
        });

        pnlVitm.setBackground(new java.awt.Color(228, 224, 225));

        tblVitm.setBackground(new java.awt.Color(214,192,179));
        tblVitm.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblVitm.setForeground(new java.awt.Color(73, 54, 40));
        tblVitm.getTableHeader().setOpaque(false);
        tblVitm.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblVitm.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblVitm.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblVitm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Description", "Retail Price (RM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVitm.setGridColor(new java.awt.Color(214, 192, 179));
        tblVitm.getTableHeader().setReorderingAllowed(false);
        tblscrollpanelitem.setViewportView(tblVitm);
        if (tblVitm.getColumnModel().getColumnCount() > 0) {
            tblVitm.getColumnModel().getColumn(0).setMinWidth(80);
            tblVitm.getColumnModel().getColumn(0).setPreferredWidth(80);
            tblVitm.getColumnModel().getColumn(0).setMaxWidth(80);
            tblVitm.getColumnModel().getColumn(1).setMinWidth(140);
            tblVitm.getColumnModel().getColumn(1).setPreferredWidth(140);
            tblVitm.getColumnModel().getColumn(1).setMaxWidth(140);
            tblVitm.getColumnModel().getColumn(2).setResizable(false);
            tblVitm.getColumnModel().getColumn(3).setMinWidth(120);
            tblVitm.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        jPanel10.setBackground(new java.awt.Color(228, 224, 225));
        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel44.setText("Item ID :");

        cmbVitmid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbVitmid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbVitmid.setFocusable(false);
        cmbVitmid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbVitmidActionPerformed(evt);
            }
        });

        btnVclear.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnVclear.setText("Clear");
        btnVclear.setFocusPainted(false);
        btnVclear.setFocusable(false);
        btnVclear.setRequestFocusEnabled(false);
        btnVclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVclearActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel45.setText("Item ID");

        txtfieldVitmid.setEditable(false);
        txtfieldVitmid.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel46.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel46.setText("Item Name");

        txtfieldVitmname.setEditable(false);
        txtfieldVitmname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel47.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel47.setText("Description");

        txtfieldVdesc.setEditable(false);
        txtfieldVdesc.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel48.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel48.setText("Retail Price (RM)");

        txtfieldVRP.setEditable(false);
        txtfieldVRP.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel49.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel49.setText("Supplied by :");

        tblitmiup.setAutoCreateRowSorter(true);
        tblitmiup.setBackground(new java.awt.Color(214,192,179));
        tblitmiup.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblitmiup.setForeground(new java.awt.Color(73, 54, 40));
        tblitmiup.getTableHeader().setOpaque(false);
        tblitmiup.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblitmiup.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblitmiup.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblitmiup.setModel(new javax.swing.table.DefaultTableModel(
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
        tblitmiup.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblitmiup);
        if (tblitmiup.getColumnModel().getColumnCount() > 0) {
            tblitmiup.getColumnModel().getColumn(0).setResizable(false);
            tblitmiup.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbVitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnVclear))
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtfieldVitmid)
                                .addComponent(txtfieldVitmname)
                                .addComponent(txtfieldVdesc)
                                .addComponent(jLabel47)
                                .addComponent(jLabel46)
                                .addComponent(jLabel45)
                                .addComponent(jLabel48)
                                .addComponent(txtfieldVRP, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel49)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbVitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(btnVclear))
                .addGap(18, 18, 18)
                .addComponent(jLabel45)
                .addGap(6, 6, 6)
                .addComponent(txtfieldVitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldVitmname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldVdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldVRP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlVitmLayout = new javax.swing.GroupLayout(pnlVitm);
        pnlVitm.setLayout(pnlVitmLayout);
        pnlVitmLayout.setHorizontalGroup(
            pnlVitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVitmLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(tblscrollpanelitem, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlVitmLayout.setVerticalGroup(
            pnlVitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVitmLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlVitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tblscrollpanelitem))
                .addGap(18, 18, 18))
        );

        pnlitm.addTab("View", pnlVitm);

        pnlAitm.setBackground(new java.awt.Color(228, 224, 225));

        pnlitmd1.setBackground(new java.awt.Color(228, 224, 225));
        pnlitmd1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        jPanel12.setBackground(new java.awt.Color(171, 136, 109));

        jLabel50.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Enter New Item Details");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel51.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel51.setText("Item ID :");

        txtfieldAIitmid.setEditable(false);
        txtfieldAIitmid.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        jLabel52.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel52.setText("Item Name");

        txtfieldAIname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldAIname.setToolTipText("");

        jLabel53.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel53.setText("Description");

        txtfieldAIdesc.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldAIdesc.setToolTipText("");

        jLabel54.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel54.setText("Quantity in Stock");

        txtfieldAIQiS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldAIQiS.setToolTipText("");

        jLabel55.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel55.setText("Reorder Level");

        txtfieldAIRL.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldAIRL.setToolTipText("");

        jLabel56.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel56.setText("Retail Price (RM)");

        txtfieldAIRP.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldAIRP.setToolTipText("");

        btnclearAI.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnclearAI.setText("Clear");
        btnclearAI.setFocusPainted(false);
        btnclearAI.setFocusable(false);
        btnclearAI.setRequestFocusEnabled(false);
        btnclearAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearAIActionPerformed(evt);
            }
        });

        btnAddAddItem.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnAddAddItem.setText("Add");
        btnAddAddItem.setFocusPainted(false);
        btnAddAddItem.setFocusable(false);
        btnAddAddItem.setRequestFocusEnabled(false);
        btnAddAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAddItemActionPerformed(evt);
            }
        });

        btncancelAI.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btncancelAI.setText("Cancel");
        btncancelAI.setEnabled(false);
        btncancelAI.setFocusPainted(false);
        btncancelAI.setFocusable(false);
        btncancelAI.setRequestFocusEnabled(false);
        btncancelAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelAIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlitmd1Layout = new javax.swing.GroupLayout(pnlitmd1);
        pnlitmd1.setLayout(pnlitmd1Layout);
        pnlitmd1Layout.setHorizontalGroup(
            pnlitmd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlitmd1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlitmd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlitmd1Layout.createSequentialGroup()
                        .addGroup(pnlitmd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel53)
                            .addComponent(jLabel52)
                            .addGroup(pnlitmd1Layout.createSequentialGroup()
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtfieldAIitmid, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel54)
                            .addGroup(pnlitmd1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(pnlitmd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel55)
                                    .addComponent(jLabel56))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlitmd1Layout.createSequentialGroup()
                        .addGroup(pnlitmd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlitmd1Layout.createSequentialGroup()
                                .addComponent(btnAddAddItem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnclearAI)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                                .addComponent(btncancelAI))
                            .addComponent(txtfieldAIname, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldAIdesc, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldAIQiS, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldAIRL, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldAIRP, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(28, 28, 28))))
        );
        pnlitmd1Layout.setVerticalGroup(
            pnlitmd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlitmd1Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlitmd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txtfieldAIitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAIname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAIdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAIQiS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAIRL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAIRP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlitmd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnclearAI)
                    .addComponent(btnAddAddItem)
                    .addComponent(btncancelAI))
                .addGap(18, 18, 18))
        );

        pnladdsup1.setBackground(new java.awt.Color(228, 224, 225));
        pnladdsup1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));
        pnladdsup1.setPreferredSize(new java.awt.Dimension(244, 481));

        jPanel7.setBackground(new java.awt.Color(171, 136, 109));

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Add Supplier for Item ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel40.setText("Item ID             :");

        cmbAddsid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbAddsid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbAddsid.setEnabled(false);
        cmbAddsid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAddsidActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel41.setText("Supplier ID       :");

        jLabel42.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel42.setText("Unit Price (RM) :");

        txtfieldAddunitprice.setEditable(false);
        txtfieldAddunitprice.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        txtfieldAddunitprice.setToolTipText("");

        btnAddAddSup.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnAddAddSup.setText("Add");
        btnAddAddSup.setEnabled(false);
        btnAddAddSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAddSupActionPerformed(evt);
            }
        });

        btnclearsup.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnclearsup.setText("Clear");
        btnclearsup.setEnabled(false);
        btnclearsup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearsupActionPerformed(evt);
            }
        });

        tblAIiup.setBackground(new java.awt.Color(214,192,179));
        tblAIiup.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblAIiup.setForeground(new java.awt.Color(73, 54, 40));
        tblAIiup.getTableHeader().setOpaque(false);
        tblAIiup.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblAIiup.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblAIiup.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblAIiup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Unit Price (RM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAIiup.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tblAIiup);
        if (tblAIiup.getColumnModel().getColumnCount() > 0) {
            tblAIiup.getColumnModel().getColumn(0).setResizable(false);
            tblAIiup.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel57.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel57.setText("Suppliers :");

        txtfieldASitmid.setEditable(false);
        txtfieldASitmid.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        btnsaveall.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnsaveall.setText("Save");
        btnsaveall.setEnabled(false);
        btnsaveall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveallActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnladdsup1Layout = new javax.swing.GroupLayout(pnladdsup1);
        pnladdsup1.setLayout(pnladdsup1Layout);
        pnladdsup1Layout.setHorizontalGroup(
            pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnladdsup1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsup1Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnsaveall)
                        .addGap(83, 83, 83))
                    .addGroup(pnladdsup1Layout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsup1Layout.createSequentialGroup()
                                .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)
                                .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbAddsid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtfieldAddunitprice)
                                    .addComponent(txtfieldASitmid))
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsup1Layout.createSequentialGroup()
                                .addComponent(btnAddAddSup)
                                .addGap(18, 18, 18)
                                .addComponent(btnclearsup)
                                .addGap(37, 37, 37))))))
        );
        pnladdsup1Layout.setVerticalGroup(
            pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsup1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtfieldASitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(cmbAddsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtfieldAddunitprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnclearsup)
                    .addComponent(btnAddAddSup))
                .addGroup(pnladdsup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnladdsup1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnsaveall)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnladdsup1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel57)
                        .addGap(6, 6, 6)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlsupd1.setBackground(new java.awt.Color(228, 224, 225));
        pnlsupd1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));
        pnlsupd1.setPreferredSize(new java.awt.Dimension(447, 469));

        jPanel8.setBackground(new java.awt.Color(171, 136, 109));

        jLabel58.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Supplier Details");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel59.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel59.setText("Supplier ID");

        txtfieldAddSID.setEditable(false);
        txtfieldAddSID.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel60.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel60.setText("Supplier Name");

        txtfieldAddSname.setEditable(false);
        txtfieldAddSname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel61.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel61.setText("Address");

        txtfieldAddaddress.setEditable(false);
        txtfieldAddaddress.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel62.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel62.setText("Contact Number");

        txtfieldAddCN.setEditable(false);
        txtfieldAddCN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel63.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel63.setText("Supplier's Rating");

        txtfieldAddSR.setEditable(false);
        txtfieldAddSR.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel64.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel64.setText("Bank Name");

        txtfieldAddBN.setEditable(false);
        txtfieldAddBN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel65.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel65.setText("Bank Account Number");

        txtfieldAddBAN.setEditable(false);
        txtfieldAddBAN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout pnlsupd1Layout = new javax.swing.GroupLayout(pnlsupd1);
        pnlsupd1.setLayout(pnlsupd1Layout);
        pnlsupd1Layout.setHorizontalGroup(
            pnlsupd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlsupd1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlsupd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel63)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupd1Layout.createSequentialGroup()
                        .addGroup(pnlsupd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addComponent(txtfieldAddBN, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlsupd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldAddBAN)
                            .addGroup(pnlsupd1Layout.createSequentialGroup()
                                .addComponent(jLabel65)
                                .addGap(0, 32, Short.MAX_VALUE))))
                    .addComponent(jLabel61)
                    .addComponent(jLabel60)
                    .addComponent(jLabel59)
                    .addComponent(txtfieldAddaddress)
                    .addComponent(jLabel62)
                    .addComponent(txtfieldAddSname)
                    .addComponent(txtfieldAddCN)
                    .addComponent(txtfieldAddSR)
                    .addComponent(txtfieldAddSID))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlsupd1Layout.setVerticalGroup(
            pnlsupd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupd1Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel59)
                .addGap(6, 6, 6)
                .addComponent(txtfieldAddSID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAddSname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAddaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAddCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlsupd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlsupd1Layout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldAddSR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlsupd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(jLabel65))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldAddBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfieldAddBAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlAitmLayout = new javax.swing.GroupLayout(pnlAitm);
        pnlAitm.setLayout(pnlAitmLayout);
        pnlAitmLayout.setHorizontalGroup(
            pnlAitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAitmLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnlitmd1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnladdsup1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlsupd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlAitmLayout.setVerticalGroup(
            pnlAitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAitmLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlAitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnladdsup1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                    .addComponent(pnlitmd1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlsupd1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        pnlitm.addTab("Add", pnlAitm);

        pnlEitm.setBackground(new java.awt.Color(228, 224, 225));

        tblEditItem.setBackground(new java.awt.Color(214,192,179));
        tblEditItem.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblEditItem.setForeground(new java.awt.Color(73, 54, 40));
        tblEditItem.getTableHeader().setOpaque(false);
        tblEditItem.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblEditItem.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblEditItem.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblEditItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Description", "Retail Price (RM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEditItem.getTableHeader().setReorderingAllowed(false);
        tblEditItemscrollpanel.setViewportView(tblEditItem);
        if (tblEditItem.getColumnModel().getColumnCount() > 0) {
            tblEditItem.getColumnModel().getColumn(0).setMinWidth(80);
            tblEditItem.getColumnModel().getColumn(0).setMaxWidth(80);
            tblEditItem.getColumnModel().getColumn(1).setMinWidth(170);
            tblEditItem.getColumnModel().getColumn(1).setMaxWidth(170);
            tblEditItem.getColumnModel().getColumn(3).setMinWidth(120);
            tblEditItem.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        pnleditQIS1.setBackground(new java.awt.Color(228, 224, 225));
        pnleditQIS1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        btnEditsave.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnEditsave.setText("Save");
        btnEditsave.setEnabled(false);
        btnEditsave.setFocusPainted(false);
        btnEditsave.setFocusable(false);
        btnEditsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditsaveActionPerformed(evt);
            }
        });

        btnEditcancel.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnEditcancel.setText("Cancel");
        btnEditcancel.setEnabled(false);
        btnEditcancel.setFocusPainted(false);
        btnEditcancel.setFocusable(false);
        btnEditcancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditcancelActionPerformed(evt);
            }
        });

        lblEitmstatus1.setFont(new java.awt.Font("Yu Gothic UI", 2, 14)); // NOI18N
        lblEitmstatus1.setForeground(new java.awt.Color(228, 224, 225));
        lblEitmstatus1.setText("*Item needs to be restocked as soon as possible!");

        btnEditedit.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnEditedit.setText("Edit");
        btnEditedit.setEnabled(false);
        btnEditedit.setFocusPainted(false);
        btnEditedit.setFocusable(false);
        btnEditedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditeditActionPerformed(evt);
            }
        });

        txtfieldEditRP.setEditable(false);
        txtfieldEditRP.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldEditRP.setToolTipText("");

        lblEstatusstar1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        lblEstatusstar1.setForeground(new java.awt.Color(228, 224, 225));
        lblEstatusstar1.setText("*");

        jLabel67.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel67.setText("Retail Price (RM)");

        txtfieldEditdesc.setEditable(false);
        txtfieldEditdesc.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldEditdesc.setToolTipText("");

        jLabel68.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel68.setText("Description");

        txtfieldEditname.setEditable(false);
        txtfieldEditname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldEditname.setToolTipText("");

        jLabel69.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel69.setText("Item Name");

        txtfieldEdititmid.setEditable(false);
        txtfieldEdititmid.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N
        txtfieldEdititmid.setToolTipText("");

        jLabel70.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel70.setText("Item ID");

        jLabel71.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel71.setText("Item ID :");

        cmbEdititmid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbEdititmid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbEdititmid.setFocusable(false);
        cmbEdititmid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEdititmidActionPerformed(evt);
            }
        });

        btnEditclear.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnEditclear.setText("Clear");
        btnEditclear.setFocusPainted(false);
        btnEditclear.setFocusable(false);
        btnEditclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditclearActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(171, 136, 109));

        jLabel72.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setText("Edit Item Details");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnEditdelete.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnEditdelete.setText("Delete");
        btnEditdelete.setEnabled(false);
        btnEditdelete.setFocusPainted(false);
        btnEditdelete.setFocusable(false);
        btnEditdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditdeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnleditQIS1Layout = new javax.swing.GroupLayout(pnleditQIS1);
        pnleditQIS1.setLayout(pnleditQIS1Layout);
        pnleditQIS1Layout.setHorizontalGroup(
            pnleditQIS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnleditQIS1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnleditQIS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnleditQIS1Layout.createSequentialGroup()
                        .addComponent(btnEditedit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditdelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditsave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditcancel))
                    .addComponent(txtfieldEdititmid)
                    .addGroup(pnleditQIS1Layout.createSequentialGroup()
                        .addGroup(pnleditQIS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel68)
                            .addComponent(jLabel69)
                            .addComponent(jLabel70)
                            .addGroup(pnleditQIS1Layout.createSequentialGroup()
                                .addComponent(jLabel67)
                                .addGap(3, 3, 3)
                                .addComponent(lblEstatusstar1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblEitmstatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnleditQIS1Layout.createSequentialGroup()
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbEdititmid, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditclear)))
                        .addGap(0, 39, Short.MAX_VALUE))
                    .addComponent(txtfieldEditname)
                    .addComponent(txtfieldEditdesc)
                    .addComponent(txtfieldEditRP))
                .addGap(15, 15, 15))
        );
        pnleditQIS1Layout.setVerticalGroup(
            pnleditQIS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditQIS1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(pnleditQIS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(cmbEdititmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditclear))
                .addGap(18, 18, 18)
                .addComponent(jLabel70)
                .addGap(6, 6, 6)
                .addComponent(txtfieldEdititmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEditname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel68)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEditdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnleditQIS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(lblEstatusstar1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEditRP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEitmstatus1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnleditQIS1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditedit)
                    .addComponent(btnEditsave)
                    .addComponent(btnEditcancel)
                    .addComponent(btnEditdelete))
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout pnlEitmLayout = new javax.swing.GroupLayout(pnlEitm);
        pnlEitm.setLayout(pnlEitmLayout);
        pnlEitmLayout.setHorizontalGroup(
            pnlEitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEitmLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnleditQIS1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tblEditItemscrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
        pnlEitmLayout.setVerticalGroup(
            pnlEitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEitmLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlEitmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tblEditItemscrollpanel)
                    .addComponent(pnleditQIS1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        pnlitm.addTab("Edit", pnlEitm);

        javax.swing.GroupLayout pnlItemsLayout = new javax.swing.GroupLayout(pnlItems);
        pnlItems.setLayout(pnlItemsLayout);
        pnlItemsLayout.setHorizontalGroup(
            pnlItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlitm)
        );
        pnlItemsLayout.setVerticalGroup(
            pnlItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlitm, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        tabpnlIM.addTab("Items", pnlItems);

        pnlSuppliers.setBackground(new java.awt.Color(228, 224, 225));

        pnlsup.setBackground(new java.awt.Color(228, 224, 225));
        pnlsup.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        pnlsup.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        pnlsup.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pnlsupStateChanged(evt);
            }
        });

        pnlVsup.setBackground(new java.awt.Color(228, 224, 225));

        jPanel11.setBackground(new java.awt.Color(228, 224, 225));
        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        jLabel66.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel66.setText("Supplier ID :");

        cmbVsid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbVsid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbVsid.setFocusable(false);
        cmbVsid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbVsidActionPerformed(evt);
            }
        });

        btnVSclear.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnVSclear.setText("Clear");
        btnVSclear.setFocusPainted(false);
        btnVSclear.setFocusable(false);
        btnVSclear.setRequestFocusEnabled(false);
        btnVSclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVSclearActionPerformed(evt);
            }
        });

        jLabel73.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel73.setText("Supplier ID");

        txtfieldVsid.setEditable(false);
        txtfieldVsid.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel74.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel74.setText("Supplier Name");

        txtfieldVsupname.setEditable(false);
        txtfieldVsupname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel75.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel75.setText("Address");

        txtfieldVsupadd.setEditable(false);
        txtfieldVsupadd.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel76.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel76.setText("Contact Number");

        txtfieldVsupCN.setEditable(false);
        txtfieldVsupCN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel77.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel77.setText("Supplier's Rating");

        txtfieldVsupSR.setEditable(false);
        txtfieldVsupSR.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        txtfieldVsupBAN.setEditable(false);
        txtfieldVsupBAN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel78.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel78.setText("Bank Account Number");

        jLabel79.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel79.setText("Bank Name");

        txtfieldVsupBN.setEditable(false);
        txtfieldVsupBN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbVsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVSclear))
                    .addComponent(txtfieldVsid)
                    .addComponent(txtfieldVsupname)
                    .addComponent(txtfieldVsupadd)
                    .addComponent(jLabel75)
                    .addComponent(jLabel74)
                    .addComponent(jLabel73)
                    .addComponent(jLabel76)
                    .addComponent(txtfieldVsupCN, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77)
                    .addComponent(txtfieldVsupSR, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(txtfieldVsupBN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel79)
                                .addGap(89, 89, 89)))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel78)
                            .addComponent(txtfieldVsupBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbVsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66)
                    .addComponent(btnVSclear))
                .addGap(18, 18, 18)
                .addComponent(jLabel73)
                .addGap(6, 6, 6)
                .addComponent(txtfieldVsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldVsupname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel75)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldVsupadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldVsupCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldVsupSR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel79)
                            .addComponent(jLabel78))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldVsupBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfieldVsupBAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblVsup.setBackground(new java.awt.Color(214,192,179));
        tblVsup.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblVsup.setForeground(new java.awt.Color(73, 54, 40));
        tblVsup.getTableHeader().setOpaque(false);
        tblVsup.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblVsup.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblVsup.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblVsup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Supplier Name", "Address", "Contact Number", "Bank Name", "Account Number", "Rating"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVsup.getTableHeader().setReorderingAllowed(false);
        tblVsupscrollpanel.setViewportView(tblVsup);
        if (tblVsup.getColumnModel().getColumnCount() > 0) {
            tblVsup.getColumnModel().getColumn(0).setMinWidth(80);
            tblVsup.getColumnModel().getColumn(0).setMaxWidth(80);
            tblVsup.getColumnModel().getColumn(1).setMinWidth(130);
            tblVsup.getColumnModel().getColumn(1).setMaxWidth(130);
            tblVsup.getColumnModel().getColumn(2).setResizable(false);
            tblVsup.getColumnModel().getColumn(3).setMinWidth(120);
            tblVsup.getColumnModel().getColumn(3).setMaxWidth(120);
            tblVsup.getColumnModel().getColumn(4).setMinWidth(110);
            tblVsup.getColumnModel().getColumn(4).setMaxWidth(110);
            tblVsup.getColumnModel().getColumn(5).setMinWidth(120);
            tblVsup.getColumnModel().getColumn(5).setMaxWidth(120);
            tblVsup.getColumnModel().getColumn(6).setMinWidth(60);
            tblVsup.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        javax.swing.GroupLayout pnlVsupLayout = new javax.swing.GroupLayout(pnlVsup);
        pnlVsup.setLayout(pnlVsupLayout);
        pnlVsupLayout.setHorizontalGroup(
            pnlVsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVsupLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(tblVsupscrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlVsupLayout.setVerticalGroup(
            pnlVsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVsupLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlVsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tblVsupscrollpanel)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        pnlsup.addTab("View", pnlVsup);

        pnlAsup.setBackground(new java.awt.Color(228, 224, 225));

        pnlsupd2.setBackground(new java.awt.Color(228, 224, 225));
        pnlsupd2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));
        pnlsupd2.setPreferredSize(new java.awt.Dimension(447, 469));

        jPanel13.setBackground(new java.awt.Color(171, 136, 109));

        jLabel80.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setText("Enter New Supplier Details");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel81.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel81.setText("Supplier ID :");

        txtfieldAddSIDS.setEditable(false);
        txtfieldAddSIDS.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        jLabel82.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel82.setText("Supplier Name");

        txtfieldAddSnameS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel83.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel83.setText("Address");

        txtfieldAddaddressS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel84.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel84.setText("Contact Number");

        txtfieldAddCNS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel85.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel85.setText("Supplier's Rating");

        txtfieldAddSRS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel86.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel86.setText("Bank Name");

        txtfieldAddBNS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel87.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel87.setText("Bank Account Number");

        txtfieldAddBANS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        btnAddAS.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnAddAS.setText("Add");
        btnAddAS.setFocusPainted(false);
        btnAddAS.setFocusable(false);
        btnAddAS.setRequestFocusEnabled(false);
        btnAddAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddASActionPerformed(evt);
            }
        });

        btnclearAS.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnclearAS.setText("Clear");
        btnclearAS.setFocusPainted(false);
        btnclearAS.setFocusable(false);
        btnclearAS.setRequestFocusEnabled(false);
        btnclearAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearASActionPerformed(evt);
            }
        });

        btncancelAS.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btncancelAS.setText("Cancel");
        btncancelAS.setEnabled(false);
        btncancelAS.setFocusPainted(false);
        btncancelAS.setFocusable(false);
        btncancelAS.setRequestFocusEnabled(false);
        btncancelAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelASActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlsupd2Layout = new javax.swing.GroupLayout(pnlsupd2);
        pnlsupd2.setLayout(pnlsupd2Layout);
        pnlsupd2Layout.setHorizontalGroup(
            pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlsupd2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlsupd2Layout.createSequentialGroup()
                        .addComponent(btnAddAS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnclearAS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncancelAS))
                    .addComponent(jLabel85)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupd2Layout.createSequentialGroup()
                        .addGroup(pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel86)
                            .addComponent(txtfieldAddBNS, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlsupd2Layout.createSequentialGroup()
                                .addComponent(jLabel87)
                                .addGap(0, 32, Short.MAX_VALUE))
                            .addComponent(txtfieldAddBANS)))
                    .addComponent(jLabel83)
                    .addComponent(jLabel82)
                    .addComponent(txtfieldAddaddressS)
                    .addComponent(jLabel84)
                    .addComponent(txtfieldAddSnameS)
                    .addComponent(txtfieldAddCNS)
                    .addComponent(txtfieldAddSRS)
                    .addGroup(pnlsupd2Layout.createSequentialGroup()
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldAddSIDS, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        pnlsupd2Layout.setVerticalGroup(
            pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupd2Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(txtfieldAddSIDS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel82)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAddSnameS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel83)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAddaddressS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel84)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAddCNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlsupd2Layout.createSequentialGroup()
                        .addComponent(jLabel85)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldAddSRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel86)
                            .addComponent(jLabel87))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldAddBNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfieldAddBANS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlsupd2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnclearAS)
                    .addComponent(btnAddAS)
                    .addComponent(btncancelAS))
                .addGap(14, 14, 14))
        );

        pnladdsup2.setBackground(new java.awt.Color(228, 224, 225));
        pnladdsup2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));
        pnladdsup2.setPreferredSize(new java.awt.Dimension(244, 481));

        jPanel14.setBackground(new java.awt.Color(171, 136, 109));

        jLabel88.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel88.setText("Add Item for Supplier ");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel89.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel89.setText("Supplier ID :");

        cmbAdditmid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbAdditmid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbAdditmid.setEnabled(false);
        cmbAdditmid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAdditmidActionPerformed(evt);
            }
        });

        jLabel90.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel90.setText("Item ID       :");

        jLabel91.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel91.setText("Unit Price (RM) :");

        txtfieldAddunitpriceS.setEditable(false);
        txtfieldAddunitpriceS.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        btnAddAdditmidS.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnAddAdditmidS.setText("Add");
        btnAddAdditmidS.setEnabled(false);
        btnAddAdditmidS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAdditmidSActionPerformed(evt);
            }
        });

        btnclearitmS.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnclearitmS.setText("Clear");
        btnclearitmS.setEnabled(false);
        btnclearitmS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearitmSActionPerformed(evt);
            }
        });

        tblASiup.setBackground(new java.awt.Color(214,192,179));
        tblASiup.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblASiup.setForeground(new java.awt.Color(73, 54, 40));
        tblASiup.getTableHeader().setOpaque(false);
        tblASiup.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblASiup.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblASiup.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblASiup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Unit Price (RM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblASiup.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tblASiup);
        if (tblASiup.getColumnModel().getColumnCount() > 0) {
            tblASiup.getColumnModel().getColumn(0).setResizable(false);
            tblASiup.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel92.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel92.setText("Supplies:");

        txtfieldASsid.setEditable(false);
        txtfieldASsid.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        btnsaveallS.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnsaveallS.setText("Save");
        btnsaveallS.setEnabled(false);
        btnsaveallS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveallSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnladdsup2Layout = new javax.swing.GroupLayout(pnladdsup2);
        pnladdsup2.setLayout(pnladdsup2Layout);
        pnladdsup2Layout.setHorizontalGroup(
            pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnladdsup2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsup2Layout.createSequentialGroup()
                        .addComponent(jLabel92)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnsaveallS)
                        .addGap(83, 83, 83))
                    .addGroup(pnladdsup2Layout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsup2Layout.createSequentialGroup()
                                .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                    .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)
                                .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbAdditmid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtfieldAddunitpriceS)
                                    .addComponent(txtfieldASsid))
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsup2Layout.createSequentialGroup()
                                .addComponent(btnAddAdditmidS)
                                .addGap(18, 18, 18)
                                .addComponent(btnclearitmS)
                                .addGap(37, 37, 37))))))
        );
        pnladdsup2Layout.setVerticalGroup(
            pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsup2Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89)
                    .addComponent(txtfieldASsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(cmbAdditmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(txtfieldAddunitpriceS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnclearitmS)
                    .addComponent(btnAddAdditmidS))
                .addGroup(pnladdsup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnladdsup2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnsaveallS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnladdsup2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel92)
                        .addGap(6, 6, 6)))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlsupd3.setBackground(new java.awt.Color(228, 224, 225));
        pnlsupd3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));
        pnlsupd3.setPreferredSize(new java.awt.Dimension(447, 469));

        jPanel15.setBackground(new java.awt.Color(171, 136, 109));

        jLabel93.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setText("Item Details");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel94.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel94.setText("Item ID");

        txtfieldAitmidS.setEditable(false);
        txtfieldAitmidS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel95.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel95.setText("Item Name");

        txtfieldAnameS.setEditable(false);
        txtfieldAnameS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel96.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel96.setText("Description");

        txtfieldAdescS.setEditable(false);
        txtfieldAdescS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel97.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel97.setText("Quantity in Stock");

        txtfieldAQiSS.setEditable(false);
        txtfieldAQiSS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel98.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel98.setText("Reorder Level");

        txtfieldARLS.setEditable(false);
        txtfieldARLS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel99.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel99.setText("Retail Price (RM)");

        txtfieldARPS.setEditable(false);
        txtfieldARPS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout pnlsupd3Layout = new javax.swing.GroupLayout(pnlsupd3);
        pnlsupd3.setLayout(pnlsupd3Layout);
        pnlsupd3Layout.setHorizontalGroup(
            pnlsupd3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlsupd3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlsupd3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel96)
                    .addComponent(jLabel95)
                    .addComponent(jLabel94)
                    .addComponent(jLabel97)
                    .addComponent(txtfieldAitmidS)
                    .addComponent(txtfieldAnameS)
                    .addComponent(txtfieldAdescS)
                    .addComponent(txtfieldAQiSS)
                    .addComponent(txtfieldARLS)
                    .addGroup(pnlsupd3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(pnlsupd3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel98)
                            .addComponent(jLabel99)))
                    .addComponent(txtfieldARPS, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlsupd3Layout.setVerticalGroup(
            pnlsupd3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupd3Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel94)
                .addGap(6, 6, 6)
                .addComponent(txtfieldAitmidS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel95)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAnameS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel96)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAdescS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel97)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAQiSS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel98)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldARLS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel99)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldARPS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout pnlAsupLayout = new javax.swing.GroupLayout(pnlAsup);
        pnlAsup.setLayout(pnlAsupLayout);
        pnlAsupLayout.setHorizontalGroup(
            pnlAsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAsupLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnlsupd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnladdsup2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlsupd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlAsupLayout.setVerticalGroup(
            pnlAsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAsupLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlAsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlsupd3, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addComponent(pnladdsup2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addComponent(pnlsupd2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        pnlsup.addTab("Add", pnlAsup);

        pnlEsup.setBackground(new java.awt.Color(228, 224, 225));

        pnlsupd4.setBackground(new java.awt.Color(228, 224, 225));
        pnlsupd4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));
        pnlsupd4.setPreferredSize(new java.awt.Dimension(447, 469));

        jPanel16.setBackground(new java.awt.Color(171, 136, 109));

        jLabel100.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(255, 255, 255));
        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel100.setText("Enter New Supplier Details");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel101.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel101.setText("Supplier ID :");

        jLabel102.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel102.setText("Supplier Name");

        txtfieldEditSname.setEditable(false);
        txtfieldEditSname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel103.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel103.setText("Address");

        txtfieldEditaddressS.setEditable(false);
        txtfieldEditaddressS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel104.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel104.setText("Contact Number");

        txtfieldEditCNS.setEditable(false);
        txtfieldEditCNS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel105.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel105.setText("Supplier's Rating");

        txtfieldEditSRS.setEditable(false);
        txtfieldEditSRS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel106.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel106.setText("Bank Name");

        txtfieldEditBNS.setEditable(false);
        txtfieldEditBNS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel107.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel107.setText("Bank Account Number");

        txtfieldEditBANS.setEditable(false);
        txtfieldEditBANS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        btneditsup.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btneditsup.setText("Edit");
        btneditsup.setEnabled(false);
        btneditsup.setFocusPainted(false);
        btneditsup.setFocusable(false);
        btneditsup.setRequestFocusEnabled(false);
        btneditsup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditsupActionPerformed(evt);
            }
        });

        btndeletesup.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btndeletesup.setText("Delete");
        btndeletesup.setEnabled(false);
        btndeletesup.setFocusPainted(false);
        btndeletesup.setFocusable(false);
        btndeletesup.setRequestFocusEnabled(false);
        btndeletesup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeletesupActionPerformed(evt);
            }
        });

        btncancelsup.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btncancelsup.setText("Cancel");
        btncancelsup.setEnabled(false);
        btncancelsup.setFocusPainted(false);
        btncancelsup.setFocusable(false);
        btncancelsup.setRequestFocusEnabled(false);
        btncancelsup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelsupActionPerformed(evt);
            }
        });

        cmbEsid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbEsid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbEsid.setFocusable(false);
        cmbEsid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEsidActionPerformed(evt);
            }
        });

        btncleareditsup.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btncleareditsup.setText("Clear");
        btncleareditsup.setFocusPainted(false);
        btncleareditsup.setFocusable(false);
        btncleareditsup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncleareditsupActionPerformed(evt);
            }
        });

        btnsavesup.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnsavesup.setText("Save");
        btnsavesup.setEnabled(false);
        btnsavesup.setFocusPainted(false);
        btnsavesup.setFocusable(false);
        btnsavesup.setRequestFocusEnabled(false);
        btnsavesup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsavesupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlsupd4Layout = new javax.swing.GroupLayout(pnlsupd4);
        pnlsupd4.setLayout(pnlsupd4Layout);
        pnlsupd4Layout.setHorizontalGroup(
            pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlsupd4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlsupd4Layout.createSequentialGroup()
                        .addComponent(btneditsup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndeletesup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnsavesup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncancelsup))
                    .addComponent(jLabel105)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupd4Layout.createSequentialGroup()
                        .addGroup(pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel106)
                            .addComponent(txtfieldEditBNS, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlsupd4Layout.createSequentialGroup()
                                .addComponent(jLabel107)
                                .addGap(0, 32, Short.MAX_VALUE))
                            .addComponent(txtfieldEditBANS)))
                    .addComponent(jLabel103)
                    .addComponent(jLabel102)
                    .addComponent(txtfieldEditaddressS)
                    .addComponent(jLabel104)
                    .addComponent(txtfieldEditSname)
                    .addComponent(txtfieldEditCNS)
                    .addComponent(txtfieldEditSRS)
                    .addGroup(pnlsupd4Layout.createSequentialGroup()
                        .addComponent(jLabel101)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEsid, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncleareditsup)))
                .addGap(29, 29, 29))
        );
        pnlsupd4Layout.setVerticalGroup(
            pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupd4Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbEsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btncleareditsup))
                    .addComponent(jLabel101))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel102)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEditSname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel103)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEditaddressS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel104)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEditCNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlsupd4Layout.createSequentialGroup()
                        .addComponent(jLabel105)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldEditSRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel106)
                            .addComponent(jLabel107))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldEditBNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfieldEditBANS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlsupd4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btndeletesup)
                    .addComponent(btneditsup)
                    .addComponent(btncancelsup)
                    .addComponent(btnsavesup))
                .addGap(14, 14, 14))
        );

        tblEsup.setBackground(new java.awt.Color(214,192,179));
        tblEsup.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblEsup.setForeground(new java.awt.Color(73, 54, 40));
        tblEsup.getTableHeader().setOpaque(false);
        tblEsup.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblEsup.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblEsup.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblEsup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Supplier Name", "Address", "Contact Number", "Bank Name", "Account Number", "Rating"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEsup.getTableHeader().setReorderingAllowed(false);
        tblEsupscrollpanel.setViewportView(tblEsup);
        if (tblEsup.getColumnModel().getColumnCount() > 0) {
            tblEsup.getColumnModel().getColumn(0).setMinWidth(80);
            tblEsup.getColumnModel().getColumn(0).setMaxWidth(80);
            tblEsup.getColumnModel().getColumn(1).setMinWidth(130);
            tblEsup.getColumnModel().getColumn(1).setMaxWidth(130);
            tblEsup.getColumnModel().getColumn(2).setResizable(false);
            tblEsup.getColumnModel().getColumn(3).setMinWidth(120);
            tblEsup.getColumnModel().getColumn(3).setMaxWidth(120);
            tblEsup.getColumnModel().getColumn(4).setMinWidth(110);
            tblEsup.getColumnModel().getColumn(4).setMaxWidth(110);
            tblEsup.getColumnModel().getColumn(5).setMinWidth(120);
            tblEsup.getColumnModel().getColumn(5).setMaxWidth(120);
            tblEsup.getColumnModel().getColumn(6).setMinWidth(60);
            tblEsup.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        javax.swing.GroupLayout pnlEsupLayout = new javax.swing.GroupLayout(pnlEsup);
        pnlEsup.setLayout(pnlEsupLayout);
        pnlEsupLayout.setHorizontalGroup(
            pnlEsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEsupLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnlsupd4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tblEsupscrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        pnlEsupLayout.setVerticalGroup(
            pnlEsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEsupLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlEsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tblEsupscrollpanel)
                    .addComponent(pnlsupd4, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        pnlsup.addTab("Edit", pnlEsup);

        javax.swing.GroupLayout pnlSuppliersLayout = new javax.swing.GroupLayout(pnlSuppliers);
        pnlSuppliers.setLayout(pnlSuppliersLayout);
        pnlSuppliersLayout.setHorizontalGroup(
            pnlSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlsup)
        );
        pnlSuppliersLayout.setVerticalGroup(
            pnlSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlsup, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        tabpnlIM.addTab("Suppliers", pnlSuppliers);

        pnlInventory.setBackground(new java.awt.Color(228, 224, 225));

        pnlUI.setBackground(new java.awt.Color(228, 224, 225));
        pnlUI.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        pnlUI.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        pnlUI.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pnlUIStateChanged(evt);
            }
        });

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
        if (tblinventory.getColumnModel().getColumnCount() > 0) {
            tblinventory.getColumnModel().getColumn(0).setMinWidth(80);
            tblinventory.getColumnModel().getColumn(0).setPreferredWidth(80);
            tblinventory.getColumnModel().getColumn(0).setMaxWidth(80);
            tblinventory.getColumnModel().getColumn(1).setMinWidth(170);
            tblinventory.getColumnModel().getColumn(1).setPreferredWidth(170);
            tblinventory.getColumnModel().getColumn(1).setMaxWidth(170);
            tblinventory.getColumnModel().getColumn(3).setMinWidth(150);
            tblinventory.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblinventory.getColumnModel().getColumn(3).setMaxWidth(150);
            tblinventory.getColumnModel().getColumn(4).setMinWidth(140);
            tblinventory.getColumnModel().getColumn(4).setPreferredWidth(140);
            tblinventory.getColumnModel().getColumn(4).setMaxWidth(140);
            tblinventory.getColumnModel().getColumn(4).setHeaderValue("Reorder Level");
        }

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
        jScrollPane1.setViewportView(tbliup);
        if (tbliup.getColumnModel().getColumnCount() > 0) {
            tbliup.getColumnModel().getColumn(0).setResizable(false);
            tbliup.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel1.setText("Item ID");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel3.setText("Item Name");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel4.setText("Description");

        txtfielditmid.setEditable(false);
        txtfielditmid.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        txtfieldname.setEditable(false);
        txtfieldname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        txtfielddesc.setEditable(false);
        txtfielddesc.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel5.setText("Quantity in Stock");

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel6.setText("Reorder Level");

        txtfieldQiS.setEditable(false);
        txtfieldQiS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        txtfieldRL.setEditable(false);
        txtfieldRL.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel7.setText("Supplied by :");

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnladddetailsLayout.createSequentialGroup()
                .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnladddetailsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
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
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1)
                                .addComponent(txtfielditmid)
                                .addComponent(txtfieldname)
                                .addComponent(txtfielddesc)
                                .addGroup(pnladddetailsLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(3, 3, 3)
                                    .addComponent(lblstatusstar, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40)
                                    .addComponent(jLabel6))
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
                    .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(txtfielditmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfielddesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(lblstatusstar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnladddetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfieldQiS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfieldRL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblitmstatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlVILayout = new javax.swing.GroupLayout(pnlVI);
        pnlVI.setLayout(pnlVILayout);
        pnlVILayout.setHorizontalGroup(
            pnlVILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVILayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(tblscrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnladddetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlVILayout.setVerticalGroup(
            pnlVILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVILayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlVILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tblscrollpanel)
                    .addComponent(pnladddetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        pnlUI.addTab("View", pnlVI);

        pnlAI.setBackground(new java.awt.Color(228, 224, 225));

        pnlitmd.setBackground(new java.awt.Color(228, 224, 225));
        pnlitmd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        jPanel4.setBackground(new java.awt.Color(171, 136, 109));

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Item Details");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel23.setText("Item ID");

        txtfieldAitmid.setEditable(false);
        txtfieldAitmid.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel24.setText("Item Name");

        txtfieldAname.setEditable(false);
        txtfieldAname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel27.setText("Description");

        txtfieldAdesc.setEditable(false);
        txtfieldAdesc.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel28.setText("Quantity in Stock");

        txtfieldAQiS.setEditable(false);
        txtfieldAQiS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel29.setText("Reorder Level");

        txtfieldARL.setEditable(false);
        txtfieldARL.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel30.setText("Retail Price (RM)");

        txtfieldARP.setEditable(false);
        txtfieldARP.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout pnlitmdLayout = new javax.swing.GroupLayout(pnlitmd);
        pnlitmd.setLayout(pnlitmdLayout);
        pnlitmdLayout.setHorizontalGroup(
            pnlitmdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlitmdLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlitmdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel27)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(jLabel28)
                    .addComponent(txtfieldAitmid)
                    .addComponent(txtfieldAname)
                    .addComponent(txtfieldAdesc)
                    .addComponent(txtfieldAQiS)
                    .addComponent(txtfieldARL)
                    .addGroup(pnlitmdLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(pnlitmdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30)))
                    .addComponent(txtfieldARP, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlitmdLayout.setVerticalGroup(
            pnlitmdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlitmdLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel23)
                .addGap(6, 6, 6)
                .addComponent(txtfieldAitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAQiS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldARL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldARP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pnladdsup.setBackground(new java.awt.Color(228, 224, 225));
        pnladdsup.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));
        pnladdsup.setPreferredSize(new java.awt.Dimension(244, 481));

        jPanel5.setBackground(new java.awt.Color(171, 136, 109));

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Add Supplier for Item ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        cmbAitmid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbAitmid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbAitmid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAitmidActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel19.setText("Item ID             :");

        cmbAsid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbAsid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbAsid.setEnabled(false);
        cmbAsid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAsidActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel20.setText("Supplier ID       :");

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel21.setText("Unit Price (RM) :");

        txtfieldAunitprice.setEditable(false);
        txtfieldAunitprice.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        btnAadd.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnAadd.setText("Add");
        btnAadd.setEnabled(false);
        btnAadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAaddActionPerformed(evt);
            }
        });

        btnAclear.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnAclear.setText("Clear");
        btnAclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAclearActionPerformed(evt);
            }
        });

        tblAiup.setBackground(new java.awt.Color(214,192,179));
        tblAiup.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblAiup.setForeground(new java.awt.Color(73, 54, 40));
        tblAiup.getTableHeader().setOpaque(false);
        tblAiup.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblAiup.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblAiup.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblAiup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Unit Price (RM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAiup.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblAiup);
        if (tblAiup.getColumnModel().getColumnCount() > 0) {
            tblAiup.getColumnModel().getColumn(0).setResizable(false);
            tblAiup.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel22.setText("Existing Suppliers :");

        javax.swing.GroupLayout pnladdsupLayout = new javax.swing.GroupLayout(pnladdsup);
        pnladdsup.setLayout(pnladdsupLayout);
        pnladdsupLayout.setHorizontalGroup(
            pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnladdsupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsupLayout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsupLayout.createSequentialGroup()
                                .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)
                                .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbAitmid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbAsid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtfieldAunitprice))
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsupLayout.createSequentialGroup()
                                .addComponent(btnAadd)
                                .addGap(18, 18, 18)
                                .addComponent(btnAclear)
                                .addGap(37, 37, 37))))
                    .addGroup(pnladdsupLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnladdsupLayout.setVerticalGroup(
            pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnladdsupLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cmbAitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cmbAsid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtfieldAunitprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnladdsupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAclear)
                    .addComponent(btnAadd))
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        pnlsupd.setBackground(new java.awt.Color(228, 224, 225));
        pnlsupd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));
        pnlsupd.setPreferredSize(new java.awt.Dimension(447, 469));

        jPanel6.setBackground(new java.awt.Color(171, 136, 109));

        jLabel37.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Supplier Details");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel31.setText("Supplier ID");

        txtfieldASID.setEditable(false);
        txtfieldASID.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel32.setText("Supplier Name");

        txtfieldASname.setEditable(false);
        txtfieldASname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel33.setText("Address");

        txtfieldAaddress.setEditable(false);
        txtfieldAaddress.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel34.setText("Contact Number");

        txtfieldACN.setEditable(false);
        txtfieldACN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel35.setText("Supplier's Rating");

        txtfieldASR.setEditable(false);
        txtfieldASR.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel36.setText("Bank Name");

        txtfieldABN.setEditable(false);
        txtfieldABN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel38.setText("Bank Account Number");

        txtfieldABAN.setEditable(false);
        txtfieldABAN.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout pnlsupdLayout = new javax.swing.GroupLayout(pnlsupd);
        pnlsupd.setLayout(pnlsupdLayout);
        pnlsupdLayout.setHorizontalGroup(
            pnlsupdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlsupdLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlsupdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel35)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupdLayout.createSequentialGroup()
                        .addGroup(pnlsupdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(txtfieldABN, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlsupdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldABAN)
                            .addGroup(pnlsupdLayout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(0, 32, Short.MAX_VALUE))))
                    .addComponent(jLabel33)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(txtfieldAaddress)
                    .addComponent(jLabel34)
                    .addComponent(txtfieldASname)
                    .addComponent(txtfieldACN)
                    .addComponent(txtfieldASR)
                    .addComponent(txtfieldASID))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        pnlsupdLayout.setVerticalGroup(
            pnlsupdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsupdLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel31)
                .addGap(6, 6, 6)
                .addComponent(txtfieldASID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldASname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldAaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldACN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlsupdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlsupdLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldASR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlsupdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfieldABN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfieldABAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlAILayout = new javax.swing.GroupLayout(pnlAI);
        pnlAI.setLayout(pnlAILayout);
        pnlAILayout.setHorizontalGroup(
            pnlAILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAILayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnladdsup, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlitmd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnlsupd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlAILayout.setVerticalGroup(
            pnlAILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAILayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlAILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlitmd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnladdsup, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addComponent(pnlsupd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        pnlUI.addTab("Add", pnlAI);

        pnlEI.setBackground(new java.awt.Color(228, 224, 225));

        pnleditQIS.setBackground(new java.awt.Color(228, 224, 225));
        pnleditQIS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        btnsave.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnsave.setText("Save");
        btnsave.setFocusPainted(false);
        btnsave.setFocusable(false);
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btncancel.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btncancel.setText("Cancel");
        btncancel.setFocusPainted(false);
        btncancel.setFocusable(false);
        btncancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelActionPerformed(evt);
            }
        });

        txtfieldERL.setEditable(false);
        txtfieldERL.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        lblEitmstatus.setFont(new java.awt.Font("Yu Gothic UI", 2, 14)); // NOI18N
        lblEitmstatus.setForeground(new java.awt.Color(228, 224, 225));
        lblEitmstatus.setText("*Item needs to be restocked as soon as possible!");

        btnedit.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnedit.setText("Edit");
        btnedit.setFocusPainted(false);
        btnedit.setFocusable(false);
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        txtfieldEQiS.setEditable(false);
        txtfieldEQiS.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        lblEstatusstar.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        lblEstatusstar.setForeground(new java.awt.Color(228, 224, 225));
        lblEstatusstar.setText("*");

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel13.setText("Reorder Level");

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel12.setText("Quantity in Stock");

        txtfieldEdesc.setEditable(false);
        txtfieldEdesc.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel11.setText("Description");

        txtfieldEname.setEditable(false);
        txtfieldEname.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel10.setText("Item Name");

        txtfieldEitmid.setEditable(false);
        txtfieldEitmid.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel9.setText("Item ID");

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel8.setText("Item ID :");

        cmbEitmid.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbEitmid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbEitmid.setFocusable(false);
        cmbEitmid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEitmidActionPerformed(evt);
            }
        });

        btnEclear.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnEclear.setText("Clear");
        btnEclear.setFocusPainted(false);
        btnEclear.setFocusable(false);
        btnEclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEclearActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(171, 136, 109));

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Edit Quantity in Stock & Reorder Level");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnleditQISLayout = new javax.swing.GroupLayout(pnleditQIS);
        pnleditQIS.setLayout(pnleditQISLayout);
        pnleditQISLayout.setHorizontalGroup(
            pnleditQISLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnleditQISLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnleditQISLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnleditQISLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(pnleditQISLayout.createSequentialGroup()
                            .addComponent(btnedit)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                            .addComponent(btnsave)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btncancel))
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtfieldEdesc, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnleditQISLayout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addGap(3, 3, 3)
                            .addComponent(lblEstatusstar, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)
                            .addComponent(jLabel13))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnleditQISLayout.createSequentialGroup()
                            .addComponent(txtfieldEQiS, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtfieldERL))
                        .addComponent(lblEitmstatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtfieldEitmid, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtfieldEname, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(pnleditQISLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbEitmid, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEclear)))
                .addGap(19, 19, 19))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnleditQISLayout.setVerticalGroup(
            pnleditQISLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditQISLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(pnleditQISLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbEitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEclear))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(6, 6, 6)
                .addComponent(txtfieldEitmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnleditQISLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(lblEstatusstar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnleditQISLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfieldEQiS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfieldERL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEitmstatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnleditQISLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnedit)
                    .addComponent(btnsave)
                    .addComponent(btncancel))
                .addGap(46, 46, 46))
        );

        pnleditSID.setBackground(new java.awt.Color(228, 224, 225));
        pnleditSID.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        tblsid.setAutoCreateRowSorter(true);
        tblsid.setBackground(new java.awt.Color(214,192,179));
        tblsid.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblsid.setForeground(new java.awt.Color(73, 54, 40));
        tblsid.getTableHeader().setOpaque(false);
        tblsid.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblsid.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblsid.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblsid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Unit Price (RM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblsid.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblsid);
        if (tblsid.getColumnModel().getColumnCount() > 0) {
            tblsid.getColumnModel().getColumn(0).setResizable(false);
            tblsid.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel14.setText("Supplier ID :");

        cmbSID.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbSID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbSID.setFocusable(false);
        cmbSID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSIDActionPerformed(evt);
            }
        });

        btnclearSID.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnclearSID.setText("Clear");
        btnclearSID.setFocusPainted(false);
        btnclearSID.setFocusable(false);
        btnclearSID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearSIDActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel15.setText("Supplier ID");

        txtfieldEsupplierid.setEditable(false);
        txtfieldEsupplierid.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel16.setText("Unit Price (RM)");

        txtfieldEunitprice.setEditable(false);
        txtfieldEunitprice.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        btneditsip.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btneditsip.setText("Edit");
        btneditsip.setFocusPainted(false);
        btneditsip.setFocusable(false);
        btneditsip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditsipActionPerformed(evt);
            }
        });

        btnsavesip.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnsavesip.setText("Save");
        btnsavesip.setFocusPainted(false);
        btnsavesip.setFocusable(false);
        btnsavesip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsavesipActionPerformed(evt);
            }
        });

        btncancelsip.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btncancelsip.setText("Cancel");
        btncancelsip.setFocusPainted(false);
        btncancelsip.setFocusable(false);
        btncancelsip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelsipActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(171, 136, 109));

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Edit Item Unit Price of Supplier");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        btndltsip.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btndltsip.setText("Delete");
        btndltsip.setFocusPainted(false);
        btndltsip.setFocusable(false);
        btndltsip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndltsipActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnleditSIDLayout = new javax.swing.GroupLayout(pnleditSID);
        pnleditSID.setLayout(pnleditSIDLayout);
        pnleditSIDLayout.setHorizontalGroup(
            pnleditSIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnleditSIDLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnleditSIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditSIDLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSID, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnclearSID)
                        .addGap(0, 73, Short.MAX_VALUE))
                    .addGroup(pnleditSIDLayout.createSequentialGroup()
                        .addGroup(pnleditSIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(71, 240, Short.MAX_VALUE))
                    .addGroup(pnleditSIDLayout.createSequentialGroup()
                        .addGroup(pnleditSIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldEunitprice)
                            .addComponent(txtfieldEsupplierid, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditSIDLayout.createSequentialGroup()
                                .addComponent(btneditsip)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btndltsip)
                                .addGap(18, 39, Short.MAX_VALUE)
                                .addComponent(btnsavesip)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncancelsip)))
                        .addGap(18, 18, 18))))
        );
        pnleditSIDLayout.setVerticalGroup(
            pnleditSIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditSIDLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(pnleditSIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cmbSID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnclearSID))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(6, 6, 6)
                .addComponent(txtfieldEsupplierid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldEunitprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnleditSIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btneditsip)
                    .addComponent(btnsavesip)
                    .addComponent(btncancelsip)
                    .addComponent(btndltsip))
                .addGap(43, 43, 43))
        );

        tblinv.setBackground(new java.awt.Color(214,192,179));
        tblinv.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblinv.setForeground(new java.awt.Color(73, 54, 40));
        tblinv.getTableHeader().setOpaque(false);
        tblinv.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblinv.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblinv.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblinv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Quantity in Stock", "Reorder Level"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblinv.getTableHeader().setReorderingAllowed(false);
        tblinvscrollpanel.setViewportView(tblinv);
        if (tblinv.getColumnModel().getColumnCount() > 0) {
            tblinv.getColumnModel().getColumn(0).setMinWidth(80);
            tblinv.getColumnModel().getColumn(0).setMaxWidth(80);
            tblinv.getColumnModel().getColumn(1).setMinWidth(120);
            tblinv.getColumnModel().getColumn(1).setMaxWidth(120);
            tblinv.getColumnModel().getColumn(2).setResizable(false);
            tblinv.getColumnModel().getColumn(2).setHeaderValue("Reorder Level");
        }

        javax.swing.GroupLayout pnlEILayout = new javax.swing.GroupLayout(pnlEI);
        pnlEI.setLayout(pnlEILayout);
        pnlEILayout.setHorizontalGroup(
            pnlEILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEILayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(tblinvscrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnleditQIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnleditSID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        pnlEILayout.setVerticalGroup(
            pnlEILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEILayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlEILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnleditQIS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tblinvscrollpanel)
                    .addComponent(pnleditSID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        pnlUI.addTab("Edit", pnlEI);

        javax.swing.GroupLayout pnlInventoryLayout = new javax.swing.GroupLayout(pnlInventory);
        pnlInventory.setLayout(pnlInventoryLayout);
        pnlInventoryLayout.setHorizontalGroup(
            pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUI, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlInventoryLayout.setVerticalGroup(
            pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlUI)
        );

        tabpnlIM.addTab("Inventory", pnlInventory);

        pnlStock.setBackground(new java.awt.Color(228, 224, 225));

        tblStock.setBackground(new java.awt.Color(214,192,179));
        tblStock.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        tblStock.setForeground(new java.awt.Color(73, 54, 40));
        tblStock.getTableHeader().setOpaque(false);
        tblStock.getTableHeader().setBackground(Color.decode("#AB886D"));
        tblStock.getTableHeader().setForeground(Color.decode("#E4E0E1"));
        tblStock.getTableHeader().setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        tblStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO ID", "PR ID", "Item ID", "Item Name", "Quantity", "Status", "Payment Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStock.getTableHeader().setReorderingAllowed(false);
        tblStockscrollpanel.setViewportView(tblStock);
        if (tblStock.getColumnModel().getColumnCount() > 0) {
            tblStock.getColumnModel().getColumn(0).setMinWidth(80);
            tblStock.getColumnModel().getColumn(0).setMaxWidth(80);
            tblStock.getColumnModel().getColumn(1).setMinWidth(80);
            tblStock.getColumnModel().getColumn(1).setMaxWidth(80);
            tblStock.getColumnModel().getColumn(2).setMinWidth(80);
            tblStock.getColumnModel().getColumn(2).setMaxWidth(80);
            tblStock.getColumnModel().getColumn(3).setResizable(false);
        }

        pnleditQIS2.setBackground(new java.awt.Color(228, 224, 225));
        pnleditQIS2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(171, 136, 109), 4, true));

        txtfieldPSstock.setEditable(false);
        txtfieldPSstock.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        txtfieldstatusstock.setEditable(false);
        txtfieldstatusstock.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel108.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel108.setText("Payment Status");

        jLabel109.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel109.setText("Status");

        txtfieldquantitystock.setEditable(false);
        txtfieldquantitystock.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel110.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel110.setText("Quantity");

        txtfielditmnamestock.setEditable(false);
        txtfielditmnamestock.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel111.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel111.setText("Item Name");

        txtfieldpoidstock.setEditable(false);
        txtfieldpoidstock.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel112.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel112.setText("PO ID");

        jLabel113.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        jLabel113.setText("PO ID :");

        cmbstock.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        cmbstock.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select..." }));
        cmbstock.setFocusable(false);
        cmbstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbstockActionPerformed(evt);
            }
        });

        btnclearstock.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnclearstock.setText("Clear");
        btnclearstock.setFocusPainted(false);
        btnclearstock.setFocusable(false);
        btnclearstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearstockActionPerformed(evt);
            }
        });

        jPanel17.setBackground(new java.awt.Color(171, 136, 109));

        jLabel114.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel114.setText("Update Stock Level");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtfieldpridstock.setEditable(false);
        txtfieldpridstock.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel115.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel115.setText("PR ID");

        txtfielditmidstock.setEditable(false);
        txtfielditmidstock.setFont(new java.awt.Font("Yu Gothic UI", 0, 13)); // NOI18N

        jLabel116.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel116.setText("Item ID");

        btnupdatestock.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        btnupdatestock.setText("Update");
        btnupdatestock.setFocusPainted(false);
        btnupdatestock.setFocusable(false);
        btnupdatestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdatestockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnleditQIS2Layout = new javax.swing.GroupLayout(pnleditQIS2);
        pnleditQIS2.setLayout(pnleditQIS2Layout);
        pnleditQIS2Layout.setHorizontalGroup(
            pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnleditQIS2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditQIS2Layout.createSequentialGroup()
                        .addGroup(pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtfieldPSstock, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldstatusstock, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfieldquantitystock, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfielditmnamestock, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnleditQIS2Layout.createSequentialGroup()
                                .addGroup(pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel112)
                                    .addComponent(txtfieldpoidstock, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel115)
                                    .addComponent(txtfieldpridstock, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel116)
                                    .addComponent(txtfielditmidstock, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(19, 19, 19))
                    .addGroup(pnleditQIS2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel108))
                    .addComponent(jLabel110)
                    .addComponent(jLabel109)
                    .addComponent(jLabel111)
                    .addGroup(pnleditQIS2Layout.createSequentialGroup()
                        .addComponent(jLabel113)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbstock, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnclearstock))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditQIS2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnupdatestock)
                .addGap(139, 139, 139))
        );
        pnleditQIS2Layout.setVerticalGroup(
            pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditQIS2Layout.createSequentialGroup()
                .addGroup(pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnleditQIS2Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel113)
                            .addComponent(cmbstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnclearstock))
                        .addGroup(pnleditQIS2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnleditQIS2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel112)
                                .addGap(6, 6, 6)
                                .addComponent(txtfieldpoidstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnleditQIS2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel116)
                                .addGap(6, 6, 6)
                                .addComponent(txtfielditmidstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnleditQIS2Layout.createSequentialGroup()
                        .addComponent(jLabel115)
                        .addGap(6, 6, 6)
                        .addComponent(txtfieldpridstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel111)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfielditmnamestock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel110)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldquantitystock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel109)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldstatusstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel108)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfieldPSstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnupdatestock)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlStockLayout = new javax.swing.GroupLayout(pnlStock);
        pnlStock.setLayout(pnlStockLayout);
        pnlStockLayout.setHorizontalGroup(
            pnlStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStockLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(tblStockscrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnleditQIS2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlStockLayout.setVerticalGroup(
            pnlStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStockLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnleditQIS2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tblStockscrollpanel))
                .addGap(18, 18, 18))
        );

        tabpnlIM.addTab("Update Stock", pnlStock);

        pnlProfile.setBackground(new java.awt.Color(228, 224, 225));

        jPanel18.setBackground(new java.awt.Color(214, 192, 179));
        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel117.setFont(new java.awt.Font("Yu Gothic UI", 0, 36)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(73, 54, 40));
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Profile");

        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/profile-modified (1).png"))); // NOI18N

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

        jLabel119.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(73, 54, 40));
        jLabel119.setText("First Name");

        jLabel120.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(73, 54, 40));
        jLabel120.setText("Last Name");

        jLabel121.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(73, 54, 40));
        jLabel121.setText("Gender");

        jLabel122.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(73, 54, 40));
        jLabel122.setText("Contact No.");

        jLabel123.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(73, 54, 40));
        jLabel123.setText("Email");

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

        ProfileEmail.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ProfileEmail.setForeground(new java.awt.Color(102, 102, 102));
        ProfileEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProfileEmail.setText("Email");

        ProfileGender.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ProfileGender.setForeground(new java.awt.Color(102, 102, 102));
        ProfileGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProfileGender.setText("Gender");

        ProfileContact.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ProfileContact.setForeground(new java.awt.Color(102, 102, 102));
        ProfileContact.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProfileContact.setText("Contact No.");

        jLabel124.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(171, 136, 109));
        jLabel124.setText("*Contact the administrator to change personal details");

        jLabel125.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(73, 54, 40));
        jLabel125.setText("Change Password");

        jLabel126.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(73, 54, 40));
        jLabel126.setText("Current Password");

        jLabel127.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(73, 54, 40));
        jLabel127.setText("New Password");

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

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ProfileID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel118))
                                        .addGap(92, 92, 92))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(103, 103, 103)))))
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(ProfileUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel119)
                            .addComponent(jLabel120)
                            .addComponent(jLabel121)
                            .addComponent(jLabel123)
                            .addComponent(jLabel122))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ProfileLN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileFN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProfileContact, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161)))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                            .addComponent(ShowHidePass, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(ProfileNewPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ProfileCurrentPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ProfileChgPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ChgPassError, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel117)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel119)
                            .addComponent(ProfileFN, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel120)
                            .addComponent(ProfileLN, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ProfileGender, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel121))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel123)
                            .addComponent(ProfileEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ProfileContact, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel122))
                        .addGap(34, 34, 34)
                        .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(ProfileUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel118)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ProfileID, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addGap(108, 108, 108)
                                        .addComponent(ChgPassError, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ProfileChgPass, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(20, 20, 20)))
                .addGap(15, 15, 15))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel126)
                        .addGap(18, 18, 18)
                        .addComponent(ProfileCurrentPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel127)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(ShowHidePass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProfileNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlProfileLayout = new javax.swing.GroupLayout(pnlProfile);
        pnlProfile.setLayout(pnlProfileLayout);
        pnlProfileLayout.setHorizontalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pnlProfileLayout.setVerticalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        tabpnlIM.addTab("Profile", pnlProfile);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(pnlTopPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(PnlSMDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabpnlIM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(pnlTopPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(PnlSMDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabpnlIM, javax.swing.GroupLayout.PREFERRED_SIZE, 549, Short.MAX_VALUE))
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

    private void tblinventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblinventoryMouseClicked
        int i = tblinventory.getSelectedRow();
        System.out.println(i);
        System.out.println(tblinventory.getValueAt(i, 0));
        cmbitmid.setSelectedIndex(i+1);
        DisplayInventoryDetails(i);
        checkQiS(i);

    }//GEN-LAST:event_tblinventoryMouseClicked

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        clearInventoryView();
    }//GEN-LAST:event_btnclearActionPerformed

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

    private void cmbEitmidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEitmidActionPerformed
        int i = cmbEitmid.getSelectedIndex();
        if(i == -1){}
        else if(i!=0)
        {
            i =i -1;
            DisplayInventoryDetailsEdit(i);
            checkQiSEdit(i);
            LoadIUP(IUPList, (String)tblinv.getValueAt(i,0),IUPTableEdit);
            cmbSID.removeAllItems();
            cmbSID.addItem("Select...");
            LoadcmbSIDEdit((String)tblinv.getValueAt(i,0));
            ShowHide("showedit");
            tblinv.setRowSelectionInterval(i, i);
            tblinv.scrollRectToVisible(new Rectangle(tblinv.getCellRect(i, 0, true)));
        }else{clearInventoryEdit();}
    }//GEN-LAST:event_cmbEitmidActionPerformed

    private void cmbSIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSIDActionPerformed
        int i = cmbSID.getSelectedIndex();
        if(i == 0){clearSID();}
        else if (i == -1){}
        else
        {
            DisplayItemUnitPriceEdit(i);
            ShowHide2("showedit");
        }
    }//GEN-LAST:event_cmbSIDActionPerformed

    private void btnEclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEclearActionPerformed
        clearInventoryEdit();
    }//GEN-LAST:event_btnEclearActionPerformed

    private void btnclearSIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearSIDActionPerformed
        clearSID();
    }//GEN-LAST:event_btnclearSIDActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        ShowHide("edit");
    }//GEN-LAST:event_btneditActionPerformed

    private void btncancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelActionPerformed
        int i = cmbEitmid.getSelectedIndex()-1;
        DisplayInventoryDetailsEdit(i);
        ShowHide("cancel");
    }//GEN-LAST:event_btncancelActionPerformed

    private void btneditsipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditsipActionPerformed
        ShowHide2("edit");
    }//GEN-LAST:event_btneditsipActionPerformed

    private void btncancelsipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelsipActionPerformed
        ShowHide2("cancel");
    }//GEN-LAST:event_btncancelsipActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        String ItmId = txtfieldEitmid.getText();
        int i = cmbEitmid.getSelectedIndex()-1;
        try{
            int QiS = Integer.parseInt(txtfieldEQiS.getText());
            int RL = Integer.parseInt(txtfieldERL.getText());
            if(QiS<0 || RL<0)
            {
                JOptionPane.showMessageDialog(null, "Please only input positive integer!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    Object Var[]= {ItmId,"-","-",QiS,RL, -2.0};
                    items.editRecord(ItemList, Var);
                    rereadItemFile();
                    JOptionPane.showMessageDialog(null, "Quantity in Stock and Reorder Level Updated!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
                    refreshInventoryTable();
                    DisplayInventoryDetailsEdit(i);
                    checkQiSEdit(i);
                    tblinv.setRowSelectionInterval(i, i);
                    tblinv.scrollRectToVisible(new Rectangle(tblinv.getCellRect(i, 0, true)));
                    ShowHide("cancel");
                }
            }
            // update array list and change text file
        }catch(NumberFormatException e){
           JOptionPane.showMessageDialog(null, "Please only input integer!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnsavesipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsavesipActionPerformed
        int i = cmbSID.getSelectedIndex();
        String itmid = txtfieldEitmid.getText();
        String supid = txtfieldEsupplierid.getText();
        try
        {
            Double UnitPrice = Double.valueOf(txtfieldEunitprice.getText());
            if(UnitPrice<=0)
            {
               JOptionPane.showMessageDialog(null, "Please only input positive number!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    Object Var[] = {itmid, supid, UnitPrice};
                    iups.editRecord(IUPList, Var);
                    rereadItemUnitPriceFile();
                    JOptionPane.showMessageDialog(null, "Item Unit Price for "+supid+" updated!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
                    refreshItemUnitPriceEdit(i,itmid);
                    ShowHide2("cancel");
                }
            }
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please only input numeric value!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnsavesipActionPerformed

    private void btndltsipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndltsipActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0)
        {
            Object Var[] = {txtfieldEsupplierid.getText(),txtfieldEitmid.getText()};
            iups.deleteRecord(IUPList, Var);
            rereadItemUnitPriceFile();
            refreshItemUnitPriceEdit(0,txtfieldEitmid.getText());
            cmbSID.removeAllItems();
            cmbSID.addItem("Select...");
            LoadcmbSIDEdit(txtfieldEitmid.getText());
            cmbSID.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, "Supplier, "+ Var[0]+" has been removed for Item, "+Var[1]+".", "Successfull", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btndltsipActionPerformed

    private void pnlUIStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pnlUIStateChanged
        pnlchangeInventory();
    }//GEN-LAST:event_pnlUIStateChanged

    private void btnAclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAclearActionPerformed
        clearInventoryAdd();
    }//GEN-LAST:event_btnAclearActionPerformed

    private void btnAaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAaddActionPerformed
        String up = txtfieldAunitprice.getText();
        String sid = (String)cmbAsid.getSelectedItem();
        String itmid =  (String)cmbAitmid.getSelectedItem();
        try
        {
            Double unitprice = Double.parseDouble(up);
            if(unitprice<=0)
            {
               JOptionPane.showMessageDialog(null, "Please only input positive number!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    IUPList.add(new ItemUnitPrice(sid, itmid, unitprice));
                    rereadItemUnitPriceFile();
                    cmbAsid.removeAllItems();
                    cmbAsid.addItem("Select...");
                    LoadcmbSupplierID(itmid, cmbAsid);
                    LoadIUP(IUPList,itmid,IUPTableAdd);
                    JOptionPane.showMessageDialog(null, "Supplier, "+sid+" added for Item, "+itmid, "Successfull", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please only input numeric value!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAaddActionPerformed

    private void cmbAsidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAsidActionPerformed
        int i  = cmbAsid.getSelectedIndex();
        String sid = (String)cmbAsid.getSelectedItem();
        switch (i) {
            case -1 -> {
            }
            case 0 -> {
                txtfieldAunitprice.setEditable(false);
                txtfieldAunitprice.setText("");
                txtfieldASID.setText("");
                txtfieldASname.setText("");
                txtfieldAaddress.setText("");
                txtfieldACN.setText("");
                txtfieldASR.setText("");
                txtfieldABN.setText("");
                txtfieldABAN.setText("");
                btnAadd.setEnabled(false);
            }
            default -> {
                txtfieldAunitprice.setText("");
                txtfieldAunitprice.setEditable(true);
                btnAadd.setEnabled(true);
                DisplaySupplierAdd(sid);
            }
        }
    }//GEN-LAST:event_cmbAsidActionPerformed

    private void cmbAitmidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAitmidActionPerformed
        switch (cmbAitmid.getSelectedIndex())
        {
            case -1 -> {}
            case 0 -> clearInventoryAdd();
            default -> {
                String itmid = (String)cmbAitmid.getSelectedItem();
                LoadIUP(IUPList, itmid, IUPTableAdd);
                cmbAsid.setEnabled(true);
                cmbAsid.removeAllItems();
                cmbAsid.addItem("Select...");
                LoadcmbSupplierID(itmid, cmbAsid);
                DisplayItemAdd(itmid);
                if(cmbAsid.getItemCount() == 1)
                {
                    JOptionPane.showMessageDialog(null, "All suppliers are supplying this item", "No available suppliers", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_cmbAitmidActionPerformed

    private void cmbVitmidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbVitmidActionPerformed
        int i = cmbVitmid.getSelectedIndex();
        if(i == -1){}
        else if(i != 0)
        {
            i = i-1;
            DisplayItemDetails(i);
            LoadIUP(IUPList, (String)tblVitm.getValueAt(i,0), IUPTableItem);
            tblVitm.setRowSelectionInterval(i, i);
            tblVitm.scrollRectToVisible(new Rectangle(tblVitm.getCellRect(i, 0, true)));
        }
        else{clearItemView();}
    }//GEN-LAST:event_cmbVitmidActionPerformed

    private void btnVclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVclearActionPerformed
        clearItemView();
    }//GEN-LAST:event_btnVclearActionPerformed

    private void btnAddAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAddItemActionPerformed
        String name = txtfieldAIname.getText();
        String desc = txtfieldAIdesc.getText();
        String qis = txtfieldAIQiS.getText();
        String rl = txtfieldAIRL.getText();
        String rp = txtfieldAIRP.getText();
        String itmid = txtfieldAIitmid.getText();
        try
        {
            int QiS = Integer.parseInt(qis);
            int RL = Integer.parseInt(rl);
            double RP = Double.parseDouble(rp);
            
            if(QiS<0 || RL<0 || RP<1 || !(name.matches("[a-zA-Z0-9-. ]+")) || !(desc.matches("[a-zA-Z0-9-. ]+")))
            {
                JOptionPane.showMessageDialog(null, "Please make sure the details are correct!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    ShowHideAddItem("Add");
                    JOptionPane.showMessageDialog(null, "Add at least one supplier to add this item.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    txtfieldASitmid.setText(itmid);
                    LoadcmbSupplierID(itmid, cmbAddsid);
                }
            }
        }catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please make sure the details are correct!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddAddItemActionPerformed

    private void cmbAddsidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAddsidActionPerformed
        int i  = cmbAddsid.getSelectedIndex();
        String sid = (String)cmbAddsid.getSelectedItem();
        switch (i) {
            case -1 -> {
            }
            case 0 -> {
                txtfieldAddunitprice.setEditable(false);
                txtfieldAddunitprice.setText("");
                txtfieldAddSID.setText("");
                txtfieldAddSname.setText("");
                txtfieldAddaddress.setText("");
                txtfieldAddCN.setText("");
                txtfieldAddSR.setText("");
                txtfieldAddBN.setText("");
                txtfieldAddBAN.setText("");
                btnAddAddSup.setEnabled(false);
                btnclearsup.setEnabled(false);
            }
            default -> {
                txtfieldAddunitprice.setText("");
                txtfieldAddunitprice.setEditable(true);
                btnAddAddSup.setEnabled(true);
                btnclearsup.setEnabled(true);
                DisplaySupplierAddItem(sid);
            }
        }
    }//GEN-LAST:event_cmbAddsidActionPerformed

    private void btnAddAddSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAddSupActionPerformed
        String up = txtfieldAddunitprice.getText();
        String sid = (String)cmbAddsid.getSelectedItem();
        String itmid =  txtfieldASitmid.getText();
        try
        {
            Double unitprice = Double.parseDouble(up);
            if(unitprice<=0)
            {
               JOptionPane.showMessageDialog(null, "Please only input positive number!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    IUPList.add(new ItemUnitPrice(sid, itmid, unitprice));
                    rereadItemUnitPriceFile();
                    cmbAddsid.removeAllItems();
                    cmbAddsid.addItem("Select...");
                    LoadcmbSupplierID(itmid, cmbAddsid);
                    LoadIUP(IUPList,itmid,IUPTableItemAdd);
                    JOptionPane.showMessageDialog(null, "Supplier, "+sid+" added for Item, "+itmid, "Successfull", JOptionPane.INFORMATION_MESSAGE);
                    btnsaveall.setEnabled(true);
                }
            }
        }catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please only input numeric value!", "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnAddAddSupActionPerformed

    private void btnclearsupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearsupActionPerformed
        clearItemAdd();
    }//GEN-LAST:event_btnclearsupActionPerformed

    private void btnsaveallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveallActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0)
        {
           ItemList.add(new Item(txtfieldAIitmid.getText(),txtfieldAIname.getText(),txtfieldAIdesc.getText(),
                        Integer.parseInt(txtfieldAIQiS.getText()),Integer.parseInt(txtfieldAIRL.getText()),
                        Double.parseDouble(txtfieldAIRP.getText())));
           rereadItemFile();
           JOptionPane.showMessageDialog(null, "Item, "+txtfieldAIitmid.getText()+ " has been added!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
           clearItemAddAll();
           clearItemAddInput();
           txtfieldAIitmid.setText(GetNextItemID());
           refreshInventory();
        }
    }//GEN-LAST:event_btnsaveallActionPerformed

    private void btncancelAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelAIActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0)
        {
            clearItemAddAll();
        }
    }//GEN-LAST:event_btncancelAIActionPerformed

    private void btnclearAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearAIActionPerformed
        clearItemAddInput();
    }//GEN-LAST:event_btnclearAIActionPerformed

    private void btnEditsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditsaveActionPerformed
        String name = txtfieldEditname.getText();
        String desc = txtfieldEditdesc.getText();
        String rp = txtfieldEditRP.getText();
        String itmid = txtfieldEdititmid.getText();
        int i = cmbEdititmid.getSelectedIndex();
        try
        {
            double RP = Double.parseDouble(rp);
            
            if( RP<1 || !(name.matches("[a-zA-Z0-9-. ]+")) || !(desc.matches("[a-zA-Z0-9-. ]+")))
            {
                JOptionPane.showMessageDialog(null, "Please make sure the details are correct!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    Object var[] = {itmid,name,desc,-2,-2,RP};
                    items.editRecord(ItemList, var);
                    rereadItemFile();
                    JOptionPane.showMessageDialog(null, "Item Details Updated!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
                    refreshInventoryTable();
                    DisplayItemEdit(itmid);
                    i = i-1;
                    tblEditItem.setRowSelectionInterval(i, i);
                    tblEditItem.scrollRectToVisible(new Rectangle(tblEditItem.getCellRect(i, 0, true)));
                    ShowHideEditItem("Cancel");
                }
            }
        }catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please make sure the details are correct!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditsaveActionPerformed

    private void btnEditcancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditcancelActionPerformed
        String itmid = txtfieldEdititmid.getText();
        DisplayItemEdit(itmid);
        ShowHideEditItem("Cancel");
    }//GEN-LAST:event_btnEditcancelActionPerformed

    private void btnEditeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditeditActionPerformed
        ShowHideEditItem("Edit");
    }//GEN-LAST:event_btnEditeditActionPerformed

    private void cmbEdititmidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEdititmidActionPerformed
        int i  = cmbEdititmid.getSelectedIndex();
        String itmid = (String)cmbEdititmid.getSelectedItem();
        switch (i) {
            case -1 -> {
            }
            case 0 -> {
                clearItemEdit();
            }
            default -> {
                btnEditedit.setEnabled(true);
                btnEditdelete.setEnabled(true);
                DisplayItemEdit(itmid);
                i=i-1;
                tblEditItem.setRowSelectionInterval(i, i);
                tblEditItem.scrollRectToVisible(new Rectangle(tblEditItem.getCellRect(i, 0, true)));
            }
        }
    }//GEN-LAST:event_cmbEdititmidActionPerformed

    private void btnEditclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditclearActionPerformed
        clearItemEdit();
    }//GEN-LAST:event_btnEditclearActionPerformed

    private void btnEditdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditdeleteActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0)
        {
            String itmid = txtfieldEdititmid.getText();
            Object var[] = {itmid};
            items.deleteRecord(ItemList, var);
            iups.CheckItemExist(IUPList, ItemList, SupplierList);
            rereadItemFile();
            rereadItemUnitPriceFile();
            JOptionPane.showMessageDialog(null, "Item Removed!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            refreshInventory();
            cmbEdititmid.setSelectedIndex(0);
            txtfieldAIitmid.setText(GetNextItemID());
        }
    }//GEN-LAST:event_btnEditdeleteActionPerformed

    private void cmbVsidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbVsidActionPerformed
        int i  = cmbVsid.getSelectedIndex();
        String sid = (String)cmbVsid.getSelectedItem();
        switch (i) {
            case -1 -> {
            }
            case 0 -> {
                clearSupplierView();
            }
            default -> {
                DisplaySupplierDetailsView(sid);
                i=i-1;
                tblVsup.setRowSelectionInterval(i, i);
                tblVsup.scrollRectToVisible(new Rectangle(tblVsup.getCellRect(i, 0, true)));
            }
        }        
    }//GEN-LAST:event_cmbVsidActionPerformed

    private void btnVSclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVSclearActionPerformed
        clearSupplierView();
    }//GEN-LAST:event_btnVSclearActionPerformed

    private void cmbAdditmidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAdditmidActionPerformed
        int i  = cmbAdditmid.getSelectedIndex();
        String itmid = (String)cmbAdditmid.getSelectedItem();
        switch (i) {
            case -1 -> {
            }
            case 0 -> {
                txtfieldAddunitpriceS.setEditable(false);
                txtfieldAddunitpriceS.setText("");
                txtfieldAitmidS.setText("");
                txtfieldAnameS.setText("");
                txtfieldAdescS.setText("");
                txtfieldAQiSS.setText("");
                txtfieldARLS.setText("");
                txtfieldARPS.setText("");  
                btnAddAdditmidS.setEnabled(false);
                btnclearitmS.setEnabled(false);
            }
            default -> {
                txtfieldAddunitpriceS.setText("");
                txtfieldAddunitpriceS.setEditable(true);
                btnAddAdditmidS.setEnabled(true);
                btnclearitmS.setEnabled(true);
                DisplayItemAddSupplier(itmid);
            }
        }    }//GEN-LAST:event_cmbAdditmidActionPerformed

    private void btnAddAdditmidSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAdditmidSActionPerformed
        String up = txtfieldAddunitpriceS.getText();
        String itmid = (String)cmbAdditmid.getSelectedItem();
        String sid =  txtfieldASsid.getText();
        try
        {
            Double unitprice = Double.parseDouble(up);
            if(unitprice<=0)
            {
               JOptionPane.showMessageDialog(null, "Please only input positive number!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    IUPList.add(new ItemUnitPrice(sid, itmid, unitprice));
                    rereadItemUnitPriceFile();
                    cmbAdditmid.removeAllItems();
                    cmbAdditmid.addItem("Select...");
                    LoadItmIDCMBSupplier(cmbAdditmid,sid);
                    LoadIUP(sid);
                    JOptionPane.showMessageDialog(null, "Item, "+itmid+" added for Supplier, "+sid, "Successfull", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please only input numeric value!", "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnAddAdditmidSActionPerformed

    private void btnclearitmSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearitmSActionPerformed
        clearSupplierAdd();
    }//GEN-LAST:event_btnclearitmSActionPerformed

    private void btnsaveallSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveallSActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0)
        {
           SupplierList.add(new Supplier(txtfieldAddSIDS.getText(),txtfieldAddSnameS.getText(),txtfieldAddaddressS.getText(),
                            txtfieldAddCNS.getText(),Double.parseDouble(txtfieldAddSRS.getText()),txtfieldAddBNS.getText(),
                            txtfieldAddBANS.getText()));
           rereadSupplierFile();
           JOptionPane.showMessageDialog(null, "Supplier, "+txtfieldAddSIDS.getText()+ " has been added!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
           clearSupplierAddAll();
           clearSupplierAddInput();
           txtfieldAddSIDS.setText(GetNextSupplierID());
           refreshSupplier();
        }    }//GEN-LAST:event_btnsaveallSActionPerformed

    private void btnclearASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearASActionPerformed
        clearSupplierAddInput();
    }//GEN-LAST:event_btnclearASActionPerformed

    private void btnAddASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddASActionPerformed
        String sid = txtfieldAddSIDS.getText();
        String sname = txtfieldAddSnameS.getText();
        String add = txtfieldAddaddressS.getText();
        String CN = txtfieldAddCNS.getText();
        String SR = txtfieldAddSRS.getText();
        String BN = txtfieldAddBNS.getText();
        String BAN = txtfieldAddBANS.getText();
        try
        {
            double sr = Double.parseDouble(SR);
            
            if(!(sr>-1 && sr<6) || !(sname.matches("[a-zA-Z0-9-. ]+")) || !(add.matches("[a-zA-Z0-9-. ]+")) ||
                !(CN.matches("[0-9-]+")) || !(BN.matches("[a-zA-Z0-9-. ]+")) || !(BAN.matches("[0-9-]+")))
            {
                JOptionPane.showMessageDialog(null, "Please make sure the details are correct!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    ShowHideAddSupplier("Add");
                    JOptionPane.showMessageDialog(null, "Adding item is optional.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    txtfieldASsid.setText(sid);
                    LoadItmIDCMBSupplier(cmbAdditmid, sid);
                }
            }
        }catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please make sure the details are correct!", "Error!", JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_btnAddASActionPerformed

    private void btncancelASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelASActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0)
        {
            clearSupplierAddAll();
        }
    }//GEN-LAST:event_btncancelASActionPerformed

    private void btneditsupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditsupActionPerformed
        ShowHideEditSupplier("Edit");
    }//GEN-LAST:event_btneditsupActionPerformed

    private void btndeletesupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletesupActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0)
        {
            String sid = (String)cmbEsid.getSelectedItem();
            Object var[] = {sid};
            sps.deleteRecord(SupplierList, var);
            iups.CheckItemExist(IUPList, ItemList, SupplierList);
            rereadSupplierFile();
            rereadItemUnitPriceFile();
            JOptionPane.showMessageDialog(null, "Supplier Removed!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            refreshSupplier();
            cmbEsid.setSelectedIndex(0);
            txtfieldAddSIDS.setText(GetNextSupplierID());
        }
    }//GEN-LAST:event_btndeletesupActionPerformed

    private void btncancelsupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelsupActionPerformed
        String sid = (String)cmbEsid.getSelectedItem();
        ShowHideEditSupplier("Cancel");
        DisplaySupplierEdit(sid);
    }//GEN-LAST:event_btncancelsupActionPerformed

    private void cmbEsidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEsidActionPerformed
        int i  = cmbEsid.getSelectedIndex();
        String sid = (String)cmbEsid.getSelectedItem();
        switch (i) {
            case -1 -> {
            }
            case 0 -> {
                clearSupplierEdit();
            }
            default -> {
                btneditsup.setEnabled(true);
                btndeletesup.setEnabled(true);
                DisplaySupplierEdit(sid);
                i=i-1;
                tblEsup.setRowSelectionInterval(i, i);
                tblEsup.scrollRectToVisible(new Rectangle(tblEsup.getCellRect(i, 0, true)));
            }
        }
    }//GEN-LAST:event_cmbEsidActionPerformed

    private void btncleareditsupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncleareditsupActionPerformed
        clearSupplierEdit();
    }//GEN-LAST:event_btncleareditsupActionPerformed

    private void btnsavesupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsavesupActionPerformed
        String sid = (String)cmbEsid.getSelectedItem();
        String sname = txtfieldEditSname.getText();
        String add = txtfieldEditaddressS.getText();
        String CN = txtfieldEditCNS.getText();
        String SR = txtfieldEditSRS.getText();
        String BN = txtfieldEditBNS.getText();
        String BAN = txtfieldEditBANS.getText();
        int i = cmbEsid.getSelectedIndex();
        try
        {
            double sr = Double.parseDouble(SR);
            
            if(!(sr>-1 && sr<6) || !(sname.matches("[a-zA-Z0-9-. ]+")) || !(add.matches("[a-zA-Z0-9-. ]+")) ||
                !(CN.matches("[0-9-]+")) || !(BN.matches("[a-zA-Z0-9-. ]+")) || !(BAN.matches("[0-9-]+")))
            {
                JOptionPane.showMessageDialog(null, "Please make sure the details are correct!sr", "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if(option == 0)
                {
                    Object Var[] = {sid,sname,add,CN,sr,BN,BAN};
                    sps.editRecord(SupplierList, Var);
                    rereadSupplierFile();
                    JOptionPane.showMessageDialog(null, "Supplier Details Updated!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
                    refreshSupplierTable();
                    DisplaySupplierEdit(sid);
                    i = i-1;
                    tblEsup.setRowSelectionInterval(i, i);
                    tblEsup.scrollRectToVisible(new Rectangle(tblEsup.getCellRect(i, 0, true)));
                    ShowHideEditSupplier("Cancel");
                }
            }
        }catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Please make sure the details are correct!", "Error!", JOptionPane.ERROR_MESSAGE);
        }    
    }//GEN-LAST:event_btnsavesupActionPerformed

    private void pnlitmStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pnlitmStateChanged
        pnlchangeItem();
    }//GEN-LAST:event_pnlitmStateChanged

    private void pnlsupStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pnlsupStateChanged
        pnlchangeSupplier();
    }//GEN-LAST:event_pnlsupStateChanged

    private void tabpnlIMStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabpnlIMStateChanged
        pnlchangeItem();
        pnlchangeInventory();
        pnlchangeSupplier();
        clearUpdateStock();
    }//GEN-LAST:event_tabpnlIMStateChanged

    private void btnclearstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearstockActionPerformed
        clearUpdateStock();
    }//GEN-LAST:event_btnclearstockActionPerformed

    private void cmbstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbstockActionPerformed
        int i = cmbstock.getSelectedIndex();
        if(i == -1){}
        else if(i != 0)
        {
            i = i-1;
            DisplayUpdateStock(i);
            tblStock.setRowSelectionInterval(i, i);
            tblStock.scrollRectToVisible(new Rectangle(tblStock.getCellRect(i, 0, true)));
        }
        else{clearUpdateStock();}
    }//GEN-LAST:event_cmbstockActionPerformed

    private void btnupdatestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdatestockActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(option == 0)
        {
            UpdateStockLevel(txtfielditmidstock.getText(), Integer.parseInt(txtfieldquantitystock.getText()));
            //change delivered status and timestamp
            Object Var[] = {txtfieldpoidstock.getText()};
            pos.editRecord(POList, Var);
            //rereadfile
            rereadPOFile();
            //reassign po object to pr
            prs.setPOwList(POList, PRList);
            JOptionPane.showMessageDialog(null, "Stock for item,"+txtfielditmidstock.getText()+" has been updated!", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            //refresh table and combo box
            clearUpdateStock();
            LoadUpdateStock();
            LoadcmbStock();
        }
    }//GEN-LAST:event_btnupdatestockActionPerformed

    private void ProfileChgPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfileChgPassActionPerformed
        im.readLoginCredentials();
        im.ResetPassword(ProfileCurrentPass.getText(),ProfileNewPass.getText(),ChgPassError, ProfileCurrentPass,ProfileNewPass,ShowHidePass);
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
            new Admin_Program(new Admin(im));
        }else{
            new LoginUI();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    
    
    
    
    
    
    
    
    
    public static void main(String args[]) {
        try {
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        FlatLaf.setGlobalExtraDefaults( Collections.singletonMap( "@accentColor", "#AB886D" ));
        FlatIntelliJLaf.setup();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IM_Program(new Inventory_Manager(new User())).setVisible(true);
            }
        });
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("Please install flatlaf for a better experience!");
            }
    }

    
    private void rereadItemFile() // write and read text file
    {
        itm.WriteFile("src/data/Item.txt", ItemList);
        itm.ReadFile("src/data/Item.txt", items);
    }
    
    private void rereadItemUnitPriceFile()
    {
        iup.WriteFile("src/data/ItemUnitPrice.txt", IUPList);
        iup.ReadFile("src/data/ItemUnitPrice.txt", iups);
    }
    
    private void rereadSupplierFile()
    {
        sp.WriteFile("src/data/Supplier.txt", SupplierList);
        sp.ReadFile("src/data/Supplier.txt", sps);
    }
    
    private void rereadPOFile()
    {
        po.WriteFile("src/data/PurchaseOrder.txt", POList);
        po.ReadFile("src/data/PurchaseOrder.txt", pos);
    }
    
    
    
    private void refreshInventoryTable()
    {
        LoadInventoryEdit();
        LoadInventory();
        LoadItemView();
        LoadItemEdit();
    }
    
    private void refreshInventoryCMB()
    {
        LoadItmIDCMB(cmbEitmid);
        LoadItmIDCMB(cmbitmid);
        LoadItmIDCMB(cmbVitmid);
        LoadItmIDCMB(cmbEdititmid);
        LoadItmIDCMB(cmbAitmid);
    }

    private void refreshInventory()
    {
        refreshInventoryTable();
        refreshInventoryCMB();
    }
    
    private void refreshItemUnitPriceEdit(Integer i, String itmid)
    {
        LoadIUP(IUPList,itmid,IUPTableEdit);
        DisplayItemUnitPriceEdit(i);
    }
    
    private void refreshSupplierTable()
    {
        LoadSupplier(SupplierTableView);
        LoadSupplier(SupplierTableEdit);

    }
    
    private void refreshSupplierCMB()
    {
        LoadSIDCMB(cmbVsid);
        LoadSIDCMB(cmbEsid);
    }
    
    private void refreshSupplier()
    {
        refreshSupplierTable();
        refreshSupplierCMB();
    }
    
    
    private void LoadInventory()
    {
        InventoryTable.setRowCount(0);
        for(Item i:ItemList)
        {
            if(i.getQuantityInStock()>=0)
            {
                Object row[] = {i.getItemID(), i.getItemName(), i.getDescription(), 
                                i.getQuantityInStock(), i.getReorderLevel()};
                InventoryTable.addRow(row);
            }
        }
    }
    
    
    private void LoadInventoryEdit()
    {
        InventoryTableEdit.setRowCount(0);
        for(Item i:ItemList)
        {
            if(i.getQuantityInStock()>=0)
            {
                Object row[] = {i.getItemID(), i.getQuantityInStock(), i.getReorderLevel()};
                InventoryTableEdit.addRow(row);
            }
        }    
    }
    
    private void LoadItemView()
    {
        ItemTable.setRowCount(0);
        for(Item i: ItemList)
        {
            if(i.getQuantityInStock()>=0)
            {
                Object row[] = {i.getItemID(), i.getItemName(), i.getDescription(),i.getRetailPrice()};
                ItemTable.addRow(row);
            }    
        }
    }
    
    private void LoadItemEdit()
    {
        ItemTableEdit.setRowCount(0);
        for(Item i: ItemList)
        {
            if(i.getQuantityInStock()>=0)
            {
                Object row[] = {i.getItemID(), i.getItemName(), i.getDescription(),i.getRetailPrice()};
                ItemTableEdit.addRow(row);
            }    
        }
    }
    
    private void LoadSupplier(DefaultTableModel SupplierTable)
    {
        SupplierTable.setRowCount(0);
        for(Supplier s: SupplierList)
        {
            if(s.getSupplierRating()>=0)
            {
                Object row[] = {s.getSupplierID(), s.getSupplierName(), s.getAddress(), s.getContactNumber(),
                                s.getBankName(), s.getBankAccountNo(), s.getSupplierRating()};
                SupplierTable.addRow(row);
            }    
        }
    }
    
//    private void LoadUpdateStock()
//    {
//        UpdateStockTable.setRowCount(0);
//        for(PurchaseOrder po: POList)
//        {
//            if(po.getPaymentStatus().equals("Paid"))
//            {
//                String poid = po.getPO_ID();
//                String prid = po.getPR_ID();
//                int quantity = po.getQuantity();
//                String status = po.getStatus();
//                String PS = po.getPaymentStatus();
//                for(PurchaseRequisite pr: PRList)
//                {
//                    if(pr.getPR_ID().equals(po.getPR_ID()))
//                    {
//                        String itmid = pr.getITM_ID();
//                        Item i = items.ReturnSpecificObject(ItemList, itmid);
//                        String itmname = i.getItemName();
//                        Object row[] = {poid,prid,itmid,itmname,quantity,status,PS};
//                        UpdateStockTable.addRow(row);
//                    }
//                }
//            }
//        }
//    }
    
    private void LoadUpdateStock()
    {
        UpdateStockTable.setRowCount(0); 
        for(PurchaseRequisite pr: PRList)
        {
            if(pr.getPo().getPR_ID()!= null)
            {
                if(pr.getPo().getPaymentStatus().equals("Paid") && pr.getPo().getDeliveryStatus().equals("Pending"))
                {
                    String poid = pr.getPo().getPO_ID();
                    String prid = pr.getPR_ID();
                    int quantity = pr.getPo().getQuantity();
                    String status = pr.getPo().getStatus();
                    String PS = pr.getPo().getPaymentStatus();
                    String itmid = pr.getITM_ID();
                    Item i = (Item)items.ReturnSpecificObject(ItemList, itmid);
                    String itmname = i.getItemName();
                    Object row[] = {poid,prid,itmid,itmname,quantity,status,PS};
                    UpdateStockTable.addRow(row); 
                }
            }
        }
    }
    
    
    
    
    
    private void LoadcmbStock()
    {
        cmbstock.removeAllItems();
        cmbstock.addItem("Select...");
        for(PurchaseOrder po: POList)
        {
            if(po.getPaymentStatus().equals("Paid") && po.getDeliveryStatus().equals("Pending"))
            {
                cmbstock.addItem(po.getPO_ID());
            }
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
    
    private void LoadIUP(String sid)
    {
        IUPTableSupplierAdd.setRowCount(0);

        for(ItemUnitPrice i: IUPList)
        {
            if(i.getSupplierID().equals(sid))
            {
                Object row[] = {i.getItemID(),i.getUnitCost()};
                IUPTableSupplierAdd.addRow(row);
            }
        }
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
    
    
    private void checkQiSEdit(Integer i)
    {
        int QiS = (Integer)tblinv.getValueAt(i,1);
        int RL = (Integer)tblinv.getValueAt(i,2);
        if(QiS<RL)
        {
            lblEitmstatus.setForeground(new java.awt.Color(239,33,33));
            lblEstatusstar.setForeground(new java.awt.Color(239,33,33));
        }
        else
        {
            lblEitmstatus.setForeground(new java.awt.Color(228,224,225));
            lblEstatusstar.setForeground(new java.awt.Color(228,224,225));
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
    
    private void DisplayItemDetails(Integer i)
    {
        txtfieldVitmid.setText((String)tblVitm.getValueAt(i, 0));
        txtfieldVitmname.setText((String)tblVitm.getValueAt(i, 1));
        txtfieldVdesc.setText((String)tblVitm.getValueAt(i, 2));
        txtfieldVRP.setText(String.valueOf(tblVitm.getValueAt(i, 3)));
    }
    
    
    private void DisplayItemAdd(String itmid)
    {
        Item i = (Item)items.ReturnSpecificObject(ItemList, itmid);
        txtfieldAitmid.setText(i.getItemID());
        txtfieldAname.setText(i.getItemName());
        txtfieldAdesc.setText(i.getDescription());
        txtfieldAQiS.setText(String.valueOf(i.getQuantityInStock()));
        txtfieldARL.setText(String.valueOf(i.getReorderLevel()));
        txtfieldARP.setText(String.valueOf(i.getRetailPrice()));
    }
    
    private void DisplayItemEdit(String itmid)
    {
        Item i = (Item)items.ReturnSpecificObject(ItemList, itmid);
        txtfieldEdititmid.setText(i.getItemID());
        txtfieldEditname.setText(i.getItemName());
        txtfieldEditdesc.setText(i.getDescription());
        txtfieldEditRP.setText(String.valueOf(i.getRetailPrice()));
    }
    
    private void DisplaySupplierAdd(String sid)
    {
        Supplier s = (Supplier)sps.ReturnSpecificObject(SupplierList, sid);
        txtfieldASID.setText(s.getSupplierID());
        txtfieldASname.setText(s.getSupplierName());
        txtfieldAaddress.setText(s.getAddress());
        txtfieldACN.setText(s.getContactNumber());
        txtfieldASR.setText(String.valueOf(s.getSupplierRating()));
        txtfieldABN.setText(s.getBankName());
        txtfieldABAN.setText(s.getBankAccountNo());
    }
    
    private void DisplaySupplierAddItem(String sid)
    {
        Supplier s = (Supplier)sps.ReturnSpecificObject(SupplierList, sid);
        txtfieldAddSID.setText(s.getSupplierID());
        txtfieldAddSname.setText(s.getSupplierName());
        txtfieldAddaddress.setText(s.getAddress());
        txtfieldAddCN.setText(s.getContactNumber());
        txtfieldAddSR.setText(String.valueOf(s.getSupplierRating()));
        txtfieldAddBN.setText(s.getBankName());
        txtfieldAddBAN.setText(s.getBankAccountNo());
    }

    private void DisplaySupplierEdit(String sid)
    {
        Supplier s = (Supplier)sps.ReturnSpecificObject(SupplierList, sid);
        txtfieldEditSname.setText(s.getSupplierName());
        txtfieldEditaddressS.setText(s.getAddress());
        txtfieldEditCNS.setText(s.getContactNumber());
        txtfieldEditSRS.setText(String.valueOf(s.getSupplierRating()));
        txtfieldEditBNS.setText(s.getBankName());
        txtfieldEditBANS.setText(s.getBankAccountNo());
    }
    
    private void DisplayItemAddSupplier(String itmid)
    {
        Item i = (Item)items.ReturnSpecificObject(ItemList, itmid);
        txtfieldAitmidS.setText(i.getItemID());
        txtfieldAnameS.setText(i.getItemName());
        txtfieldAdescS.setText(i.getDescription());
        txtfieldAQiSS.setText(String.valueOf(i.getQuantityInStock()));
        txtfieldARLS.setText(String.valueOf(i.getReorderLevel()));
        txtfieldARPS.setText(String.valueOf(i.getRetailPrice()));   
    }
    
    private void DisplaySupplierDetailsView(String sid)
    {
        Supplier s = (Supplier)sps.ReturnSpecificObject(SupplierList, sid);
        txtfieldVsid.setText(s.getSupplierID());
        txtfieldVsupname.setText(s.getSupplierName());
        txtfieldVsupadd.setText(s.getAddress());
        txtfieldVsupCN.setText(s.getContactNumber());
        txtfieldVsupSR.setText(String.valueOf(s.getSupplierRating()));
        txtfieldVsupBN.setText(s.getBankName());
        txtfieldVsupBAN.setText(s.getBankAccountNo());
    }
    
    private void DisplayUpdateStock(int i)
    {
        txtfieldpoidstock.setText((String)tblStock.getValueAt(i, 0));
        txtfieldpridstock.setText((String)tblStock.getValueAt(i, 1));
        txtfielditmidstock.setText((String)tblStock.getValueAt(i, 2));
        txtfielditmnamestock.setText((String)tblStock.getValueAt(i, 3));
        txtfieldquantitystock.setText(String.valueOf(tblStock.getValueAt(i, 4)));
        txtfieldstatusstock.setText((String)tblStock.getValueAt(i, 5));
        txtfieldPSstock.setText((String)tblStock.getValueAt(i, 6));
    }
    
    
    private void DisplayInventoryDetailsEdit(Integer i)
    {
        txtfieldEitmid.setText((String)tblinv.getValueAt(i, 0));
        for(Item itm: ItemList)
        {
            if(itm.getItemID().equals((String)tblinv.getValueAt(i, 0)))
            {
                txtfieldEname.setText(itm.getItemName());
                txtfieldEdesc.setText(itm.getDescription());
            }
        }
        txtfieldEQiS.setText(String.valueOf(tblinv.getValueAt(i, 1)));
        txtfieldERL.setText(String.valueOf(tblinv.getValueAt(i, 2)));
    }
    
    private void DisplayItemUnitPriceEdit(Integer i)
    {
        for(int j =0; j<tblsid.getRowCount();j++)
        {
            if(tblsid.getValueAt(j, 0).equals(cmbSID.getItemAt(i)))
            {
                txtfieldEsupplierid.setText((String)tblsid.getValueAt(j, 0));
                txtfieldEunitprice.setText(String.valueOf(tblsid.getValueAt(j, 1)));
            }
        }
    }
   
    private String GetNextItemID()
    {
        Item i = ItemList.get(ItemList.size()-1);
        int num = Integer.parseInt(i.getItemID().substring(3))+1;
        String itmid = "ITM".concat(String.format("%03d", num));
        return itmid;
    }
    
    private String GetNextSupplierID()
    {
        Supplier s = SupplierList.get(SupplierList.size()-1);
        int num = Integer.parseInt(s.getSupplierID().substring(2))+1;
        String sid = "SP".concat(String.format("%03d", num));
        return sid;
    }
    
    
    private void LoadItmIDCMB(javax.swing.JComboBox<String> cmbitmid)
    {
        cmbitmid.removeAllItems();
        cmbitmid.addItem("Select...");
        for(Item i: ItemList)
        {
            if(i.getQuantityInStock()>=0)
            {
                cmbitmid.addItem(i.getItemID());
            }
        }
    }
    
    private void LoadItmIDCMBSupplier(javax.swing.JComboBox<String> cmbitmid, String sid)
    {
        cmbitmid.removeAllItems();
        cmbitmid.addItem("Select...");
        int match = 0;
        for(Item i: ItemList)
        {
            match = 0;
            if(i.getQuantityInStock()>=0)
            {
                for(ItemUnitPrice iup : IUPList)
                {
                    if(i.getItemID().equals(iup.getItemID()) && iup.getSupplierID().equals(sid))
                    {
                        match = 1;
                    }
                }
                if (match == 0)
                {
                    cmbitmid.addItem(i.getItemID());
                }
            }
        }
    }
    
    private void LoadSIDCMB(javax.swing.JComboBox<String> cmbsid)
    {
        cmbsid.removeAllItems();
        cmbsid.addItem("Select...");
        for(Supplier s: SupplierList)
        {
            if(s.getSupplierRating()>=0)
            {
                cmbsid.addItem(s.getSupplierID());
            }
        }
    }
    

    
    private void LoadcmbSIDEdit(String ItemID)
    {
        for(ItemUnitPrice i: IUPList)
        {
            if(i.getItemID().equals(ItemID))
            {
                cmbSID.addItem(i.getSupplierID());
            }
        }
    }
    
    
    private void LoadcmbSupplierID(String ItemID, javax.swing.JComboBox<String> cmbsid)
    {
        cmbsid.removeAllItems();
        cmbsid.addItem("Select...");
        int match;
        for(Supplier s: SupplierList)
        {
            match = 0;
            for(ItemUnitPrice i: IUPList)
            {
                if((i.getSupplierID().equals(s.getSupplierID()))  && (i.getItemID().equals(ItemID)))
                {
                    match = 1;
                }
            }
            if(match == 0)
            {
                if(s.getSupplierRating()>=0){cmbsid.addItem(s.getSupplierID());}
            }
        }
    }
    
    
    private void UpdateStockLevel(String itmid, int quantity)
    {
        Item i = (Item)items.ReturnSpecificObject(ItemList, itmid);
        quantity = i.getQuantityInStock()+quantity;
        Object Var[] = {itmid,"-","-",quantity,-2,-2.0};
        i.editRecord(ItemList, Var);
        rereadItemFile();
        refreshInventoryTable();
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
    
    private void clearItemView()
    {
        txtfieldVitmid.setText("");
        txtfieldVitmname.setText("");
        txtfieldVdesc.setText("");
        txtfieldVRP.setText("");
        tblVitm.clearSelection();
        tblscrollpanelitem.getVerticalScrollBar().setValue(0);
        if(cmbVitmid.getSelectedIndex() != -1){cmbVitmid.setSelectedIndex(0);}
        if(IUPTableItem != null){IUPTableItem.setRowCount(0);}
    }
    
    private void clearSupplierView()
    {
        txtfieldVsid.setText("");
        txtfieldVsupname.setText("");
        txtfieldVsupadd.setText("");
        txtfieldVsupCN.setText("");
        txtfieldVsupSR.setText("");
        txtfieldVsupBN.setText("");
        txtfieldVsupBAN.setText("");
        tblVsupscrollpanel.getVerticalScrollBar().setValue(0);
        tblVsup.clearSelection();
        if(cmbVsid.getSelectedIndex() != -1){cmbVsid.setSelectedIndex(0);}
    }
    
    private void clearInventoryEdit()
    {
        txtfieldEitmid.setText("");
        txtfieldEname.setText("");
        txtfieldEdesc.setText("");
        txtfieldEQiS.setText("");
        txtfieldERL.setText("");
        txtfieldEsupplierid.setText("");
        txtfieldEunitprice.setText("");
        tblinv.clearSelection();
        tblinvscrollpanel.getVerticalScrollBar().setValue(0);
        lblEitmstatus.setForeground(new java.awt.Color(228,224,225));
        lblEstatusstar.setForeground(new java.awt.Color(228,224,225));
        if(cmbEitmid.getSelectedIndex() != -1){cmbEitmid.setSelectedIndex(0);}
        if(IUPTableEdit != null){IUPTableEdit.setRowCount(0);}
        cmbSID.removeAllItems();
        cmbSID.addItem("Select...");
        ShowHide("hideall");
        ShowHide2("hideall");
    }
    
    
    private void clearSID()
    {
        txtfieldEsupplierid.setText("");
        txtfieldEunitprice.setText("");
        cmbSID.setSelectedIndex(0);
        ShowHide2("hideall");

    }
    
    private void clearInventoryAdd()
    {
        cmbAsid.removeAllItems();
        cmbAsid.addItem("Select...");
        if(cmbAitmid.getSelectedIndex() != -1){cmbAitmid.setSelectedIndex(0);}
        cmbAsid.setEnabled(false);
        txtfieldAunitprice.setEditable(false);
        txtfieldAunitprice.setText("");
        if(IUPTableAdd != null){IUPTableAdd.setRowCount(0);}
        txtfieldAitmid.setText("");
        txtfieldAname.setText("");
        txtfieldAdesc.setText("");
        txtfieldAQiS.setText("");
        txtfieldARL.setText("");
        txtfieldARP.setText("");
        txtfieldASID.setText("");
        txtfieldASname.setText("");
        txtfieldAaddress.setText("");
        txtfieldACN.setText("");
        txtfieldASR.setText("");
        txtfieldABN.setText("");
        txtfieldABAN.setText("");
        btnAadd.setEnabled(false);
    }
    
    private void clearItemAdd()
    {
        if(cmbAddsid.getSelectedIndex() != -1){cmbAddsid.setSelectedIndex(0);}
        txtfieldAddunitprice.setEditable(false);
        txtfieldAddunitprice.setText("");
        btnAddAddSup.setEnabled(false);
        btnclearsup.setEnabled(false);
    }
    
    private void clearItemAddInput()
    {
        txtfieldAIname.setText("");
        txtfieldAIdesc.setText("");
        txtfieldAIQiS.setText("");
        txtfieldAIRL.setText("");
        txtfieldAIRP.setText("");
    }
    
    private void clearItemAddAll()
    {
        clearItemAdd();
        iups.CheckItemExist(IUPList, ItemList, SupplierList);
        rereadItemUnitPriceFile();
        if(IUPTableItemAdd!= null){IUPTableItemAdd.setRowCount(0);}
        ShowHideAddItem("Cancel");
    }
    
    private void clearSupplierAdd()
    {
        if(cmbAdditmid.getSelectedIndex() != -1){cmbAdditmid.setSelectedIndex(0);}
        txtfieldAddunitpriceS.setEditable(false);
        txtfieldAddunitpriceS.setText("");
        btnAddAdditmidS.setEnabled(false);
        btnclearitmS.setEnabled(false);
    }
    
    private void clearSupplierAddInput()
    {
        txtfieldAddSnameS.setText("");
        txtfieldAddaddressS.setText("");
        txtfieldAddCNS.setText("");
        txtfieldAddSRS.setText("");
        txtfieldAddBNS.setText("");
        txtfieldAddBANS.setText("");
    }
    
    private void clearSupplierAddAll()
    {
        clearSupplierAdd();
        iups.CheckItemExist(IUPList, ItemList, SupplierList);
        rereadItemUnitPriceFile();
        if(IUPTableSupplierAdd!= null){IUPTableSupplierAdd.setRowCount(0);}
        ShowHideAddSupplier("Cancel");
    }
    
    
    private void clearItemEdit()
    {
        txtfieldEdititmid.setText("");
        txtfieldEditname.setText("");
        txtfieldEditdesc.setText("");
        txtfieldEditRP.setText("");
        btnEditedit.setEnabled(false);
        btnEditdelete.setEnabled(false);
        btnEditsave.setEnabled(false);
        btnEditcancel.setEnabled(false);   
        if(cmbEdititmid.getSelectedIndex() != -1){cmbEdititmid.setSelectedIndex(0);}
        tblEditItem.clearSelection();
        tblEditItemscrollpanel.getVerticalScrollBar().setValue(0);
    }
    
    private void clearSupplierEdit()
    {
        txtfieldEditSname.setText("");
        txtfieldEditaddressS.setText("");
        txtfieldEditCNS.setText("");
        txtfieldEditSRS.setText("");
        txtfieldEditBNS.setText("");
        txtfieldEditBANS.setText(""); 
        btneditsup.setEnabled(false);
        btndeletesup.setEnabled(false);
        btncancelsup.setEnabled(false);
        btnsavesup.setEnabled(false);
        if(cmbEsid.getSelectedIndex() != -1){cmbEsid.setSelectedIndex(0);}
        tblEsup.clearSelection();
        tblEsupscrollpanel.getVerticalScrollBar().setValue(0);
    }
    
    private void clearUpdateStock()
    {
        txtfieldpoidstock.setText("");
        txtfieldpridstock.setText("");
        txtfielditmidstock.setText("");
        txtfielditmnamestock.setText("");
        txtfieldquantitystock.setText("");
        txtfieldstatusstock.setText("");
        txtfieldPSstock.setText("");
        if(cmbstock.getSelectedIndex() != -1){cmbstock.setSelectedIndex(0);}
        tblStock.clearSelection();
        tblStockscrollpanel.getVerticalScrollBar().setValue(0);
    }
    
    private void ShowHide(String run)
    {
        if(run.equals("edit"))
        {
            btnsave.show();
            btncancel.show();
            btnedit.hide();
            cmbEitmid.setEnabled(false);
            btnEclear.setEnabled(false);
            txtfieldEQiS.setEditable(true);
            txtfieldERL.setEditable(true);
            btneditsip.setEnabled(false);
            cmbSID.setEnabled(false);
            btnclearSID.setEnabled(false);
            btndltsip.setEnabled(false);
        }else if (run.equals("cancel"))
        {
            btnsave.hide();
            btncancel.hide();
            btnedit.show();
            cmbEitmid.setEnabled(true);
            btnEclear.setEnabled(true);
            txtfieldEQiS.setEditable(false);
            txtfieldERL.setEditable(false);
            btneditsip.setEnabled(true);
            cmbSID.setEnabled(true);
            btnclearSID.setEnabled(true);
            btndltsip.setEnabled(true);
        }else if (run.equals("hideall"))
        {
            btnsave.hide();
            btncancel.hide();
            btnedit.hide();
        }else if (run.equals("showedit"))
        {
            btnedit.show();
        }
    }
    
    private void ShowHide2(String run)
    {
        if(run.equals("edit"))
        {
            btnsavesip.show();
            btncancelsip.show();
            btneditsip.hide();
            cmbSID.setEnabled(false);
            cmbEitmid.setEnabled(false);
            btnEclear.setEnabled(false);
            btnclearSID.setEnabled(false);
            txtfieldEunitprice.setEditable(true);
            btnedit.setEnabled(false);
            btndltsip.hide();
        }else if (run.equals("cancel"))
        {
            btnsavesip.hide();
            btncancelsip.hide();
            btneditsip.show();
            cmbSID.setEnabled(true);
            btnclearSID.setEnabled(true);
            cmbEitmid.setEnabled(true);
            btnEclear.setEnabled(true);
            txtfieldEunitprice.setEditable(false);
            btnedit.setEnabled(true);
            btndltsip.show();
        }else if (run.equals("hideall"))
        {
            btnsavesip.hide();
            btncancelsip.hide();
            btneditsip.hide();
            btndltsip.hide();
        }else if (run.equals("showedit"))
        {
            btneditsip.show();
            btndltsip.show();
        }
    }
    
    private void ShowHideAddItem(String Status)
    {
        if(Status.equals("Add"))
        {
            txtfieldAIname.setEditable(false);
            txtfieldAIdesc.setEditable(false);
            txtfieldAIQiS.setEditable(false);
            txtfieldAIRL.setEditable(false);
            txtfieldAIRP.setEditable(false);
            btnAddAddItem.setEnabled(false);
            btnclearAI.setEnabled(false);
            btncancelAI.setEnabled(true);
            cmbAddsid.setEnabled(true);
        }else if (Status.equals("Cancel"))
        {
            txtfieldAIname.setEditable(true);
            txtfieldAIdesc.setEditable(true);
            txtfieldAIQiS.setEditable(true);
            txtfieldAIRL.setEditable(true);
            txtfieldAIRP.setEditable(true);
            btnAddAddItem.setEnabled(true);
            btnclearAI.setEnabled(true);
            btncancelAI.setEnabled(false);
            cmbAddsid.setEnabled(false);
            btnsaveall.setEnabled(false);
            txtfieldASitmid.setText("");
        }
    }
    

    
    private void ShowHideAddSupplier(String Status)
    {
        if(Status.equals("Add"))
        {
            txtfieldAddSnameS.setEditable(false);
            txtfieldAddaddressS.setEditable(false);
            txtfieldAddCNS.setEditable(false);
            txtfieldAddSRS.setEditable(false);
            txtfieldAddBNS.setEditable(false);
            txtfieldAddBANS.setEditable(false);
            btnAddAS.setEnabled(false);
            btnclearAS.setEnabled(false);
            btncancelAS.setEnabled(true);
            cmbAdditmid.setEnabled(true);
            btnsaveallS.setEnabled(true);
        }else if (Status.equals("Cancel"))
        {
            txtfieldAddSnameS.setEditable(true);
            txtfieldAddaddressS.setEditable(true);
            txtfieldAddCNS.setEditable(true);
            txtfieldAddSRS.setEditable(true);
            txtfieldAddBNS.setEditable(true);
            txtfieldAddBANS.setEditable(true);
            btnAddAS.setEnabled(true);
            btnclearAS.setEnabled(true);
            btncancelAS.setEnabled(false);
            cmbAdditmid.setEnabled(false);
            btnsaveallS.setEnabled(false);
            txtfieldASsid.setText("");
        }
    }
    
    
    
    private void ShowHideEditItem(String Status)
    {
        if(Status.equals("Edit"))
        {
            txtfieldEditname.setEditable(true);
            txtfieldEditdesc.setEditable(true);
            txtfieldEditRP.setEditable(true);
            btnEditedit.setEnabled(false);
            btnEditdelete.setEnabled(false);
            btnEditsave.setEnabled(true);
            btnEditcancel.setEnabled(true);   
            cmbEdititmid.setEnabled(false);
            btnEditclear.setEnabled(false);
        }else if (Status.equals("Cancel"))
        {
            txtfieldEditname.setEditable(false);
            txtfieldEditdesc.setEditable(false);
            txtfieldEditRP.setEditable(false);
            btnEditedit.setEnabled(true);
            btnEditdelete.setEnabled(true);
            btnEditsave.setEnabled(false);
            btnEditcancel.setEnabled(false); 
            cmbEdititmid.setEnabled(true);
            btnEditclear.setEnabled(true);
        }
    }
    
    private void ShowHideEditSupplier(String Status)
    {
        if(Status.equals("Edit"))
        {
            txtfieldEditSname.setEditable(true);
            txtfieldEditaddressS.setEditable(true);
            txtfieldEditCNS.setEditable(true);
            txtfieldEditSRS.setEditable(true);
            txtfieldEditBNS.setEditable(true);
            txtfieldEditBANS.setEditable(true);
            btneditsup.setEnabled(false);
            btncleareditsup.setEnabled(false);
            cmbEsid.setEnabled(false);
            btndeletesup.setEnabled(false);
            btncancelsup.setEnabled(true);
            btnsavesup.setEnabled(true);
        }else if (Status.equals("Cancel"))
        {
            txtfieldEditSname.setEditable(false);
            txtfieldEditaddressS.setEditable(false);
            txtfieldEditCNS.setEditable(false);
            txtfieldEditSRS.setEditable(false);
            txtfieldEditBNS.setEditable(false);
            txtfieldEditBANS.setEditable(false);
            btneditsup.setEnabled(true);
            btncleareditsup.setEnabled(true);
            cmbEsid.setEnabled(true);
            btndeletesup.setEnabled(true);
            btncancelsup.setEnabled(false);
            btnsavesup.setEnabled(false);
        }
    }
    
    private void pnlchangeItem()
    {
        ShowHideEditItem("Cancel");
        clearItemView();
        clearItemAddAll();
        clearItemEdit();
    }
    
    private void pnlchangeSupplier()
    {
        ShowHideEditSupplier("Cancel");
        clearSupplierView();
        clearSupplierAddAll();
        clearSupplierEdit();
    }
    
    private void pnlchangeInventory()
    {
        ShowHide("cancel");
        ShowHide2("cancel");
        clearInventoryView();
        clearInventoryEdit();
        clearInventoryAdd();
    }
    
    private void SetNameID(){
        lblIM_ID.setText(im.getUserID());
        lblIM_Name.setText("Welcome, "+im.getFirstName()+" "+im.getLastName()+"!");      
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChgPassError;
    private javax.swing.JPanel PnlSMDashboard;
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
    private javax.swing.JToggleButton ShowHidePass;
    private javax.swing.JButton btnAadd;
    private javax.swing.JButton btnAclear;
    private javax.swing.JButton btnAddAS;
    private javax.swing.JButton btnAddAddItem;
    private javax.swing.JButton btnAddAddSup;
    private javax.swing.JButton btnAddAdditmidS;
    private javax.swing.JButton btnEclear;
    private javax.swing.JButton btnEditcancel;
    private javax.swing.JButton btnEditclear;
    private javax.swing.JButton btnEditdelete;
    private javax.swing.JButton btnEditedit;
    private javax.swing.JButton btnEditsave;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnVSclear;
    private javax.swing.JButton btnVclear;
    private javax.swing.JButton btncancel;
    private javax.swing.JButton btncancelAI;
    private javax.swing.JButton btncancelAS;
    private javax.swing.JButton btncancelsip;
    private javax.swing.JButton btncancelsup;
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btnclearAI;
    private javax.swing.JButton btnclearAS;
    private javax.swing.JButton btnclearSID;
    private javax.swing.JButton btncleareditsup;
    private javax.swing.JButton btnclearitmS;
    private javax.swing.JButton btnclearstock;
    private javax.swing.JButton btnclearsup;
    private javax.swing.JButton btndeletesup;
    private javax.swing.JButton btndltsip;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btneditsip;
    private javax.swing.JButton btneditsup;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsaveall;
    private javax.swing.JButton btnsaveallS;
    private javax.swing.JButton btnsavesip;
    private javax.swing.JButton btnsavesup;
    private javax.swing.JButton btnupdatestock;
    private javax.swing.JComboBox<String> cmbAdditmid;
    private javax.swing.JComboBox<String> cmbAddsid;
    private javax.swing.JComboBox<String> cmbAitmid;
    private javax.swing.JComboBox<String> cmbAsid;
    private javax.swing.JComboBox<String> cmbEdititmid;
    private javax.swing.JComboBox<String> cmbEitmid;
    private javax.swing.JComboBox<String> cmbEsid;
    private javax.swing.JComboBox<String> cmbSID;
    private javax.swing.JComboBox<String> cmbVitmid;
    private javax.swing.JComboBox<String> cmbVsid;
    private javax.swing.JComboBox<String> cmbitmid;
    private javax.swing.JComboBox<String> cmbstock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
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
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblEitmstatus;
    private javax.swing.JLabel lblEitmstatus1;
    private javax.swing.JLabel lblEstatusstar;
    private javax.swing.JLabel lblEstatusstar1;
    private javax.swing.JLabel lblIM_ID;
    private javax.swing.JLabel lblIM_Name;
    private javax.swing.JLabel lblNexusLogo3;
    private javax.swing.JLabel lblitmstatus;
    private javax.swing.JLabel lblstatusstar;
    private javax.swing.JPanel pnlAI;
    private javax.swing.JPanel pnlAitm;
    private javax.swing.JPanel pnlAsup;
    private javax.swing.JPanel pnlEI;
    private javax.swing.JPanel pnlEitm;
    private javax.swing.JPanel pnlEsup;
    private javax.swing.JPanel pnlInventory;
    private javax.swing.JPanel pnlItems;
    private javax.swing.JPanel pnlProfile;
    private javax.swing.JPanel pnlStock;
    private javax.swing.JPanel pnlSuppliers;
    private javax.swing.JPanel pnlTopPanel3;
    private javax.swing.JTabbedPane pnlUI;
    private javax.swing.JPanel pnlVI;
    private javax.swing.JPanel pnlVitm;
    private javax.swing.JPanel pnlVsup;
    private javax.swing.JPanel pnladddetails;
    private javax.swing.JPanel pnladdsup;
    private javax.swing.JPanel pnladdsup1;
    private javax.swing.JPanel pnladdsup2;
    private javax.swing.JPanel pnleditQIS;
    private javax.swing.JPanel pnleditQIS1;
    private javax.swing.JPanel pnleditQIS2;
    private javax.swing.JPanel pnleditSID;
    private javax.swing.JTabbedPane pnlitm;
    private javax.swing.JPanel pnlitmd;
    private javax.swing.JPanel pnlitmd1;
    private javax.swing.JTabbedPane pnlsup;
    private javax.swing.JPanel pnlsupd;
    private javax.swing.JPanel pnlsupd1;
    private javax.swing.JPanel pnlsupd2;
    private javax.swing.JPanel pnlsupd3;
    private javax.swing.JPanel pnlsupd4;
    private javax.swing.JTabbedPane tabpnlIM;
    private javax.swing.JTable tblAIiup;
    private javax.swing.JTable tblASiup;
    private javax.swing.JTable tblAiup;
    private javax.swing.JTable tblEditItem;
    private javax.swing.JScrollPane tblEditItemscrollpanel;
    private javax.swing.JTable tblEsup;
    private javax.swing.JScrollPane tblEsupscrollpanel;
    private javax.swing.JTable tblStock;
    private javax.swing.JScrollPane tblStockscrollpanel;
    private javax.swing.JTable tblVitm;
    private javax.swing.JTable tblVsup;
    private javax.swing.JScrollPane tblVsupscrollpanel;
    private javax.swing.JTable tblinv;
    private javax.swing.JTable tblinventory;
    private javax.swing.JScrollPane tblinvscrollpanel;
    private javax.swing.JTable tblitmiup;
    private javax.swing.JTable tbliup;
    private javax.swing.JScrollPane tblscrollpanel;
    private javax.swing.JScrollPane tblscrollpanelitem;
    private javax.swing.JTable tblsid;
    private javax.swing.JTextField txtfieldABAN;
    private javax.swing.JTextField txtfieldABN;
    private javax.swing.JTextField txtfieldACN;
    private javax.swing.JTextField txtfieldAIQiS;
    private javax.swing.JTextField txtfieldAIRL;
    private javax.swing.JTextField txtfieldAIRP;
    private javax.swing.JTextField txtfieldAIdesc;
    private javax.swing.JTextField txtfieldAIitmid;
    private javax.swing.JTextField txtfieldAIname;
    private javax.swing.JTextField txtfieldAQiS;
    private javax.swing.JTextField txtfieldAQiSS;
    private javax.swing.JTextField txtfieldARL;
    private javax.swing.JTextField txtfieldARLS;
    private javax.swing.JTextField txtfieldARP;
    private javax.swing.JTextField txtfieldARPS;
    private javax.swing.JTextField txtfieldASID;
    private javax.swing.JTextField txtfieldASR;
    private javax.swing.JTextField txtfieldASitmid;
    private javax.swing.JTextField txtfieldASname;
    private javax.swing.JTextField txtfieldASsid;
    private javax.swing.JTextField txtfieldAaddress;
    private javax.swing.JTextField txtfieldAddBAN;
    private javax.swing.JTextField txtfieldAddBANS;
    private javax.swing.JTextField txtfieldAddBN;
    private javax.swing.JTextField txtfieldAddBNS;
    private javax.swing.JTextField txtfieldAddCN;
    private javax.swing.JTextField txtfieldAddCNS;
    private javax.swing.JTextField txtfieldAddSID;
    private javax.swing.JTextField txtfieldAddSIDS;
    private javax.swing.JTextField txtfieldAddSR;
    private javax.swing.JTextField txtfieldAddSRS;
    private javax.swing.JTextField txtfieldAddSname;
    private javax.swing.JTextField txtfieldAddSnameS;
    private javax.swing.JTextField txtfieldAddaddress;
    private javax.swing.JTextField txtfieldAddaddressS;
    private javax.swing.JTextField txtfieldAddunitprice;
    private javax.swing.JTextField txtfieldAddunitpriceS;
    private javax.swing.JTextField txtfieldAdesc;
    private javax.swing.JTextField txtfieldAdescS;
    private javax.swing.JTextField txtfieldAitmid;
    private javax.swing.JTextField txtfieldAitmidS;
    private javax.swing.JTextField txtfieldAname;
    private javax.swing.JTextField txtfieldAnameS;
    private javax.swing.JTextField txtfieldAunitprice;
    private javax.swing.JTextField txtfieldEQiS;
    private javax.swing.JTextField txtfieldERL;
    private javax.swing.JTextField txtfieldEdesc;
    private javax.swing.JTextField txtfieldEditBANS;
    private javax.swing.JTextField txtfieldEditBNS;
    private javax.swing.JTextField txtfieldEditCNS;
    private javax.swing.JTextField txtfieldEditRP;
    private javax.swing.JTextField txtfieldEditSRS;
    private javax.swing.JTextField txtfieldEditSname;
    private javax.swing.JTextField txtfieldEditaddressS;
    private javax.swing.JTextField txtfieldEditdesc;
    private javax.swing.JTextField txtfieldEdititmid;
    private javax.swing.JTextField txtfieldEditname;
    private javax.swing.JTextField txtfieldEitmid;
    private javax.swing.JTextField txtfieldEname;
    private javax.swing.JTextField txtfieldEsupplierid;
    private javax.swing.JTextField txtfieldEunitprice;
    private javax.swing.JTextField txtfieldPSstock;
    private javax.swing.JTextField txtfieldQiS;
    private javax.swing.JTextField txtfieldRL;
    private javax.swing.JTextField txtfieldVRP;
    private javax.swing.JTextField txtfieldVdesc;
    private javax.swing.JTextField txtfieldVitmid;
    private javax.swing.JTextField txtfieldVitmname;
    private javax.swing.JTextField txtfieldVsid;
    private javax.swing.JTextField txtfieldVsupBAN;
    private javax.swing.JTextField txtfieldVsupBN;
    private javax.swing.JTextField txtfieldVsupCN;
    private javax.swing.JTextField txtfieldVsupSR;
    private javax.swing.JTextField txtfieldVsupadd;
    private javax.swing.JTextField txtfieldVsupname;
    private javax.swing.JTextField txtfielddesc;
    private javax.swing.JTextField txtfielditmid;
    private javax.swing.JTextField txtfielditmidstock;
    private javax.swing.JTextField txtfielditmnamestock;
    private javax.swing.JTextField txtfieldname;
    private javax.swing.JTextField txtfieldpoidstock;
    private javax.swing.JTextField txtfieldpridstock;
    private javax.swing.JTextField txtfieldquantitystock;
    private javax.swing.JTextField txtfieldstatusstock;
    // End of variables declaration//GEN-END:variables

}
