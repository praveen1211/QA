<%-- This is JSP comment --%>
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
<script src="../js/jquery.maskedinput.js" type="text/javascript"></script>
<style type="text/css">
.req {
	color: #ef4048;
}
</style>
<script>
	function checkExist() {

		var xmlhttp = new XMLHttpRequest();
		var email = document.forms["userRegisterForm"]["email"].value;
		var url = "../usernameAvailability.qa?email=" + email;
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var text = xmlhttp.responseText.trim();

				if (text == "email already exists") {
					// document.getElementById("isE").style.color = "red";
					alert("email already exists");
					document.forms["userRegisterForm"]["email"].value = "";

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
<!-- code for validating the fields  by ramya-->
<script type="text/JavaScript">
	function validate() {
		if (document.userRegisterForm.select.value == "-1") {
			alert("Please select type of user!");
			return false;
		}
	}

</script>
</head>
<body class="submit_body">
	<div id="sitename">
		<div class="width">
			
			<jsp:include page="Adminheader.jsp" />


			<div class="clear"></div>
		</div>
	</div>
	<section id="body" class="width clear homeBody"> <jsp:include
		page="AdminLeftNav.jsp" /> <section id="content" class="column-right">
	<article>
	<form action="${pageContext.request.contextPath}/register.qa"
		method="post" name="userRegisterForm"
		onsubmit="return validate(this);" autocomplete="off">
		<div class="col-xs-4" style="">
			<section class="content"> <label for="first_name">First
				Name: <span class="req">*</span>
			</label> <input class="form-control" type="text" name="firstname"
				placeholder="Enter First Name" maxlength="32"
				pattern="[A-Za-z]{1,32}" required /> </section>
		</div>
		<div class="col-xs-4" style="display: inline-block; margin-left: 25%;">
			<section class="content"> <label for="last_name">Last
				Name: <span class="req">*</span>
			</label> <input class="form-control" type="text" name="lastname"
				placeholder="Enter Last Name" maxlength="32"
				pattern="[A-Za-z]{1,32}" required /> </section>

		</div>
		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="email">Email
				: <span class="req">*</span>
			</label> <input class="form-control" type="email" onblur="checkExist()"
				name="email" placeholder="Enter Email"
				pattern="[A-Za-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
				title="please enter an valid email address" required
				data-type="email" /> <span id="isE"> </span> </section>

		</div>
		<div class="col-xs-4" style="display: inline-block; margin-left: 25%;">
			<section class="content"> <label for="phone">Phone
				Number : <!-- <span class="req">*</span> -->
			</label> <input class="form-control" type="text" id="phone" name="phone"
				placeholder="Enter Phone Number"  /> </section>
		</div>
		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="pwd">Password:
				<span class="req">*</span>
			</label> <input class="form-control" type="password" name="pwd"
				placeholder="Enter Password"
				pattern="(?=.*\d)(?=.*[@!#\$\^%&*_-])(?=.*[a-z])(?=.*[A-Z]).{6,}"
				id="password"
				title="Must be at least 6 characters long, and contain at least one number, one uppercase and one lowercase letter and a special charcter"
				required data-type="Password" tabindex="2" /> </section>

		</div>
		<div class="col-xs-4" style="display: inline-block; margin-left: 25%;">
			<section class="content"> <label for="dropdown">User
				Role: <span class="req">*</span>
			</label> <select class="form-control" name="dropdown" style="width: 130%"
				id="select">
				<option value="-1" selected>Select</option>

				<option value="Admin">Admin</option>
				<option value="Tester">Tester</option>
				<option value="TeamLead">Team Lead</option>
			</select> </section>
		</div>





		<div class="LoginButton"
			style="display: inline-block; margin-top: 5%; margin-right: 36%; margin-left: 20%;">
			<button type="submit" class="button submit" id="btnUpload">
				<span>SUBMIT</span>
			</button>

		</div>
		<div class="Logincancel" style="display: inline-block;">
			<button type="reset" class="button cancel">
				<span>CANCEL</span>
			</button>
		</div>

	</form>
	</article> </section> </section>

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
