package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import insights.model.PartnerCounts;
import insights.model.Partners;

public class PartnersDao {
	protected ConnectionManager connectionManager;
	private static PartnersDao instance = null;
	
	protected PartnersDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static PartnersDao getInstance() {
		if(instance == null) {
			instance = new PartnersDao();
		}
		return instance;
	}
	
	 public static class KeyValue {
	        String key;
	        String value;

	        public KeyValue(String key, String value) {
	            super();
	            this.key = key;
	            this.value = value;
	        }

	        public String getKey() {
	            return key;
	        }

	        public void setKey(String key) {
	            this.key = key;
	        }

	        public String getValue() {
	            return value;
	        }

	        public void setValue(String value) {
	            this.value = value;
	        }

	        @Override
	        public String toString() {
	            return "KeyValue [key=" + key + ", value=" + value + "]";
	        }

	    }
		

		public List<KeyValue> getNumOfPartnersPerSector() throws SQLException {
			List<KeyValue> sectorMap = new ArrayList<KeyValue>();
			String selectPartners = "SELECT PartnerSector, COUNT(PartnerName) AS NumOfPartners FROM Partners GROUP BY PartnerSector";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectPartners);
				results = selectStmt.executeQuery();
				while(results.next()) {
					String sector = results.getString("PartnerSector");
					String numOfPartners = String.valueOf(results.getInt("NumOfPartners"));
					sectorMap.add(new KeyValue(sector, numOfPartners));
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
			return sectorMap;
		}
	
	/**
	 * Returns top 10 partners who have funded maximum number of loans for the given country
	 * @param region - the country in which we want to find partners who have historically funded loans in
	 * @return - the top 10 partners who have funded maximum number of loans for the given country
	 * @throws SQLException
	 */
	public List<PartnerCounts> findPartnersFundingMaximumLoansForCountry(String country) throws SQLException {
		List<PartnerCounts> partnerResults = new ArrayList<>();
		String selectCountryCode = 
				"SELECT CountryCode FROM Countries WHERE Country=?;";
		String selectPartners = 
				"SELECT COUNT(*) AS COUNT,Partners.PartnerID " +
				"FROM( " +
					"SELECT LoanID,PartnerID,Countries.Country AS COUNTRY " +
					"FROM Loans INNER JOIN Countries " +
					"ON Loans.CountryCode = Countries.CountryCode " +
					"WHERE Countries.CountryCode=? " +
					"LIMIT 1000000) AS LOAN_COUNTRY " +
				"INNER JOIN Partners " +
						"ON LOAN_COUNTRY.PartnerID = Partners.PartnerID " +
				"GROUP BY Partners.PartnerID " +
				"ORDER BY COUNT DESC " +
				"LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt1 = null;
		PreparedStatement selectStmt2 = null;
		ResultSet results1 = null;
		ResultSet results2 = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt1 = connection.prepareStatement(selectCountryCode);
			selectStmt1.setString(1, country);
			results1 = selectStmt1.executeQuery();
			if (results1.next()) {
				String countryCode = results1.getString("CountryCode");
				if(selectStmt1 != null) {
					selectStmt1.close();
				}
				if(results1 != null) {
					results1.close();
				}
				selectStmt2 = connection.prepareStatement(selectPartners);
				selectStmt2.setString(1, countryCode);
				results2 = selectStmt2.executeQuery();
				while (results2.next()) {
					int partnerID = results2.getInt("Partners.PartnerId");
					int loanCount = results2.getInt("COUNT");
					Partners partner = getPartnerByPartnerId(partnerID);
					PartnerCounts result = new PartnerCounts(partner, loanCount);
					partnerResults.add(result);
				}
				return partnerResults;
			} else {
				return partnerResults;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt2 != null) {
				selectStmt2.close();
			}
			if(results2 != null) {
				results2.close();
			}
		}
	}
	
	/**
	 * Returns top 10 partners who have funded maximum number of loans for the given sector
	 * @param sector - the sector in which we want to find partners who have historically funded loans in
	 * @return - the top 10 partners who have funded maximum number of loans for the given sector
	 * @throws SQLException
	 */
	public List<PartnerCounts> findPartnersFundingMaximumLoansForSector(String sector) throws SQLException {
		List<PartnerCounts> partnerResults = new ArrayList<>();
		String selectPartners = 
				"SELECT COUNT(*) AS COUNT,Partners.PartnerID " +
				"FROM " +
					"(SELECT LoanID,PartnerID,LoanSectors " +
					"FROM Loans INNER JOIN LoanActivities " +
					"ON Loans.LoanActivities = LoanActivities.LoanActivities " +
					"WHERE LoanSectors=? " +
					"LIMIT 1000000) AS LOANS_SECTOR " +
				"INNER JOIN Partners " +
						"ON Partners.PartnerID = LOANS_SECTOR.PartnerID " +
				"GROUP BY Partners.PartnerID " +
				"ORDER BY COUNT DESC " +
				"LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPartners);
			selectStmt.setString(1, sector);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int partnerID = results.getInt("Partners.PartnerId");
				int loanCount = results.getInt("COUNT");
				Partners partner = getPartnerByPartnerId(partnerID);
				PartnerCounts result = new PartnerCounts(partner, loanCount);
				partnerResults.add(result);
			}
			return partnerResults;
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
	}

	/**
	 * Save the Partners instance by storing it in MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Partners create(Partners partner) throws SQLException {
		String insertPartner = "INSERT INTO Partners(PartnerID,PartnerName,PartnerSector) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPartner);
			insertStmt.setInt(1, partner.getPartnerId());
			insertStmt.setString(2, partner.getPartnerName());
			insertStmt.setString(3, partner.getSector().name().replaceAll("_", " "));
			insertStmt.executeUpdate();
			return partner;
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
	 * Get the Partners record by fetching it from MySQL instance.
	 * This runs a SELECT statement and returns a single Partners instance.
	 */
	public Partners getPartnerByPartnerId(int partnerId) throws SQLException {
		String selectPartner = "SELECT PartnerID,PartnerName,PartnerSector FROM Partners WHERE PartnerID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPartner);
			selectStmt.setInt(1, partnerId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultPartnerId = results.getInt("PartnerID");
				String partnerName = results.getString("PartnerName");
				Partners.PartnerSector partnerSector = Partners.PartnerSector.valueOf(
						results.getString("PartnerSector").replaceAll(" ", "_"));
				Partners partner = new Partners(resultPartnerId, partnerName, partnerSector);
				return partner;
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
	 * Get the Partners record by fetching it from MySQL instance.
	 * This runs a SELECT statement and returns a single Partners instance.
	 */
	public List<Partners> getAllPartners() throws SQLException {
		List<Partners> partners = new ArrayList<Partners>();
		String selectPartners = "SELECT * FROM Partners;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPartners);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String partnerId = results.getString("PartnerID");
				String partnerName = results.getString("PartnerName");
				String sector = results.getString("PartnerSector");
				Partners partner = new Partners(Integer.valueOf(partnerId), partnerName, Partners.PartnerSector.valueOf(sector.replaceAll(" ", "_")));
				partners.add(partner);
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
		return partners;
	}
	
	/**
	 * Update the PartnerName of the Partners instance.
	 * This runs a UPDATE statement.
	 */
	public Partners updatePartnerName(Partners partner, String newPartnerName) throws SQLException {
		String updatePartner = "UPDATE Partners SET PartnerName=? WHERE PartnerID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePartner);
			updateStmt.setString(1, newPartnerName);
			updateStmt.setInt(2, partner.getPartnerId());
			updateStmt.executeUpdate();
			
			// Update the partner param before returning to the caller.
			partner.setPartnerName(newPartnerName);
			return partner;
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
	 * Delete the Partners instance.
	 * This runs a DELETE statement.
	 */
	public Partners delete(Partners partner) throws SQLException {
		String deletePartner = "DELETE FROM Partners WHERE PartnerID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePartner);
			deleteStmt.setInt(1, partner.getPartnerId());
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
