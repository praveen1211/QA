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
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap-select.min.js"></script>
<script>
	$(document).ready(function() {
		$('.selectpicker').selectpicker();
	});

	$(document).ready(function() {
		var msg = $('#noData').val();
		if (msg == 'noData') {
			alert("No users are assigned to this Project");
		}
	});
</script>
<script type="text/javascript">
	function funct() {
		var option = document.getElementById("assignProj").value;

		//alert(option);
	}
</script>


<script>
	function change1() {
		// alert("here");
		//alert(document.getElementById("assignProj").value);
		//var option = document.getElementById("assignProj").value;

		//alert(option);
		// alert(document.getElementById("assignRemove").value);
		var k = document.getElementById("assignProj").value;
		var g = document.getElementById("assignRemove").value;

		//document.getElementById("assignRemove").action="${pageContext.request.contextPath}/getUserName.qa/"+k+"/"+g;
		//alert("${pageContext.request.contextPath}/getUserName.qa/"+k+"/"+g);

		window.location = "${pageContext.request.contextPath}/getUserName/" + k
				+ "/" + g + ".qa";
	}

	function Validate() {
		var f = document.getElementById("sbOne");
		if (f.value == "") {
			alert("Please select a user");
			return false;
		}
		return true;
	}

	function ValidateTwo() {

		var e = document.getElementById("sbTwo");
		if (e.value == "") {
			alert("Please select a user");
			return false;
		}
		return true;
	}

	$(document).ready(function() {
		$('#AssRem').addClass("selected-item");
	});
</script>
<style>
#myform .btn-group.bootstrap-select {
	margin-top: 0;
}
</style>

</head>
<body>


			<jsp:include page="Adminheader.jsp" />
	<section id="body" class="width clear homeBody"> <jsp:include
		page="AdminLeftNav.jsp" /> <section id="content" class="column-right">
	<article>

	<form method="post" id="myform" autocomplete="off">
		<div class="text" style="display: inline-block;">
			<p>Select Testing Projects</p>
	<input type="hidden" name="msg" value="${message}" id="noData" />
			<c:set var="msg" value="${message}"></c:set>
			<c:set var="msg" value="${message}"></c:set>
			<c:if test="${(msg ==null)||(msg =='noData')}">



				<select id="assignProj" class="selectpicker" name="assignProj"
					data-live-search="true" data-show-subtext="true">
					<option>Select Projects</option>
					<c:forEach items="${sessionScope.AssignProjList}"
						var="AssignProjListValue">
						<option>${AssignProjListValue}</option>
					</c:forEach>

				</select>
		</div>

		<div class="text" style="display: inline-block; margin-left: 8%;">
			<p>Select to Assign or Remove</p>
			<select id="assignRemove" class="selectpicker" name="assignRemove"
				onchange="change1()">
				<option>Select</option>
				<option value="ASSIGN">ASSIGN</option>
				<option value="REMOVE">REMOVE</option>

			</select>
		</div>
		</c:if>

		<c:if test="${msg=='assign1'}">





			<select id="assignProj" class="selectpicker" name="assignProj"
				onchange="funct()" disabled="disabled">

				<option value="${projSelected}">${projSelected}</option>


			</select>
			</div>
			<div class="text" style="display: inline-block; margin-left: 8%;">
				<p>Select to Assign or Remove</p>

				<select id="assignOption" class="selectpicker" name="assignOption"
					onchange="funct()" disabled="disabled">

					<option value="${assignRemoveOption}">${assignRemoveOption}</option>


				</select>
			</div>
			<c:forEach items="${sessionScope.allList}" var="emp" end="0">
			<input type="hidden" name="toolname" value="${emp.toolName}" id="toolname" />
			</c:forEach>
			<div class="text" style="display: inline-block; margin-left: 8%;">
				<p>Select User Name</p>
				<select id="sbTwo" name="sbTwo" class="selectpicker"
					multiple="multiple" data-live-search="true">
					<c:forEach items="${sessionScope.allList}" var="emp">
						<option>${emp.username}</option>
						<!-- usernames for assigning projects -->
					</c:forEach>
				</select>
			</div>

			<c:forEach items="${sessionScope.allList}" var="emp">
				<input type="hidden" name="${emp.username}" value="${emp.userID}" />

			</c:forEach>


			<input type="hidden" name="projid" value="${sessionScope.projID}" />
			<br />
			<div class="LoginButton">
				<button type="submit" class="button submit" id="AssignUser"
					value="SAVE" onclick="return ValidateTwo()"
					formaction="${pageContext.request.contextPath}/namesInSelectBox.qa">
					<span>SAVE</span>
				</button>

			</div>
			<div class="Logincancel">
				<button type="submit"
					formaction="${pageContext.request.contextPath}/resetFunc.qa">
					<span>CANCEL</span>
				</button>
			</div>

		</c:if>
		<c:if test="${msg=='other'}">
			<button>other</button>
		</c:if>
		<c:if test="${msg=='remove'}">



			<select id="assignProj" name="assignProj" class="selectpicker"
				onchange="funct()" disabled="disabled">
				<option value="${projSelected}">${projSelected}</option>

			</select>
			</div>
			<div class="text" style="display: inline-block; margin-left: 8%;">
				<p>Select to Assign or Remove</p>

				<select id="assignOption" name="assignOption" class="selectpicker"
					onchange="funct()" disabled="disabled">

					<option value="${assignRemoveOption}">${assignRemoveOption}</option>


				</select>
			</div>
			<div class="text" style="display: inline-block; margin-left: 8%;">
				<p>Select User Name</p>
				<select id="sbOne" class="selectpicker" name="sbOne"
					multiple="multiple" data-live-search="true">

					<c:forEach items="${sessionScope.list}" var="emp">
						<option>${emp.username}</option>
					</c:forEach>
				</select>
				<c:forEach items="${sessionScope.list}" var="emp">
					<input type="hidden" name="${emp.username}" value="${emp.userID}" />
				</c:forEach>
				<input type="hidden" name="projid" value="${sessionScope.projID}" />
			</div>
			<br />

			<div class="LoginButton">
				<button type="submit" class="button submit" id="AssignUser"
					value="SAVE" onclick="return Validate()"
					formaction="${pageContext.request.contextPath}/namesRemoveInSelectBox.qa">
					<span>SAVE</span>
				</button>

			</div>
			<div class="Logincancel">
				<button type="submit"
					formaction="${pageContext.request.contextPath}/resetFunc.qa">
					<span>CANCEL</span>
				</button>
				<!--<input type="button" value="cancel" formaction="${pageContext.request.contextPath}/resetFunc.qa" >-->

			</div>
		</c:if>
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