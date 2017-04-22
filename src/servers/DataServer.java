
package servers;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Akshat
 */
public class DataServer implements Runnable {
    private static Socket cs2;
    private ArrayList ar2=new ArrayList();
   
    DataServer(Socket s){
    DataServer.cs2=s;
     }
    
    public static void main(String[] args) throws Exception {
       
        
        System.out.println("Data Server up and listening");
        ServerSocket dss=new ServerSocket(6666);
        while(true){
        try{
           
            Socket ds=dss.accept();
            System.out.println("Client Connected");
            new Thread(new DataServer(ds)).start();
        }
          
             catch(Exception e){
             System.out.println(e);
              }
    }
   
    }
    @Override
    public void run() {
        
     try {  
         ObjectInputStream ois=new ObjectInputStream(cs2.getInputStream());
         Object object = ois.readObject();
        ar2 =  (ArrayList<Integer>) object;
        int method2;
     method2=(int)ar2.get(0);
     System.out.println("Method 2="+method2);
         
       //  System.out.println("uid="+uid+"\notp="+otp );
         
         switch(method2){
                    case 1:
                        System.out.println("Method Number="+method2);
                        otpInsert();
                        break;
                    case 2:
                      System.out.println("Method Number="+method2);
                       otpVerify();
                        break;
                    case 3:
                        System.out.println("Method Number="+method2);
                     //   otpgen();
                          break;
                    default :
                        System.out.println("incorect method value="+method2);
                        break;
                        }
         
         cs2.close();
     } catch (IOException | ClassNotFoundException ex) {
         Logger.getLogger(DataServer.class.getName()).log(Level.SEVERE, null, ex);
     }
    
    }

    private void otpInsert() {
        try{
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dsdb","root","");               
                   PreparedStatement ps=con.prepareStatement("insert into clientverification(uid,otp) VALUES(?,?)");               
                   String uid = (String)ar2.get(1);
                   String otp=(String) ar2.get(2);
                   ps.setString(1,uid);
                   ps.setString(2,otp);
                   int rs=ps.executeUpdate();
                   if(rs>0){System.out.println("inserted id ="+uid+"\n & otp="+otp);                   }
                   else{System.out.println("not inserted");}
                   con.close();    
        }
    
    catch(ClassNotFoundException | SQLException e){System.out.println(e);}
    
    }
    private void otpVerify() {
        try{
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dsdb","root","");               
                   PreparedStatement ps=con.prepareStatement("select uid, otp from clientverification where uid=? and otp=?");               
                   String uid = (String)ar2.get(1);
                   String otp=(String) ar2.get(2);
                   ps.setString(1,uid);
                   ps.setString(2,otp);
                   ResultSet rs=ps.executeQuery();
                 //  System.out.println(rs.rowUpdated());
                   
                   if(rs.next()){
                   System.out.println("verified id ="+uid+"\n & otp="+otp);  
                   PreparedStatement ps1=con.prepareStatement("DELETE from clientverification where uid=? ");
                   ps1.setString(1,uid);
                   int rs1=ps1.executeUpdate();
                   if(rs1!=0){
                   System.out.println("verified id ="+uid+"\n & otp="+otp);
                   System.out.println("client now authorised");
                   }
                   else
                   System.out.println("Something went wrong");
                   }
                   else{System.out.println("not deleted");}
                   con.close();    
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DataServer.class.getName()).log(Level.SEVERE, null, ex);
        }}
    
}
