package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 18.01.2017.
 */
public class Manager {
    private static Connection con;
    private static Manager instance;
    private static DataSource dataSource;

    private Manager() {
    }

    public static synchronized Manager getInstance() {
        if (instance == null) {
            try {
                instance = new Manager();
                Context ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/data");
                con = dataSource.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public List<City> getAllCities() throws SQLException {
        List<City> cities = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM cities");
        while (rs.next()) {
            City st = new City(rs);
            cities.add(st);
        }
        rs.close();
        stmt.close();
        return cities;
    }

    public void insertCity(City city) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO cities "+
        "(cityName, region, popup, found)"
                + "VALUES( ?,  ?,  ?,  ?)");
        stmt.setString(1, city.getCityName());
        stmt.setString(2, city.getRegion());
        stmt.setInt(3, city.getPopup());
        stmt.setInt(4, city.getFound());
        stmt.execute();
    }

    public void deleteCity(City city) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM cities WHERE id =  ?");
        stmt.setInt(1, city.getId());
        stmt.execute();
    }

    public void updateCity(City city) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE cities SET " +
                "cityName =?, region = ?, popup = ?, found = ? " +
                "WHERE id = ?;");
        stmt.setString(1, city.getCityName());
        stmt.setString(2, city.getRegion());
        stmt.setInt(3, city.getPopup());
        stmt.setInt(4, city.getFound());
        stmt.setInt(5, city.getId());
        stmt.execute();
    }
}

