package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "event")
public class Event implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String venue;

    private String address;

    private Date date;

    @Column(name = "event_hour")
    private Integer hour;

    @Column(name = "event_min")
    private Integer min;

    private String period;

    private int ticketCount;

    private int ticketCategory;

    @Column(name = "publish")
    private int publish;

    private Date ticketExpiryDate;

    private String description;

    private Integer organizerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(int ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public int getPublish() {
        return publish;
    }

    public void setPublish(int publish) {
        this.publish = publish;
    }

    public Date getTicketExpiryDate() {
        return ticketExpiryDate;
    }

    public void setTicketExpiryDate(Date ticketExpiryDate) {
        this.ticketExpiryDate = ticketExpiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Integer organizerId) {
        this.organizerId = organizerId;
    }
}
