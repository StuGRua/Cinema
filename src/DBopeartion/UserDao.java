package DBopeartion;

import java.util.List;

import Entity.Audience;

public interface UserDao {
    List<Audience> getAllUser();

    int AddUser(Audience user);
}
