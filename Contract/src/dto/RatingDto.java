package dto;

import java.io.Serializable;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class RatingDto implements PersistentEntityDto,Serializable {
    private Long id;
    private int difficulty;
    private int spectacle;
    private int twisty;
    private int surfaceQuality;

    public RatingDto() {
    }

    public RatingDto(Long id) {
        this.id = id;
    }

    public RatingDto(Long id, int difficulty, int spectacle, int twisty, int surfaceQuality) {
        this.id = id;
        this.difficulty = difficulty;
        this.spectacle = spectacle;
        this.twisty = twisty;
        this.surfaceQuality = surfaceQuality;
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
