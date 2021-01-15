package insights.model;

public class PartnerCounts {
	protected Partners partner;
	protected int loansFundedCount;
	
	public PartnerCounts(Partners partner, int loansFundedCount) {
		this.partner = partner;
		this.loansFundedCount = loansFundedCount;
	}

	public Partners getPartner() {
		return partner;
	}

	public void setPartner(Partners partner) {
		this.partner = partner;
	}

	public int getLoansFundedCount() {
		return loansFundedCount;
	}

	public void setLoansFundedCount(int loansFundedCount) {
		this.loansFundedCount = loansFundedCount;
	}

}
