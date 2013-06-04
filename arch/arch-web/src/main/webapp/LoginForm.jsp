<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />	
	<title>Вход в систему</title>
</head>


<body>
		<div id="section-content1" style="width: 550px;
										border: solid 1px #CCC;
										padding: 20px;
										box-shadow: 1px 3px 10px #CFCFCF;
										border-radius: 5px;
										top: 200px;
										margin-left: auto; 
										margin-right: auto;
										background-color: white;
										position: relative;">
        <!--span id="content"-->
		
	<form method="post" action="/arch/LoginServlet" class="def-form" style="margin-left: auto; margin-right: auto; ">
		<fieldset style="border:none">
		<table width="200" border="0">
			<tr>
				<td><table width="400" border="0">
						<tr>
							<td class="shortlabel"><label class="fieldlabel">Имя пользователя:</label></td>
							<td class="short"><input type="text" size="20" name="j_username" class="short"/></td>
						</tr>
						<tr>
							<td class="shortlabel"><label class="fieldlabel">Пароль:</label></td>
							<td class="short"><input type="password" size="20" name="j_password" class="short"/></td>
						</tr>
						<tr>
							<td></td>

							<td>
								<div style="float: right; margin-right:55px">
									<button type="submit" class="ui-button-text" style="padding: 0.5em 1em;">Вход</button>
								</div>
							</td>
						</tr>
					</table></td>
			</tr>

		</table>
		</fieldset>
		<input type="hidden" value="form" name="loginSource" />
	</form>
	
	
		<!--/span-->
        </div>
		
</body>
	</html>
</jsp:root>