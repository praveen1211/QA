<%-- This is JSP comment --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="assets/header-search.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/createUser.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href='http://fonts.googleapis.com/css?family=Cookie'
	rel='stylesheet' type='text/css'>
<title>QA Framework</title>
<script>
	$(document).ready(function() {
		$('#Home').addClass("selected-item");
	});
</script>
<style type="text/css">
.req {
	color: #ef4048;
}
</style>


<script>
	function checkExist() {

		var xmlhttp = new XMLHttpRequest();
		var email = document.forms["userRegisterForm"]["email"].value;
		var url = "/usernameAvailability.jsp?email="
				+ email;
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var text = xmlhttp.responseText.trim();

				if (text == "email already exists") {
					// document.getElementById("isE").style.color = "red";
					alert("email already exists");
					document.forms["userRegisterForm"]["email"].value = "";

				}

				//else
				// document.getElementById("isE").style.color = "green";
				//document.getElementById("isE").innerHTML = xmlhttp.responseText;
			}

		};
		try {
			xmlhttp.open("GET", url, true);
			xmlhttp.send();
		} catch (e) {
			alert("unable to connect to server");
		}
	}
</script>
<!-- code for validating the fields  by ramya-->
<script>
	function validateForm() {
		var firstname = document.getElementById("firstname").value;
		var lastname = document.getElementById("lastname").value;
		var isE = document.getElementById("isE").value;
		var phone = document.getElementById("phone").value;

		//alert("vales>>" + firstname + " " + lastname + " " + isE);

		if (firstname.length == 0 || lastname.length == 0 || isE.length == 0
				|| phone.length == 0) {
			alert("please enter all mandatory fields!")
			return false;
		}

	}
</script>
<script type="text/javascript">
	$(document).ready(function(){
var msg=document.getElementById("message").value;
if(msg!="")
{
alert(msg);
}
   // jQuery methods go here...

});
</script>


</head>
<body class="submit_body">
<input type="hidden" id="message" value="${message}"/>
	
			<jsp:include page="Adminheader.jsp" />
	<section id="body" class="width clear homeBody"> <jsp:include
		page="AdminLeftNav.jsp" /> <section id="content" class="column-right">
	<article> <form:form method="POST"
		action="${pageContext.request.contextPath}/editsave.qa"
		name="userRegisterForm" autocomplete="off">
		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="first_name">First
				Name :<span class="req">*</span>
			</label> <form:input class="form-control" path="firstNAME" id="firstname"
				readonly="true" /> </section>
		</div>
		<div class="col-xs-4" style="display: inline-block; margin-left: 25%">
			<section class="content"> <label for="last_name">Last
				Name :<span class="req">*</span>
			</label> <form:input class="form-control" path="lastNAME" id="lastname"
				readonly="true" /></section>
		</div>
		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="email">Email
				:<span class="req">*</span>
			</label> <form:input class="form-control" path="email" onblur="checkExist()"
				id="isE" readonly="true" /></section>
		</div>

		<div class="col-xs-4" style="display: inline-block; margin-left: 25%">
			<section class="content"> <label for="phone">Phone
				:<!--<span class="req">*</span>-->
			</label> <form:input class="form-control" path="phone" id="phone"
				readonly="true" /></section>
		</div>
		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="dropdown">User
				Role: <span class="req">*</span>
			</label> <form:select class="form-control" path="userRole" id="userRole"
				style="width: 130%;">
				<form:option label="tester" value="1"></form:option>
				<form:option label="Admin" value="0"></form:option>
			</form:select> </section>

		</div>
		<div class="LoginButton">
			<button type="submit" class="button submit" id="btnUpload"
				onclick="return validateForm()">
				<span>Edit Save</span>

			</button>

		</div>
	</form:form> </article> </section> </section>

	<footer class="clear">
	<div class="width">
		<p class="left">
			Copyrights <i class="fa fa-copyright"></i> 2017.All Rights Reserved.
		</p>
		<p class="right">
			<a title="www.miraclesoft.com" href="http://www.miraclesoft.com"
				target="_blank"> Miracle Software Systems,Inc. </a>
		</p>
	</div>
	</footer>

</body>
</html>