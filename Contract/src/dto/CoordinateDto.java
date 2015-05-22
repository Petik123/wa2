package dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Petikk on 2. 5. 2015.
 */
@XmlRootElement
public class CoordinateDto implements PersistentEntityDto,Serializable{
    private Long id;
    private Double lon;
    private Double lat;

    public CoordinateDto() {
    }

    public CoordinateDto(Long id) {
        this.id = id;
    }

    public CoordinateDto(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public CoordinateDto(Long id, Double lon, Double lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
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
