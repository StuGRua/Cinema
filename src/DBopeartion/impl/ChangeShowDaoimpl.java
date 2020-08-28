package DBopeartion.impl;

import DBopeartion.BaseDao;
import DBopeartion.ChangeShowDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChangeShowDaoImpl extends BaseDao implements ChangeShowDao {
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement prepareSql = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集

    @Override
    public int updateShow(String sql, Object[] param) {
        return super.executeSQL(sql,param);
    }
}
