package extra.jdbc.basics;

/* note : 
create procedure SHOW_SUPPLIERS
as
begin  
    select SUPPLIERS.SUP_NAME, COFFEES.COF_NAME
    from SUPPLIERS, COFFEES
    where SUPPLIERS.SUP_ID = COFFEES.SUP_ID
    order by SUP_NAME ;
end;
*/

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class CallableStatements {

    public static void main(String[] args) {
        Connection con = DriverManagerConnection.getConnection();
        CallableStatement cs = null ;
        ResultSet rs = null;
        
        try{
            cs = con.prepareCall("{call SHOW_SUPPLIERS}");
            rs = cs.executeQuery();

            while (rs.next()) {
                String supName = rs.getString("SUP_NAME");
                String coffeeName = rs.getString("COF_NAME");
                System.out.println(supName + "     " + coffeeName);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DriverManagerConnection.close(con, cs, rs);
        }
    }

}
