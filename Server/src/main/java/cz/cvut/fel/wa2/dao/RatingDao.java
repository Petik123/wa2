package cz.cvut.fel.wa2.dao;


import cz.cvut.fel.wa2.entities.Rating;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class RatingDao extends AbstractDao<Rating> {
    @Override
    protected Class<Rating> getEntityClass() {
        return Rating.class;
    }
}
