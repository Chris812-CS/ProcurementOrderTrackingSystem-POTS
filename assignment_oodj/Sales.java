package assignment_oodj;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class Sales implements Data{
    private String Receipt_No;
    private String SM_ID;
    private String Receipt_TimeStamp;
    private String ITM_ID;
    private double Retail_Price;
    private int Quantity;
    private double Subtotal;
    private double Total;
    
    

    public Sales() {
    }

    public Sales(String Receipt_No, String SM_ID,String Receipt_TimeStamp, String ITM_ID, double Retail_Price, int Quantity, double Subtotal, double Total) {
        this.Receipt_No = Receipt_No;
        this.SM_ID = SM_ID;
        this.Receipt_TimeStamp = Receipt_TimeStamp;
        this.ITM_ID = ITM_ID;
        this.Retail_Price = Retail_Price;
        this.Quantity = Quantity;
        this.Subtotal = Subtotal;
        this.Total = Total;
    }

    public String getReceipt_No() {return Receipt_No;}
    public String getSM_ID() {return SM_ID;}
    public String getReceipt_TimeStamp() {return Receipt_TimeStamp;}
    public String getITM_ID() {return ITM_ID;}
    public double getRetail_Price() {return Retail_Price;}
    public int getQuantity() {return Quantity;}
    public double getSubtotal() {return Subtotal;}
    public double getTotal() {return Total;}

    public void setReceipt_No(String Receipt_No) {
        this.Receipt_No = Receipt_No;
    }
    public void setSM_ID(String SM_ID) {
        this.SM_ID = SM_ID;
    }
    public void setReceipt_TimeStamp(String Receipt_TimeStamp) {
        this.Receipt_TimeStamp = Receipt_TimeStamp;
    }
    public void setITM_ID(String ITM_ID) {
        this.ITM_ID = ITM_ID;
    }
    public void setRetail_Price(double Retail_Price) {
        this.Retail_Price = Retail_Price;
    }
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
    public void setSubtotal(double Subtotal) {
        this.Subtotal = Subtotal;
    }
    public void setTotal(double Total) {
        this.Total = Total;
    }
    

    public String AssignReceiptNumber(ArrayList<Sales> SalesList){
        DecimalFormat df = new DecimalFormat ("R0000000");
        int ReceiptCount = 1;
        for (int i = 0; i < SalesList.size();i++){
            String ReceiptNo = SalesList.get(i).Receipt_No.substring(SalesList.get(i).Receipt_No.length() - 7);
            int ReceiptInt = Integer.parseInt(ReceiptNo);
            if (ReceiptInt > ReceiptCount){
                ReceiptCount = ReceiptInt;
            }
        }
        ReceiptCount++;
        return df.format(ReceiptCount);
    }

    @Override
    public String toString() {
        return Receipt_No + "," + SM_ID + "," + Receipt_TimeStamp + "," + ITM_ID + "," + Retail_Price + "," + Quantity + "," + Subtotal + "," + Total;
    }
    
    @Override
    public Data newObject(String StrArray[]) {
        return new Sales(StrArray[0],StrArray[1],StrArray[2],StrArray[3],
                        Double.parseDouble(StrArray[4]),
                        Integer.parseInt(StrArray[5]),
                        Double.parseDouble(StrArray[6]),
                        Double.parseDouble(StrArray[7]));
    }
    
    
    
    
}
