package DBopeartion;

import java.util.List;
import Entity.Movie;

public interface ChangeMovieDao {
    /*
      添加影片*/
   // public abstract int insertMovie(String sql,String[] param);
    /*
      删除影片*/
   // public abstract int delMovie(String sql,String[] param);
    /**
     * 更新影片*/
    int updateMovie(String sql, Object[] param);

    List<Movie> getAllMovie();
    Movie getMovie(Object[] param);
}
