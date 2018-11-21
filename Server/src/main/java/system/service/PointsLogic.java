package system.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import system.dao.PointsDAOImpl;
import system.entity.Points;

import java.io.IOException;
import java.util.List;

public class PointsLogic {
    public boolean addPoint(HttpEntity<String> request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PointsDAOImpl pointsService = new PointsDAOImpl();
        Points points = new Points();
        JsonNode actualObj = mapper.readTree(request.getBody());

        String country = actualObj.get("country").textValue();
        String region = actualObj.get("region").textValue();
        String city = actualObj.get("city").textValue();
        List<Points> pointsList = pointsService.getPoints();

        if(region.equals("")) {
            String name = country+" "+city;
            points.setName(name);
            for(Points point : pointsList)
            {
                if(point.getName().equals(points.getName())) {
                    return false;
                }
            }
            pointsService.add(points);
            return true;
        }
        else{
            String name = country+" "+region+" "+city;
            points.setName(name);
            for(Points point : pointsList)
            {
                if(point.getName().equals(points.getName())) {
                    return false;
                }
            }
            pointsService.add(points);
            return true;
        }
    }
}
