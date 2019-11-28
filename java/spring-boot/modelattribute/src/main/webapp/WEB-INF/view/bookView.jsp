<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Spring MVC Form Handling</title>
</head>
<body>

	<h2>Submitted Book Information</h2>
	<h3>${msg}</h3>
	<table>
		<tr>
			<td>Author :</td>
			<td>${author}</td>
		</tr>
		<tr>
			<td>Title :</td>
			<td>${title}</td>
		</tr>
		<tr>
			<td>ISBN :</td>
			<td>${isbn}</td>
		</tr>
	</table>
</body>
</html>