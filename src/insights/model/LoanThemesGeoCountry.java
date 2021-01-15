package insights.model;

public class LoanThemesGeoCountry {
	protected String loanThemeDescription;
	protected String countryName;
	protected double avgLoanAmount;
	protected double GDP;
	protected double Index;
	
	public LoanThemesGeoCountry(String loanThemeDescription, String countryName, double avgLoanAmount, double gDP,
			double index) {
		super();
		this.loanThemeDescription = loanThemeDescription;
		this.countryName = countryName;
		this.avgLoanAmount = avgLoanAmount;
		GDP = gDP;
		Index = index;
	}

	public String getLoanThemeDescription() {
		return loanThemeDescription;
	}

	public void setLoanThemeDescription(String loanThemeDescription) {
		this.loanThemeDescription = loanThemeDescription;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public double getAvgLoanAmount() {
		return avgLoanAmount;
	}

	public void setAvgLoanAmount(double avgLoanAmount) {
		this.avgLoanAmount = avgLoanAmount;
	}

	public double getGDP() {
		return GDP;
	}

	public void setGDP(double gDP) {
		GDP = gDP;
	}

	public double getIndex() {
		return Index;
	}

	public void setIndex(double index) {
		Index = index;
	}
	
}
