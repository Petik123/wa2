package navigation.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import dto.*;
import navigation.client.exceptions.BadRequestException;
import navigation.client.exceptions.CannotBeModifiedException;
import navigation.client.exceptions.FailedException;

import java.util.List;

@RemoteServiceRelativePath("NavigationService")
public interface NavigationService extends RemoteService {

    void getDistance(TrackDto trackDto) throws FailedException, BadRequestException;

    List<RefuelDto> getRefuels() throws FailedException, BadRequestException;

    void insertRefuel(RefuelDto refuelDto) throws BadRequestException, FailedException,CannotBeModifiedException;

    void deleteRefuel(RefuelDto refuelDto) throws BadRequestException, FailedException,CannotBeModifiedException;

    void insertTrack(TrackDto track) throws BadRequestException, FailedException,CannotBeModifiedException;

    List<TrackDto> getTracks() throws BadRequestException, FailedException;

    void deleteTrack(TrackDto dto) throws BadRequestException, FailedException,CannotBeModifiedException;

    void insertRating(RatingDto dto) throws BadRequestException, FailedException,CannotBeModifiedException;

    List<RatingDto> getRatings() throws BadRequestException, FailedException;

    void deleteRating(RatingDto dto) throws BadRequestException, FailedException,CannotBeModifiedException;

    void insertTripTip(TripTipsDto dto) throws BadRequestException, FailedException,CannotBeModifiedException;

    List<TripTipsDto> getTripTips() throws BadRequestException, FailedException;

    void deleteTripTip(TripTipsDto dto) throws BadRequestException, FailedException,CannotBeModifiedException;

    void insertUser(UserDto dto) throws BadRequestException, FailedException,CannotBeModifiedException;

    List<UserDto> getUsers() throws BadRequestException, FailedException;

    void deleteUser(UserDto dto) throws BadRequestException, FailedException,CannotBeModifiedException;

    TrackDto getTrackByIdWithCoordinates(TrackDto object) throws BadRequestException, FailedException;
    TrackDto getTrackByIdUntilDistance(TrackDto object)throws BadRequestException, FailedException;

    TripTipsDto getTripTipsByIdWithCoordinates(TripTipsDto object) throws BadRequestException, FailedException;



    public static class App {
        private static NavigationServiceAsync ourInstance = GWT.create(NavigationService.class);

        public static synchronized NavigationServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
