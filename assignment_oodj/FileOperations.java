package assignment_oodj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// any question please contact me or message in group //

public class FileOperations<data extends Data> {
    String Buffer;
    String LineBuffer[];
    ArrayList<data> allObjects = new ArrayList<>();
    

    // read data from text file
    // the class should have its own implementation of newObject method
    // in their own methods, please create a new object with all variables
    // # refer to PO example of newObject method
    @SuppressWarnings("unchecked")
    public ArrayList<data> ReadFile(String txtfile, data obj){
        allObjects.clear();
        try{             
            BufferedReader readfile = new BufferedReader(new FileReader(txtfile));            
            while(true){                                
                Buffer = readfile.readLine();         
                if(Buffer == null || Buffer.equals("")){
                    break;
                }
                LineBuffer = Buffer.split(",");                
                allObjects.add((data)obj.newObject(LineBuffer));                                
            }
            readfile.close();

        }catch (FileNotFoundException e){

        }catch(IOException e){

        }


        return allObjects;

    }

    // append new data and pass in whole object with filled variables
    // all classes should have the toString method # follow PO example of toString
    public void AppendData(String txtfile,data obj){
        
        try{ 
            BufferedWriter writefile = new BufferedWriter(new FileWriter(txtfile, true));
            writefile.write(obj.toString() + "\n");
            writefile.close(); 
        }catch(FileNotFoundException e){ 

        }catch(IOException e){

        }

    }

    // rewrites the text file 
    // before passing in array list, add the obj inside the list first
    public void WriteFile(String txtfile, ArrayList<data> data){
        try{ 
            BufferedWriter writefile = new BufferedWriter(new FileWriter(txtfile));
            for(data obj : data){
                writefile.write(obj.toString() + "\n");
            }        
            writefile.close(); 
        }catch(FileNotFoundException e){ 

        }catch(IOException e){

        }
    }
    
}
