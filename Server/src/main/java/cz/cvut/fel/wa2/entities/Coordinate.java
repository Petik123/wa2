package cz.cvut.fel.wa2.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Petikk on 2. 5. 2015.
 */
@Entity
public class Coordinate implements PersistentEntity,Serializable{
    private static final long serialVersionUID = 7;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double lon;
    private Double lat;

    public Coordinate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
