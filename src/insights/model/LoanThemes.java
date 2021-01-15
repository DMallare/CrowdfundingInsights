package insights.model;

public class LoanThemes {
	protected String loanThemeId;
	protected String loanThemeDescription;
	
	
	public LoanThemes(String loanThemeId, String loanThemeDescription) {
		this.loanThemeId = loanThemeId;
		this.loanThemeDescription = loanThemeDescription;
	}


	public LoanThemes(String loanThemeId) {
		super();
		this.loanThemeId = loanThemeId;
	}

	

	public LoanThemes() {
	}


	public String getLoanThemeId() {
		return loanThemeId;
	}


	public void setLoanThemeId(String loanThemeId) {
		this.loanThemeId = loanThemeId;
	}


	public String getLoanThemeDescription() {
		return loanThemeDescription;
	}


	public void setLoanThemeDescription(String loanThemeDescription) {
		this.loanThemeDescription = loanThemeDescription;
	}
	
}
