package insights.tools;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import insights.dal.*;
import insights.model.*;
import insights.model.Borrowers.RepaymentInterval;
import insights.model.LoanActivities.LoanSectorType;

public class Inserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances
		PartnersDao partnersDao = PartnersDao.getInstance();
		BorrowersDao borrowersDao = BorrowersDao.getInstance();
		LoansDao loansDao = LoansDao.getInstance();
        CountriesDao countriesDao = CountriesDao.getInstance();
        RegionsDao regionsDao = RegionsDao.getInstance();
        LoanContributersDao loanContributersDao = LoanContributersDao.getInstance();
        LoanDurationsDao loanDurationsDao = LoanDurationsDao.getInstance();
        
		// Demo Loans Table CRUD Operations
		// Create
		Date postedDate = new Date();
		postedDate.setTime(1788225988988L);
		Loans loan1 = new Loans(11, 1100, 1100, "US", 198, "Funding to build a new ramp at school entrance",
				"Portland", postedDate, "Services", "a1050000000jEC");
		Loans loan2 = new Loans(12, 2000, 2000, "US", 198, "Buy books",
				"Portland", postedDate, "Services", "a1050000000jEC");
		Loans loan3 = new Loans(13, 700, 700, "US", 198, "Buy supplies",
				"Portland", postedDate, "Services", "a1050000000jEC");

	
		 Loans newLoan = loansDao.create(loan1);
		 Loans newLoan2 = loansDao.create(loan2);
		 Loans newLoan3 = loansDao.create(loan3);
		 System.out.format("Inserted loan ID:%s \n", newLoan.getLoanID());
		 System.out.format("Inserted loan ID:%s \n", newLoan2.getLoanID());
		 System.out.format("Inserted loan ID:%s \n", newLoan3.getLoanID());
		
		
		// Update
		Loans updatedLoan = loansDao.updateFundedAmount(loan1, 950.0);
		System.out.format("loan ID:%s now has funded amount = %s \n", 
			updatedLoan.getLoanID(), updatedLoan.getFundedAmount());
		
		// Read
		Loans retrievedLoan = loansDao.getLoanById(653052);
		System.out.format("loan ID:%s Description: %s \n", 
				retrievedLoan.getLoanID(), retrievedLoan.getDescription());
		
		// Delete
		// loansDao.delete(loan1);
		
		// Get all loans with the given country
		
		List<Loans> loans = loansDao.getLoansForCountry("Italy");
		for (Loans loanCountry: loans) {
			System.out.println(loanCountry.getDescription());
		}

			
		// Get all loans for a given partner
		Partners demoPartner = partnersDao.getPartnerByPartnerId(393);
		List<Loans> loansPartners = loansDao.getLoansForPartner(demoPartner);
		for (Loans loanPartner: loans) {
			System.out.println(loanPartner.getDescription());		
		}


        // create
        // need Loans objects
        LoanContributers loanContributers1 = new LoanContributers(loan1, 10);
        loanContributers1 = loanContributersDao.create(loanContributers1);
        LoanContributers loanContributers2 = new LoanContributers(loan2, 20);
        loanContributers2 = loanContributersDao.create(loanContributers2);
        LoanContributers loanContributers3 = new LoanContributers(loan3, 30);
        loanContributers3 = loanContributersDao.create(loanContributers3);

        LoanDurations loanDuration1 = new LoanDurations(1, loan1, 10.0);
        loanDuration1 = loanDurationsDao.create(loanDuration1);
        LoanDurations loanDuration2 = new LoanDurations(2, loan2, 20.0);
        loanDuration2 = loanDurationsDao.create(loanDuration2);
        LoanDurations loanDuration3 = new LoanDurations(3, loan3, 20.0);
        loanDuration3 = loanDurationsDao.create(loanDuration3);


        // Read
        LoanContributers cc1 = loanContributersDao.getLoanContributersById(6);
        System.out.format("Reading LoanContributers: ID:%d LoanID:%d LenderCount:%d \n",
            cc1.getLoanContributersId(), cc1.getLoan().getLoanID(), cc1.getLenderCount());

        /*
        LoanDurations d1 = loanDurationsDao.getLoanDurationById(7);
        System.out.format("Reading LoanDurations: ID:%d LoanID:%d TermInMonths:%f \n",
            d1.getLoanDurationId(), d1.getLoan().getLoanID(), d1.getTermInMonths());

        // Update
        LoanContributers loanContributers1 = new LoanContributers(loan1, 10);
        LoanContributers loanContributers3 = new LoanContributers(loan3, 30);
        LoanContributers cu1 = loanContributersDao.updateLenderCount(loanContributers3, 50);
        LoanDurations du1 = loanDurationsDao.updateTermInMonths(loanDuration3, 50.0);

        // Delete
        loanContributers1 = loanContributersDao.delete(loanContributers1);
        if (loanContributers1 == null) {
            System.out.println("DELETE LoanContributers Successfully");
        }

        loanDuration1 = loanDurationsDao.delete(loanDuration1);
        if (loanDuration1 == null) {
            System.out.println("DELETE LoanDuration Successfully");
        }
        */
        
        /*
        // Create
        Countries country1 = new Countries("AAA", "Country1");
        country1 = countriesDao.create(country1);
        Countries country2 = new Countries("AAB", "Country2");
        country2 = countriesDao.create(country2);
        Countries country3 = new Countries("AAC", "Country3");
        country3 = countriesDao.create(country3);

        Regions region1 = new Regions("AARegionA", "CountryA", "AAA", "AWorldRegion", 1.0, 1.0, 1.0);
        region1 = regionsDao.create(region1);
        Regions region2 = new Regions("AARegionB", "CoutnryB", "BBB", "AWorldRegion", 1.0, 1.0, 1.0);
        region2 = regionsDao.create(region2);
        Regions region3 = new Regions("AARegionC", "CountryC", "CCC", "AWorldRegion", 1.0, 1.0, 1.0);
        region3 = regionsDao.create(region3);

        // Read
        Countries c1 = countriesDao.getCountryByCountryCode("AAA");
        System.out.format("Reading country: CountryCode:%s Country:%s",
            c1.getCountryCode(), c1.getCountry());

        Regions r1 = regionsDao.getRegionByRegion("AARegionA");
        System.out.format("Reading region: Region:%s Country:%s ISO:%s WorldRegion:%s MPI:%f Latitude:%f Longitude:%f\n",
            r1.getRegion(), r1.getCountry(), r1.getIso(), r1.getWorldRegion(), r1.getMpi(), r1.getLatitude(), r1.getLongitude());

        // Update
        Countries cu3 = countriesDao.updateCountry(country3, "NotCountry3");
        Regions ru3 = regionsDao.updateRegionMPI(region3, 100.1);

        // Print results
        System.out.format("Reading country3: Country:%s CountryCode:%s\n",
            cu3.getCountry(), cu3.getCountryCode());
        System.out.format("Reading region3: Region:%s Country:%s ISO:%s WorldRegion:%s MPI:%f Latitude:%f Longitude:%f\n",
            ru3.getRegion(), ru3.getCountry(), ru3.getIso(), ru3.getWorldRegion(), ru3.getMpi(), ru3.getLatitude(), ru3.getLongitude());

        //Delete
        countriesDao.delete(country1);
        regionsDao.delete(region1);
        System.out.println("Deleted all the first records of tables");

        //------------------------------------------------------------------------------------
        // DAO instances

        LoanThemesDao loanThemesDao = LoanThemesDao.getInstance();

        LoanThemes loanTheme1 = new LoanThemes("testTheme1", "themeDescription1");
        LoanThemes loanTheme2 = new LoanThemes("testTheme2", "themeDescription2");
        LoanThemes loanTheme3 = new LoanThemes("testTheme3", "themeDescription3");
        loanThemesDao.create(loanTheme1);
        loanThemesDao.create(loanTheme2);
        loanThemesDao.create(loanTheme3);

        // Create
        LoanActivitiesDao loanActivitiesDao = LoanActivitiesDao.getInstance();
        LoanActivities loanActivities1 = new LoanActivities("testActivities1", LoanSectorType.Agriculture);
        LoanActivities loanActivities2 = new LoanActivities("testActivities2", LoanSectorType.Arts);
        LoanActivities loanActivities3 = new LoanActivities("testActivities3", LoanSectorType.Personal_Use);
        loanActivitiesDao.create(loanActivities1);
        loanActivitiesDao.create(loanActivities2);
        loanActivitiesDao.create(loanActivities3);

        // read
        System.out.println(loanThemesDao.getLoanThemesFromDescription("themeDescription1").get(0).getLoanThemeId());
        System.out.println(loanThemesDao.getLoanThemesByThemeIdLoanThemes("testTheme2").getLoanThemeDescription());

        System.out.println(loanActivitiesDao.getLoanActivitiesFromLoanSector("Agriculture").get(0).getLoanActivities());
        System.out.println(loanActivitiesDao.getLoanActivitiesByActivityName("testActivities2").getLoanSectors().toString());
        System.out.println(loanActivitiesDao.getLoanActivitiesFromLoanSector("Personal Use").get(0).getLoanActivities());

        // update
        System.out.println();
        System.out.println("test update");
        loanThemesDao.updateLoanThemeDescription(loanTheme1, "themeUpdated1");
        System.out.println(loanThemesDao.getLoanThemesByThemeIdLoanThemes("testTheme1").getLoanThemeDescription());

//        loanActivitiesDao.updateLoanSector(loanActivities1, "Clothing");
//        System.out.println(loanActivitiesDao.getLoanActivitiesByActivityName("testActivities1").getLoanSectors().toString());

        // delete
        System.out.println();
        loanThemesDao.delete(loanTheme3);
        loanActivitiesDao.delete(loanActivities3);

        //------------------------------------------------------------------------------------
   
        // Create
        Partners partner1 = new Partners(1,"Partner1",Partners.PartnerSector.Agriculture);
        partner1 = partnersDao.create(partner1);
        Partners partner2 = new Partners(2,"Partner2",Partners.PartnerSector.Artisan);
        partner2 = partnersDao.create(partner2);
        Partners partner3 = new Partners(3,"Partner3",Partners.PartnerSector.Clean_Energy);
        partner3 = partnersDao.create(partner3);
        Partners partner4 = new Partners(4,"Partner4",Partners.PartnerSector.DSE_Direct);
        partner4 = partnersDao.create(partner4);
        Partners partner5 = new Partners(5,"Partner5",Partners.PartnerSector.Education);
        partner5 = partnersDao.create(partner5);


        Loans loan1 = new Loans(653047);
        Loans loan2 = new Loans(653048);
        Loans loan3 = new Loans(653049);
        Loans loan4 = new Loans(653050);
        Loans loan5 = new Loans(653051);
        */


		/*
        Partners partner1 = new Partners(1,"Partner1",Partners.PartnerSector.Agriculture);
        Partners partner5 = new Partners(5,"Partner5",Partners.PartnerSector.Education);
        
        Borrowers borrower1 = new Borrowers(10000001,8,8,0,Borrowers.RepaymentInterval.Irregular,loan1);
        borrower1 = borrowersDao.create(borrower1);
        Borrowers borrower2 = new Borrowers(10000002,1,1,0,Borrowers.RepaymentInterval.Bullet,loan2);
        borrower2 = borrowersDao.create(borrower2);
        Borrowers borrower3 = new Borrowers(10000003,1,1,0,Borrowers.RepaymentInterval.Monthly,loan3);
        borrower3 = borrowersDao.create(borrower3);
//        Borrowers borrower4 = new Borrowers(10000004,2,2,0,Borrowers.RepaymentInterval.Weekly,loan4);
//        borrower4 = borrowersDao.create(borrower4);
//        Borrowers borrower5 = new Borrowers(1000000,1,1,0,Borrowers.RepaymentInterval.Irregular,loan5);
//        borrower5 = borrowersDao.create(borrower5);

        // Read
        Partners p1 = partnersDao.getPartnerByPartnerId(3);
        System.out.format("Reading partner: ID:%d Name:%s Sector:%s \n",
            p1.getPartnerId(), p1.getPartnerName(), p1.getSector().name());

        Borrowers b1 = borrowersDao.getBorrowerByLoanId(653047);
        System.out.format("Reading borrower: ID:%d Individuals:%d Female:%d Male:%d Repayment:%s LoanID:%d \n",
            b1.getBorrowerId(), b1.getNumberOfIndividuals(), b1.getFemaleCount(), b1.getMaleCount(),
            b1.getRepaymentInterval().name(), b1.getLoan().getLoanID());

        // Update
        Partners pu1 = partnersDao.updatePartnerName(partner5, "New Name");
        Borrowers bu1 = borrowersDao.updateRepaymentInterval(borrower3, "Bullet");

        // Print results
        System.out.format("Reading partner1: ID:%d Name:%s Sector:%s \n",
            pu1.getPartnerId(), pu1.getPartnerName(), pu1.getSector().name());
        System.out.format("Reading borrower1: ID:%d Individuals:%d Female:%d Male:%d Repayment:%s LoanID:%d \n",
            bu1.getBorrowerId(), bu1.getNumberOfIndividuals(), bu1.getFemaleCount(), bu1.getMaleCount(),
            bu1.getRepaymentInterval().name(), bu1.getLoan().getLoanID());

        //Delete
        partnersDao.delete(partner1);
        borrowersDao.delete(borrower1);
        System.out.println("Deleted all the first records of tables");
        */

	}
}