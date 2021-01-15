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


@WebServlet("/partnerdelete")
public class PartnerDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        req.getRequestDispatcher("/PartnerDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String partnerId = req.getParameter("partnerid");
        if (partnerId == null || partnerId.trim().isEmpty()) {
            messages.put("success", "Invalid Partner ID");
            messages.put("disableSubmit", "false");
        } else {
	        try {
		        Partners partner = partnersDao.getPartnerByPartnerId((int)Integer.valueOf(partnerId));

	        	// Update the message.
		        if (partner != null) {
			        // Delete partner
		        	partner = partnersDao.delete(partner);
		            messages.put("success", "Successfully deleted Partner ID " + partnerId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("success", "No such Partner ID");
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PartnerDelete.jsp").forward(req, resp);
    }
}
