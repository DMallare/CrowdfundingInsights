

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
@WebServlet("/finddurations")
public class FindDurations extends HttpServlet {
	private static final long serialVersionUID = -885438509027158868L;
	protected LoanDurationsDao loanDurationsDao;
	
	@Override
	public void init() throws ServletException {
		loanDurationsDao = LoanDurationsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        List<LoanDurations> loanDurations = new ArrayList<LoanDurations>();
        
        // Retrieve and validate durations.
        String terminmonths = req.getParameter("terminmonths");
        String loanID = req.getParameter("loanid");
    	messages.put("hideDeleteUpdate", "true");

        if ((loanID == null || loanID.trim().isEmpty()) && (terminmonths == null || terminmonths.trim().isEmpty())) {
        	messages.put("success", "Please enter a loan ID or Duration");
        } else {
        	// Retrieve LoanDurations, and store as a message.
        	try {
        		if ((loanID == null || loanID.trim().isEmpty())) {
        			loanDurations = loanDurationsDao.getLoanDurationsByTermInMonths(Double.parseDouble(terminmonths));
        			if (loanDurations.size() == 0) {
        				messages.put("hideDeleteUpdate", "true");
    		        	messages.put("success", "No Durations on record");
        			} else {
        				messages.put("hideDeleteUpdate", "false");
    		        	messages.put("success", "Displaying results for Durations with Term In Months: " + terminmonths);
        			}
        				

        		} else if (terminmonths == null || terminmonths.trim().isEmpty()){
        			loanDurations = loanDurationsDao.getLoanDurationsByLoanId(Integer.valueOf(loanID));
        			if (loanDurations.size() == 0) {
        				messages.put("hideDeleteUpdate", "true");
    		        	messages.put("success", "No Durations on record");
        			} else {
        				messages.put("hideDeleteUpdate", "false");
    		        	messages.put("success", "Displaying results for Durations with LoanId: " + loanID);
        			}
        		} else {
        			List<LoanDurations> findByLoanId = loanDurationsDao.getLoanDurationsByLoanId(Integer.valueOf(loanID));
        			List<LoanDurations> findByDuration = loanDurationsDao.getLoanDurationsByTermInMonths(Double.parseDouble(terminmonths));
        			for (LoanDurations temp: findByDuration) {
        				if (findByLoanId.contains(temp)) {
        					loanDurations.add(temp);
        				}
        			}
        			if (loanDurations.size() == 0) {
        				messages.put("hideDeleteUpdate", "true");
    		        	messages.put("success", "No Durations on record");
        			} else {
        				messages.put("hideDeleteUpdate", "false");
    		        	messages.put("success", "Displaying results for Durations with Term In Months: " + terminmonths + " and Loan Id: " + loanID);
        			}
        		}
				
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.setAttribute("durations", loanDurations);
        
        req.getRequestDispatcher("/FindDurations.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<LoanDurations> loanDurations = new ArrayList<LoanDurations>();
        
        // Retrieve and validate durations.
        String terminmonths = req.getParameter("terminmonths");
        String loanID = req.getParameter("loanid");
    	messages.put("hideDeleteUpdate", "true");

    	if ((loanID == null || loanID.trim().isEmpty()) && (terminmonths == null || terminmonths.trim().isEmpty())) {
        	messages.put("success", "Please enter a loan ID or Duration");
        } else {
        	// Retrieve LoanDurations, and store as a message.
        	try {
        		if ((loanID == null || loanID.trim().isEmpty())) {
        			loanDurations = loanDurationsDao.getLoanDurationsByTermInMonths(Double.parseDouble(terminmonths));
        			if (loanDurations.size() == 0) {
        				messages.put("hideDeleteUpdate", "true");
    		        	messages.put("success", "No Durations on record");
        			} else {
        				messages.put("hideDeleteUpdate", "false");
    		        	messages.put("success", "Displaying results for Durations with Term In Months: " + terminmonths);
        			}
        				

        		} else if (terminmonths == null || terminmonths.trim().isEmpty()){
        			loanDurations = loanDurationsDao.getLoanDurationsByLoanId(Integer.valueOf(loanID));
        			if (loanDurations.size() == 0) {
        				messages.put("hideDeleteUpdate", "true");
    		        	messages.put("success", "No Durations on record");
        			} else {
        				messages.put("hideDeleteUpdate", "false");
    		        	messages.put("success", "Displaying results for Durations with LoanId: " + loanID);
        			}
        		} else {
        			List<LoanDurations> findByLoanId = loanDurationsDao.getLoanDurationsByLoanId(Integer.valueOf(loanID));
        			List<LoanDurations> findByDuration = loanDurationsDao.getLoanDurationsByTermInMonths(Double.parseDouble(terminmonths));
        			for (LoanDurations temp: findByDuration) {
        				if (findByLoanId.contains(temp)) {
        					loanDurations.add(temp);
        				}
        			}
        			if (loanDurations.size() == 0) {
        				messages.put("hideDeleteUpdate", "true");
    		        	messages.put("success", "No Durations on record");
        			} else {
        				messages.put("hideDeleteUpdate", "false");
    		        	messages.put("success", "Displaying results for Durations with Term In Months: " + terminmonths + " and Loan Id: " + loanID);
        			}
        		}
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.setAttribute("durations", loanDurations);
        
        req.getRequestDispatcher("/FindDurations.jsp").forward(req, resp);
    }
}