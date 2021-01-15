package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import insights.model.LoanThemes;
import insights.model.LoanThemesGeo;
import insights.model.LoanThemesGeoCountry;
import insights.model.LoanThemesGeoData;

public class LoanThemesDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static LoanThemesDao instance = null;
	protected LoanThemesDao() {
		connectionManager = new ConnectionManager();
	}
	public static LoanThemesDao getInstance() {
		if(instance == null) {
			instance = new LoanThemesDao();
		}
		return instance;
	}
	
	public LoanThemes create(LoanThemes loanTheme) throws SQLException {
		String insertLoanTheme = "INSERT INTO LoanThemes(LoanThemeID, LoanThemeGeneralDescription) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLoanTheme);

			// replace _ to blank
			insertStmt.setString(1, loanTheme.getLoanThemeId());
			insertStmt.setString(2, loanTheme.getLoanThemeDescription());

			insertStmt.executeUpdate();
			
			return loanTheme;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
		
	}
	
	/**
	 * Get the Loan Themes record by fetching it from MySQL instance.
	 * This runs a SELECT statement and returns a single Partners instance.
	 */
	public List<LoanThemes> getAllLoanThemes() throws SQLException {
		List<LoanThemes> loanThemes = new ArrayList<LoanThemes>();
		String selectLoanThemes = "SELECT * FROM LoanThemes;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanThemes);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String themeId = results.getString("LoanThemeID");
				String partnerName = results.getString("LoanThemeGeneralDescription");

				LoanThemes loanTheme = new LoanThemes(themeId, partnerName);
				loanThemes.add(loanTheme);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return loanThemes;
	}
	
	public LoanThemes getLoanThemesByThemeId(String themeId) throws SQLException {
		String selectLoanTheme = "SELECT LoanThemeID, LoanThemeGeneralDescription FROM LoanThemes WHERE LoanThemeID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanTheme);
			selectStmt.setString(1, themeId);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String resultLoanThemeId = results.getString("LoanThemeID");
				String loanThemeDescription = results.getString("LoanThemeGeneralDescription");
				LoanThemes loanThemes = new LoanThemes(resultLoanThemeId, loanThemeDescription);
				return loanThemes;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	public LoanThemes updateLoanThemeDescription(LoanThemes loanTheme, String description) throws SQLException {
		String updateThemeDescription = "UPDATE LoanThemes SET LoanThemeGeneralDescription=? WHERE LoanThemeID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateThemeDescription);
			updateStmt.setString(1, description);
			updateStmt.setString(2, loanTheme.getLoanThemeId());
			updateStmt.executeUpdate();
			

			loanTheme.setLoanThemeDescription(description);
			return loanTheme;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public LoanThemes delete(LoanThemes loanTheme) throws SQLException {
		String deleteLoanTheme = "DELETE FROM LoanThemes WHERE LoanThemeID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteLoanTheme);
			deleteStmt.setString(1, loanTheme.getLoanThemeId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	
	public List<LoanThemes> getLoanThemesFromDescription(String description) throws SQLException {
		List<LoanThemes> loanThemes = new ArrayList<LoanThemes>();
		String selectLoanThemes =
			"SELECT LoanThemeID, LoanThemeGeneralDescription FROM LoanThemes WHERE LoanThemeGeneralDescription=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanThemes);
			selectStmt.setString(1, description);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String loanThemeId = results.getString("LoanThemeID");
				String resultDescription = results.getString("LoanThemeGeneralDescription");
				
				LoanThemes loanTheme = new LoanThemes(loanThemeId, resultDescription);
				loanThemes.add(loanTheme);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return loanThemes;
	}
	
	public List<LoanThemesGeo> getLoanThemesGeoInfo(String description) throws SQLException {
		List<LoanThemesGeo> loanThemesGeos = new ArrayList<LoanThemesGeo>();
		String selectLoanThemeGeos =
				"SELECT LoanThemeGeneralDescription AS LoanThemeDesp, LoanID, LoanAmount,Country, Region, LoanActivities, Description\n"
				+ "FROM (\n"
				+ "SELECT Loans.LoanThemeID AS LoanThemeID, LoanThemeGeneralDescription, LoanID, LoanAmount, CountryCode, Region, LoanActivities, Description\n"
				+ "FROM Loans INNER JOIN (\n"
				+ "	SELECT *\n"
				+ "	FROM LoanThemes\n"
				+ "	WHERE LoanThemeGeneralDescription = ?) AS A\n"
				+ "on Loans.LoanThemeID = A.LoanThemeID ) AS B\n"
				+ "LEFT OUTER JOIN Countries ON B.CountryCode = Countries.CountryCode\n"
				+ "ORDER BY LoanAmount DESC\n"
				+ "LIMIT 100;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanThemeGeos);
			
			String[] words = description.trim().split(" ");
			// capitalize each word
			for (int i = 0; i < words.length; i++)
			{
			    words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
			}
			// rejoin back into a sentence
			description = String.join(" ", words);
			
			selectStmt.setString(1, description);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String loanThemeDesp = results.getString("LoanThemeDesp");
				int loanID = results.getInt("LoanID");
				int loanAmount = results.getInt("LoanAmount");
				String countryName = results.getString("Country");
				String region = results.getString("Region");
				String loanActivity = results.getString("LoanActivities");
				String resultDescription = results.getString("Description");
				
				LoanThemesGeo loanThemesGeo = new LoanThemesGeo(loanThemeDesp, loanID, loanAmount, countryName, region, loanActivity, resultDescription);
				loanThemesGeos.add(loanThemesGeo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return loanThemesGeos;
	}
	
	public List<LoanThemesGeoData> getLoanThemesTopCountRegions(String description) throws SQLException {
		List<LoanThemesGeoData> loanThemesGeoDatas = new ArrayList<LoanThemesGeoData>();
		String selectLoanThemeGeoDatas =
				"SELECT LoanThemeGeneralDescription AS LoanThemeDesp, Country, Region, COUNT(*) AS CNT\n"
				+ "FROM (\n"
				+ "SELECT Loans.LoanThemeID AS LoanThemeID, LoanThemeGeneralDescription, LoanID, LoanAmount, CountryCode, Region, LoanActivities, Description\n"
				+ "FROM Loans INNER JOIN (\n"
				+ "	SELECT *\n"
				+ "	FROM LoanThemes\n"
				+ "	WHERE LoanThemeGeneralDescription = ?) AS A\n"
				+ "on Loans.LoanThemeID = A.LoanThemeID ) AS B\n"
				+ "LEFT OUTER JOIN Countries ON B.CountryCode = Countries.CountryCode\n"
				+ "GROUP BY Country, Region\n"
				+ "ORDER BY CNT DESC\n"
				+ "LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanThemeGeoDatas);
			
			String[] words = description.trim().split(" ");
			// capitalize each word
			for (int i = 0; i < words.length; i++)
			{
			    words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
			}
			// rejoin back into a sentence
			description = String.join(" ", words);
			
			selectStmt.setString(1, description);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String loanThemeDesp = results.getString("LoanThemeDesp");
				String countryName = results.getString("Country");
				String region = results.getString("Region");
				Double data = results.getDouble("CNT");
				
				LoanThemesGeoData loanThemesGeoData = new LoanThemesGeoData(loanThemeDesp, countryName, region, data);
				
				loanThemesGeoDatas.add(loanThemesGeoData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return loanThemesGeoDatas;
	}


	public List<LoanThemesGeoData> getLoanThemesTopSumRegions(String description) throws SQLException {
		List<LoanThemesGeoData> loanThemesGeoDatas = new ArrayList<LoanThemesGeoData>();
		String selectLoanThemeGeoDatas =
				"SELECT LoanThemeGeneralDescription AS LoanThemeDesp, Country, Region, SUM(LoanAmount) AS SUM\n"
				+ "FROM (\n"
				+ "SELECT Loans.LoanThemeID AS LoanThemeID, LoanThemeGeneralDescription, LoanID, LoanAmount, CountryCode, Region, LoanActivities, Description\n"
				+ "FROM Loans INNER JOIN (\n"
				+ "	SELECT *\n"
				+ "	FROM LoanThemes\n"
				+ "	WHERE LoanThemeGeneralDescription = ?) AS A\n"
				+ "on Loans.LoanThemeID = A.LoanThemeID ) AS B\n"
				+ "LEFT OUTER JOIN Countries ON B.CountryCode = Countries.CountryCode\n"
				+ "GROUP BY Country, Region\n"
				+ "ORDER BY SUM DESC\n"
				+ "LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanThemeGeoDatas);
			
			String[] words = description.trim().split(" ");
			// capitalize each word
			for (int i = 0; i < words.length; i++)
			{
			    words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
			}
			// rejoin back into a sentence
			description = String.join(" ", words);
			
			selectStmt.setString(1, description);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String loanThemeDesp = results.getString("LoanThemeDesp");
				String countryName = results.getString("Country");
				String region = results.getString("Region");
				Double data = results.getDouble("SUM");
				
				LoanThemesGeoData loanThemesGeoData = new LoanThemesGeoData(loanThemeDesp, countryName, region, data);
				
				loanThemesGeoDatas.add(loanThemesGeoData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return loanThemesGeoDatas;
	}
	
	
	public List<LoanThemesGeoCountry> getLoanThemesTopCountryIndex(String description) throws SQLException {
		List<LoanThemesGeoCountry> loanThemesGeoCountries = new ArrayList<LoanThemesGeoCountry>();
		String selectLoanThemesGeoCountries =
				"SELECT LoanThemeDesp, C.Country AS Country, ROUND(AVG, 2) AS AVG_Amount, GDP, ROUND(AVG / GDP, 3) AS `Index`\n"
				+ "FROM (\n"
				+ "SELECT LoanThemeGeneralDescription AS LoanThemeDesp, Country, AVG(LoanAmount) AS AVG\n"
				+ "FROM (\n"
				+ "SELECT Loans.LoanThemeID AS LoanThemeID, LoanThemeGeneralDescription, LoanID, LoanAmount, CountryCode, Region, LoanActivities, Description\n"
				+ "FROM Loans INNER JOIN (\n"
				+ "	SELECT *\n"
				+ "	FROM LoanThemes\n"
				+ "	WHERE LoanThemeGeneralDescription = ?) AS A\n"
				+ "on Loans.LoanThemeID = A.LoanThemeID ) AS B\n"
				+ "LEFT OUTER JOIN Countries ON B.CountryCode = Countries.CountryCode\n"
				+ "GROUP BY Country ) AS C\n"
				+ "INNER JOIN CountryGDP ON C.Country = CountryGDP.Country\n"
				+ "ORDER BY AVG/GDP\n"
				+ "LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanThemesGeoCountries);
			
			String[] words = description.trim().split(" ");
			// capitalize each word
			for (int i = 0; i < words.length; i++)
			{
			    words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
			}
			// rejoin back into a sentence
			description = String.join(" ", words);
			
			selectStmt.setString(1, description);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String loanThemeDesp = results.getString("LoanThemeDesp");
				String countryName = results.getString("Country");
				Double avgLoanAmount = results.getDouble("AVG_Amount");
				Double GDP = results.getDouble("GDP");
				Double index = results.getDouble("Index");
				
				LoanThemesGeoCountry loanThemesGeoCountry = new LoanThemesGeoCountry(loanThemeDesp, countryName, avgLoanAmount, GDP, index);
				
				
				loanThemesGeoCountries.add(loanThemesGeoCountry);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return loanThemesGeoCountries;
	}
	
	
	public List<LoanThemesGeoCountry> getLoanThemesTopCountryIndexAll(String description) throws SQLException {
		List<LoanThemesGeoCountry> loanThemesGeoCountries = new ArrayList<LoanThemesGeoCountry>();
		String selectLoanThemesGeoCountries =
				"SELECT LoanThemeDesp, C.Country AS Country, ROUND(AVG, 2) AS AVG_Amount, GDP, ROUND(AVG / GDP, 3) AS `Index`\n"
				+ "FROM (\n"
				+ "SELECT LoanThemeGeneralDescription AS LoanThemeDesp, Country, AVG(LoanAmount) AS AVG\n"
				+ "FROM (\n"
				+ "SELECT Loans.LoanThemeID AS LoanThemeID, LoanThemeGeneralDescription, LoanID, LoanAmount, CountryCode, Region, LoanActivities, Description\n"
				+ "FROM Loans INNER JOIN (\n"
				+ "	SELECT *\n"
				+ "	FROM LoanThemes\n"
				+ "	WHERE LoanThemeGeneralDescription = ?) AS A\n"
				+ "on Loans.LoanThemeID = A.LoanThemeID ) AS B\n"
				+ "LEFT OUTER JOIN Countries ON B.CountryCode = Countries.CountryCode\n"
				+ "GROUP BY Country ) AS C\n"
				+ "INNER JOIN CountryGDP ON C.Country = CountryGDP.Country\n"
				+ "ORDER BY AVG/GDP;";

		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanThemesGeoCountries);
			
			String[] words = description.trim().split(" ");
			// capitalize each word
			for (int i = 0; i < words.length; i++)
			{
			    words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
			}
			// rejoin back into a sentence
			description = String.join(" ", words);
			
			selectStmt.setString(1, description);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String loanThemeDesp = results.getString("LoanThemeDesp");
				String countryName = results.getString("Country");
				Double avgLoanAmount = results.getDouble("AVG_Amount");
				Double GDP = results.getDouble("GDP");
				Double index = results.getDouble("Index");
				
				LoanThemesGeoCountry loanThemesGeoCountry = new LoanThemesGeoCountry(loanThemeDesp, countryName, avgLoanAmount, GDP, index);
				
				
				loanThemesGeoCountries.add(loanThemesGeoCountry);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return loanThemesGeoCountries;
	}

}
