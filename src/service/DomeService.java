package service;

import java.sql.SQLException;

import dao.DaoFactory;
import dao.UserDao;
import dome.User;
import jdbcUtils.JdbcUtils;

public class DomeService {
	// 先写依赖
	private UserDao userDao = DaoFactory.getUserDao();

	/**
	 * 业务（查）
	 * 
	 * @throws Exception
	 */
	// 方法中定义就这样写就可以
	public User find(User user) throws Exception {
		User user1 = null;
		// 开启事务
		try {
			JdbcUtils.beginTransaction();
			// 调用可以这样搞
			 user1 = userDao.findByUsername(user.getUsername());
			JdbcUtils.commitTranscation();
		} catch (Exception e) {
			JdbcUtils.rollbackTranscation();
		}
		return user1;

	}
}
