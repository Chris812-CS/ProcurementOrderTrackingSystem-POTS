package assignment_oodj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;

public class PurchaseRequisite implements Data, Search{
    private String PR_ID;
    private String Request_TimeStamp;
    private String SM_ID;
    private String ITM_ID;
    private int Quantity;
    private String Status;
    private String App_Rej_TimeStamp;
    private String PM_ID;
    private PurchaseOrder po;

    public PurchaseRequisite() {
    }

    public PurchaseRequisite(String PR_ID, String Request_TimeStamp, String SM_ID, String ITM_ID, int Quantity, String Status, String App_Rej_TimeStamp, String PM_ID) {
        this.PR_ID = PR_ID;
        this.Request_TimeStamp = Request_TimeStamp;
        this.SM_ID = SM_ID;
        this.ITM_ID = ITM_ID;
        this.Quantity = Quantity;
        this.Status = Status;
        this.App_Rej_TimeStamp = App_Rej_TimeStamp;
        this.PM_ID = PM_ID;
        this.po = new PurchaseOrder();
    }
    
    public void setPOwList(ArrayList<PurchaseOrder> POList, ArrayList<PurchaseRequisite> PRList)
    {
        for(PurchaseOrder po: POList)
        {
            for(PurchaseRequisite pr: PRList)
            {
                if(po.getPR_ID().equals(pr.getPR_ID()))
                {
                    pr.po = po;
                }
            }
        }
    }
    
    public PurchaseRequisite ReturnPRwPO (ArrayList<PurchaseRequisite> PRList, String POID){
        PurchaseRequisite prreturn = null;
        for (PurchaseRequisite pr: PRList){
            if (POID.equals(pr.po.getPO_ID())){
                prreturn = pr;
            }
        }
        return prreturn;
    }



    public String getPR_ID() {return PR_ID;}
    public String getRequest_TimeStamp() {return Request_TimeStamp;}
    public String getSM_ID() {return SM_ID;}
    public String getITM_ID() {return ITM_ID;}
    public int getQuantity() {return Quantity;}
    public String getStatus() {return Status;}
    public String getApp_Rej_TimeStamp() {return App_Rej_TimeStamp;}
    public String getPM_ID() {return PM_ID;}

    public PurchaseOrder getPo() {
        return po;
    }

    public void setPo(PurchaseOrder po) {
        this.po = po;
    }
    

    public void setPR_ID(String PR_ID) {
        this.PR_ID = PR_ID;
    }
    public void setRequest_TimeStamp(String Request_TimeStamp) {
        this.Request_TimeStamp = Request_TimeStamp;
    }
    public void setSM_ID(String SM_ID) {
        this.SM_ID = SM_ID;
    }
    public void setITM_ID(String ITM_ID) {
        this.ITM_ID = ITM_ID;
    }
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
    public void setStatus(String Status) {
        this.Status = Status;
    }
    public void setApp_Rej_TimeStamp(String App_Rej_TimeStamp) {
        this.App_Rej_TimeStamp = App_Rej_TimeStamp;
    }
    public void setPM_ID(String PM_ID) {
        this.PM_ID = PM_ID;
    }
    
    public String AssignPR_ID(ArrayList<PurchaseRequisite> PRList){
        DecimalFormat df = new DecimalFormat ("PR000");
        int PRCount = 1;
        for (int i = 0; i < PRList.size();i++){
            String PRNo = PRList.get(i).PR_ID.substring(PRList.get(i).PR_ID.length() - 3);
            int PRInt = Integer.parseInt(PRNo);
            if (PRInt > PRCount){
                PRCount = PRInt;
            }
        }
        
        PRCount++;
        return df.format(PRCount);
    }
    

    @Override
    public String toString() {
        return PR_ID + "," + Request_TimeStamp + "," + SM_ID + "," + ITM_ID + "," + Quantity + "," + Status + "," + App_Rej_TimeStamp + "," + PM_ID;
    }
    
     @Override
    public Data newObject(String StrArray[]) {
        return new PurchaseRequisite(StrArray[0],StrArray[1],StrArray[2],StrArray[3],
                Integer.parseInt(StrArray[4]),StrArray[5],
                StrArray[6],StrArray[7]);
    }
    
    @Override
    public Object ReturnSpecificObject(ArrayList List, String ID){
        ArrayList<PurchaseRequisite> PRList = List;
        PurchaseRequisite pr = new PurchaseRequisite();
        for (PurchaseRequisite p: PRList){
            if(p.getPR_ID().equals(ID)){
                pr = p;
            }
        }
        return pr;
    }
    
    @Override
    public int ReturnSpecificIndex(ArrayList List, String ID){
        ArrayList<PurchaseRequisite> PRList = List;
        int i;
        for (i = 0; i < PRList.size(); i++){
            if (PRList.get(i).PR_ID.equals(ID)){
                break;
            }
        }
        return i;
    }
    
}
