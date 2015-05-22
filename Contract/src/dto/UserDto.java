package dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Petikk on 1. 5. 2015.
 */
public class UserDto implements PersistentEntityDto,Serializable {
    private Long id;
    private String name;
    private String surname;
    private String userName;
    private String password;
    private Set<RefuelDto> refuels;
    private Set<TrackDto> tracks;

    public UserDto() {
    }

    public UserDto(Long id) {
        this.id = id;
    }

    public UserDto(Long id, String name, String surname, String userName, Set<RefuelDto> refuels, Set<TrackDto> tracks) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.refuels = refuels;
        this.tracks = tracks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<RefuelDto> getRefuels() {
        return refuels;
    }

    public void setRefuels(Set<RefuelDto> refuels) {
        this.refuels = refuels;
    }

    public Set<TrackDto> getTracks() {
        return tracks;
    }

    public void setTracks(Set<TrackDto> tracks) {
        this.tracks = tracks;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
