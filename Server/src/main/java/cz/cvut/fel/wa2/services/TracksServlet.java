package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.RefuelDao;
import cz.cvut.fel.wa2.dao.TrackDao;
import cz.cvut.fel.wa2.dao.UserDao;
import cz.cvut.fel.wa2.entities.Coordinate;
import cz.cvut.fel.wa2.entities.Refuel;
import cz.cvut.fel.wa2.entities.Track;
import cz.cvut.fel.wa2.entities.User;
import dto.CoordinateDto;
import dto.RefuelDto;
import dto.TrackDto;
import dto.UserDto;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petikk on 9. 5. 2015.
 */
@WebServlet(name = "TracksServlet")
public class TracksServlet extends HttpServlet {
    TrackDao trackDao = new TrackDao();
    UserDao userDao=new UserDao();
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String id=req.getQueryString();
            if(id==null){
                List<Track> list = trackDao.getAllWithUsers();
                List<TrackDto> dtoList=new ArrayList<TrackDto>(list.size());
                TrackDto dto;
                UserDto user;
                for (Track t:list){
                    dto=mapper.mapObject(t,TrackDto.class);
                    user=mapper.mapObject(t.getUser(),UserDto.class);
                    dto.setUser(user);
                    dtoList.add(dto);
                }
                resp.setContentType("application/json");
                jsonMapper.writeValue(resp.getWriter(), dtoList);
            }else{
                Long lid=Long.parseLong(id.substring(3));
                Track track=trackDao.getById(lid);
                if(track==null){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().flush();
                    return;
                }
                TrackDto trackDto=mapper.mapObject(track,TrackDto.class);
                jsonMapper.writeValue(resp.getWriter(), trackDto);
            }
            resp.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TrackDto trackDto = jsonMapper.readValue(request.getInputStream(), TrackDto.class);
        User user = userDao.getById(trackDto.getUser().getId());
        if (user == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain");
            response.getWriter().write("User id doesn't exists");
        } else {
            Track track = mapper.mapObject(trackDto, Track.class);
            List<Coordinate> list=mapper.mapList(trackDto.getCoordinates(),Coordinate.class);
            track.setUser(user);
            track.setCoordinates(list);
            trackDao.insert(track);
        }
    }

}
