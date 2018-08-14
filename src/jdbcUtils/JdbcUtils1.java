package jdbcUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import java.sql.Connection;//注意不要倒错包

/**
 * 类中不能直接写执行方法 申明初始化写在外面
 * 
 * @author 罗琪
 *
 */
public class JdbcUtils1 {
	private static Properties prop = null;
	static {
		try {
			InputStream in = JdbcUtils1.class.getClassLoader().getResourceAsStream("dbConfig.properties");
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			Class.forName(prop.getProperty("driverClassName"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	public  static Connection getConnection() throws SQLException {
		return  DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
	}
}
