package test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTest
{
    public static void main(String[] args)
    {

        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/user";
        String username = "root";
        String password = "root";
        
        List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
        try
        {
            
            Class.forName(driver); // 注册数据库驱动程序
            con = DriverManager.getConnection(url, username, password); // 建立到数据库库
            stmt = con.createStatement(); // 创建一个 Statement 对象来将 SQL语句发送到数据库
            
            String sql = "SELECT * FROM  useritem"; // SQL 查询语句
            rs = stmt.executeQuery(sql); // 执行数据库查询并将查询结果集数据表rs
            
            while (rs.next())
            {
                Map<String, String> oneBean = new HashMap<String, String>();
                oneBean.put("userId", rs.getString(1));
                oneBean.put("userName", rs.getString(2));
                oneBean.put("sex", rs.getString(3));
                oneBean.put("userPhone", rs.getString(4));
                oneBean.put("userAddress", rs.getString(5));
                resultList.add(oneBean);
            }
            
            System.out.println(resultList);
        }
        catch (Exception e3)
        {
            e3.printStackTrace();
        }
        
        finally
        { // 释放连接
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (con != null)
                    con.close();
            }
            catch (Exception e4)
            {
                e4.printStackTrace();
            }
        }
        
        
        
        stmt = null;
        rs = null;
        con = null;

        try
        {
            System.out.println();
            Class.forName(driver); // 注册数据库驱动程序
            con = DriverManager.getConnection(url, username, password); // 建立到数据库库
            stmt = con.createStatement(); // 创建一个 Statement 对象来将 SQL语句发送到数据库
            
            String sql = "insert into useritem (userName,sex,userPhone,userAddress) values ('0','0','0','0')"; // SQL 查询语句
            stmt.execute(sql); // 执行数据库查询并将查询结果集数据表rs
        }
        catch (Exception e3)
        {
            e3.printStackTrace();
        }
        
        finally
        { // 释放连接
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (con != null)
                    con.close();
            }
            catch (Exception e4)
            {
                e4.printStackTrace();
            }
        }
        System.out.println();
    }

}