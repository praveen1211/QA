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

<link href='http://fonts.googleapis.com/css?family=Cookie'
	rel='stylesheet' type='text/css'>
<title>QA Framework</title>
<link
	href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script>
	$(function() {

		$("#datepicker1").datepicker({
			dateFormat : "mm-dd-yy"
		});
		$("#datepicker1").datepicker("setDate",
				document.getElementById("datepicker1").value);
		//$("#datepicker1").datepicker("option", "dateFormat", "mm-dd-yy");
		$("#datepicker2").datepicker({
			dateFormat : "mm-dd-yy"
		});
		$("#datepicker2").datepicker("setDate",
				document.getElementById("datepicker2").value);
		//$("#datepicker2").datepicker("option", "dateFormat", "mm-dd-yy");

	});
</script>
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
		var url = "http://172.17.11.203:8080/QAFramework/jsp/usernameAvailability.jsp?email="
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
		var projname = document.getElementById("projname").value;
		var toolname = document.getElementById("toolname").value;
		var datepicker1 = document.getElementById("datepicker1").value;
		var datepicker2 = document.getElementById("datepicker2").value;
		var descripton = document.getElementById("descripton").value;

		//alert("vales>>" + projname + " " +toolname + " " +datepicker1+" "+descripton+" "+datepicker2);

		if (projname.length == 0 || toolname.length == 0
				|| datepicker1.length == 0 || descripton.length == 0
				|| datepicker2.length == 0) {
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
		action="${pageContext.request.contextPath}/editProjsave.qa"
		name="userRegisterForm" autocomplete="off">
		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="project_name">Project
				Name :<span class="req">*</span>
			</label> <form:input class="form-control" path="projName" id="projname" /> </section>
		</div>

		<div class="col-xs-4" style="display: inline-block; margin-left: 25%">
			<section class="content"> <label for="testingTool_name">Testing
				Tool Name: <span class="req">*</span>
			</label> <form:input class="form-control" path="toolName" id="toolname" /></section>
		</div>


		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="Created_Date">Created
				Date :<span class="req">*</span>
			</label> <form:input class="form-control" path="createDate" id="datepicker1"
				onblur="checkExist()" readonly=" " /> <i class="fa fa-calendar"
				style="left: 115%; position: relative; right: 0; top: -30px; z-index: 10; bottom: 0;"></i></section>
		</div>

		<div class="col-xs-4" style="display: inline-block; margin-left: 25%">
			<section class="content"> <label for="Completed_Date">Completed
				Date: <!-- <span class="req">*</span> -->
			</label> <form:input class="form-control" path="completeDate"
				id="datepicker2" /> <i class="fa fa-calendar"
				style="left: 115%; position: relative; right: 0; top: -30px; z-index: 10; bottom: 0;"></i></section>
		</div>

		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="description">Description:
				<!-- <span class="req">*</span> -->
			</label> <form:textarea cols="39" rows="3" path="description" id="descripton" /></section>

		</div>

		<div class="LoginButton">
			<button type="submit" class="button submit" id="btnUpload"
				onclick="return validateForm()" style="margin-top: 24%;">
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