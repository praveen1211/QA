<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.File"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<%@ page import="java.io.FileFilter"%>


<!DOCTYPE html>
<html id="reportHtml">
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

</head>
<body id="reportBody">
	
	<jsp:include page="Testerheader.jsp" />
	<section id="body" class="width1 clear">
		<aside id="sidebar" class="column-left">
			<ul>
				<li>
					<h4></h4>
					<ul class="blocklist">
						<li class="selected-item"><a	href="${pageContext.request.contextPath}/jsp/changePassword.jsp">Reset Password</a></li>
						<!--    <li><a href="${pageContext.request.contextPath}/jsp/history.jsp">History</a></li>
                            <li><a href="${pageContext.request.contextPath}/jsp/versionchanges.jsp">Version Changes</a></li>-->
					</ul>
				</li>
			</ul>
		</aside>




		<section id="content" class="column-right">
			<article>
				<form id="myForm" action="${pageContext.request.contextPath}/doFrgtPwdChng.qa" method="post" name="userLoginForm"
					autocomplete="off">
			<div class="modal-headerone " style="display: inline-block;margin-left: 12%;">
			
						<p class="col-xs-4"
							style="display: inline-block; margin-left: -3%;">
							New Password: <span class="req">*</span><input class="form-control" id="field_pwd2" required="required"  name="field_pwd2" type="password" placeholder="New Password"  style="width: 170%;"/></p>
				
						<p class="col-xs-4"
							style="display: inline-block; margin-left: 196px;">
							Confirm Password: <span class="req">*</span><input class="form-control" id="field_pwd3" required="required" name="field_pwd3" type="password" placeholder="Confirm Password"  style="width: 170%;"/>
						</p>
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