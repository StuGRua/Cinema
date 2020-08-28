package DBopeartion;

import Entity.Arrange;
import Entity.Show;

import java.util.List;

public interface TicketDao {
    /**
     * 查询场次
     */
    List<List<String>> findShow(int Aud_id);

    /**
     * 查询订票信息
     */
    List<List<String>> findTicket(int Aud_id);

    /**
     * 根据电影名得到场次
     */
    List<Show> getShow(String sql, String[] param);

    /**
     * 查询票务信息
     */
    List<Arrange> search(String sql, String[] param);

    /**
     * 订票
     */
    int insertArrange(String sql, String[] param);

    /**
     * 改票
     */
    int updateArrange(String sql, String[] param);

    /**
     * 退票
     */
    int delArrange(String sql, String[] param);
    /*
     */
}
