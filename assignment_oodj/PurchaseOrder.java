package assignment_oodj;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;


public class PurchaseOrder extends ModifyDetails implements Data, Search{
    private String PO_ID;
    private String PR_ID;
    private String PM_ID;
    private String FM_ID;
    private String Supplier_ID;
    private String PO_Creation_TimeStamp;
    private String FM_Approval_TimeStamp;
    private String Status;
    private int Quantity;
    private double Total;
    private String PaymentStatus;
    private String DeliveryStatus;
    private String Delivered_TimeStamp;
    private static final SimpleDateFormat tmstmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public PurchaseOrder() {}

    public PurchaseOrder(String PO_ID, String PR_ID, String PM_ID, String FM_ID, String Supplier_ID, String PO_Creation_TimeStamp, String FM_Approval_TimeStamp, String Status, int Quantity, double Total, String PaymentStatus, String DeliveryStatus, String Delivered_TimeStamp) {
        this.PO_ID = PO_ID;
        this.PR_ID = PR_ID;
        this.PM_ID = PM_ID;
        this.FM_ID = FM_ID;
        this.Supplier_ID = Supplier_ID;
        this.PO_Creation_TimeStamp = PO_Creation_TimeStamp;
        this.FM_Approval_TimeStamp = FM_Approval_TimeStamp;
        this.Status = Status;
        this.Quantity = Quantity;
        this.Total = Total;
        this.PaymentStatus = PaymentStatus;
        this.DeliveryStatus = DeliveryStatus;
        this.Delivered_TimeStamp = Delivered_TimeStamp;
    }

    public String getPO_ID() {return PO_ID;}
    public String getPR_ID() {return PR_ID;}
    public String getPM_ID() {return PM_ID;}
    public String getFM_ID() {return FM_ID;}
    public String getSupplier_ID() {return Supplier_ID;}
    public String getPO_Creation_TimeStamp() {return PO_Creation_TimeStamp;}
    public String getFM_Approval_TimeStamp() {return FM_Approval_TimeStamp;}
    public String getStatus() {return Status;}
    public int getQuantity() {return Quantity;}
    public double getTotal() {return Total;}
    public String getPaymentStatus() {return PaymentStatus;}
    public String getDeliveryStatus() {return DeliveryStatus;}
    public String getDelivered_TimeStamp() {return Delivered_TimeStamp;}

    public void setPO_ID(String PO_ID) {
        this.PO_ID = PO_ID;
    }
    public void setPR_ID(String PR_ID) {
        this.PR_ID = PR_ID;
    }
    public void setPM_ID(String PM_ID) {
        this.PM_ID = PM_ID;
    }
    public void setFM_ID(String FM_ID) {
        this.FM_ID = FM_ID;
    }
    public void setSupplier_ID(String Supplier_ID) {
        this.Supplier_ID = Supplier_ID;
    }
    public void setPO_Creation_TimeStamp(String PO_Creation_TimeStamp) {
        this.PO_Creation_TimeStamp = PO_Creation_TimeStamp;
    }
    public void setFM_Approval_TimeStamp(String FM_Approval_TimeStamp) {
        this.FM_Approval_TimeStamp = FM_Approval_TimeStamp;
    }
    public void setStatus(String Status) {
        this.Status = Status;
    }
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
    public void setTotal(double Total) {
        this.Total = Total;
    }
    public void setPaymentStatus(String PaymentStatus) {
        this.PaymentStatus = PaymentStatus;
    }
    public void setDeliveryStatus(String DeliveryStatus) {
        this.DeliveryStatus = DeliveryStatus;
    }
    public void setDelivered_TimeStamp(String Delivered_TimeStamp) {
        this.Delivered_TimeStamp = Delivered_TimeStamp;
    }
    


    @Override
    public String toString() {
        return PO_ID + "," + PR_ID + "," + PM_ID + "," + FM_ID + "," + 
                Supplier_ID + "," + PO_Creation_TimeStamp + "," + FM_Approval_TimeStamp + "," + 
                Status + "," + Quantity + "," + Total + "," + PaymentStatus + "," + DeliveryStatus + "," + Delivered_TimeStamp;
    }
    
    @Override
    public Data newObject(String StrArray[]) {
        return new PurchaseOrder(StrArray[0],StrArray[1],StrArray[2],StrArray[3],StrArray[4],StrArray[5],StrArray[6],StrArray[7],
                        Integer.parseInt(StrArray[8]), Double.parseDouble(StrArray[9]),
                        StrArray[10],StrArray[11],StrArray[12]);
    }
    
    @Override
    public void editRecord(ArrayList obj, Object[] Var) 
    {
        ArrayList<PurchaseOrder> polist = obj;
        for(PurchaseOrder po: polist)
        {
            if(po.getPO_ID().equals(Var[0]))
            {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                po.setDeliveryStatus("Delivered");
                System.out.println("Changed Status");
                po.setDelivered_TimeStamp(tmstmp.format(timestamp));
            }
        }
    }
    
    @Override
    public Object ReturnSpecificObject(ArrayList List, String ID){
        ArrayList<PurchaseOrder> POList = List;
        PurchaseOrder po = new PurchaseOrder();
        for (PurchaseOrder p: POList){
            if(p.getPO_ID().equals(ID)){
                po = p;
            }
        }
        return po;
    }
    
    @Override
    public int ReturnSpecificIndex(ArrayList List, String ID){
        ArrayList<PurchaseOrder> POList = List;
        int i;
        for (i = 0; i < POList.size(); i++){
            if (POList.get(i).PO_ID.equals(ID)){
                break;
            }
        }
        return i;
    }
    
}
