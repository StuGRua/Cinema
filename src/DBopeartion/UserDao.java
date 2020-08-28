package DBopeartion;

import java.util.HashMap;
import java.util.List;
import entity.Auident;

public interface UserDao {
    public abstract List<Auident> getAllUser();
    public abstract int AddUser(Auident user);
}
