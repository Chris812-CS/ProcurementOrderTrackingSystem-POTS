/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_oodj;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author User
 */
public class User implements Data{
    public void Start(){};
    
    private ArrayList<User> tempallUsers = new ArrayList<>();
    private ArrayList<User> allUsers = new ArrayList<>();
    private FileOperations<User> userfile = new FileOperations<>();
    private final String loginCredentials = "src/data/loginCredentials.txt";

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }


    private String userType;
    private String userID;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String contactNo;
    private String password;    
    
    private String Buffer;
    private String[] LineBuffer;
    
    
    
    public User(){
        
    }
    
    
    
    public User(String userType, String userID, String firstName, String lastName, String gender, String email, String contactNo, String password) {
        this.userType = userType;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
        
    }

    public User Login(String ID, String Password, javax.swing.JLabel Error){
        //read file here
//        userfile.ReadFile(loginCredentials, this);
        if(ID.isBlank() || Password.isBlank()){
            this.userType = null;
            Error.setText("Credentials not found !! Try Again !!!");
            return null;
        }else if(ID.equals("DELETED")){
            this.userType = null;
            Error.setText("Credentials not found !! Try Again !!!");
            return null;
        }else{
            Error.setText("");
            for(User user : allUsers){
                if(ID.equals(user.userID)){
                  if(Password.equals(user.password)){
                      Error.setText("");
                        return user;                      
                  }else{
                    Error.setText("Credentials not found !! Try Again !!!");
                    this.userType = null;
                    return null;
                  }
                }else if (allUsers.indexOf(user) == allUsers.size() - 1 || Password.equals("DELETED")){
                  Error.setText("Credentials not found !! Try Again !!!");
                  this.userType = null;
                  return null;
                }
            }    
            return null;
        }

        // check what role then return respective number
    
    
    
    
    }
    
//    //
    public void readLoginCredentials (){
        allUsers.clear();
        tempallUsers.clear();
        Admin.count = 0; Sales_Manager.count = 0; Inventory_Manager.count = 0; Finance_Manager.count = 0; Purchase_Manager.count = 0;
        tempallUsers = userfile.ReadFile(loginCredentials, this);
        convertUserType();  
    }
    
     public void reWriteFile(){
         userfile.WriteFile(loginCredentials, allUsers);

     }
     
     public void DisplayProfile( JLabel utype,JLabel uid, JLabel fn, JLabel ln, JLabel gender ,JLabel email, JLabel contact){
         uid.setText(this.userID);
         fn.setText(this.firstName);
         ln.setText(this.lastName);       
         email.setText(this.email);       
         contact.setText(this.contactNo);                
         if(this.userType.equals("A")){
            utype.setText("Admin");     
          }else if(this.userType.equals("SM")){
            utype.setText("Sales Manager");     
          }else if(this.userType.equals("IM")){
            utype.setText("Inventory Manager");     
          }else if(this.userType.equals("FM")){
            utype.setText("Finance Manager");     
          }else if(this.userType.equals("PM")){
            utype.setText("Purchase Manager");     
          } 
         if(this.gender.equals("M")){
         gender.setText("Male");}else{gender.setText("Female");}
         
     
     }
     
     public void ResetPassword(String oldPass, String newPass, javax.swing.JLabel error, javax.swing.JPasswordField oldp, javax.swing.JPasswordField newp, javax.swing.JToggleButton toggle){
         if(oldPass.isBlank() || newPass.isBlank()){
                error.setText("Please fill up all fields !");
                return;
         }
         if(oldPass.equals(this.password)){
             if(newPass.length() >= 8){
                 error.setText("");
             }else{
                error.setText("New password must be more than 8 letters !");
                return;
             }
         }else{
             error.setText("Current password is incorrect !!");
             return;
         }
         
         for(User user : allUsers){          
             
             if(user.userID.equals(this.userID)){
                 this.password = newPass;
                 user.password = newPass;
                 break;
              }       
         }
         reWriteFile();         
         readLoginCredentials();
         
         oldp.setText("");
         newp.setText("");
         JOptionPane.showMessageDialog(null, "Password Changed !", "Notice", JOptionPane.INFORMATION_MESSAGE);
         toggle.setSelected(false);
         newp.setEchoChar('\u2022');
     }
     

    public void DisplayUserInfo(String UID,JTextField info []){
        for(User user : allUsers){
            if(user.userID.equals(UID)){
                info[0].setText(user.firstName); 
                info[1].setText(user.lastName); 
                info[2].setText(user.gender); 
                info[3].setText(user.email); 
                info[4].setText(user.contactNo); 
                info[5].setText(user.password); 
            }
        
        }
    }
    public int ReturnUserIndex(String UID){
        int i;
          for (i = 0; i < allUsers.size(); i ++){
              if (allUsers.get(i).userID.equals(UID)){
                  break;
              }
          }
          if (UID.equals("Pending")){
            return -1;
          } else{
            return i;
          }
}

    @Override
    public String toString() {
        return userType + "," + userID + "," + firstName + "," + lastName + "," + gender + "," + email + "," + contactNo + "," + password;
    }

        public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
     
    public String getLoginCredentials() {
        return loginCredentials;
    }
    
    

    @Override
    public Data newObject(String[] line) {
        return new User(line[0], line[1],line[2], line[3], line[4], line[5], line[6], line[7]);
    }

    private void convertUserType(){
        for(User user: tempallUsers){
            if(user.getUserType().equals("A")){
                allUsers.add(new Admin(user));                
            }else if (user.getUserType().equals("SM")){
                allUsers.add(new Sales_Manager(user));
            }else if (user.getUserType().equals("PM")){
                allUsers.add(new Purchase_Manager(user));
            }else if (user.getUserType().equals("IM")){
                allUsers.add(new Inventory_Manager(user));
            }else if (user.getUserType().equals("FM")){
                allUsers.add(new Finance_Manager(user));
            }                
        }

        
    }
    
}



      //read
//         try{             
//            BufferedReader readfile = new BufferedReader(new FileReader("src/data/loginCredentials.txt"));            
//            while(true){                
//                Buffer = readfile.readLine();         
//                if(Buffer == null){
//                    break;
//                }
//                LineBuffer = Buffer.split(",");
//                User tempuser = new User(LineBuffer[0], LineBuffer[1], LineBuffer[2], LineBuffer[3], LineBuffer[4], LineBuffer[5], LineBuffer[6], LineBuffer[7]);
//                if(LineBuffer[0].equals("A")){
//                  allUsers.add(new Admin(tempuser));
//                }else if(LineBuffer[0].equals("SM")){
//                  allUsers.add(new Sales_Manager(tempuser));
//                }else if(LineBuffer[0].equals("IM")){
//                  allUsers.add(new Inventory_Manager(tempuser));
//                }else if(LineBuffer[0].equals("FM")){
//                  allUsers.add(new Finance_Manager(tempuser));
//                }else if(LineBuffer[0].equals("PM")){
//                  allUsers.add(new Purchase_Manager(tempuser));
//                }                                          
//            }
//            readfile.close();
//        }catch (FileNotFoundException e){
//
//        }catch(IOException e){
//
//        }


// rewrite
//            try{ 
//               BufferedWriter writefile = new BufferedWriter(new FileWriter("src/data/loginCredentials.txt"));
//                for(User user: allUsers){
//                    writefile.write(user.toString() + "\n");
//               }
//                writefile.close(); 
//           }catch(FileNotFoundException e){ 
//
//           }catch(IOException e){
//           }



//    
//    protected void updateAllUsers(String FN, String LN, String Email, String Contact, String Gender, String ID, String password){
//        for(User user: allUsers){
//            if(user.userID.equals(ID)){
//                user.firstName = FN;
//                user.lastName = LN;
//                user.email = Email;
//                user.gender = Gender;
//                user.contactNo = Contact;
//                user.password = password;
//            }
//        }
//    }
//    
//    
//        protected void updateAllUsers(String ID){
//        for(User user: allUsers){
//            if(user.userID.equals(ID)){
//                user.firstName = "DELETED";
//                user.lastName = "DELETED";
//                user.email = "DELETED";
//                user.gender = "DELETED";
//                user.contactNo = "DELETED";
//                user.password = "DELETED";
//                user.userID = "DELETED";
//            }
//        }
//    }
//    
 

//     public void updateUserInfoTable(DefaultTableModel dtm, JComboBox userIDs){
//         userIDs.removeAllItems();
//         dtm.setRowCount(0);
//         String buffer[];
//         for(User user : allUsers){
////             System.out.println(user);
//             if(user.userID.equals("DELETED")){
//                continue;
//             }
//             buffer = user.toString().split(",");
//             int length = buffer[7].length();
//             buffer[7] = "";
//             for(int i = 0 ; i <length;i++){
//
//                 buffer[7] += "*";
//             }
//             userIDs.addItem(buffer[1]);
//             dtm.addRow(buffer);         
//         } 
//     }
//     
//    public void updateUserInfoTable(DefaultTableModel dtm, String usertype, JComboBox userIDs){
//         userIDs.removeAllItems();
//         dtm.setRowCount(0);
//         String buffer[];
//         for(User user : allUsers){
//             if(user.userType.equals(usertype)){
//                 //System.out.println(user);
//                if(user.userID.equals("DELETED")){
//                    continue;
//                }
//                buffer = user.toString().split(",");
//                int length = buffer[7].length();
//                buffer[7] = "";
//                for(int i = 0 ; i <length;i++){
//                        buffer[7] += "*";
//                 }
//                userIDs.addItem(buffer[1]);
//                dtm.addRow(buffer);         
//             }
//         } 
//     }
//    
  