package insights.model;

import java.util.Date;

public class Loans {
	protected int loanID;
	protected double fundedAmount;
	protected double loanAmount;
	protected String countryCode;
	protected int partnerId;
	protected String description;
	protected String region;
	protected Date postedDate;
	protected String loanActivities;
	protected String loanThemeID;
	
	
	public Loans(int loanID, double fundedAmount, double loanAmount, String countryCode, int partnerId,
			String description, String region, Date postedDate, String loanActivities, String loanThemeID) {
		super();
		this.loanID = loanID;
		this.fundedAmount = fundedAmount;
		this.loanAmount = loanAmount;
		this.countryCode = countryCode;
		this.partnerId = partnerId;
		this.description = description;
		this.region = region;
		this.postedDate = postedDate;
		this.loanActivities = loanActivities;
		this.loanThemeID = loanThemeID;
	}
	
	
	/** This constructor can be used for reading records from MySQL, 
	* where we only have the loanId, such as a foreign key reference to loanID.
	*/
	public Loans(int loanID) {
		this.loanID = loanID;
	}
	
	
	/**
	 * Constructor that takes in no parameters
	 */
	public Loans() {
		
	}


	public int getLoanID() {
		return loanID;
	}


	public void setLoanID(int loanID) {
		this.loanID = loanID;
	}


	public double getFundedAmount() {
		return fundedAmount;
	}


	public void setFundedAmount(double fundedAmount) {
		this.fundedAmount = fundedAmount;
	}


	public double getLoanAmount() {
		return loanAmount;
	}


	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	public int getPartnerId() {
		return partnerId;
	}


	public void setPartner(int partnerId) {
		this.partnerId = partnerId;
	}
	
	
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public Date getPostedDate() {
		return postedDate;
	}


	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}


	public String getLoanActivities() {
		return loanActivities;
	}


	public void setLoanActivities(String loanActivities) {
		this.loanActivities = loanActivities;
	}


	public String getLoanThemeID() {
		return loanThemeID;
	}


	public void setLoanThemeID(String loanThemeID) {
		this.loanThemeID = loanThemeID;
	}

	
}