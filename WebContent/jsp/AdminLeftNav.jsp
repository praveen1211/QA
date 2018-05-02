<%-- This is JSP comment --%>
<aside id="sidebar" class="column-left">
				<ul>
                	<li>
						<h4></h4>
                        <ul class="blocklist">
                            <li id="Home" ><a href="${pageContext.request.contextPath}/viewFunc.qa">Home</a></li>
                
                           <li id="creUser" class=""><a href="${pageContext.request.contextPath}/jsp/createUser.jsp">Create User</a></li>
                           <li id="creProject"><a href="${pageContext.request.contextPath}/jsp/createProject.jsp">Create Project</a></li>
                           <li id="AssRem"><a href="${pageContext.request.contextPath}/assignFunc.qa">Assign/Remove Projects</a></li>
                        <li id="deleteProj"><a href="${pageContext.request.contextPath}/projStatus.qa">Manage Projects</a></li>
                           <li id="unassignUser"><a href="${pageContext.request.contextPath}/unassignedUsers.qa">Unassigned Users</a></li>  
                         
                      
                        </ul>

					</li>	
				</ul>
			</aside>