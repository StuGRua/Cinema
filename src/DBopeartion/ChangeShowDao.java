package DBopeartion;

/* *
 * @Author StuG
 * @Description 更新放映场次接口
 * @Date  2020-8-2
 **/
public interface ChangeShowDao {
    //更新场次
    int updateShow(String sql, Object[] param);
}
