package insights.servlet;

import insights.dal.*;
import insights.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/partnerpiechart")
public class PartnerPieChart extends HttpServlet {
	
	protected PartnersDao partnersDao;
	
	@Override
	public void init() throws ServletException {
		partnersDao = PartnersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        List<PartnersDao.KeyValue> pieChart = null;
		try {
			pieChart = partnersDao.getNumOfPartnersPerSector();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        req.setAttribute("piechart", pieChart);
        req.getRequestDispatcher("/PartnerPieChart.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    }
}
