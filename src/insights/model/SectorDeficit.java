package insights.model;

public class SectorDeficit {
	protected String sector;
	protected double percentFunded;
		
	public SectorDeficit(String sector, double percentFunded) {
		this.sector = sector;
		this.percentFunded = percentFunded;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public double getPercentFunded() {
		return percentFunded;
	}

	public void setPercentFunded(double percentFunded) {
		this.percentFunded = percentFunded;
	}

}
