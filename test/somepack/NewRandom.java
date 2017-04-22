/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package somepack;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Akshat
 */
public class NewRandom {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*       Random rd=new Random();
        Integer x=rd.nextInt(9999);
        System.out.println(x);
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(10000);
        //String formatted = String.format("%04d", num);
        //System.out.println(formatted);
        //String formattedx = String.format("%04d", random.nextInt(10000));
        System.out.println(String.format("%04d", random.nextInt(10000)));
        System.out.println(String.format("%04d", random.nextInt(10000)));
        System.out.println(String.format("%04d", random.nextInt(10000)));
        System.out.println(String.format("%04d", random.nextInt(10000)));
        System.out.println(String.format("%04d", random.nextInt(10000)));
        ArrayList arx=new ArrayList();
        int method=4;
        arx.add(method);
        System.out.println(arx.get(0)); */
        
        try {
        
        ArrayList ar2=new ArrayList();
        int method =2;
        ar2.add(method);
        Socket s=new Socket("localhost",6666);
    ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
    oos.writeObject(ar2);
    oos.flush();
    oos.close();
    
    } catch (IOException ex) {
        Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    
    
    
    
    
 }
        
        
        
        
    }
    
}
