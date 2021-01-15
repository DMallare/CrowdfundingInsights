<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<title>Find Loans by Themes</title>

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
			google.charts.load('current', {
				'packages' : [ 'geochart' ],
				// Note: you will need to get a mapsApiKey for your project.
				// See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
				'mapsApiKey' : 'AIzaSyCASX6o6U5mBnRzwry-x3riG9Z4P6WsSlk'
			});
			google.charts.setOnLoadCallback(drawRegionsMap);

			function drawRegionsMap() {
				var data = new google.visualization.DataTable();
				data.addColumn('string', 'Country');
				data.addColumn('number', 'Index');
				data.addRows([
						<c:forEach items="${loanCountryMap}" var="entry">
						[ '${entry.key}', ${entry.value} ],
						</c:forEach>
						]);

				var options = {};

				var chart = new google.visualization.GeoChart(document
						.getElementById('regions_div'));

				chart.draw(data, options);
			}
			</script>


</head>

<body>

	<nav class="navbar navbar-expand-sm navbar-dark bg-primary"
		style="margin-bottom: 40px"> <a class="navbar-brand"
		href="/CrowdfundingInsights" style="text-align: center">Crowdfunding<br>Insights
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor01" aria-controls="navbarColor01"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarColor01">
		<ul class="navbar-nav ml-auto" style="padding-right: 50px">
			<li class="nav-item"><a class="nav-link"
				href="/CrowdfundingInsights">Home <span class="sr-only">(current)</span>
			</a></li>

			<li class="nav-item dropdown active"><a
				class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"
				role="button" aria-haspopup="true" aria-expanded="false">Loans</a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="findloans">Search Loans</a> <a
						class="dropdown-item" href="finddurations">Search Duration</a> <a
						class="dropdown-item" href="findloanthemes">Search Themes</a> <a
						class="dropdown-item" href="loandeficitanalysis">Analyze Funding</a>
				</div></li>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"
				role="button" aria-haspopup="true" aria-expanded="false">Partners</a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="findpartners">Search Partners</a> <a
						class="dropdown-item" href="partnercreate">Create Partner</a> <a
						class="dropdown-item" href="partnerrecommender">Partner
						Recommender</a>
				</div></li>

			<li class="nav-item"><a class="nav-link" href="findregions">Regions</a>
			</li>
		</ul>
	</div>
	</nav>

	<div class="container theme-showcase" role="main">

		<form action="findloanthemes" method="post">
			<div class="search-bar">
				<label for="loanThemeDesp">Theme Descriptions</label> <input
					id="loanThemeDesp" name="loanThemeDesp"
					placeholder="Enter a loan theme description	"
					value="${fn:escapeXml(param.loanThemeDesp)}"> <input
					type="submit" value="Search" class="btn btn-sm btn-primary"
					style="margin-right: 10px" valign="top" align="right"> <a
					href="themeCreate"><button type="button"
						class="btn btn-outline-primary btn-sm">Create LoanTheme</button></a> <a
					href="themeUpdate"><button type="button"
						class="btn btn-outline-primary btn-sm">Update LoanTheme</button></a> <a
					href="themeDelete"><button type="button"
						class="btn btn-outline-primary btn-sm">Delete LoanTheme</button></a>
			</div>

		</form>
		<br />
		<div class="alert alert-info" role="alert">
			<h6>
				<span id="successMessage"><b>Note: Please enter a loan
						theme description to get its geographical info.<br> <br>${messages.success}</b></span>
			</h6>
		</div>
		<br />

		<div>
			<br>
			<h5>Loan Theme: "${param.loanThemeDesp}"</h5>
			<br>
		</div>


		<div class="container">
			<div
				style="text-align: left; width: 32%; float: left; display: inline-block;">
				<h5>Regions with Top Loan Count</h5>
				<table class="table table-striped">
					<thead>
						<tr>
							<!-- <th>Theme</th> -->
							<th>Country</th>
							<th>Region</th>
							<th>Count</th>
						</tr>
					</thead>
					<c:forEach items="${loanThemeGeoDatasCnt}"
						var="loanThemeGeoDatasCnt">
						<tbody>
							<tr>
								<%-- <td><c:out
										value="${loanThemeGeoDatasCnt.getLoanThemeDescription()}" /></td> --%>
								<td style="text-align: left"><c:out
										value="${loanThemeGeoDatasCnt.getCountryName()}" /></td>
								<td style="text-align: left"><c:out
										value="${loanThemeGeoDatasCnt.getRegion()}" /></td>
								<td style="text-align: left"><c:out
										value="${loanThemeGeoDatasCnt.getLoanThemeData()}" /></td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>

			<div
				style="text-align: left; width: 32%; float: left; display: inline-block; margin-left: 1%;">
				<h5>Regions with Top Amount Sum</h5>
				<table class="table table-striped">
					<thead>
						<tr>
							<!-- <th>Theme</th> -->
							<th>Country</th>
							<th>Region</th>
							<th>Sum</th>
						</tr>
					</thead>
					<c:forEach items="${loanThemeGeoDatasSum}"
						var="loanThemeGeoDatasSum">
						<tbody>
							<tr>
								<%-- <td><c:out
										value="${loanThemeGeoDatasSum.getLoanThemeDescription()}" /></td> --%>
								<td style="text-align: left"><c:out
										value="${loanThemeGeoDatasSum.getCountryName()}" /></td>
								<td style="text-align: left"><c:out
										value="${loanThemeGeoDatasSum.getRegion()}" /></td>
								<td style="text-align: left"><c:out
										value="${loanThemeGeoDatasSum.getLoanThemeData()}" /></td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>

			<div
				style="text-align: left; width: 34%; float: left; display: inline-block; margin-left: 1%">
				<h5>"Need"-Driven Index</h5>
				<table class="table table-striped">
					<thead>
						<tr>
							<!-- <th>Theme</th> -->
							<th>Country</th>
							<th>Avg_Amount</th>
							<th>GDP</th>
							<th>Index</th>
						</tr>
					</thead>
					<c:forEach items="${loanThemesGeoCountries}"
						var="loanThemesGeoCountries">
						<tbody>
							<tr>
								<%-- <td><c:out value="${loanThemesGeoCountries.getLoanThemeDescription()}" /></td> --%>
								<td style="text-align: left"><c:out
										value="${loanThemesGeoCountries.getCountryName()}" /></td>
								<td style="text-align: left"><c:out
										value="${loanThemesGeoCountries.getAvgLoanAmount()}" /></td>
								<td style="text-align: left"><c:out
										value="${loanThemesGeoCountries.getGDP()}" /></td>
								<td style="text-align: left"><c:out
										value="${loanThemesGeoCountries.getIndex()}" /></td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
		</div>


		<div style="clear: both;" class="container">
			<br>

			<div id="regions_div"
				style="width: 900px; height: 500px; float: left">
			</div>
			
			<div style="float: right">
				<table style="valign: center;">
					<c:forEach items="${loanCountryMap}" var="entry">
						<tbody>
							<tr>
								<td style="text-align: left; color: lightGrey; text-align: center"><c:out value="${entry.key}" /></td>
							</tr>
<%-- 							<tr>
								<td style="text-align: right; color: silver"><c:out <fmt:formatNumber type = "number" maxFractionDigits = "1" value = "${entry.value}" /> /></td>
							</tr> --%>
						</tbody>
					</c:forEach>
				</table>
			</div>
			<br> <br> <br>
		</div>

		<div style="clear: both;">
			<br> <br>
		</div>

		<div style="clear: both;">
			<h5>Matching LoanThemeDescriptions</h5>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>LoanTheme</th>
						<th>Loan Amount</th>
						<th>Country</th>
						<th>Region</th>
						<th>Activity</th>
						<th>Description</th>
						<th>Loan ID</th>
					</tr>
				</thead>
				<c:forEach items="${loanThemeGeos}" var="loanThemeGeo">
					<tbody>
						<tr>
							<td style="text-align: left"><c:out
									value="${loanThemeGeo.getLoanThemeDescription()}" /></td>
							<td style="text-align: left"><c:out
									value="${loanThemeGeo.getLoanAmount()}" /></td>
							<td style="text-align: left"><c:out
									value="${loanThemeGeo.getCountryName()}" /></td>
							<td style="text-align: left"><c:out
									value="${loanThemeGeo.getRegion()}" /></td>
							<td style="text-align: left"><c:out
									value="${loanThemeGeo.getLoanActivities()}" /></td>
							<td style="text-align: left"><c:out
									value="${loanThemeGeo.getDescription()}" /></td>
							<td style="text-align: left"><c:out
									value="${loanThemeGeo.getLoanID()}" /></td>
						</tr>
					</tbody>
				</c:forEach>
			</table>
		</div>
	</div>

	<!-- Bootstrap -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"
		type="text/javascript"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
