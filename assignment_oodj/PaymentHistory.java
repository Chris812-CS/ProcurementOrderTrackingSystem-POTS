package assignment_oodj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PaymentHistory implements Data{
    private String PaymentID;
    private String PO_ID;
    private String PaymentTimeStamp;
    private double PaymentAmount;

    public PaymentHistory() {
    }

    public PaymentHistory(String PaymentID, String PO_ID, String PaymentTimeStamp, double PaymentAmount) {
        this.PaymentID = PaymentID;
        this.PO_ID = PO_ID;
        this.PaymentTimeStamp = PaymentTimeStamp;
        this.PaymentAmount = PaymentAmount;
    }

    public String getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(String PaymentID) {
        this.PaymentID = PaymentID;
    }

    public String getPO_ID() {
        return PO_ID;
    }

    public void setPO_ID(String PO_ID) {
        this.PO_ID = PO_ID;
    }

    public String getPaymentTimeStamp() {
        return PaymentTimeStamp;
    }

    public void setPaymentTimeStamp(String PaymentTimeStamp) {
        this.PaymentTimeStamp = PaymentTimeStamp;
    }

    public double getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(double PaymentAmount) {
        this.PaymentAmount = PaymentAmount;
    }
    
    @Override
    public String toString() {
        return PaymentID + "," + PO_ID + "," + PaymentTimeStamp + "," + PaymentAmount;
    }

    public Data newObject(String Line[]) {
       return new PaymentHistory(Line[0], Line[1], Line[2], Double.parseDouble(Line[3]));}  
}
