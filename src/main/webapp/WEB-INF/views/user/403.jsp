<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Spring MVC - Access Denied</title>
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
				<h4 class="text-center">You do not have permission to access this page!</h4>
				<hr>
				<table class="table table-bordered table-striped">
					<tbody>
						<tr>
							<td align="center">
								<form class="form-inline" action="<%=request.getContextPath()%>/j_spring_security_logout" method="post">
							      <input type="submit" value="Sign in as different user"  class="btn btn-primary"/>
							      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    </form>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>

</html>
