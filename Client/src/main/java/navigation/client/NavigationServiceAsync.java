package navigation.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import dto.*;

import java.util.List;

public interface NavigationServiceAsync {
    void getRefuels(AsyncCallback<List<RefuelDto>> async);

    void insertRefuel(RefuelDto refuelDto, AsyncCallback<Void> async);

    void deleteRefuel(RefuelDto refuelDto, AsyncCallback<Void> async);

    void insertTrack(TrackDto track, AsyncCallback<Void> async);

    void getTracks(AsyncCallback<List<TrackDto>> async);

    void deleteTrack(TrackDto dto, AsyncCallback<Void> async);

    void insertRating(RatingDto dto, AsyncCallback<Void> async);

    void getRatings(AsyncCallback<List<RatingDto>> async);

    void deleteRating(RatingDto dto, AsyncCallback<Void> async);

    void insertTripTip(TripTipsDto dto, AsyncCallback<Void> async);

    void getTripTips(AsyncCallback<List<TripTipsDto>> async);

    void deleteTripTip(TripTipsDto dto, AsyncCallback<Void> async);

    void insertUser(UserDto dto, AsyncCallback<Void> async);

    void getUsers(AsyncCallback<List<UserDto>> async);

    void deleteUser(UserDto dto, AsyncCallback<Void> async);

    void getTrackByIdWithCoordinates(TrackDto object, AsyncCallback<TrackDto> asyncCallback);

    void getTripTipsByIdWithCoordinates(TripTipsDto object, AsyncCallback<TripTipsDto> asyncCallback);

    void getDistance(TrackDto trackDto, AsyncCallback<Void> async);

    void getTrackByIdUntilDistance(TrackDto object, AsyncCallback<TrackDto> asyncCallback);
}
