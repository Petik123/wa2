package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.TrackDao;
import cz.cvut.fel.wa2.dao.UserDao;
import cz.cvut.fel.wa2.entities.Coordinate;
import cz.cvut.fel.wa2.entities.Track;
import cz.cvut.fel.wa2.entities.User;
import dto.CoordinateDto;
import dto.TrackDto;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Petikk on 9. 5. 2015.
 */
@WebServlet(name = "TrackWithCoordinatesServlet")
public class GetTrackWithCoordinatesServlet extends HttpServlet {
    TrackDao trackDao = new TrackDao();
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String id=req.getQueryString();
            if(id==null){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().flush();
                return;
            }else{
                Long lid=Long.parseLong(id.substring(3));
                Track track=trackDao.getByIdWithCoordinates(lid);
                if(track==null){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().flush();
                    return;
                }
                TrackDto trackDto=mapper.mapObject(track,TrackDto.class);
                List<CoordinateDto> coordinateDtos=mapper.mapList(track.getCoordinates(),CoordinateDto.class);
                trackDto.setCoordinates(coordinateDtos);
                jsonMapper.writeValue(resp.getWriter(), trackDto);
            }
            resp.getWriter().flush();
    }

}
