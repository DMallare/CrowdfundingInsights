package insights.model;

import java.util.Objects;

public class Regions {
	protected String region;
	protected String country;
	protected String iso;
	protected String worldRegion;
	protected double mpi;
	protected double latitude;
	protected double longitude;

	public Regions(String region, String country, String iso, String worldRegion, double mpi, double latitude,
			double longitude) {
		super();
		this.region = region;
		this.country = country;
		this.iso = iso;
		this.worldRegion = worldRegion;
		this.mpi = mpi;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getWorldRegion() {
		return worldRegion;
	}

	public void setWorldRegion(String worldRegion) {
		this.worldRegion = worldRegion;
	}

	public double getMpi() {
		return mpi;
	}

	public void setMpi(double mpi) {
		this.mpi = mpi;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public int hashCode() {
		return Objects.hash(country, iso, latitude, longitude, mpi, region, worldRegion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Regions other = (Regions) obj;
		return Objects.equals(country, other.country) && Objects.equals(iso, other.iso)
				&& Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude)
				&& Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude)
				&& Double.doubleToLongBits(mpi) == Double.doubleToLongBits(other.mpi)
				&& Objects.equals(region, other.region) && Objects.equals(worldRegion, other.worldRegion);
	}
}
