package insights.model;

public class LoanContributers {
	protected int loanContributersId;
	protected Loans loan;
	protected int lenderCount;
	
	
	public LoanContributers(int loanContributersId, Loans loan, int lenderCount) {
		this.loanContributersId = loanContributersId;
		this.loan = loan;
		this.lenderCount = lenderCount;
	}


	public LoanContributers(int loanContributersId) {
		this.loanContributersId = loanContributersId;
	}
	
	public LoanContributers(Loans loan, int lenderCount) {
		this.loan = loan;
		this.lenderCount = lenderCount;
	}


	public int getLoanContributersId() {
		return loanContributersId;
	}


	public void setLoanContributersId(int loanContributersId) {
		this.loanContributersId = loanContributersId;
	}


	public Loans getLoan() {
		return loan;
	}


	public void setLoan(Loans loan) {
		this.loan = loan;
	}


	public int getLenderCount() {
		return lenderCount;
	}


	public void setLenderCount(int lenderCount) {
		this.lenderCount = lenderCount;
	}
	
	

}