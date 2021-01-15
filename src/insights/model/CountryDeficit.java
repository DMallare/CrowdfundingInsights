package insights.model;

public class CountryDeficit {
	protected String country;
	protected double percentFunded;
	
	public CountryDeficit(String country, double fundedToLoanAmountRatio) {
		this.country = country;
		this.percentFunded = fundedToLoanAmountRatio;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getPercentFunded() {
		return percentFunded;
	}

	public void setPercentFunded(double percentFunded) {
		this.percentFunded = percentFunded;
	}

}
