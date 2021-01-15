package insights.model;

public class LoanThemesGeoData {
	protected String loanThemeDescription;
	protected String countryName;
	protected String region;
	protected double loanThemeData;
	
	
	public LoanThemesGeoData(String loanThemeDescription, String countryName, String region, double loanThemeData) {
		this.loanThemeDescription = loanThemeDescription;
		this.countryName = countryName;
		this.region = region;
		this.loanThemeData = loanThemeData;
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


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public double getLoanThemeData() {
		return loanThemeData;
	}


	public void setLoanThemeData(double loanThemeData) {
		this.loanThemeData = loanThemeData;
	}
	
}
