package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import insights.model.Countries;

public class CountriesDao {
	protected ConnectionManager connectionManager;
	private static CountriesDao instance = null;
	
	protected CountriesDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static CountriesDao getInstance() {
		if(instance == null) {
			instance = new CountriesDao();
		}
		return instance;
	}

	/**
	 * Save the Countries instance by storing it in MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Countries create(Countries country) throws SQLException {
		String insertCountry = "INSERT INTO Countries(CountryCode,Country) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCountry);
			insertStmt.setString(1, country.getCountryCode());
			insertStmt.setString(2, country.getCountry());
			insertStmt.executeUpdate();
			return country;
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
	 * Get the Countries record by fetching it from MySQL instance.
	 * This runs a SELECT statement and returns a single Countries instance.
	 */
	public Countries getCountryByCountryCode(String countryCode) throws SQLException {
		String selectCountry = "SELECT CountryCode,Country FROM Countries WHERE CountryCode=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCountry);
			selectStmt.setString(1, countryCode);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultCountryCode = results.getString("CountryCode");
				String resultCountry = results.getString("Country");
				Countries country = new Countries(resultCountryCode, resultCountry);
				return country;
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
	 * Get the Countries record by fetching it from MySQL instance.
	 * This runs a SELECT statement and returns a single Countries instance.
	 */
	public List<Countries> getAllCountries() throws SQLException {
		List<Countries> countries = new ArrayList<Countries>();
		String selectCountries = "SELECT * FROM Countries;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCountries);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String countryCode = results.getString("CountryCode");
				String country = results.getString("Country");
				Countries currentCountry = new Countries(countryCode, country);
				countries.add(currentCountry);
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
		return countries;
	}
	
	/**
	 * Update the Country of the Countries instance.
	 * This runs a UPDATE statement.
	 */
	public Countries updateCountry(Countries country, String newCountryName) throws SQLException {
		String updateCountry = "UPDATE Countries SET Country=? WHERE CountryCode=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCountry);
			updateStmt.setString(1, newCountryName);
			updateStmt.setString(2, country.getCountryCode());
			updateStmt.executeUpdate();
			
			// Update the country param before returning to the caller.
			country.setCountry(newCountryName);
			return country;
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
	 * Delete the Countries instance.
	 * This runs a DELETE statement.
	 */
	public Countries delete(Countries country) throws SQLException {
		String deleteCountry = "DELETE FROM Countries WHERE CountryCode=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCountry);
			deleteStmt.setString(1, country.getCountryCode());
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
