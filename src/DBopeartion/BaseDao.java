package DBopeartion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/* *
 * @Author StuG
 * @Description BaseDao类为Dao类基类，提供基础的数据库连接操作；每一个Dao类都继承BaseDao类, 而且都有各自的接口。
 * @Date  2020-8-1
 **/

public class BaseDao {
    public static String DRIVER; // 数据库驱动

    public static String URL; // url

    public static String DBNAME; // 数据库用户名

    public static String PASSWORD; // 数据库密码

    static {
        init();
    }

    Connection conn = null;// 数据连接对象

    /**
     * 初始化连接参数,从配置文件里获得
     */
    public static void init() {
        Properties params = new Properties();
        // String configFile = "databaseForSqlite.properties";//配置文件路径
        String configFile = "database.properties";//配置文件路径
        //加载配置文件到输入流中
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        if (is != null) {
            try {
                //从输入流中读取属性列表
                params.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //根据指定的获取对应的值
            DRIVER = params.getProperty("driver");
            URL = params.getProperty("url");
            DBNAME = params.getProperty("user");
            PASSWORD = params.getProperty("password");
        } else {
            DRIVER = "com.mysql.jdbc.Driver";
            URL = "jdbc:mysql://localhost:3306/cinema?useUnicode=true&characterEncoding=UTF-8";
            DBNAME = "StuG";
            PASSWORD = "x74rtw05";
        }
    }

    /**
     * 得到数据库连接
     *
     * @return 数据库连接
     */
    public Connection getConn() throws ClassNotFoundException {
        Connection conn = null;
        try {
            Class.forName(DRIVER); // 注册驱动
            conn = DriverManager.getConnection(URL, DBNAME, PASSWORD); // 获得数据库连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn; // 返回连接
    }

    /**
     * 释放资源
     *
     * @param conn       数据库连接
     * @param prepareSql PreparedStatement对象
     * @param rs         结果集
     */
    public void closeAll(Connection conn, PreparedStatement prepareSql, ResultSet rs) {

        /* 如果rs不空，关闭rs */
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        /* 如果prepareSql不空，关闭prepareSql */
        if (prepareSql != null) {
            try {
                prepareSql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        /* 如果conn不空，关闭conn */
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 执行SQL语句，可以进行增、删、改的操作，不能执行查询
     *
     * @param param 预编译的 SQL 语句中的‘？’参数的字符串数组
     * @return 影响的条数
     */
    public int executeSQL(String preparedSql, Object[] param) {
        Connection conn = null;
        PreparedStatement prepareSql = null;
        int num = 0;

        /* 处理SQL,执行SQL */
        try {
            conn = getConn(); // 得到数据库连接
            prepareSql = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    prepareSql.setObject(i + 1, param[i]); // 为预编译sql设置参数
                }
            }

            num = prepareSql.executeUpdate(); // 执行SQL语句
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // 处理ClassNotFoundException异常
        } // 处理SQLException异常
        finally {
            this.closeAll(conn, prepareSql, null);
        }
        return num;
    }
}
