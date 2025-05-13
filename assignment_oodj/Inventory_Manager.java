/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_oodj;

/**
 *
 * @author User
 */
public class Inventory_Manager extends User {
        static int count = 0;
        
        public Inventory_Manager(){};
        public Inventory_Manager(User temp){
                    super(temp.getUserType(),temp.getUserID(),temp.getFirstName(),
                temp.getLastName(),temp.getGender(),temp.getEmail(),
                temp.getContactNo(),temp.getPassword());
//        userType = temp.userType; 
//        userID = temp.userID; 
//         firstName = temp.firstName;
//         lastName = temp.lastName;
//         gender = temp.gender;
//         email = temp.email;
//         contactNo = temp.contactNo;
//         password = temp.password;
         count = count + 1;
    }
    
    @Override
    public void Start(){
        //start program and pass in current object
        System.out.println("Inventory");
        System.out.println(this);
        new IM_Program(this);
        
        
    }

    
    
}
