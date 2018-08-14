package dome;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import jdbcUtils.JdbcUtils1;

public class Dome1 {
	@Test
	public void fun() throws Exception {
		String sql = "select * from  user_ where id=?";
		PreparedStatement pstm = JdbcUtils1.getConnection().prepareStatement(sql);
		pstm.setInt(1, 2);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			System.out.println(id + name);
		}

	}

	@Test
	public void fun1() throws SQLException, Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		dataSource.setUser("root");
		dataSource.setPassword("admin");
		
		Connection con = dataSource.getConnection();
		String sql = "select * from  user_ where id=?";
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, 2);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			System.out.println(id + name);

		
	}
		con.close();//记得将连接归还连接池
	}
	@Test
	public void fun2() throws SQLException, Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		
		Connection con = dataSource.getConnection();
		String sql = "select * from  user_ where id=?";
		PreparedStatement pstm = con.prepareStatement(sql);
		pstm.setInt(1, 2);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			System.out.println(id + name);
			
			
		}
		con.close();
	}
}

