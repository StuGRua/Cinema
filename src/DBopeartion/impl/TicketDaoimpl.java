package DBopeartion.impl;

import DBopeartion.BaseDao;
import DBopeartion.TicketDao;
import Entity.Arrange;
import Entity.Show;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketDaoImpl extends BaseDao implements TicketDao {
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement prepareSql = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集

    /**
     * 当前时间的 电影名 影厅号 时间 影厅类型 电影时长 票价
     */

    @Override
    public List<List<String>> findShow(int Aud_id) {
        List<List<String>> showList = new ArrayList<>();
        try {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            String preparedSql;
            preparedSql = "select Movie_name,Show.Hall_id,Show.Show_time,MovieHall.Type,Movie.last_time,Base_price " +
                    "from `Show` join Movie on Show.Movie_id = Movie.Movie_id " +
                    "join MovieHall on Show.Hall_id=MovieHall.Hall_id " +
                    "where Show.Show_time>'" + timestamp + "'";
            conn = getConn();
            prepareSql = conn.prepareStatement(preparedSql);
            rs = prepareSql.executeQuery(); //执行
            while (rs.next()) {
                List<String> show = new ArrayList<>();
                show.add(rs.getString(1) + "");
                show.add(rs.getInt(2) + "");
                show.add(rs.getTimestamp(3) + "");
                show.add(rs.getString(4) + "");
                show.add(rs.getString(5) + "");
                show.add(rs.getFloat(6) + "");
                showList.add(show);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql, rs);
        }
        return showList;
    }

    /**
     * 电影名 厅号 厅类型 时间 用户名 座位 票价
     */
    @Override
    public List<List<String>> findTicket(int Aud_id) {
        List<List<String>> showList = new ArrayList<>();
        try {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            String preparedSql;
            if (Aud_id == 1 || Aud_id == 2) {
                preparedSql = "select Movie.Movie_name,Moviehall.Hall_id,Moviehall.type,arrange_time,name,line,row,base_price as ticket_price " +
                        "from Arrange join Movie on Arrange.movie_id = Movie.movie_id " +
                        "join audience on audience.aud_id = arrange.aud_id " +
                        "join MovieHall on arrange.hall_id=MovieHall.Hall_id ";
            } else {
                preparedSql = "select Movie.Movie_name,Moviehall.Hall_id,Moviehall.type,arrange_time,name,line,row,base_price as ticket_price " +
                        "from Arrange join Movie on Arrange.movie_id = Movie.movie_id " +
                        "join audience on audience.aud_id = arrange.aud_id " +
                        "join MovieHall on arrange.hall_id=MovieHall.Hall_id " +
                        "where Arrange.aud_id=" + Aud_id;
            }
            conn = getConn();
            prepareSql = conn.prepareStatement(preparedSql);
            rs = prepareSql.executeQuery(); //执行
            while (rs.next()) {
                List<String> show = new ArrayList<>();
                show.add(rs.getString(1) + "");
                show.add(rs.getInt(2) + "");
                show.add(rs.getString(3) + "");
                show.add(rs.getTimestamp(4) + "");
                show.add(rs.getString(5) + "");
                show.add(rs.getInt(6) + "");
                show.add(rs.getInt(7) + "");
                show.add(rs.getFloat(8) + "");
                showList.add(show);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql, rs);
        }
        return showList;
    }


    //根据特定的条件查找场次
    @Override
    public List<Show> getShow(String sql, String[] param) {
        List<Show> showList = new ArrayList<>();
        try {
            conn = getConn();
            prepareSql = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    prepareSql.setString(i + 1, param[i]);
                }
            }
            rs = prepareSql.executeQuery();
            while (rs.next()) {
                Show show = new Show();
                show.setHall_id(rs.getInt(3));
                show.setMovie_id(rs.getInt(1));
                show.setShow_time(rs.getTimestamp(2));
                showList.add(show);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql, rs);
        }
        return showList;
    }

    @Override
    public List<Arrange> search(String sql, String[] param) {
        List<Arrange> arrangesList = new ArrayList<>();
        try {
            conn = getConn();
            prepareSql = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    prepareSql.setString(i + 1, param[i]);
                }
            }
            rs = prepareSql.executeQuery();
            while (rs.next()) {
                Arrange arrange = new Arrange();
                arrange.setAud_id(rs.getInt(1));
                arrange.setHall_id(rs.getInt(2));
                arrange.setMovie_id(rs.getInt(3));
                arrange.setLine(rs.getInt(4));
                arrange.setRow(rs.getInt(5));
                arrange.setArrange_time(rs.getTimestamp(6));
                arrangesList.add(arrange);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql, rs);
        }
        return arrangesList;
    }

    @Override
    public int insertArrange(String sql, String[] param) {
        return super.executeSQL(sql, param);
    }

    @Override
    public int updateArrange(String sql, String[] param) {
        return super.executeSQL(sql, param);
    }

    @Override
    public int delArrange(String sql, String[] param) {
        return super.executeSQL(sql, param);
    }
}

