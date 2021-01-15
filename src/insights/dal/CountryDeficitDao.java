package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import insights.model.*;

public class CountryDeficitDao {
	protected ConnectionManager connectionManager;
	private static CountryDeficitDao instance = null;
	
	protected CountryDeficitDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static CountryDeficitDao getInstance() {
		if(instance == null) {
			instance = new CountryDeficitDao();
		}
		return instance;
	}
	
	/**
	 * Get the 10 countries with the smallest funded amount to requested loan amount ratio
	 * @return - the 10 countries with the smallest funded amount to requested loan amount ratio
	 * @throws SQLException
	 */
	public List<CountryDeficit> getCountriesWithSmallestLoanDeficitRatio() throws SQLException {
		List<CountryDeficit> percents = new ArrayList<>();
		String selectResults =
			"SELECT countries.Country AS COUNTRY, ((SUM(loans.FundedAmount) / SUM(loans.LoanAmount)) * 100 ) AS PERCENT_FUNDED "
			+ "FROM loans INNER JOIN countries "
			+ "ON loans.CountryCode = countries.CountryCode "
			+ "GROUP BY COUNTRY "
			+ "ORDER BY PERCENT_FUNDED ASC "
			+ "LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectResults);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultCountry = results.getString("Countries.Country");
				double percent = results.getDouble("PERCENT_FUNDED");
				CountryDeficit p = new CountryDeficit(resultCountry, percent);
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
