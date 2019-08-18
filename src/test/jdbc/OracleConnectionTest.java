package test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class OracleConnectionTest
{
    public static void main(String[] args) throws Exception
    {
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        
        String driver = "oracle.jdbc.driver.OracleDriver";
        
        Class.forName(driver); // 注册数据库驱动程序
        
        Properties conProps = new Properties();
        conProps.put("user", "sys");
        conProps.put("password", "root");
        conProps.put("defaultRowPrefetch", "15");
        conProps.put("internal_logon", "SYSDBA");
        con = DriverManager.getConnection("jdbc:oracle:thin:@113.128.105.137:1521:orcl", conProps);
        stmt = con.createStatement();
        String sql = "select * from alert_qt";
        
        rs = stmt.executeQuery(sql);
        
        System.out.println(rs);
    }
}
