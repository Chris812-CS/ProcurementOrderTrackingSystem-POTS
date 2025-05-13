/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_oodj;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author User
 */
public class Finance_Manager extends User{
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
        private ArrayList<ItemUnitPrice> ItemUnitPriceList = unitprice.ReadFile("src/data/ItemUnitPrice.txt", new ItemUnitPrice());
        private ArrayList<Supplier> SupplierList = supplier.ReadFile("src/data/Supplier.txt", new Supplier());
        private ArrayList<PaymentHistory> HistoryList = history.ReadFile("src/data/PaymentHistory.txt", new PaymentHistory());

        Item item = new Item();
        Sales sale = new Sales();
        PurchaseRequisite PR = new PurchaseRequisite();
        PurchaseOrder PO = new PurchaseOrder();
        Supplier sup = new Supplier();
        ItemUnitPrice iup = new ItemUnitPrice();

        private HashMap<String, String> ids = new HashMap<String, String>();
        private HashSet<String> InventoryList = new HashSet<>();
        private HashSet<String> Approvelist = new HashSet<>();
        private HashSet<String> SupplierRecordList = new HashSet<>();

        private int totalDeliveryDays = 0;
        private int numberOfDeliveredOrders = 0;
        private int averageDays = 0;

        static int count = 0;
        
        public Finance_Manager(){};
        public Finance_Manager(User temp){
                    super(temp.getUserType(),temp.getUserID(),temp.getFirstName(),
                temp.getLastName(),temp.getGender(),temp.getEmail(),
                temp.getContactNo(),temp.getPassword());
         count = count + 1;
    }
    
    @Override
    public void Start(){
        //start program and pass in current object
        System.out.println("Finance");
        System.out.println(this);
        new FM_Program(this);
        
    }

    
    public int ReturnItemIndex(ArrayList<Item> ItemList, String FindItemID){
        return item.ReturnSpecificIndex(ItemList, FindItemID);
    }
    
    public boolean CheckItemValid(ArrayList<Item> ItemList, String AddItemID){
        return item.CheckItemValid(ItemList, AddItemID);
    }
    
    public String AssignReceiptNumber(ArrayList<Sales> SalesList){
        return sale.AssignReceiptNumber(SalesList);
    }
    
    public String AssignPR_ID(ArrayList<PurchaseRequisite> PRList){
        return PR.AssignPR_ID(PRList);
    }
    
    public int ReturnPRIndex(String PRID, ArrayList<PurchaseRequisite> PRList){
        return PR.ReturnSpecificIndex( PRList,PRID);
    }
    
    public int ReturnPOIndex(String POID, ArrayList<PurchaseOrder> POList){
        return PO.ReturnSpecificIndex( POList,POID);
    }
    
    public int ReturnSupIndex(String SupID, ArrayList<Supplier> SupplierList){
        return sup.ReturnSpecificIndex( SupplierList,SupID);
    }
    
    public PurchaseRequisite ReturnPRwPO (ArrayList<PurchaseRequisite> PRList, String POID){
        return PR.ReturnPRwPO(PRList, POID);
    }
    
    public ItemUnitPrice returnIUP (ArrayList<ItemUnitPrice> IUPList, String ITMID, String SUPID){
        return iup.returnIUP(IUPList,ITMID,SUPID);
    }
    
    public void setPOwList(ArrayList<PurchaseOrder> POList, ArrayList<PurchaseRequisite> PRList){
        PR.setPOwList(POList,PRList);
    }
    
    public HashMap<String, String> getDatas(String poNumber) {
        
        for(PurchaseOrder p: POList){
            if (p.getPO_ID().equals(poNumber)) {            
            ids.put("PR_ID", p.getPR_ID());
            ids.put("Supplier_ID", p.getSupplier_ID());
            ids.put("PO_Creation_TimeStamp", p.getPO_Creation_TimeStamp());
            ids.put("FM_Approval_TimeStamp", p.getFM_Approval_TimeStamp());
            ids.put("Quantity", String.valueOf(p.getQuantity()));
            ids.put("Total", String.valueOf(p.getTotal()));
            ids.put("Delivery_Status", p.getDeliveryStatus());
            ids.put("Delivered_TimeStamp", p.getDelivered_TimeStamp());

            }

        for(PurchaseRequisite r: PRList){
            if (r.getPR_ID().equals(ids.get("PR_ID"))) {
                ids.put("Item_ID", r.getITM_ID());
            }
        }
        
        for(Item i: ItemList){
            if(i.getItemID().equals(ids.get("Item_ID"))){
                ids.put("Item_Name", i.getItemName());
                ids.put("Item_Description", i.getDescription());
                ids.put("Quantity_In_Stock", String.valueOf(i.getQuantityInStock()));
                ids.put("Reorder_Level", String.valueOf(i.getReorderLevel()));
                ids.put("Retail_Price", String.valueOf(i.getRetailPrice()));
            }
        }
        
        for(ItemUnitPrice unit: ItemUnitPriceList){
            if(unit.getItemID().equals(ids.get("Item_ID")) && unit.getSupplierID().equals(ids.get("Supplier_ID"))){
                ids.put("Unit_Cost", String.valueOf(unit.getUnitCost()));
            }
        }
        
        for(Supplier s: SupplierList){
            if(s.getSupplierID().equals(ids.get("Supplier_ID"))){
                ids.put("Supplier_Name", s.getSupplierName());
                ids.put("Address", s.getAddress());
                ids.put("Contact_Number", s.getContactNumber());
                ids.put("Supplier_Rating", String.valueOf(s.getSupplierRating()));
                ids.put("Bank_Name", s.getBankName());
                ids.put("Bank_Account", s.getBankAccountNo()); 
            }
        }
        
        for(PaymentHistory h: HistoryList){
            if(h.getPO_ID().equals(poNumber)){
                ids.put("Payment_ID", h.getPaymentID());
                ids.put("Payment_TimeStamp", h.getPaymentTimeStamp());
                ids.put("Payment_Amount", String.valueOf(h.getPaymentAmount()));
            }
        }
        
        for(Sales record: SalesList){
            if(record.getITM_ID().equals(ids.get("Item_ID"))){
                ids.put("Receipt_No", record.getReceipt_No());
            }
        }
    }
        
    return ids;
}
    
    public HashSet<String> List(){
        for (PurchaseOrder order : POList) {
            String PoID = order.getPO_ID();
            String status = order.getFM_Approval_TimeStamp();
            
            if(status.equals("Pending")){
                Approvelist.add(PoID);
            }
        }
        
        return Approvelist;
    }
    
    public HashSet<Object> List(String SupplierID){
    HashSet<Object> deliveredOrders = new HashSet<>();
    
    
    String poNo;
        for (PurchaseOrder p: POList){
            if(p.getSupplier_ID().equals(SupplierID)){
                poNo = p.getPO_ID();
                
                getDatas(poNo); 
                if(ids.get("Delivery_Status").equals("Delivered")){
                    LocalDate Delivery = LocalDateTime.parse(ids.get("Delivered_TimeStamp"),dateTimeFormatter).toLocalDate();
                    LocalDate Approval = LocalDateTime.parse(ids.get("FM_Approval_TimeStamp"),dateTimeFormatter).toLocalDate();
                    
                    int deliverydays = Period.between(Approval, Delivery).getDays();
                    this.totalDeliveryDays += deliverydays;  
                    this.numberOfDeliveredOrders++;
                    
                    Object[] row = {ids.get("Item_ID"), ids.get("Item_Name"), ids.get("Quantity"), ("RM" + ids.get("Total") + "0"), deliverydays};
                    deliveredOrders.add(row);
                }
            }
        }        
        return deliveredOrders;
    }
    
    public HashSet<Object> PaymentHistoryList(){
        HashSet<Object> Histories = new HashSet<>();
        
        for(PaymentHistory p: HistoryList){
           getDatas(p.getPO_ID());
           Object[] row = {p.getPaymentID(), ids.get("Supplier Name"), p.getPaymentTimeStamp(), p.getPaymentAmount()};
           Histories.add(row);
        }
        
        return Histories;
    };

    public int getTotalDeliveryDays() {
        return totalDeliveryDays;
    }

    public int getNumberOfDeliveredOrders() {
        return numberOfDeliveredOrders;
    }

    public int getAverageDays() {
        return averageDays;
    }

    public void setAverageDays(int averageDays) {
        this.averageDays = totalDeliveryDays / numberOfDeliveredOrders;
    }
    
    public HashSet<String> getInventoryList() {   
        for(Item i: ItemList){
            if(i.getQuantityInStock() >= 0){
                InventoryList.add(i.getItemID());
            }
        }
        
        return InventoryList;
    }
    
    public double[] calculation(String itemID) {
        System.out.println(itemID);
        
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define the time periods
        LocalDateTime sixtyDaysAgo = now.minusDays(60);
        LocalDateTime thirtyDaysAgo = now.minusDays(30);
        
        System.out.println("60: " + sixtyDaysAgo);
        System.out.println("30: " + thirtyDaysAgo);

        double totalitemsales = 0;
        double totalsales = 0;
        double pastsales = 0;
        double pastitemsales = 0;
        
        for (Sales record : SalesList) {
            String date = record.getReceipt_TimeStamp();

        try {
            LocalDateTime recordDate = LocalDateTime.parse(date, dateTimeFormatter);

            // Check if the record date is between 60 days and 30 days ago
            if (recordDate.isAfter(sixtyDaysAgo) && recordDate.isBefore(thirtyDaysAgo)) {
                pastsales += record.getSubtotal();
                if (record.getITM_ID().equals(itemID)) {
                    pastitemsales += record.getSubtotal();
                }
            }

            // Check if the record date is within the last 30 days
            if (recordDate.isAfter(thirtyDaysAgo)) {
                totalsales += record.getSubtotal();
                if (record.getITM_ID().equals(itemID)) {
                    totalitemsales += record.getSubtotal();
                }
                System.out.println("SalesID: " + record.getReceipt_No());
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace(); // Handle parse exception
        }
    }
    
     //totalitemsales - totalsales
    double percentage = (totalitemsales / totalsales) * 100;
        
    //pastitemsales - pastsales
    double pastpercentage = (pastitemsales / pastsales) * 100;
        
    double percentageResult = percentage - pastpercentage;
    
    return new double[] {percentageResult, percentage};
    }
    
    public void updateDecision(String IDSelected, String FMID, String TimeStamp, Double totalAmount){        
        for(PurchaseOrder order : POList){
            if(order.getPO_ID().equals(IDSelected)){
                {
                 order.setFM_ID(FMID);
                 order.setFM_Approval_TimeStamp(TimeStamp);
                 order.setStatus("Approved");
                 order.setPaymentStatus("Paid");
                 order.setDeliveryStatus("Pending");
                 order.setDelivered_TimeStamp("Pending");
                 break;
                }

            }
        }
        
        po.WriteFile("src/data/PurchaseOrder.txt", POList);
        
        String newID;
        if (!HistoryList.isEmpty()) {
            String lastID = HistoryList.get(HistoryList.size() - 1).getPaymentID();
            int numericPart = Integer.parseInt(lastID.substring(2)); 
            newID = String.format("PH%03d", numericPart + 1); 
        } else {
            newID = "PH001"; // Start from the first ID if the list is empty
        }


        HistoryList.add(new PaymentHistory(newID, IDSelected, TimeStamp, totalAmount));
        history.WriteFile("src/data/PaymentHistory.txt", HistoryList);        
    }
    
    public void updateDecision(String IDSelected, String FMID){        
        for(PurchaseOrder order : POList){
            if(order.getPO_ID().equals(IDSelected)){
                {
                 order.setFM_ID(FMID);
                 order.setFM_Approval_TimeStamp("Rejected");
                 order.setStatus("Rejected");
                 order.setPaymentStatus("Rejected");
                 order.setDeliveryStatus("Rejected");
                 order.setDelivered_TimeStamp("Rejected");
                 break;
                }

            }
        }
        
        po.WriteFile("src/data/PurchaseOrder.txt", POList);
    }
    
    public HashSet<Object> LoadInventory()
    {
        HashSet<Object> Inventory = new HashSet<>();
        
        for(Item i:ItemList)
        {
            if(i.getQuantityInStock()>=0)
            {
                Object[] row = {i.getItemID(), i.getItemName(), i.getDescription(), 
                                i.getQuantityInStock(), i.getReorderLevel()};
                Inventory.add(row);
            }
        }
        return Inventory;
    }
    
    
    
}
