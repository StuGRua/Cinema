package DBopeartion;

import Entity.Hall;

import java.util.List;

/* *
 * @Author StuG
 * @Description 大厅接口
 * @Date  2020-8-1
 **/
public interface HallDao {
    //特定条件查找大厅
    List<Hall> getHall(String sql, String[] param);
}
