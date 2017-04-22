/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import static java.util.stream.StreamSupport.intStream;

/**
 *
 * @author Akshat
 */
public class AccessServer {
    
  static  ArrayList ar1=new ArrayList();
  
 static ServerSocket ss =null;
final static String u=null;
final static String p=null;
  
         
      public static void connect() throws Exception{
          while(true){
        try{
                int method;
                ss = new ServerSocket(6026);
                Socket s=ss.accept();
                DataInputStream dis=new DataInputStream(s.getInputStream());
                System.out.println(0);
                ObjectInputStream objectInput = new ObjectInputStream(s.getInputStream());
                Object object = objectInput.readObject();
                ar1 =  (ArrayList<Integer>) object;
                // System.out.println(1);
                String u=(String)ar1.get(1);
                String p=(String)ar1.get(2);
               //  System.out.println(ar1.get(0));
                System.out.println("Socket created");
                method = (Integer)ar1.get(0);
                System.out.println("Method Number="+method);
               // ar1.clear();
          
               switch(method){
                    case 1:
                     //   new AccessServer().ssocketcreate();
                        sqlInsert();
                        break;
                    case 2:
                      //  new AccessServer().ssocketcreate();
                        sqlVerify();
                        break;
                    case 3:
                        otpgen();
                          break;
                    default :
                        System.out.println("incorect method value");
                        break;
                        }
                 //s.close();
        }
          
      catch(IOException | ClassNotFoundException ex){
            System.out.println(ex);
            }
   
        
       }
      }    
     public static void main(String[] args) throws Exception {
        connect();
    }

       
    

    private static void sqlInsert() {
            
              try  { 
               //   new AccessServer().ssocketcreate();
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/asdb","root","");               
                   PreparedStatement ps=con.prepareStatement("insert into credentials(Username,Password) VALUES(?,?)");
               //    String u=(String)ar1.get(1);
                  // String p=(String)ar1.get(2);                  
                   ps.setString(1,u);
                   ps.setString(2,p);
                   int rs=ps.executeUpdate();
                   if(rs>0){System.out.println(u+"\n pass="+p);                   }
                   else{System.out.println("not inserted");}
                   con.close();
                     }
             
              catch(ClassNotFoundException | SQLException  cnfe){
              System.out.println(cnfe);             
              }  
               
    }

    private static void sqlVerify() {
    
        try  { 
      
                   System.out.println("Inside verify()");
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/asdb","root","");               
                   PreparedStatement ps=con.prepareStatement("Select Username, Password from credentials where Username=? and Password=?");
            //       String u=(String)ar1.get(1);
              //     String p=(String)ar1.get(2);
                   ps.setString(1,u);
                   ps.setString(2,p);
                   ResultSet rs=ps.executeQuery();
                //   ServerSocket ss1 = new ServerSocket(6026);
                Socket s1=ss.accept();
                 DataOutputStream dout = new DataOutputStream(s1.getOutputStream());
                   if(rs.next()){
                       System.out.println("OK sent");
                       dout.write(0);
                   }
                   else{System.out.println("not intrested");
                       dout.write(1);
                
                   }
                   
                  dout.close();
                   con.close();
                    s1.close();
} 
             
              catch(ClassNotFoundException | SQLException | IOException  cnfe){
              System.out.println(cnfe);             
              }  

    }

    private static void otpgen() {
                try{
                    System.out.println("otpgen4");
                    // new AccessServer().ssocketcreate();
                    Random rd=new Random();
                       Integer x=rd.nextInt(9999);
                //       ServerSocket ss=new ServerSocket(6091);
                       Socket s1=ss.accept();
                     //  ObjectInputStream objectInput = new ObjectInputStream(s1.getInputStream());
                       //Object object = objectInput.readObject();
                       
                      // DataInputStream dis=new DataInputStream(s1.getInputStream());
                    //int c=   dis.read();
                       //ar1 =  (ArrayList<String>) object;
                    //int method=(int)ar1.get(1);
            //      intStream y= rd.ints(0,9999);
                  int y=rd.nextInt(9999);
                  
                                         System.out.println("otpgen4");
                            DataOutputStream dout = new DataOutputStream(s1.getOutputStream());
                        //int method=(int)ar1.get(1);
                        dout.writeUTF(x.toString());
                        dout.flush();
                    }
                    
                    catch(Exception e){
                    System.out.println(e);
                }
    }
    
  
}
