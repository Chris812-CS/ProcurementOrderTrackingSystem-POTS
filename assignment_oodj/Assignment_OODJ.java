/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment_oodj;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import java.util.Collections;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author User
 */
public class Assignment_OODJ {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
         try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            FlatLaf.setGlobalExtraDefaults( Collections.singletonMap( "@accentColor", "#AB886D" ));
            FlatIntelliJLaf.setup();
            LoginUI login = new LoginUI();
            /* Create and display the form */
        } catch (UnsupportedLookAndFeelException ex) {
//        Logger.getLogger(SM_Program.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Please install flatlaf for a better experience!");
        }
    }
    
}
