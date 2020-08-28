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


public class UserDaoImpl extends BaseDao implements UserDao {
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement prepareSql = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集

    @Override
    public List<Audience> getAllUser() {
        List<Audience> auidentsList = new ArrayList<Audience>();
        String sql = "select * from Audience";
        try {
            conn = getConn();
            prepareSql = conn.prepareStatement(sql);

            rs = prepareSql.executeQuery();
            while (rs.next()) {
                Audience audience = new Audience();
                audience.setAud_id(rs.getInt(1));
                audience.setAud_name(rs.getString(2));
                audience.setAid_password(rs.getString(3));
                audience.setAud_tel(rs.getString(4));
                audience.setAud_type(rs.getString(5));
                auidentsList.add(audience);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql, rs);
        }


        return auidentsList;
    }

    @Override
    public int AddUser(Audience user) {
        String sql = "insert into audience values (?,?,?,?,?)";
        Object[] param = {user.getAud_id(), user.getAud_name(), user.getAid_password(), user.getAud_tel(), user.getAud_type()};
        return executeSQL(sql, param);

    }
}
