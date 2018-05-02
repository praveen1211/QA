<%-- This is JSP comment --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/createProject.css">
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
		$("#datepicker-1").datepicker();
		$("#datepicker-1").datepicker("option", "dateFormat", "mm-dd-yy");
		$("#datepicker-2").datepicker();
		$("#datepicker-2").datepicker("option", "dateFormat", "mm-dd-yy");

	});
	$(document).ready(function() {
		$('#creProject').addClass("selected-item");
	});
</script>
<style type="text/css">
.req {
	color: #ef4048;
}
</style>
<script type="text/JavaScript">
	function validate() {
		if (document.userRegisterForm.status.value == "-1") {
			alert("Please select status!");
			return false;
		}
	}
</script>
</head>
<body class="submit_body">
	
			<jsp:include page="Adminheader.jsp" />

	<section id="body" class="width clear homeBody"> <jsp:include
		page="AdminLeftNav.jsp" /> <section id="content" class="column-right">
	<article>

	<form action="${pageContext.request.contextPath}/createProj.qa"
		method="post" name="userRegisterForm"
		onsubmit="return validate(this);" autocomplete="off">
		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="project_name">Project
				Name: <span class="req">*</span>
			</label> <input class="form-control" type="text" name="projName"
				placeholder="Enter Project Name" pattern="[A-Za-z0-9\s]{1,32}" required /> </section>
		</div>

		<div class="col-xs-4" style="display: inline-block; margin-left: 25%;">
			<section class="content"> <label for="Testingtool_name">Testing
				Tool Name: <span class="req">*</span>
			</label>
			<!-- <select id="toolname" class="form-control" name="toolname"
					>
					<option>Select tools</option>
					<c:forEach items="${sessionScope.toolList}"
						var="toolListValue">
						<option>${toolListValue}</option>
					</c:forEach>

				</select> -->
			  <input class="form-control" type="text" name="toolName"
				placeholder="Enter Testing Tool Name" pattern="[A-Za-z\s]{1,32}"  required /> 
				</section>

		</div>

		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="Created Date">Created
				Date : <span class="req">*</span>
			</label> <input class="form-control" type="text" id="datepicker-1"
				name="createDate" placeholder="mm-dd-yyyy" required> <i
				class="fa fa-calendar"
				style="left: 115%; position: relative; right: 0; top: -30px; z-index: 10; bottom: 0;"></i>
			</input> </section>

		</div>
		<div class="col-xs-4" style="display: inline-block; margin-left: 25%">
			<section class="content"> <label for="Completed Date">Completed
				Date:<!--  <span class="req">*</span> -->
			</label> <input class="form-control" type="text" id="datepicker-2"
				name="completeDate" placeholder="mm-dd-yyyy" /> <i
				class="fa fa-calendar"
				style="left: 115%; position: relative; right: 0; top: -30px; z-index: 10; bottom: 0;"></i>
			</section>
		</div>
		<!-- <div class="col-xs-4" style="display:inline-block;">
 <section class="content">
 <label for="project_name">Project ID:  <span class="req">*</span></label>
  <input  class="form-control"  type="text" name="projID" placeholder="Enter project ID" required />
  </section>
  </div>
   -->
		<div class="col-xs-4" style="display: inline-block;">
			<section class="content"> <label for="Status">Status:
				<span class="req">*</span>
			</label> <select class="form-control" style="width: 130%" name="status"
				id="status">
				<option value="-1" selected>Select</option>
				<option value="Active">Active</option>
				<option value="Inactive">Inactive</option>
			</select> </section>
		</div>




		<div class="col-xs-4" style="display: inline-block; margin-left: 25%;">
			<section class="content"> <label for="Description" >Description:
				<!-- <span class="req">*</span> -->
			</label> <textarea cols="46" rows="3" name="description" placeholder="Enter Project Description"></textarea>
			</section>

		</div>
		<div class="col-xs-4" style="display: inline-block;"></div>
		<div class="LoginButton"
			style="display: inline-block; margin-left: 24%; margin-right: 36%; margin-top: 5%;">
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
