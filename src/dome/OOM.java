package dome;
/**
 * 
* @ClassName: OOM 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 罗琪
* @date 2018年8月15日 上午1:01:04 
*本文记录使用java的jdbc从MySQL中读取大量数据不出现OOM的方法，一般的使用jdbc读取的时候，会将查询结果全部导入到内存中，如果数据量很大的时候会出现OOM异常，本文将介绍如何使用MySQL中的分页功能，设置featchSize大小防止OOM，轻松读取海量数据的方法(笔者测试读取量为3000W行数据)
 */
public class OOM {
	 try {
         con = DriverManager.getConnection(url);
         prep = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
         prep .setFetchSize(Integer.MIN_VALUE);
         prep .setFetchDirection(ResultSet.FETCH_REVERSE);
         rst = prep .executeQuery();
         while (rst.next()) {
             // 逻辑操作
             if (count % 10000 == 0) {
                 System.out.println(" 第  " + count + " 条数据！");
             }
         }
         }catch (SQLException e) {
         e.printStackTrace();
     } finally {
         try {
             if (rst != null) {
                 rst.close();
             }
             if (prep != null) {
                 prep.close();
             }
             if (st != null) {
                 st.close();
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }
}


PreparedStatement ps = connection.prepareStatement("select .. from ..", 
        ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY); 

/*
TYPE_FORWARD_ONLY和CONCUR_READ_ONLY是mysql 驱动的默认值，所以不指定也是可以的 比如： PreparedStatement ps = connection.prepareStatement("select .. from .."); 
*/

//可以修改jdbc url通过defaultFetchSize参数来设置，这样默认所以的返回结果都是通过流方式读取
ps.setFetchSize(Integer.MIN_VALUE); 
ResultSet rs = ps.executeQuery(); 

while (rs.next()) { 
System.out.println(rs.getString("fieldName")); 
}

/*
mysql判断是否开启流式读取结果的方法，有三个条件forward-only，read-only和fatch size是Integer.MIN_VALUE。我们可以看看它的源码：
/**
* We only stream result sets when they are forward-only, read-only, and the
* fetch size has been set to Integer.MIN_VALUE
*
* @return true if this result set should be streamed row at-a-time, rather
* than read all at once.
*/
protected boolean createStreamingResultSet() {
try {
    synchronized(checkClosed().getConnectionMutex()) {
        return ((this.resultSetType == java.sql.ResultSet.TYPE_FORWARD_ONLY)
             && (this.resultSetConcurrency == java.sql.ResultSet.CONCUR_READ_ONLY) 
             && (this.fetchSize == Integer.MIN_VALUE));
    }
} catch (SQLException e) {
    // we can't break the interface, having this be no-op in case of error is ok

    return false;
}
}
*/





public class TestInsert {

    public static void main(String[] args) throws SQLException {

        int batchSize = 1000;
        int insertCount = 1000;

        testDefault(batchSize, insertCount);

        testRewriteBatchedStatements(batchSize,insertCount);

    }

    //默认方式插入
    private static void testDefault(int batchSize, int insertCount) throws SQLException{  

        long start = System.currentTimeMillis();

        doBatchedInsert(batchSize, insertCount,"");

        long end = System.currentTimeMillis();
        System.out.println("default:" + (end -start) + "ms");
    }


    //批量插入
    private static void testRewriteBatchedStatements(int batchSize, int insertCount) throws SQLException {

        long start = System.currentTimeMillis();

        doBatchedInsert(batchSize, insertCount, "rewriteBatchedStatements=true");

        long end = System.currentTimeMillis();
        System.out.println("rewriteBatchedStatements:" + (end -start) + "ms");
    }


    private static void doBatchedInsert(int batchSize, int insertCount, String mysqlProperties) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://ip:3306/test?" + mysqlProperties);
        dataSource.setUsername("name");
        dataSource.setPassword("password");

        dataSource.init();

        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into Test (name,gmt_created,gmt_modified) values (?,now(),now())");

        for (int i = 0; i < insertCount; i++) {
            preparedStatement.setString(1, i+" ");
            preparedStatement.addBatch();
            if((i+1) % batchSize == 0) {
                preparedStatement.executeBatch();
            }
        }
        preparedStatement.executeBatch();

        connection.close();   
        dataSource.close();
    }

}




