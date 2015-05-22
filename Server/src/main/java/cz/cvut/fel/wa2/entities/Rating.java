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
public class Rating implements PersistentEntity,Serializable {
    private static final long serialVersionUID = 7;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int difficulty;
    private int spectacle;
    private int twisty;
    private int surfaceQuality;

    public Rating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSpectacle() {
        return spectacle;
    }

    public void setSpectacle(int spectacle) {
        this.spectacle = spectacle;
    }

    public int getTwisty() {
        return twisty;
    }

    public void setTwisty(int twisty) {
        this.twisty = twisty;
    }

    public int getSurfaceQuality() {
        return surfaceQuality;
    }

    public void setSurfaceQuality(int surfaceQuality) {
        this.surfaceQuality = surfaceQuality;
    }
}
