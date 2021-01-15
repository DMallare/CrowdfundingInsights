package insights.model;

import java.util.Objects;

public class Partners {
	protected int partnerId;
	protected String partnerName;
	protected PartnerSector sector;
	
	public enum PartnerSector {
		Agriculture, Artisan, Clean_Energy, DSE_Direct, Education, General_Financial_Inclusion,
		Health, Mobile_Money_and_ICT, Other, SME_Financial_Inclusion, Water_and_Sanitation
	}

	public Partners(int partnerId, String partnerName, PartnerSector sector) {
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.sector = sector;
	}

	public Partners(String partnerName, PartnerSector sector) {
		this.partnerName = partnerName;
		this.sector = sector;
	}

	public Partners(int partnerId) {
		this.partnerId = partnerId;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public PartnerSector getSector() {
		return sector;
	}

	public void setSector(PartnerSector sector) {
		this.sector = sector;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	    	return false;
	    }

	    Partners that = (Partners) obj;
	    return Objects.equals(this.partnerId, that.partnerId)
	        && Objects.equals(this.partnerName, that.partnerName)
	        && Objects.equals(this.sector, that.sector);
	  }

	@Override
	public int hashCode() {
		return Objects.hash(this.partnerId, this.partnerName, this.sector);
	  }
	
}
