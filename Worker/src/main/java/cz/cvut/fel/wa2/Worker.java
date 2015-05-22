package cz.cvut.fel.wa2;

import cz.cvut.fel.wa2.dao.TrackDao;
import cz.cvut.fel.wa2.entitities.Coordinate;
import cz.cvut.fel.wa2.entitities.Track;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Petikk on 18. 5. 2015.
 */
public class Worker implements Runnable {
    static int counter = 0;
    int id;
    TrackDao trackDao;
    MessageConsumer consumer;

    public Worker() throws JMSException {
        counter++;
        id = counter;
        trackDao = new TrackDao();
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("distance");
        consumer = session.createConsumer(queue);
    }

    public static void main(String[] args) throws JMSException {
        Thread worker1 = new Thread(new Worker());
        Thread worker2 = new Thread(new Worker());
        worker1.start();
        worker2.start();
    }

    public void run() {
        try {
            receive();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void receive() throws JMSException {
        while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            Long id = Long.parseLong(message.getText());
            Track track = trackDao.getByIdWithCoordinates(id);
            Double dist = 0.0;
            for (Coordinate c : track.getCoordinates()) {
                dist += 10.0;
            }
            track.setDistance(dist);
            trackDao.update(track);
        }
    }
}
