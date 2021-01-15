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
 * FindLoans allows the user to search loans by ID
 * 
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findloans
 * URL in the browser.
 * doPost() handles the http POST request. This method is called after you click the submit button.
 * 
 * To run:
 * 1. Run the SQL script to recreate your database schema
 * 2. Insert test data. You can do this by running insights.tools.Inserter (right click,
 *    Run As > JavaApplication.
 *    Notice that this is similar to Runner.java in our JDBC example.
 * 3. Run the Tomcat server at localhost.
 * 4. Point your browser to http://localhost:8080/CrowdfundingInsights/findloans.
 */
@WebServlet("/findloans")
public class FindLoans extends HttpServlet {	
	private static final long serialVersionUID = -885438509027158868L;
	protected LoansDao loansDao;
	
	@Override
	public void init() throws ServletException {
		loansDao = LoansDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        List<Loans> loans = new ArrayList<>();
        
        // Retrieve and validate ID or country code or sector or country code and sector
        // LoanID, country code, and sector are retrieved from the URL query string.
        String loanID = req.getParameter("loanid");
        String country = req.getParameter("Country");
        String sector = req.getParameter("sector");
        if ((loanID == null || loanID.trim().isEmpty())
        		&& (country == null || country.trim().isEmpty())
        		&& (sector == null || sector == "" || sector.trim().isEmpty())) {
            messages.put("success", "Please enter a loan ID or country code");
        } else {
        	// Retrieve Loan/s, and store as a message.
        	try {
        		boolean loanIDSpecified = (loanID != null && !loanID.trim().isEmpty());
        		boolean countrySpecified = (country != null && !country.trim().isEmpty());
        		boolean sectorSpecified = (sector != null && sector != "" && !sector.trim().isEmpty());
        		if (!loanIDSpecified && countrySpecified && !sectorSpecified) {
        			// filter on just country
    				loans = loansDao.getLoansForCountry(country);
    				// check for results
        			if (loans.size() == 0) {
        		        messages.put("hideDeleteUpdate", "true");
        		        messages.put("success", "No loans on record with country code " + country);
        			} else {
        		        messages.put("hideDeleteUpdate", "false");
        			     messages.put("success", "Displaying the first 100 results for loans with country code " + country);
        			}
        		} else if (!loanIDSpecified && countrySpecified && sectorSpecified) {
        				// filter on country and loan sector
        				loans = loansDao.getLoansForCountryAndSector(country, sector);      				
            			if (loans.size() == 0) {
            		        messages.put("hideDeleteUpdate", "true");
            		        messages.put("success", "No loans on record with country code " + country + 
            		        		" and sector " + sector);
            			} else {
            		        messages.put("hideDeleteUpdate", "false");
            			     messages.put("success", "Displaying the first 100 results for loans with country code " + country +
            			    		 " and sector " + sector);
            			}
        		} else if (!loanIDSpecified && !countrySpecified && sectorSpecified) {
        			// filter only on sector
    				loans = loansDao.getLoansForSector(sector);      				
        			if (loans.size() == 0) {
        		        messages.put("hideDeleteUpdate", "true");
        		        messages.put("success", "No loans on record with sector " + sector);
        			} else {
        		        messages.put("hideDeleteUpdate", "false");
        			     messages.put("success", "Displaying the first 100 results for loans with sector " + sector);
        			} 
        			
        		} else {
        			// filter on loan ID
        			Loans loan = loansDao.getLoanById(Integer.valueOf(loanID));
    				if (loan == null) {
    		        	messages.put("hideDeleteUpdate", "true");
    		        	messages.put("success", "No loan on record with ID " + loanID);
    				} else {
    					loans.add(loan);
    		        	messages.put("hideDeleteUpdate", "false");
    			     	messages.put("success", "Displaying results for Loan ID: " + loanID);
    				}
        		}
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindLoans.jsp.
        	messages.put("previousLoanID", loanID);
        }
        req.setAttribute("loan", loans);
        
        req.getRequestDispatcher("/FindLoans.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Loans> loans = new ArrayList<>();
        
        String loanID = req.getParameter("loanid");
        String country = req.getParameter("Country");
        String sector = req.getParameter("sector");
        if ((loanID == null || loanID.trim().isEmpty())
        		&& (country == null || country.trim().isEmpty())
        		&& (sector == null || sector == "" || sector.trim().isEmpty())) {
            messages.put("success", "Please enter a search parameter");
        } else {
        	// Retrieve Loan/s, and store as a message.
        	try {
        		boolean loanIDSpecified = (loanID != null && !loanID.trim().isEmpty());
        		boolean countrySpecified = (country != null && !country.trim().isEmpty());
        		boolean sectorSpecified = (sector != null && sector != "" && !sector.trim().isEmpty());
        		if (!loanIDSpecified && countrySpecified && !sectorSpecified) {
        			// filter on just country
    				loans = loansDao.getLoansForCountry(country);
    				// check for results
        			if (loans.size() == 0) {
        		        messages.put("hideDeleteUpdate", "true");
        		        messages.put("success", "No loans on record with country " + country);
        			} else {
        		        messages.put("hideDeleteUpdate", "false");
        			     messages.put("success", "Displaying the first 100 results for loans with country " + country);
        			}
        		} else if (!loanIDSpecified && countrySpecified && sectorSpecified) {
        				// filter on country and loan sector
        				loans = loansDao.getLoansForCountryAndSector(country, sector);      				
            			if (loans.size() == 0) {
            		        messages.put("hideDeleteUpdate", "true");
            		        messages.put("success", "No loans on record with country code " + country + 
            		        		" and sector " + sector);
            			} else {
            		        messages.put("hideDeleteUpdate", "false");
            			     messages.put("success", "Displaying the first 100 results for loans with country " + country +
            			    		 " and sector " + sector);
            			} 
        		} else if (!loanIDSpecified && !countrySpecified && sectorSpecified) {
        			// filter only on sector
    				loans = loansDao.getLoansForSector(sector);      				
        			if (loans.size() == 0) {
        		        messages.put("hideDeleteUpdate", "true");
        		        messages.put("success", "No loans on record with sector " + sector);
        			} else {
        		        messages.put("hideDeleteUpdate", "false");
        			     messages.put("success", "Displaying the first 100 results for loans with sector " + sector);
        			} 
        		} else {
        			// filter on loan ID
        			Loans loan = loansDao.getLoanById(Integer.valueOf(loanID));
    				if (loan == null) {
    		        	messages.put("hideDeleteUpdate", "true");
    		        	messages.put("success", "No loan on record with ID " + loanID);
    				} else {
    					loans.add(loan);
    		        	messages.put("hideDeleteUpdate", "false");
    			     	messages.put("success", "Displaying results for Loan ID: " + loanID);
    				}
        		}
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.setAttribute("loans", loans);       
        req.getRequestDispatcher("/FindLoans.jsp").forward(req, resp);
    }
}
