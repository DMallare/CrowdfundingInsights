package insights.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import insights.dal.CountryDeficitDao;
import insights.dal.SectorDeficitDao;
import insights.model.CountryDeficit;
import insights.model.SectorDeficit;


@WebServlet("/loandeficitanalysis")
public class LoanDeficitAnalysis  extends HttpServlet{
	private static final long serialVersionUID = -1283915797204721439L;
	protected CountryDeficitDao analysisDao;
	protected SectorDeficitDao sectorDeficitDao;
	
	@Override
	public void init() throws ServletException {
		analysisDao = CountryDeficitDao.getInstance();
		sectorDeficitDao = SectorDeficitDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<CountryDeficit> resultsByCountry = new ArrayList<CountryDeficit>();

        try {
        	resultsByCountry = analysisDao.getCountriesWithSmallestLoanDeficitRatio();
	        if (resultsByCountry != null && resultsByCountry.size() > 0) {
	            messages.put("success", "Analysis Successful");
	        } else {
	        	messages.put("success", "Analysis Unsuccessful");
	        }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
	    req.setAttribute("resultsCountry", resultsByCountry);
        
        try {
	        
	        List<SectorDeficit> resultsSector = new ArrayList<>();
	        resultsSector = sectorDeficitDao.getSectorsWithSmallestLoanDeficitRatio();
	        if (resultsSector != null && resultsSector.size() > 0) {
	            messages.put("success", "Analysis Successful");
	        } else {
	        	messages.put("success", "Analysis Unsuccessful");
	        }
	        
	        req.setAttribute("resultsSector", resultsSector);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
      
        req.getRequestDispatcher("/LoanDeficit.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<CountryDeficit> resultsByCountry = new ArrayList<CountryDeficit>();

        try {
        	resultsByCountry = analysisDao.getCountriesWithSmallestLoanDeficitRatio();
	        if (resultsByCountry != null && resultsByCountry.size() > 0) {
	            messages.put("success", "Analysis Successful");
	        } else {
	        	messages.put("success", "Analysis Unsuccessful");
	        }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
	    req.setAttribute("resultsCountry", resultsByCountry);
        
        try {
	        
	        List<SectorDeficit> resultsSector = new ArrayList<>();
	        resultsSector = sectorDeficitDao.getSectorsWithSmallestLoanDeficitRatio();
	        if (resultsSector != null && resultsSector.size() > 0) {
	            messages.put("success", "Analysis Successful");
	        } else {
	        	messages.put("success", "Analysis Unsuccessful");
	        }
	        
	        req.setAttribute("resultsSector", resultsSector);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
      
        req.getRequestDispatcher("/LoanDeficit.jsp").forward(req, resp);
	}
	

}
