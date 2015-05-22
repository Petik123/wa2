package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.TrackDao;
import cz.cvut.fel.wa2.dao.TripTipsDao;
import cz.cvut.fel.wa2.entities.Track;
import cz.cvut.fel.wa2.entities.TripTips;
import dto.TrackDto;
import dto.TripTipsDto;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Petikk on 17. 5. 2015.
 */
@WebServlet(name = "DeleteTripTipServlet")
public class DeleteTripTipServlet extends HttpServlet {
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();
    TripTipsDao tripTipsDao= new TripTipsDao();

    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        TripTipsDto tripTipsDto= jsonMapper.readValue(req.getInputStream(), TripTipsDto.class);
        TripTips tripTips= mapper.mapObject(tripTipsDto, TripTips.class);
        tripTipsDao.delete(tripTips);
    }
}