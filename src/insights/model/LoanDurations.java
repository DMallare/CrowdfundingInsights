package insights.model;

public class LoanDurations {
	protected int loanDurationId;
	protected Loans loan;
	protected double termInMonths;
	
	public LoanDurations(int loanDurationId, Loans loan, double termInMonths) {
		this.loanDurationId = loanDurationId;
		this.loan = loan;
		this.termInMonths = termInMonths;
	}
	
	public LoanDurations(int loanDurationId) {
		this.loanDurationId = loanDurationId;
	}
	
	public LoanDurations(Loans loan, double termInMonths) {
		this.loan = loan;
		this.termInMonths = termInMonths;
	}

	public int getLoanDurationId() {
		return loanDurationId;
	}

	public void setLoanDurationId(int loanDurationId) {
		this.loanDurationId = loanDurationId;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}

	public double getTermInMonths() {
		return termInMonths;
	}

	public void setTermInMonths(double termInMonths) {
		this.termInMonths = termInMonths;
	}

	

}