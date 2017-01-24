package servlets;


import model.City;
import model.Manager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Андрей on 10.01.2017.
 */

public class MainServlet extends HttpServlet {

    private List<City> cities = new ArrayList();
    private City cityEdit = new City();
    private String currentFilter = "";
    private String currentMask = "";

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        if (req.getParameter("filter") != null) {
            try {
                filterCheck(req);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                cities = filtration();
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            }
        }

        if (req.getParameter("Add") != null) {
            try {
                insertCity(req);
                cities = filtration();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (req.getParameter("Delete") != null) {
            try {
                deleteCity(req);
                cities = filtration();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (req.getParameter("check") != null) {
            for (City c : cities) {
                if (c.getId() == Integer.parseInt(req.getParameter("check"))) {
                    cityEdit = c;
                }
            }
        }

        req.setAttribute("cityEdit", cityEdit);

        if ((req.getParameter("cityName") != null) && (req.getParameter("check") == null)) {
            try {
                updateCity(req);
                cities = filtration();

                for (City c : cities) {
                    if (c.getId() == cityEdit.getId()) {
                        cityEdit = c;
                    }
                }

                req.setAttribute("cityEdit", cityEdit);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        req.setAttribute("cities", cities);
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void insertCity(HttpServletRequest req) throws SQLException, ParseException {
        City city = prepareCity(req);
        Manager.getInstance().insertCity(city);
    }

    private City prepareCity(HttpServletRequest req) throws ParseException {
        City city = new City();
        if (req.getParameter("Add") != null) {
            city.setCityName("Название");
            city.setRegion("Регион");
            city.setPopup(0);
            city.setFound(0);
        }
        if (req.getParameter("Edit") != null) {
            city.setId(cityEdit.getId());
            city.setCityName(req.getParameter("cityName"));
            city.setRegion(req.getParameter("region"));
            city.setPopup(Integer.parseInt(req.getParameter("popup")));
            city.setFound(Integer.parseInt(req.getParameter("found")));
        }
        return city;
    }

    private void deleteCity(HttpServletRequest req) throws SQLException, ParseException {
        City city = prepareCity(req);
        if (req.getParameter("check") != null) {
            city.setId(Integer.parseInt(req.getParameter("check")));
            Manager.getInstance().deleteCity(city);
        }
    }

    private void updateCity(HttpServletRequest req) throws SQLException, ParseException {
        City city = prepareCity(req);
        Manager.getInstance().updateCity(city);
    }

    private void filterCheck(HttpServletRequest req) throws SQLException, ParseException {

        String filter = req.getParameter("filter");
        String mask = req.getParameter("mask");

        if ((filter.equals("found")) && (!mask.equals(""))) {
            cities = Manager.getInstance().filtration(filter, mask);
            currentFilter = filter;
            currentMask = mask;
        } else if ((filter.equals("popup")) && (!mask.equals(""))) {
            cities = Manager.getInstance().filtration(filter, mask);
            currentFilter = filter;
            currentMask = mask;
        } else if ((filter.equals("region")) && (!mask.equals(""))) {
            cities = Manager.getInstance().filtration(filter, mask);
            currentFilter = filter;
            currentMask = mask;
        } else if ((filter.equals("cityName")) && (!mask.equals(""))) {
            cities = Manager.getInstance().filtration(filter, mask);
            currentFilter = filter;
            currentMask = mask;
        } else if ((filter.equals("id")) && (!mask.equals(""))) {
            cities = Manager.getInstance().filtration(filter, mask);
            currentFilter = filter;
            currentMask = mask;
        } else {
            cities = Manager.getInstance().getCities();
            currentFilter = "";
            currentMask = "";
        }
    }

    private List<City> filtration() throws SQLException {
        if (!(currentFilter.equals("")) && !(currentMask.equals(""))) {
            return Manager.getInstance().filtration(currentFilter, currentMask);
        } else
            return Manager.getInstance().getCities();
    }
}
