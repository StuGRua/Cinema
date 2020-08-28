package DBopeartion.impl;

import DBopeartion.BaseDao;
import DBopeartion.ChangeMovieDao;
import entity.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ChangeMovieDaoimpl extends BaseDao implements ChangeMovieDao {
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement pstmt = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集

    @Override
    public int updateMovie(String sql, Object[] param) {
        int count=super.executeSQL(sql,param);
        return count;
    }

    @Override
    public List<Movie> getAllMovie() {
        List<Movie> movieList = new ArrayList<Movie>();

        String sql = "select * from movie";

        try{
            conn=getConn();
            pstmt=conn.prepareStatement(sql);

            rs=pstmt.executeQuery();
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
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            super.closeAll(conn,pstmt,rs);
        }
        return movieList;
    }

    @Override
    public Movie getMovie(Object[] param) {
        Movie movie=new Movie();
        String sql="select * from Movie where Movie_id=?";
        try{
            conn=getConn();
            pstmt=conn.prepareStatement(sql);
            if(param!=null){
                for(int i=0;i<param.length;i++) {
                    pstmt.setObject(i+1,param[i]);
                }
            }
            rs=pstmt.executeQuery();
            while(rs.next()){
                movie.setMovie_id(rs.getInt(1));
                movie.setMovie_name(rs.getString(2));
                movie.setMovie_baseprice(rs.getFloat(3));
                movie.setMovie_starttime(rs.getTimestamp(4));
                movie.setMovie_endtime(rs.getTimestamp(5));
                movie.setLast_time(rs.getInt(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            super.closeAll(conn,pstmt,rs);
        }
        return movie;
    }
}
