package dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class TrackDto implements PersistentEntityDto,Serializable{
    private Long id;
    private String name;
    private Double maxSpeed;
    private Double averageSpeed;
    private Double distance;
    private Double lean;
    private Double duration;
    private Date date;
    private List<CoordinateDto> coordinates;
    private UserDto user;
    private List<TripTipsDto> tripTips;

    public TrackDto() {
    }

    public TrackDto(Long id) {
        this.id = id;
    }

    public TrackDto(Long id, String name, Double maxSpeed, Double averageSpeed, Double distance, Double lean, Double duration, Date date, List<CoordinateDto> coordinates, UserDto user) {
        this.id = id;
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.averageSpeed = averageSpeed;
        this.distance = distance;
        this.lean = lean;
        this.duration = duration;
        this.date = date;
        this.coordinates = coordinates;
        this.user = user;
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

    public List<CoordinateDto> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<CoordinateDto> coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<TripTipsDto> getTripTips() {
        return tripTips;
    }

    public void setTripTips(List<TripTipsDto> tripTips) {
        this.tripTips = tripTips;
    }
}
