/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_oodj;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Sales_Manager extends User {
        Item item;
        Sales sales;
        PurchaseRequisite pr;
        PurchaseOrder po;
        Supplier sup;
        ItemUnitPrice iup;

        FileOperations<Item> itmfop = new FileOperations<>();
        FileOperations<PurchaseRequisite> prfop = new FileOperations<>();
        FileOperations<PurchaseOrder> pofop = new FileOperations<>();
        FileOperations<Sales> salesfop = new FileOperations<>();
        FileOperations<Supplier> supfop = new FileOperations<>();
        FileOperations<ItemUnitPrice> iupfop = new FileOperations<>();

        //Read Item.txt
        public ArrayList<Item> ItemList = itmfop.ReadFile("src/data/Item.txt", new Item());
        //Read PurchaseRequisite.txt
        public ArrayList<PurchaseRequisite> PRList = prfop.ReadFile("src/data/PurchaseRequisite.txt", new PurchaseRequisite());
        //Read PurchaseOrder.txt
        public ArrayList<PurchaseOrder> POList = pofop.ReadFile("src/data/PurchaseOrder.txt",new PurchaseOrder());
        //Read Sales.txt
        public ArrayList<Sales> SalesList = salesfop.ReadFile("src/data/Sales.txt", new Sales());
        //Read Supplier.txt
        public ArrayList<Supplier> SupList = supfop.ReadFile("src/data/Supplier.txt", new Supplier());
        //Read ItemUnitPrice.txt
        public ArrayList<ItemUnitPrice> IUPList = iupfop.ReadFile("src/data/ItemUnitPrice.txt", new ItemUnitPrice());



        static int count = 0;
        
        public Sales_Manager(){};
        public Sales_Manager(User temp){
                    super(temp.getUserType(),temp.getUserID(),temp.getFirstName(),
                temp.getLastName(),temp.getGender(),temp.getEmail(),
                temp.getContactNo(),temp.getPassword());
         count = count + 1;
        pr = new PurchaseRequisite();
        sales= new Sales();
        item = new Item();
        sup = new Supplier();
        po = new PurchaseOrder();
        iup = new ItemUnitPrice();
    }
    
    @Override
    public void Start(){
        //start program and pass in current object
        System.out.println("Sales");
        System.out.println(this);
        new SM_Program(this);
        
    }

 
    public int ReturnItemIndex(ArrayList<Item> ItemList, String FindItemID){
        return item.ReturnSpecificIndex(ItemList,FindItemID);
    }
    
    public boolean CheckItemValid(ArrayList<Item> ItemList, String AddItemID){
        return item.CheckItemValid(ItemList, AddItemID);
    }
    
    public String AssignReceiptNumber(ArrayList<Sales> SalesList){
        return sales.AssignReceiptNumber(SalesList);
    }
    
    public String AssignPR_ID(ArrayList<PurchaseRequisite> PRList){
        return pr.AssignPR_ID(PRList);
    }
    
    public int ReturnPRIndex(String PRID, ArrayList<PurchaseRequisite> PRList){
        return pr.ReturnSpecificIndex(PRList,PRID);
    }
    
    public void setPOwList (ArrayList<PurchaseOrder> POList, ArrayList<PurchaseRequisite> PRList){
        pr.setPOwList(POList,PRList);
    }
    
    public PurchaseRequisite ReturnPRwPO (ArrayList<PurchaseRequisite> PRList, String POID){
        return pr.ReturnPRwPO(PRList, POID);
    }
    
    
    public int ReturnPOIndex(String POID, ArrayList<PurchaseOrder> POList){
        return po.ReturnSpecificIndex(POList, POID);
    }
    
    public int ReturnSupIndex(String SUPID, ArrayList<Supplier> SupList){
        return sup.ReturnSpecificIndex(SupList,SUPID);
    }
    
    public ItemUnitPrice returnIUP (ArrayList<ItemUnitPrice> IUPList, String ITMID, String SUPID){
        return iup.returnIUP(IUPList,ITMID,SUPID);
    }
      
}
