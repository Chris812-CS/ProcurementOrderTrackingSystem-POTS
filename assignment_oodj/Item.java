package assignment_oodj;

import java.util.ArrayList;

public class Item extends ModifyDetails implements  Data, Search{
    private String ItemID;
    private String ItemName;
    private String Description;
    private int QuantityInStock;
    private int ReorderLevel;
    private double RetailPrice;

    public String getItemID() {
        return ItemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getDescription() {
        return Description;
    }

    public int getQuantityInStock() {
        return QuantityInStock;
    }

    public int getReorderLevel() {
        return ReorderLevel;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public void setItemName(String ItemName) {
        if(ItemName.equals("-")){}
        else{this.ItemName = ItemName;}
    }

    public void setDescription(String Description) {
        if(Description.equals("-")){}
        else{this.Description = Description;}
    }

    public void setQuantityInStock(int QuantityInStock) {
        if(QuantityInStock == -2){}
        else{this.QuantityInStock = QuantityInStock;}
    }

    public void setReorderLevel(int ReorderLevel) {
        if(ReorderLevel == -2){}
        else{this.ReorderLevel = ReorderLevel;}
    }

    public void setRetailPrice(double RetailPrice) {
        if(RetailPrice == -2.0){}
        else{this.RetailPrice = RetailPrice;}
    }

    

    public Item() {
    }

    public Item(String ItemID, String ItemName, String Description, int QuantityInStock, int ReorderLevel, double RetailPrice) {
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.Description = Description;
        this.QuantityInStock = QuantityInStock;
        this.ReorderLevel = ReorderLevel;
        this.RetailPrice = RetailPrice;
    }


    public boolean CheckItemValid(ArrayList<Item> ItemList, String AddItemID){
        for (Item item: ItemList){
            if (item.ItemID.equals(AddItemID)){
                return(true);
            }
        }
        return(false);
    }
    
    
    
    @Override
    public int ReturnSpecificIndex(ArrayList List, String ID){
        ArrayList<Item> ItemList = List;
        int i;
        for (i = 0; i < ItemList.size(); i++){
            if (ItemList.get(i).ItemID.equals(ID)){
                break;
            }
        }
        return i;
    }
    
    @Override
    public Object ReturnSpecificObject(ArrayList List, String ID)
    {
        ArrayList<Item> ItemList = List;
        Item itm = new Item();
        for(Item i: ItemList)
        {
            if(i.getItemID().equals(ID))
            {
                itm = i;
            }
        }
        return itm;
    }
    

    @Override
    public String toString() {
        return ItemID +","+ItemName+","+Description+","+QuantityInStock+","+ReorderLevel+","+RetailPrice;
    }

    @Override

    public Data newObject(String line[])
    {
        return new Item(line[0],line[1],line[2],Integer.parseInt(line[3]),Integer.parseInt(line[4]),Double.parseDouble(line[5]));
    }
    

    @Override
    public void editRecord(ArrayList obj, Object Var[])
    {
        ArrayList<Item> itmlist = obj;
        for(Item j: itmlist)
        {
            if(j.getItemID().equals(Var[0]))
            {
                j.setItemName((String)Var[1]);
                j.setDescription((String)Var[2]);
                j.setQuantityInStock((Integer)(Var[3]));
                j.setReorderLevel((Integer)(Var[4]));
                j.setRetailPrice((Double)(Var[5]));
            }
        }
        
    }
    @Override
    public void deleteRecord(ArrayList obj, Object Var[])
    {
        ArrayList<Item> itmlist = obj;
        for(Item j: itmlist)
        {
            if(j.getItemID().equals(Var[0]))
            {
                j.setQuantityInStock(-1);
            }
        }
    }
}
