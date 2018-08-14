package jdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;//最初的接口

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {

	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	// 它是事务专用连接
	/*
	 * private static Connection con = null;// 申明一个静态变量
	 */
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	// 为了使每个线程都有自己的connection,防止A线程调用开启事务，B线程调用提交事务。
	public static Connection getConnection() throws SQLException {
		Connection con = tl.get();
		if (con != null)
			return con;// 说明con已经被赋值了。说明已经开启了事物。最重要的一步
		return dataSource.getConnection();

	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	// 开启事务的方法,还要保证dao中的连接是这个
	public static void beginTransaction() throws SQLException {
		Connection con = null;
		con = getConnection();// 获得连接
		con.setAutoCommit(false);// 设置为手动提交
		tl.set(con);
		//静态方法中不能调用非静态的成员变量和成员方法。
	}

	/**
	 * 获取beginTransaction提供的connection,然后调用commit方法
	 * 
	 * @throws SQLException
	 */
	public static void commitTranscation() throws SQLException {
		Connection con = tl.get();
		con.commit();// 提交事务
		con.close();
		/*
		 * con=null;//防止service不开启事务，调用dao方法获得的连接是已经关闭了的con
		 */
		tl.remove();// 将它移除出ThreadLocal
	}

	/**
	 * 获取beginTransaction提供的connection,然后调用rollBack方法
	 * 
	 * @throws SQLException
	 */
	public static void rollbackTranscation() throws SQLException {
		Connection con = tl.get();
		con.rollback();// 回滚事务
		con.close();
		/* con = null; */
		tl.remove();// 将它移除出ThreadLocal
	}

}
