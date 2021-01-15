package insights.model;

import java.util.Date;

public class LoanThemesGeo {
	protected String loanThemeDescription;
	protected int loanID;
	protected double loanAmount;
	protected String countryName;
	protected String region;
	protected String loanActivities;
	protected String description;
	
	
	public LoanThemesGeo(String loanThemeDescription, int loanID, double loanAmount, String countryName, String region,
			String loanActivities, String description) {
		super();
		this.loanThemeDescription = loanThemeDescription;
		this.loanID = loanID;
		this.loanAmount = loanAmount;
		this.countryName = countryName;
		this.region = region;
		this.loanActivities = loanActivities;
		this.description = description;
	}


	public String getLoanThemeDescription() {
		return loanThemeDescription;
	}


	public void setLoanThemeDescription(String loanThemeDescription) {
		this.loanThemeDescription = loanThemeDescription;
	}


	public int getLoanID() {
		return loanID;
	}


	public void setLoanID(int loanID) {
		this.loanID = loanID;
	}


	public double getLoanAmount() {
		return loanAmount;
	}


	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
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


	public String getLoanActivities() {
		return loanActivities;
	}


	public void setLoanActivities(String loanActivities) {
		this.loanActivities = loanActivities;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

}
