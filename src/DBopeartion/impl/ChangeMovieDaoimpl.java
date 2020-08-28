package DBopeartion.impl;

import DBopeartion.BaseDao;
import DBopeartion.ChangeMovieDao;
import Entity.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ChangeMovieDaoImpl extends BaseDao implements ChangeMovieDao {
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement prepareSql = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集

    @Override
    public int updateMovie(String sql, Object[] param) {
        return super.executeSQL(sql,param);
    }

    @Override
    public List<Movie> getAllMovie() {
        List<Movie> movieList = new ArrayList<>();

        String sql = "select * from movie";

        try{
            conn=getConn();
            prepareSql =conn.prepareStatement(sql);

            rs= prepareSql.executeQuery();
            while(rs.next()){
                Movie movie=new Movie();
                movie.setMovie_id(rs.getInt(1));
                movie.setMovie_name(rs.getString(2));
                movie.setMovie_baseprice(rs.getFloat(3));
                movie.setMovie_starttime(rs.getTimestamp(4));
                movie.setMovie_endtime(rs.getTimestamp(5));
                movie.setLast_time(rs.getInt(6));
                movieList.add(movie);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql,rs);
        }
        return movieList;
    }

    @Override
    public Movie getMovie(Object[] param) {
        Movie movie=new Movie();
        String sql="select * from Movie where Movie_id=?";
        try{
            conn=getConn();
            prepareSql =conn.prepareStatement(sql);
            if(param!=null){
                for(int i=0;i<param.length;i++) {
                    prepareSql.setObject(i+1,param[i]);
                }
            }
            rs= prepareSql.executeQuery();
            while(rs.next()){
                movie.setMovie_id(rs.getInt(1));
                movie.setMovie_name(rs.getString(2));
                movie.setMovie_baseprice(rs.getFloat(3));
                movie.setMovie_starttime(rs.getTimestamp(4));
                movie.setMovie_endtime(rs.getTimestamp(5));
                movie.setLast_time(rs.getInt(6));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql,rs);
        }
        return movie;
    }
}
