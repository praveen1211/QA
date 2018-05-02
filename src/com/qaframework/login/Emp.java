/**
     * *************************************
     *
     * @Emp.java-Contains setters and getters for the variables used.
     *
     * @Author:Maitri
     *
     * @Created Date:04/15/2015
     *
     **************************************
     */

package com.qaframework.login;

import java.util.ArrayList;
import java.util.Date;


   public class Emp {
   private int projID;
   private String projName;
   private String firstNAME;
   private String toolName;
   private int roleID;
   private int toolID;
   private String lastNAME;
   private String email;
   private String phone;
   private String projectStatus;
   private String password;
   private String userRole;
   private String username;
   private String createDate;
   private String completeDate;
   private int userID;
   private int flag;
   
   
   public int getFlag() {
	return flag;
}
public void setFlag(int flag) {
	this.flag = flag;
}
public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
 public String getCreateDate() {
	return createDate;
}
public void setCreateDate(String createDate) {
	this.createDate = createDate;
}
public String getCompleteDate() {
	return completeDate;
}
public void setCompleteDate(String completeDate) {
	this.completeDate = completeDate;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
private String description;
   
   
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
public String getUserRole() {
	return userRole;
}
public void setUserRole(String userRole) {
	this.userRole = userRole;
}
public String getProjectStatus() {
	return projectStatus;
}
public void setProjectStatus(String projectStatus) {
	this.projectStatus = projectStatus;
}
public String getLastNAME() {
	return lastNAME;
}
public void setLastNAME(String lastNAME) {
	this.lastNAME = lastNAME;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public ArrayList aList;


public ArrayList getaList() {
	return aList;
}
public void setaList(ArrayList aList) {
	this.aList = aList;
}
public int getUserID() {
	return userID;
}
public void setUserID(int userID) {
	this.userID = userID;
}
public int getToolID() {
	return toolID;
}
public void setToolID(int toolID) {
	this.toolID = toolID;
}
public String getFirstNAME() {
	return firstNAME;
}
public void setFirstNAME(String firstNAME) {
	this.firstNAME = firstNAME;
}
public String getToolName() {
	return toolName;
}
public void setToolName(String toolName) {
	this.toolName = toolName;
}
public int getProjID() {
		return projID;
	}
	public void setProjID(int projID) {
		this.projID = projID;
	}
public String getProjName() {
	return projName;
}
public void setProjName(String projName) {
	this.projName = projName;
}


 
}