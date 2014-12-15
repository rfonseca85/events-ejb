package dao;

import entities.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ConversationScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Stateless
public class UserDaoImpl implements Serializable, UserDao {

    @PersistenceContext(unitName = "persistDB")
    private EntityManager entityManager;

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public void insertUser(User user) {
        log.info("insertUser: " + user);
        entityManager.persist(user);
    }

    @Override
    public User getUser(User user) {
        String email = user.getEmail();
        String pass = user.getPassword();
        TypedQuery<User> query = entityManager.createQuery("SELECT u " +
                "FROM User u " +
                "WHERE u.email = :email AND u.password = :pass",
                User.class);
        query.setParameter("email", email);
        query.setParameter("pass", pass);
        try {
            user = query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserById(int userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public void updateUserInfo(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserWithContactList(Integer userId) {
        try {
            return (User) entityManager.createQuery("SELECT u " +
                    "FROM User u " +
                    "JOIN FETCH u.contactList c " +
                    "WHERE u.id = :userId " +
                    "ORDER BY u.id DESC")
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<User> getUserForAutoComplete(Integer userId, String query) {
        try {
            return entityManager.createQuery("SELECT u " +
                    "FROM User u " +
                    "WHERE u.id <> :userId AND u.email LIKE :search")
                    .setParameter("userId", userId)
                    .setParameter("search", "%" + query + "%")
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void updateUserContactList(Integer userId, User friend) {
        User user = getUserById(userId);
        user.getContactList().add(friend);
        entityManager.merge(user);
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return (User) entityManager.createQuery("SELECT u " +
                    "FROM User u " +
                    "WHERE u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
