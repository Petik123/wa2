package cz.cvut.fel.wa2.dao;

import cz.cvut.fel.wa2.entities.Refuel;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class RefuelDao extends AbstractDao<Refuel>{
    @Override
    protected Class<Refuel> getEntityClass() {
        return Refuel.class;
    }
}
