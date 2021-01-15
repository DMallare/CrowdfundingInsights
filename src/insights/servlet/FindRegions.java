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
 * FindRegions is the primary entry point into the application.
 * 
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findregions
 * URL in the browser.
 * doPost() handles the http POST request. This method is called after you click the submit button.
 * 
 * To run:
 * 1. Run the SQL script to recreate your database schema: http://goo.gl/86a11H.
 * 2. Insert test data. You can do this by running insights.tools.Inserter (right click,
 *    Run As > JavaApplication.
 *    Notice that this is similar to Runner.java in our JDBC example.
 * 3. Run the Tomcat server at localhost.
 * 4. Point your browser to http://localhost:8080/CrowdfundingInsights/findregions.
 */
@WebServlet("/findregions")
public class FindRegions extends HttpServlet {
	
	protected RegionsDao regionsDao;
	
	@Override
	public void init() throws ServletException {
		regionsDao = RegionsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Regions> regions = new ArrayList<Regions>();
        
        // Retrieve and validate name.
        // region is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindRegions.jsp).
        String region = req.getParameter("region");
    	messages.put("hideDeleteUpdate", "true");

        if (region == null || region.trim().isEmpty()) {
        	try {
        		regions = regionsDao.getAllRegions();
				if (regions.size() == 0) {
		        	messages.put("hideDeleteUpdate", "true");
		        	messages.put("success", "No regions on record");
				} else {
		        	messages.put("hideDeleteUpdate", "false");
		        	messages.put("success", "Displaying results for all regions");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        } else {
        	// Retrieve Regions, and store as a message.
        	try {
        		Regions currentRegion = regionsDao.getRegionByRegion(region);
        		regions.add(currentRegion);
				if (currentRegion == null) {
		        	messages.put("hideDeleteUpdate", "true");
		        	messages.put("success", "No regions on record");
				} else {
		        	messages.put("hideDeleteUpdate", "false");
		        	messages.put("success", "Displaying results for Region: " + region);
				}
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.setAttribute("regions", regions);
        
        req.getRequestDispatcher("/FindRegions.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Regions> regions = new ArrayList<Regions>();
        
        // Retrieve and validate name.
        // region is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindRegions.jsp).
        String region = req.getParameter("region");
    	messages.put("hideDeleteUpdate", "true");

        if (region == null || region.trim().isEmpty()) {
        	try {
				regions = regionsDao.getAllRegions();
				if (regions.size() == 0) {
		        	messages.put("hideDeleteUpdate", "true");
		        	messages.put("success", "No regions on record");
				} else {
		        	messages.put("hideDeleteUpdate", "false");
		        	messages.put("success", "Displaying results for all regions");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        } else {
        	// Retrieve Regions, and store as a message.
        	try {
        		Regions currentRegion = regionsDao.getRegionByRegion(region);
        		regions.add(currentRegion);
				if (currentRegion == null) {
		        	messages.put("hideDeleteUpdate", "true");
		        	messages.put("success", "No regions on record");
				} else {
		        	messages.put("hideDeleteUpdate", "false");
		        	messages.put("success", "Displaying results for Region: " + region);
				}
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.setAttribute("regions", regions);
        
        req.getRequestDispatcher("/FindRegions.jsp").forward(req, resp);
    }
}
