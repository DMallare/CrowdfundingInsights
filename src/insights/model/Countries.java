package insights.model;

import java.util.Objects;

public class Countries {
	protected String countryCode;
	protected String country;

	public Countries(String countryCode, String country) {
		this.countryCode = countryCode;
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		return Objects.hash(country, countryCode);
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
		Countries other = (Countries) obj;
		return Objects.equals(country, other.country) && Objects.equals(countryCode, other.countryCode);
	}
}
