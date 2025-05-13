package assignment_oodj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Supplier extends ModifyDetails implements  Data,Search {
    private String SupplierID;
    private String SupplierName;
    private String Address;
    private String ContactNumber;
    private double SupplierRating;
    private String BankName;
    private String BankAccountNo;

    public Supplier() {
    }

    public Supplier(String SupplierID, String SupplierName, String Address, String ContactNumber, double SupplierRating, String BankName, String BankAccountNo) {
        this.SupplierID = SupplierID;
        this.SupplierName = SupplierName;
        this.Address = Address;
        this.ContactNumber = ContactNumber;
        this.SupplierRating = SupplierRating;
        this.BankName = BankName;
        this.BankAccountNo = BankAccountNo;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String SupplierID) {
        this.SupplierID = SupplierID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String SupplierName) {
        this.SupplierName = SupplierName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }

    public double getSupplierRating() {
        return SupplierRating;
    }

    public void setSupplierRating(double SupplierRating) {
        this.SupplierRating = SupplierRating;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getBankAccountNo() {
        return BankAccountNo;
    }

    public void setBankAccountNo(String BankAccountNo) {
        this.BankAccountNo = BankAccountNo;
    }

    
    @Override
    public Object ReturnSpecificObject(ArrayList List, String ID)
    {
        ArrayList<Supplier> SupplierList = List;
        Supplier sup = new Supplier();
        for(Supplier s: SupplierList)
        {
            if(s.getSupplierID().equals(ID))
            {
                sup = s;
            }
        }
        return sup;
    }
    
    
   
    @Override
    public int ReturnSpecificIndex(ArrayList List, String ID)
    {
        ArrayList<Supplier> SupplierList = List;
        int i;
        for (i = 0; i < SupplierList.size(); i++){
            if (SupplierList.get(i).SupplierID.equals(ID)){
                break;
            }
        }
        return i;
    }
    
    
    
    @Override
    public String toString() {
        return SupplierID+ ","+SupplierName+ ","+Address+ ","+ContactNumber+ ","+SupplierRating+ ","+BankName+ ","+BankAccountNo;
    }

    
    @Override

    public Data newObject(String line[])
    {
        return new Supplier(line[0],line[1],line[2],line[3], Double.parseDouble(line[4]), line[5], line[6]);
    }
        
    
    

    @Override
    public void editRecord(ArrayList obj, Object Var[])
    {
        ArrayList<Supplier> SupplierList = obj;
        for(Supplier s: SupplierList)
        {
            if(s.getSupplierID().equals(Var[0]))
            {
                s.setSupplierName((String)Var[1]);
                s.setAddress((String)Var[2]);
                s.setContactNumber((String)Var[3]);
                s.setSupplierRating((Double)Var[4]);
                s.setBankName((String)Var[5]);
                s.setBankAccountNo((String)Var[6]);
            }
        }
    }
    @Override
    public void deleteRecord(ArrayList obj, Object Var[])
    {
        ArrayList<Supplier> SupplierList = obj;
        for(Supplier s: SupplierList)
        {
            if(s.getSupplierID().equals(Var[0]))
            {
                s.setSupplierRating(-1);
            }
        }
    }   
}
