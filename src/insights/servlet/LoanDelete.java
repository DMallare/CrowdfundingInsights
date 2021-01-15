package insights.servlet;

import insights.dal.*;
import insights.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loandelete")
public class LoanDelete extends HttpServlet {
	private static final long serialVersionUID = 489424660876749619L;
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
        // Provide a title and render the JSP.
        req.getRequestDispatcher("/LoanDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String loanId = req.getParameter("loanid");
        if (loanId == null || loanId.trim().isEmpty()) {
            messages.put("success", "Loan ID " + loanId + " is invalid.");
            messages.put("disableSubmit", "false");
        } else {
	        try {
		        Loans loan = loansDao.getLoanById((int)Integer.valueOf(loanId));

	        	// Update the message.
		        if (loan != null) {
			        // Delete loan
		        	loan = loansDao.delete(loan);
		            messages.put("success", "Successfully deleted loan ID " + loanId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("success", "No such loan with ID " + loanId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/LoanDelete.jsp").forward(req, resp);
    }

}
