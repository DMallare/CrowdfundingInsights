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


@WebServlet("/loandetail")
public class LoanDetail extends HttpServlet {	
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
        // Retrieve and validate ID
        String loanId = req.getParameter("loanid");
        if (loanId == null || loanId.trim().isEmpty()) {
            messages.put("success", "Loan ID " + loanId + " is invalid.");
            messages.put("disableSubmit", "false");
        } else {
	        try {
		        Loans loan = loansDao.getLoanById((int)Integer.valueOf(loanId));
	            loans.add(loan);
	        	// Update the message.
		        if (loan != null) {
			        // Show loan details
		            messages.put("success", "Details for loan: " + loanId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("success", "No such loan with ID " + loanId);
		        	messages.put("disableSubmit", "false");
		        }
		        req.setAttribute("loans", loans);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/LoanDetail.jsp").forward(req, resp);
    }
	
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Loans> loans = new ArrayList<>();
        // Retrieve and validate ID
        String loanId = req.getParameter("loanid");
        if (loanId == null || loanId.trim().isEmpty()) {
            messages.put("success", "Loan ID " + loanId + " is invalid.");
            messages.put("disableSubmit", "false");
        } else {
	        try {
		        Loans loan = loansDao.getLoanById((int)Integer.valueOf(loanId));
	            loans.add(loan);
	        	// Update the message.
		        if (loan != null) {
			        // Show loan details
		            messages.put("success", "Details for loan: " + loanId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("success", "No such loan with ID " + loanId);
		        	messages.put("disableSubmit", "false");
		        }
		        req.setAttribute("loans", loans);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/LoanDetail.jsp").forward(req, resp);
    }
}
