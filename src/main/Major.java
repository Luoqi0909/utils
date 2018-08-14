package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbcUtils.JdbcUtils1;

public class Major {
	public static void dataMigration(String sql, String outputServerName, String inputServerName) throws Exception {
    Connection con = JdbcUtils1.getConnection(outputServerName);
    Statement statement = con.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    
	}

}
