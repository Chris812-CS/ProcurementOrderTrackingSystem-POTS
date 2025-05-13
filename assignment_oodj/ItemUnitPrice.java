package assignment_oodj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;


public class ItemUnitPrice extends ModifyDetails implements Data {
    private String SupplierID;
    private String ItemID;
    private double unitCost;
    public ItemUnitPrice() {
    }
    
    public ItemUnitPrice(String SupplierID, String ItemID, double unitCost) {
        this.SupplierID = SupplierID;
        this.ItemID = ItemID;
        this.unitCost = unitCost;
    }

    public void setSupplierID(String SupplierID) {
        this.SupplierID = SupplierID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public String getItemID() {
        return ItemID;
    }

    public double getUnitCost() {
        return unitCost;
    }

    @Override
    public String toString() {
        return SupplierID+","+ItemID+","+unitCost;
    }
    
    
    public void CheckItemExist(ArrayList<ItemUnitPrice> IUPList, ArrayList<Item> ItemList, ArrayList<Supplier> SupplierList)
    {
        int match = 0;
        Iterator<ItemUnitPrice> it = IUPList.iterator();
        while(it.hasNext())
        {
            // check item
            match = 0;
            ItemUnitPrice iup = it.next();
            for(Item i: ItemList)
            {
                if(i.getItemID().equals(iup.getItemID()) && i.getQuantityInStock() !=-1) 
                {match = 1;}
            }
            if(match == 0)
            {
                it.remove();
            }else{
                // check supplier
                match = 0;
                for(Supplier s: SupplierList)
                {
                    if(s.getSupplierID().equals(iup.getSupplierID()) && s.getSupplierRating() !=-1) 
                    {match = 1;}
                }
                if(match == 0)
                {
                    it.remove();
                }
            }
        }        
    }
   
    public ItemUnitPrice returnIUP (ArrayList<ItemUnitPrice> IUPList, String ITMID, String SUPID){
        ItemUnitPrice returniup = null;
        for (ItemUnitPrice iup: IUPList){
            if (iup.ItemID.equals(ITMID) && iup.SupplierID.equals(SUPID)){
                returniup = iup;
            }
        }
        return returniup;
    }

    @Override

    public Data newObject(String line[])
    {
        return new ItemUnitPrice(line[0],line[1],Double.parseDouble(line[2]));
    }
    

    @Override
    public void editRecord(ArrayList obj, Object Var[])
    {
        ArrayList<ItemUnitPrice> iup = obj;
        for(ItemUnitPrice j:iup)
        {
           if((j.getItemID().equals(Var[0])) && (j.getSupplierID().equals(Var[1])))
           {
               j.setUnitCost((Double)Var[2]);
           }
        }
    }
    
    @Override
    public void deleteRecord(ArrayList obj, Object Var[])
    {
        ArrayList<ItemUnitPrice> iup = obj;
        Iterator<ItemUnitPrice> it = iup.iterator();
        if((Var[0].equals("-")) || (Var[1].equals("-"))) // to remove the unitprice if item id or supplier is removed
        {
            while(it.hasNext())
            {
                ItemUnitPrice k = it.next();
                if((k.getSupplierID().equals(Var[0])) || (k.getItemID().equals(Var[1])))
                {
                    it.remove();
                }
            }
        } 
        // to remove unitprice when particular supplierid is selected
        else
        {
            while(it.hasNext())
            {
                ItemUnitPrice i = it.next();
                if((i.getSupplierID().equals(Var[0])) && (i.getItemID().equals(Var[1])))
                {
                    it.remove();
                }
            }
        }
        
        
    }
    
}