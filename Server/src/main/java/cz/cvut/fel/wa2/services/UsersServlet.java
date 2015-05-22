package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.UserDao;
import cz.cvut.fel.wa2.entities.Track;
import cz.cvut.fel.wa2.entities.TripTips;
import cz.cvut.fel.wa2.entities.User;
import dto.TripTipsDto;
import dto.UserDto;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Petikk on 21. 5. 2015.
 */
@WebServlet(name = "UsersServlet")
public class UsersServlet extends HttpServlet {
    UserDao userDao = new UserDao();
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> list = userDao.getAll();
            List<UserDto> dtoList = mapper.mapList(list, UserDto.class);
            resp.setContentType("application/json");
            jsonMapper.writeValue(resp.getWriter(), dtoList);
            resp.getWriter().flush();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDto userDto = jsonMapper.readValue(request.getInputStream(), UserDto.class);
                User user= mapper.mapObject(userDto, User.class);
                userDao.insert(user);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
