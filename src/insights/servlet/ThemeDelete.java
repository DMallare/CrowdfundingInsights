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


@WebServlet("/themeDelete")
public class ThemeDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        req.getRequestDispatcher("/ThemeDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        
        String themeId = req.getParameter("loanThemeId");
        
        // Retrieve and validate name.
        if (themeId == null || themeId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid themeId.");
        } else {
        	// Create a loan theme
        	try {
        		LoanThemes loanTheme = loanThemesDao.getLoanThemesByThemeId(themeId);

	        	// Update the message.
		        if (loanTheme != null) {
			        // Delete partner
		        	loanTheme = loanThemesDao.delete(loanTheme);
		            messages.put("success", "Successfully deleted Loan Theme ID " + themeId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("success", "No such Loan Theme ID");
		        }
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        }
        req.getRequestDispatcher("/ThemeDelete.jsp").forward(req, resp);
    }
	
}
