package DBopeartion;
import java.util.List;
import Entity.Audience;

/* *
 * @Author StuG
 * @Description 用户接口，对用户操作
 * @Date  2020-8-1
 **/

public interface UserDao {
    List<Audience> getAllUser();
    //添加用户
    int AddUser(Audience user);
    //更新用户信息
    int updateUser(String sql, Object[] param);
}
