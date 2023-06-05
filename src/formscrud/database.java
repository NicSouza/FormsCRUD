package formscrud;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author nicol
 */
public class database {
    
    public static Connection connect(){
        
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
                    
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/viagem","root","");       
            return connect;        
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
