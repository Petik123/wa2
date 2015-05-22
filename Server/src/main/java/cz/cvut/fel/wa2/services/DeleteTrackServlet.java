package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.RefuelDao;
import cz.cvut.fel.wa2.dao.TrackDao;
import cz.cvut.fel.wa2.entities.Refuel;
import cz.cvut.fel.wa2.entities.Track;
import dto.RefuelDto;
import dto.TrackDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Petikk on 17. 5. 2015.
 */
@WebServlet(name = "DeleteTrackServlet")
public class DeleteTrackServlet extends HttpServlet {
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();
    TrackDao trackDao = new TrackDao();

    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try{
            TrackDto trackDto = jsonMapper.readValue(req.getInputStream(), TrackDto.class);
            Track track= mapper.mapObject(trackDto, Track.class);
            trackDao.delete(track);
        }catch(ConstraintViolationException e){
            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
        }

    }
}