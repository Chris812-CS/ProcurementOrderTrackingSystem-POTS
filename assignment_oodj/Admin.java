/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_oodj;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Admin extends User {
    static int count = 0;
    private FileOperations<User> userfile = new FileOperations<>();
    
    
    public Admin(){
    
    }
    public Admin(User temp){     
        super(temp.getUserType(),temp.getUserID(),temp.getFirstName(),
                temp.getLastName(),temp.getGender(),temp.getEmail(),
                temp.getContactNo(),temp.getPassword());
         count = count +1 ;
    }
    
    @Override
    public void Start(){
        //start program and pass in current object
        System.out.println(this.getFirstName());
        new Admin_Program(this);
        System.out.println("Admin");
        System.out.println(this);
        
    }

    
    
    public void Register(User user,String FN, String LN, String Email, String Contact, JRadioButton Male, JRadioButton Female, String ID, JComboBox<String> usertype, String password, ArrayList<JLabel> Errors){
        int error;
         if(FN.isBlank() || LN.isBlank() || Email.isBlank() || Contact.isBlank() || (!Male.isSelected() && !Female.isSelected()) || ID.isBlank()){            
            Errors.get(5).setText("Make sure all fields are filled accordingly");    
            error = CheckErrors(FN, LN, Email, Contact, Male, Female,ID, usertype,password,Errors);
    
        }else{
              error = CheckErrors(FN, LN, Email, Contact, Male, Female,ID, usertype,password,Errors);
              Errors.get(5).setText("");
         }
         if(error == 1 ){
             //user.userType = null;
             user.setUserType(null);
            return;
         }else{            
            //user.userID = ID;
            user.setUserID(ID);
            if(usertype.getSelectedItem().equals("Admin")){
                //user.userType = "A";
                user.setUserType("A");
            }else if(usertype.getSelectedItem().equals("Sales Manager (SM)")){
                //user.userType = "SM";
                user.setUserType("SM");
            }else if(usertype.getSelectedItem().equals("Purchase Manager (PM)")){
                //user.userType = "PM";
                user.setUserType("PM");
            }else if(usertype.getSelectedItem().equals("Inventory Manager (IM)")){
                //user.userType = "IM";
                user.setUserType("IM");
            }else if(usertype.getSelectedItem().equals("Finance Manager (FM)")){
                //user.userType = "FM";
                user.setUserType("FM");
            }
            //user.firstName = FN;
            user.setFirstName(FN);
            //user.lastName = LN;
            user.setLastName(LN);
            //user.contactNo =Contact;
            user.setContactNo(Contact);
            if(Male.isSelected()){
                //user.gender = "M";
                user.setGender("M");
                
            }else{
                //user.gender = "F";
                user.setGender("F");
            }            
            //user.email = Email;
            user.setEmail(Email);
            //user.password = password;
            user.setPassword(password);
             
             
         }
         
    }
    
    
    private int CheckErrors(String FN, String LN, String Email, String Contact, JRadioButton Male, JRadioButton Female, String ID, JComboBox<String> usertype, String password, ArrayList<JLabel> Errors){
        int error= 0;
            if(FN.isBlank()){
                Errors.get(0).setText("!");
                error= 1;
            }else{
                Errors.get(0).setText("");
            }
            if(LN.isBlank()){
                Errors.get(1).setText("!");
                error= 1;
            }
            else{
                Errors.get(1).setText("");
            }
            if(Email.isBlank() || !Email.contains("@")){
                Errors.get(2).setText("!");
                error= 1;
            }
            else{
                Errors.get(2).setText("");
            }
            if(!Male.isSelected() && !Female.isSelected()){
                Errors.get(3).setText("!");
                error= 1;
            }
            else{
                Errors.get(3).setText("");
            }
            if(Contact.isBlank()){
                Errors.get(4).setText("!");
                error= 1;
                
            }
            else{       
                try{
                  Integer.parseInt(Contact);
                  Errors.get(4).setText("");
                }catch (NumberFormatException e){                    
                    Errors.get(4).setText("!");
                    error= 1;
                }
            }
            if(ID.isBlank()){
            error = 1;
            }       
            return error;
}

        private int CheckErrors(String FN, String LN, String Email, String Contact,String Gender, String ID,  String password){
        int error= 0;
            if(FN.isBlank()){
                error= 1;
                javax.swing.JOptionPane.showMessageDialog(null, "First Name is empty!", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
                return 1;
            }else{
            }
            if(LN.isBlank()){
                javax.swing.JOptionPane.showMessageDialog(null, "Last Name is empty!", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            else{
            }
            if(Email.isBlank() || !Email.contains("@")){
                javax.swing.JOptionPane.showMessageDialog(null, "Email needs to include '@'", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            else{
            }
            if(Gender.isBlank() || (!Gender.equalsIgnoreCase("M") && !Gender.equalsIgnoreCase("F"))){
                System.out.print(Gender);
               javax.swing.JOptionPane.showMessageDialog(null, "Gender should be 'M' or 'F'", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            else{
            }
            if(Contact.isBlank()){
                javax.swing.JOptionPane.showMessageDialog(null, "Contact is empty", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            else{       
                try{
                  Integer.parseInt(Contact);
                }catch (NumberFormatException e){                    
                    javax.swing.JOptionPane.showMessageDialog(null, "Contact should be numeric", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return 1;
                }
            }
            if(ID.isBlank()){
                javax.swing.JOptionPane.showMessageDialog(null, "There is no ID selected!", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
                return 1;
            }       
            if(password.isBlank() || password.length() < 8){
                javax.swing.JOptionPane.showMessageDialog(null, "Password should be more than 8 letters", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            return error;
}
    public void AppendCredentials(User user){   
        userfile.AppendData(getLoginCredentials(), user);
    }
    
     public void updateProfile(String FN, String LN, String Email, String Contact, String Gender, String ID, String password){
        int error;
         if(FN.isBlank() || LN.isBlank() || Email.isBlank() || Contact.isBlank() |Gender.isBlank()|| ID.isBlank()){                        
            error = CheckErrors(FN, LN, Email, Contact, Gender,ID,password);
//             javax.swing.JOptionPane.showMessageDialog(null, "Make sure all fields are filled !!", "Empty Fields ", javax.swing.JOptionPane.ERROR_MESSAGE);
        }else{
              error = CheckErrors(FN, LN, Email, Contact,Gender,ID,password);
         }
         if(error == 1 ){
             
            return;
         }else{            
             updateAllUsers(FN, LN, Email, Contact, Gender,ID,password);
             reWriteFile();
             readLoginCredentials();
             javax.swing.JOptionPane.showMessageDialog(null, "Information saved successfully!!", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
             
         }
         
    }
     
public void deleteUser(String ID){
         updateAllUsers(ID);
         reWriteFile();
         readLoginCredentials();
     }
    
 public void updateUserInfoTable(DefaultTableModel dtm, JComboBox userIDs){
         userIDs.removeAllItems();
         dtm.setRowCount(0);
         String buffer[];
         for(User user : this.getAllUsers()){
//             System.out.println(user);
             if(user.getPassword().equals("DELETED")){
                continue;
             }
             buffer = user.toString().split(",");
             int length = buffer[7].length();
             buffer[7] = "";
             for(int i = 0 ; i <length;i++){

                 buffer[7] += "*";
             }
             userIDs.addItem(buffer[1]);
             dtm.addRow(buffer);         
         } 
     }
   public void updateUserInfoTable(DefaultTableModel dtm, String usertype, JComboBox userIDs){
         userIDs.removeAllItems();
         dtm.setRowCount(0);
         String buffer[];
         for(User user : getAllUsers()){
             if(user.getUserType().equals(usertype)){
                 //System.out.println(user);
                if(user.getPassword().equals("DELETED")){
                    continue;
                }
                buffer = user.toString().split(",");
                int length = buffer[7].length();
                buffer[7] = "";
                for(int i = 0 ; i <length;i++){
                        buffer[7] += "*";
                 }
                userIDs.addItem(buffer[1]);
                dtm.addRow(buffer);         
             }
         } 
     }  
   
    protected void updateAllUsers(String FN, String LN, String Email, String Contact, String Gender, String ID, String password){
        for(User user: getAllUsers()){
            if(user.getUserID().equals(ID)){
                user.setFirstName(FN);
                user.setLastName(LN);
                user.setEmail(Email);
                user.setGender(Gender);
                user.setContactNo(Contact);
                user.setPassword(password);
            }
        }
    }
    
    
        protected void updateAllUsers(String ID){
        for(User user: getAllUsers()){
            if(user.getUserID().equals(ID)){
                user.setPassword("DELETED");
            }
        }
    }
    
}

// append
//        try{ 
//            BufferedWriter writefile = new BufferedWriter(new FileWriter("src/data/loginCredentials.txt",true)); 
//            writefile.write(user.toString() + "\n");
//            writefile.close(); 
//        }catch(FileNotFoundException e){ 
//            
//        }catch(IOException e){
//        }    