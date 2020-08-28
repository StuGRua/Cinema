package DBopeartion.impl;

import DBopeartion.BaseDao;
import DBopeartion.HallDao;
import Entity.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HallDaoImpl extends BaseDao implements HallDao{
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement prepareSql = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集
    @Override
    public List<Hall> findHall() {

        return null;
    }

    @Override
    public List<Hall> getHall(String sql,String[] param) {
        List<Hall> hallList=new ArrayList<Hall>();
        try{
            conn=getConn();
            prepareSql =conn.prepareStatement(sql);
            if(param!=null){
                for(int i=0;i<param.length;i++) {
                    prepareSql.setString(i+1,param[i]);
                }
            }
            rs= prepareSql.executeQuery();
            while(rs.next()){
                Hall hall=new Hall();
                hall.setHallId(rs.getInt(1));
                hall.setHallType(rs.getString(2));
                hall.setHallRowSum(rs.getInt(3));
                hall.setHallLineSum(rs.getInt(4));
                hallList.add(hall);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql,rs);
        }
        return hallList;
    }
}
