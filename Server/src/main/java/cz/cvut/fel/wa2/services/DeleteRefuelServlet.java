package cz.cvut.fel.wa2.services;

import cz.cvut.fel.wa2.dao.RefuelDao;
import cz.cvut.fel.wa2.entities.Refuel;
import dto.RefuelDto;
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
@WebServlet(name = "DeleteRefuelServlet")
public class DeleteRefuelServlet extends HttpServlet {
    ObjectMapper jsonMapper = new ObjectMapper();
    Mapper mapper = new Mapper();
    RefuelDao refuelDao = new RefuelDao();

    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        RefuelDto refuelDto = jsonMapper.readValue(req.getInputStream(), RefuelDto.class);
        Refuel refuel = mapper.mapObject(refuelDto, Refuel.class);
        refuelDao.delete(refuel);
    }
}

