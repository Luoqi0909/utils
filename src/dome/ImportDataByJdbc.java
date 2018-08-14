package dome;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.UUID;

import oracle.jdbc.driver.OracleConnection;

import oracle.sql.CLOB;

/**
 * 
 * 
 * 
 * 
 * import data from one database to another
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author
 * 
 * 
 * 
 * 
 * @version
 * 
 * 
 * 
 * 
 */

public class ImportDataByJdbc {

	public static void main(String[] args) throws Exception {

		Connection con = null;// 创建一个数据库连接

		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement

		ResultSet result = null;// 创建一个结果集对象

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序

			System.out.println("开始尝试连接cms数据库！");

			String url = "jdbc:oracle:" + "thin:@192.168.0.xx:1521:ORCL";

			String user = "policy";// 用户名,系统默认的账户名

			String password = "xxxx";// 你安装时选设置的密码

			con = DriverManager.getConnection(url, user, password);// 获取连接

			System.out.println("cms连接成功！");

			String sql = "select t.title,t.channel_code,t.content from cms_news t";// 预编译语句，“？”代表参数

			pre = con.prepareStatement(sql);// 实例化预编译语句

			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数

			importData(result);

			// while (result.next())

			// 当结果集不为空时

			// System.out.println("用户名:" + result.getString("title"));

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源

				// 注意关闭的顺序，最后使用的最先关闭

				if (result != null)

					result.close();

				if (pre != null)

					pre.close();

				if (con != null)

					con.close();

				System.out.println("cms数据库连接已关闭！");

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

	public static String importData(ResultSet result) {

		Connection con = null;// 创建一个数据库连接

		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement

		ResultSet result2 = null;// 创建一个结果集对象

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateString = formatter.format(currentTime);

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序

			System.out.println("开始尝试连接omp数据库！");

			String url = "jdbc:oracle:" + "thin:@192.168.0.xx:1521:orclgbk";

			String user = "xjsp";// 用户名,系统默认的账户名

			String password = "xxx";// 你安装时选设置的密码

			con = DriverManager.getConnection(url, user, password);// 获取连接

			System.out.println("omp连接成功！");

			int num = 0;

			while (result.next()) {

				String id = getUUID();

				String entryType = getEntryType(result

						.getString("CHANNEL_CODE"));

				String sql = "insert into kms_entry(ID,ENTRY_NAME,ENTRY_TYPE,ENTRY_CONTENT,EDIT_FLAG,AUDIT_TIME,IF_DEL,IF_AUDIT,LAST_EDIT_TIME,CREATE_USER,CREATE_TIME,LAST_EDIT_USER) "

						+ " values('"

						+ id

						+ "','"

						+ result.getString("title")

						+ "','"

						+ entryType

						+ "',?,'1',to_date('"

						+ dateString

						+ "','yyyy-mm-dd hh24:mi:ss'),'1','3',to_date('"

						+ dateString

						+ "','yyyy-mm-dd hh24:mi:ss'),'00000000000000000000000000000000',to_date('"

						+ dateString

						+ "','yyyy-mm-dd hh24:mi:ss'),'00000000000000000000000000000000')";// 预编译语句，“？”代表参数

				pre = con.prepareStatement(sql);// 实例化预编译语句

				CLOB clob = new CLOB((OracleConnection) con); // 创建一个实例化对象

				clob = oracle.sql.CLOB.createTemporary((OracleConnection) con,

						true, 1);

				clob.putString(1, result.getString("CONTENT")); // 为对象赋值

				pre.setClob(1, clob);

				pre.execute();

				pre.close();

				num = num + 1;

				System.out.println("插入成功第" + (num) + "条数据");

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源

				// 注意关闭的顺序，最后使用的最先关闭

				if (result != null)

					result.close();

				if (pre != null)

					pre.close();

				if (con != null)

					con.close();

				System.out.println("omp数据库连接已关闭！");

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

		return null;

	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 获取uuid
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 * 
	 * 
	 * 
	 * 
	 */

	public static String getUUID() {

		UUID uuid = UUID.randomUUID();

		String s = uuid.toString();

		String uuids = s.substring(0, 8) + s.substring(9, 13)

				+ s.substring(14, 18) + s.substring(19, 23) + s.substring(24);

		return uuids;

	}

	public static String getEntryType(String chanelcode) {

		String entryTypeString = "";

		if (chanelcode != "" && chanelcode != null) {

		}

		return entryTypeString;

	}

}
