package insights.servlet;

import insights.dal.*;
import insights.model.PartnerCounts;
import insights.model.Partners;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Partner Recommender allows the user to search for a partner that may more likely fund a loan
 * given the loan sector
 */
@WebServlet("/partnerrecommender")
public class PartnerRecommender extends HttpServlet {	
	private static final long serialVersionUID = -888438509027158868L;
	protected PartnersDao partnersDao;
	
	@Override
	public void init() throws ServletException {
		partnersDao = PartnersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        List<PartnerCounts> results = new ArrayList<>();
		
        // Retrieve and validate sector or country
        String sector = req.getParameter("sector");
        String country = req.getParameter("country");
        if ((sector == null || sector == "" || sector == "None")
        		&& (country ==  null || country.trim().isEmpty())) {
            messages.put("success", "Please select a sector or specify a country");
        } else {
        	// Retrieve Loan/s, and store as a message.
        	boolean recommendBySector = (sector != null && sector != "");
        	try {
        		if (recommendBySector == true) {        
        			results = partnersDao.findPartnersFundingMaximumLoansForSector(sector);
        	        req.setAttribute("results", results);  
        		} else {
        			results = partnersDao.findPartnersFundingMaximumLoansForCountry(country);
        			req.setAttribute("results", results);
        		}

            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }   
        req.getRequestDispatcher("/PartnerRecommender.jsp").forward(req, resp);
	}
	
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
		List<PartnerCounts> results = new ArrayList<>();
		
        // Retrieve and validate sector or country
        String sector = req.getParameter("sector");
        String country = req.getParameter("country");
        if ((sector == null || sector == "" || sector == "None")
        		&& (country ==  null || country.trim().isEmpty())) {
            messages.put("success", "Please select a sector or specify a country");
        } else {
        	// Retrieve Loan/s, and store as a message.
        	boolean recommendBySector = (sector != null && sector != "");
        	try {
        		if (recommendBySector == true) {        
        			results = partnersDao.findPartnersFundingMaximumLoansForSector(sector);
        			if (results.size() > 0) {
        		        messages.put("success", "Partner Recommendations shown for sector: " + sector);
        			} else {
        			     messages.put("success", "No partners have funded loans in sector: " + sector);
        			}
        	        req.setAttribute("results", results);  
        		} else {
        			results = partnersDao.findPartnersFundingMaximumLoansForCountry(country);
          			if (results.size() > 0) {
        		        messages.put("success", "Partner Recommendations shown for country: " + country);
        			} else {
        			     messages.put("success", "No partners have funded loans in country: " + country);
        			}
        			req.setAttribute("results", results);
        		}

            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }   
        req.getRequestDispatcher("/PartnerRecommender.jsp").forward(req, resp);
	}
}

