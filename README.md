# Crowdfunding Insights

<p>
“Crowdfunding Insights” is a web application that provides nonprofits such as Kiva (see note below) with a detailed analysis of their outreach efforts, lending partners, and recipients. This product provides thoughtful analysis of their critical data and tools to help simplify their outreach efforts to partners and borrowers, helping lenders understand their target communities thoughtfully, allowing them to maximize their efforts, make better informed decisions, and help set investment priorities.
</p>

![Home page](https://github.com/DMallare/CrowdfundingInsights/blob/main/images/HomePage.PNG)
___

## Team Members
Rebecca Buckler, Jiapei Li, Danielle Mallare-Dani, </br>
Jason Ven, Mengjiemei Wang, Zhiwei Zhang

## Features

### User can filter loans by
* ID
* Region
* Country
* Partner
* Sector 
* Duration (term of the loan)

![Filter loans by country and sector feature](https://github.com/DMallare/CrowdfundingInsights/blob/main/images/SearchLoanFeature.PNG)

### Analyze funding gaps
* Calculates percent of loan funded for each loan
* Allows user to see countries where funded percentage is the least
* Allows user to see sectors where funded percemtage is the least


### Analyze need by loan theme (e.g. "startups")
* Allows user to see regions with top loan count and funding amounts for the specified theme.
* Calciulates a "need"-driven index to determine which regions have greatest need for funding in the given loan theme category. Uses average loan amount and country GDP in calculation of index.
* Includes a map to display locations where demand for loan theme is greatest

![Top countries and need-driven index for "startup" loan theme](https://github.com/DMallare/CrowdfundingInsights/blob/main/images/LoanThemeAnalysisFeature.PNG)

![Map display of demand for loans with theme "startup"](https://github.com/DMallare/CrowdfundingInsights/blob/main/images/LoanThemeAnalysisMap.PNG)

### Recommender tool
* Recommends a loan partner by given region or given loan sector
* Tool suggests partners who have historically contributed the most in the given region or to loans in the spcified sector.

![Partner recommender tool used with education sector](https://github.com/DMallare/CrowdfundingInsights/blob/main/images/PartnerRecommenderFeature.PNG)

### Analyze partners
* Allows user to search for a parnter or create a new partner
* Graphical display of distribution of partners by sector

![Pie chart displaying distribution of partners among business sectors](https://github.com/DMallare/CrowdfundingInsights/blob/main/images/PartnerSectorBreakdown.PNG)

