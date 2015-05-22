package cz.cvut.fel.wa2.dao;


import cz.cvut.fel.wa2.entitities.Track;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class TrackDao extends AbstractDao<Track>{
    @Override
    protected Class<Track> getEntityClass() {
        return Track.class;
    }

    public Track getByIdWithCoordinates(Long id) {
        final Session session = getSession();
        Track track;
        try {
            Transaction tx = session.beginTransaction();
            String query = "select t from Track as t join fetch t.coordinates where t.id="+id;
            Query q = session.createQuery(query);
            track = (Track) q.uniqueResult();
            session.flush();
            tx.commit();
        } finally {
            session.close();
        }
        return track;
    }
}
