package Service;

import Entity.Arrange;

import java.util.List;

/**
 * 客户查询、订购、修改、退票
 * */
public interface Ticket {
    void order();
    void refund();
    /**
     * 查询票务信息
     * */
    List<Arrange> search(String movie_name);
    void change();
    void find();
}
