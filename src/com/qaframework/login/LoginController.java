 /**
     * *************************************
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
package com.qaframework.login;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mss.FileFilterDateIntervalUtils;
import com.mss.GettingReports;
import com.mss.ReadExcelSheet;
import com.mss.ReadPropertyFile;
import com.mss.utils.MailManager;

import edu.emory.mathcs.backport.java.util.Collections;

@Controller
public class LoginController {
	myData obj = new myData();
	static final Logger logger = Logger.getLogger(LoginController.class);
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	private static final int BUFFER_SIZE = 4096;
	
	private static final String SMTP_AUTH_USER = "ksonti@miraclesoft.com";
	private static final String SMTP_AUTH_PWD = "Sidhu2806@";

	/**
	 * Path of the file to be downloaded, relative to application's directory
	 */
	//private String filePath = "\\Reports\\";
	@Autowired
	EmpDAO dao = new EmpDAO();
	
	 /**
     * *************************************
     *
     * @project() method gets the list of projects and tools from database.
     *
     * @Author:Kamesh
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping("/project")
	public ModelAndView projectPage(HttpServletRequest request,
			HttpServletResponse response) {
		//ServletContext context = request.getServletContext();
		//String log4jConfigFile = context.getInitParameter("log4j-config-location");
		System.out.println("entered in login controller");
		HttpSession session = request.getSession();
		//List<Emp> projectList = new ArrayList();
		
		try {
			
			
			System.out
					.println("path=" + new java.io.File("").getAbsolutePath());
			System.out.println("path1"
					+ LoginController.class.getClassLoader().getResource("")
							.getPath());
			String userids = session.getAttribute("userid").toString();
			System.out.println("userid="+userids);
			int userid = 0;
			int i=0;
			dao.setUserid(userid);
			List<Emp> projNAME= dao.getProjectList(userids);
			ArrayList projectList=new ArrayList<>() ;
			
			while(i<projNAME.size())
			{
				System.out.println("in getprojlist"+i+projNAME.get(0).getProjName());
				projectList.add(projNAME.get(i).getProjName());
				i++;
			}
			
			
			if (projectList.size() > 0) {
				session.setAttribute("ProjectList", projectList);
			}
			int j=0;
			List<Emp> toolNAME= dao.getToolList(userids);
		
			ArrayList toolList=new ArrayList<>();

			//toolList = dao.getToolList(userids);
			//System.out.println("in tools gettoollist"+i+toolNAME.get(0).getToolName());
			while(j<toolNAME.size())
			{
				//System.out.println("in tools gettoollist"+i+toolNAME.get(0).getToolName());
				toolList.add(toolNAME.get(j).getToolName());
				j++;
			}
			if (toolList.size() > 0) {
				session.setAttribute("ToolList", toolList);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("projectTool", "message", null);

	}
	 /**
     * *************************************
     *
     * @excel() method sets the generated report name with username_selected project_testing tool name.
     *
     * @Author:Kamesh
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping("/excel")
	public ModelAndView welcomePage(HttpServletRequest request,
			HttpServletResponse response) {
		ArrayList uploadFileArray = new ArrayList();
		//ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		//String log4jConfigFile = context.getInitParameter("log4j-config-location");
		//String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
		// checks if the request actually contains upload file
		PrintWriter writer;
		String projType = null;
		String testingType = null;
		String fname = (String) session.getAttribute("frstname");
		try {
			if (!ServletFileUpload.isMultipartContent(request)) {

				writer = response.getWriter();

				writer.println("Request does not contain upload data");
				writer.flush();

			}
			// configures upload settings
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(THRESHOLD_SIZE);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);

			String uploadPath = request.getServletContext().getRealPath("")
					+ File.separator + UPLOAD_DIRECTORY+'\\'+fname;

			// String uploadPath = new File(contextRoot)
			// + File.separator+ UPLOAD_DIRECTORY;

			// / creates the directory if it does not exist
			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) {
				System.out.println("file path not exist=" + uploadDir);
				uploadDir.mkdirs();
			}

			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();

			// iterates over form's fields
			while (iter.hasNext()) {

				FileItem item = (FileItem) iter.next();
				// FileItem item1 = (FileItem) iter.next();
				// processes only fields that are not form fields
				if ((item.getFieldName().equalsIgnoreCase("samProjValue"))) {
					projType = item.getString();

				}
				if (item.getFieldName().equalsIgnoreCase("samTestingValue")) {
					testingType = item.getString();
				}
				System.out.println("store file=" + projType);
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					System.out.println("filename=" + fileName);
					String filePath = uploadPath + File.separator + fileName;
					uploadFileArray.add(filePath);
					System.out.println("file path=" + filePath);
					File storeFile = new File(filePath);
					System.out.println("store file=" + storeFile);
					// saves the file on disk
					item.write(storeFile);

				}
			}
			/*
			 * String excelFile=request.getParameter("exelTextFile"); String
			 * excelfilePath = uploadPath + File.separator + excelFile;
			 * uploadFileArray.add(excelfilePath);
			 * System.out.println("file path="+excelfilePath); File
			 * excelstoreFile = new File(excelfilePath);
			 * System.out.println("store file="+excelstoreFile);
			 * excelstoreFile.createNewFile(); // saves the file on disk //
			 * item.write(storeFile); String
			 * propFile=request.getParameter("propTextFile"); String
			 * propfilePath = uploadPath + File.separator + propFile;
			 * uploadFileArray.add(propfilePath);
			 * System.out.println("file path="+propfilePath); File PropstoreFile
			 * = new File(propfilePath);
			 * System.out.println("store file="+PropstoreFile);
			 * PropstoreFile.createNewFile();
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// logger.info("Uname: password:"+request.getParameter("excelFile")+request.getParameter("propertyFile"));
		// String excelFiles=request.getParameter("excelFile");
		// String propertyFiles=request.getParameter("propertyFile");
		File excelFile = new File(uploadFileArray.get(0).toString());
		File propertyFile = new File(uploadFileArray.get(1).toString());
		logger.info("Uname: password:" + excelFile
				+ propertyFile.getAbsolutePath());

		String repName = (String) session.getAttribute("frstname");
		System.out.println("frst name is :" + repName);
		session.setAttribute("repName", repName);
		//String fname = (String) session.getAttribute("frstname");
		// String jobCategory =request.getParameter("propTextFile");
		System.out.println("project selected is " + projType);
		System.out.println("testing tool selected is " + testingType);

		try {
			
			String repPath=request.getServletContext().getRealPath("");
			ReadPropertyFile readPropertyFile=new ReadPropertyFile();
			Properties properties= readPropertyFile.getPath(propertyFile.getAbsolutePath());
			ReadExcelSheet readExcelSheet=new ReadExcelSheet(excelFile.getAbsolutePath());
			String scenarios=properties.getProperty("mainsheet");
			GettingReports sample = new GettingReports();
			String RepFolder=null;
			ArrayList senList=new ArrayList();
			session.setAttribute("senList",null);
			for(int row=1;row<=readExcelSheet.count(scenarios);row++){
				if(readExcelSheet.readData(scenarios, row, 2).equals("Y")){
					  System.out.println("Senerio"+readExcelSheet.readData(scenarios, row, 0));
					readExcelSheet.removeCells(readExcelSheet.readData(scenarios, row, 0),5);
					//  senList.add(readExcelSheet.readData(scenarios, row, 0));
					  
						
				}
			}
			//session.setAttribute("senList",senList);
			 RepFolder = sample.gettingReports(
						excelFile.getAbsolutePath(),
						propertyFile.getAbsolutePath(), repPath, repName, fname,
						projType, testingType,readExcelSheet,scenarios);
			File f = new File(repPath + "\\Reports\\" + fname + "\\");
			// File theFile = new File(repPath+"\\Reports\\" +
			// session.getAttribute("frstname"));
			f.mkdirs();
			// File f = new File(repPath+"\\Reports\\");
			System.out.println("repPath=" + repPath);
			ArrayList names = new ArrayList(Arrays.asList(f.list()));
			session.setAttribute("names", names);
			
			session.setAttribute("projName", dao.getProjName());
			System.out.println("ds value=" + dao.getProjName());
			System.out.println("all files are=" + names);
			session.setAttribute("RepFolder", fname + "/" + RepFolder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		return new ModelAndView("welcome", "message", "");

	}
	 /**
     * *************************************
     *
     * @download() method used to download and save the generated test report.
     *
     * @Author:Kamesh
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping("/download")
	public void doDownload(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		// get absolute path of the application
		ServletContext context =  request.getServletContext();
		String appPath = context.getRealPath("");
		System.out.println("appPath = " + appPath);

		session.getAttribute("frstname");
		System.out.println("first name in dwnload="
				+ session.getAttribute("frstname"));
		String repPath =  request.getServletContext().getRealPath("");
		// construct the complete absolute path of the file
		String fullPath = repPath + "\\Reports\\"

		+ request.getParameter("selectedValue");/*
												 * change here-
												 * session.getAttribute
												 * ("frstname")
												 */
		/*
		 * String fullPath1=repPath + "\\Reports\\" + "\\"+
		 * session.getAttribute("frstname") +
		 * request.getParameter("selectedValue");
		 */
		System.out.println("full path=" + fullPath);
		// System.out.println("full path1=" + fullPath1);
		System.out.println("selected value="
				+ request.getParameter("selectedValue"));
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		System.out.println("MIME type: " + mimeType);

		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());
		// request.getParameter("selectedValue");
		/*
		 * List<String>   results = new ArrayList<String>();
		 * 
		 * 
		 * File[] files = new File("C:\\CRM_Interface\\Reports\\").listFiles();
		 * //If this pathname does not denote a directory, then listFiles()
		 * returns null.
		 * 
		 * for (File file : files) { if (file.isFile()) {
		 * results.add(file.getName()); System.out.println("\n"); } }
		 * session.setAttribute("results", results);
		 */

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();

	}
	 /**
     * *************************************
     *
     * @login() method validates the user credentials in the login page.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		System.out.println("email value1="+session.getAttribute("email"));
	//	if (request.getSession(false).getAttribute("email").toString() != null) {
			System.out.println("Session Result>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Available");
		ServletContext context =  request.getServletContext();
		String log4jConfigFile = context
				.getInitParameter("log4j-config-location");
		String fullPath = context.getRealPath("") + File.separator
				+ log4jConfigFile;
		String repPath =  request.getServletContext().getRealPath("");

		PropertyConfigurator.configure(fullPath);
		logger.info("Sample info message");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		logger.info("Uname:" + username + " password:" + password);

		String message;
		//DataSourceFile datasourcefile = new DataSourceFile();
		//EmpDAO data=new EmpDAO();
		dao.setUsername(username);
		dao.setPassword(password);
		String projectName = dao.getProjName();

		System.out.println("inner folder name=" + projectName);

		int userid = 0;
		dao.setUserid(userid);
		//int result = dao.testDataSource(username,password);
	//	Emp emp =dao.testDataSource(username,password);
	//	logger.info("Go result" + emp);
		//HttpSession session = request.getSession();
		//if (result > 0) {
	//	Emp roleId = dao.testDataSource(username,password);
			logger.info("Go welcome");
			Emp emp =dao.testDataSource(username,password);
			//result = 0;
			if(emp!=null)
			{
			message = " username or password are valid.";
			session.setAttribute("frstname", emp.getFirstNAME());
			session.setAttribute("username", emp.getFirstNAME()
					+ " " + emp.getLastNAME());
			session.setAttribute("userid", emp.getUserID());
			session.setAttribute("projName", dao.getProjName());
			String fname = (String) session.getAttribute("frstname");
			session.setAttribute("fname", fname);
			// String projectName= datasourcefile.getProjName();
			System.out.println("inner folder=" + projectName);
			System.out.println("userid=" + userid);
			System.out.println("email=" + emp.getEmail());
			System.out.println("email dao=" + dao.getEmail());
			session.setAttribute("email", emp.getEmail());
			System.out.println("frstname=" + emp.getFirstNAME());
			System.out.println("username=" + emp.getLastNAME() + " "
					+ emp.getLastNAME());
		//	Emp e=new Emp();
			
			String oldPwd =dao.getPassword();
			session.setAttribute("oldPwd", oldPwd);
			
			System.out.println("PasswordExisting=" + dao.getPassword());
			
			System.out.println("email2=" + emp.getEmail());
			System.out.println("pwd2=" + emp.getPassword());
			System.out.println("roleid=" + emp.getRoleID());
			System.out.println("flag login==" + emp.getFlag());
			int flagValue= emp.getFlag();
			int roleid=emp.getRoleID();
			request.setAttribute("roleid", 21);
		//	System.out.println("role id name=" + roleId);
			File f = new File(repPath + "\\Reports\\" + fname + "\\");
			// File theFile = new File(repPath+"\\Reports\\" +
			// session.getAttribute("frstname"));
			f.mkdirs();

			System.out.println("repPath=" + repPath);
			System.out.println("new path=" + f);

			ArrayList names = new ArrayList(Arrays.asList(f.list()));
			session.setAttribute("names", names);
			int i=0;
			List<Emp> toolListNAME= dao.getToolNames();
			ArrayList toolList=new ArrayList<>() ;
			System.out.println("tools names  are="+toolListNAME);
			
			while(i<toolListNAME.size())
			{
				System.out.println("in toolListNAME="+i+toolListNAME.get(0).getToolName());
				toolList.add(toolListNAME.get(i).getToolName());
				i++;
			}
			
			
			if (toolList.size() > 0) {
				session.setAttribute("toolList", toolList);
			}
			/*
			  ArrayList toolList = new ArrayList();
				toolList = dao.getToolNames();
				System.out.println("tools names are are="+toolList);
				try {
					
					if (toolList.size() > 0) {
						session.setAttribute("toolList", toolList);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			if ((roleid==1||roleid==2) && flagValue==1) {

				return new ModelAndView("welcome", "message", message);
			} else if (roleid==0 && flagValue==1) {
				List<Emp> list = dao.getEmployees();
				System.out
						.println("list in login controller hello------------------"
								+ list);
				//ServletContext context1 = request.getServletContext();
				HttpSession ses = request.getSession();
				
				//int i=0;
				List<Emp> assignProjListName= dao.assignProjectList();
				ArrayList assignProjList=new ArrayList<>() ;
				System.out.println("assign proj list size="+assignProjListName.size());
				System.out.println("in assignProjList1"+i+assignProjListName.get(0).getProjName());
				while(i<assignProjListName.size())
				{
					System.out.println("in assignProjList"+i+assignProjListName.get(0).getProjName());
					assignProjList.add(assignProjListName.get(i).getProjName());
					i++;
				}
				
				
				if (assignProjList.size() > 0) {
					ses.setAttribute("assignProjList", assignProjList);
				}
				/*ArrayList assignProjList = new ArrayList();
				assignProjList = dao.assignProjectList();
				Collections.sort(assignProjList);
				try {
					if (assignProjList.size() > 0) {
						ses.setAttribute("AssignProjList", assignProjList);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				System.out.println("list in login controller" + assignProjList);
				// System.out.println("all files are="+names);
				ses.setAttribute("viewEmpList", list);
				return new ModelAndView("viewemp", "list", list);
			}else if (flagValue==0) {
				//message = "Wrong username or password.";
				return new ModelAndView("frgtPwdChng", "", "");
			}
			else {
				message = "Wrong username or password.";
				return new ModelAndView("index", "message", message);
			}
			}
			else {
				message = "Wrong username or password.";
				return new ModelAndView("index", "message", message);
			}
		/*} else if (result == 0) {
			logger.error("Go error");
			message = "Wrong username or password.";

			return new ModelAndView("errorPage", "message", message);
		} else {
			message = "Wrong username or password.";
			return new ModelAndView("errorPage", "message", message);
		}*/
		/*}
		else{
			System.out.println("Session Result>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Un--->Available");
			return new ModelAndView("errorPage", "", "");
		}*/

	}

	 /**
     * *************************************
     *
     * @calendar() method gets the generated reports between two given dates.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	
	@RequestMapping("/calendar")
	public ModelAndView doCalender(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();

		String repPath = request.getServletContext().getRealPath("");
		ArrayList names = new ArrayList();
		session.setAttribute("reportsGen", names);
		String date1 = request.getParameter("fromDate").toString();
		String date2 = request.getParameter("toDate").toString();
		System.out.println("from date=" + date1);
		System.out.println("to date=" + date2);
		if (date1.equals("") || date2.equals("")) {
			System.out.println("no values entered");
		}
		/*
		 * boolean samedate =date1.equals(date2);
		 * System.out.println("dates same"+samedate);
		 */
		if (date1.equals(date2)) {

			System.out.println(" from and to dates are same");

		}
		session.setAttribute("fromdate", date1);
		session.setAttribute("todate", date2);

		File directory = new File(repPath + "\\Reports\\"
				+ session.getAttribute("frstname"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FileFilterDateIntervalUtils filter = new FileFilterDateIntervalUtils(
				sdf.format(new Date(date1)), sdf.format(new Date(date2)));
		System.out.println("filter=" + filter);
		File[] files = directory.listFiles(filter);

		for (File file : files) {
			Date lastMod = new Date(file.lastModified());
			System.out.println("File: " + file.getName() + ", Date: " + lastMod
					+ "");

			// File f = new File("C:\\CRM_Interface\\Reports\\");
			/*
			 * if(lastMod.after(new Date(date1))&&lastMod.before(new
			 * Date(date2))) {
			 */
			/*
			 * if(file.list()!=null) {
			 */

			names.add(file.getName());
			// = new ArrayList<String>(Arrays.asList(file.list()));
			System.out.println("windws files" + names);
			System.out.println("list size=" + names.size());

			session.setAttribute("reportsGen", names);
			// }
			// }

		}
		return new ModelAndView("reports", "message", "");

	}
 /**
     * *************************************
     *
     * @doRegister() method is used to get entered values from createUser.jsp and store them in database.
     * Then a mail is sent to the registerd email.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

	@RequestMapping("/register")
	public ModelAndView doRegister(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String passwrd = request.getParameter("pwd");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String roles = request.getParameter("dropdown");
		// String[] roles=request.getParameterValues("dropdown");

		String userName = firstName;
		System.out.println("fisrt name=" + firstName);
		System.out.println("last name=" + lastName);
		System.out.println("password=" + passwrd);
		System.out.println("email=" + email);
		System.out.println("phone=" + phone);
		System.out.println("roles=" + roles);
		System.out.println("user name=" + userName);

		int roleId;
		if (roles.equals("Admin")) {
			roleId = 0;
		} else if (roles.equals("Tester")){
			roleId = 1;
		}else
		{
			roleId = 2;
		}

		
		int i = 0;
		try {
			//myData data = new myData();
           
			//Class.forName(data.JDBC_DRIVER);
		/*	con = (Connection) DriverManager.getConnection(data.DB_URL,
					data.USER, data.PASS);*/
			/* con = obj.getDBConnection();
			Statement st = (Statement) con.createStatement();
			i = st.executeUpdate("insert into users(first_name,last_name,password,email,phone,role_id,username) values('"
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
					+ "')");*/
			i=dao.createUser(firstName,lastName,passwrd,email,phone,roleId,userName);
			System.out.println("Data is successfully inserted!" + i);
			// System.out.println("rows affected by insert "+st.executeUpdate(i));
		} catch (Exception e) {
			System.out.print(e);
			e.printStackTrace();
		}
		if (i == 1) {
			MailManager sender = new MailManager(SMTP_AUTH_USER, SMTP_AUTH_PWD);
			sender.sendMail("QAFramework services", "",
					"QAFramework@miraclesoft.com", email,firstName, roles, passwrd);
			List<Emp> list = dao.getEmployees();
			session.setAttribute("viewEmpList", list);
			System.out.println("values in register.qa" + list);
			String message;
			message ="You  have Successfully created the user";
			return new ModelAndView("viewemp", "message", message);
		} else {
			String message;
			message = "Unsuccessfull in creating the user";
			return new ModelAndView("registerFailure", "message", message);
		}
	}

/**
     * *************************************
     *
     * @doCreateProj() method is used to get entered values from createProj.jsp and store them in database.
     *
     * @Author:Manoranjan
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

	@RequestMapping("/createProj")
	public ModelAndView doCreateProj(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		System.out.println("entered");
		//DataSourceFile datasourcefile = new DataSourceFile();
		String projectName = request.getParameter("projName");
		String projID = request.getParameter("projID");
		String testingToolName = request.getParameter("toolName");
		String createdDate = request.getParameter("createDate");
		String completedDate = request.getParameter("completeDate");
		String Proj_Status = request.getParameter("status");

		String projDescripton = request.getParameter("description");
		System.out.println("start date=" + createdDate);
		System.out.println("end date=" + completedDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// surround below line with try catch block as below code throws checked
		// exception
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = sdf.parse(createdDate);
			endDate = sdf.parse(completedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// do further processing with Date object
		System.out.println("start date=" + sdf.format(startDate));
		System.out.println("end date=" + sdf.format(endDate));
		System.out.println("proj name=" + projectName);
		System.out.println("proj ID=" + projID);
		System.out.println("tool name=" + testingToolName);
		System.out.println("desc=" + projDescripton);
		System.out.println("Status is=" + Proj_Status);

		
		
		String result=null;
		try {
			//myData data = new myData();

			//Class.forName(data.JDBC_DRIVER);
			/*con = (Connection) DriverManager.getConnection(data.DB_URL,
					data.USER, data.PASS);*/
			result=dao.createProj(projectName, Proj_Status,testingToolName,createdDate,completedDate,projDescripton);
			/*i = st.executeUpdate("INSERT INTO projects (Proj_Name,Proj_Status) VALUES ('"
					+ projectName
					+ "','"
					+ Proj_Status
					+ "')");
			
			System.out
					.println("Data is successfully inserted in projects!" + i);*/
			// System.out.println("rows affected by insert "+st.executeUpdate(i));
			/*if(i==1){
			Statement stProj = (Statement) con.createStatement();

			// j =
			// st.executeUpdate("INSERT INTO projects (proj_name,proj_id) VALUES ('"+
			// projectName+ "','"+projID+"')");
			j = stProj.executeUpdate("insert into createproj(Proj_Name,Testing_Tool,Created_Date,End_Date,Description,Proj_Status) values('"
					+ projectName
					+ "','"
					+ testingToolName
					+ "','"
					+ createdDate
					+ "','"
					+ completedDate
					+ "','"
					+ projDescripton + "','" + Proj_Status + "')");
			System.out.println("Data is successfully inserted " + j);
			}*/
		} catch (Exception e) {
			System.out.print(e);
			e.printStackTrace();
		}
		if (result.equalsIgnoreCase("success") ) {

			/*
			 * List<Emp> list=dao.getProjName();
			 * session.setAttribute("viewEmpList", list);
			 * System.out.println("values in createProj.qa"+list); return new
			 * ModelAndView("viewemp", "", "");
			 */
			/*int i=0;
			List<Emp> assignProjListName= dao.assignProjectList();
			ArrayList assignProjList=new ArrayList<>() ;
			
			while(i<assignProjListName.size())
			{
				System.out.println("in assignProjList"+i+assignProjListName.get(0).getProjName());
				assignProjList.add(assignProjListName.get(i).getProjName());
				i++;
			}
			
			
			if (assignProjList.size() > 0) {
				ses.setAttribute("assignProjList", assignProjList);
			}*/
			/*ArrayList assignProjList = new ArrayList();*/
			List<Emp> assignProjList = dao.assignProjectList();
			//Collections.sort(assignProjList);
			try {
				if (assignProjList.size() > 0) {
					session.setAttribute("AssignProjList", assignProjList);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("values in createProj.qa" + assignProjList);
			String message;
			message ="You  have Successfully created the Project";
			return new ModelAndView("viewemp", "message", message);
		} else {
			String message;
			message = "Unsuccessfull in creating the project";
			return new ModelAndView("registerFailure", "message", message);
		}
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("emp") Emp emp) {
		dao.save(emp);
		System.out.println("helloooo14");
		return new ModelAndView("redirect:/viewemp.qa"); // will redirect to
															// viewemp request
															// mapping
	}

	/*
	 * It displays object data into form for the given id. The @PathVariable
	 * puts URL data into variable.
	 */

 /**
     * *************************************
     *
     * @editemp() method is used to get entered values from createProj.jsp and store them in database.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping(value = "/editemp/{projID:[\\d]+}/{userID:[\\d]+}/{firstNAME}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("projID") int projID,
			@PathVariable("userID") int userID,
			@PathVariable("firstNAME") String firstNAME,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("emp id in edit emp Controller=" + projID);
		System.out.println("user name in edit emp Controller=" + firstNAME);
		System.out.println("user id in edit emp=" + userID);
		Emp emp = dao.getEmpById(projID, firstNAME);
		HttpSession session = request.getSession();
		session.setAttribute("userID", userID);
		
		// System.out.println("emp proj id and name="+emp.gegetProjID()+emp.getProjName()+projID);
		return new ModelAndView("empeditform", "command", emp);
	}
 /**
     * *************************************
     *
     * @editproj() method is called when we click on project link in viewemp.jsp.
     * It gets the details of the user and redirect to projeditform.jsp.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */	
	@RequestMapping(value = "/editproj/{projID:[\\d]+}/{userID:[\\d]+}/{firstNAME}", method = RequestMethod.GET)
	public ModelAndView editProj(@PathVariable("projID") int projID,         /* same as edit emp*/
			@PathVariable("userID") int userID,
			@PathVariable("firstNAME") String firstNAME,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("emp id in edit emp Controller=" + projID);
		System.out.println("user name in edit emp Controller=" + firstNAME);
		System.out.println("user id in edit emp=" + userID);
		Emp emp = dao.getProjById(projID);
		HttpSession session = request.getSession();
		session.setAttribute("projID", projID);
		System.out.println("Tool Name=" + emp.getToolName());
		System.out.println("Tool Name=");
		session.setAttribute("toolName", emp.getToolName());
		// System.out.println("emp proj id and name="+emp.gegetProjID()+emp.getProjName()+projID);
		return new ModelAndView("projeditform", "command", emp);
	}
 /**
     * *************************************
     *
     * @editsave() method saves the edited details in editempform.jsp.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	/* It updates model object. */
	@RequestMapping(value = "/editsave", method = RequestMethod.POST)
	public ModelAndView editsave(@ModelAttribute("emp") Emp emp,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String email= request.getParameter("email");
		String name=request.getParameter("firstNAME");
		int uID = (int) session.getAttribute("userID");
		String Userrole=emp.getUserRole();
		dao.update(emp, uID);
		dao.updateUserRole(emp, email,name);
		if(Userrole.equals("0"))
		{
		dao.updateUserRole1(uID);
		}
		System.out.println("helloooo2");
		System.out.println("user id in edit save="
				+ session.getAttribute("userID"));
		System.out.println("user id in edit save 1=" + uID);

		
		System.out.println("email id in edit save="
				+ session.getAttribute("email"));
		
		List<Emp> list = dao.getEmployees();
		session.setAttribute("viewEmpList", list);
		String message;
		message="Edited changes are saved!!!";
		return new ModelAndView("viewemp", "message", message);
	}
 /**
     * *************************************
     *
     * @editProjsave() method saves the edited details in projeditform.jsp
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */	
	@RequestMapping(value = "/editProjsave", method = RequestMethod.POST)
	public ModelAndView editProjsave(@ModelAttribute("emp") Emp emp,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int pID = (int) session.getAttribute("projID");
		String toolName = (String) session.getAttribute("toolName");
		
		dao.updateProj(emp, pID);
		dao.updateProj1(emp, pID);
		dao.updateTool(emp, toolName);
		System.out.println("helloooo2");
		System.out.println("user id in edit save="
				+ session.getAttribute("userID"));
		System.out.println("user id in edit save 1=" + pID);

		List<Emp> list = dao.getEmployees();
		session.setAttribute("viewEmpList", list);
		String message;
		message="Edited changes are saved!!!";
		return new ModelAndView("viewemp", "message", message);
	}


/**
     * *************************************
     *
     * @deleteemp() method is called when we click delete link in viewemp.jsp.
     * It deletes the entire row.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

	@RequestMapping(value = "/deleteemp/{projID:[\\d]+}/{userID:[\\d]+}/{toolID}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("projID") int projID,
			@PathVariable("userID") int userID,
			@PathVariable("toolID") int toolID, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("projid=" + projID);
		System.out.println("userid=" + userID);
		System.out.println("toolid=" + toolID);
		dao.delete(projID, userID, toolID);
		System.out.println("helloooo23");
		HttpSession ses = request.getSession();
		List<Emp> list = dao.getEmployees();
		ses.setAttribute("viewEmpList", list);
		
		return new ModelAndView("redirect:/viewFunc.qa", "msg", "delete");
	}
	
	@RequestMapping(value = "/deleteUnassignedUsers/{userID}", method = RequestMethod.GET)
	public ModelAndView deleteUnassignedUsers(
			@PathVariable("userID") int userID,
			 HttpServletRequest request,
			HttpServletResponse response) {
		//System.out.println("email=" + email);
		System.out.println("userid in delete unassigned users=" + userID);
		//System.out.println("phone=" + phone);
		dao.deleteUnassignedUsers(userID);
		System.out.println("helloooo23");
		HttpSession ses = request.getSession();
		List<Emp> listgetUnassignedUsers = dao.getUnassignedUsers();
		ses.setAttribute("listgetUnassignedUsers", listgetUnassignedUsers);

		return new ModelAndView("unassignedUsers", "", "");
	}

 /**
     * *************************************
     *
     * @getUserName() method is used to get names of all users who are not assigned for particular project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping("/getUserName/{assignProj}/{assignRemove}")
	public ModelAndView getUserNames(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("assignProj") String assignProj,
			@PathVariable("assignRemove") String assignRemove) {
		System.out.println("v:::::::::::::::::" + request);
		HttpSession session = request.getSession();
		session.setAttribute("allList", "");
		// session.setAttribute("list", "");
		String projSelected = assignProj;
		System.out.println("jjjjjjjjjjjjjjjj");
		String assignRemoveOption = assignRemove;
		System.out.println("value in assign/Remove=" + assignRemoveOption);
		System.out.println("Selected proj value=" + projSelected);

		session.setAttribute("projSelected", projSelected);
		session.setAttribute("assignRemoveOption", assignRemoveOption);

		if (assignRemoveOption.equalsIgnoreCase("assign"))

		/*
		 * List<Emp> list = dao.getUserName(projSelected);
		 * System.out.println("helloooo last=" + projSelected);
		 * session.setAttribute("list", list); Emp e = new Emp();
		 * System.out.println("userid =" + e.getUserID());
		 * System.out.println("projid =" + e.getProjID());
		 * System.out.println("firstName =" + e.getFirstNAME());
		 */

		{
			System.out.println("value in assign/Remove in if 1="
					+ assignRemoveOption);

			List<Emp> allList = dao.getAllUserName(projSelected);

			// System.out.println("helloooo 2nd last=" + projSelected);
			// Collections.sort(allList);
			session.setAttribute("allList", allList);

			List<Emp> projID = dao.getProjID(projSelected);
			System.out.println("helloooo 2nd last=" + projID);
			session.setAttribute("projID", projID.get(0).getProjID());

			// session.setAttribute("allList", allList);
			// Emp emp = new Emp();
			// System.out.println("userid one =" + emp.getUserID());
			// System.out.println("projid one=" + emp.getProjID());
			// System.out.println("firstName one =" + emp.getFirstNAME());

			String message = "assign1";
			return new ModelAndView("assignProjects", "message", message);
		} else if (assignRemoveOption.equalsIgnoreCase("remove"))

		{
			System.out.println("value in assign/Remove in if 2="
					+ assignRemoveOption);
			List<Emp> list = dao.getUserName(projSelected);
			if (list.size() != 0) {
				System.out.println("size in all list is=" + list.size());

				System.out.println("helloooo last=" + projSelected);
				session.setAttribute("list", list);
				List<Emp> projID = dao.getProjID(projSelected);
				System.out.println("helloooo 2nd last=" + projID);
				session.setAttribute("projID", projID.get(0).getProjID());
				// session.setAttribute("allList", allList);
				Emp emp = new Emp();
				System.out.println("userid one =" + emp.getUserID());
				System.out.println("projid one=" + emp.getProjID());
				System.out.println("firstName one =" + emp.getFirstNAME());

				String message = "remove";
				return new ModelAndView("assignProjects", "message", message);
			} else {
				String message = "noData";
				return new ModelAndView("assignProjects", "message", message);
			}

		} else {
			System.out.println("value in assign/Remove in if 3="
					+ assignRemoveOption);
			String message = "other";
			return new ModelAndView("assignProjects", "message", message);
		}

	}
 /**
     * *************************************
     *
     * @namesInSelectBox() method assigns the selected project to the user selected from all displayed users.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping("/namesInSelectBox")
	public ModelAndView getAllUserNames(@ModelAttribute("emp") Emp emp,HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String[] allNames = request.getParameterValues("sbTwo");
		System.out.println(" all names length=" + allNames.length);

		for (int i = 0; i < allNames.length; i++) {
			System.out.println(" request.getParameter(allNames[i])=" +request.getParameter(allNames[i]));
			int UserId = Integer.parseInt(request.getParameter(allNames[i]));

			int ProjId = Integer.parseInt(request.getParameter("projid"));
			System.out.println("helloooo all names1=" + allNames.length);
			System.out.println("helloooo userid=" + UserId);
			System.out.println("helloooo projid=" + ProjId);
			String toolname=request.getParameter("toolname");

			int toolid = dao.gettoolid(emp,toolname);
		//	Emp emp = new Emp();
			System.out.println("tool id ="+emp.getToolID());

			try {
				//myData data = new myData();

				//Class.forName(data.JDBC_DRIVER);
			/*	con = (Connection) DriverManager.getConnection(data.DB_URL,
						data.USER, data.PASS);*/
				 /*con = obj.getDBConnection();
				 st =  con.createStatement();
				st.executeUpdate("insert into lookup_users(User_Id,Proj_Id,Tool_Id,LookupUser_Status) values("
						+ UserId + "," + ProjId + ",1005,'Active')");
				System.out.println("Data is successfully inserted !!!!" + i);*/
				// System.out.println("rows affected by insert "+st.executeUpdate(i));
				dao.insertLookUP(UserId, ProjId,toolid);

			} catch (Exception e) {
				System.out.print(e);
				e.printStackTrace();
			}
		}

		List<Emp> list = dao.getEmployees();
		session.setAttribute("viewEmpList", list);
		
		return new ModelAndView("viewemp", "message", "Projects assigned successfully to the user");
	}
/**
     * *************************************
     *
     * @removeFunctionality() method gets the names of all users who are assigned to a particular project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping("/removeFunctionality")
	public ModelAndView removeFunctionality(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String projSelected = request.getParameter("assignProj");
		List<Emp> list = dao.getUserName(projSelected);
		System.out.println("helloooo last=" + projSelected);
		session.setAttribute("list", list);
		Emp e = new Emp();
		System.out.println("userid =" + e.getUserID());
		System.out.println("projid =" + e.getProjID());
		System.out.println("firstName =" + e.getFirstNAME());

		List<Emp> allList = dao.getAllUserName(projSelected);
		System.out.println("helloooo 2nd last=" + projSelected);

		session.setAttribute("allList", allList);

		List<Emp> projID = dao.getProjID(projSelected);
		System.out.println("helloooo 2nd last=" + projID);
		session.setAttribute("projID", projID.get(0).getProjID());
		session.setAttribute("allList", allList);
		Emp emp = new Emp();
		System.out.println("userid one =" + emp.getUserID());
		System.out.println("projid one=" + emp.getProjID());
		System.out.println("firstName one =" + emp.getFirstNAME());

		String message = "remove";
		return new ModelAndView("assignProjects", "message", message);

	}
/**
     * *************************************
    *
    * @namesRemoveInSelectBox() method unassigns the selected project for a particular user.
    *
    * @Author:Maitri
    *
    * @Created Date:04/15/2015
    *
    **************************************
    */
	@RequestMapping("/namesRemoveInSelectBox")
	public ModelAndView namesRemoveInSelectBox(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();

		String[] allNames = request.getParameterValues("sbOne");

		for (int i = 0; i < allNames.length; i++) {

			int UserId = Integer.parseInt(request.getParameter(allNames[i]));
			int ProjId = Integer.parseInt(request.getParameter("projid"));
			System.out.println("helloooo all names=" + allNames[i]);
			System.out.println("helloooo userid=" + UserId);
			System.out.println("helloooo projid=" + ProjId);

			
			// int i = 0;
			try {
				//myData data = new myData();

				//lass.forName(data.JDBC_DRIVER);
			/*	con = (Connection) DriverManager.getConnection(data.DB_URL,
						data.USER, data.PASS);*/
				/* con = obj.getDBConnection();
				Statement st = (Statement) con.createStatement();
				i = st.executeUpdate("DELETE lookup_users.* from lookup_users WHERE User_Id IN("
						+ UserId + ") AND Proj_Id IN (" + ProjId + ") ");
				/*i = st.executeUpdate("UPDATE lookup_users SET lookup_users.LookupUser_Status='Inactive' WHERE User_Id IN("
						+ UserId + ") AND Proj_Id IN (" + ProjId + ") ");*/
				dao.deleteLookUP(UserId, ProjId);
				System.out.println("Data is successfully deleted !!!!" + i);
				// System.out.println("rows affected by insert "+st.executeUpdate(i));

				List<Emp> list = dao.getEmployees();
				session.setAttribute("viewEmpList", list);

			} catch (Exception e) {
				System.out.print(e);
				e.printStackTrace();
			}
		}
		// for ending

		return new ModelAndView("viewemp", "message","Project removed successfully from the user");
	}
/**
     * *************************************
     *
     * @resetFunc() method redirects to the viewemp.jsp.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping("/resetFunc")
	public ModelAndView resetFunc() {
		System.out.println("helloo reset");

		return new ModelAndView("viewemp", "", "");
	}
 /**
     * *************************************
     *
     * @projStatus() method gets the active and inactive projects from database.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

	@RequestMapping("/projStatus")
	public ModelAndView getUserNames(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		//String projSelected = request.getParameter("assignProj");
		
		List<Emp> list = dao.getProjStatusActive();
		System.out.println("list Active projects=" + list);
		session.setAttribute("list", list);
		Emp e=new Emp(); 
		System.out.println("projname =" + e.getProjName());
		System.out.println("project status =" + e.getProjectStatus());
		
		
		List<Emp> allList = dao.getProjStatusInactive();
		System.out.println("list inactive projects=" + allList);
		
		session.setAttribute("allList", allList);
		
		//List<Emp> projID = dao.getProjID(projSelected);
		//System.out.println("helloooo 2nd last=" + projID);
		//session.setAttribute("projID", projID.get(0).getProjID());
		//session.setAttribute("allList", allList);
		Emp emp=new Emp(); 
		System.out.println("projname =" + emp.getProjName());
		System.out.println("project status =" + emp.getProjectStatus());

		return new ModelAndView("deleteProj", "", "");

	}
 /**
     * *************************************
     *
     * @namesInProjStatus() method gets the final names of projects after swapping functionality .
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */	
	@RequestMapping("/namesInProjStatus")
	public ModelAndView namesInProjStatus(@ModelAttribute("emp") Emp emp,HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		//EmpDAO data=new EmpDAO();
		System.out.println("in namesIN");
		String[] allNames={};
		String[] allNamesActive={};
		  allNames = request.getParameterValues("sbTwo"); 
		  String delimeter="','";
		  String finaValue=StringUtils.join(allNames, delimeter);
		  allNamesActive = request.getParameterValues("sbOne"); 
		  
		  String allNamesActiveValue=StringUtils.join(allNamesActive, delimeter);
		  System.out.println("finaValue inLogincontroller namesInProjStatus is>>>>>>>>>>>"+finaValue);
		  dao.activeInactiveProj(emp,"Active", allNamesActiveValue);
		  dao.activeInactiveProj(emp,"Inactive", finaValue);
		  List<Emp> allList = dao.getProjStatusInactive();
			session.setAttribute("allList", allList);
			
			List<Emp> list = dao.getProjStatusActive();
			session.setAttribute("list", list);
		 // for(int i=0;i<allNames.length;i++)
		  //{
			  
			 // int projNamesTwo=Integer.parseInt(request.getParameter(allNames[i]));
			//  int ProjId=Integer.parseInt(request.getParameter("projid"));
			 // System.out.println("helloooo all names="+allNames[i]);
			 // System.out.println("helloooo userid="+projNamesTwo);
			//  System.out.println("helloooo projid="+ProjId);
		  
		  
		 
		  
		  //}
			List<Emp> list1 = dao.getEmployees();
			session.setAttribute("viewEmpList", list1);
		return new ModelAndView("viewemp", "message", "Projects saved successfully!!!");

	}
 /**
     * *************************************
     *
     * @usernameAvailability() method checks whether the email entered already exists or not.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */	
/*	@RequestMapping("/usernameAvailability/{email}")
	public ModelAndView usernameAvailability(HttpServletRequest request,
			HttpServletResponse response,@PathVariable("email") String email) {
			//String projSelected = request.getParameter("assignProj");
		try{
	            ResultSet res =dao.userAvailability(request.getParameter("email"));
	            // ps.executeQuery();
	            if(res.first()){
	               System.out.print("email already exists");
	            }else{
	                System.out.print("email is valid");
	            }
	        }catch (Exception e){
	            System.out.println(e);  
	        }

		return new ModelAndView("deleteProj", "", "");

	}*/
 /**
     * *************************************
     *
     * @assignFunc() method 
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */	
	@RequestMapping("/assignFunc")
	public ModelAndView assignFunc(@ModelAttribute("emp") Emp emp,HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession ses = request.getSession();
		
		int i=0;
		List<Emp> assignProjListName= dao.assignProjectList();
		ArrayList assignProjList=new ArrayList<>() ;
		
		while(i<assignProjListName.size())
		{
			System.out.println("in assignProjList"+i+assignProjListName.get(0).getProjName());
			assignProjList.add(assignProjListName.get(i).getProjName());
			i++;
		}
		
		Collections.sort(assignProjList);
		if (assignProjList.size() > 0) {
			ses.setAttribute("AssignProjList", assignProjList);
		}
		
		
		return new ModelAndView("assignProjects", "", "");

	}
 /**
     * *************************************
     *
     * @viewFunc() method updates the list and redirects to viewemp.jsp
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */
	@RequestMapping("/viewFunc")
	public ModelAndView viewFunc(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();


		List<Emp> list = dao.getEmployees();
		session.setAttribute("viewEmpList", list);
		
		return new ModelAndView("viewemp", "", "");

	}
/**
     * *************************************
     *
     * @unassignedUsers() method gets the names of users who are not assigned to any project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

	@RequestMapping("/unassignedUsers")
	public ModelAndView unassignedUsers(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();


		List<Emp> listgetUnassignedUsers = dao.getUnassignedUsers();
		System.out.println("list Active projects getUnassignedUsers=" + listgetUnassignedUsers);
		session.setAttribute("listgetUnassignedUsers", listgetUnassignedUsers);
		Emp e=new Emp(); 
		
		System.out.println("firstname =" + e.getFirstNAME());
		System.out.println("userId =" + e.getUserID());
		System.out.println("email =" + e.getEmail());
		System.out.println("phone =" + e.getPhone());
		System.out.println("full name =" + e.getUsername());
		System.out.println("list is=" + listgetUnassignedUsers);
		
		
		return new ModelAndView("unassignedUsers", "", "");

	}
	
	/*@RequestMapping("/doChngPwd") //this method for changing the password in tester role
	public ModelAndView doChngPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

	//	String emailId = request.getParameter("field_username");
		String pwd = request.getParameter("field_pwd1");
		String chngPwd = request.getParameter("field_pwd2");
	//	String name= request.getParameter("firstNAME");
		
		// String[] roles=request.getParameterValues("dropdown");
		String email=(String) session.getAttribute("email");
		String fname = (String) session.getAttribute("frstname");
		//String userName = firstName;
		System.out.println("email=" + email);
		System.out.println("pwd=" + pwd);
		System.out.println("password=" + chngPwd);
		System.out.println("fname=" + fname);

		

		
		int i = 0;
		try {
			//myData data = new myData();
			
			//Class.forName(data.JDBC_DRIVER);
		//	con = (Connection) DriverManager.getConnection(data.DB_URL,
				//	data.USER, data.PASS);
			/*con = obj.getDBConnection();
			Statement st = con.createStatement();*/
	/*		i = dao.updateUser(email, pwd);
					//st.executeUpdate("UPDATE users SET PASSWORD='"+pwd+"' WHERE email='"+email+"'");
			System.out.println("Password is successfully changed !!!!" + i);
			
		if (i == 1) {
			MailManager sender = new MailManager(SMTP_AUTH_USER, SMTP_AUTH_PWD);
			sender.sendchngPwdMail("QAFramework services", "",
					"QAFramework@miraclesoft.com", email, chngPwd, fname);
			
			List<Emp> list = dao.getEmployees();
			session.setAttribute("viewEmpList", list);
			System.out.println("values in register.qa" + list);
			return new ModelAndView("index", "", "");
		} else {
			String message;
			message = "Wrong username or password.";
			return new ModelAndView("registerFailure", "message", "message");
		}
			
		} catch (Exception e) {
			System.out.print(e);
			e.printStackTrace();
		}
	
		return new ModelAndView("index", "", "");

	}
*/
	/*@RequestMapping("/doChngPwd") //this method for changing the password in tester role
	public ModelAndView doChngPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

	//	String emailId = request.getParameter("field_username");
		String pwd = request.getParameter("field_pwd1");
		String chngPwd = request.getParameter("field_pwd2");
	//	String name= request.getParameter("firstNAME");
		if(pwd.equals(chngPwd))
		{
			System.out.println("same pwd");
		
		
		// String[] roles=request.getParameterValues("dropdown");
		String email=(String) session.getAttribute("email");
		String fname = (String) session.getAttribute("frstname");
		//String userName = firstName;
		System.out.println("email=" + email);
		System.out.println("pwd=" + pwd);
		System.out.println("password=" + chngPwd);
		System.out.println("fname=" + fname);

		

		
		int i = 0;
		try {
			//myData data = new myData();
			
			//Class.forName(data.JDBC_DRIVER);
		//	con = (Connection) DriverManager.getConnection(data.DB_URL,
				//	data.USER, data.PASS);
			/*con = obj.getDBConnection();
			Statement st = con.createStatement();
			i = dao.updateUser(email, pwd);
					//st.executeUpdate("UPDATE users SET PASSWORD='"+pwd+"' WHERE email='"+email+"'");
			System.out.println("Password is successfully changed !!!!" + i);
			
		if (i == 1) {
			MailManager sender = new MailManager(SMTP_AUTH_USER, SMTP_AUTH_PWD);
			sender.sendchngPwdMail("QAFramework services", "",
					"QAFramework@miraclesoft.com", email, chngPwd, fname);
			
			List<Emp> list = dao.getEmployees();
			session.setAttribute("viewEmpList", list);
			System.out.println("values in register.qa" + list);
			return new ModelAndView("index", "", "");
		} else {
			String message;
			message = "Wrong username or password.";
			return new ModelAndView("registerFailure", "message", "message");
		}
		
			
		} catch (Exception e) {
			System.out.print(e);
			e.printStackTrace();
		}
		}
		else{
			System.out.println("no match");
			return new ModelAndView("registerFailure", "", "");
		}
	
		return new ModelAndView("index", "", "");

	}*/
	 @RequestMapping("/doChngPwd") //this method for changing the password in tester role
		public ModelAndView doChngPwd(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			HttpSession session = request.getSession();

		//	String emailId = request.getParameter("field_username");
			String oldpwd1 = request.getParameter("field_pwd1");
			String pwd = request.getParameter("field_pwd2");
			String chngPwd = request.getParameter("field_pwd3");
			
			String oldPwd = (String) session.getAttribute("oldPwd");
		//	String name= request.getParameter("firstNAME");
			System.out.println("old==="+oldPwd+" "+ oldpwd1);
			if(oldPwd.equals(oldpwd1))
			{
				System.out.println("Both are equals");
			
			 if(pwd.equals(chngPwd))
			{
				System.out.println("same pwd");
			
			
			
			// String[] roles=request.getParameterValues("dropdown");
			String email=(String) session.getAttribute("email");
			String fname= (String) session.getAttribute("frstname");
		//	String oldPwd = (String) session.getAttribute("oldpwd");
			//String userName = firstName;
			System.out.println("email=" + email);
			System.out.println("pwd=" + pwd);
			System.out.println("password=" + chngPwd);
			System.out.println("fname=" + fname);
			System.out.println("password=" + oldpwd1);

			int i = 0;
			try {
				//myData data = new myData();
				
				//Class.forName(data.JDBC_DRIVER);
			//	con = (Connection) DriverManager.getConnection(data.DB_URL,
					//	data.USER, data.PASS);
				/*con = obj.getDBConnection();
				Statement st = con.createStatement();*/
				i = dao.updateUser(email, pwd);
						//st.executeUpdate("UPDATE users SET PASSWORD='"+pwd+"' WHERE email='"+email+"'");
				System.out.println("Password is successfully changed !!!!" + i);
				
			if (i == 1) {
				MailManager sender = new MailManager(SMTP_AUTH_USER, SMTP_AUTH_PWD);
				sender.sendchngPwdMail("QAFramework services", "",
						"QAFramework@miraclesoft.com", email, chngPwd, fname);
				
				List<Emp> list = dao.getEmployees();
				session.setAttribute("viewEmpList", list);
				System.out.println("values in register.qa" + list);
				return new ModelAndView("index", "", "");
			} else {
				String message;
				message = "Wrong username or password.";
				return new ModelAndView("registerFailure", "message", message);
			}
						
			} catch (Exception e) {
				System.out.print(e);
				e.printStackTrace();
			}
			}
			
			 else{
				 System.out.println("Password does not match");
				 String message;
				 message="Password does not match";
					return new ModelAndView("changePassword", "message", message);
			}
			}
			 else{
					System.out.println("Password are not match!!!");
					String message;
					message="Password does not match!!!";
					return new ModelAndView("changePassword", "message",message );
				}
			return new ModelAndView("index", "", "");
}
	@RequestMapping("/oldPwdEmail")
	public ModelAndView oldPwdEmail(@ModelAttribute("emp") Emp emp,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		
		String oldEmail = request.getParameter("oldEmail");
		System.out.println("email =" + oldEmail);

		
		int result = dao.checkForEmailExistance(emp,oldEmail);
		System.out.println("result pwd===="+result);
		if(result==1)
		{
			RandomGenerator randomGenerator=new RandomGenerator();
			System.out.println("email login=" + session.getAttribute("email"));
		
			int updateresult=0;
			String newPwd="";
			newPwd=randomGenerator.generatePassword();
			System.out.println("randomGenerator =" + newPwd);
			
			dao.updateOTPPwd(emp, newPwd,oldEmail);
			MailManager sender = new MailManager(SMTP_AUTH_USER, SMTP_AUTH_PWD);
			sender.sendOTPMail("QAFramework services", "",
					"QAFramework@miraclesoft.com", oldEmail,newPwd);
			
			System.out.println("hello new");
			
			return new ModelAndView("index", "message", "Mail has been sent successfully to the registered email.");
		}
		else
			//HttpSession executeSession = request.getSession();
			//RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				
		/*List<Emp> listgetUnassignedUsers = dao.getUnassignedUsers();
		System.out.println("list Active projects getUnassignedUsers=" + listgetUnassignedUsers);
		session.setAttribute("listgetUnassignedUsers", listgetUnassignedUsers);
		Emp e=new Emp(); 
		
		System.out.println("firstname =" + e.getFirstNAME());
		System.out.println("userId =" + e.getUserID());
		System.out.println("email =" + e.getEmail());
		System.out.println("phone =" + e.getPhone());
		System.out.println("full name =" + e.getUsername());
		System.out.println("list is=" + listgetUnassignedUsers);
		
		*/
		return new ModelAndView("index", "", "");

	}

	@RequestMapping("/doFrgtPwdChng") //this method for changing the password in tester role
	public ModelAndView doFrgtPwdChng(HttpServletRequest request,
	HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();

	// String emailId = request.getParameter("field_username");
	//String oldpwd1 = request.getParameter("field_pwd1");
	String pwd = request.getParameter("field_pwd2");
	String chngPwd = request.getParameter("field_pwd3");

	// String oldPwd = (String) session.getAttribute("oldPwd");
	// String name= request.getParameter("firstNAME");
	System.out.println("pwds==="+pwd+" "+ chngPwd);

	if(pwd.equals(chngPwd))
	{
	System.out.println("same pwd");



	// String[] roles=request.getParameterValues("dropdown");
	String email=(String) session.getAttribute("email");
	String fname= (String) session.getAttribute("frstname");
	// String oldPwd = (String) session.getAttribute("oldpwd");
	//String userName = firstName;
	System.out.println("email=" + email);

	// System.out.println("password=" + oldpwd1);

	int i = 0;
	try {
	//myData data = new myData();

	//Class.forName(data.JDBC_DRIVER);
	// con = (Connection) DriverManager.getConnection(data.DB_URL,
	// data.USER, data.PASS);
	/*con = obj.getDBConnection();
	Statement st = con.createStatement();*/
	i = dao.updateUser(email, pwd);
	//st.executeUpdate("UPDATE users SET PASSWORD='"+pwd+"' WHERE email='"+email+"'");
	System.out.println("Password is successfully changed !!!!" + i);

	if (i == 1) {
	MailManager sender = new MailManager(SMTP_AUTH_USER, SMTP_AUTH_PWD);
	sender.sendchngPwdMail("QAFramework services", "",
	"QAFramework@miraclesoft.com", email, chngPwd, fname);

	List<Emp> list = dao.getEmployees();
	session.setAttribute("viewEmpList", list);
	System.out.println("values in register.qa" + list);
	return new ModelAndView("index", "", "");
	} else {
	String message;
	message = "Wrong username or password.";
	return new ModelAndView("registerFailure", "message", message);
	}

	} catch (Exception e) {
	System.out.print(e);
	e.printStackTrace();
	}
	}

	else{
	System.out.println("Password does not match");
	String message;
	message="Password does not match";
	return new ModelAndView("changePassword", "message", message);

	}
	return new ModelAndView("index", "", "");
	}
	/**
     * *
	 * @return ************************************
     *
     * @unassignedUsers() method gets the names of users who are not assigned to any project.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

	@RequestMapping("/usernameAvailability")
	public @ResponseBody String usernameAvailability(@RequestParam("email") String email) {
		String response = "";
		System.out.println("hi"+email);
		int count=0;
		try{
		       count=dao.TestEmail(email);
		            if(count>0){
		            	response="email already exists";
		            }else{
		            	response="email is valid";
		            }
		        }catch (Exception e){
		            System.out.println(e);  
		        }
		// Process the request
		// Prepare the response string
		return response;

	}
	
	@RequestMapping("/listofTL")/* same as assignfunc*/
	public ModelAndView listofTL(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();

		
		
		int i=0;
		
		String email=(String) session.getAttribute("email");
		System.out.println("email=="+email);
		List<Emp> assignProjListName= dao.getTLProj(email);
		ArrayList assignProjList=new ArrayList<>() ;
		
		while(i<assignProjListName.size())
		{
			System.out.println("in assignProjList"+i+assignProjListName.get(0).getProjName());
			assignProjList.add(assignProjListName.get(i).getProjName());
			i++;
		}
		
		Collections.sort(assignProjList);
		if (assignProjList.size() > 0) {
			session.setAttribute("AssignProjList", assignProjList);
		}
		
		return new ModelAndView("TLAssignRemove", "", "");

	}
	
	@RequestMapping("/TLgetUserName/{assignProj}/{assignRemove}")
	public ModelAndView TLgetUserNames(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("assignProj") String assignProj,
			@PathVariable("assignRemove") String assignRemove) {
		System.out.println("tl:::::::::::::::::" + request);
		HttpSession session = request.getSession();
		session.setAttribute("allList", "");
		// session.setAttribute("list", "");
		String projSelected = assignProj;
		System.out.println("jjjjjjjjjjjjjjjj");
		String assignRemoveOption = assignRemove;
		System.out.println("value in assign/Remove=" + assignRemoveOption);
		System.out.println("Selected proj value assign=" + projSelected);

		session.setAttribute("projSelected", projSelected);
		session.setAttribute("assignRemoveOption", assignRemoveOption);

		if (assignRemoveOption.equalsIgnoreCase("assign"))

		/*
		 * List<Emp> list = dao.getUserName(projSelected);
		 * System.out.println("helloooo last=" + projSelected);
		 * session.setAttribute("list", list); Emp e = new Emp();
		 * System.out.println("userid =" + e.getUserID());
		 * System.out.println("projid =" + e.getProjID());
		 * System.out.println("firstName =" + e.getFirstNAME());
		 */

		{
			System.out.println("value in assign/Remove in if 1="
					+ assignRemoveOption);

			List<Emp> allList = dao.getAllUserName(projSelected);

			// System.out.println("helloooo 2nd last=" + projSelected);
			// Collections.sort(allList);
			session.setAttribute("allList", allList);

			List<Emp> projID = dao.getProjID(projSelected);
			System.out.println("projID assign=" + projID);
			session.setAttribute("projID", projID.get(0).getProjID());

			// session.setAttribute("allList", allList);
			// Emp emp = new Emp();
			// System.out.println("userid one =" + emp.getUserID());
			// System.out.println("projid one=" + emp.getProjID());
			// System.out.println("firstName one =" + emp.getFirstNAME());

			String message = "assign1";
			return new ModelAndView("TLAssignRemove", "message", message);
		} else if (assignRemoveOption.equalsIgnoreCase("remove"))

		{
			System.out.println("value in assign/Remove in if 2="
					+ assignRemoveOption);
			List<Emp> list = dao.getUserName(projSelected);
			if (list.size() != 0) {
				System.out.println("size in all list is=" + list.size());

				System.out.println("selected proj in remove=" + projSelected);
				session.setAttribute("list", list);
				List<Emp> projID = dao.getProjID(projSelected);
				System.out.println("proj id remove=" + projID);
				session.setAttribute("projID", projID.get(0).getProjID());
				// session.setAttribute("allList", allList);
				Emp emp = new Emp();
				System.out.println("userid one =" + emp.getUserID());
				System.out.println("projid one=" + emp.getProjID());
				System.out.println("firstName one =" + emp.getFirstNAME());

				String message = "remove";
				return new ModelAndView("TLAssignRemove", "message", message);
			} else {
				String message = "noData";
				return new ModelAndView("TLAssignRemove", "message", message);
			}

		} else {
			System.out.println("value in assign/Remove in if 3="
					+ assignRemoveOption);
			String message = "other";
			return new ModelAndView("TLAssignRemove", "message", message);
		}

	}
	@RequestMapping("/TLInsertUserNames")
	public ModelAndView TLInsertUserNames(@ModelAttribute("emp") Emp emp,HttpServletRequest request,/* same as names in select box*/
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String[] allNames = request.getParameterValues("sbTwo");
		System.out.println(" all names length=" + allNames.length);

		for (int i = 0; i < allNames.length; i++) {
			System.out.println(" request.getParameter(allNames[i])=" +request.getParameter(allNames[i]));
			int UserId = Integer.parseInt(request.getParameter(allNames[i]));

			int ProjId = Integer.parseInt(request.getParameter("projid"));
			System.out.println("helloooo all names1=" + allNames.length);
			System.out.println("helloooo userid=" + UserId);
			System.out.println("helloooo projid=" + ProjId);
			String toolname=request.getParameter("toolname");

			int toolid = dao.gettoolid(emp,toolname);
		//	Emp emp = new Emp();
			System.out.println("tool id ="+emp.getToolID());

			try {
				//myData data = new myData();

				//Class.forName(data.JDBC_DRIVER);
			/*	con = (Connection) DriverManager.getConnection(data.DB_URL,
						data.USER, data.PASS);*/
				 /*con = obj.getDBConnection();
				 st =  con.createStatement();
				st.executeUpdate("insert into lookup_users(User_Id,Proj_Id,Tool_Id,LookupUser_Status) values("
						+ UserId + "," + ProjId + ",1005,'Active')");
				System.out.println("Data is successfully inserted !!!!" + i);*/
				// System.out.println("rows affected by insert "+st.executeUpdate(i));
				dao.TLinsertLookUP(UserId, ProjId,toolid);

			} catch (Exception e) {
				System.out.print(e);
				e.printStackTrace();
			}
		}

		List<Emp> list = dao.getEmployees();
		session.setAttribute("viewEmpList", list);
		
		return new ModelAndView("welcome", "message", "Projects assigned successfully to the user");
	}
}
