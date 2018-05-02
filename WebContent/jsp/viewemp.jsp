<%-- This is JSP comment --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href='http://fonts.googleapis.com/css?family=Cookie'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href=" https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/msc-style.css">
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
		$('#Home').addClass("selected-item");
	});
</script>
    <script src="js/msc-script.js"></script>
<!-- code for validating the fields  by ramya-->
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function() {
var demobtn2 = document.querySelector("#delete");
        demobtn2.addEventListener("click", function() {
          mscConfirm("Delete", "Are you sure you want to delete this post?", function(){
            mscAlert("Post deleted");
            window.location = "${pageContext.request.contextPath}/deleteemp/${emp.projID}/${emp.userID}/${emp.toolID}.qa";
          },
          function() {
           // mscAlert('Cancelled');
          });
        });
        });
	function confirm_alert(node,projID,userID,toolID) {
	//alert(projID);
	mscConfirm("Delete", "Are you sure you want to delete the user to project?", function(){
           
            window.location = "${pageContext.request.contextPath}/deleteemp/"+projID+"/"+userID+"/"+toolID+".qa";
             
          },
          function() {
           // mscAlert('Cancelled');
          }
           );
	//	return mscConfirm("Delete", "Are you sure you want to delete this post?");
		
		//return confirm("Are you sure want to delete?");
	}
	
</script>
<script type="text/javascript">
	$(document).ready(function(){
var msg=document.getElementById("msg").value;
if(msg=="delete")
{
alert("User is successfully deleted from the project");
}
   // jQuery methods go here...

});
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


<title>QA Framework</title>
</head>
<body class="submit_body">
<input type="hidden" id="message" value="${message}"/>
	
	 <jsp:include page="Adminheader.jsp" />

			
	 <input type="hidden" id="msg" value="${param.msg}"/>
	<section id="body" class="width clear homeBody"> <jsp:include
		page="AdminLeftNav.jsp" /> <section id="content" class="column-right">
	<article>
	<table id="example" class="table table-striped table-bordered"
		cellspacing="0" width="100%">


		<thead>
			<tr>
				<th>User Name</th>

				<th>Project Name</th>
				<th>Tool Name</th>
				<th>Email</th>


				<th>Delete</th>
			</tr>

		</thead>
		<tbody>
			<c:forEach items="${sessionScope.viewEmpList}" var="emp">
				<tr>
					<td><a
						href="${pageContext.request.contextPath}/editemp/${emp.projID}/${emp.userID}/${emp.firstNAME}.qa">${emp.username}</a></td>

					<td><a
						href="${pageContext.request.contextPath}/editproj/${emp.projID}/${emp.userID}/${emp.firstNAME}.qa">${emp.projName}</a></td>
					<td>${emp.toolName}</td>
					<td>${emp.email}</td>

					<!--  <td><a href="${pageContext.request.contextPath}/editemp/${emp.projID}/${emp.userID}/${emp.firstNAME}.qa">Edit</a></td>-->
					<!-- href="${pageContext.request.contextPath}/deleteemp/${emp.projID}/${emp.userID}/${emp.toolID}.qa" -->
					<td><a
						href="#" onclick="return confirm_alert(this,${emp.projID},${emp.userID},${emp.toolID});">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>




	</table>
	<br />
	<a href="${pageContext.request.contextPath}/jsp/createUser.jsp">Add
		New User</a> </article> </section> </section>

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
