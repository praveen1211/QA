<%-- This is JSP comment --%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="assets/header-search.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/history.css">
<link href='http://fonts.googleapis.com/css?family=Cookie'
	rel='stylesheet' type='text/css'>
<title>QA Framework</title>
</head>
<body class="submit_body">
	<div id="sitename">
		<div class="width">
			<h1>
				<img src="${pageContext.request.contextPath}/images/qa.png"
					style="height: 204px; margin-bottom: -44px; margin-left: 36px; margin-top: -48px; width: 246px;" />
				</a>
			</h1>
			<nav>
				<ul>
					<li class=""><a
						href="${pageContext.request.contextPath}/jsp/welcome.jsp">Home</a></li>
					<li class="start selected"><a
						href="${pageContext.request.contextPath}/project.qa">Projects</a></li>
					<li><a
						href="${pageContext.request.contextPath}/jsp/logoutprocess.jsp">Logout</a></li>

				</ul>
			</nav>


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
							href="${pageContext.request.contextPath}/jsp/welcome.jsp">Home</a></li>
						<li class=""><a
							href="${pageContext.request.contextPath}/project.qa">Projects</a></li>
						<li><a
							href="${pageContext.request.contextPath}/jsp/reports.jsp">Reports</a></li>
						<li class="selected-item"><a href="#">History</a></li>
						<li><a
							href="${pageContext.request.contextPath}/jsp/versionchanges.jsp">Version
								Changes</a></li>
					</ul>

				</li>
			</ul>
		</aside>
		<section id="content" class="column-right">
			<article>

				<h3>Page under Construction.</h3>
			</article>
		</section>
	</section>

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
