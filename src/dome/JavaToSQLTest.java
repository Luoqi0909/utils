package dome;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * java读取数据库内容并存放到文件中
 * id作为文件名,相同的id只能创建一个文件
 * 有重复文件名就向文件中续写,id不存在就新建
 * 
 * 写入文件换行用fw.write("\r\n");
 * 或者fw.write("\n");
 * @author young
 *
 */
public class JavaToSQLTest {
    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter pw = null;
        FileWriter fw = null;
        // 定义数据库驱动
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        // 数据库连接URL
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName = soft";
        Connection conn = null;
        String id;
        String fname, lname;
        try {
            // pw.println("emp_id\t\tfname\t\tlname");
            // pw.println("------\t\t------\t\t------");
            // 加载数据库驱动
            Class.forName(driver);
            // 创建数据库连接
            conn = DriverManager.getConnection(url, "sa", "1234");
            // 创建预编译SQL对象
            PreparedStatement ps = conn
                    .prepareStatement("select emp_id, fname, lname from emps");
            // 执行SQL,获取结果集rs
            ResultSet rs = ps.executeQuery();
            // 处理结果集
            while (rs.next()) {
                id = rs.getString("emp_id");
                fname = rs.getString("fname");
                lname = rs.getString("lname");
                String filename = id + ".txt";
                // 关联文件
                File file = new File(filename);
                if(!file.exists()){
                    // 判断文件不存在就new新文件,写数据
                    try {
                        file.createNewFile();
                        // java IO流和文件关联
                        pw = new PrintWriter(file);
                        pw.print(id + "\t");
                        pw.print(fname + "\t\t");
                        pw.print(lname);
                        pw.println();
                        pw.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                     
                }else{
                    // 判断文件存在,就以FileWriter文件追加的方式写文件
                    try {
                        fw = new FileWriter(filename,true);
                        fw.write(id + "\t");
                        fw.write(fname + "\t\t");
                        fw.write(lname);
                        fw.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
 
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("加载数据库失败");
            System.exit(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接错误");
            System.exit(1);
        } finally {
            if (conn != null) {
                try {
                    // 关闭数据库连接
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pw != null) {
                // 关闭IO流
                pw.close();
            }
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}