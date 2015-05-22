package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.RefuelDao;
import cz.cvut.fel.wa2.dao.UserDao;
import cz.cvut.fel.wa2.entities.Refuel;
import cz.cvut.fel.wa2.entities.User;
import dto.RefuelDto;
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
@WebServlet(name = "RefuelsServlet")
public class RefuelsServlet extends HttpServlet {
    RefuelDao refuelDao = new RefuelDao();
    UserDao userDao = new UserDao();
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Refuel> list = refuelDao.getAll();
            List<RefuelDto> dtoList = mapper.mapList(list, RefuelDto.class);
            resp.setContentType("application/json");
            jsonMapper.writeValue(resp.getWriter(), dtoList);
            resp.getWriter().flush();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RefuelDto refuelDto = jsonMapper.readValue(request.getInputStream(), RefuelDto.class);
        User user = userDao.getById(refuelDto.getUserId());
        if (user == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain");
            response.getWriter().write("User id doesn't exists");
        } else {
            Refuel refuel = mapper.mapObject(refuelDto, Refuel.class);
            //refuel.setUser(user);
            refuelDao.insert(refuel);
        }
    }

//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        RefuelDto refuelDto = jsonMapper.readValue(req.getInputStream(), RefuelDto.class);
//        Refuel refuel = mapper.mapObject(refuelDto, Refuel.class);
//        userDao.delete(refuel);
//    }

}
