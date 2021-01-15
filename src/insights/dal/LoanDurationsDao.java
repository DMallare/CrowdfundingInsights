package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import insights.model.LoanDurations;
import insights.model.Loans;


public class LoanDurationsDao {
	protected ConnectionManager connectionManager;
	
	private static LoanDurationsDao instance = null;
	protected LoanDurationsDao() {
		connectionManager = new ConnectionManager();
	}
	public static LoanDurationsDao getInstance() {
		if (instance == null) {
			instance = new LoanDurationsDao();
		}
		return instance;
	}
	
	public LoanDurations create(LoanDurations loanDuration) throws SQLException {
		String insertLoanDuration = "INSERT INTO LoanDurations(LoanID, TermInMonths) VALUES(?,?)";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLoanDuration, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, loanDuration.getLoan().getLoanID());
			insertStmt.setDouble(2, loanDuration.getTermInMonths());
			
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int loanDurationId = -1;
			if (resultKey.next()) {
				loanDurationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			loanDuration.setLoanDurationId(loanDurationId);
			return loanDuration;
		}  catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public LoanDurations getLoanDurationById(int loanDurationId) throws SQLException {
		String selectLoanDuration = "SELECT LoanDurationID,LoanID,TermInMonths FROM LoanDurations WHERE LoanDurationID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanDuration);
			selectStmt.setInt(1, loanDurationId);
			results = selectStmt.executeQuery();
			LoansDao loansDao = LoansDao.getInstance();
			if (results.next()) {
				int resultLoanDurationId = results.getInt("LoanDurationID");
				int loanId = results.getInt("LoanID");
				Loans loan = loansDao.getLoanById(loanId);
				double termInMonths = results.getDouble("TermInMonths");
				LoanDurations loanDuration = new LoanDurations(resultLoanDurationId, loan, termInMonths);
				return loanDuration;
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
	
	public List<LoanDurations> getLoanDurationsByLoanId(int loanId) throws SQLException {
		List<LoanDurations> loanDurations = new ArrayList<>();
		String selectLoanDurations = "SELECT LoanDurationID,LoanID,TermInMonths FROM LoanDurations WHERE LoanID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		LoansDao loansDao = LoansDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanDurations);
			selectStmt.setInt(1, loanId);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int loanDurationId = results.getInt("LoanDurationID");
				int resultLoanId = results.getInt("LoanID");
				Loans loan = loansDao.getLoanById(resultLoanId);
				double termInMonths = results.getDouble("TermInMonths");
				LoanDurations loanDuration = new LoanDurations(loanDurationId, loan, termInMonths);
				loanDurations.add(loanDuration);
				
			}
		}  catch (SQLException e) {
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
		return loanDurations;
	}
	
	public List<LoanDurations> getLoanDurationsByTermInMonths(double termInMonths) throws SQLException {
		List<LoanDurations> loanDurations = new ArrayList<>();
		String selectLoanDurations = "SELECT LoanDurationID,LoanID,TermInMonths FROM LoanDurations WHERE TermInMonths=? " + 
				"LIMIT 100;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		LoansDao loansDao = LoansDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanDurations);
			selectStmt.setDouble(1, termInMonths);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int loanDurationId = results.getInt("LoanDurationID");
				int loanId = results.getInt("LoanID");
				Loans loan = loansDao.getLoanById(loanId);
				double resulttermInMonths = results.getDouble("TermInMonths");
				LoanDurations loanDuration = new LoanDurations(loanDurationId, loan, resulttermInMonths);
				loanDurations.add(loanDuration);
				
			}
		}  catch (SQLException e) {
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
		return loanDurations;
	}
	
	public LoanDurations updateTermInMonths(LoanDurations loanDuration, double newTermInMonths) throws SQLException {
		String updateLoanDuration = "UPDATE LoanDurations SET TermInMonths=? WHERE LoanDurationID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateLoanDuration);
			updateStmt.setDouble(1, newTermInMonths);
			updateStmt.setInt(2, loanDuration.getLoanDurationId());
			updateStmt.executeUpdate();
			
			loanDuration.setTermInMonths(newTermInMonths);
			return loanDuration;
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

	
	public LoanDurations delete(LoanDurations loanDuration) throws SQLException {
		String deleteLoanDuration = "DELETE FROM LoanDurations WHERE LoanDurationID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteLoanDuration);
			deleteStmt.setInt(1, loanDuration.getLoanDurationId());
			deleteStmt.executeUpdate();
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}