package jdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;//最初的接口

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils2 {

	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();
	}

	public static DataSource getDataSource() {
		return dataSource;
	}
}
