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
public class Purchase_Manager extends User {
        static int count = 0;
        
        public Purchase_Manager(){};
        public Purchase_Manager(User temp){
            super(temp.getUserType(),temp.getUserID(),temp.getFirstName(),
                temp.getLastName(),temp.getGender(),temp.getEmail(),
                temp.getContactNo(),temp.getPassword());

         count = count + 1;
    }
    
    @Override
    public void Start(){
        //start program and pass in current object
        System.out.println("Purchase");
        System.out.println(this);
        new PM_dashboard(this);
        
    }


    Item item = new Item();
    public int ReturnItemIndex(ArrayList<Item> ItemList, String FindItemID){
        return item.ReturnSpecificIndex(ItemList, FindItemID);
    }
    
    public boolean CheckItemValid(ArrayList<Item> ItemList, String AddItemID){
        return item.CheckItemValid(ItemList, AddItemID);
    }
    
    PurchaseRequisite PR = new PurchaseRequisite();
    public String AssignPR_ID(ArrayList<PurchaseRequisite> PRList){
        return PR.AssignPR_ID(PRList);
    }
    
    public int ReturnPRIndex(String PRID, ArrayList<PurchaseRequisite> PRList){
        return PR.ReturnSpecificIndex(PRList,PRID);
    }
    
    PurchaseOrder PO = new PurchaseOrder();
    public int ReturnPOIndex(String POID, ArrayList<PurchaseOrder> POList){
        return PO.ReturnSpecificIndex(POList,POID);
    }
    
    
}
