package DBopeartion.impl;
import DBopeartion.BaseDao;
import DBopeartion.ChangeShowDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/* *
 * @Author StuG
 * @Description 对放映场次操作
 * @Date  2020-8-3
 **/

public class ChangeShowDaoImpl extends BaseDao implements ChangeShowDao {
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement prepareSql = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集

    //传递sql实现更新
    @Override
    public int updateShow(String sql, Object[] param) {
        return super.executeSQL(sql, param);
    }
}
