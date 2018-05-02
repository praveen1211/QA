<%-- This is JSP comment --%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>QA Framework</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/home.css" type="text/css" />
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css"
	rel="stylesheet" type="text/css">


<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
</head>

<body>

	
			<jsp:include page="Testerheader.jsp" />
	<header>
		<div class="jquery-script-center">


			<div class="jquery-script-clear"></div>

		</div>
		<div class="width">
			<h2>
				<%
					
				%>Welcome
				<%=" " + session.getAttribute("username") + ","%></h2>

			<div id="carousel">
				<div class="btn-bar" style="display: none;">
					<div id="buttons">
						<a id="prev" href="#"><</a><a id="next" href="#">></a>
					</div>
				</div>
				<div id="slides">
					<ul>
						<li class="slide">
							<div class="quoteContainer">
								<p class="quote-phrase">
									<span class="quote-marks">"</span>Miracle's QA framework assist
									auditors (testers) to perform automation, performance, security
									type of audits for web applications using standard frameworks
									available in IT industry.<span class="quote-marks">"</span>
								</p>
							</div>

						</li>
						<li class="slide">
							<div class="quoteContainer">
								<p class="quote-phrase">
									<span class="quote-marks">"</span>It is quality rather than
									quantity that counts <span class="quote-marks">"</span>
								</p>
							</div>
							<div class="authorContainer">
								<p class="quote-author">By Lucius Annaeus Seneca</p>
							</div>

						</li>
						<li class="slide">
							<div class="quoteContainer">
								<p class="quote-phrase">
									<span class="quote-marks">"</span> Give quality to client,
									that's best kind of advertising.<span class="quote-marks">"</span>
								</p>
							</div>
							<div class="authorContainer">
								<p class="quote-author">By Milton Hershey</p>
							</div>

						</li>
						<li class="slide">
							<div class="quoteContainer">
								<p class="quote-phrase">
									<span class="quote-marks">"</span>A code that cannot be tested
									is flawed.<span class="quote-marks">"</span>
								</p>
							</div>

						</li>
					</ul>
				</div>
			</div>
		</div>
	</header>
	<section id="body" class="width clear">
		<aside id="sidebar" class="column-left">
			<ul>
				<li>
					<h4></h4>
					<ul class="blocklist">
						<li class="selected-item"><a href="#">Home</a></li>

						<li><a href="${pageContext.request.contextPath}/project.qa">Projects</a></li>
						<li><a
							href="${pageContext.request.contextPath}/jsp/reports.jsp">Reports</a></li>
								<li><a	href="${pageContext.request.contextPath}/jsp/changePassword.jsp">Change password</a></li>
								<li id="TLAssRem"><a href="${pageContext.request.contextPath}/listofTL.qa">TL Assign/Remove Projects</a></li> 
								<li id="TLviewemp" ><a href="${pageContext.request.contextPath}/viewFunc.qa"></a>TL Resources</li>
						<!--  <li><a href="${pageContext.request.contextPath}/jsp/history.jsp">History</a></li>
                            <li><a href="${pageContext.request.contextPath}/jsp/versionchanges.jsp">Version Changes</a></li> -->
					</ul>

				</li>
			</ul>
		</aside>
		<section id="content" class="column-right">

			<article>


				<div class="modal-content">
					<div class="modal-header "
						style="background: rgba(0, 0, 0, 0) linear-gradient(#00aae7, #0d416b) repeat scroll 0 0;; border-top-right-radius: 0px; border-top-left-radius: 0px;">

						<h4 class="modal-title" id="myModalLabel" style="color: white">Recent
							Activity</h4>
					</div>
					<div class="modal-body">

						<p style="margin: 0;"></p>

						<%
							String repFolder = (String) session.getAttribute("RepFolder");
							String link;
						%>
						<h4>
							<%
								if (repFolder != null && repFolder != "") {
									link = repFolder;
							%>
							<a
								href="${pageContext.request.contextPath}/download.qa?selectedValue=<%=link%>"
								target="right"><%=link.substring(link.lastIndexOf("/") + 1)%>
							</a>
						</h4>
						<%
							} else {
								link = "Report not yet generated.";
						%>
						<%=link%>
						<%
							}
						%>

						<%
							response.addHeader(
									"Cache-Control",
									"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
							response.addHeader("Pragma", "no-cache");
							response.addDateHeader("Expires", 0);
						%>
					</div>
				</div>
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
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							//rotation speed and timer
							var speed = 10000;

							var run = setInterval(rotate, speed);
							var slides = $('.slide');
							var container = $('#slides ul');
							var elm = container.find(':first-child').prop(
									"tagName");
							var item_width = container.width();
							var previous = 'prev'; //id of previous button
							var next = 'next'; //id of next button
							slides.width(item_width); //set the slides to the correct pixel width
							container.parent().width(item_width);
							container.width(slides.length * item_width); //set the slides container to the correct total width
							container.find(elm + ':first').before(
									container.find(elm + ':last'));
							resetSlides();

							//if user clicked on prev button

							$('#buttons a')
									.click(
											function(e) {
												//slide the item

												if (container.is(':animated')) {
													return false;
												}
												if (e.target.id == previous) {
													container
															.stop()
															.animate(
																	{
																		'left' : 0
																	},
																	1500,
																	function() {
																		container
																				.find(
																						elm
																								+ ':first')
																				.before(
																						container
																								.find(elm
																										+ ':last'));
																		resetSlides();
																	});
												}

												if (e.target.id == next) {
													container
															.stop()
															.animate(
																	{
																		'left' : item_width
																				* -2
																	},
																	1500,
																	function() {
																		container
																				.find(
																						elm
																								+ ':last')
																				.after(
																						container
																								.find(elm
																										+ ':first'));
																		resetSlides();
																	});
												}

												//cancel the link behavior            
												return false;

											});

							//if mouse hover, pause the auto rotation, otherwise rotate it    
							container.parent().mouseenter(function() {
								clearInterval(run);
							}).mouseleave(function() {
								run = setInterval(rotate, speed);
							});

							function resetSlides() {
								//and adjust the container so current is in the frame
								container.css({
									'left' : -1 * item_width
								});
							}

						});
		//a simple function to click next link
		//a timer will call this function, and the rotation will begin

		function rotate() {
			$('#next').click();
		}
	</script>
	<script type="text/javascript">
		var _gaq = _gaq || [];
		_gaq.push([ '_setAccount', 'UA-36251023-1' ]);
		_gaq.push([ '_setDomainName', 'jqueryscript.net' ]);
		_gaq.push([ '_trackPageview' ]);

		(function() {
			var ga = document.createElement('script');
			ga.type = 'text/javascript';
			ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl'
					: 'http://www')
					+ '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(ga, s);
		})();
	</script>

</body>
</html>
