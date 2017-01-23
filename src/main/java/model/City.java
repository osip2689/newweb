package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Андрей on 18.01.2017.
 */
public class City {
    private int id;
    private String cityName;
    private String region;
    private int popup;
    private int found;

    public City(ResultSet rs) throws SQLException {
        setId(rs.getInt(1));
        setCityName(rs.getString(2));
        setRegion(rs.getString(3));
        setPopup(rs.getInt(4));
        setFound(rs.getInt(5));
    }

    public City() {
    }

    public void initFromCity(City city) {
        this.id = city.getId();
        this.cityName = city.getCityName();
        this.region = city.getRegion();
        this.popup = city.getPopup();
        this.found = city.getFound();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPopup() {
        return popup;
    }

    public void setPopup(int popup) {
        this.popup = popup;
    }

    public int getFound() {
        return found;
    }

    public void setFound(int found) {
        this.found = found;
    }
}
