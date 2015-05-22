package cz.cvut.fel.wa2.dao;

import cz.cvut.fel.wa2.entities.TripTips;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class TripTipsDao extends AbstractDao<TripTips>{
    @Override
    protected Class<TripTips> getEntityClass() {
        return TripTips.class;
    }

    public TripTips getByIdWithCoordinates(Long id) {
        final Session session = getSession();
        TripTips tripTip;
        try {
            Transaction tx = session.beginTransaction();
            String query = "select t from TripTips as t join fetch t.track as u join fetch u.coordinates where t.id="+id;
            Query q = session.createQuery(query);
            tripTip = (TripTips) q.uniqueResult();
            session.flush();
            tx.commit();
        } finally {
            session.close();
        }
        return tripTip;
    }

}
