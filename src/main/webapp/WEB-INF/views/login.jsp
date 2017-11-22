<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Spring MVC CRUD</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-md-4">
				<h4 class="text-center">User Login</h4>
				<hr>
				<form method="post" action="j_spring_security_check">
				    <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
					<div class="form-group">
						<label for="login">Login: </label>
						<input type="text" name="username" class="form-control" value=""/>
					</div>
					<div class="form-group">
						<label for="password">Password: </label>
						<input type="password" name="password" class="form-control"  value=""/>
					</div>
					<div class="form-group">
						<input type="submit" value="Login" class="btn btn-success" />
					</div>
				</form>
				<c:if test="${param.error eq '1' }">
   					<font color="red">Login / Password incorrect</font>
   				</c:if> 

			</div>
		</div>
	</div>
</body>
</html>
