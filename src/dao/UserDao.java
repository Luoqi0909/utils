package dao;

import dome.User;

/**
 * UserDao接口
 * @author cxf
 *
 */
public interface UserDao {
	 void addUser(User form);
	 User findByUsername(String username);
}
