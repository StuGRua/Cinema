package DBopeartion;

import Entity.Movie;

import java.util.List;

/* *
 * @Author StuG
 * @Description 更新影片接口
 * @Date  2020-8-2
 **/
public interface ChangeMovieDao {


    //更新影片（实际上是提供了sql操作接口）
    int updateMovie(String sql, Object[] param);

    //获得电影列表
    List<Movie> getAllMovie();

    //获得特定条件电影对象，意义不大，可简化
    Movie getMovie(Object[] param);
}
