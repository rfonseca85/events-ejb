package dao;

import entities.User;

import javax.ejb.Local;
import java.util.List;


@Local
public interface UserDao {
    public void insertUser(User user);

    public User getUser(User user);

    public User getUserById(int userId);

    public void updateUserInfo(User user);

    public User getUserWithContactList(Integer userId);

    public List<User> getUserForAutoComplete(Integer userId, String query);

    public void updateUserContactList(Integer userId, User friend);

    public User getUserByEmail(String email);
}
