package dao;

import java.util.List;

import javax.ejb.Local;

import entities.Event;
import entities.User;

@Local
public interface EventDao {
    public User getEventByUserId(int userId);

    public List<Event> getAllEvents();

    public Integer insertEvent(Integer userId, Event event);

    public Event getEventById(int eventId);

    public void updateEventPublish(Event event);

    public Event getEventByOrganizerId(Integer organizerId, Integer eventId);

    public void eventParticipate(Integer userId, Event event);

    public List<Event> getLimitedEvents(int limit);

    public List<Event> getEventBySearch(String search);

    public void updateEventInfo(Event event);
}
