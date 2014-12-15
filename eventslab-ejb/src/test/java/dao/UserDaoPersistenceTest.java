package dao;

import entities.Event;
import entities.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class UserDaoPersistenceTest {

    @PersistenceContext(unitName = "test")
    EntityManager entityManager;

    @Inject
    UserTransaction transaction;

    private Map<String, String> userList;

    @Deployment
    public static Archive<?> createDeployment() {
        Archive archive = ShrinkWrap.create(WebArchive.class, "user-dao.war")
                .addPackage(User.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println("UserDaoPersistenceTest: " + archive.toString(true));

        return archive;
    }

    private void prepareData() {
        System.out.println("prepare");
        userList = new HashMap<String, String>();
        userList.put("a@a.com", "123");
        userList.put("sh@sh.com", "123456789");
        userList.put("ab@a.com", "123456");
    }

    private void insertData() throws Exception {
        transaction.begin();
        entityManager.joinTransaction();

        for(Object key : userList.keySet()) {
            User user = new User();
            user.setEmail(key.toString());
            user.setPassword(userList.get(key));

            entityManager.persist(user);
        }

        transaction.commit();
        entityManager.clear();
    }

    @Test
    @InSequence(1)
    public void testGetUser() throws Exception {
        prepareData();
        insertData();

        String email = "a@a.com";
        String pass = "123";

        User user = entityManager.createQuery("SELECT u " +
                                              "FROM User u " +
                                              "WHERE u.email = :email AND u.password = :pass",
                                              User.class)
                                 .setParameter("email", email)
                                 .setParameter("pass", pass)
                                 .getSingleResult();

        assertEquals("1", user.getId().toString());
    }

    @Test
    @InSequence(2)
    public void testGetUserById() {
        User user = entityManager.find(User.class, 1);
        assertNotNull(user);
    }

}
