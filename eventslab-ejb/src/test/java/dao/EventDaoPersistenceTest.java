package dao;

import entities.Event;
import entities.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class EventDaoPersistenceTest {
    @PersistenceContext(unitName = "test")
    EntityManager entityManager;

    @Inject
    UserTransaction transaction;

    private List<User> userList;

    @Deployment
    public static Archive<?> createDeployment() {
        Archive archive = ShrinkWrap.create(WebArchive.class, "event-dao.war")
                .addPackage(Event.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println("EventDaoPersistenceTest: " + archive.toString(true));

        return archive;
    }

    private void prepareData() {
        User user = new User();
        user.setEmail("a@a.com");

        List<Event> eventList = new ArrayList<Event>();

        Event event1 = new Event();
        event1.setTitle("first event");
        Event event2 = new Event();
        event2.setTitle("second event");
        event2.setPublish(1);

        eventList.add(event1);
        eventList.add(event2);

        user.setEventList(eventList);

        userList = new ArrayList<User>();
        userList.add(user);
    }

    private void insertData() throws Exception {
        transaction.begin();
        entityManager.joinTransaction();

        for (User user : userList) {
            entityManager.persist(user);
        }

        transaction.commit();
        entityManager.clear();
    }

    @Test
    @InSequence(1)
    public void testGetAllEvents() throws Exception {
        prepareData();
        insertData();

        List<Event> list = entityManager.createQuery("SELECT e " +
                                                     "FROM Event e " +
                                                     "WHERE e.publish <> 0" +
                                                     "ORDER BY e.id DESC",
                                                     Event.class)
                                        .getResultList();
        assertEquals(1, list.size());
    }
}
