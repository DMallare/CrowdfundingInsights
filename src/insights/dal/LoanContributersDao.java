package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import insights.model.LoanContributers;
import insights.model.Loans;


public class LoanContributersDao {
	protected ConnectionManager connectionManager;
	
	private static LoanContributersDao instance = null;
	protected LoanContributersDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static LoanContributersDao getInstance() {
		if (instance == null) {
			instance = new LoanContributersDao();
		}
		return instance;
	}
	
	public LoanContributers create(LoanContributers loanContributers) throws SQLException {
		String insertLoanContributers = "INSERT INTO LoanContributers(LoanID, LenderCount) VALUES(?,?)";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLoanContributers, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, loanContributers.getLoan().getLoanID());
			insertStmt.setInt(2, loanContributers.getLenderCount());
			
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int loanContributersId = -1;
			if (resultKey.next()) {
				loanContributersId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			loanContributers.setLoanContributersId(loanContributersId);
			return loanContributers;
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
	
	public LoanContributers getLoanContributersById(int loanContributersId) throws SQLException {
		String selectLoanContributers = "SELECT LoanContributersID,LoanID,LenderCount FROM LoanContributers WHERE LoanContributersID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanContributers);
			selectStmt.setInt(1, loanContributersId);
			results = selectStmt.executeQuery();
			LoansDao loansDao = LoansDao.getInstance();
			if (results.next()) {
				int resultLoanContributersId = results.getInt("LoanContributersID");
				int loanId = results.getInt("LoanID");
				Loans loan = loansDao.getLoanById(loanId);
				int lenderCount = results.getInt("LenderCount");
				LoanContributers loanContributers = new LoanContributers(resultLoanContributersId, loan, lenderCount);
				return loanContributers;
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
	
	public List<LoanContributers> getLoanContributersByLoanId(int loanId) throws SQLException {
		List<LoanContributers> loanContributers = new ArrayList<>();
		String selectLoanContributers = "SELECT LoanContributersID,LoanID,LenderCount FROM LoanDurations WHERE LoanID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		LoansDao loansDao = LoansDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLoanContributers);
			selectStmt.setInt(1, loanId);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int loanContributersId = results.getInt("LoanContributersID");
				int resultLoanId = results.getInt("LoanID");
				Loans loan = loansDao.getLoanById(resultLoanId);
				int lenderCount = results.getInt("LenderCount");
				LoanContributers loanContributer = new LoanContributers(loanContributersId, loan, lenderCount);
				loanContributers.add(loanContributer);
				
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
		return loanContributers;
		
	}
	
	public LoanContributers updateLenderCount(LoanContributers loanContributers, int newLenderCount) throws SQLException {
		String updateLoanContributers = "UPDATE LoanContributers SET LenderCount=? WHERE LoanContributersID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateLoanContributers);
			updateStmt.setInt(1, newLenderCount);
			updateStmt.setInt(2, loanContributers.getLoanContributersId());
			updateStmt.executeUpdate();
			
			loanContributers.setLenderCount(newLenderCount);
			return loanContributers;
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
	
	public LoanContributers delete(LoanContributers loanContributers) throws SQLException {
		String deleteLoanContributers = "DELETE FROM LoanContributers WHERE LoanContributersID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteLoanContributers);
			deleteStmt.setInt(1, loanContributers.getLoanContributersId());
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