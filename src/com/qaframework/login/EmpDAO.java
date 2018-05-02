/**
     * *************************************
     *
     * @save() method is used to save the data and redirect to viewemp.qa().
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

package com.qaframework.login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.mss.utils.MailManager;
//import com.mysql.jdbc.Statement;
import com.qaframework.login.Emp;

public class EmpDAO {
	
	private static final String SMTP_AUTH_USER = "ksonti@miraclesoft.com";
	private static final String SMTP_AUTH_PWD = "Sidhu2806@";
	JdbcTemplate template;
	myData obj=new myData();
	protected static final Logger logger = Logger.getLogger(EmpDAO.class);
	public String username="";
	public String password="";
	public static String result="No";
	public String firstName="";
	public String lastName="";
	public int userid=0;
	public String projName="";
	public int roleid=0;
	public String email="";
	public myData getObj() {
		return obj;
	}

	public void setObj(myData obj) {
		this.obj = obj;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static String getResult() {
		return result;
	}

	public static void setResult(String result) {
		EmpDAO.result = result;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public JdbcTemplate getTemplate() {
		return template;
	}

	public static Logger getLogger() {
		return logger;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	/**
     * *************************************
     *
     * @save() method is used to save the data and redirect to viewemp.qa().
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int save(Emp p) {
		String sql = "insert into projects(Proj_Name) values('"
				+ p.getProjName() + "')";
		System.out.println("he155");
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @update() method updates the user table.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int update(Emp p, int uID) {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";
		String sql = "UPDATE users SET first_name='" + p.getFirstNAME()
				+ "',last_name='" + p.getLastNAME() + "', email='"
				+ p.getEmail() + "',phone='" + p.getPhone()
				+ "' WHERE userid='" + uID + "' ";
		System.out.println("he1895");
		System.out.println("fname=" + p.getFirstNAME());
		System.out.println("lname=" + p.getLastNAME());
		System.out.println("email=" + p.getEmail());
		System.out.println("phone=" + p.getPhone());
		System.out.println("userid=" + uID);
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @updateUserRole() method updates the user role in user table based on email..
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int updateUserRole(Emp p, String email,String name) throws Exception {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";
		String sql ="UPDATE users SET role_id='"+p.getUserRole()+"' WHERE email='"+email+"'";				
			
		System.out.println("update userrole");
		String Userrole=p.getUserRole();
		System.out.println("role_id=" + p.getUserRole());
		System.out.println("email=" + email);
		System.out.println("name=" + name);
		if(Userrole.equals("0"))
		{
		MailManager sender = new MailManager(SMTP_AUTH_USER, SMTP_AUTH_PWD);
		
		sender.senduserRoleMail("QAFramework services", "",
				"QAFramework@miraclesoft.com", email, Userrole, name);
		}
		return template.update(sql);
	}
	
	public int updateUserRole1(int userid) throws Exception {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";
		String sql ="DELETE lookup_users.* FROM lookup_users WHERE lookup_users.User_Id='"+userid+"'";				
			
		
		
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @updateProj() method updates project table to set the project name based on project id.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int updateProj(Emp p, int pID) {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";

		String sql = "UPDATE projects SET Proj_Name='" + p.getProjName()
				+ "' WHERE Proj_Id='" + pID + "' ";
		System.out.println("he1895");
		System.out.println("projName=" + p.getProjName());
		System.out.println("userid=" + pID);
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @updateTool() method updates project table to set the project name based on project id.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int updateTool(Emp p, String toolName) {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";

		String sql = "UPDATE lookup_tools SET tool_name='" + p.getToolName()
				+ "' WHERE tool_name='" + toolName + "' ";
		System.out.println("he1895");
		System.out.println("projName=" + p.getToolName());
		System.out.println("userid=" + toolName);
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @updateProj1() method updates createProject table based on project id.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int updateProj1(Emp p, int pID) {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";

		String sql = "UPDATE createproj SET Proj_Name='" + p.getProjName()
				+ "' , Testing_Tool='" + p.getToolName() + "' , Created_Date='"
				+ p.getCreateDate() + "' , End_Date='" + p.getCompleteDate()
				+ "' , Description='" + p.getDescription()
				+ "' WHERE Proj_Id='" + pID + "' ";
		System.out.println("he1895");
		System.out.println("projName=" + p.getProjName());
		System.out.println("userid=" + pID);
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @delete() method updates lookup_users table to set status as Inactive based on userID.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int delete(int projID, int userID, int toolID) {
		// String
		// sql="DELETE users.*,lookup_users.* FROM lookup_users LEFT JOIN users ON (users.userid=lookup_users.User_Id) WHERE Proj_Id="+projID+" AND User_Id="+userID+" AND Tool_Id="+toolID+"";
		String sql = "DELETE lookup_users.* from  lookup_users WHERE User_Id IN("
				+ userID
				+ ") AND Proj_Id IN ("
				+ projID
				+ ") AND Tool_Id IN("
				+ toolID + ")";
		/*String sql = "UPDATE lookup_users SET lookup_users.LookupUser_Status='Inactive' WHERE User_Id IN("
				+ userID
				+ ") AND Proj_Id IN ("
				+ projID
				+ ") AND Tool_Id IN("
				+ toolID + ")";*/
		System.out.println("he14556");
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @getEmpById() method gets user details based on given project id and first name.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public Emp getEmpById(int projID, String firstNAME) {
		System.out.println("project id=" + projID);

		String sql = "SELECT u.first_name,u.last_name,u.email,u.phone,u.password,u.role_id,p.Proj_Id,p.Proj_Name,t.tool_name FROM users u LEFT OUTER JOIN lookup_users lu ON(lu.User_Id=u.userid) LEFT OUTER JOIN projects p ON(p.Proj_Id=lu.Proj_Id) LEFT OUTER JOIN lookup_tools t ON(t.Tool_Id=lu.Tool_Id) WHERE p.Proj_Id=? AND u.first_name=?";
		System.out.println("get emp by iD details:=" + sql);
		System.out.println("user name=" + firstNAME);
		return template.queryForObject(sql, new Object[] { projID, firstNAME },
				new RowMapper<Emp>() {
					public Emp mapRow(ResultSet rs, int row)
							throws SQLException {

						Emp e = new Emp();
						e.setFirstNAME(rs.getString("first_name"));
						e.setProjID(rs.getInt("Proj_Id"));
						e.setProjName(rs.getString("Proj_Name"));
						e.setToolName(rs.getString("tool_name"));
						e.setLastNAME(rs.getString("last_name"));
						e.setPassword(rs.getString("password"));
						e.setUserRole(rs.getString("role_id"));
						e.setEmail(rs.getString("email"));
						e.setPhone(rs.getString("phone"));

						System.out.println("lastname="
								+ rs.getString("last_name"));
						System.out.println("pwd=" + rs.getString("password"));
						System.out.println("roleid=" + rs.getString("role_id"));

						// System.out.println("got into List<Emp> getEmpById"+new
						// Object[]{projID});
						return e;

					}
				});
	}
	/**
     * *************************************
     *
     * @getProjById() method gets the project details based on the given project id..
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public Emp getProjById(int projID) {
		// System.out.println("project id="+projID);

		String sql = "SELECT c.Proj_Name,c.Testing_Tool,c.Created_Date,c.End_Date,c.Description,c.Proj_Id FROM createproj c LEFT OUTER JOIN projects p ON(p.Proj_Id=c.Proj_Id) WHERE p.Proj_Id=?    ";
		System.out.println("get emp by iD details:=" + sql);
		// System.out.println("user name="+firstNAME);
		return template.queryForObject(sql, new Object[] { projID },
				new RowMapper<Emp>() {
					public Emp mapRow(ResultSet rs, int row)
							throws SQLException {

						Emp e = new Emp();
						// e.setFirstNAME(rs.getString("first_name"));
						e.setProjID(rs.getInt("Proj_Id"));
						e.setProjName(rs.getString("Proj_Name"));
						e.setToolName(rs.getString("Testing_Tool"));
						// e.setLastNAME(rs.getString("last_name"));
						// e.setPassword(rs.getString("password"));
						// e.setUserRole(rs.getString("role_id"));
						// e.setEmail(rs.getString("email"));
						// e.setPhone(rs.getString("phone"));
						e.setCreateDate(rs.getString("Created_Date"));
						e.setCompleteDate(rs.getString("End_Date"));
						e.setDescription(rs.getString("Description"));

						// System.out.println("lastname="+rs.getString("last_name"));
						// System.out.println("password="+rs.getString("password"));
						// System.out.println("role id="+rs.getString("role_id"));
						System.out.println("role id="
								+ rs.getString("Created_Date"));
						System.out.println("role id="
								+ rs.getString("End_Date"));
						System.out.println("role id="
								+ rs.getString("Description"));

						// System.out.println("got into List<Emp> getEmpById"+new
						// Object[]{projID});
						return e;

					}
				});
	}
	/**
     * *************************************
     *
     * @getEmployees() method gets the user details.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public List<Emp> getEmployees() {
		String sql = "SELECT u.first_name,u.last_name,u.email,p.Proj_Id,p.Proj_Name,t.tool_name,lu.User_Id,lu.Tool_Id FROM users u LEFT OUTER JOIN lookup_users lu ON (lu.User_Id=u.userid) LEFT OUTER JOIN projects p ON(p.Proj_Id=lu.Proj_Id) LEFT OUTER JOIN lookup_tools t ON(t.Tool_Id=lu.Tool_Id) WHERE lu.User_Id!='' AND lu.LookupUser_Status='Active'  AND p.Proj_Status='Active' and u.role_id=1";
		// System.out.println("template value in List<Emp>:="+sql);
		return template.query(sql, new RowMapper<Emp>() {
			public Emp mapRow(ResultSet rs, int row) throws SQLException {

				Emp e = new Emp();
				e.setFirstNAME(rs.getString("first_name"));
				e.setLastNAME(rs.getString("last_name"));
				e.setProjName(rs.getString("Proj_Name"));
				e.setToolName(rs.getString("tool_name"));
				e.setEmail(rs.getString("email"));
				e.setProjID(rs.getInt("Proj_Id"));
				e.setToolID(rs.getInt("Tool_Id"));
				e.setUserID(rs.getInt("User_Id"));
				e.setUsername(rs.getString("first_name") + " "
						+ rs.getString("last_name"));

				System.out.println("got into List<Emp>-projID"
						+ rs.getInt("Proj_Id"));
				System.out.println("got into List<Emp>-projName"
						+ rs.getString("Proj_Name"));
				System.out.println("got into List<Emp>-email"
						+ rs.getString("email"));
				System.out.println("got into List<Emp>-full name"
						+ rs.getString("first_name") + " "
						+ rs.getString("last_name"));
				return e;

			}
		});
	}
	/**
     * *************************************
     *
     * @getUserName() method gets the details of all users who are assigned to the given project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public List<Emp> getUserName(final String projSelected) {
		//String proj; // for removing projects
		// if (proj==projSelected)

		String sql = "SELECT u.first_name,u.last_name,lu.User_Id,lu.Proj_Id FROM users u INNER JOIN lookup_users lu ON (lu.User_Id=u.userid) INNER JOIN projects p ON(p.Proj_Id=lu.Proj_Id) WHERE p.Proj_Name=?  AND lu.LookupUser_Status='Active' and u.role_id=1";
		// String
		// sql="SELECT u.first_name,u.last_name,lu.User_Id,lu.Proj_Id FROM users u LEFT OUTER JOIN lookup_users lu ON (lu.User_Id=u.userid) LEFT OUTER JOIN projects p ON(p.Proj_Id=lu.Proj_Id) WHERE p.Proj_Name!=? GROUP BY lu.User_Id";
		// System.out.println("template value in List<Emp>:="+sql);
		// String
		// sql="SELECT u.userid,u.first_name FROM users u LEFT OUTER JOIN projects p  ON p.Proj_Name !='?' GROUP BY u.userid";
		return template.query(sql, new Object[] { projSelected },
				new RowMapper<Emp>() {
					public Emp mapRow(ResultSet rs, int row)
							throws SQLException {

						Emp e = new Emp();
						e.setFirstNAME(rs.getString("first_name"));
						e.setUserID(rs.getInt("User_Id"));
						e.setProjID(rs.getInt("Proj_Id"));
						e.setUsername(rs.getString("first_name") + " "
								+ rs.getString("last_name"));
						System.out.println("got into List<Emp>"
								+ rs.getString("first_name"));
						System.out.println("got into List<Emp>"
								+ rs.getInt("User_Id"));
						System.out.println("got into List<Emp>"
								+ rs.getInt("Proj_Id"));
						System.out.println("got into List<Emp>-userid"
								+ rs.getString("first_name")
								+ rs.getString("last_name"));
						System.out.println("after got into List<Emp>"
								+ projSelected);
						return e;

					}
				});
	}
	/**
     * *************************************
     *
     * @getAllUserName() method gets the details of all users except the users assigned to given project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public List<Emp> getAllUserName(final String projSelected) { // for
																	// assigning
																	// projects
		// final String
		// sql="SELECT u.first_name,lu.User_Id,lu.Proj_Id FROM users u INNER JOIN lookup_users lu ON (lu.User_Id=u.userid) INNER JOIN projects p ON(p.Proj_Id=lu.Proj_Id) WHERE p.Proj_Name!=?";
		System.out.println("template value in List<Emp>");
//		final String sql = "SELECT u.userid,u.first_name,u.last_name FROM users u LEFT OUTER JOIN projects p  ON p.Proj_Name !=? WHERE u.role_id='1' GROUP BY u.userid";
		final String sql = "SELECT u.first_name as first_name,u.last_name as last_name,u.userid as userid,cp.Testing_Tool as Testing_Tool FROM users u,createproj cp WHERE u.role_id IN (1,2) AND  userid NOT IN(SELECT u.userid FROM users u LEFT OUTER JOIN lookup_users lu ON (lu.User_Id=u.userid) LEFT OUTER JOIN projects p ON (p.Proj_Id=lu.Proj_Id) WHERE lu.LookupUser_Status='Active' AND lu.Proj_Id=p.Proj_Id AND p.Proj_Name=?) AND cp.Proj_Name=? ";
		// final String
		// sql="SELECT u.first_name,u.last_name,lu.User_Id,lu.Proj_Id FROM users u LEFT OUTER JOIN lookup_users lu ON (lu.User_Id=u.userid) LEFT OUTER JOIN projects p ON(p.Proj_Id=lu.Proj_Id) WHERE p.Proj_Name!=? GROUP BY lu.User_Id";
		return template.query(sql, new Object[] { projSelected ,projSelected},
				new RowMapper<Emp>() {
					public Emp mapRow(ResultSet rs, int row)
							throws SQLException {

						Emp e = new Emp();
						e.setFirstNAME(rs.getString("first_name"));
						e.setLastNAME(rs.getString("last_name"));
						// e.setUserID(rs.getInt("User_Id"));
						e.setUserID(rs.getInt("userid"));
						e.setUsername(rs.getString("first_name") + " "
								+ rs.getString("last_name"));
						e.setToolName(rs.getString("Testing_Tool"));
						// e.setProjID(rs.getInt("Proj_Id"));
						System.out.println("got into List<Emp>-first name"
								+ rs.getString("first_name"));
						System.out.println("got into List<Emp>last name"
								+ rs.getString("last_name"));
						System.out.println("got into List<Emp>-User_Id"
								+ rs.getInt("userid"));
						System.out.println("got into List<Emp>-Username"
								+ rs.getString("first_name")
								+ rs.getString("last_name"));
						// System.out.println("got into List<Emp>"+rs.getInt("Proj_Id"));
						System.out
								.println("after got into List<Emp>-proj selected"
										+ projSelected);
						System.out.println("query 2nd" + sql);
						return e;

					}
				});
	}
	
	public int deleteUnassignedUsers( int userID) {
		// String
		// sql="DELETE users.*,lookup_users.* FROM lookup_users LEFT JOIN users ON (users.userid=lookup_users.User_Id) WHERE Proj_Id="+projID+" AND User_Id="+userID+" AND Tool_Id="+toolID+"";
		String sql = "DELETE users.* FROM users WHERE userid="+userID+"";

		/*String sql = "UPDATE lookup_users SET lookup_users.LookupUser_Status='Inactive' WHERE User_Id IN("
				+ userID
				+ ") AND Proj_Id IN ("
				+ projID
				+ ") AND Tool_Id IN("
				+ toolID + ")";*/
		System.out.println("he14556");
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @getProjID() method gets the project id from projects table based on project name.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public List<Emp> getProjID(final String projSelected) {
		final String sql = "SELECT Proj_Id FROM projects WHERE Proj_Name=?";
		// System.out.println("template value in List<Emp>:="+sql);

		return template.query(sql, new Object[] { projSelected },
				new RowMapper<Emp>() {
					public Emp mapRow(ResultSet rs, int row)
							throws SQLException {

						Emp e = new Emp();
						// e.setFirstNAME(rs.getString("first_name"));
						// e.setUserID(rs.getInt("User_Id"));
						e.setProjID(rs.getInt("Proj_Id"));

						System.out.println("got into List<Emp>"
								+ rs.getInt("Proj_Id"));
						System.out.println("after got into List<Emp>"
								+ projSelected);
						System.out.println("query 2nd" + sql);
						return e;

					}
				});
	}
	/**
     * *************************************
     *
     * @getProjStatusActive() method gets project name and their status for all active projects from projects table.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public List<Emp> getProjStatusActive() {

		// String
		// sql="SELECT u.first_name,lu.User_Id,lu.Proj_Id FROM users u INNER JOIN lookup_users lu ON (lu.User_Id=u.userid) INNER JOIN projects p ON(p.Proj_Id=lu.Proj_Id) WHERE p.Proj_Name=?";
		String sql = "SELECT Proj_Name,Proj_Status FROM projects WHERE Proj_Status='Active'";

		return template.query(sql, new RowMapper<Emp>() {
			public Emp mapRow(ResultSet rs, int row) throws SQLException {

				System.out.println("got into List<Emp> Active");
				Emp e = new Emp();
				// e.setFirstNAME(rs.getString("first_name"));
				// e.setUserID(rs.getInt("User_Id"));
				// e.setProjID(rs.getInt("Proj_Id"));
				e.setProjName(rs.getString("Proj_Name"));
				e.setProjectStatus(rs.getString("Proj_Status"));
				System.out.println("got into List<Emp>-project name"
						+ rs.getString("Proj_Name"));
				System.out.println("got into List<Emp>- project status"
						+ rs.getString("Proj_Status"));

				return e;

			}
		});
	}
	/**
     * *************************************
     *
     * @getProjStatusInactive() method gets project name and their status for all inactive projects from projects table.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public List<Emp> getProjStatusInactive() {
         
		// myData obj=new myData();
		// String
		// sql="SELECT u.first_name,lu.User_Id,lu.Proj_Id FROM users u INNER JOIN lookup_users lu ON (lu.User_Id=u.userid) INNER JOIN projects p ON(p.Proj_Id=lu.Proj_Id) WHERE p.Proj_Name=?";
		String sql = "SELECT Proj_Name,Proj_Status FROM projects WHERE Proj_Status='Inactive'";

		return template.query(sql, new RowMapper<Emp>() {
			public Emp mapRow(ResultSet rs, int row) throws SQLException {

				System.out.println("got into List<Emp> Inactive");
				Emp e = new Emp();
				// e.setFirstNAME(rs.getString("first_name"));
				// e.setUserID(rs.getInt("User_Id"));
				// e.setProjID(rs.getInt("Proj_Id"));
				e.setProjName(rs.getString("Proj_Name"));
				e.setProjectStatus(rs.getString("Proj_Status"));
				System.out.println("got into List<Emp>"
						+ rs.getString("Proj_Name"));
				System.out.println("got into List<Emp>"
						+ rs.getString("Proj_Status"));

				return e;

			}
		});
	}
	
	/**
     * *************************************
     *
     * @createUser() method is used to create user.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int createUser(String firstName,String lastName,String passwrd,String email,String phone,int roleId,String userName) {
		String sql = "insert into users(first_name,last_name,password,email,phone,role_id,username,flag) values('"
				+ firstName
				+ "','"
				+ lastName
				+ "','"
				+ passwrd
				+ "','"
				+ email
				+ "','"
				+ phone
				+ "',"
				+ roleId
				+ ",'"
				+ userName
				+ "','0')";
		System.out.println("he155");
		return template.update(sql);
	}
	

	/**
     * *************************************
     *
     * @createProj() method creates the project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public String createProj(String projectName,String  Proj_Status,String testingToolName,String createdDate,String completedDate,String projDescripton)
{
		String results=null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(template).withProcedureName("spCreateProject");

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("frmProjName", projectName);
		inParamMap.put("frmProjStatus", Proj_Status);
		inParamMap.put("frmToolName", testingToolName);
		inParamMap.put("frmCreateDate", createdDate);
		inParamMap.put("frmEndDate", completedDate);
		inParamMap.put("frmProjDesc", projDescripton);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);


		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		Iterator<Entry<String, Object>> it = simpleJdbcCallResult.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
	        String key = (String) entry.getKey();
	        Object value = (Object) entry.getValue();
	        results=value.toString();
	        System.out.println("Key: "+key);
	        System.out.println("Value: "+value);
	    }
	/*	
Connection connection = null;
	//int i = 0;
	//myData obj=new myData();
	String results=null;
	CallableStatement callableStatement=null;
	try {
		connection = obj.getDBConnection();
		callableStatement =  connection.prepareCall("{call spCreateProject (?,?,?,?,?,?,?)}");
		callableStatement.setString(1,projectName );
		callableStatement.setString(2,Proj_Status );
		callableStatement.setString(3,testingToolName );
		callableStatement.setString(4,createdDate );
		callableStatement.setString(5,completedDate );
		callableStatement.setString(6,projDescripton );
		callableStatement.registerOutParameter(7,Types.VARCHAR);
		callableStatement.executeUpdate();
		results=callableStatement.getString(7);

	} catch (Exception e) {
		System.out.print(e);
		e.printStackTrace();
	}
	finally{
        try{
            
            if(callableStatement!=null){
                callableStatement.close();
                callableStatement = null;
            }
            
            if(connection != null){
                connection.close();
                connection = null;
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
    }*/
	return results;
}

	/**
     * *************************************
     *
     * @insertLookUP() method is used to insert lookup user.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int insertLookUP(int UserId,int ProjId, int toolid) {
		String sql = "insert into lookup_users(User_Id,Proj_Id,Tool_Id,LookupUser_Status) values("
				+ UserId + "," + ProjId + ","+toolid+",'Active')";
		System.out.println("he155");
		return template.update(sql);
	}
	
	public int TLinsertLookUP(int UserId,int ProjId, int toolid) {
		String sql = "insert into TLlookup_users(User_Id,Proj_Id,Tool_Id,LookupUser_Status) values("
				+ UserId + "," + ProjId + ","+toolid+",'Active')";
		System.out.println("he155");
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @gettoolid().
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int gettoolid(Emp p,String toolname) {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";
		//int tool_id =0;
		String sql = "SELECT tool_id FROM lookup_tools WHERE tool_name='"+toolname+"' ";
		System.out.println("he1895tool");
		System.out.println("toolid="+p.getToolID());
		System.out.println("toolname=" + toolname);
		return template.queryForInt(sql);
		
	}
	/*public int gettoolid(String toolname) {
		
		
		Connection connection = null;
		int toolid =0;
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		connection = obj.getDBConnection();
		try {
			
			String query="SELECT tool_id FROM lookup_tools WHERE tool_name='"+toolname+"'";
			logger.info(">>>>>>>>>"+query);
			System.out.println("testDataSource query");
			preparedStatement = connection.prepareStatement(query);
			System.out.println("testDataSource statement");
			resultset = preparedStatement.executeQuery();
			
			   System.out.println("testDataSource resultset");
								
			 if(resultset.next()){
				
				
					
				 //data.setCount(1);
			     toolid = resultset.getInt("tool_id");
		         
				
		             logger.info("toolid:"+toolid);
	             System.out.println("email used is="+toolid);
	             
	             
	          //   System.out.println("roleid="+getRoleid());
	             //System.out.println("roleid =="+roleID);
			}
			else
			{
				//roleID=2;
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(resultset != null)
						{
						resultset.close();
						resultset = null;
						}
					if(preparedStatement != null) 
						{
						preparedStatement.close();
						preparedStatement = null;
						}
					if(connection != null) 
						{
						connection.close();
						connection = null;
						}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return toolid;
	}*/
	
	/**
     * *************************************
     *
     * @deleteLookUP() method is used to delete lookup user.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int deleteLookUP(int UserId,int ProjId) {
		String sql = "DELETE lookup_users.* from lookup_users WHERE User_Id IN("
				+ UserId + ") AND Proj_Id IN (" + ProjId + ") ";
		System.out.println("he155");
		return template.update(sql);
	}
	
	
	/**
     * *************************************
     *
     * @userAvailability() method check user availability.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	/*public ResultSet userAvailability(String email)
{
Connection connection = null;
	//int i = 0;
	//myData obj=new myData();
	ResultSet resultSet =null;
	PreparedStatement preparedStatement=null;
	try {
		connection = obj.getDBConnection();
		preparedStatement = connection.prepareStatement("SELECT  * FROM users WHERE email = ?");
		preparedStatement.setString(1,email);
		resultSet = preparedStatement.executeQuery();

	} catch (Exception e) {
		System.out.print(e);
		e.printStackTrace();
	}
	finally{
        try{
            
            if(preparedStatement!=null){
            	preparedStatement.close();
            	preparedStatement = null;
            }
            
            if(connection != null){
                connection.close();
                connection = null;
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
	return resultSet;
}
	*/
	/**
     * *************************************
     *
     * @updateUser() method is used to update user.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int updateUser(String email,String pwd) {
		String sql = "UPDATE users SET PASSWORD='"+pwd+"',flag='1' WHERE email='"+email+"'";
		System.out.println("he155");
		return template.update(sql);
	}
	/**
     * *************************************
     *
     * @updateOTPPwd() method updates createProject table based on project id.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int updateOTPPwd(Emp p, String newPwd,String oldEmail) {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";

		System.out.println("newPwd=" + newPwd);
		System.out.println("oldEmail="+oldEmail);
		String sql = "UPDATE users SET PASSWORD='"+newPwd+"',flag='0' WHERE email='"+oldEmail+"' ";
		
	
		System.out.println("over");
		return template.update(sql);
	}
	
	/**
     * *************************************
     *
     * @activeInactiveProj() method updates the project status for given project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int activeInactiveProj(Emp p, String status,String value) {
		
		String sql = "UPDATE projects SET Proj_Status='"+status+"' WHERE Proj_Name IN ('"+value+"') ";
		System.out.println("he1895");
		
		return template.update(sql);
	}
	/*public int activeInactiveProj(String status,String value)
{
Connection connection = null;
	int i = 0;
	//myData obj=new myData();
	Statement statement=null;
	try {
//		myData data = new myData();
//
//		Class.forName(data.JDBC_DRIVER);
//		con = (Connection) DriverManager.getConnection(data.DB_URL,
//				data.USER, data.PASS);
		connection=obj.getDBConnection();
		statement = (Statement) connection.createStatement();
		i = statement.executeUpdate("UPDATE projects SET Proj_Status='"+status+"' WHERE Proj_Name IN ('"+value+"')");
		System.out.println("Data is successfully inserted !!!!" + i);
		// System.out.println("rows affected by insert "+st.executeUpdate(i));

	} catch (Exception e) {
		System.out.print(e);
		e.printStackTrace();
	}
	finally{
        try{
            
            if(statement!=null){
            	statement.close();
            	statement = null;
            }
            
            if(connection != null){
                connection.close();
                connection = null;
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
	return i;
}*/
	/**
     * *************************************
     *
     * @testDataSource() method gets email aned password for validation in login page..
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public Emp testDataSource(String username,String password) {
		// System.out.println("project id="+projID);

		String sql = "SELECT * FROM users WHERE email=? AND PASSWORD=?";
		System.out.println("get emp by iD details:=" + sql);
		// System.out.println("user name="+firstNAME);
		try {
		 Emp e= template.queryForObject(sql, new Object[] { username,password },
				new RowMapper<Emp>() {
					public Emp mapRow(ResultSet rs, int row)
							throws SQLException {

						Emp e = new Emp();
						 e.setFirstNAME(rs.getString("first_name"));
						 e.setLastNAME(rs.getString("last_name"));
						 e.setEmail(rs.getString("email"));
						  e.setRoleID(rs.getInt("role_id"));
						  e.setUserID(rs.getInt("userid"));
						  e.setFlag(rs.getInt("flag"));
						 
						 System.out.println("lastname="+rs.getString("last_name"));
						 System.out.println("password="+rs.getString("password"));
					
						System.out.println("role id="
								+ rs.getString("role_id"));
						System.out.println("email="
								+ rs.getString("email"));
					
					return e;

					}
				});
		 return e;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	/*public int testDataSource(String username,String password) {
		
		
		logger.info("Entered into testDataSource method getting the Database connection");
		Connection connection = null;
		int roleID=0;
		
			connection = obj.getDBConnection();
	
		
			System.out.println("testDataSource connection");
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		try {
			
			String query="SELECT * FROM users WHERE email='"+username+"' AND PASSWORD='"+password+"'";
			logger.info(">>>>>>>>>"+query);
			System.out.println("testDataSource query");
			preparedStatement = connection.prepareStatement(query);
			System.out.println("testDataSource statement");
			resultset = preparedStatement.executeQuery();
			
			   System.out.println("testDataSource resultset");
								
			 if(resultset.next()){
			
				 String firstName = resultset.getString("first_name");
		         String lastName = resultset.getString("last_name");
		         String email=resultset.getString("email");
		         roleID=resultset.getInt("role_id");
				
		         setUserid(resultset.getInt("userid"));
		         setFirstName(firstName);
		         setLastName(lastName);
		         setEmail(email);
		         setRoleid(roleID);
				 logger.info("roleid:"+getRoleid());
				 logger.info("firstname:"+getFirstName());
                 logger.info("lastname:"+getLastName());
                 logger.info("userid:"+getUserid());
                 System.out.println("email used is="+getEmail());
                 
                 
             
			}
			else
			{
				roleID=2;
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(resultset != null)
						{
						resultset.close();
						resultset = null;
						}
					if(preparedStatement != null) 
						{
						preparedStatement.close();
						preparedStatement = null;
						}
					if(connection != null) 
						{
						connection.close();
						connection = null;
						}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return roleID;
	}*/
/**
 * *************************************
 *
 * @getProjectList() method gets project name assigned to the given userid.
 *
 * @Author:Maitri
 *
 * @Created Date:04/15/2015
 *
 **************************************
 */
public List<Emp> getProjectList( String userids) {
	System.out.println("in getprojlist1");
	String sql = "SELECT p.Proj_Name FROM projects p INNER JOIN lookup_users l ON (p.Proj_Id=l.Proj_Id) WHERE l.User_Id=?";
	System.out.println("in getprojlist2");
	System.out.println("after got into List<Emp>"
			+ userids);
	return template.query(sql, new Object[] { userids },
			new RowMapper<Emp>() {
				public Emp mapRow(ResultSet rs, int row)
						throws SQLException {
					System.out.println("in getprojlist3");
					Emp e = new Emp();
					System.out.println("in getprojlist4");
					e.setProjName(rs.getString("Proj_Name"));
					

					
					System.out.println("proj name="+rs.getString("Proj_Name"));
					
					return e;

				}
			});
}
	/*public ArrayList getProjectList(String userids) {
		
		logger.info("Values from form in Data source:"+getUsername()+" and "+getPassword());
		ArrayList arraylist=new ArrayList();
		Connection connection = null;
		//if("mysql".equals(dbType)){
//			connection = myData.getMySQLDataSource();
			connection =obj.getDBConnection();
		
		/*}else{
			logger.error("invalid db type");
			return arraylist;
		}
		
		
		PreparedStatement prepareStatement = null;
		ResultSet resultset = null;
		
		try {
			String query="SELECT p.Proj_Name FROM projects p INNER JOIN lookup_users l ON (p.Proj_Id=l.Proj_Id) WHERE l.User_Id="+userids;
			logger.info(">>>>>>>>>"+query);
			prepareStatement = connection.prepareStatement(query);
			resultset = prepareStatement.executeQuery();
			while(resultset.next()){
				String projName=resultset.getString("Proj_Name");
				setProjName(projName);
				 
				// System.out.println("proj name="+projName);
				arraylist.add(resultset.getString("Proj_Name"));
				logger.info("firstname:"+getFirstName());
				logger.info("lastname:"+getLastName());
				logger.info("userid:"+getUserid());
				logger.info("proj name:"+getProjName());
				
			}
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(resultset != null)
					{
					resultset.close();
					resultset = null;
					}
				if(prepareStatement != null) 
					{
					prepareStatement.close();
					prepareStatement = null;
					}
				if(connection != null) 
					{
					connection.close();
					connection = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return arraylist;
	}*/
	/**
     * *************************************
     *
     * @getToolList() method gets the tool name assigned to the given userid.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
public List<Emp> getToolList( String userids) {
	System.out.println("in getToolList1");
	String sql = "SELECT t.tool_name FROM lookup_tools t INNER JOIN lookup_users l ON (t.Tool_Id=l.Tool_Id) WHERE l.User_Id=?";
	System.out.println("in getToolList2");
	System.out.println("after got into List<Emp>"
			+ userids);
return template.query(sql, new Object[] { userids },
			new RowMapper<Emp>() {
				public Emp mapRow(ResultSet rs, int row)
						throws SQLException {
					System.out.println("in getToolList3");
					Emp e = new Emp();
					System.out.println("in getToolList4");
					e.setToolName(rs.getString("tool_name"));;
					System.out.println("tool name="+rs.getString("tool_name"));
					
					return e;

				}
			});
}

public List<Emp> getTLProj( String email) {
	System.out.println("in getTLProj1");
	String sql = "SELECT u.userid,u.first_name,p.Proj_Name,lu.Proj_Id FROM users u INNER JOIN lookup_users lu ON (u.userid=lu.User_Id) INNER JOIN projects p ON (p.Proj_Id=lu.Proj_Id) WHERE u.email=?";
	System.out.println("in getTLProj2");
	System.out.println("after got into List<Emp> gettlproj"
			+ email);
return template.query(sql, new Object[] { email },
			new RowMapper<Emp>() {
				public Emp mapRow(ResultSet rs, int row)
						throws SQLException {
					System.out.println("in getTLProj3");
					Emp e = new Emp();
					System.out.println("in getTLProj4");
					e.setUserID(rs.getInt("userid"));
					e.setFirstNAME(rs.getString("first_name"));
					e.setProjName(rs.getString("Proj_Name"));
					e.setProjID(rs.getInt("Proj_Id"));
					System.out.println("userid="+rs.getString("userid"));
					System.out.println("first name="+rs.getString("first_name"));
					System.out.println("Proj_Name name="+rs.getString("Proj_Name"));
					System.out.println("Proj_Id name="+rs.getString("Proj_Id"));
					return e;

				}
			});
}
	/*public ArrayList getToolList(String userids) {
		
		logger.info("Values from form in Data source:"+getUsername()+" and "+getPassword());
		ArrayList arraylist=new ArrayList();
		Connection connection = null;
		//if("mysql".equals(dbType)){
			connection = obj.getDBConnection();
		
		/*}else{
			logger.error("invalid db type");
			return arraylist;
		}
		
		
		PreparedStatement prepareStatement = null;
		ResultSet resultset = null;
		
		try {
			String query="SELECT t.tool_name FROM lookup_tools t INNER JOIN lookup_users l ON (t.Tool_Id=l.Tool_Id) WHERE l.User_Id="+userids;
			logger.info(">>>>>>>>>"+query);
			prepareStatement = connection.prepareStatement(query);
			resultset = prepareStatement.executeQuery();
			while(resultset.next()){
				arraylist.add(resultset.getString("tool_name"));
				logger.info("firstname:"+getFirstName());
				logger.info("lastname:"+getLastName());
				logger.info("userid:"+getUserid());
			}
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(resultset != null)
					{
					resultset.close();
					resultset = null;
					}
				if(prepareStatement != null) 
					{
					prepareStatement.close();
					prepareStatement = null;
					}
				if(connection != null) 
					{
					connection.close();
					connection = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return arraylist;
	}*/
	/**
     * *************************************
     *
     * @assignProjectList() method gets the names of projects which are active from projects table..
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
public List<Emp> assignProjectList() {
	System.out.println("in assignProjectList");
	String sql = "SELECT Proj_Name FROM projects WHERE Proj_Status='Active'";
	System.out.println("in assignProjectList");

		return template.query(sql, 
			new RowMapper<Emp>() {
				public Emp mapRow(ResultSet rs, int row)
						throws SQLException {
					System.out.println("in assignProjectList");
					Emp e = new Emp();
					System.out.println("in assignProjectList");
					e.setProjName(rs.getString("Proj_Name"));
					System.out.println("proj name="+rs.getString("Proj_Name"));
					
					return e;

				}
			});
}

public List<Emp> listofTL() {
	System.out.println("in listofTL1");
	String sql = "SELECT first_name,last_name FROM users WHERE role_id=2";
	System.out.println("in listofTL2");

		return template.query(sql, 
			new RowMapper<Emp>() {
				public Emp mapRow(ResultSet rs, int row)
						throws SQLException {
					System.out.println("in listofTL3");
					Emp e = new Emp();
					System.out.println("in listofTL4");
					e.setUsername(rs.getString("first_name") + " "
								+ rs.getString("last_name"));
					System.out.println("user name="+rs.getString("first_name")+""+rs.getString("last_name"));
					
					return e;

				}
			});
}
	/*public ArrayList assignProjectList() {
		
		logger.info("Values from form in Data source:"+getUsername()+" and "+getPassword());
		ArrayList arraylist=new ArrayList();
		Connection connection = null;
		//if("mysql".equals(dbType)){
			connection = obj.getDBConnection();
		
		/*}else{
			logger.error("invalid db type");
			return arraylist;
		}
		
		
		PreparedStatement prepareStatement = null;
		ResultSet resultset = null;
		
		try {
			String query="SELECT Proj_Name FROM projects WHERE Proj_Status='Active'";// getting projects which are active in assign proj page
			logger.info(">>>>>>>>>"+query);
			prepareStatement = connection.prepareStatement(query);
			resultset = prepareStatement.executeQuery();
			while(resultset.next()){
				arraylist.add(resultset.getString("Proj_Name"));
				System.out.println("proj name="+resultset.getString("Proj_Name"));
			}
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(resultset != null)
					{
					resultset.close();
					resultset = null;
					}
				if(prepareStatement != null) 
					{
					prepareStatement.close();
					prepareStatement = null;
					}
				if(connection != null) 
					{
					connection.close();
					connection = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return arraylist;
	}	*/
	/**
     * *************************************
     *
     * @getUnassignedUsers() method gets the list of users who are not assigned to any project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public List<Emp> getUnassignedUsers() {
		String sql = "SELECT u.first_name,u.userid,u.last_name,u.phone,u.email,u.role_id FROM users u  WHERE  u.userid  NOT IN(SELECT u.userid FROM users u  LEFT OUTER JOIN lookup_users lu ON (lu.User_Id=u.userid) LEFT OUTER JOIN projects p ON (p.Proj_Id=lu.Proj_Id)WHERE  lu.Proj_Id=p.Proj_Id ) UNION(SELECT u.first_name,u.userid,u.last_name,u.phone,u.email,u.role_id FROM users u  LEFT OUTER JOIN  lookup_users lu ON (lu.User_Id=u.userid) WHERE  lu.LookupUser_Status='Inactive' )";
		// System.out.println("template value in List<Emp>:="+sql);
		return template.query(sql, new RowMapper<Emp>() {
			public Emp mapRow(ResultSet rs, int row) throws SQLException {

				Emp e = new Emp();
				e.setFirstNAME(rs.getString("first_name"));
				int roleId=rs.getInt("role_id");
				if(roleId==0)
				{
					e.setUserRole("Admin");
					
				}else
				{
					e.setUserRole("Tester");
				}
				
				//e.setLastNAME(rs.getString("last_name"));
				//e.setProjName(rs.getString("Proj_Name"));
				//e.setToolName(rs.getString("tool_name"));
				e.setEmail(rs.getString("email"));
				e.setPhone(rs.getString("phone"));
				//e.setProjID(rs.getInt("Proj_Id"));
				//e.setToolID(rs.getInt("Tool_Id"));
				e.setUserID(rs.getInt("userid"));
				//e.setUsername(rs.getString("first_name") + " "
					//	+ rs.getString("last_name"));
				e.setUsername(rs.getString("first_name") + " "
						+ rs.getString("last_name"));

				
				System.out.println("got into List<Emp>-first name"
						+ rs.getString("first_name"));
				System.out.println("got into List<Emp>-userid"
						+ rs.getInt("userid"));
				
				return e;

			}
		});
	}

	
	public List<Emp> getToolNames() {
		System.out.println("in getToolNames");
		String sql = "SELECT tool_name FROM lookup_tools";
		System.out.println("in getToolNames");

			return template.query(sql, 
				new RowMapper<Emp>() {
					public Emp mapRow(ResultSet rs, int row)
							throws SQLException {
						System.out.println("in getToolNames");
						Emp e = new Emp();
						System.out.println("in getToolNames");
						e.setToolName(rs.getString("tool_name"));
						System.out.println("tool name="+rs.getString("tool_name"));
						
						return e;

					}
				});
	}
	/**
     * *************************************
     *
     * @checkForEmailExistance() method updates createProject table based on project id.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int checkForEmailExistance(Emp p,String Email) {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";

		System.out.println("Email=" + Email);
	//	System.out.println("oldEmail="+oldEmail);
		String sql = "SELECT COUNT(userid)AS COUNT FROM users WHERE email='"+Email+"' ";
		
	
		System.out.println("over check");
		return template.queryForInt(sql);
	}
	
	/*
	public ArrayList getToolNames() {

		logger.info("Values from form in Data source:" + getUsername()
				+ " and " + getPassword());
		ArrayList arrayToolNames = new ArrayList();
		Connection connection = null;
		// if("mysql".equals(dbType)){
		connection = obj.getDBConnection();

		/*
		 * }else{ logger.error("invalid db type"); return arraylist; }
		 */

		/*PreparedStatement statement = null;
		ResultSet resultset = null;

		try {
			String query = "SELECT tool_name FROM lookup_tools";
			logger.info(">>>>>>>>>" + query);
			statement = connection.prepareStatement(query);
			resultset = statement.executeQuery();
			while (resultset.next()) {
				arrayToolNames.add(resultset.getString("tool_name"));
				System.out.println("tool name="
						+ resultset.getString("tool_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultset != null)
					resultset.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayToolNames;
	}

*/
	
	/**
     * *************************************
     *
     * @gettoolid().
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	public int TestEmail(String email) {
		// String
		// sql="update users set first_name='"+p.getFirstNAME()+"' and  where Proj_Id="+p.getProjID()+" ";
		//int tool_id =0;
		String sql = "SELECT count(*) FROM users WHERE  email = '"+email+"' ";
		System.out.println("he1895tool");

		System.out.println("toolname=" + email);
		return template.queryForInt(sql);
		
	}
}
