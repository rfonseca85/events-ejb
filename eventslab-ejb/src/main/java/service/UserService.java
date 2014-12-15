package service;

import dao.UserDao;
import entities.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserService {

    @EJB
    private UserDao userDao;

    private User user;

    protected final Log log = LogFactory.getLog(getClass());

    public void signUpService(User user) {
        userDao.insertUser(user);
    }

    public Integer logInService(User user) {
        user = userDao.getUser(user);
        log.info("logInService: " + user);
        if (user == null) {
            return null;
        } else {
            System.out.println("logInService: " + user.getId());
            return user.getId();
        }
    }

    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void updateUserInfo(User user) {
        userDao.updateUserInfo(user);
    }

    public User getUserWithContactList(Integer userId) {
        return userDao.getUserWithContactList(userId);
    }

    public List<User> getUserForAutoComplete(Integer userId, String query) {
        return userDao.getUserForAutoComplete(userId, query);
    }

    public void addFriend(Integer userId, String email) {
        User friend = userDao.getUserByEmail(email);
        userDao.updateUserContactList(userId, friend);
    }
}
