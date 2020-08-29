package Service;
import Entity.ArrangeTicket;
import java.util.List;


/* *
 * @Author StuG
 * @Description 票务接口：客户查询、订购、修改、退票
 * @Date  2020-8-5
 **/

public interface Ticket {

    void Order();

    void Refund();

    /**
     *
     * @param movie_name 电影名字
     * @return 根据电影名字返回订票信息
     */
    List<ArrangeTicket> SearchTickets(String movie_name);

    void Change();

    void Find();
}
