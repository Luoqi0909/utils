package dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import dome.User;
import jdbcUtils.JdbcUtils;;

public class UserDaoImpl implements UserDao {

	@Override
	public void addUser(User form) {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner();//如果要开启事务，这里不获取连接。
		String sql = "INSERT INTO t_user VALUES(?,?,?,?)";
		try {//这里使用开启事务时，赋给的connection
			qr.update(JdbcUtils.getConnection(),sql, form.getUsername(),form.getPassword(),form.getAge(),form.getGender());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public User findByUsername(String username) {
		User user=null;
		QueryRunner qr = new QueryRunner();//同上
		String sql = "SELECT * FROM t_user WHERE username=?";
		try {
			 user = qr.query(JdbcUtils.getConnection(),sql, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
