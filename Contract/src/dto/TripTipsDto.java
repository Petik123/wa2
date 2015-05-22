package dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class TripTipsDto implements PersistentEntityDto,Serializable{
    private Long id;
    private String name;
    private String description;
    private RatingDto rating;
    private TrackDto track;

    public TripTipsDto() {
    }

    public TripTipsDto(Long id) {
        this.id = id;
    }

    public TripTipsDto(Long id, String description, RatingDto rating, TrackDto track) {
        this.id = id;
        this.description = description;
        this.rating = rating;
        this.track = track;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RatingDto getRating() {
        return rating;
    }

    public void setRating(RatingDto rating) {
        this.rating = rating;
    }

    public TrackDto getTrack() {
        return track;
    }

    public void setTrack(TrackDto track) {
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
