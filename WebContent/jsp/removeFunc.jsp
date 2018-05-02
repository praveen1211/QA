<%-- This is JSP comment --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/assignProjects.css">
<title> QA Framework</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/assignProjects.css">
<script type="text/javascript">
 $(function () {
            function moveItems(origin, dest) {
                $(origin).find(':selected').appendTo(dest);
            }

            function moveAllItems(origin, dest) {
                $(origin).children().appendTo(dest);
            }
           /* function funct() {
		var option = document.getElementById("assignProj").value;

		alert(option);
	}*/
});
           
</script>

</head>
<body>
<div id="sitename">
			<div class="width">
				<h1><a href="#"><img src="${pageContext.request.contextPath}/images/qa.png" style="height: 204px;
    margin-bottom: -44px;
   margin-left: 36px;
    margin-top: -48px;
    width: 246px;"/> </a></h1>
<jsp:include page="Adminheader.jsp" />
	
				<div class="clear"></div>
			</div>
		</div>
		<section id="body" class="width clear">
			<aside id="sidebar" class="column-left">
				<ul>
                	<li>
						<h4></h4>
                        <ul class="blocklist">
                            <li ><a href="${pageContext.request.contextPath}/jsp/viewemp.jsp">Home</a></li>
                
                           <li class=""><a href="${pageContext.request.contextPath}/jsp/createUser.jsp">Create User</a></li>
                           <li class=""><a href="${pageContext.request.contextPath}/jsp/createProject.jsp">Create Project</a></li>
                            <li class="selected-item"><a href="${pageContext.request.contextPath}/jsp/assignProjects.jsp">Assign/Remove Projects</a></li>
                        </ul>

					</li>	
				</ul>
			</aside>
			<section  id="content" class="column-right">
			  <article>
			
			  
<form action="${pageContext.request.contextPath}/namesRemoveInSelectBox.qa" method="post"  autocomplete="off">
			
    <select id="sbOne" name="sbOne" multiple="multiple" >
    
        <c:forEach items="${sessionScope.list}" var="emp">
				<option >${emp.firstNAME}</option> 
				</c:forEach>
    </select>
      <c:forEach items="${sessionScope.list}" var="emp">
				<input type="hidden" name="${emp.firstNAME}" value="${emp.userID}"/> 
				</c:forEach>
 
 		<input type="hidden" name="projid" value="${sessionScope.projID}"/>
    <br />
 <div class="LoginButton">
               <button type="submit" class="button submit" id="AssignUser" value="SAVE" ><span>SAVE</span></button>
														
                                                    </div>
													<div class="Logincancel">
													<button type="cancel" class="button cancel"><span>CANCEL</span></button>
													</div>
</form>
</article>
 </section>
			    </section>
			    
		<footer class="clear">
			<div  class="width">
				<p class="left"> Copyrights <i class="fa fa-copyright"></i> 2017.All Rights Reserved.  </p>
				<p class="right"><a title="www.miraclesoft.com" href="http://www.miraclesoft.com" target="_blank">  Miracle Software Systems,Inc. </a></p>
			</div>
		</footer>  
