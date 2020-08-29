package DBopeartion.impl;

import DBopeartion.BaseDao;
import DBopeartion.UserDao;
import Entity.Audience;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* *
 * @Author StuG
 * @Description 对用户操作
 * @Date  2020-8-3
 **/

public class UserDaoImpl extends BaseDao implements UserDao {
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement prepareSql = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集

    /**
     * @return 返回用户列表
     */
    @Override
    public List<Audience> getAllUser() {
        List<Audience> audiencesList = new ArrayList<>();
        String sql = "select * from Audience";
        try {
            conn = getConn();
            prepareSql = conn.prepareStatement(sql);

            rs = prepareSql.executeQuery();
            while (rs.next()) {
                Audience audience = new Audience();
                audience.setAud_id(rs.getInt(1));
                audience.setAud_name(rs.getString(2));
                audience.setAud_password(rs.getString(3));
                audience.setAud_tel(rs.getString(4));
                audience.setAud_type(rs.getString(5));
                audiencesList.add(audience);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql, rs);
        }
        return audiencesList;
    }

    /**
     *
     * @param user 插入的用户
     * @return  传递sql语句，返回执行情况
     */
    @Override
    public int AddUser(Audience user) {
        String sql = "insert into audience values (?,?,?,?,?)";
        Object[] param = {user.getAud_id(), user.getAud_name(), user.getAud_password(), user.getAud_tel(), user.getAud_type()};
        return executeSQL(sql, param);
    }

    /**
     *
     * @param sql sql预编译语句
     * @param param 语句参数
     * @return 传递sql语句，返回执行情况
     */
    @Override
    public int updateUser(String sql, Object[] param) {
        return super.executeSQL(sql, param);
    }
}
