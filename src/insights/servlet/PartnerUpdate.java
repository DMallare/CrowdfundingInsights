package insights.servlet;

import insights.dal.*;
import insights.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/partnerupdate")
public class PartnerUpdate extends HttpServlet {
	
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

        // Retrieve partner and validate.
        String partnerId = req.getParameter("partnerid");
        if (partnerId == null || partnerId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Partner ID.");
        } else {
        	try {
        		Partners partner = partnersDao.getPartnerByPartnerId(Integer.valueOf(partnerId));
        		if(partner == null) {
        			messages.put("success", "Partner ID does not exist.");
        		}
        		req.setAttribute("partner", partner);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PartnerUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve partner and validate.
        String partnerId = req.getParameter("partnerid");
        if (partnerId == null || partnerId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Partner ID.");
        } else {
        	try {
        		Partners partner = partnersDao.getPartnerByPartnerId(Integer.valueOf(partnerId));
        		if(partner == null) {
        			messages.put("success", "Partner ID does not exist. No update to perform.");
        		} else {
        			String newPartnerName = req.getParameter("partnername");
        			if (newPartnerName == null || newPartnerName.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Partner Name.");
        	        } else {
        	        	partner = partnersDao.updatePartnerName(partner, newPartnerName);
        	        	messages.put("success", "Successfully updated Partner Name to: " + newPartnerName);
        	        }
        		}
        		req.setAttribute("partner", partner);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PartnerUpdate.jsp").forward(req, resp);
    }
}
