package dome;

public class Insert {

	public void exec(Connection conn) {

		try {

			// 开始时间

			Long beginTime = System.currentTimeMillis();

			// 设置手动提交

			conn.setAutoCommit(false);

			Statement st = conn.createStatement();

			for (int i = 0; i < 100000; i++) {

				String sql = "insert into t1(id) values (" + i + ")";

				st.executeUpdate(sql);

			}

			// 结束时间

			Long endTime = System.currentTimeMillis();

			System.out.println("st：" + (endTime - beginTime) / 1000 + "秒");// 计算时间

			st.close();

			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}
	// ===================================================================

	public void exec2(Connection conn) {

		try {

			Long beginTime = System.currentTimeMillis();

			conn.setAutoCommit(false);// 手动提交

			PreparedStatement pst = conn.prepareStatement("insert into t1(id) values (?)");

			for (int i = 0; i < 100000; i++) {

				pst.setInt(1, i);

				pst.execute();

			}

			conn.commit();

			Long endTime = System.currentTimeMillis();

			System.out.println("pst:" + (endTime - beginTime) / 1000 + "秒");// 计算时间

			pst.close();

			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}
	// ============================================

	public void exec3(Connection conn) {

		try {

			conn.setAutoCommit(false);

			Long beginTime = System.currentTimeMillis();

			// 构造预处理statement

			PreparedStatement pst = conn.prepareStatement("insert into t1(id) values (?)");

			// 1万次循环

			for (int i = 1; i <= 100000; i++) {

				pst.setInt(1, i);

				pst.addBatch();

				// 每1000次提交一次

				if (i % 1000 == 0) {// 可以设置不同的大小；如50，100，500，1000等等

					pst.executeBatch();

					conn.commit();

					pst.clearBatch();

				}

			}

			Long endTime = System.currentTimeMillis();

			System.out.println("pst+batch：" + (endTime - beginTime) / 1000 + "秒");

			pst.close();

			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

}
