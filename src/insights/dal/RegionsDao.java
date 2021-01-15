package insights.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import insights.model.Regions;

public class RegionsDao {
	protected ConnectionManager connectionManager;
	private static RegionsDao instance = null;
	
	protected RegionsDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static RegionsDao getInstance() {
		if(instance == null) {
			instance = new RegionsDao();
		}
		return instance;
	}

	/**
	 * Save the Regions instance by storing it in MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Regions create(Regions region) throws SQLException {
		String insertRegion = "INSERT INTO Regions(Region,Country,ISO,WorldRegion,MPI,Latitude,Longitude) VALUES(?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRegion);
			insertStmt.setString(1, region.getRegion());
			insertStmt.setString(2, region.getCountry());
			insertStmt.setString(3, region.getIso());
			insertStmt.setString(4, region.getWorldRegion());
			insertStmt.setDouble(5, region.getMpi());
			insertStmt.setDouble(6, region.getLatitude());
			insertStmt.setDouble(7, region.getLongitude());
			insertStmt.executeUpdate();
			return region;
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
	 * Get the Regions record by fetching it from MySQL instance.
	 * This runs a SELECT statement and returns a single Regions instance.
	 */
	public Regions getRegionByRegion(String region) throws SQLException {
		String selectRegion = "SELECT Region,Country,ISO,WorldRegion,MPI,Latitude,Longitude FROM Regions WHERE Region=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRegion);
			selectStmt.setString(1, region);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultRegion = results.getString("Region");
				String resultCountry = results.getString("Country");
				String resultIso = results.getString("ISO");
				String resultWorldRegion = results.getString("WorldRegion");
				Double resultMpi = results.getDouble("MPI");
				Double resultLatitude = results.getDouble("Latitude");
				Double resultLongitude = results.getDouble("Longitude");
				Regions currentRegion = new Regions(resultRegion, resultCountry, resultIso, resultWorldRegion, resultMpi, resultLatitude, resultLongitude);
				return currentRegion;
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
	 * Get the Regions record by fetching it from MySQL instance.
	 * This runs a SELECT statement and returns a single Regions instance.
	 */
	public List<Regions> getAllRegions() throws SQLException {
		List<Regions> regions = new ArrayList<Regions>();
		String selectRegions = "SELECT * FROM Regions;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRegions);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String region = results.getString("Region");
				String country = results.getString("Country");
				String iso = results.getString("ISO");
				String worldRegion = results.getString("WorldRegion");
				Double mpi = results.getDouble("MPI");
				Double latitude = results.getDouble("Latitude");
				Double longitude = results.getDouble("Longitude");
				Regions currentRegion = new Regions(region, country, iso, worldRegion, mpi,latitude, longitude);
				regions.add(currentRegion);
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
		return regions;
	}
	
	/**
	 * Update the Region of the Regions instance.
	 * This runs a UPDATE statement.
	 */
	public Regions updateRegionMPI(Regions region, double newMPI) throws SQLException {
		String updateRegion = "UPDATE Regions SET MPI=? WHERE Region=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateRegion);
			updateStmt.setDouble(1, newMPI);
			updateStmt.setString(2, region.getRegion());
			updateStmt.executeUpdate();
			
			// Update the mpi param before returning to the caller.
			region.setMpi(newMPI);
			return region;
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
	 * Delete the Regions instance.
	 * This runs a DELETE statement.
	 */
	public Regions delete(Regions region) throws SQLException {
		String deleteRegion = "DELETE FROM Regions WHERE Region=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRegion);
			deleteStmt.setString(1, region.getRegion());
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
