<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.File"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<%@ page import="java.io.FileFilter"%>


<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reports.css">
<link href='http://fonts.googleapis.com/css?family=Cookie'
	rel='stylesheet' type='text/css'>
<title>QA Framework</title>
<link
	href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>


<link rel="stylesheet"
	href=" https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="  https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script
	src="  https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script
	src="   https://cdn.datatables.net/1.10.13/js/dataTables.bootstrap.min.js"></script>
<style type="text/css">
.req {
	color: #ef4048;
}
</style>
<script>
	function checkExist() {

		var xmlhttp = new XMLHttpRequest();
		var email = document.forms["userLoginForm"]["oldEmail"].value;
		alert(email);
		var url = "usernameAvailability.jsp?email=" + email;
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var text = xmlhttp.responseText.trim();

				if (text == "email is invalid") {
					// document.getElementById("isE").style.color = "red";
					alert("email is invalid");
					document.forms["userLoginForm"]["oldEmail"].value = "";

				}
				//  else{
				// alert("email not exists");
				//}
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
	$(document).ready(function() {
		$('#creUser').addClass("selected-item");
			
 $("#phone").mask("(999)-999-9999");

	});
</script>

</head>
<body>
	
	<div id="sitename">
		<div class="width">
			<h1>
			<a href="#" style=" margin-left: 14%;float:left;   margin-top: 20px;">
			<img
					src="${pageContext.request.contextPath}/images/miraclelogo.png"
					 /></a>
    <a href="#" style="float: right;position:relative ;left: 800px;bottom: 124px;">
<img
					src="${pageContext.request.contextPath}/images/QAlogo.png"
					style="height: 190px;width: 230px;" /></a>
					

		</h1>
			<div class="clear"></div>
		</div>
	</div>
	<section id="body" class="width clear">
		<aside id="sidebar" class="column-left">
			<ul>
				<li>
					<h4></h4>
					<ul class="blocklist">
						<li><a
							href="${pageContext.request.contextPath}">Back</a></li>
					<!-- 	<li class=""><a
							href="${pageContext.request.contextPath}/project.qa">Projects</a></li>
						<li ><a
							href="${pageContext.request.contextPath}/jsp/reports.jsp">Reports</a></li>
					<li class="selected-item"><a	href="${pageContext.request.contextPath}/jsp/changePassword.jsp">Change password</a></li> -->
					
					</ul>

				</li>
			</ul>
		</aside>




		<section id="content" class="column-right">
			<article>
				<form id="myForm" action="${pageContext.request.contextPath}/oldPwdEmail.qa" method="post" name="userLoginForm"
					autocomplete="off">
			<div class="modal-headerone " style="display: inline-block;">
						<p class="col-xs-4"
							style="display: inline-block; margin-right: 52%; margin-left: 30%;">
							Enter your Email ID: <span class="req">*</span><input class="form-control" id="oldEmail" required="required"  name="oldEmail" type="email" placeholder="Enter your EmailId"  style="width:215px;" /></p>
				
				<!--  	<p class="col-xs-4"
							style="display: inline-block; margin-left: 196px;">
							Confirm your new Password: <span class="req">*</span><input class="form-control" id="field_pwd2" required="required" name="field_pwd2" type="password" placeholder="Confirm your new Password" pattern="(?=.*\d)(?=.*[@!#\$\^%&*_-])(?=.*[a-z])(?=.*[A-Z]).{6,}" style="width: 170%;"/>
						</p>-->
					</div>

				
							<!--  <p>Email ID: <input id="field_username" name="field_username"  type="text"></p>-->
                       

						
					<div class="LoginButton">
						<button type="submit" class="button submit" id="btnUpload"
							onclick="return calendarCheck()">
							<span>SUBMIT</span>
						</button>

					</div>
					<div class="Logincancel">
						<button type="reset" class="button cancel">
							<span>CANCEL</span>
						</button>
					</div>



					

				</form>


			</article>

		</section>


	</section>



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