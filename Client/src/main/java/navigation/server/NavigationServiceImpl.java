package navigation.server;

import com.google.gwt.core.client.impl.AsyncFragmentLoader;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import dto.*;
import navigation.client.NavigationService;
import navigation.client.exceptions.BadRequestException;
import navigation.client.exceptions.FailedException;
import navigation.client.exceptions.CannotBeModifiedException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NavigationServiceImpl extends RemoteServiceServlet implements NavigationService {
    private static final String REFUELS = "/Refuels";
    private static final String TRACKS = "/Tracks";
    private static final String RATINGS = "/Ratings";
    private static final String TRIPTIPS = "/TripTips";
    private static final String USERS = "/Users";
    private static final String DISTANCE = "/Distance";

    HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
    ObjectMapper jsonMapper = new ObjectMapper();
    List<String> addresses;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        addresses = new ArrayList<String>();
        String addressParam = getServletContext().getInitParameter("addresses");
        StringTokenizer st = new StringTokenizer(addressParam, ",");
        while (st.hasMoreTokens()) {
            addresses.add(st.nextToken());
        }
    }

    public void getDistance(TrackDto trackDto) throws BadRequestException, FailedException {
        Random r=new Random();
        String address = addresses.get(r.nextInt(addresses.size())) + DISTANCE;
        String status="status";

        HttpMethod method = new GetMethod(address);
        method.setQueryString(""+trackDto.getId());
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(method);
            status = method.getStatusText();
            switch (statusCode) {
                case 200:
                    break;
                case 400:
                    throw new BadRequestException(status);
                default:
                    throw new FailedException(status);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            method.releaseConnection();
        }
    }

    private <T> void insertEntity(T t,String path) throws BadRequestException, FailedException, CannotBeModifiedException {
        Random r=new Random();
        String address = addresses.get(r.nextInt(addresses.size())) + path;
        PostMethod method = new PostMethod(address);
        String status = "status";
        int statusCode = 0;
        try {
            StringRequestEntity entity = new StringRequestEntity(jsonMapper.writeValueAsString(t), "application/json", "UTF-8");
            method.setRequestEntity(entity);

            statusCode = client.executeMethod(method);
            status = method.getStatusText();
            switch (statusCode) {
                case 200:
                    break;
                case 400:
                    throw new BadRequestException(status);
                case 304:
                    throw new CannotBeModifiedException(status);
                default:
                    throw new FailedException(status);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            method.releaseConnection();
        }
    }

    private<T extends PersistentEntityDto> List<T> getEntities(String path,Class<T> c)throws BadRequestException, FailedException {
        List<T> list = new ArrayList<T>();
        Random r=new Random();
        String address = addresses.get(r.nextInt(addresses.size())) + path;
        String status="status";

        HttpMethod method = new GetMethod(address);
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(method);
            status = method.getStatusText();
            switch (statusCode) {
                case 200:
                    break;
                case 400:
                    throw new BadRequestException(status);
                default:
                    throw new FailedException(status);
            }
            String response = method.getResponseBodyAsString();
            TypeFactory typeFactory = jsonMapper.getTypeFactory();
            list = jsonMapper.readValue(response, typeFactory.constructCollectionType(List.class, c));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            method.releaseConnection();
        }
        return list;
    }

    public void insertRefuel(RefuelDto refuelDto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(refuelDto, REFUELS);
    }

    public List<RefuelDto> getRefuels() throws BadRequestException, FailedException {
        return getEntities(REFUELS,RefuelDto.class);
    }

    public void deleteRefuel(RefuelDto refuelDto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(refuelDto,REFUELS+"/DeleteRefuel");
    }

    public void insertTrack(TrackDto trackDto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(trackDto,TRACKS);
    }

    public List<TrackDto> getTracks() throws BadRequestException, FailedException {
        return getEntities(TRACKS,TrackDto.class);
    }

    public void deleteTrack(TrackDto trackDto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(trackDto,TRACKS+"/DeleteTrack");
    }

    public void insertRating(RatingDto dto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(dto,RATINGS);
    }

    public List<RatingDto> getRatings() throws BadRequestException, FailedException {
        return getEntities(RATINGS,RatingDto.class);
    }

    public void deleteRating(RatingDto dto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(dto,RATINGS+"/DeleteRating");
    }

    public void insertTripTip(TripTipsDto dto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(dto,TRIPTIPS);
    }

    public List<TripTipsDto> getTripTips() throws BadRequestException, FailedException {
        return getEntities(TRIPTIPS,TripTipsDto.class);
    }

    public void deleteTripTip(TripTipsDto dto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(dto,TRIPTIPS+"/DeleteTripTip");
    }

    public void insertUser(UserDto dto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(dto,USERS);
    }

    public List<UserDto> getUsers() throws BadRequestException, FailedException {
        return getEntities(USERS,UserDto.class);
    }

    public void deleteUser(UserDto dto) throws BadRequestException, FailedException, CannotBeModifiedException {
        insertEntity(dto,USERS+"/DeleteUser");
    }

    public TrackDto getTrackByIdWithCoordinates(TrackDto object) throws BadRequestException, FailedException {
        TrackDto trackDto=new TrackDto();
        Random r=new Random();
        String address = addresses.get(r.nextInt(addresses.size())) + TRACKS+"/Coordinates";
        String status="status";

        GetMethod method = new GetMethod(address);
        method.setQueryString(new NameValuePair[]{new NameValuePair("id",object.getId().toString())});
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(method);
            status = method.getStatusText();
            switch (statusCode) {
                case 200:
                    break;
                case 400:
                    throw new BadRequestException(status);
                default:
                    throw new FailedException(status);
            }
            String response = method.getResponseBodyAsString();
            trackDto= jsonMapper.readValue(response,TrackDto.class);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            method.releaseConnection();
        }
        return trackDto;
    }

    public TrackDto getTrackByIdUntilDistance(TrackDto object) throws BadRequestException, FailedException {
        TrackDto trackDto=new TrackDto();
        Random r=new Random();
        String address = addresses.get(r.nextInt(addresses.size())) + TRACKS;
        String status="status";

        GetMethod method = new GetMethod(address);
        method.setQueryString(new NameValuePair[]{new NameValuePair("id",object.getId().toString())});
        int statusCode = 0;
        int time=10;
        while(trackDto.getDistance()==null){
            try {
                statusCode = client.executeMethod(method);
                status = method.getStatusText();
                switch (statusCode) {
                    case 200:
                        break;
                    case 400:
                        throw new BadRequestException(status);
                    default:
                        throw new FailedException(status);
                }
                String response = method.getResponseBodyAsString();
                trackDto= jsonMapper.readValue(response,TrackDto.class);

            } catch (IOException e) {
                e.printStackTrace();
                method.releaseConnection();
                throw new FailedException(e.getMessage());
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new FailedException(e.getMessage());
            }
            if(time<2000){
                time=time*time;
            }else{
                time=2000;
            }
        }

        return trackDto;
    }


    public TripTipsDto getTripTipsByIdWithCoordinates(TripTipsDto object) throws BadRequestException, FailedException{
        TripTipsDto tripTipDto=new TripTipsDto();
        Random r=new Random();
        String address = addresses.get(r.nextInt(addresses.size())) + TRIPTIPS;
        String status="status";

        GetMethod method = new GetMethod(address);
        method.setQueryString(new NameValuePair[]{new NameValuePair("id",object.getId().toString())});
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(method);
            status = method.getStatusText();
            switch (statusCode) {
                case 200:
                    break;
                case 400:
                    throw new BadRequestException(status);
                default:
                    throw new FailedException(status);
            }
            String response = method.getResponseBodyAsString();
            tripTipDto= jsonMapper.readValue(response,TripTipsDto.class);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            method.releaseConnection();
        }
        return tripTipDto;
    }
}