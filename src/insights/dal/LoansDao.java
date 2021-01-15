package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import insights.model.*;

public class LoansDao {
	protected ConnectionManager connectionManager;
	private static LoansDao instance = null;
	
	protected LoansDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static LoansDao getInstance() {
		if(instance == null) {
			instance = new LoansDao();
		}
		return instance;
	}

	/**
	 * Creates a new Loans instance by storing it in MySQL.
	 * Runs an INSERT statement.
	 * @param Loan - the loan to be inserted into the Database.
	 * @return - the loan that was inserted into the Database.
	 * @throws SQLException
	 */
	public Loans create(Loans loan) throws SQLException {
		String insertLoan = "INSERT INTO Loans(LoanID,FundedAmount,LoanAmount,"
				+ "CountryCode,PartnerID,Description,Region,PostedDate,"
				+ "LoanActivities,LoanthemeID)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLoan);
			insertStmt.setInt(1, loan.getLoanID());
			insertStmt.setDouble(2, loan.getFundedAmount());
			insertStmt.setDouble(3, loan.getLoanAmount());
			insertStmt.setString(4, loan.getCountryCode());
			insertStmt.setInt(5, loan.getPartnerId());
			insertStmt.setString(6, loan.getDescription());
			insertStmt.setString(7, loan.getRegion());
			insertStmt.setTimestamp(8, new Timestamp(loan.getPostedDate().getTime()));
			insertStmt.setString(9, loan.getLoanActivities());
			insertStmt.setString(10, loan.getLoanThemeID());		
			insertStmt.executeUpdate();
			return loan;
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
	 * Updates the funded amount of the loan to the given amount
	 * @param loan - the loan to update
	 * @param fundedAmount - the given funded amount
	 * @return - the updated loan
	 * @throws SQLException
	 */
	public Loans updateFundedAmount(Loans loan, Double fundedAmount) throws SQLException {
		String updateLoan = "UPDATE Loans SET FundedAmount=? WHERE LoanID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateLoan);
			updateStmt.setDouble(1, fundedAmount);
			updateStmt.setInt(2, loan.getLoanID());
			updateStmt.executeUpdate();
			// Update the loan funded amount before returning to the caller.
			loan.setFundedAmount(fundedAmount);
			return loan;
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
	
	/**
	 * Delete the Loans instance.
	 * This runs a DELETE statement.
	 * @param loan - the loan to be deleted
	 * @throws SQLException
	 */
	public Loans delete(Loans loan) throws SQLException {
		String deleteLoan = "DELETE FROM Loans WHERE LoanID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteLoan);
			deleteStmt.setInt(1, loan.getLoanID());
			deleteStmt.executeUpdate();
			// Return null so the caller can no longer operate on the BlogPosts instance.
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
	
	/**
	 * Get the Loans record by fetching it from the database
	 * This runs a SELECT statement and returns a single Loans instance.
	 * @param loanID - the Id of the loan we want to retrieve
	 * @return - the loan corresponding to the given Id
	 * @throws SQLException
	 */
	public Loans getLoanById(int loanID) throws SQLException {
		String selectLoan =
			"SELECT LoanID,FundedAmount,LoanAmount,CountryCode,PartnerID," +
			"Description,Region,PostedDate,LoanActivities,LoanThemeID " +
			"FROM Loans " +
			"WHERE LoanID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoan);
			selectStmt.setInt(1, loanID);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultLoanID = results.getInt("LoanID");
				double fundedAmount = results.getDouble("FundedAmount");
				double loanAmount = results.getDouble("LoanAmount");
				String countryCode = results.getString("CountryCode");
				int partnerID = results.getInt("PartnerID");
				String description = results.getString("Description");
				String region = results.getString("Region");
				Date postedDate =  new Date(results.getTimestamp("PostedDate").getTime());
				String loanActivities = results.getString("LoanActivities");
				String loanThemeID = results.getString("LoanThemeID");
				
				Loans loan = new Loans(resultLoanID, fundedAmount, loanAmount,
						countryCode, partnerID, description, region, postedDate,
						loanActivities, loanThemeID);
				return loan;
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
	
	
	
	/**
	 * Get the first 1000 the Loans corresponding to the given Country Code
	 * @param countryCode
	 * @return - a list of the first 1000 loans corresponding to the given country code
	 * @throws SQLException
	 */
	public List<Loans> getLoansForCountry(String country) throws SQLException {
		List<Loans> loans = new ArrayList<>();
		String selectLoans =
			"SELECT LoanID,FundedAmount,LoanAmount,Loans.CountryCode,PartnerID," +
				"Description,Region,PostedDate,LoanActivities,LoanThemeID, Country " +
				"FROM Loans INNER JOIN Countries " +
				"ON Loans.CountryCode = Countries.CountryCode " +
				"WHERE Country=? " +
			    "LIMIT 100;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoans);
			selectStmt.setString(1, country);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultLoanID = results.getInt("LoanID");
				double fundedAmount = results.getDouble("FundedAmount");
				double loanAmount = results.getDouble("LoanAmount");
				String resultCountryCode = results.getString("CountryCode");
				int partnerID = results.getInt("PartnerID");
				String description = results.getString("Description");
				String region = results.getString("Region");
				Date postedDate =  new Date(results.getTimestamp("PostedDate").getTime());
				String loanActivities = results.getString("LoanActivities");
				String loanThemeID = results.getString("LoanThemeID");
				
				Loans loan = new Loans(resultLoanID, fundedAmount, loanAmount,
						resultCountryCode, partnerID, description, region, postedDate,
						loanActivities, loanThemeID);
				loans.add(loan);
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
		return loans;
	}
	
	/**
	 * Get the first 1000 the Loans within the given sector
	 * @param sector
	 * @return - a list of the first 1000 loans within the given sector
	 * @throws SQLException
	 */
	public List<Loans> getLoansForSector(String sector) throws SQLException {
		List<Loans> loans = new ArrayList<>();
		String selectLoans =
			"SELECT LoanID,FundedAmount,LoanAmount,CountryCode,PartnerID," +
				"Description,Region,PostedDate,Loans.LoanActivities,LoanThemeID,LoanSectors " +
				"FROM Loans INNER JOIN LoanActivities " +
				"ON Loans.LoanActivities = LoanActivities.LoanActivities " +
				"WHERE LoanSectors=? " +
			    "LIMIT 100;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoans);
			selectStmt.setString(1, sector);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultLoanID = results.getInt("LoanID");
				double fundedAmount = results.getDouble("FundedAmount");
				double loanAmount = results.getDouble("LoanAmount");
				String resultCountryCode = results.getString("CountryCode");
				int partnerID = results.getInt("PartnerID");
				String description = results.getString("Description");
				String region = results.getString("Region");
				Date postedDate =  new Date(results.getTimestamp("PostedDate").getTime());
				String loanActivities = results.getString("LoanActivities");
				String loanThemeID = results.getString("LoanThemeID");
				
				Loans loan = new Loans(resultLoanID, fundedAmount, loanAmount,
						resultCountryCode, partnerID, description, region, postedDate,
						loanActivities, loanThemeID);
				loans.add(loan);
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
		return loans;
	}
	
	/**
	 * Get the first 1000 the Loans within the given sector and from the given country
	 * @param country
	 * @param sector
	 * @return - a list of the first 1000 loans within the given sector and from the given country
	 * @throws SQLException
	 */
	public List<Loans> getLoansForCountryAndSector(String country, String sector) throws SQLException {
		List<Loans> loans = new ArrayList<>();
		String selectLoans =
			"SELECT LoanID,FundedAmount,LoanAmount,CountryCode,PartnerID, " +
			 "Description,Region,PostedDate,LoansForCountry.LoanActivities,LoanThemeID,LoanSectors " +
		     "FROM( " +
		     	"SELECT LoanID,FundedAmount,LoanAmount,Loans.CountryCode,PartnerID, " +
		     	"Description,Region,PostedDate,LoanActivities,LoanThemeID,Country " +
		     	"FROM Loans INNER JOIN Countries " +
				"ON Loans.CountryCode = Countries.CountryCode " +
			    "WHERE Country=? " +
			    "LIMIT 100) AS LoansForCountry " +
			 "INNER JOIN LoanActivities " +
			 	"ON LoanActivities.LoanActivities = LoansForCountry.LoanActivities " +
		     "WHERE LoanSectors=? " +
		     "LIMIT 100;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoans);
			selectStmt.setString(1, country);
			selectStmt.setString(2, sector);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultLoanID = results.getInt("LoanID");
				double fundedAmount = results.getDouble("FundedAmount");
				double loanAmount = results.getDouble("LoanAmount");
				String resultCountryCode = results.getString("CountryCode");
				int partnerID = results.getInt("PartnerID");
				String description = results.getString("Description");
				String region = results.getString("Region");
				Date postedDate =  new Date(results.getTimestamp("PostedDate").getTime());
				String loanActivities = results.getString("LoanActivities");
				String loanThemeID = results.getString("LoanThemeID");
				
				Loans loan = new Loans(resultLoanID, fundedAmount, loanAmount,
						resultCountryCode, partnerID, description, region, postedDate,
						loanActivities, loanThemeID);
				loans.add(loan);
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
		return loans;
	}
	
	/**
	 * Get the all the Loans corresponding to the given partner
	 * @param countryCode
	 * @return - a list of all loans corresponding to the given partner
	 * @throws SQLException
	 */
	public List<Loans> getLoansForPartner(Partners partner) throws SQLException {
		List<Loans> loans = new ArrayList<>();
		String selectLoans =
			"SELECT LoanID,FundedAmount,LoanAmount,CountryCode,PartnerID," +
				"Description,Region,PostedDate,LoanActivities,LoanThemeID " +
				"FROM Loans " +
				"WHERE PartnerID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoans);
			selectStmt.setInt(1, partner.getPartnerId());
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultLoanID = results.getInt("LoanID");
				double fundedAmount = results.getDouble("FundedAmount");
				double loanAmount = results.getDouble("LoanAmount");
				String countryCode = results.getString("CountryCode");
				int partnerID = results.getInt("PartnerID");
				String description = results.getString("Description");
				String region = results.getString("Region");
				Date postedDate =  new Date(results.getTimestamp("PostedDate").getTime());
				String loanActivities = results.getString("LoanActivities");
				String loanThemeID = results.getString("LoanThemeID");
				
				Loans loan = new Loans(resultLoanID, fundedAmount, loanAmount,
						countryCode, partnerID, description, region, postedDate,
						loanActivities, loanThemeID);
				loans.add(loan);
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
		return loans;
	}
	
	/**
	 * Get the all the Loans corresponding to the given partner
	 * @param countryCode
	 * @return - a list of all loans corresponding to the given partner
	 * @throws SQLException
	 */
	public List<Loans> getLoansForActivity(String activity) throws SQLException {
		List<Loans> loans = new ArrayList<>();
		String selectLoans =
			"SELECT LoanID,FundedAmount,LoanAmount,CountryCode,PartnerID," +
				"Description,Region,PostedDate,LoanActivities,LoanThemeID " +
				"FROM Loans " +
				"WHERE LoanActivities=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoans);
			selectStmt.setString(1, activity);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultLoanID = results.getInt("LoanID");
				double fundedAmount = results.getDouble("FundedAmount");
				double loanAmount = results.getDouble("LoanAmount");
				String countryCode = results.getString("CountryCode");
				int partnerID = results.getInt("PartnerID");
				String description = results.getString("Description");
				String region = results.getString("Region");
				Date postedDate =  new Date(results.getTimestamp("PostedDate").getTime());
				String loanActivities = results.getString("LoanActivities");
				String loanThemeID = results.getString("LoanThemeID");
				
				Loans loan = new Loans(resultLoanID, fundedAmount, loanAmount,
						countryCode, partnerID, description, region, postedDate,
						loanActivities, loanThemeID);
				loans.add(loan);
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
		return loans;
	}

}