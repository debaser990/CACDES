package somepack;

import java.io.IOException;
import java.io.ObjectOutputStream;
import static java.lang.Math.random;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewClass {
    
   
   /* public void myvariable()
    {
    
    
    }
*/

public static void main(){

    try {
        /*Random rd=new Random();
        
        System.out.println(rd.nextInt(9999));
        int i=1;
        switch (i) {
        case 0:
        System.out.println("zero");
        break;
        case 1:
        System.out.println("one");
        case 2:
        System.out.println("two");
        default:
        System.out.println("default");*/
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
