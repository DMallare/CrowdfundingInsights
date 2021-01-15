package insights.dal;

import insights.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LoanActivitiesDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static LoanActivitiesDao instance = null;
	protected LoanActivitiesDao() {
		connectionManager = new ConnectionManager();
	}
	public static LoanActivitiesDao getInstance() {
		if(instance == null) {
			instance = new LoanActivitiesDao();
		}
		return instance;
	}

	
	public LoanActivities create(LoanActivities loanActivity) throws SQLException {
		String insertLoanActivity = "INSERT INTO LoanActivities(LoanActivities,LoanSectors) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLoanActivity);

			// replace _ to blank
			insertStmt.setString(1, loanActivity.getLoanActivities());
			insertStmt.setString(2, loanActivity.getLoanSectors().toString().replaceAll("_", " "));

			insertStmt.executeUpdate();
			
			return loanActivity;
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
	
	public LoanActivities getLoanActivitiesByActivityName(String activityName) throws SQLException {
		String selectActivity = "SELECT LoanActivities.LoanActivities AS LoanActivities, LoanSectors FROM LoanActivities WHERE LoanActivities=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectActivity);
			selectStmt.setString(1, activityName);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String resultLoanActivities = results.getString("LoanActivities");
				LoanActivities.LoanSectorType loanSector = LoanActivities.LoanSectorType.valueOf(
						results.getString("LoanSectors").replaceAll(" ", "_"));
				
				LoanActivities loanActivities = new LoanActivities(resultLoanActivities, loanSector);
				return loanActivities;
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
	
	public LoanActivities updateLoanSector(LoanActivities loanActivity, String loanSector) throws SQLException {
		String updateLoanSector = "UPDATE LoanActivities SET LoanSectors=? WHERE LoanActivities=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateLoanSector);
			updateStmt.setString(1, loanSector.replaceAll("_", " "));
			updateStmt.setString(2, loanActivity.getLoanActivities());
			updateStmt.executeUpdate();
			
			// change string to Enum
			loanActivity.setLoanSectors(LoanActivities.LoanSectorType.valueOf(loanSector.replaceAll(" ", "_")));
			return loanActivity;
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
	
	
	public LoanActivities delete(LoanActivities loanActivity) throws SQLException {
		String deleteLoanActivity = "DELETE FROM LoanActivities WHERE LoanActivities=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteLoanActivity);
			deleteStmt.setString(1, loanActivity.getLoanActivities());
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
	
	
	public List<LoanActivities> getLoanActivitiesFromLoanSector(String loanSector) throws SQLException {
		List<LoanActivities> loanActivities = new ArrayList<LoanActivities>();
		String selectLoanActivities =
			"SELECT LoanActivities.LoanActivities AS LoanActivities, LoanSectors FROM LoanActivities WHERE LoanSectors=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanActivities);
			selectStmt.setString(1, loanSector.replaceAll("_", " "));
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String resultActivity = results.getString("LoanActivities");
				LoanActivities.LoanSectorType loanSectorType = LoanActivities.LoanSectorType.valueOf(results.getString("LoanSectors").replaceAll(" ", "_"));
				
				LoanActivities loanActivity = new LoanActivities(resultActivity, loanSectorType);
				loanActivities.add(loanActivity);
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
		return loanActivities;
	}
	
}
