package insights.model;

import java.util.Objects;

public class Borrowers {
	protected int borrowerId;
	protected int numberOfIndividuals;
	protected int femaleCount;
	protected int maleCount;
	protected RepaymentInterval repaymentInterval;
	protected Loans loan;

	public enum RepaymentInterval {
		Irregular, Bullet, Monthly, Weekly
	}

	public Borrowers(int borrowerId, int numberOfIndividuals, int femaleCount, int maleCount,
			RepaymentInterval repaymentInterval, Loans loan) {
		this.borrowerId = borrowerId;
		this.numberOfIndividuals = numberOfIndividuals;
		this.femaleCount = femaleCount;
		this.maleCount = maleCount;
		this.repaymentInterval = repaymentInterval;
		this.loan = loan;
	}

	public Borrowers(int numberOfIndividuals, int femaleCount, int maleCount, RepaymentInterval repaymentInterval,
			Loans loan) {
		this.numberOfIndividuals = numberOfIndividuals;
		this.femaleCount = femaleCount;
		this.maleCount = maleCount;
		this.repaymentInterval = repaymentInterval;
		this.loan = loan;
	}

	public Borrowers(int borrowerId) {
		this.borrowerId = borrowerId;
	}

	public int getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(int borrowerId) {
		this.borrowerId = borrowerId;
	}

	public int getNumberOfIndividuals() {
		return numberOfIndividuals;
	}

	public void setNumberOfIndividuals(int numberOfIndividuals) {
		this.numberOfIndividuals = numberOfIndividuals;
	}

	public int getFemaleCount() {
		return femaleCount;
	}

	public void setFemaleCount(int femaleCount) {
		this.femaleCount = femaleCount;
	}

	public int getMaleCount() {
		return maleCount;
	}

	public void setMaleCount(int maleCount) {
		this.maleCount = maleCount;
	}

	public RepaymentInterval getRepaymentInterval() {
		return repaymentInterval;
	}

	public void setRepaymentInterval(RepaymentInterval repaymentInterval) {
		this.repaymentInterval = repaymentInterval;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	    	return false;
	    }

	    Borrowers that = (Borrowers) obj;
	    return Objects.equals(this.borrowerId, that.borrowerId)
	        && Objects.equals(this.numberOfIndividuals, that.numberOfIndividuals)
	        && Objects.equals(this.femaleCount, that.femaleCount)
	        && Objects.equals(this.maleCount, that.maleCount)
	        && Objects.equals(this.repaymentInterval, that.repaymentInterval)
        && Objects.equals(this.loan, that.loan);
	  }

	@Override
	public int hashCode() {
		return Objects.hash(this.borrowerId, this.numberOfIndividuals, this.femaleCount,
				this.maleCount, this.repaymentInterval, this.loan);
	  }
}
