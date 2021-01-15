package insights.model;

public class LoanActivities {
	protected String loanActivities;
	protected LoanSectorType loanSectors;
	
	
	public enum LoanSectorType {
		Agriculture,
	    Arts,
	    Clothing,
	    Construction,
	    Education,
	    Entertainment,
	    Food,
	    Health,
	    Housing,
	    Manufacturing,
	    Personal_Use,
	    Retail,
	    Services,
	    Transportation,
	    Wholesale
	}


	public LoanActivities(String loanActivities, LoanSectorType loanSectors) {
		this.loanActivities = loanActivities;
		this.loanSectors = loanSectors;
	}


	public LoanActivities(String loanActivities) {
		this.loanActivities = loanActivities;
	}


	public String getLoanActivities() {
		return loanActivities;
	}


	public void setLoanActivities(String loanActivities) {
		this.loanActivities = loanActivities;
	}


	public LoanSectorType getLoanSectors() {
		return loanSectors;
	}


	public void setLoanSectors(LoanSectorType loanSectors) {
		this.loanSectors = loanSectors;
	}

}
