package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.UserDao;
import cz.cvut.fel.wa2.entities.TripTips;
import cz.cvut.fel.wa2.entities.User;
import dto.UserDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Petikk on 21. 5. 2015.
 */
@WebServlet(name = "DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();
    UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try{
            UserDto userDto = jsonMapper.readValue(req.getInputStream(), UserDto.class);
            User user = mapper.mapObject(userDto, User.class);
            userDao.delete(user);
        }catch(ConstraintViolationException e){
            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
        }

    }
}