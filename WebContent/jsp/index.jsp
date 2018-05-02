<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>QA Framework</title>
  
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/main.css">
	<link href='http://fonts.googleapis.com/css?family=Cookie' rel='stylesheet' type='text/css'>
	


<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
var msg=document.getElementById("message").value;
if(msg!="")
{
alert(msg);
}
   // jQuery methods go here...

});
	</script>
    </head>
 
  <body class="submit_body">

	<div id="sitename">
		<div class="width">
			<h1>
			<a href="#" style=" margin-left: 14%;float:left;   margin-top: 20px;">
			<img
					src="${pageContext.request.contextPath}/images/miraclelogo.png"
					 /></a>
    <a href="#" style="float: right;position:relative ;left: 800px;bottom: 106px;">
<img
					src="${pageContext.request.contextPath}/images/QAlogo.png"
					style="height: 190px;
     
    width: 230px;" /></a>
					

		</h1>

			<!-- nav>
					<ul>
        					<li class="start selected"><a href="#">Sign In</a></li>
        	    		 	<li class=""><a href="#">Contact Us</a></li>
         	   				<li class=""><a href="#">About Us</a></li>
          	  				
        				</ul>
				</nav> -->

			<div class="clear"></div>
		</div>
	</div>

<!-- The content of your page would go here. -->



        
         <!--  <h2>${message}</h2><br/> -->
          <input type="hidden" id="message" value="${message}"/>
        <div class="login-form" id="myLogin" tabindex="-1" role="dialog" style="outline: none;" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" id="Form_login" role="document" >
                                        <div class="modal-content">
                                            <div class="modal-header " style="background-color: #2368a0; border-top-right-radius: 0px; border-top-left-radius: 0px;">
                                               
                                                <h4 class="modal-title" id="myModalLabel" style="color:white">SIGN IN</h4>
                                            </div>
                                            <div class="modal-body">
                                                
                                                <script type="text/javascript">
                                             
                                                  //  $('#myLogin').modal('show');
                                  
                                                </script>  
                                                
                                               <form action="login.qa" method="post" name="userLoginForm" id="userLoginForm" > 
                                                    <div class="logCredential"><i class="fa fa-envelope"></i> <input type="text" placeholder="Email" class="frgt_pwd" name="username" id="username" pattern="[^@]+@[^@]+\.[a-zA-Z]{2,6}" required data-error-message="LoginId is required!" tabindex="1"/></div>

                                                    <div class="logCredential"><i class="fa fa-key"></i> <input type="password" placeholder="Password" class="frgt_pwd"  name='password' pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" id="password" title="Must be at least 6 characters long, and contain at least one number, one uppercase and one lowercase letter" required data-type="Password" tabindex="2" /></div>
  <p id="wrapper" class="frgt_pwd"><a  class="slide_open" href="jsp/frgtPwdEmail.jsp" id="closeLogin" ><font class ="fgtPwd" style="float:right">Forgot password?</font></a></p>
                                                   
                                                    <div class="LoginButton">
                                                        <button type="submit" >PROCEED</button>
														
                                                    </div>
													<div class="cancel">
													<button type="submit" >CANCEL</button>
													</div>
													 
                                                </form>




                                            </div></div>
											
																			</div></div>
											<footer class="clear">
			<div  class="width">
				<p class="left"> Copyrights <i class="fa fa-copyright"></i> 2017.All Rights Reserved.  </p>
				<p class="right"><a title="www.miraclesoft.com" href="http://www.miraclesoft.com" target="_blank">  Miracle Software Systems,Inc. </a></p>
			</div>
		</footer>

    </body>
</html>