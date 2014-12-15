package service;

import dao.EventDao;
import entities.Event;
import entities.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.events.EventTarget;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EventService {

    @EJB
    private EventDao eventDao;

    protected final Log log = LogFactory.getLog(getClass());

    private User user;

    public List<Event> getEvents() {
        return eventDao.getAllEvents();
    }

    public Integer createNewEvent(Integer userId, Event event) {
        return eventDao.insertEvent(userId, event);
    }

    public User getEventByUserId(int userId) {
        return eventDao.getEventByUserId(userId);
    }

    public Event getEventById(int eventId) {
        return eventDao.getEventById(eventId);
    }

    public void publishEvent(Event event) {
        eventDao.updateEventPublish(event);
    }

    public Integer isUserAnOrganizer(Integer userId, Integer eventId) {
        Event event = eventDao.getEventByOrganizerId(userId, eventId);
        return (event == null) ? 0 : 1;
    }

    public void eventParticipate(Integer userId, Event event) {
        eventDao.eventParticipate(userId, event);
    }

    public List<Event> getLimitedEvents(int limit) {
        return eventDao.getLimitedEvents(limit);
    }

    public List<Event> searchEvents(String search) {
        return eventDao.getEventBySearch(search);
    }

    public void updateEventInfo(Event event) {
        eventDao.updateEventInfo(event);
    }

}
