package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import insights.model.*;

public class SectorDeficitDao {
	protected ConnectionManager connectionManager;
	private static SectorDeficitDao instance = null;
	
	protected SectorDeficitDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static SectorDeficitDao getInstance() {
		if(instance == null) {
			instance = new SectorDeficitDao();
		}
		return instance;
	}
	
	/**
	 * Get the 5 sectors with the smallest funded amount to requested loan amount ratio
	 * @return - the 5 sectors with the smallest funded amount to requested loan amount ratio
	 * @throws SQLException
	 */
	public List<SectorDeficit> getSectorsWithSmallestLoanDeficitRatio() throws SQLException {
		List<SectorDeficit> percents = new ArrayList<>();
		String selectResults =
			"SELECT loanactivities.LoanSectors AS SECTOR, ((SUM(loans.FundedAmount) / SUM(loans.LoanAmount)) * 100) AS PERCENT_FUNDED "
			+ "FROM loans LEFT OUTER JOIN loanactivities "
			+ "ON loans.LoanActivities = loanactivities.LoanActivities "
			+ "GROUP BY SECTOR "
			+ "ORDER BY PERCENT_FUNDED ASC "
			+ "LIMIT 5;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectResults);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultSector = results.getString("SECTOR");
				double percent = results.getDouble("PERCENT_FUNDED");
				SectorDeficit p = new SectorDeficit(resultSector, percent);
				percents.add(p);
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
		return percents;
	}

}
