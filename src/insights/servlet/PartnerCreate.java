package insights.servlet;

import insights.dal.*;
import insights.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/partnercreate")
public class PartnerCreate extends HttpServlet {
	
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
        //Just render the JSP.   
        req.getRequestDispatcher("/PartnerCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String partnerId = req.getParameter("partnerid");
        String partnerName = req.getParameter("partnername");
        if (partnerId == null || partnerId.trim().isEmpty()) {
            messages.put("success", "Invalid PartnerID");
        } else if (partnerName == null || partnerName.trim().isEmpty()) {
            messages.put("success", "Invalid Partner Name");
        } else {
        	// Create the Partner.
        	String sector = req.getParameter("sector");
	        try {
	        	List<Partners> partners = partnersDao.getAllPartners();
	        	Set<Integer> partnerIds = new HashSet<Integer>();
	        	for (Partners partner : partners) {
	        		partnerIds.add(partner.getPartnerId());
	        	}
	        	
	        	Partners newPartner = new Partners(Integer.valueOf(partnerId), partnerName, Partners.PartnerSector.valueOf(sector.replaceAll(" ", "_")));
	        	if (!partnerIds.contains(Integer.valueOf(partnerId))) {
	        		newPartner = partnersDao.create(newPartner);
		        	messages.put("success", "Successfully created Partner ID: " + partnerId + ", Name: " + partnerName + ", Sector: " + sector);
	        	} else {
		        	messages.put("success", "This Partner ID already exists, please change another");
	        	}
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PartnerCreate.jsp").forward(req, resp);
    }
}
