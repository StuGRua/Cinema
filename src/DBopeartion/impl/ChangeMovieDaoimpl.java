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

/* *
 * @Author StuG
 * @Description
 * @Date  2020-8-3
 **/

public class ChangeMovieDaoImpl extends BaseDao implements ChangeMovieDao {
    private Connection conn = null; // 保存数据库连接

    private PreparedStatement prepareSql = null; // 用于执行SQL语句

    private ResultSet rs = null; // 用户保存查询到的结果集

    private void setMovieFromSql(Movie movie) throws SQLException {
        movie.setMovie_id(rs.getInt(1));
        movie.setMovie_name(rs.getString(2));
        movie.setMovie_baseprice(rs.getFloat(3));
        movie.setMovie_starttime(rs.getTimestamp(4));
        movie.setMovie_endtime(rs.getTimestamp(5));
        movie.setLast_time(rs.getInt(6));
    }

    //传递sql实现更新
    @Override
    public int updateMovie(String sql, Object[] param) {

        return super.executeSQL(sql, param);
    }

    /**
     *
     * @return 返回电影列表
     */
    @Override
    public List<Movie> getAllMovie() {
        List<Movie> movieList = new ArrayList<>();

        String sql = "select * from movie";

        try {
            conn = getConn();
            prepareSql = conn.prepareStatement(sql);

            rs = prepareSql.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                setMovieFromSql(movie);
                movieList.add(movie);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql, rs);
        }
        return movieList;
    }

    /**
     *
     * @param param sql预编译参数
     * @return 返回特定条件电影
     */
    @Override
    public Movie getMovie(Object[] param) {
        Movie movie = new Movie();
        String sql = "select * from Movie where Movie_id=?";
        try {
            conn = getConn();
            prepareSql = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    prepareSql.setObject(i + 1, param[i]);
                }
            }
            rs = prepareSql.executeQuery();
            while (rs.next()) {
                setMovieFromSql(movie);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            super.closeAll(conn, prepareSql, rs);
        }
        return movie;
    }
}
