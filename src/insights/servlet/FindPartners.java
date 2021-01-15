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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * FindPartners is the primary entry point into the application.
 * 
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findpartners
 * URL in the browser.
 * doPost() handles the http POST request. This method is called after you click the submit button.
 * 
 * To run:
 * 1. Run the SQL script to recreate your database schema: http://goo.gl/86a11H.
 * 2. Insert test data. You can do this by running insights.tools.Inserter (right click,
 *    Run As > JavaApplication.
 *    Notice that this is similar to Runner.java in our JDBC example.
 * 3. Run the Tomcat server at localhost.
 * 4. Point your browser to http://localhost:8080/CrowdfundingInsights/findpartners.
 */
@WebServlet("/findpartners")
public class FindPartners extends HttpServlet {
	
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

        List<Partners> partners = new ArrayList<Partners>();
        
        // Retrieve and validate name.
        // partnerId is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindPartners.jsp).
        String partnerId = req.getParameter("partnerid");
    	messages.put("hideDeleteUpdate", "true");

        if (partnerId == null || partnerId.trim().isEmpty()) {
        	try {
				partners = partnersDao.getAllPartners();
				if (partners.size() == 0) {
		        	messages.put("hideDeleteUpdate", "true");
		        	messages.put("success", "No partners on record");
				} else {
		        	messages.put("hideDeleteUpdate", "false");
		        	messages.put("success", "Displaying results for all partners");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        } else {
        	// Retrieve Partners, and store as a message.
        	try {
        		Partners partner = partnersDao.getPartnerByPartnerId(Integer.valueOf(partnerId));
        		partners.add(partner);
				if (partner == null) {
		        	messages.put("hideDeleteUpdate", "true");
		        	messages.put("success", "No partners on record");
				} else {
		        	messages.put("hideDeleteUpdate", "false");
		        	messages.put("success", "Displaying results for Partner ID: " + partnerId);
				}
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.setAttribute("partners", partners);
        
        req.getRequestDispatcher("/FindPartners.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Partners> partners = new ArrayList<Partners>();
        
        // Retrieve and validate name.
        // partnerId is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindPartners.jsp).
        String partnerId = req.getParameter("partnerid");
    	messages.put("hideDeleteUpdate", "true");

        if (partnerId == null || partnerId.trim().isEmpty()) {
        	try {
				partners = partnersDao.getAllPartners();
				if (partners.size() == 0) {
		        	messages.put("hideDeleteUpdate", "true");
		        	messages.put("success", "No partners on record");
				} else {
		        	messages.put("hideDeleteUpdate", "false");
		        	messages.put("success", "Displaying results for all partners");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        } else {
        	// Retrieve Partners, and store as a message.
        	try {
        		Partners partner = partnersDao.getPartnerByPartnerId(Integer.valueOf(partnerId));
        		partners.add(partner);
				if (partner == null) {
		        	messages.put("hideDeleteUpdate", "true");
		        	messages.put("success", "No partners on record");
				} else {
		        	messages.put("hideDeleteUpdate", "false");
		        	messages.put("success", "Displaying results for Partner ID: " + partnerId);
				}
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.setAttribute("partners", partners);
        
        req.getRequestDispatcher("/FindPartners.jsp").forward(req, resp);
    }
}
