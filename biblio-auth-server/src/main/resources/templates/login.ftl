<html>
<head>
<link rel="stylesheet" href="css/wro.css"/>
<link href="css/angular-bootstrap.css" rel="stylesheet">
</head>
<body>
<#if RequestParameters['error']??>
	<div class="alert alert-danger">
		There was a problem logging in. Please try again.
	</div>
</#if>
	<div class="container">
<div class="col-md-6 col-md-offset-3">
    <h2>Login</h2>
		<form role="form" action="login" method="post">
		  <div class="form-group">
		    <label for="username">Username:</label>
		    <input type="text" class="form-control" id="username" name="username"/>
		  </div>
		  <div class="form-group">
		    <label for="password">Password:</label>
		    <input type="password" class="form-control" id="password" name="password"/>
		  </div>
		  <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="form-actions">
                   <button type="submit" class="btn btn-primary">Submit</button>
                </div>
		</form>
	</div>
</div>
	<script src="js/wro.js" type="text/javascript"></script>
</body>
</html>