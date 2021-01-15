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

import org.eclipse.jdt.internal.compiler.apt.dispatch.RoundDispatcher;

import insights.dal.LoanThemesDao;
import insights.model.LoanThemes;
import insights.model.LoanThemesGeo;
import insights.model.LoanThemesGeoCountry;
import insights.model.LoanThemesGeoData;

@WebServlet("/findloanthemes")
public class FindLoanThemes extends HttpServlet {
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

//        LoanThemes loanTheme = new LoanThemes();
//        String loanThemeId = req.getParameter("loanThemeId");
        
        List<LoanThemesGeo> loanThemesGeos = new ArrayList<LoanThemesGeo>();
        String loanThemeDesp = req.getParameter("loanThemeDesp");
       
         
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeos = loanThemesDao.getLoanThemesGeoInfo(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousLoanDescription", loanThemeDesp);
        }
        req.setAttribute("loanThemeGeos", loanThemesGeos);
        
        
        
        List<LoanThemesGeoData> loanThemesGeoCntDatas = new ArrayList<LoanThemesGeoData>();
        
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeoCntDatas = loanThemesDao.getLoanThemesTopCountRegions(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousLoanDescription", loanThemeDesp);
        }
        req.setAttribute("loanThemeGeoDatasCnt", loanThemesGeoCntDatas);
        
        
        List<LoanThemesGeoData> loanThemesGeoSumDatas = new ArrayList<LoanThemesGeoData>();
        
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeoSumDatas = loanThemesDao.getLoanThemesTopSumRegions(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousLoanDescription", loanThemeDesp);
        }
        req.setAttribute("loanThemeGeoDatasSum", loanThemesGeoSumDatas);
        
        
        
        
        List<LoanThemesGeoCountry> loanThemesGeoCountries = new ArrayList<LoanThemesGeoCountry>();
        
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeoCountries = loanThemesDao.getLoanThemesTopCountryIndex(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousLoanDescription", loanThemeDesp);
        }
        req.setAttribute("loanThemesGeoCountries", loanThemesGeoCountries);
        
        
        
        
        List<LoanThemesGeoCountry> loanThemesGeoCountriesAll = new ArrayList<LoanThemesGeoCountry>();
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeoCountriesAll = loanThemesDao.getLoanThemesTopCountryIndexAll(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        }
    
        Map<String, Double> loanCountryMap = new HashMap<String, Double>();
        if (loanThemesGeoCountriesAll != null && loanThemesGeoCountriesAll.size() > 0) {
            double low = loanThemesGeoCountriesAll.get(0).getIndex();
            double high = loanThemesGeoCountriesAll.get(loanThemesGeoCountriesAll.size() - 1).getIndex();
            
            for (LoanThemesGeoCountry country : loanThemesGeoCountriesAll) {
            	double temp = 10 - (country.getIndex() - low) / (high - low) * (10 - 0) + 0;
            	loanCountryMap.put(country.getCountryName(), temp);
            }
        }
        
        req.setAttribute("loanCountryMap", loanCountryMap);
        
        
        req.getRequestDispatcher("/FindLoanThemes.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

//        LoanThemes loanTheme = new LoanThemes();
        
        List<LoanThemesGeo> loanThemesGeos = new ArrayList<LoanThemesGeo>();
        String loanThemeDesp = req.getParameter("loanThemeDesp");
        
        // Retrieve and validate name.
        // firstname is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindUsers.jsp).
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		loanThemesGeos = loanThemesDao.getLoanThemesGeoInfo(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        }
        req.setAttribute("loanThemeGeos", loanThemesGeos);
        
        
        List<LoanThemesGeoData> loanThemesGeoCntDatas = new ArrayList<LoanThemesGeoData>();
        
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeoCntDatas = loanThemesDao.getLoanThemesTopCountRegions(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        }
        req.setAttribute("loanThemeGeoDatasCnt", loanThemesGeoCntDatas);
        
        
        List<LoanThemesGeoData> loanThemesGeoSumDatas = new ArrayList<LoanThemesGeoData>();
        
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeoSumDatas = loanThemesDao.getLoanThemesTopSumRegions(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        }
        req.setAttribute("loanThemeGeoDatasSum", loanThemesGeoSumDatas);
        
        
        
        List<LoanThemesGeoCountry> loanThemesGeoCountries = new ArrayList<LoanThemesGeoCountry>();
        
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeoCountries = loanThemesDao.getLoanThemesTopCountryIndex(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        }
        req.setAttribute("loanThemesGeoCountries", loanThemesGeoCountries);
        
        
        
        List<LoanThemesGeoCountry> loanThemesGeoCountriesAll = new ArrayList<LoanThemesGeoCountry>();
        if (loanThemeDesp == null || loanThemeDesp.trim().isEmpty()) {
            messages.put("success", "Please enter a valid loan theme.");
        } else {
        	// Retrieve LoanThemes, and store as a message.
        	try {
        		loanThemesGeoCountriesAll = loanThemesDao.getLoanThemesTopCountryIndexAll(loanThemeDesp);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + loanThemeDesp);
        }
        
        
        Map<String, Double> loanCountryMap = new HashMap<String, Double>();
        
        if (loanThemesGeoCountriesAll != null && loanThemesGeoCountriesAll.size() > 0) {
            double low = loanThemesGeoCountriesAll.get(0).getIndex();
            double high = loanThemesGeoCountriesAll.get(loanThemesGeoCountriesAll.size() - 1).getIndex();
            
            for (LoanThemesGeoCountry country : loanThemesGeoCountriesAll) {
            	double temp = 10 - (country.getIndex() - low) / (high - low) * (10 - 0) + 0;
            	loanCountryMap.put(country.getCountryName(), temp);
            }
        }
        

        req.setAttribute("loanCountryMap", loanCountryMap);
        
        
        req.getRequestDispatcher("/FindLoanThemes.jsp").forward(req, resp);
    }

}
