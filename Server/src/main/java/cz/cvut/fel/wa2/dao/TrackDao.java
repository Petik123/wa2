package cz.cvut.fel.wa2.dao;

import cz.cvut.fel.wa2.entities.Track;
import cz.cvut.fel.wa2.entities.TripTips;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petikk on 2. 5. 2015.
 */
public class TrackDao extends AbstractDao<Track> {
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

    public List<Track> getAllWithUsers(){
        final Session session = getSession();
        List<Track>list=null;
        try {
            Transaction tx = session.beginTransaction();
            String query = "select t from Track as t join fetch t.user";
            Query q = session.createQuery(query);
            list=q.list();
            session.flush();
            tx.commit();
        } finally {
            session.close();
            if(list==null){
                list=new ArrayList<Track>();
            }
        }
        return list;
    }
}
