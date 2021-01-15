package insights.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import insights.dal.LoanThemesDao;
import insights.model.LoanThemes;
import insights.model.LoanThemesGeo;

@WebServlet("/themeCreate")
public class ThemeCreate extends HttpServlet {
	protected LoanThemesDao loanThemesDao;
	
	@Override
	public void init() throws ServletException {
		loanThemesDao = LoanThemesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/ThemeCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        
        String themeId = req.getParameter("loanThemeId");
        String themeDesp = req.getParameter("loanThemeDesp");
        
  
        // Retrieve and validate name.
        if (themeId == null || themeId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid themeId.");
        } else if (themeDesp == null || themeDesp.trim().isEmpty()) {
        	messages.put("success", "Please enter a valid theme description.");
        } else {
        	// Create a loan theme
        	try {
        		List<LoanThemes> loanThemes = loanThemesDao.getAllLoanThemes();
        		Set<String> loanThemeIds = new HashSet<String>();
        		for (LoanThemes loanTheme : loanThemes) {
        			loanThemeIds.add(loanTheme.getLoanThemeId());
        		}
        		
        		LoanThemes newLoanThemes = new LoanThemes(themeId, themeDesp);
        		
        		if (!loanThemeIds.contains(themeId)) {
        			newLoanThemes = loanThemesDao.create(newLoanThemes);
		        	messages.put("success", "Successfully created Loan Theme ID: " + themeId + ", Description: " + themeDesp);
        		} else {
		        	messages.put("success", "This Loan Theme ID already exists, please change to another.");
        		}
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.getRequestDispatcher("/ThemeCreate.jsp").forward(req, resp);
    }

}
