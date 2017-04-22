
package servers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadedAccessServer implements Runnable {

static  ArrayList ar1=new ArrayList();
static  ArrayList ar2=new ArrayList();
 //static Hashtable hm1=new Hashtable();
 //static ServerSocket ss =null;
 static String u=null;
 static String p=null;
//       String u=(String)ar1.get(1);
       static Socket cs;
   // private Socket s;
    static String otp;

     
    ThreadedAccessServer(Socket c){
    ThreadedAccessServer.cs=c;
    
    }

            @Override
    public void run() {
        { 
            try{
            ObjectInputStream objectInput = new ObjectInputStream(cs.getInputStream());
         Object object = objectInput.readObject();
        ar1 =  (ArrayList<Integer>) object; 
            // hm1 =  (Hashtable) object;
            }
            catch(IOException | ClassNotFoundException e){System.out.println("No 1 = "+e);}
          
         int method = (int)ar1.get(0);
        // u=(String)ar1.get(1);
        // p=(String)ar1.get(2);
         
              // System.out.println("Method Number="+method);

                      switch(method){
                    case 1:
                     //   new AccessServer().ssocketcreate();
                        System.out.println("Method Number="+method);
                        sqlInsert();
                        break;
                    case 2:
                      System.out.println("Method Number="+method);
                        sqlVerify();
                        break;
                    case 3:
                        System.out.println("Method Number="+method);
                        otpgen();
                          break;
                    default :
                        System.out.println("incorect method value="+method);
                        break;
                        }
         }
}
    private static void sqlInsert() {
try  { 
               //   new AccessServer().ssocketcreate();
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/asdb","root","");               
                   PreparedStatement ps=con.prepareStatement("insert into credentials(Username,Password) VALUES(?,?)");               
                   u=(String)ar1.get(1);
                   p=(String)ar1.get(2);
                   ps.setString(1,u);
                   ps.setString(2,p);
                   int rs=ps.executeUpdate();
                   if(rs>0){System.out.println(u+"\n pass="+p);                   }
                   else{System.out.println("not inserted");}
                   con.close();
                     }
             
              catch(ClassNotFoundException | SQLException  cnfe){
              System.out.println(cnfe);             
              }      }

    private static void sqlVerify() {
try  { 
      
                   System.out.println("Inside verify()");
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/asdb","root","");               
                   PreparedStatement ps1=con.prepareStatement("Select Username, Password from credentials where Username=? and Password=?");
                   u=(String)ar1.get(1);
                   p=(String)ar1.get(2);
                   ps1.setString(1,u);
                   ps1.setString(2,p);
                   System.out.println(u);
                   System.out.println(p);
                   ResultSet rs=ps1.executeQuery();
                //   ServerSocket ss1 = new ServerSocket(6026);
               // Socket s1=ss.accept();
                 DataOutputStream dout = new DataOutputStream(cs.getOutputStream());
                   if(rs.next()){
                       System.out.println("OK sent");
                       dout.write(0);
                   }
                   else{System.out.println("not intrested");
                       dout.write(1);
                
                   }
                   
                  dout.close();
                   con.close();
                    cs.close();
}     catch(ClassNotFoundException | SQLException | IOException  cnfe){
              System.out.println(cnfe);             
              }  
 }

    private static void otpgen() {
       
try{
                    System.out.println("otpgen4");
                    // new AccessServer().ssocketcreate();
                     SecureRandom rd=new SecureRandom();
                      // Integer x=rd.nextInt(9999);
                       otp=String.format("%04d", rd.nextInt(10000));
                       
                //      
                                    
                            DataOutputStream dout = new DataOutputStream(cs.getOutputStream());
                        //int method=(int)ar1.get(1);
                       dout.writeUTF(otp);
                      //  dout.write(y);
                        dout.flush();
                        cs.close();
                    }
                    
                    catch(Exception e){
                    System.out.println(e);
                } 
    
                   finally{
    try {        
        cs.close();
    } catch (IOException ex) {
        Logger.getLogger(ThreadedAccessServer.class.getName()).log(Level.SEVERE, null, ex);
    }
                    }
    
    OtpDsSender();
    }
    
    private static void OtpDsSender() {
   try{
   Socket ds1= new Socket("localhost",6666);
   ObjectOutputStream oos1=new ObjectOutputStream(ds1.getOutputStream());
   int method2= 1;
   ar2.add(method2);
   ar2.add(u);
   ar2.add(otp);
   oos1.writeObject(ar2);
   oos1.flush();
   oos1.close();
       
       
   }
   catch(Exception e){System.out.print(e);}



    }
    
public static void main(String args[]) throws Exception {
 ServerSocket ss=new ServerSocket(6026);
 System.out.println("Access Server up and listening");   
    while(true){
        
    
        ss.getLocalPort();
        Socket s=ss.accept();
        new Thread(new ThreadedAccessServer(s)).start();
        
        /*DataInputStream dis=new DataInputStream(s.getInputStream());
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        ObjectInputStream objectInput = new ObjectInputStream(s.getInputStream());
        Object object = objectInput.readObject();
        ar1= (ArrayList <Integer>) object;
        String u=(String)ar1.get(1);
        String p=(String)ar1.get(2);
        ss.close();*/
        
        }
}

}
