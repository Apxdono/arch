<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Вход в систему</title>
</head>
<body>
	<div id="section-content1"
		style="width: 550px; border: solid 1px #CCC; padding: 20px; box-shadow: 1px 3px 10px #CFCFCF; border-radius: 5px; top: 200px; margin-left: auto; margin-right: auto; background-color: white; position: relative;">
		<form method="post" action="j_security_check" class="def-form"
			style="margin-left: auto; margin-right: auto;">
			<fieldset style="border: none">
				<table width="200" border="0">
					<tr>
						<td><table width="400" border="0">
								<tr>
									<td class="shortlabel"><label class="fieldlabel">Имя
											пользователя:</label></td>
									<td class="short"><input type="text" size="20"
										name="j_username" class="short" /></td>
								</tr>
								<tr>
									<td class="shortlabel"><label class="fieldlabel">Пароль:</label></td>
									<td class="short"><input type="password" size="20"
										name="j_password" class="short" /></td>
								</tr>
								<tr>
									<td></td>

									<td>
										<div style="float: right; margin-right: 55px">
											<button type="submit" class="ui-button-text"
												style="padding: 0.5em 1em;">Вход</button>
										</div>
									</td>
								</tr>
							</table></td>
					</tr>

				</table>
			</fieldset>
			<input type="hidden" value="form" name="loginSource" />
		</form>
	</div>
</body>
</html>
