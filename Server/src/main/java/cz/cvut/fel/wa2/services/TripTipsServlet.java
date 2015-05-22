package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.TrackDao;
import cz.cvut.fel.wa2.dao.TripTipsDao;
import cz.cvut.fel.wa2.dao.UserDao;
import cz.cvut.fel.wa2.entities.Coordinate;
import cz.cvut.fel.wa2.entities.Track;
import cz.cvut.fel.wa2.entities.TripTips;
import cz.cvut.fel.wa2.entities.User;
import dto.CoordinateDto;
import dto.TrackDto;
import dto.TripTipsDto;
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
@WebServlet(name = "TripTipsServlet")
public class TripTipsServlet extends HttpServlet {
    TripTipsDao tripTipDao = new TripTipsDao();
    TrackDao trackDao =new TrackDao();
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String id=req.getQueryString();
            if(id==null){
                List<TripTips> list = tripTipDao.getAll();
                List<TripTipsDto> dtoList = mapper.mapList(list, TripTipsDto.class);
                resp.setContentType("application/json");
                jsonMapper.writeValue(resp.getWriter(), dtoList);
            }else{
                Long lid=Long.parseLong(id.substring(3));
                TripTips tripTip= tripTipDao.getByIdWithCoordinates(lid);
                if(tripTip==null){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().flush();
                    return;
                }
                TripTipsDto tripTipsDto=mapper.mapObject(tripTip,TripTipsDto.class);
                TrackDto trackDto=mapper.mapObject(tripTip.getTrack(),TrackDto.class);
                List<CoordinateDto> coordinateDtos=mapper.mapList(tripTip.getTrack().getCoordinates(),CoordinateDto.class);
                trackDto.setCoordinates(coordinateDtos);
                tripTipsDto.setTrack(trackDto);
                jsonMapper.writeValue(resp.getWriter(), tripTipsDto);
            }
            resp.getWriter().flush();
        }catch(Exception e){
            System.out.println(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            TripTipsDto tripTipDto = jsonMapper.readValue(request.getInputStream(), TripTipsDto.class);
            Track track = trackDao.getById(tripTipDto.getTrack().getId());
            if (track == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("text/plain");
                response.getWriter().write("User id doesn't exists");
            } else {
                TripTips tripTips = mapper.mapObject(tripTipDto, TripTips.class);
                tripTips.setTrack(track);
                tripTipDao.insert(tripTips);
            }
    }

}
