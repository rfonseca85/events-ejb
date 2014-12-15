package dao;

import entities.Event;
import entities.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;


@Stateless
public class EventDaoImpl implements EventDao {

    @PersistenceContext(unitName = "persistDB")
    private EntityManager entityManager;

    @EJB
    private UserDao userDao;

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public User getEventByUserId(int userId) {
        try{
            return (User) entityManager.createQuery("SELECT u " +
                    "FROM User u " +
                    "JOIN FETCH u.eventList e " +
                    "WHERE u.id = :userId " +
                    "ORDER BY e.id DESC")
                    .setParameter("userId", userId).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Event> getAllEvents() {
        TypedQuery<Event> query = entityManager.createQuery("SELECT e " +
                "FROM Event e " +
                "WHERE e.publish <> 0" +
                "ORDER BY e.id DESC",
                Event.class);
        List<Event> events;
        try {
            events = query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
        return events;
    }

    @Override
    public Integer insertEvent(Integer userId, Event event) {
        User user = entityManager.find(User.class, userId);
        user.getEventList().add(event);
        entityManager.merge(user);

        Event dbEvent= (Event) entityManager.createQuery("SELECT e FROM Event e WHERE e.organizerId = :userId ORDER BY e.id DESC")
                                    .setParameter("userId", userId)
                                    .setMaxResults(1)
                                    .getSingleResult();
        return dbEvent.getId();
    }

    @Override
    public Event getEventById(int eventId) {
        return entityManager.find(Event.class, eventId);
    }

    @Override
    public void updateEventPublish(Event event) {
        entityManager.merge(event);
    }

    @Override
    public Event getEventByOrganizerId(Integer userId, Integer eventId) {
        try {
            return entityManager.createQuery("SELECT e FROM Event e " +
                                "WHERE e.organizerId = :userId AND e.id = :eventId", Event.class)
                                .setParameter("userId", userId)
                                .setParameter("eventId", eventId)
                                .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void eventParticipate(Integer userId, Event event) {
        User user = entityManager.find(User.class, userId);
        user.getParticipatedEvents().add(event);
        entityManager.merge(user);
    }

    @Override
    public List<Event> getLimitedEvents(int limit) {
        return entityManager.createQuery("SELECT e " +
                                         "FROM Event e " +
                                         "WHERE e.publish <> 0 " +
                                         "ORDER BY e.id DESC", Event.class)
                            .setMaxResults(limit)
                            .getResultList();
    }

    @Override
    public List<Event> getEventBySearch(String search) {
        try {
        return entityManager.createQuery("SELECT e FROM Event e WHERE e.title LIKE :search AND e.publish <> 0", Event.class)
                .setParameter("search", "%" + search + "%")
                .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void updateEventInfo(Event event) {
        entityManager.merge(event);
    }
}
