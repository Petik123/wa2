package cz.cvut.fel.wa2.dao;


import cz.cvut.fel.wa2.entities.Coordinate;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class CoordinateDao extends AbstractDao<Coordinate> {
    @Override
    protected Class<Coordinate> getEntityClass() {
        return Coordinate.class;
    }
}
