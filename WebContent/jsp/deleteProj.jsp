<%-- This is JSP comment --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QA Framework</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/assignProjects.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript">
	$(function() {
		function moveItems(origin, dest) {
			$(origin).find(':selected').appendTo(dest);
		}

		function moveAllItems(origin, dest) {
			$(origin).children().appendTo(dest);
		}

		$('#left').click(function() {
			moveItems('#sbTwo', '#sbOne');
		});

		$('#right').on('click', function() {
			moveItems('#sbOne', '#sbTwo');
		});

		$('#leftall').on('click', function() {
			moveAllItems('#sbTwo', '#sbOne');
		});

		$('#rightall').on('click', function() {
			moveAllItems('#sbOne', '#sbTwo');
		});
	});

	function selectStatus() {
		// alert("in method");
		$('select#sbOne > option').prop('selected', 'selected');
		$('select#sbTwo > option').prop('selected', 'selected');
	};
	$(document).ready(function() {
		$('#deleteProj').addClass("selected-item");
	});
</script>
<style>
.st label {
	color: #0d416b;
	font-weight: bold;
	font-size: 20px;
}

.st1 label {
	color: #0d416b;
	font-weight: bold;
	font-size: 20px;
	float: right;
	margin-right: 29%;
	margin-top: -35%;
}
</style>
</head>
<body>
	
			<jsp:include page="Adminheader.jsp" />
	<section id="body" class="width clear homeBody"> <jsp:include
		page="AdminLeftNav.jsp" /> <section id="content" class="column-right">
	<article>
	<form action="${pageContext.request.contextPath}/namesInProjStatus.qa"
		method="post">
		<div class="st">
			<label for="Active">Active: </label>
		</div>
		<select id="sbOne" name="sbOne" multiple="multiple" size=15
			style='height: 100%; width: 39%; margin-top: 10px;'>
			<c:forEach items="${sessionScope.list}" var="emp">
				<option>${emp.projName}</option>
			</c:forEach>
		</select>
		<div style="width: 0%; display: inline-block;">
			<input type="button" id="left" value="<-"
				style="margin-left: 60px; margin-bottom: 20px;" /> <input
				type="button" id="right" value="->"
				style="margin-left: 60px; margin-bottom: 20px;" /> <input
				type="button" id="leftall" value="<<-"
				style="margin-left: 60px; margin-bottom: 20px;" /> <input
				type="button" id="rightall" value="->>"
				style="margin-left: 60px; margin-bottom: 20px;" /><br>
			<br>
		</div>

		<div class="st1">
			<label for="InActive">InActive: </label>
		</div>
		<select id="sbTwo" name="sbTwo" multiple="multiple" size=15
			style='height: 100%; width: 39%; float: right; margin-top: -30%;'>
			<c:forEach items="${sessionScope.allList}" var="emp">
				<option>${emp.projName}</option>
			</c:forEach>
		</select> <input type="hidden" name="projid" value="${sessionScope.projID}" />
		<br />



		<div class="LoginButton"
			style="display: inline-block; margin-left: 18%;">
			<button type="submit" class="button submit" id="AssignUser"
				value="SAVE" onclick="selectStatus()">
				<span>SAVE</span>
			</button>

		</div>
		<div class="Logincancel"
			style="display: inline-block; margin-left: 36%;">
			<button type="cancel" class="button cancel">
				<span>CANCEL</span>
			</button>
		</div>
	</form>
	</article> </section> </section>

	<footer class="clear">
	<div class="width">
		<p class="left">
			Copyrights <i class="fa fa-copyright"></i> 2016.All Rights Reserved.
		</p>
		<p class="right">
			<a title="www.miraclesoft.com" href="http://www.miraclesoft.com"
				target="_blank"> Miracle Software Systems,Inc. </a>
		</p>
	</div>
	</footer>
</body>
</html>