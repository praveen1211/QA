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
	href="${pageContext.request.contextPath}/css/createUser.css">
<link href='http://fonts.googleapis.com/css?family=Cookie'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href=" https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="  https://code.jquery.com/jquery-1.12.4.js"></script>
<script
	src="  https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script
	src="   https://cdn.datatables.net/1.10.13/js/dataTables.bootstrap.min.js"></script>
<script>
	$(document).ready(function() {
		$('#example').DataTable();
	});
	$(document).ready(function() {
		$('#unassignUser').addClass("selected-item");
	});
</script>

<title>QA Framework</title>
</head>
<body class="submit_body">

	
			<jsp:include page="Adminheader.jsp" />

		
	<section id="body" class="width clear homeBody"> <jsp:include
		page="AdminLeftNav.jsp" /> <section id="content" class="column-right">
	<article>
	<table id="example" class="table table-striped table-bordered"
		cellspacing="0" width="100%">


		<thead>
			<tr>
				<th>User Name</th>
				<th>Email</th>
				<th>Phone Number</th>
				<th>User Role</th>
				<th>Delete</th>
			</tr>

		</thead>
		<tbody>
			<c:forEach items="${sessionScope.listgetUnassignedUsers}" var="emp">
				<tr>

					<td>${emp.username}</td>
					<td>${emp.email}</td>
					<td>${emp.phone}</td>
					<td>${emp.userRole}</td>
					<td>
					
					
					<a href="${pageContext.request.contextPath}/deleteUnassignedUsers/${emp.userID}.qa"
						onclick="return confirm_alert(this);">Delete</a>
						<!-- <c:if test="${emp.userRole=='Tester'}">-->
						<!--</c:if>-->
						</td>

					<!--  <td><a href="${pageContext.request.contextPath}/editemp/${emp.projID}/${emp.userID}/${emp.firstNAME}.qa">Edit</a></td>-->

				</tr>
			</c:forEach>
		</tbody>




	</table>
	<br />

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
