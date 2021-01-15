<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<title>Find a Duration</title>
</head>
<body>

<nav class="navbar navbar-expand-sm navbar-dark bg-primary" style="margin-bottom: 40px">
  <a class="navbar-brand" href="/CrowdfundingInsights" style="text-align: center">Crowdfunding<br>Insights</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarColor01">
    <ul class="navbar-nav ml-auto" style="padding-right: 50px">
      <li class="nav-item">
        <a class="nav-link" href="/CrowdfundingInsights">Home
          <span class="sr-only">(current)</span>
        </a>
      </li>
        
      <li class="nav-item dropdown active">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Loans</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="findloans">Search Loans</a>
          <a class="dropdown-item" href="finddurations">Search Duration</a>
          <a class="dropdown-item" href="findloanthemes">Search Themes</a>
          <a class="dropdown-item" href="loananalysis">Analyze Funding</a>
        </div>
      </li>
        
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Partners</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="findpartners">Search Partners</a>
          <a class="dropdown-item" href="partnercreate">Create Partner</a>
          <a class="dropdown-item" href="partnerrecommender">Partner Recommender</a>
        </div>
      </li>
        
        <li class="nav-item">
          <a class="nav-link" href="findregions">Regions</a>
        </li>
    </ul>
  </div>
</nav>

    <div class="container theme-showcase" role="main">

	<form action="finddurations" method="post">		
		<div class="search-bar">
		<label for="loanid">Loan ID</label>
			<input id="loanid" name="loanid" type="number" min="0" placeholder=" Enter a loan ID" style="margin-right: 10px" value="${fn:escapeXml(param.loanid)}">
			<label for="terminmonths">Duration</label>
			<input id="terminmonths" name="terminmonths" type="text" placeholder=" Enter a Duration" style="margin-left: 10px" value="${fn:escapeXml(param.terminmonths)}">			
			<input type="submit" value="Search" class="btn btn-sm btn-primary" style="margin-right: 10px">
		</div>
		<br/><br/>
		
	</form>
	<br/>
	<div class="alert alert-info" role="alert">
	<h6><span id="successMessage"><b>${messages.success}</b></span></h6>
	</div>
	<br/>
	<h5>Matching Loan Durations</h5>
        <table class="table table-striped">
            <thead><tr>
            	<th>LoanDuration ID</th>
                <th>Loan ID</th>
                <th>Term In Months</th>
            </tr></thead>
            <c:forEach items="${durations}" var="duration" >
            	<tbody><tr>
            		<td><c:out value="${duration.getLoanDurationId()}" /></td>
                	<td><c:out value="${duration.getLoan().getLoanID()}" /></td>
                	<td><c:out value="${duration.getTermInMonths()}" /></td>
            	</tr></tbody>
            </c:forEach>
       </table>
       
    </div>
     
    <!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
       
</body>
</html>