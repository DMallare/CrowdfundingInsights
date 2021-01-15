package insights.servlet;

import insights.dal.*;
import insights.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/loanupdate")
public class LoanUpdate extends HttpServlet {
	private static final long serialVersionUID = -7133386366945478263L;
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

        // Retrieve loan and validate.
        String loanId = req.getParameter("loanid");
        if (loanId == null || loanId.trim().isEmpty()) {
            messages.put("success", "Please enter a loan ID");
        } else {
        	try {
        		Loans loanToUpdate = loansDao.getLoanById(Integer.valueOf(loanId));
        		if (loanToUpdate == null) {
        			messages.put("success", "Loan with ID " + loanId + " does not exist.");
        		}
        		req.setAttribute("loan", loanToUpdate);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/LoanUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve loan and validate.
        String loanId = req.getParameter("loanid");
        if (loanId == null || loanId.trim().isEmpty()) {
            messages.put("success", "Please input a valid loan ID");
        } else {
        	try {
        		Loans loan = loansDao.getLoanById(Integer.valueOf(loanId));
        		if (loan == null) {
        			messages.put("success", "loan ID " + loanId + " does not exist.");
        		} else {
        			String newFundedAmount = req.getParameter("fundedAmount");
        			if (newFundedAmount == null || newFundedAmount.trim().isEmpty() 
        					|| Double.valueOf(newFundedAmount) < 1) {
        	            messages.put("success", "Please enter a valid funded amount.");
        	        } else {
        	        	loan = loansDao.updateFundedAmount(loan, Double.valueOf(newFundedAmount));
        	        	messages.put("success", "Successfully updated funded amount to: " + newFundedAmount);
        	        }
        		}
        		req.setAttribute("loan", loan);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/LoanUpdate.jsp").forward(req, resp);
    }
}

