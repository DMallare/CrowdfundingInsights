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
<title>Create a Partner</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary" style="margin-bottom: 40px">
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
        
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Loans</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="findloans">Search Loans</a>
          <a class="dropdown-item" href="finddurations">Search Duration</a>
          <a class="dropdown-item" href="findloanthemes">Search Themes</a>
          <a class="dropdown-item" href="loandeficitanalysis">Analyze Funding</a>
        </div>
      </li>
        
      <li class="nav-item dropdown active">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Partners</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="findpartners">Search Partners</a>
          <a class="dropdown-item" href="partnercreate">Create Partner</a>
          <a class="dropdown-item" href="partnerrecommender">Partner Recommender</a>
<!--           <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Separated link</a> -->
        </div>
      </li>
        
        <li class="nav-item">
          <a class="nav-link" href="findregions">Regions</a>
        </li>
    </ul>
  </div>
</nav>

	<div class="container theme-showcase" role="main">
	<form action="partnercreate" method="post">
		<h4 style="margin-bottom: 15px">Create a partner</h4>
		<p>
			<label for="partnerid">Partner ID</label>
			<input id="partnerid" type="number" min="0" name="partnerid" value="">
		</p>
		<p>
			<label for="partnername">Partner Name</label>
			<input id="partnername" name="partnername" value="">
		</p>
		<p>
			<label for="sector">Partner Sector</label>
			<!-- <input id="sector" name="sector" value=""> -->
  			<select id="sector" name="sector">
    			<option value="Agriculture">Agriculture</option>
    			<option value="Artisan">Artisan</option>
    			<option value="Clean Energy">Clean Energy</option>
    			<option value="DSE Direct">DSE Direct</option>
    			<option value="Education">Education</option>
    			<option value="General Financial Inclusion">General Financial Inclusion</option>
    			<option value="Health">Health</option>
    			<option value="Mobile Money and ICT">Mobile Money and ICT</option>
    			<option value="Other">Other</option>
    			<option value="SME Financial Inclusion">SME Financial Inclusion</option>
    			<option value="Water and Sanitation">Water and Sanitation</option>
  			</select>
		</p>
		<p>
			<input type="submit" value="Create" class="btn btn-sm btn-primary">
			<a href="findpartners"><button type="button" class="btn btn-outline-primary btn-sm">Back to Home Page</button></a>
		</p>
	</form>
	<br/><br/>
	<p>
		<div class="alert alert-success" role="alert">
		<span id="successMessage"><b>Information:<br>${messages.success}</b></span>
		</div>
	</p>
	
	</div>
	
	<!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    
</body>
</html>