package DBopeartion;

import Entity.Hall;

import java.util.List;

public interface HallDao {
    /**
     * 查找大厅
     */
    List<Hall> findHall();

    /**
     * 特定条件查找大厅
     */
    List<Hall> getHall(String sql, String[] param);
}
