package cz.cvut.fel.wa2.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Petikk on 2. 5. 2015.
 */
@Entity
public class Track implements PersistentEntity,Serializable{
    private static final long serialVersionUID = 7;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Double maxSpeed;
    private Double averageSpeed;
    private Double distance;
    private Double lean;
    private Double duration;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Coordinate> coordinates;

    @OneToMany(fetch=FetchType.LAZY ,mappedBy = "track")
    private List<TripTips> tripTips;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Track() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getLean() {
        return lean;
    }

    public void setLean(Double lean) {
        this.lean = lean;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TripTips> getTripTips() {
        return tripTips;
    }

    public void setTripTips(List<TripTips> tripTips) {
        this.tripTips = tripTips;
    }
}
