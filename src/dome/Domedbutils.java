package dome;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import jdbcUtils.JdbcUtils2;

//简化jdbc代码
public class Domedbutils {
	@Test
	public void fun1() throws Exception {
		String username = null;
		QueryRunner qr = new QueryRunner(JdbcUtils2.getDataSource());
		String sql = "SELECT * FROM t_user WHERE username=?";

		// String sql ResultSetHander rsh Object...params
		User user = qr.query(sql, new BeanHandler<User>(User.class), "luoqi");
		System.out.println(user.getAge());

	}

	@Test
	public void fun2() throws Exception {
		String username = null;
		QueryRunner qr = new QueryRunner(JdbcUtils2.getDataSource());
		String sql = "INSERT INTO t_user VALUES(?,?,?,?)";
		qr.update(sql, "luoqi66", "gx960325", 22, "shi");

	}

	@Test
	public void fun3() throws Exception {
		String username = null;
		QueryRunner qr = new QueryRunner(JdbcUtils2.getDataSource());
		String sql = "SELECT * FROM t_user ";

		// String sql ResultSetHander rsh Object...params
		List<User> list = qr.query(sql, new BeanListHandler<User>(User.class));
		for (User u : list) {
			System.out.println(u.getAge());
		}

	}
	
	@Test
	public void fun4() throws Exception {
		String username = null;
		QueryRunner qr = new QueryRunner(JdbcUtils2.getDataSource());
		String sql = "SELECT  count(*) FROM t_user ";

		// String sql ResultSetHander rsh Object...params
		Number num = (Number) qr.query(sql,new ScalarHandler());
		int c = num.intValue();//先写前面的，后面可以看提醒

	}
	
}
