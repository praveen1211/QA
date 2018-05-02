package com.mss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import com.spring.controller.LoginBean;

@Controller
public class ExcelController {
   @RequestMapping("/QAFramework/ex/excel")  
   public ModelAndView Excel(HttpServletRequest request,
		   HttpServletResponse response) {
	   
	   
	   try {
		   GettingReports gettingReports=new GettingReports();
	//fin.testing();
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	   
	  String message = " hello welcome to QA testing.";
	   
	   return new ModelAndView("reports", 
 			  "message", message);    
   }
   }