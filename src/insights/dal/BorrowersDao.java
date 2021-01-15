package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import insights.model.Borrowers;
import insights.model.Loans;

public class BorrowersDao {
	protected ConnectionManager connectionManager;
	private static BorrowersDao instance = null;
	
	protected BorrowersDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static BorrowersDao getInstance() {
		if(instance == null) {
			instance = new BorrowersDao();
		}
		return instance;
	}

	/**
	 * Save the Borrowers instance by storing it in MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Borrowers create(Borrowers borrower) throws SQLException {
		String insertBorrower = "INSERT INTO Borrowers(BorrowerID,NumberOfIndividuals,FemaleCount,MaleCount,RepaymentInterval,LoanID) VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertBorrower);
			insertStmt.setInt(1, borrower.getBorrowerId());
			insertStmt.setInt(2, borrower.getNumberOfIndividuals());
			insertStmt.setInt(3, borrower.getFemaleCount());
			insertStmt.setInt(4, borrower.getMaleCount());
			insertStmt.setString(5, borrower.getRepaymentInterval().name());
			insertStmt.setInt(6, borrower.getLoan().getLoanID());
			insertStmt.executeUpdate();
			return borrower;
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
	 * Get the Borrowers record by fetching it from MySQL instance.
	 * This runs a SELECT statement and returns a single Borrowers instance.
	 */
	public Borrowers getBorrowerByLoanId(int loanId) throws SQLException {
		String selectBorrower = "SELECT BorrowerID,NumberOfIndividuals,FemaleCount,MaleCount,RepaymentInterval,LoanID FROM Borrowers WHERE LoanID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBorrower);
			selectStmt.setInt(1, loanId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int borrowerId = results.getInt("BorrowerID");
				int numberOfIndividuals = results.getInt("NumberOfIndividuals");
				int femaleCount = results.getInt("FemaleCount");
				int maleCount = results.getInt("MaleCount");
				Borrowers.RepaymentInterval repaymentInterval = Borrowers.RepaymentInterval.valueOf(results.getString("RepaymentInterval"));
				// int resultLoanId = results.getInt("LoanID");
				Loans loan = new Loans(loanId);
				Borrowers borrower = new Borrowers(borrowerId, numberOfIndividuals, femaleCount, maleCount, repaymentInterval, loan);
				return borrower;
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
	 * Update the RepaymentInterval of the Borrowers instance.
	 * This runs a UPDATE statement.
	 */
	public Borrowers updateRepaymentInterval(Borrowers borrower, String newRepaymentInterval) throws SQLException {
		String updateBorrower = "UPDATE Borrowers SET RepaymentInterval=? WHERE BorrowerID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBorrower);
			updateStmt.setString(1, newRepaymentInterval);
			updateStmt.setInt(2, borrower.getBorrowerId());
			updateStmt.executeUpdate();
			
			// Update the borrower param before returning to the caller.
			borrower.setRepaymentInterval(Borrowers.RepaymentInterval.valueOf(newRepaymentInterval));
			return borrower;
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
	 * Delete the Borrowers instance.
	 * This runs a DELETE statement.
	 */
	public Borrowers delete(Borrowers borrower) throws SQLException {
		String deleteBorrower = "DELETE FROM Borrowers WHERE BorrowerID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteBorrower);
			deleteStmt.setInt(1, borrower.getBorrowerId());
			deleteStmt.executeUpdate();
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
}
